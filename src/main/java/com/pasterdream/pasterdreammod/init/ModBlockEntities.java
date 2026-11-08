package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollBlockEntity;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollBlockEntity;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombBlockEntity;
import com.pasterdream.pasterdreammod.world.block.lostswordtomb.LostSwordTombBlockEntity;
import com.pasterdream.pasterdreammod.world.block.lifecrystal.LifeCrystalBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableBlockEntity;
import com.pasterdream.pasterdreammod.world.block.NippyEdelweissBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk.DyedreamDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.shadowdesk.ShadowDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.windmoordesk.WindMoorDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.picnicbasket.PicnicBasketBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.shadowchest.ShadowChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.windmoorcrate.WindMoorCrateBlockEntity;
import com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers.TheEndlessBookOfDreamSeekersBlockEntity;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorBlockEntity;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceAddonBlockEntity;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 方块实体类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModBlockEntities.java}。
 */
public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LostSwordTombBlockEntity>> LOST_SWORD_TOMB =
            BLOCK_ENTITIES.register("lost_sword_tomb",
                    () -> BlockEntityType.Builder.of(LostSwordTombBlockEntity::new, ModBlocks.LOST_SWORD_TOMB.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LifeCrystalBlockEntity>> LIFE_CRYSTAL =
            BLOCK_ENTITIES.register("life_crystal",
                    () -> BlockEntityType.Builder.of(LifeCrystalBlockEntity::new, ModBlocks.LIFE_CRYSTAL.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DesertHeroTombBlockEntity>> DESERT_HERO_TOMB =
            BLOCK_ENTITIES.register("desert_hero_tomb",
                    () -> BlockEntityType.Builder.of(DesertHeroTombBlockEntity::new, ModBlocks.DESERT_HERO_TOMB.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<QYMDollBlockEntity>> QYM_DOLL = BLOCK_ENTITIES.register("qym_doll", () -> BlockEntityType.Builder.of(QYMDollBlockEntity::new, ModBlocks.QYM_DOLL.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<UUZDollBlockEntity>> UUZ_DOLL = BLOCK_ENTITIES.register("uuz_doll", () -> BlockEntityType.Builder.of(UUZDollBlockEntity::new, ModBlocks.UUZ_DOLL.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WeaponWorkshopCraftingTableBlockEntity>> WEAPON_WORKSHOP_CRAFTING_TABLE =
            BLOCK_ENTITIES.register("weapon_workshop_crafting_table",
                    () -> BlockEntityType.Builder.of(WeaponWorkshopCraftingTableBlockEntity::new, ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DyedreamDeskBlockEntity>> DYEDREAM_DESK =
            BLOCK_ENTITIES.register("dyedream_desk",
                    () -> BlockEntityType.Builder.of(DyedreamDeskBlockEntity::new, ModBlocks.DYEDREAM_DESK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowDeskBlockEntity>> SHADOW_DESK =
            BLOCK_ENTITIES.register("shadow_desk",
                    () -> BlockEntityType.Builder.of(ShadowDeskBlockEntity::new, ModBlocks.SHADOW_DESK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WindMoorDeskBlockEntity>> WIND_MOOR_DESK =
            BLOCK_ENTITIES.register("wind_moot_desk",
                    () -> BlockEntityType.Builder.of(WindMoorDeskBlockEntity::new, ModBlocks.WIND_MOOR_DESK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PicnicBasketBlockEntity>> PICNIC_BASKET =
            BLOCK_ENTITIES.register("picnic_basket",
                    () -> BlockEntityType.Builder.of(PicnicBasketBlockEntity::new, ModBlocks.PICNIC_BASKET.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowChestBlockEntity>> SHADOW_CHEST =
            BLOCK_ENTITIES.register("shadow_chest",
                    () -> BlockEntityType.Builder.of(ShadowChestBlockEntity::new, ModBlocks.SHADOW_CHEST.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WindMoorCrateBlockEntity>> WIND_MOOR_CRATE =
            BLOCK_ENTITIES.register("wind_moor_crate",
                    () -> BlockEntityType.Builder.of(WindMoorCrateBlockEntity::new, ModBlocks.WIND_MOOR_CRATE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TheEndlessBookOfDreamSeekersBlockEntity>> THE_ENDLESS_BOOK_OF_DREAM_SEEKERS =
            BLOCK_ENTITIES.register("the_endless_book_of_dream_seekers",
                    () -> BlockEntityType.Builder.of(TheEndlessBookOfDreamSeekersBlockEntity::new, ModBlocks.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClaypanBlockEntity>> CLAYPAN =
            BLOCK_ENTITIES.register("claypan",
                    () -> BlockEntityType.Builder.of(ClaypanBlockEntity::new, ModBlocks.CLAYPAN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DreamCauldronBlockEntity>> DREAM_CAULDRON =
            BLOCK_ENTITIES.register("dream_cauldron",
                    () -> BlockEntityType.Builder.of(DreamCauldronBlockEntity::new, ModBlocks.DREAM_CAULDRON.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DreamAccumulatorBlockEntity>> DREAM_ACCUMULATOR =
            BLOCK_ENTITIES.register("dream_accumulator",
                    () -> BlockEntityType.Builder.of(DreamAccumulatorBlockEntity::new, ModBlocks.DREAM_ACCUMULATOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowBlastFurnaceBlockEntity>> SHADOW_BLAST_FURNACE =
            BLOCK_ENTITIES.register("shadow_blast_furnace",
                    () -> BlockEntityType.Builder.of(ShadowBlastFurnaceBlockEntity::new, ModBlocks.SHADOW_BLAST_FURNACE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowBlastFurnaceAddonBlockEntity>> SHADOW_BLAST_FURNACE_ADDON =
            BLOCK_ENTITIES.register("shadow_blast_furnace_addon",
                    () -> BlockEntityType.Builder.of(ShadowBlastFurnaceAddonBlockEntity::new, ModBlocks.SHADOW_BLAST_FURNACE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NippyEdelweissBlockEntity>> NIPPY_EDELWEISS =
            BLOCK_ENTITIES.register("nippy_edelweiss",
                    () -> BlockEntityType.Builder.of(NippyEdelweissBlockEntity::new, ModBlocks.NIPPY_EDELWEISS.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    private ModBlockEntities() {
    }
}
