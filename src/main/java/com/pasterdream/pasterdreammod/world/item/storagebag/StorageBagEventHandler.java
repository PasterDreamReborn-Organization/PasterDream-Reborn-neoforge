package com.pasterdream.pasterdreammod.world.item.storagebag;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.lang.reflect.Field;
import java.util.List;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class StorageBagEventHandler {

    private static final Field HEALTH_FIELD;

    static {
        Field f = null;
        try {
            f = ItemEntity.class.getDeclaredField("health");
            f.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
        HEALTH_FIELD = f;
    }

    /**
     * 当袋子物品实体被烧毁/销毁时，散落袋内物品
     */
    @SubscribeEvent
    public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        if (!(event.getEntity() instanceof ItemEntity itemEntity)) return;
        ItemStack stack = itemEntity.getItem();
        if (!isStorageBag(stack)) return;

        // 只在被摧毁（血量为 0）时处理，避免被拾取/切换维度时误触发
        int health = getHealth(itemEntity);
        if (health > 0) return;

        scatterContents(itemEntity.level(), itemEntity, stack);
    }

    private static boolean isStorageBag(ItemStack stack) {
        return stack.getItem() instanceof StorageBagItem || stack.getItem() instanceof LargeStorageBagItem;
    }

    private static int getHealth(ItemEntity entity) {
        if (HEALTH_FIELD == null) return 1;
        try {
            return HEALTH_FIELD.getInt(entity);
        } catch (IllegalAccessException e) {
            return 1;
        }
    }

    /**
     * 散落袋子内的物品，并释放捕获生物
     */
    private static void scatterContents(Level level, ItemEntity bagEntity, ItemStack bagStack) {
        List<ItemStack> items;
        if (bagStack.getItem() instanceof StorageBagItem) {
            items = StorageBagItem.getInventory(bagStack);
        } else if (bagStack.getItem() instanceof LargeStorageBagItem) {
            items = LargeStorageBagItem.getInventory(bagStack);
        } else {
            return;
        }

        for (ItemStack content : items) {
            if (!content.isEmpty()) {
                ItemEntity drop = new ItemEntity(level,
                        bagEntity.getX(), bagEntity.getY() + 0.5, bagEntity.getZ(),
                        content);
                drop.setDefaultPickUpDelay();
                drop.setDeltaMovement(
                        (level.random.nextDouble() - 0.5) * 0.2,
                        level.random.nextDouble() * 0.3,
                        (level.random.nextDouble() - 0.5) * 0.2
                );
                level.addFreshEntity(drop);
            }
        }

        // 释放捕获的生物
        if (bagStack.getItem() instanceof LargeStorageBagItem
                && LargeStorageBagItem.hasCapturedEntity(bagStack)) {
            LargeStorageBagItem.releaseCapturedEntityAt(level, bagStack,
                    bagEntity.getX(), bagEntity.getY() + 0.5, bagEntity.getZ());
        }
    }

    // === 大型便携储物袋 Shift+右键释放生物（对空气/方块） ===

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof LargeStorageBagItem)) return;

        if (!LargeStorageBagItem.hasCapturedEntity(stack)) return;

        // 只有潜行时释放，避免与正常交互冲突
        if (!player.isShiftKeyDown()) return;

        LargeStorageBagItem.releaseCapturedEntity(player.level(), player, stack);
        // 通过 setItem 替换槽位中的 ItemStack，确保背包系统保存 tag 完成
        int slot = event.getHand() == InteractionHand.MAIN_HAND ? player.getInventory().selected : 40;
        player.getInventory().setItem(slot, stack.copy());
        event.setCanceled(true);
        player.level().playSound(null, player.blockPosition(),
                ModSounds.ZIPPER.get(), SoundSource.NEUTRAL, 0.2f, 1f);
    }
}
