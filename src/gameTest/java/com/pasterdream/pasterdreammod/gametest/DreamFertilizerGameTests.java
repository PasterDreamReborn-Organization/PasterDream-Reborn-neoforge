package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DreamFertilizerGameTests {
    @GameTest(template = "empty")
    public static void recipeProducesDreamFertilizer(GameTestHelper helper) {
        var manager = helper.getLevel().getRecipeManager();
        var input = CraftingInput.of(3, 1, List.of(
                new ItemStack(ModItems.PINK_SLIMEBALL.get()),
                new ItemStack(Items.BONE_BLOCK),
                ModItems.MORTAR.toStack()));
        var recipe = manager.getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
        helper.assertTrue(recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "dream_fertilizer_from_pink_slimeball_and_bone_block")), "衍梦肥泥配方 ID 与来源不一致");
        ItemStack result = recipe.value().assemble(input, helper.getLevel().registryAccess());
        helper.assertTrue(result.is(ModItems.DREAM_FERTILIZER.get()) && result.getCount() == 9,
                "粉红粘液球与骨块应产出 9 个衍梦肥泥");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void growsStemGrassIntoTallStemGrass(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack fertilizer = ModItems.DREAM_FERTILIZER.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, fertilizer);
        helper.setBlock(new BlockPos(1, 0, 1), Blocks.DIRT);
        helper.setBlock(new BlockPos(1, 1, 1), ModBlocks.STEM_GRASS.get());
        BlockPos abs = helper.absolutePos(new BlockPos(1, 1, 1));
        var context = new UseOnContext(player, InteractionHand.MAIN_HAND,
                new BlockHitResult(Vec3.atCenterOf(abs), Direction.UP, abs, false));
        ModItems.DREAM_FERTILIZER.get().useOn(context);
        helper.assertBlockPresent(ModBlocks.TALL_STEM_GRASS.get(), new BlockPos(1, 1, 1));
        helper.assertBlockProperty(new BlockPos(1, 1, 1), DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
        helper.assertBlockPresent(ModBlocks.TALL_STEM_GRASS.get(), new BlockPos(1, 2, 1));
        helper.assertBlockProperty(new BlockPos(1, 2, 1), DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
        helper.assertTrue(fertilizer.isEmpty(), "生存模式使用衍梦肥泥应消耗一个");
        helper.succeed();
    }
}
