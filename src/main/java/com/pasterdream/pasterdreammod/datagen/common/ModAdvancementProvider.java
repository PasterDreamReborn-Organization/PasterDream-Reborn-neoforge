package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.advancement.critereon.EatGalaxyJellyAtHeightTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.FoundDesertFortressTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.FoundTombTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.HasAdvancementTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.LookAtPinkSheepTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.NewStandardSwordDrawingTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.OpenDreamAccumulatorTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.ReadDreamNoteTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.UseBoneNeedleTrigger;
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
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.RecipeCraftedTrigger;
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
        super(output, registries, existingFileHelper, List.of(new StoryAdvancements(), new FishingAdvancements(), new ExplorationAdvancements()));
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

            // ========== 子进度：染梦裂隙 ==========
            AdvancementHolder dyedreamCrack = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            ModBlocks.DYEDREAM_CRACK.get(),
                            Component.translatable("advancements.pasterdream.story.dyedream_crack.title"),
                            Component.translatable("advancements.pasterdream.story.dyedream_crack.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("read_dyedream_crack_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("染梦裂隙"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/dyedream_crack"), existingFileHelper);

            // ========== 染梦裂隙子进度：太陽とレインボ一 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamCrack)
                    .display(
                            ModItems.PLIERS.get(),
                            Component.translatable("advancements.pasterdream.story.create_pliers.title"),
                            Component.translatable("advancements.pasterdream.story.create_pliers.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("create_pliers", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.PLIERS.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/create_pliers"), existingFileHelper);

            // ========== 染梦世界 ==========
            AdvancementHolder dyedreamWorld = Advancement.Builder.advancement()
                    .parent(dyedreamCrack)
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

            // ========== 染梦世界子进度：尘埃落定（首次打开蓄梦池）==========
            Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.DREAM_ACCUMULATOR.get(),
                            Component.translatable("advancements.pasterdream.story.dream_accumulator.title"),
                            Component.translatable("advancements.pasterdream.story.dream_accumulator.description"),
                            null,
                            AdvancementType.GOAL,
                            true, true, false
                    )
                    .addCriterion("open_dream_accumulator", OpenDreamAccumulatorTrigger.TriggerInstance.opened())
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/dream_accumulator"), existingFileHelper);

            // ========== 染梦世界子进度：染梦残页 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.DYEDREAM_BROKEN_NOTE.get(),
                            Component.translatable("advancements.pasterdream.story.dyedream_broken_note.title"),
                            Component.translatable("advancements.pasterdream.story.dyedream_broken_note.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("obtain_dyedream_broken_note", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.DYEDREAM_BROKEN_NOTE.get()))
                    .addCriterion("has_dyedream_world", HasAdvancementTrigger.TriggerInstance.hasAdvancement(
                            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/dyedream_world")))
                    .rewards(AdvancementRewards.Builder.experience(10)
                            .addRecipe(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_unknown_note")))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/dyedream_broken_note"), existingFileHelper);

            // ========== 染梦世界子进度：好梦在何方 ==========
            Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .display(
                            ModItems.GLASS_JAR_OF_DREAM_JUICE.get(),
                            Component.translatable("advancements.pasterdream.story.glass_jar_of_dream_juice.title"),
                            Component.translatable("advancements.pasterdream.story.glass_jar_of_dream_juice.description"),
                            null,
                            AdvancementType.GOAL,
                            true, true, false
                    )
                    .addCriterion("drink_dream_juice", ConsumeItemTrigger.TriggerInstance.usedItem(
                            ModItems.GLASS_JAR_OF_DREAM_JUICE.get()))
                    .rewards(AdvancementRewards.Builder.experience(20))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/glass_jar_of_dream_juice"), existingFileHelper);

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
            AdvancementHolder pureAndFlawless = Advancement.Builder.advancement()
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

            // ========== 子进度：哦，痛！ ==========
            AdvancementHolder useBoneNeedle = Advancement.Builder.advancement()
                    .parent(pureAndFlawless)
                    .display(
                            ModItems.PALE_BONENEEDLE.get(),
                            Component.translatable("advancements.pasterdream.story.use_pale_boneneedle.title"),
                            Component.translatable("advancements.pasterdream.story.use_pale_boneneedle.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("used_boneneedle", UseBoneNeedleTrigger.TriggerInstance.use())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/use_pale_boneneedle"), existingFileHelper);

            // ========== 挑战子进度：人类跌出梦境 ==========
            Advancement.Builder.advancement()
                    .parent(useBoneNeedle)
                    .display(
                            ModItems.PALE_BONENEEDLE.get(),
                            Component.translatable("advancements.pasterdream.story.human_falls_out_of_dream.title"),
                            Component.translatable("advancements.pasterdream.story.human_falls_out_of_dream.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("fell_and_used", UseBoneNeedleTrigger.TriggerInstance.fellAndUse())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/human_falls_out_of_dream"), existingFileHelper);

            // ========== 染梦世界剧情线（纯逻辑进度，不在进度界面显示）==========
            AdvancementHolder researchOnSweetDreamWorld = Advancement.Builder.advancement()
                    .parent(dyedreamWorld)
                    .addCriterion("read_research_on_sweet_dream_world_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("关于美梦世界的研究"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/research_on_sweet_dream_world"), existingFileHelper);

            AdvancementHolder essenceOfDreamWorld = Advancement.Builder.advancement()
                    .parent(researchOnSweetDreamWorld)
                    .addCriterion("read_essence_of_dream_world_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("梦境世界的本质"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/essence_of_dream_world"), existingFileHelper);

            Advancement.Builder.advancement()
                    .parent(essenceOfDreamWorld)
                    .addCriterion("read_pale_snow_lotus_and_boneneeedle_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("苍白雪莲与苍白骨针"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/pale_snow_lotus_and_boneneeedle"), existingFileHelper);
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

            // ========== 海之馈赠子进度：海洋之泪 ==========
            AdvancementHolder getBlueDew = Advancement.Builder.advancement()
                    .parent(getDeepSeaTreasure)
                    .display(
                            ModItems.BLUE_DEW.get(),
                            Component.translatable("advancements.pasterdream.get_blue_dew.title"),
                            Component.translatable("advancements.pasterdream.get_blue_dew.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_blue_dew", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.BLUE_DEW.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "husbandry/get_blue_dew"), existingFileHelper);

            // ========== 海洋之泪子进度：深蓝之心 ==========
            AdvancementHolder getBlueHeartOfTheSea = Advancement.Builder.advancement()
                    .parent(getBlueDew)
                    .display(
                            ModItems.BLUE_HEART_OF_THE_SEA.get(),
                            Component.translatable("advancements.pasterdream.get_blue_heart_of_the_sea.title"),
                            Component.translatable("advancements.pasterdream.get_blue_heart_of_the_sea.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_blue_dew", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.BLUE_HEART_OF_THE_SEA.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "husbandry/get_blue_heart_of_the_sea"), existingFileHelper);

            // ========== 深蓝之心子进度：潮汐之神 ==========
            Advancement.Builder.advancement()
                    .parent(getBlueHeartOfTheSea)
                    .display(
                            ModItems.BEIHAI_RUO_TIDE_SWORD.get(),
                            Component.translatable("advancements.pasterdream.get_beihairuo_tide_sword.title"),
                            Component.translatable("advancements.pasterdream.get_beihairuo_tide_sword.description"),
                            null,
                            AdvancementType.GOAL,
                            true, true, false
                    )
                    .addCriterion("has_beihairuo_tide_sword", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.BEIHAI_RUO_TIDE_SWORD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "husbandry/get_beihairuo_tide_sword"), existingFileHelper);

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

    public static class ExplorationAdvancements implements AdvancementProvider.AdvancementGenerator {

        @Override
        public void generate(HolderLookup.@NotNull Provider registries,
                             @NotNull Consumer<AdvancementHolder> saver,
                             @NotNull ExistingFileHelper existingFileHelper) {

            ResourceLocation adventureRoot = ResourceLocation.fromNamespaceAndPath("minecraft", "adventure/root");
            ResourceLocation netherRoot = ResourceLocation.fromNamespaceAndPath("minecraft", "nether/root");

            // ========== 冒险Tab子进度：被遗忘的剑冢 ==========
            AdvancementHolder forgottenSwordTomb = Advancement.Builder.advancement()
                    .parent(adventureRoot)
                    .display(
                            ModItems.SWORD_EMBRYO.get(),
                            Component.translatable("advancements.pasterdream.adventure.forgotten_sword_tomb.title"),
                            Component.translatable("advancements.pasterdream.adventure.forgotten_sword_tomb.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("found_tomb", FoundTombTrigger.TriggerInstance.found())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/forgotten_sword_tomb"), existingFileHelper);

            // ========== 被遗忘的剑冢子进度：被遗忘之剑 ==========
            AdvancementHolder getTheLostSword = Advancement.Builder.advancement()
                    .parent(forgottenSwordTomb)
                    .display(
                            ModItems.SWORD_EMBRYO.get(),
                            Component.translatable("advancements.pasterdream.story.get_the_lost_sword.title"),
                            Component.translatable("advancements.pasterdream.story.get_the_lost_sword.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_sword_embryo", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.SWORD_EMBRYO.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/get_the_lost_sword"), existingFileHelper);

            AdvancementHolder craftKusanagi = Advancement.Builder.advancement()
                    .parent(getTheLostSword)
                    .display(
                            ModItems.KUSANAGI.get(),
                            Component.translatable("advancements.pasterdream.craft_kusanagi.title"),
                            Component.translatable("advancements.pasterdream.craft_kusanagi.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_kusanagi", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.KUSANAGI.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/craft_kusanagi"), existingFileHelper);

            Advancement.Builder.advancement()
                    .parent(craftKusanagi)
                    .display(
                            ModItems.MURAKUMO_KUSANAGI.get(),
                            Component.translatable("advancements.pasterdream.get_murakumo_kusanagi.title"),
                            Component.translatable("advancements.pasterdream.get_murakumo_kusanagi.description"),
                            null,
                            AdvancementType.GOAL,
                            true, true, false
                    )
                    .addCriterion("has_murakumo_kusanagi", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.MURAKUMO_KUSANAGI.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/get_murakumo_kusanagi"), existingFileHelper);

            // ========== 挑战进度1：邻家有女初长成，力拔山兮气盖世 ==========
            AdvancementHolder hasLostSwordTomb = Advancement.Builder.advancement()
                    .parent(forgottenSwordTomb)
                    .display(
                            ModItems.LOST_SWORD_TOMB.get(),
                            Component.translatable("advancements.pasterdream.story.dig_up_a_tomb.title"),
                            Component.translatable("advancements.pasterdream.story.dig_up_a_tomb.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("has_lost_sword_tomb", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.LOST_SWORD_TOMB.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/has_lost_sword_tomb"), existingFileHelper);

            // ========== 挑战进度2：新概念拔剑 ==========
            AdvancementHolder newStandardSwordDrawing = Advancement.Builder.advancement()
                    .parent(hasLostSwordTomb)
                    .display(
                            ModItems.LOST_SWORD_TOMB.get(),
                            Component.translatable("advancements.pasterdream.new_standard_sword_drawing.title"),
                            Component.translatable("advancements.pasterdream.new_standard_sword_drawing.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("new_standard_sword_drawing", NewStandardSwordDrawingTrigger.TriggerInstance.draw())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/new_standard_sword_drawing"), existingFileHelper);

            // ========== 挑战进度3：物归原主……？ ==========
            Advancement.Builder.advancement()
                    .parent(newStandardSwordDrawing)
                    .display(
                            ModItems.LOST_SWORD_TOMB.get(),
                            Component.translatable("advancements.pasterdream.return_to_owner.title"),
                            Component.translatable("advancements.pasterdream.return_to_owner.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("crafted_lost_sword_tomb", RecipeCraftedTrigger.TriggerInstance.craftedItem(
                            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lost_sword_tomb")))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/return_to_owner"), existingFileHelper);

            // ========== 冒险Tab子进度：沙漠英灵 ==========
            AdvancementHolder findDesertFortress = Advancement.Builder.advancement()
                    .parent(adventureRoot)
                    .display(
                            ModItems.DESERT_HERO_TOMB.get(),
                            Component.translatable("advancements.pasterdream.adventure.find_desert_fortress.title"),
                            Component.translatable("advancements.pasterdream.adventure.find_desert_fortress.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("find_desert_fortress", FoundDesertFortressTrigger.TriggerInstance.found())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/desert_fortress"), existingFileHelper);

            // ========== 沙漠英灵子进度：英灵之剑 ==========
            AdvancementHolder getDesertSword = Advancement.Builder.advancement()
                    .parent(findDesertFortress)
                    .display(
                            ModItems.DESERT_SWORD.get(),
                            Component.translatable("advancements.pasterdream.adventure.get_desert_sword.title"),
                            Component.translatable("advancements.pasterdream.adventure.get_desert_sword.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("get_desert_sword", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.DESERT_SWORD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/get_desert_sword"), existingFileHelper);

            // ========== 英灵之剑子进度：遗志已成 ==========
            Advancement.Builder.advancement()
                    .parent(getDesertSword)
                    .display(
                            ModItems.CHENJINGMEN_DESERT_SWORD.get(),
                            Component.translatable("advancements.pasterdream.adventure.get_chenjingmen_desert_sword.title"),
                            Component.translatable("advancements.pasterdream.adventure.get_chenjingmen_desert_sword.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, false
                    )
                    .addCriterion("get_chenjingmen_desert_sword", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.CHENJINGMEN_DESERT_SWORD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "adventure/get_chenjingmen_desert_sword"), existingFileHelper);

            // ========== 下界Tab子进度：真金不怕火炼 ==========
            AdvancementHolder getMoltenGoldIngot = Advancement.Builder.advancement()
                    .parent(netherRoot)
                    .display(
                            ModItems.MOLTEN_GOLD_INGOT.get(),
                            Component.translatable("advancements.pasterdream.get_molten_gold_ingot.title"),
                            Component.translatable("advancements.pasterdream.get_molten_gold_ingot.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_molten_gold_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.MOLTEN_GOLD_INGOT.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "nether/get_molten_gold_ingot"), existingFileHelper);

            // ========== 真金不怕火炼任务线1：炙热之剑 ==========
            AdvancementHolder craftHellfireSword = Advancement.Builder.advancement()
                    .parent(getMoltenGoldIngot)
                    .display(
                            ModItems.HELLFIRE_SWORD.get(),
                            Component.translatable("advancements.pasterdream.craft_hellfire_sword.title"),
                            Component.translatable("advancements.pasterdream.craft_hellfire_sword.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_hellfire_sword", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.HELLFIRE_SWORD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "nether/craft_hellfire_sword"), existingFileHelper);

            // ========== 真金不怕火炼任务线2：炼狱之火 ==========
            Advancement.Builder.advancement()
                    .parent(craftHellfireSword)
                    .display(
                            ModItems.INFERNO_SWORD.get(),
                            Component.translatable("advancements.pasterdream.craft_inferno_sword.title"),
                            Component.translatable("advancements.pasterdream.craft_inferno_sword.description"),
                            null,
                            AdvancementType.GOAL,
                            true, true, false
                    )
                    .addCriterion("has_inferno_sword", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.INFERNO_SWORD.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "nether/craft_inferno_sword"), existingFileHelper);
        }
    }
}
