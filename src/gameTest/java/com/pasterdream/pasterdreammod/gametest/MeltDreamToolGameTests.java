package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergy;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class MeltDreamToolGameTests {
    private static final List<Item> TOOLS = List.of(ModItems.MELT_DREAM_PICKAXE.get(), ModItems.MELT_DREAM_AXE.get(),
            ModItems.MELT_DREAM_SHOVEL.get(), ModItems.MELT_DREAM_HOE.get(), ModItems.SHARP_MELT_DREAM_SWORD.get());
    private static final List<Item> BASES = List.of(ModItems.DYEDREAM_PICKAXE.get(), ModItems.DYEDREAM_AXE.get(),
            ModItems.DYEDREAM_SHOVEL.get(), ModItems.DYEDREAM_HOE.get(), ModItems.DYEDREAM_SWORD.get());

    @GameTest(template = "empty")
    public static void toolPropertiesAndSmithing(GameTestHelper helper) {
        double[] damage = {5.5, 10.0, 6.0, 1.5, 8.5};
        double[] speed = {-2.8, -3.0, -3.0, 0.5, -2.4};
        var tags = List.of(ItemTags.PICKAXES, ItemTags.AXES, ItemTags.SHOVELS, ItemTags.HOES, ItemTags.SWORDS);
        String[] ids = {"melt_dream_pickaxe_smithing", "melt_dream_axe_smithing", "melt_dream_shovel_smithing",
                "melt_dream_hoe_smithing", "dyedream_sharp_sword_smithing"};
        for (int i = 0; i < TOOLS.size(); i++) {
            var stack = new ItemStack(TOOLS.get(i));
            helper.assertTrue(stack.getMaxDamage() == 1728 && stack.getMaxStackSize() == 1 && stack.is(tags.get(i)), "融梦工具耐久或标签错误");
            helper.assertTrue(stack.has(DataComponents.FIRE_RESISTANT) && TOOLS.get(i).getEnchantmentValue() == 22, "工具防火或附魔值错误");
            helper.assertTrue(TOOLS.get(i).isValidRepairItem(stack, ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.toStack()), "碎片应修复工具");
            var mods = stack.get(DataComponents.ATTRIBUTE_MODIFIERS).modifiers();
            double d = mods.stream().filter(x -> x.attribute().equals(Attributes.ATTACK_DAMAGE)).mapToDouble(x -> x.modifier().amount()).sum();
            double s = mods.stream().filter(x -> x.attribute().equals(Attributes.ATTACK_SPEED)).mapToDouble(x -> x.modifier().amount()).sum();
            helper.assertTrue(Math.abs(d - damage[i]) < 0.0001 && Math.abs(s - speed[i]) < 0.0001, "融梦工具来源攻击数值错误 " + i + " " + d + " " + s);

            var base = new ItemStack(BASES.get(i));
            base.setDamageValue(50);
            base.set(DataComponents.CUSTOM_NAME, Component.literal("kept"));
            var input = new SmithingRecipeInput(i == 4 ? ModItems.DYEDREAM_DUST.toStack() : ModItems.DYEDREAM_UPGRADE.toStack(),
                    base, ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.toStack());
            var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMITHING, input, helper.getLevel()).orElseThrow();
            helper.assertTrue(recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, ids[i])), "锻造配方 ID 错误");
            var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
            helper.assertTrue(result.is(TOOLS.get(i)) && result.getDamageValue() == 50
                    && Component.literal("kept").equals(result.get(DataComponents.CUSTOM_NAME)), "锻造应保留损伤和名称");
        }
        helper.assertTrue(ModItems.MELT_DREAM_PICKAXE.toStack().isCorrectToolForDrops(Blocks.OBSIDIAN.defaultBlockState()), "融梦镐应能采集黑曜石");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void energyPersistenceAndRepair(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        helper.assertTrue(energy.getMeltDreamEnergy() == 0 && energy.getMaxMeltDreamEnergy() == 100
                && energy.getIsOrNotNeedConsumeDreamEnergy() && !energy.isConsumeDoubled(), "融梦能量默认字段错误");
        energy.setMeltDreamEnergy(1);
        var stack = ModItems.MELT_DREAM_PICKAXE.toStack();
        stack.setDamageValue(2);
        player.tickCount = 10;
        stack.getItem().inventoryTick(stack, helper.getLevel(), player, 0, true);
        helper.assertTrue(stack.getDamageValue() == 1 && Math.abs(energy.getMeltDreamEnergy() - 0.99) < 0.0001, "融梦修补应消耗 0.01 能量修复 1 耐久");
        energy.setIsConsumeDoubled(true);
        player.tickCount = 20;
        stack.getItem().inventoryTick(stack, helper.getLevel(), player, 0, true);
        helper.assertTrue(stack.getDamageValue() == 0 && Math.abs(energy.getMeltDreamEnergy() - 0.97) < 0.0001, "消耗翻倍应扣 0.02");
        var saved = energy.serializeNBT(helper.getLevel().registryAccess());
        var restored = new MeltDreamEnergy();
        restored.deserializeNBT(helper.getLevel().registryAccess(), saved);
        helper.assertTrue(restored.getMeltDreamEnergy() == energy.getMeltDreamEnergy() && restored.isConsumeDoubled(), "能量 NBT 往返错误");
        energy.setIsOrNotNeedConsumeDreamEnergy(false);
        stack.setDamageValue(1);
        player.tickCount = 30;
        stack.getItem().inventoryTick(stack, helper.getLevel(), player, 0, true);
        helper.assertTrue(stack.getDamageValue() == 0 && Math.abs(energy.getMeltDreamEnergy() - 0.97) < 0.0001, "免消耗仍应修复且不扣能量");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void sharpSwordChargedHit(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var sword = ModItems.SHARP_MELT_DREAM_SWORD.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, sword);
        var result = sword.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(result.getObject() == sword && player.getCooldowns().isOnCooldown(sword.getItem()), "右键应蓄势并施加共享冷却");
        player.getCooldowns().removeCooldown(sword.getItem());
        helper.assertTrue(sword.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND).getResult() == InteractionResult.FAIL,
                "冷却提前结束但尚未命中时不得重新蓄势");
        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        float health = zombie.getHealth();
        sword.getItem().hurtEnemy(sword, zombie, player);
        helper.assertTrue(zombie.getHealth() < health && zombie.getDeltaMovement().y > 0, "凌空斩应额外伤害并击飞");
        helper.succeed();
    }
}
