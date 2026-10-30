package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.recipe.NbtPreservingShapelessRecipe;
import com.pasterdream.pasterdreammod.world.effect.TitaniumArmorEffect;
import com.pasterdream.pasterdreammod.world.item.armoritem.TitaniumArmorItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class TitaniumArmorGameTests {
    private static List<Item> armor() {
        return List.of(ModItems.TITANIUM_HELMET.get(), ModItems.TITANIUM_CHESTPLATE.get(),
                ModItems.TITANIUM_LEGGINGS.get(), ModItems.TITANIUM_BOOTS.get());
    }

    @GameTest(template = "empty")
    public static void armorPropertiesAndSet(GameTestHelper helper) {
        int[] defense = {3, 8, 6, 3};
        int[] durability = {275, 400, 375, 325};
        var tags = List.of(ItemTags.HEAD_ARMOR, ItemTags.CHEST_ARMOR, ItemTags.LEG_ARMOR, ItemTags.FOOT_ARMOR);
        var slots = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (int i = 0; i < armor().size(); i++) {
            var item = (ArmorItem) armor().get(i);
            var stack = new ItemStack(item);
            helper.assertTrue(item.getDefense() == defense[i] && stack.getMaxDamage() == durability[i], "护甲防护/耐久不符");
            helper.assertTrue(item.getEnchantmentValue() == 17 && stack.is(tags.get(i)), "附魔值/护甲标签不符");
            helper.assertTrue(item.getToughness() == 3.0f && item.getMaterial().value().knockbackResistance() == 0.1f,
                    "护甲韧性/击退抗性不符");
            helper.assertTrue(item.isValidRepairItem(stack, ModItems.TITANIUM_INGOT.toStack()), "钛金锭应可修复护甲");
            player.setItemSlot(slots.get(i), stack);
        }
        helper.assertTrue(TitaniumArmorItem.hasFullSet(player), "完整四件应构成套装");
        player.getInventory().tick();
        helper.assertTrue(player.hasEffect(ModEffects.TITANIUM_ARMOR), "头盔应施加 25 tick 套装效果");
        player.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
        helper.assertFalse(TitaniumArmorItem.hasFullSet(player), "混搭护甲不应构成套装");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void protectedEffectRemovalAndAbsorption(GameTestHelper helper) {
        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        var protection = new MobEffectInstance(ModEffects.TITANIUM_ARMOR, 25, 0);
        var event = new MobEffectEvent.Applicable(zombie, protection, null);
        event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        helper.assertTrue(event.getApplicationResult(), "受保护效果应越过 Applicable 拒绝");
        zombie.addEffect(protection);
        zombie.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200));
        helper.assertFalse(zombie.removeEffect(ModEffects.TITANIUM_ARMOR), "单项移除不应清除钛金守护");
        helper.assertTrue(zombie.removeAllEffects(), "清除全部应清除普通效果");
        helper.assertTrue(zombie.hasEffect(ModEffects.TITANIUM_ARMOR), "清除全部应保留受保护效果");
        helper.assertFalse(zombie.hasEffect(MobEffects.MOVEMENT_SPEED), "清除全部应移除普通效果");
        zombie.tickCount = 599;
        ((TitaniumArmorEffect) ModEffects.TITANIUM_ARMOR.get()).applyEffectTick(zombie, 0);
        helper.assertFalse(zombie.hasEffect(MobEffects.ABSORPTION), "未满 600 tick 不应给予吸收");
        zombie.tickCount = 600;
        ((TitaniumArmorEffect) ModEffects.TITANIUM_ARMOR.get()).applyEffectTick(zombie, 0);
        var absorption = zombie.getEffect(MobEffects.ABSORPTION);
        helper.assertTrue(absorption != null && absorption.getDuration() == 600 && absorption.getAmplifier() == 0,
                "每 600 tick 应给予 600 tick 吸收 I");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void protectedEffectExpiresNaturally(GameTestHelper helper) {
        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        zombie.addEffect(new MobEffectInstance(ModEffects.TITANIUM_ARMOR, 5));
        helper.assertTrue(zombie.hasEffect(ModEffects.TITANIUM_ARMOR), "短时效果应先施加");
        helper.runAfterDelay(10, () -> {
            helper.assertFalse(zombie.hasEffect(ModEffects.TITANIUM_ARMOR), "效果自然到期不应被移除保护阻止");
            helper.succeed();
        });
    }

    @GameTest(template = "empty")
    public static void armorUpgradeRecipes(GameTestHelper helper) {
        var bases = List.of(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS);
        for (int i = 0; i < armor().size(); i++) {
            var base = new ItemStack(bases.get(i));
            base.set(DataComponents.CUSTOM_NAME, net.minecraft.network.chat.Component.literal("记忆护甲"));
            base.setDamageValue(73);
            var input = CraftingInput.of(2, 2, List.of(base, ModItems.TITANIUM_INGOT.toStack(),
                    ModItems.TITANIUM_INGOT.toStack(), ModItems.BLACK_STICK.toStack()));
            var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
            helper.assertTrue(recipe.value() instanceof NbtPreservingShapelessRecipe, "升级应保留组件");
            helper.assertTrue(recipe.id().equals(BuiltInRegistries.ITEM.getKey(armor().get(i)).withSuffix("_crafting")),
                    "护甲升级配方 ID 不符");
            var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
            helper.assertTrue(result.is(armor().get(i)) && result.getDamageValue() == 0
                    && result.getHoverName().getString().equals("记忆护甲"), "升级应保留名称并修复损伤");
        }
        helper.succeed();
    }
}
