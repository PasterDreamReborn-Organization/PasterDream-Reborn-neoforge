package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.advancement.critereon.EatGalaxyJellyAtHeightTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.LookAtPinkSheepTrigger;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {

    public ModAdvancementProvider(PackOutput output,
                                  CompletableFuture<HolderLookup.Provider> registries,
                                  ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new StoryAdvancements(), new FishingAdvancements()));
    }

    public static class StoryAdvancements implements AdvancementProvider.AdvancementGenerator {

        @Override
        public void generate(HolderLookup.@NotNull Provider registries,
                             @NotNull Consumer<AdvancementHolder> saver,
                             @NotNull ExistingFileHelper existingFileHelper) {

            HolderGetter<Biome> biomes = registries.lookupOrThrow(Registries.BIOME);

            // ========== 根进度：帕斯特之梦 ==========
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(
                            ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get(),
                            Component.translatable("advancements.pasterdream.story.root.title"),
                            Component.translatable("advancements.pasterdream.story.root.description"),
                            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                    "textures/screens/guiblock.png"),
                            AdvancementType.TASK,
                            false, false, false
                    )
                    .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/root"), existingFileHelper);

            // ========== 子进度：首次接触染梦裂隙（无显示，纯标记）==========
            Advancement.Builder.advancement()
                    .parent(root)
                    .addCriterion("first_contact", new Criterion<>(CriteriaTriggers.IMPOSSIBLE,
                            new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/first_contact_dyedream_crack"), existingFileHelper);

            // ========== 染梦世界（待染梦裂隙前置迁入后恢复其父节点）==========
            AdvancementHolder dyedreamWorld = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            ModBlocks.DYEDREAM_GRASS_BLOCK.get(),
                            Component.translatable("advancements.pasterdream.story.dyedream_world.title"),
                            Component.translatable("advancements.pasterdream.story.dyedream_world.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("first_enter_dyedream", new Criterion<>(CriteriaTriggers.IMPOSSIBLE,
                            new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/dyedream_world"), existingFileHelper);

            // ========== 哥德堡安眠曲子进度：山河入梦（遍历全部染梦群系） ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.MELT_DREAM_BOOTS.get(),
                            Component.translatable("advancements.pasterdream.story.dyedream_biomes.title"),
                            Component.translatable("advancements.pasterdream.story.dyedream_biomes.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, false
                    )
                    .addCriterion("dyedream_plains", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_PLAINS)))))
                    .addCriterion("dyedream_mushroom_mountains", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS)))))
                    .addCriterion("dyedream_snowy_plains", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_SNOWY_PLAINS)))))
                    .addCriterion("dyedream_frozen_ocean", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_FROZEN_OCEAN)))))
                    .addCriterion("dyedream_ocean", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_OCEAN)))))
                    .addCriterion("dyedream_cold_ocean", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_COLD_OCEAN)))))
                    .addCriterion("dyedream_beach", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_BEACH)))))
                    .addCriterion("dyedream_river", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_RIVER)))))
                    .addCriterion("dyedream_frozen_river", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_FROZEN_RIVER)))))
                    .addCriterion("dyedream_snowy_peaks", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_SNOWY_PEAKS)))))
                    .addCriterion("dyedream_cherry_grove", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_CHERRY_GROVE)))))
                    .addCriterion("dyedream_forest", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_FOREST)))))
                    .addCriterion("dyedream_flower_field", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_FLOWER_FIELD)))))
                    .addCriterion("dyedream_caves", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_CAVES)))))
                    .addCriterion("dyedream_lush_caves", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_LUSH_CAVES)))))
                    .addCriterion("dyedream_dripstone_caves", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(biomes.getOrThrow(ModBiomes.DYEDREAM_DRIPSTONE_CAVES)))))
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/dyedream_biomes"), existingFileHelper);

            // ========== 哥德堡安眠曲子进度：冶梦花莲 ==========
            AdvancementHolder mistyDreamingLotus = Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.MISTY_DREAMING_LOTUS.get(),
                            Component.translatable("advancements.pasterdream.story.misty_dreaming_lotus.title"),
                            Component.translatable("advancements.pasterdream.story.misty_dreaming_lotus.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_misty_dreaming_lotus", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.MISTY_DREAMING_LOTUS.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/misty_dreaming_lotus"), existingFileHelper);

            // ========== 冶梦花莲子进度：梦随风起 ==========
            Advancement.Builder.advancement()
                    .parent(mistyDreamingLotus)
                    .display(
                            ModItems.QUEER_SOUP.get(),
                            Component.translatable("advancements.pasterdream.story.queer_soup.title"),
                            Component.translatable("advancements.pasterdream.story.queer_soup.description"),
                            null,
                            AdvancementType.GOAL,
                            true, true, false
                    )
                    .addCriterion("has_queer_soup", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.QUEER_SOUP.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/queer_soup"), existingFileHelper);

            // ========== 染梦世界子进度：方块人会梦见粉红羊吗 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            Items.PINK_WOOL,
                            Component.translatable("advancements.pasterdream.story.look_at_pink_sheep.title"),
                            Component.translatable("advancements.pasterdream.story.look_at_pink_sheep.description"),
                            null,
                            AdvancementType.GOAL,
                            true, true, false
                    )
                    .addCriterion("look_at_pink_sheep", LookAtPinkSheepTrigger.TriggerInstance.look())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/look_at_pink_sheep"), existingFileHelper);

            // ========== 染梦世界子进度：不可食用果冻 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.DREAM_FERTILIZER.get(),
                            Component.translatable("advancements.pasterdream.story.dream_fertilizer.title"),
                            Component.translatable("advancements.pasterdream.story.dream_fertilizer.description"),
                            null,
                            AdvancementType.GOAL,
                            true, true, false
                    )
                    .addCriterion("has_dream_fertilizer", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.DREAM_FERTILIZER.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/dream_fertilizer"), existingFileHelper);

            // ========== 染梦世界子进度：把梦捧在手心里! ==========
            AdvancementHolder dyedreamDust = Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.DYEDREAM_DUST.get(),
                            Component.translatable("advancements.pasterdream.story.dyedream_dust.title"),
                            Component.translatable("advancements.pasterdream.story.dyedream_dust.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_dyedream_dust", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.DYEDREAM_DUST.get()))
                    .rewards(AdvancementRewards.Builder.experience(20))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/dyedream_dust"), existingFileHelper);

            // ========== 把梦捧在手心里子进度：熔融于梦 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamDust)
                    .display(
                            ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get(),
                            Component.translatable("advancements.pasterdream.story.melt_dream_crystal_fragment.title"),
                            Component.translatable("advancements.pasterdream.story.melt_dream_crystal_fragment.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, false
                    )
                    .addCriterion("has_melt_dream_crystal_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/melt_dream_crystal_fragment"), existingFileHelper);

            // ========== 染梦世界子进度：百味杂陈 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.MELT_DREAM_LIQUID_BUCKET.get(),
                            Component.translatable("advancements.pasterdream.story.melt_dream_liquid_bucket.title"),
                            Component.translatable("advancements.pasterdream.story.melt_dream_liquid_bucket.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_melt_dream_liquid_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.MELT_DREAM_LIQUID_BUCKET.get()))
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/melt_dream_liquid_bucket"), existingFileHelper);

            // ========== 染梦世界子进度：星辰凝胶 ==========
            AdvancementHolder galaxyJelly = Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.GALAXY_JELLY.get(),
                            Component.translatable("advancements.pasterdream.story.galaxy_jelly.title"),
                            Component.translatable("advancements.pasterdream.story.galaxy_jelly.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_galaxy_jelly", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.GALAXY_JELLY.get()))
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/galaxy_jelly"), existingFileHelper);

            // ========== 星辰凝胶子进度：太空专业配音员 ==========
            Advancement.Builder.advancement()
                    .parent(galaxyJelly)
                    .display(
                            ModItems.GALAXY_JELLY.get(),
                            Component.translatable("advancements.pasterdream.story.eat_galaxy_jelly_on_high_height.title"),
                            Component.translatable("advancements.pasterdream.story.eat_galaxy_jelly_on_high_height.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("eat_galaxy_jelly_on_high_height", EatGalaxyJellyAtHeightTrigger.TriggerInstance.atBuildHeight())
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/eat_galaxy_jelly_on_high_height"), existingFileHelper);

            // ========== 子进度：纯洁无瑕 ==========
            Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            ModItems.WHITE_COROLLA.get(),
                            Component.translatable("advancements.pasterdream.story.pure_and_flawless.title"),
                            Component.translatable("advancements.pasterdream.story.pure_and_flawless.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_white_corolla", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.WHITE_COROLLA_CROP_AGE_1.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/pure_and_flawless"), existingFileHelper);
        }
    }

    public static class FishingAdvancements implements AdvancementProvider.AdvancementGenerator {

        @Override
        public void generate(HolderLookup.@NotNull Provider registries,
                             @NotNull Consumer<AdvancementHolder> saver,
                             @NotNull ExistingFileHelper existingFileHelper) {

            // ========== 海之馈赠 ==========
            AdvancementHolder getDeepSeaTreasure = Advancement.Builder.advancement()
                    .parent(ResourceLocation.fromNamespaceAndPath("minecraft", "husbandry/fishy_business"))
                    .display(
                            ModItems.DEEP_SEA_TREASURE.get(),
                            Component.translatable("advancements.pasterdream.get_deep_sea_treasure.title"),
                            Component.translatable("advancements.pasterdream.get_deep_sea_treasure.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_deep_sea_treasure", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.DEEP_SEA_TREASURE.get()))
                    .addCriterion("has_dyedream_deep_sea_treasure", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.DYEDREAM_DEEP_SEA_TREASURE.get()))
                    .addCriterion("has_shadow_deep_sea_treasure", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.SHADOW_DEEP_SEA_TREASURE.get()))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "husbandry/get_deep_sea_treasure"), existingFileHelper);

            // ========== To wish upon a satellite...... ==========
            DataComponentPredicate superPredicate = DataComponentPredicate.builder()
                    .expect(ModDataComponents.DEEP_TREASURE_SUPER.get(), true)
                    .build();

            Advancement.Builder.advancement()
                    .parent(getDeepSeaTreasure)
                    .display(
                            ModItems.DEEP_SEA_TREASURE.get(),
                            Component.translatable("advancements.pasterdream.get_super_deep_sea_treasure.title"),
                            Component.translatable("advancements.pasterdream.get_super_deep_sea_treasure.description"),
                            null,
                            AdvancementType.GOAL,
                            true, true, false
                    )
                    .addCriterion("has_super_deep_sea_treasure", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item()
                                    .of(ModItems.DEEP_SEA_TREASURE.get())
                                    .hasComponents(superPredicate)
                                    .build()))
                    .addCriterion("has_super_dyedream_deep_sea_treasure", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item()
                                    .of(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get())
                                    .hasComponents(superPredicate)
                                    .build()))
                    .addCriterion("has_super_shadow_deep_sea_treasure", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item()
                                    .of(ModItems.SHADOW_DEEP_SEA_TREASURE.get())
                                    .hasComponents(superPredicate)
                                    .build()))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "husbandry/get_super_deep_sea_treasure"), existingFileHelper);

            // ========== 鱼儿如浩空繁星 ==========
            Advancement.Builder.advancement()
                    .parent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/root"))
                    .display(
                            ModItems.STAR_WISH_ROD.get(),
                            Component.translatable("advancements.pasterdream.get_star_wish_rod.title"),
                            Component.translatable("advancements.pasterdream.get_star_wish_rod.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("get_star_wish_rod", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.STAR_WISH_ROD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_star_wish_rod"), existingFileHelper);
        }
    }
}
