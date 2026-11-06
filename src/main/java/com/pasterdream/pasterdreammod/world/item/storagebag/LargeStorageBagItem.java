package com.pasterdream.pasterdreammod.world.item.storagebag;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class LargeStorageBagItem extends Item {

    public static final int SLOT_COUNT = 25; // 5x5
    private static final String TAG_CAPTURED_ENTITY = "CapturedEntity";

    public LargeStorageBagItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    public static List<ItemStack> getInventory(ItemStack stack) {
        return BagItemData.load(stack, SLOT_COUNT);
    }

    public static void saveInventory(ItemStack stack, List<ItemStack> items) {
        BagItemData.save(stack, items);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.pasterdream.introduction.tooltip"));
            tooltip.add(Component.translatable("tooltip.pasterdream.large_storage_bag.tooltip1"));
            tooltip.add(Component.translatable("tooltip.pasterdream.large_storage_bag.tooltip2",
                    Component.translatable(Config.CREATURE_CAPTURE_ENABLED.get() ?
                            "tooltip.pasterdream.large_storage_bag.catch_enabled" :
                            "tooltip.pasterdream.large_storage_bag.catch_disabled")));
            // 显示捕获生物名称
            if (hasCapturedEntity(stack)) {
                CompoundTag bagTag = getBagTag(stack);
                CompoundTag capturedTag = bagTag.getCompound(TAG_CAPTURED_ENTITY);
                CompoundTag entityData = capturedTag.getCompound("EntityData");
                String entityId = entityData.getString("id");
                if (!entityId.isEmpty()) {
                    tooltip.add(Component.translatable("tooltip.pasterdream.large_storage_bag.tooltip3",
                            "§e" + entityId));
                }
            }
        } else {
            tooltip.add(Component.translatable("tooltip.pasterdream.hold_shift"));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        // 潜行时走 capture/release，不打开 GUI
        if (entity.isShiftKeyDown()) return InteractionResultHolder.pass(entity.getItemInHand(hand));
        if (entity instanceof ServerPlayer serverPlayer) {
            ItemStack stack = entity.getItemInHand(hand);
            world.playSound(null, entity.blockPosition(), ModSounds.ZIPPER.get(), SoundSource.NEUTRAL, 0.2f, 1f);
            serverPlayer.openMenu(
                    new SimpleMenuProvider(
                            (id, inv, player) -> new LargeStorageBagMenu(id, inv, stack, hand),
                            Component.empty()
                    ),
                    buf -> buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1)
            );
        }
        return InteractionResultHolder.sidedSuccess(entity.getItemInHand(hand), world.isClientSide);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!Config.CREATURE_CAPTURE_ENABLED.get()) return InteractionResult.PASS;

        Level level = player.level();

        // Shift + 右键尝试捕获生物
        if (player.isShiftKeyDown()) {
            if (hasCapturedEntity(stack)) return InteractionResult.PASS; // 已有捕获生物，不能再抓
            if (!canCaptureEntity(target)) return InteractionResult.PASS;
            if (!level.isClientSide) {
                captureEntity(stack, target);
                target.discard();
                // 通过 setItem 替换槽位中的 ItemStack，确保背包系统保存 tag 并与客户端同步
                int slot = hand == InteractionHand.MAIN_HAND ? player.getInventory().selected : 40;
                player.getInventory().setItem(slot, stack.copy());
                level.playSound(null, player.blockPosition(), ModSounds.ZIPPER.get(), SoundSource.NEUTRAL, 0.2f, 1f);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        // 普通右键释放生物
        if (hasCapturedEntity(stack)) {
            if (!level.isClientSide) {
                releaseCapturedEntity(level, player, stack);
                // 通过 setItem 确保背包系统保存 tag 完成
                int slot = hand == InteractionHand.MAIN_HAND ? player.getInventory().selected : 40;
                player.getInventory().setItem(slot, stack.copy());
                level.playSound(null, player.blockPosition(), ModSounds.ZIPPER.get(), SoundSource.NEUTRAL, 0.2f, 1f);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    // === 供事件处理器调用 ===

    public static boolean hasCapturedEntity(ItemStack stack) {
        return getBagTag(stack).contains(TAG_CAPTURED_ENTITY);
    }

    public static boolean isBagEmpty(ItemStack stack) {
        if (hasCapturedEntity(stack)) return false;
        List<ItemStack> items = getInventory(stack);
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    public static boolean canCaptureEntity(LivingEntity entity) {
        // 禁止抓取 Boss
        if (entity instanceof WitherBoss || entity instanceof EnderDragon) return false;
        // 史莱姆只允许小型（size == 1）
        if (entity instanceof Slime slime && slime.getSize() > 1) return false;
        // 配置白名单
        return Config.isCapturableEntity(entity.getType());
    }

    public static void captureEntity(ItemStack stack, LivingEntity entity) {
        // 先弹出乘客，防止乘客数据残留导致释放时出错
        entity.ejectPassengers();

        // 获取实体数据（用 saveWithoutId，避免 id/UUID 字段，随后补回 id 并移除 UUID）
        CompoundTag entityData = entity.saveWithoutId(new CompoundTag());
        entityData.putString("id", EntityType.getKey(entity.getType()).toString());
        entityData.remove("UUID"); // 清除原始 UUID，避免实体自己冲突

        // 清除乘客数据（已通过 ejectPassengers 处理，防止释放失败）
        entityData.remove("Passengers");

        CompoundTag capturedTag = new CompoundTag();
        capturedTag.put("EntityData", entityData);
        if (entity.hasCustomName()) {
            capturedTag.putString("CustomName",
                    Component.Serializer.toJson(entity.getCustomName(), entity.level().registryAccess()));
        }
        CustomData.update(DataComponents.CUSTOM_DATA, stack,
                tag -> tag.put(TAG_CAPTURED_ENTITY, capturedTag));
    }

    public static void releaseCapturedEntity(Level level, Player player, ItemStack stack) {
        CompoundTag bagTag = getBagTag(stack);
        if (!bagTag.contains(TAG_CAPTURED_ENTITY)) return;

        CompoundTag capturedTag = bagTag.getCompound(TAG_CAPTURED_ENTITY);
        CompoundTag entityData = capturedTag.getCompound("EntityData");
        if (entityData.isEmpty()) return;

        // 通过 id 查找 EntityType
        String entityId = entityData.getString("id");
        Optional<EntityType<?>> type = EntityType.byString(entityId);
        if (type.isEmpty()) return;

        // 计算目标生成位置
        Vec3 look = player.getLookAngle();
        double targetX = player.getX() + look.x * 1.5;
        double targetY = player.getY() + player.getEyeHeight() + look.y * 0.5;
        double targetZ = player.getZ() + look.z * 1.5;

        Entity entity = null;
        try {
            entity = type.get().create(level);
            if (entity == null) return;

            // 修改 entityData，防止 load() 恢复旧位置/旧速度导致实体飞走
            // -- 覆盖 Pos 为目标生成位置
            ListTag posTag = new ListTag();
            posTag.add(DoubleTag.valueOf(targetX));
            posTag.add(DoubleTag.valueOf(targetY));
            posTag.add(DoubleTag.valueOf(targetZ));
            entityData.put("Pos", posTag);

            // -- 覆盖 Motion，防止实体带着旧速度飞出去
            ListTag motionTag = new ListTag();
            motionTag.add(DoubleTag.valueOf(0.0));
            motionTag.add(DoubleTag.valueOf(0.0));
            motionTag.add(DoubleTag.valueOf(0.0));
            entityData.put("Motion", motionTag);

            // -- Rotation 设为玩家朝向
            ListTag rotTag = new ListTag();
            rotTag.add(FloatTag.valueOf(player.getYRot()));
            rotTag.add(FloatTag.valueOf(player.getXRot()));
            entityData.put("Rotation", rotTag);

            // -- 移除 id 字段，否则 entity.load() 会报错
            entityData.remove("id");

            entity.load(entityData);

            // 确保生成后速度和掉落距离重置，防止实体滑行/摔落
            entity.setDeltaMovement(0, 0, 0);
            entity.fallDistance = 0;

            if (entity instanceof LivingEntity living) {
                living.yHeadRot = player.getYRot();
                living.yBodyRot = player.getYRot();
                living.yHeadRotO = player.getYRot();
                living.yBodyRotO = player.getYRot();
            }

            // 恢复自定义名称
            if (capturedTag.contains("CustomName")) {
                entity.setCustomName(Component.Serializer.fromJson(capturedTag.getString("CustomName"),
                        level.registryAccess()));
            }

            // 加入世界，失败则删除 NBT，防止实体被吞
            if (!level.addFreshEntity(entity)) {
                // 生成失败，保留 CapturedEntity 数据供下次使用
                return;
            }
        } catch (Exception e) {
            // 加载或生成失败时保留 NBT 数据，防止实体被"吞掉"
            if (entity != null && entity.isAlive()) {
                entity.discard();
            }
            return;
        }

        // 成功释放后清除 NBT 数据
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.remove(TAG_CAPTURED_ENTITY));
    }

    /**
     * 在指定位置释放生物（例如玩家在袋子被销毁时排出），用于袋子爆炸等情况。
     */
    public static void releaseCapturedEntityAt(Level level, ItemStack stack, double x, double y, double z) {
        CompoundTag bagTag = getBagTag(stack);
        if (!bagTag.contains(TAG_CAPTURED_ENTITY)) return;

        CompoundTag capturedTag = bagTag.getCompound(TAG_CAPTURED_ENTITY);
        CompoundTag entityData = capturedTag.getCompound("EntityData");
        if (entityData.isEmpty()) return;

        String entityId = entityData.getString("id");
        Optional<EntityType<?>> type = EntityType.byString(entityId);
        if (type.isEmpty()) return;

        Entity entity = null;
        try {
            entity = type.get().create(level);
            if (entity == null) return;

            ListTag posTag = new ListTag();
            posTag.add(DoubleTag.valueOf(x));
            posTag.add(DoubleTag.valueOf(y));
            posTag.add(DoubleTag.valueOf(z));
            entityData.put("Pos", posTag);

            ListTag motionTag = new ListTag();
            motionTag.add(DoubleTag.valueOf(0.0));
            motionTag.add(DoubleTag.valueOf(0.0));
            motionTag.add(DoubleTag.valueOf(0.0));
            entityData.put("Motion", motionTag);

            ListTag rotTag = new ListTag();
            rotTag.add(FloatTag.valueOf(level.random.nextFloat() * 360f));
            rotTag.add(FloatTag.valueOf(0f));
            entityData.put("Rotation", rotTag);

            entityData.remove("id");
            entity.load(entityData);
            entity.setDeltaMovement(0, 0, 0);
            entity.fallDistance = 0;

            if (capturedTag.contains("CustomName")) {
                entity.setCustomName(Component.Serializer.fromJson(capturedTag.getString("CustomName"),
                        level.registryAccess()));
            }

            if (!level.addFreshEntity(entity)) return;
        } catch (Exception e) {
            if (entity != null && entity.isAlive()) entity.discard();
            return;
        }

        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.remove(TAG_CAPTURED_ENTITY));
    }

    private static CompoundTag getBagTag(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }
}
