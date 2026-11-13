package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class ShadowHandLanternGameTests {

    @GameTest(template = "empty")
    public static void propertiesRecipeAndUse(GameTestHelper helper) {
        helper.assertTrue(BuiltInRegistries.ITEM.getKey(ModItems.SHADOW_HAND_LANTERN.get())
                        .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_hand_lantern")),
                "暗影提灯应以 shadow_hand_lantern 注册");

        ItemStack lantern = ModItems.SHADOW_HAND_LANTERN.toStack();
        helper.assertTrue(lantern.getMaxStackSize() == 1, "暗影提灯应不可堆叠");
        var modifiers = lantern.get(DataComponents.ATTRIBUTE_MODIFIERS).modifiers();
        helper.assertTrue(modifiers.stream().anyMatch(entry -> entry.attribute().equals(ModAttributes.SAN_VARIABILITY)
                        && entry.modifier().amount() == 1.2
                        && entry.slot() == EquipmentSlotGroup.HAND),
                "手持暗影提灯应提供 1.2 SAN 波动");

        CraftingInput input = CraftingInput.of(3, 3, List.of(
                ItemStack.EMPTY, new ItemStack(Items.CHAIN), ItemStack.EMPTY,
                new ItemStack(Items.GLASS_PANE), new ItemStack(Items.PINK_CANDLE), new ItemStack(Items.GLASS_PANE),
                ModItems.BLACK_METAL_NUGGET.toStack(), ModItems.BLACK_METAL_INGOT.toStack(),
                ModItems.BLACK_METAL_NUGGET.toStack()));
        var recipe = helper.getLevel().getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
        helper.assertTrue(recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_hand_lantern"))
                        && recipe.value().assemble(input, helper.getLevel().registryAccess())
                        .is(ModItems.SHADOW_HAND_LANTERN.get()),
                "暗影提灯配方应保持链+玻璃板+粉色蜡烛+黑金属粒/锭");

        FakePlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "shadow-lantern-test"));
        player.setPos(helper.absolutePos(new BlockPos(2, 1, 2)).getCenter());
        player.getData(ModAttachments.SAN).setSanValue(10.0);
        player.setItemInHand(InteractionHand.MAIN_HAND, lantern);
        Silverfish shadowMob = EntityType.SILVERFISH.create(helper.getLevel());
        shadowMob.moveTo(player.getX() + 2, player.getY(), player.getZ(), 0, 0);
        helper.getLevel().addFreshEntity(shadowMob);

        lantern.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);

        helper.assertTrue(player.getCooldowns().isOnCooldown(ModItems.SHADOW_HAND_LANTERN.get()),
                "使用后应进入 8 秒冷却");
        helper.assertTrue(Math.abs(player.getData(ModAttachments.SAN).getSanValue() - 9.0) < 0.0001,
                "使用应消耗 1 SAN");
        helper.assertTrue(shadowMob.hasEffect(ModEffects.VULNERABILITY),
                "半径 15 格内暗影生物应获得易伤");
        helper.succeed();
    }
}
