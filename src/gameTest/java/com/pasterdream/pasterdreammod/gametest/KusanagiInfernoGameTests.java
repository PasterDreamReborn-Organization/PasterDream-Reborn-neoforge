package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class KusanagiInfernoGameTests {

    @GameTest(template = "empty")
    public static void itemPropertiesAndTags(GameTestHelper helper) {
        ItemStack inferno = new ItemStack(ModItems.INFERNO_SWORD.get());
        helper.assertTrue(inferno.getMaxDamage() == 1255, "『融骸』狱炎剑耐久应为 1255");
        helper.assertTrue(inferno.is(ItemTags.SWORDS), "狱炎剑应属于剑标签");

        helper.assertTrue(new ItemStack(ModItems.KUSANAGI.get()).getMaxDamage() == 874, "草薙耐久应为 874");
        helper.assertTrue(new ItemStack(ModItems.MURAKUMO_KUSANAGI.get()).getMaxDamage() == 1311,
                "『天丛云』草薙耐久应为 1311");
        helper.assertTrue(new ItemStack(ModItems.SWORD_EMBRYO.get()).is(ItemTags.SWORDS), "失落之剑应属于剑标签");

        var food = new ItemStack(ModItems.JUNGLE_SPORE.get()).getFoodProperties(null);
        helper.assertTrue(food != null && food.nutrition() == 1 && food.saturation() == 0f,
                "丛林孢子应为 1 点饱食度、0 饱和度食物");

        ItemStack tomb = new ItemStack(ModItems.LOST_SWORD_TOMB.get());
        var modifiers = tomb.getAttributeModifiers();
        helper.assertTrue(modifiers.modifiers().stream().anyMatch(entry ->
                        entry.attribute().is(Attributes.ATTACK_DAMAGE)
                                && entry.slot() == EquipmentSlotGroup.MAINHAND
                                && entry.modifier().amount() == 9.0),
                "失落剑冢手持时应提供 +9 攻击力");
        helper.assertTrue(ModEntities.KUSANAGI_VENOM_STINGER.get() != null, "天丛云毒刺实体应完成注册");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void recipesAndToxicEffect(GameTestHelper helper) {
        var recipes = helper.getLevel().getRecipeManager();
        for (String id : new String[]{"inferno_sword_smithing", "kusanagi", "jungle_spore_from_bone_meal", "lost_sword_tomb"}) {
            helper.assertTrue(recipes.byKey(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, id)).isPresent(),
                    "缺少配方 " + id);
        }

        var zombie = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        zombie.setHealth(5.0f);
        var toxic = ModEffects.HIGHLY_TOXIC.value();
        toxic.applyEffectTick(zombie, 0);
        helper.assertTrue(zombie.getHealth() == 4.0f, "剧毒每跳应造成 1 点魔法伤害");

        zombie.setHealth(1.0f);
        zombie.removeEffect(ModEffects.HIGHLY_TOXIC);
        zombie.addEffect(new MobEffectInstance(ModEffects.HIGHLY_TOXIC, 100, 0));
        toxic.applyEffectTick(zombie, 0);
        helper.assertTrue(zombie.getHealth() == 1.0f, "剧毒不应使目标低于 1 点生命");
        helper.succeed();
    }
}
