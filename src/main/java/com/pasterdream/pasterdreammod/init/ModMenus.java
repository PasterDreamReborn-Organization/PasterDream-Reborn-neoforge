package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableMenu;
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

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

    private ModMenus() {
    }
}
