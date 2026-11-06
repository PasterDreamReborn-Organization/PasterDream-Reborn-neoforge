package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class FluidBucketGameTests {
    @GameTest(template = "empty")
    public static void meltDreamSpringPlacesAndFlows(GameTestHelper helper) {
        checkFluid(helper, ModFluids.MELT_DREAM_LIQUID.get(), ModFluids.FLOWING_MELT_DREAM_LIQUID.get(),
                ModItems.MELT_DREAM_LIQUID_BUCKET.get());
        BlockPos pos = new BlockPos(1, 2, 1);
        helper.setBlock(pos, ModBlocks.MELTDREAM_LIQUID.get());
        helper.assertTrue(helper.getBlockState(pos).getFluidState().isSource(), "融梦涌泉方块应含源液");
        helper.runAfterDelay(10, () -> {
            helper.assertTrue(helper.getBlockState(pos).getFluidState().getType().isSame(ModFluids.MELT_DREAM_LIQUID.get()),
                    "融梦涌泉源液应持续存在");
            helper.assertTrue(helper.getBlockState(pos.below()).getFluidState().getType().isSame(ModFluids.MELT_DREAM_LIQUID.get()),
                    "融梦涌泉应向下流动");
            helper.succeed();
        });
    }

    @GameTest(template = "empty")
    public static void shadowLiquidPlacesAndFlows(GameTestHelper helper) {
        checkFluid(helper, ModFluids.SHADOW_LIQUID.get(), ModFluids.FLOWING_SHADOW_LIQUID.get(),
                ModItems.SHADOW_LIQUID_BUCKET.get());
        BlockPos pos = new BlockPos(1, 2, 1);
        helper.setBlock(pos, ModBlocks.SHADOW_LIQUID.get());
        helper.assertTrue(helper.getBlockState(pos).getFluidState().isSource(), "熔融阴影方块应含源液");
        helper.runAfterDelay(10, () -> {
            helper.assertTrue(helper.getBlockState(pos).getFluidState().getType().isSame(ModFluids.SHADOW_LIQUID.get()),
                    "熔融阴影源液应持续存在");
            helper.assertTrue(helper.getBlockState(pos.below()).getFluidState().getType().isSame(ModFluids.SHADOW_LIQUID.get()),
                    "熔融阴影应向下流动");
            helper.succeed();
        });
    }

    private static void checkFluid(GameTestHelper helper, Fluid source, Fluid flowing, BucketItem bucket) {
        helper.assertTrue(source.isSame(flowing) && flowing.isSame(source), "源液和流动液应归为同种流体");
        helper.assertTrue(source.getBucket() == bucket && flowing.getBucket() == bucket,
                "两种流体状态应返回对应的桶");
        helper.assertTrue(bucket.getCraftingRemainingItem(bucket.getDefaultInstance()).is(Items.BUCKET),
                "流体桶配方余料应为空桶");
        var handler = FluidUtil.getFluidHandler(bucket.getDefaultInstance()).orElseThrow();
        helper.assertTrue(handler.getTankCapacity(0) == 1000 && handler.getFluidInTank(0).getAmount() == 1000,
                "流体桶应提供 1000 mB 的物品流体接口");
        helper.assertTrue(handler.drain(500, FluidAction.EXECUTE).isEmpty(), "不足一桶不可部分排放");
        helper.assertTrue(handler.drain(new FluidStack(Fluids.WATER, 1000), FluidAction.EXECUTE).isEmpty(),
                "不兼容流体不可排放");
        helper.assertTrue(handler.drain(1000, FluidAction.SIMULATE).getFluid().isSame(source)
                        && handler.getContainer().is(bucket), "模拟排放不可改变桶");
        helper.assertTrue(handler.drain(1000, FluidAction.EXECUTE).getFluid().isSame(source)
                        && handler.getContainer().is(Items.BUCKET), "排放整桶后应返回空桶");
        helper.assertTrue(handler.fill(new FluidStack(source, 1000), FluidAction.SIMULATE) == 1000
                        && handler.getContainer().is(Items.BUCKET), "模拟充填不可改变空桶");
        helper.assertTrue(handler.fill(new FluidStack(source, 1000), FluidAction.EXECUTE) == 1000
                        && handler.getContainer().is(bucket), "充填整桶后应恢复原桶");
    }
}
