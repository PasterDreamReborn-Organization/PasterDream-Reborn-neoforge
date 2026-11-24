package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEnchantment;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.enchantment.HeartDevourRepairHandler;
import com.pasterdream.pasterdreammod.world.enchantment.MeltDreamRepairHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class EnchantmentGameTests {
    @GameTest(template = "empty", batch = "enchantments")
    public static void enchantmentDefinitionsAndAttributes(GameTestHelper helper) {
        var registry = helper.getLevel().registryAccess();
        var swiftStrike = registry.holderOrThrow(ModEnchantment.SWIFT_STRIKE_ENCHANTMENT);
        var gatherDream = registry.holderOrThrow(ModEnchantment.DREAM_GATHERING_ENCHANTMENT);
        var mending = registry.holderOrThrow(Enchantments.MENDING);
        var meltRepair = registry.holderOrThrow(ModEnchantment.MELT_DREAM_REPAIR_ENCHANTMENT);
        var heartRepair = registry.holderOrThrow(ModEnchantment.HEART_DEVOUR_REPAIR_ENCHANTMENT);
        var sword = new ItemStack(Items.IRON_SWORD);
        var axe = new ItemStack(Items.IRON_AXE);
        helper.assertTrue(sword.isPrimaryItemFor(swiftStrike) && axe.supportsEnchantment(swiftStrike)
                        && !axe.isPrimaryItemFor(swiftStrike) && swiftStrike.is(EnchantmentTags.IN_ENCHANTING_TABLE),
                "疾风连击应能在附魔台附到剑上，斧通过铁砧附魔");
        helper.assertTrue(heartRepair.is(EnchantmentTags.CURSE) && meltRepair.is(EnchantmentTags.ON_RANDOM_LOOT)
                        && meltRepair.is(EnchantmentTags.TRADEABLE) && !meltRepair.is(EnchantmentTags.IN_ENCHANTING_TABLE)
                        && !Enchantment.areCompatible(mending, meltRepair) && !Enchantment.areCompatible(mending, heartRepair)
                        && !Enchantment.areCompatible(meltRepair, heartRepair),
                "宝藏与诅咒标签或三种修补的互斥配置错误");

        sword.enchant(swiftStrike, 3);
        axe.enchant(swiftStrike, 3);
        var helmet = new ItemStack(Items.IRON_HELMET);
        helmet.enchant(gatherDream, 6);
        helper.assertTrue(sword.getAttributeModifiers().modifiers().stream()
                        .anyMatch(entry -> entry.attribute().equals(Attributes.ATTACK_SPEED)
                                && entry.slot() == EquipmentSlotGroup.MAINHAND && Math.abs(entry.modifier().amount() - 0.18) < 1e-6),
                "疾风连击 III 应给剑主手增加 18% 攻速");
        helper.assertTrue(axe.getAttributeModifiers().modifiers().stream()
                        .anyMatch(entry -> entry.attribute().equals(Attributes.ATTACK_SPEED)
                                && entry.slot() == EquipmentSlotGroup.MAINHAND && Math.abs(entry.modifier().amount() - 0.12) < 1e-6),
                "疾风连击 III 应给斧主手增加 12% 攻速");
        helper.assertTrue(helmet.getAttributeModifiers().modifiers().stream()
                        .anyMatch(entry -> entry.attribute().equals(ModAttributes.MELT_DREAM_VARIABILITY)
                                && entry.slot() == EquipmentSlotGroup.HEAD && entry.modifier().amount() == 1.5),
                "聚梦 VI 应为头盔增加 1.5 融梦光环");
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "enchantments")
    public static void shelterUsesDamageAfterInvulnerabilityFrames(GameTestHelper helper) {
        var zombie = helper.spawn(EntityType.ZOMBIE, 1, 1, 1);
        var shelter = helper.getLevel().registryAccess().holderOrThrow(ModEnchantment.SHELTER_ENCHANTMENT);
        var helmet = new ItemStack(Items.IRON_HELMET);
        var boots = new ItemStack(Items.IRON_BOOTS);
        helmet.enchant(shelter, 3);
        boots.enchant(shelter, 3);
        zombie.setItemSlot(EquipmentSlot.HEAD, helmet);
        zombie.setItemSlot(EquipmentSlot.FEET, boots);
        var source = zombie.damageSources().magic();
        zombie.hurt(source, 10.0F);
        helper.assertTrue(Math.abs(zombie.getHealth() - 11.2F) < 1e-4, "两件庇护 III 应叠加 12% 减伤");
        helper.assertTrue(!zombie.hurt(source, 9.0F), "同一无敌帧内较低伤害应被忽略");
        zombie.hurt(source, 14.0F);
        helper.assertTrue(Math.abs(zombie.getHealth() - 7.68F) < 1e-4, "无敌帧内应对伤害差额结算庇护");
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "enchantments")
    public static void repairsChargeTheirOwnResource(GameTestHelper helper) {
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "enchantment-repair"));
        player.tickCount = 10;
        var registry = helper.getLevel().registryAccess();
        var meltRepair = registry.holderOrThrow(ModEnchantment.MELT_DREAM_REPAIR_ENCHANTMENT);
        var heartRepair = registry.holderOrThrow(ModEnchantment.HEART_DEVOUR_REPAIR_ENCHANTMENT);
        var sword = new ItemStack(Items.IRON_SWORD);
        sword.enchant(meltRepair, 1);
        sword.setDamageValue(2);
        player.getInventory().items.set(0, sword);
        var shield = new ItemStack(Items.SHIELD);
        shield.enchant(heartRepair, 1);
        shield.setDamageValue(2);
        player.setItemSlot(EquipmentSlot.OFFHAND, shield);
        var helmet = ModItems.MELT_DREAM_HELMET.toStack();
        helmet.enchant(meltRepair, 1);
        helmet.setDamageValue(2);
        player.setItemSlot(EquipmentSlot.HEAD, helmet);

        var san = player.getData(ModAttachments.SAN);
        san.setIsSanEnable(true);
        san.setSanValue(0.15);
        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        energy.setMeltDreamEnergy(1.0);
        energy.setIsConsumeDoubled(true);
        MeltDreamRepairHandler.onPlayerTick(new PlayerTickEvent.Post(player));
        HeartDevourRepairHandler.onPlayerTick(new PlayerTickEvent.Post(player));
        helper.assertTrue(sword.getDamageValue() == 1 && Math.abs(energy.getMeltDreamEnergy() - 0.98) < 1e-6
                        && helmet.getDamageValue() == 2,
                "融梦修补应按耗能倍率扣费，融梦水晶护甲由自身被动处理");
        helper.assertTrue(shield.getDamageValue() == 1 && Math.abs(san.getSanValue() - 0.05) < 1e-6,
                "噬心修补应修复副手盾并扣除 0.1 理智");
        HeartDevourRepairHandler.onPlayerTick(new PlayerTickEvent.Post(player));
        helper.assertTrue(shield.getDamageValue() == 1, "理智不足应停止修复");
        san.setIsSanEnable(false);
        float health = player.getHealth();
        HeartDevourRepairHandler.onPlayerTick(new PlayerTickEvent.Post(player));
        helper.assertTrue(shield.getDamageValue() == 0 && player.getHealth() == health - 1.0F,
                "关闭理智系统时应扣除 1 点生命");
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "enchantments")
    public static void heartDevourRepairMakesChaoticArrowsInfinite(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var bow = new ItemStack(Items.BOW);
        var ammunition = ModItems.CHAOTIC_ARROW.toStack(3);
        var arrowItem = (ArrowItem) ammunition.getItem();
        ItemStack consumed = AmmoTestBow.load(bow, ammunition, player);
        helper.assertTrue(ammunition.getCount() == 2
                        && arrowItem.createArrow(helper.getLevel(), consumed, player, bow).pickup == AbstractArrow.Pickup.ALLOWED,
                "无噬心修补时混沌箭应正常消耗并可回收");

        bow.enchant(helper.getLevel().registryAccess().holderOrThrow(ModEnchantment.HEART_DEVOUR_REPAIR_ENCHANTMENT), 1);
        ItemStack free = AmmoTestBow.load(bow, ammunition, player);
        helper.assertTrue(ammunition.getCount() == 2 && free.has(DataComponents.INTANGIBLE_PROJECTILE)
                        && arrowItem.createArrow(helper.getLevel(), free, player, bow).pickup == AbstractArrow.Pickup.CREATIVE_ONLY,
                "噬心修补应保留混沌箭弹药且禁止生存模式回收免费箭");
        var ordinary = new ItemStack(Items.ARROW, 3);
        AmmoTestBow.load(bow, ordinary, player);
        helper.assertTrue(ordinary.getCount() == 2, "噬心修补不应让普通箭无限");
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "enchantments")
    public static void meltRepairBookUsesClampedLibrarianPrice(GameTestHelper helper) {
        var trader = helper.makeMockPlayer(GameType.SURVIVAL);
        var listing = new VillagerTrades.EnchantBookForEmeralds(1, EnchantmentTags.TRADEABLE);
        var random = RandomSource.create(42L);
        var meltRepair = helper.getLevel().registryAccess().holderOrThrow(ModEnchantment.MELT_DREAM_REPAIR_ENCHANTMENT);
        for (int i = 0; i < 512; ++i) {
            var offer = listing.getOffer(trader, random);
            if (EnchantmentHelper.getEnchantmentsForCrafting(offer.getResult()).getLevel(meltRepair) == 0) continue;

            int price = offer.getBaseCostA().getCount();
            helper.assertTrue(price >= 34 && price <= 64 && offer.getCostB().is(Items.BOOK),
                    "融梦修补附魔书的基础价格应为 34–64 绿宝石加一本书");
            helper.succeed();
            return;
        }
        helper.fail("图书管理员的交易未抽到融梦修补附魔书");
    }

    private static class AmmoTestBow extends BowItem {
        private AmmoTestBow(Properties properties) {
            super(properties);
        }

        private static ItemStack load(ItemStack bow, ItemStack ammo, LivingEntity shooter) {
            return useAmmo(bow, ammo, shooter, false);
        }
    }
}
