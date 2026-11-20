package com.pasterdream.pasterdreammod.helper.itemwithnbt.spawneggwithnbt;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomData;

public class GetSpawnEgg
{
    public static ItemStack getSpawnEgg(Entity entity)
    {
        if(entity != null)
        {
            if(entity instanceof LivingEntity)
            {
                SpawnEggItem spawnEgg = SpawnEggItem.byId(entity.getType());
                if(spawnEgg != null) {
                    ItemStack itemStack = new ItemStack(spawnEgg);

                    CompoundTag entityTag = new CompoundTag();
                    entity.saveWithoutId(entityTag);
                    entityTag.putString("id", net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());

                    entityTag.remove("Pos");
                    entityTag.remove("Motion");
                    entityTag.remove("Rotation");
                    entityTag.remove("FallDistance");
                    entityTag.remove("PortalCooldown");
                    entityTag.remove("HurtTime");
                    entityTag.remove("HurtByTimestamp");
                    entityTag.remove("Fire");
                    entityTag.remove("UUID");

                    if (entityTag.contains("attributes", Tag.TAG_LIST)) {
                        ListTag attributes = entityTag.getList("attributes", Tag.TAG_COMPOUND);
                        for (int i = 0; i < attributes.size(); i++) {
                            CompoundTag attribute = attributes.getCompound(i);
                            if (attribute.contains("modifiers", Tag.TAG_LIST)) {
                                ListTag modifiers = attribute.getList("modifiers", Tag.TAG_COMPOUND);
                                modifiers.removeIf(tag ->
                                {
                                    if (tag instanceof CompoundTag modifier) {
                                        return "minecraft:random_spawn_bonus".equals(modifier.getString("id"));
                                    }
                                    return false;
                                });
                                if (modifiers.isEmpty()) {
                                    attribute.remove("modifiers");
                                }
                            }
                        }
                    }

                    itemStack.set(DataComponents.ENTITY_DATA, CustomData.of(entityTag));

                    return itemStack;
                }
            }
                else
                {
                    ItemStack baseItem = entity.getPickResult();

                    if (baseItem != null && !(baseItem.isEmpty()))
                    {
                        CompoundTag entityTag = new CompoundTag();
                        entity.saveWithoutId(entityTag);

                        entityTag.remove("Pos");
                        entityTag.remove("Motion");
                        entityTag.remove("Rotation");
                        entityTag.remove("FallDistance");
                        entityTag.remove("PortalCooldown");
                        entityTag.remove("HurtTime");
                        entityTag.remove("HurtByTimestamp");
                        entityTag.remove("Fire");
                        entityTag.remove("UUID");

                        baseItem.set(DataComponents.ENTITY_DATA, CustomData.of(entityTag));
                        return baseItem;
                    }
                }
        }
        return ItemStack.EMPTY;
    }
}
