package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.event.ModMobDrops;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.effect.SculkArmorEffect;
import com.pasterdream.pasterdreammod.world.item.armoritem.SculkArmorItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.ArrayList;
import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class SculkArmorGameTests {
    private static List<Item> armor() {
        return List.of(ModItems.SCULK_HELMET.get(), ModItems.SCULK_CHESTPLATE.get(),
                ModItems.SCULK_LEGGINGS.get(), ModItems.SCULK_BOOTS.get());
    }

    @GameTest(template = "empty")
    public static void armorPropertiesAndSet(GameTestHelper helper) {
        int[] defense = {3, 8, 6, 3};
        int[] durability = {462, 672, 630, 546};
        var tags = List.of(ItemTags.HEAD_ARMOR, ItemTags.CHEST_ARMOR, ItemTags.LEG_ARMOR, ItemTags.FOOT_ARMOR);
        var slots = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (int i = 0; i < armor().size(); i++) {
            var item = (ArmorItem) armor().get(i);
            var stack = new ItemStack(item);
            helper.assertTrue(item.getDefense() == defense[i] && stack.getMaxDamage() == durability[i], "护甲防御/耐久不符");
            helper.assertTrue(item.getEnchantmentValue() == 9 && stack.is(tags.get(i)), "附魔值/护甲标签不符");
            helper.assertTrue(item.getToughness() == 3.5f && item.getMaterial().value().knockbackResistance() == 0.15f,
                    "护甲韧性/击退抗性不符");
            helper.assertTrue(item.isValidRepairItem(stack, ModItems.SCULK_HEART.toStack()), "回响之心应能修复装备");
            helper.assertTrue(stack.getRarity() == Rarity.UNCOMMON && stack.has(DataComponents.FIRE_RESISTANT),
                    "回响护甲应为罕见且防火");
            player.setItemSlot(slots.get(i), stack);
        }
        helper.assertTrue(SculkArmorItem.hasFullSet(player), "穿满四件应识别为整套");
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100));
        player.getInventory().tick();
        helper.assertTrue(player.hasEffect(ModEffects.SCULK_ARMOR), "头盔应施加 25 tick 幽匿回响");
        helper.assertFalse(player.hasEffect(MobEffects.BLINDNESS), "幽匿回响应移除失明");
        helper.assertTrue(player.getAttributeValue(Attributes.MAX_HEALTH) == 24.0, "幽匿回响应使最大生命值 +4");
        player.setItemSlot(EquipmentSlot.FEET, ModItems.TITANIUM_BOOTS.toStack());
        helper.assertFalse(SculkArmorItem.hasFullSet(player), "混搭钛金靴不应识别为整套");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void protectedEffectAndDarknessConversion(GameTestHelper helper) {
        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        var protection = new MobEffectInstance(ModEffects.SCULK_ARMOR, 25, 0);
        var event = new MobEffectEvent.Applicable(zombie, protection, null);
        event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        helper.assertTrue(event.getApplicationResult(), "受保护效果应越过 Applicable 拒绝");
        zombie.addEffect(protection);
        helper.assertTrue(zombie.getAttributeValue(Attributes.MAX_HEALTH) == 24.0, "幽匿回响应使最大生命值 +4");
        zombie.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200));
        helper.assertFalse(zombie.removeEffect(ModEffects.SCULK_ARMOR), "单独移除应被幽匿回响拒绝");
        helper.assertTrue(zombie.removeAllEffects(), "移除全部应移除普通效果");
        helper.assertTrue(zombie.hasEffect(ModEffects.SCULK_ARMOR), "移除全部应保留受保护效果");
        helper.assertFalse(zombie.hasEffect(MobEffects.MOVEMENT_SPEED), "移除全部应清除普通效果");

        zombie.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200));
        ((SculkArmorEffect) ModEffects.SCULK_ARMOR.get()).applyEffectTick(zombie, 0);
        helper.assertFalse(zombie.hasEffect(MobEffects.DARKNESS), "受到黑暗时应移除黑暗");
        var speed = zombie.getEffect(MobEffects.MOVEMENT_SPEED);
        var resistance = zombie.getEffect(MobEffects.DAMAGE_RESISTANCE);
        helper.assertTrue(speed != null && speed.getDuration() == 1200 && speed.getAmplifier() == 1,
                "黑暗转换应给予 1200 tick 移速 II");
        helper.assertTrue(resistance != null && resistance.getDuration() == 1200 && resistance.getAmplifier() == 1,
                "黑暗转换应给予 1200 tick 抗性 II");

        zombie.removeEffect(MobEffects.DAMAGE_RESISTANCE);
        zombie.setPos(zombie.getX(), -1.0, zombie.getZ());
        ((SculkArmorEffect) ModEffects.SCULK_ARMOR.get()).applyEffectTick(zombie, 0);
        var deepResistance = zombie.getEffect(MobEffects.DAMAGE_RESISTANCE);
        helper.assertTrue(deepResistance != null && deepResistance.getDuration() == 25
                        && deepResistance.getAmplifier() == 0,
                "身处 Y<0 应给予 25 tick 抗性 I");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void protectedEffectExpiresNaturally(GameTestHelper helper) {
        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        zombie.addEffect(new MobEffectInstance(ModEffects.SCULK_ARMOR, 5));
        helper.assertTrue(zombie.hasEffect(ModEffects.SCULK_ARMOR), "定时效果应被施加");
        helper.runAfterDelay(10, () -> {
            helper.assertFalse(zombie.hasEffect(ModEffects.SCULK_ARMOR), "效果自然到期不应被保护机制阻止");
            helper.assertTrue(zombie.getAttributeValue(Attributes.MAX_HEALTH) == 20.0, "效果到期应移除最大生命值加成");
            helper.succeed();
        });
    }

    @GameTest(template = "empty")
    public static void wardenIgnoresAndCannotHurtSetWearer(GameTestHelper helper) {
        var warden = helper.spawnWithNoFreeWill(EntityType.WARDEN, new BlockPos(1, 2, 1));
        var protectedPlayer = helper.makeMockPlayer(GameType.SURVIVAL);
        protectedPlayer.addEffect(new MobEffectInstance(ModEffects.SCULK_ARMOR, 100));
        warden.setTarget(protectedPlayer);
        warden.aiStep();
        helper.assertTrue(warden.getTarget() == null, "监守者应清除穿戴者的仇恨目标");
        helper.assertFalse(protectedPlayer.hurt(helper.getLevel().damageSources().mobAttack(warden), 5.0f),
                "幽匿回响应免疫监守者伤害");
        var normalPlayer = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(normalPlayer.hurt(helper.getLevel().damageSources().mobAttack(warden), 5.0f),
                "未穿戴套装应正常受到监守者伤害");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void wardenDropsSculkHeart(GameTestHelper helper) {
        var warden = helper.spawnWithNoFreeWill(EntityType.WARDEN, new BlockPos(1, 2, 1));
        var drops = new ArrayList<net.minecraft.world.entity.item.ItemEntity>();
        var event = new LivingDropsEvent(warden, helper.getLevel().damageSources().generic(), drops, true);
        ModMobDrops.onLivingDrops(event);
        helper.assertTrue(drops.size() == 1 && drops.get(0).getItem().is(ModItems.SCULK_HEART.get()),
                "监守者死亡应掉落回响之心");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void upgradeAndSmithingRecipes(GameTestHelper helper) {
        var empty = ItemStack.EMPTY;
        var echo = new ItemStack(Items.ECHO_SHARD);
        var vein = new ItemStack(Items.SCULK_VEIN);
        checkCraft(helper, CraftingInput.of(3, 3, List.of(empty, new ItemStack(Items.IRON_INGOT), empty,
                        echo, ModItems.SCULK_HEART.toStack(), echo, vein, echo, vein)),
                "sculk_upgrade", ModItems.SCULK_UPGRADE.get());

        var bases = List.of(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE,
                Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS);
        for (int i = 0; i < armor().size(); i++) {
            var base = new ItemStack(bases.get(i));
            base.set(DataComponents.CUSTOM_NAME, net.minecraft.network.chat.Component.literal("回响基底"));
            base.setDamageValue(73);
            var input = new SmithingRecipeInput(new ItemStack(Items.SCULK_VEIN), base,
                    ModItems.SCULK_UPGRADE.toStack());
            var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMITHING, input,
                    helper.getLevel()).orElseThrow();
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                    BuiltInRegistries.ITEM.getKey(armor().get(i)).getPath() + "_smithing");
            helper.assertTrue(recipe.id().equals(id), "锻造配方 ID 不符");
            var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
            helper.assertTrue(result.is(armor().get(i)) && result.getDamageValue() == 73
                    && result.getHoverName().getString().equals("回响基底"), "锻造应保留组件和损伤");
        }
        helper.succeed();
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
