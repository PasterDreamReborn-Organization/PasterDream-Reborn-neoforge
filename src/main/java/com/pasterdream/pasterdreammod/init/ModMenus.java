package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableMenu;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.anvil.WeaponWorkshopAnvilBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.anvil.WeaponWorkshopAnvilMenu;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.coolerpot.WeaponWorkshopCoolerPotBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.coolerpot.WeaponWorkshopCoolerPotMenu;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.grindstone.WeaponWorkshopGrindStoneBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.grindstone.WeaponWorkshopGrindStoneMenu;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceMenu;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk.DyedreamDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk.DyedreamDeskMenu;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.shadowdesk.ShadowDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.shadowdesk.ShadowDeskMenu;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.windmoordesk.WindMoorDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.windmoordesk.WindMoorDeskMenu;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.picnicbasket.PicnicBasketBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.picnicbasket.PicnicBasketMenu;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.shadowchest.ShadowChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.shadowchest.ShadowChestMenu;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestMenu;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.windmoorcrate.WindMoorCrateBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.windmoorcrate.WindMoorCrateMenu;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlockEntity;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanMenu;
import com.pasterdream.pasterdreammod.world.item.storagebag.LargeStorageBagMenu;
import com.pasterdream.pasterdreammod.world.item.storagebag.StorageBagMenu;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarMenu;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronMenu;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorMenu;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceBlockEntity;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 容器菜单类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModMenus.java}。
 * Screen 在 {@code client.ModScreens} 的 {@code RegisterMenuScreensEvent} 中注册。
 */
public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<WeaponWorkshopCraftingTableMenu>> WEAPON_WORKSHOP_CRAFTING_TABLE =
            MENUS.register("weapon_workshop_crafting_table", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new WeaponWorkshopCraftingTableMenu(windowId, inv, (WeaponWorkshopCraftingTableBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<WeaponWorkshopAnvilMenu>> WEAPON_WORKSHOP_ANVIL =
            MENUS.register("weapon_workshop_anvil", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new WeaponWorkshopAnvilMenu(windowId, inv, (WeaponWorkshopAnvilBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<WeaponWorkshopCoolerPotMenu>> WEAPON_WORKSHOP_COOLER_POT =
            MENUS.register("weapon_workshop_cooler_pot", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new WeaponWorkshopCoolerPotMenu(windowId, inv, (WeaponWorkshopCoolerPotBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<WeaponWorkshopGrindStoneMenu>> WEAPON_WORKSHOP_GRIND_STONE =
            MENUS.register("weapon_workshop_grind_stone", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new WeaponWorkshopGrindStoneMenu(windowId, inv, (WeaponWorkshopGrindStoneBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<WeaponWorkshopBlastFurnaceMenu>> WEAPON_WORKSHOP_BLAST_FURNACE =
            MENUS.register("weapon_workshop_blast_furnace", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new WeaponWorkshopBlastFurnaceMenu(windowId, inv, (WeaponWorkshopBlastFurnaceBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<DyedreamDeskMenu>> DYEDREAM_DESK =
            MENUS.register("dyedream_desk", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new DyedreamDeskMenu(windowId, inv, (DyedreamDeskBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<ShadowDeskMenu>> SHADOW_DESK =
            MENUS.register("shadow_desk", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new ShadowDeskMenu(windowId, inv, (ShadowDeskBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<WindMoorDeskMenu>> WIND_MOOR_DESK =
            MENUS.register("wind_moor_desk", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new WindMoorDeskMenu(windowId, inv, (WindMoorDeskBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<PicnicBasketMenu>> PICNIC_BASKET =
            MENUS.register("picnic_basket", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new PicnicBasketMenu(windowId, inv, (PicnicBasketBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<ShadowChestMenu>> SHADOW_CHEST =
            MENUS.register("shadow_chest", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new ShadowChestMenu(windowId, inv, (ShadowChestBlockEntity) blockEntity);
            }));
    public static final DeferredHolder<MenuType<?>, MenuType<OpenedMeltDreamCrystalChestMenu>> OPENED_MELT_DREAM_CRYSTAL_CHEST =
            MENUS.register("opened_melt_dream_crystal_chest", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new OpenedMeltDreamCrystalChestMenu(windowId, inv, (OpenedMeltDreamCrystalChestBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<WindMoorCrateMenu>> WIND_MOOR_CRATE =
            MENUS.register("wind_moor_crate", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new WindMoorCrateMenu(windowId, inv, (WindMoorCrateBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<StorageBagMenu>> STORAGE_BAG =
            MENUS.register("storage_bag", () -> IMenuTypeExtension.create(
                    (windowId, inv, data) -> new StorageBagMenu(windowId, inv, data)));

    public static final DeferredHolder<MenuType<?>, MenuType<MortarMenu>> MORTAR =
            MENUS.register("mortar", () -> IMenuTypeExtension.create(MortarMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<ClaypanMenu>> CLAYPAN =
            MENUS.register("claypan", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new ClaypanMenu(windowId, inv, (ClaypanBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<DreamCauldronMenu>> DREAM_CAULDRON =
            MENUS.register("dream_cauldron", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new DreamCauldronMenu(windowId, inv, (DreamCauldronBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<DreamAccumulatorMenu>> DREAM_ACCUMULATOR =
            MENUS.register("dream_accumulator", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new DreamAccumulatorMenu(windowId, inv, (DreamAccumulatorBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<ShadowBlastFurnaceMenu>> SHADOW_BLAST_FURNACE =
            MENUS.register("shadow_blast_furnace", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new ShadowBlastFurnaceMenu(windowId, inv, (ShadowBlastFurnaceBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<LargeStorageBagMenu>> LARGE_STORAGE_BAG =
            MENUS.register("large_storage_bag", () -> IMenuTypeExtension.create(
                    (windowId, inv, data) -> new LargeStorageBagMenu(windowId, inv, data)));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

    private ModMenus() {
    }
}
