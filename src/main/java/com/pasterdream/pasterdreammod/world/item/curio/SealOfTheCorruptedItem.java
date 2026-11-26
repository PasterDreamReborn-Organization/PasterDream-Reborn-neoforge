package com.pasterdream.pasterdreammod.world.item.curio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.ISanModifier;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.AdvancementHelper;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class SealOfTheCorruptedItem extends Item implements ICurioItem, ISanModifier {

    private static final ResourceLocation ENTITY_REACH_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "seal_of_the_corrupted.entity_reach");
    private static final ResourceLocation BLOCK_REACH_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "seal_of_the_corrupted.block_reach");
    private static final ResourceLocation ATTACK_DAMAGE_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "seal_of_the_corrupted.attack_damage");
    private static final ResourceLocation TALENT_SHADOW_ADV =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/talent_shadow");

    public SealOfTheCorruptedItem() {
        super(new Properties().stacksTo(1).rarity(ModRarities.epic()));
    }

    @Override
    public boolean immuneToNegativeEffects() {
        return true;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        // 读档时 Curios 会把槽里那一件重新校验一遍，判重必须放行自身槽位；详见 CurioEquipHelper。
        return CurioEquipHelper.allowsSingleCopy(slotContext, stack);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (!(slotContext.entity() instanceof ServerPlayer player)) return;
        if (player.isCreative()) return;
        if (!SanHelper.getIsSanEnabled(player)) {
            reject(player, stack, false);
            return;
        }
        if (AdvancementHelper.isDone(player, TALENT_SHADOW_ADV)) return;
        reject(player, stack, true);
    }

    private static void reject(ServerPlayer player, ItemStack stack, boolean damage) {
        ItemStack dropped = stack.copy();
        dropped.setCount(1);
        player.displayClientMessage(Component.translatable("message.pasterdream.seal_of_the_corrupted.rejected"), true);
        clearEquippedCurio(player, stack.getItem());
        if (damage) {
            player.hurt(player.level().damageSources().generic(), 5.0F);
        }
        if (player.level() instanceof ServerLevel serverLevel) {
            ItemEntity itemEntity = new IndestructibleItemEntity(serverLevel,
                    player.getX(), player.getY() + 0.5, player.getZ(), dropped);
            itemEntity.setPickUpDelay(20);
            itemEntity.setDeltaMovement(0, 0.2, 0);
            serverLevel.addFreshEntity(itemEntity);
        }
    }

    private static void clearEquippedCurio(ServerPlayer player, Item item) {
        CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                handler.findFirstCurio(item).ifPresent(result ->
                        handler.setEquippedCurio(result.slotContext().identifier(),
                                result.slotContext().index(), ItemStack.EMPTY)
                )
        );
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
            SlotContext slotContext, ResourceLocation uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        CuriosApi.addSlotModifier(modifiers, "body", uuid, 1.0, AttributeModifier.Operation.ADD_VALUE);
        modifiers.put(Attributes.ENTITY_INTERACTION_RANGE,
                new AttributeModifier(ENTITY_REACH_ID, 1.0, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.BLOCK_INTERACTION_RANGE,
                new AttributeModifier(BLOCK_REACH_ID, 1.0, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(ATTACK_DAMAGE_ID, 2.0, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.seal_of_the_corrupted.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.seal_of_the_corrupted.effect2"));
        list.add(Component.translatable("tooltip.pasterdream.seal_of_the_corrupted.effect3"));
        list.add(Component.translatable("tooltip.pasterdream.seal_of_the_corrupted.flavor"));
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        var entity = new IndestructibleItemEntity(level, location.getX(), location.getY(), location.getZ(), stack);
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(location.getDeltaMovement());
        return entity;
    }
}
