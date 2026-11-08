package com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.CauldronPotionFluidTags;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.CauldronPotionModule;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.CauldronPotionTags;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.PotionEffectCodec;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.VanillaBrewingAdapter;
import com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle.ElixirBottleWithFluidNBTBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/** JEI templates for the source brewing, fusion and blending operations. */
public final class CauldronBrewRecipeCatalog
{
    private CauldronBrewRecipeCatalog() {}

    public static List<DreamCauldronJEIRecipe> createAll(Level level)
    {
        if (!Config.DREAM_CAULDRON_POTION_ENABLED.get()) return List.of();
        List<DreamCauldronJEIRecipe> recipes = new ArrayList<>();
        for (Item item : BuiltInRegistries.ITEM)
        {
            ItemStack catalyst = new ItemStack(item);
            if (catalyst.isEmpty()) continue;
            var brew = VanillaBrewingAdapter.brew(level, catalyst, VanillaBrewingAdapter.Enhance.NONE);
            if (brew.status() != VanillaBrewingAdapter.BrewStatus.OK) continue;
            recipes.add(build(List.of(), List.of(
                    ItemIngredient.of(CauldronPotionTags.BASE, 1),
                    ItemIngredient.of(item, 1),
                    ItemIngredient.of(CauldronPotionTags.ENHANCE, 1)),
                    CauldronPotionModule.DRINK_MB, brew.effects()));
        }

        var speed = VanillaBrewingAdapter.brew(level, new ItemStack(Items.SUGAR), VanillaBrewingAdapter.Enhance.NONE);
        var strength = VanillaBrewingAdapter.brew(level, new ItemStack(Items.BLAZE_POWDER), VanillaBrewingAdapter.Enhance.NONE);
        if (speed.status() == VanillaBrewingAdapter.BrewStatus.OK
                && strength.status() == VanillaBrewingAdapter.BrewStatus.OK)
        {
            recipes.add(build(List.of(
                    FluidIngredient.of(CauldronPotionFluidTags.SPRING, CauldronPotionModule.SPRING_MB),
                    FluidIngredient.of(CauldronPotionFluidTags.POTION, CauldronPotionModule.DRINK_MB,
                            legacyEffects(speed.effects()))),
                    List.of(ItemIngredient.of(CauldronPotionTags.BASE, 1),
                            ItemIngredient.of(CauldronPotionTags.CATALYST, 1),
                            ItemIngredient.of(CauldronPotionTags.ENHANCE, 1)),
                    CauldronPotionModule.DRINK_MB + CauldronPotionModule.FUSE_ADD_MB,
                    merge(speed.effects(), strength.effects())));
            recipes.add(build(List.of(
                    FluidIngredient.of(CauldronPotionFluidTags.SPRING, CauldronPotionModule.SPRING_MB),
                    FluidIngredient.of(CauldronPotionFluidTags.POTION, CauldronPotionModule.DRINK_MB,
                            legacyEffects(speed.effects()))),
                    List.of(), CauldronPotionModule.DRINK_MB + CauldronPotionModule.SPRING_MB, speed.effects()));
        }
        return recipes;
    }

    private static DreamCauldronJEIRecipe build(List<FluidIngredient> fluidInputs,
                                                 List<ItemIngredient> itemInputs, int amount,
                                                 List<MobEffectInstance> effects)
    {
        return new DreamCauldronJEIRecipe(fluidInputs, itemInputs,
                List.of(FluidIngredient.of(ModFluids.POTION.get(), amount, legacyEffects(effects))),
                ElixirBottleWithFluidNBTBuilder.builder(PotionEffectCodec.createPotionFluid(1000, effects)));
    }

    private static CompoundTag legacyEffects(List<MobEffectInstance> effects)
    {
        CompoundTag tag = new CompoundTag();
        ListTag entries = new ListTag();
        for (MobEffectInstance effect : effects)
        {
            CompoundTag entry = new CompoundTag();
            entry.putString("id", BuiltInRegistries.MOB_EFFECT.getKey(effect.getEffect().value()).toString());
            entry.putInt("lvl", effect.getAmplifier());
            entry.putInt("time", effect.getDuration());
            entries.add(entry);
        }
        tag.put("EffectList", entries);
        return tag;
    }

    private static List<MobEffectInstance> merge(List<MobEffectInstance> existing, List<MobEffectInstance> added)
    {
        List<MobEffectInstance> out = new ArrayList<>(existing);
        for (MobEffectInstance add : added)
        {
            boolean merged = false;
            for (int i = 0; i < out.size(); i++)
            {
                if (out.get(i).getEffect() == add.getEffect())
                {
                    if (add.getAmplifier() > out.get(i).getAmplifier()) out.set(i, add);
                    merged = true;
                    break;
                }
            }
            if (!merged) out.add(add);
        }
        return out;
    }
}
