package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforgespi.language.IModFileInfo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * 将模组自带的可选资源包注册为内置资源包，使其在正式环境中也能被资源包界面检测到。
 * 资源包位于 jar 内的 {@code resourcepacks/} 目录下。
 */
public final class ModResourcePacks
{
    public static final String LEGACY_TEXTURES_PACK_ID = PasterDreamMod.MOD_ID + ":legacy_textures";
    private static final String LEGACY_TEXTURES_PATH = "resourcepacks/pasterdream_legacy_textures";

    public static final String VANILLA_UI_PACK_ID = PasterDreamMod.MOD_ID + ":vanilla_ui";
    private static final String VANILLA_UI_PATH = "resourcepacks/paster_vanilla_ui";

    private ModResourcePacks() {}

    public static void onAddPackFinders(AddPackFindersEvent event)
    {
        if (event.getPackType() != PackType.CLIENT_RESOURCES)
            return;

        IModFileInfo modFileInfo = ModList.get().getModFileById(PasterDreamMod.MOD_ID);
        if (modFileInfo == null)
            return;

        registerPack(event, modFileInfo, LEGACY_TEXTURES_PACK_ID, LEGACY_TEXTURES_PATH,
                "pack.pasterdream.legacy_textures");
        registerPack(event, modFileInfo, VANILLA_UI_PACK_ID, VANILLA_UI_PATH,
                "pack.pasterdream.vanilla_ui");
    }

    private static void registerPack(AddPackFindersEvent event, IModFileInfo modFileInfo,
                                     String packId, String path, String titleKey)
    {
        Path packRoot = modFileInfo.getFile().findResource(path);
        if (packRoot == null || !Files.exists(packRoot))
            return;

        event.addRepositorySource(consumer ->
        {
            Pack pack = Pack.readMetaAndCreate(
                    new PackLocationInfo(packId, Component.translatable(titleKey), PackSource.BUILT_IN, Optional.empty()),
                    BuiltInPackSource.fromName(location -> new PathPackResources(location, packRoot)),
                    PackType.CLIENT_RESOURCES,
                    new PackSelectionConfig(false, Pack.Position.TOP, false));
            if (pack != null)
                consumer.accept(pack);
        });
    }
}
