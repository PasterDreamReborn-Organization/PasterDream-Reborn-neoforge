package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * 网络注册中心（NeoForge 1.21.1 payload 框架）。
 *
 * <p>NeoForge 已移除 {@code SimpleChannel}：每个数据包实现 {@link CustomPacketPayload}，
 * 用 {@code StreamCodec} 序列化；通过 {@link RegisterPayloadHandlersEvent} 的
 * {@link PayloadRegistrar} 注册。搬运源仓库 {@code init/ModNetwork.java} 的 28 个数据包时，
 * 逐个重写为 record + {@code Type} + {@code STREAM_CODEC} + {@code handle(IPayloadContext)}。
 *
 * <p>本类为 {@code IModBusEvent} 监听器，由 {@code @EventBusSubscriber} 自动挂到 Mod 总线。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class ModNetwork {
    public static final String PROTOCOL_VERSION = "1";

    private ModNetwork() {
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        // TODO:
        // registrar.playToServer(BlinkPacket.TYPE, BlinkPacket.STREAM_CODEC, BlinkPacket::handle);
        // registrar.playToClient(SanSyncPacket.TYPE, SanSyncPacket.STREAM_CODEC, SanSyncPacket::handle);
    }

    /** 客户端 → 服务端。 */
    public static void sendToServer(CustomPacketPayload payload) {
        PacketDistributor.sendToServer(payload);
    }
}
