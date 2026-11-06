package com.pasterdream.pasterdreammod.datagen.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/** 保留原版引雷定义，仅增加萦风弓作为可附魔物品。 */
public class ModChannelingEnchantmentProvider implements DataProvider {
    private final Path output;

    public ModChannelingEnchantmentProvider(PackOutput output) {
        this.output = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve("minecraft/enchantment/channeling.json");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        InputStream stream = getClass().getClassLoader()
                .getResourceAsStream("data/minecraft/enchantment/channeling.json");
        if (stream == null) throw new IllegalStateException("Missing locked Minecraft channeling enchantment resource");
        JsonObject channeling;
        try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            channeling = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (java.io.IOException e) {
            throw new IllegalStateException("Could not read Minecraft channeling enchantment resource", e);
        }
        channeling.addProperty("supported_items", "#pasterdream:channeling_enchantable");
        return DataProvider.saveStable(cache, channeling, output);
    }

    @Override
    public String getName() {
        return "Channeling for Fluffy Wind Bow";
    }
}
