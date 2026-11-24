package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.compat.kubejs.food.PasterDreamFoodEvents;
import com.pasterdream.pasterdreammod.compat.kubejs.food.PasterDreamFoodRegistry;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.Map;
import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class KubeJSFoodGameTests {
    @GameTest(template = "empty", batch = "kubejsFood")
    public static void registeredFoodGrantsResourcesAfterEating(GameTestHelper helper) {
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "kubejs-food"));
        var san = player.getData(ModAttachments.SAN);
        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        san.setSanValue(30.0);
        energy.setMeltDreamEnergy(10.0);

        String appleId = "minecraft:apple";
        helper.assertTrue(PasterDreamFoodRegistry.register(appleId, Map.of("san", 2, "meltDreamEnergy", 3.5)),
                "应接受启动脚本的对象参数形式");
        var used = new LivingEntityUseItemEvent.Finish(player, new ItemStack(Items.APPLE), 0, ItemStack.EMPTY);
        try {
            PasterDreamFoodEvents.onFoodEaten(used);
            helper.assertTrue(san.getSanValue() == 32.0 && energy.getMeltDreamEnergy() == 13.5,
                    "进食时应按已注册的数值增减玩家资源");
        } finally {
            PasterDreamFoodRegistry.unregister(appleId);
        }
        PasterDreamFoodEvents.onFoodEaten(used);
        helper.assertTrue(san.getSanValue() == 32.0 && energy.getMeltDreamEnergy() == 13.5,
                "解除注册后应停止追加进食效果");
        helper.succeed();
    }
}
