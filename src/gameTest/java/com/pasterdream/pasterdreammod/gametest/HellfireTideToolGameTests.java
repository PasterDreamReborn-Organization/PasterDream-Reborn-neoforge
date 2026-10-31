package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.event.ModMobDrops;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.TideSwordProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.ArrayList;
import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class HellfireTideToolGameTests {

    @GameTest(template = "empty")
    public static void hellfireToolPropertiesAndUse(GameTestHelper helper) {
        var sword = ModItems.HELLFIRE_SWORD.toStack();
        var pickaxe = ModItems.HELLFIRE_PICKAXE.toStack();
        for (var stack : List.of(sword, pickaxe)) {
            helper.assertTrue(stack.getMaxDamage() == 1255 && stack.getMaxStackSize() == 1, "狱炎工具耐久或堆叠错误");
            helper.assertTrue(stack.has(DataComponents.FIRE_RESISTANT) && stack.getItem().getEnchantmentValue() == 23, "狱炎工具防火或附魔值错误");
            helper.assertTrue(stack.getItem().isValidRepairItem(stack, ModItems.MOLTEN_GOLD_INGOT.toStack()), "炙焰金锭应修复狱炎工具");
        }
        helper.assertTrue(sword.is(ItemTags.SWORDS) && pickaxe.is(ItemTags.PICKAXES), "狱炎工具标签错误");
        helper.assertTrue(ModItems.HELLFIRE_PICKAXE.toStack().isCorrectToolForDrops(Blocks.OBSIDIAN.defaultBlockState()), "狱炎镐应能采集黑曜石");
        assertAttributes(helper, sword, 6.0, -2.2, "狱炎剑");
        assertAttributes(helper, pickaxe, 4.0, -2.6, "狱炎镐");

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, pickaxe);
        player.getFoodData().setExhaustion(0);
        int beforeFood = player.getFoodData().getFoodLevel();
        pickaxe.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(player.getFoodData().getExhaustionLevel() == 4.0f, "狱炎镐应消耗 4 点饱食度");
        helper.assertTrue(player.getFoodData().getFoodLevel() == beforeFood, "狱炎镐不应立即扣饥饿值");
        var haste = player.getEffect(net.minecraft.world.effect.MobEffects.DIG_SPEED);
        helper.assertTrue(haste != null && haste.getDuration() == 600 && haste.getAmplifier() == 1, "狱炎镐应给予 30 秒急迫 II");
        helper.assertTrue(player.getCooldowns().isOnCooldown(pickaxe.getItem()), "狱炎镐应设置 8 秒冷却");

        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        sword.getItem().hurtEnemy(sword, zombie, player);
        helper.assertTrue(zombie.getRemainingFireTicks() >= 80, "狱炎剑应点燃目标 4 秒");
        zombie.setRemainingFireTicks(10);
        sword.getItem().hurtEnemy(sword, zombie, player);
        helper.assertTrue(zombie.getRemainingFireTicks() >= 50, "已燃烧目标应叠加 2 秒");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void tideSwordProperties(GameTestHelper helper) {
        var tide = ModItems.TIDE_SWORD.toStack();
        var beihai = ModItems.BEIHAI_RUO_TIDE_SWORD.toStack();
        for (var stack : List.of(tide, beihai)) {
            helper.assertTrue(stack.getMaxDamage() == 1561 && stack.is(ItemTags.SWORDS), "引潮剑耐久或标签错误");
            helper.assertTrue(stack.getItem().getEnchantmentValue() == 11, "引潮剑附魔值错误");
            helper.assertTrue(stack.getItem().isValidRepairItem(stack, new ItemStack(Items.PRISMARINE_CRYSTALS)), "海晶碎片应修复引潮剑");
        }
        assertAttributes(helper, tide, 7.5, -2.8, "引潮剑");
        assertAttributes(helper, beihai, 8.0, -2.8, "北海若引潮");
        helper.assertTrue(beihai.is(ItemTags.TRIDENT_ENCHANTABLE), "北海若引潮应可附魔三叉戟附魔");
        helper.assertTrue(new ItemStack(Items.TRIDENT).is(ItemTags.TRIDENT_ENCHANTABLE), "原版三叉戟应仍属于三叉戟附魔标签");
        helper.assertTrue(BuiltInRegistries.ITEM.getTag(com.pasterdream.pasterdreammod.tag.ModItemTags.SKILL_COOLDOWN)
                .map(tag -> tag.stream().anyMatch(h -> h.value() == ModItems.BEIHAI_RUO_TIDE_SWORD.get()))
                .orElse(false), "北海若引潮应属于战技冷却标签");

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, tide);
        tide.getItem().inventoryTick(tide, helper.getLevel(), player, 0, true);
        helper.assertTrue(player.hasEffect(net.minecraft.world.effect.MobEffects.WATER_BREATHING), "引潮剑手持应提供水下呼吸");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void recipesAndProjectile(GameTestHelper helper) {
        var blaze = new ItemStack(Items.BLAZE_POWDER);
        var scrap = new ItemStack(Items.NETHERITE_SCRAP);
        var moltenSword = ModItems.MOLTEN_GOLD_SWORD.toStack();
        moltenSword.setDamageValue(50);
        moltenSword.set(DataComponents.CUSTOM_NAME, Component.literal("kept"));
        var block = ModItems.MOLTEN_GOLD_BLOCK.toStack();
        var hellfireInput = CraftingInput.of(3, 3, List.of(
                ItemStack.EMPTY, blaze, ItemStack.EMPTY,
                scrap, moltenSword, scrap,
                ItemStack.EMPTY, block, ItemStack.EMPTY));
        var hellfireRecipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, hellfireInput, helper.getLevel()).orElseThrow();
        helper.assertTrue(hellfireRecipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "hellfire_sword")), "狱炎剑配方 ID 错误");
        var hellfireResult = hellfireRecipe.value().assemble(hellfireInput, helper.getLevel().registryAccess());
        helper.assertTrue(hellfireResult.is(ModItems.HELLFIRE_SWORD.get()) && hellfireResult.getDamageValue() == 0
                && Component.literal("kept").equals(hellfireResult.get(DataComponents.CUSTOM_NAME)), "狱炎剑升级应保留组件并重置损伤");

        var tideInput = CraftingInput.of(3, 3, List.of(
                ItemStack.EMPTY, new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_CRYSTALS),
                new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.TRIDENT), new ItemStack(Items.PRISMARINE_SHARD),
                ModItems.ELDER_GUARDIAN_SCALE.toStack(), new ItemStack(Items.PRISMARINE_SHARD), ItemStack.EMPTY));
        var tideRecipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, tideInput, helper.getLevel()).orElseThrow();
        helper.assertTrue(tideRecipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "tide_sword")), "引潮剑配方 ID 错误");

        var smithingInput = new SmithingRecipeInput(new ItemStack(Items.PRISMARINE_CRYSTALS),
                ModItems.TIDE_SWORD.toStack(), ModItems.BLUE_HEART_OF_THE_SEA.toStack());
        var beihaiRecipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMITHING, smithingInput, helper.getLevel()).orElseThrow();
        helper.assertTrue(beihaiRecipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "beihairuo_tide_sword_smithing")), "北海若锻造配方 ID 错误");

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var projectile = new TideSwordProjectileEntity(helper.getLevel(), player, ModItems.BEIHAI_RUO_TIDE_SWORD.toStack());
        helper.assertTrue(projectile.getType() == ModEntities.TIDE_SWORD_PROJECTILE.get(), "投掷物实体类型错误");
        helper.assertTrue(projectile.getDisplayItem().is(ModItems.BEIHAI_RUO_TIDE_SWORD.get()), "投掷物应同步展示剑");
        helper.assertTrue(projectile.pickup == AbstractArrow.Pickup.ALLOWED, "玩家投掷物应可拾取");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void materialsAndDrops(GameTestHelper helper) {
        helper.assertTrue(ModItems.BLUE_HEART_OF_THE_SEA.toStack().getRarity() == net.minecraft.world.item.Rarity.UNCOMMON, "蔚蓝海洋之心应为罕见");
        var dew = ModItems.BLUE_DEW.toStack();
        var food = dew.get(DataComponents.FOOD);
        helper.assertTrue(food != null && food.nutrition() == 0 && food.canAlwaysEat(), "蔚蓝露滴应可随时饮用且不提供饱食度");

        var heartInput = CraftingInput.of(3, 3, List.of(
                new ItemStack(Items.GLOW_INK_SAC), ModItems.BLUE_DEW.toStack(), new ItemStack(Items.GLOW_INK_SAC),
                ModItems.BLUE_DEW.toStack(), new ItemStack(Items.HEART_OF_THE_SEA), ModItems.BLUE_DEW.toStack(),
                new ItemStack(Items.GLOW_INK_SAC), ModItems.BLUE_DEW.toStack(), new ItemStack(Items.GLOW_INK_SAC)));
        var heartRecipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, heartInput, helper.getLevel()).orElseThrow();
        helper.assertTrue(heartRecipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "blue_heart_of_the_sea")), "蔚蓝海洋之心配方 ID 错误");

        ElderGuardian guardian = helper.spawnWithNoFreeWill(EntityType.ELDER_GUARDIAN, new BlockPos(1, 2, 1));
        var drops = new ArrayList<ItemEntity>();
        ModMobDrops.onLivingDrops(new LivingDropsEvent(guardian,
                helper.getLevel().damageSources().generic(), drops, true));
        helper.assertTrue(drops.stream().anyMatch(drop -> drop.getItem().is(ModItems.ELDER_GUARDIAN_SCALE.get())), "远古守卫者应掉落鳞片");
        helper.succeed();
    }

    private static void assertAttributes(GameTestHelper helper, ItemStack stack, double damage, double speed, String name) {
        var modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS).modifiers();
        double actualDamage = modifiers.stream().filter(entry -> entry.attribute().equals(Attributes.ATTACK_DAMAGE))
                .mapToDouble(entry -> entry.modifier().amount()).sum();
        double actualSpeed = modifiers.stream().filter(entry -> entry.attribute().equals(Attributes.ATTACK_SPEED))
                .mapToDouble(entry -> entry.modifier().amount()).sum();
        helper.assertTrue(Math.abs(actualDamage - damage) < 0.0001 && Math.abs(actualSpeed - speed) < 0.0001,
                name + "攻击属性错误: damage=" + actualDamage + " speed=" + actualSpeed);
    }
}
