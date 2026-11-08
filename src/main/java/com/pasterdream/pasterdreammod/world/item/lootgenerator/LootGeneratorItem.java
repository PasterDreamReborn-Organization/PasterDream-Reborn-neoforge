package com.pasterdream.pasterdreammod.world.item.lootgenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class LootGeneratorItem extends Item
{
    public LootGeneratorItem()
    {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Nullable
    public static ResourceLocation getToolLootTable(ItemStack stack)
    {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null && data.contains("LootTable"))
        {
            return ResourceLocation.tryParse(data.copyTag().getString("LootTable"));
        }
        return null;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos blockPosition = context.getClickedPos();
        ItemStack itemStack = context.getItemInHand();

        BlockEntity blockEntity = level.getBlockEntity(blockPosition);

        if (!level.isClientSide && player instanceof ServerPlayer)
        {
            CustomData toolData = itemStack.get(DataComponents.CUSTOM_DATA);
            if (toolData != null && toolData.contains("LootTable"))
            {
                String lootTable = toolData.copyTag().getString("LootTable");
                if (blockEntity != null)
                {
                    CompoundTag containerTag = blockEntity.saveWithFullMetadata(level.registryAccess());
                    //清除已有物品
                    if (containerTag.contains("Items") || containerTag.contains("LootTable") || containerTag.contains("LootTableSeed"))
                    {
                        containerTag.put("Items", new ListTag());
                    }
                    else
                        if (containerTag.contains("Inventory") && containerTag.getCompound("Inventory").contains("Items"))
                        {
                            containerTag.getCompound("Inventory").put("Items", new ListTag());
                        }
                        else
                            if (level.getCapability(Capabilities.ItemHandler.BLOCK, blockPosition, null) == null)
                            {
                                player.displayClientMessage(Component.translatable("message.pasterdream.loot_generator.not_container"), true);
                                return InteractionResult.FAIL;
                            }

                    //清除已有战利品种子
                    if (containerTag.contains("LootTableSeed"))
                    {
                        containerTag.remove("LootTableSeed");
                    }
                    containerTag.putString("LootTable", lootTable);
                    blockEntity.loadWithComponents(containerTag, level.registryAccess());
                    blockEntity.setChanged();
                    player.displayClientMessage(Component.translatable("message.pasterdream.loot_generator.loot_table_set", lootTable), true);
                    return InteractionResult.SUCCESS;
                }
                else
                {
                    player.displayClientMessage(Component.translatable("message.pasterdream.loot_generator.not_container"), true);
                    return InteractionResult.FAIL;
                }
            }
            else
            {
                player.displayClientMessage(Component.translatable("message.pasterdream.loot_generator.no_loot_table"), true);
                return InteractionResult.FAIL;
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.loot_generator.usage.shift"));

        ResourceLocation lootTable = getToolLootTable(stack);
        if (lootTable != null)
        {
            tooltip.add(Component.translatable("tooltip.pasterdream.当前设置战利品表：")
                    .append(Component.literal(lootTable.toString()).withStyle(style ->
                            style.withColor(0x55FFFF).withItalic(true))));
        }
        else
        {
            tooltip.add(Component.translatable("tooltip.pasterdream.当前设置战利品表：")
                    .append(Component.translatable("tooltip.pasterdream.loot_table.not_set")));
        }
    }
}
