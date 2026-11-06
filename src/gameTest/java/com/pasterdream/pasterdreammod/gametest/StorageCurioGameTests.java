package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.event.MagicDamageHandler;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.curio.BobojiCurioItem;
import com.pasterdream.pasterdreammod.world.item.curio.ShadowBreathItem;
import com.pasterdream.pasterdreammod.world.item.storagebag.LargeStorageBagItem;
import com.pasterdream.pasterdreammod.world.item.storagebag.StorageBagItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class StorageCurioGameTests {

    @GameTest(template = "empty")
    public static void slotsAndRecipes(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (Item item : List.of(ModItems.BOBOJI_CURIO.get(), ModItems.WAR_FLAG.get(),
                ModItems.SHADOW_BREATH.get())) {
            helper.assertTrue(new ItemStack(item).is(curiosTag("curio"))
                            && CuriosApi.getItemStackSlots(new ItemStack(item), player).containsKey("curio"),
                    "饰品应能佩戴于 curio 槽: " + item);
        }
        helper.assertTrue(new ItemStack(ModItems.WIND_SPIRIT.get()).is(curiosTag("charm"))
                        && CuriosApi.getItemStackSlots(new ItemStack(ModItems.WIND_SPIRIT.get()), player)
                        .containsKey("charm"),
                "风精灵应使用 charm 槽");

        checkCraft(helper, CraftingInput.of(3, 3, List.of(
                ItemStack.EMPTY, ModItems.SPOOL.toStack(), ItemStack.EMPTY,
                ModItems.FABRIC.toStack(), ItemStack.EMPTY, ModItems.FABRIC.toStack(),
                ItemStack.EMPTY, ModItems.FABRIC.toStack(), ItemStack.EMPTY)), ModItems.STORAGE_BAG.get());
        checkCraft(helper, CraftingInput.of(3, 3, List.of(
                ItemStack.EMPTY, new ItemStack(Items.DIAMOND), ItemStack.EMPTY,
                ModItems.FABRIC.toStack(), ModItems.STORAGE_BAG.toStack(), ModItems.FABRIC.toStack(),
                ItemStack.EMPTY, new ItemStack(Items.ENDER_PEARL), ItemStack.EMPTY)),
                ModItems.LARGE_STORAGE_BAG.get());
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void storageBagContentsRoundTrip(GameTestHelper helper) {
        ItemStack bag = ModItems.STORAGE_BAG.toStack();
        List<ItemStack> items = new ArrayList<>(Collections.nCopies(StorageBagItem.SLOT_COUNT, ItemStack.EMPTY));
        items.set(3, new ItemStack(Items.DIAMOND, 5));
        StorageBagItem.saveInventory(bag, items);
        helper.assertTrue(bag.has(DataComponents.CONTAINER), "储物袋应写入 CONTAINER 组件");
        List<ItemStack> loaded = StorageBagItem.getInventory(bag);
        helper.assertTrue(loaded.size() == StorageBagItem.SLOT_COUNT
                        && loaded.get(3).is(Items.DIAMOND) && loaded.get(3).getCount() == 5,
                "储物袋内容应按槽位读写");

        ItemStack largeBag = ModItems.LARGE_STORAGE_BAG.toStack();
        List<ItemStack> largeItems = new ArrayList<>(
                Collections.nCopies(LargeStorageBagItem.SLOT_COUNT, ItemStack.EMPTY));
        largeItems.set(24, new ItemStack(Items.EMERALD, 2));
        LargeStorageBagItem.saveInventory(largeBag, largeItems);
        List<ItemStack> largeLoaded = LargeStorageBagItem.getInventory(largeBag);
        helper.assertTrue(largeLoaded.size() == LargeStorageBagItem.SLOT_COUNT
                        && largeLoaded.get(24).is(Items.EMERALD) && largeLoaded.get(24).getCount() == 2,
                "大型储物袋应支持 25 格内容");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void windSpiritAndBobojiAttributes(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        SlotContext charm = new SlotContext("charm", player, 0, false, true);
        var windModifiers = CuriosApi.getAttributeModifiers(charm, CuriosApi.getSlotId(charm),
                ModItems.WIND_SPIRIT.toStack());
        helper.assertTrue(windModifiers.containsKey(ModAttributes.ARROW_VELOCITY)
                        && windModifiers.containsKey(ModAttributes.RANGED_CHARGE_TIME_REDUCTION),
                "风精灵应提供箭矢速度与装填缩减属性");

        double baseSpeed = player.getAttributeValue(Attributes.MOVEMENT_SPEED);
        ICurioItem boboji = (BobojiCurioItem) ModItems.BOBOJI_CURIO.get();
        SlotContext curio = new SlotContext("curio", player, 0, false, true);
        boboji.onEquip(curio, ItemStack.EMPTY, ModItems.BOBOJI_CURIO.toStack());
        helper.assertTrue(Math.abs(player.getAttributeValue(ModAttributes.BLINK_COOLDOWN_REDUCTION) - 0.2) < 0.0001
                        && Math.abs(player.getAttributeValue(ModAttributes.BLINK_RANGE) - 1.1) < 0.0001
                        && Math.abs(player.getAttributeValue(ModAttributes.BLINK_CONSUME) - 0.6) < 0.0001
                        && Math.abs(player.getAttributeValue(Attributes.MOVEMENT_SPEED) - baseSpeed * 1.05) < 0.0001,
                "啵啵鸡饰品应提供瞬身术与移动速度加成");
        boboji.onUnequip(curio, ItemStack.EMPTY, ModItems.BOBOJI_CURIO.toStack());
        helper.assertTrue(Math.abs(player.getAttributeValue(ModAttributes.BLINK_COOLDOWN_REDUCTION)) < 0.0001
                        && Math.abs(player.getAttributeValue(ModAttributes.BLINK_RANGE) - 1.0) < 0.0001
                        && Math.abs(player.getAttributeValue(ModAttributes.BLINK_CONSUME) - 1.0) < 0.0001
                        && Math.abs(player.getAttributeValue(Attributes.MOVEMENT_SPEED) - baseSpeed) < 0.0001,
                "卸下啵啵鸡饰品应移除全部加成");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void shadowBreathAndWarFlagEffects(GameTestHelper helper) {
        FakePlayer player = new FakePlayer(helper.getLevel(),
                new GameProfile(UUID.randomUUID(), "shadow-breath-test"));
        SanHelper.setIsSanEnabled(player, true);
        SanHelper.setPlayerSanAndSync(player, 100);
        ICurioItem shadowBreath = (ShadowBreathItem) ModItems.SHADOW_BREATH.get();
        SlotContext curio = new SlotContext("curio", player, 0, false, true);
        shadowBreath.curioTick(curio, ModItems.SHADOW_BREATH.toStack());
        helper.assertTrue(Math.abs(player.getAttributeValue(Attributes.ATTACK_DAMAGE) - 1.2) < 0.0001
                        && Math.abs(player.getAttributeValue(ModAttributes.MAGIC_DAMAGE_RATE) - 1.2) < 0.0001,
                "满理智时暗影之息应提供 20% 攻击与魔法伤害");

        SanHelper.setPlayerSanAndSync(player, 0);
        shadowBreath.curioTick(curio, ModItems.SHADOW_BREATH.toStack());
        helper.assertTrue(Math.abs(player.getAttributeValue(Attributes.ARMOR) - 10.0) < 0.0001
                        && player.getEffect(MobEffects.REGENERATION) != null
                        && player.getEffect(MobEffects.REGENERATION).getAmplifier() == 3,
                "低理智时暗影之息应提供护甲与生命恢复 IV");

        player.setHealth(10.0F);
        float before = player.getHealth();
        ModEffects.WAR_FLAG.get().applyEffectTick(player, 1);
        helper.assertTrue(player.getHealth() > before, "战意效果应提供回血");
        player.addEffect(new MobEffectInstance(ModEffects.WAR_FLAG, 40, 0));
        helper.assertTrue(Math.abs(player.getAttributeValue(Attributes.ATTACK_DAMAGE) - 1.15) < 0.0001,
                "战意效果应提供 15% 攻击伤害");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void magicDamageRateScalesIndirectMagic(GameTestHelper helper) {
        FakePlayer player = new FakePlayer(helper.getLevel(),
                new GameProfile(UUID.randomUUID(), "magic-damage-test"));
        var magicAttr = player.getAttribute(ModAttributes.MAGIC_DAMAGE_RATE);
        magicAttr.addPermanentModifier(new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "test_magic_damage"),
                0.5, AttributeModifier.Operation.ADD_VALUE));
        DamageSource source = helper.getLevel().damageSources().indirectMagic(player, player);
        LivingIncomingDamageEvent event = new LivingIncomingDamageEvent(player,
                new DamageContainer(source, 10.0f));
        MagicDamageHandler.onIncomingMagicDamage(event);
        helper.assertTrue(Math.abs(event.getAmount() - 15.0f) < 0.0001,
                "魔法伤害倍率应作用于间接魔法伤害（含投掷伤害药水）");
        helper.succeed();
    }

    private static TagKey<Item> curiosTag(String slot) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", slot));
    }

    private static void checkCraft(GameTestHelper helper, CraftingInput input, Item expected) {
        var recipe = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
        helper.assertTrue(recipe.value().assemble(input, helper.getLevel().registryAccess()).is(expected),
                "配方应产出 " + expected);
    }
}
