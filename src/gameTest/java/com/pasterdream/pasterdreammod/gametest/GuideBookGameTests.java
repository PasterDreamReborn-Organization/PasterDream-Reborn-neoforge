package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.event.PlayerEvents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class GuideBookGameTests {

    private static final ResourceLocation SENIORS_DREAM = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "seniors_dream");

    @GameTest(template = "empty")
    public static void firstLoginGivesGuideBookOnce(GameTestHelper helper) {
        var level = helper.getLevel();
        ServerPlayer player = new ServerPlayer(level.getServer(), level,
                new GameProfile(UUID.randomUUID(), "guide-book-test"), ClientInformation.createDefault());
        ItemStack expected = PatchouliAPI.get().getBookStack(SENIORS_DREAM);
        helper.assertFalse(expected.isEmpty(), "应能取得《旧梦归引》书物品");

        PlayerEvents.onPlayerLogin(new PlayerEvent.PlayerLoggedInEvent(player));
        int firstCount = countGuideBooks(player, expected);
        helper.assertTrue(firstCount == 1, "首次登录应发放一本《旧梦归引》，实际 " + firstCount + " 本");

        PlayerEvents.onPlayerLogin(new PlayerEvent.PlayerLoggedInEvent(player));
        int secondCount = countGuideBooks(player, expected);
        helper.assertTrue(secondCount == 1, "重复登录不应重复发放《旧梦归引》，实际 " + secondCount + " 本");
        helper.succeed();
    }

    private static int countGuideBooks(ServerPlayer player, ItemStack expected) {
        int count = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty() && ItemStack.isSameItemSameComponents(stack, expected)) {
                count += stack.getCount();
            }
        }
        return count;
    }
}
