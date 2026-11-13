package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.event.PlayerEvents;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.pasterdream.pasterdreammod.world.item.curio.RedDewRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.MagnifyingGlassOfSherryHandler;
import com.pasterdream.pasterdreammod.world.item.curio.StrikeRingItem;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.common.util.FakePlayer;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class CurioGameTests {
    @GameTest(template = "empty")
    public static void mementoConsumablesAndWearableSlots(GameTestHelper helper) {
        ServerPlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "memento-test"));
        SanHelper.setPlayerMaxSanAndSync(player, 100);
        SanHelper.setPlayerSanAndSync(player, 20);
        MeltDreamEnergyHelper.setPlayerMaxMeltDreamEnergyAndSync(player, 100);
        MeltDreamEnergyHelper.setPlayerMeltDreamEnergyAndSync(player, 20);
        player.setItemInHand(InteractionHand.MAIN_HAND, ModItems.ORIGIN_DREAM_DICTIONARY.toStack());
        ModItems.ORIGIN_DREAM_DICTIONARY.get().use(player.level(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(SanHelper.getPlayerSan(player) == 30
                        && MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) == 30,
                "起源之梦辞典应各恢复 10 点 SAN 与融梦能量");
        helper.assertTrue(player.hasEffect(ModEffects.MEMENTO)
                        && player.getAttributeValue(Attributes.LUCK) >= 10,
                "辞典应赋予 3 分钟的幸运 +10 效果");
        helper.assertTrue(player.getMainHandItem().isEmpty(), "生存模式使用辞典应消耗一件");

        player.setItemInHand(InteractionHand.MAIN_HAND, ModItems.EMPTY_VESSEL.toStack());
        ModItems.EMPTY_VESSEL.get().use(player.level(), player, InteractionHand.MAIN_HAND);
        double san = SanHelper.getPlayerSan(player);
        helper.assertTrue(san == 0 || san == 50, "空容器应按来源概率清空 SAN 或恢复上限的 20%");
        helper.assertTrue(player.getMainHandItem().isEmpty(), "生存模式使用空容器应消耗一件");

        helper.assertTrue(ModItems.MAGNIFYING_GLASS_OF_SHERRY.toStack().is(curiosTag("charm")),
                "大侦探的放大镜应能装入 charm 槽");
        helper.assertTrue(ModItems.IRIDESCENT_FAIRY_JADE.toStack().is(curiosTag("curio")),
                "虹彩仙玉应能装入 curio 槽");

        var inventory = CuriosApi.getCuriosInventory(player).orElseThrow();
        inventory.setEquippedCurio("charm", 0, ModItems.MAGNIFYING_GLASS_OF_SHERRY.toStack());
        var victim = helper.makeMockPlayer(GameType.SURVIVAL);
        var source = helper.getLevel().damageSources().playerAttack(player);
        var incoming = new LivingIncomingDamageEvent(victim, new DamageContainer(source, 4.0F));
        MagnifyingGlassOfSherryHandler.onMeleeDamage(incoming);
        helper.assertTrue(incoming.getAmount() == 12.0F, "佩戴放大镜空手攻击应造成 3 倍伤害");

        var jadeSlot = new SlotContext("curio", player, 0, false, true);
        var jadeModifiers = CuriosApi.getAttributeModifiers(jadeSlot, CuriosApi.getSlotId(jadeSlot),
                ModItems.IRIDESCENT_FAIRY_JADE.toStack());
        helper.assertTrue(jadeModifiers.get(ModAttributes.ARROW_VELOCITY).stream()
                        .anyMatch(modifier -> modifier.amount() == 0.5),
                "虹彩仙玉应提供 0.5 点箭矢速度属性");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void curioSlotsAndRecipes(GameTestHelper helper) {
        for (Item item : List.of(ModItems.GOLD_CHARM.get(), ModItems.ENDEYE_CHARM.get(),
                ModItems.SEA_CHARM.get(), ModItems.CARAPAX_CHARM.get(), ModItems.WORLDTREE_SEEDPOD.get())) {
            helper.assertTrue(new ItemStack(item).is(curiosTag("charm")), "护身符必须能装入 charm 槽: " + item);
        }
        for (Item item : List.of(ModItems.GARLAND.get(), ModItems.SNOW_VOW_HEAD.get())) {
            helper.assertTrue(new ItemStack(item).is(curiosTag("head")), "头饰必须能装入 head 槽: " + item);
        }

        checkCraft(helper, CraftingInput.of(2, 1, List.of(ModItems.EMBRYO_CHARM.toStack(),
                new ItemStack(Items.GOLD_INGOT))), ModItems.GOLD_CHARM.get());
        checkCraft(helper, CraftingInput.of(3, 1, List.of(ModItems.EMBRYO_CHARM.toStack(),
                new ItemStack(Items.ENDER_EYE), new ItemStack(Items.CHORUS_FLOWER))), ModItems.ENDEYE_CHARM.get());
        checkCraft(helper, CraftingInput.of(3, 3, List.of(
                new ItemStack(Items.SEAGRASS), new ItemStack(Items.TURTLE_SCUTE), new ItemStack(Items.SEAGRASS),
                new ItemStack(Items.TURTLE_SCUTE), ModItems.EMBRYO_CHARM.toStack(), new ItemStack(Items.TURTLE_SCUTE),
                new ItemStack(Items.SEAGRASS), new ItemStack(Items.TURTLE_SCUTE), new ItemStack(Items.SEAGRASS))),
                ModItems.CARAPAX_CHARM.get());
        checkCraft(helper, CraftingInput.of(3, 3, List.of(
                new ItemStack(Items.POPPY), new ItemStack(Items.DANDELION), new ItemStack(Items.BLUE_ORCHID),
                new ItemStack(Items.ALLIUM), ItemStack.EMPTY, new ItemStack(Items.AZURE_BLUET),
                new ItemStack(Items.RED_TULIP), new ItemStack(Items.ORANGE_TULIP), new ItemStack(Items.WHITE_TULIP))),
                ModItems.GARLAND.get());
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void snowVowClearsFireAndFreezing(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        double initialLuck = player.getAttributeValue(Attributes.LUCK);
        player.addEffect(new MobEffectInstance(ModEffects.SNOW_VOW, 40));
        helper.assertTrue(player.getAttributeValue(Attributes.LUCK) == initialLuck + 3.0,
                "雪下的誓言应增加 3 点幸运值");
        player.setRemainingFireTicks(100);
        player.setTicksFrozen(100);
        ModEffects.SNOW_VOW.get().applyEffectTick(player, 0);
        helper.assertTrue(player.getRemainingFireTicks() <= 0 && player.getTicksFrozen() == 0,
                "雪下的誓言应清除燃烧与冻结");
        player.removeEffect(ModEffects.SNOW_VOW);
        helper.assertTrue(player.getAttributeValue(Attributes.LUCK) == initialLuck,
                "移除效果后幸运值应恢复");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void attributeCuriosExposeModifiers(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        var slots = CuriosApi.getEntitySlots(player);
        helper.assertTrue(slots.keySet().containsAll(List.of("head", "necklace", "back", "body",
                        "ring", "belt", "charm", "curio")) && slots.get("ring").getSize() == 2,
                "玩家应获得来源的八类槽位，戒指槽应有两个位置");
        for (Item item : List.of(ModItems.SEA_CHARM.get(), ModItems.CARAPAX_CHARM.get())) {
            ItemStack stack = new ItemStack(item);
            helper.assertTrue(CuriosApi.getItemStackSlots(stack, player).containsKey("charm"),
                    "属性饰品应识别 charm 槽: " + item);
            var slot = new SlotContext("charm", player, 0, false, true);
            var modifiers = CuriosApi.getAttributeModifiers(slot, CuriosApi.getSlotId(slot), stack);
            helper.assertTrue(modifiers.size() == (item == ModItems.SEA_CHARM.get() ? 2 : 3),
                    "Curios 应读取全部属性修饰符: " + item);
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void butterflyAndNecklaceSlotsAndRecipe(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (Item item : List.of(ModItems.LIGHT_BUTTERFLY_CURIO.get(), ModItems.BRIGHT_BUTTERFLY_CURIO.get())) {
            helper.assertTrue(new ItemStack(item).is(curiosTag("curio"))
                            && CuriosApi.getItemStackSlots(new ItemStack(item), player).containsKey("curio"),
                    "蝴蝶饰品应能佩戴于 curio 槽: " + item);
        }
        helper.assertTrue(new ItemStack(ModItems.QYM_BUTTERFLY_STAR_HAIRPIN.get()).is(curiosTag("head")),
                "琴雨梦发卡应使用 head 槽");
        for (Item item : List.of(ModItems.RABBIT_FOOT_NECKLACE.get(), ModItems.FEATHER_NECKLACE.get(),
                ModItems.HEALTH_NECKLACE.get(), ModItems.FIRE_NECKLACE.get(), ModItems.CROSS_NECKLACE.get())) {
            helper.assertTrue(new ItemStack(item).is(curiosTag("necklace"))
                            && CuriosApi.getItemStackSlots(new ItemStack(item), player).containsKey("necklace"),
                    "项链应能佩戴于 necklace 槽: " + item);
        }
        checkCraft(helper, CraftingInput.of(3, 2, List.of(ModItems.EMBRYO_NECKLACE.toStack(),
                new ItemStack(Items.RABBIT_FOOT), new ItemStack(Items.POWDER_SNOW_BUCKET),
                new ItemStack(Items.DIAMOND), new ItemStack(Items.NETHER_WART), ItemStack.EMPTY)),
                ModItems.RABBIT_FOOT_NECKLACE.get());
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void butterflyAndNecklaceEffects(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        var inventory = CuriosApi.getCuriosInventory(player).orElseThrow();
        inventory.setEquippedCurio("head", 0, ModItems.QYM_BUTTERFLY_STAR_HAIRPIN.toStack());
        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        zombie.setTarget(player);
        helper.assertTrue(zombie.getTarget() == null, "琴雨梦发卡应取消生物主动锁定");
        zombie.setLastHurtByMob(player);
        zombie.setTarget(player);
        helper.assertTrue(zombie.getTarget() == player, "被玩家攻击的生物应允许反击");

        inventory.setEquippedCurio("curio", 0, ModItems.BRIGHT_BUTTERFLY_CURIO.toStack());
        helper.assertFalse(player.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.DARKNESS, 40)),
                "光明飞蝶应免疫黑暗");
        helper.assertFalse(player.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.BLINDNESS, 40)),
                "光明飞蝶应免疫失明");
        var bright = (top.theillusivec4.curios.api.type.capability.ICurioItem) ModItems.BRIGHT_BUTTERFLY_CURIO.get();
        bright.curioTick(new SlotContext("curio", player, 0, false, true), ModItems.BRIGHT_BUTTERFLY_CURIO.toStack());
        helper.assertTrue(player.hasEffect(net.minecraft.world.effect.MobEffects.NIGHT_VISION),
                "光明飞蝶应提供夜视");

        inventory.setEquippedCurio("necklace", 0, ModItems.CROSS_NECKLACE.toStack());
        player.addEffect(new MobEffectInstance(ModEffects.EVASION, 6));
        var cross = (top.theillusivec4.curios.api.type.capability.ICurioItem) ModItems.CROSS_NECKLACE.get();
        cross.curioTick(new SlotContext("necklace", player, 0, false, true), ModItems.CROSS_NECKLACE.toStack());
        helper.assertTrue(player.getEffect(ModEffects.EVASION).getDuration() == 10,
                "十字章项链应把回避延长到 10 tick");

        inventory.setEquippedCurio("necklace", 0, ModItems.FEATHER_NECKLACE.toStack());
        var feather = (top.theillusivec4.curios.api.type.capability.ICurioItem) ModItems.FEATHER_NECKLACE.get();
        feather.onEquip(new SlotContext("necklace", player, 0, false, true), ItemStack.EMPTY,
                ModItems.FEATHER_NECKLACE.toStack());
        helper.assertTrue(Math.abs(player.getAttributeValue(com.pasterdream.pasterdreammod.init.ModAttributes.BLINK_CONSUME) - 0.95) < 0.0001
                        && Math.abs(player.getAttributeValue(com.pasterdream.pasterdreammod.init.ModAttributes.BLINK_RANGE) - 1.2) < 0.0001,
                "轻羽护符应调整瞬身术消耗和距离");
        inventory.setEquippedCurio("necklace", 0, ModItems.HEALTH_NECKLACE.toStack());
        feather.onUnequip(new SlotContext("necklace", player, 0, false, true), ModItems.HEALTH_NECKLACE.toStack(),
                ModItems.FEATHER_NECKLACE.toStack());
        var health = (top.theillusivec4.curios.api.type.capability.ICurioItem) ModItems.HEALTH_NECKLACE.get();
        health.onEquip(new SlotContext("necklace", player, 0, false, true), ItemStack.EMPTY,
                ModItems.HEALTH_NECKLACE.toStack());
        helper.assertTrue(player.getMaxHealth() == 22.0F, "生命项链应增加 2 点生命上限");
        health.curioTick(new SlotContext("necklace", player, 0, false, true), ModItems.HEALTH_NECKLACE.toStack());
        helper.assertTrue(player.hasEffect(net.minecraft.world.effect.MobEffects.REGENERATION),
                "生命项链应提供生命恢复");
        helper.assertTrue(player.getAttributeValue(com.pasterdream.pasterdreammod.init.ModAttributes.BLINK_CONSUME) == 1.0,
                "卸下轻羽护符应移除属性修饰符");
        health.onUnequip(new SlotContext("necklace", player, 0, false, true), ModItems.FIRE_NECKLACE.toStack(),
                ModItems.HEALTH_NECKLACE.toStack());
        helper.assertTrue(player.getMaxHealth() == 20.0F, "卸下生命项链应移除生命上限加成");

        helper.setBlock(new BlockPos(2, 1, 2), Blocks.STONE);
        helper.setBlock(new BlockPos(2, 2, 2), Blocks.AIR);
        player.setPos(helper.absolutePos(new BlockPos(2, 2, 2)).getCenter());
        inventory.setEquippedCurio("necklace", 0, ModItems.FIRE_NECKLACE.toStack());
        var fire = (top.theillusivec4.curios.api.type.capability.ICurioItem) ModItems.FIRE_NECKLACE.get();
        fire.curioTick(new SlotContext("necklace", player, 0, false, true), ModItems.FIRE_NECKLACE.toStack());
        helper.assertTrue(helper.getLevel().getBlockState(player.blockPosition()).is(Blocks.FIRE)
                        && player.hasEffect(net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE),
                "业火项链应留火并提供防火");

        inventory.setEquippedCurio("necklace", 0, ModItems.RABBIT_FOOT_NECKLACE.toStack());
        var rabbit = (top.theillusivec4.curios.api.type.capability.ICurioItem) ModItems.RABBIT_FOOT_NECKLACE.get();
        rabbit.onEquip(new SlotContext("necklace", player, 0, false, true), ItemStack.EMPTY,
                ModItems.RABBIT_FOOT_NECKLACE.toStack());
        rabbit.curioTick(new SlotContext("necklace", player, 0, false, true), ModItems.RABBIT_FOOT_NECKLACE.toStack());
        helper.assertTrue(player.getAttributeValue(Attributes.LUCK) == 1.0
                        && player.getEffect(net.minecraft.world.effect.MobEffects.JUMP).getAmplifier() == 1,
                "雪兔脚项链应提供跳跃提升 II 与一点幸运");
        rabbit.onUnequip(new SlotContext("necklace", player, 0, false, true), ItemStack.EMPTY,
                ModItems.RABBIT_FOOT_NECKLACE.toStack());
        helper.assertTrue(player.getAttributeValue(Attributes.LUCK) == 0.0,
                "卸下雪兔脚项链应移除幸运加成");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void lightButterflyUsesEnergyInDarkness(GameTestHelper helper) {
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 1; z <= 3; z++) {
                    helper.setBlock(new BlockPos(x, y, z),
                            x == 2 && y == 2 && z == 2 ? Blocks.AIR : Blocks.STONE);
                }
            }
        }
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "light-butterfly"));
        player.setPos(helper.absolutePos(new BlockPos(2, 2, 2)).getCenter());
        MeltDreamEnergyHelper.setPlayerMeltDreamEnergyAndSync(player, 1.0);
        helper.runAfterDelay(10, () -> {
            helper.assertTrue(helper.getLevel().getMaxLocalRawBrightness(player.blockPosition()) <= 7,
                    "封闭测试空间应满足夜明蝶的低亮度条件");
            var light = (top.theillusivec4.curios.api.type.capability.ICurioItem) ModItems.LIGHT_BUTTERFLY_CURIO.get();
            light.curioTick(new SlotContext("curio", player, 0, false, true), ModItems.LIGHT_BUTTERFLY_CURIO.toStack());
            helper.assertTrue(Math.abs(MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) - 0.996) < 0.0001
                            && player.hasEffect(net.minecraft.world.effect.MobEffects.NIGHT_VISION),
                    "夜明蝶应消耗 0.004 融梦能量并提供夜视");
            helper.succeed();
        });
    }

    @GameTest(template = "empty")
    public static void beltsAndRingsSlotsAndRecipes(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (Item item : List.of(ModItems.NATURE_BELT.get(), ModItems.TRAVELER_BELT.get(),
                ModItems.DREAM_TRAVELER_BELT.get())) {
            helper.assertTrue(new ItemStack(item).is(curiosTag("belt"))
                            && CuriosApi.getItemStackSlots(new ItemStack(item), player).containsKey("belt"),
                    "腰带应能佩戴于 belt 槽: " + item);
        }
        for (Item item : List.of(ModItems.STRIKE_RING.get(), ModItems.COUNTER_RING.get(),
                ModItems.MELT_DREAM_ENERGY_RING.get())) {
            helper.assertTrue(new ItemStack(item).is(curiosTag("ring"))
                            && CuriosApi.getItemStackSlots(new ItemStack(item), player).containsKey("ring"),
                    "戒指应能佩戴于 ring 槽: " + item);
        }

        checkCraft(helper, CraftingInput.of(3, 3, List.of(
                new ItemStack(Items.GLOW_BERRIES), new ItemStack(Items.VINE), new ItemStack(Items.GLOW_BERRIES),
                new ItemStack(Items.VINE), ItemStack.EMPTY, new ItemStack(Items.VINE),
                new ItemStack(Items.GLOW_BERRIES), new ItemStack(Items.VINE), new ItemStack(Items.GLOW_BERRIES))),
                ModItems.NATURE_BELT.get());
        checkCraft(helper, CraftingInput.of(3, 3, List.of(
                ItemStack.EMPTY, ModItems.FABRIC.toStack(), ItemStack.EMPTY,
                new ItemStack(Items.LEATHER), ModItems.EMBRYO_BELT.toStack(), new ItemStack(Items.LEATHER),
                ItemStack.EMPTY, new ItemStack(Items.DIAMOND), ItemStack.EMPTY)), ModItems.TRAVELER_BELT.get());
        checkCraft(helper, CraftingInput.of(3, 3, List.of(
                ItemStack.EMPTY, ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.toStack(), ItemStack.EMPTY,
                ModItems.DYEDREAM_ALLOY_NUGGET.toStack(), ModItems.EMBRYO_RING.toStack(), ModItems.DYEDREAM_ALLOY_NUGGET.toStack(),
                ItemStack.EMPTY, ModItems.DYEDREAM_ALLOY_NUGGET.toStack(), ItemStack.EMPTY)),
                ModItems.MELT_DREAM_ENERGY_RING.get());

        ItemStack lv1 = craft(helper, CraftingInput.of(3, 3, List.of(
                ItemStack.EMPTY, new ItemStack(Items.BLAZE_POWDER), ItemStack.EMPTY,
                new ItemStack(Items.COPPER_INGOT), ModItems.EMBRYO_RING.toStack(), new ItemStack(Items.COPPER_INGOT),
                ItemStack.EMPTY, new ItemStack(Items.COPPER_INGOT), ItemStack.EMPTY)));
        helper.assertTrue(lv1.is(ModItems.STRIKE_RING.get()) && StrikeRingItem.getLv(lv1) == 1,
                "强击戒指一级配方应产出 lv=1");

        ItemStack lv2 = craft(helper, CraftingInput.of(3, 3, List.of(
                new ItemStack(Items.BLAZE_ROD), ModItems.MOLTEN_GOLD_INGOT.toStack(), new ItemStack(Items.BLAZE_ROD),
                ModItems.MOLTEN_GOLD_INGOT.toStack(), lv1, ModItems.MOLTEN_GOLD_INGOT.toStack(),
                new ItemStack(Items.BLAZE_ROD), ModItems.MOLTEN_GOLD_INGOT.toStack(), new ItemStack(Items.BLAZE_ROD))));
        helper.assertTrue(lv2.is(ModItems.STRIKE_RING.get()) && StrikeRingItem.getLv(lv2) == 2
                        && lv2.getRarity() == ModRarities.excellent(),
                "强击戒指二级配方应用 lv=1 升级为 lv=2 并提升品质");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void redDewRingSlotsRecipesAndHealth(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(new ItemStack(ModItems.RED_DEW_RING.get()).is(curiosTag("ring"))
                        && CuriosApi.getItemStackSlots(new ItemStack(ModItems.RED_DEW_RING.get()), player).containsKey("ring"),
                "红露滴戒指应能佩戴于 ring 槽");

        ItemStack lv1 = craft(helper, CraftingInput.of(1, 3, List.of(ModItems.EMBRYO_RING.toStack(),
                new ItemStack(Items.GOLD_INGOT), ModItems.RED_DEW.toStack())));
        helper.assertTrue(lv1.is(ModItems.RED_DEW_RING.get()) && RedDewRingItem.getLv(lv1) == 1
                        && lv1.getRarity() == ModRarities.common(),
                "红露滴戒指一级配方应产出 lv=1");

        ItemStack lv2 = craft(helper, CraftingInput.of(1, 3, List.of(lv1,
                ModItems.DYEDREAM_DUST.toStack(), ModItems.RED_DEW.toStack())));
        helper.assertTrue(RedDewRingItem.getLv(lv2) == 2 && lv2.getRarity() == ModRarities.excellent(),
                "红露滴戒指二级配方应升级为 lv=2 并提升品质");

        ItemStack lv3 = craft(helper, CraftingInput.of(2, 2, List.of(lv2, ModItems.DYEDREAM_DUST.toStack(),
                ModItems.RED_DEW.toStack(), ModItems.MELT_DREAM_LIQUID_BUCKET.toStack())));
        helper.assertTrue(RedDewRingItem.getLv(lv3) == 3 && lv3.getRarity() == ModRarities.superior(),
                "红露滴戒指三级配方应升级为 lv=3 并提升品质");

        ItemStack lv4 = craft(helper, CraftingInput.of(2, 2, List.of(lv3, ModItems.DYEDREAM_DUST.toStack(),
                ModItems.RED_DEW.toStack(), ModItems.LIFE_CRYSTAL.toStack())));
        helper.assertTrue(RedDewRingItem.getLv(lv4) == 4 && lv4.getRarity() == ModRarities.master(),
                "红露滴戒指四级配方应升级为 lv=4 并提升品质");

        ICurioItem redDewRing = (ICurioItem) ModItems.RED_DEW_RING.get();
        SlotContext ringSlot = new SlotContext("ring", player, 0, false, true);
        double healthBefore = player.getAttributeValue(Attributes.MAX_HEALTH);
        redDewRing.onEquip(ringSlot, ItemStack.EMPTY, lv4);
        helper.assertTrue(Math.abs(player.getAttributeValue(Attributes.MAX_HEALTH) - (healthBefore + 10.0)) < 0.0001,
                "四级红露滴戒指应提供 +10 最大生命值");
        redDewRing.onUnequip(ringSlot, ItemStack.EMPTY, lv4);
        helper.assertTrue(Math.abs(player.getAttributeValue(Attributes.MAX_HEALTH) - healthBefore) < 0.0001,
                "卸下红露滴戒指应移除最大生命值修饰符");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void beltAndRingAttributesAndCounter(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        SlotContext beltSlot = new SlotContext("belt", player, 0, false, true);
        SlotContext ringSlot = new SlotContext("ring", player, 0, false, true);

        ICurioItem nature = (ICurioItem) ModItems.NATURE_BELT.get();
        helper.assertTrue(CuriosApi.getAttributeModifiers(beltSlot, CuriosApi.getSlotId(beltSlot),
                        ModItems.NATURE_BELT.toStack()).containsKey(ModAttributes.SAN_VARIABILITY),
                "自然腰带应提供理智光环属性");

        ICurioItem meltRing = (ICurioItem) ModItems.MELT_DREAM_ENERGY_RING.get();
        helper.assertTrue(CuriosApi.getAttributeModifiers(ringSlot, CuriosApi.getSlotId(ringSlot),
                        ModItems.MELT_DREAM_ENERGY_RING.toStack()).containsKey(ModAttributes.MELT_DREAM_VARIABILITY),
                "融梦光环戒指应提供融梦能量属性");

        ICurioItem traveler = (ICurioItem) ModItems.TRAVELER_BELT.get();
        traveler.onEquip(beltSlot, ItemStack.EMPTY, ModItems.TRAVELER_BELT.toStack());
        helper.assertTrue(Math.abs(player.getAttributeValue(ModAttributes.BLINK_CONSUME) - 0.5) < 0.0001,
                "旅者腰带应减少 0.5 瞬身术消耗");
        traveler.onUnequip(beltSlot, ItemStack.EMPTY, ModItems.TRAVELER_BELT.toStack());
        helper.assertTrue(Math.abs(player.getAttributeValue(ModAttributes.BLINK_CONSUME) - 1.0) < 0.0001,
                "卸下旅者腰带应移除消耗修饰符");

        ICurioItem dreamTraveler = (ICurioItem) ModItems.DREAM_TRAVELER_BELT.get();
        double speedBefore = player.getAttributeValue(Attributes.MOVEMENT_SPEED);
        dreamTraveler.onEquip(beltSlot, ItemStack.EMPTY, ModItems.DREAM_TRAVELER_BELT.toStack());
        helper.assertTrue(Math.abs(player.getAttributeValue(Attributes.MOVEMENT_SPEED) - speedBefore * 1.03) < 0.0001
                        && Math.abs(player.getAttributeValue(ModAttributes.BLINK_COOLDOWN_REDUCTION) - 0.1) < 0.0001,
                "迷梦旅者腰带应提供移动速度与瞬身术冷却缩减");
        dreamTraveler.onUnequip(beltSlot, ItemStack.EMPTY, ModItems.DREAM_TRAVELER_BELT.toStack());
        helper.assertTrue(Math.abs(player.getAttributeValue(Attributes.MOVEMENT_SPEED) - speedBefore) < 0.0001,
                "卸下迷梦旅者腰带应移除移动速度修饰符");

        ICurioItem strike = (ICurioItem) ModItems.STRIKE_RING.get();
        double attackBefore = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        strike.onEquip(ringSlot, ItemStack.EMPTY, StrikeRingItem.createWithLv(ModItems.STRIKE_RING.get(), 2));
        helper.assertTrue(Math.abs(player.getAttributeValue(Attributes.ATTACK_DAMAGE) - (attackBefore + 3.0)) < 0.0001,
                "二级强击戒指应提供 +3 攻击伤害");
        strike.onUnequip(ringSlot, ItemStack.EMPTY, StrikeRingItem.createWithLv(ModItems.STRIKE_RING.get(), 2));
        helper.assertTrue(Math.abs(player.getAttributeValue(Attributes.ATTACK_DAMAGE) - attackBefore) < 0.0001,
                "卸下强击戒指应移除攻击伤害修饰符");

        var inventory = CuriosApi.getCuriosInventory(player).orElseThrow();
        inventory.setEquippedCurio("ring", 0, ModItems.COUNTER_RING.toStack());
        player.addEffect(new MobEffectInstance(ModEffects.EVASION, 6));
        var source = helper.getLevel().damageSources().magic();
        LivingIncomingDamageEvent incoming = new LivingIncomingDamageEvent(player, new DamageContainer(source, 4.0f));
        PlayerEvents.onLivingHurt(incoming);
        helper.assertTrue(incoming.isCanceled() && player.hasEffect(ModEffects.COUNTER_ATTACK)
                        && player.hasEffect(MobEffects.DAMAGE_BOOST),
                "反击戒指应在成功闪避后提供反击与力量");

        var attackSource = helper.getLevel().damageSources().playerAttack(player);
        LivingDamageEvent.Pre outgoing = new LivingDamageEvent.Pre(player, new DamageContainer(attackSource, 4.0f));
        PlayerEvents.onLivingDamage(outgoing);
        helper.assertTrue(Math.abs(outgoing.getNewDamage() - 6.0f) < 0.0001
                        && !player.hasEffect(ModEffects.COUNTER_ATTACK)
                        && !player.hasEffect(MobEffects.DAMAGE_BOOST),
                "反击应使下一次伤害提高 50% 并在命中后移除");
        helper.succeed();
    }

    private static ItemStack craft(GameTestHelper helper, CraftingInput input) {
        var recipe = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
        return recipe.value().assemble(input, helper.getLevel().registryAccess());
    }

    private static TagKey<Item> curiosTag(String slot) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", slot));
    }

    private static void checkCraft(GameTestHelper helper, CraftingInput input, Item expected) {
        var recipe = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
        helper.assertTrue(recipe.value().assemble(input, helper.getLevel().registryAccess()).is(expected),
                "饰品配方应产出 " + expected);
    }
}
