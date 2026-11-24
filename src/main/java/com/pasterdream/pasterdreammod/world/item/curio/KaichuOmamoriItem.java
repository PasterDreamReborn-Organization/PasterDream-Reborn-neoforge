package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModKeyMappings;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.entity.FoxFireEntity;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class KaichuOmamoriItem extends Item implements ICurioItem {

    private static final double ENERGY_COST = 5.0;
    private static final net.minecraft.resources.ResourceLocation LUCK_ID =
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                    com.pasterdream.pasterdreammod.PasterDreamMod.MOD_ID, "kaichu_omamori_luck");

    public KaichuOmamoriItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.legendary()));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                    handler.findFirstCurio(stack.getItem()).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var luckAttr = entity.getAttribute(Attributes.LUCK);
            if (luckAttr != null && luckAttr.getModifier(LUCK_ID) == null) {
                luckAttr.addPermanentModifier(
                        new AttributeModifier(LUCK_ID, 5.0, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var luckAttr = entity.getAttribute(Attributes.LUCK);
            if (luckAttr != null) {
                luckAttr.removeModifier(LUCK_ID);
            }
        }
    }

    /**
     * 供快捷键（use 与 useOn）调用，在服务端检查能量/冷却/饰品装备状态后生成狐火。
     * @return true 表示成功生成
     */
    public static boolean tryActivate(Player player, Level level, BlockPos pos) {
        if (player instanceof ServerPlayer serverPlayer) {
            // 检查是否装备了怀中御守（饰品栏或手持均可）
            boolean hasItem = CuriosApi.getCuriosInventory(serverPlayer)
                    .map(h -> h.findFirstCurio(ModItems.KAICHU_OMAMORI.get()).isPresent())
                    .orElse(false);
            if (!hasItem) {
                hasItem = player.getMainHandItem().is(ModItems.KAICHU_OMAMORI.get())
                       || player.getOffhandItem().is(ModItems.KAICHU_OMAMORI.get());
            }
            if (!hasItem) return false;

            if (player.getCooldowns().isOnCooldown(ModItems.KAICHU_OMAMORI.get()))
                return false;

            double currentEnergy = MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(serverPlayer);
            if (!player.isCreative() && currentEnergy < ENERGY_COST) {
                player.displayClientMessage(Component.translatable("message.pasterdream.融梦能量不足"), true);
                return false;
            }

            // 消耗能量 + 设置冷却
            if (!player.isCreative()) {
                MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, -ENERGY_COST);
            }
            player.getCooldowns().addCooldown(ModItems.KAICHU_OMAMORI.get(), Config.KAICHU_OMAMORI_COOLDOWN.get() * 20);

            // 生成狐火
            FoxFireEntity foxFire = new FoxFireEntity(ModEntities.FOX_FIRE.get(), level);
            foxFire.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            foxFire.setOwner(player);
            level.addFreshEntity(foxFire);

            level.playSound(null, pos, ModSounds.FOX_FIRE.get(),
                    SoundSource.PLAYERS, 1.0F, 1.0F);
            return true;
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            // 从玩家视线方向获取目标位置
            BlockHitResult hit = (BlockHitResult) player.pick(5.0, 1.0F, false);
            BlockPos pos;
            if (hit.getType() != HitResult.Type.MISS) {
                pos = hit.getBlockPos().relative(hit.getDirection());
            } else {
                // 如果没有瞄准方块，则在玩家前方
                pos = BlockPos.containing(
                        player.getX() + player.getLookAngle().x * 2,
                        player.getY() + player.getEyeHeight() + player.getLookAngle().y * 2,
                        player.getZ() + player.getLookAngle().z * 2);
            }
            if (!tryActivate(player, level, pos)) {
                return InteractionResultHolder.fail(stack);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        if (!level.isClientSide()) {
            BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
            if (!tryActivate(player, level, pos)) {
                return InteractionResult.FAIL;
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(Component.translatable("tooltip.pasterdream.kaichu_omamori.effect.foxfire"));
        list.add(Component.translatable("tooltip.pasterdream.kaichu_omamori.effect.vulnerable"));
        list.add(Component.translatable("tooltip.pasterdream.kaichu_omamori.effect.regen"));
        list.add(Component.translatable("tooltip.pasterdream.kaichu_omamori.effect.duration",
                Config.FOX_FIRE_LIFETIME.get()).withStyle(ChatFormatting.BLUE));
        list.add(Component.translatable("tooltip.pasterdream.kaichu_omamori.effect.energy_cost"));
        list.add(Component.translatable("tooltip.pasterdream.kaichu_omamori.effect.cooldown",
                Config.KAICHU_OMAMORI_COOLDOWN.get()).withStyle(ChatFormatting.BLUE));
        list.add(Component.translatable("tooltip.pasterdream.kaichu_omamori.equip"));
        list.add(Component.translatable("tooltip.pasterdream.kaichu_omamori.luck"));
        list.add(Component.translatable("tooltip.pasterdream.kaichu_omamori.hotkey",
                ModKeyMappings.KAICHU_OMAMORI.getTranslatedKeyMessage()).withStyle(ChatFormatting.GREEN));
        list.add(Component.translatable("tooltip.pasterdream.kaichu_omamori.flavor"));
    }
}
