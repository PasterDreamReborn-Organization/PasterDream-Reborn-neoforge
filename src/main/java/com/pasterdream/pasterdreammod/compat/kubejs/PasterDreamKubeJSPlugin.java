package com.pasterdream.pasterdreammod.compat.kubejs;

import com.pasterdream.pasterdreammod.PasterDreamTipsManager;
import com.pasterdream.pasterdreammod.compat.kubejs.food.PasterDreamFoodEvents;
import com.pasterdream.pasterdreammod.compat.kubejs.food.PasterDreamFoodRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;
import java.util.Map;

public class PasterDreamKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void init() {
        NeoForge.EVENT_BUS.register(PasterDreamFoodEvents.class);
    }

    @Override
    public void registerBindings(BindingRegistry event) {
        event.add("PasterDreamTips", new TipsBinding());
        event.add("PasterDreamFoods", new PasterDreamFoodsBinding());
    }

    public static class TipsBinding {

        public List<String> getTips() {
            return PasterDreamTipsManager.INSTANCE.getActiveTips();
        }

        public List<String> getCustomTips() {
            return PasterDreamTipsManager.INSTANCE.getCustomTips();
        }

        public List<String> getDefaultTips() {
            return PasterDreamTipsManager.INSTANCE.getDefaultTips();
        }

        public void add(String tip) {
            PasterDreamTipsManager.INSTANCE.addTip(tip);
        }

        public void remove(int index) {
            PasterDreamTipsManager.INSTANCE.removeTip(index);
        }

        public void clear() {
            PasterDreamTipsManager.INSTANCE.clearCustomTips();
        }

        public void reset() {
            PasterDreamTipsManager.INSTANCE.resetToDefaults();
        }
    }

    /**
     * 外部食物属性绑定：让任意物品（含原版与其他模组食物）获得 SAN 值与融梦能量加成，
     * 进食时自动生效并同步，ToolTip 自动追加加成行。
     *
     * <p>用法示例（startup_scripts）：
     * <pre>{@code
     * PasterDreamFoods.register('minecraft:apple', 1, 0.5);
     * PasterDreamFoods.register('minecraft:golden_apple', { san: 2, meltDreamEnergy: 1.5 });
     * PasterDreamFoods.get('minecraft:apple');
     * PasterDreamFoods.unregister('minecraft:apple');
     * PasterDreamFoods.clear();
     * }</pre>
     */
    public static class PasterDreamFoodsBinding {

        /** 注册食物属性：sanAdd = SAN 加成，meltDreamEnergyAdd = 融梦能量加成（负值扣减）。 */
        public boolean register(String itemId, double sanAdd, double meltDreamEnergyAdd) {
            return PasterDreamFoodRegistry.register(itemId, sanAdd, meltDreamEnergyAdd);
        }

        /** 注册食物属性：对象形式，支持键 san/sanAdd、meltDreamEnergy/meltDreamEnergyAdd。 */
        public boolean register(String itemId, Map<?, ?> properties) {
            return PasterDreamFoodRegistry.register(itemId, properties);
        }

        public boolean unregister(String itemId) {
            return PasterDreamFoodRegistry.unregister(itemId);
        }

        public void clear() {
            PasterDreamFoodRegistry.clear();
        }

        public boolean isRegistered(String itemId) {
            return PasterDreamFoodRegistry.isRegistered(itemId);
        }

        /** 已注册物品的 SAN 加成，未注册返回 0。 */
        public double getSan(String itemId) {
            return PasterDreamFoodRegistry.getSan(itemId);
        }

        /** 已注册物品的融梦能量加成，未注册返回 0。 */
        public double getMeltDreamEnergy(String itemId) {
            return PasterDreamFoodRegistry.getMeltDreamEnergy(itemId);
        }

        /** 返回注册效果对象（getSan() / getMeltDreamEnergy()），未注册返回 null。 */
        public PasterDreamFoodRegistry.FoodEffects get(String itemId) {
            return PasterDreamFoodRegistry.get(itemId);
        }

        /** 返回全部注册项（键为物品 id）。 */
        public Map<?, ?> getAll() {
            return PasterDreamFoodRegistry.getAll();
        }
    }
}
