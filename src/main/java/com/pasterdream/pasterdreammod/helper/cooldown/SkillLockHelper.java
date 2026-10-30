package com.pasterdream.pasterdreammod.helper.cooldown;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import top.theillusivec4.curios.api.CuriosApi;

/** 来源锁定物为大侦探的放大镜；该饰品注册随 Curios 批次接入。 */
public final class SkillLockHelper {
    private SkillLockHelper() {}

    public static boolean isSkillLocked(Player player) {
        if (isSkillLockedSilent(player)) {
            if (player instanceof ServerPlayer sp) {
                sp.displayClientMessage(Component.translatable("message.pasterdream.skill_locked"), true);
            }
            return true;
        }
        return false;
    }

    public static boolean isSkillLockedSilent(Player player) {
        var item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(
                PasterDreamMod.MOD_ID, "magnifying_glass_of_sherry"));
        if (item == null || item == Items.AIR) return false;
        return CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(item).isPresent())
                .orElse(false);
    }
}
