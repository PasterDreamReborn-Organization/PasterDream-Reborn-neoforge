package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.moltengoldtool.MoltenGoldSwordItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class MoltenGoldToolGameTests {
    private static List<Item> tools() {
        return List.of(ModItems.MOLTEN_GOLD_SWORD.get(), ModItems.MOLTEN_GOLD_PICKAXE.get(),
                ModItems.MOLTEN_GOLD_AXE.get(), ModItems.MOLTEN_GOLD_SHOVEL.get(), ModItems.MOLTEN_GOLD_HOE.get());
    }

    @GameTest(template = "empty")
    public static void toolProperties(GameTestHelper helper) {
        double[] damage = {5.0, 3.0, 8.0, 3.5, 0.0};
        double[] speed = {-2.3, -2.7, -3.0, -2.9, -0.5};
        var tags = List.of(ItemTags.SWORDS, ItemTags.PICKAXES, ItemTags.AXES, ItemTags.SHOVELS, ItemTags.HOES);
        for (int i = 0; i < tools().size(); i++) {
            Item item = tools().get(i);
            var stack = new ItemStack(item);
            helper.assertTrue(stack.getMaxDamage() == 251 && stack.getMaxStackSize() == 1, "炙焰金工具耐久/堆叠错误");
            helper.assertTrue(item.getEnchantmentValue() == 23 && stack.is(tags.get(i)), "附魔值/工具标签错误");
            helper.assertTrue(item.isValidRepairItem(stack, ModItems.MOLTEN_GOLD_INGOT.toStack()), "炙焰金锭应修复工具");
            helper.assertFalse(item.isValidRepairItem(stack, new ItemStack(Items.GOLD_INGOT)), "金锭不应修复工具");
            helper.assertTrue(stack.has(DataComponents.FIRE_RESISTANT), "工具应防火");
            var modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS).modifiers();
            double actualDamage = modifiers.stream().filter(entry -> entry.attribute().equals(Attributes.ATTACK_DAMAGE))
                    .mapToDouble(entry -> entry.modifier().amount()).sum();
            double actualSpeed = modifiers.stream().filter(entry -> entry.attribute().equals(Attributes.ATTACK_SPEED))
                    .mapToDouble(entry -> entry.modifier().amount()).sum();
            helper.assertTrue(Math.abs(actualDamage - damage[i]) < 0.0001 && Math.abs(actualSpeed - speed[i]) < 0.0001,
                    "攻击属性须保持来源值: index=" + i + " damage=" + actualDamage + " speed=" + actualSpeed);
        }
        var pickaxe = ModItems.MOLTEN_GOLD_PICKAXE.toStack();
        helper.assertTrue(pickaxe.isCorrectToolForDrops(Blocks.DIAMOND_ORE.defaultBlockState()), "炙焰金镐应可采集钻石矿");
        helper.assertFalse(pickaxe.isCorrectToolForDrops(Blocks.OBSIDIAN.defaultBlockState()), "炙焰金镐不应采集黑曜石");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void activeUseAndSwordFire(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (int i = 1; i < tools().size(); i++) {
            Item tool = tools().get(i);
            var stack = new ItemStack(tool);
            player.setItemInHand(InteractionHand.OFF_HAND, stack);
            player.getFoodData().setExhaustion(0);
            int beforeFood = player.getFoodData().getFoodLevel();
            var result = tool.use(helper.getLevel(), player, InteractionHand.OFF_HAND);
            helper.assertTrue(result.getObject() == stack, "副手应返回原工具堆叠");
            helper.assertTrue(player.getFoodData().getExhaustionLevel() == 4.0f, "使用应增加 4 点耗竭");
            helper.assertTrue(player.getFoodData().getFoodLevel() == beforeFood, "使用不应立即扣 4 点饥饿值");
            var haste = player.getEffect(net.minecraft.world.effect.MobEffects.DIG_SPEED);
            helper.assertTrue(haste != null && haste.getDuration() == 400 && haste.getAmplifier() == 0,
                    "使用应给予 400 tick 急迫 I");
            helper.assertTrue(player.getCooldowns().isOnCooldown(tool), "使用应设置 200 tick 冷却");
        }
        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        var sword = ModItems.MOLTEN_GOLD_SWORD.toStack();
        ((MoltenGoldSwordItem) sword.getItem()).hurtEnemy(sword, zombie, player);
        helper.assertTrue(zombie.getRemainingFireTicks() >= 60, "剑命中应点燃目标 3 秒");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void sourceCraftingPatterns(GameTestHelper helper) {
        var ingot = ModItems.MOLTEN_GOLD_INGOT.toStack();
        var stick = ModItems.BLACK_STICK.toStack();
        checkCraft(helper, CraftingInput.of(1, 3, List.of(ingot, ingot, stick)), ModItems.MOLTEN_GOLD_SWORD.get());
        checkCraft(helper, CraftingInput.of(3, 3, List.of(ingot, ingot, ingot,
                ItemStack.EMPTY, stick, ItemStack.EMPTY, ItemStack.EMPTY, stick, ItemStack.EMPTY)), ModItems.MOLTEN_GOLD_PICKAXE.get());
        checkCraft(helper, CraftingInput.of(2, 3, List.of(ingot, ingot, ingot, stick, ItemStack.EMPTY, stick)), ModItems.MOLTEN_GOLD_AXE.get());
        checkCraft(helper, CraftingInput.of(1, 3, List.of(ingot, stick, stick)), ModItems.MOLTEN_GOLD_SHOVEL.get());
        checkCraft(helper, CraftingInput.of(2, 3, List.of(ingot, ingot, ItemStack.EMPTY, stick, ItemStack.EMPTY, stick)), ModItems.MOLTEN_GOLD_HOE.get());
        helper.succeed();
    }

    private static void checkCraft(GameTestHelper helper, CraftingInput input, Item expected) {
        var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
        var id = BuiltInRegistries.ITEM.getKey(expected);
        helper.assertTrue(recipe.id().equals(id), "炙焰金工具配方 ID 错误");
        var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
        helper.assertTrue(result.is(expected) && result.getCount() == 1, "炙焰金工具配方产物错误");
    }
}
