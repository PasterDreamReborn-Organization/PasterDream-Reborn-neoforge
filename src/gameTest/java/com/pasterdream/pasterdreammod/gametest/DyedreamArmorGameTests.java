package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.effect.DyedreamArmorEffect;
import com.pasterdream.pasterdreammod.world.item.armoritem.DyedreamArmorItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DyedreamArmorGameTests {
    private static List<Item> armor() {
        return List.of(ModItems.DYEDREAM_HELMET.get(), ModItems.DYEDREAM_CHESTPLATE.get(),
                ModItems.DYEDREAM_LEGGINGS.get(), ModItems.DYEDREAM_BOOTS.get());
    }

    @GameTest(template = "empty")
    public static void armorPropertiesAndSet(GameTestHelper helper) {
        int[] defense = {3, 8, 6, 3};
        int[] durability = {440, 640, 600, 520};
        var tags = List.of(ItemTags.HEAD_ARMOR, ItemTags.CHEST_ARMOR, ItemTags.LEG_ARMOR, ItemTags.FOOT_ARMOR);
        var slots = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (int i = 0; i < armor().size(); i++) {
            var item = (ArmorItem) armor().get(i);
            var stack = new ItemStack(item);
            helper.assertTrue(item.getDefense() == defense[i] && stack.getMaxDamage() == durability[i], "护甲防御/耐久不符");
            helper.assertTrue(item.getEnchantmentValue() == 22 && stack.is(tags.get(i)), "附魔值/护甲标签不符");
            helper.assertTrue(item.getToughness() == 3.0f && item.getMaterial().value().knockbackResistance() == 0.1f,
                    "护甲韧性/击退抗性不符");
            helper.assertTrue(item.isValidRepairItem(stack, ModItems.DYEDREAM_ALLOY_INGOT.toStack()), "染梦合金锭应能修复装备");
            player.setItemSlot(slots.get(i), stack);
        }
        helper.assertTrue(DyedreamArmorItem.hasFullSet(player), "穿满四件应识别为整套");
        player.getInventory().tick();
        helper.assertTrue(player.hasEffect(ModEffects.DYEDREAM_ARMOR), "头盔应施加 25 tick 染梦守护");
        helper.assertTrue(player.hasEffect(ModEffects.DYEDREAM_UP), "头盔应施加 25 tick 染梦工具增强");
        helper.assertTrue(player.getAttributeValue(Attributes.MAX_HEALTH) == 24.0, "染梦守护应使最大生命值 +4");
        player.setItemSlot(EquipmentSlot.FEET, ModItems.TITANIUM_BOOTS.toStack());
        helper.assertFalse(DyedreamArmorItem.hasFullSet(player), "混搭钛金靴不应识别为整套");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void protectedEffectRemovalAndAbsorption(GameTestHelper helper) {
        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        var protection = new MobEffectInstance(ModEffects.DYEDREAM_ARMOR, 25, 0);
        var event = new MobEffectEvent.Applicable(zombie, protection, null);
        event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        helper.assertTrue(event.getApplicationResult(), "受保护效果应越过 Applicable 拒绝");
        zombie.addEffect(protection);
        helper.assertTrue(zombie.getAttributeValue(Attributes.MAX_HEALTH) == 24.0, "染梦守护应使最大生命值 +4");
        zombie.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200));
        helper.assertFalse(zombie.removeEffect(ModEffects.DYEDREAM_ARMOR), "单独移除应被染梦守护拒绝");
        helper.assertTrue(zombie.removeAllEffects(), "移除全部应移除普通效果");
        helper.assertTrue(zombie.hasEffect(ModEffects.DYEDREAM_ARMOR), "移除全部应保留受保护效果");
        helper.assertFalse(zombie.hasEffect(MobEffects.MOVEMENT_SPEED), "移除全部应清除普通效果");
        zombie.tickCount = 599;
        ((DyedreamArmorEffect) ModEffects.DYEDREAM_ARMOR.get()).applyEffectTick(zombie, 0);
        helper.assertFalse(zombie.hasEffect(MobEffects.ABSORPTION), "未到 600 tick 不应给予吸收");
        zombie.tickCount = 600;
        ((DyedreamArmorEffect) ModEffects.DYEDREAM_ARMOR.get()).applyEffectTick(zombie, 0);
        var absorption = zombie.getEffect(MobEffects.ABSORPTION);
        helper.assertTrue(absorption != null && absorption.getDuration() == 600 && absorption.getAmplifier() == 0,
                "每 600 tick 应给予 600 tick 伤害吸收 I");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void protectedEffectExpiresNaturally(GameTestHelper helper) {
        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        zombie.addEffect(new MobEffectInstance(ModEffects.DYEDREAM_ARMOR, 5));
        helper.assertTrue(zombie.hasEffect(ModEffects.DYEDREAM_ARMOR), "定时效果应被施加");
        helper.runAfterDelay(10, () -> {
            helper.assertFalse(zombie.hasEffect(ModEffects.DYEDREAM_ARMOR), "效果自然到期不应被保护机制阻止");
            helper.assertTrue(zombie.getAttributeValue(Attributes.MAX_HEALTH) == 20.0, "效果到期应移除最大生命值加成");
            helper.succeed();
        });
    }

    @GameTest(template = "empty")
    public static void dyedreamToolDamageBonus(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        DamageSource source = helper.getLevel().damageSources().playerAttack(player);
        player.addEffect(new MobEffectInstance(ModEffects.DYEDREAM_UP, 100, 0));
        player.setItemSlot(EquipmentSlot.MAINHAND, ModItems.DYEDREAM_SWORD.toStack());
        var boosted = damageEvent(zombie, source, 10.0f);
        PasterDreamMod.onLivingHurt(boosted);
        helper.assertTrue(boosted.getAmount() == 15.0f, "染梦工具在套装增强下应造成 1.5 倍伤害");
        player.setItemSlot(EquipmentSlot.MAINHAND, ModItems.TITANIUM_SWORD.toStack());
        var titanium = damageEvent(zombie, source, 10.0f);
        PasterDreamMod.onLivingHurt(titanium);
        helper.assertTrue(titanium.getAmount() == 10.0f, "钛金工具不应享受染梦增伤");
        var unequipped = helper.makeMockPlayer(GameType.SURVIVAL);
        unequipped.setItemSlot(EquipmentSlot.MAINHAND, ModItems.DYEDREAM_SWORD.toStack());
        var withoutSet = damageEvent(zombie, helper.getLevel().damageSources().playerAttack(unequipped), 10.0f);
        PasterDreamMod.onLivingHurt(withoutSet);
        helper.assertTrue(withoutSet.getAmount() == 10.0f, "未穿整套时染梦工具不应增伤");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void armorCraftingAndSmithingRecipes(GameTestHelper helper) {
        var ingot = ModItems.DYEDREAM_ALLOY_INGOT.toStack();
        var deck = ModItems.PROTECT_DECK.toStack();
        var empty = ItemStack.EMPTY;
        checkCraft(helper, CraftingInput.of(3, 2, List.of(ingot, deck, ingot, ingot, empty, ingot)),
                "dyedream_helmet_from_protect_deck", ModItems.DYEDREAM_HELMET.get());
        checkCraft(helper, CraftingInput.of(3, 3, List.of(deck, empty, deck,
                ingot, ingot, ingot, deck, ingot, deck)),
                "dyedream_chestplate_from_protect_deck", ModItems.DYEDREAM_CHESTPLATE.get());
        checkCraft(helper, CraftingInput.of(3, 3, List.of(ingot, deck, ingot,
                ingot, empty, ingot, deck, empty, deck)),
                "dyedream_leggings_from_protect_deck", ModItems.DYEDREAM_LEGGINGS.get());
        checkCraft(helper, CraftingInput.of(3, 2, List.of(ingot, empty, ingot, ingot, empty, ingot)),
                "dyedream_boots_from_protect_deck", ModItems.DYEDREAM_BOOTS.get());

        var bases = List.of(ModItems.TITANIUM_HELMET.get(), ModItems.TITANIUM_CHESTPLATE.get(),
                ModItems.TITANIUM_LEGGINGS.get(), ModItems.TITANIUM_BOOTS.get());
        for (int i = 0; i < armor().size(); i++) {
            var base = new ItemStack(bases.get(i));
            base.set(DataComponents.CUSTOM_NAME, Component.literal("保留名称"));
            base.setDamageValue(73);
            var input = new SmithingRecipeInput(deck, base, ModItems.DYEDREAM_UPGRADE.toStack());
            var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMITHING, input,
                    helper.getLevel()).orElseThrow();
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                    net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(armor().get(i)).getPath() + "_smithing");
            helper.assertTrue(recipe.id().equals(id), "锻造配方 ID 不符");
            var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
            helper.assertTrue(result.is(armor().get(i)) && result.getDamageValue() == 73
                    && result.getHoverName().getString().equals("保留名称"), "锻造应保留损伤与自定义名称");
        }
        helper.succeed();
    }

    private static LivingIncomingDamageEvent damageEvent(net.minecraft.world.entity.LivingEntity victim,
                                                         DamageSource source, float amount) {
        return new LivingIncomingDamageEvent(victim, new DamageContainer(source, amount));
    }

    private static void checkCraft(GameTestHelper helper, CraftingInput input, String path, Item expected) {
        var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input,
                helper.getLevel()).orElseThrow();
        helper.assertTrue(recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path)),
                "工作台配方 ID 不符: " + path);
        var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
        helper.assertTrue(result.is(expected) && result.getCount() == 1, "工作台配方产物不符: " + path);
    }
}
