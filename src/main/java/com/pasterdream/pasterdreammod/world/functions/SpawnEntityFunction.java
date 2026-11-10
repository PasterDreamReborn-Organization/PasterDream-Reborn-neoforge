package com.pasterdream.pasterdreammod.world.functions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SpawnEntityFunction extends LootItemConditionalFunction {
    public static final MapCodec<SpawnEntityFunction> CODEC = RecordCodecBuilder.mapCodec(instance ->
            commonFields(instance).and(instance.group(
                    BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity").forGetter(function -> function.entityType),
                    Codec.INT.fieldOf("count").forGetter(function -> function.count),
                    Codec.DOUBLE.fieldOf("offsetY").forGetter(function -> function.offsetY),
                    Codec.BOOL.optionalFieldOf("consume", false).forGetter(function -> function.consume)
            )).apply(instance, SpawnEntityFunction::new));

    private final EntityType<?> entityType;
    private final int count;
    private final double offsetY;
    private final boolean consume;

    SpawnEntityFunction(List<LootItemCondition> conditions, EntityType<?> entityType, int count, double offsetY, boolean consume) {
        super(conditions);
        this.entityType = entityType;
        this.count = count;
        this.offsetY = offsetY;
        this.consume = consume;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext ctx) {
        Vec3 origin = ctx.getParamOrNull(LootContextParams.ORIGIN);
        if (origin != null) {
            for (int i = 0; i < count; i++) {
                Entity entity = entityType.create(ctx.getLevel());
                if (entity != null) {
                    entity.setPos(origin.x + (ctx.getRandom().nextDouble() - 0.5) * 0.5,
                            origin.y + offsetY,
                            origin.z + (ctx.getRandom().nextDouble() - 0.5) * 0.5);
                    ctx.getLevel().addFreshEntity(entity);
                }
            }
        }
        return consume ? ItemStack.EMPTY : stack;
    }

    @Override
    public LootItemFunctionType<SpawnEntityFunction> getType() {
        return ModLootTables.SPAWN_ENTITY_FUNCTION_TYPE.get();
    }

    public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
        private final EntityType<?> entityType;
        private final int count;
        private final double offsetY;
        private boolean consume;

        private Builder(EntityType<?> entityType, int count, double offsetY) {
            this.entityType = entityType;
            this.count = count;
            this.offsetY = offsetY;
        }

        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public SpawnEntityFunction build() {
            return new SpawnEntityFunction(getConditions(), entityType, count, offsetY, consume);
        }

        public static Builder spawnOnly(EntityType<?> entityType, int count, double offsetY) {
            Builder builder = new Builder(entityType, count, offsetY);
            builder.consume = true;
            return builder;
        }
    }
}
