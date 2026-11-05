package com.pasterdream.pasterdreammod.init;

import net.minecraft.world.item.BlockItem;
import com.pasterdream.pasterdreammod.world.item.NightmareFuelItem;
import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollItem;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollItem;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.ModToolTiers;
import com.pasterdream.pasterdreammod.world.item.ModArmorMaterials;
import com.pasterdream.pasterdreammod.world.item.BlueDewItem;
import com.pasterdream.pasterdreammod.world.item.DyedreamHammerItem;
import com.pasterdream.pasterdreammod.world.item.MeltDreamCoinItem;
import com.pasterdream.pasterdreammod.world.item.MeltDreamCrystalFragmentItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.DyedreamArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.MeltDreamCrystalArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.SculkArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.TitaniumArmorItem;
import com.pasterdream.pasterdreammod.world.item.deserttool.ChenjingmenDesertSwordItem;
import com.pasterdream.pasterdreammod.world.item.deserttool.DesertSwordItem;
import com.pasterdream.pasterdreammod.world.item.grasstool.KusanagiItem;
import com.pasterdream.pasterdreammod.world.item.grasstool.MurakumoKusanagiItem;
import com.pasterdream.pasterdreammod.world.item.hellfiretool.*;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombItem;
import com.pasterdream.pasterdreammod.world.block.lostswordtomb.LostSwordTombItem;
import com.pasterdream.pasterdreammod.world.block.lifecrystal.LifeCrystalItem;
import com.pasterdream.pasterdreammod.world.item.moltengoldtool.*;
import com.pasterdream.pasterdreammod.world.item.meltdreamtool.*;
import com.pasterdream.pasterdreammod.world.item.tidetool.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PasterDreamMod.MOD_ID);

    public static final DeferredItem<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> TITANIUM_NUGGET = ITEMS.register("titanium_nugget",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_TITANIUM = ITEMS.register("raw_titanium",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DYEDREAM_BUD_NUGGET = ITEMS.registerSimpleItem("dyedream_bud_nugget");
    public static final DeferredItem<Item> RAW_DYEDREAM_ALLOY_INGOT = ITEMS.registerSimpleItem("raw_dyedream_alloy_ingot");
    public static final DeferredItem<Item> DYEDREAM_ALLOY_INGOT = ITEMS.register("dyedream_alloy_ingot",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> DYEDREAM_ALLOY_NUGGET = ITEMS.registerSimpleItem("dyedream_alloy_nugget");

    // ===== 露滴 =====
    public static final DeferredItem<Item> BLUE_HEART_OF_THE_SEA = ITEMS.register("blue_heart_of_the_sea",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> ELDER_GUARDIAN_SCALE = ITEMS.register("elder_guardian_scale",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLUE_DEW = ITEMS.register("blue_dew", BlueDewItem::new);

    public static final DeferredItem<BlockItem> TITANIUM_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.TITANIUM_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_TITANIUM_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DEEPSLATE_TITANIUM_ORE);
    public static final DeferredItem<BlockItem> RAW_TITANIUM_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.RAW_TITANIUM_BLOCK);
    public static final DeferredItem<BlockItem> TITANIUM_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.TITANIUM_BLOCK);

    public static final DeferredItem<Item> MOLTEN_GOLD_INGOT = ITEMS.register("molten_gold_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MOLTEN_GOLD_NUGGET = ITEMS.register("molten_gold_nugget",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_MOLTEN_GOLD = ITEMS.register("raw_molten_gold",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<BlockItem> MOLTEN_GOLD_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.MOLTEN_GOLD_ORE);
    public static final DeferredItem<BlockItem> MOLTEN_GOLD_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.MOLTEN_GOLD_BLOCK);

    public static final DeferredItem<Item> DYEDREAM_QUARTZ = ITEMS.registerSimpleItem("dyedream_quartz");
    public static final DeferredItem<Item> DYEDREAM_DUST = ITEMS.registerSimpleItem("dyedream_dust");
    public static final DeferredItem<Item> DYEDREAM_DUST_PIECE = ITEMS.registerSimpleItem("dyedream_dust_piece");
    public static final DeferredItem<Item> PINK_SLIMEBALL = ITEMS.registerSimpleItem("pink_slimeball");
    public static final DeferredItem<Item> DYEDREAM_DYE = ITEMS.registerSimpleItem("dyedream_dye");
    public static final DeferredItem<Item> MELT_DREAM_CRYSTAL_FRAGMENT = ITEMS.register("melt_dream_crystal_fragment",
            MeltDreamCrystalFragmentItem::new);
    public static final DeferredItem<Item> MELT_DREAM_COIN = ITEMS.register("melt_dream_coin",
            () -> new MeltDreamCoinItem(new Item.Properties(), false));
    public static final DeferredItem<Item> MELT_DREAM_COIN_PILE = ITEMS.register("melt_dream_coin_pile",
            () -> new MeltDreamCoinItem(new Item.Properties(), true));
    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_ORE);
    public static final DeferredItem<BlockItem> DYEDREAM_DUST_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_DUST_ORE);
    public static final DeferredItem<BlockItem> PINK_SLIME_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.PINK_SLIME_BLOCK);

    public static final DeferredItem<BlockItem> DYEDREAM_BUDDING_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_BUDDING_BLOCK);
    public static final DeferredItem<BlockItem> SMALL_DYEDREAM_BUD = ITEMS.registerSimpleBlockItem(ModBlocks.SMALL_DYEDREAM_BUD);
    public static final DeferredItem<BlockItem> MEDIUM_DYEDREAM_BUD = ITEMS.registerSimpleBlockItem(ModBlocks.MEDIUM_DYEDREAM_BUD);
    public static final DeferredItem<BlockItem> LARGE_DYEDREAM_BUD = ITEMS.registerSimpleBlockItem(ModBlocks.LARGE_DYEDREAM_BUD);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_BUD_BLOCK);
    public static final DeferredItem<BlockItem> DYEDREAM_ALLOY_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_ALLOY_BLOCK);

    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> SMOOTH_DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> BRICKS_DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> PILLAR_DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> CHISELED_DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_BLOCK_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS);
    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_BLOCK_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB);
    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_BLOCK_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL);

    public static final DeferredItem<Item> COTTON = ITEMS.register("cotton", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SCULK_HEART = ITEMS.register("sculk_heart",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> SPOOL = ITEMS.register("spool",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FABRIC = ITEMS.register("fabric",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PROTECT_DECK = ITEMS.register("protect_deck",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> JUNGLE_SPORE = ITEMS.register("jungle_spore",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(1).saturationModifier(0f).build())));
    public static final DeferredItem<Item> RICE_CAKE = ITEMS.register("rice_cake",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(6).saturationModifier(0.415f).build())));

    public static final DeferredItem<Item> BLACK_STICK = ITEMS.registerSimpleItem("black_stick");
    public static final DeferredItem<Item> TITANIUM_UPGRADE = ITEMS.registerSimpleItem("titanium_upgrade", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> SCULK_UPGRADE = ITEMS.registerSimpleItem("sculk_upgrade", new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> DYEDREAM_UPGRADE = ITEMS.registerSimpleItem("dyedream_upgrade", new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON));
    public static final DeferredItem<SwordItem> TITANIUM_SWORD = ITEMS.register("titanium_sword",
            () -> new SwordItem(ModToolTiers.TITANIUM, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.TITANIUM, 3, -2.4f))));
    public static final DeferredItem<PickaxeItem> TITANIUM_PICKAXE = ITEMS.register("titanium_pickaxe",
            () -> new PickaxeItem(ModToolTiers.TITANIUM, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(ModToolTiers.TITANIUM, 1, -2.8f))));
    public static final DeferredItem<AxeItem> TITANIUM_AXE = ITEMS.register("titanium_axe",
            () -> new AxeItem(ModToolTiers.TITANIUM, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(ModToolTiers.TITANIUM, 5.5f, -3.0f))));
    public static final DeferredItem<ShovelItem> TITANIUM_SHOVEL = ITEMS.register("titanium_shovel",
            () -> new ShovelItem(ModToolTiers.TITANIUM, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(ModToolTiers.TITANIUM, 2.0f, -3.0f))));
    public static final DeferredItem<HoeItem> TITANIUM_HOE = ITEMS.register("titanium_hoe",
            () -> new HoeItem(ModToolTiers.TITANIUM, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(ModToolTiers.TITANIUM, -3, 0.0f))));

    // ===== 铜工具 =====
    public static final DeferredItem<Item> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new SwordItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.COPPER, 3, -2.4f))));
    public static final DeferredItem<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(ModToolTiers.COPPER, 1, -2.8f))));
    public static final DeferredItem<Item> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new AxeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(ModToolTiers.COPPER, 6.5f, -3.15f))));
    public static final DeferredItem<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(ModToolTiers.COPPER, 1.5f, -3.0f))));
    public static final DeferredItem<Item> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new HoeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(ModToolTiers.COPPER, 0, -1.5f))));

    // ===== 铜装备 =====
    public static final DeferredItem<Item> COPPER_HELMET = ITEMS.register("copper_helmet",
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(14))));
    public static final DeferredItem<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate",
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(14))));
    public static final DeferredItem<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings",
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(14))));
    public static final DeferredItem<Item> COPPER_BOOTS = ITEMS.register("copper_boots",
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(14))));

    // ===== 染梦合金工具 =====
    public static final DeferredItem<SwordItem> DYEDREAM_SWORD = ITEMS.register("dyedream_sword",
            () -> new SwordItem(ModToolTiers.DYEDREAM, new Item.Properties().rarity(Rarity.UNCOMMON)
                    .attributes(SwordItem.createAttributes(ModToolTiers.DYEDREAM, 3, -2.4f))));
    public static final DeferredItem<PickaxeItem> DYEDREAM_PICKAXE = ITEMS.register("dyedream_pickaxe",
            () -> new PickaxeItem(ModToolTiers.DYEDREAM, new Item.Properties().rarity(Rarity.UNCOMMON)
                    .attributes(DiggerItem.createAttributes(ModToolTiers.DYEDREAM, 1, -2.8f))));
    public static final DeferredItem<AxeItem> DYEDREAM_AXE = ITEMS.register("dyedream_axe",
            () -> new AxeItem(ModToolTiers.DYEDREAM, new Item.Properties().rarity(Rarity.UNCOMMON)
                    .attributes(DiggerItem.createAttributes(ModToolTiers.DYEDREAM, 5.5f, -3.0f))));
    public static final DeferredItem<ShovelItem> DYEDREAM_SHOVEL = ITEMS.register("dyedream_shovel",
            () -> new ShovelItem(ModToolTiers.DYEDREAM, new Item.Properties().rarity(Rarity.UNCOMMON)
                    .attributes(DiggerItem.createAttributes(ModToolTiers.DYEDREAM, 1.5f, -3.0f))));
    public static final DeferredItem<HoeItem> DYEDREAM_HOE = ITEMS.register("dyedream_hoe",
            () -> new HoeItem(ModToolTiers.DYEDREAM, new Item.Properties().rarity(Rarity.UNCOMMON)
                    .attributes(DiggerItem.createAttributes(ModToolTiers.DYEDREAM, -3, 0.0f))));
    public static final DeferredItem<Item> DYEDREAM_HAMMER = ITEMS.register("dyedream_hammer",
            () -> new DyedreamHammerItem(ModToolTiers.DYEDREAM, new Item.Properties().rarity(Rarity.UNCOMMON)));

    // ===== 融梦水晶工具 =====
    public static final DeferredItem<Item> MELT_DREAM_PICKAXE = ITEMS.register("melt_dream_pickaxe",
            () -> new MeltDreamPickaxeItem(ModToolTiers.MELT_DREAM, 1, -2.8f, new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> MELT_DREAM_AXE = ITEMS.register("melt_dream_axe",
            () -> new MeltDreamAxeItem(ModToolTiers.MELT_DREAM, 5.5f, -3.0f, new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> MELT_DREAM_SHOVEL = ITEMS.register("melt_dream_shovel",
            () -> new MeltDreamShovelItem(ModToolTiers.MELT_DREAM, 1.5f, -3.0f, new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> MELT_DREAM_HOE = ITEMS.register("melt_dream_hoe",
            () -> new MeltDreamHoeItem(ModToolTiers.MELT_DREAM, -3, 0.5f, new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> SHARP_MELT_DREAM_SWORD = ITEMS.register("sharp_melt_dream_sword",
            () -> new SharpMeltDreamSwordItem(ModToolTiers.MELT_DREAM, new Item.Properties().rarity(Rarity.RARE)));

    // ===== 炙焰金工具 =====
    public static final DeferredItem<Item> MOLTEN_GOLD_SWORD = ITEMS.register("molten_gold_sword",
            () -> new MoltenGoldSwordItem(ModToolTiers.MOLTEN_GOLD, 3, -2.3f, new Item.Properties()));
    public static final DeferredItem<Item> MOLTEN_GOLD_PICKAXE = ITEMS.register("molten_gold_pickaxe",
            () -> new MoltenGoldPickaxeItem(ModToolTiers.MOLTEN_GOLD, 1, -2.7f, new Item.Properties()));
    public static final DeferredItem<Item> MOLTEN_GOLD_AXE = ITEMS.register("molten_gold_axe",
            () -> new MoltenGoldAxeItem(ModToolTiers.MOLTEN_GOLD, 6.0f, -3.0f, new Item.Properties()));
    public static final DeferredItem<Item> MOLTEN_GOLD_SHOVEL = ITEMS.register("molten_gold_shovel",
            () -> new MoltenGoldShovelItem(ModToolTiers.MOLTEN_GOLD, 1.5f, -2.9f, new Item.Properties()));
    public static final DeferredItem<Item> MOLTEN_GOLD_HOE = ITEMS.register("molten_gold_hoe",
            () -> new MoltenGoldHoeItem(ModToolTiers.MOLTEN_GOLD, -2, -0.5f, new Item.Properties()));

    // ===== 狱炎工具 =====
    public static final DeferredItem<Item> HELLFIRE_SWORD = ITEMS.register("hellfire_sword",
            () -> new HellfireSwordItem(ModToolTiers.HELLFIRE, 3, -2.2f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> HELLFIRE_PICKAXE = ITEMS.register("hellfire_pickaxe",
            () -> new HellfirePickaxeItem(ModToolTiers.HELLFIRE, 1, -2.6f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> INFERNO_SWORD = ITEMS.register("inferno_sword",
            () -> new InfernoSwordItem(ModToolTiers.HELLFIRE, 4, -3.1f, new Item.Properties().rarity(Rarity.UNCOMMON)));

    // ===== 独立武器 =====
    public static final DeferredItem<Item> TIDE_SWORD = ITEMS.register("tide_sword",
            () -> new TideSwordItem(ModToolTiers.TIDE_SWORD, 3, -2.8f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> BEIHAI_RUO_TIDE_SWORD = ITEMS.register("beihairuo_tide_sword",
            () -> new BeihaiRuoTideSwordItem(ModToolTiers.BEIHAI_RUO_TIDE_SWORD, 3, -2.8f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> KUSANAGI = ITEMS.register("kusanagi",
            () -> new KusanagiItem(ModToolTiers.KUSANAGI, 3, -2.5f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> MURAKUMO_KUSANAGI = ITEMS.register("murakumo_kusanagi",
            () -> new MurakumoKusanagiItem(ModToolTiers.MURAKUMO_KUSANAGI, 3, -2.5f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> SWORD_EMBRYO = ITEMS.register("sword_embryo",
            () -> new SwordItem(Tiers.IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 1, -2.4f))));
    public static final DeferredItem<Item> DESERT_SWORD = ITEMS.register("desert_sword",
            () -> new DesertSwordItem(ModToolTiers.DESERT_SWORD, 3, -3.1f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> CHENJINGMEN_DESERT_SWORD = ITEMS.register("chenjingmen_desert_sword",
            () -> new ChenjingmenDesertSwordItem(ModToolTiers.CHENJINGMEN_DESERT_SWORD, 3, -3.1f, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> BROKEN_HERO_SWORD = ITEMS.register("broken_hero_sword",
            () -> new SwordItem(ModToolTiers.BROKEN_HERO_SWORD, new Item.Properties().fireResistant()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BROKEN_HERO_SWORD, 3, -2.4f))));

    // ===== 钛金装备 =====
    public static final DeferredItem<Item> TITANIUM_HELMET = ITEMS.register("titanium_helmet",
            () -> new TitaniumArmorItem(ModArmorMaterials.TITANIUM, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(25))));
    public static final DeferredItem<Item> TITANIUM_CHESTPLATE = ITEMS.register("titanium_chestplate",
            () -> new ArmorItem(ModArmorMaterials.TITANIUM, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(25))));
    public static final DeferredItem<Item> TITANIUM_LEGGINGS = ITEMS.register("titanium_leggings",
            () -> new ArmorItem(ModArmorMaterials.TITANIUM, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(25))));
    public static final DeferredItem<Item> TITANIUM_BOOTS = ITEMS.register("titanium_boots",
            () -> new ArmorItem(ModArmorMaterials.TITANIUM, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(25))));

    // ===== 回响装备 =====
    public static final DeferredItem<Item> SCULK_HELMET = ITEMS.register("sculk_helmet",
            () -> new SculkArmorItem(ModArmorMaterials.SCULK, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.UNCOMMON)
                            .durability(ArmorItem.Type.HELMET.getDurability(42))));
    public static final DeferredItem<Item> SCULK_CHESTPLATE = ITEMS.register("sculk_chestplate",
            () -> new ArmorItem(ModArmorMaterials.SCULK, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)
                            .durability(ArmorItem.Type.CHESTPLATE.getDurability(42))));
    public static final DeferredItem<Item> SCULK_LEGGINGS = ITEMS.register("sculk_leggings",
            () -> new ArmorItem(ModArmorMaterials.SCULK, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)
                            .durability(ArmorItem.Type.LEGGINGS.getDurability(42))));
    public static final DeferredItem<Item> SCULK_BOOTS = ITEMS.register("sculk_boots",
            () -> new ArmorItem(ModArmorMaterials.SCULK, ArmorItem.Type.BOOTS,
                    new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)
                            .durability(ArmorItem.Type.BOOTS.getDurability(42))));

    // ===== 染梦合金装备 =====
    public static final DeferredItem<Item> DYEDREAM_HELMET = ITEMS.register("dyedream_helmet",
            () -> new DyedreamArmorItem(ModArmorMaterials.DYEDREAM, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.UNCOMMON)
                            .durability(ArmorItem.Type.HELMET.getDurability(40))));
    public static final DeferredItem<Item> DYEDREAM_CHESTPLATE = ITEMS.register("dyedream_chestplate",
            () -> new ArmorItem(ModArmorMaterials.DYEDREAM, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()
                            .durability(ArmorItem.Type.CHESTPLATE.getDurability(40))));
    public static final DeferredItem<Item> DYEDREAM_LEGGINGS = ITEMS.register("dyedream_leggings",
            () -> new ArmorItem(ModArmorMaterials.DYEDREAM, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()
                            .durability(ArmorItem.Type.LEGGINGS.getDurability(40))));
    public static final DeferredItem<Item> DYEDREAM_BOOTS = ITEMS.register("dyedream_boots",
            () -> new ArmorItem(ModArmorMaterials.DYEDREAM, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()
                            .durability(ArmorItem.Type.BOOTS.getDurability(40))));

    // ===== 融梦水晶装备 =====
    public static final DeferredItem<Item> MELT_DREAM_HELMET = ITEMS.register("melt_dream_helmet",
            () -> new MeltDreamCrystalArmorItem(ModArmorMaterials.MELT_DREAM, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.RARE).durability(ArmorItem.Type.HELMET.getDurability(45))));
    public static final DeferredItem<Item> MELT_DREAM_CHESTPLATE = ITEMS.register("melt_dream_chestplate",
            () -> new MeltDreamCrystalArmorItem(ModArmorMaterials.MELT_DREAM, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(Rarity.RARE).durability(ArmorItem.Type.CHESTPLATE.getDurability(45))));
    public static final DeferredItem<Item> MELT_DREAM_LEGGINGS = ITEMS.register("melt_dream_leggings",
            () -> new MeltDreamCrystalArmorItem(ModArmorMaterials.MELT_DREAM, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(Rarity.RARE).durability(ArmorItem.Type.LEGGINGS.getDurability(45))));
    public static final DeferredItem<Item> MELT_DREAM_BOOTS = ITEMS.register("melt_dream_boots",
            () -> new MeltDreamCrystalArmorItem(ModArmorMaterials.MELT_DREAM, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(Rarity.RARE).durability(ArmorItem.Type.BOOTS.getDurability(45))));

    public static final DeferredItem<Item> LOST_SWORD_TOMB = ITEMS.register("lost_sword_tomb",
            () -> new LostSwordTombItem(ModBlocks.LOST_SWORD_TOMB.get(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> LIFE_CRYSTAL = ITEMS.register("life_crystal",
            () -> new LifeCrystalItem(ModBlocks.LIFE_CRYSTAL.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> DESERT_HERO_TOMB = ITEMS.register("desert_hero_tomb",
            () -> new DesertHeroTombItem(ModBlocks.DESERT_HERO_TOMB.get(), new Item.Properties()));

    public static final DeferredItem<Item> QYM_DOLL = ITEMS.register("qym_doll", () -> new QYMDollItem(ModBlocks.QYM_DOLL.get(), new Item.Properties().fireResistant().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> UUZ_DOLL = ITEMS.register("uuz_doll", () -> new UUZDollItem(ModBlocks.UUZ_DOLL.get(), new Item.Properties().fireResistant().rarity(Rarity.EPIC)));

    public static final DeferredItem<Item> NIGHTMARE_FUEL = ITEMS.register("nightmare_fuel", NightmareFuelItem::new);
    public static final DeferredItem<BlockItem> SHADOW = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW);
    public static final DeferredItem<BlockItem> THICK_SHADOW = ITEMS.registerSimpleBlockItem(ModBlocks.THICK_SHADOW);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private ModItems() {
    }
}
