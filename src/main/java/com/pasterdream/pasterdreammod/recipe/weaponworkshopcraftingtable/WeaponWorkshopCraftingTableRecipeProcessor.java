package com.pasterdream.pasterdreammod.recipe.weaponworkshopcraftingtable;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.enhancestoneattributerelation.AttributeInfo;
import com.pasterdream.pasterdreammod.helper.enhancestoneattributerelation.EnhanceStoneAttributeRelationRegistry;
import com.pasterdream.pasterdreammod.helper.nbthelper.ItemsNBTAdderCalculator;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.genericprocessor.ItemProcessor;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.genericprocessor.SingleItemProcessor;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.List;

public class WeaponWorkshopCraftingTableRecipeProcessor
{
    private static final ResourceLocation ATTACK_BOOST_ID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "attack_boost");
    private static final ResourceLocation LUCK_BOOST_ID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "luck_boost");

    public static WeaponWorkshopCraftingTableRecipeInventory processing(WeaponWorkshopCraftingTableRecipeInventory recipeRequire, WeaponWorkshopCraftingTableRecipeInventory machineInventory, RandomSource random, double playerLuckLevel)
    {
        List<ItemStack> matchedInputItemStacks = recipeRequire.inputItemStacks();
        List<ItemStack> machineInputItemStacks = machineInventory.inputItemStacks();
        // 1.21 物品数量归零后 getComponents() 返回空映射，先在消耗前保留输入快照以维持来源的 NBT 汇总语义。
        List<ItemStack> machineInputItemStacksForNbt = new java.util.ArrayList<>(machineInputItemStacks.size());
        for (ItemStack itemStack : machineInputItemStacks)
        {
            machineInputItemStacksForNbt.add(itemStack.copy());
        }
        List<ItemStack> machineInputInventoryProcessResult = ItemProcessor.processor(matchedInputItemStacks, machineInventory.inputItemStacks(), false);
        if(machineInputInventoryProcessResult == null)
        {
            return null;
        }

        ItemStack matchedEnhanceStone = recipeRequire.enhanceStone();
        ItemStack machineEnhanceStone = machineInventory.enhanceStone();
        ItemStack enhanceStoneProcessResult = machineEnhanceStone.copy();
        AttributeInfo enhanceStoneInfo = null;
        if (!matchedEnhanceStone.isEmpty())
        {
            enhanceStoneProcessResult = SingleItemProcessor.processor(matchedEnhanceStone, machineEnhanceStone, false);
            if(enhanceStoneProcessResult == null)
            {
                return null;
            }
            enhanceStoneInfo = EnhanceStoneAttributeRelationRegistry.getInfo(matchedEnhanceStone.getItem());
        }

        CompoundTag calculatedNBT = ItemsNBTAdderCalculator.calculate(machineInputItemStacksForNbt);
        ItemEnchantments calculatedEnchantments = ItemsNBTAdderCalculator.calculateEnchantments(machineInputItemStacksForNbt);

        if(enhanceStoneInfo != null)
        {
            double level = (enhanceStoneInfo.max() - enhanceStoneInfo.min()) * (Math.pow(random.nextDouble(), (1 / (1 + playerLuckLevel)))) + enhanceStoneInfo.min();
            Holder<Attribute> attributeType = enhanceStoneInfo.attribute();

            if(attributeType.equals(Attributes.ATTACK_DAMAGE))
            {
                double attackBoost = 0;
                if(calculatedNBT.contains("AttackBoost"))
                {
                    attackBoost += calculatedNBT.getDouble("AttackBoost");
                }
                attackBoost += level;
                calculatedNBT.putDouble("AttackBoost", attackBoost);
            }
            else
                if(attributeType.equals(Attributes.LUCK))
                {
                    double luckBoost = 0;
                    if(calculatedNBT.contains("LuckBoost"))
                    {
                        luckBoost += calculatedNBT.getDouble("LuckBoost");
                    }
                    luckBoost += level;
                    calculatedNBT.putDouble("LuckBoost", luckBoost);
                }
        }

        ItemStack matchedOutputItemStack = recipeRequire.outputItemStack().copy();
        ItemStack machineOutputItemStack = machineInventory.outputItemStack();

        if(calculatedNBT.isEmpty())
        {
            matchedOutputItemStack.remove(DataComponents.CUSTOM_DATA);
        }
            else
            {
                matchedOutputItemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(calculatedNBT));
            }

        if(calculatedEnchantments == null)
        {
            matchedOutputItemStack.remove(DataComponents.ENCHANTMENTS);
        }
            else
            {
                matchedOutputItemStack.set(DataComponents.ENCHANTMENTS, calculatedEnchantments);
            }

        applyAttributeBoosts(matchedOutputItemStack, calculatedNBT);

        ItemStack machineOutputInventoryProcessResult = SingleItemProcessor.processor(matchedOutputItemStack, machineOutputItemStack, true);
        if(machineOutputInventoryProcessResult != null)
        {
            return new WeaponWorkshopCraftingTableRecipeInventory(machineInputInventoryProcessResult, enhanceStoneProcessResult, machineOutputInventoryProcessResult);
        }
            else
            {
                return null;
            }
    }

    /**
     * 1.20.1 来源通过物品 NBT 的 AttackBoost/LuckBoost 与 Mixin 在主手时动态附加属性；
     * 1.21 属性修饰符由物品组件承载，因此在产出物上写入同 ID、同运算方式的主手修饰符。
     */
    private static void applyAttributeBoosts(ItemStack itemStack, CompoundTag calculatedNBT)
    {
        boolean changed = false;
        ItemAttributeModifiers attributeModifiers = itemStack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);

        if(calculatedNBT.contains("AttackBoost"))
        {
            AttributeModifier modifier = new AttributeModifier(ATTACK_BOOST_ID, calculatedNBT.getDouble("AttackBoost"),
                    AttributeModifier.Operation.ADD_VALUE);
            attributeModifiers = attributeModifiers.withModifierAdded(Attributes.ATTACK_DAMAGE, modifier, EquipmentSlotGroup.MAINHAND);
            changed = true;
        }

        if(calculatedNBT.contains("LuckBoost"))
        {
            AttributeModifier modifier = new AttributeModifier(LUCK_BOOST_ID, calculatedNBT.getDouble("LuckBoost"),
                    AttributeModifier.Operation.ADD_VALUE);
            attributeModifiers = attributeModifiers.withModifierAdded(Attributes.LUCK, modifier, EquipmentSlotGroup.MAINHAND);
            changed = true;
        }

        if(changed)
        {
            itemStack.set(DataComponents.ATTRIBUTE_MODIFIERS, attributeModifiers);
        }
    }
}
