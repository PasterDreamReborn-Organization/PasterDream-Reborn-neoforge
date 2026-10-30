package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergy;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * AttachmentType 注册中心（取代 Forge Capability）。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code capability/ModCapabilities.java}。
 * 融梦能量 / SAN 等玩家数值能力在此注册，数据类与 Helper 放 {@code capability/}。
 */
public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MeltDreamEnergy>> MELT_DREAM_ENERGY =
            ATTACHMENTS.register("melt_dream_energy", () -> AttachmentType.serializable(MeltDreamEnergy::new)
                    .copyOnDeath()
                    .sync((holder, to) -> holder == to, new StreamCodec<RegistryFriendlyByteBuf, MeltDreamEnergy>() {
                        @Override
                        public MeltDreamEnergy decode(RegistryFriendlyByteBuf buffer) {
                            MeltDreamEnergy energy = new MeltDreamEnergy();
                            energy.setMeltDreamEnergy(buffer.readDouble());
                            energy.setIsOrNotNeedConsumeDreamEnergy(buffer.readBoolean());
                            energy.setIsConsumeDoubled(buffer.readBoolean());
                            energy.setMaxMeltDreamEnergy(buffer.readDouble());
                            return energy;
                        }

                        @Override
                        public void encode(RegistryFriendlyByteBuf buffer, MeltDreamEnergy energy) {
                            buffer.writeDouble(energy.getMeltDreamEnergy());
                            buffer.writeBoolean(energy.getIsOrNotNeedConsumeDreamEnergy());
                            buffer.writeBoolean(energy.isConsumeDoubled());
                            buffer.writeDouble(energy.getMaxMeltDreamEnergy());
                        }
                    }).build());

    public static void register(IEventBus eventBus) {
        ATTACHMENTS.register(eventBus);
    }

    private ModAttachments() {
    }
}
