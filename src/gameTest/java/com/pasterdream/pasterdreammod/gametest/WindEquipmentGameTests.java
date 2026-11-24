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
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.util.Unit;
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
                new AABB(player.blockPosition()).inflate(8), arrow -> arrow.getOwner() == player);
        helper.assertTrue(arrows.size() == 1 && arrows.getFirst().getType() == ModEntities.FLUFFY_WIND_ARROW.get()
                && arrows.getFirst().isNoGravity(), "萦风弓应射出无重力强化箭");
        energy.setMeltDreamEnergy(0.0);
        bow.getItem().releaseUsing(bow, helper.getLevel(), player, bow.getUseDuration(player) - 20);
        var allArrows = helper.getLevel().getEntitiesOfClass(Arrow.class,
                new AABB(player.blockPosition()).inflate(8), arrow -> arrow.getOwner() == player);
        helper.assertTrue(allArrows.size() == 2 && allArrows.stream().anyMatch(a -> a.getType() == net.minecraft.world.entity.EntityType.ARROW)
                && energy.getMeltDreamEnergy() == 0.0, "能量不足应射出普通箭且不扣能量");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void windBowEmpoweredSpectralArrow(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        var bow = ModItems.FLUFFY_WIND_BOW.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, bow);
        player.getInventory().add(new ItemStack(Items.SPECTRAL_ARROW, 1));
        energy.setMeltDreamEnergy(1.0);
        bow.getItem().releaseUsing(bow, helper.getLevel(), player, bow.getUseDuration(player) - 20);
        helper.assertTrue(Math.abs(energy.getMeltDreamEnergy() - 0.8) < 0.0001,
                "强化光灵箭应消耗 0.2 融梦能量");
        var arrows = helper.getLevel().getEntitiesOfClass(FluffyWindArrowEntity.class,
                new AABB(player.blockPosition()).inflate(8), arrow -> arrow.getOwner() == player);
        helper.assertTrue(arrows.size() == 1, "能量充足时强化光灵箭应使用萦风箭实体");
        FluffyWindArrowEntity arrow = arrows.getFirst();
        helper.assertTrue(arrow.getAmmoItem().is(Items.SPECTRAL_ARROW), "强化箭应保留光灵箭弹药");
        helper.assertTrue(arrow.getPickupItemStackOrigin().is(Items.SPECTRAL_ARROW),
                "强化光灵箭应保留光灵箭拾取物");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void windBowUnconsumedAmmoArrowStaysOutOfInventory(GameTestHelper helper) {
        // 创造模式 / 无限附魔时原版不会真正消耗弹药，会给弹药打上 intangible_projectile 标记，
        // 并把箭矢设为不可拾取；强化箭默认允许拾取，这里必须保持原版语义：箭矢不进入玩家背包。
        var shooter = helper.makeMockPlayer(GameType.CREATIVE);
        shooter.getAbilities().instabuild = true;
        shooter.getData(ModAttachments.MELT_DREAM_ENERGY).setMeltDreamEnergy(1.0);
        ItemStack bow = ModItems.FLUFFY_WIND_BOW.toStack();
        shooter.setItemInHand(InteractionHand.MAIN_HAND, bow);
        shooter.getInventory().add(new ItemStack(Items.ARROW, 1));
        bow.getItem().releaseUsing(bow, helper.getLevel(), shooter, bow.getUseDuration(shooter) - 20);
        var shot = helper.getLevel().getEntitiesOfClass(FluffyWindArrowEntity.class,
                new AABB(shooter.blockPosition()).inflate(8), arrow -> arrow.getOwner() == shooter);
        helper.assertTrue(shot.size() == 1, "创造模式应射出一支萦风强化箭");
        FluffyWindArrowEntity fired = shot.getFirst();
        helper.assertTrue(fired.getAmmoItem().has(DataComponents.INTANGIBLE_PROJECTILE),
                "创造模式未消耗的弹药应带有 intangible_projectile 标记");

        fired.setNoPhysics(true);
        fired.playerTouch(shooter);
        helper.assertTrue(countArrows(shooter) == 1,
                "创造模式回收的强化箭不应进入物品栏，实际箭矢总数 " + countArrows(shooter));
        helper.assertTrue(!hasIntangibleArrow(shooter), "物品栏中不应出现带 intangible_projectile 标记的箭矢");

        // 无限附魔（生存玩家）：原版箭矢不可拾取，留在原地
        var picker = helper.makeMockPlayer(GameType.SURVIVAL);
        picker.getInventory().add(new ItemStack(Items.ARROW, 1));
        FluffyWindArrowEntity infiniteArrow = new FluffyWindArrowEntity(helper.getLevel(), picker,
                intangibleArrow(), bow);
        helper.getLevel().addFreshEntity(infiniteArrow);
        infiniteArrow.setNoPhysics(true);
        infiniteArrow.playerTouch(picker);
        helper.assertTrue(countArrows(picker) == 1 && !infiniteArrow.isRemoved(),
                "无限附魔射出的强化箭不应被生存玩家拾取");

        // 正常消耗弹药的箭矢仍可回收，并与背包里的普通箭合并成一堆
        FluffyWindArrowEntity recovered = new FluffyWindArrowEntity(helper.getLevel(), picker,
                new ItemStack(Items.ARROW), bow);
        helper.getLevel().addFreshEntity(recovered);
        recovered.setNoPhysics(true);
        recovered.playerTouch(picker);
        helper.assertTrue(countArrowStacks(picker) == 1 && countArrows(picker) == 2,
                "正常回收的强化箭应与普通箭堆叠，实际栈数 " + countArrowStacks(picker)
                        + "、总数 " + countArrows(picker));
        helper.succeed();
    }

    private static ItemStack intangibleArrow() {
        ItemStack arrow = new ItemStack(Items.ARROW);
        arrow.set(DataComponents.INTANGIBLE_PROJECTILE, Unit.INSTANCE);
        return arrow;
    }

    private static boolean hasIntangibleArrow(Player player) {
        return player.getInventory().hasAnyMatching(
                stack -> stack.is(Items.ARROW) && stack.has(DataComponents.INTANGIBLE_PROJECTILE));
    }

    private static int countArrows(Player player) {
        int count = 0;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(Items.ARROW)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private static int countArrowStacks(Player player) {
        int stacks = 0;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            if (player.getInventory().getItem(slot).is(Items.ARROW)) {
                stacks++;
            }
        }
        return stacks;
    }
}
