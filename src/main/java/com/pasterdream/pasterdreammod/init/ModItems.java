package com.pasterdream.pasterdreammod.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import com.pasterdream.pasterdreammod.world.item.debugsword.DebugSwordItem;
import com.pasterdream.pasterdreammod.world.item.lootgenerator.LootGeneratorItem;
import com.pasterdream.pasterdreammod.world.item.modelbreakparticleproviderblockitem.ModelBreakParticleProviderBlockItem;
import com.pasterdream.pasterdreammod.world.block.cropblock.MatureCropItem;
import com.pasterdream.pasterdreammod.world.item.NightmareFuelItem;
import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollItem;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollItem;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.ModToolTiers;
import com.pasterdream.pasterdreammod.world.item.ModArmorMaterials;
import com.pasterdream.pasterdreammod.world.item.BlueDewItem;
import com.pasterdream.pasterdreammod.world.item.DreamFertilizerItem;
import com.pasterdream.pasterdreammod.world.item.DyedreamHammerItem;
import com.pasterdream.pasterdreammod.world.item.MeltDreamCoinItem;
import com.pasterdream.pasterdreammod.world.item.MeltDreamCrystalFragmentItem;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.pasterdream.pasterdreammod.world.item.PulseWindRunnerCrystalItem;
import com.pasterdream.pasterdreammod.world.item.RedDewItem;
import com.pasterdream.pasterdreammod.world.item.PebbleItem;
import com.pasterdream.pasterdreammod.world.item.PliersItem;
import com.pasterdream.pasterdreammod.world.item.SandofTimeItem;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import com.pasterdream.pasterdreammod.world.item.prophecycard.EmptyProphecyCardItem;
import com.pasterdream.pasterdreammod.world.item.prophecycard.ProphecyCardItem;
import com.pasterdream.pasterdreammod.world.item.SnowballPebbleItem;
import com.pasterdream.pasterdreammod.world.item.FireproofBlockItem;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.WindwreathedThunderSpearItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.DyedreamArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.MeltDreamCrystalArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.SculkArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.TitaniumArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.WindRunnerCloakItem;
import com.pasterdream.pasterdreammod.world.item.curio.FracturedAngelStatueItem;
import com.pasterdream.pasterdreammod.world.item.curio.FourleafCloverCurioItem;
import com.pasterdream.pasterdreammod.world.item.curio.EmbryoBeltItem;
import com.pasterdream.pasterdreammod.world.item.curio.EmbryoCharmItem;
import com.pasterdream.pasterdreammod.world.item.curio.EmbryoNecklaceItem;
import com.pasterdream.pasterdreammod.world.item.curio.EmbryoRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.CarapaxCharmItem;
import com.pasterdream.pasterdreammod.world.item.curio.EndeyeCharmItem;
import com.pasterdream.pasterdreammod.world.item.curio.GarlandItem;
import com.pasterdream.pasterdreammod.world.item.curio.GoldCharmItem;
import com.pasterdream.pasterdreammod.world.item.curio.SeaCharmItem;
import com.pasterdream.pasterdreammod.world.item.curio.SnowVowHeadItem;
import com.pasterdream.pasterdreammod.world.item.curio.WorldtreeSeedpodItem;
import com.pasterdream.pasterdreammod.world.item.curio.LightButterflyCurioItem;
import com.pasterdream.pasterdreammod.world.item.curio.BrightButterflyCurioItem;
import com.pasterdream.pasterdreammod.world.item.curio.QymButterflyStarHairpinItem;
import com.pasterdream.pasterdreammod.world.item.curio.RabbitFootNecklaceItem;
import com.pasterdream.pasterdreammod.world.item.curio.FeatherNecklaceItem;
import com.pasterdream.pasterdreammod.world.item.curio.HealthNecklaceItem;
import com.pasterdream.pasterdreammod.world.item.curio.FireNecklaceItem;
import com.pasterdream.pasterdreammod.world.item.curio.CrossNecklaceItem;
import com.pasterdream.pasterdreammod.world.item.curio.NatureBeltItem;
import com.pasterdream.pasterdreammod.world.item.curio.TravelerBeltItem;
import com.pasterdream.pasterdreammod.world.item.curio.DreamTravelerBeltItem;
import com.pasterdream.pasterdreammod.world.item.curio.RedDewRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.StrikeRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.DukeCoinCurioItem;
import com.pasterdream.pasterdreammod.world.item.curio.HiyoriButterflyHairpinItem;
import com.pasterdream.pasterdreammod.world.item.curio.AllkindsRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.KaichuOmamoriItem;
import com.pasterdream.pasterdreammod.world.item.curio.TerraFloatingIslandItem;
import com.pasterdream.pasterdreammod.world.item.curio.BlessingOfCeciliaItem;
import com.pasterdream.pasterdreammod.world.item.curio.FadedBlessingOfCeciliaItem;
import com.pasterdream.pasterdreammod.world.item.curio.AngelWingItem;
import com.pasterdream.pasterdreammod.world.item.curio.ForsakensWingItem;
import com.pasterdream.pasterdreammod.world.item.curio.MachineLightWingItem;
import com.pasterdream.pasterdreammod.world.item.curio.TurnbackCloakItem;
import com.pasterdream.pasterdreammod.world.item.curio.EvasionCloakItem;
import com.pasterdream.pasterdreammod.world.item.ThermalDaggerItem;
import com.pasterdream.pasterdreammod.world.item.TerraBladeItem;
import com.pasterdream.pasterdreammod.world.item.StrawberryHeartItem;
import com.pasterdream.pasterdreammod.world.item.curio.CounterRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.MeltDreamEnergyRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.BobojiCurioItem;
import com.pasterdream.pasterdreammod.world.item.curio.ShadowBreathItem;
import com.pasterdream.pasterdreammod.world.item.curio.WarFlagItem;
import com.pasterdream.pasterdreammod.world.item.curio.WindSpiritItem;
import com.pasterdream.pasterdreammod.world.item.storagebag.StorageBagItem;
import com.pasterdream.pasterdreammod.world.item.storagebag.LargeStorageBagItem;
import com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle.ElixirBottleItem;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarItem;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceItem;
import com.pasterdream.pasterdreammod.world.block.shadowhandtrap.ShadowHandTrapItem;
import com.pasterdream.pasterdreammod.world.block.ecologyglassjar.EcologyGlassJarItem;
import com.pasterdream.pasterdreammod.world.block.fireflyglassjar.FireflyGlassJarItem;
import com.pasterdream.pasterdreammod.world.block.birdsnest.BirdsNestItem;
import com.pasterdream.pasterdreammod.world.item.deserttool.ChenjingmenDesertSwordItem;
import com.pasterdream.pasterdreammod.world.item.deserttool.DesertSwordItem;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamFoodItem;
import com.pasterdream.pasterdreammod.world.item.drinkandfooditem.PasterDreamDrinkItem;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.PasterDreamDrinkAndFoodProperties;
import com.pasterdream.pasterdreammod.world.item.grasstool.KusanagiItem;
import com.pasterdream.pasterdreammod.world.item.grasstool.MurakumoKusanagiItem;
import com.pasterdream.pasterdreammod.world.item.hellfiretool.*;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombItem;
import com.pasterdream.pasterdreammod.world.block.lostswordtomb.LostSwordTombItem;
import com.pasterdream.pasterdreammod.world.block.lifecrystal.LifeCrystalItem;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.shadowchest.ShadowChestItem;
import com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers.TheEndlessBookOfDreamSeekersItem;
import com.pasterdream.pasterdreammod.world.item.fluffywindalloy.*;
import com.pasterdream.pasterdreammod.world.item.moltengoldtool.*;
import com.pasterdream.pasterdreammod.world.item.shadowerosiontool.*;
import com.pasterdream.pasterdreammod.world.item.shadowalloytool.*;
import com.pasterdream.pasterdreammod.world.item.meltdreamtool.*;
import com.pasterdream.pasterdreammod.world.item.tidetool.*;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.IEventBus;
import org.jetbrains.annotations.Nullable;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PasterDreamMod.MOD_ID);
    public static final DeferredItem<Item> FOURLEAF_CLOVER_CURIO = ITEMS.register("fourleaf_clover_curio", FourleafCloverCurioItem::new);

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

    // ===== 强化石 =====
    public static final DeferredItem<Item> ATTACK_ENHANCE_STONE = ITEMS.register("attack_enhance_stone",
            () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)) {
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, context, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdream.enhance_stone.usage"));
                    tooltip.add(Component.translatable("tooltip.pasterdream.attack_enhance_stone.effect"));
                }
            });
    public static final DeferredItem<Item> LUCK_ENHANCE_STONE = ITEMS.register("luck_enhance_stone",
            () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)) {
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, context, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdream.enhance_stone.usage"));
                    tooltip.add(Component.translatable("tooltip.pasterdream.luck_enhance_stone.effect"));
                }
            });

    public static final DeferredItem<Item> BLACK_METAL_INGOT = ITEMS.register("black_metal_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLACK_METAL_NUGGET = ITEMS.register("black_metal_nugget",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RUST_BLACK_METAL_GRAIN = ITEMS.register("rust_black_metal_grain",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SHADOW_ALLOY_INGOT = ITEMS.register("shadow_alloy_ingot",
            () -> new Item(new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));

    // ===== 风之旅途材料 =====
    public static final DeferredItem<Item> CONGEAL_WIND = ITEMS.register("congeal_wind",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WIND_RUNNER_CRYSTAL = ITEMS.register("wind_runner_crystal",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CONGEAL_WIND_IRON_INGOT = ITEMS.register("congeal_wind_iron_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CONGEAL_WIND_IRON_NUGGET = ITEMS.register("congeal_wind_iron_nugget",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FLUFFY_WIND_ALLOY_INGOT = ITEMS.register("fluffy_wind_alloy_ingot",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> PULSE_WIND_RUNNER_CRYSTAL = ITEMS.register("pulse_wind_runner_crystal",
            () -> new PulseWindRunnerCrystalItem());
    public static final DeferredItem<Item> EMBRYO_CHARM = ITEMS.register("embryo_charm", EmbryoCharmItem::new);
    public static final DeferredItem<Item> EMBRYO_NECKLACE = ITEMS.register("embryo_necklace", EmbryoNecklaceItem::new);
    public static final DeferredItem<Item> EMBRYO_RING = ITEMS.register("embryo_ring", EmbryoRingItem::new);
    public static final DeferredItem<Item> EMBRYO_BELT = ITEMS.register("embryo_belt", EmbryoBeltItem::new);
    public static final DeferredItem<Item> SWEET_DREAM_MUSIC_DISC = ITEMS.register("sweet_dream_music_disc",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ModJukeboxSongs.SWEET_DREAM)));
    public static final DeferredItem<Item> SNOWFALL_DREAM_MUSIC_DISC = ITEMS.register("snowfall_dream_music_disc",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ModJukeboxSongs.SNOWFALL_DREAM)));
    public static final DeferredItem<Item> WIND_JOURNEY_MUSIC_DISC = ITEMS.register("wind_journey_music_disc",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ModJukeboxSongs.WIND_JOURNEY)));
    public static final DeferredItem<Item> AARONCOS_MUSIC_DISC = ITEMS.register("aaroncos_music_disc",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ModJukeboxSongs.AARONCOS_MUSIC)));
    public static final DeferredItem<Item> FRACTURED_ANGEL_STATUE = ITEMS.register("fractured_angel_statue",
            FracturedAngelStatueItem::new);
    public static final DeferredItem<Item> GOLD_CHARM = ITEMS.register("gold_charm", GoldCharmItem::new);
    public static final DeferredItem<Item> ENDEYE_CHARM = ITEMS.register("endeye_charm", EndeyeCharmItem::new);
    public static final DeferredItem<Item> SEA_CHARM = ITEMS.register("sea_charm", SeaCharmItem::new);
    public static final DeferredItem<Item> CARAPAX_CHARM = ITEMS.register("carapax_charm", CarapaxCharmItem::new);
    public static final DeferredItem<Item> WORLDTREE_SEEDPOD = ITEMS.register("worldtree_seedpod", WorldtreeSeedpodItem::new);
    public static final DeferredItem<Item> GARLAND = ITEMS.register("garland", GarlandItem::new);
    public static final DeferredItem<Item> SNOW_VOW_HEAD = ITEMS.register("snow_vow_head", SnowVowHeadItem::new);
    public static final DeferredItem<Item> LIGHT_BUTTERFLY_CURIO = ITEMS.register("light_butterfly_curio", LightButterflyCurioItem::new);
    public static final DeferredItem<Item> BRIGHT_BUTTERFLY_CURIO = ITEMS.register("bright_butterfly_curio", BrightButterflyCurioItem::new);
    public static final DeferredItem<Item> QYM_BUTTERFLY_STAR_HAIRPIN = ITEMS.register("qym_butterfly_star_hairpin", QymButterflyStarHairpinItem::new);
    public static final DeferredItem<Item> RABBIT_FOOT_NECKLACE = ITEMS.register("rabbit_foot_necklace", RabbitFootNecklaceItem::new);
    public static final DeferredItem<Item> FEATHER_NECKLACE = ITEMS.register("feather_necklace", FeatherNecklaceItem::new);
    public static final DeferredItem<Item> HEALTH_NECKLACE = ITEMS.register("health_necklace", HealthNecklaceItem::new);
    public static final DeferredItem<Item> FIRE_NECKLACE = ITEMS.register("fire_necklace", FireNecklaceItem::new);
    public static final DeferredItem<Item> CROSS_NECKLACE = ITEMS.register("cross_necklace", CrossNecklaceItem::new);
    public static final DeferredItem<Item> BOBOJI_CURIO = ITEMS.register("boboji_curio", BobojiCurioItem::new);
    public static final DeferredItem<Item> SHADOW_BREATH = ITEMS.register("shadow_breath", ShadowBreathItem::new);
    public static final DeferredItem<Item> WAR_FLAG = ITEMS.register("war_flag", WarFlagItem::new);
    public static final DeferredItem<Item> WIND_SPIRIT = ITEMS.register("wind_spirit", WindSpiritItem::new);

    // ===== 便携储物袋 =====
    public static final DeferredItem<Item> STORAGE_BAG = ITEMS.register("storage_bag", StorageBagItem::new);
    public static final DeferredItem<Item> LARGE_STORAGE_BAG = ITEMS.register("large_storage_bag", LargeStorageBagItem::new);

    // ===== 饰品（戒指） =====
    public static final DeferredItem<Item> RED_DEW_RING = ITEMS.register("red_dew_ring", RedDewRingItem::new);
    public static final DeferredItem<Item> STRIKE_RING = ITEMS.register("strike_ring", StrikeRingItem::new);
    public static final DeferredItem<Item> COUNTER_RING = ITEMS.register("counter_ring", CounterRingItem::new);
    public static final DeferredItem<Item> MELT_DREAM_ENERGY_RING = ITEMS.register("melt_dream_energy_ring",
            MeltDreamEnergyRingItem::new);

    // ===== 饰品（腰带） =====
    public static final DeferredItem<Item> NATURE_BELT = ITEMS.register("nature_belt", NatureBeltItem::new);
    public static final DeferredItem<Item> TRAVELER_BELT = ITEMS.register("traveler_belt", TravelerBeltItem::new);
    public static final DeferredItem<Item> DREAM_TRAVELER_BELT = ITEMS.register("dream_traveler_belt",
            DreamTravelerBeltItem::new);

    // ===== 露滴 =====
    public static final DeferredItem<Item> BLUE_HEART_OF_THE_SEA = ITEMS.register("blue_heart_of_the_sea",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> ELDER_GUARDIAN_SCALE = ITEMS.register("elder_guardian_scale",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RED_DEW = ITEMS.register("red_dew", RedDewItem::new);
    public static final DeferredItem<Item> BLUE_DEW = ITEMS.register("blue_dew", BlueDewItem::new);

    public static final DeferredItem<BlockItem> TITANIUM_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.TITANIUM_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_TITANIUM_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DEEPSLATE_TITANIUM_ORE);
    public static final DeferredItem<BlockItem> RAW_TITANIUM_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.RAW_TITANIUM_BLOCK);
    public static final DeferredItem<BlockItem> TITANIUM_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.TITANIUM_BLOCK);

    public static final DeferredItem<BlockItem> SOUL_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.SOUL_ORE);
    public static final DeferredItem<Item> SOUL_DUST = ITEMS.register("soul_dust", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SOUL_ESSENCE = ITEMS.register("soul_essence", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MAGIC_STONE = ITEMS.register("magic_stone",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CHARGED_AMETHYST = ITEMS.register("charged_amethyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> REED_ROD = ITEMS.register("reed_rod",
            () -> new Item(new Item.Properties()) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return 100;
                }
            });
    public static final DeferredItem<Item> PERGAMYN = ITEMS.register("pergamyn",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RYE_SEED = ITEMS.register("rye_seed",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MORTAR = ITEMS.register("mortar", () -> new MortarItem(new Item.Properties()));
    public static final DeferredItem<Item> FLOUR = ITEMS.register("flour",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COARSE_SALT = ITEMS.register("coarse_salt",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SALT = ITEMS.register("salt",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MOLTEN_GOLD_INGOT = ITEMS.register("molten_gold_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MOLTEN_GOLD_NUGGET = ITEMS.register("molten_gold_nugget",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_MOLTEN_GOLD = ITEMS.register("raw_molten_gold",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<BlockItem> MOLTEN_GOLD_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.MOLTEN_GOLD_ORE);
    public static final DeferredItem<BlockItem> MOLTEN_GOLD_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.MOLTEN_GOLD_BLOCK);
    public static final DeferredItem<BlockItem> SALT_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.SALT_BLOCK);
    public static final DeferredItem<BlockItem> CHARGED_AMETHYST_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.CHARGED_AMETHYST_BLOCK);

    public static final DeferredItem<Item> DYEDREAM_QUARTZ = ITEMS.registerSimpleItem("dyedream_quartz");
    public static final DeferredItem<Item> DYEDREAM_DUST = ITEMS.registerSimpleItem("dyedream_dust");
    public static final DeferredItem<Item> DYEDREAM_DUST_PIECE = ITEMS.registerSimpleItem("dyedream_dust_piece");
    public static final DeferredItem<Item> PINK_SLIMEBALL = ITEMS.registerSimpleItem("pink_slimeball");
    public static final DeferredItem<Item> DREAM_FERTILIZER = ITEMS.register("dream_fertilizer",
            DreamFertilizerItem::new);
    public static final DeferredItem<Item> DYEDREAM_DYE = ITEMS.registerSimpleItem("dyedream_dye");
    public static final DeferredItem<Item> MELT_DREAM_CRYSTAL_FRAGMENT = ITEMS.register("melt_dream_crystal_fragment",
            MeltDreamCrystalFragmentItem::new);
    public static final DeferredItem<Item> MELT_DREAM_COIN = ITEMS.register("melt_dream_coin",
            () -> new MeltDreamCoinItem(new Item.Properties(), false));
    public static final DeferredItem<Item> MELT_DREAM_COIN_PILE = ITEMS.register("melt_dream_coin_pile",
            () -> new MeltDreamCoinItem(new Item.Properties(), true));
    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_ORE);
    public static final DeferredItem<BlockItem> DYEDREAM_DUST_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_DUST_ORE);
    public static final DeferredItem<BlockItem> AMBER_CANDY_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.AMBER_CANDY_ORE);
    public static final DeferredItem<BlockItem> CALCITE_COAL_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_COAL_ORE);
    public static final DeferredItem<BlockItem> CALCITE_IRON_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_IRON_ORE);
    public static final DeferredItem<BlockItem> CALCITE_COPPER_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_COPPER_ORE);
    public static final DeferredItem<BlockItem> CALCITE_GOLD_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_GOLD_ORE);
    public static final DeferredItem<BlockItem> CALCITE_REDSTONE_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_REDSTONE_ORE);
    public static final DeferredItem<BlockItem> CALCITE_LAPIS_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_LAPIS_ORE);
    public static final DeferredItem<BlockItem> CALCITE_DIAMOND_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_DIAMOND_ORE);
    public static final DeferredItem<BlockItem> CALCITE_EMERALD_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_EMERALD_ORE);
    public static final DeferredItem<BucketItem> MELT_DREAM_LIQUID_BUCKET = ITEMS.register("melt_dream_liquid_bucket",
            () -> new BucketItem(ModFluids.MELT_DREAM_LIQUID.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<BucketItem> SHADOW_LIQUID_BUCKET = ITEMS.register("shadow_liquid_bucket",
            () -> new BucketItem(ModFluids.SHADOW_LIQUID.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON)));

    public static final DeferredItem<BlockItem> PINK_SLIME_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.PINK_SLIME_BLOCK);
    public static final DeferredItem<BlockItem> PINK_MUSHROOM_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.PINK_MUSHROOM_BLOCK);
    public static final DeferredItem<BlockItem> PINK_MUSHROOM_STEM = ITEMS.registerSimpleBlockItem(ModBlocks.PINK_MUSHROOM_STEM);
    public static final DeferredItem<BlockItem> PINK_SHROOMLIGHT = ITEMS.registerSimpleBlockItem(ModBlocks.PINK_SHROOMLIGHT);

    public static final DeferredItem<BlockItem> DYEDREAM_DIRT = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_DIRT);
    public static final DeferredItem<BlockItem> DYEDREAM_GRASS_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_GRASS_BLOCK);
    public static final DeferredItem<BlockItem> DYEDREAM_FARMLAND = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_FARMLAND);
    public static final DeferredItem<BlockItem> DYEDREAM_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_LOG);
    public static final DeferredItem<BlockItem> DYEDREAM_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_WOOD);
    public static final DeferredItem<BlockItem> DYEDREAM_LEAVES = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_LEAVES);
    public static final DeferredItem<BlockItem> DYEDREAM_WORLDTREE_LEAVES = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_WORLDTREE_LEAVES);
    public static final DeferredItem<BlockItem> DYEDREAM_SAND = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_SAND);

    public static final DeferredItem<BlockItem> DYEDREAM_BUDDING_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_BUDDING_BLOCK);
    public static final DeferredItem<BlockItem> SMALL_DYEDREAM_BUD = ITEMS.registerSimpleBlockItem(ModBlocks.SMALL_DYEDREAM_BUD);
    public static final DeferredItem<BlockItem> MEDIUM_DYEDREAM_BUD = ITEMS.registerSimpleBlockItem(ModBlocks.MEDIUM_DYEDREAM_BUD);
    public static final DeferredItem<BlockItem> LARGE_DYEDREAM_BUD = ITEMS.registerSimpleBlockItem(ModBlocks.LARGE_DYEDREAM_BUD);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_BUD_BLOCK);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_BRICKS = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_BUD_BRICKS);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_BUD_STAIRS);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_BUD_SLAB);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_BUD_WALL);
    public static final DeferredItem<BlockItem> DYEDREAM_ALLOY_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_ALLOY_BLOCK);
    public static final DeferredItem<BlockItem> DYEDREAM_ICE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_ICE);
    public static final DeferredItem<BlockItem> DYEDREAM_PACKED_ICE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_PACKED_ICE);
    public static final DeferredItem<BlockItem> CLOUD = ITEMS.registerSimpleBlockItem(ModBlocks.CLOUD);
    public static final DeferredItem<BlockItem> DARK_CLOUD = ITEMS.registerSimpleBlockItem(ModBlocks.DARK_CLOUD);
    public static final DeferredItem<BlockItem> BIG_BUBBLE = ITEMS.registerSimpleBlockItem(ModBlocks.BIG_BUBBLE);
    public static final DeferredItem<BlockItem> DYEDREAM_CRYSTAL_LANTERN = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_CRYSTAL_LANTERN);
    public static final DeferredItem<BlockItem> DYEDREAM_LANTERN = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_LANTERN);
    public static final DeferredItem<BlockItem> ICE_STONE = ITEMS.registerSimpleBlockItem(ModBlocks.ICE_STONE);
    public static final DeferredItem<BlockItem> ICE_BUD = ITEMS.registerSimpleBlockItem(ModBlocks.ICE_BUD);

    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> SMOOTH_DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> BRICKS_DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> PILLAR_DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> CHISELED_DYEDREAM_QUARTZ_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_BLOCK_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS);
    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_BLOCK_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB);
    public static final DeferredItem<BlockItem> DYEDREAM_QUARTZ_BLOCK_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL);

    public static final DeferredItem<BlockItem> DYEDREAM_SANDSTONE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_SANDSTONE);
    public static final DeferredItem<BlockItem> DYEDREAM_SANDSTONE_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_SANDSTONE_STAIRS);
    public static final DeferredItem<BlockItem> DYEDREAM_SANDSTONE_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_SANDSTONE_SLAB);
    public static final DeferredItem<BlockItem> DYEDREAM_SANDSTONE_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_SANDSTONE_WALL);
    public static final DeferredItem<BlockItem> CUT_DYEDREAM_SANDSTONE = ITEMS.registerSimpleBlockItem(ModBlocks.CUT_DYEDREAM_SANDSTONE);
    public static final DeferredItem<BlockItem> CUT_DYEDREAM_SANDSTONE_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.CUT_DYEDREAM_SANDSTONE_STAIRS);
    public static final DeferredItem<BlockItem> CUT_DYEDREAM_SANDSTONE_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.CUT_DYEDREAM_SANDSTONE_SLAB);
    public static final DeferredItem<BlockItem> CUT_DYEDREAM_SANDSTONE_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.CUT_DYEDREAM_SANDSTONE_WALL);
    public static final DeferredItem<BlockItem> CHISELED_DYEDREAM_SANDSTONE = ITEMS.registerSimpleBlockItem(ModBlocks.CHISELED_DYEDREAM_SANDSTONE);
    public static final DeferredItem<BlockItem> SMOOTH_DYEDREAM_SANDSTONE = ITEMS.registerSimpleBlockItem(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE);
    public static final DeferredItem<BlockItem> SMOOTH_DYEDREAM_SANDSTONE_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_STAIRS);
    public static final DeferredItem<BlockItem> SMOOTH_DYEDREAM_SANDSTONE_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_SLAB);
    public static final DeferredItem<BlockItem> SMOOTH_DYEDREAM_SANDSTONE_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_WALL);

    public static final DeferredItem<BlockItem> DYEDREAM_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_PLANKS);
    public static final DeferredItem<BlockItem> DYEDREAM_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_STAIRS);
    public static final DeferredItem<BlockItem> DYEDREAM_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_SLAB);
    public static final DeferredItem<BlockItem> DYEDREAM_FENCE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_FENCE);
    public static final DeferredItem<BlockItem> DYEDREAM_FENCE_GATE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_FENCE_GATE);
    public static final DeferredItem<BlockItem> DYEDREAM_PANE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_PANE);
    public static final DeferredItem<BlockItem> DYEDREAM_DOOR = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_DOOR);
    public static final DeferredItem<BlockItem> DYEDREAM_TRAPDOOR = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_TRAPDOOR);
    public static final DeferredItem<BlockItem> DYEDREAM_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> DYEDREAM_BUTTON = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_BUTTON);

    public static final DeferredItem<BlockItem> DYEDREAM_GLASS = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_GLASS);
    public static final DeferredItem<BlockItem> DYEDREAM_GLASS_PANE = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_GLASS_PANE);
    public static final DeferredItem<BlockItem> CARVE_DYEDREAM_GLASS = ITEMS.registerSimpleBlockItem(ModBlocks.CARVE_DYEDREAM_GLASS);
    public static final DeferredItem<BlockItem> CARVE_DYEDREAM_GLASS_PANE = ITEMS.registerSimpleBlockItem(ModBlocks.CARVE_DYEDREAM_GLASS_PANE);
    public static final DeferredItem<BlockItem> GOLD_CARVE_DYEDREAM_GLASS = ITEMS.registerSimpleBlockItem(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS);
    public static final DeferredItem<BlockItem> GOLD_CARVE_DYEDREAM_GLASS_PANE = ITEMS.registerSimpleBlockItem(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE);

    public static final DeferredItem<BlockItem> POLISHED_CALCITE = ITEMS.registerSimpleBlockItem(ModBlocks.POLISHED_CALCITE);
    public static final DeferredItem<BlockItem> POLISHED_CALCITE_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.POLISHED_CALCITE_STAIRS);
    public static final DeferredItem<BlockItem> POLISHED_CALCITE_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.POLISHED_CALCITE_SLAB);
    public static final DeferredItem<BlockItem> POLISHED_CALCITE_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.POLISHED_CALCITE_WALL);
    public static final DeferredItem<BlockItem> CALCITE_TILES = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_TILES);
    public static final DeferredItem<BlockItem> CALCITE_TILES_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_TILES_STAIRS);
    public static final DeferredItem<BlockItem> CALCITE_TILES_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_TILES_SLAB);
    public static final DeferredItem<BlockItem> CALCITE_TILES_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_TILES_WALL);
    public static final DeferredItem<BlockItem> CALCITE_CONE = ITEMS.registerSimpleBlockItem(ModBlocks.CALCITE_CONE);

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
    public static final DeferredItem<Item> DYEDREAM_FRUIT = ITEMS.register("dyedream_fruit",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0f)
                            .nutrition(3).saturationModifier(0.5f).build())));
    public static final DeferredItem<Item> RICE_CAKE = ITEMS.register("rice_cake",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(6).saturationModifier(0.415f).build())));

    // ===== 玻璃杯系列 =====
    public static final DeferredItem<Item> GLASS_CUP = ITEMS.register("glass_cup", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GLASS_CUP_OF_APPLE_JUICE = ITEMS.register("glass_cup_of_apple_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).alwaysEdible().build()).craftRemainder(ModItems.GLASS_CUP.get())));
    public static final DeferredItem<Item> GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA = ITEMS.register("glass_cup_of_uncooked_dyedream_flower_tea", () -> new Item(new Item.Properties().craftRemainder(ModItems.GLASS_CUP.get())));
    public static final DeferredItem<Item> GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA = ITEMS.register("glass_cup_of_cooked_dyedream_flower_tea", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 0), 1.0f).alwaysEdible().build()).craftRemainder(ModItems.GLASS_CUP.get())) {
        @Override
        public boolean isFoil(ItemStack stack) {
            return true;
        }
    });
    public static final DeferredItem<Item> GLASS_CUP_OF_DYEDREAM_JUICE = ITEMS.register("glass_cup_of_dyedream_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.5f).alwaysEdible().build()).meltDreamEnergyAdd(0.2).craftRemainder(ModItems.GLASS_CUP.get())));
    public static final DeferredItem<Item> GLASS_CUP_OF_HONEY_JUICE = ITEMS.register("glass_cup_of_honey_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.5f).alwaysEdible().build()).craftRemainder(ModItems.GLASS_CUP.get())) {
        @Override
        protected void onDrinkSpecial(LivingEntity entity, Level level) {
            if (!level.isClientSide) {
                entity.getActiveEffects().stream()
                        .map(MobEffectInstance::getEffect)
                        .filter(effect -> !effect.value().isBeneficial())
                        .toList()
                        .forEach(entity::removeEffect);
            }
        }
    });
    public static final DeferredItem<Item> GLASS_CUP_OF_WATERMELON_JUICE = ITEMS.register("glass_cup_of_watermelon_juice", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.5f).alwaysEdible().build()).craftRemainder(ModItems.GLASS_CUP.get())));

    // ===== 玻璃罐系列 =====
    public static final DeferredItem<Item> GLASS_JAR = ITEMS.register("glass_jar", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GLASS_JAR_OF_WATER = ITEMS.register("glass_jar_of_water", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().alwaysEdible().build()).useDuration(24).craftRemainder(ModItems.GLASS_JAR.get())));
    public static final DeferredItem<Item> GLASS_JAR_OF_MILK = ITEMS.register("glass_jar_of_milk", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().alwaysEdible().build()).useDuration(24).craftRemainder(ModItems.GLASS_JAR.get())));
    public static final DeferredItem<Item> GLASS_JAR_OF_YEAST = ITEMS.register("glass_jar_of_yeast", () -> new Item(new Item.Properties().craftRemainder(ModItems.GLASS_JAR.get())));
    public static final DeferredItem<Item> GLASS_JAR_OF_GUIDING_DRUG = ITEMS.register("glass_jar_of_guiding_drug", () -> new Item(new Item.Properties().craftRemainder(ModItems.GLASS_JAR.get())));
    public static final DeferredItem<Item> GLASS_JAR_OF_WIND_PLANT_EXTRACT = ITEMS.register("glass_jar_of_wind_plant_extract", () -> new Item(new Item.Properties().craftRemainder(ModItems.GLASS_JAR.get())));
    public static final DeferredItem<Item> GLASS_JAR_OF_INK = ITEMS.register("glass_jar_of_ink", () -> new Item(new Item.Properties().craftRemainder(ModItems.GLASS_JAR.get())));

    // ===== 食材系列 =====
    public static final DeferredItem<Item> AMBER_CANDY = ITEMS.register("amber_candy",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).alwaysEdible()
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 0), 1.0f).build())
                    .useDuration(16)));

    public static final DeferredItem<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.375f).build())));

    public static final DeferredItem<Item> DOUGH = ITEMS.register("dough",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.5f).build())));

    public static final DeferredItem<Item> DOUGH_WITH_EGG = ITEMS.register("dough_with_egg",
            () -> new Item(new Item.Properties()));

    // ===== 蛋糕系列 =====
    public static final DeferredItem<Item> CAKE_BASE = ITEMS.register("cake_base",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.375f).build())));

    public static final DeferredItem<Item> CREAM_BUN_CAKE = ITEMS.register("cream_bun_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.625f).build())));

    public static final DeferredItem<Item> BERRY_BUN_CAKE = ITEMS.register("berry_bun_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(7).saturationModifier(0.625f).alwaysEdible().build())));

    public static final DeferredItem<Item> TUBER_BUN_CAKE = ITEMS.register("tuber_bun_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(7).saturationModifier(0.625f).alwaysEdible().build())));

    public static final DeferredItem<Item> WATERMELON_BUN_CAKE = ITEMS.register("watermelon_bun_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(7).saturationModifier(0.625f).alwaysEdible().build())));

    public static final DeferredItem<Item> PUMPKIN_BUN_CAKE = ITEMS.register("pumpkin_bun_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(7).saturationModifier(0.625f).alwaysEdible().build())));

    public static final DeferredItem<Item> GLOW_BERRY_BUN_CAKE = ITEMS.register("glow_berry_bun_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(7).saturationModifier(0.625f).alwaysEdible().build())));

    public static final DeferredItem<Item> DYEDREAM_FRUIT_BUN_CAKE = ITEMS.register("dyedream_fruit_bun_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1).meltDreamEnergyAdd(0.3)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(7).alwaysEdible().saturationModifier(0.625f).build())));

    public static final DeferredItem<Item> CHOCOLATE_MATCHA_CAKE = ITEMS.register("chocolate_matcha_cake",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(8).saturationModifier(1).build())));

    // ===== 杂项食物系列 =====
    public static final DeferredItem<Item> DYEDREAM_POPSICLE = ITEMS.register("dyedream_popsicle",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1).meltDreamEnergyAdd(0.2)
                    .food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.5f).alwaysEdible().build())){
                @Override
                protected void onFoodSpecial(LivingEntity entity, Level level) {
                    if (entity.isOnFire()) {
                        int remainingTicks = entity.getRemainingFireTicks();
                        entity.setRemainingFireTicks(Math.max(0, remainingTicks - 100));
                    }
                }//减少燃烧时间
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, context, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_popsicle"));
                }
            });

    public static final DeferredItem<Item> FRIED_EGG = ITEMS.register("fried_egg",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.25f).build())));

    public static final DeferredItem<Item> BACON_AND_EGG = ITEMS.register("bacon_and_egg",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(10).saturationModifier(1.2f).build())));

    public static final DeferredItem<Item> ODD_BACON_AND_EGG = ITEMS.register("odd_bacon_and_egg",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(2)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK, 2400, 0), 1.0f)
                            .nutrition(11).saturationModifier(1.365f).build())));

    public static final DeferredItem<Item> HEART_CHOCOLATE = ITEMS.register("heart_chocolate",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(0.5)
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.375f).build())));

    public static final DeferredItem<Item> WHITE_HEART_CHOCOLATE = ITEMS.register("white_heart_chocolate",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.375f).build())));

    public static final DeferredItem<Item> PINK_HEART_CHOCOLATE = ITEMS.register("pink_heart_chocolate",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1.5).meltDreamEnergyAdd(0.9)
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.375f).build())));

    public static final DeferredItem<Item> BREAD_SLICE = ITEMS.register("bread_slice",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).build())));

    public static final DeferredItem<Item> SWISS_ROLL = ITEMS.register("swiss_roll",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(6).saturationModifier(0.75f).build())));

    public static final DeferredItem<Item> SANDWICH = ITEMS.register("sandwich",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(10).saturationModifier(0.9f).build())));

    public static final DeferredItem<Item> WAFER_BISCUIT = ITEMS.register("wafer_biscuit",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.165f).build())));

    public static final DeferredItem<Item> STUFFED_WAFER_COOKIES = ITEMS.register("stuffed_wafer_cookies",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(7).saturationModifier(1).build())));

    public static final DeferredItem<Item> GINGERBREAD_MAN = ITEMS.register("gingerbread_man",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(5).saturationModifier(0.6f).build())));

    public static final DeferredItem<Item> CANDY_CANE = ITEMS.register("candy_cane",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK, 1200, 0), 1.0f)
                            .nutrition(3).saturationModifier(0.5f).build())));

    public static final DeferredItem<Item> POPPING_CANDY = ITEMS.register("popping_candy",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.JUMP, 400, 3), 1.0f)
                            .nutrition(2).saturationModifier(0.25f).alwaysEdible().build())));

    public static final DeferredItem<Item> YINHUL_COTTON_CANDY = ITEMS.register("yinhul_cotton_candy",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(15).meltDreamEnergyAdd(15).rarity(Rarity.EPIC)
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.75f).alwaysEdible().build())));

    public static final DeferredItem<Item> MELT_DREAM_COTTON_CANDY = ITEMS.register("melt_dream_cotton_candy",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().meltDreamEnergyAdd(4)
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.625f).alwaysEdible().build())));

    public static final DeferredItem<Item> BUBBLE_GUM = ITEMS.register("bubble_gum",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(3)
                    .food(new FoodProperties.Builder().nutrition(1).alwaysEdible().build()).useDuration(64)));

    public static final DeferredItem<Item> GALAXY_JELLY = ITEMS.register("galaxy_jelly",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(6).alwaysEdible().saturationModifier(0.415f).build()).useDuration(25)
            ){
                @Override
                protected void onFoodSpecial(LivingEntity entity, Level level) {
                    // 检测饰品栏或盔甲栏是否有 galaxy_jelly_boost 标签的物品
                    boolean hasBoost = entity.getItemBySlot(EquipmentSlot.CHEST).is(ModItemTags.GALAXY_JELLY_BOOST)
                            || CuriosApi.getCuriosInventory(entity)
                                    .map(inv -> inv.findFirstCurio(
                                            stack -> stack.is(ModItemTags.GALAXY_JELLY_BOOST)).isPresent())
                                    .orElse(false);

                    if (hasBoost || entity.hasEffect(ModEffects.WIND_RUNNER)) {
                        entity.setDeltaMovement(entity.getDeltaMovement().x, 2, entity.getDeltaMovement().z);
                        entity.hurtMarked = true;
                        entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 140, 0));
                        if (entity instanceof Player player) {
                            player.getCooldowns().addCooldown(this, 80);
                        }
                        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                                SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 3.0F, 1.0F);
                    } else {
                        // 常规效果
                        entity.setDeltaMovement(entity.getDeltaMovement().x, 3, entity.getDeltaMovement().z);
                        entity.hurtMarked = true;
                        entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 140, 0));
                        if (entity instanceof Player player) {
                            player.getCooldowns().addCooldown(this, 40);
                        }
                        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                                SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 3.0F, 1.0F);
                    }

                    // 进度触发器：在建筑高度上限吃下星河果冻
                    if (entity instanceof ServerPlayer sp) {
                        ModCriteriaTriggers.EAT_GALAXY_JELLY_AT_HEIGHT.get().trigger(sp, sp.getY());
                    }
                }
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, context, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.galaxy_jelly"));
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.galaxy_jelly.flavor"));
                }
            });

    public static final DeferredItem<Item> MILKY_WAY_JELLY = ITEMS.register("milky_way_jelly",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(8).alwaysEdible().saturationModifier(0.5f).build()).useDuration(25)
            ){
                @Override
                protected void onFoodSpecial(LivingEntity entity, Level level) {
                    if (entity instanceof ServerPlayer sp) {
                        sp.teleportTo(sp.serverLevel(), sp.getX(), level.getMaxBuildHeight(), sp.getZ(), sp.getYRot(), sp.getXRot());
                    } else {
                        entity.setPos(entity.getX(), level.getMaxBuildHeight(), entity.getZ());
                    }
                    entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0));
                    if (entity instanceof Player player) {
                        player.getCooldowns().addCooldown(this, 400);
                    }
                    level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                            SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 3.0F, 1.0F);
                }
                @Override
                public boolean isFoil(ItemStack stack) {
                    return true;
                }
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, context, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.milky_way_jelly"));
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.milky_way_jelly.flavor"));
                }
            });

    public static final DeferredItem<Item> FORTUNE_JELLY = ITEMS.register("fortune_jelly",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(6).alwaysEdible().saturationModifier(0.415f).build()).useDuration(25)
            ){
                @Override
                protected void onFoodSpecial(LivingEntity entity, Level level) {
                    entity.addEffect(new MobEffectInstance(MobEffects.LUCK, 1200, 0));
                }
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, context, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.fortune_jelly"));
                }
            });

    public static final DeferredItem<Item> WIND_RUNNER_JELLY = ITEMS.register("wind_runner_jelly",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(6).alwaysEdible().saturationModifier(0.415f)
                            .effect(() -> new MobEffectInstance(ModEffects.WIND_RUNNER, 3600, 0), 1.0f)
                            .build()).useDuration(25)
            ));

    public static final DeferredItem<Item> LIGHT_ORGAN = ITEMS.register("light_organ",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(-1)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.GLOWING, 100, 0), 1.0f)
                            .nutrition(1).build())));

    public static final DeferredItem<Item> JELLYFISH_MUD = ITEMS.register("jellyfish_mud",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().sanAdd(-3)
                    .food(new FoodProperties.Builder().nutrition(1).saturationModifier(0f)
                            .effect(() -> new MobEffectInstance(ModEffects.WINDPROOF, 1200, 0), 1.0f).build())));

    public static final DeferredItem<Item> JELLYFISH_JELLO = ITEMS.register("jellyfish_jello",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).alwaysEdible()
                            .effect(() -> new MobEffectInstance(ModEffects.WINDPROOF, 12000, 0), 1.0f).build())));

    public static final DeferredItem<Item> QUEER_SOUP = ITEMS.register("queer_soup",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().meltDreamEnergyAdd(1)
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.375f).alwaysEdible()
                            .effect(() -> new MobEffectInstance(ModEffects.MISTY_DREAM, 6000, 0), 1.0f).build()))
            {
                @Override
                protected void onFoodSpecial(LivingEntity entity, Level level)
                {
                    if (!level.isClientSide && entity instanceof Player player && !player.isCreative())
                    {
                            ItemStack containerStack = new ItemStack(Items.BOWL);
                            if (!player.getInventory().add(containerStack))
                            {
                                player.drop(containerStack, false);
                            }
                    }
                }
            });

    private static final ResourceLocation LEGENDARY_DRAGON_HORN_ICE_CREAM_LUCK_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "legendary_dragon_horn_ice_cream_luck");

    public static final DeferredItem<Item> LEGENDARY_DRAGON_HORN_ICE_CREAM = ITEMS.register("legendary_dragon_horn_ice_cream",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties()
                    .food(new FoodProperties.Builder().nutrition(10).saturationModifier(1.2f).alwaysEdible().build())
                    .rarity(Rarity.EPIC)) {
                @Override
                protected void onFoodSpecial(LivingEntity entity, Level level) {
                    if (!level.isClientSide) {
                        if (entity instanceof Player player && !player.isCreative()) {
                            ItemStack containerStack = new ItemStack(Items.BOWL);
                            if (!player.getInventory().add(containerStack)) {
                                player.drop(containerStack, false);
                            }
                        }

                        var luckAttr = entity.getAttribute(Attributes.LUCK);
                        if (luckAttr != null) {
                            AttributeModifier existingModifier = luckAttr.getModifier(LEGENDARY_DRAGON_HORN_ICE_CREAM_LUCK_ID);
                            if (existingModifier == null) {
                                luckAttr.addPermanentModifier(new AttributeModifier(LEGENDARY_DRAGON_HORN_ICE_CREAM_LUCK_ID,
                                        10, AttributeModifier.Operation.ADD_VALUE));
                                if (level instanceof ServerLevel serverLevel) {
                                    serverLevel.sendParticles(ParticleTypes.SNOWFLAKE,
                                            entity.getX(), entity.getY() + 3, entity.getZ(),
                                            128, 2, 0.5, 2, 1);
                                }
                                if (entity instanceof Player player) {
                                    player.displayClientMessage(Component.translatable("item.pasterdream.legendary_dragon_horn_ice_cream.client.success"), false);
                                }
                            } else {
                                if (entity instanceof Player player) {
                                    player.displayClientMessage(Component.translatable("item.pasterdream.legendary_dragon_horn_ice_cream.client.fail"), false);
                                }
                            }
                        }
                    }
                }
            });

    public static final DeferredItem<Item> ELIXIR_BOTTLE = ITEMS.register("elixir_bottle", () -> new ElixirBottleItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> POTION_BOTTLE = ITEMS.register("potion_bottle", () -> new PotionBottleItem(""));

    public static final DeferredItem<Item> ELIXIR_BOTTLE_OF_MELT_DREAM = ITEMS.register("elixir_bottle_of_melt_dream",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().stacksTo(1).meltDreamEnergyAdd(25).rarity(Rarity.UNCOMMON)
                    .craftRemainder(ModItems.ELIXIR_BOTTLE.get())
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(2).alwaysEdible().build())));

    private static final ResourceLocation ELIXIR_BOTTLE_OF_RAGE_ELIXIR_SKILL_CD_ID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "elixir_bottle_of_rage_elixir_skill_cd_reduction");
    private static final ResourceLocation ELIXIR_BOTTLE_OF_RAGE_ELIXIR_ATTACK_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "elixir_bottle_of_rage_elixir_attack_damage");
    public static final DeferredItem<Item> ELIXIR_BOTTLE_OF_RAGE_ELIXIR = ITEMS.register("elixir_bottle_of_rage_elixir",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().stacksTo(1).rarity(Rarity.UNCOMMON)
                    .craftRemainder(ModItems.ELIXIR_BOTTLE.get())
                    .food(new FoodProperties.Builder().alwaysEdible().build())) {
                @Override
                protected void onDrinkSpecial(LivingEntity entity, Level level) {
                    if (level.isClientSide) {
                        return;
                    }

                    boolean applied = false;

                    var skillCdAttr = entity.getAttribute(ModAttributes.SKILL_COOLDOWN_REDUCTION);
                    if (skillCdAttr != null && skillCdAttr.getModifier(ELIXIR_BOTTLE_OF_RAGE_ELIXIR_SKILL_CD_ID) == null) {
                        skillCdAttr.addPermanentModifier(new AttributeModifier(ELIXIR_BOTTLE_OF_RAGE_ELIXIR_SKILL_CD_ID,
                                0.15, AttributeModifier.Operation.ADD_VALUE));
                        applied = true;
                    }

                    var attackDamageAttr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
                    if (attackDamageAttr != null && attackDamageAttr.getModifier(ELIXIR_BOTTLE_OF_RAGE_ELIXIR_ATTACK_DAMAGE_ID) == null) {
                        attackDamageAttr.addPermanentModifier(new AttributeModifier(ELIXIR_BOTTLE_OF_RAGE_ELIXIR_ATTACK_DAMAGE_ID,
                                0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                        applied = true;
                    }

                    if (applied) {
                        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                                ModSounds.AWAKE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                        if (entity instanceof Player player) {
                            player.displayClientMessage(Component.translatable("item.pasterdream.elixir_bottle_of_rage_elixir.client.success"), false);
                        }
                    } else if (entity instanceof Player player) {
                        player.displayClientMessage(Component.translatable("item.pasterdream.elixir_bottle_of_rage_elixir.client.fail"), false);
                    }
                }
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, context, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.elixir_bottle_of_rage_elixir.1"));
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.elixir_bottle_of_rage_elixir.2"));
                }
            });

    public static final DeferredItem<Item> PINEAPPLE_LOVE_SEA = ITEMS.register("pineapple_love_sea",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().sanAdd(15).rarity(Rarity.UNCOMMON)
                    .food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COOK, 6000, 1), 1.0f)
                            .nutrition(3).saturationModifier(0.5f).alwaysEdible().build())));

    public static final DeferredItem<Item> BUBBLE_TEA = ITEMS.register("bubble_tea", () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties()
            .food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.7f).alwaysEdible().build())));

    // ===== 特殊食物 =====
    public static final DeferredItem<Item> MOLTEN_GOLD_APPLE = ITEMS.register("molten_gold_apple",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().rarity(Rarity.RARE)
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(1.2f).alwaysEdible()//金苹果数值
                            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400, 0), 1.0f)//2分钟抗火
                            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2400, 1), 1.0f)//2分钟急迫II
                            .build())));

    public static final DeferredItem<Item> ENCHANTED_MOLTEN_GOLD_APPLE = ITEMS.register("enchanted_molten_gold_apple",
            () -> new PasterDreamFoodItem(new PasterDreamDrinkAndFoodProperties().rarity(Rarity.EPIC)
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(1.2f).alwaysEdible()//附魔金苹果数值
                            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0f)//5分钟抗火
                            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 3), 1.0f)//5分钟急迫IV
                            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 2), 1.0f)//5分钟迅捷III
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0f)//20秒生命恢复II
                            .build())) {
                @Override
                public boolean isFoil(ItemStack stack) {
                    return true;
                }
            });

    // ===== 秋麒麟茶与染梦香水 =====
    public static final DeferredItem<Item> GLASS_JAR_OF_GOLDENROD_TEA = ITEMS.register("glass_jar_of_goldenrod_tea",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().nutrition(1).effect(() -> new MobEffectInstance(ModEffects.GOLDENROD_TEA, 3600, 0), 1.0f).alwaysEdible().build()).useDuration(24).craftRemainder(ModItems.GLASS_JAR.get())) {
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, context, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.goldenrod_tea"));
                }
            });
    public static final DeferredItem<Item> GLASS_JAR_OF_DYEDREAM_PERFUME = ITEMS.register("glass_jar_of_dyedream_perfume",
            () -> new PasterDreamDrinkItem(new PasterDreamDrinkAndFoodProperties().food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.DYEDREAM_PERFUME, 12000, 0), 1.0f).alwaysEdible().build()).useDuration(24).craftRemainder(ModItems.GLASS_JAR.get())) {
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, context, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_perfume"));
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_perfume.flavor.1"));
                    tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_perfume.flavor.2"));
                }

                @Override
                protected void onDrinkSpecial(LivingEntity entity, Level level) {
                    if (entity instanceof ServerPlayer serverPlayer) {
                        serverPlayer.getStats().setValue(serverPlayer, Stats.CUSTOM.get(Stats.TIME_SINCE_REST), 0);
                        AABB range = entity.getBoundingBox().inflate(64.0D);
                        for (Phantom phantom : level.getEntitiesOfClass(Phantom.class, range)) {
                            if (phantom.getTarget() == entity) {
                                phantom.setTarget(null);
                            }
                        }
                    }
                }
            });

    public static final DeferredItem<Item> BLACK_STICK = ITEMS.registerSimpleItem("black_stick");
    public static final DeferredItem<Item> LAMP_SHADOW_BROKEN_NOTE = ITEMS.register("lamp_shadow_broken_note",
            () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
    public static final DeferredItem<Item> DYEDREAM_BROKEN_NOTE = ITEMS.register("dyedream_broken_note",
            () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)));
    public static final DeferredItem<Item> TITANIUM_UPGRADE = ITEMS.registerSimpleItem("titanium_upgrade", new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> SCULK_UPGRADE = ITEMS.registerSimpleItem("sculk_upgrade", new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> DYEDREAM_UPGRADE = ITEMS.registerSimpleItem("dyedream_upgrade", new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> FLUFFY_WIND_ALLOY_UPGRADE = ITEMS.registerSimpleItem("fluffy_wind_alloy_upgrade", new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON));
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

    // ===== 凝风铁工具 =====
    public static final DeferredItem<Item> CONGEAL_WIND_IRON_SWORD = ITEMS.register("congeal_wind_iron_sword",
            () -> new SwordItem(ModToolTiers.CONGEAL_WIND_IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CONGEAL_WIND_IRON, 3, -1.4f))));
    public static final DeferredItem<Item> CONGEAL_WIND_IRON_PICKAXE = ITEMS.register("congeal_wind_iron_pickaxe",
            () -> new PickaxeItem(ModToolTiers.CONGEAL_WIND_IRON, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(ModToolTiers.CONGEAL_WIND_IRON, 1, -1.8f))));
    public static final DeferredItem<Item> CONGEAL_WIND_IRON_AXE = ITEMS.register("congeal_wind_iron_axe",
            () -> new AxeItem(ModToolTiers.CONGEAL_WIND_IRON, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(ModToolTiers.CONGEAL_WIND_IRON, 5.5f, -2.0f))));
    public static final DeferredItem<Item> CONGEAL_WIND_IRON_SHOVEL = ITEMS.register("congeal_wind_iron_shovel",
            () -> new ShovelItem(ModToolTiers.CONGEAL_WIND_IRON, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(ModToolTiers.CONGEAL_WIND_IRON, 1.5f, -2.0f))));
    public static final DeferredItem<Item> CONGEAL_WIND_IRON_HOE = ITEMS.register("congeal_wind_iron_hoe",
            () -> new HoeItem(ModToolTiers.CONGEAL_WIND_IRON, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(ModToolTiers.CONGEAL_WIND_IRON, -2, -1.0f))));

    // ===== 萦风合金工具 =====
    public static final DeferredItem<Item> WINDWREATHED_THUNDER_SPEAR = ITEMS.register("windwreathed_thunder_spear",
            () -> new WindwreathedThunderSpearItem(ModToolTiers.WINDWREATHED_THUNDER_SPEAR, 2, -1.6f,
                    new Item.Properties().fireResistant().rarity(ModRarities.legendary())));
    public static final DeferredItem<Item> FLUFFY_WIND_ALLOY_SWORD = ITEMS.register("fluffy_wind_alloy_sword",
            () -> new FluffyWindAlloySwordItem(ModToolTiers.FLUFFY_WIND_ALLOY, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FLUFFY_WIND_ALLOY_PICKAXE = ITEMS.register("fluffy_wind_alloy_pickaxe",
            () -> new FluffyWindAlloyPickaxeItem(ModToolTiers.FLUFFY_WIND_ALLOY, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FLUFFY_WIND_ALLOY_AXE = ITEMS.register("fluffy_wind_alloy_axe",
            () -> new FluffyWindAlloyAxeItem(ModToolTiers.FLUFFY_WIND_ALLOY, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FLUFFY_WIND_ALLOY_SHOVEL = ITEMS.register("fluffy_wind_alloy_shovel",
            () -> new FluffyWindAlloyShovelItem(ModToolTiers.FLUFFY_WIND_ALLOY, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FLUFFY_WIND_ALLOY_HOE = ITEMS.register("fluffy_wind_alloy_hoe",
            () -> new FluffyWindAlloyHoeItem(ModToolTiers.FLUFFY_WIND_ALLOY, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FLUFFY_WIND_BOW = ITEMS.register("fluffy_wind_bow",
            () -> new FluffyWindBowItem(new Item.Properties().fireResistant().durability(842).rarity(Rarity.UNCOMMON)));

    // ===== 萦风合金装备与风行者披风 =====
    public static final DeferredItem<Item> FLUFFY_WIND_ALLOY_HELMET = ITEMS.register("fluffy_wind_alloy_helmet",
            () -> new FluffyWindAlloyArmorItem(ModArmorMaterials.FLUFFY_WIND_ALLOY, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.HELMET.getDurability(46))));
    public static final DeferredItem<Item> FLUFFY_WIND_ALLOY_CHESTPLATE = ITEMS.register("fluffy_wind_alloy_chestplate",
            () -> new FluffyWindAlloyArmorItem(ModArmorMaterials.FLUFFY_WIND_ALLOY, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.CHESTPLATE.getDurability(46))));
    public static final DeferredItem<Item> FLUFFY_WIND_ALLOY_LEGGINGS = ITEMS.register("fluffy_wind_alloy_leggings",
            () -> new FluffyWindAlloyArmorItem(ModArmorMaterials.FLUFFY_WIND_ALLOY, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.LEGGINGS.getDurability(46))));
    public static final DeferredItem<Item> FLUFFY_WIND_ALLOY_BOOTS = ITEMS.register("fluffy_wind_alloy_boots",
            () -> new FluffyWindAlloyArmorItem(ModArmorMaterials.FLUFFY_WIND_ALLOY, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.BOOTS.getDurability(46))));
    public static final DeferredItem<Item> WIND_RUNNER_CLOAK = ITEMS.register("wind_runner_cloak",
            () -> new WindRunnerCloakItem(ModArmorMaterials.WIND_RUNNER_CLOAK, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)
                            .durability(ArmorItem.Type.CHESTPLATE.getDurability(30))));

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
    public static final DeferredItem<Item> SHADOW_DUNGEON_KEY = ITEMS.register("shadow_dungeon_key",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)) {
                @Override
                public boolean hasCustomEntity(ItemStack stack) {
                    return true;
                }

                @Nullable
                @Override
                public Entity createEntity(Level level, Entity location, ItemStack stack) {
                    var entity = new IndestructibleItemEntity(level, location.getX(), location.getY(), location.getZ(), stack);
                    entity.setDefaultPickUpDelay();
                    entity.setDeltaMovement(location.getDeltaMovement());
                    return entity;
                }
            });
    public static final DeferredItem<BlockItem> SHADOW = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW);
    public static final DeferredItem<BlockItem> THICK_SHADOW = ITEMS.registerSimpleBlockItem(ModBlocks.THICK_SHADOW);
    public static final DeferredItem<BlockItem> SHADOW_STONE = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE);
    public static final DeferredItem<BlockItem> SHADOW_STONE_BRICK = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE_BRICK);
    public static final DeferredItem<BlockItem> SHADOW_STONE_BRICK_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE_BRICK_STAIRS);
    public static final DeferredItem<BlockItem> SHADOW_STONE_BRICK_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE_BRICK_SLAB);
    public static final DeferredItem<BlockItem> SHADOW_STONE_BRICK_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE_BRICK_WALL);
    public static final DeferredItem<BlockItem> NARROW_SHADOW_STONE_BRICK = ITEMS.registerSimpleBlockItem(ModBlocks.NARROW_SHADOW_STONE_BRICK);
    public static final DeferredItem<BlockItem> NARROW_SHADOW_STONE_BRICK_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS);
    public static final DeferredItem<BlockItem> NARROW_SHADOW_STONE_BRICK_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB);
    public static final DeferredItem<BlockItem> NARROW_SHADOW_STONE_BRICK_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL);
    public static final DeferredItem<BlockItem> SHADOW_STONE_TILES = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE_TILES);
    public static final DeferredItem<BlockItem> SHADOW_STONE_TILES_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE_TILES_STAIRS);
    public static final DeferredItem<BlockItem> SHADOW_STONE_TILES_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE_TILES_SLAB);
    public static final DeferredItem<BlockItem> SHADOW_STONE_TILES_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE_TILES_WALL);
    public static final DeferredItem<BlockItem> CRACKED_SHADOW_STONE_BRICK = ITEMS.registerSimpleBlockItem(ModBlocks.CRACKED_SHADOW_STONE_BRICK);
    public static final DeferredItem<BlockItem> CHISELED_SHADOW_STONE_BRICK = ITEMS.registerSimpleBlockItem(ModBlocks.CHISELED_SHADOW_STONE_BRICK);
    public static final DeferredItem<BlockItem> SHADOW_STONE_CAGE_RUNE = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE_CAGE_RUNE);
    public static final DeferredItem<BlockItem> SHADOW_STONE_HOLY_GRAIL_RUNE = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE_HOLY_GRAIL_RUNE);
    public static final DeferredItem<BlockItem> SHADOW_STONE_OBLATION_RUNE = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE_OBLATION_RUNE);
    public static final DeferredItem<BlockItem> SHADOW_STONE_TRIPOD_CAULDRON_RUNE = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_STONE_TRIPOD_CAULDRON_RUNE);
    public static final DeferredItem<BlockItem> SHADOW_DUNGEON_STONE = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_DUNGEON_STONE);
    public static final DeferredItem<BlockItem> CHISELED_SHADOW_DUNGEON_BRICKS = ITEMS.registerSimpleBlockItem(ModBlocks.CHISELED_SHADOW_DUNGEON_BRICKS);
    public static final DeferredItem<BlockItem> SHADOW_DUNGEON_BRICKS = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_DUNGEON_BRICKS);
    public static final DeferredItem<BlockItem> CRACKED_SHADOW_DUNGEON_BRICKS = ITEMS.registerSimpleBlockItem(ModBlocks.CRACKED_SHADOW_DUNGEON_BRICKS);
    public static final DeferredItem<BlockItem> FRACTURED_SHADOW_DUNGEON_BRICKS = ITEMS.registerSimpleBlockItem(ModBlocks.FRACTURED_SHADOW_DUNGEON_BRICKS);
    public static final DeferredItem<BlockItem> SHADOW_DUNGEON_BRICK_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS);
    public static final DeferredItem<BlockItem> SHADOW_DUNGEON_BRICK_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB);
    public static final DeferredItem<BlockItem> SHATTERED_SHADOW_DUNGEON_BRICKS = ITEMS.registerSimpleBlockItem(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS);
    public static final DeferredItem<BlockItem> SHADOW_ARENA_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_ARENA_BLOCK);
    public static final DeferredItem<BlockItem> SHADOW_FISSURE_0 = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_FISSURE_0);
    public static final DeferredItem<BlockItem> SHADOW_FISSURE_1 = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_FISSURE_1);
    public static final DeferredItem<BlockItem> SHADOW_FISSURE_2 = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_FISSURE_2);
    public static final DeferredItem<BlockItem> SHADOW_FISSURE_3 = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_FISSURE_3);
    public static final DeferredItem<BlockItem> SHADOW_FISSURE_4 = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_FISSURE_4);
    public static final DeferredItem<BlockItem> SHADOW_FISSURE_5 = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_FISSURE_5);
    public static final DeferredItem<BlockItem> SHADOW_NYLIUM = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_NYLIUM);
    public static final DeferredItem<BlockItem> SHADOW_LIGHT = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_LIGHT);
    public static final DeferredItem<BlockItem> SHADOW_SHROOMLIGHT = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_SHROOMLIGHT);
    public static final DeferredItem<BlockItem> SHADOW_CANDLE = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_CANDLE);
    public static final DeferredItem<BlockItem> SHADOW_WART_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_WART_BLOCK);
    public static final DeferredItem<BlockItem> SHADOW_STEM = ITEMS.register("shadow_stem",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_STEM.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_HYPHAE = ITEMS.register("shadow_hyphae",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_HYPHAE.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> STRIPPED_SHADOW_STEM = ITEMS.register("stripped_shadow_stem",
            () -> new FireproofBlockItem(ModBlocks.STRIPPED_SHADOW_STEM.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> STRIPPED_SHADOW_HYPHAE = ITEMS.register("stripped_shadow_hyphae",
            () -> new FireproofBlockItem(ModBlocks.STRIPPED_SHADOW_HYPHAE.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_PLANKS = ITEMS.register("shadow_planks",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_PLANKS.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_STAIRS = ITEMS.register("shadow_stairs",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_STAIRS.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_SLAB = ITEMS.register("shadow_slab",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_SLAB.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_FENCE = ITEMS.register("shadow_fence",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_FENCE.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_FENCE_GATE = ITEMS.register("shadow_fence_gate",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_FENCE_GATE.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_PANE = ITEMS.register("shadow_pane",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_PANE.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_DOOR = ITEMS.register("shadow_door",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_DOOR.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_TRAPDOOR = ITEMS.register("shadow_trapdoor",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_TRAPDOOR.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_PRESSURE_PLATE = ITEMS.register("shadow_pressure_plate",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_BUTTON = ITEMS.register("shadow_button",
            () -> new FireproofBlockItem(ModBlocks.SHADOW_BUTTON.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_BOOKSHELF = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_BOOKSHELF);
    public static final DeferredItem<BlockItem> WORN_SHADOW_BOOKSHELF = ITEMS.registerSimpleBlockItem(ModBlocks.WORN_SHADOW_BOOKSHELF);
    public static final DeferredItem<BlockItem> COBWEB_SHADOW_BOOKSHELF = ITEMS.registerSimpleBlockItem(ModBlocks.COBWEB_SHADOW_BOOKSHELF);
    public static final DeferredItem<BlockItem> KEY_SHADOW_BOOKSHELF = ITEMS.registerSimpleBlockItem(ModBlocks.KEY_SHADOW_BOOKSHELF);
    public static final DeferredItem<BlockItem> BLACK_METAL_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.BLACK_METAL_BLOCK);
    public static final DeferredItem<BlockItem> RUST_BLACK_METAL_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.RUST_BLACK_METAL_BLOCK);
    public static final DeferredItem<BlockItem> RUST_BLACK_METAL_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.RUST_BLACK_METAL_WALL);
    public static final DeferredItem<BlockItem> RUST_BLACK_METAL_BARS = ITEMS.registerSimpleBlockItem(ModBlocks.RUST_BLACK_METAL_BARS);
    public static final DeferredItem<BlockItem> SHADOW_CLAY_POT = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_CLAY_POT);
    public static final DeferredItem<BlockItem> WEAPON_WORKSHOP_CRAFTING_TABLE = ITEMS.registerSimpleBlockItem(ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE);
    public static final DeferredItem<BlockItem> DYEDREAM_DESK = ITEMS.registerSimpleBlockItem(ModBlocks.DYEDREAM_DESK);
    public static final DeferredItem<BlockItem> SHADOW_DESK = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_DESK);
    public static final DeferredItem<BlockItem> WIND_MOOR_DESK = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_DESK);
    public static final DeferredItem<BlockItem> PICNIC_BASKET = ITEMS.registerSimpleBlockItem(ModBlocks.PICNIC_BASKET);
    public static final DeferredItem<Item> SHADOW_CHEST = ITEMS.register("shadow_chest",
            () -> new ShadowChestItem(ModBlocks.SHADOW_CHEST.get(), new Item.Properties()));
    public static final DeferredItem<Item> SHADOW_HAND_TRAP = ITEMS.register("shadow_hand_trap",
            () -> new ShadowHandTrapItem(ModBlocks.SHADOW_HAND_TRAP.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> WIND_MOOR_CRATE = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_CRATE);
    public static final DeferredItem<Item> THE_ENDLESS_BOOK_OF_DREAM_SEEKERS = ITEMS.register("the_endless_book_of_dream_seekers",
            () -> new TheEndlessBookOfDreamSeekersItem(ModBlocks.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get(), new Item.Properties()));
    public static final DeferredItem<Item> PLIERS = ITEMS.register("pliers", () -> new PliersItem(new Item.Properties()));
    public static final DeferredItem<Item> FEATHER_PEN = ITEMS.register("feather_pen", () -> new Item(new Item.Properties().durability(8)));
    public static final DeferredItem<Item> SAND_OF_TIME = ITEMS.register("sand_of_time", SandofTimeItem::new);
    public static final DeferredItem<BlockItem> CLAYPAN = ITEMS.registerSimpleBlockItem(ModBlocks.CLAYPAN);
    public static final DeferredItem<Item> DREAM_CAULDRON = ITEMS.register("dream_cauldron", () -> new com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronItem(ModBlocks.DREAM_CAULDRON.get(), new Item.Properties()));
    public static final DeferredItem<Item> DREAM_ACCUMULATOR = ITEMS.register("dream_accumulator", () -> new com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorItem(ModBlocks.DREAM_ACCUMULATOR.get(), new Item.Properties()));
    public static final DeferredItem<Item> SORBENT = ITEMS.register("sorbent", () -> new Item(new Item.Properties().durability(54000)));
    public static final DeferredItem<Item> SHADOW_BLAST_FURNACE = ITEMS.register("shadow_blast_furnace", () -> new ShadowBlastFurnaceItem(ModBlocks.SHADOW_BLAST_FURNACE.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> SHADOW_BLAST_FURNACE_CORE = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_BLAST_FURNACE_CORE);
    public static final DeferredItem<BlockItem> SHADOW_DUNGEON_WALL_KEY = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_DUNGEON_WALL_KEY);
    public static final DeferredItem<BlockItem> SHADOW_DUNGEON_FLOOR_KEY = ITEMS.registerSimpleBlockItem(ModBlocks.SHADOW_DUNGEON_FLOOR_KEY);
    public static final DeferredItem<Item> ECOLOGY_GLASS_JAR = ITEMS.register("ecology_glass_jar", () -> new EcologyGlassJarItem(ModBlocks.ECOLOGY_GLASS_JAR.get(), new Item.Properties()));
    public static final DeferredItem<Item> FIREFLY_GLASS_JAR = ITEMS.register("firefly_glass_jar", () -> new FireflyGlassJarItem(ModBlocks.FIREFLY_GLASS_JAR.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> FIREFLY_NEST = ITEMS.registerSimpleBlockItem(ModBlocks.FIREFLY_NEST);
    public static final DeferredItem<Item> BIRDS_NEST = ITEMS.register("birds_nest", () -> new BirdsNestItem(ModBlocks.BIRDS_NEST.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> CHRISTMAS_LIGHTS = ITEMS.registerSimpleBlockItem(ModBlocks.CHRISTMAS_LIGHTS);
    public static final DeferredItem<BlockItem> SMALL_STONE_SPIRIT_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.SMALL_STONE_SPIRIT_BLOCK);

    // ===== 影蚀系列 =====
    public static final DeferredItem<Item> SHADOW_EROSION_DAGGER = ITEMS.register("shadow_erosion_dagger",
            () -> new ShadowErosionDaggerItem(ModToolTiers.SHADOW_EROSION, 3, -2.0f, new Item.Properties()));
    public static final DeferredItem<Item> SHADOW_EROSION_PICKAXE = ITEMS.register("shadow_erosion_pickaxe",
            () -> new ShadowErosionPickaxeItem(ModToolTiers.SHADOW_EROSION, 2, -2.8f, new Item.Properties()));
    public static final DeferredItem<Item> SHADOW_EROSION_AXE = ITEMS.register("shadow_erosion_axe",
            () -> new ShadowErosionAxeItem(ModToolTiers.SHADOW_EROSION, 7, -3.0f, new Item.Properties()));
    public static final DeferredItem<Item> SHADOW_EROSION_SHOVEL = ITEMS.register("shadow_erosion_shovel",
            () -> new ShadowErosionShovelItem(ModToolTiers.SHADOW_EROSION, 3, -3.0f, new Item.Properties()));
    public static final DeferredItem<Item> SHADOW_EROSION_HOE = ITEMS.register("shadow_erosion_hoe",
            () -> new ShadowErosionHoeItem(ModToolTiers.SHADOW_EROSION, -2, 0.0f, new Item.Properties()));

    // ===== 暗影合金系列 =====
    public static final DeferredItem<Item> SHADOW_ALLOY_PICKAXE = ITEMS.register("shadow_alloy_pickaxe",
            () -> new ShadowAlloyPickaxeItem(ModToolTiers.SHADOW_ALLOY, 2, -2.8f, new Item.Properties()));
    public static final DeferredItem<Item> SHADOW_ALLOY_AXE = ITEMS.register("shadow_alloy_axe",
            () -> new ShadowAlloyAxeItem(ModToolTiers.SHADOW_ALLOY, 7, -3.0f, new Item.Properties()));
    public static final DeferredItem<Item> SHADOW_ALLOY_SHOVEL = ITEMS.register("shadow_alloy_shovel",
            () -> new ShadowAlloyShovelItem(ModToolTiers.SHADOW_ALLOY, 3, -3.0f, new Item.Properties()));
    public static final DeferredItem<Item> SHADOW_ALLOY_HOE = ITEMS.register("shadow_alloy_hoe",
            () -> new ShadowAlloyHoeItem(ModToolTiers.SHADOW_ALLOY, -2, 0.0f, new Item.Properties()));
    public static final DeferredItem<Item> SHADOW_ALLOY_CROSSBOW = ITEMS.register("shadow_alloy_crossbow",
            () -> new ShadowAlloyCrossbowItem(new Item.Properties().stacksTo(1).durability(842)));

    public static final DeferredItem<Item> DYEDREAM_SAPLING = ITEMS.register("dyedream_sapling",
            () -> new BlockItem(ModBlocks.DYEDREAM_SAPLING.get(), new Item.Properties()));

    public static final DeferredItem<Item> DYEDREAM_COROLLA_CROP_AGE_0 = ITEMS.register("dyedream_corolla_crop_age_0", () -> new BlockItem(ModBlocks.DYEDREAM_COROLLA_CROP.get(), new Item.Properties()));

    public static final DeferredItem<Item> DYEDREAM_COROLLA_CROP_AGE_1 = ITEMS.register("dyedream_corolla_crop_age_1", () -> new MatureCropItem(new Item.Properties(), ModBlocks.DYEDREAM_COROLLA_CROP.get()));

    public static final DeferredItem<Item> LIGHT_BALL_CROP_AGE_0 = ITEMS.register("light_ball_crop_age_0", () -> new BlockItem(ModBlocks.LIGHT_BALL_CROP.get(), new Item.Properties()));

    public static final DeferredItem<Item> LIGHT_BALL_CROP_AGE_1 = ITEMS.register("light_ball_crop_age_1", () -> new MatureCropItem(new Item.Properties(), ModBlocks.LIGHT_BALL_CROP.get()));

    public static final DeferredItem<Item> CLOUD_CROP_AGE_0 = ITEMS.register("cloud_crop_age_0", () -> new BlockItem(ModBlocks.CLOUD_CROP.get(), new Item.Properties()));

    public static final DeferredItem<Item> CLOUD_CROP_AGE_1 = ITEMS.register("cloud_crop_age_1", () -> new MatureCropItem(new Item.Properties(), ModBlocks.CLOUD_CROP.get()));

    public static final DeferredItem<Item> STEM_GRASS = ITEMS.register("stem_grass",
            () -> new BlockItem(ModBlocks.STEM_GRASS.get(), new Item.Properties()));

    public static final DeferredItem<Item> TALL_STEM_GRASS = ITEMS.register("tall_stem_grass",
            () -> new BlockItem(ModBlocks.TALL_STEM_GRASS.get(), new Item.Properties()));

    public static final DeferredItem<Item> PINK_MUSHROOM = ITEMS.register("pink_mushroom",
            () -> new BlockItem(ModBlocks.PINK_MUSHROOM.get(), new Item.Properties()));

    public static final DeferredItem<Item> TALL_PINK_MUSHROOM = ITEMS.register("tall_pink_mushroom",
            () -> new BlockItem(ModBlocks.TALL_PINK_MUSHROOM.get(), new Item.Properties()));

    public static final DeferredItem<Item> POLISHED_CALCITE_STALICRIPE = ITEMS.register("polished_calcite_stalicripe",
            () -> new BlockItem(ModBlocks.POLISHED_CALCITE_STALICRIPE.get(), new Item.Properties()));

    public static final DeferredItem<Item> SMALL_POLISHED_CALCITE_STALICRIPE = ITEMS.register("small_polished_calcite_stalicripe",
            () -> new BlockItem(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get(), new Item.Properties()));

    public static final DeferredItem<Item> DYEDREAM_MOSS = ITEMS.register("dyedream_moss",
            () -> new BlockItem(ModBlocks.DYEDREAM_MOSS.get(), new Item.Properties()));

    public static final DeferredItem<Item> LINHT_FLOWER = ITEMS.register("linht_flower",
            () -> new BlockItem(ModBlocks.LINHT_FLOWER.get(), new Item.Properties()));

    public static final DeferredItem<Item> DYEDREAM_LILY_OF_THE_VALLEY = ITEMS.register("dyedream_lily_of_the_valley",
            () -> new BlockItem(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get(), new Item.Properties()));

    public static final DeferredItem<Item> SINGULARITY_FERN = ITEMS.register("singularity_fern",
            () -> new BlockItem(ModBlocks.SINGULARITY_FERN.get(), new Item.Properties()));

    public static final DeferredItem<Item> DYEDREAM_VINE = ITEMS.register("dyedream_vine",
            () -> new BlockItem(ModBlocks.DYEDREAM_VINE.get(), new Item.Properties()));

    public static final DeferredItem<Item> DYEDREAM_LILY_PAD = ITEMS.register("dyedream_lily_pad",
            () -> new PlaceOnWaterBlockItem(ModBlocks.DYEDREAM_LILY_PAD.get(), new Item.Properties()));

    public static final DeferredItem<Item> DYEDREAM_SEAGRASS = ITEMS.register("dyedream_seagrass",
            () -> new BlockItem(ModBlocks.DYEDREAM_SEAGRASS.get(), new Item.Properties()));

    public static final DeferredItem<Item> DYEDREAM_LOTUS = ITEMS.register("dyedream_lotus",
            () -> new PlaceOnWaterBlockItem(ModBlocks.DYEDREAM_LOTUS.get(), new Item.Properties()));

    public static final DeferredItem<Item> DREAMING_LOTUS = ITEMS.register("dreaming_lotus",
            () -> new BlockItem(ModBlocks.DREAMING_LOTUS.get(), new Item.Properties()));

    public static final DeferredItem<Item> MISTY_DREAMING_LOTUS = ITEMS.register("misty_dreaming_lotus",
            () -> new BlockItem(ModBlocks.MISTY_DREAMING_LOTUS.get(), new Item.Properties()));

    public static final DeferredItem<Item> EDELWEISS = ITEMS.register("edelweiss",
            () -> new BlockItem(ModBlocks.EDELWEISS.get(), new Item.Properties()));

    public static final DeferredItem<Item> NIPPY_EDELWEISS = ITEMS.register("nippy_edelweiss",
            () -> new BlockItem(ModBlocks.NIPPY_EDELWEISS.get(), new Item.Properties()));

    public static final DeferredItem<Item> WHITE_COROLLA_CROP_AGE_0 = ITEMS.register("white_corolla_crop_age_0", () -> new BlockItem(ModBlocks.WHITE_COROLLA_CROP.get(), new Item.Properties()));

    public static final DeferredItem<Item> WHITE_COROLLA_CROP_AGE_1 = ITEMS.register("white_corolla_crop_age_1", () -> new MatureCropItem(new Item.Properties(), ModBlocks.WHITE_COROLLA_CROP.get()));

    public static final DeferredItem<Item> COTTON_CROP_AGE_0 = ITEMS.register("cotton_crop_age_0", () -> new BlockItem(ModBlocks.COTTON_CROP.get(), new Item.Properties()));

    public static final DeferredItem<Item> COTTON_CROP_AGE_1 = ITEMS.register("cotton_crop_age_1", () -> new MatureCropItem(new Item.Properties(), ModBlocks.COTTON_CROP.get()));

    public static final DeferredItem<Item> FERRARIA_CRISPA = ITEMS.register("ferraria_crispa",//f1
            () -> new BlockItem(ModBlocks.FERRARIA_CRISPA.get(), new Item.Properties()));

    public static final DeferredItem<Item> EUSTOMA = ITEMS.register("eustoma",//f2
            () -> new BlockItem(ModBlocks.EUSTOMA.get(), new Item.Properties()));

    public static final DeferredItem<Item> JUNGLE_SPORANGIUM = ITEMS.register("jungle_sporangium",
            () -> new BlockItem(ModBlocks.JUNGLE_SPORANGIUM.get(), new Item.Properties()));

    public static final DeferredItem<Item> MALVA_SINENSIS_CAVAN = ITEMS.register("malva_sinensis_cavan",//f5
            () -> new BlockItem(ModBlocks.MALVA_SINENSIS_CAVAN.get(), new Item.Properties()));

    public static final DeferredItem<Item> REED = ITEMS.register("reed",
        () -> new BlockItem(ModBlocks.REED.get(), new Item.Properties()));

    public static final DeferredItem<Item> RYE = ITEMS.register("rye",
            () -> new BlockItem(ModBlocks.RYE.get(), new Item.Properties()));

    public static final DeferredItem<Item> OATS = ITEMS.register("oats",
            () -> new BlockItem(ModBlocks.OATS.get(), new Item.Properties()));

    public static final DeferredItem<Item> GOLDENROD = ITEMS.register("goldenrod",
            () -> new BlockItem(ModBlocks.GOLDENROD.get(), new Item.Properties()));

    public static final DeferredItem<Item> FOURLEAF_CLOVER = ITEMS.register("fourleaf_clover",
            () -> new BlockItem(ModBlocks.FOURLEAF_CLOVER.get(), new Item.Properties()));

    public static final DeferredItem<Item> BLAZE_FLOWER = ITEMS.register("blaze_flower",//f6
            () -> new BlockItem(ModBlocks.BLAZE_FLOWER.get(), new Item.Properties()));

    public static final DeferredItem<Item> CRIMSON_THORNS = ITEMS.register("crimson_thorns",
            () -> new BlockItem(ModBlocks.CRIMSON_THORNS.get(), new Item.Properties()));

    public static final DeferredItem<Item> HAIRY_MOSS = ITEMS.register("hairy_moss",
            () -> new BlockItem(ModBlocks.HAIRY_MOSS.get(), new Item.Properties()));

    public static final DeferredItem<Item> WIND_CLEAVING_GRASS = ITEMS.register("wind_cleaving_grass",
            () -> new BlockItem(ModBlocks.WIND_CLEAVING_GRASS.get(), new Item.Properties()));

    public static final DeferredItem<Item> WIND_FEATHER_GRASS = ITEMS.register("wind_feather_grass",
            () -> new BlockItem(ModBlocks.WIND_FEATHER_GRASS.get(), new Item.Properties()));

    public static final DeferredItem<Item> WIND_ISLAND_REED = ITEMS.register("wind_island_reed",
            () -> new BlockItem(ModBlocks.WIND_ISLAND_REED.get(), new Item.Properties()));

    public static final DeferredItem<Item> SHADOW_SHORT_ROOTS = ITEMS.register("shadow_short_roots",
            () -> new BlockItem(ModBlocks.SHADOW_SHORT_ROOTS.get(), new Item.Properties()));

    public static final DeferredItem<Item> SHADOW_ROOTS = ITEMS.register("shadow_roots",
            () -> new BlockItem(ModBlocks.SHADOW_ROOTS.get(), new Item.Properties()));

    public static final DeferredItem<Item> SHADOW_STEM_FERN = ITEMS.register("shadow_stem_fern",
            () -> new BlockItem(ModBlocks.SHADOW_STEM_FERN.get(), new Item.Properties()));

    public static final DeferredItem<Item> SHADOW_SPROUTS = ITEMS.register("shadow_sprouts",
            () -> new BlockItem(ModBlocks.SHADOW_SPROUTS.get(), new Item.Properties()));

    public static final DeferredItem<Item> SHADOW_FERN = ITEMS.register("shadow_fern",
            () -> new BlockItem(ModBlocks.SHADOW_FERN.get(), new Item.Properties()));

    public static final DeferredItem<Item> SHADOW_FUNGUS = ITEMS.register("shadow_fungus",
            () -> new BlockItem(ModBlocks.SHADOW_FUNGUS.get(), new Item.Properties()));

    public static final DeferredItem<Item> WHITE_ORCHID_FLOWER = ITEMS.register("white_orchid_flower",
            () -> new BlockItem(ModBlocks.WHITE_ORCHID_FLOWER.get(), new Item.Properties()));

    public static final DeferredItem<Item> DYEDREAM_COROLLA = ITEMS.register("dyedream_corolla", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> WHITE_COROLLA = ITEMS.register("white_corolla", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> WHITE_CRYSTAL = ITEMS.register("white_crystal",
            () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> SHADOW_HILT = ITEMS.register("shadow_hilt",
            () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON)));

    // ===== 小石子 =====
    public static final DeferredItem<Item> PEBBLE = ITEMS.register("pebble",
            () -> new PebbleItem(ModBlocks.PEBBLE.get(), new Item.Properties()));

    public static final DeferredItem<Item> SNOWBALL_PEBBLE = ITEMS.register("snowball_pebble",
            () -> new SnowballPebbleItem(new Item.Properties()));

    public static final DeferredItem<Item> PURE_HORROR = ITEMS.register("pure_horror",
            () -> new Item(new Item.Properties().stacksTo(64)));

    // 厚翅甲壳
    public static final DeferredItem<Item> BLACK_BEETLE_CARAPACE = ITEMS.register("black_beetle_carapace",
            () -> new Item(new Item.Properties()));

    // 振音声膜
    public static final DeferredItem<Item> BLACK_BEETLE_VOCALCORD = ITEMS.register("black_beetle_vocalcord",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
                    tooltipComponents.add(Component.translatable("tooltip.pasterdream.black_beetle_vocalcord"));
                }
            });

    public static final DeferredItem<Item> LIGHT_BALL = ITEMS.register("light_ball",
            () -> new BlockItem(ModBlocks.LIGHT_BALL.get(), new Item.Properties()));

    public static final DeferredItem<Item> EMPTY_PROPHECY_CARD = ITEMS.register("empty_prophecy_card",
            EmptyProphecyCardItem::new);

    public static final DeferredItem<Item> PROPHECY_CARD = ITEMS.register("prophecy_card",
            ProphecyCardItem::new);



    // ===== 风之旅途·群系方块 =====
    public static final DeferredItem<BlockItem> WHITE_SAND = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_SAND);
    public static final DeferredItem<BlockItem> THICK_CLOUD = ITEMS.registerSimpleBlockItem(ModBlocks.THICK_CLOUD);
    public static final DeferredItem<BlockItem> WHITE_SANDSTONE = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_SANDSTONE);
    public static final DeferredItem<BlockItem> WHITE_SANDSTONE_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_SANDSTONE_STAIRS);
    public static final DeferredItem<BlockItem> WHITE_SANDSTONE_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_SANDSTONE_SLAB);
    public static final DeferredItem<BlockItem> WHITE_SANDSTONE_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_SANDSTONE_WALL);
    public static final DeferredItem<BlockItem> CUT_WHITE_SANDSTONE = ITEMS.registerSimpleBlockItem(ModBlocks.CUT_WHITE_SANDSTONE);
    public static final DeferredItem<BlockItem> CUT_WHITE_SANDSTONE_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.CUT_WHITE_SANDSTONE_STAIRS);
    public static final DeferredItem<BlockItem> CUT_WHITE_SANDSTONE_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.CUT_WHITE_SANDSTONE_SLAB);
    public static final DeferredItem<BlockItem> CUT_WHITE_SANDSTONE_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.CUT_WHITE_SANDSTONE_WALL);
    public static final DeferredItem<BlockItem> CHISELED_WHITE_SANDSTONE = ITEMS.registerSimpleBlockItem(ModBlocks.CHISELED_WHITE_SANDSTONE);
    public static final DeferredItem<BlockItem> SMOOTH_WHITE_SANDSTONE = ITEMS.registerSimpleBlockItem(ModBlocks.SMOOTH_WHITE_SANDSTONE);
    public static final DeferredItem<BlockItem> SMOOTH_WHITE_SANDSTONE_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.SMOOTH_WHITE_SANDSTONE_STAIRS);
    public static final DeferredItem<BlockItem> SMOOTH_WHITE_SANDSTONE_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.SMOOTH_WHITE_SANDSTONE_SLAB);
    public static final DeferredItem<BlockItem> SMOOTH_WHITE_SANDSTONE_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.SMOOTH_WHITE_SANDSTONE_WALL);
    public static final DeferredItem<BlockItem> CLARITY_GLASS = ITEMS.registerSimpleBlockItem(ModBlocks.CLARITY_GLASS);
    public static final DeferredItem<BlockItem> CLARITY_GLASS_PANE = ITEMS.registerSimpleBlockItem(ModBlocks.CLARITY_GLASS_PANE);
    public static final DeferredItem<BlockItem> CARVE_CLARITY_GLASS = ITEMS.registerSimpleBlockItem(ModBlocks.CARVE_CLARITY_GLASS);
    public static final DeferredItem<BlockItem> CARVE_CLARITY_GLASS_PANE = ITEMS.registerSimpleBlockItem(ModBlocks.CARVE_CLARITY_GLASS_PANE);
    public static final DeferredItem<BlockItem> FRAME_CLARITY_GLASS = ITEMS.registerSimpleBlockItem(ModBlocks.FRAME_CLARITY_GLASS);
    public static final DeferredItem<BlockItem> FRAME_CLARITY_GLASS_PANE = ITEMS.registerSimpleBlockItem(ModBlocks.FRAME_CLARITY_GLASS_PANE);
    public static final DeferredItem<BlockItem> BREAK_WIND_CURTAIN = ITEMS.registerSimpleBlockItem(ModBlocks.BREAK_WIND_CURTAIN);
    public static final DeferredItem<BlockItem> CONGEAL_WIND_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.CONGEAL_WIND_ORE);
    public static final DeferredItem<BlockItem> WIND_RUNNER_CRYSTAL_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_RUNNER_CRYSTAL_ORE);
    public static final DeferredItem<BlockItem> CONGEAL_WIND_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.CONGEAL_WIND_BLOCK);
    public static final DeferredItem<BlockItem> WIND_RUNNER_CRYSTAL_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_RUNNER_CRYSTAL_BLOCK);
    public static final DeferredItem<BlockItem> CONGEAL_WIND_IRON_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.CONGEAL_WIND_IRON_BLOCK);
    public static final DeferredItem<BlockItem> FLUFFY_WIND_ALLOY_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.FLUFFY_WIND_ALLOY_BLOCK);
    public static final DeferredItem<BlockItem> EJECTION_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(ModBlocks.EJECTION_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> EJECTION_PRESSURE_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.EJECTION_PRESSURE_BLOCK);
    public static final DeferredItem<BlockItem> CONGEAL_WIND_IRON_BARS = ITEMS.registerSimpleBlockItem(ModBlocks.CONGEAL_WIND_IRON_BARS);
    public static final DeferredItem<BlockItem> CONGEAL_WIND_IRON_LANTERN = ITEMS.registerSimpleBlockItem(ModBlocks.CONGEAL_WIND_IRON_LANTERN);
    public static final DeferredItem<BlockItem> CONGEAL_WIND_IRON_CHAIN = ITEMS.registerSimpleBlockItem(ModBlocks.CONGEAL_WIND_IRON_CHAIN);
    public static final DeferredItem<BlockItem> CONGEAL_WIND_IRON_TRAPDOOR = ITEMS.registerSimpleBlockItem(ModBlocks.CONGEAL_WIND_IRON_TRAPDOOR);
    public static final DeferredItem<BlockItem> CONGEAL_WIND_IRON_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(ModBlocks.CONGEAL_WIND_IRON_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> CYAN_STONE = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_STONE);
    public static final DeferredItem<BlockItem> CYAN_MOSS_STONE = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_MOSS_STONE);
    public static final DeferredItem<BlockItem> CYAN_STONE_BRICKS = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_STONE_BRICKS);
    public static final DeferredItem<BlockItem> CYAN_STONE_BRICK_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_STONE_BRICK_STAIRS);
    public static final DeferredItem<BlockItem> CYAN_STONE_BRICK_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_STONE_BRICK_SLAB);
    public static final DeferredItem<BlockItem> CYAN_STONE_BRICK_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_STONE_BRICK_WALL);
    public static final DeferredItem<BlockItem> CYAN_STONE_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_STONE_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> CYAN_STONE_BUTTON = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_STONE_BUTTON);
    public static final DeferredItem<BlockItem> MOSSY_CYAN_STONE_BRICKS = ITEMS.registerSimpleBlockItem(ModBlocks.MOSSY_CYAN_STONE_BRICKS);
    public static final DeferredItem<BlockItem> MOSSY_CYAN_STONE_BRICK_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.MOSSY_CYAN_STONE_BRICK_STAIRS);
    public static final DeferredItem<BlockItem> MOSSY_CYAN_STONE_BRICK_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.MOSSY_CYAN_STONE_BRICK_SLAB);
    public static final DeferredItem<BlockItem> MOSSY_CYAN_STONE_BRICK_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.MOSSY_CYAN_STONE_BRICK_WALL);
    public static final DeferredItem<BlockItem> CHISELED_CYAN_STONE_BRICKS = ITEMS.registerSimpleBlockItem(ModBlocks.CHISELED_CYAN_STONE_BRICKS);
    public static final DeferredItem<BlockItem> CYAN_STONE_PILLAR = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_STONE_PILLAR);
    public static final DeferredItem<BlockItem> POLISHED_CYAN_STONE = ITEMS.registerSimpleBlockItem(ModBlocks.POLISHED_CYAN_STONE);
    public static final DeferredItem<BlockItem> POLISHED_CYAN_STONE_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.POLISHED_CYAN_STONE_STAIRS);
    public static final DeferredItem<BlockItem> POLISHED_CYAN_STONE_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.POLISHED_CYAN_STONE_SLAB);
    public static final DeferredItem<BlockItem> POLISHED_CYAN_STONE_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.POLISHED_CYAN_STONE_WALL);
    public static final DeferredItem<BlockItem> WIND_MOOR_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_LOG);
    public static final DeferredItem<BlockItem> WIND_MOOR_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_WIND_MOOR_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_WIND_MOOR_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_WIND_MOOR_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_WIND_MOOR_WOOD);
    public static final DeferredItem<BlockItem> WIND_MOOR_LEAVES_0 = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_LEAVES_0);
    public static final DeferredItem<BlockItem> WIND_MOOR_LEAVES_1 = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_LEAVES_1);
    public static final DeferredItem<BlockItem> WIND_MOOR_SAPLING = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_SAPLING);
    public static final DeferredItem<BlockItem> WIND_MOOR_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_PLANKS);
    public static final DeferredItem<BlockItem> WIND_MOOR_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_STAIRS);
    public static final DeferredItem<BlockItem> WIND_MOOR_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_SLAB);
    public static final DeferredItem<BlockItem> WIND_MOOR_FENCE = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_FENCE);
    public static final DeferredItem<BlockItem> WIND_MOOR_FENCE_GATE = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_FENCE_GATE);
    public static final DeferredItem<BlockItem> WIND_MOOR_PANE = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_PANE);
    public static final DeferredItem<BlockItem> WIND_MOOR_DOOR = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_DOOR);
    public static final DeferredItem<BlockItem> WIND_MOOR_TRAPDOOR = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_TRAPDOOR);
    public static final DeferredItem<BlockItem> WIND_MOOR_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> WIND_MOOR_BUTTON = ITEMS.registerSimpleBlockItem(ModBlocks.WIND_MOOR_BUTTON);

    public static final DeferredItem<Item> FIG = ITEMS.register("fig",
            () -> new ItemNameBlockItem(ModBlocks.FIG_VINE.get(), new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.165f).build())));

    // ===== 调试工具 =====
    public static final DeferredItem<Item> DEBUG_SWORD = ITEMS.register("debug_sword", DebugSwordItem::new);
    public static final DeferredItem<Item> LOOT_GENERATOR = ITEMS.register("loot_generator", LootGeneratorItem::new);
    public static final DeferredItem<BlockItem> MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0 = ITEMS.register("model_break_particle_provider_block_0",
            () -> new ModelBreakParticleProviderBlockItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1 = ITEMS.register("model_break_particle_provider_block_1",
            () -> new ModelBreakParticleProviderBlockItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2 = ITEMS.register("model_break_particle_provider_block_2",
            () -> new ModelBreakParticleProviderBlockItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3 = ITEMS.register("model_break_particle_provider_block_3",
            () -> new ModelBreakParticleProviderBlockItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3.get(), new Item.Properties()));

    // ===== 刷怪蛋 =====
    public static final DeferredItem<Item> TERRORBEAK_SPAWN_EGG = ITEMS.register("terrorbeak_spawn_egg",
            () -> new SpawnEggItem(ModEntities.TERRORBEAK.get(), 0x1c1c1c, 0x3d3d3d, new Item.Properties()));
    public static final DeferredItem<Item> CRAZY_TERRORBEAK_SPAWN_EGG = ITEMS.register("crazy_terrorbeak_spawn_egg",
            () -> new SpawnEggItem(ModEntities.CRAZY_TERRORBEAK.get(), -16777216, -11271421, new Item.Properties()));
    public static final DeferredItem<Item> WEAKENESS_TERRORBEAK_SPAWN_EGG = ITEMS.register("weakeness_terrorbeak_spawn_egg",
            () -> new SpawnEggItem(ModEntities.WEAKENESS_TERRORBEAK.get(), -16777216, -13421773, new Item.Properties()));
    public static final DeferredItem<Item> SHADOW_HAND_SPAWN_EGG = ITEMS.register("shadow_hand_spawn_egg",
            () -> new SpawnEggItem(ModEntities.SHADOW_HAND.get(), -12763843, -16316665, new Item.Properties()));
    public static final DeferredItem<Item> FIREFLY_SPAWN_EGG = ITEMS.register("firefly_spawn_egg",
            () -> new SpawnEggItem(ModEntities.FIREFLY.get(), -12570340, -5382814, new Item.Properties()));
    public static final DeferredItem<Item> SMALL_STONE_SPIRIT_SPAWN_EGG = ITEMS.register("small_stone_spirit_spawn_egg",
            () -> new SpawnEggItem(ModEntities.SMALL_STONE_SPIRIT.get(), -3937560, -9340294, new Item.Properties()));

    // ===== 拓展栏物品 =====
    public static final DeferredItem<Item> MELT_DREAM_AURORIAN_STEEL_INGOT = ITEMS.register("melt_dream_aurorian_steel_ingot",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> THERMAL_DAGGER = ITEMS.register("thermal_dagger",
            () -> new ThermalDaggerItem(new Item.Properties()));
    public static final DeferredItem<Item> TERRA_BLADE = ITEMS.register("terra_blade",
            () -> new TerraBladeItem(ModToolTiers.TERRA_SWORD, 7, -2.4f));
    public static final DeferredItem<Item> DUKE_COIN_CURIO = ITEMS.register("duke_coin_curio",
            DukeCoinCurioItem::new);
    public static final DeferredItem<Item> HIYORI_BUTTERFLY_HAIRPIN = ITEMS.register("hiyori_butterfly_hairpin",
            HiyoriButterflyHairpinItem::new);
    public static final DeferredItem<Item> ALLKINDS_RING = ITEMS.register("allkinds_ring",
            AllkindsRingItem::new);
    public static final DeferredItem<Item> KAICHU_OMAMORI = ITEMS.register("kaichu_omamori",
            KaichuOmamoriItem::new);
    public static final DeferredItem<Item> TERRA_FLOATING_ISLAND = ITEMS.register("terra_floating_island",
            TerraFloatingIslandItem::new);
    public static final DeferredItem<Item> BLESSING_OF_CECILIA = ITEMS.register("blessing_of_cecilia",
            BlessingOfCeciliaItem::new);
    public static final DeferredItem<Item> FADED_BLESSING_OF_CECILIA = ITEMS.register("faded_blessing_of_cecilia",
            FadedBlessingOfCeciliaItem::new);
    public static final DeferredItem<Item> STRAWBERRY_HEART = ITEMS.register("strawberry_heart",
            StrawberryHeartItem::new);
    public static final DeferredItem<Item> ANGEL_WING = ITEMS.register("angel_wing",
            AngelWingItem::new);
    public static final DeferredItem<Item> FORSAKENS_WING = ITEMS.register("forsakens_wing",
            ForsakensWingItem::new);
    public static final DeferredItem<Item> MACHINE_LIGHT_WING = ITEMS.register("machine_light_wing",
            MachineLightWingItem::new);
    public static final DeferredItem<Item> TURNBACK_CLOAK = ITEMS.register("turnback_cloak",
            TurnbackCloakItem::new);
    public static final DeferredItem<Item> EVASION_CLOAK = ITEMS.register("evasion_cloak",
            EvasionCloakItem::new);
    public static final DeferredItem<Item> SLIVER_BELL = ITEMS.register("silver_bell",
            () -> new Item(new Item.Properties().rarity(Rarity.COMMON)) {
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, java.util.List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, context, tooltip, flag);
                    tooltip.add(Component.translatable("tooltip.pasterdream.sliver_bell"));
                }
            });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private ModItems() {
    }
}
