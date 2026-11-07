package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableMenu;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk.DyedreamDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk.DyedreamDeskMenu;
import com.pasterdream.pasterdreammod.world.item.storagebag.LargeStorageBagMenu;
import com.pasterdream.pasterdreammod.world.item.storagebag.StorageBagMenu;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarMenu;
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

    public static final DeferredHolder<MenuType<?>, MenuType<DyedreamDeskMenu>> DYEDREAM_DESK =
            MENUS.register("dyedream_desk", () -> IMenuTypeExtension.create((windowId, inv, data) ->
            {
                BlockEntity blockEntity = inv.player.level().getBlockEntity(data.readBlockPos());
                return new DyedreamDeskMenu(windowId, inv, (DyedreamDeskBlockEntity) blockEntity);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<StorageBagMenu>> STORAGE_BAG =
            MENUS.register("storage_bag", () -> IMenuTypeExtension.create(
                    (windowId, inv, data) -> new StorageBagMenu(windowId, inv, data)));

    public static final DeferredHolder<MenuType<?>, MenuType<MortarMenu>> MORTAR =
            MENUS.register("mortar", () -> IMenuTypeExtension.create(MortarMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<LargeStorageBagMenu>> LARGE_STORAGE_BAG =
            MENUS.register("large_storage_bag", () -> IMenuTypeExtension.create(
                    (windowId, inv, data) -> new LargeStorageBagMenu(windowId, inv, data)));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

    private ModMenus() {
    }
}
