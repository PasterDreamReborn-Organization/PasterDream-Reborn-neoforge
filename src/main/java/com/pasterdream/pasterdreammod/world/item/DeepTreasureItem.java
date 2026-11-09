package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModDataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public class DeepTreasureItem extends Item {
    private final String normalLootTable;
    private final String superLootTable;
    private final String tooltipKey;

    public DeepTreasureItem(Properties properties, String normalLootTable, String superLootTable) {
        this(properties, normalLootTable, superLootTable, "tooltip.pasterdream.deep_treasure");
    }

    public DeepTreasureItem(Properties properties, String normalLootTable, String superLootTable, String tooltipKey) {
        super(properties);
        this.normalLootTable = normalLootTable;
        this.superLootTable = superLootTable;
        this.tooltipKey = tooltipKey;
    }

    public static ItemStack createSuper(Item item) {
        return createSuper(item, 1);
    }

    public static ItemStack createSuper(Item item, int count) {
        ItemStack stack = new ItemStack(item, count);
        stack.set(ModDataComponents.DEEP_TREASURE_SUPER.get(), true);
        return stack;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.DEEP_TREASURE_SUPER.get(), false);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable(tooltipKey));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()) {
            return InteractionResultHolder.success(stack);
        }
        ServerLevel serverLevel = (ServerLevel) level;
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.CHEST_OPEN, SoundSource.PLAYERS, 1.0f, 1.0f);

        boolean isSuper = stack.getOrDefault(ModDataComponents.DEEP_TREASURE_SUPER.get(), false);
        ResourceLocation tableId = ResourceLocation.tryParse(isSuper ? superLootTable : normalLootTable);

        if (tableId != null) {
            LootTable lootTable = serverLevel.getServer().reloadableRegistries()
                    .getLootTable(ResourceKey.create(Registries.LOOT_TABLE, tableId));
            LootParams params = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .withParameter(LootContextParams.THIS_ENTITY, player)
                    .create(LootContextParamSets.CHEST);
            for (ItemStack loot : lootTable.getRandomItems(params)) {
                ItemEntity entity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), loot);
                entity.setPickUpDelay(5);
                level.addFreshEntity(entity);
            }
        }
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return InteractionResultHolder.consume(stack);
    }
}
