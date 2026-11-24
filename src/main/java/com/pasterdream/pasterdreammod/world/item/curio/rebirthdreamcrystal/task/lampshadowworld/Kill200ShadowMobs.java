package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.lampshadowworld;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamSingleTask;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class Kill200ShadowMobs implements IPasterDreamSingleTask
{
    @Override
    public Component getTaskName()
    {
        return Component.translatable("task.pasterdream.击杀200个暗影生物");
    }

    @Override
    public boolean isTaskProgressShowWithIntOrDouble()
    {
        return true;
    }

    @Override
    public double getTaskProgress(Player player)
    {
        double totalKillMobCounts = 0;
        if(player instanceof ServerPlayer serverPlayer)
        {
            // NeoForge 1.21.1 无 ITag 视图，改由原版注册表的命名标签查询同一份数据包标签
            TagKey<EntityType<?>> shadowMobTag = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_mob"));
            Optional<HolderSet.Named<EntityType<?>>> shadowMobTagContent = BuiltInRegistries.ENTITY_TYPE.getTag(shadowMobTag);

            if(shadowMobTagContent.isPresent())
            {
                for (Holder<EntityType<?>> entityType : shadowMobTagContent.get())
                {
                    totalKillMobCounts += serverPlayer.getStats().getValue(Stats.ENTITY_KILLED, entityType.value());
                }
            }
        }
        return totalKillMobCounts;
    }

    @Override
    public double getTaskMaxProgress(Player player)
    {
        return 200;
    }
}
