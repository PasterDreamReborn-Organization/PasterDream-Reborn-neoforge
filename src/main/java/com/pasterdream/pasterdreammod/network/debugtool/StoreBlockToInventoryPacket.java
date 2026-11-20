package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.helper.nbthelper.WrappedNBTBlockItem;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolBlockEditorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class StoreBlockToInventoryPacket implements CustomPacketPayload
{
    public static final Type<StoreBlockToInventoryPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/store_block_to_inventory"));
    public static final StreamCodec<FriendlyByteBuf, StoreBlockToInventoryPacket> STREAM_CODEC =
            StreamCodec.ofMember(StoreBlockToInventoryPacket::encode, StoreBlockToInventoryPacket::new);

    @Override
    public Type<StoreBlockToInventoryPacket> type()
    {
        return TYPE;
    }

    private final int menuId;
    private final BlockPos blockPosition;

    public StoreBlockToInventoryPacket(int menuId, BlockPos blockPosition)
    {
        this.menuId = menuId;
        this.blockPosition = blockPosition;
    }

    public StoreBlockToInventoryPacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.blockPosition = buffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
        buffer.writeBlockPos(blockPosition);
    }

    public static void handle(StoreBlockToInventoryPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (!(context.player() instanceof ServerPlayer player)
                    || !DebugToolItem.canEdit(player) || !player.containerMenu.stillValid(player))
            {
                return;
            }
            if (player.containerMenu.containerId == packet.menuId && player.containerMenu instanceof DebugToolBlockEditorMenu menu && menu.getBlockPosition().equals(packet.blockPosition))
            {
                ServerLevel level = player.serverLevel();
                BlockState state = level.getBlockState(packet.blockPosition);
                Block block = state.getBlock();
                Item blockItem = block.asItem();
                if (blockItem == Items.AIR)
                {
                    level.removeBlock(packet.blockPosition, false);
                    player.closeContainer();
                    return;
                }

                ItemStack stack = new ItemStack(blockItem);
                BlockEntity blockEntity = level.getBlockEntity(packet.blockPosition);
                CompoundTag nbt = blockEntity == null ? null : blockEntity.saveWithId(level.registryAccess());
                if (nbt != null)
                {
                    WrappedNBTBlockItem.wrapper(stack, nbt);
                    // The captured item already owns the contents. Prevent onRemove from spilling them.
                    level.removeBlockEntity(packet.blockPosition);
                }

                if (!level.removeBlock(packet.blockPosition, false))
                {
                    if (blockEntity != null)
                    {
                        level.setBlockEntity(blockEntity);
                    }
                    return;
                }
                if (!player.getInventory().add(stack))
                {
                    player.drop(stack, false);
                }
                player.closeContainer();
            }
        });
    }
}
