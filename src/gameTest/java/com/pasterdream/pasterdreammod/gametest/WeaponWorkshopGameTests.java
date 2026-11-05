package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.enhancestoneattributerelation.AttributeInfo;
import com.pasterdream.pasterdreammod.helper.enhancestoneattributerelation.EnhanceStoneAttributeRelationRegistry;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class WeaponWorkshopGameTests {
    @GameTest(template = "empty")
    public static void enhanceStoneAttributeRelations(GameTestHelper helper) {
        AttributeInfo attack = EnhanceStoneAttributeRelationRegistry.getInfo(ModItems.ATTACK_ENHANCE_STONE.get());
        AttributeInfo luck = EnhanceStoneAttributeRelationRegistry.getInfo(ModItems.LUCK_ENHANCE_STONE.get());
        helper.assertTrue(attack != null && attack.attribute().equals(Attributes.ATTACK_DAMAGE)
                        && attack.min() == 1 && attack.max() == 3,
                "攻击强化石属性关系错误");
        helper.assertTrue(luck != null && luck.attribute().equals(Attributes.LUCK)
                        && luck.min() == 1 && luck.max() == 3,
                "幸运强化石属性关系错误");
        helper.assertTrue(ModItems.WEAPON_WORKSHOP_CRAFTING_TABLE.get() instanceof BlockItem,
                "精铸工坊工作台物品应可放置");
        helper.succeed();
    }

    @GameTest(template = "empty", timeoutTicks = 100)
    public static void craftingTableCraft(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get());

        var drops = Block.getDrops(helper.getBlockState(pos), helper.getLevel(), helper.absolutePos(pos),
                helper.getBlockEntity(pos), null, new ItemStack(Items.IRON_PICKAXE));
        helper.assertTrue(drops.size() == 1 && drops.getFirst().is(ModItems.WEAPON_WORKSHOP_CRAFTING_TABLE.get()),
                "挖掘精铸工坊工作台应掉落自身");

        WeaponWorkshopCraftingTableBlockEntity blockEntity = helper.getBlockEntity(pos);
        ItemStack input = new ItemStack(Items.DIAMOND_SWORD);
        ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        enchantments.set(helper.getLevel().holderOrThrow(Enchantments.SHARPNESS), 1);
        input.set(DataComponents.ENCHANTMENTS, enchantments.toImmutable());
        helper.assertTrue(input.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
                        .getLevel(helper.getLevel().holderOrThrow(Enchantments.SHARPNESS)) == 1,
                "测试输入附魔未写入");
        blockEntity.getItemHandler().setStackInSlot(0, input);
        blockEntity.getItemHandler().setStackInSlot(5, new ItemStack(ModItems.ATTACK_ENHANCE_STONE.get()));

        blockEntity.craft(null);

        ItemStack output = blockEntity.getItemHandler().getStackInSlot(6);
        helper.assertTrue(output.is(Items.DIAMOND_SWORD), "工作台产物错误");
        helper.assertTrue(blockEntity.getItemHandler().getStackInSlot(0).isEmpty()
                        && blockEntity.getItemHandler().getStackInSlot(5).isEmpty(),
                "工作台应消耗输入物与强化石");

        var customData = output.get(DataComponents.CUSTOM_DATA);
        helper.assertTrue(customData != null && customData.copyTag().contains("AttackBoost"),
                "强化石未写入攻击加成");
        double attackBoost = customData.copyTag().getDouble("AttackBoost");
        helper.assertTrue(attackBoost >= 1 && attackBoost <= 3, "攻击加成超出强化石范围");

        var attributeModifiers = output.getAttributeModifiers().modifiers();
        helper.assertTrue(attributeModifiers.stream().anyMatch(entry -> entry.attribute().equals(Attributes.ATTACK_DAMAGE)
                        && entry.modifier().id().getPath().equals("attack_boost")
                        && entry.modifier().amount() == attackBoost),
                "攻击加成未写入主手属性修饰符");

        var mergedEnchantments = output.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        int sharpnessLevel = mergedEnchantments.getLevel(helper.getLevel().holderOrThrow(Enchantments.SHARPNESS));
        helper.assertTrue(sharpnessLevel == 1,
                "附魔合并不符合来源规则: size=" + mergedEnchantments.size() + ", sharpness=" + sharpnessLevel);
        helper.succeed();
    }

    @GameTest(template = "empty", timeoutTicks = 100)
    public static void windwreathedThunderSpearRecipe(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get());
        WeaponWorkshopCraftingTableBlockEntity blockEntity = helper.getBlockEntity(pos);

        blockEntity.getItemHandler().setStackInSlot(0, new ItemStack(ModItems.FLUFFY_WIND_ALLOY_SWORD.get()));
        blockEntity.getItemHandler().setStackInSlot(1, new ItemStack(ModItems.FLUFFY_WIND_ALLOY_PICKAXE.get()));
        blockEntity.getItemHandler().setStackInSlot(2, new ItemStack(ModItems.FLUFFY_WIND_ALLOY_AXE.get()));
        blockEntity.getItemHandler().setStackInSlot(3, new ItemStack(ModItems.FLUFFY_WIND_ALLOY_SHOVEL.get()));
        blockEntity.getItemHandler().setStackInSlot(4, new ItemStack(ModItems.FLUFFY_WIND_ALLOY_HOE.get()));
        blockEntity.getItemHandler().setStackInSlot(5, new ItemStack(ModItems.ATTACK_ENHANCE_STONE.get()));

        blockEntity.craft(null);

        ItemStack output = blockEntity.getItemHandler().getStackInSlot(6);
        helper.assertTrue(output.is(ModItems.WINDWREATHED_THUNDER_SPEAR.get()), "雷矛配方产物错误");
        for (int i = 0; i < 5; i++) {
            helper.assertTrue(blockEntity.getItemHandler().getStackInSlot(i).isEmpty(),
                    "雷矛配方应消耗第 " + i + " 个输入工具");
        }
        helper.assertTrue(blockEntity.getItemHandler().getStackInSlot(5).isEmpty(),
                "雷矛配方应消耗强化石");
        helper.succeed();
    }
}
