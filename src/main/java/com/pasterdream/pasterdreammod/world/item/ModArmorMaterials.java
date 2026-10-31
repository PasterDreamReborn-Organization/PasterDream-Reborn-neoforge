package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;

public class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, PasterDreamMod.MOD_ID);

    // 钛金：保留来源的防护、附魔、修复、韧性和击退抗性；1.21 耐久由物品属性设置。
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TITANIUM = ARMOR_MATERIALS.register("titanium",
            () -> new ArmorMaterial(Map.of(
                    ArmorItem.Type.HELMET, 3,
                    ArmorItem.Type.CHESTPLATE, 8,
                    ArmorItem.Type.LEGGINGS, 6,
                    ArmorItem.Type.BOOTS, 3),
                    17, SoundEvents.ARMOR_EQUIP_DIAMOND,
                    () -> Ingredient.of(ModItems.TITANIUM_INGOT.get()),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium"))),
                    3.0F, 0.1F));

    // 染梦合金：保留来源的防御、耐久倍率、附魔值、韧性、击退抗性与合金锭修复。
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DYEDREAM = ARMOR_MATERIALS.register("dyedream",
            () -> new ArmorMaterial(Map.of(
                    ArmorItem.Type.HELMET, 3,
                    ArmorItem.Type.CHESTPLATE, 8,
                    ArmorItem.Type.LEGGINGS, 6,
                    ArmorItem.Type.BOOTS, 3),
                    22, SoundEvents.ARMOR_EQUIP_DIAMOND,
                    () -> Ingredient.of(ModItems.DYEDREAM_ALLOY_INGOT.get()),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream"))),
                    3.0F, 0.1F));

    // 回响：来源防御 3/8/6/3、耐久系数 42、附魔值 9、韧性 3.5、击退抗性 0.15，回响之心修复。
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SCULK = ARMOR_MATERIALS.register("sculk",
            () -> new ArmorMaterial(Map.of(
                    ArmorItem.Type.HELMET, 3,
                    ArmorItem.Type.CHESTPLATE, 8,
                    ArmorItem.Type.LEGGINGS, 6,
                    ArmorItem.Type.BOOTS, 3),
                    9, Holder.direct(SoundEvents.EMPTY),
                    () -> Ingredient.of(ModItems.SCULK_HEART.get()),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sculk"))),
                    3.5F, 0.15F));

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}
