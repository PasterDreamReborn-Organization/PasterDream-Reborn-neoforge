package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableRecipe;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.pasterdream.pasterdreammod.world.item.armoritem.qym.QymCatEarsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DreamGearGameTests {

    private static List<Item> shadowArmor() {
        return List.of(ModItems.SHADOW_ALLOY_HELMET.get(), ModItems.SHADOW_ALLOY_CHESTPLATE.get(),
                ModItems.SHADOW_ALLOY_LEGGINGS.get(), ModItems.SHADOW_ALLOY_BOOTS.get());
    }

    private static List<Item> qymArmor() {
        return List.of(ModItems.QYM_CAT_EARS.get(), ModItems.QYM_WIND_SHIRT.get(),
                ModItems.QYM_SWAYING_SKIRT.get(), ModItems.QYM_CLOUD_BOOTS.get());
    }

    @GameTest(template = "empty")
    public static void shadowAlloyArmorProperties(GameTestHelper helper) {
        int[] defense = {4, 10, 8, 4};
        int[] durability = {517, 752, 705, 611};
        var tags = List.of(ItemTags.HEAD_ARMOR, ItemTags.CHEST_ARMOR, ItemTags.LEG_ARMOR, ItemTags.FOOT_ARMOR);
        var slots = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (int i = 0; i < shadowArmor().size(); i++) {
            var item = (ArmorItem) shadowArmor().get(i);
            var stack = new ItemStack(item);
            helper.assertTrue(item.getDefense() == defense[i] && stack.getMaxDamage() == durability[i],
                    "暗影合金护甲防御/耐久不符");
            helper.assertTrue(item.getEnchantmentValue() == 20 && stack.is(tags.get(i)), "附魔值/护甲标签不符");
            helper.assertTrue(item.getToughness() == 4.0f && item.getMaterial().value().knockbackResistance() == 0.15f,
                    "护甲韧性/击退抗性不符");
            helper.assertTrue(item.isValidRepairItem(stack, ModItems.SHADOW_ALLOY_INGOT.toStack()),
                    "暗影合金锭应能修复装备");
            helper.assertTrue(stack.getRarity() == Rarity.EPIC && stack.has(DataComponents.FIRE_RESISTANT),
                    "暗影合金护甲应为史诗且防火");
            player.setItemSlot(slots.get(i), stack);
        }
        helper.assertTrue(com.pasterdream.pasterdreammod.world.item.armoritem.ShadowAlloyArmorItem.hasFullSet(player),
                "穿满四件应识别为整套");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void qymArmorPropertiesAndCurios(GameTestHelper helper) {
        int[] defense = {2, 10, 10, 10};
        for (int i = 0; i < qymArmor().size(); i++) {
            var item = (ArmorItem) qymArmor().get(i);
            var stack = new ItemStack(item);
            helper.assertTrue(item.getDefense() == defense[i] && stack.getMaxDamage() == 0,
                    "琴雨梦护甲防御/不可损坏不符");
            helper.assertTrue(item.getEnchantmentValue() == 99 && item.getToughness() == 10.0f,
                    "琴雨梦护甲附魔值/韧性不符");
            helper.assertTrue(item.getMaterial().value().knockbackResistance() == 1.0f,
                    "琴雨梦护甲击退抗性不符");
            helper.assertTrue(stack.getRarity() == ModRarities.miracle() && stack.has(DataComponents.FIRE_RESISTANT),
                    "琴雨梦护甲应为奇迹品质且防火");
        }
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemSlot(EquipmentSlot.HEAD, ModItems.QYM_CAT_EARS.toStack());
        player.setItemSlot(EquipmentSlot.CHEST, ModItems.QYM_WIND_SHIRT.toStack());
        player.setItemSlot(EquipmentSlot.LEGS, ModItems.QYM_SWAYING_SKIRT.toStack());
        player.setItemSlot(EquipmentSlot.FEET, ModItems.QYM_CLOUD_BOOTS.toStack());
        helper.assertTrue(QymCatEarsItem.hasFullSet(player), "穿满琴雨梦四件应识别为整套");

        var curioTag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", "curio"));
        helper.assertTrue(ModItems.PAPER_PLANE.toStack().is(curioTag)
                        && ModItems.MOSS_PHANTOM_MEMBRANE.toStack().is(curioTag)
                        && ModItems.LIGHT_MOSS_PHANTOM_MEMBRANE.toStack().is(curioTag),
                "纸飞机与幻翼膜应加入 curio 饰品标签");
        helper.assertTrue(ModEffects.DARK_WATCH.isBound() && ModEffects.DREAM_HARP_OF_WANDERER.isBound(),
                "黑暗守望与竖琴效果应已注册");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void gearRecipes(GameTestHelper helper) {
        var manager = helper.getLevel().getRecipeManager();
        for (Item armor : shadowArmor()) {
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                    "weapon_workshop_crafting_table/" + net.minecraft.core.registries.BuiltInRegistries.ITEM
                            .getKey(armor).getPath());
            var recipe = manager.byKey(id).orElseThrow();
            helper.assertTrue(recipe.value() instanceof WeaponWorkshopCraftingTableRecipe workshop
                            && workshop.getOutputItemIngredients().getItem() == armor,
                    "暗影合金护甲武器工坊配方错误: " + id);
        }

        var windVaneInput = CraftingInput.of(3, 3, List.of(
                ItemStack.EMPTY, ModItems.CONGEAL_WIND_IRON_INGOT.toStack(), ItemStack.EMPTY,
                ModItems.CONGEAL_WIND_IRON_INGOT.toStack(), ModItems.CONGEAL_WIND_IRON_BARS.toStack(),
                ModItems.CONGEAL_WIND_IRON_INGOT.toStack(),
                ItemStack.EMPTY, ModItems.CONGEAL_WIND_IRON_BARS.toStack(), ItemStack.EMPTY));
        var windVane = manager.getRecipeFor(RecipeType.CRAFTING, windVaneInput, helper.getLevel()).orElseThrow();
        helper.assertTrue(windVane.value().assemble(windVaneInput, helper.getLevel().registryAccess())
                        .is(ModItems.WIND_VANE.get()),
                "风向标配方错误");

        var membraneInput = CraftingInput.of(2, 2, List.of(
                new ItemStack(Items.PHANTOM_MEMBRANE), new ItemStack(Items.MOSS_BLOCK),
                ModItems.JUNGLE_SPORE.toStack(), ModItems.GLASS_JAR_OF_YEAST.toStack()));
        var membrane = manager.getRecipeFor(RecipeType.CRAFTING, membraneInput, helper.getLevel()).orElseThrow();
        helper.assertTrue(membrane.value().assemble(membraneInput, helper.getLevel().registryAccess())
                        .is(ModItems.MOSS_PHANTOM_MEMBRANE.get()),
                "光合幻翼膜配方错误");

        var lightInput = CraftingInput.of(3, 2, List.of(
                ModItems.MOSS_PHANTOM_MEMBRANE.toStack(), ModItems.LIGHT_ORGAN.toStack(), ItemStack.EMPTY,
                ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.toStack(), ModItems.LIGHT_BALL.toStack(),
                new ItemStack(Items.VINE)));
        var lightMembrane = manager.getRecipeFor(RecipeType.CRAFTING, lightInput, helper.getLevel()).orElseThrow();
        helper.assertTrue(lightMembrane.value().assemble(lightInput, helper.getLevel().registryAccess())
                        .is(ModItems.LIGHT_MOSS_PHANTOM_MEMBRANE.get()),
                "萤火光合幻翼膜配方错误");
        helper.succeed();
    }

    private static void equipQymSet(Player player) {
        player.setItemSlot(EquipmentSlot.HEAD, ModItems.QYM_CAT_EARS.toStack());
        player.setItemSlot(EquipmentSlot.CHEST, ModItems.QYM_WIND_SHIRT.toStack());
        player.setItemSlot(EquipmentSlot.LEGS, ModItems.QYM_SWAYING_SKIRT.toStack());
        player.setItemSlot(EquipmentSlot.FEET, ModItems.QYM_CLOUD_BOOTS.toStack());
    }

    private static Player qymAttacker(GameTestHelper helper) {
        var attacker = helper.makeMockPlayer(GameType.SURVIVAL);
        equipQymSet(attacker);
        // 让 20tick 冷却处于就绪状态，避免测试开始时世界时间小于冷却值。
        attacker.getPersistentData().putLong("pasterdream.qym_magic_last",
                helper.getLevel().getGameTime() - 20);
        return attacker;
    }

    private static Mob spawnArmorlessZombie(GameTestHelper helper) {
        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        // 僵尸自带 2 点护甲，会按护甲公式减免主攻击；清零以便断言精确数值。
        zombie.getAttribute(Attributes.ARMOR).setBaseValue(0.0);
        zombie.setHealth(zombie.getMaxHealth());
        return zombie;
    }

    @GameTest(template = "empty")
    public static void qymMagicBonusDamage(GameTestHelper helper) {
        var attacker = qymAttacker(helper);
        var zombie = spawnArmorlessZombie(helper);

        helper.assertTrue(zombie.hurt(helper.getLevel().damageSources().playerAttack(attacker), 4.0F),
                "主攻击应命中僵尸");
        // 20 - 4（主攻击）- 1（20 点生命值的 5%）= 15；主攻击的结算不应被附伤吃掉。
        helper.assertTrue(Math.abs(zombie.getHealth() - 15.0F) < 1.0E-4F,
                "附伤应额外造成目标当前生命值5%的魔法伤害，实际生命值: " + zombie.getHealth());

        zombie.invulnerableTime = 0;
        zombie.lastHurt = 0.0F;
        helper.assertTrue(zombie.hurt(helper.getLevel().damageSources().playerAttack(attacker), 4.0F),
                "冷却内的第二击应命中");
        helper.assertTrue(Math.abs(zombie.getHealth() - 11.0F) < 1.0E-4F,
                "20tick 冷却内不应再次附加魔法伤害，实际生命值: " + zombie.getHealth());
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void qymMagicBonusAppliesMultiplierOnce(GameTestHelper helper) {
        var attacker = qymAttacker(helper);
        var magicRate = attacker.getAttribute(ModAttributes.MAGIC_DAMAGE_RATE);
        helper.assertTrue(magicRate != null, "玩家应带有魔法伤害倍率属性");
        magicRate.setBaseValue(2.0);

        var zombie = spawnArmorlessZombie(helper);
        helper.assertTrue(zombie.hurt(helper.getLevel().damageSources().playerAttack(attacker), 4.0F),
                "主攻击应命中僵尸");
        // 20 - 4 - 1×2（倍率只应生效一次）= 14；若被平方则为 12。
        helper.assertTrue(Math.abs(zombie.getHealth() - 14.0F) < 1.0E-4F,
                "魔法伤害倍率应只应用一次，实际生命值: " + zombie.getHealth());
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void qymMagicBonusRequiresFullSet(GameTestHelper helper) {
        var attacker = helper.makeMockPlayer(GameType.SURVIVAL);
        attacker.getPersistentData().putLong("pasterdream.qym_magic_last",
                helper.getLevel().getGameTime() - 20);

        var zombie = spawnArmorlessZombie(helper);
        helper.assertTrue(zombie.hurt(helper.getLevel().damageSources().playerAttack(attacker), 4.0F),
                "主攻击应命中僵尸");
        helper.assertTrue(Math.abs(zombie.getHealth() - 16.0F) < 1.0E-4F,
                "未穿满琴雨梦套装时不应附加魔法伤害，实际生命值: " + zombie.getHealth());
        helper.succeed();
    }
}
