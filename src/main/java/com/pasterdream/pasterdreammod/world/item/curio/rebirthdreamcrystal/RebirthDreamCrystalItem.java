package com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class RebirthDreamCrystalItem extends Item implements ICurioItem {

    // 修饰符 ID（1.21 的 AttributeModifier 改由 ResourceLocation 键控，取代 1.20.1 的 UUID）
    private static final ResourceLocation MAX_HEALTH_ID = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "rebirth_dream_crystal_max_health");
    private static final ResourceLocation MELT_DREAM_VARIABILITY_ID = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "rebirth_dream_crystal_melt_dream_variability");
    private static final ResourceLocation SKILL_DAMAGE_MULTIPLIER_ID = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "rebirth_dream_crystal_skill_damage");
    private static final ResourceLocation MAX_ARMOR_ID = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "rebirth_dream_crystal_max_armor");
    private static final ResourceLocation SPEED_MULTIPLIER_ID = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "rebirth_dream_crystal_speed");
    private static final ResourceLocation SKILL_COOLDOWN_MULTIPLIER_ID = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "rebirth_dream_crystal_skill_cooldown");

    // 上游的 SAN_VARIABILITY_UUID 残留清理未搬运：1.21 无法按 UUID 删除修饰符，
    // 且本仓库此前未移植本饰品，不存在需要清理的历史修饰符。

    public RebirthDreamCrystalItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.miracle()));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);
        if(!level.isClientSide())
        {
            if (player.isShiftKeyDown())
            {
                List<Item> loot = Config.getRebirthDreamCrystalLoot();
                if (!loot.isEmpty())
                {
                    Item picked = loot.get(player.getRandom().nextInt(loot.size()));
                    ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(picked));
                }
                level.playSound(null, player.blockPosition(), ModSounds.DING.get(), SoundSource.PLAYERS, 1, 1);

                if (!player.getAbilities().instabuild)
                {
                    itemStack.shrink(1);
                }
            }
            else
            {
                if (player instanceof ServerPlayer serverPlayer)
                {
                    serverPlayer.openMenu(new MenuProvider()
                    {
                        @Override
                        public Component getDisplayName()
                        {
                            return Component.translatable("gui." + PasterDreamMod.MOD_ID + ".rebirth_dream_crystal");
                        }

                        @Override
                        public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
                        {
                            return new RebirthDreamCrystalMenu(id, inventory);
                        }
                    });
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack)
    {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide())
        {
            if(entity instanceof Player player)
            {
                if(PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, "dyedream_world"))
                {
                    AttributeInstance maxHealthAttribute = entity.getAttribute(Attributes.MAX_HEALTH);
                    if(maxHealthAttribute != null && maxHealthAttribute.getModifier(MAX_HEALTH_ID) == null)
                    {
                        maxHealthAttribute.addPermanentModifier(new AttributeModifier(MAX_HEALTH_ID, 16, AttributeModifier.Operation.ADD_VALUE));
                        player.setHealth(player.getHealth() + 16);
                    }

                    AttributeInstance maxMeltDreamVariability = entity.getAttribute(ModAttributes.MELT_DREAM_VARIABILITY);
                    if(maxMeltDreamVariability != null && maxMeltDreamVariability.getModifier(MELT_DREAM_VARIABILITY_ID) == null)
                    {
                        maxMeltDreamVariability.addPermanentModifier(new AttributeModifier(MELT_DREAM_VARIABILITY_ID, 16, AttributeModifier.Operation.ADD_VALUE));
                    }
                }

                if(PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, "lamp_shadow_world"))
                {
                    AttributeInstance skillDamageMultiplier = entity.getAttribute(ModAttributes.SKILL_DAMAGE_RATE);
                    if(skillDamageMultiplier != null && skillDamageMultiplier.getModifier(SKILL_DAMAGE_MULTIPLIER_ID) == null)
                    {
                        skillDamageMultiplier.addPermanentModifier(new AttributeModifier(SKILL_DAMAGE_MULTIPLIER_ID, 0.16, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    }

                    AttributeInstance maxArmor = entity.getAttribute(Attributes.ARMOR);
                    if(maxArmor != null && maxArmor.getModifier(MAX_ARMOR_ID) == null)
                    {
                        maxArmor.addPermanentModifier(new AttributeModifier(MAX_ARMOR_ID, 16, AttributeModifier.Operation.ADD_VALUE));
                    }
                }

                if(PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, "wind_journey_world"))
                {
                    AttributeInstance movementSpeedMultiplier = entity.getAttribute(Attributes.MOVEMENT_SPEED);
                    if(movementSpeedMultiplier != null && movementSpeedMultiplier.getModifier(SPEED_MULTIPLIER_ID) == null)
                    {
                        movementSpeedMultiplier.addPermanentModifier(new AttributeModifier(SPEED_MULTIPLIER_ID, 0.16, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    }

                    AttributeInstance skillCooldownMultiplier = entity.getAttribute(ModAttributes.SKILL_COOLDOWN_REDUCTION);
                    if(skillCooldownMultiplier != null && skillCooldownMultiplier.getModifier(SKILL_COOLDOWN_MULTIPLIER_ID) == null)
                    {
                        skillCooldownMultiplier.addPermanentModifier(new AttributeModifier(SKILL_COOLDOWN_MULTIPLIER_ID, 0.16, AttributeModifier.Operation.ADD_VALUE));
                    }
                }
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack)
    {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide())
        {
            if(entity instanceof Player player)
            {
                if(PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, "dyedream_world"))
                {
                    AttributeInstance maxHealthAttribute = entity.getAttribute(Attributes.MAX_HEALTH);
                    if(maxHealthAttribute != null)
                    {
                        maxHealthAttribute.removeModifier(MAX_HEALTH_ID);
                        player.setHealth(Math.min(player.getHealth(), player.getMaxHealth()));
                    }

                    AttributeInstance maxMeltDreamVariability = entity.getAttribute(ModAttributes.MELT_DREAM_VARIABILITY);
                    if(maxMeltDreamVariability != null)
                    {
                        maxMeltDreamVariability.removeModifier(MELT_DREAM_VARIABILITY_ID);
                    }
                }

                if(PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, "lamp_shadow_world"))
                {
                    AttributeInstance skillDamageMultiplier = entity.getAttribute(ModAttributes.SKILL_DAMAGE_RATE);
                    if(skillDamageMultiplier != null)
                    {
                        skillDamageMultiplier.removeModifier(SKILL_DAMAGE_MULTIPLIER_ID);
                    }

                    AttributeInstance maxArmor = entity.getAttribute(Attributes.ARMOR);
                    if(maxArmor != null)
                    {
                        maxArmor.removeModifier(MAX_ARMOR_ID);
                    }
                }

                if(PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, "wind_journey_world"))
                {
                    AttributeInstance movementSpeedMultiplier = entity.getAttribute(Attributes.MOVEMENT_SPEED);
                    if(movementSpeedMultiplier != null)
                    {
                        movementSpeedMultiplier.removeModifier(SPEED_MULTIPLIER_ID);
                    }

                    AttributeInstance skillCooldownMultiplier = entity.getAttribute(ModAttributes.SKILL_COOLDOWN_REDUCTION);
                    if(skillCooldownMultiplier != null)
                    {
                        skillCooldownMultiplier.removeModifier(SKILL_COOLDOWN_MULTIPLIER_ID);
                    }
                }
            }
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                            handler.findFirstCurio(this).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.1"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.2"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.+16最大生命值(完成染梦世界任务后解锁)"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.+16融梦光环(完成染梦世界任务后解锁)"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.+16%战技伤害(完成灯影之下任务后解锁)"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.+16护甲值(完成灯影之下任务后解锁)"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.+16%速度(完成风之旅途任务后解锁)"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.-16%战技冷却(完成风之旅途任务后解锁)"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.5"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.6"));
        list.add(Component.translatable("tooltip.pasterdream.rebirth_dream_crystal.7"));
    }

}
