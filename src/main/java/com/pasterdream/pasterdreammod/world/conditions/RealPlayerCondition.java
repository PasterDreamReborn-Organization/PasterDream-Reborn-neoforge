package com.pasterdream.pasterdreammod.world.conditions;

import com.mojang.serialization.MapCodec;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.common.util.FakePlayer;

public final class RealPlayerCondition implements LootItemCondition {
    public static final MapCodec<RealPlayerCondition> CODEC = MapCodec.unit(new RealPlayerCondition());
    private static final RealPlayerCondition INSTANCE = new RealPlayerCondition();

    private RealPlayerCondition() {
    }

    @Override
    public boolean test(LootContext context) {
        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        return entity instanceof ServerPlayer && !(entity instanceof FakePlayer);
    }

    @Override
    public LootItemConditionType getType() {
        return ModLootTables.REAL_PLAYER.get();
    }

    public static LootItemCondition.Builder builder() {
        return () -> INSTANCE;
    }
}
