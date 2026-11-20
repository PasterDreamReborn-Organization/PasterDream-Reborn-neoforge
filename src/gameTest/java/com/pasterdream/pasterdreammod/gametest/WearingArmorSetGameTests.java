package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.advancement.critereon.WearingArmorSetTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.WearingArmorSetTrigger.ArmorSet;
import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.Map;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class WearingArmorSetGameTests {

    private static void equip(Player player, List<Item> armor) {
        var slots = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
        for (int i = 0; i < armor.size(); i++) {
            player.setItemSlot(slots.get(i), new ItemStack(armor.get(i)));
        }
    }

    @GameTest(template = "empty")
    public static void wearingArmorSetTriggerRegistered(GameTestHelper helper) {
        var trigger = ModCriteriaTriggers.WEARING_ARMOR_SET.get();
        helper.assertTrue(BuiltInRegistries.TRIGGER_TYPES.getKey(trigger)
                        .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wearing_armor_set")),
                "穿戴全套护甲触发器应以 wearing_armor_set 注册");
        helper.assertTrue(ArmorSet.values().length == 3, "应只登记三套可穿戴护甲");
        for (ArmorSet set : ArmorSet.values()) {
            helper.assertTrue(set.id() != null && !set.id().isEmpty(), "套装应带注册用 id: " + set.name());
        }
        helper.assertTrue(ArmorSet.MELT_DREAM.id().equals("melt_dream")
                        && ArmorSet.SHADOW_ALLOY.id().equals("shadow_alloy")
                        && ArmorSet.FLUFFY_WIND_ALLOY.id().equals("fluffy_wind_alloy"),
                "套装 id 应与进度 JSON 中的 set 字段一致");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void armorSetAdvancementsDecode(GameTestHelper helper) {
        var advancements = helper.getLevel().getServer().getAdvancements();
        var expected = Map.of(
                "story/melt_dream_full_set", ArmorSet.MELT_DREAM,
                "story/shadow_alloy_full_set", ArmorSet.SHADOW_ALLOY,
                "story/fluffy_wind_alloy_full_set", ArmorSet.FLUFFY_WIND_ALLOY);
        for (var entry : expected.entrySet()) {
            String path = entry.getKey();
            ArmorSet set = entry.getValue();
            var advancement = advancements.get(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path));
            helper.assertTrue(advancement != null, "应存在进度: " + path);
            var criterion = advancement.value().criteria().get("wearing_full_set");
            helper.assertTrue(criterion != null, "进度应以 wearing_full_set 判定: " + path);
            helper.assertTrue(criterion.trigger() == ModCriteriaTriggers.WEARING_ARMOR_SET.get(),
                    "进度应使用 wearing_armor_set 触发器: " + path);
            helper.assertTrue(criterion.triggerInstance() instanceof WearingArmorSetTrigger.TriggerInstance instance
                            && instance.set() == set,
                    "进度 set 字段应解码为 " + set.id() + ": " + path);
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void wearingArmorSetSemantics(GameTestHelper helper) {
        var meltDream = List.of(ModItems.MELT_DREAM_HELMET.get(), ModItems.MELT_DREAM_CHESTPLATE.get(),
                ModItems.MELT_DREAM_LEGGINGS.get(), ModItems.MELT_DREAM_BOOTS.get());
        var shadow = List.of(ModItems.SHADOW_ALLOY_HELMET.get(), ModItems.SHADOW_ALLOY_CHESTPLATE.get(),
                ModItems.SHADOW_ALLOY_LEGGINGS.get(), ModItems.SHADOW_ALLOY_BOOTS.get());
        var fluffyWind = List.of(ModItems.FLUFFY_WIND_ALLOY_HELMET.get(), ModItems.FLUFFY_WIND_ALLOY_CHESTPLATE.get(),
                ModItems.FLUFFY_WIND_ALLOY_LEGGINGS.get(), ModItems.FLUFFY_WIND_ALLOY_BOOTS.get());

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (ArmorSet set : ArmorSet.values()) {
            helper.assertFalse(set.isWearing(player), "空装备时不应判定为穿齐: " + set.id());
        }

        equip(player, meltDream);
        helper.assertTrue(ArmorSet.MELT_DREAM.isWearing(player), "穿齐融梦水晶四件应判定为穿齐");
        helper.assertFalse(ArmorSet.SHADOW_ALLOY.isWearing(player), "穿着融梦套装时不应判定暗影合金套装");
        helper.assertFalse(ArmorSet.FLUFFY_WIND_ALLOY.isWearing(player), "穿着融梦套装时不应判定萦风合金套装");

        player.setItemSlot(EquipmentSlot.FEET, new ItemStack(shadow.get(3)));
        helper.assertFalse(ArmorSet.MELT_DREAM.isWearing(player), "混入一件其他套装后不应判定为穿齐");

        equip(player, shadow);
        helper.assertTrue(ArmorSet.SHADOW_ALLOY.isWearing(player), "穿齐暗影合金四件应判定为穿齐");
        helper.assertFalse(ArmorSet.MELT_DREAM.isWearing(player), "穿着暗影合金套装时不应判定融梦水晶套装");

        equip(player, fluffyWind);
        helper.assertTrue(ArmorSet.FLUFFY_WIND_ALLOY.isWearing(player), "穿齐萦风合金四件应判定为穿齐");
        helper.assertFalse(ArmorSet.SHADOW_ALLOY.isWearing(player), "穿着萦风合金套装时不应判定暗影合金套装");
        helper.succeed();
    }
}
