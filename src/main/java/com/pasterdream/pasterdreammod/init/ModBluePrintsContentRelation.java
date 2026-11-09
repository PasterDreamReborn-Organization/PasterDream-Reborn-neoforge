package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.helper.localnbtreader.LocalNBTReader;
import com.pasterdream.pasterdreammod.world.item.blueprints.BluePrintRegistry;
import net.minecraft.network.chat.Component;

/**
 * 蓝图内容注册（精铸工坊 / 暗影高炉）。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModBluePrintsContentRelation.java}。
 */
public final class ModBluePrintsContentRelation {
    public static void registerBluePrintsContentRelation() {
        BluePrintRegistry.register("精铸工坊", Component.translatable("book.pasterdream.title.精铸工坊"), LocalNBTReader.WEAPON_WORKSHOP_MATERIAL, LocalNBTReader.WEAPON_WORKSHOP_RESULT);
        BluePrintRegistry.register("暗影高炉", Component.translatable("book.pasterdream.title.暗影高炉"), LocalNBTReader.SHADOW_BLAST_FURNACE_MATERIAL, LocalNBTReader.SHADOW_BLAST_FURNACE_RESULT);
    }

    private ModBluePrintsContentRelation() {
    }
}
