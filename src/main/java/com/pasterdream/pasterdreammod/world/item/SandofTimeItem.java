package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.List;

public class SandofTimeItem extends Item {

    private static final String TAG_COOLDOWN = "TimeCooldown";

    public SandofTimeItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            long now = level.getGameTime();
            long lastUse = getLastUse(stack);
            int cooldownTicks = Config.TIME_OF_SAND_COOLDOWN.get() * 20;

            if (lastUse != 0 && now - lastUse < cooldownTicks) {
                long remainingTicks = cooldownTicks - (now - lastUse);
                int remainingSeconds = (int) Math.ceil(remainingTicks / 20.0);
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.sand_of_time.cooldown", remainingSeconds), true);
                return InteractionResultHolder.fail(stack);
            }

            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putLong(TAG_COOLDOWN, now));

            boolean isDay = level.isDay();
            long newTime = isDay ? 18000 : 1000;
            // DerivedLevelData.setDayTime() 是空方法，必须遍历所有维度（与 /time set 行为一致）
            for (ServerLevel sl : serverLevel.getServer().getAllLevels()) {
                sl.setDayTime(newTime);
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.EVASION.get(), SoundSource.PLAYERS, 1.0f, 1.5f);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        int cooldownTime = Config.TIME_OF_SAND_COOLDOWN.get();
        tooltip.add(Component.translatable("tooltip.pasterdream.sand_of_time.1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.sand_of_time.2", cooldownTime).withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.pasterdream.sand_of_time.3"));

        // 显示剩余冷却时间
        Level level = context.level();
        if (level != null) {
            long now = level.getGameTime();
            long lastUse = getLastUse(stack);
            int cooldownTicks = cooldownTime * 20;
            long remainingTicks = cooldownTicks - (now - lastUse);

            if (lastUse != 0 && remainingTicks > 0) {
                int remainingSeconds = (int) Math.ceil(remainingTicks / 20.0);
                tooltip.add(Component.translatable("tooltip.pasterdream.sand_of_time.4", remainingSeconds)
                        .withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.pasterdream.sand_of_time.5")
                        .withStyle(ChatFormatting.GREEN));
            }
        }
    }

    private static long getLastUse(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return tag.getLong(TAG_COOLDOWN);
    }
}
