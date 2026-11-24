package com.pasterdream.pasterdreammod.world.block.portal;

import com.pasterdream.pasterdreammod.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

/**
 * 染梦世界传送门方块。行为对齐原版下界传送门：
 * 无碰撞、发光、玩家/生物/掉落物进入即触发跨维度传送，框架被破坏后自动消失。
 * 此外带有“染梦侵染”特性：随机刻时按 {@code pasterdream:dyedream_contamination} 配方，
 * 由近及远地把周围方块转换为染梦版本。
 * <p>
 * 1.21.1 起原版改用 {@link Portal} 接口与 {@link net.minecraft.world.entity.PortalProcessor} 管理
 * 门内计时、冷却与维度切换，因此这里只负责声明过渡时间与目标地点，不再自行累计 NBT 计时。
 */
public class DyedreamWorldPortalBlock extends Block implements Portal {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

    public DyedreamWorldPortalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(AXIS) == Direction.Axis.Z ? Z_AXIS_AABB : X_AXIS_AABB;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        Direction.Axis neighborAxis = direction.getAxis();
        Direction.Axis axis = state.getValue(AXIS);
        boolean samePlane = axis != neighborAxis && neighborAxis.isHorizontal();
        return !samePlane && !neighborState.is(this)
                && !new DyedreamWorldPortalShape(level, pos, axis).isComplete()
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.canUsePortal(false)) {
            return;
        }
        // 客户端始终标记门内状态以驱动原版传送门叠加层；服务端仅在总开关打开时进入传送流程。
        if (level.isClientSide || Config.DYEDREAM_WORLD_LEAPSTONE_ENABLED.get()) {
            entity.setAsInsidePortal(this, pos);
        }
    }

    @Override
    public int getPortalTransitionTime(ServerLevel level, Entity entity) {
        return entity instanceof Player player
                ? Math.max(1, level.getGameRules().getInt(player.getAbilities().invulnerable
                        ? GameRules.RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY
                        : GameRules.RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY))
                : 0;
    }

    @Nullable
    @Override
    public DimensionTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof DyedreamWorldPortalBlock)) {
            return null;
        }
        return DyedreamWorldPortalTeleporter.travel(level, entity, pos, state);
    }

    @Override
    public Portal.Transition getLocalTransition() {
        return Portal.Transition.CONFUSION;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(100) == 0) {
            level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS,
                    0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }
        if (random.nextInt(2) == 0) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();
            level.addParticle(ParticleTypes.CHERRY_LEAVES, x, y, z, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!Config.DYEDREAM_WORLD_LEAPSTONE_ENABLED.get() || !Config.DYEDREAM_PORTAL_CONTAMINATION_ENABLED.get()) {
            return;
        }
        DyedreamContamination.tick(level, pos, Config.DYEDREAM_PORTAL_CONTAMINATION_RADIUS.get(), 1);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(AXIS)) {
                case X -> state.setValue(AXIS, Direction.Axis.Z);
                case Z -> state.setValue(AXIS, Direction.Axis.X);
                default -> state;
            };
            default -> state;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}
