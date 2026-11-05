package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.helper.enhancestoneattributerelation.EnhanceStoneAttributeRelationRegistry;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * 强化石属性关系注册。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModEnhanceStoneAttributeRelation.java}。
 */
public final class ModEnhanceStoneAttributeRelation {
    public static void registerModEnhanceStoneAttributeRelation() {
        EnhanceStoneAttributeRelationRegistry.register(ModItems.ATTACK_ENHANCE_STONE.get().asItem(), Attributes.ATTACK_DAMAGE, 1, 3);
        EnhanceStoneAttributeRelationRegistry.register(ModItems.LUCK_ENHANCE_STONE.get().asItem(), Attributes.LUCK, 1, 3);
    }

    private ModEnhanceStoneAttributeRelation() {
    }
}
