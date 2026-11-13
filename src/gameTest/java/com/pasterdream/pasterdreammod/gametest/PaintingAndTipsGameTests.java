package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.PasterDreamTipsManager;
import com.pasterdream.pasterdreammod.init.ModPaintings;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.Random;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class PaintingAndTipsGameTests {
    private static final TagKey<PaintingVariant> PLACEABLE = TagKey.create(Registries.PAINTING_VARIANT,
            ResourceLocation.withDefaultNamespace("placeable"));

    @GameTest(template = "empty")
    public static void paintingVariantsArePlaceable(GameTestHelper helper) {
        var registry = helper.getLevel().registryAccess().registryOrThrow(Registries.PAINTING_VARIANT);
        List<ResourceKey<PaintingVariant>> keys = List.of(
                ModPaintings.PASTERDREAM_TITLE,
                ModPaintings.PASTERDREAM_START,
                ModPaintings.PASTERDREAM_AEROLITE_DUST,
                ModPaintings.PASTERDREAM_PORTAL,
                ModPaintings.PASTERDREAM_TELESCOPE,
                ModPaintings.PASTERDREAM_DYEDREAM_FLOWER,
                ModPaintings.PASTERDREAM_DYEDREAM_STATION,
                ModPaintings.PASTERDREAM_PINK_YELLOW_HOUSE);
        int[][] sizes = {{4, 4}, {4, 4}, {16, 16}, {1, 1}, {2, 1}, {1, 1}, {2, 2}, {2, 1}};
        for (int i = 0; i < keys.size(); i++) {
            var holder = registry.getHolderOrThrow(keys.get(i));
            helper.assertTrue(holder.is(PLACEABLE), keys.get(i).location() + " 应可由画物品放置");
            helper.assertTrue(holder.value().width() == sizes[i][0] && holder.value().height() == sizes[i][1],
                    keys.get(i).location() + " 画幅尺寸应与来源贴图一致");
            helper.assertTrue(holder.value().assetId().equals(keys.get(i).location()),
                    keys.get(i).location() + " 应引用同名画作贴图");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void customTipsOverrideAndReset(GameTestHelper helper) {
        PasterDreamTipsManager manager = PasterDreamTipsManager.INSTANCE;
        manager.clearCustomTips();
        try {
            helper.assertTrue(manager.getActiveTips() == manager.getDefaultTips(), "未添加自定义提示时应使用来源默认列表");
            manager.addTip("test tip");
            helper.assertTrue(manager.getRandomTip(new Random(0)).equals("test tip"), "自定义提示应覆盖默认列表");
            manager.removeTip(0);
            helper.assertTrue(manager.getActiveTips() == manager.getDefaultTips(), "删除最后一条提示后应回到默认列表");
        } finally {
            manager.resetToDefaults();
        }
        helper.succeed();
    }
}
