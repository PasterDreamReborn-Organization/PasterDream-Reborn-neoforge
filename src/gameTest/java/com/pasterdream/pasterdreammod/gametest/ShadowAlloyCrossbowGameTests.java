package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.ShadowAlloyArrowEntity;
import com.pasterdream.pasterdreammod.world.item.shadowalloytool.ShadowAlloyCrossbowHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class ShadowAlloyCrossbowGameTests {

    @GameTest(template = "empty")
    public static void crossbowRegistrationAndRecipe(GameTestHelper helper) {
        ItemStack crossbow = ModItems.SHADOW_ALLOY_CROSSBOW.toStack();
        helper.assertTrue(crossbow.getMaxDamage() == 842, "暗影合金弩耐久应为 842");
        helper.assertTrue(crossbow.is(ItemTags.CROSSBOW_ENCHANTABLE), "暗影合金弩应在弩附魔标签中");
        helper.assertTrue(crossbow.is(ItemTags.DURABILITY_ENCHANTABLE), "暗影合金弩应在耐久附魔标签中");
        helper.assertTrue(crossbow.isPrimaryItemFor(
                helper.getLevel().registryAccess().holderOrThrow(Enchantments.POWER)), "暗影合金弩应可附魔力量");
        helper.assertTrue(crossbow.isPrimaryItemFor(
                helper.getLevel().registryAccess().holderOrThrow(Enchantments.INFINITY)), "暗影合金弩应可附魔无限");
        helper.assertTrue(BuiltInRegistries.MOB_EFFECT.getKey(ModEffects.VULNERABILITY.get())
                .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "vulnerability")),
                "易伤效果注册 ID 错误");
        helper.assertTrue(BuiltInRegistries.MOB_EFFECT.getKey(ModEffects.BIND.get())
                .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "bind")),
                "束缚效果注册 ID 错误");
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_alloy_crossbow")).isPresent(),
                "暗影合金弩配方未加载");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void crossbowEnergyAndProjectile(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        ItemStack crossbow = ModItems.SHADOW_ALLOY_CROSSBOW.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, crossbow);
        player.getInventory().add(new ItemStack(Items.ARROW, 2));
        energy.setMeltDreamEnergy(1.0);

        crossbow.getItem().releaseUsing(crossbow, helper.getLevel(), player, 0);
        helper.assertTrue(net.minecraft.world.item.CrossbowItem.isCharged(crossbow), "暗影合金弩应完成装填");
        helper.assertTrue(Math.abs(energy.getMeltDreamEnergy() - 1.0) < 0.0001, "装填不应消耗融梦能量");

        crossbow.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(Math.abs(energy.getMeltDreamEnergy() - 0.8) < 0.0001,
                "发射应消耗 0.2 融梦能量");
        var arrows = helper.getLevel().getEntitiesOfClass(ShadowAlloyArrowEntity.class,
                new AABB(player.blockPosition()).inflate(8), arrow -> arrow.getOwner() == player);
        helper.assertTrue(arrows.size() == 1, "能量充足时应射出暗影合金箭实体");
        helper.assertTrue(arrows.getFirst().getType() == ModEntities.SHADOW_ALLOY_ARROW.get(),
                "暗影合金箭实体类型错误");
        helper.assertTrue(arrows.getFirst().getPersistentData()
                .getBoolean(ShadowAlloyCrossbowHandler.PROJECTILE_TAG), "暗影飞矢应带被动标记");

        // 能量不足：照常发射普通箭，不触发被动
        energy.setMeltDreamEnergy(0.0);
        crossbow.getItem().releaseUsing(crossbow, helper.getLevel(), player, 0);
        crossbow.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        var allArrows = helper.getLevel().getEntitiesOfClass(AbstractArrow.class,
                new AABB(player.blockPosition()).inflate(8), arrow -> arrow.getOwner() == player);
        helper.assertTrue(allArrows.size() == 2, "能量不足时应照常射出第二支箭");
        helper.assertTrue(allArrows.stream().anyMatch(arrow -> arrow.getType() == net.minecraft.world.entity.EntityType.ARROW),
                "能量不足时应使用原版箭实体");
        helper.assertTrue(energy.getMeltDreamEnergy() == 0.0, "能量不足时不应扣成负数");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void crossbowInfinityWithoutAmmo(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        ItemStack crossbow = ModItems.SHADOW_ALLOY_CROSSBOW.toStack();
        crossbow.enchant(helper.getLevel().registryAccess().holderOrThrow(Enchantments.INFINITY), 1);
        player.setItemInHand(InteractionHand.MAIN_HAND, crossbow);
        energy.setMeltDreamEnergy(1.0);

        crossbow.getItem().releaseUsing(crossbow, helper.getLevel(), player, 0);
        helper.assertTrue(net.minecraft.world.item.CrossbowItem.isCharged(crossbow),
                "无限附魔下无箭也应完成装填");
        crossbow.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        var arrows = helper.getLevel().getEntitiesOfClass(AbstractArrow.class,
                new AABB(player.blockPosition()).inflate(8), arrow -> arrow.getOwner() == player);
        helper.assertTrue(arrows.size() == 1, "无限附魔应射出箭矢");
        helper.assertTrue(Math.abs(energy.getMeltDreamEnergy() - 0.8) < 0.0001,
                "无限只免箭矢，不免融梦能量");
        helper.succeed();
    }
}
