package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DyedreamToolGameTests {
    private static List<Item> tools() {
        return List.of(ModItems.DYEDREAM_SWORD.get(), ModItems.DYEDREAM_PICKAXE.get(),
                ModItems.DYEDREAM_AXE.get(), ModItems.DYEDREAM_SHOVEL.get(), ModItems.DYEDREAM_HOE.get());
    }

    private static List<Item> titaniumTools() {
        return List.of(ModItems.TITANIUM_SWORD.get(), ModItems.TITANIUM_PICKAXE.get(),
                ModItems.TITANIUM_AXE.get(), ModItems.TITANIUM_SHOVEL.get(), ModItems.TITANIUM_HOE.get());
    }

    @GameTest(template = "empty")
    public static void toolProperties(GameTestHelper helper) {
        double[] damage = {7.0, 5.0, 9.5, 5.5, 1.0};
        double[] speed = {-2.4, -2.8, -3.0, -3.0, 0.0};
        var tags = List.of(ItemTags.SWORDS, ItemTags.PICKAXES, ItemTags.AXES, ItemTags.SHOVELS, ItemTags.HOES);
        for (int i = 0; i < tools().size(); i++) {
            Item item = tools().get(i);
            var stack = new ItemStack(item);
            helper.assertTrue(stack.getMaxDamage() == 1314 && stack.getMaxStackSize() == 1, "染梦合金工具耐久/堆叠上限错误");
            helper.assertTrue(item.getEnchantmentValue() == 22 && stack.is(tags.get(i)), "染梦合金工具附魔值/种类标签错误");
            helper.assertTrue(item.isValidRepairItem(stack, ModItems.DYEDREAM_ALLOY_INGOT.toStack()), "染梦合金锭应可修复工具");
            helper.assertFalse(item.isValidRepairItem(stack, ModItems.TITANIUM_INGOT.toStack()), "钛金锭不应修复染梦合金工具");
            var modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS).modifiers();
            double actualDamage = modifiers.stream().filter(entry -> entry.attribute().equals(Attributes.ATTACK_DAMAGE))
                    .mapToDouble(entry -> entry.modifier().amount()).sum();
            double actualSpeed = modifiers.stream().filter(entry -> entry.attribute().equals(Attributes.ATTACK_SPEED))
                    .mapToDouble(entry -> entry.modifier().amount()).sum();
            helper.assertTrue(Math.abs(actualDamage - damage[i]) < 0.0001 && Math.abs(actualSpeed - speed[i]) < 0.0001,
                    "染梦合金工具攻击属性应保持来源数值");
        }
        var hammer = ModItems.DYEDREAM_HAMMER.toStack();
        helper.assertTrue(hammer.getMaxDamage() == 1314 && hammer.getMaxStackSize() == 1, "染梦合金锤耐久/堆叠上限错误");
        var hammerModifiers = hammer.get(DataComponents.ATTRIBUTE_MODIFIERS).modifiers();
        double hammerDamage = hammerModifiers.stream().filter(entry -> entry.attribute().equals(Attributes.ATTACK_DAMAGE))
                .mapToDouble(entry -> entry.modifier().amount()).sum();
        double hammerSpeed = hammerModifiers.stream().filter(entry -> entry.attribute().equals(Attributes.ATTACK_SPEED))
                .mapToDouble(entry -> entry.modifier().amount()).sum();
        helper.assertTrue(Math.abs(hammerDamage - 9.0) < 0.0001 && Math.abs(hammerSpeed - (-3.3)) < 0.0001,
                "染梦合金锤攻击属性应保持来源数值");
        var pickaxe = ModItems.DYEDREAM_PICKAXE.toStack();
        for (var block : List.of(Blocks.OBSIDIAN, Blocks.ANCIENT_DEBRIS)) {
            helper.assertTrue(pickaxe.isCorrectToolForDrops(block.defaultBlockState()), "染梦合金镐应能采集高等级矿物");
            helper.assertTrue(pickaxe.getDestroySpeed(block.defaultBlockState()) == 11f, "染梦合金镐速度应为 11");
        }
        helper.assertFalse(pickaxe.isCorrectToolForDrops(Blocks.OAK_LOG.defaultBlockState()), "镐不应替代斧的采集类别");
        helper.assertTrue(ModItems.DYEDREAM_UPGRADE.toStack().getMaxStackSize() == 16, "升级套件上限应为 16");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void sourceCraftingPatterns(GameTestHelper helper) {
        var ingot = ModItems.DYEDREAM_ALLOY_INGOT.toStack();
        var bud = ModItems.DYEDREAM_BUD_NUGGET.toStack();
        var stick = ModItems.BLACK_STICK.toStack();
        checkCraft(helper, "raw_dyedream_alloy_ingot", 3, 2, List.of(ModItems.TITANIUM_INGOT.toStack(),
                ModItems.DYEDREAM_DUST.toStack(), bud.copy(), bud.copy(), bud.copy(), bud.copy()),
                ModItems.RAW_DYEDREAM_ALLOY_INGOT.get(), 1);
        checkCraft(helper, "dyedream_upgrade", 3, 3, List.of(ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT), ItemStack.EMPTY,
                ingot.copy(), bud.copy(), ingot.copy(), stick.copy(), ingot.copy(), stick.copy()),
                ModItems.DYEDREAM_UPGRADE.get(), 1);
        checkCraft(helper, "dyedream_hammer", 3, 3, List.of(ingot.copy(), ingot.copy(), ingot.copy(),
                ingot.copy(), stick.copy(), ingot.copy(), ItemStack.EMPTY, stick.copy(), ItemStack.EMPTY),
                ModItems.DYEDREAM_HAMMER.get(), 1);
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void smithingPreservesToolComponents(GameTestHelper helper) {
        for (int i = 0; i < tools().size(); i++) {
            var base = new ItemStack(titaniumTools().get(i));
            base.setDamageValue(87);
            base.set(DataComponents.CUSTOM_NAME, Component.literal("kept name"));
            var input = new SmithingRecipeInput(ModItems.DYEDREAM_BUD_NUGGET.toStack(), base,
                    ModItems.DYEDREAM_UPGRADE.toStack());
            var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMITHING, input, helper.getLevel()).orElseThrow();
            helper.assertTrue(recipe.id().equals(id("dyedream_" + name(tools().get(i)) + "_smithing")), "锻造配方 ID 错误");
            var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
            helper.assertTrue(result.is(tools().get(i)) && result.getCount() == 1 && result.getDamageValue() == 87,
                    "锻造产物、数量或损伤错误");
            helper.assertTrue(Component.literal("kept name").equals(result.get(DataComponents.CUSTOM_NAME)),
                    "锻造应保留自定义名称");
            var wrongTemplate = new SmithingRecipeInput(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE.getDefaultInstance(), base,
                    ModItems.DYEDREAM_UPGRADE.toStack());
            helper.assertFalse(recipe.value().matches(wrongTemplate, helper.getLevel()), "锻造模板位置必须消耗染梦晶芽粒");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void alloyCookingAndStorage(GameTestHelper helper) {
        var raw = ModItems.RAW_DYEDREAM_ALLOY_INGOT.toStack();
        var smelting = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(raw), helper.getLevel()).orElseThrow();
        helper.assertTrue(smelting.id().equals(id("dyedream_alloy_ingot_from_smelting")), "熔炉配方 ID 错误");
        helper.assertTrue(smelting.value().getResultItem(helper.getLevel().registryAccess()).is(ModItems.DYEDREAM_ALLOY_INGOT.get()),
                "熔炉产物应为染梦合金锭");
        var blasting = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.BLASTING, new SingleRecipeInput(raw), helper.getLevel()).orElseThrow();
        helper.assertTrue(blasting.id().equals(id("dyedream_alloy_ingot_from_blasting")), "高炉配方 ID 错误");
        helper.assertTrue(blasting.value().getResultItem(helper.getLevel().registryAccess()).is(ModItems.DYEDREAM_ALLOY_INGOT.get()),
                "高炉产物应为染梦合金锭");

        var nugget = ModItems.DYEDREAM_ALLOY_NUGGET.toStack();
        var nuggets = new java.util.ArrayList<ItemStack>();
        for (int i = 0; i < 9; i++) {
            nuggets.add(nugget.copy());
        }
        checkCraft(helper, "dyedream_alloy_ingot_from_dyedream_alloy_nugget", 3, 3, nuggets,
                ModItems.DYEDREAM_ALLOY_INGOT.get(), 1);
        checkCraft(helper, "dyedream_alloy_nugget_from_dyedream_alloy_ingot", 1, 1,
                List.of(ModItems.DYEDREAM_ALLOY_INGOT.toStack()), ModItems.DYEDREAM_ALLOY_NUGGET.get(), 9);
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void hammerAreaMining(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var center = new BlockPos(1, 3, 1);
        var absoluteCenter = helper.absolutePos(center);
        player.moveTo(absoluteCenter.getX() + 0.5, absoluteCenter.getY() + 2.0, absoluteCenter.getZ() + 0.5, 0.0F, 90.0F);
        for (int x = 0; x <= 2; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 0; z <= 2; z++) {
                    helper.setBlock(new BlockPos(x, y, z), Blocks.STONE);
                }
            }
        }
        helper.setBlock(center, Blocks.AIR);
        var hammer = ModItems.DYEDREAM_HAMMER.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, hammer);
        hammer.getItem().mineBlock(hammer, helper.getLevel(), Blocks.STONE.defaultBlockState(), absoluteCenter, player);
        for (int x = 0; x <= 2; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 0; z <= 2; z++) {
                    helper.assertBlockPresent(Blocks.AIR, new BlockPos(x, y, z));
                }
            }
        }
        helper.assertValueEqual(hammer.getDamageValue(), 27, "锤应消耗 27 点耐久");
        helper.succeed();
    }

    private static void checkCraft(GameTestHelper helper, String id, int width, int height, List<ItemStack> items,
                                   Item expected, int count) {
        var input = CraftingInput.of(width, height, items);
        var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
        helper.assertTrue(recipe.id().equals(id(id)), "配方 ID 错误");
        var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
        helper.assertTrue(result.is(expected) && result.getCount() == count, "配方产物/数量错误");
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path);
    }

    private static String name(Item item) {
        var key = item.builtInRegistryHolder().key().location().getPath();
        return key.substring(key.indexOf('_') + 1);
    }
}
