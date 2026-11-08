package com.pasterdream.pasterdreammod.world.block.itemcontainer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class ItemContainerBlockEntity extends BlockEntity implements MenuProvider, IItemContainerInventory
{
    private final Component displayName;

    @Nullable
    protected ResourceLocation lootTable;
    protected long lootTableSeed = 0L;

    private final ItemStackHandler itemHandler = new ItemStackHandler(setItemStackHandlerSize());

    public ItemContainerBlockEntity(BlockEntityType<?> type, BlockPos blockPosition, BlockState state, String nameKey)
    {
        super(type, blockPosition, state);
        this.displayName = Component.translatable(nameKey);
    }

    @Override
    public Component getDisplayName()
    {
        return this.displayName;
    }

    public abstract int setItemStackHandlerSize();

    @Override
    public abstract AbstractContainerMenu createMenu(int id, Inventory inventory, Player player);

    @Override
    public ItemStackHandler getItemHandler()
    {
        return itemHandler;
    }

    protected void tryLoadLootTable(CompoundTag tag)
    {
        this.lootTable = null;
        this.lootTableSeed = 0L;
        if (tag.contains("LootTable", 8))
        {
            this.lootTable = ResourceLocation.parse(tag.getString("LootTable"));
            if (tag.contains("LootTableSeed", 4))
            {
                this.lootTableSeed = tag.getLong("LootTableSeed");
            }
            else
            {
                // NBT没有种子时，用方块坐标自动生成（和原版结构放置逻辑一致）
                this.lootTableSeed = this.worldPosition.asLong();
            }
        }
    }

    /**
     * 当容器首次被打开时，从战利品表生成物品（原版风格：物品随机分散到槽位中）
     */
    public void unpackLootTable(@Nullable Player player)
    {
        if (this.lootTable != null && this.level instanceof ServerLevel serverLevel)
        {
            LootTable loottable = serverLevel.getServer().reloadableRegistries()
                    .getLootTable(ResourceKey.create(Registries.LOOT_TABLE, this.lootTable));
            this.lootTable = null;
            LootParams.Builder paramsBuilder = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition));
            if (player != null)
            {
                paramsBuilder.withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player);
            }
            LootParams params = paramsBuilder.create(LootContextParamSets.CHEST);
            List<ItemStack> items = loottable.getRandomItems(params);

            // 原版箱子风格：随机打乱槽位后分散放置物品
            int slotCount = itemHandler.getSlots();
            List<Integer> shuffledSlots = new ArrayList<>(slotCount);
            for (int i = 0; i < slotCount; i++)
            {
                shuffledSlots.add(i);
            }

            // 无参Random，槽位分布真正随机，不受坐标/种子影响
            Random random = new Random();
            Collections.shuffle(shuffledSlots, random);

            for (ItemStack stack : items)
            {
                for (int slotIndex : shuffledSlots)
                {
                    if (stack.isEmpty()) break;
                    ItemStack remaining = itemHandler.insertItem(slotIndex, stack, false);
                    if (remaining.getCount() != stack.getCount())
                    {
                        stack = remaining;
                    }
                }
            }
            this.setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", itemHandler.serializeNBT(registries));
        if (this.lootTable != null)
        {
            tag.putString("LootTable", this.lootTable.toString());
            tag.putLong("LootTableSeed", this.lootTableSeed);
        }
    }

    /**
     * 设置战利品表，容器下次打开时自动生成战利品（原版结构箱子风格）
     * @param lootTable 战利品表 ResourceLocation
     * @param seed 随机种子，用于控制生成结果；若为 0 则自动使用方块坐标
     */
    public void setLootTable(@Nullable ResourceLocation lootTable, long seed)
    {
        this.lootTable = lootTable;
        this.lootTableSeed = seed != 0L ? seed : this.worldPosition.asLong();
        this.setChanged();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
        tryLoadLootTable(tag);
    }
}
