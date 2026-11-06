package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.FluffyWindArrowEntity;
import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorHandler;
import com.pasterdream.pasterdreammod.world.item.armoritem.WindRunnerCloakHandler;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class WindEquipmentGameTests {
    @GameTest(template = "empty")
    public static void windArmorAndRecipes(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var armor = List.of(ModItems.FLUFFY_WIND_ALLOY_HELMET.get(), ModItems.FLUFFY_WIND_ALLOY_CHESTPLATE.get(),
                ModItems.FLUFFY_WIND_ALLOY_LEGGINGS.get(), ModItems.FLUFFY_WIND_ALLOY_BOOTS.get());
        var slots = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
        var tags = List.of(ItemTags.HEAD_ARMOR, ItemTags.CHEST_ARMOR, ItemTags.LEG_ARMOR, ItemTags.FOOT_ARMOR);
        for (int i = 0; i < 4; i++) {
            ItemStack stack = new ItemStack(armor.get(i));
            helper.assertTrue(stack.is(tags.get(i)) && stack.getMaxDamage() ==
                    switch (slots.get(i)) { case HEAD -> 11 * 46; case CHEST -> 16 * 46;
                        case LEGS -> 15 * 46; case FEET -> 13 * 46; default -> 0; }, "萦风护甲耐久或标签错误");
            player.setItemSlot(slots.get(i), stack);
        }
        helper.assertTrue(FluffyWindAlloyArmorItem.hasFullSet(player), "萦风护甲满套识别错误");
        player.setItemSlot(EquipmentSlot.CHEST, ModItems.WIND_RUNNER_CLOAK.toStack());
        helper.assertTrue(!FluffyWindAlloyArmorItem.hasFullSet(player) && WindRunnerCloakHandler.isWearing(player),
                "卸下一件后满套应失效，披风应单独识别");

        player.setItemSlot(EquipmentSlot.CHEST, ModItems.FLUFFY_WIND_ALLOY_CHESTPLATE.toStack());
        player.getPersistentData().putBoolean(FluffyWindAlloyArmorHandler.NBT_HOVER_JUMP, true);
        player.setOnGround(false);
        player.setDeltaMovement(new Vec3(0.2, -0.3, 0.1));
        player.getFoodData().setFoodLevel(20);
        FluffyWindAlloyArmorHandler.applySlowFall(player, true);
        helper.assertTrue(Math.abs(player.getDeltaMovement().y + 0.23) < 0.0001
                        && player.getDeltaMovement().x == 0.2 && player.getDeltaMovement().z == 0.1,
                "萦风满套缓降应只补偿纵向重力");

        for (String id : List.of("fluffy_wind_bow", "wind_runner_cloak", "pulse_wind_runner_crystal",
                "weapon_workshop_crafting_table/fluffy_wind_alloy_helmet",
                "weapon_workshop_crafting_table/fluffy_wind_alloy_chestplate",
                "weapon_workshop_crafting_table/fluffy_wind_alloy_leggings",
                "weapon_workshop_crafting_table/fluffy_wind_alloy_boots")) {
            helper.assertTrue(helper.getLevel().getRecipeManager().byKey(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, id)).isPresent(),
                    "风装备配方未加载: " + id);
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void windBowEnergyAndProjectile(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        var bow = ModItems.FLUFFY_WIND_BOW.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, bow);
        player.getInventory().add(new ItemStack(Items.ARROW, 2));
        energy.setMeltDreamEnergy(1.0);
        bow.getItem().releaseUsing(bow, helper.getLevel(), player, bow.getUseDuration(player) - 20);
        helper.assertTrue(Math.abs(energy.getMeltDreamEnergy() - 0.8) < 0.0001,
                "萦风强化箭应消耗 0.2 融梦能量");
        var arrows = helper.getLevel().getEntitiesOfClass(FluffyWindArrowEntity.class,
                new AABB(player.blockPosition()).inflate(8));
        helper.assertTrue(arrows.size() == 1 && arrows.getFirst().getType() == ModEntities.FLUFFY_WIND_ARROW.get()
                && arrows.getFirst().isNoGravity(), "萦风弓应射出无重力强化箭");
        energy.setMeltDreamEnergy(0.0);
        bow.getItem().releaseUsing(bow, helper.getLevel(), player, bow.getUseDuration(player) - 20);
        var allArrows = helper.getLevel().getEntitiesOfClass(Arrow.class,
                new AABB(player.blockPosition()).inflate(8));
        helper.assertTrue(allArrows.size() == 2 && allArrows.stream().anyMatch(a -> a.getType() == net.minecraft.world.entity.EntityType.ARROW)
                && energy.getMeltDreamEnergy() == 0.0, "能量不足应射出普通箭且不扣能量");
        helper.succeed();
    }
}
