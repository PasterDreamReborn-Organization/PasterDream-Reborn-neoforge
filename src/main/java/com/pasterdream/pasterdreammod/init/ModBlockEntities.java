package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollBlockEntity;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollBlockEntity;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombBlockEntity;
import com.pasterdream.pasterdreammod.world.block.shadowhandtrap.ShadowHandTrapBlockEntity;
import com.pasterdream.pasterdreammod.world.block.lostswordtomb.LostSwordTombBlockEntity;
import com.pasterdream.pasterdreammod.world.block.lifecrystal.LifeCrystalBlockEntity;
import com.pasterdream.pasterdreammod.world.block.goldenfoxsculpture.GoldenFoxSculptureBlockEntity;
import com.pasterdream.pasterdreammod.world.block.foxsculpture.FoxSculptureBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.anvil.WeaponWorkshopAnvilBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.coolerpot.WeaponWorkshopCoolerPotBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.grindstone.WeaponWorkshopGrindStoneBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceAddonBlockEntity;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceBlockEntity;
import com.pasterdream.pasterdreammod.world.block.NippyEdelweissBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk.DyedreamDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.shadowdesk.ShadowDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.windmoordesk.WindMoorDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.picnicbasket.PicnicBasketBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.shadowchest.ShadowChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest.MeltDreamCrystalChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.windmoorcrate.WindMoorCrateBlockEntity;
import com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers.TheEndlessBookOfDreamSeekersBlockEntity;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorBlockEntity;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceAddonBlockEntity;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceBlockEntity;
import com.pasterdream.pasterdreammod.world.block.ecologyglassjar.EcologyGlassJarBlockEntity;
import com.pasterdream.pasterdreammod.world.block.fireflyglassjar.FireflyGlassJarBlockEntity;
import com.pasterdream.pasterdreammod.world.block.fireflynest.FireflyNestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.birdsnest.BirdsNestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.shadowbed.ShadowBedBlockEntity;
import com.pasterdream.pasterdreammod.world.block.twilightlantern.TwilightLanternBlockEntity;
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

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<com.pasterdream.pasterdreammod.world.block.dreamtrain.DreamTrainBroadcastBlockEntity>> DREAM_TRAIN_BROADCAST =
            BLOCK_ENTITIES.register("dream_train_broadcast", () -> BlockEntityType.Builder.of(com.pasterdream.pasterdreammod.world.block.dreamtrain.DreamTrainBroadcastBlockEntity::new, ModBlocks.DREAM_TRAIN_STRUCTURE.get()).build(null));

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

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowHandTrapBlockEntity>> SHADOW_HAND_TRAP =
            BLOCK_ENTITIES.register("shadow_hand_trap",
                    () -> BlockEntityType.Builder.of(ShadowHandTrapBlockEntity::new, ModBlocks.SHADOW_HAND_TRAP.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<QYMDollBlockEntity>> QYM_DOLL = BLOCK_ENTITIES.register("qym_doll", () -> BlockEntityType.Builder.of(QYMDollBlockEntity::new, ModBlocks.QYM_DOLL.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<UUZDollBlockEntity>> UUZ_DOLL = BLOCK_ENTITIES.register("uuz_doll", () -> BlockEntityType.Builder.of(UUZDollBlockEntity::new, ModBlocks.UUZ_DOLL.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WeaponWorkshopCraftingTableBlockEntity>> WEAPON_WORKSHOP_CRAFTING_TABLE =
            BLOCK_ENTITIES.register("weapon_workshop_crafting_table",
                    () -> BlockEntityType.Builder.of(WeaponWorkshopCraftingTableBlockEntity::new, ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WeaponWorkshopAnvilBlockEntity>> WEAPON_WORKSHOP_ANVIL =
            BLOCK_ENTITIES.register("weapon_workshop_anvil",
                    () -> BlockEntityType.Builder.of(WeaponWorkshopAnvilBlockEntity::new, ModBlocks.WEAPON_WORKSHOP_ANVIL.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WeaponWorkshopCoolerPotBlockEntity>> WEAPON_WORKSHOP_COOLER_POT =
            BLOCK_ENTITIES.register("weapon_workshop_cooler_pot",
                    () -> BlockEntityType.Builder.of(WeaponWorkshopCoolerPotBlockEntity::new, ModBlocks.WEAPON_WORKSHOP_COOLER_POT.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WeaponWorkshopGrindStoneBlockEntity>> WEAPON_WORKSHOP_GRIND_STONE =
            BLOCK_ENTITIES.register("weapon_workshop_grind_stone",
                    () -> BlockEntityType.Builder.of(WeaponWorkshopGrindStoneBlockEntity::new, ModBlocks.WEAPON_WORKSHOP_GRIND_STONE.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WeaponWorkshopBlastFurnaceBlockEntity>> WEAPON_WORKSHOP_BLAST_FURNACE =
            BLOCK_ENTITIES.register("weapon_workshop_blast_furnace",
                    () -> BlockEntityType.Builder.of(WeaponWorkshopBlastFurnaceBlockEntity::new, ModBlocks.WEAPON_WORKSHOP_BLAST_FURNACE.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WeaponWorkshopBlastFurnaceAddonBlockEntity>> WEAPON_WORKSHOP_BLAST_FURNACE_ADDON =
            BLOCK_ENTITIES.register("weapon_workshop_blast_furnace_addon",
                    () -> BlockEntityType.Builder.of(WeaponWorkshopBlastFurnaceAddonBlockEntity::new, ModBlocks.WEAPON_WORKSHOP_BLAST_FURNACE.get())
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
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MeltDreamCrystalChestBlockEntity>> MELT_DREAM_CRYSTAL_CHEST =
            BLOCK_ENTITIES.register("melt_dream_crystal_chest", () -> BlockEntityType.Builder.of(MeltDreamCrystalChestBlockEntity::new, ModBlocks.MELT_DREAM_CRYSTAL_CHEST.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OpenedMeltDreamCrystalChestBlockEntity>> OPENED_MELT_DREAM_CRYSTAL_CHEST =
            BLOCK_ENTITIES.register("opened_melt_dream_crystal_chest", () -> BlockEntityType.Builder.of(OpenedMeltDreamCrystalChestBlockEntity::new, ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get()).build(null));

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

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EcologyGlassJarBlockEntity>> ECOLOGY_GLASS_JAR =
            BLOCK_ENTITIES.register("ecology_glass_jar",
                    () -> BlockEntityType.Builder.of(EcologyGlassJarBlockEntity::new, ModBlocks.ECOLOGY_GLASS_JAR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FireflyGlassJarBlockEntity>> FIREFLY_GLASS_JAR =
            BLOCK_ENTITIES.register("firefly_glass_jar",
                    () -> BlockEntityType.Builder.of(FireflyGlassJarBlockEntity::new, ModBlocks.FIREFLY_GLASS_JAR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FireflyNestBlockEntity>> FIREFLY_NEST =
            BLOCK_ENTITIES.register("firefly_nest",
                    () -> BlockEntityType.Builder.of(FireflyNestBlockEntity::new, ModBlocks.FIREFLY_NEST.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BirdsNestBlockEntity>> BIRDS_NEST =
            BLOCK_ENTITIES.register("birds_nest",
                    () -> BlockEntityType.Builder.of(BirdsNestBlockEntity::new, ModBlocks.BIRDS_NEST.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowBlastFurnaceBlockEntity>> SHADOW_BLAST_FURNACE =
            BLOCK_ENTITIES.register("shadow_blast_furnace",
                    () -> BlockEntityType.Builder.of(ShadowBlastFurnaceBlockEntity::new, ModBlocks.SHADOW_BLAST_FURNACE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowBlastFurnaceAddonBlockEntity>> SHADOW_BLAST_FURNACE_ADDON =
            BLOCK_ENTITIES.register("shadow_blast_furnace_addon",
                    () -> BlockEntityType.Builder.of(ShadowBlastFurnaceAddonBlockEntity::new, ModBlocks.SHADOW_BLAST_FURNACE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NippyEdelweissBlockEntity>> NIPPY_EDELWEISS =
            BLOCK_ENTITIES.register("nippy_edelweiss",
                    () -> BlockEntityType.Builder.of(NippyEdelweissBlockEntity::new, ModBlocks.NIPPY_EDELWEISS.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GoldenFoxSculptureBlockEntity>> GOLDEN_FOX_SCULPTURE =
            BLOCK_ENTITIES.register("golden_fox_sculpture",
                    () -> BlockEntityType.Builder.of(GoldenFoxSculptureBlockEntity::new, ModBlocks.GOLDEN_FOX_SCULPTURE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FoxSculptureBlockEntity>> FOX_SCULPTURE =
            BLOCK_ENTITIES.register("fox_sculpture",
                    () -> BlockEntityType.Builder.of(FoxSculptureBlockEntity::new, ModBlocks.FOX_SCULPTURE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TwilightLanternBlockEntity>> TWILIGHT_LANTERN =
            BLOCK_ENTITIES.register("twilight_lantern",
                    () -> BlockEntityType.Builder.of(TwilightLanternBlockEntity::new, ModBlocks.TWILIGHT_LANTERN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowBedBlockEntity>> SHADOW_BED =
            BLOCK_ENTITIES.register("shadow_bed",
                    () -> BlockEntityType.Builder.of(ShadowBedBlockEntity::new, ModBlocks.SHADOW_BED.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    private ModBlockEntities() {
    }
}
