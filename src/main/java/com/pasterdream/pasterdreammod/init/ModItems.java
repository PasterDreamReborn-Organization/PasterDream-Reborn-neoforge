package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.ModToolTiers;
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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private ModItems() {
    }
}
