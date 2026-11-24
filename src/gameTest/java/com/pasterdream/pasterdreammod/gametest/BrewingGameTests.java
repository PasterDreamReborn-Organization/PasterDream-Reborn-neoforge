package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModPotions;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.VanillaBrewingAdapter;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class BrewingGameTests {
    @GameTest(template = "empty", batch = "brewing")
    public static void fortuneJellyBrewsAllLuckPotions(GameTestHelper helper) {
        var brewing = helper.getLevel().potionBrewing();
        var jelly = ModItems.FORTUNE_JELLY.toStack();
        var redstone = new ItemStack(Items.REDSTONE);
        var glowstone = new ItemStack(Items.GLOWSTONE_DUST);
        for (var bottle : List.of(Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION)) {
            var awkward = PotionContents.createItemStack(bottle, Potions.AWKWARD);
            var water = PotionContents.createItemStack(bottle, Potions.WATER);
            helper.assertTrue(!brewing.hasMix(water, jelly), "福灵果冻只使用粗制药水作基底");
            helper.assertTrue(brewing.hasMix(awkward, jelly), "粗制药水应能和福灵果冻一起酿造");
            var luck = brewing.mix(jelly, awkward);
            assertPotion(helper, luck, bottle, ModPotions.LUCK.getDelegate(), 3600, 0);
            helper.assertTrue(brewing.hasMix(luck, redstone) && brewing.hasMix(luck, glowstone),
                    "模组幸运药水应同时支持延长和强化");
            var longer = brewing.mix(redstone, luck);
            var stronger = brewing.mix(glowstone, luck);
            assertPotion(helper, longer, bottle, ModPotions.LONG_LUCK.getDelegate(), 9600, 0);
            assertPotion(helper, stronger, bottle, ModPotions.STRONG_LUCK.getDelegate(), 1800, 1);
            helper.assertTrue(!brewing.hasMix(longer, glowstone) && !brewing.hasMix(stronger, redstone),
                    "延长与强化不可叠加");
            helper.assertTrue(!brewing.hasMix(PotionContents.createItemStack(bottle, Potions.LUCK), redstone),
                    "原版幸运药水维持原有酿造规则");
        }

        var cauldron = VanillaBrewingAdapter.brew(helper.getLevel(), jelly, VanillaBrewingAdapter.Enhance.REDSTONE);
        helper.assertTrue(cauldron.status() == VanillaBrewingAdapter.BrewStatus.OK
                        && cauldron.potion().is(ModPotions.LONG_LUCK.getKey())
                        && cauldron.effects().getFirst().getDuration() == 9600,
                "梦之釜应继承酿造台的幸运药水延长配方");
        helper.succeed();
    }

    private static void assertPotion(GameTestHelper helper, ItemStack stack, Item bottle,
                                     Holder<Potion> potion, int duration, int amplifier) {
        var contents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        helper.assertTrue(stack.is(bottle) && contents.is(potion), "酿造应保留瓶型并产出预期的模组药水");
        var effect = potion.value().getEffects().getFirst();
        helper.assertTrue(effect.getEffect().is(MobEffects.LUCK) && effect.getDuration() == duration
                        && effect.getAmplifier() == amplifier,
                "幸运效果的时长或等级与来源不同");
    }
}
