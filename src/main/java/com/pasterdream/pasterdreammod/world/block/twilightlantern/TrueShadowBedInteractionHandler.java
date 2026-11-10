package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.world.dimension.LampShadowDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class TrueShadowBedInteractionHandler {

    private static final ResourceLocation BASTION_GUARD_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/bastion_guard");

    public static void execute(Level world, BlockPos pos, Player entity) {
        if (entity == null) return;
        if (world.isClientSide()) return;

        // Swing main hand
        entity.swing(InteractionHand.MAIN_HAND, true);

        if (!(entity instanceof ServerPlayer sp)) return;

        // 灯影之下：返回主世界。第二段对话与影/光选择界面属于后续剧情批次，尚未迁入。
        if (world.dimension() == LampShadowDimension.LAMP_SHADOW_WORLD) {
            LampShadowWorldTeleporter.teleportToOverworld(sp, pos);
            return;
        }

        // 主世界/下界：需完成据点守卫事件后才可入梦
        if (!AdvancementHelper.isDone(sp, BASTION_GUARD_ADV)) {
            return;
        }

        // Drain 10 San and teleport to lamp_shadow_world
        SanHelper.addPlayerSanAndSync(sp, -10);
        // 于影共眠进度属于后续剧情批次，尚未迁入。
        sp.server.execute(() -> LampShadowWorldTeleporter.execute(sp.level(), sp));
    }
}
