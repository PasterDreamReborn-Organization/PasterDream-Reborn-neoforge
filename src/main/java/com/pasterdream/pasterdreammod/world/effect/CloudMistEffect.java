package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

/**
 * 云雾 buff：标记效果，在风之旅途持续给予，用于显示退出维度的云雾进度。
 * 实际传送由 WindDirectionHandler 的保底判定（Y<0）触发。
 * 不产生粒子，也不可被牛奶等物品主动移除。
 */
public class CloudMistEffect extends MobEffect {

    public CloudMistEffect() {
        super(MobEffectCategory.NEUTRAL, -2687745);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        // 对应来源 getCurativeItems 返回空列表：不可被牛奶等治愈
    }

    @Override
    public void initializeClient(java.util.function.Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInGui(MobEffectInstance effect) {
                return false;
            }

            @Override
            public boolean isVisibleInInventory(MobEffectInstance effect) {
                return false;
            }
        });
    }
}
