package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.event.PlayerEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DyedreamTreasureGameTests {

    private static final ResourceKey<Level> DYEDREAM_WORLD = ResourceKey.create(
            Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"));
    private static final ResourceLocation ROOT_DYEDREAM_TREASURE = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "treasure/root_dyedream_treasure");

    @GameTest(template = "empty")
    public static void enterDyedreamGrantsTreasureRoot(GameTestHelper helper) {
        var level = helper.getLevel();
        ServerPlayer player = new ServerPlayer(level.getServer(), level,
                new GameProfile(UUID.randomUUID(), "treasure-test"), ClientInformation.createDefault());

        var root = level.getServer().getAdvancements().get(ROOT_DYEDREAM_TREASURE);
        helper.assertTrue(root != null, "应存在进度: treasure/root_dyedream_treasure");
        helper.assertFalse(player.getAdvancements().getOrStartProgress(root).isDone(),
                "进入染梦维度前不应完成染梦珍藏根进度");

        PlayerEvents.onPlayerChangedDimension(
                new PlayerEvent.PlayerChangedDimensionEvent(player, Level.OVERWORLD, DYEDREAM_WORLD));

        helper.assertTrue(player.getAdvancements().getOrStartProgress(root).isDone(),
                "进入染梦维度后应授予染梦珍藏根进度");
        helper.succeed();
    }
}
