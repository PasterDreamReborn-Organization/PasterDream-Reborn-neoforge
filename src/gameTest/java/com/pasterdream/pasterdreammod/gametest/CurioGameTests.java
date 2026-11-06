package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class CurioGameTests {
    @GameTest(template = "empty")
    public static void curioSlotsAndRecipes(GameTestHelper helper) {
        for (Item item : List.of(ModItems.GOLD_CHARM.get(), ModItems.ENDEYE_CHARM.get(),
                ModItems.SEA_CHARM.get(), ModItems.CARAPAX_CHARM.get(), ModItems.WORLDTREE_SEEDPOD.get())) {
            helper.assertTrue(new ItemStack(item).is(curiosTag("charm")), "护身符必须能装入 charm 槽: " + item);
        }
        for (Item item : List.of(ModItems.GARLAND.get(), ModItems.SNOW_VOW_HEAD.get())) {
            helper.assertTrue(new ItemStack(item).is(curiosTag("head")), "头饰必须能装入 head 槽: " + item);
        }

        checkCraft(helper, CraftingInput.of(2, 1, List.of(ModItems.EMBRYO_CHARM.toStack(),
                new ItemStack(Items.GOLD_INGOT))), ModItems.GOLD_CHARM.get());
        checkCraft(helper, CraftingInput.of(3, 1, List.of(ModItems.EMBRYO_CHARM.toStack(),
                new ItemStack(Items.ENDER_EYE), new ItemStack(Items.CHORUS_FLOWER))), ModItems.ENDEYE_CHARM.get());
        checkCraft(helper, CraftingInput.of(3, 3, List.of(
                new ItemStack(Items.SEAGRASS), new ItemStack(Items.TURTLE_SCUTE), new ItemStack(Items.SEAGRASS),
                new ItemStack(Items.TURTLE_SCUTE), ModItems.EMBRYO_CHARM.toStack(), new ItemStack(Items.TURTLE_SCUTE),
                new ItemStack(Items.SEAGRASS), new ItemStack(Items.TURTLE_SCUTE), new ItemStack(Items.SEAGRASS))),
                ModItems.CARAPAX_CHARM.get());
        checkCraft(helper, CraftingInput.of(3, 3, List.of(
                new ItemStack(Items.POPPY), new ItemStack(Items.DANDELION), new ItemStack(Items.BLUE_ORCHID),
                new ItemStack(Items.ALLIUM), ItemStack.EMPTY, new ItemStack(Items.AZURE_BLUET),
                new ItemStack(Items.RED_TULIP), new ItemStack(Items.ORANGE_TULIP), new ItemStack(Items.WHITE_TULIP))),
                ModItems.GARLAND.get());
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void snowVowClearsFireAndFreezing(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        double initialLuck = player.getAttributeValue(Attributes.LUCK);
        player.addEffect(new MobEffectInstance(ModEffects.SNOW_VOW, 40));
        helper.assertTrue(player.getAttributeValue(Attributes.LUCK) == initialLuck + 3.0,
                "雪下的誓言应增加 3 点幸运值");
        player.setRemainingFireTicks(100);
        player.setTicksFrozen(100);
        ModEffects.SNOW_VOW.get().applyEffectTick(player, 0);
        helper.assertTrue(player.getRemainingFireTicks() <= 0 && player.getTicksFrozen() == 0,
                "雪下的誓言应清除燃烧与冻结");
        player.removeEffect(ModEffects.SNOW_VOW);
        helper.assertTrue(player.getAttributeValue(Attributes.LUCK) == initialLuck,
                "移除效果后幸运值应恢复");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void attributeCuriosExposeModifiers(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        var slots = CuriosApi.getEntitySlots(player);
        helper.assertTrue(slots.keySet().containsAll(List.of("head", "necklace", "back", "body",
                        "ring", "belt", "charm", "curio")) && slots.get("ring").getSize() == 2,
                "玩家应获得来源的八类槽位，戒指槽应有两个位置");
        for (Item item : List.of(ModItems.SEA_CHARM.get(), ModItems.CARAPAX_CHARM.get())) {
            ItemStack stack = new ItemStack(item);
            helper.assertTrue(CuriosApi.getItemStackSlots(stack, player).containsKey("charm"),
                    "属性饰品应识别 charm 槽: " + item);
            var slot = new SlotContext("charm", player, 0, false, true);
            var modifiers = CuriosApi.getAttributeModifiers(slot, CuriosApi.getSlotId(slot), stack);
            helper.assertTrue(modifiers.size() == (item == ModItems.SEA_CHARM.get() ? 2 : 3),
                    "Curios 应读取全部属性修饰符: " + item);
        }
        helper.succeed();
    }

    private static TagKey<Item> curiosTag(String slot) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", slot));
    }

    private static void checkCraft(GameTestHelper helper, CraftingInput input, Item expected) {
        var recipe = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
        helper.assertTrue(recipe.value().assemble(input, helper.getLevel().registryAccess()).is(expected),
                "饰品配方应产出 " + expected);
    }
}
