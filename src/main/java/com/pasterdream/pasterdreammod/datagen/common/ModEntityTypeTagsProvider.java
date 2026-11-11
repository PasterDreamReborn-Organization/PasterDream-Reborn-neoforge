package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModEntityTypeTags.SHADOW_MOB)
                .add(ModEntities.SHADOW_GOLEM.get())
                .add(ModEntities.TERRORBEAK.get())
                .add(ModEntities.CRAZY_TERRORBEAK.get())
                .add(ModEntities.WEAKENESS_TERRORBEAK.get())
                .add(ModEntities.SHADOW_HAND.get())
                .add(ModEntities.SHADOW_GHOST.get())
                .add(ModEntities.SHADOW_SQUEAL_GHOST.get())
                .add(ModEntities.WAILING_SHADOW_GHOST.get())
                .add(ModEntities.FRIENDLY_SHADOW_GHOST.get())
                .add(ModEntities.BLACK_BEETLE.get())
                .add(ModEntities.BLACK_BEETLE_MOTHER.get())
                .add(EntityType.SILVERFISH);

        // 自定义箭矢加入原版箭矢实体标签，使力量 / 冲击 / 火矢等附魔按 1.21 数据驱动结算。
        tag(EntityTypeTags.ARROWS)
                .add(ModEntities.MELT_DREAM_ARROW.get())
                .add(ModEntities.FLUFFY_WIND_ARROW.get())
                .add(ModEntities.TOXIC_SPORE_ARROW.get())
                .add(ModEntities.FRAGDREAM_ARROW.get())
                .add(ModEntities.MAGICAL_ARROW.get())
                .add(ModEntities.CHARGED_ARROW.get())
                .add(ModEntities.BLACKSTONE_ARROW.get())
                .add(ModEntities.CHAOTIC_ARROW.get())
                .add(ModEntities.BLAST_ARROW.get())
                .add(ModEntities.CONGEAL_WIND_ARROW.get())
                .add(ModEntities.DEW_ARROW.get());

        tag(ModEntityTypeTags.SPECIAL_ENTITY)
                .add(EntityType.ITEM)
                .add(EntityType.EXPERIENCE_ORB)
                .add(EntityType.ITEM_FRAME)
                .add(EntityType.GLOW_ITEM_FRAME)
                .add(ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get())
                .add(ModEntities.TERRASWORD_WAVE.get())
                .add(ModEntities.SHAKING_CRYSTAL.get())
                .add(ModEntities.FOX_FIRE.get());
    }
}
