package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class DyedreamDeskBlockEntity extends BlockEntity implements MenuProvider
{
    private final Component displayName = Component.translatable("block." + PasterDreamMod.MOD_ID + ".dyedream_desk");
    @Nullable
    protected ResourceLocation lootTable;
    protected long lootTableSeed = 0L;

    private final ItemStackHandler itemHandler = new ItemStackHandler(1)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };

    public DyedreamDeskBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.DYEDREAM_DESK.get(), pos, state);
    }

    @Override
    public Component getDisplayName()
    {
        return displayName;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
    {
        return new DyedreamDeskMenu(id, inv, this);
    }

    public ItemStackHandler getItemHandler()
    {
        return itemHandler;
    }

    public void unpackLootTable(@Nullable Player player)
    {
        if (lootTable != null && level instanceof ServerLevel serverLevel)
        {
            LootTable table = serverLevel.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, lootTable));
            lootTable = null;
            LootParams.Builder params = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(worldPosition));
            if (player != null)
                params.withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player);
            for (ItemStack stack : table.getRandomItems(params.create(LootContextParamSets.CHEST)))
                itemHandler.insertItem(0, stack, false);
            setChanged();
        }
    }

    public void setLootTable(@Nullable ResourceLocation lootTable, long seed)
    {
        this.lootTable = lootTable;
        this.lootTableSeed = seed != 0L ? seed : worldPosition.asLong();
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", itemHandler.serializeNBT(registries));
        if (lootTable != null)
        {
            tag.putString("LootTable", lootTable.toString());
            tag.putLong("LootTableSeed", lootTableSeed);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
        lootTable = tag.contains("LootTable", 8) ? ResourceLocation.parse(tag.getString("LootTable")) : null;
        lootTableSeed = tag.contains("LootTableSeed", 4) ? tag.getLong("LootTableSeed") : worldPosition.asLong();
    }
}
