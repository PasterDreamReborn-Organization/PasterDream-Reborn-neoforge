package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.network.debugsword.DebugBlockActionPacket;
import com.pasterdream.pasterdreammod.network.animationstatechange.AnimationStateChangePacket;
import com.pasterdream.pasterdreammod.network.meltdreamcrystalchest.MeltDreamCrystalChestResetCooldownSyncPacket;
import com.pasterdream.pasterdreammod.network.blueprint.BlueprintPlacePacket;
import com.pasterdream.pasterdreammod.network.blueprint.StartBlueprintPlacementPacket;
import com.pasterdream.pasterdreammod.network.blueprint.UpdateBlueprintPlacingPacket;
import com.pasterdream.pasterdreammod.network.san.LowSanConfigSyncPacket;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.network.WeaponWorkshopCraftingTableCraftPacket;
import com.pasterdream.pasterdreammod.network.mortar.MortarCraftPacket;
import com.pasterdream.pasterdreammod.network.fluidslot.FluidSlotInteractPacket;
import com.pasterdream.pasterdreammod.network.fluidslot.FluidSyncPacket;
import com.pasterdream.pasterdreammod.network.researchtable.ResearchTableCopyPacket;
import com.pasterdream.pasterdreammod.network.researchtable.ResearchTableResearchPacket;
import com.pasterdream.pasterdreammod.network.DreamCauldronCraftPacket;
import com.pasterdream.pasterdreammod.network.DreamCauldronMessagePacket;
import com.pasterdream.pasterdreammod.network.fluffywindalloy.WindAlloyMiningSpeedSyncPacket;
import com.pasterdream.pasterdreammod.network.shadowerosion.ShadowErosionMiningSpeedSyncPacket;
import com.pasterdream.pasterdreammod.network.shadowselect.ShadowSelectEndButtonPacket;
import com.pasterdream.pasterdreammod.network.fluffywindalloy.WindHoverInputPacket;
import com.pasterdream.pasterdreammod.network.skill.KusanagiVenomStingerPacket;
import com.pasterdream.pasterdreammod.network.skill.BlinkPacket;
import com.pasterdream.pasterdreammod.network.skill.TerraBladeSwingPacket;
import com.pasterdream.pasterdreammod.network.curio.CloakActivationPacket;
import com.pasterdream.pasterdreammod.network.curio.CurioActivationPacket;
import com.pasterdream.pasterdreammod.network.curio.KaichuOmamoriActivationPacket;
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
        registrar.playToServer(KusanagiVenomStingerPacket.TYPE, KusanagiVenomStingerPacket.STREAM_CODEC,
                KusanagiVenomStingerPacket::handle);
        registrar.playToServer(WeaponWorkshopCraftingTableCraftPacket.TYPE, WeaponWorkshopCraftingTableCraftPacket.STREAM_CODEC,
                WeaponWorkshopCraftingTableCraftPacket::handle);
        registrar.playToServer(MortarCraftPacket.TYPE, MortarCraftPacket.STREAM_CODEC, MortarCraftPacket::handle);
        registrar.playToServer(DreamCauldronCraftPacket.TYPE, DreamCauldronCraftPacket.STREAM_CODEC, DreamCauldronCraftPacket::handle);
        registrar.playToClient(DreamCauldronMessagePacket.TYPE, DreamCauldronMessagePacket.STREAM_CODEC, DreamCauldronMessagePacket::handle);
        registrar.playToServer(FluidSlotInteractPacket.TYPE, FluidSlotInteractPacket.STREAM_CODEC,
                FluidSlotInteractPacket::handle);
        registrar.playToServer(ResearchTableCopyPacket.TYPE, ResearchTableCopyPacket.STREAM_CODEC,
                ResearchTableCopyPacket::handle);
        registrar.playToServer(ResearchTableResearchPacket.TYPE, ResearchTableResearchPacket.STREAM_CODEC,
                ResearchTableResearchPacket::handle);
        registrar.playToClient(FluidSyncPacket.TYPE, FluidSyncPacket.STREAM_CODEC,
                FluidSyncPacket::handle);
        registrar.playToServer(BlinkPacket.TYPE, BlinkPacket.STREAM_CODEC, BlinkPacket::handle);
        registrar.playToServer(TerraBladeSwingPacket.TYPE, TerraBladeSwingPacket.STREAM_CODEC,
                TerraBladeSwingPacket::handle);
        registrar.playToServer(CloakActivationPacket.TYPE, CloakActivationPacket.STREAM_CODEC,
                CloakActivationPacket::handle);
        registrar.playToServer(KaichuOmamoriActivationPacket.TYPE, KaichuOmamoriActivationPacket.STREAM_CODEC,
                KaichuOmamoriActivationPacket::handle);
        registrar.playToClient(CurioActivationPacket.TYPE, CurioActivationPacket.STREAM_CODEC,
                CurioActivationPacket::handle);
        registrar.playToServer(WindHoverInputPacket.TYPE, WindHoverInputPacket.STREAM_CODEC,
                WindHoverInputPacket::handle);
        registrar.playToClient(LowSanConfigSyncPacket.TYPE, LowSanConfigSyncPacket.STREAM_CODEC, LowSanConfigSyncPacket::handle);
        registrar.playToClient(WindAlloyMiningSpeedSyncPacket.TYPE, WindAlloyMiningSpeedSyncPacket.STREAM_CODEC,
                WindAlloyMiningSpeedSyncPacket::handle);
        registrar.playToClient(ShadowErosionMiningSpeedSyncPacket.TYPE, ShadowErosionMiningSpeedSyncPacket.STREAM_CODEC,
                ShadowErosionMiningSpeedSyncPacket::handle);
        registrar.playToServer(ShadowSelectEndButtonPacket.TYPE, ShadowSelectEndButtonPacket.STREAM_CODEC,
                ShadowSelectEndButtonPacket::handle);
        registrar.playToServer(DebugBlockActionPacket.TYPE, DebugBlockActionPacket.STREAM_CODEC,
                DebugBlockActionPacket::handle);
        registrar.playToClient(AnimationStateChangePacket.TYPE, AnimationStateChangePacket.STREAM_CODEC,
                AnimationStateChangePacket::handle);
        registrar.playToClient(MeltDreamCrystalChestResetCooldownSyncPacket.TYPE, MeltDreamCrystalChestResetCooldownSyncPacket.STREAM_CODEC,
                MeltDreamCrystalChestResetCooldownSyncPacket::handle);
        registrar.playToServer(StartBlueprintPlacementPacket.TYPE, StartBlueprintPlacementPacket.STREAM_CODEC,
                StartBlueprintPlacementPacket::handle);
        registrar.playToServer(BlueprintPlacePacket.TYPE, BlueprintPlacePacket.STREAM_CODEC,
                BlueprintPlacePacket::handle);
        registrar.playToServer(UpdateBlueprintPlacingPacket.TYPE, UpdateBlueprintPlacingPacket.STREAM_CODEC,
                UpdateBlueprintPlacingPacket::handle);
    }

    /** 客户端 → 服务端。 */
    public static void sendToServer(CustomPacketPayload payload) {
        PacketDistributor.sendToServer(payload);
    }

    /** 服务端 → 追踪该实体及自身的客户端 */
    public static void sendToTrackingAndSelf(net.minecraft.world.entity.Entity entity,
                                             CustomPacketPayload payload) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, payload);
    }
}
