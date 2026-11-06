package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class ShadowToolGameTests {
    @GameTest(template = "empty")
    public static void toolPropertiesAndRepair(GameTestHelper helper) {
        ItemStack erosion = ModItems.SHADOW_EROSION_PICKAXE.toStack();
        ItemStack alloy = ModItems.SHADOW_ALLOY_PICKAXE.toStack();
        helper.assertTrue(erosion.getMaxDamage() == 1725 && alloy.getMaxDamage() == 1850,
                "影蚀/暗影合金耐久错误");
        helper.assertTrue(erosion.is(ItemTags.PICKAXES) && alloy.is(ItemTags.PICKAXES),
                "工具标签缺失");
        for (ItemStack stack : List.of(erosion, alloy)) {
            var modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS).modifiers();
            helper.assertTrue(modifiers.stream().anyMatch(entry -> entry.attribute().equals(ModAttributes.SAN_VARIABILITY)
                            && entry.modifier().amount() == -1.2), "SAN 波动修正缺失");
            helper.assertTrue(modifiers.stream().anyMatch(entry -> entry.attribute().equals(Attributes.BLOCK_INTERACTION_RANGE)
                            && entry.modifier().amount() == 1.0), "方块触及修正缺失");
        }

        FakePlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "shadow-tool-test"));
        var san = player.getData(ModAttachments.SAN);
        san.setSanValue(10.0);
        alloy.setDamageValue(5);
        alloy.getItem().inventoryTick(alloy, helper.getLevel(), player, 0, false);
        helper.assertTrue(alloy.getDamageValue() == 4 && Math.abs(san.getSanValue() - 9.9) < 0.0001,
                "噬心修补应消耗 0.1 SAN 并修复 1 耐久");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void blackMetalConversionAndAlloyUpgrade(GameTestHelper helper) {
        var nuggetInput = CraftingInput.of(2, 2, Arrays.asList(
                ModItems.BLACK_METAL_NUGGET.toStack(), ModItems.BLACK_METAL_NUGGET.toStack(),
                ModItems.BLACK_METAL_NUGGET.toStack(), ModItems.BLACK_METAL_NUGGET.toStack()));
        var nuggetRecipe = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, nuggetInput, helper.getLevel()).orElseThrow();
        helper.assertTrue(nuggetRecipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "black_metal_ingot_from_grain"))
                        && nuggetRecipe.value().assemble(nuggetInput, helper.getLevel().registryAccess())
                        .is(ModItems.BLACK_METAL_INGOT.get()), "黑金属粒合锭配方错误");

        ItemStack oldTool = ModItems.SHADOW_EROSION_PICKAXE.toStack();
        oldTool.setDamageValue(20);
        oldTool.set(DataComponents.CUSTOM_NAME, net.minecraft.network.chat.Component.literal("kept"));
        var grid = CraftingInput.of(3, 3, List.of(
                ModItems.NIGHTMARE_FUEL.toStack(), ModItems.SHADOW_ALLOY_INGOT.toStack(), ModItems.NIGHTMARE_FUEL.toStack(),
                ModItems.SHADOW_ALLOY_INGOT.toStack(), oldTool, ModItems.SHADOW_ALLOY_INGOT.toStack(),
                ModItems.NIGHTMARE_FUEL.toStack(), ModItems.SHADOW_ALLOY_INGOT.toStack(), ModItems.NIGHTMARE_FUEL.toStack()));
        var recipe = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, grid, helper.getLevel()).orElseThrow();
        ItemStack result = recipe.value().assemble(grid, helper.getLevel().registryAccess());
        helper.assertTrue(recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "shadow_alloy_pickaxe_upgrade")) && result.is(ModItems.SHADOW_ALLOY_PICKAXE.get())
                        && result.getDamageValue() == 0 && "kept".equals(result.getHoverName().getString()),
                "暗影合金镐升级应保留名称并重置耐久");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void erosionWorkshopRecipe(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get());
        WeaponWorkshopCraftingTableBlockEntity blockEntity = helper.getBlockEntity(pos);
        blockEntity.getItemHandler().setStackInSlot(0, new ItemStack(Items.IRON_PICKAXE));
        blockEntity.getItemHandler().setStackInSlot(1, new ItemStack(ModItems.BLACK_METAL_INGOT.get(), 2));
        blockEntity.getItemHandler().setStackInSlot(2, ModItems.NIGHTMARE_FUEL.toStack());
        blockEntity.getItemHandler().setStackInSlot(3, new ItemStack(Items.CRYING_OBSIDIAN, 3));
        blockEntity.getItemHandler().setStackInSlot(4, ModItems.BLACK_STICK.toStack());
        blockEntity.getItemHandler().setStackInSlot(5, ModItems.ATTACK_ENHANCE_STONE.toStack());
        blockEntity.craft(null);
        helper.assertTrue(blockEntity.getItemHandler().getStackInSlot(6).is(ModItems.SHADOW_EROSION_PICKAXE.get()),
                "影蚀镐精铸工坊配方错误");
        helper.succeed();
    }
}
