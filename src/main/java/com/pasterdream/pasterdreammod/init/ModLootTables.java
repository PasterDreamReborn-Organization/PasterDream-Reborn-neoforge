package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.functions.ApplyEntityLootingFunction;
import com.pasterdream.pasterdreammod.world.conditions.RealPlayerCondition;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * 战利品表 {@code ResourceKey} 常量。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModLootTables.java}。
 * 搬运战利品表时在此定义常量，供世界生成 / 方块掉落引用。
 */
public final class ModLootTables {
    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTION_TYPES =
            DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, PasterDreamMod.MOD_ID);
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES =
            DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, PasterDreamMod.MOD_ID);
    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> REAL_PLAYER =
            LOOT_CONDITION_TYPES.register("real_player", () -> new LootItemConditionType(RealPlayerCondition.CODEC));

    public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<ApplyEntityLootingFunction>> APPLY_ENTITY_LOOTING_FUNCTION =
            LOOT_FUNCTION_TYPES.register("apply_entity_looting",
                    () -> new LootItemFunctionType<>(ApplyEntityLootingFunction.CODEC));

    private ModLootTables() {
    }
}
