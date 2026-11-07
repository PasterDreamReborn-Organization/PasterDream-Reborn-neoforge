package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class GlassCupGameTests
{
    @GameTest(template = "empty")
    public static void glassCupFluidMappings(GameTestHelper helper)
    {
        Fluid[] fluids = {
                ModFluids.APPLE_JUICE.get(), ModFluids.UNCOOKED_DYEDREAM_FLOWER_TEA.get(),
                ModFluids.DYEDREAM_FLOWER_TEA.get(), ModFluids.DYEDREAM_JUICE.get(),
                ModFluids.HONEY_JUICE.get(), ModFluids.WATERMELON_JUICE.get()
        };
        Item[] drinks = {
                ModItems.GLASS_CUP_OF_APPLE_JUICE.get(), ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get(),
                ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get(), ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get(),
                ModItems.GLASS_CUP_OF_HONEY_JUICE.get(), ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get()
        };
        for (int i = 0; i < fluids.length; i++)
        {
            var handler = ModItems.GLASS_CUP.toStack().getCapability(Capabilities.FluidHandler.ITEM);
            helper.assertTrue(handler != null && handler.fill(new FluidStack(fluids[i], 250), FluidAction.EXECUTE) == 250,
                    "玻璃杯应能装入 250 mB 饮品流体");
            helper.assertTrue(handler.getContainer().is(drinks[i]), "装液后的玻璃杯物品不符");
            helper.assertTrue(handler.drain(250, FluidAction.EXECUTE).getAmount() == 250
                    && handler.getContainer().is(ModItems.GLASS_CUP.get()), "排液后应恢复玻璃杯");
            helper.assertTrue(new ItemStack(drinks[i]).getCraftingRemainingItem().is(ModItems.GLASS_CUP.get()),
                    "杯装饮品作为配方材料应返还玻璃杯");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void glassCupRecipesLoaded(GameTestHelper helper)
    {
        String[] ids = {
                "glass_cup", "glass_cup_of_apple_juice", "glass_cup_of_honey_juice",
                "glass_cup_of_watermelon_juice", "glass_cup_of_uncooked_dyedream_flower_tea",
                "glass_cup_of_dyedream_juice", "red_dew",
                "glass_cup_of_cooked_dyedream_flower_tea_from_glass_cup_of_uncooked_dyedream_flower_tea_smelting",
                "glass_cup_of_cooked_dyedream_flower_tea_from_glass_cup_of_uncooked_dyedream_flower_tea_smoking",
                "glass_cup_of_cooked_dyedream_flower_tea_from_glass_cup_of_uncooked_dyedream_flower_tea_campfire_cooking"
        };
        for (String id : ids)
        {
            helper.assertTrue(helper.getLevel().getRecipeManager().byKey(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, id)).isPresent(),
                    "缺少玻璃杯配方: " + id);
        }
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "glass_cup_of_slumber_palm_club_soda")).isEmpty(),
                "本批不应生成眠椰苏打水配方");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void honeyJuiceRemovesOnlyNegativeEffects(GameTestHelper helper)
    {
        FakePlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "honey-juice"));
        player.addEffect(new MobEffectInstance(MobEffects.POISON, 200));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200));
        ItemStack cup = ModItems.GLASS_CUP_OF_HONEY_JUICE.toStack();
        ItemStack emptyCup = ModItems.GLASS_CUP_OF_HONEY_JUICE.get().finishUsingItem(cup, helper.getLevel(), player);
        helper.assertFalse(player.hasEffect(MobEffects.POISON), "蜂蜜汁应清除负面效果");
        helper.assertTrue(player.hasEffect(MobEffects.MOVEMENT_SPEED), "蜂蜜汁应保留正面效果");
        helper.assertTrue(emptyCup.is(ModItems.GLASS_CUP.get()), "饮用后应返还玻璃杯");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void flowerTeaAndDyedreamJuiceEffects(GameTestHelper helper)
    {
        FakePlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "cup-drink-effects"));
        player.setHealth(10.0f);
        ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get().finishUsingItem(
                ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.toStack(), helper.getLevel(), player);

        MeltDreamEnergyHelper.setPlayerMeltDreamEnergyAndSync(player, 0.0);
        ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get().finishUsingItem(
                ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.toStack(), helper.getLevel(), player);
        helper.assertTrue(Math.abs(MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) - 0.2) < 0.0001,
                "染梦果汁应恢复 0.2 融梦能量");
        helper.runAfterDelay(2, () -> {
            helper.assertTrue(player.getHealth() > 10.0f, "熟染梦花茶应治疗饮用者");
            helper.succeed();
        });
    }
}
