package com.pasterdream.pasterdreammod.network.debugtool;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolBlockEditorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import net.minecraft.server.level.ServerPlayer;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class SetBlockStatePacket implements CustomPacketPayload
{
    public static final Type<SetBlockStatePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/set_block_state"));
    public static final StreamCodec<FriendlyByteBuf, SetBlockStatePacket> STREAM_CODEC =
            StreamCodec.ofMember(SetBlockStatePacket::encode, SetBlockStatePacket::new);

    @Override
    public Type<SetBlockStatePacket> type()
    {
        return TYPE;
    }

    private final int menuId;
    private final BlockPos blockPosition;
    private final String stateString;

    public SetBlockStatePacket(int menuId, BlockPos blockPosition, String stateString)
    {
        this.menuId = menuId;
        this.blockPosition = blockPosition;
        this.stateString = stateString;
    }

    public SetBlockStatePacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.blockPosition = buffer.readBlockPos();
        this.stateString = buffer.readUtf();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
        buffer.writeBlockPos(blockPosition);
        buffer.writeUtf(stateString);
    }

    public static void handle(SetBlockStatePacket packet, IPayloadContext context)
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
                try
                {
                    BlockStateParser.BlockResult result = BlockStateParser.parseForBlock(level.holderLookup(Registries.BLOCK), packet.stateString, false);
                    level.setBlock(packet.blockPosition, result.blockState(), 3);
                    context.reply(new SyncBlockStatePacket(menu.containerId,
                            BlockStateParser.serialize(level.getBlockState(packet.blockPosition))));
                    var blockEntity = menu.getBlockEntity();
                    context.reply(new SyncBlockNbtPacket(menu.containerId,
                            blockEntity == null ? null : blockEntity.saveWithId(level.registryAccess())));
                }
                    catch (CommandSyntaxException e)
                    {

                    }
            }
        });
    }
}
