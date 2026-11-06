package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class WorldtreeSeedpodItem extends Item implements ICurioItem {
    private static final ResourceKey<Level> DYEDREAM_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_world"));
    private static final ResourceLocation WORLDTREE_LEAVES =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_worldtree_leaves");

    public WorldtreeSeedpodItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.legendary()));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (level.isClientSide()) return;

        var entity = slotContext.entity();
        if (entity.tickCount % 20 != 0) return;
        if (!level.dimension().equals(DYEDREAM_WORLD)) return;
        if (!level.canSeeSky(entity.blockPosition())) return;
        if (!level.isDay()) return;
        if (entity.getY() <= 160) return;

        var pos = entity.blockPosition();
        var below1 = level.getBlockState(pos.below());
        var below2 = level.getBlockState(pos.below(2));
        if (!isWorldtreeLeaves(below1) && !isWorldtreeLeaves(below2)) return;

        if (entity instanceof ServerPlayer sp) {
            sp.causeFoodExhaustion(0.4f);
            MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(sp, 0.1);
        }
    }

    private static boolean isWorldtreeLeaves(BlockState state) {
        return BuiltInRegistries.BLOCK.getKey(state.getBlock()).equals(WORLDTREE_LEAVES);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.worldtree_seedpod.condition"));
        list.add(Component.translatable("tooltip.pasterdream.worldtree_seedpod.hunger"));
        list.add(Component.translatable("tooltip.pasterdream.worldtree_seedpod.energy"));
        list.add(Component.translatable("tooltip.pasterdream.worldtree_seedpod.flavor"));
    }
}
