package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.dreamtrainticket.DreamTrainTicketItem;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DreamTrainTicketGameTests {

    @GameTest(template = "empty")
    public static void dreamTrainTicketRegistrationAndWhitelist(GameTestHelper helper) {
        ItemStack stack = new ItemStack(ModItems.DREAM_TRAIN_TICKET.get());
        helper.assertTrue(stack.getItem() instanceof DreamTrainTicketItem, "逐梦列车车票应使用专用物品类");
        helper.assertTrue(stack.getMaxStackSize() == 1, "逐梦列车车票应不可堆叠");
        helper.assertTrue(Config.isTrainTicketDimensionAllowed(
                ResourceLocation.fromNamespaceAndPath("minecraft", "overworld")), "主世界应在逐梦列车站点白名单中");
        helper.assertFalse(Config.isTrainTicketDimensionAllowed(
                ResourceLocation.fromNamespaceAndPath("pasterdream", "no_such_station")), "未知维度不应被允许停靠");
        helper.succeed();
    }
}
