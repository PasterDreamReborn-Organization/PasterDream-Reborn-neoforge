package com.pasterdream.pasterdreammod.world.enchantment;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.capability.san.SanDisabledHealthCost;
import com.pasterdream.pasterdreammod.init.ModEnchantment;
import com.pasterdream.pasterdreammod.world.item.armoritem.MeltDreamCrystalArmorItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.items.IItemHandler;
import top.theillusivec4.curios.api.CuriosApi;

/**
 * 「噬心修补」附魔处理器：携带（背包 / 护甲 / 饰品栏）且附有该附魔、有损伤的物品，
 * 每 10 tick 消耗玩家 0.1 理智修复 1 点耐久；理智不足则停止修复。
 * SAN 系统关闭时改为消耗 1 点生命值修复 1 点耐久（可致死）。
 * 创造模式不消耗理智或生命值。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class HeartDevourRepairHandler {

    /** 每次修复 1 点耐久的理智消耗 */
    private static final double REPAIR_COST = 0.1;
    private static final double HEALTH_COST = 1.0;
    /** 处理间隔（tick） */
    private static final int INTERVAL = 10;

    private HeartDevourRepairHandler() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.tickCount % INTERVAL != 0 || player.isDeadOrDying()) return;

        var san = player.getData(ModAttachments.SAN);
        boolean free = player.isCreative();
        boolean sanEnabled = san.getIsSanEnabled();
        double[] budget = {free || !sanEnabled ? Double.MAX_VALUE : san.getSanValue()};

        int repaired = 0;
        repaired += repair(player.getInventory().items, budget, free);
        repaired += repair(player.getInventory().armor, budget, free);
        repaired += repair(player.getInventory().offhand, budget, free);

        int[] curioRepaired = {0};
        CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                handler.getCurios().values().forEach(stacksHandler -> {
                    IItemHandler stacks = stacksHandler.getStacks();
                    for (int i = 0; i < stacks.getSlots(); i++) {
                        curioRepaired[0] += repairSingle(stacks.getStackInSlot(i), budget, free);
                    }
                }));
        repaired += curioRepaired[0];

        if (repaired <= 0 || free) return;
        if (sanEnabled) {
            san.addSanValue(-repaired * REPAIR_COST);
            player.syncData(ModAttachments.SAN);
        } else {
            SanDisabledHealthCost.drain(player, (float) (repaired * HEALTH_COST));
        }
    }

    private static int repair(Iterable<ItemStack> stacks, double[] budget, boolean free) {
        int count = 0;
        for (ItemStack stack : stacks) {
            count += repairSingle(stack, budget, free);
        }
        return count;
    }

    /** 尝试为单个物品消耗理智修复 1 点耐久，返回是否发生修复。 */
    private static int repairSingle(ItemStack stack, double[] budget, boolean free) {
        if (!isCursedDamaged(stack)) return 0;

        // 融梦水晶护甲由 MeltDreamCrystalArmorHandler 自行修补，此处跳过避免重复修复
        if (stack.getItem() instanceof MeltDreamCrystalArmorItem) return 0;

        if (!free) {
            if (budget[0] < REPAIR_COST) return 0;
            budget[0] -= REPAIR_COST;
        }
        stack.setDamageValue(stack.getDamageValue() - 1);
        return 1;
    }

    private static boolean isCursedDamaged(ItemStack stack) {
        return !stack.isEmpty()
                && stack.isDamageableItem()
                && stack.getDamageValue() >= 1
                && ModEnchantment.getLevel(stack, ModEnchantment.HEART_DEVOUR_REPAIR_ENCHANTMENT) >= 1;
    }
}
