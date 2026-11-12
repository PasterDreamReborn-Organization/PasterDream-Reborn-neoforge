package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.advancement.critereon.BreakWindCurtainTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.CaptureFireflyTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.EatGalaxyJellyAtHeightTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.FoundDesertFortressTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.FoundTombTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.HasAdvancementTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.LookAtPinkSheepTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.NewStandardSwordDrawingTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.OpenDreamAccumulatorTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.ReadDreamNoteTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.UseBoneNeedleTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.WindFlightTrigger;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
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
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.BredAnimalsTrigger;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.RecipeCraftedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
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

            // ========== 灯影世界剧情线 ==========
            ResourceKey<Level> lampShadowWorld = ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));

            // 进度：侵染教堂 — 需要阅读"侵染教堂"笔记后解锁（笔记由玩家自行探索获取）
            AdvancementHolder lampShadowRoot = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            ModBlocks.TWILIGHT_LANTERN.get(),
                            Component.translatable("advancements.pasterdream.story.lamp_shadow_root.title"),
                            Component.translatable("advancements.pasterdream.story.lamp_shadow_root.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("read_infested_church_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("侵染教堂-黑面"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/lamp_shadow_root"), existingFileHelper);

            // 据点守卫 — 完成暮影之笼事件后授予
            Advancement.Builder.advancement()
                    .parent(lampShadowRoot)
                    .display(
                            ModBlocks.TWILIGHT_LANTERN.get(),
                            Component.translatable("advancements.pasterdream.story.bastion_guard.title"),
                            Component.translatable("advancements.pasterdream.story.bastion_guard.description"),
                            null,
                            AdvancementType.GOAL,
                            true, true, false
                    )
                    .addCriterion("complete_bastion_guard",
                            new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/bastion_guard"), existingFileHelper);

            // ========== 灯影之下 Tab 页根进度（独立 tab，CHALLENGE）==========
            // 原作 achievement_shadow_start，首次进入 lamp_shadow_world 时触发
            AdvancementHolder enterLampShadowWorld = Advancement.Builder.advancement()
                    .display(
                            ModBlocks.TWILIGHT_LANTERN.get(),
                            Component.translatable("advancements.pasterdream.story.enter_lamp_shadow_world.title"),
                            Component.translatable("advancements.pasterdream.story.enter_lamp_shadow_world.description"),
                            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                    "textures/screens/yin_ying_.png"),
                            AdvancementType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("enter_lamp_shadow_world",
                            ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(lampShadowWorld))
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/enter_lamp_shadow_world"), existingFileHelper);

            // 浸影回忆 — 获得笔记残页（原作 achievement_shadow_a_0，+10 XP，解锁解析配方）
            AdvancementHolder shadowBrokenNote = Advancement.Builder.advancement()
                    .parent(enterLampShadowWorld)
                    .display(
                            ModItems.LAMP_SHADOW_BROKEN_NOTE.get(),
                            Component.translatable("advancements.pasterdream.story.broken_note.title"),
                            Component.translatable("advancements.pasterdream.story.broken_note.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("obtain_broken_note", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(ModItems.LAMP_SHADOW_BROKEN_NOTE.get())
                                    .withCount(MinMaxBounds.Ints.exactly(1)).build()))
                    .addCriterion("has_lamp_shadow_root",
                            HasAdvancementTrigger.TriggerInstance.hasAdvancement(
                                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                            "story/enter_lamp_shadow_world")))
                    .rewards(AdvancementRewards.Builder.experience(10)
                            .addRecipe(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_unknown_note")))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/broken_note"), existingFileHelper);

            // 于影研读 — 获得研究台（原作 achievement_shadow_a_1 位置，+10 XP）
            AdvancementHolder shadowResearchTable = Advancement.Builder.advancement()
                    .parent(shadowBrokenNote)
                    .display(
                            ModItems.RESEARCH_TABLE.get(),
                            Component.translatable("advancements.pasterdream.story.research_table.title"),
                            Component.translatable("advancements.pasterdream.story.research_table.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("obtain_research_table", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.RESEARCH_TABLE.get()))
                    .addCriterion("has_lamp_shadow_root",
                            HasAdvancementTrigger.TriggerInstance.hasAdvancement(
                                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                            "story/enter_lamp_shadow_world")))
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/research_table"), existingFileHelper);

            // 困顿囚徒 —— 首次进入暗影地牢（原作 achievement_shadow_c_0，程序授予，+10 XP）
            AdvancementHolder shadowPrisoner = Advancement.Builder.advancement()
                    .parent(shadowResearchTable)
                    .display(
                            ModBlocks.BROKEN_SHADOW_DUNGEON_PORTAL.get(),
                            Component.translatable("advancements.pasterdream.story.shadow_prisoner.title"),
                            Component.translatable("advancements.pasterdream.story.shadow_prisoner.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("enter_shadow_dungeon", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shadow_prisoner"), existingFileHelper);

            // 第一次对话完成（程序授予，触发暗影窥视）
            AdvancementHolder shadowNpcFirstDialogue = Advancement.Builder.advancement()
                    .addCriterion("first_dialogue", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shadow_npc_first_dialogue"), existingFileHelper);

            // 暗影入侵事件完成（解锁第二次对话）—— 可见目标进度，挂困顿囚徒之后
            AdvancementHolder shadowIntrudeComplete = Advancement.Builder.advancement()
                    .parent(shadowPrisoner)
                    .display(
                            ModItems.NIGHTMARE_FUEL.get(),
                            Component.translatable("advancements.pasterdream.story.shadow_intrude_complete.title"),
                            Component.translatable("advancements.pasterdream.story.shadow_intrude_complete.description"),
                            null,
                            AdvancementType.GOAL,
                            true, true, true
                    )
                    .addCriterion("intrude_complete", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shadow_intrude_complete"), existingFileHelper);

            // 第二次对话完成（解锁灯影选择）
            AdvancementHolder shadowNpcSecondDialogue = Advancement.Builder.advancement()
                    .addCriterion("second_dialogue", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shadow_npc_second_dialogue"), existingFileHelper);

            // 灯与影 —— 做出选择（原作 achievement_shadow_d_0，parent 原作 shadow_c_0，现挂到暗影入侵 shadow_intrude_complete 之下）
            AdvancementHolder shadowChoice = Advancement.Builder.advancement()
                    .parent(shadowIntrudeComplete)
                    .display(
                            ModBlocks.SHADOW_CANDLE.get(),
                            Component.translatable("advancements.pasterdream.story.shadow_choice.title"),
                            Component.translatable("advancements.pasterdream.story.shadow_choice.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, true
                    )
                    .addCriterion("make_choice", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shadow_choice"), existingFileHelper);

            // 信仰光明 —— 选灯
            AdvancementHolder talentLight = Advancement.Builder.advancement()
                    .parent(shadowChoice)
                    .display(
                            ModItems.TALENT_LIGHT.get(),
                            Component.translatable("advancements.pasterdream.story.talent_light.title"),
                            Component.translatable("advancements.pasterdream.story.talent_light.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("choose_light", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/talent_light"), existingFileHelper);

            // 暗影仆从 —— 选影
            AdvancementHolder talentShadow = Advancement.Builder.advancement()
                    .parent(shadowChoice)
                    .display(
                            ModItems.TALENT_SHADOW.get(),
                            Component.translatable("advancements.pasterdream.story.talent_shadow.title"),
                            Component.translatable("advancements.pasterdream.story.talent_shadow.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("choose_shadow", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/talent_shadow"), existingFileHelper);

            // 吹影镂尘 —— 击败亚伦柯斯之触（原作 achievement_shadow_e_0），挂暗影入侵之后
            AdvancementHolder defeatAaroncos = Advancement.Builder.advancement()
                    .parent(shadowIntrudeComplete)
                    .display(
                            ModItems.PURE_HORROR.get(),
                            Component.translatable("advancements.pasterdream.story.defeat_aaroncos.title"),
                            Component.translatable("advancements.pasterdream.story.defeat_aaroncos.description"),
                            null,
                            AdvancementType.GOAL,
                            true, true, false
                    )
                    .addCriterion("defeat_aaroncos", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/defeat_aaroncos"), existingFileHelper);

            // ========== 灯影世界剧情线（纯逻辑进度，不在进度界面显示）==========
            AdvancementHolder depositionShadow = Advancement.Builder.advancement()
                    .parent(enterLampShadowWorld)
                    .addCriterion("story_progress", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/deposition_shadow"), existingFileHelper);

            AdvancementHolder lampShadowTravelogue1 = Advancement.Builder.advancement()
                    .parent(depositionShadow)
                    .addCriterion("story_progress", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/lamp_shadow_travelogue_1"), existingFileHelper);

            AdvancementHolder shadowDungeon = Advancement.Builder.advancement()
                    .parent(lampShadowTravelogue1)
                    .addCriterion("story_progress", new Criterion<>(CriteriaTriggers.IMPOSSIBLE, new ImpossibleTrigger.TriggerInstance()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shadow_dungeon"), existingFileHelper);

            AdvancementHolder deception = Advancement.Builder.advancement()
                    .parent(shadowDungeon)
                    .addCriterion("read_deception_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("欺诈"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/deception"), existingFileHelper);

            AdvancementHolder bargain = Advancement.Builder.advancement()
                    .parent(deception)
                    .addCriterion("read_bargain_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("交易"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/bargain"), existingFileHelper);

            Advancement.Builder.advancement()
                    .parent(bargain)
                    .addCriterion("read_shattered_note",
                            ReadDreamNoteTrigger.TriggerInstance.forContent("破碎"))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/shattered"), existingFileHelper);

            // ========== 风之旅途 Tab 页根进度（独立 tab，CHALLENGE）==========
            // 首次进入 wind_journey_world 维度时触发
            ResourceKey<Level> windJourneyWorld = ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world"));

            AdvancementHolder enterWindJourney = Advancement.Builder.advancement()
                    .display(
                            ModBlocks.CYAN_MOSS_STONE.get(),
                            Component.translatable("advancements.pasterdream.story.enter_wind_journey.title"),
                            Component.translatable("advancements.pasterdream.story.enter_wind_journey.description"),
                            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                    "textures/block/cyan_stone_bricks.png"),
                            AdvancementType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("enter_wind_journey",
                            ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(windJourneyWorld))
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/enter_wind_journey"), existingFileHelper);

            // ========== 风之旅途子进度：凝风为露 ==========
            // 合成风植萃取液
            Advancement.Builder.advancement()
                    .parent(enterWindJourney)
                    .display(
                            ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get(),
                            Component.translatable("advancements.pasterdream.story.condense_wind_dew.title"),
                            Component.translatable("advancements.pasterdream.story.condense_wind_dew.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("craft_wind_plant_extract", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/condense_wind_dew"), existingFileHelper);

            // ========== 风之旅途子进度：晶凝风行 ==========
            // 获得风行者水晶
            Advancement.Builder.advancement()
                    .parent(enterWindJourney)
                    .display(
                            ModItems.WIND_RUNNER_CRYSTAL.get(),
                            Component.translatable("advancements.pasterdream.story.crystal_wind_runner.title"),
                            Component.translatable("advancements.pasterdream.story.crystal_wind_runner.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("get_wind_runner_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.WIND_RUNNER_CRYSTAL.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/crystal_wind_runner"), existingFileHelper);

            // ========== 风之旅途子进度：瓶中萤火 ==========
            // 用生态玻璃罐右键捕获一只萤火虫
            Advancement.Builder.advancement()
                    .parent(enterWindJourney)
                    .display(
                            ModItems.FIREFLY_GLASS_JAR.get(),
                            Component.translatable("advancements.pasterdream.story.firefly_in_jar.title"),
                            Component.translatable("advancements.pasterdream.story.firefly_in_jar.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("capture_firefly",
                            CaptureFireflyTrigger.TriggerInstance.captured())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/firefly_in_jar"), existingFileHelper);

            // ========== 风之旅途子进度：海的繁衍 ==========
            // 用海带繁殖水母
            Advancement.Builder.advancement()
                    .parent(enterWindJourney)
                    .display(
                            ModItems.JELLYFISH_MUD.get(),
                            Component.translatable("advancements.pasterdream.story.sea_bloom.title"),
                            Component.translatable("advancements.pasterdream.story.sea_bloom.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("breed_jellyfish",
                            BredAnimalsTrigger.TriggerInstance.bredAnimals(
                                    EntityPredicate.Builder.entity().of(ModEntities.JELLYFISH.get())))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/sea_bloom"), existingFileHelper);

            // ========== 风之旅途子进度：风凝有形 ==========
            // 获得凝结之风
            AdvancementHolder congealWind = Advancement.Builder.advancement()
                    .parent(enterWindJourney)
                    .display(
                            ModItems.CONGEAL_WIND.get(),
                            Component.translatable("advancements.pasterdream.story.congeal_wind.title"),
                            Component.translatable("advancements.pasterdream.story.congeal_wind.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("get_congeal_wind", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.CONGEAL_WIND.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/congeal_wind"), existingFileHelper);

            // ========== 风凝有形子进度：凝风锻铁 ==========
            // 获得凝风铁锭
            AdvancementHolder congealWindIron = Advancement.Builder.advancement()
                    .parent(congealWind)
                    .display(
                            ModItems.CONGEAL_WIND_IRON_INGOT.get(),
                            Component.translatable("advancements.pasterdream.story.congeal_wind_iron.title"),
                            Component.translatable("advancements.pasterdream.story.congeal_wind_iron.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("get_congeal_wind_iron_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.CONGEAL_WIND_IRON_INGOT.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/congeal_wind_iron"), existingFileHelper);

            // ========== 凝风锻铁子进度：瓶中闪电 ==========
            // 用梦釜合成雷电药剂瓶
            CompoundTag lightningBottleTag = new CompoundTag();
            lightningBottleTag.putString(PotionBottleItem.TAG_POTION_TYPE, PotionBottleItem.TYPE_LIGHTNING);
            DataComponentPredicate lightningBottlePredicate = DataComponentPredicate.builder()
                    .expect(DataComponents.CUSTOM_DATA, CustomData.of(lightningBottleTag))
                    .build();
            // 破风的骑士及其子进度依赖尚未迁入的破风骑士祭坛/召唤触发器，留待 T19-C/T20。
            Advancement.Builder.advancement()
                    .parent(congealWindIron)
                    .display(
                            PotionBottleItem.createWithType(ModItems.POTION_BOTTLE.get(),
                                    PotionBottleItem.TYPE_LIGHTNING),
                            Component.translatable("advancements.pasterdream.story.lightning_in_bottle.title"),
                            Component.translatable("advancements.pasterdream.story.lightning_in_bottle.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("get_lightning_potion_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(ModItems.POTION_BOTTLE.get())
                                    .hasComponents(lightningBottlePredicate).build()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/lightning_in_bottle"), existingFileHelper);

            // ========== 风之旅途子进度：展翅的无翼鸟 ==========
            // 食用风行者果冻（获得风行者效果）
            AdvancementHolder winglessBird = Advancement.Builder.advancement()
                    .parent(enterWindJourney)
                    .display(
                            ModItems.WIND_RUNNER_JELLY.get(),
                            Component.translatable("advancements.pasterdream.story.wingless_bird.title"),
                            Component.translatable("advancements.pasterdream.story.wingless_bird.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("eat_wind_runner_jelly", ConsumeItemTrigger.TriggerInstance.usedItem(
                            ModItems.WIND_RUNNER_JELLY.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/wingless_bird"), existingFileHelper);

            // ========== 展翅的无翼鸟子进度：乘风破幕 ==========
            // 飞行穿过破风幕帐
            AdvancementHolder breakWindCurtain = Advancement.Builder.advancement()
                    .parent(winglessBird)
                    .display(
                            ModItems.BREAK_WIND_CURTAIN.get(),
                            Component.translatable("advancements.pasterdream.story.break_wind_curtain.title"),
                            Component.translatable("advancements.pasterdream.story.break_wind_curtain.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("pass_break_wind_curtain",
                            BreakWindCurtainTrigger.TriggerInstance.passed())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/break_wind_curtain"), existingFileHelper);

            // ========== 乘风破幕子进度：百幕穿行（二级挑战） ==========
            // 累计穿过破风幕帐 100 次
            AdvancementHolder breakWindCurtainHundred = Advancement.Builder.advancement()
                    .parent(breakWindCurtain)
                    .display(
                            ModItems.BREAK_WIND_CURTAIN.get(),
                            Component.translatable("advancements.pasterdream.story.break_wind_curtain_hundred.title"),
                            Component.translatable("advancements.pasterdream.story.break_wind_curtain_hundred.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, false
                    )
                    .addCriterion("pass_break_wind_curtain_100_times",
                            BreakWindCurtainTrigger.TriggerInstance.passedTimes(100))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/break_wind_curtain_hundred"), existingFileHelper);

            // ========== 百幕穿行子进度：Angel Beats! ==========
            // 完成三项飞行挑战后获得天使之翼
            Advancement.Builder.advancement()
                    .parent(breakWindCurtainHundred)
                    .display(
                            ModItems.ANGEL_WING.get(),
                            Component.translatable("advancements.pasterdream.story.angel_beats.title"),
                            Component.translatable("advancements.pasterdream.story.angel_beats.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, false
                    )
                    .addCriterion("complete_wind_tailwind_flight",
                            HasAdvancementTrigger.TriggerInstance.hasAdvancement(
                                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                            "story/wind_tailwind_flight")))
                    .addCriterion("complete_wind_deadwind_flight",
                            HasAdvancementTrigger.TriggerInstance.hasAdvancement(
                                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                            "story/wind_deadwind_flight")))
                    .addCriterion("complete_break_wind_curtain_hundred",
                            HasAdvancementTrigger.TriggerInstance.hasAdvancement(
                                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                            "story/break_wind_curtain_hundred")))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/angel_beats"), existingFileHelper);

            // ========== 风之旅途子进度：风伴你而行 ==========
            // 获得过顺风与逆风效果（两者互斥，需两个 effects_changed 判定）
            AdvancementHolder windFollow = Advancement.Builder.advancement()
                    .parent(enterWindJourney)
                    .display(
                            Items.FEATHER,
                            Component.translatable("advancements.pasterdream.story.wind_follow.title"),
                            Component.translatable("advancements.pasterdream.story.wind_follow.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_tailwind", EffectsChangedTrigger.TriggerInstance.hasEffects(
                            MobEffectsPredicate.Builder.effects().and(ModEffects.TAILWIND)))
                    .addCriterion("has_deadwind", EffectsChangedTrigger.TriggerInstance.hasEffects(
                            MobEffectsPredicate.Builder.effects().and(ModEffects.DEADWIND)))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/wind_follow"), existingFileHelper);

            // ========== 风伴你而行子进度：逆风而进 ==========
            // 逆风效果下累计鞘翅飞行 10000 格
            Advancement.Builder.advancement()
                    .parent(windFollow)
                    .display(
                            ModItems.WIND_DEADWIND_ICON.get(),
                            Component.translatable("advancements.pasterdream.story.wind_deadwind_flight.title"),
                            Component.translatable("advancements.pasterdream.story.wind_deadwind_flight.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, false
                    )
                    .addCriterion("fly_10000_with_deadwind",
                            WindFlightTrigger.TriggerInstance.flown(WindFlightTrigger.FlightType.DEADWIND,
                                    MinMaxBounds.Doubles.atLeast(10000)))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/wind_deadwind_flight"), existingFileHelper);

            // ========== 风伴你而行子进度：御风而行 ==========
            // 顺风效果下累计鞘翅飞行 10000 格
            Advancement.Builder.advancement()
                    .parent(windFollow)
                    .display(
                            ModItems.WIND_TAILWIND_ICON.get(),
                            Component.translatable("advancements.pasterdream.story.wind_tailwind_flight.title"),
                            Component.translatable("advancements.pasterdream.story.wind_tailwind_flight.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, false
                    )
                    .addCriterion("fly_10000_with_tailwind",
                            WindFlightTrigger.TriggerInstance.flown(WindFlightTrigger.FlightType.TAILWIND,
                                    MinMaxBounds.Doubles.atLeast(10000)))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "story/wind_tailwind_flight"), existingFileHelper);
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

            // ========== 金狐的愿望 ==========
            Advancement.Builder.advancement()
                    .parent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/root"))
                    .display(
                            ModItems.KAICHU_OMAMORI.get(),
                            Component.translatable("advancements.pasterdream.get_kaichu_omamori.title"),
                            Component.translatable("advancements.pasterdream.get_kaichu_omamori.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, true
                    )
                    .addCriterion("get_kaichu_omamori", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ModItems.KAICHU_OMAMORI.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            "treasure/get_kaichu_omamori"), existingFileHelper);
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
