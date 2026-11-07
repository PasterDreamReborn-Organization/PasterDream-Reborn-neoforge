package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.helper.ContainerBalanceHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class GlassJarGameTests
{
    @GameTest(template = "empty")
    public static void glassJarFluidCapability(GameTestHelper helper)
    {
        ItemStack jar = ModItems.GLASS_JAR.toStack();
        var handler = jar.getCapability(Capabilities.FluidHandler.ITEM);
        helper.assertTrue(handler != null, "空玻璃罐应提供流体物品能力");
        helper.assertTrue(handler.fill(new FluidStack(Fluids.WATER, 125), FluidAction.EXECUTE) == 125,
                "空玻璃罐应能装入 125 mB 水");
        helper.assertTrue(handler.getContainer().is(ModItems.GLASS_JAR_OF_WATER.get()),
                "装水后的容器应为水罐");
        helper.assertTrue(handler.getFluidInTank(0).getFluid() == Fluids.WATER
                && handler.getFluidInTank(0).getAmount() == 125, "水罐流体内容不符");
        helper.assertTrue(handler.drain(125, FluidAction.EXECUTE).getAmount() == 125,
                "水罐应能排出 125 mB 水");
        helper.assertTrue(handler.getContainer().is(ModItems.GLASS_JAR.get()), "排空后的容器应为玻璃罐");

        ItemStack milkJar = ModItems.GLASS_JAR.toStack();
        var milkHandler = milkJar.getCapability(Capabilities.FluidHandler.ITEM);
        helper.assertTrue(milkHandler != null
                && milkHandler.fill(new FluidStack(NeoForgeMod.MILK.get(), 125), FluidAction.EXECUTE) == 125,
                "空玻璃罐应能装入 125 mB 牛奶");
        helper.assertTrue(milkHandler.getContainer().is(ModItems.GLASS_JAR_OF_MILK.get()),
                "装牛奶后的容器应为牛奶罐");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void glassJarContainerRecipesBalance(GameTestHelper helper)
    {
        CraftingInput inkInput = CraftingInput.of(3, 1, List.of(
                ModItems.GLASS_JAR_OF_WATER.toStack(), ModItems.GLASS_JAR.toStack(),
                new ItemStack(Items.BLACK_DYE)));
        NonNullList<ItemStack> inkRemainder = NonNullList.withSize(3, ItemStack.EMPTY);
        inkRemainder.set(0, ModItems.GLASS_JAR.toStack());
        NonNullList<ItemStack> balancedInk = ContainerBalanceHelper.balance(inkInput,
                new ItemStack(ModItems.GLASS_JAR_OF_INK.get(), 2), inkRemainder,
                List.of(Ingredient.of(ModItems.GLASS_JAR_OF_WATER.get()),
                        Ingredient.of(ModItems.GLASS_JAR.get()), Ingredient.of(Items.BLACK_DYE)));
        helper.assertTrue(balancedInk.get(0).isEmpty(), "墨水配方不得额外返还水罐的空罐");

        CraftingInput yeastInput = CraftingInput.of(5, 1, List.of(
                ModItems.GLASS_JAR.toStack(2), ModItems.FLOUR.toStack(),
                ModItems.GLASS_JAR_OF_WATER.toStack(), ModItems.GLASS_JAR_OF_YEAST.toStack(),
                new ItemStack(Items.SUGAR)));
        NonNullList<ItemStack> yeastRemainder = NonNullList.withSize(5, ItemStack.EMPTY);
        yeastRemainder.set(2, ModItems.GLASS_JAR.toStack());
        yeastRemainder.set(3, ModItems.GLASS_JAR.toStack());
        NonNullList<ItemStack> balancedYeast = ContainerBalanceHelper.balance(yeastInput,
                new ItemStack(ModItems.GLASS_JAR_OF_YEAST.get(), 4), yeastRemainder,
                List.of(Ingredient.of(ModItems.GLASS_JAR.get()), Ingredient.of(ModItems.FLOUR.get()),
                        Ingredient.of(ModItems.GLASS_JAR_OF_WATER.get()),
                        Ingredient.of(ModItems.GLASS_JAR_OF_YEAST.get()), Ingredient.of(Items.SUGAR)));
        helper.assertTrue(balancedYeast.get(2).isEmpty() && balancedYeast.get(3).isEmpty(),
                "酵母配方应抵消两份填充罐的回流空罐");

        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(
                        net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                "glass_jar_of_ink_from_black_dye")).isPresent(),
                "墨水配方应加载");
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(
                        net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                "glass_jar_of_yeast")).isPresent(),
                "酵母配方应加载");
        helper.succeed();
    }
}
