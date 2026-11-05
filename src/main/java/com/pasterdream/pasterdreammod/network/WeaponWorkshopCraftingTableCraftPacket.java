package com.pasterdream.pasterdreammod.network;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record WeaponWorkshopCraftingTableCraftPacket(BlockPos blockPosition) implements CustomPacketPayload
{
    public static final Type<WeaponWorkshopCraftingTableCraftPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "weapon_workshop_crafting_table_craft"));
    public static final StreamCodec<RegistryFriendlyByteBuf, WeaponWorkshopCraftingTableCraftPacket> STREAM_CODEC =
            StreamCodec.composite(BlockPos.STREAM_CODEC, WeaponWorkshopCraftingTableCraftPacket::blockPosition,
                    WeaponWorkshopCraftingTableCraftPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static void handle(WeaponWorkshopCraftingTableCraftPacket message, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player() instanceof ServerPlayer player)
            {
                Level level = player.level();
                BlockEntity blockEntity = level.getBlockEntity(message.blockPosition);
                if (blockEntity instanceof WeaponWorkshopCraftingTableBlockEntity weaponWorkshopCraftingTableBlockEntity)
                {
                    weaponWorkshopCraftingTableBlockEntity.craft(player);
                }
            }
        });
    }
}
