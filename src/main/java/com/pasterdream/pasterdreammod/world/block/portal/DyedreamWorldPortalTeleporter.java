package com.pasterdream.pasterdreammod.world.block.portal;

import com.pasterdream.pasterdreammod.helper.DreamDimensionHelper;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * 染梦世界传送门的跨维度逻辑。
 * <p>
 * 坐标 1:1 对应；到达对面时若对应位置没有传送门，则以来源门内空左下角为基准
 * 生成一座默认 4×5 外框（内空 2×3）的传送门，并清理空间、铺设垫脚平台。
 * <p>
 * 与原版下界传送门一致：结果以 {@link DimensionTransition} 返回，门内计时、冷却、维度切换、
 * 效果包重发与区块票据都交由原版 {@link net.minecraft.world.entity.PortalProcessor} 和
 * {@link Entity#changeDimension(DimensionTransition)} 处理。
 */
public final class DyedreamWorldPortalTeleporter {
    private DyedreamWorldPortalTeleporter() {}

    /** 复用已有传送门的水平/垂直搜索半径。 */
    private static final int SEARCH_RADIUS = 4;
    /** 默认生成传送门的内空尺寸。 */
    private static final int DEFAULT_WIDTH = 2;
    private static final int DEFAULT_HEIGHT = 3;

    /**
     * 由来源门计算目标维度的落地位置。目标维度不可用时返回 {@code null}，原版会放弃本次传送。
     */
    @Nullable
    public static DimensionTransition travel(ServerLevel source, Entity entity, BlockPos portalPos, BlockState portalState) {
        Direction.Axis axis = portalState.getValue(DyedreamWorldPortalBlock.AXIS);
        ResourceKey<Level> targetKey = source.dimension().equals(DreamDimensionHelper.DYEDREAM_WORLD)
                ? Level.OVERWORLD
                : DreamDimensionHelper.DYEDREAM_WORLD;
        ServerLevel target = source.getServer().getLevel(targetKey);
        if (target == null) {
            return null;
        }

        BlockUtil.FoundRectangle sourceRect = BlockUtil.getLargestRectangleAround(
                portalPos, axis, 21, Direction.Axis.Y, 21,
                pos -> source.getBlockState(pos) == portalState);
        BlockPos interiorMin = sourceRect.minCorner;
        if (interiorMin.getY() <= source.getMinBuildHeight()) {
            return null;
        }

        Vec3 landing = resolveDestination(target, interiorMin, axis);

        source.playSound(null, portalPos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 1.0F, 1.0F);

        // 落地后播放同一音效并放置区块票据，避免对面区块在抵达前被卸载。
        DimensionTransition.PostDimensionTransition post = arrived -> {
            arrived.level().playSound(null, arrived.blockPosition(),
                    SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 1.0F, 1.0F);
            arrived.placePortalTicket(BlockPos.containing(arrived.position()));
        };

        return new DimensionTransition(target, landing, entity.getDeltaMovement(),
                entity.getYRot(), entity.getXRot(), post);
    }

    /** 目标侧已有门则复用，否则在对应位置生成默认尺寸的新门，返回落地坐标。 */
    private static Vec3 resolveDestination(ServerLevel target, BlockPos desiredInteriorMin, Direction.Axis axis) {
        target.getChunk(desiredInteriorMin);

        BlockPos existing = findPortalBlockNear(target, desiredInteriorMin);
        if (existing != null) {
            BlockState state = target.getBlockState(existing);
            if (state.getBlock() instanceof DyedreamWorldPortalBlock) {
                Direction.Axis existingAxis = state.getValue(DyedreamWorldPortalBlock.AXIS);
                BlockUtil.FoundRectangle rect = BlockUtil.getLargestRectangleAround(
                        existing, existingAxis, 21, Direction.Axis.Y, 21,
                        pos -> target.getBlockState(pos) == state);
                return landingPosition(rect.minCorner, existingAxis, rect.axis1Size, rect.axis2Size);
            }
        }

        int y = Mth.clamp(desiredInteriorMin.getY(),
                target.getMinBuildHeight() + 2, target.getMaxBuildHeight() - 6);
        BlockPos min = new BlockPos(desiredInteriorMin.getX(), y, desiredInteriorMin.getZ());
        placePortal(target, min, axis);
        return landingPosition(min, axis, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Nullable
    private static BlockPos findPortalBlockNear(ServerLevel level, BlockPos center) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int dy = -SEARCH_RADIUS; dy <= SEARCH_RADIUS; dy++) {
            for (int dx = -SEARCH_RADIUS; dx <= SEARCH_RADIUS; dx++) {
                for (int dz = -SEARCH_RADIUS; dz <= SEARCH_RADIUS; dz++) {
                    mutable.set(center.getX() + dx, center.getY() + dy, center.getZ() + dz);
                    if (level.isLoaded(mutable)
                            && level.getBlockState(mutable).getBlock() instanceof DyedreamWorldPortalBlock) {
                        return mutable.immutable();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 以内空左下角 {@code min} 为基准生成一座默认尺寸的传送门。
     * 外框位于内空外扩一格，仅掏空框架内部通道（内空横截面，沿法线两侧各扩一格），
     * 同时在框架下方（若悬空）铺设垫脚平台。
     * <p>
     * 生成过程中被替换的可破坏方块，若存在方块物品则作为掉落物掉出；无法破坏的方块保持原样。
     */
    private static void placePortal(ServerLevel level, BlockPos min, Direction.Axis axis) {
        BlockState frame = ModBlocks.DYEDREAM_WORLD_LEAPSTONE.get().defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();

        // 第一遍：铺设外框与垫脚平台，内空先留空。
        for (int along = -1; along <= DEFAULT_WIDTH; along++) {
            for (int y = -2; y <= DEFAULT_HEIGHT; y++) {
                for (int normal = -1; normal <= 1; normal++) {
                    BlockPos pos = offset(min, axis, along, y, normal);
                    if (y == -2) {
                        if (!level.getBlockState(pos).isSolid()) {
                            generateBlock(level, pos, frame, 3);
                        }
                    } else if (normal != 0) {
                        // 只掏空框架内部通道：内空横截面（DEFAULT_WIDTH × DEFAULT_HEIGHT），
                        // 沿法线两侧各扩一格，共 3 格深。例如 4×5 外框掏空 2×3×3。
                        if (along >= 0 && along < DEFAULT_WIDTH && y >= 0 && y < DEFAULT_HEIGHT) {
                            generateBlock(level, pos, air, 3);
                        }
                    } else {
                        boolean perimeter = along == -1 || along == DEFAULT_WIDTH
                                || y == -1 || y == DEFAULT_HEIGHT;
                        generateBlock(level, pos, perimeter ? frame : air, 3);
                    }
                }
            }
        }

        // 第二遍：外框完整后再放置传送门方块，避免 updateShape 将未成形的门方块清除。
        BlockState portal = ModBlocks.DYEDREAM_WORLD_PORTAL.get().defaultBlockState()
                .setValue(DyedreamWorldPortalBlock.AXIS, axis);
        for (int along = 0; along < DEFAULT_WIDTH; along++) {
            for (int y = 0; y < DEFAULT_HEIGHT; y++) {
                generateBlock(level, offset(min, axis, along, y, 0), portal, 18);
            }
        }
    }

    /**
     * 传送门生成时替换一个方块：无法破坏的方块保持原样（返回 false）；
     * 可破坏的方块若存在方块物品，则先按该物品掉落，再放置新方块。
     */
    private static boolean generateBlock(ServerLevel level, BlockPos pos, BlockState state, int flags) {
        BlockState existing = level.getBlockState(pos);
        if (!existing.isAir()) {
            if (existing.getDestroySpeed(level, pos) < 0) {
                return false;
            }
            if (existing != state) {
                Item item = existing.getBlock().asItem();
                if (item != Items.AIR) {
                    Block.popResource(level, pos, new ItemStack(item));
                }
            }
        }
        return level.setBlock(pos, state, flags);
    }

    private static BlockPos offset(BlockPos min, Direction.Axis axis, int along, int y, int normal) {
        return axis == Direction.Axis.X
                ? min.offset(along, y, normal)
                : min.offset(normal, y, along);
    }

    /** 由门内空左下角与尺寸算出实体落地坐标（内空底边中心）。 */
    private static Vec3 landingPosition(BlockPos min, Direction.Axis axis, int width, int height) {
        double y = min.getY();
        if (axis == Direction.Axis.X) {
            return new Vec3(min.getX() + width / 2.0, y, min.getZ() + 0.5);
        }
        return new Vec3(min.getX() + 0.5, y, min.getZ() + width / 2.0);
    }
}
