package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class RaidRewardsGameTests {
    @GameTest(template = "empty", batch = "raidRewards")
    public static void clericAddsFortuneJellyToRaidGifts(GameTestHelper helper) {
        var level = helper.getLevel();
        var villager = helper.spawn(EntityType.VILLAGER, 1, 1, 1);
        villager.setVillagerData(villager.getVillagerData().setProfession(VillagerProfession.CLERIC));
        var profession = BuiltInRegistries.VILLAGER_PROFESSION.wrapAsHolder(villager.getVillagerData().getProfession());
        var gift = profession.getData(NeoForgeDataMaps.RAID_HERO_GIFTS);
        helper.assertTrue(gift != null && gift.lootTable().equals(BuiltInLootTables.CLERIC_GIFT),
                "牧师应使用原版袭击胜利赠礼表");

        var tables = level.getServer().reloadableRegistries();
        var cleric = tables.getLootTable(gift.lootTable());
        var farmer = tables.getLootTable(BuiltInLootTables.FARMER_GIFT);
        var params = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, villager.position())
                .withParameter(LootContextParams.THIS_ENTITY, villager)
                .create(LootContextParamSets.GIFT);
        int minCount = 4;
        int maxCount = 0;
        for (long seed = 1; seed <= 32; seed++) {
            var drops = cleric.getRandomItems(params, seed);
            helper.assertTrue(drops.size() == 2 && drops.stream().anyMatch(
                            stack -> stack.is(Items.REDSTONE) || stack.is(Items.LAPIS_LAZULI)),
                    "牧师应保留红石或青金石赠礼，并额外赠送一组福灵果冻");
            int count = drops.stream().filter(stack -> stack.is(ModItems.FORTUNE_JELLY.get()))
                    .mapToInt(stack -> stack.getCount()).sum();
            helper.assertTrue(count >= 1 && count <= 3, "福灵果冻的每次赠送数量应为 1～3");
            minCount = Math.min(minCount, count);
            maxCount = Math.max(maxCount, count);
        }
        helper.assertTrue(minCount == 1 && maxCount == 3, "福灵果冻应能抽到数量区间的两端");
        helper.assertTrue(farmer.getRandomItems(params, 1L).stream()
                        .noneMatch(stack -> stack.is(ModItems.FORTUNE_JELLY.get())),
                "农民的赠礼应维持原版掉落");
        helper.succeed();
    }
}
