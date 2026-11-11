package com.pasterdream.pasterdreammod.world.block.dreamspawner;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DreamSpawnerBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
    private boolean firstSpawn;
    private double playerRange = 16;
    private int spawnCount;

    public DreamSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DREAM_SPAWNER.get(), pos, state);
    }

    public boolean isFirstSpawn() { return firstSpawn; }
    public void setFirstSpawn(boolean v) { firstSpawn = v; setChanged(); }
    public double getPlayerRange() { return playerRange; }
    public int getSpawnCount() { return spawnCount; }
    public void setSpawnCount(int v) { spawnCount = v; setChanged(); }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, stacks, registries);
        firstSpawn = tag.getBoolean("FirstSpawn");
        playerRange = tag.getDouble("PlayerRange");
        spawnCount = tag.getInt("SpawnCount");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, stacks, registries);
        tag.putBoolean("FirstSpawn", firstSpawn);
        tag.putDouble("PlayerRange", playerRange);
        tag.putInt("SpawnCount", spawnCount);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public int getContainerSize() { return stacks.size(); }

    @Override
    public boolean isEmpty() {
        for (ItemStack s : stacks) if (!s.isEmpty()) return false;
        return true;
    }

    @Override
    public Component getDefaultName() { return Component.literal("dream_spawner"); }

    @Override
    public int getMaxStackSize() { return 64; }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) { return ChestMenu.threeRows(id, inventory); }

    @Override
    public Component getDisplayName() { return Component.literal("Dream Spawner"); }

    @Override
    protected NonNullList<ItemStack> getItems() { return stacks; }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks) { this.stacks = stacks; }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) { return true; }
}
