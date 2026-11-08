package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.CauldronBrewRecipeCatalog;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronMenu;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.CauldronPotionModule;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.PotionEffectCodec;
import com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle.ElixirBottleItem;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DreamCauldronGameTests
{
    @GameTest(template = "empty")
    public static void sourceRecipesLoadAndCraft(GameTestHelper helper)
    {
        var recipes = helper.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.DREAM_CAULDRON.get());
        helper.assertTrue(recipes.size() == 90, "梦之釜可用来源配方应加载 90 条，实际 " + recipes.size());
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(
                PasterDreamMod.MOD_ID, "dream_cauldron/potion_recipe/speed_long")).isPresent(),
                "来源迅捷延长配方 ID 应保留");
        for (var holder : recipes)
        {
            for (var ingredient : holder.value().getInputFluidIngredients())
                if (ingredient.getFluid() == ModFluids.POTION.get() && ingredient.getNbt() != null)
                    helper.assertTrue(PotionEffectCodec.isPotionFluid(ingredient.getFluidStack()),
                            "药水输入效果 ID 应在 1.21 注册表中解析：" + holder.id());
            for (var ingredient : holder.value().getOutputFluidIngredients())
                if (ingredient.getFluid() == ModFluids.POTION.get() && ingredient.getNbt() != null)
                    helper.assertTrue(PotionEffectCodec.isPotionFluid(ingredient.getFluidStack()),
                            "药水输出效果 ID 应在 1.21 注册表中解析：" + holder.id());
        }

        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.DREAM_CAULDRON.get());
        DreamCauldronBlockEntity cauldron = helper.getBlockEntity(pos);
        helper.assertTrue(cauldron.getFluidTank(0).getCapacity() == 2000
                && cauldron.getFluidTank(1).getCapacity() == 8000
                && cauldron.getFluidTank(2).getCapacity() == 1000, "三个液罐容量应保留来源数值");
        helper.assertTrue(helper.getLevel().getCapability(Capabilities.FluidHandler.BLOCK, helper.absolutePos(pos), null) != null,
                "梦之釜应暴露流体接口");
        cauldron.getFluidTank(0).setFluid(new FluidStack(Fluids.WATER, 1000));
        cauldron.getFluidTank(1).setFluid(new FluidStack(ModFluids.GUIDING_DRUG.get(), 125));
        cauldron.getItemHandler().setStackInSlot(0, new ItemStack(Items.SUGAR));
        cauldron.getItemHandler().setStackInSlot(1, new ItemStack(Items.REDSTONE));
        cauldron.craft(null);
        FluidStack result = cauldron.getFluidTank(2).getFluid();
        helper.assertTrue(result.getFluid() == ModFluids.POTION.get() && result.getAmount() == 1000,
                "配方应产出 1000 mB 成品药水");
        var effects = PotionEffectCodec.readFluidEffects(result);
        helper.assertTrue(effects.size() == 1 && effects.getFirst().getEffect() == MobEffects.MOVEMENT_SPEED
                && effects.getFirst().getDuration() == 9600, "来源 EffectList 应迁为药水组件并保留时长");
        helper.assertTrue(cauldron.getFluidTank(0).getFluid().isEmpty()
                && cauldron.getFluidTank(1).getFluid().isEmpty()
                && cauldron.getItemHandler().getStackInSlot(0).isEmpty()
                && cauldron.getItemHandler().getStackInSlot(1).isEmpty(), "加工后应扣除物品与流体输入");
        helper.assertTrue(cauldron.getUpdateTag(helper.getLevel().registryAccess()).getInt("AnimationState") == 1,
                "加工动画状态应随方块实体更新包同步");
        var saved = cauldron.saveWithFullMetadata(helper.getLevel().registryAccess());
        var restored = new DreamCauldronBlockEntity(helper.absolutePos(pos), helper.getBlockState(pos));
        restored.loadWithComponents(saved, helper.getLevel().registryAccess());
        helper.assertTrue(restored.getFluidTank(2).getFluid().getAmount() == 1000
                && PotionEffectCodec.readFluidEffects(restored.getFluidTank(2).getFluid()).getFirst().getDuration() == 9600,
                "方块实体重载后应保留药水数量与效果");
        cauldron.getFluidTank(0).setFluid(new FluidStack(Fluids.WATER, 1000));
        cauldron.getFluidTank(1).setFluid(new FluidStack(ModFluids.GUIDING_DRUG.get(), 125));
        cauldron.getItemHandler().setStackInSlot(0, new ItemStack(Items.SUGAR));
        cauldron.getItemHandler().setStackInSlot(1, new ItemStack(Items.REDSTONE));
        cauldron.craft(null);
        helper.assertTrue(cauldron.getFluidTank(0).getFluid().getAmount() == 1000
                && cauldron.getItemHandler().getStackInSlot(0).getCount() == 1,
                "输出槽已满时不得扣除输入");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void potionBrewBlendAndBottle(GameTestHelper helper)
    {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.DREAM_CAULDRON.get());
        DreamCauldronBlockEntity cauldron = helper.getBlockEntity(pos);
        cauldron.getItemHandler().setStackInSlot(0, ModItems.DYEDREAM_FRUIT.toStack());
        cauldron.getItemHandler().setStackInSlot(1, new ItemStack(Items.SUGAR));
        cauldron.craft(null);
        FluidStack brewed = cauldron.getFluidTank(2).getFluid();
        helper.assertTrue(PotionEffectCodec.isPotionFluid(brewed) && brewed.getAmount() == 250,
                "基础酿造应输出 250 mB 药水");
        helper.assertTrue(PotionEffectCodec.readFluidEffects(brewed).getFirst().getEffect() == MobEffects.MOVEMENT_SPEED,
                "糖应酿出迅捷效果");
        var jeiTemplates = CauldronBrewRecipeCatalog.createAll(helper.getLevel());
        helper.assertTrue(jeiTemplates.size() > 2 && jeiTemplates.stream().anyMatch(recipe ->
                recipe.getDisplayBottle().is(ModItems.ELIXIR_BOTTLE.get())),
                "JEI 应提供可查询的酿造、融合与勾兑模板");

        FakePlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "cauldron-bottle"));
        player.setPos(helper.absolutePos(pos).getCenter());
        player.setItemInHand(InteractionHand.MAIN_HAND, ModItems.ELIXIR_BOTTLE.toStack());
        helper.assertTrue(CauldronPotionModule.tryBottle(cauldron, player, InteractionHand.MAIN_HAND).outcome()
                == CauldronPotionModule.Outcome.HANDLED, "空灵药瓶应可装取釜中药水");
        helper.assertTrue(cauldron.getFluidTank(2).getFluid().isEmpty(), "装瓶后应扣除输出液");
        boolean foundBottle = false;
        for (ItemStack stack : player.getInventory().items)
        {
            FluidStack contained = ElixirBottleItem.getElixirBottleFluidStack(stack);
            if (stack.is(ModItems.ELIXIR_BOTTLE.get()) && contained.getAmount() == 250
                    && PotionEffectCodec.isPotionFluid(contained)) foundBottle = true;
        }
        helper.assertTrue(foundBottle, "装瓶应保留 250 mB 效果药水");

        cauldron.getFluidTank(1).setFluid(PotionEffectCodec.createPotionFluid(250,
                List.of(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600))));
        cauldron.getFluidTank(0).setFluid(new FluidStack(ModFluids.MELT_DREAM_LIQUID.get(), 250));
        helper.assertTrue(CauldronPotionModule.tryHandle(cauldron).outcome() == CauldronPotionModule.Outcome.HANDLED,
                "无物品时应执行勾兑");
        FluidStack blended = cauldron.getFluidTank(2).getFluid();
        helper.assertTrue(blended.getAmount() == 500
                && PotionEffectCodec.readFluidEffects(blended).getFirst().getDuration() == 3600,
                "勾兑应增加液量且不改变效果时长");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void overCapacityDoesNotConsumeInputs(GameTestHelper helper)
    {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.DREAM_CAULDRON.get());
        DreamCauldronBlockEntity cauldron = helper.getBlockEntity(pos);
        cauldron.getFluidTank(1).setFluid(PotionEffectCodec.createPotionFluid(1250,
                List.of(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600))));
        cauldron.getFluidTank(0).setFluid(new FluidStack(ModFluids.MELT_DREAM_LIQUID.get(), 250));
        CauldronPotionModule.tryHandle(cauldron);
        helper.assertTrue(cauldron.getFluidTank(1).getFluid().getAmount() == 1250
                && cauldron.getFluidTank(0).getFluid().getAmount() == 250
                && cauldron.getFluidTank(2).getFluid().isEmpty(),
                "超过输出容量时不应扣液或生成产物");
        helper.succeed();
    }
}
