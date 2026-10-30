package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.ModToolTiers;
import com.pasterdream.pasterdreammod.world.item.ModArmorMaterials;
import com.pasterdream.pasterdreammod.world.item.armoritem.TitaniumArmorItem;
import com.pasterdream.pasterdreammod.world.item.moltengoldtool.*;
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
    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_ORE);
    public static final DeferredItem<BlockItem> DYEDREAM_DUST_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_DUST_ORE);

    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> SMOOTH_DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> BRICKS_DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> PILLAR_DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> CHISELED_DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_BLOCK_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS);
    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_BLOCK_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB);
    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_BLOCK_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL);

    public static final DeferredItem<Item> COTTON = ITEMS.register("cotton", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPOOL = ITEMS.register("spool",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FABRIC = ITEMS.register("fabric",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PROTECT_DECK = ITEMS.register("protect_deck",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BLACK_STICK = ITEMS.registerSimpleItem("black_stick");
    public static final DeferredItem<Item> TITANIUM_UPGRADE = ITEMS.registerSimpleItem("titanium_upgrade", new Item.Properties().stacksTo(16));
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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private ModItems() {
    }
}
