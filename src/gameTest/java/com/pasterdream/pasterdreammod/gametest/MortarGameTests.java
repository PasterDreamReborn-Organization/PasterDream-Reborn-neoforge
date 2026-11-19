package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.ContainerBalanceHelper;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeInventory;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeMatcher;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeProcesser;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarRecipe;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarDataHandler;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarMenu;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class MortarGameTests
{
    @GameTest(template = "empty")
    public static void filledContainerOutputBalancesRemainder(GameTestHelper helper)
    {
        CraftingInput input = CraftingInput.of(2, 1,
                List.of(new ItemStack(Items.HONEY_BOTTLE), new ItemStack(Items.SUGAR)));
        NonNullList<ItemStack> remainder = NonNullList.withSize(2, ItemStack.EMPTY);
        remainder.set(0, new ItemStack(Items.GLASS_BOTTLE));
        NonNullList<ItemStack> balanced = ContainerBalanceHelper.balance(input,
                new ItemStack(Items.HONEY_BOTTLE), remainder,
                List.of(Ingredient.of(Items.HONEY_BOTTLE), Ingredient.of(Items.SUGAR)));
        helper.assertTrue(balanced.get(0).isEmpty(), "产出填充瓶时不能额外返还空瓶");
        helper.assertTrue(remainder.get(0).is(Items.GLASS_BOTTLE), "余料配平不得修改原始列表");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void genericMixedRecipeIsAtomic(GameTestHelper helper)
    {
        MortarRecipe recipe = new MortarRecipe(
                List.of(FluidIngredient.of(Fluids.WATER, 250)),
                List.of(ItemIngredient.of(Items.APPLE, 1)),
                List.of(FluidIngredient.of(Fluids.WATER, 600)),
                List.of(ItemIngredient.of(Items.SUGAR, 1)), 0);
        List<ItemStack> items = List.of(new ItemStack(Items.APPLE));
        List<FluidStack> fluids = List.of(new FluidStack(Fluids.WATER, 250));
        GenericRecipeInventory required = GenericRecipeMatcher.match(items, fluids, List.of(recipe));
        helper.assertTrue(required != null, "混合物品与流体输入应匹配");
        helper.assertTrue(GenericRecipeMatcher.match(items,
                List.of(new FluidStack(Fluids.LAVA, 250)), List.of(recipe)) == null,
                "不匹配的流体不得触发配方");

        GenericRecipeInventory blocked = new GenericRecipeInventory(items, fluids,
                List.of(new ItemStack(Items.COBBLESTONE, 64)),
                List.of(new FluidStack(Fluids.WATER, 1000)), 0, 1000);
        helper.assertTrue(GenericRecipeProcesser.processing(required, blocked) == null,
                "满输出槽不得加工");
        helper.assertTrue(items.getFirst().getCount() == 1 && fluids.getFirst().getAmount() == 250,
                "加工失败不得修改原始输入");

        GenericRecipeInventory available = new GenericRecipeInventory(items, fluids,
                List.of(ItemStack.EMPTY), List.of(new FluidStack(Fluids.WATER, 400)), 0, 1000);
        GenericRecipeInventory result = GenericRecipeProcesser.processing(required, available);
        helper.assertTrue(result != null && result.inputItemStacks().getFirst().isEmpty()
                && result.inputFluidStacks().getFirst().isEmpty(), "混合加工应消耗两类输入");
        helper.assertTrue(result.outputItemStacks().getFirst().is(Items.SUGAR)
                && result.outputFluidStacks().getFirst().getAmount() == 1000,
                "混合加工输出或容量处理错误");
        GenericRecipeInventory split = GenericRecipeProcesser.processing(required,
                new GenericRecipeInventory(items, fluids, List.of(ItemStack.EMPTY),
                        List.of(new FluidStack(Fluids.WATER, 700), FluidStack.EMPTY), 0, 1000));
        helper.assertTrue(split != null && split.outputFluidStacks().get(0).getAmount() == 1000
                && split.outputFluidStacks().get(1).getAmount() == 300,
                "流体产物应跨输出槽分配且不丢液");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void mortarCraftAndPersistence(GameTestHelper helper)
    {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack mortar = ModItems.MORTAR.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, mortar);
        MortarMenu menu = new MortarMenu(1, player.getInventory(), mortar, InteractionHand.MAIN_HAND);
        menu.getSlot(0).set(new ItemStack(Items.WHEAT, 2));
        menu.craft();
        helper.assertTrue(menu.getSlot(0).getItem().isEmpty(), "面粉研磨应消耗两份小麦");
        helper.assertTrue(menu.getSlot(8).getItem().is(ModItems.FLOUR.get()), "研钵输出面粉不符");
        menu.craft();
        helper.assertTrue(menu.getSlot(8).getItem().getCount() == 1, "重复研磨不得额外产出");
        menu.removed(player);
        helper.assertTrue(new MortarDataHandler(mortar).getAllItems().get(8).is(ModItems.FLOUR.get()), "关闭菜单后产物未保存");

        ItemStack second = ModItems.MORTAR.toStack();
        helper.assertTrue(new MortarDataHandler(second).getAllItems().get(8).isEmpty(), "不同研钵的数据不应串联");
        ItemStack seedMortar = ModItems.MORTAR.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, seedMortar);
        MortarMenu seedMenu = new MortarMenu(3, player.getInventory(), seedMortar, InteractionHand.MAIN_HAND);
        seedMenu.getSlot(0).set(new ItemStack(Items.WHEAT_SEEDS, 4));
        seedMenu.craft();
        helper.assertTrue(seedMenu.getSlot(8).getItem().is(ModItems.FLOUR.get()), "种子标签研磨应产出面粉");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void mortarCulturesYeast(GameTestHelper helper)
    {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack mortar = ModItems.MORTAR.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, mortar);
        MortarMenu menu = new MortarMenu(1, player.getInventory(), mortar, InteractionHand.MAIN_HAND);
        menu.setCarried(ModItems.GLASS_JAR_OF_WATER.toStack());
        menu.interactFluid(player, 0, true);
        menu.getSlot(0).set(new ItemStack(Items.SUGAR));
        menu.getSlot(1).set(ModItems.FLOUR.toStack());

        menu.craft();
        helper.assertTrue(menu.getFluid(0).getAmount() == 125 && menu.getFluid(4).isEmpty()
                && !menu.getSlot(0).getItem().isEmpty() && !menu.getSlot(1).getItem().isEmpty(),
                "缺少酵母原液时应保留水和物品原料");

        menu.setCarried(ModItems.GLASS_JAR_OF_YEAST.toStack());
        menu.interactFluid(player, 1, true);
        menu.craft();
        helper.assertTrue(menu.getFluid(0).isEmpty() && menu.getFluid(1).isEmpty()
                && menu.getSlot(0).getItem().isEmpty() && menu.getSlot(1).getItem().isEmpty(),
                "研磨酵母应消耗水、酵母原液、糖与面粉");
        helper.assertTrue(menu.getFluid(4).is(ModFluids.YEAST.get()) && menu.getFluid(4).getAmount() == 500,
                "研磨应产出 500 mB 酵母");

        for (int i = 0; i < 4; i++)
        {
            menu.setCarried(ModItems.GLASS_JAR.toStack());
            menu.interactFluid(player, 4, false);
            helper.assertTrue(menu.getCarried().is(ModItems.GLASS_JAR_OF_YEAST.get()),
                    "每 125 mB 酵母应装满一个玻璃罐");
        }
        helper.assertTrue(menu.getFluid(4).isEmpty(), "酵母装满四罐后输出槽应清空");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void mortarOutputCapacityAndRecipeIds(GameTestHelper helper)
    {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack mortar = ModItems.MORTAR.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, mortar);
        MortarMenu menu = new MortarMenu(2, player.getInventory(), mortar, InteractionHand.MAIN_HAND);
        menu.getSlot(0).set(ModItems.DYEDREAM_DUST_PIECE.toStack());
        menu.getSlot(1).set(new ItemStack(Items.BONE_MEAL));
        for (int i = 8; i < 12; i++) menu.getSlot(i).set(new ItemStack(Items.COBBLESTONE, 64));
        menu.craft();
        helper.assertTrue(menu.getSlot(0).getItem().getCount() == 1 && menu.getSlot(1).getItem().getCount() == 1,
                "输出放不下时不能消耗输入");
        menu.getSlot(8).set(ItemStack.EMPTY);
        menu.craft();
        helper.assertTrue(menu.getSlot(8).getItem().is(ModItems.DYEDREAM_DYE.get())
                && menu.getSlot(8).getItem().getCount() == 9, "来源 GUI 染料配方应产出 9 个");
        helper.assertTrue(helper.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.MORTAR.get()).stream()
                .anyMatch(recipe -> recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "mortar/dyedream_dye_from_dyedream_dust_piece_and_bone_meal"))), "研钵配方 ID 不符");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void mortarWorkbenchRemainder(GameTestHelper helper)
    {
        ItemStack mortar = ModItems.MORTAR.toStack();
        var input = CraftingInput.of(2, 1, List.of(mortar, ModItems.COARSE_SALT.toStack()));
        var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input,
                helper.getLevel()).orElseThrow();
        helper.assertTrue(recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "salt_from_coarse_salt")), "粗盐工作台配方 ID 不符");
        helper.assertTrue(recipe.value().assemble(input, helper.getLevel().registryAccess()).is(ModItems.SALT.get()),
                "粗盐工作台产物不符");
        helper.assertTrue(recipe.value().getRemainingItems(input).get(0).is(ModItems.MORTAR.get()),
                "工作台研钵应返还");

        var dyeInput = CraftingInput.of(3, 1, List.of(ModItems.DYEDREAM_DUST_PIECE.toStack(),
                new ItemStack(Items.BONE_MEAL), mortar));
        var dyeRecipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, dyeInput,
                helper.getLevel()).orElseThrow();
        helper.assertTrue(dyeRecipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "dyedream_dye_from_dust_piece")), "染料工作台配方 ID 不符");
        helper.assertTrue(dyeRecipe.value().assemble(dyeInput, helper.getLevel().registryAccess()).getCount() == 7,
                "粉尘碎片工作台染料应产出 7 个");
        helper.assertTrue(dyeRecipe.value().getRemainingItems(dyeInput).get(2).is(ModItems.MORTAR.get()),
                "染料工作台研钵应返还");

        new MortarDataHandler(mortar).saveItems(List.of(new ItemStack(Items.APPLE)));
        var mortarFluids = mortar.getCapability(Capabilities.FluidHandler.ITEM);
        helper.assertTrue(mortarFluids != null &&
                mortarFluids.fill(new FluidStack(Fluids.WATER, 1000), FluidAction.EXECUTE) == 1000,
                "重置前研钵应能存入流体");
        var resetInput = CraftingInput.of(1, 1, List.of(mortar));
        var resetRecipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, resetInput,
                helper.getLevel()).orElseThrow();
        helper.assertTrue(resetRecipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "mortar_from_mortar")), "研钵重置配方 ID 不符");
        ItemStack reset = resetRecipe.value().assemble(resetInput, helper.getLevel().registryAccess());
        helper.assertTrue(new MortarDataHandler(reset).getAllItems().get(0).isEmpty(), "重置后的研钵应清空内容");
        helper.assertTrue(new MortarDataHandler(reset).getAllFluids().get(0).isEmpty(), "重置后的研钵应清空流体");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void mortarFluidSlotsAndCapability(GameTestHelper helper)
    {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack mortar = ModItems.MORTAR.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, mortar);
        var capability = FluidUtil.getFluidHandler(mortar).orElseThrow();
        helper.assertTrue(capability.fill(new FluidStack(Fluids.WATER, 1000), FluidAction.SIMULATE) == 1000,
                "研钵流体能力模拟充填不符");
        helper.assertTrue(capability.getFluidInTank(0).isEmpty(), "模拟充填不得改变研钵");
        MortarMenu menu = new MortarMenu(4, player.getInventory(), mortar, InteractionHand.MAIN_HAND);
        menu.setCarried(new ItemStack(Items.WATER_BUCKET));
        menu.interactFluid(player, 0, true);
        helper.assertTrue(menu.getFluid(0).getAmount() == 1000 && menu.getCarried().is(Items.BUCKET),
                "右键流体槽应把水桶倒入研钵并返还桶");
        helper.assertTrue(new MortarDataHandler(mortar).getAllFluids().get(0).getAmount() == 1000,
                "研钵流体状态未持久化");
        var itemHandler = mortar.getCapability(Capabilities.ItemHandler.ITEM);
        helper.assertTrue(itemHandler != null && itemHandler.insertItem(0, new ItemStack(Items.APPLE), false).isEmpty(),
                "研钵物品能力未接通");
        helper.assertTrue(new MortarDataHandler(mortar).getAllFluids().get(0).getAmount() == 1000,
                "物品能力写入不得覆盖其他流体处理器的状态");
        ItemStack restored = ItemStack.parseOptional(helper.getLevel().registryAccess(),
                (CompoundTag) mortar.save(helper.getLevel().registryAccess()));
        helper.assertTrue(new MortarDataHandler(restored).getAllFluids().get(0).getAmount() == 1000,
                "重载研钵物品后流体状态未保存");
        helper.assertTrue(new MortarDataHandler(ModItems.MORTAR.toStack()).getAllFluids().get(0).isEmpty(),
                "不同研钵的流体状态不应串联");
        menu.setCarried(new ItemStack(Items.BUCKET));
        menu.interactFluid(player, 0, false);
        helper.assertTrue(menu.getFluid(0).isEmpty() && menu.getCarried().is(Items.WATER_BUCKET),
                "左键流体槽应将水取回桶");
        player.setItemInHand(InteractionHand.MAIN_HAND, ModItems.MORTAR.toStack());
        menu.setCarried(new ItemStack(Items.WATER_BUCKET));
        menu.interactFluid(player, 0, true);
        helper.assertTrue(menu.getFluid(0).isEmpty(), "换手持研钵后旧菜单不得继续写入");
        helper.succeed();
    }
}
