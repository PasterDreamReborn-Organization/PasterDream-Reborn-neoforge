package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * AttachmentType 注册中心（取代 Forge Capability）。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code capability/ModCapabilities.java}。
 * 融梦能量 / SAN 等玩家数值能力在此注册，数据类与 Helper 放 {@code capability/}。
 */
public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, PasterDreamMod.MOD_ID);

    // TODO: 在此注册 AttachmentType（builder(...).serialize(...).sync(...)）

    public static void register(IEventBus eventBus) {
        ATTACHMENTS.register(eventBus);
    }

    private ModAttachments() {
    }
}
