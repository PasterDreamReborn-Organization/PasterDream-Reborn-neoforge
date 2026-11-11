package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.recipe.weaponworkshopcraftingtable.IWeaponWorkshopCraftingTableRecipe;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import com.pasterdream.pasterdreammod.world.entity.WhiteSwordRainProjectileEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import top.theillusivec4.curios.api.CuriosApi;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class WhiteShadowGearGameTests {

    @GameTest(template = "empty")
    public static void itemPropertiesAndTags(GameTestHelper helper) {
        var white = ModItems.WHITE_SWORD.toStack();
        var shadow = ModItems.SHADOW_SWORD.toStack();
        for (var stack : java.util.List.of(white, shadow)) {
            helper.assertTrue(stack.getMaxDamage() == 1771 && stack.getMaxStackSize() == 1, "白厄剑/影刃耐久或堆叠数错误");
            helper.assertTrue(stack.has(DataComponents.FIRE_RESISTANT), "白厄剑/影刃应防火");
            helper.assertTrue(stack.is(ItemTags.SWORDS), "白厄剑/影刃应属于剑标签");
            helper.assertTrue(stack.is(ModItemTags.SKILL_COOLDOWN), "白厄剑/影刃应属于战技冷却标签");
            helper.assertTrue(stack.is(ModItemTags.LAMP_SHADOW_WEAPON), "白厄剑/影刃应属于暮影武器标签");
        }
        helper.assertTrue(white.getItem().isValidRepairItem(white, ModItems.SHADOW_LIGHT.toStack()), "白厄剑应可用暗影之光修复");
        helper.assertTrue(shadow.getItem().isValidRepairItem(shadow, ModItems.NIGHTMARE_FUEL.toStack()), "影刃应可用惊梦燃料修复");

        var brooch = ModItems.BROOCH_OF_WHITE_ORCHID.toStack();
        var seal = ModItems.SEAL_OF_THE_CORRUPTED.toStack();
        helper.assertTrue(brooch.is(ModItemTags.PURE_HORROR_SOURCES) && seal.is(ModItemTags.PURE_HORROR_SOURCES),
                "胸针与堕落者之印应可作为纯粹恐惧原料");

        var shadowBase = shadow.getAttributeModifiers();
        helper.assertTrue(shadowBase.modifiers().size() == 3, "影刃基础属性应包含攻击力、攻速和 SAN 波动");
        CustomData.update(DataComponents.CUSTOM_DATA, shadow, tag -> tag.putDouble("sanRatio", 0.0));
        helper.assertTrue(shadow.getAttributeModifiers().modifiers().size() == 4, "影刃应随 sanRatio 添加动态攻速修饰器");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void curioSlotsAndWeaponWorkshopRecipes(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (var stack : java.util.List.of(ModItems.BROOCH_OF_WHITE_ORCHID.toStack(),
                ModItems.SEAL_OF_THE_CORRUPTED.toStack())) {
            helper.assertTrue(CuriosApi.getItemStackSlots(stack, player).containsKey("body"),
                    "饰品应可装备于 body 槽: " + stack);
        }

        for (var entry : java.util.List.of(
                java.util.Map.entry("white_sword", ModItems.WHITE_SWORD.toStack()),
                java.util.Map.entry("shadow_sword", ModItems.SHADOW_SWORD.toStack()))) {
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                    "weapon_workshop_crafting_table/" + entry.getKey());
            var recipe = helper.getLevel().getRecipeManager().byKey(id).orElse(null);
            helper.assertTrue(recipe != null, "应存在武器工坊配方: " + id);
            helper.assertTrue(recipe.value() instanceof IWeaponWorkshopCraftingTableRecipe,
                    "武器工坊配方类型错误: " + id);
            IWeaponWorkshopCraftingTableRecipe workshop = (IWeaponWorkshopCraftingTableRecipe) recipe.value();
            helper.assertTrue(workshop.getInputItemIngredients().size() == 5
                            && workshop.getOutputItemIngredients().test(entry.getValue()),
                    "武器工坊配方原料/产物错误: " + id);
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void swordRainProjectileRegisters(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var projectile = new WhiteSwordRainProjectileEntity(ModEntities.WHITE_SWORD_RAIN_PROJECTILE.get(), helper.getLevel());
        helper.assertTrue(projectile.getType() == ModEntities.WHITE_SWORD_RAIN_PROJECTILE.get(), "白厄剑雨实体类型错误");
        projectile.setOwner(player);
        projectile.setDamage(1.0f);
        helper.assertTrue(!projectile.canBeCollidedWith() && !projectile.isPickable() && !projectile.isPushable(),
                "白厄剑雨应为不可碰撞/不可拾取实体");
        helper.succeed();
    }
}
