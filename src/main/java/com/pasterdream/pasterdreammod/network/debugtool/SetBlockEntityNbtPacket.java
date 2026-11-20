package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolBlockEditorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class SetBlockEntityNbtPacket implements CustomPacketPayload
{
    public static final Type<SetBlockEntityNbtPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/set_block_entity_nbt"));
    public static final StreamCodec<FriendlyByteBuf, SetBlockEntityNbtPacket> STREAM_CODEC =
            StreamCodec.ofMember(SetBlockEntityNbtPacket::encode, SetBlockEntityNbtPacket::new);

    @Override
    public Type<SetBlockEntityNbtPacket> type()
    {
        return TYPE;
    }

    private final int menuId;
    private final BlockPos blockPosition;
    private final CompoundTag NBT;

    public SetBlockEntityNbtPacket(int menuId, BlockPos blockPosition, CompoundTag NBT)
    {
        this.menuId = menuId;
        this.blockPosition = blockPosition;
        this.NBT = NBT;
    }

    public SetBlockEntityNbtPacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.blockPosition = buffer.readBlockPos();
        this.NBT = buffer.readNbt();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
        buffer.writeBlockPos(blockPosition);
        buffer.writeNbt(NBT);
    }

    public static void handle(SetBlockEntityNbtPacket packet, IPayloadContext context)
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
                BlockEntity blockEntity = level.getBlockEntity(packet.blockPosition);
                if (blockEntity != null)
                {
                    CompoundTag incoming = (packet.NBT == null ? new CompoundTag() : packet.NBT.copy());
                    incoming.remove("x");
                    incoming.remove("y");
                    incoming.remove("z");
                    incoming.remove("id");

                    CompoundTag previous = blockEntity.saveWithId(level.registryAccess());
                    try
                    {
                        blockEntity.loadWithComponents(incoming, level.registryAccess());
                    }
                    catch (RuntimeException exception)
                    {
                        blockEntity.loadWithComponents(previous, level.registryAccess());
                        player.sendSystemMessage(Component.translatable("error.pasterdream.无效的NBT", exception.getMessage()));
                        return;
                    }
                    blockEntity.setChanged();
                    level.sendBlockUpdated(packet.blockPosition, blockEntity.getBlockState(), blockEntity.getBlockState(), 3);
                    context.reply(new SyncBlockNbtPacket(menu.containerId, blockEntity.saveWithId(level.registryAccess())));
                }
            }
        });
    }
}
