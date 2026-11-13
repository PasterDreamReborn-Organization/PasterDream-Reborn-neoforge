package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class ModPaintings {

    public static final ResourceKey<PaintingVariant> PASTERDREAM_TITLE = key("pasterdream_title");
    public static final ResourceKey<PaintingVariant> PASTERDREAM_START = key("pasterdream_start");
    public static final ResourceKey<PaintingVariant> PASTERDREAM_AEROLITE_DUST = key("pasterdream_aerolite_dust");
    public static final ResourceKey<PaintingVariant> PASTERDREAM_PORTAL = key("pasterdream_portal");
    public static final ResourceKey<PaintingVariant> PASTERDREAM_TELESCOPE = key("pasterdream_telescope");
    public static final ResourceKey<PaintingVariant> PASTERDREAM_DYEDREAM_FLOWER = key("pasterdream_dyedream_flower");
    public static final ResourceKey<PaintingVariant> PASTERDREAM_DYEDREAM_STATION = key("pasterdream_dyedream_station");
    public static final ResourceKey<PaintingVariant> PASTERDREAM_PINK_YELLOW_HOUSE = key("pasterdream_pink_yellow_house");

    private static ResourceKey<PaintingVariant> key(String name) {
        return ResourceKey.create(Registries.PAINTING_VARIANT,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<PaintingVariant> context) {
        register(context, PASTERDREAM_TITLE, 64, 64);
        register(context, PASTERDREAM_START, 64, 64);
        register(context, PASTERDREAM_AEROLITE_DUST, 256, 256);
        register(context, PASTERDREAM_PORTAL, 16, 16);
        register(context, PASTERDREAM_TELESCOPE, 32, 16);
        register(context, PASTERDREAM_DYEDREAM_FLOWER, 16, 16);
        register(context, PASTERDREAM_DYEDREAM_STATION, 32, 32);
        register(context, PASTERDREAM_PINK_YELLOW_HOUSE, 32, 16);
    }

    private static void register(BootstrapContext<PaintingVariant> context, ResourceKey<PaintingVariant> key,
                                 int width, int height) {
        context.register(key, new PaintingVariant(width / 16, height / 16, key.location()));
    }
}
