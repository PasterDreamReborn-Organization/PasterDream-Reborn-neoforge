package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.lifecrystal.LifeCrystalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class LifeCrystalGameTests {
    @GameTest(template = "empty", timeoutTicks = 100)
    public static void blockInteractionAndAbsorption(GameTestHelper helper) {
        BlockPos first = new BlockPos(1, 1, 1);
        BlockPos second = new BlockPos(2, 1, 1);
        helper.setBlock(first, ModBlocks.LIFE_CRYSTAL.get());
        helper.setBlock(second, ModBlocks.LIFE_CRYSTAL.get());
        var state = helper.getBlockState(first);
        helper.assertTrue(state.is(BlockTags.MINEABLE_WITH_PICKAXE) && state.getLightEmission() == 12,
                "生命水晶应可用镐挖掘且亮度为 12");
        helper.assertTrue(ModItems.LIFE_CRYSTAL.get() instanceof net.minecraft.world.item.BlockItem,
                "生命水晶物品应可放置");
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "life-crystal-test"));
        player.setPos(helper.absolutePos(first).getCenter());
        helper.getLevel().addNewPlayer(player);
        helper.useBlock(first, player);
        LifeCrystalBlockEntity entity = helper.getBlockEntity(first);
        helper.assertTrue(entity.getAnimationState() == 1 && player.getUUID().equals(entity.getAbsorbingPlayer()),
                "右键应启动吸收动画并记录玩家");
        helper.useBlock(second, player);
        LifeCrystalBlockEntity other = helper.getBlockEntity(second);
        helper.assertTrue(other.getAbsorbingPlayer() == null, "同一玩家不能同时吸收第二颗生命水晶");
        helper.runAfterDelay(42, () -> {
            helper.assertTrue(helper.getLevel().getBlockState(helper.absolutePos(first)).isAir(),
                    "吸收 40 tick 后生命水晶应消失");
            helper.assertTrue(player.getAttributeValue(Attributes.MAX_HEALTH) == 22.0,
                    "生命水晶应永久增加 2 点生命上限");
            helper.useBlock(second, player);
            helper.assertTrue(other.getAbsorbingPlayer() == null, "已吸收过的玩家不能再次吸收");
            helper.getLevel().removePlayerImmediately(player, Entity.RemovalReason.DISCARDED);
            helper.succeed();
        });
    }

    @GameTest(template = "empty")
    public static void meltArmorSmithingRecipes(GameTestHelper helper) {
        var bases = List.of(ModItems.DYEDREAM_HELMET.get(), ModItems.DYEDREAM_CHESTPLATE.get(),
                ModItems.DYEDREAM_LEGGINGS.get(), ModItems.DYEDREAM_BOOTS.get());
        var results = List.of(ModItems.MELT_DREAM_HELMET.get(), ModItems.MELT_DREAM_CHESTPLATE.get(),
                ModItems.MELT_DREAM_LEGGINGS.get(), ModItems.MELT_DREAM_BOOTS.get());
        String[] ids = {"melt_dream_helmet_smithing", "melt_dream_chestplate_smithing",
                "melt_dream_leggings_smithing", "melt_dream_boots_smithing"};
        for (int i = 0; i < bases.size(); i++) {
            var base = new ItemStack(bases.get(i));
            base.setDamageValue(20);
            var input = new SmithingRecipeInput(ModItems.LIFE_CRYSTAL.toStack(),
                    base, ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.toStack());
            var recipe = helper.getLevel().getRecipeManager()
                    .getRecipeFor(RecipeType.SMITHING, input, helper.getLevel()).orElseThrow();
            helper.assertTrue(recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, ids[i])),
                    "融梦护甲锻造配方 ID 错误");
            var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
            helper.assertTrue(result.is(results.get(i)) && result.getDamageValue() == 20,
                    "锻造应保留护甲损伤");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void blockEntityPersistence(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.LIFE_CRYSTAL.get());
        LifeCrystalBlockEntity original = helper.getBlockEntity(pos);
        var drops = Block.getDrops(helper.getBlockState(pos), helper.getLevel(), helper.absolutePos(pos),
                original, null, new ItemStack(Items.IRON_PICKAXE));
        helper.assertTrue(drops.size() == 1 && drops.getFirst().is(ModItems.LIFE_CRYSTAL.get()),
                "挖掘生命水晶应掉落自身");
        UUID player = UUID.randomUUID();
        original.setAnimationState(1);
        original.setAbsorbingPlayer(player);
        var saved = original.saveWithFullMetadata(helper.getLevel().registryAccess());
        var restored = new LifeCrystalBlockEntity(helper.absolutePos(pos), helper.getBlockState(pos));
        restored.loadWithComponents(saved, helper.getLevel().registryAccess());
        helper.assertTrue(restored.getAnimationState() == 1 && player.equals(restored.getAbsorbingPlayer()),
                "生命水晶吸收玩家和动画状态应在存档后恢复");
        helper.succeed();
    }
}
