package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.pasterdream.pasterdreammod.world.entity.WindAlloyLightningEntity;
import com.pasterdream.pasterdreammod.world.entity.WindThunderSpearEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class WindGearGameTests {

    private static final String MODE_TAG = "pasterdream:fluffy_wind_alloy_mode";

    @GameTest(template = "empty")
    public static void windGearPropertiesAndTags(GameTestHelper helper) {
        var congealTools = List.of(ModItems.CONGEAL_WIND_IRON_SWORD.get(), ModItems.CONGEAL_WIND_IRON_PICKAXE.get(),
                ModItems.CONGEAL_WIND_IRON_AXE.get(), ModItems.CONGEAL_WIND_IRON_SHOVEL.get(),
                ModItems.CONGEAL_WIND_IRON_HOE.get());
        var fluffyTools = List.of(ModItems.FLUFFY_WIND_ALLOY_SWORD.get(), ModItems.FLUFFY_WIND_ALLOY_PICKAXE.get(),
                ModItems.FLUFFY_WIND_ALLOY_AXE.get(), ModItems.FLUFFY_WIND_ALLOY_SHOVEL.get(),
                ModItems.FLUFFY_WIND_ALLOY_HOE.get());
        var tags = List.of(ItemTags.SWORDS, ItemTags.PICKAXES, ItemTags.AXES, ItemTags.SHOVELS, ItemTags.HOES);
        double[] congealDamage = {5.5, 3.5, 8.0, 4.0, 0.5};
        double[] fluffyDamage = {8.0, 5.0, 9.5, 5.5, 1.0};
        for (int i = 0; i < congealTools.size(); i++) {
            var congeal = new ItemStack(congealTools.get(i));
            var fluffy = new ItemStack(fluffyTools.get(i));
            helper.assertTrue(congeal.getMaxDamage() == 750 && fluffy.getMaxDamage() == 1500,
                    "凝风铁/萦风合金工具耐久错误");
            helper.assertTrue(congeal.is(tags.get(i)) && fluffy.is(tags.get(i)), "风之旅途工具标签错误");
            helper.assertTrue(attackDamage(congeal) == congealDamage[i] && attackDamage(fluffy) == fluffyDamage[i],
                    "风之旅途工具攻击数值错误 " + i);
        }

        var spear = new ItemStack(ModItems.WINDWREATHED_THUNDER_SPEAR.get());
        helper.assertTrue(spear.getMaxDamage() == 2011 && spear.is(ItemTags.SWORDS)
                && spear.is(ItemTags.TRIDENT_ENCHANTABLE), "萦风雷矛耐久/标签错误");
        helper.assertTrue(attackDamage(spear) == 11.0 && spear.is(ModItemTags.SKILL_COOLDOWN),
                "萦风雷矛攻击或战技冷却标签错误");
        boolean reach = spear.get(DataComponents.ATTRIBUTE_MODIFIERS).modifiers().stream()
                .anyMatch(entry -> entry.attribute().equals(Attributes.ENTITY_INTERACTION_RANGE)
                        && entry.modifier().amount() == 2.0);
        helper.assertTrue(reach, "萦风雷矛应提供 +2 触及距离");
        helper.assertTrue(spear.getRarity() == ModRarities.legendary()
                        && "pasterdream:legendary".equals(ModRarities.legendary().getSerializedName())
                        && ModRarities.tierOf(ModRarities.legendary()) == 7,
                "萦风雷矛应使用传说品质，枚举扩展应包含全部自定义品质");
        helper.assertTrue(ModItems.FLUFFY_WIND_ALLOY_SWORD.toStack().is(ModItemTags.SKILL_COOLDOWN)
                && ModItems.FLUFFY_WIND_ALLOY_HOE.toStack().is(ModItemTags.SKILL_COOLDOWN),
                "萦风剑/锄应加入战技冷却标签");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void windSwordModeAndEntities(GameTestHelper helper) {
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "wind-gear-test"));
        player.setItemInHand(InteractionHand.MAIN_HAND, ModItems.FLUFFY_WIND_ALLOY_SWORD.toStack());
        player.setShiftKeyDown(true);
        player.getMainHandItem().getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue("thunder".equals(mode(player.getMainHandItem())), "萦风剑首次切换应进入雷模式");
        player.getMainHandItem().getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue("wind".equals(mode(player.getMainHandItem())), "萦风剑再次切换应回到风模式");

        var lightning = ModEntities.WIND_ALLOY_LIGHTNING.get().create(helper.getLevel());
        helper.assertTrue(lightning instanceof WindAlloyLightningEntity, "萦风落雷实体注册错误");
        var spear = new WindThunderSpearEntity(helper.getLevel(), player,
                ModItems.WINDWREATHED_THUNDER_SPEAR.toStack());
        helper.assertTrue(spear.getType() == ModEntities.WIND_THUNDER_SPEAR.get(), "萦风雷矛弹射物注册错误");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void windGearRecipes(GameTestHelper helper) {
        var swordInput = CraftingInput.of(1, 3, List.of(
                new ItemStack(ModItems.CONGEAL_WIND_IRON_INGOT.get()),
                new ItemStack(ModItems.CONGEAL_WIND_IRON_INGOT.get()),
                new ItemStack(ModItems.BLACK_STICK.get())));
        var swordRecipe = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, swordInput, helper.getLevel()).orElseThrow();
        helper.assertTrue(swordRecipe.id().equals(ResourceLocation.fromNamespaceAndPath(
                        PasterDreamMod.MOD_ID, "congeal_wind_iron_sword_direct"))
                        && swordRecipe.value().assemble(swordInput, helper.getLevel().registryAccess())
                        .is(ModItems.CONGEAL_WIND_IRON_SWORD.get()),
                "凝风铁剑直接配方错误");

        var alloyInput = CraftingInput.of(3, 3, List.of(
                new ItemStack(ModItems.CONGEAL_WIND.get()), new ItemStack(ModItems.TITANIUM_INGOT.get()),
                new ItemStack(ModItems.CONGEAL_WIND.get()),
                new ItemStack(ModItems.CONGEAL_WIND_IRON_INGOT.get()),
                new ItemStack(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get()),
                new ItemStack(ModItems.CONGEAL_WIND_IRON_INGOT.get()),
                new ItemStack(ModItems.CONGEAL_WIND.get()), new ItemStack(ModItems.TITANIUM_INGOT.get()),
                new ItemStack(ModItems.CONGEAL_WIND.get())));
        var alloyRecipe = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, alloyInput, helper.getLevel()).orElseThrow();
        var alloyResult = alloyRecipe.value().assemble(alloyInput, helper.getLevel().registryAccess());
        helper.assertTrue(alloyRecipe.id().equals(ResourceLocation.fromNamespaceAndPath(
                        PasterDreamMod.MOD_ID, "fluffy_wind_alloy_ingot"))
                        && alloyResult.is(ModItems.FLUFFY_WIND_ALLOY_INGOT.get()) && alloyResult.getCount() == 2,
                "萦风合金锭配方错误");

        var smithingInput = new SmithingRecipeInput(
                ModItems.CONGEAL_WIND.toStack(),
                ModItems.CONGEAL_WIND_IRON_SWORD.toStack(),
                ModItems.FLUFFY_WIND_ALLOY_UPGRADE.toStack());
        var smithingRecipe = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.SMITHING, smithingInput, helper.getLevel()).orElseThrow();
        helper.assertTrue(smithingRecipe.id().equals(ResourceLocation.fromNamespaceAndPath(
                        PasterDreamMod.MOD_ID, "fluffy_wind_alloy_sword_smithing"))
                        && smithingRecipe.value().assemble(smithingInput, helper.getLevel().registryAccess())
                        .is(ModItems.FLUFFY_WIND_ALLOY_SWORD.get()),
                "萦风合金剑锻造配方错误");

        var nuggetBlock = CraftingInput.of(3, 3, List.of(
                new ItemStack(ModItems.CONGEAL_WIND_IRON_NUGGET.get()),
                new ItemStack(ModItems.CONGEAL_WIND_IRON_NUGGET.get()),
                new ItemStack(ModItems.CONGEAL_WIND_IRON_NUGGET.get()),
                new ItemStack(ModItems.CONGEAL_WIND_IRON_NUGGET.get()),
                new ItemStack(ModItems.CONGEAL_WIND_IRON_NUGGET.get()),
                new ItemStack(ModItems.CONGEAL_WIND_IRON_NUGGET.get()),
                new ItemStack(ModItems.CONGEAL_WIND_IRON_NUGGET.get()),
                new ItemStack(ModItems.CONGEAL_WIND_IRON_NUGGET.get()),
                new ItemStack(ModItems.CONGEAL_WIND_IRON_NUGGET.get())));
        var ingotRecipe = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, nuggetBlock, helper.getLevel()).orElseThrow();
        helper.assertTrue(ingotRecipe.id().equals(ResourceLocation.fromNamespaceAndPath(
                        PasterDreamMod.MOD_ID, "congeal_wind_iron_ingot_from_congeal_wind_iron_nugget"))
                        && ingotRecipe.value().assemble(nuggetBlock, helper.getLevel().registryAccess())
                        .is(ModItems.CONGEAL_WIND_IRON_INGOT.get()),
                "凝风铁粒压缩配方错误");
        helper.succeed();
    }

    private static double attackDamage(ItemStack stack) {
        return stack.get(DataComponents.ATTRIBUTE_MODIFIERS).modifiers().stream()
                .filter(entry -> entry.attribute().equals(Attributes.ATTACK_DAMAGE))
                .mapToDouble(entry -> entry.modifier().amount())
                .sum();
    }

    private static String mode(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString(MODE_TAG);
    }
}
