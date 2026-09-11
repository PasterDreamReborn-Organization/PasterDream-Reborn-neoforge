package com.pasterdream.pasterdreammod;

import com.mojang.logging.LogUtils;
import com.pasterdream.pasterdreammod.config.PasterDreamClientConfig;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModBluePrintsContentRelation;
import com.pasterdream.pasterdreammod.init.ModCreativeModeTabs;
import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import com.pasterdream.pasterdreammod.init.ModCropRelation;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.init.ModDreamNotesBookContentRelation;
import com.pasterdream.pasterdreammod.init.ModDreamNotesContentRelation;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEnhanceStoneAttributeRelation;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModFeatures;
import com.pasterdream.pasterdreammod.init.ModFluidContainerRelation;
import com.pasterdream.pasterdreammod.init.ModFluidPropertiesRelation;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModFoliagePlacerTypes;
import com.pasterdream.pasterdreammod.init.ModGameRules;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModPotions;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.init.ModShadowDungeonStructureSet;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.init.ModTreeDecoratorTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

/**
 * PasterDream: Reborn — NeoForge 1.21.1 主类。
 *
 * <p>构造参数 {@link IEventBus} 与 {@link ModContainer} 由 FML 注入，是唯一的注册入口。
 * 注册顺序与 Forge 1.20.1 源仓库保持一致，API 差异见 {@code document/reference/移植总纲.md}。
 */
@Mod(PasterDreamMod.MOD_ID)
public class PasterDreamMod {
    public static final String MOD_ID = "pasterdream";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PasterDreamMod(IEventBus modEventBus, ModContainer modContainer) {
        // ---- 注册中心 ----
        ModBlocks.register(modEventBus);           // 方块
        ModItems.register(modEventBus);            // 物品
        ModCreativeModeTabs.register(modEventBus); // 创造模式物品栏
        ModBlockEntities.register(modEventBus);    // 方块实体
        ModEntities.register(modEventBus);         // 实体
        ModFluids.register(modEventBus);           // 流体 + 流体类型
        ModMenus.register(modEventBus);            // 菜单
        ModRecipes.register(modEventBus);          // 配方类型 / 序列化器
        ModSounds.register(modEventBus);           // 音效
        ModEffects.register(modEventBus);          // 药水效果
        ModPotions.register(modEventBus);          // 药水
        ModAttributes.register(modEventBus);       // 自定义属性
        ModParticleTypes.register(modEventBus);    // 粒子类型
        ModFeatures.register(modEventBus);         // 自定义 Feature
        ModFoliagePlacerTypes.register(modEventBus); // 树叶放置器类型
        ModTreeDecoratorTypes.register(modEventBus); // 树木装饰器类型
        ModDataComponents.register(modEventBus);   // DataComponents
        ModAttachments.register(modEventBus);      // AttachmentType（取代 Forge Capability）

        // ---- 启动阶段监听 ----
        modEventBus.addListener(this::commonSetup);

        // ---- 游戏总线监听（本类自己订阅的事件）----
        NeoForge.EVENT_BUS.register(this);

        // ---- 静态初始化（不走 DeferredRegister）----
        ModGameRules.init();        // gamerule
        ModCriteriaTriggers.init(); // 自定义进度触发器
        // 网络框架由 ModNetwork 的 @EventBusSubscriber 自行注册（RegisterPayloadHandlersEvent）

        // ---- 配置文件 ----
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, PasterDreamClientConfig.SPEC, "PasterDream-Client.toml");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModFluidContainerRelation.registerFluidContainerRelation();
            ModFluidPropertiesRelation.register();
            ModDreamNotesContentRelation.registerDreamNotesContentRelation();
            ModDreamNotesBookContentRelation.registerDreamNotesBookContentRelation();
            ModBluePrintsContentRelation.registerBluePrintsContentRelation();
            ModCropRelation.registerCropRelation();
            ModEnhanceStoneAttributeRelation.registerModEnhanceStoneAttributeRelation();
            ModShadowDungeonStructureSet.register();
        });
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("PasterDream: Reborn (NeoForge) server starting");
    }
}
