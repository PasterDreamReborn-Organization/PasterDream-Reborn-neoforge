package com.pasterdream.pasterdreammod.gametest;

import com.mojang.serialization.JsonOps;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.recipe.NbtPreservingShapelessRecipe;
import io.netty.buffer.Unpooled;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class TitaniumToolGameTests {
    private static List<Item> tools() {
        return List.of(ModItems.TITANIUM_SWORD.get(), ModItems.TITANIUM_PICKAXE.get(), ModItems.TITANIUM_AXE.get(),
                ModItems.TITANIUM_SHOVEL.get(), ModItems.TITANIUM_HOE.get());
    }

    private static List<Item> bases() {
        return List.of(Items.DIAMOND_SWORD, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_SHOVEL, Items.DIAMOND_HOE);
    }

    @GameTest(template = "empty")
    public static void toolProperties(GameTestHelper helper) {
        double[] damage = {6.5, 4.5, 9.0, 5.5, 0.5};
        double[] speed = {-2.4, -2.8, -3.0, -3.0, 0.0};
        var tags = List.of(ItemTags.SWORDS, ItemTags.PICKAXES, ItemTags.AXES, ItemTags.SHOVELS, ItemTags.HOES);
        for (int i = 0; i < tools().size(); i++) {
            Item item = tools().get(i);
            var stack = new ItemStack(item);
            helper.assertTrue(stack.getMaxDamage() == 1721 && stack.getMaxStackSize() == 1, "工具耐久/堆叠上限错误");
            helper.assertTrue(item.getEnchantmentValue() == 17 && stack.is(tags.get(i)), "工具附魔值/种类标签错误");
            helper.assertTrue(item.isValidRepairItem(stack, ModItems.TITANIUM_INGOT.toStack()), "钛金锭应可修复工具");
            helper.assertFalse(item.isValidRepairItem(stack, new ItemStack(Items.DIAMOND)), "钻石不应修复钛金工具");
            var modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS).modifiers();
            double actualDamage = modifiers.stream().filter(entry -> entry.attribute().equals(Attributes.ATTACK_DAMAGE))
                    .mapToDouble(entry -> entry.modifier().amount()).sum();
            double actualSpeed = modifiers.stream().filter(entry -> entry.attribute().equals(Attributes.ATTACK_SPEED))
                    .mapToDouble(entry -> entry.modifier().amount()).sum();
            helper.assertTrue(Math.abs(actualDamage - damage[i]) < 0.0001 && Math.abs(actualSpeed - speed[i]) < 0.0001,
                    "工具攻击属性应保持来源数值");
        }
        var pickaxe = ModItems.TITANIUM_PICKAXE.toStack();
        for (var block : List.of(Blocks.OBSIDIAN, Blocks.ANCIENT_DEBRIS, ModBlocks.DEEPSLATE_TITANIUM_ORE.get())) {
            helper.assertTrue(pickaxe.isCorrectToolForDrops(block.defaultBlockState()), "钛金镐应能采集高等级矿物");
            helper.assertTrue(pickaxe.getDestroySpeed(block.defaultBlockState()) == 9f, "钛金镐速度应为 9");
        }
        helper.assertFalse(pickaxe.isCorrectToolForDrops(Blocks.OAK_LOG.defaultBlockState()), "镐不应替代斧的采集类别");
        helper.assertTrue(ModItems.TITANIUM_UPGRADE.toStack().getMaxStackSize() == 16, "升级套件上限应为 16");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void craftingPreservesComponentsAndRepairs(GameTestHelper helper) {
        for (int i = 0; i < tools().size(); i++) {
            var base = decorated(helper, bases().get(i));
            var before = base.copy();
            var input = upgradeInput(base);
            var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
            helper.assertTrue(recipe.value() instanceof NbtPreservingShapelessRecipe, "工作台应使用组件保留配方");
            var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
            assertUpgrade(helper, base, result, tools().get(i), 0);
            helper.assertTrue(ItemStack.matches(base, before), "预览合成不得修改原料");
            helper.assertTrue(recipe.value().getRemainingItems(input).stream().allMatch(ItemStack::isEmpty), "升级不应返还原料");
            var insufficient = CraftingInput.of(3, 1, List.of(base, ModItems.TITANIUM_INGOT.toStack(), ModItems.BLACK_STICK.toStack()));
            helper.assertFalse(recipe.value().matches(insufficient, helper.getLevel()), "少一块钛金锭不应匹配");
        }
        // 不可破坏组件不应阻止工作台清除原有损伤。
        var base = decorated(helper, Items.DIAMOND_PICKAXE);
        base.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
        var input = upgradeInput(base);
        var result = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel())
                .orElseThrow().value().assemble(input, helper.getLevel().registryAccess());
        helper.assertTrue(result.has(DataComponents.UNBREAKABLE) && result.getDamageValue() == 0, "不可破坏工具仍应清除损伤");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void smithingPreservesDamage(GameTestHelper helper) {
        for (int i = 0; i < tools().size(); i++) {
            var base = decorated(helper, bases().get(i));
            var input = new SmithingRecipeInput(new ItemStack(Items.FLINT), base, ModItems.TITANIUM_UPGRADE.toStack());
            var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMITHING, input, helper.getLevel()).orElseThrow();
            assertUpgrade(helper, base, recipe.value().assemble(input, helper.getLevel().registryAccess()), tools().get(i), 87);
            var wrongTemplate = new SmithingRecipeInput(new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), base,
                    ModItems.TITANIUM_UPGRADE.toStack());
            helper.assertFalse(recipe.value().matches(wrongTemplate, helper.getLevel()), "锻造模板位置必须消耗燧石");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void recipeSerializationAndSourceOrder(GameTestHelper helper) {
        var input = upgradeInput(decorated(helper, Items.DIAMOND_PICKAXE));
        var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow().value();
        var ops = helper.getLevel().registryAccess().createSerializationContext(JsonOps.INSTANCE);
        var json = Recipe.CODEC.encodeStart(ops, recipe).getOrThrow();
        var decoded = (NbtPreservingShapelessRecipe) Recipe.CODEC.parse(ops, json).getOrThrow();
        var buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), helper.getLevel().registryAccess());
        try {
            Recipe.STREAM_CODEC.encode(buffer, decoded);
            var network = (NbtPreservingShapelessRecipe) Recipe.STREAM_CODEC.decode(buffer);
            helper.assertTrue(network.matches(input, helper.getLevel()), "网络往返后配方应仍匹配");
            assertUpgrade(helper, input.getItem(0), network.assemble(input, helper.getLevel().registryAccess()),
                    ModItems.TITANIUM_PICKAXE.get(), 0);
        } finally {
            buffer.release();
        }
        var namedIngot = ModItems.TITANIUM_INGOT.toStack();
        namedIngot.set(DataComponents.CUSTOM_NAME, Component.literal("material name"));
        var plainToolFirst = CraftingInput.of(2, 2, List.of(new ItemStack(Items.DIAMOND_PICKAXE), namedIngot,
                ModItems.TITANIUM_INGOT.toStack(), ModItems.BLACK_STICK.toStack()));
        helper.assertFalse(decoded.assemble(plainToolFirst, helper.getLevel().registryAccess()).has(DataComponents.CUSTOM_NAME),
                "全新耐久工具仍应优先于后方带名材料，兼容旧 Damage:0 NBT");
        var namedMaterialFirst = CraftingInput.of(2, 2, List.of(namedIngot, new ItemStack(Items.DIAMOND_PICKAXE),
                ModItems.TITANIUM_INGOT.toStack(), ModItems.BLACK_STICK.toStack()));
        helper.assertTrue(decoded.assemble(namedMaterialFirst, helper.getLevel().registryAccess()).getHoverName().getString().equals("material name"),
                "保留来源首个带数据原料的扫描顺序");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void ingredientAcquisition(GameTestHelper helper) {
        var blackstone = new ItemStack(Items.BLACKSTONE);
        checkCraft(helper, "black_stick", CraftingInput.of(3, 3, List.of(ItemStack.EMPTY, ItemStack.EMPTY, blackstone,
                ItemStack.EMPTY, blackstone, ItemStack.EMPTY, blackstone, ItemStack.EMPTY, ItemStack.EMPTY)), ModItems.BLACK_STICK.get());
        checkCraft(helper, "black_stick_straight", CraftingInput.of(1, 3, List.of(blackstone, blackstone, blackstone)), ModItems.BLACK_STICK.get());
        var ingot = ModItems.TITANIUM_INGOT.toStack();
        var stick = ModItems.BLACK_STICK.toStack();
        checkCraft(helper, "titanium_upgrade", CraftingInput.of(3, 3, List.of(ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT), ItemStack.EMPTY,
                ingot, new ItemStack(Items.STONE_BUTTON), ingot, stick, ingot, stick)), ModItems.TITANIUM_UPGRADE.get());
        helper.succeed();
    }

    private static ItemStack decorated(GameTestHelper helper, Item item) {
        var stack = new ItemStack(item);
        stack.setDamageValue(87);
        stack.set(DataComponents.CUSTOM_NAME, Component.literal("kept name"));
        stack.set(DataComponents.REPAIR_COST, 7);
        var data = new CompoundTag();
        data.putString("test_marker", "retained");
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(data));
        stack.enchant(helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING), 3);
        return stack;
    }

    private static CraftingInput upgradeInput(ItemStack base) {
        return CraftingInput.of(2, 2, List.of(base, ModItems.TITANIUM_INGOT.toStack(), ModItems.TITANIUM_INGOT.toStack(), ModItems.BLACK_STICK.toStack()));
    }

    private static void assertUpgrade(GameTestHelper helper, ItemStack base, ItemStack result, Item expected, int damage) {
        helper.assertTrue(result.is(expected) && result.getCount() == 1 && result.getMaxDamage() == 1721 && result.getDamageValue() == damage,
                "升级产物、耐久上限或损伤错误");
        helper.assertTrue(result.get(DataComponents.CUSTOM_NAME).equals(base.get(DataComponents.CUSTOM_NAME))
                && result.get(DataComponents.ENCHANTMENTS).equals(base.get(DataComponents.ENCHANTMENTS))
                && result.get(DataComponents.REPAIR_COST).equals(base.get(DataComponents.REPAIR_COST))
                && result.get(DataComponents.CUSTOM_DATA).equals(base.get(DataComponents.CUSTOM_DATA)), "升级应保留附魔、名称、修复费用及自定义数据");
        helper.assertTrue(result.get(DataComponents.ATTRIBUTE_MODIFIERS).equals(new ItemStack(expected).get(DataComponents.ATTRIBUTE_MODIFIERS))
                && result.get(DataComponents.TOOL).equals(new ItemStack(expected).get(DataComponents.TOOL)), "升级应保留目标工具默认属性与挖掘组件");
    }

    private static void checkCraft(GameTestHelper helper, String id, CraftingInput input, Item expected) {
        var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
        helper.assertTrue(recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, id)), "配方 ID 错误");
        var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
        helper.assertTrue(result.is(expected) && result.getCount() == 1, "前置材料配方产物/数量错误");
    }
}
