package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.armoritem.MeltDreamCrystalArmorHandler;
import com.pasterdream.pasterdreammod.world.item.armoritem.MeltDreamCrystalArmorItem;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class CopperMeltArmorGameTests {
    @GameTest(template = "empty")
    public static void copperPropertiesAndRecipe(GameTestHelper helper) {
        var armor = List.of(ModItems.COPPER_HELMET.get(), ModItems.COPPER_CHESTPLATE.get(),
                ModItems.COPPER_LEGGINGS.get(), ModItems.COPPER_BOOTS.get());
        int[] defense = {2, 5, 3, 1};
        int[] durability = {154, 224, 210, 182};
        var tags = List.of(ItemTags.HEAD_ARMOR, ItemTags.CHEST_ARMOR, ItemTags.LEG_ARMOR, ItemTags.FOOT_ARMOR);
        for (int i = 0; i < armor.size(); i++) {
            var item = (ArmorItem) armor.get(i);
            var stack = new ItemStack(item);
            helper.assertTrue(item.getDefense() == defense[i] && stack.getMaxDamage() == durability[i],
                    "铜装备防御或耐久错误");
            helper.assertTrue(item.getEnchantmentValue() == 8 && stack.is(tags.get(i)),
                    "铜装备附魔值或标签错误");
            helper.assertTrue(item.isValidRepairItem(stack, new ItemStack(Items.COPPER_INGOT)),
                    "铜锭应能修复铜装备");
        }
        var tools = List.of(ModItems.COPPER_SWORD.get(), ModItems.COPPER_PICKAXE.get(),
                ModItems.COPPER_AXE.get(), ModItems.COPPER_SHOVEL.get(), ModItems.COPPER_HOE.get());
        var toolTags = List.of(ItemTags.SWORDS, ItemTags.PICKAXES, ItemTags.AXES, ItemTags.SHOVELS, ItemTags.HOES);
        for (int i = 0; i < tools.size(); i++) {
            var stack = new ItemStack(tools.get(i));
            helper.assertTrue(stack.getMaxDamage() == 225 && stack.is(toolTags.get(i)),
                    "铜工具耐久或标签错误");
        }
        var input = CraftingInput.of(1, 3, List.of(new ItemStack(Items.COPPER_INGOT),
                new ItemStack(Items.COPPER_INGOT), new ItemStack(Items.STICK)));
        var recipe = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
        helper.assertTrue(recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "copper_sword")),
                "铜剑配方 ID 错误");
        helper.assertTrue(recipe.value().assemble(input, helper.getLevel().registryAccess()).is(ModItems.COPPER_SWORD.get()),
                "铜剑配方产物错误");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void meltArmorAttributesAndSet(GameTestHelper helper) {
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "melt-armor-test"));
        var armor = List.of(ModItems.MELT_DREAM_HELMET.get(), ModItems.MELT_DREAM_CHESTPLATE.get(),
                ModItems.MELT_DREAM_LEGGINGS.get(), ModItems.MELT_DREAM_BOOTS.get());
        var slots = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
        int[] defense = {4, 9, 7, 4};
        int[] durability = {495, 720, 675, 585};
        for (int i = 0; i < armor.size(); i++) {
            var item = (ArmorItem) armor.get(i);
            var stack = new ItemStack(item);
            helper.assertTrue(item.getDefense() == defense[i] && stack.getMaxDamage() == durability[i],
                    "融梦护甲防御或耐久错误");
            helper.assertTrue(stack.has(DataComponents.FIRE_RESISTANT) && item.getEnchantmentValue() == 18,
                    "融梦护甲防火或附魔值错误");
            helper.assertTrue(item.isValidRepairItem(stack, ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.toStack()),
                    "融梦碎片应能修复护甲");
            var modifiers = stack.getAttributeModifiers().modifiers();
            helper.assertTrue(modifiers.stream().anyMatch(x -> x.attribute().equals(Attributes.MAX_HEALTH)
                            && x.modifier().amount() == 2.0)
                            && modifiers.stream().anyMatch(x -> x.attribute().equals(ModAttributes.MAX_MELT_DREAM_ENERGY_EXTRA)
                            && x.modifier().amount() == 5.0)
                            && modifiers.stream().anyMatch(x -> x.attribute().equals(ModAttributes.MELT_DREAM_VARIABILITY)
                            && x.modifier().amount() == 1.0),
                    "融梦护甲逐件属性错误");
            player.setItemSlot(slots.get(i), stack);
        }
        helper.assertTrue(MeltDreamCrystalArmorItem.hasFullSet(player), "融梦整套识别失败");
        player.tick();
        helper.assertTrue(MeltDreamCrystalArmorItem.hasFullSet(player) && !player.isSpectator(),
                "玩家 tick 后应保持融梦整套与生存模式");
        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        energy.setMeltDreamEnergy(60);
        player.tickCount = 10;
        player.getItemBySlot(EquipmentSlot.HEAD).setDamageValue(2);
        MeltDreamCrystalArmorHandler.onPlayerTick(new PlayerTickEvent.Post(player));
        helper.assertTrue(player.hasEffect(ModEffects.MELT_DREAM_CRYSTAL_ARMOR) && player.hasEffect(ModEffects.DYEDREAM_UP),
                "融梦整套效果未施加，融梦=" + player.hasEffect(ModEffects.MELT_DREAM_CRYSTAL_ARMOR)
                        + " 染梦=" + player.hasEffect(ModEffects.DYEDREAM_UP));
        helper.assertTrue(player.getItemBySlot(EquipmentSlot.HEAD).getDamageValue() == 1
                        && Math.abs(energy.getMeltDreamEnergy() - 59.995) < 0.0001,
                "融梦整套修补费用错误");
        helper.assertTrue(player.getAttributeValue(ModAttributes.SAN_VARIABILITY) == 1.2,
                "高能量理智光环错误");
        player.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
        MeltDreamCrystalArmorHandler.onPlayerTick(new PlayerTickEvent.Post(player));
        helper.assertTrue(player.getAttributeValue(ModAttributes.SAN_VARIABILITY) == 0,
                "脱下融梦护甲后理智光环未移除");
        helper.succeed();
    }
}
