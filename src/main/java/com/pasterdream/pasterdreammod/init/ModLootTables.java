package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.functions.ApplyEntityLootingFunction;
import com.pasterdream.pasterdreammod.world.functions.SpawnEntityFunction;
import com.pasterdream.pasterdreammod.world.conditions.RealPlayerCondition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
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
    public static final ResourceKey<LootTable> PICNIC_BASKET_OVERWORLD_LOOT = create("chests/picnic_basket_overworld");
    public static final ResourceKey<LootTable> PICNIC_BASKET_DYEDREAM_WORLD_LOOT = create("chests/picnic_basket_dyedream_world");
    public static final ResourceKey<LootTable> DYEDREAM_RELIC_CHEST_LOOT_COMMON = create("chests/dyedream_relic_chest_loot_common");
    public static final ResourceKey<LootTable> DYEDREAM_RELIC_CHEST_LOOT_RARE = create("chests/dyedream_relic_chest_loot_rare");
    public static final ResourceKey<LootTable> SHADOW_RELIC_CHEST_LOOT_COMMON = create("chests/shadow_relic_chest_loot_common");
    public static final ResourceKey<LootTable> SHADOW_DUNGEON_REPOSITORY_CORE = create("chests/shadow_dungeon_repository_core");
    public static final ResourceKey<LootTable> SHADOW_DUNGEON_REPOSITORY_COMMON = create("chests/shadow_dungeon_repository_common");
    public static final ResourceKey<LootTable> SHADOW_DUNGEON_REPOSITORY_SUPPLY = create("chests/shadow_dungeon_repository_supply");
    public static final ResourceKey<LootTable> ECOSYSTEM_BUBBLE_CHEST_LOOT = create("chests/ecosystem_bubble_chest");
    public static final ResourceKey<LootTable> FISHERMAN_HUT_CHEST_LOOT = create("chests/fisherman_hut_chest");
    public static final ResourceKey<LootTable> DESERT_FORTRESS_CHEST_LOOT = create("chests/desert_fortress_chest");
    public static final ResourceKey<LootTable> DESERT_COTTAGE_CHEST_LOOT = create("chests/desert_cottage_chest");
    public static final ResourceKey<LootTable> WIND_JOURNEY_CHEST_LOOT = create("chests/wind_journey_chest");
    public static final ResourceKey<LootTable> WIND_JOURNEY_CHEST_RARE_LOOT = create("chests/wind_journey_chest_rare");
    public static final ResourceKey<LootTable> MELT_DREAM_CRYSTAL_CHEST_WIND_JOURNEY_NORMAL_LOOT =
            create("chests/melt_dream_crystal_chest_wind_journey_world_normal");
    public static final ResourceKey<LootTable> MELT_DREAM_CRYSTAL_CHEST_WIND_JOURNEY_RARE_LOOT =
            create("chests/melt_dream_crystal_chest_wind_journey_world_rare");
    public static final ResourceKey<LootTable> MELT_DREAM_CRYSTAL_CHEST_WIND_JOURNEY_LEGEND_LOOT =
            create("chests/melt_dream_crystal_chest_wind_journey_world_legend");

    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTION_TYPES =
            DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, PasterDreamMod.MOD_ID);
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES =
            DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, PasterDreamMod.MOD_ID);
    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> REAL_PLAYER =
            LOOT_CONDITION_TYPES.register("real_player", () -> new LootItemConditionType(RealPlayerCondition.CODEC));

    public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<ApplyEntityLootingFunction>> APPLY_ENTITY_LOOTING_FUNCTION =
            LOOT_FUNCTION_TYPES.register("apply_entity_looting",
                    () -> new LootItemFunctionType<>(ApplyEntityLootingFunction.CODEC));
    public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<SpawnEntityFunction>> SPAWN_ENTITY_FUNCTION_TYPE =
            LOOT_FUNCTION_TYPES.register("spawn_entity", () -> new LootItemFunctionType<>(SpawnEntityFunction.CODEC));

    /** 园艺钳剪树叶走自定义战利品表：pliers_cutting/<命名空间>/<方块注册名>，与 datagen 路径一致。 */
    public static final String PLIERS_CUTTING_PREFIX = "pliers_cutting/";

    public static ResourceLocation pliersCuttingLoot(Block block) {
        ResourceLocation key = BuiltInRegistries.BLOCK.getKey(block);
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                PLIERS_CUTTING_PREFIX + key.getNamespace() + "/" + key.getPath());
    }

    private static ResourceKey<LootTable> create(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path));
    }

    private ModLootTables() {
    }
}
