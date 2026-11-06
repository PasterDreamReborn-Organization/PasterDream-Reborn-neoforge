package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.ChargedProjectiles;
import java.util.List;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

/** 折翼天使雕像的防护与瞬身术联动。 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class FracturedAngelStatueHandler {
    private static final String INVULN_UNTIL_TAG = "pasterdream:fractured_angel_statue_invuln_until";

    public static boolean isWearing(ServerPlayer player) {
        return CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.FRACTURED_ANGEL_STATUE.get()).isPresent())
                .orElse(false);
    }

    private static boolean isInvulnerableActive(ServerPlayer player) {
        return player.getPersistentData().getLong(INVULN_UNTIL_TAG) > player.level().getGameTime();
    }

    public static void onBlink(ServerPlayer player) {
        if (!isWearing(player)) return;
        ItemStack crossbow = player.getMainHandItem();
        if (!(crossbow.getItem() instanceof CrossbowItem)) return;
        if (CrossbowItem.isCharged(crossbow)) return;
        List<ItemStack> projectiles = CrossbowLoader.load(crossbow, player);
        if (!projectiles.isEmpty()) {
            crossbow.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(projectiles));
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.CROSSBOW_LOADING_END, SoundSource.PLAYERS, 1.0F,
                    1.0F / (player.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    /** 1.21 的 CrossbowItem.tryLoadProjectiles 为私有；复用其父类的原版弹药处理。 */
    private static final class CrossbowLoader extends CrossbowItem {
        private CrossbowLoader() {
            super(new Properties());
        }

        private static List<ItemStack> load(ItemStack crossbow, ServerPlayer player) {
            return draw(crossbow, player.getProjectile(crossbow), player);
        }
    }

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !isWearing(player)) return;
        DamageSource source = event.getSource();
        if (source.is(DamageTypes.FALL) || isInvulnerableActive(player)) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !isWearing(player)) return;
        DamageSource source = event.getSource();
        if (source.is(DamageTypes.FALL) || source.is(DamageTypes.FLY_INTO_WALL)) return;
        if (isInvulnerableActive(player)) return;
        if (player.getRandom().nextFloat() < Config.FRACTURED_ANGEL_STATUE_INVULNERABLE_CHANCE.get()) {
            player.getPersistentData().putLong(INVULN_UNTIL_TAG,
                    player.level().getGameTime() + Config.FRACTURED_ANGEL_STATUE_INVULNERABLE_TICKS.get());
            if (player.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.SCRAPE, player.getX(),
                        player.getY() + player.getBbHeight() * 0.6, player.getZ(),
                        24, 1.2, 1.0, 1.2, 0.1);
            }
        }
    }
}
