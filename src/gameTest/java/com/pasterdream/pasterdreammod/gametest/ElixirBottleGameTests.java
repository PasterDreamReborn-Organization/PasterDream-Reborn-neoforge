package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle.ElixirBottleItem;
import com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle.ElixirBottleWithFluidNBTBuilder;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class ElixirBottleGameTests
{
    @GameTest(template = "empty")
    public static void bottleStoresAndDrinksFluid(GameTestHelper helper)
    {
        ItemStack bottle = ModItems.ELIXIR_BOTTLE.toStack();
        var handler = bottle.getCapability(Capabilities.FluidHandler.ITEM);
        helper.assertTrue(handler != null && handler.getTankCapacity(0) == 1000, "灵药瓶容量应为 1000 mB");
        helper.assertTrue(handler.fill(new FluidStack(ModFluids.MELT_DREAM_LIQUID.get(), 1200), FluidAction.EXECUTE) == 1000,
                "灵药瓶应限制填充量");
        helper.assertTrue(ElixirBottleItem.getElixirBottleFluidStack(bottle).getAmount() == 1000,
                "瓶内流体应写入物品组件");
        helper.assertTrue(handler.drain(250, FluidAction.EXECUTE).getAmount() == 250,
                "灵药瓶应支持部分排液");
        helper.assertTrue(ElixirBottleWithFluidNBTBuilder.builder(new FluidStack(ModFluids.RAGE_ELIXIR.get(), 500))
                .getCapability(Capabilities.FluidHandler.ITEM).getFluidInTank(0).getAmount() == 500,
                "瓶内流体构建器应保留数量");

        FakePlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "elixir-fluid"));
        MeltDreamEnergyHelper.setPlayerMaxMeltDreamEnergyAndSync(player, 100);
        MeltDreamEnergyHelper.setPlayerMeltDreamEnergyAndSync(player, 0);
        ModItems.ELIXIR_BOTTLE.get().finishUsingItem(bottle, helper.getLevel(), player);
        helper.assertTrue(Math.abs(MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) - 18.75) < 0.001,
                "750 mB 应按比例恢复融梦能量");
        helper.assertTrue(ElixirBottleItem.getElixirBottleFluidStack(bottle).isEmpty(), "饮用后应清空流体并保留灵药瓶");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void derivativesApplyEffectsAndReturnBottle(GameTestHelper helper)
    {
        FakePlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "elixir-items"));
        MeltDreamEnergyHelper.setPlayerMaxMeltDreamEnergyAndSync(player, 100);
        MeltDreamEnergyHelper.setPlayerMeltDreamEnergyAndSync(player, 0);
        ItemStack meltBottle = ModItems.ELIXIR_BOTTLE_OF_MELT_DREAM.get().finishUsingItem(
                ModItems.ELIXIR_BOTTLE_OF_MELT_DREAM.toStack(), helper.getLevel(), player);
        helper.assertTrue(meltBottle.is(ModItems.ELIXIR_BOTTLE.get()), "融梦灵药饮用后应返还空灵药瓶");
        helper.assertTrue(ModItems.ELIXIR_BOTTLE_OF_MELT_DREAM.toStack().getCraftingRemainingItem().is(ModItems.ELIXIR_BOTTLE.get()),
                "融梦灵药作为配方材料也应返还空灵药瓶");
        helper.assertTrue(Math.abs(MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) - 25) < 0.001,
                "融梦灵药应恢复 25 融梦能量");

        ItemStack rageBottle = ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.get().finishUsingItem(
                ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.toStack(), helper.getLevel(), player);
        helper.assertTrue(rageBottle.is(ModItems.ELIXIR_BOTTLE.get()), "狂暴战意灵药饮用后应返还空灵药瓶");
        helper.assertTrue(ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.toStack().getCraftingRemainingItem().is(ModItems.ELIXIR_BOTTLE.get()),
                "狂暴战意灵药作为配方材料也应返还空灵药瓶");
        helper.assertTrue(player.getAttribute(ModAttributes.SKILL_COOLDOWN_REDUCTION).getModifier(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "elixir_bottle_of_rage_elixir_skill_cd_reduction")) != null,
                "狂暴战意灵药应施加技能冷却修饰符");
        helper.assertTrue(player.getAttribute(Attributes.ATTACK_DAMAGE).getModifier(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "elixir_bottle_of_rage_elixir_attack_damage")) != null,
                "狂暴战意灵药应施加攻击力修饰符");

        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "elixir_bottle")).isPresent(), "缺少灵药瓶配方");
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "elixir_bottle_of_melt_dream")).isPresent(), "缺少融梦灵药配方");
        helper.succeed();
    }
}
