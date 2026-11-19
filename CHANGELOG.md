# 更新日志

## 未发布（NeoForge 1.21.1 移植）

> 本仓库为 [`PasterDream-Reborn-forge1.20.1`](https://github.com/PasterDreamReborn-Organization/PasterDream-Reborn-forge1.20.1) 的 NeoForge 移植版，从空 MDK 搭建骨架。
> 搬运总纲见 [`document/reference/移植总纲.md`](document/reference/移植总纲.md)。

### 批次 135：搬运眠椰系列与风泊群岛水池生成（2026-11-19，T11/T19 部分）

- 迁入眠椰原木、木头、两种去皮木材、木板、树叶、树苗、盆栽和眠椰块的落地/贴墙形态。补齐斧头去皮、木材配方、园艺钳剪取、挂果掉落和精准采集。
- 风泊群岛的成功生成水池按上游配置以 20% 概率尝试在岸边生成 1–2 棵眠椰树。沙地树苗可用骨粉催生同一地物。接通眠椰、眠椰苏打水和海盐眠椰饮的流体容器关系，补入海盐眠椰饮研钵配方，恢复染梦野餐篮计划使用的眠椰和海盐眠椰饮奖池。
- `runData`、`build` 和 `runGameTestServer` 226/226 通过。种子 `0` 的隔离服务端新生成区块中核到眠椰原木、树叶和 6 个贴墙果实及方块实体。客户端渲染、多人交互和跨种子的自然分布待验收。
- **建议提交信息：** `feat(slumber-palm): 搬运眠椰方块、世界生成与饮品`

### 批次 134：修复帕斯特UI资源包下饰品栏背包底图透明通道丢失（2026-11-18）

- 启用内置资源包 `pasterdream:vanilla_ui` 后，从背包点开饰品栏时，被替换的 `textures/gui/container/inventory.png` 半透明面板渲染为不透明亮块。原因是饰品栏界面为 Curios 的 `CuriosScreen`，它复用同一张背包底图却不是 `InventoryScreen`，而 `InventoryScreenAlphaMixin` 的放行白名单只含原版生存/创造背包。
- `InventoryScreenAlphaMixin` 抽出私有判定 `pasterdream$drawsInventoryBackground()`，把 `top.theillusivec4.curios.api.client.ICuriosScreen` 一并放行，两个注入器共用；Curios 为本仓库 required 依赖，直接引用 API 标记接口而不反射，也不扩大到所有 `AbstractContainerScreen`。
- `compileJava`、`runData`、`build` 通过，`runData` 写入 0 个文件。实机对照（临时把客户端运行目录指向 `build/run-client-ui-check`，验收后已还原）：同一视角下 `CuriosScreen` 背包面板区均值 RGB 未修复为 (171, 204, 231)，等于该贴图 alpha=157 像素的原始色 (171, 201, 227)，即 alpha 被忽略；修复后为 (126, 152, 168)，与创造模式背包的 (131, 149, 162) 一致。
- **建议提交信息：** `fix(mixin): 修复饰品栏背包底图透明通道丢失`

### 批次 133：补衍梦肥泥催熟与三种花染料配方（2026-11-17）

- 补做批次 132 的两条待办：作物交互补入衍梦肥泥催熟——未成熟时置为成熟、非创造模式消耗 1 个并播放骨粉音效；成熟作物不再响应肥料，仍按园艺钳/剪刀规则收割。
- 按上游 #1311 新增 `RecipeHelpers.dye` 与「三种染料合成配方」：魔星兰 → 黑色染料、洋桔梗 → 绿色染料、锦葵 → 粉色染料，配方 ID 为 `pasterdream:black_dye_from_ferraria_crispa` 等，均落在 `pasterdream` 命名空间；同时把 `glass_cup_drink` 的 Javadoc 更正为「饮料类合成配方」。
- `compileJava`、`compileGameTestJava`、`runData`、`build` 通过，`runData` 生成 3 条配方与 3 条进度、二次运行写入 0；`runGameTestServer` 224/224 通过，新增催熟与花染料配方用例。客户端手感与花田生成待实机验收。
- **建议提交信息：** `feat(crop): 补衍梦肥泥催熟与三种花染料配方`

### 批次 132：同步上游 #1308–#1313 修复性更改（2026-11-17）

- 作物收割承认 `FakePlayer`、剪刀标签与园艺钳，并新增默认开启的「采摘是否需要园艺钳或剪刀」配置，只有剪刀/园艺钳参与收割时才扣耐久；月亮小屋换用上游新结构（2 个战利品木桶、4 个融梦水晶箱），三条水晶箱抛出速度配置上界放宽并补单位。
- 芦苇杆造纸与染梦书桌的讲台配方改用 `pasterdream` 命名空间 ID，`runData` 清除了原先误落到 `minecraft` 的配方与进度，不再覆盖原版甘蔗造纸。狐火与撼地水晶改为普通实体：不再有血量、属性、经验与 AI，撼地水晶关闭推动、拾取与流体移动，到期爆炸伤害保留。
- `compileJava`、`runData`、`build` 通过；`runGameTestServer` 222/222 通过，新增实体状态、水晶到期伤害与作物收割用例，书桌 Gametest 的命名空间断言已翻转。#1311 的染料配方在本仓库无对应调用，已登记迁入约定与 `DREAM_FERTILIZER` 催熟缺失待办；客户端表现待实机验收。
- **建议提交信息：** `fix: 同步上游 #1308–#1313 修复性更改`

### 批次 131：同步上游 #1307 工具公共词条顺序（2026-11-13）

- 将工具系列公共词条注入点移至 NeoForge 自动属性生成前，修复公共词条显示在属性、物品 ID 和模组名之后的问题；保留五个系列的独立派发。
- 更新程序规范，新增顺序 GameTest；`runData`、`build` 通过，`runGameTestServer` 219/219 通过。客户端 tooltip 排版待实机验收。
- **建议提交信息：** `fix(mixin): 同步工具公共词条显示顺序修复`

### 批次 130：追忆物品栏开发者遗物（第一批，2026-11-13，T21 部分）

- 迁入起源之梦辞典、空容器、大侦探的放大镜、虹彩仙玉及其原始纹理、模型、语言与 Curios 槽位标签；补入「寻梦者的祈愿」幸运 +10 效果。
- 辞典恢复 SAN 与融梦能量各 10，空容器按来源概率恢复 20% SAN 或清空 SAN；放大镜提供挖掘与近战加成、防火及战技锁定；虹彩仙玉提供弹幕速度、易伤、无敌帧忽略与随机额外弹幕。保留来源配置默认弹幕池及黑名单。
- `runData`、`build` 通过；`runGameTestServer` 218/218 通过，覆盖消耗品、效果、槽位、放大镜空手倍率与仙玉属性。客户端视觉、额外弹幕和多人行为仍待实机验收。
- 重生之梦水晶的任务进度与界面链、调试工具的编辑器链尚未迁入，追忆栏仍未完整；保留后续批次待办。
- **建议提交信息：** `feat(memento): 搬运四件追忆开发者遗物`

### 批次 129：光影兼容模块（2026-11-13，T22 部分）

- 迁入 Forge 来源的 `ShaderCompat`、`ShaderPackConfig`、`ShaderBlockInjector` 与 `ShaderCompatEvents`：可选检测 Iris/Oculus，读取当前光影包 `block.properties`，并按原版草、树叶和藤蔓的 shader ID 注入本模组植物方块状态；加入默认开启的 `shaderBlockInjection` 客户端配置。
- 1.21.1 适配：客户端事件与方块注册对象改用 NeoForge 类型，草参考 ID 改为 `minecraft:short_grass`；对照用户提供的 Iris NeoForge 1.8.0 JAR 确认反射类及方法存在，不增加 Iris 编译或运行硬依赖。
- `compileJava`、`runData`、`build` 通过，`runData` 写入 2 个文件且无受跟踪资源变更；隔离服务端 `runGameTestServer` 217/217 通过。带 Iris 与实际光影包的客户端注入日志、植物动效及换包重载仍待实机验收。
- **建议提交信息：** `feat(client): 搬运光影兼容与植物动效注入`

### 批次 128：自定义画与加载界面 Tips（2026-11-13，T22 部分）

- 迁入 8 幅来源画作及原始贴图；按 1.21.1 数据驱动注册表生成画作 JSON 与 `minecraft:placeable` 标签，并补齐中英文标题、作者文本。
- 迁入来源 Tips 列表、增删/重置接口、加载界面渲染和默认开启的 `loading_gui_tips` 客户端配置；恢复 KubeJS `PasterDreamTips` 绑定。
- `compileJava`、`runData`、`build` 通过，`runGameTestServer` 217/217 通过。画作与 Tips 客户端视觉、KubeJS 实机调用待验收。
- **建议提交信息：** `feat(client): 搬运自定义画与加载界面 Tips`

### 批次 127：生命值 HUD 与两个内置资源包（2026-11-13，T22 部分）

- 迁入 `PlayerHealthHud`：可选的帕斯特之梦主题血条替换原版生命值行，保留受击闪烁、中毒/凋零条颜色、伤害吸收文本变黄与 85×10 边框/83 像素填充布局；当前/最大生命值按配置小数位格式化并去除末尾零。
- 迁入 `ModResourcePacks` 与 `resourcepacks/` 下 `pasterdream_legacy_textures`、`paster_vanilla_ui` 两个内置资源包（共 230 个文件），保留包 ID `pasterdream:legacy_textures` / `pasterdream:vanilla_ui` 与标题/描述语言键。
- 1.21.1 适配：GUI 由 Forge `IGuiOverlay`/`ForgeGui` 改为 `RegisterGuiLayersEvent` 的 `LayeredDraw.Layer`，通过 `RenderGuiLayerEvent.Pre` 隐藏原版 `minecraft:player_health`；资源包注册改用 NeoForge `AddPackFindersEvent`、`PackLocationInfo` 与 `BuiltInPackSource`；包格式由 15 升至 34。
- UI 包按 1.21.1 精灵图布局重建：从来源 `icons.png`、`widgets.png`、`bars.png`、`tabs.png`、`container/inventory.png` 裁出 138 张 HUD/按钮/热键栏/BOSS 条/创造栏标签/状态效果框等精灵，并复制 3 个按钮九宫格 `mcmeta`；原 1.20.1 整图保留但不再被 1.21.1 使用。
- 修复资源包背包透明通道：创造栏图标渲染后、主体底图绘制前重新启用标准 Alpha 混合；状态效果框独立绘制时也开启混合，并补齐 `container/inventory/effect_background_large` / `small` 精灵。快捷栏沿用原版已有的混合处理。
- 补入血条纹理 `assets/pasterdream/textures/screens/paster_widgets.png`；新增客户端配置 `pasterHealthHud`（默认 false）与 `pasterHealthHudDecimalPlaces`（0~2，默认 0）。血条绘制按来源补回混合、默认 Alpha 混合函数、关闭深度测试与渲染后恢复。
- `compileJava`、`runData`、`build` 通过，首次 `runData` 写入 2 个语言文件，复核写入 0 个文件；未运行 GameTest。独立开发客户端实际加载资源包后，创造背包半透明底图、状态效果粉色边框与快捷栏透明背景目视通过；生存背包及血条实机渲染待客户端验收。
- **建议提交信息：** `feat(client): 搬运生命值 HUD 与两个内置资源包`

### 批次 126：JEI 流体容器关系页（2026-11-13，T22 部分）

- 迁入 `compat/jei/fluidcontainerrelation/FluidContainerRecipeCategory`：JEI 中新增「流体容器绑定关系」分类，显示空容器 → 流体 → 装满容器；通用流体储罐只显示空容器与代表流体。
- 迁入 `helper/fluidcontainercapability/FluidContainerRelation` 与 `GetAllFluidContainerCapability`：扫描注册物品的 `Capabilities.FluidHandler.ITEM`，模拟填充生成专用映射，全部流体均可满容量填充时归为通用储罐。
- 1.21.1 适配：Forge `LazyOptional`/`ForgeCapabilities` 改为 NeoForge 直接能力查询，JEI 流体槽使用 `FluidStack.getComponentsPatch()`；补齐分类纹理与中英文标题。
- `compileJava`、`runData`、`build` 通过；JEI 分类的实际显示与客户端交互待实机验收。
- **建议提交信息：** `feat(jei): 搬运流体容器关系页`

### 批次 125：原版食物注入补齐与进度就绪检查（2026-11-13，T11-A/T11-B/T05 部分）

- 将 `jungle_spore` 与 `rice_cake` 恢复为来源的 `PasterDreamFoodItem`，不再降级为普通 `FoodProperties`；饱食度、饱和度与 SAN/融梦能量仍按来源数值。
- 补入眠椰系列：`slumber_palm`、`glass_cup_of_slumber_palm_club_soda`、`martini_glass_of_sea_salt_slumber_palm_beverage` 与容器 `martini_glass`；苏打水给予 60 秒速度 I，海盐眠椰饮给予 30 秒料理 I，均保留来源 `alwaysEdible`、营养与饱和度。
- 补齐 4 张物品纹理、5 条配方（马天尼杯互转/玻璃板合成、眠椰苏打水、海盐眠椰饮）、物品模型、双语名称与饮食物品栏顺序。
- 补齐来源缺失的 22 个进度：`story/tame_friendly_ghost`、`mark_wailing_shadow_ghost`、`sleep_with_shadow`、`shadow_blast_furnace_core`、`chocolate_000`、`wind_journey_treasure`、`christmas_tree_island`，以及染梦珍藏根与 14 个珍藏子进度；`get_star_wish_rod` 恢复挂在 `get_snow_vow_head` 下。
- 新增 `MarkWailingShadowGhostTrigger` 与 `OpenWindJourneyCrystalChestTrigger` 并注册；纷争预言卡标记悲泣尖啸怨魂、风之旅途融梦水晶箱开启时实际触发。`PlayerEvents` 补齐暗影高炉核心/黑金属粒的前置进度兜底检查。
- 补齐全部新增进度的中英文标题/描述，复制染梦珍藏根进度背景 `guiblock_dyedream_treasure.png`；逐项核对 654 个进度 JSON 的父节点、图标与语言键均存在。
- `compileJava`、`runData`、`build` 通过；`runData` 写入 40 个文件；`runGameTestServer` 215/215 通过，`GlassCupGameTests` 改为校验眠椰/马天尼杯配方存在。
- 暂缓：眠椰树方块、树木生成、果实首份获取与眠椰流体尚未迁入，眠椰系列在生存中仍不可获得；新增进度的实际游戏内触发与客户端视觉未实测。

### 批次 124：风之旅途结构生成（2026-11-13，T16-B/T19-A/T19-B 部分）

- 迁入风之旅途全部 34 个结构：风泊群岛 `wind_island`、`windmill_lodge`、`break_wind_knight_altar`、`windmoor_tree`、`wind_infested_stone_0/1`、`wind_pond`；迷梦云层 `big_bubbles_6/7`、`bocchi_0/1`、`break_wind_curtain`、`hakurei_reimu`、`hot_air_balloon_0~7`、`small_ballon_0~10`、`christmas_tree_island`、`windbell_cage`，补齐静态 structure/template_pool JSON 与 32 个 NBT；`big_bubbles_6/7` 按来源复用 `big_bubbles_2/1` 模板。
- 保持来源 spacing/separation/salt、生成阶段、高度范围、地形适应、群系限制与结构集权重；`christmas_tree_island` 与 `windbell_cage` 继续使用 `wind_journey_structures` 共享集（spacing 52 / separation 26，权重 1:2），其余为独立随机分布。
- 使用 `scripts/convert_structure_item_nbt.py` 转换风之旅途 NBT 中 25 个 1.20.1 物品堆叠；脚本补充 `written_book` 到 `minecraft:written_book_content` 的转换，并顺带修正破风骑士祭坛内旧笔记的 `Count`/`tag` 格式。
- 结构 JSON 的 `biomes` 按 1.21 要求改为列表；静态模板池保留来源 `block_ignore` 处理器与模板引用。
- 补齐 82 个已注册结构的双语 `structure.pasterdream.*` 翻译键，包含此前缺失的阴影结构、风之旅途结构与气泡/云层装饰结构。
- `compileJava`、`runData`、`compileGameTestJava`、`build` 通过；`runData` 写入风之旅途独立结构集与共享集；`runGameTestServer` 215/215 通过，新增 `windJourneyStructuresLoad` 覆盖 34 个结构注册、模板池、NBT 加载与共享集条目。
- 待验收：真实风之旅途世界中的浮空岛/云层结构分布、结构间重叠与高度贴合、箱内实体/物品/告示牌/横幅表现，以及多人环境下的结构生成。
- **建议提交信息：** `feat(worldgen): 搬运风之旅途结构生成`

### 批次 123：调试栏顺序、圣诞树岛战利品与设备栏熔梦水晶箱（2026-11-13，T16-B/T19/T20 部分）

- 调试创造栏按来源 `pasterdream_dream_debug_tab` 重排：移除来源调试栏没有的暗影高炉、暗影地牢仓库与沙漠结构战利品生成器；恢复熔梦水晶箱重置工具本体及普通/稀有/传奇三种 NBT 变体，并校正暗影涡旋、暗影火盆与刷怪蛋顺序。
- 迁入 `chests/christmas_tree_chest`（原 `loots_relic_8`）到风之旅途结构战利品 Provider，保留 5 个奖池的权重、数量区间与空条目；调试栏补对应战利品生成器条目。
- 设备创造栏的熔梦水晶箱改回无 NBT 的普通物品，与来源一致；带战利品列表的水晶箱仅保留在调试栏。
- 1.21.1 适配：附魔书池改用 `EnchantWithLevelsFunction.enchantWithLevels(registries, ...)`，不再调用 1.20.1 的 `allowTreasure()`。
- `compileJava`、`runData`、`compileGameTestJava`、`build` 通过，`runData` 写入 1 张战利品表；`runGameTestServer` 214/214 通过，`WindJourneyDimensionGameTests` 增加圣诞树岛战利品表可加载断言。圣诞树岛结构本身尚未迁入，实际箱子投放待结构批次。
- **建议提交信息：** `feat(worldgen): 搬运圣诞树岛战利品并整理调试栏`

### 批次 122：暗影提灯（2026-11-13，T20 部分）

- 迁入 `shadow_hand_lantern` 暗影提灯：手持时提供 1.2 SAN/分钟光环；右键消耗 1 SAN，播放音效与末地烛/灰烬粒子，使直径 15 格内 `#pasterdream:shadow_mob` 获得 15 秒易伤 II（20%），冷却 8 秒。
- 迁入 GeckoLib 物品模型、待机/使用动画与第一人称手臂覆盖渲染；物品不可堆叠，配方保持链 + 玻璃板 + 粉色蜡烛 + 黑金属粒/锭。
- 设备创造栏按来源 `paster_tab_5` 顺序将暗影提灯置于「暮影之笼」与「暮影长床」之间。
- 1.21.1 适配：`createGeoRenderer`/`GeoRenderProvider`、GeckoLib 4.9.2 包路径与 `int renderColor`、`RenderUtil`、`AbstractClientPlayer#getSkin().texture()`、`ModelPart#render` 颜色整数（新增 `util/AnimUtils`）、`AttributeModifier` 改 `ResourceLocation` + `EquipmentSlotGroup.HAND`、`geckoAnim` 改 `CustomData`、`appendHoverText` 使用 `Item.TooltipContext`；玻璃板标签由 `forge:glass_panes` 改为 `c:glass_panes`。第一人称手臂覆盖改为逐次获取皮肤/物品缓冲并在绘制后恢复，避免 1.21 `BufferSource` 切换 RenderType 触发 `BufferBuilder: Not building!`。
- `compileJava`、`runData`、`build`、`compileGameTestJava` 通过；`runGameTestServer` 214/214 通过，新增 `ShadowHandLanternGameTests` 覆盖注册、属性、配方、冷却与 SAN 消耗、暗影生物易伤。客户端模型/动画/手臂渲染、粒子与音效待游戏内验收。
- **建议提交信息：** `feat(item): 搬运暗影提灯`

### 批次 121：破风骑士与破风骑士祭坛召唤（2026-11-13，T19-C/T20 部分）

- 迁入破风骑士实体：250 生命/20 护甲/20 攻击、突刺普攻与回旋斩战技、半血狂暴召唤四朵高压雷云、落雷概率、受击无敌与 BOSS 限伤；补齐刷怪蛋、实体战利品、`wind_knight_minion` 标签与自定义 BOSS 条。
- 迁入破风骑士祭坛方块/方块实体/物品/GeckoLib 渲染与五阶段组装流程，接入雷电药剂瓶投掷唤醒、召唤触发器与 `story/summon_wind_knight` → `story/pulse_wind_runner_crystal` 进度。
- 迁入祭坛结构 JSON/模板池/NBT 与结构集；`biomes` 改为 1.21 可解析的列表写法，生成群系与范围保持风泊群岛。
- 1.21.1 适配：GeckoLib 4.9.2 包路径与 `int renderColor`、`SynchedEntityData.Builder`、`getDefaultDimensions`、实体覆写 `isWithinMeleeAttackRange`、`BaseEntityBlock#codec`、`useItemOn`/`useWithoutItem`、NeoForge `IClientItemExtensions`；移除 `setKnockback`/`setPierceLevel` 与 `getMobType`。
- 整理设备创造栏：按来源 `paster_tab_5` 顺序排列，破风骑士祭坛归入来源位置，移除风之旅途栏中重复的风骑士旗帜/祭坛条目。
- `compileJava`、`runData`、`build`、`compileGameTestJava` 通过；`runGameTestServer` 213/213 通过，新增 `WindKnightGameTests` 覆盖注册、祭坛结构与群系、战利品/标签/进度及实际召唤。客户端模型/动画/BOSS 条、祭坛组装与投掷唤醒、真实世界结构生成与多人战斗待游戏内验收。
- **建议提交信息：** `feat(entity): 搬运破风骑士与破风骑士祭坛召唤`

### 批次 120：风之旅途宝箱与水晶箱战利品及调试栏整理（2026-11-13，T16-B/T17/T19-B 部分）

- 迁入 `chests/wind_journey_chest`、`chests/wind_journey_chest_rare` 两张风之旅途结构宝箱表，补齐 `fluffy_wind_church` 等结构此前引用但为空的战利品。
- 迁入 `chests/melt_dream_crystal_chest_wind_journey_world_normal/rare/legend` 三张水晶箱表，传奇池恢复来源 5% 逐梦列车车票奖池；新增 `ModLootTables` 常量与 `ModWindJourneyStructureLootTablesProvider`。
- 1.21.1 适配：饰品等级 NBT 改 `SetComponentsFunction` + `CustomData`；附魔改为 `enchantWithLevels(registries, ...)` 的 `#minecraft:on_random_loot`；烟花 NBT 改 `DataComponents.FIREWORKS` + `FireworkExplosion`。
- 整理调试创造栏：补齐已迁入战利品表的 `LOOT_GENERATOR` NBT 变体，并补风之旅途水晶箱 NBT 与重置工具变体。
- `compileJava`、`runData`、`build` 通过，`runData` 写入 5 张战利品表 JSON；`runGameTestServer` 208/208 通过，`WindJourneyDimensionGameTests` 新增战利品表加载与三表 NBT 断言。实际结构投放、水晶箱抽取与烟花客户端表现待游戏内验收。
- **建议提交信息：** `feat(loot): 搬运风之旅途宝箱与水晶箱战利品`

### 批次 119：风之旅途生物与 story 进度链（2026-11-12，T19-A/T19-B/T05 部分）

- 迁入骨翼、余烬骨翼、雷云、高压雷云、水母五种风之旅途生物，以及骨翼火球与雷云落雷射弹；保留来源属性、AI、飞行盘旋、落雷概率、水母繁殖、死亡时长、战利品与群系生成权重/生成预算。骨翼与余烬骨翼改由 `#minecraft:undead` 实体标签承接亡灵语义，高压雷云死亡闪电复用雷电药剂瓶效果。
- 迁入 `lightning` 粒子与 `thundercloud_attack`、`bone_wing_fire_ball` 音效；补齐五种刷怪蛋、创造栏条目、中英文本与 `fireball_projectile.png` 等来源纹理、GEO/动画资源。
- 补齐风之旅途 story 进度链 15 个节点：进入维度、凝风为露、晶凝风行、瓶中萤火、海的繁衍、风凝有形 → 凝风锻铁 → 瓶中闪电、展翅的无翼鸟 → 乘风破幕 → 百幕穿行 → Angel Beats!、风伴你而行 → 逆风而进/御风而行；新增 `wind_tailwind_icon`、`wind_deadwind_icon` 隐藏图标物品。破风骑士召唤、风之旅途水晶箱与圣诞树岛笔记节点因前置未迁入而保留待办。
- 1.21.1 适配：GeckoLib 4.9.2 包路径与 `int renderColor` 渲染签名、`SynchedEntityData.Builder`、`getDefaultDimensions`、`RegisterSpawnPlacementsEvent`、`AbstractArrow` 新构造器与 `igniteForSeconds`；1.21 移除 `setKnockback`/`setPierceLevel` 后保留基础伤害与命中逻辑；`AbstractThundercloudEntity` 的破风骑士引用暂改为通用 `Mob`。
- 修复 `runClient` 进入风之旅途时的区块生成崩溃：原版 `Feature.LAKE` 的结冰查询会访问生成区域外区块，风之旅途水池改用既有 `DyedreamWaterPoolFeature`，保留水体/围边算法并移除跨区块查询。
- 补灯影之下/风之旅途天空与雾色：迁入 `LevelRendererMixin`（灯影黑色雨、取消纯色天空与风之旅途云层）与 `FogRendererMixin`（灯影纯黑清屏/雾色、风之旅途青色清屏/雾色），新增 `LampShadowDimensionEffects` 并注册。
- 补云雾效果表现：新增 `CloudMistHud` 全屏云雾叠加层并注册；`CloudMistEffect` 在 HUD 与物品栏状态效果列表中隐藏，避免与自定义云雾 GUI 重复显示。
- 补小石堆破坏粒子纹理 `textures/block/cang_qing_yan_.png`，修复 `small_stone_spirit_block` 模型 `particle` 贴图缺失。
- `compileJava`、`runData`、`build` 通过，`runGameTestServer` 207/207 通过；新增 `WindCreatureGameTests` 覆盖生物/射弹/刷怪蛋注册、亡灵标签、风泊群岛生成项与 15 条 story 进度，`WindJourneyDimensionGameTests` 增加水池 Feature 断言。客户端模型动画、粒子/音效、实际生成与多人同步待游戏内验收。
- **建议提交信息：** `feat(entity): 搬运风之旅途生物与 story 进度链`

### 批次 118：暗影合金/琴雨梦护甲与漂泊旅者等装备（2026-11-12，T13/T15/T19 部分）

- 迁入暗影合金四件套与 `ShadowAlloyArmorHandler`：单件 +4 最大生命 / +1 攻击力，满套挂「黑暗守望」，灯影武器 +30%、受击反击 10s（30s 冷却）、暗影生物攻击 50% 最终减伤；GeckoLib GEO 模型、动画、护甲纹理与效果图标按来源复制。
- 迁入琴雨梦四件套与 `QymArmorEvents`：猫耳冻结 SAN 上限、衬衫飞行、裙摆维持融梦能量上限、靴子清除瞬身/战技冷却；满套 80% 减伤、攻击附目标当前生命 5% 魔法伤害、梦境中免疫伤害，套装效果接入 `CurioPassiveHandler`。
- 迁入 `dream_harp_of_wanderer`（漂泊旅者的染梦竖琴）及效果/音效：3 融梦能量、30 秒冷却、附近玩家最大生命 +4、移速 +10%、+4.8 理智光环并回复 5 SAN；`paper_plane`、`wind_vane`、`moss_phantom_membrane`、`light_moss_phantom_membrane` 及其 Curio 标签与交互。
- 配方按来源补齐：暗影合金四件套走武器工坊（钛金底 + 暗影合金锭×4 + 噩梦燃料×8 + 阴影×4 + 黑曜石×4），风向标与两张幻翼膜走工作台；琴雨梦套装来源无配方，保持无配方。
- 1.21.1 适配：护甲属性改 `ItemAttributeModifiers` + `EquipmentSlotGroup`，属性修饰符 ID 改 `ResourceLocation`；事件改 `PlayerTickEvent.Post`/`LivingIncomingDamageEvent`/`LivingDamageEvent.Pre`/`LivingEquipmentChangeEvent`；效果属性改 `AttributeMap`，`MobEffectInstance` 直接使用 `Holder`；`appendHoverText` 使用 `Item.TooltipContext`。
- `compileJava`、`runData`、`build` 通过，`runGameTestServer` 204/204 通过；新增 `DreamGearGameTests` 覆盖护甲数值/标签/套装、Curio 标签、效果注册与七条配方。待办：纸飞机/风向标的风之旅途结构宝箱获取、染梦竖琴的列车漂泊旅者获取与对应进度仍随结构/NPC 批次保留；客户端 GEO 护甲、竖琴音效与多人同步待游戏内验收。
- **建议提交信息：** `feat(item): 搬运暗影合金与琴雨梦护甲及漂泊旅者等装备`

### 批次 117：风之旅途维度、风向机制与地物生成（2026-11-12，T19 部分）

- 迁入 `wind_journey_world` 维度类型、末地浮空岛噪声设置、`wind_moor_archipelago` / `misty_dream_cloud_layer` 双群系与 LevelStem，并登记 `wind_journey_world_biome` 标签。
- 迁入 `WindDirectionHandler` 与 `pasterdreamWindDirection` 游戏规则：每昼夜随机换向、播报、风向钟声/羽毛粒子、微风延迟、顺逆风判定、云雾标记、虚空回主世界与高空迷梦进入，以及顺/逆风累计飞行距离的 `wind_flight` 进度触发；事件改为 NeoForge `LevelTickEvent.Post`/`PlayerTickEvent.Post` 显式注册。
- 迁入顺风/逆风/云雾效果（移速、瞬身距离与冷却属性、互斥与不可治愈语义）及 `breeze_wind` 音效；顺逆风属性按 1.21 `AttributeMap` 与 `ResourceLocation` 修饰符适配。
- 迁入凝风矿、破风骑士水晶矿与 11 种风之旅途地物（云团高低位、水池、小石子、萤火虫巢、苍青苔石团块、小石堆、茸毛苔、斩风风羽草、风岛芦苇）及群系注入；按用户确认不涉及结构生成。
- 同批注册风骑士旗帜 Curio 作为风向机制依赖；其生存获取随结构/奖励批次接通。骨翼、灰骨翼、雷云、高压雷云、水母尚未迁入，群系生成项暂不登记。
- `runData`、`build` 通过，`runGameTestServer` 201/201 通过；新增 `WindJourneyDimensionGameTests` 覆盖维度/群系/地物注册与注入、风向规则及效果注册。维度实际生成、客户端天空/雾效、风向玩法与多人同步待游戏内验收。
- **建议提交信息：** `feat(worldgen): 搬运风之旅途维度、风向机制与地物生成`

### 批次 116：暗影果冻核验与食物物品栏顺序对齐（2026-11-12，T20 修复）

- 核验 `shadow_jelly` 已在批次 106 随灯影之下战利品表迁入：`ShadowJellyItem`（SAN -10、食用后暗影难度随机 ±1 并提示）、物品注册、研钵配方、模型/来源贴图、双语词条与创造栏条目均与来源一致，本批无需重复搬运。
- 按来源重排饮食物品栏：`MOLTEN_GOLD_APPLE`、`ENCHANTED_MOLTEN_GOLD_APPLE` 前移至 `DYEDREAM_FRUIT` 之后，`FIG`、`GLASS_JAR_OF_DREAM_JUICE` 随后；来源中未迁入的眠椰/马天尼系列条目继续跳过。
- `build` 通过。创造栏顺序属客户端展示，待游戏内确认。
- **建议提交信息：** `feat(item): 核验暗影果冻并按源重排食物物品栏`

### 批次 115：修复暗影符文塔与暗影魔法球死亡动画（2026-11-12，T20 修复）

- 补回 `ShadowTuneTotemRenderer` 与 `ShadowMagicballRenderer` 遗漏的来源覆写 `getDeathMaxRotation(...) -> 0.0F`，避免 GeckoLib 默认 90° 死亡旋转干扰自定义 `death` 动画；同时恢复来源 `preRender` 的 `scaleWidth/scaleHeight = 1` 设置。
- `build` 通过。死亡动画属客户端视觉，待游戏内击杀确认；未新增服务端 GameTest。
- **建议提交信息：** `fix(entity): 修复暗影符文塔与暗影魔法球死亡动画`

### 批次 114：亚伦柯斯竞技场维度与结构（2026-11-12，T20 部分）

- 迁入 `aaroncos_arena_world` 维度类型、末地虚空噪声设置、单群系 `aaroncos_arena` 与 LevelStem；迁入 `AaroncosArenaWorldDimension` / `AaroncosArenaTeleporter`，保留眼方块判定、强制加载区块放置竞技场结构、战斗时间轴（恐惧喙波次、音乐循环、双手死亡奖励箱/进度/移除暗影窥视）、手箱开启后的 410 tick 离场倒计时与游戏模式恢复。
- 迁入 `aaroncos_eye` / `aaroncos_hand_chest` / `aaroncos_arena_portals` 方块、方块实体、GeckoLib 模型/动画/渲染与展示物品，以及 `aaroncos_arena_create` 创造物品；补齐方块状态、模型、纹理、战利品、创造栏与中英文本。
- 复制 `aaroncos_arena.nbt` 与 `aaroncos_arena_portal.nbt`，登记灯影之下菌索荒原的 `aaroncos_arena_portal` 结构、结构集与模板池；保留 `aaroncos_arena` 的 SAN 群系速率。
- 1.21.1 适配：`BaseEntityBlock#codec`、`useWithoutItem`、`HolderLookup.Provider` 方块实体存取、GeckoLib 4.9.2 包路径；事件改为 `PasterDreamMod` 显式注册，维度特殊效果拆到客户端类；出生点改用 `getSpawnPos`。
- `runData`、`build` 通过，`runGameTestServer` 196/196 通过。竞技场实际进入/结构放置/BOSS 战/离场/音乐/客户端雾效与多人行为待游戏内验收；帕秋莉手册未迁入。
- **建议提交信息：** `feat(worldgen): 搬运亚伦柯斯竞技场维度与结构`

### 批次 113：亚伦柯斯之触 BOSS 实体与技能依赖（2026-11-12，T20 部分）

- 迁入亚伦柯斯之触左手/右手两个 GeckoLib BOSS 实体，保留来源阶段状态机、冲刺/重击/剑气与魔法球/漩涡/音图腾技能、姐妹协同、血锁召唤暗影之手和限伤接线；同步迁入 `shadow_magicball`、`shadow_tune_totem` 召唤物及刷怪蛋。
- 迁入 `shadow_vortex` 方块/方块实体/展示物品、`restrainmove_block` 与 `oppression` 效果、`aaroncos_spawn` / `stone_crack` / `stone_shatter` / `shadow_vortex` 音效，以及 `BossDamageLimiter` / `BossLimitProfile` / `BossLimitValues` 与全局/亚伦柯斯限伤配置。
- 迁入亚伦柯斯之触组合 BOSS 条：左右手血量分栏、共享纵向槽位布局，随原版 BOSS 条数量下移；新增 `BossHealthOverlay.events` 访问转换与 4 张 GUI 纹理。
- 补齐实体标签（`shadow_mob`、`special_entity_tag`）、调试物品栏、刷怪蛋模型、`shadow_vortex` 战利品与中英文本；GeckoLib 模型、动画、纹理与音效资源按来源复制。
- 1.21.1 适配：数据同步 Builder、四参数 `finalizeSpawn`、`canChangeDimensions(Level, Level)`、`getBaseExperienceReward`、`ResourceLocation` 属性修饰符、`Holder<MobEffect>`、`BaseEntityBlock#codec` 与 `HolderLookup.Provider` 方块实体存取；`MeleeAttackGoal` 攻击距离改由实体 `isWithinMeleeAttackRange` 保留。
- `compileJava`、`runData` 与 `build` 通过。实体 AI/技能、客户端模型动画、音效、BOSS 条视觉与多人行为待游戏内验收；竞技场维度/结构/传送、眼方块/手箱/传送门、BOSS 音乐与 `defeat_aaroncos` 进度仍待后续批次。
- **建议提交信息：** `feat(entity): 搬运亚伦柯斯之触双手 BOSS 实体与技能依赖`

### 批次 112：同步上游工具被动重构与存储袋包名修正（2026-11-12）

- 同步上游 #1304（`c7032082`）：新增 `ItemSharedToolBehaviorMixin` 与 `ItemStackSharedToolBehaviorMixin`，按独立 `instanceof` 分支统一派发融梦水晶、炙焰金、暗影合金、影蚀、萦风合金系列的背包 tick 与公共词条；移除各工具类中重复的 `inventoryTick` / `appendHoverText` override。
- 萦风合金被动统一为「疾风过境」：新增 `FluffyWindAlloyAttackHandler`，剑/斧/镐/锹/锄与萦风雷矛统一获得攻击附雷、移速加伤/挖掘、风卷掉落；雷矛保留激流突进自旋的 `applyDashOnTouch` 专属分支。炙焰金剑补齐右键急迫。
- 同步上游 #1305（`5417a76`）：存储袋包名已为全小写 `world/item/storagebag/`，本批仅修正文档中的旧路径引用。
- 1.21.1 适配：`appendHoverText` / `getTooltipLines` 使用 `Item.TooltipContext`；攻击被动改用 `LivingIncomingDamageEvent`；Helper 可见性与 mixin 注入签名按 NeoForge 调整。
- 语言文件合并各工具单独被动描述为统一词条，删除 `tooltip.pasterdreammod.molten_gold_sword.1`、剑/斧/雷矛的独立被动词条。`compileJava` 与 `runData`（写入 2 个语言文件）通过，`build` 通过，`runGameTestServer` 196/196 通过；客户端词条顺序与多人表现待游戏内验收。

- **建议提交信息：** `refactor(tools): 同步上游工具被动统一派发与存储袋包名修正`

### 批次 111：修复骨粉催生粒子过少（2026-11-11）

- 1.21 的 `BoneMealItem#addGrowthParticles` 改为按 `BonemealableBlock#getType` 选择粒子：默认 `GROWER` 只在单个方块内生成 15 个粒子，而 1.20.1 会在半径 3 范围内生成同样数量。为 `DyedreamGrassBlock`、`CyanMossStoneBlock` 与 `ShadowNyliumBlock` 覆写 `getType()` 返回 `NEIGHBOR_SPREADER`，恢复扩散到 7×7 地表的粒子表现，与原版草方块一致。
- 1.21 移除了 `2005`（`PARTICLES_BONE_MEAL`）事件，`DreamFertilizerItem` 与 `FluffyWindAlloyToolHelper#ripenCrops` 原来的 `levelEvent(2005, ...)` 已不再产生粒子。两者改用 `ServerLevel#sendParticles` 发送 15 个 `HAPPY_VILLAGER` 粒子：衍梦肥泥保留 1.20.1 的约 7×7 扩散与无音效行为，萦风合金锄则把粒子收束到每个被催熟方块内，整体只覆盖实际催熟的 3×3 区域。
- `compileJava` 通过；粒子属客户端视觉，待游戏内验收。

- **建议提交信息：** `fix(block): 修复骨粉催生粒子过少`

### 批次 110：暗影地牢结构与世界生成（2026-11-11，T18-B 部分）

- 复制暗影地牢 22 个结构 NBT（入口、起始房、单层框架、床、火盆、甲虫、挖掘层、夹层、书库、无名、宝库），使用 `scripts/convert_structure_item_nbt.py` 转换 84 个物品栈（含 5 本笔记的内容组件），补齐 `shadow_dungeon` 入口 jigsaw、`can_shadow_dungeon_spawn_biome` 群系标签与 `shadow_structures` 共享结构集条目。
- 迁入 `shadow_brazier` 方块/方块实体/GeckoLib 模型与动画/渲染/展示物品/资源：需阴影蜡烛点燃，按来源 20 tick 节奏完成 120 次仪式并生成恐惧喙、暗影之手与阴影傀儡，结束时掉落暗影地牢钥匙与黑金属粒。
- 补注册地牢模板引用的 `clay_pot` 陶罐，保留精准采集掉自身、否则按来源战利品池抽取的掉落；补齐方块状态、物品模型、标签、创造栏与中英文本。
- 1.21.1 适配：`BaseEntityBlock#codec`、`useWithoutItem`、方块实体 `HolderLookup.Provider` 存取、GeckoLib 4.9.2 包路径与 `createGeoRenderer`、`hasSilkTouch()` 战利品判断。
- 新增 `ShadowDungeonGameTests` 覆盖方块/方块实体注册、火盆点燃与更新标签、入口结构/结构集/22 个模板、全部 NBT 物品组件（含入口与书库笔记内容）及宝库战利品表加载。`runData` 写入 12 个文件，`build` 通过，`runGameTestServer` 196/196 通过。
- 暗影地牢实际生成与传送、火盆仪式刷怪/掉落、结构内无名与机关、客户端表现及多人行为仍待游戏内验收。

- **建议提交信息：** `feat(block): 搬运暗影地牢结构与世界生成`

### 批次 109：无名实体、暗影入侵与灯与影的选择（2026-11-11，T18-B / T21 部分）

- 迁入 `nameless` 无名实体、GeckoLib 模型/动画/纹理、刷怪蛋与对话状态机；保留两段对话行数、40 tick 行间隔、首次对话结束传送回主世界并施加暗影窥视，第二次对话后解锁灯与影选择。
- 迁入 `shadow_spyon` 暗影窥视效果与暗影入侵处理器：主世界夜晚低亮度按概率触发，附近生成虚弱恐惧喙与暗影之手；清空或天亮后结束、授予 `story/shadow_intrude_complete` 并授权移除效果。
- 迁入灯与影选择：暮影长床在灯影之下按第二次对话/选择进度打开 `shadow_select_end` 界面；选影授予 `story/talent_shadow` 与凝影剑柄，选灯授予 `story/talent_light` 与白厄水晶，并播放来源分支对话。
- 补 `story/shadow_choice`、`story/talent_light`、`story/talent_shadow` 进度、无名/天赋物品/效果/界面双语文本与 `talent_light`、`talent_shadow`、无名刷怪蛋模型及创造栏条目；复制来源界面、按钮、效果图标、实体/物品纹理和 Geo/动画资源。
- 1.21.1 适配：实体改用 `SynchedEntityData.Builder`、`getDefaultDimensions` 与 NeoForge 实体注册；效果改用 `shouldApplyEffectTickThisTick`/`fillEffectCures`；`ImageButton` 的旧图集构造改为自绘两帧按钮；网络包改为 `CustomPacketPayload`/`StreamCodec`；菜单打开改用 `SimpleMenuProvider`。
- 新增 `NamelessShadowStoryGameTests` 覆盖实体/效果/菜单/物品/刷怪蛋与进度加载。`runData` 写入 8 个文件，`build` 通过，`runGameTestServer` 192/192 通过（首轮既有 `catalystdurabilityconsumedinrecipebiome` 波动失败，重跑通过）。
- 无名实际对话、暗影入侵刷怪、灯与影界面按钮与分支对话、GeckoLib 客户端表现及多人同步仍待游戏内验收；暗影地牢结构生成与无名在结构中的放置留待后续批次。

- **建议提交信息：** `feat(entity): 搬运无名实体、暗影入侵与灯与影的选择`

### 批次 108：暗影地牢门扉/屏障/传送门核心与构梦刷怪笼（2026-11-11，T18-B 部分）

- 迁入 `broken_shadow_dungeon_portal`、`shadow_dungeon_portal`（GeckoLib 方块实体/动画/显示物品）、`shadow_dungeon_gate`、`shadow_dungeon_barrier`（3×3 多方块）、`dream_spawner`、`faded_dream_spawner`；补齐 `_3x3Part` 多方块属性与横/纵部件定位 helper、`shadow_door` 音效。
- 传送门按来源恢复修复（影灯+黑金属+知识进度）、创造模式修复、传送倒计时、暗影地牢结构生成/传送与 `story/shadow_prisoner` 授予；接入 `ModShadowDungeonStructureSet` 结构清单与 `shadow_dungeon_mob` 实体标签。
- 补齐进度定义 `story/shadow_prisoner`、`story/shadow_intrude_complete`、`story/shadow_npc_first_dialogue`、`story/shadow_npc_second_dialogue`、`story/defeat_aaroncos`；其中无名对话/暗影入侵/亚伦柯斯相关获取方式依赖后续批次，先保留读取与定义。
- 1.21.1 适配：`BaseEntityBlock#codec`、`DirectionalBlock#codec`、`useItemOn`/`useWithoutItem` 拆分、`AdvancementHolder` 替代 `Advancement`、`SpawnEggItem#getType(ItemStack)`、方块实体 `loadAdditional/saveAdditional` 与 `HolderLookup.Provider`、GeckoLib `createGeoRenderer`、`PacketDistributor#sendToPlayersTrackingChunk`。
- `runData` 写入 14 个文件；`build` 通过；`runGameTestServer` 190/190 通过。暗影地牢结构 NBT 模板、地牢实际生成、门钥匙生存获取与客户端视觉待后续批次与游戏内验收。
- **建议提交信息：** `feat(block): 迁入暗影地牢传送门与构梦刷怪笼`

### 批次 107：白厄剑、白厄花胸针、影刃与堕落者之印（2026-11-11，T13-B / T18-B 部分）

- 迁入 `white_sword` 与 `shadow_sword`：保留来源注册 ID、1771 耐久、剑雨波次/追踪/反弹/沉默/束缚、影刃 SAN 波动与噩梦斩数值；主动战技分别消耗融梦能量或 SAN/生命并共用战技冷却。
- 迁入 `brooch_of_white_orchid` 与 `seal_of_the_corrupted`：胸针拦截环境负向 SAN 并强化白厄剑雨；封印免疫低 SAN 负面效果、使暗影生物中立，并让低 SAN 刷怪协助玩家。两者使用 `body` 槽与不可摧毁掉落物。
- 补入两把剑的武器工坊配方、剑/战技冷却/暮影武器/纯粹恐惧原料标签、白厄剑雨实体与粒子、两条音效和四张物品纹理。
- 1.21.1 适配：动态 SAN 攻速改用 `getDefaultAttributeModifiers(ItemStack)`；自定义数据改 `DataComponents.CUSTOM_DATA`；伤害事件改 `LivingIncomingDamageEvent`；实体移除 Forge 生成包构造器；Curios 改用 `Holder<Attribute>` 与 `ResourceLocation`。
- 新增 `WhiteShadowGearGameTests` 覆盖物品属性/标签/槽位/配方/实体；`runData` 写入 12 个文件，`build` 通过，`runGameTestServer` 190/190 通过。
- 进度锁（`story/talent_light`、`story/talent_shadow`）与亚伦柯斯战利品仍待后续批次；当前两把剑保留来源门禁，胸针与堕落者之印暂仅创造模式获取。客户端粒子/音效、战技命中与饰品实战效果待游戏内验收。

### 批次 106：研究台、灯影之下战利品与剧情笔记道具（2026-11-11，T12-C / T18-B 部分）

- 迁入 `research_table` 方块、主/附属方块实体、菜单/屏幕、方块物品与双格放置逻辑；保留来源物品栏 6 格、250 mB 墨水槽、复制/研究按钮位置与快捷转移顺序。
- 迁入研究台复制/研究两类配方及序列化器：以 `c:ink` 墨水、`forge:pen` 羽毛笔、来源笔记/残页和羊皮纸为输入；复制消耗 2 倍源笔记数量并扣羽毛笔耐久，解析把未解析笔记转为剧情指南。
- 迁入剧情道具：`lamp_shadow_unknown_note`、`dyedream_unknown_note`、`lamp_shadow_story_guide`、`dyedream_story_guide`、三种 `dream_notes_*` 与寻梦者笔记阅读界面/内容注册；补齐 4 残页合成未解析笔记、研究台合成与墨水研究配方；已解析剧情指南在创造模式使用时不消耗；补灯影残页/研究台的玩家 tick 周期进度补触发。
- 迁入灯影之下战利品表：`chests/shadow_relic_chest_loot_common`、`chests/shadow_dungeon_repository_common/core/supply`，并补齐战利品依赖的 `shadow_jelly` 食物、研钵配方与贴图；结构模板中的 `research_table` 现可正常放置。
- 1.21.1 适配：配方改为 `RecipeInput` + `MapCodec`/`StreamCodec`，NBT 菜单数据改用 `HolderLookup.Provider`，方块交互改为 `useWithoutItem`/`useItemOn`，能力注册集中到 `ModCapabilities`；羽毛笔耐久改用 `setDamageValue`，来源 `forge:ink` 标签改为 `c:ink`。
- 新增 `ResearchTableGameTests`：复制/解析处理器、6 条配方加载、羽毛笔消耗与 4 张灯影战利品表加载断言。`runData` 写入 34 个文件后复跑写入 0，`build` 通过，`runGameTestServer` 187/187 通过；客户端 GUI/阅读界面、结构内实际放置、战利品箱实测与 JEI 分类仍待验收。

### 批次 105：同步泰拉剑气命中/纹理渲染与 lastHurt 访问变换（2026-11-11）

- 同步上游 #1301/#1302：泰拉剑气按本 tick 位移做扫掠 OBB 命中判定，候选 AABB 覆盖整段路径；侧轴统一由飞行方向与高度轴计算；仅在 `hurt()` 成功后记录命中并扣减穿透伤害，火焰附加改为命中后结算。
- 剑气刀光由程序化几何改为双面加亮纹理渲染，新增 `swordWave` RenderType 与 `terrasword_wave.png`；服务端拖尾粒子改为按 96 格内玩家逐个强制发送，避免 32 格发送限制与客户端粒子设置过滤导致远端消失。
- 同步 #1291：移除 `LivingEntityAccessor` Mixin，改用访问变换器暴露 `LivingEntity.lastHurt`；剧毒效果与本仓库既有回避结算同步改用该字段。
- 1.21.1 适配：纹理 RenderType 使用 `DefaultVertexFormat.POSITION_TEX_COLOR` 与 `position_tex_color` shader，顶点改为 `addVertex(...).setColor(...).setUv(...)`；粒子发送使用 `ServerLevel.sendParticles(player, particle, true, ...)`。
- `runData` 写入 0 个文件，`build` 通过，`runGameTestServer` 183/183 通过。客户端纹理方向/透明度、双面加亮、远端拖尾与高速边缘命中待游戏内验收。

### 批次 104：冰影战锤与撼地水晶链（2026-11-11，T18-B 部分）

- 迁入 `ice_shadow_hammer` 武器与 `shaking_crystal` 撼地水晶实体：保留来源 ID、835 耐久、4.0 攻速、9.0 攻击加成、防火、EPIC 品质、0.5 融梦能量消耗、80 tick 冷却、撼地水晶三段伤害与混乱/冻结/伤害吸收 I 行为。
- 接回批次 103 的 `ice_shadow_curio`：佩戴后战技额外释放两枚水晶，普通释放沿朝向前后排列，潜行释放则左右分布，延迟 10/20 tick 保持来源数值。
- 补齐实体注册/属性/渲染器、GeckoLib geo/animation/实体纹理、物品模型与纹理、创造装备栏、`minecraft:swords` 与技能冷却/灯影武器标签、`special_entity_tag`、双语 lang 和工坊配方。
- 1.21 适配：`SwordItem` 属性移入 `Item.Properties#attributes`，tooltip 使用 `Item.TooltipContext`，`SoundEvents.GENERIC_EXPLODE` 取 `.value()`；实体移除 Forge 生成包/自定义客户端工厂，改 `SynchedEntityData.Builder` 与 GeckoLib 4.9.2 包名，亡灵/节肢判定改用 `EntityTypeTags`，火焰附加改用 `igniteForSeconds`。
- `runData` 写入 8 个文件，`build` 通过，`runGameTestServer` 183/183 通过。客户端动画/渲染、战技伤害与饰品额外水晶、工坊实机合成待游戏内验收；箱子战利品表仍未迁入，暂无生存获取来源。

### 批次 103：灯影之下箱子战利品中的未移植饰品（2026-11-10，T13-B / T18-B 部分）

- 核对来源 `chests/shadow_relic_chest_loot_common`、`chests/shadow_dungeon_repository_common/core/supply`：其中尚未移植的饰品类为 `ice_shadow_curio`（冰影水晶）、`calais_spice_bottle`（卡莱的调料瓶）与 `ghost_face`（鬼魂之面）；`war_flag`、`shadow_breath` 此前已迁入。
- 迁入三个饰品原类与 `calais_spice_bottle` 状态效果：卡莱的调料瓶支持装备时维持卡莱料理、进食加速 40%、按饥饿值叠层、攻击消耗层数并随机触发正面效果/SAN 恢复/治疗/目标负面/闪避/doll 反馈；鬼魂之面复制远程射弹、20% 额外一发、弹射物无视无敌帧并带冷却；冰影水晶沿用来源饰品位与 tooltip。
- 接入 `CurioPassiveHandler` 的料理进食/攻击/完成事件与鬼魂之面 `ArrowLooseEvent`、`EntityJoinLevelEvent`、`LivingIncomingDamageEvent`、延迟生成逻辑；1.21 以 `ItemStack.parseOptional`/`ItemStack#save(Provider)` 处理弹药 NBT，以 `igniteForSeconds` 替代 `setSecondsOnFire`。鬼魂之面不再引用未移植的彩虹仙玉。
- 新增 `Config` 的卡莱调料瓶效果/权重/SAN/治疗/负面/闪避配置与鬼魂之面投掷物黑名单/复制冷却；注册物品与效果、创造模式饰品页、双语名称与 tooltip、`curios:curio`/`curios:head` 标签、物品模型和来源纹理。
- 冰影水晶的实际战技效果依赖冰影战锤与撼地水晶实体链，该链仍未迁入；`shadow_relic_chest_loot_common` 与 `shadow_dungeon_repository_*` 箱子战利品表本身也仍未迁入，因此当前饰品只在创造模式与后续战利品表补齐后自然获取。
- `runData` 与 `build` 通过，`runGameTestServer` 183/183 通过（首次运行因既有 `catalystdurabilityconsumedinrecipebiome` 间歇失败，重跑通过）；三个饰品与被动的事件内表现、鬼魂之面射弹复制、调料瓶层数与随机效果、`curios` 槽位实装待游戏内验收。

### 批次 102：灯影之下阴影古迹结构生成（2026-11-10，T18-B 部分）

- 接入共享结构集 `pasterdream:shadow_structures`：迁入 `shadownote_ruin_0/1/2`、`shadow_shelter`、`shadow_nest`、`shadow_fungus_house`、`shadow_foundry` 与 `shadow_underground_workroom`；按本次范围不迁入暗影地牢 `shadow_dungeon` 与亚伦克斯竞技场传送门 `aaroncos_arena_portal` 的生成。
- 复制 9 份结构 NBT（含地下工坊上/下层）与地下工坊静态 `structure`/`template_pool` JSON；新增 `can_shadownote_ruin_spawn_biome` 群系标签（`shadow_ruins`）。结构集沿用来源 `spacing=12`/`separation=6`，权重依次为 2/2/2/2/7/6/2/2，生成高度、地形适应与 salt 保持来源值。
- 使用 `scripts/convert_structure_item_nbt.py` 将模板内 11 处 1.20 物品栈转为 1.21 组件（笔记/蓝图 `content`、`Count`、`display.Name`），并补脚本对 `blue_print` 内容的映射。
- 普通结构由 `ModStructureProvider`/`ModStructureSetProvider`/`ModTemplatePoolProvider` 生成到 `worldgen/structure`、`worldgen/structure_set` 与 `worldgen/template_pool`；`shadow_underground_workroom` 使用静态 JSON，仅加入结构集并保留双向 jigsaw 连接。
- 结构模板引用的 `pasterdream:clay_pot` 与 `pasterdream:research_table` 尚未迁入，生成时相关位置留空；箱体引用的 `pasterdream:chests/shadow_relic_chest_loot_common` 战利品表也尚未迁入，当前箱体为空。上述内容待对应批次补齐。
- `runData` 写入 16 个文件，`build` 通过，`runGameTestServer` 183/183 通过；真实世界生成、结构与群系落点、地形贴合与多人环境表现待游戏内验收。

### 批次 101：暮影之笼、暮影长床与灯影之下入门进度（2026-11-10，T18-B / T05 部分）

- 迁入暮影之笼（`twilight_lantern`）方块、方块实体、GeckoLib 模型/动画/显示物品、交互/tick/波次事件与 `LampShadowWorldTeleporter`；迁入暮影长床（`shadow_bed`）主/附属方块、方块实体与入梦交互。
- 迁入 `twilight_lantern` 与 `shadow_world_spawn` 结构 NBT、一次性世界放置逻辑与 `/locate` 混入：下界基岩层上方随机放置暮影之笼并阻止结构内自然刷怪，灯影之下按高度放置出生点结构。
- 补「阅读侵染教堂-黑面 → 侵染教堂」「完成据点守卫 → 据点守卫」「进入灯影之下 → 灯影之下（挑战根进度，100 经验）」三条进度；阅读笔记时同时显示已放置暮影之笼坐标。
- 迁入 `shadow_ominous`、`shadow_music` 音效与 `bastionGuardGrantRadius` 配置；按来源顺序整理设备物品栏并补入暮影之笼/暮影长床。
- 1.21 适配方块 `codec()`、拆分后的 `useItemOn`/`useWithoutItem`、方块实体 Provider 重载、`SavedData.Factory`、`MobSpawnEvent.PositionCheck.Result.FAIL`、`ServerTickEvent.Post`、`LevelData#getSpawnPos()` 与 GeckoLib 4.9.2 显示物品接口；结构 NBT 笔记物品栈转换为组件。
- `runData` 写入 9 个文件，`build` 通过；`runGameTestServer` 182/182 通过，新增 2 项注册/配方/进度/结构模板断言。暮影长床第二段对话与影/光选择界面、`于影共眠` 进度留待后续批次；实际波次、客户端视觉、真实放置与多人同步待游戏内验收。

### 批次 100：融梦弓与自定义箭矢系列（2026-11-10，T14-B / T15-C 部分）

- 迁入箭矢行为体系 `world/entity/arrows/behavior/`：`ArrowBehavior`/`ArrowBehaviorProvider`/`ArrowBehaviors`，覆盖黑石、毒孢、碎梦、魔法、充能、混沌、爆破、凝风、露滴 9 种箭矢的伤害、粒子、追踪、范围伤害、爆炸、剧毒、氧气恢复与 SAN 结算。
- 迁入 9 种自定义箭实体与物品、5 种箭体纹理映射、10 个箭矢渲染器与实体/物品纹理；拖尾箭 `TrailArrowEntity` 接入行为钩子，武器强化后仍保留弹药特性与拾取物；补 `DispenserBlock.registerProjectileBehavior` 发射器支持。
- 迁入融梦弓 `MeltDreamBowItem` 与 `MeltDreamArrowEntity`：0.2 融梦能量换 +30% 魔法伤害、染梦世界普通箭矢免箭矢/耐久/能量、抢夺附魔、融梦修补；粉色三棱柱拖尾复用 `ArrowTrailRenderer`。
- 1.21 适配：`setKnockback` 移除后通过 `#minecraft:arrows` 实体标签与 `AbstractArrowAccessor` 的 `firedFromWeapon` 接入力量/冲击/火矢数据驱动附魔，凝风箭自带冲击由行为钩子补齐；`ArrowItem#createArrow` 补武器参数，发射器改用 `ProjectileItem#asProjectile`，`MobEffectInstance` 改收 `Holder`。
- 补齐配方与获取：融梦弓合成、黑石箭/凝风箭/毒孢箭/露滴箭合成、爆破/充能/碎梦/魔法箭工坊配方，以及此前暂缓的混沌箭暗影高炉配方；箭矢加入 `minecraft:arrows` 物品/实体标签与 `enchantable/bow`。
- `runData` 写入 36 个文件，`build` 通过，`runGameTestServer` 180/180 通过。客户端拖尾/箭体视觉、强化射击魔法伤害、各箭矢命中和发射器实机表现待游戏内验收。

### 批次 099：灯影之下维度与自然生成（2026-11-10，T18-B 部分）

- 迁入灯影之下维度类型、噪声设置与 LevelStem：固定午夜、无天光、环境光照 0.15、海平面 63，默认方块阴影石与默认流体影液，保留含水层并禁用地表熔岩湖/矿脉；通过多噪声源生成 `shadow_nylium_wastes`、`shadow_forest`、`shadow_ruins`、`shadow_ocean` 四个群系。
- 迁入群系地表规则与共享环境音/背景音乐 `shadow_biome`；按群系接入影芽、影蕨、矮菌索、菌索、影茎蕨、影菌、白厄花、阴影真菌树、残垣断壁、锁链柱（地表/海底）与古墓。
- 迁入 11 个 configured feature、13 个 placed feature、4 个自定义 Feature 类，以及 `shadow_hand`、`shadow_tomb_0/1`、`shadow_light_lantern` 四份地物 NBT 模板。
- 迁入群系标签与生物生成 biome modifier：菌索荒原的恐喙兽/暗影之手、阴影森林的厚翅甲虫双条目/友善怨魂、阴影古迹的怨魂三变体/阴影傀儡/暗影之手，保留来源权重与数量；Forge `forge:add_spawns` 改为 NeoForge `add_spawns` 数据。
- 迁入 `WorldGenRegionMixin`，在世界生成阶段禁止 `Block.dropResources` 生成掉落物，避免阴影森林蘑菇树替换两格高植物时触发线程异常；补齐 4 个灯影群系的中英翻译键。
- `runData` 写入 47 个文件并重新生成双语语言文件，`build` 通过，`runGameTestServer` 180/180 通过。阴影地牢等结构生成、客户端维度特效/黑色降雨与真实世界地形/刷怪分布仍待后续或游戏内验收。

### 批次 098：灯影之下生物（2026-11-10，T18-B 部分）

- 迁入灯影之下 7 种生物：阴影傀儡、怨魂、尖啸怨魂、悲泣尖啸怨魂、友善怨魂、厚翅甲虫与厚翅甲虫之母，以及怨魂远程攻击使用的尖啸波射弹、8 个刷怪蛋、实体战利品表、暗影生物标签、中英名称与调试物品栏条目。
- 保留来源行为：怨魂飞行接近与尖啸远程攻击、悲泣尖啸怨魂受击召唤同类且受暗影难度技能开关门控、友善怨魂用灵魂精华驯服与驻留/跟随切换、厚翅甲虫用白兰花驯服并掉落甲壳、厚翅甲虫之母浮空减伤与召唤虫群/强化虫群、阴影傀儡每 200 tick 触发范围冲击波并按难度缩放伤害；新增 `shadowGolemSkillDamage` 配置与 `ShadowDifficultyHelper` 查询方法。
- 迁入 GeckoLib 模型/动画/纹理、4 个自定义音效与 2 个粒子（阴影石、尖啸波）及其注册；怨魂系通过 `enumextensions.json` 注册独立 `MobCategory`（上限 30，避免占用原版怪物配额）。
- 1.21 适配移除 `PlayMessages`/`NetworkHooks`/`MobType`，`defineSynchedData` 改用 `SynchedEntityData.Builder`（怨魂纹理默认值改由 `getDefaultTexture()` 写入，修复悲泣尖啸怨魂刷怪蛋无法生成），`FollowOwnerGoal` 与 `finalizeSpawn` 参数更新，驯服事件改用 `EventHooks.onAnimalTame`，`FoodProperties#nutrition`、`AbstractArrow#getDefaultPickupItem` 与实体尺寸/乘客挂点按 1.21 API 重写。
- 灯影维度、群系与自然生成尚未迁入，本批未添加群系生成项；震晶与冰影锤技能链不在本批。`runData` 写入 32 个文件，`build` 通过，`runGameTestServer` 178/178 通过；客户端模型/动画/音效与实战 AI、多人同步待游戏内验收。

### 批次 097：染梦与风之旅途情景音乐（2026-11-10）

- 迁入上游客户端音乐注入：`SituationalMusicMixin` 在染梦世界与风之旅途覆盖 `Minecraft#getSituationalMusic`，优先使用当前群系背景音乐；`MusicManagerMixin` 将两个维度的下一曲间隔上限压到 1200 tick，并保留 35% 概率不干预。
- 按 1.21.1 平台差异用 `@Shadow` 读取 `MusicManager` 私有 `minecraft` 与 `nextSongDelay` 字段；群系音乐继续使用批次 069 已迁入的 `sweet_dream_music` / `snowfall_dream_music`。
- `build` 通过；客户端混入注入、曲目切换与实机听感待游戏内验收。

### 批次 096：染梦世界结构生成（2026-11-10）

- 迁入染梦世界 30 个结构及其 34 个模板池：染梦教堂、侵染教堂、水晶球、祈愿树、萦风教堂、花园解密三座、旅者小屋、生命水晶洞穴、染梦凉亭、浮空神庙、客栈、营地、穿云塔、空岛、世界树、实验室、逐梦列车、融梦涌泉井、粉顶蘑菇屋、月亮小屋、染梦/普通生态气泡与三个大气泡。
- 补 `dyedream_structures`、`dyedream_bubbles`、`moon_house`、`dyedream_worldtree_fixed` 四个结构集，保留来源间距、分离、盐、权重、高度、地形适应、排除区与世界树固定区块；新增 1.21 `pasterdream:fixed_chunk` 结构放置类型。
- 迁入来源 `ModStructureConfig`、`StructureGenerationConfig` 与 structure/structure_set/template_pool 三个 DataProvider；28 个结构的 JSON 改由 `runData` 生成，删除主资源中的同名静态副本，仅多部件与固定结构保留静态 JSON。
- 复制 33 个 NBT 模板，并用 `scripts/convert_structure_item_nbt.py` 转换 37 个物品栈：笔记内容组件、`minecraft:damage`、`minecraft:repair_cost`、`minecraft:custom_name`；`--check` 确认无遗留 1.20 字段。
- 补 4 个结构群系标签与 30 个结构中英名称；`runData`、`build` 通过，`runGameTestServer` 177/177 通过。真实世界结构抽检、固定世界树位置、地形贴合与客户端视觉待游戏内验收；`fluffy_wind_church` 的风之旅途战利品表仍待相关物品迁入。

### 批次 095：同步两本寻梦者笔记与英文翻译（2026-11-10）

- 同步上游 #1294/#1295/#1300：注册 `世界树调查记录`（染梦世界，作者绫苒）与 `关于风旅生物的研究笔记`（风之旅途，作者叶锦），补内容登记、维度绑定与创造模式笔记栏收录，并更新设计文档《新剧情书》。
- 按 #1300 将 `使用说明`、`逐梦列车`、`灵魂石的现世`、`灵魂石的现世（假）`、`魔法使的记录 其三` 的英文正文替换为来源译文，补齐两本新书的中英标题与正文。
- #1295 的结构 NBT 因对应结构尚未移植不改；风之旅途阅读任务 6→7 依赖的追忆任务系统尚未迁入，本批不适用。
- `runData` 重新生成双语语言文件，`build` 通过，`runGameTestServer` 175/175 通过；客户端书籍翻页与英文排版待游戏内验收。

### 批次 094：染梦世界粉红鸡、粉红史莱姆与自然生成（2026-11-10）

- 迁入染梦世界原生生物 `pink_chicken`、`pink_slime` 及 `pink_egg` 物品与 `thrown_pink_egg` 投掷物：粉红鸡保留飞行移动、染梦果繁殖/吸引、6000–12000 tick 产蛋与摔落免疫；粉红史莱姆保留跳跃随机位移、摔落免疫与无分裂行为。
- 按来源权重和数量在 8 个染梦群系接通自然生成（平原、蘑菇山地、雪原、海滩、雪峰、樱花树林、森林、花田），并补齐 `animals_spawnable_on` 所需的染梦草方块既有标签、刷怪蛋、实体/物品中英名称、物品模型、创造栏、实体战利品表与 `c:eggs` 物品标签。雪原、雪峰与樱花树林同时加入 `minecraft:spawns_snow_foxes`，使自然生成的狐狸为雪狐。
- 迁入 `FoxTargetMixin`，狐狸在设置目标时追加对粉红鸡的主动攻击目标；染梦果继续通过既有 `minecraft:fox_food` 标签作为狐狸食物。
- 粉红蛋可用于来源煎蛋熔炉/烟熏/篝火配方；投掷后 1/5 概率生成幼年粉红鸡。1.21 适配移除 `PlayMessages`/`NetworkHooks`/`MobType` 与 5 参数 `finalizeSpawn`，出生点改用 `RegisterSpawnPlacementsEvent`，模型 `renderToBuffer` 改用颜色整数。
- `runData` 写入实体战利品表、物品/刷怪蛋模型、群系生成与双语条目；`build` 通过；`runGameTestServer` 175/175 通过。自然生成的实际世界抽检、客户端飞行动画与多人同步仍待游戏内验收。

### 批次 093：金狐交易与宠物（2026-11-10）

- 按完整引用组迁入金狐交易/宠物：5 条数据驱动交易配方（金苹果、空桶、融梦钱堆、融梦水晶碎片、熔金苹果）、金狐交易实体、可驯服金狐宠物与狐火球、两座雕像方块/物品、`FireballProjectileModel`、刷怪蛋、`ding` 音效与「金狐的愿望」进度。
- 保留来源注册 ID、交易产物（融梦钱堆换取 3 个带超级组件的染梦深海秘宝）、雕像仪式条件（四角迷雾梦莲 + 发光浆果 + 0–6000 tick + 每玩家 600 tick 冷却）、实体数值与驯服/交易/雕像变回行为；1.21 配方改为 `MapCodec`/`StreamCodec`，GeckoLib 4.9 与同步数据接口按平台重写。
- `runData` 生成交易配方、雕像方块状态/模型/战利品表、金狐实体空战利品表、进度与双语条目；`build` 通过；`runGameTestServer` 175/175 通过。客户端动画/半透明渲染、实体 AI/保存读取/多人同步与雕像仪式实机仍待验收；染梦花园结构与 Patchouli 条目未迁入。

### 批次 092：逐梦列车车票（2026-11-10）

- 迁入 `dream_train_ticket` 逐梦列车车票：堆叠上限 1，使用后在玩家上方 145 格高度放置 `dream_train` 结构，按玩家朝向旋转并向全服广播列车位置、播放风铃；默认每名玩家在每个维度只能使用一次，`trainTicketOnePerDimension` 开启时改为全服每维度一次（存档 `SavedData`）。
- 新增 `trainTicketDimensionWhitelist` 与 `trainTicketOnePerDimension` 配置，默认允许原版三维度及染梦世界、灯影之下、风之旅途。
- 补物品模型、中英名称与 tooltip、创造模式物品栏条目，并在染梦世界传奇融梦水晶箱中恢复来源 5% 车票奖池（替换批次 090 的空奖占位）。
- `runData`、`build` 通过，`runGameTestServer` 173/173 通过；列车实际放置、客户端视觉、配置热重载与多人维度唯一性仍待游戏内验收。

### 批次 091：染梦结构箱战利品与逐梦列车广播方块（2026-11-10）

- 迁入染梦野餐篮、染梦遗物箱普通/稀有及生态气泡箱共 4 张来源战利品表，保留奖池、抽取次数、条目顺序与权重；1.21 红露戒指等级改写为自定义数据组件，海龟鳞甲使用新物品 ID。
- 迁入 `dream_train_structure` 隐形标记方块及 `dream_train_broadcast` 方块实体、列车与标记 NBT、风铃音效和双语提示；非旁观玩家进入 200 格范围时按来源放置列车、全服播报并移除标记。染梦结构的 placement、template pool 与 structure set 留待后续批次。
- 列车 NBT 的 3 个旧格式物品栈已用 `scripts/convert_structure_item_nbt.py` 转换；两本寻梦者笔记的 `tag.content` 写入专用 `pasterdream:dream_note_content` 组件。转换流程与校验写入 worldgen skill。
- `runData`、`build` 通过，`runGameTestServer` 172/172 通过（含两本笔记的物品组件解析）；真实世界生成、客户端视觉/音效与多人广播仍待实机验收。

### 批次 090：融梦水晶箱与染梦世界战利品（2026-11-10）

- 迁入未开启/已开启融梦水晶箱、开箱品质抽取和动画、九格容器、重置工具及玩家冷却同步；时之沙恢复跳过重置冷却的来源交互。
- 迁入染梦世界普通、稀有、传奇三张战利品表及创意栏预置箱体，保留来源奖池抽取次数、条目和权重；逐梦列车车票因列车系统未迁，传奇表对应概率暂为空奖。
- `runData` 生成战利品表、方块状态、物品模型、掉落、标签和中英语言文件；`build` 与 `runGameTestServer` 验证通过。染梦世界结构生成、客户端视觉与多人行为待验收。

### 批次 089：魔法使的灵魂石、追忆宝石与追忆物品栏（2026-11-10）

- 迁入饰品魔法使的灵魂石：提供融梦光环、战技伤害 +40%、魔法伤害 +50%、融梦能量消耗翻倍；能量低于 30 时受伤翻倍，主手 shift 右键可恢复全部能量并在 2 分钟内免除消耗、提升战技与魔法伤害（冷却 4 分半钟），装备于 `body` 槽。
- 迁入追忆宝石，并新建「帕斯特之梦 | 追忆」创造模式物品栏，当前收录追忆宝石与魔法使的灵魂石；其余遗物待后续迁入。
- `runData` 生成两件物品模型与中英语言条目，`build` 通过；本次无适用 GameTest，客户端佩戴与 tooltip 待实机验收。

### 批次 088：补全染梦侵染 JEI 信息页（2026-11-10）

- 迁入上游染梦侵染 JEI 分类：10 条方块转换配方展示原料、裂隙图标与产物，染梦裂隙作为分类催化剂，并补中英双语分类名称。
- 配方网络同步时把标签原料解析为具体方块列表传给客户端，避免联机 JEI 页面原料槽为空；不改动服务端侵染匹配、转换目标与数值。
- `runData` 写入 2 个语言文件，`build` 通过，`runGameTestServer` 168/168 通过；JEI 实际界面与联机展示待客户端验收。

### 批次 087：修复蓄梦池跨群系产出（2026-11-10）

- 移除开发运行时会加载的无群系限制 GameTest 配方，避免它与正式染梦粉尘碎片配方产生相同产物并绕过群系条件。
- 蓄梦池处理已缓存进度时重新匹配当前群系与配方；原配方失效时清除进度，不再继续消耗催化剂或产出。
- GameTest 验证错误群系无法启动加工、消耗吸附剂，以及无法利用已缓存进度完成产出；保留允许群系中吸附剂每 tick 扣耐久的测试。`runData` 写入 0 个文件，`build` 通过，`runGameTestServer` 168/168 通过；真实染梦群系与客户端行为待游戏内验收。

### 批次 086：补全染梦世界与原版进度页可搬运进度（2026-11-09）

- 补全染梦世界标签页缺口的可搬运进度：`story/create_pliers`（太陽とレインボ一）、`story/dream_accumulator`（尘埃落定）、`story/dyedream_broken_note`（染梦残页）与三条纯逻辑笔记进度 `research_on_sweet_dream_world`、`essence_of_dream_world`、`pale_snow_lotus_and_boneneeedle`。
- `dream_accumulator` 按实际玩法改为首次打开蓄梦池即授予（新增 `open_dream_accumulator` 触发器），不再沿用来源已失效的读旧笔记条件；`dyedream_broken_note` 使用 `inventory_changed` + 新增 `has_advancement` 组合条件，保留 10 点经验与解锁 `dyedream_unknown_note` 配方奖励。
- 补全原版页签进度：冒险页 10 条（被遗忘的剑冢、遗忘之剑、丛林之力、刀下亡魂、邻家有女初长成、新概念拔剑、物归原主、沙漠英灵、英灵之剑、遗志已成）、农牧业页 3 条（海洋之泪、深蓝之心、潮汐之神）、下界页 3 条（真金不怕火炼、炙热之剑、炼狱之火）。
- 新增 `found_tomb`、`found_desert_fortress`、`new_standard_sword_drawing` 三个触发器及对应结构探测处理器；剑冢击碎时补触发「新概念拔剑」。
- 未搬运：灯影/风之旅途/暗影分支（维度、`TWILIGHT_LANTERN` 或相关触发器未迁入）；`dyedream_unknown_note` 物品与配方本身仍未迁入，残页进度奖励会保留指向该配方。
- `runData` 新增 22 个进度 JSON 并更新双语，`build` 通过，`runGameTestServer` 167/167 通过；结构探测与笔记触发待实际地形/客户端验收。

### 批次 085：梦境果汁与骨针系列（2026-11-09，T07 / T17 部分）

- 迁入 `glass_jar_of_dream_juice` 梦境果汁物品与 `dream_juice` 存储流体：保留堆叠 8、24 tick 饮用、返还玻璃罐、90 秒美梦祈愿效果与入睡 60 tick 后进入染梦世界的传送；补玻璃罐 125 mB 容器映射。
- 迁入 `pale_boneneedle`、`roots_pale_boneneedle` 与 `pale_bone_needle_talisman`：骨针在梦境维度使用后回主世界重生点/标记点，骨针护符在梦境拦截致命伤并延迟送回重生点；新增 `DreamDimensionHelper`、`GameModeHelper`、`DreamWishEffect` 与 `use_boneneedle` 触发器。
- 骨针坐标状态改用 `CUSTOM_DATA`（`switch` 整数、`x/y/z` double），清空坐标配方使用 `DataComponentIngredient` 部分匹配；补 5 条工作台配方与 3 个进度（哦，痛！/人类坠出梦境/好梦在何方）。
- `runData` 写入 19 个文件，`build` 通过，`runGameTestServer` 167/167 通过。睡眠传送、护符拦截、客户端视觉与多人/死亡/重登行为待游戏内验收。

### 批次 084：修复秋麒麟茶效果未生效（2026-11-09）

- 修复 `goldenrod_tea` 效果只有构造器、没有 tick 逻辑导致饮用秋麒麟茶后不移除饥饿与反胃的问题。
- 按上游行为在 `GoldenrodTeaEffect` 覆写 `shouldApplyEffectTickThisTick` 与 `applyEffectTick`：每 10 tick 移除饥饿与反胃；1.21.1 已将该钩子暴露为可覆写方法，未引入 `MobEffect` 全局 Mixin。
- 新增 GameTest `goldenrodTeaRemovesHungerAndNausea`；`build` 通过，`runGameTestServer` 167/167 通过。

### 批次 083：定向同步 #1293 蓄梦池 JEI 群系滚动显示（2026-11-09）

- 同步上游 #1293：新增无阴影文本纵向滚动绘制，蓄梦池 JEI 将群系条件从「标签名 + 群系数量」恢复为逐群系列表，并在固定视口内自动上下滚动显示。
- 纵向滚动为 #1293 新增方法，按来源方法体复制到现有 `NonShadowCenteredString`；#1293 对 #1292 横向滚动方法的重命名及未迁移界面调用点不适用，本次未改动。
- 复用批次 073 已补的 16 个 `biome.pasterdream.dyedream_*` 名称，移除不再使用的 `biome_tag.pasterdream.dyedream_world_biome` 与 `jei.pasterdream.dream_accumulator.biome_count` 语言键。
- `runData` 写入 2 个语言文件，`build` 通过；JEI 滚动视觉待客户端游戏内验收。

### 批次 082：主世界地物与结构补全（2026-11-09，T16 部分）

- 补全主世界 12 种地物：四叶草、秋麒麟、魔星兰、锦葵、洋桔梗、燕麦、黑麦、苍白雪莲、棉花、丛林孢子囊、芦苇与小石子，保留来源随机簇参数与稀有度；按群系标签注入 `neoforge:add_features`。
- 补全主世界结构：橡木/云杉渔夫小屋、露营地、沙漠小屋与沙漠要塞，含 structure/structure_set/template_pool、6 个 NBT 模板与来源 spacing/separation/salt；结构箱子接入 4 条来源权重的战利品表。
- 新增 12 个主世界群系标签；1.21 适配 `EnchantWithLevelsFunction`、`TURTLE_SCUTE`，并把沙漠要塞模板中的 `grass` 对齐为 `short_grass`。
- 结构 NBT 物品栈改为 1.21 组件格式：沙漠小屋《探求秘辛》笔记写入 `dream_note_content` 组件（修复 `null - null`），渔夫村民交易与要塞弩的 `Count`/`tag` 同步为 `count`/`components`；补 5 个结构的中英翻译键。
- 修复失落剑冢不生成：模板 NBT 从复数 `data/pasterdream/structures/` 移到 1.21 单数 `data/pasterdream/structure/`；GameTest 增加其结构、结构集与模板加载断言。
- `runData` 写入 54 个文件（含补结构翻译键的 2 个语言文件），`build` 通过，`runGameTestServer` 166/166 通过（新增地物注册、结构注册、模板加载与 4 张结构战利品表加载断言）。真实世界分布与结构视觉待游戏内验收。

### 批次 081：染梦裂隙与维度进入流程（2026-11-09，T17 部分）

- 迁入 `dyedream_crack` 染梦裂隙方块与物品：保留可朝向形状、发光 15、随机 tick、粒子/音效和不可破坏属性；补主世界裂隙空岛结构模板、structure/structure_set/template_pool 与双语结构名。
- 迁入染梦污染配方类型/序列化器与 10 条转换配方、来源方块标签及黑名单；新增 `dyedreamCrackContaminationEnabled`、`dyedreamCrackContaminationRadius` 配置。
- 接通 `接触裂隙 → 睡眠 40 tick 获得笔记 → 阅读/授予染梦裂隙进度 → 进入裂隙传送 → 染梦维度授予世界进度` 链路；补 `TeleportHelper` 效果包重发、首次接触标记与双向传送冷却。
- 1.21 适配：补方块 `codec()`、配方 `MapCodec`/`StreamCodec`、结构目录改为单数 `structure/`、短草改用 `short_grass`、效果包构造补 `blend` 参数。
- `runData` 写入 25 个文件，`build` 通过，`runGameTestServer` 163/163 通过（新增结构模板、污染配方与方块注册断言）。真实结构分布、客户端视觉与多人传送待游戏内验收。
- 复查修正方块双语名称键，并将传送音效限制在实际目标维度确定之后，首次接触裂隙不再播放。

### 批次 080：蓝图物品与搭建系统（2026-11-09，T05-B 部分）

- 迁入 `blue_print` 蓝图物品、`精铸工坊` / `暗影高炉` 内容注册、蓝图结构预览界面、材料清单校验、按玩家朝向旋转放置与材料消耗；蓝图数据改用 `BLUEPRINT_CONTENT` / `BLUEPRINT_PLACING` 数据组件，三个网络包改为 NeoForge payload。
- 新增上下箭头翻层控件、蓝图 GUI 渲染、JEI 变体区分与 `pasterdream:dream_notes` 标签；复制 2 张界面/物品纹理和 4 个结构 NBT，结构内方块均已注册。
- 大师级工具商按 `toolsmithBlueprintTradeChance`（默认 1.0）出售精铸工坊蓝图；暗影高炉蓝图的生存来源仍依赖未迁移的 `shadow_foundry` 结构箱。
- `runData`、`build` 通过；`runGameTestServer` 163/163 通过。客户端 GUI/线框预览、实际放置与多人同步待游戏内验收。

### 批次 079：精铸工坊其余设备方块（2026-11-09，T15-A 部分）

- 迁入精铸工坊剩余五类设备：铁砧、冷却锅、锻锤、砂轮与高炉的方块、方块实体、菜单/界面、冷却锅流体渲染、高炉放置物品与 `_2x4x2` 多结构；保留来源注册 ID、方块参数、槽位数量、流体容量与放置/破坏语义。
- 1.21 适配：补 `codec()`、`use`→`useWithoutItem`/`useItemOn`、`ServerPlayer#openMenu`，能力改用 `RegisterCapabilitiesEvent` 集中登记，流体/物品存档改用 `HolderLookup.Provider` 重载，冷却锅渲染改用 1.21 顶点接口。来源空的高炉配方类保留空实现，高炉仍只提供存储与界面。
- 掉落表、`mineable_with_pickaxe` 标签与双语名称由 Provider 生成；砂轮来源漏标镐挖掘，本批按原版砂轮属性补入。蓝图内容注册与工坊结构 NBT 仍未迁入。
- `runData` 生成 5 个掉落表并更新方块标签与双语语言；`build` 通过；`runGameTestServer` 163/163 通过。客户端 GUI、流体渲染与多人同步待游戏内验收。

### 批次 078：寻梦者笔记书系统（2026-11-09，T05-A 部分）

- 迁入 62 本寻梦者笔记书：`dream_notes_book` 物品、内容/作者/正文注册表、按宽度自动换行与按高度分页的阅读界面、左右箭头与滚轮翻页，以及 `dream_notes_book_*` 物品材质覆写。
- 新增 `DREAM_NOTE_CONTENT` 字符串组件承载笔记内容，替代来源 NBT `content`；新增 `read_dream_note` 进度触发器与维度阅读进度记录。
- 创造模式物品栏新增 `pasterdream_dream_notes_tab`，按来源顺序列出全部笔记书；双语标题与正文随 Provider 生成。
- 修复 1.21 中 `Screen#render` 自动调用 `renderBackground` 导致的二次背景模糊：阅读界面改为手动渲染控件，书页与文字保持清晰。
- 维度任务系统、暮影之灯提示、剧情引导物品与蓝图尚未迁入，相关入口按最小适配略去；客户端阅读与模型切换待游戏内验收。
- `runData` 更新物品模型与双语语言；`build` 通过；`runGameTestServer` 161/161 通过。

### 批次 077：钓鱼系统、深海秘宝与占星者的祈愿（2026-11-09，T17 部分）

- 迁入 `deep_sea_treasure`、`dyedream_deep_sea_treasure`、`shadow_deep_sea_treasure` 与 `star_wish_rod`：右键展开对应宝箱战利品表，super 变体改用 `deep_treasure_super` 数据组件并显示附魔光效；创造模式使用深海秘宝不消耗物品。
- 恢复维度感知钓鱼战利品注入：主世界/染梦海洋按来源权重产出深海秘宝，星愿钓竿额外必出超级变体；保留原版与其他模组注入条目，`shadow_ocean` 未迁入时跳过浸影秘宝池。星愿钓竿的饵钓合计封顶 Lure V（500 tick），避免 1.21.1 `FishingHook` 在更高数值下反复重掷、永远不咬钩。
- 迁入 `FishingHookMixin`（熔融阴影可钓鱼）、`ShadowHandFishingHandler`、`shadow_roar` 音效与星愿钓竿 `cast` 模型谓词；武器工坊新增来源五材料配方。
- 新增 `get_deep_sea_treasure`、`get_super_deep_sea_treasure`、`get_star_wish_rod` 进度；因 `get_snow_vow_head` 未迁入，星愿钓竿进度暂挂 `story/root`。
- `runData` 写入 18 个新增数据文件并更新双语语言；`build` 通过；`runGameTestServer` 161/161 通过。客户端模型切换、现实钓获概率与音效待游戏内验收。

### 批次 076：主线与染梦世界进度、衍梦肥泥与染梦粉红羊（2026-11-09，T05/T16 部分）

- 新增 `ModAdvancementProvider`，接入主线根→染梦世界的 14 个可完成进度：`story/root`、`first_contact_dyedream_crack`、`dyedream_world`、`dyedream_biomes`、`misty_dreaming_lotus`、`queer_soup`、`look_at_pink_sheep`、`dream_fertilizer`、`dyedream_dust`、`melt_dream_crystal_fragment`、`melt_dream_liquid_bucket`、`galaxy_jelly`、`eat_galaxy_jelly_on_high_height`、`pure_and_flawless`；1.21 适配 `AdvancementHolder`/`AdvancementType`、`Criterion`、`setBiomes(HolderSet)` 与注册表 lookup。
- 迁入衍梦肥泥 `dream_fertilizer` 物品、粒子与研钵配方：右键优先把短茎草变为高茎草，否则走骨粉生长，仅成功时消耗并生成粒子；`粉红粘液球 + 骨块 + 研钵 → 9`。
- 染梦粉红羊：加入时位于 `pasterdream:dyedream_world_biome` 的羊设为粉色；玩家在染梦维度注视 16 格内粉色羊时触发 `look_at_pink_sheep`。
- 暂缓 `dyedream_crack`、`create_pliers`、`glass_jar_of_dream_juice`、`dream_accumulator`、`use_pale_boneneedle`、`human_falls_out_of_dream`、`dyedream_broken_note` 与三条笔记链，待裂隙方块、笔记/骨针触发器、玻璃罐与园艺钳父链迁入；`dyedream_world` 父节点暂指向 `root`。
- `runData` 生成 14 个进度 JSON、物品模型、研钵配方与双语语言；`build` 通过；`runGameTestServer` 161/161 通过。客户端界面、粒子、真实染梦生成与进度授予待游戏内验收。

### 批次 075：暗影之手陷阱与狐火免伤修复（2026-11-09，T18 部分）

- 迁入 `shadow_hand_trap` 方块、方块实体、放置物品与来源 `shadow_trap` 音效：踩踏触发 5 点魔法伤害、-5 SAN、黑暗 80t/缓慢 20t，攻击动画后自毁；非创造、非精准采集破坏时生成 `shadow_hand` 并 -1 SAN。
- 1.21 适配：`BaseEntityBlock` 补 `codec()`，魔法伤害改用 `damageSources().magic()`，精准采集改注册表 `Holder` 查询，GeckoLib 4.x 使用 `software.bernie.geckolib.animation.*` 与 `createGeoRenderer`，方块实体存档改 `HolderLookup.Provider`。
- 数据与资源：掉落表（精准采集自身，否则阴影 + 10% 噩梦燃料）、熔炉配方、`mineable_with_hoe`/`shadow_erosion_tool_can_boost` 标签、方块状态、物品模型与双语名称由 Provider 生成；逐字节复制 geo/动画/`models/custom`/`displaysettings`/纹理/音效。
- 补齐来源对原版方解石的额外标签：`pasterdream:dyedream_bud_can_spawn_on` 加入方解石，`minecraft:stone_tool_materials` 与 `minecraft:stone_crafting_materials` 加入方解石。
- 修复陷阱攻击动画未播完即自毁：以触发时的 `gameTime` 计算 25 tick 延迟，常规 20 tick 调度不再提前销毁。
- 补 `rust_black_metal_bars`、`congeal_wind_iron_bars` 与手写 `congeal_wind_iron_chain` 模型的 `cutout` 渲染类型，恢复 alpha 通道镂空渲染。
- 修复 `fox_fire` 被玩家泰拉剑气伤害：`FoxFireEntity` 实现 `OwnableEntity` 并暴露 `getOwnerUUID()`，使其被 `MinionFilterHelper` 识别为施放者召唤物。
- `runData` 写入 24 个文件；`build` 通过；`runGameTestServer` 159/159 通过。陷阱客户端动画/音效与结构实际生成待游戏内验收。

### 批次 074：暗影地牢钥匙、高炉核心与生态方块及生物（2026-11-09，T14/T18 部分）

- 迁入 `shadow_dungeon_wall_key`、`shadow_dungeon_floor_key`、`shadow_blast_furnace_core`、`ecology_glass_jar`、`firefly_glass_jar`、`firefly_nest`、`birds_nest`、`christmas_lights`、`small_stone_spirit_block`，以及 `firefly`、`small_stone_spirit` 生物、刷怪蛋与 `capture_firefly` 触发器。
- 方块实体与 GeckoLib 渲染/动画、萤火虫与玻璃罐粒子、玻璃罐动画状态网络同步（`CustomPacketPayload`）一并迁入；萤火虫玻璃罐的 `Lit` 状态改用 `DataComponents.CUSTOM_DATA`。
- 圣诞彩灯与生态玻璃罐配方、方块掉落表、挖掘/铁工具/影蚀加速标签、方块状态、物品模型与中英文名称由 Provider 生成；其余方块暂无自制配方，结构/世界生成获取待后续。
- 1.21 适配：`use`→`useWithoutItem`/`useItemOn`，`BaseEntityBlock`/方向方块补 `codec()`，`getDimensions`→`getDefaultDimensions`，`MeleeAttackGoal#getAttackReachSqr`→`canPerformAttack`，自定义触发器改 codec `SimpleInstance`。
- 修复 `dyedream_pane`、`shadow_pane`、`wind_moor_pane` 三个木窗格方块的 alpha 渲染：来源在 `ClientSetRenderLayer` 将其设为 `cutout`，1.21 改为在生成的窗格模型写入 `render_type: minecraft:cutout`。
- `runData` 写入 27 个新增文件；`build` 通过。客户端 GeckoLib/粒子视觉、动画同步、真实世界生成与多人行为待游戏内验收。

### 批次 073：蓄梦池与吸附剂（2026-11-08，T12-A 部分）

- 迁入 `dream_accumulator` 方块、方块实体、菜单/界面、GeckoLib 模型/动画/物品渲染、`dream_accumulator` 配方类型与序列化器、JEI 类别，以及吸附剂 `sorbent`；保留 2 物品槽、6000 tick 蓄梦时间、吸附剂 10 倍速率、`dyedream_world_biome` 群系限定与输出槽合并语义。
- 来源配方 `pasterdream:dream_accumulator/dyedream_dust_piece` 以 1.21 配方目录与 1.21 物品栈书记格式迁入；方块工作台配方、掉落表、挖掘/功能标签、物品模型与语言由 Provider 生成。
- 1.21 适配：`Recipe<RecipeInput>` 与 `MapCodec`/`StreamCodec`、`ItemStack.CODEC` 的 `id` 字段、`ItemStackHandler.serializeNBT(HolderLookup.Provider)`、`ItemStack.parseOptional`、`ItemStack#hurtAndBreak`、GeckoLib 4.x 包名与 `createGeoRenderer`、`RegisterCapabilitiesEvent` 物品接口。修正来源吸附剂仅在已有 Damage 标签时才扣耐久的问题，催化剂现在每 tick 正常消耗 1 点耐久。
- 界面修正：蓄梦池 GUI 绘制显式启用透明混合；JEI 群系条件改为「标签名 + 群系数量」显示，并补齐 16 个染梦群系与标签翻译，避免逐群系长列表溢出分类框。
- `runData` 写入 11 个文件；`build` 通过；`runGameTestServer` 158/158 通过，新增 3 项覆盖配方字段/工作台产物、方块实体槽位/能力/产物合并与吸附剂耐久消耗。客户端 GeckoLib/GUI 视觉、真实染梦群系蓄梦与多人同步待游戏内验收。

### 批次 072：暗影高炉及配方系统（2026-11-08，T18-C 部分）

- 迁入 `shadow_blast_furnace` 3×3×3 多方块、主/附属方块实体、放置物品、菜单与界面，保留 9000 mB 暗影流体单罐、2 输入/2 输出槽与来源形状/放置/破坏逻辑。
- 新增 `shadow_blast_furnace` 配方类型、序列化器与 JEI 类别，补入黑金属粒、暗影、暗影石、暗影合金锭、凋灵玫瑰、凋灵骷髅头 6 条加工配方；`shadow_stone` 的 `forge:stone` 适配为 `c:stones`。
- 1.21 适配：`BaseEntityBlock.codec()`、`useWithoutItem`/`useItemOn`、`ServerPlayer.openMenu`、`HolderLookup.Provider` 存档、`RegisterCapabilitiesEvent` 能力注册、共享流体槽菜单索引调整与 `ItemStack.parseOptional`。
- 混沌之箭配方因黑石箭/混沌之箭物品未迁入暂缓；来源无工作台配方，方块本体仍依赖未迁入的蓝图获取链。
- `runData` 写入 4 个文件；`build` 通过；`runGameTestServer` 155/155 通过。多方块实际摆放/破坏、附属能力、JEI/GUI 与客户端交互待游戏内验收。

### 批次 071：调试物品栏首批搬运（2026-11-08，T20 前置）

- 从来源 `pasterdream_dream_debug_tab` 迁入无前置阻塞的 10 项：调试之剑、战利品生成器、4 个破碎粒子提供方块、恐怖尖喙/疯狂恐怖尖喙/虚弱恐怖尖喙/暗影之手刷怪蛋，并建立调试创造物品栏。
- 调试之剑接入左键删除实体、左键方块选项界面（六种模拟破坏/清除方式）与右键实体/方块 NBT 打印；战利品生成器使用 `CustomData` 保存战利品表并写入容器；4 个粒子方块补齐模型、战利品表，半透明方块使用 `translucent` 渲染类型。
- 1.21 适配：NeoForge 玩家事件与 `PartEntity`、`saveWithoutId`/`saveWithFullMetadata`/`loadWithComponents`、`DataComponents.CUSTOM_DATA`、`Capabilities.ItemHandler.BLOCK`、`CustomPacketPayload` 数据包与注册表附魔 `Holder`。
- 剩余 56 个调试栏条目依赖箱子战利品表、融梦水晶箱、刷怪笼/暗影漩涡/暗影火盆或未迁实体，暂缓。
- `runData` 写入 24 个文件；`build` 通过；`runGameTestServer` 153/153 通过。客户端界面与真实容器/刷怪行为待游戏内验收。

### 批次 070：染梦维度地物生成补全（2026-11-08，T16-A 其余）

- 迁入染梦维度全部地物：方解石锥/锥簇/尖锥、浮空流明光球、繁茂洞穴巨菇、粉顶菌垂帘、小粉顶菌与方解石尖锥 NBT 放置 8 个自定义 Feature，约 70 个 configured feature 与约 90 个 placed feature。
- `ModBiomeModifiers` 按来源条目名、群系标签与生成阶段重建 `neoforge:add_features` 注入；`ModBiomeTagsProvider` 补齐 25 个 `dyedream_world_*` 分组标签与钓鱼标签。
- 逐字节复制来源 `stone_pillar_0/1.nbt` 结构模板，供海洋方解石尖锥放置；1.21 `HeightmapPlacement.onHeightmap` 公开后移除来源反射构造。
- 雪原水池新增 `DyedreamWaterPoolFeature`：1.21 原版 `LakeFeature` 的水面结冰查询会请求相邻区块的群系状态并抛出 `Requested chunk unavailable during world generation`，现保留水体/雪块围边算法并移除结冰查询，修复染梦维度进入时崩溃。
- 染梦森林萤火虫巢因 `FIREFLY_NEST` 方块/实体/粒子与玻璃罐整组未迁而暂缓；`PINK_SLIME`/`PINK_CHICKEN` 生成项、结构与生存闭环仍待后续任务。
- `runData` 写入 276 个文件，`build` 通过，`runGameTestServer` 153/153 通过；专用服务器在染梦维度强制生成 4 个区块无崩溃。真实维度分布、NBT 尖锥放置、客户端视觉与多人行为待游戏内验收。

### 批次 069：染梦维度地形与群系骨架（2026-11-08，T16-A 部分）

- 迁入染梦维度类型、噪声设置与 `LevelStem`：复用主世界噪声路由，方解石默认方块，保留来源染梦草方块/染梦沙/染梦土/浮冰/冰/白沙等地表规则与海平面。
- 迁入 16 个染梦群系的多噪声源与群系定义，保留天空/雾/水色、温度/降水、环境粒子、背景音乐与洞穴雕刻器；本批不注入 configured/placed feature，不迁结构，`PINK_SLIME`/`PINK_CHICKEN` 生成项待实体迁移。
- 补 `LEAVES` 粒子类型、客户端 Provider 与来源粒子资源；`ModWorldGenProvider` 增加 BIOME / DIMENSION_TYPE / NOISE_SETTINGS / LEVEL_STEM 注册。
- `runData` 生成维度、维度类型、噪声设置与 16 个群系 JSON；`build` 通过，`runGameTestServer` 152/152 通过。真实维度生成、群系分布、客户端天空/雾与多人行为待游戏内验收。

### 批次 068：拓展栏物品全量搬运（2026-11-08，T13 部分）

- 搬运来源 `PASTERDREAM_EXPANSION_TAB` 全部 17 项物品：`duke_coin_curio`、`hiyori_butterfly_hairpin`、`allkinds_ring`、`kaichu_omamori`、`thermal_dagger`、`melt_dream_aurorian_steel_ingot`、`terra_blade`、`terra_floating_island`、`faded_blessing_of_cecilia`、`blessing_of_cecilia`、`strawberry_heart`、`angel_wing`、`forsakens_wing`、`machine_light_wing`、`turnback_cloak`、`evasion_cloak`、`silver_bell`，保留来源注册 ID、稀有度、创造栏顺序与数值。
- 一并迁入依赖系统：`TERRA_SWORD`/`THERMAL_DAGGER` 工具等级、`cloak`/`thermal_dagger`/`sword_wave`/`fox_fire` 音效、`golden`/`silver`/`feather_white`/`fox_fire`/`terrasword_wave` 粒子、`cecilia_blessing`/`turnback_cloak`/`evasion_cloak` 效果、`kaichu_omamori`/`cloak_activate` 按键、四个网络包（含加护激活动画）、`fox_fire`/`terrasword_wave` 实体及 GeckoLib 模型渲染、三套翅膀饰品渲染器与 `kaichu_omamori` 配置项。
- 补入塞西莉亚加护的常驻减伤、致命伤害拦截与加护替换、加护效果不可移除处理；转身/回避衣装接入瞬身术回避次数扣除、耗尽后取消转身状态与反击获取，回避衣装将瞬身术回避时间延长至 0.6 秒。
- 塞西莉亚加护触发时向追踪客户端发送 `CurioActivationPacket`，恢复来源的物品激活展示动画。
- 1.21 适配：`MobType` 改用 `EntityTypeTags.UNDEAD`/`ARTHROPOD`，`LivingEntity#getDimensions` 已为 final，`VertexConsumer#vertex/endVertex` 改为 `addVertex/setColor/setNormal`，物品 NBT 改用 `DataComponents.CUSTOM_DATA`，属性修饰符 id 改 `ResourceLocation`，能力改 `ModAttachments`，GeckoLib 包名与 `ClientTickEvent.Post`/`LivingIncomingDamageEvent` 等 NeoForge 事件同步调整。
- `runData` 写入 25 个文件（物品模型、语言、标签、武器工坊配方），`build` 通过，`runGameTestServer` 151/151 通过。大地之刃使用来源手写 `models/item/terra_blade.json`（不入 datagen），热能匕首与融梦极光钢锭补入来源动画纹理 `.mcmeta`。宝箱/深海秘宝战利品、金狐交易与相关进度尚未迁入，生存获取闭环及客户端视觉/音效/GeckoLib 动画待后续批次与游戏内验收。

### 批次 067：设备栏容器、园艺钳与时之沙搬运（2026-11-08，T10/T11 部分）

- 搬运 `pliers`、`shadow_desk`、`wind_moor_desk`、`picnic_basket`、`shadow_chest`、`wind_moor_crate`、`the_endless_book_of_dream_seekers`、`feather_pen`、`sand_of_time`，保留来源注册 ID、创造栏顺序、配方、耐久、冷却与战利品行为。
- 补齐来源通用 `ItemContainer` 及 `Desk`/`Crate` 基类，迁入两本书桌、野餐篮、浸影宝盒、风泊板条筐的单槽/十五槽容器、菜单、界面、战利品展开与物品接口；染梦书桌保留现有独立实现。
- 浸影宝盒与永恒书卷接入来源 GeckoLib 模型、动画与物品渲染；1.21 动画同步改用方块更新，交互拆为 `useWithoutItem`/`useItemOn`，能力改由 `RegisterCapabilitiesEvent` 注册。
- 三本书桌与野餐篮、浸影宝盒、风泊板条筐 GUI 显式启用透明混合，补齐遗漏的风泊板条筐 GUI 纹理，避免半透明边缘渲染成黑色。
- 迁入来源 `BlockElementMixin`，放宽 1.21 方块模型旋转角/坐标限制，修复野餐篮等 Blockbench 模型因任意角度导致的模型加载失败。
- 园艺钳生成已迁移树叶的剪取战利品表，眠椰树叶与风泊鹦鹉生成池因前置未迁移暂缺；时之沙跳过融梦水晶箱冷却的交互待水晶箱迁移，昼夜切换与冷却完整保留。
- `runData` 写入 54 个文件（含补结构翻译键的 2 个语言文件），`build` 通过，`runGameTestServer` 151/151 通过（新增 2 项设备容器与园艺钳测试）；客户端 GUI/渲染/音效、实际昼夜切换与多人行为待游戏内验收。

### 批次 066：笔记碎片与音乐唱片搬运（2026-11-08，T05/T21 部分）

- 搬运 `lamp_shadow_broken_note`、`dyedream_broken_note` 两种笔记碎片与 `sweet_dream_music_disc`、`snowfall_dream_music_disc`、`wind_journey_music_disc`、`aaroncos_music_disc` 四张音乐唱片，保留来源注册 ID、稀有度、创造栏顺序与配方。
- 1.21 适配：`RecordItem` 已移除，唱片改用 `JukeboxSong` 数据包注册表与 `JukeboxPlayable` 组件，时长按来源 tick 数换算为秒；新增 `ModJukeboxSongProvider` 数据生成。
- 逐字节复制来源纹理与音效；`runData` 生成 4 个 JukeboxSong、4 个配方、6 个物品模型与语言条目，`build` 通过，`runGameTestServer` 149/149 通过；笔记碎片生存获取待战利品表批次。

### 批次 065：风之旅途物品栏与无花果搬运（2026-11-08，T19-A/T19-B 部分）

- 搬运来源创造栏 `WIND_JOURNEY_WORLD_TAB` 全部条目：苍青岩系列、白砂岩全家、厚云、风泊木全家（原木/去皮/树叶/树苗/木板/楼梯/台阶/栅栏/栅栏门/窗格/门/活板门/压力板/按钮）、澄澈玻璃系列、破风幕帐、凝风矿与凝风块、凝风铁块/栏杆/灯笼/锁链/活板门/压力板、蓬松风合金块、弹射压力板与弹射方块；另搬运无花果 `fig` 与无花果藤 `fig_vine` 及其前置风泊树叶。
- 一并迁入 `CyanMossStoneBlock`、`BreakWindCurtainBlock`、`EjectionPressurePlateBlock`、`FigVineBlock`、`WindMoorDroopingDecorator`、`BreakWindCurtainTrigger` 与 `small_wind_moor_tree` configured feature；无花果藤会自然垂落并按 1-2 个（受时运影响）掉落无花果。
- 1.21 适配：`FallingBlock`→`ColoredFallingBlock`、`GlassBlock`→`TransparentBlock`，压力板/活板门/门/按钮/栅栏门构造参数改为 1.21 顺序，`BonemealableBlock` 去除 `isClient`，方块交互改用 `useItemOn`/`ItemInteractionResult`，`ItemStack#hurtAndBreak` 改用 `EquipmentSlot`，去皮改用 `ItemAbility`。
- 澄澈玻璃、錾刻/框边变体及三种玻璃板的方块与物品模型显式写入 `render_type: translucent`，修复 alpha 通道未参与渲染的问题；补充盆栽风泊树苗及其方块状态、战利品与语言。
- `runData` 写入 631 个文件，`build` 通过，`runGameTestServer` 149/149 通过；世界生成结构、群系与真实地形投放未纳入本批，客户端视觉和树苗实际生长待游戏内验收。

### 批次 064：预言卡系列物品搬运（2026-11-08，T21 部分）

- 搬运 `empty_prophecy_card` 与九种 `prophecy_card`（平衡、混乱、纷争、墓园、守护、圣杯、罪恶、疾行、持剑），保留来源注册 ID、稀有度、创造栏顺序、右键效果、音效与模型 predicate 哈希；空白卡右键随机抽取一种类型。
- 一并迁入守护、高速反射、圣杯、怒气爆发、纷争标记五个效果及守护减伤、摔落免疫处理器和对应配置项；混乱效果复用既有实现。
- 1.21 适配：卡牌类型改用 `DataComponents.CUSTOM_DATA`，效果属性 modifier 改用 `ResourceLocation` id，罪恶卡的亡灵/灾厄判断改用实体标签，僵尸村民转换改用 `saveWithoutId`/`load`。
- `runData` 生成 11 个物品模型与 2 个语言文件，`build` 通过，`runGameTestServer` 149/149 通过；生存获取依赖的宝箱/结构战利品与悲泣尖啸怨魂进度仍待后续批次，客户端视觉与卡牌效果待游戏内验收。

### 批次 063：上游 #1290 粉顶菇菌光体去重（2026-11-08）

- 同步上游 #1290：移除 `PinkMushroom`、`TallPinkMushroom` 骨粉催生成功后额外的菌光体散布逻辑，避免菌光体被放到巨菇外侧，与自然生成外观不一致。
- 菌光体统一由 `PINK_HUGE_MUSHROOM` Feature 放置，并将放置概率由 `0.25f` 提升至 `0.4f` 以补偿密度。
- 在程序规范「易犯错误记录」补充骨粉催生与共用 Feature 重复放置装饰方块的处理方式；`build` 与 149/149 GameTest 通过。

### 批次 062：上游 #1289 碰撞箱同步与阴影蘑菇生长补全（2026-11-08）

- 同步上游 #1289：`PinkMushroom` 与 `ShadowFungusBlock` 的 `getShape` 应用方块随机偏移，使碰撞箱随视觉位置移动。
- 补全阴影蘑菇骨粉生长：注册此前遗漏的 `shadow_fungus_tree` configured feature，采用阴影菌岩基底、阴影菌柄、阴影疣块菌盖与阴影菌光体装饰的诡异菌形态配置。
- `runData` 写入 1 个文件，`build` 通过，`runGameTestServer` 149/149 通过（新增阴影蘑菇骨粉生长测试）；placed feature、群系挂载与自然生成仍待 T16。

### 批次 061：灯影之下物品栏全量搬运（2026-11-08）

- 全量搬运灯影之下创造模式物品栏 64 项条目，含阴影石系列、暗影地牢方块、灯影竞技场与裂隙、阴影菌岩/影灯/阴影蜡烛、阴影巨型菌与木建材、阴影书架、黑金属方块及阴影陶罐；保留来源注册 ID、方块属性与条目顺序。
- 新增 `ShadowCandleBlock`、`ShadowBookshelfBlock`、`KeyShadowBookshelfBlock`、`ShadowNyliumBlock`、`ClayPotBlock`、`StrippableRotatedPillarBlock`、`FireproofBlockItem`，并将阴影方块补入独立 `pasterdream_shadow_tab`。
- 1.21 适配：`ClayPotBlock` 补 `MapCodec`/`codec()` 与两参数 `isPathfindable`；`ShadowNyliumBlock` 采用新版 `isValidBonemealTarget`；门、活板门、压力板、按钮、栅栏门构造器改为 1.21 参数顺序；去皮柱改用 `ItemAbility` 判定。
- 补齐方块状态、物品模型、战利品表、采集/工具/木质/书架/刷怪标签、`c:shadow_stones`/`c:shadow_stems` 与灯影建材/木板/书架/黑金属配方；复制来源方块与物品纹理、阴影蜡烛与陶罐手动模型。
- `runData`、`build` 通过，`runGameTestServer` 148/148 通过；客户端视觉、方块交互与实际生存获取链待游戏内验收。

### 批次 060：成熟作物转换配方补齐（2026-11-08）

- 按来源 `othersRecipes` 补入 5 条无形成熟作物转换配方：成熟梦染花冠/苍白花冠/流明光球/棉花植株各得 1 个对应产物，成熟云朵植株得 5 个云。
- 作物方块、成熟/幼苗物品、掉落表与创造栏此前已迁入；本批只补齐成熟植株物品到产物的工作台转换路径，保留来源配方 ID、产物数量与解锁条件。
- `runData` 生成 5 条配方及对应进度，`build` 通过，`runGameTestServer` 148/148 通过（新增 1 项覆盖 5 条配方的 ID、输入与产物数量）。

### 批次 059：上游 #1284 / #1287 增量同步（2026-11-08）

- 同步上游 #1284：新增融梦工具与炙焰金工具的标记接口 `MeltDreamTool`、`MoltenGoldTool`，已迁入的 10 个工具类实现对应接口；融梦弓与提示管理器尚未迁入，暂不处理。
- 同步上游 #1287：狂暴药剂瓶范围效果由 60 tick（3 秒）延长至 600 tick（30 秒）；陶盆粗盐晾晒加工时间由 24000 tick 缩短至 6000 tick。
- `pasterdream_items_tab` 按来源顺序重排已迁入条目，并补入此前遗漏的流明光球、梦染花冠与苍白花冠；药剂瓶保持末尾动态生成。
- `runData` 写入 0 个文件，`build` 通过，147/147 GameTest 通过。

### 批次 058：陶盆及配方系统（2026-11-08）

- 搬运陶盆方块、方块实体、共享流体槽菜单/界面、流体液面渲染、配方类型与 JEI 类别；保留 1000 mB 单流体罐、三物品交互区与来源计时显示。
- 载入 3 条来源加工配方：125 mB 水晾晒 24000 tick 得粗盐，苹果汁/西瓜汁各 125 mB 晾晒 6000 tick 得糖；工作台配方保留白染料加陶瓦产 2 个陶盆。
- 1.21 适配：配方接入通用 `GenericPasterDreamRecipe` 序列化框架，方块实体使用 `HolderLookup` 保存/同步与 NeoForge 流体/物品能力，渲染改用 `addVertex`/`setUv`/`setLight`；因共享流体槽实现不占用菜单槽位，陶盆输出槽索引相应前移。
- 复制来源 2 张方块纹理、2 张 GUI 纹理与 4 个模型/状态文件；加工配方放入 1.21 的 `data/pasterdream/recipe/claypan/`。
- `runData` 写入 6 个文件，`build` 通过，`runGameTestServer` 147/147 通过（新增 2 项覆盖配方加载、加工扣料与计时持久化）；客户端界面、液面与实际多人交互待游戏内验证。

### 批次 057：药剂瓶系列投掷物（2026-11-08）

- 搬运药剂瓶及狂暴、冰冻、剧毒、闪电、回春、狱火、飓风 7 种类型，保留来源堆叠数、7 秒冷却、投掷速度与类型 tooltip。
- 注册药剂瓶投掷物、多阶段效果实体与回春治疗实体；迁移闪电落雷、毒气波、冰冻脉冲、狱火伤害波和飓风抛飞逻辑，新增狂暴、冰冻、折翼效果及折翼摔落伤害 Mixin。冰冻效果到期后通过 `MobEffectEvent.Expired` 恢复生物 AI，并保持来源的不可被牛奶治愈行为。
- 药剂类型在 1.21 使用 `CUSTOM_DATA` 持久化，与梦之釜配方 `nbt` 输出兼容；发射器改用 `ProjectileItem` + `registerProjectileBehavior`。
- 补入 7 条梦之釜药剂瓶配方，复制来源纹理、粒子、音效并生成模型与双语文本；闪电瓶 t=86 祭坛召唤因祭坛未迁入暂缓，JEI 按药剂类型注册子类型解释器。
- `runData` 写入 10 个文件，`build` 与 145/145 GameTest 通过；客户端模型切换、粒子音效与真实投掷命中待游戏内验证。

### 批次 056：基础素材与小石子投掷（2026-11-08）

- 搬运锈蚀金属碎屑、风行者水晶、白厄水晶、凝影剑柄、纯粹恐惧、暗影地牢钥匙、厚翅甲壳、振音声膜、小石子与雪球，沿用来源 ID、堆叠数、稀有度和 tooltip。
- 注册小石子方块、pebble 投掷物实体及渲染器，保留投掷、放置、粒子与 3.0F 命中伤害；暗影地牢钥匙掉落物沿用免毁实体。
- 复制来源纹理与石子自定义模型，补齐方块状态、战利品、物品模型、双语文本和创造栏。
- 补入小石子链、风行者果冻、光明飞蝶、纯粹恐惧转化与梦之釜易伤药水配方；矿石/块、暗影高炉与影刃等依赖未迁入内容仍待后续。
- `runData`、`build` 与 143/143 GameTest 通过；梦之釜来源配方期望更新为 92 条，客户端投掷渲染与实际方块交互待游戏内验证。

### 批次 055：梦之釜 JEI 流体槽对齐（2026-11-08）

- 梦之釜 JEI 配方界面中三个流体槽向右、向下各调整 1 像素，使流体与槽框内缘对齐；游戏内梦之釜界面的绘制坐标保持原值。

### 批次 054：梦之釜及配方系统（2026-11-08）

- 搬运梦之釜方块、方块实体、GUI、GeckoLib 模型与动画、四物品槽和三流体罐；接通配方加工、酿造、融合、勾兑、装瓶及红石控制。
- 注册物品/方块流体能力、菜单与网络请求、JEI 类别和灵药瓶药水子类型；来源药水 `EffectList` 转为 1.21 的 `POTION_CONTENTS` 组件，保留效果顺序与时长。
- 载入 90 条来源配方，另 9 条依赖未迁入的 `potion_bottle` 或 `black_beetle_carapace`，待前置完成后接入；复制 10 个来源资源文件。
- `runData`、`build` 和 143/143 GameTest 通过；客户端视觉、JEI 实际展示及多人交互待游戏内验证。

### 批次 052：灵药瓶及衍生饮品（2026-11-08）

- 搬运空灵药瓶、融梦灵药瓶和狂暴战意灵药；空瓶使用 1000 mB 流体组件，支持装液、部分排液与按量饮用。两种成品沿用来源数值和空瓶余料；狂暴战意效果使用 1.21 属性修饰符，重复饮用不叠加。
- 注册狂暴战意流体、专用创造栏、双语文本、模型及来源两条配方；复制 3 张来源纹理。普通药水流体与炼药釜产物仍待 T12-B。
- 修正灵药瓶液面纹理在 1.21 的 UV 换算：将来源 0–16 像素坐标归一化后再向图集取样。
- `runData`、`build` 和 139/139 GameTest 通过；客户端液面与多人行为待游戏内验证。

### 批次 053：灵药瓶装普通药水（2026-11-08）

- 注册通用 `potion` 流体与流体类型，灵药瓶可装普通药水；饮用每次 250 mB、32 tick，按剩余量折算效果时长，效果颜色按等级加权混合。
- 灵药瓶 tooltip 展开药水效果、等级与时长；创造栏补齐每种效果与全效果药水样例。药水效果数据沿用 `POTION_CONTENTS` 组件，可随物品流体组件持久化与同步。
- 将特殊饮用效果提前到排液前执行，避免药水按已扣除后的余量折算；炼药釜、JEI 子类型与狂暴战意灵药生存来源仍待 T12-B。
- `runData`、`build` 和 140/140 GameTest 通过；客户端染色与多人行为待游戏内验证。

### 批次 051：暗影合金弩与易伤/束缚效果（2026-11-07）

- 搬运暗影合金弩，保留来源耐久 842、防火/EPIC、暗影合金锭修复、自带力量 III、可附魔无限与装填/发射不消耗普通箭矢的逻辑；发射时消耗 0.2 融梦能量触发「暗影飞矢」，命中施加 10s 易伤与配置白名单内的随机 5s 负面效果，烟花爆炸同样群体生效。
- 补齐易伤与束缚效果及易伤每级 +10% 受伤结算；配置新增 `shadowAlloyCrossbowDebuffs`、时长与等级三项，默认白名单含缓慢、挖掘疲劳、失明、虚弱、中毒、凋零、飘浮、混乱与束缚。
- 新增暗影合金箭实体、通用拖尾基类与客户端三棱柱管状拖尾；暗紫拖尾 `0xFF8E24AA → 0x008E24AA`，保留光灵箭贴图、命中发光与拾取物。物品模型谓词在客户端手动注册。
- 生成来源配方（黑石棍 + 暗影合金锭 + 原版弩 + 噩梦燃料）与弩/耐久附魔标签，力量与无限由物品类放行；复制 6 张弩纹理和 2 张效果图标，补齐中英文名称、tooltip 与创造栏位置。
- `runData` 写入 13 个文件，`build`、`runGameTestServer` 通过（137/137），新增 3 个 GameTest；客户端拖尾、模型切换、附魔与真实命中效果待游戏内确认。

### 批次 050：红露滴戒指与强击戒指 JEI 修复（2026-11-07）

- 搬运红露滴戒指 lv1～lv4，保留来源最大生命值加成（+4/+6/+8/+10）、等级模型切换、单枚佩戴限制与提示文本；等级改存 1.21 `CUSTOM_DATA`，品质随等级写入 `RARITY`。
- 按来源补齐四条升级配方：戒指原胚/金锭/鲜红露滴合成 lv1，之后逐级用染梦粉尘与鲜红露滴升级，lv3 追加融梦液体桶、lv4 追加生命水晶，并只匹配对应等级的上一阶戒指。
- 注册 Curios `ring` 标签与创造栏 lv1～lv4 条目，复制来源五张模型和四张纹理，补齐双语名称与效果提示。
- 在 JEI 注册红露滴戒指与强击戒指的等级子类型解释器，修复强击戒指按等级查询配方时因缺少子类型而无法正确命中的问题（同步来源 `be38469a` 的修复）；未改配方、数值或来源 ID。
- `runData` 写入 10 个文件，`build`、`runGameTestServer` 通过（134/134），新增 GameTest 覆盖四级配方、品质与最大生命值穿脱；客户端模型显示、JEI 实际按键查询与真实生存获取待游戏内确认。

### 批次 049：玻璃杯及杯装饮品（2026-11-07）

- 搬运玻璃杯及苹果汁、未烹调/烹调染梦花茶、染梦果汁、蜂蜜汁、西瓜汁，排除眠椰苏打水；保留来源饮用效果、营养数值、空杯余料与蜂蜜汁提示，并适配 1.21 瞬时效果处理以保证熟花茶治疗生效。
- 增加 6 种存储流体、6 条 250 mB 容器映射、7 个物品流体能力；复制 7 张来源纹理，补齐模型、中英文名称和创造栏顺序。
- 生成玻璃杯与饮品、花茶三种烹调以及鲜红露滴共 10 条配方；`runData`、`build`、`runGameTestServer` 通过（133/133），客户端视觉与实际合成待游戏内确认。

### 批次 048：饮食物品栏食物与料理效果（2026-11-07）

- 搬运饮食物品栏中除眠椰、梦境果汁、玻璃杯、马天尼杯、暗影果冻、灵药瓶之外的食物、食材与饮品共 47 项，保留来源营养/饱和度、SAN、融梦能量、药水效果、食用时长、空碗/空罐余料与 ToolTip。
- 补齐迷梦、防风、风行者、秋麒麟茶、染梦香水 5 个效果与星河果冻高度进度触发器；新增 `galaxy_jelly_boost`、`cooked_eggs` 标签并将面团加入 `c:foods/dough`。
- 复制 44 张物品纹理、5 张效果纹理，补齐中英文名称、模型、创造栏顺序与 39 条配方。
- 补全风行者鞘翅飞行（`ItemElytraMixin`）、染梦香水幻翼锁定阻止（目标变更事件）、星河果冻强化分支缓降、琥珀糖矿石战利品表，以及风行者果冻/传说龙角冰淇淋/奇异炖菜提示。
- 未纳入需要未迁前置的内容：无花果及水母果冻配方、风行者果冻配方、鲜红露滴配方；物品已注册，获取途径待前置。
- `runData` 写入 133 个文件，`build`、`runGameTestServer` 通过（129/129）；客户端显示待游戏内确认。

### 批次 047：玻璃罐系列与容器配平接线（2026-11-07）

- 搬运玻璃罐、水罐、牛奶罐、酵母罐、药引瓶、风植萃取液与墨水罐；水罐与牛奶罐可饮用并返还空罐，其余保留来源空罐余料。
- 补齐药引、风植萃取液、酵母、墨水四种存储流体，接通 6 条 125 mB 容器映射与 7 个罐的物品流体能力；玻璃罐、水罐、牛奶罐、药引瓶、风植萃取液、酵母及两条墨水配方按来源 ID 生成，容器配方启用余料配平校验。
- 复制 7 张来源纹理，补齐中英文名称、物品模型与创造栏位置，并补上共享流体槽的空槽 ToolTip 翻译键。
- `runData` 写入 26 个文件，`build`、`runGameTestServer` 通过，126/126；新增玻璃罐能力与配平 GameTest。客户端饮用动画、实际合成与 JEI 显示待游戏内确认。

### 批次 046：染梦书桌与研钵 GUI 透明混合修复（2026-11-07）

- 书桌与研钵底图绘制前显式恢复标准透明混合及白色着色；共享物品栏绘制后保留混合状态，使后续研钵流体槽贴图按阿尔法通道渲染。未修改原始纹理或 GUI 布局。
- `runData`、`build`、`runGameTestServer` 通过，124/124；客户端视觉待游戏内确认。

### 批次 045：研钵与通用流体槽、配方基础（2026-11-07）

- 搬运研钵物品、GUI、组件持久化、8+4 物品槽、4+2 流体槽、研磨请求与 JEI 类别；三张原始纹理逐字节复制。
- 研钵提示文本恢复来源的中英文原文与灰色格式。
- 同批接通共享流体槽菜单/界面/网络同步、流体原料与通用配方编解码、匹配和原子处理；研钵改用共享框架，JEI 可展示流体原料与产物。通用容器映射/处理器与合成余料配平工具已迁入，填充容器的专用映射仍随物品迁移。
- 接入研钵制作与重置、染梦染料 7/63 产出的工作台配方、粗盐提纯和三条面粉工作台配方；GUI 接入 5 条现有原料可用的来源配方。其余含未注册流体/容器的配方保留待办。
- `runData`、`build`、`runGameTestServer` 通过，124/124；GameTest 覆盖混合物品/流体加工的容量不足回滚和容器余料守恒。客户端视觉与多人交互待游戏内验证。

### 批次 044：染梦书桌与植物交互补全（2026-11-07）

- 搬运染梦书桌的单槽存储、GUI、物品接口、两条来源配方、方块模型与资源；接入雪绒花及冶梦莲解密逻辑，补齐凛冽雪绒花随机刻冻结入口与方块实体。
- 补齐染梦树苗与粉顶菌的骨粉生长特征，恢复单株巨菇、2×2 巨树、双层粉顶菌及树叶/菌光体装饰逻辑。
- 搬运当前植物栏对应的 32 种盆栽，包括 5 种可生长、剪刀采收的盆栽作物；补齐花盆种入、空手取出、分阶段掉落、模型与标签。普通作物剪刀收割恢复来源 `dust_0` 粒子。
- `runData`、`build` 和 `runGameTestServer` 通过，118/118。冶梦莲转换仍需染梦维度；园艺钳、自然生成和客户端视觉留待后续。

### 批次 043：植物物品栏与植物掉落（2026-11-07）

- 搬运来源植物栏 51 个条目及花冠、棉花、光球和幸运四叶草等掉落产物；补齐植物模型（含睡莲、莲花、四叶草的自定义几何）、纹理、中英文名称、种植标签与战利品表，并恢复粉顶菌方块的来源掉落。
- 花园解密转换暂缓；园艺钳、衍梦肥泥、盆栽与树苗/巨菇世界生成链待后续迁移。`runData`、`build` 和 `runGameTestServer` 通过，109/109。

### 批次 042：熔融阴影桶、融梦涌泉桶与流体系统（2026-11-07）

- 搬运两种桶、源液/流动液、流体类型和世界液体方块，保留注册 ID、物理属性、融梦涌泉粒子与来源动画纹理；生成 NeoForge 桶模型、流体标签及中英文名称。
- 两种桶接入 1000 mB 标准物品流体接口；GameTest 覆盖整桶充排、模拟操作、部分排放与错液拒绝。通用容器/流体槽及生存获取链仍按迁移任务线后续推进。
- `runData`、`build` 与 `runGameTestServer` 通过，107/107；客户端视觉与实际放桶/收桶交互待验。

### 批次 041：盈能紫水晶与料理基础材料（2026-11-06）

- 搬运 `charged_amethyst`、`pergamyn`、`reed_rod`、`rye_seed`、`flour`、`magic_stone`、`salt`、`coarse_salt` 共 8 种物品；保留来源注册 ID、纹理、物品模型与创造模式物品栏顺序。
- `reed_rod` 保留来源 100 tick 燃烧时间；`magic_stone` 补回来源的提示文本。8 张物品纹理从来源逐字节复制，SHA-256 一致。
- 补齐不依赖未迁移机器的配方：盈能紫水晶、盈能紫水晶块 4:1 互转、魔法石（钻石/冰凌晶芽 + 灵魂粉尘）、羊皮纸、盐与盐块互转、芦苇杆合成纸；盐/面粉的 `c:salt`、`c:flour`、`c:flour/wheat` 标签同步补齐。研磨机（mortar）相关面粉与粗盐提纯配方、依赖未迁移结构/物品的获取途径暂缓。
- `runData`、`build` 通过；客户端视觉与生存获取链待游戏内验收；`git diff --check` 通过。

### 批次 040：染梦世界剩余方块（2026-11-06）

- 搬运染梦世界物品栏剩余 39 种方块：琥珀糖矿石、粉顶菌方块/菌柄/菌光体、染梦冰/浮冰、巨型泡泡、染梦砂岩与切制/雕纹/平滑全套建材、染梦水晶灯/灯笼、染梦玻璃与雕花/镶金全套玻璃板、染梦水晶砖全套建材、冰晶岩/冰凌晶芽、云朵/乌云、方解石锥、盐块与盈能紫水晶块；保留来源注册 ID。
- 按来源顺序重排染梦世界物品栏；暗影方块因独立暗影物品栏尚未迁移，暂留栏尾避免创造模式检索丢失。眠椰相关、蔚蓝海沙与狐雕塑按约定不纳入本批。
- 方解石锥沿用来源 `CalciteConeBlock` 的含水、厚度堆叠、下落、滴水、生长、摔落伤害与三叉戟击碎行为；1.21 将 `isPathfindable` 改为 `(BlockState, PathComputationType)`。玻璃与冰使用 `TransparentBlock`/`HalfTransparentBlock`，模型按来源补 `translucent`/`cutout` 渲染类型；三种玻璃板物品模型同步补 `translucent`，修复物品栏透明通道渲染。
- 依赖尚未迁移物品的配方与特殊掉落暂缓：琥珀糖矿石、粉顶菌方块暂自掉落；盐块、盈能紫水晶块、云/乌云、冰凌晶芽仅完成方块本体与标签，盐/充能紫水晶/云作物/厚云/魔法石相关配方待后续批次。狐雕塑 GeckoLib 链路单独暂缓。
- `runData`、`build` 通过；`runGameTestServer` 105/105 通过。28 张来源纹理与 5 个方解石锥模型逐字节复制且 SHA-256 与来源一致；客户端视觉待游戏内验收。

### 批次 039：方解石系列矿石（2026-11-06）

- 搬运方解石煤矿石、铁矿石、铜矿石、金矿石、红石矿石、青金石矿石、钻石矿石、绿宝石矿石 8 种矿物；保留来源注册 ID、贴图、模型、语言与创造栏顺序。
- 保留来源矿物数值：硬度 3.0/抗性 3.0，煤矿石经验 0~2、青金石 2~5、钻石/绿宝石 3~7；红石矿石沿用独立 `CalciteRedstoneOreBlock` 的点亮、踩踏/破坏/右键激活、随机熄灭与粒子行为，1.21 交互改由 `useItemOn` 返回 `ItemInteractionResult`。
- 补齐原版矿石掉落、8 个标准矿物标签与地面标签、石/铁工具等级标签，以及 16 个熔炉/高炉配方（铁、铜、金、煤、红石、青金石、钻石、绿宝石）。
- `runData`、`build` 通过；`runGameTestServer` 105/105 通过，新增矿物标签、红石点亮、矿石掉落与熔炼/高炉成品的测试；同时把风装备测试的箭矢检索按 owner 过滤，避免新增测试改变结构排布后与相邻测试串扰。8 张来源纹理与上游 Git blob 一致；客户端视觉与自然生成待游戏内验收。

### 批次 038：染梦木板与方解石建材系列（2026-11-06）

- 搬运染梦木板、楼梯、台阶、栅栏、栅栏门、窗格、门、活板门、压力板、按钮 10 种木建材，以及磨制方解石、方解石瓦各自的全套楼梯/台阶/墙 8 种石建材；保留来源注册 ID、贴图、模型、语言与创造栏顺序。
- 补齐 28 个配方：原木/木头/染料转木板，木板全套木建材，玻璃板加木板合成窗格，方解石加工与两套建材及切石机配方；同步补齐斧/镐采集、木板与木制功能标签、楼梯/台阶/墙标签和方块战利品。
- 1.21 构造器按平台调整顺序与参数：门、活板门、压力板使用 `BlockSetType`，栅栏门使用 `WoodType`，按钮不再保留旧 `arrowsOnly` 参数；栅栏与按钮物品模型改由 Provider 生成。
- `runData`、`build` 通过；`runGameTestServer` 103/103 通过，新增木石建材标签、配方产物数量、双层台阶与门掉落实测。7 张来源纹理与上游 Git blob 一致；客户端视觉待游戏内验收。

### 批次 037：同步上游 #1276 自定义弓蓄力视角修复（2026-11-06）

- 按上游 `3239cccc` 增加客户端 FOV 事件处理；已迁入的萦风弓按原版蓄力曲线缩放视角，并遵从“视角效果”强度设置。
- 融梦弓尚未迁入，待其迁移时接入同一处理器；同步补充程序规范说明。

### 批次 036：染梦地形方块与染梦果（2026-11-06）

- 搬运染梦泥土、染梦草方块、染梦耕地、染梦原木、染梦木头、染梦树叶、染梦世界树树叶、染梦沙与染梦果；保留来源注册 ID、贴图、模型、语言与创造栏顺序。
- 草方块与耕地沿用来源行为：草方块蔓延与骨粉植被、耕地保湿与踩踏回泥；锄头右键染梦泥土/草方块可转为染梦耕地。
- 染梦果接入 `PasterDreamFoodItem` 的食用、SAN/融梦能量提示与生命恢复效果；新增染梦泥土/草方块、树叶与染料转换配方，以及原木合成染梦木头。
- 树叶战利品暂时只保留果实与木棍掉落，来源中的染梦树苗掉落待 T16 树苗与树木数据迁入后补回；染梦沙使用 1.21 `ColoredFallingBlock`，`canSustainPlant` 与耕地破坏事件按 NeoForge 1.21 API 适配。
- `runData`、`build` 通过；`runGameTestServer` 101/101 通过，新增地形标签、草方块/耕地/树叶掉落与锄头耕地转换测试；客户端视觉与真实世界生成待游戏内验收。

### 批次 035：便携储物袋与四件饰品（2026-11-06）

- 搬运便携储物袋、大型便携储物袋、暗影之息、战争旗帜、啵啵鸡的华丽飞羽与风精灵；保留来源注册 ID、贴图、模型、语言与创造栏顺序。
- 储物袋内容改存于 1.21 `DataComponents.CONTAINER`，新增 `RegisterCapabilitiesEvent` 物品 `IItemHandler`、9/25 格菜单与界面、袋体烧毁散落和大型袋生物捕获/释放配置。
- 暗影之息接入理智比例加成与实时 Tooltip；战争旗帜接入战意效果、击杀叠层和范围共享；啵啵鸡提供瞬身术属性与回避粒子/音效；风精灵提供箭矢速度与装填缩减。
- 补充 `ARROW_VELOCITY`、`MAGIC_DAMAGE_RATE` 自定义属性，以及储物袋配方、战意效果图标与 `zipper` 音效；新增 `MagicDamageHandler`，使投掷伤害药水等间接魔法伤害按魔法伤害倍率缩放。
- 来源进度 `get_boboji_curios`、JEI 暗影之息信息与宝库/风之旅途宝箱战利品表暂未迁移；当前可从创造栏与储物袋配方取得。
- `runData`、`build` 通过；`runGameTestServer` 99/99 通过，新增储物袋内容往返、槽位/配方、饰品属性、暗影之息、战意效果与间接魔法伤害测试。

### 批次 034：三件腰带与三枚戒指（2026-11-06）

- 搬运自然腰带、旅者腰带、迷梦旅者腰带、强击戒指、反击戒指与融梦光环戒指；接入 Curios `belt` / `ring` 槽位、贴图、模型、语言与创造栏。
- 保留自然腰带的理智光环与损耗、旅者腰带的瞬身消耗、迷梦旅者腰带的移速与冷却缩减、强击戒指的分级攻击力、反击戒指的闪避反击以及融梦光环戒指的能量回复。
- 迁入反击效果与伤害结算钩子；强击戒指的等级改存于 1.21 `CUSTOM_DATA`，品质随等级写入 `RARITY` 组件，并保留分级模型切换。
- 自然、旅者腰带，强击戒指两级与融梦光环戒指配方已生成；迷梦旅者腰带配方依赖尚未迁入的迷梦睡莲，暂缓接回。
- 装备栏按来源现存条目相对顺序重排极锋染梦合金剑、融梦工具与染梦装备的位置；未迁入条目跳过。
- `runData`、`build` 通过，`runGameTestServer` 94/94 通过；新增测试覆盖槽位、五条配方、穿脱属性与反击伤害。

### 批次 033：八件蝴蝶与项链饰品（2026-11-06）

- 搬运琴雨梦的蝴蝶星发卡、夜明蝶、光明飞蝶、雪兔脚项链、轻羽护符、生命项链、业火项链和十字章项链；接入 Curios 槽位、贴图、模型、语言与创造栏。
- 保留发卡防主动锁定、夜明蝶低亮度耗能夜视、光明飞蝶常驻夜视与亮度提升及黑暗免疫、五件项链的来源被动；接通雪兔脚项链原配方。
- 其余来源配方与箱子奖励依赖未迁入材料或战利品体系，分项待后续迁入；客户端亮度、佩戴视觉和多人行为待实测。
- `runData`、`build` 通过，`runGameTestServer` 92/92 通过；夜明蝶暗室耗能与夜视已由 GameTest 验证。

### 批次 032：七件基础饰品（2026-11-06）

- 搬运纯金、邪眼、海波、龟壳护身符，世界树种荚、雪下的誓言与花环；接通 Curios 槽位、物品被动、雪下的誓言效果、贴图、模型、语言与创造栏。
- 补齐玩家的八类 Curios 槽位分配和两个戒指位，恢复海波、龟壳护身符的自动属性提示所需槽位查询；回归测试验证两件饰品的属性修饰符可由 Curios 读取。
- 创造栏按来源将四种原胚放回物品栏，七件饰品按来源顺序列在装备栏后段的饰品区，折翼天使雕像随后。
- 纯金、邪眼、龟壳护身符和花环可按来源合成；海波护身符的来源配方等待水罐系统。世界树种荚依赖未迁入的染梦维度与世界树叶，种荚及雪下的誓言的来源掉落也待对应产线迁入。
- `runData`、`build` 通过，`runGameTestServer` 89/89 通过；开发客户端已确认海波、龟壳护身符的自动属性提示显示。佩戴外观、多人效果及世界树种荚触发待游戏内验证。

### 批次 031：灵魂矿土与饰品原胚（2026-11-06）

- 迁入灵魂矿土及灵魂粉尘、灵魂精华和四种饰品原胚，保留来源 ID、贴图、提示与 Curios 槽位。
- 灵魂矿土仅在灵魂沙峡谷替换灵魂土，矿脉大小 16、每区块 8 次、Y=0～128；挖掘掉落 1～2 灵魂粉尘并受时运加成，精准采集保留方块；熔炉/高炉可将矿土炼为粉尘，粉尘可合成精华及四种原胚。
- runData、build 和 runGameTestServer 86/86 通过；真实世界生成、客户端纹理与 Curios 佩戴视觉待游戏内验收。

### 修复：精铸工坊工作台物品提示（2026-11-06）

- 恢复工作台界面在绘制槽位后调用 `renderTooltip`，鼠标悬停输入、强化石、输出和玩家背包物品时可显示提示。
- `runData`、`build` 通过，数据生成写入 0 个文件；客户端实际悬停显示待人工确认。

### 批次 030：影蚀工具与暗影合金工具材料（2026-11-06）

- 搬运影蚀匕首/镐/斧/锹/锄、暗影合金镐/斧/锹/锄，以及黑金属锭/粒、暗影合金锭的注册、属性、贴图、模型、语言、标签与创造栏；噩梦燃料沿用批次 022 已迁的 1200 tick 燃烧与掉落实现。
- 接通影蚀工具的低亮度和暗影/深板岩/幽匿类方块挖速加成、SAN 波动和 +1 方块触及；暗影合金工具继承这些特性，并增加低 SAN 挖速/伤害倍率与耗 SAN 修补。挖速倍率由服务端同步到客户端。
- 接通黑金属粒与锭互转、五件影蚀工具的精铸工坊配方，以及四件暗影合金工具的有序组件保留升级配方。暗影合金锭配方按约定待蓝图与暗影高炉完成后再迁。
- `runData`、`build` 和 `runGameTestServer` 84/84 通过；首份黑金属粒及暗影合金锭生存获取、客户端挖掘动画与多人行为待后续验证。

### 批次 029：同步上游 #1274 强化光灵箭修复（2026-11-06）

- 同步上游 PR #1274（合并提交 `2ee22c23`）：强化武器在能量充足时不再因弹药是光灵箭而回退原版实体，避免「能量已扣但强化失效」。
- 本仓库当前仅萦风弓已迁入，按 `FluffyWindArrowEntity` 架构适配：新增同步弹药栈，命中光灵箭施加 200 tick 发光，渲染器按弹药使用原版光灵箭贴图，并保留光灵箭拾取物；融梦弓、暗影合金弩随其迁移实现，暗影合金弩设计文档已先行同步。
- 新增强化光灵箭 GameTest；`runData`、`build` 通过，GameTest 81/81 通过。客户端贴图与发光表现待实测。

### 批次 028：修复自定义品质星级提示（2026-11-06）

- 恢复来源 `AddToolTip` 中遗漏的自动品质行：物品使用 `ModRarities` 自定义稀有度时，在物品名下显示对应品质名称与星数；原版稀有度不添加该行。
- 新增 GameTest 验证传说品质七星提示和原版物品不受影响；`runData`、`build` 通过，GameTest 80/80 通过。客户端实际悬停显示待验收。

### 批次 027：萦风弓、风行者披风、萦风合金护甲与折翼天使雕像（2026-11-06）

- 迁入萦风弓与强化箭：消耗融梦能量、无重力、命中飘浮、引雷附魔落雷与普通箭回退；以 1.21 的弓弹射物钩子和数据驱动附魔标签适配来源行为。强化箭保留来源的青绿色渐变三棱柱拖尾及命中后收缩消失。
- 迁入四件萦风合金护甲及满套天气加成、疾射、落雷防护、弹射物偏转和空中缓降；迁入风行者披风的鞘翅飞行、空中加速与撞墙保护。接入客户端空格输入、披风层抑制、GeckoLib 模型与动画。
- 迁入折翼天使雕像 Curios 饰品：摔落免疫、受击概率无敌、瞬身术即时装填主手弩；弩弹药处理沿用原版多重射击与无限弹药规则。补齐萦风弓、披风、脉冲风行者水晶及四件护甲的配方、标签、模型、翻译与来源资源。
- `runData`、`build` 通过，`runGameTestServer` 79/79 通过。雕像与披风依赖尚未迁入的风骑士奖励，风之旅途原料自然来源尚未接通；客户端视觉、实际飞行和多人行为待实测。

### 批次 026：瞬身术基础系统（2026-11-06）

- 迁入 C 键瞬身术的客户端按键、服务端请求、位移、属性消耗与冷却，以及回避效果的免伤、成功回避音效和粒子尾迹；保留来源的注册 ID 与数值。
- 迁入回避音效及回避效果图标原文件，补中英文按键、效果、属性与字幕翻译；修正 NeoForge 提前触发伤害事件导致贴身史莱姆连续碰撞时回避特效出现但仍掉血的问题，恢复原版受击无敌时间与上次受击量判定；数据生成、构建和 GameTest 77/77 通过。
- 回避衣装、折翼天使雕像、转身衣装与反击饰品的联动随各自内容后续迁入；客户端按键与视觉、多人与实际操作手感待实测。

### CI 修复（2026-11-05）

- 在 `.gitattributes` 和 `.editorconfig` 中为 Unix Gradle 启动脚本 `gradlew` 单独设置 LF 换行，避免 Linux CI 检出或编辑器保存时被仓库默认 CRLF 规则转换；其余文本文件继续遵循现有规则。
- 此修复针对 GitHub Actions 运行 #42056439200 的退出码 126；具体失败步骤仍需运行日志或后续远程 CI 复核。

### 协议与仓库规范同步（2026-11-05）

- 按上游已合并 PR #1271 同步 PDR 协议第 0 条：定义公开 API、附属模组与独立程序，明确第 3 条优先于第 1 条，并将协议适用范围扩展至使用、分发、附属创作与相关内容发布。
- 强化附属模组条款：即使仅通过公开 API 实现、未复制代码或资源，仍须事先书面许可并登记于上游唯一授权名单；同步 PERMISSION_GRANTED 的协议表述与中英文 README。
- 删除程序规范文档中过时的“代码: MTI / 美术资产: ARR”协议段；新增根目录 `.editorconfig`，统一 UTF-8 无 BOM、CRLF、缩进及行尾空白。
- 本次仅调整协议与仓库规范，不改变内容迁移状态；runData、build 通过，未运行 GameTest。

### 批次 025：精铸工坊工作台、配方系统与强化石（2026-11-05）

- 迁入精铸工坊工作台方块及方块物品、攻击强化石与幸运强化石，保留来源注册 ID、方块参数、掉落和提示文案。
- 迁入 `weapon_workshop_crafting_table` 配方类型/序列化器、物品匹配与处理链路，以及工作台方块实体、菜单、界面、能力注册与客户端合成请求包；工作台自身铁砧/锻造台/岩浆块/切制铜块配方按来源补齐。
- 强化石属性关系与随机加成保留：攻击/幸运在主手提供 1~3 点加成，随玩家幸运缩放；附魔按来源的 2^等级合并规则写入 1.21 附魔组件，攻击/幸运加成同时写入主手属性修饰符。
- 复制工作台方块状态、物品模型、GUI 与两张强化石纹理；方块模型因 1.21 只允许 ±45/±22.5/0 的元素旋转，将两个 90° Y 元素预变换为轴对齐几何并重算面 UV。
- 独立目录客户端启动检查通过，未再出现 `Invalid rotation 90` 或工作台模型缺失；真实游戏内摆放视觉仍待验收。
- 迁入萦风雷矛工坊配方：五件萦风合金工具 + `pasterdream:enhance_stone` 标签强化石 → 雷矛；其余 23 条内置工坊配方依赖尚未迁入的箭矢、护甲或暗影/白水晶内容，继续暂缓。
- 新增精铸工坊 JEI 分类、催化剂与配方展示；客户端进入世界后 JEI 已注册分类并加入工坊配方。
- 强化石来源仍仅为尚未迁入的宝箱战利品，未宣告生存获取闭环；其余五类工坊设备、蓝图/指南书与进度仍待后续任务。
- `runData`、`build` 通过；`runGameTestServer` 73/73 通过（新增 3 项）。客户端视觉、真实数据包配方、JEI 与多人同步待实测。

### 批次 024：风之旅途材料与部分萦风装备（2026-11-05）

- 迁入凝结之风、凝风铁锭/粒、萦风合金锭、萦风合金升级套件与脉冲风行者水晶，保留来源注册 ID、稀有度与不可毁掉落实体。
- 迁入 `ModRarities` 八级品质：通过 NeoForge 枚举扩展注册 `pasterdream:common`～`pasterdream:miracle` 自定义稀有度，提供品质名称、星级 tooltip 与按名称/等级查询；萦风雷矛使用传说品质。
- 迁入凝风铁五件工具；迁入萦风合金五件工具及「疾风过境」「风卷掉落」「连锁破坏」「春风化雨」行为，接入共享战技冷却、挖掘速度同步和战技伤害倍率。
- 补齐可闭合配方：凝风铁五件工具直接配方、萦风五件工具锻造配方、萦风合金升级套件、萦风合金锭、凝风铁锭/粒互转；雷矛配方仍按约定不迁。
- 仍需依赖未迁内容的配方暂缓：凝风铁锭（风植萃取液）、凝结之风（风矿/方块）、脉冲风行者水晶（折翼天使雕像）、萦风弓与相关方块；后续随对应内容接入。
- 迁入萦风雷矛三形态（近战、激流式突进、投掷连锁闪电），含落雷载体、投掷虚影、GeckoLib 物品/实体渲染与 +2 触及；萦风弓整体暂缓。
- `runData`、`build`、`runGameTestServer` 70/70 通过；客户端视觉、真实战斗手感与多人同步待实测。

### 批次 023：铜工具、铜装备、融梦水晶装备与生命水晶（2026-11-05）

- 迁入铜剑、镐、斧、锹、锄与四件铜护甲，保留来源数值、九条工作台配方、物品标签、模型、翻译及纹理。
- 迁入四件融梦水晶护甲及整套效果：逐件生命值、融梦能量上限和恢复，融梦修补、生命恢复 II、染梦工具增强与理智光环。
- 迁入可放置、可吸收的生命水晶，含方块实体、动画、音效、掉落和 40 tick 吸收后永久增加 2 点生命上限的行为；融梦护甲四条锻造配方已接通。
- 生命水晶的自然结构与水晶箱奖励仍待迁入；客户端视觉和多人行为待实测。

### 批次 022：SAN、设备栏与休憩（2026-11-05）

- 玩家理智随光照、群系、装备与效果变化。接入两套 SAN HUD、低理智视听效果（疯狂画面绘制在快捷栏下方）、命令、死亡恢复与暗影生成。三种恐怖尖喙和暗影之手按理智与难度在夜间出现。
- 设备创造栏收录娇小琴雨梦玩偶、娇小幼幼紫玩偶、失落剑冢和荒漠英雄之墓。右键玩偶恢复 1 点理智，靠近获得休憩 II；点燃的营火和夜间床提供休憩 I。
- 原版四种汤煲接入理智和料理效果。恐怖尖喙可掉落噩梦燃料、阴影或厚重阴影。

### 协议同步（2026-11-03）

- 按上游已合并的 `3a2db069` 同步 PDR 协议第 3.1 条：附属模组需书面许可并登记于上游唯一的授权名单；NeoForge 仓库保留完整协议文本及本仓库申请入口。
- README 中英文版指向同一名单，并提示授权范围需写明适用版本与加载器；本次仅调整协议与文档，不改变内容迁移状态。

### 工作流调整（2026-11-03）

- NeoForge 移植的提交与推送改由用户亲自执行；代理交付未提交的改动、验证结果和建议提交信息。

### 批次 001：数据层与钛金基础材料

- 迁入粗钛、钛金锭/粒、两种钛矿及两个储存块，保留来源 ID、物理参数、挖掘等级和掉落行为。
- 新增 datagen、标签与 worldgen bootstrap：12 个配方及解锁进度、模型、语言、标签、战利品和主世界深层钛矿生成。
- 复制 7 张来源纹理，替换 MDK 示例翻译；两个创造模式物品栏仅显示已迁入内容。
- 增加独立 GameTest 源码集与 4 项服务端集成检查，测试和缓存不进入发布 Jar；CI 增加数据生成一致性和 GameTest 检查。
- 新增 AGENTS 入口、迁移批次记录，明确不维护 MCR 原作档案、上游 PR/master 同步和 NeoForge 可直接推送的规则。
- 钛金工具/护甲与染梦维度仍待迁移，客户端视觉待验证。详细验证结果见迁移批次记录。

### 批次 002：上游文档与许可同步（2026-10-23）

- 按上游 `9f121ee`（PR #1217）同步 `document/`：新增 14 个设计、需求、参考与规范文档，更新 9 个内容文档。
- 架构规范、目录结构组织、程序规范文档、重写项目结构说明、容器配方平衡系统采用三方合并，保留 NeoForge 适配并吸收上游新增规范。
- 同步 `src/main/resources/ASSETS_MANIFEST.md`，按上游清单补全重绘/新增纹理；根目录许可切换为 PDR 协议，并同步 README、README_EN、PERMISSION_GRANTED。
- 更新 `neoforge.mods.toml` 致谢与可选 Iris 依赖，`gradle.properties` 的 `mod_license` 改为 PDR/ARR 描述。
- 本批仅文档与许可变更，不含 Java、实际资源文件、注册或世界生成调整；迁移进度不变。

### 批次 003：炙焰金基础材料与下界矿脉（2026-10-23）

- 迁入粗炙焰金、炙焰金锭/粒、矿石和储存块，补齐 8 个配方、解锁进度、模型、语言、标签、战利品及创造模式条目；保留来源数值和 `moltengold_*` 历史配方 ID。
- 迁入下界炙焰金矿脉：下界岩替换、矿脉大小 16、每区块 4 次、Y=0～64，保留来源生成阶段；复制 5 张已登记来源纹理。
- 新增 4 项炙焰金 GameTest，连同钛金回归共 8/8 通过；`runData`、生成一致性、`build` 与独立客户端启动/资源加载检查通过。客户端实际视觉、自然矿脉分布和多人行为待验证。
- 上游审阅游标推进至 PR #1218（`76447a3`）；箭矢行为重构及配套文档/skill 已登记为待同步，未声明已移植。炙焰金工具、食物、剧情进度和指南书仍待迁移。

### 批次 004：钛金工具与组件保留升级配方（2026-10-23）

- 迁入钛金五件工具、黑石棍、钛金升级套件和装备创造栏，补齐 13 个配方、解锁进度、工具标签、中英名称与 7 张来源纹理。
- 建立 1.21 工具等级与无序升级配方链路：保留附魔、名称等组件修改；工作台升级重置损伤，锻造升级按来源保留损伤，产物沿用钛金默认属性。
- 新增 5 项 GameTest，共 13/13 回归通过；数据生成一致性、构建和客户端资源加载检查通过，本批实际视觉/交互与多人联机待实测。
- 记录用户对批次 003 的“游戏内没问题”确认；自然矿脉统计与多人验证继续单独记录。上游仍为 `76447a3`，#1218 箭矢待办状态不变。

### 批次 005：染梦基础素材（2026-10-29）

- 迁入染梦石英、染梦粉尘、染梦粉尘碎片、粉色史莱姆球与染梦染料，补齐基础物品模型、中英文名称、创造模式条目及 5 张原样来源纹理。
- 上游本地 `origin/master` 已到 `abe0ab9`（PR #1247）；这组物品的注册与纹理在 `76447a3..abe0ab9` 间未见修改，但整体上游增量影响尚未全部审阅，游标暂不推进。上游页面检索显示开放 PR 为 0，实时状态受网络访问限制。
- 本批不含依赖研钵、蓄梦器或梦境炼药系统的配方，也不迁入食物/自定义行为；验证与遗留事项见迁移批次记录。

### 规划批次 006：迁移依赖任务线（2026-10-29）

- 新增 `document/reference/迁移任务线.md`，以 T00–T22 明确系统前置、物品/方块解锁、获取闭环与验收，提供近期批次顺序和后续执行模板。
- 固定“低 API 差异直接复制来源原类、保持字段/方法/语句顺序，仅做必要 API 适配”的执行规则，同步 AGENTS、程序规范、总纲和 `port-content` 工作流。
- 定向核对研钵、流体配平、护甲效果保护、设备、饰品与传送依赖；纠正粉尘互转无需等待研钵的规划和来源 SHA 抄写错误。联网复核正式上游至 `7819bc6`（#1249）、开放 PR 为 0，登记 #1248/#1249 传送与裂隙增量。纯文档变更，不增加玩法、不跨过既有审阅缺口推进全局游标。

### 批次 007：染梦石英矿、粉尘矿与基础配方（2026-10-30）

- 按正式上游 `b76807f` 迁入两种染梦矿石及方块物品，补齐来源纹理、模型、翻译、创造栏、镐/铁镐要求、矿石分类标签及精准采集/时运/爆炸衰减掉落。
- 新增粉尘与小撮粉尘 1↔9 工作台配方，以及两种矿石各自的熔炉配方；保留来源 ID、产出、经验与耗时。
- `runData`、`build` 和 15/15 GameTest 通过。矿石自然生成及生存获取仍随染梦世界任务迁移，客户端视觉、真实世界分布和多人验证待完成；细节见迁移批次记录。

### 批次 008：染梦石英建材系列（2026-10-30）

- 迁入染梦石英的 8 个建材方块与方块物品，补齐 16 条熔炼、工作台和切石机配方，以及模型、标签、掉落、翻译、创造栏和 7 张来源纹理。
- `runData`、`build` 和 16/16 GameTest 通过。石英首份自然获取仍随染梦世界迁移；客户端视觉、真实生成和多人验证待完成。结束时实时确认正式上游仍为 `b76807f`、开放 PR 为 0，详细来源和适配记录见迁移批次记录。

### 仓库维护：生成资源退出版本控制（2026-10-30）

- `src/generated/` 改由 `runData` 在本地及 CI 生成，不再跟踪生成 JSON；CI 改为检查关键生成资源存在后构建。

### 批次 009：炙焰金工具（2026-10-30）

- 按正式上游 `b76807f` 迁入炙焰金五件工具及共享右键逻辑；剑命中点燃 3 秒，其余四件工具使用时增加 4 点耗竭、给予 400 tick 急迫并设置 200 tick 冷却。
- 补齐五条来源工作台配方、模型、工具标签、中英文名称与提示、创造栏条目，原样复制五张来源纹理。
- `runData`、`build` 与 19/19 GameTest 通过；客户端实际视觉及多人交互仍待验证。详细来源与验证边界见迁移批次记录。

### 仓库维护：标签 Provider 与来源风格对齐（2026-10-30）

- `ModBlockTagsProvider` 与 `ModItemTagsProvider` 改用 Forge 1.20.1 来源类的链式标签写法、分组和注释风格；保留 NeoForge `c:` 标签适配与现有标签内容。`runData` 写入 0 个资源文件，`build` 通过。

### 批次 010：钛金护甲与效果保护（2026-10-30）

- 迁入四件钛金护甲、套装效果和效果移除保护，适配 1.21 的护甲材质注册、玩家护甲槽与 NeoForge 效果施加事件；补齐四条保留组件的钻石护甲升级配方。
- 补齐模型、标签、中英文名称和提示、创造栏及 7 张原样来源纹理。`runData`、`build` 和 23/23 GameTest 通过；护甲板直合/锻造分支仍待布料链，客户端穿戴视觉与多人行为待验证。

### 批次 011：钛金护甲材料与配方链（2026-10-30）

- 迁入棉花、线轴、纺织布料与护甲板物品，补齐线轴→布料→护甲板的 3 条配方，以及四件钛金护甲的护甲板直合和锻造配方共 8 条；保留来源靴子配方的历史 ID 与仅消耗钛金锭的输入。
- 原样复制 4 张来源纹理，补齐模型、翻译和创造栏。`runData`、`build` 与 25/25 GameTest 通过。棉花作物按本批范围未迁，首份棉花的生存获取和客户端视觉仍待验证。

### 批次 012：粉色粘液块与压缩配方（2026-10-30）

- 迁入粉色粘液块、透明模型、来源贴图和 9↔1 压缩配方，保留重力与物理参数；1.21.1 使用原版 `ColoredFallingBlock` 承接来源行为。
- `runData`、`build` 和 26/26 GameTest 通过；首份粉色粘液球的自然获取、客户端透明视觉及多人行为仍待验证。详细来源与适配记录见迁移批次记录。

### 批次 013：染梦合金工具与材料链（2026-10-30）

- 迁入染梦合金锭粗胚、染梦合金锭、染梦合金粒、染梦晶芽粒与染梦升级套件，以及染梦合金剑、镐、斧、锹、锄和锤共 6 件工具。
- 补齐 12 条来源配方：粗胚无序合成、熔炉/高炉冶炼、锭↔粒、升级套件，5 件钛金工具锻造升级和锤的工作台合成；保留来源 ID、数量、分类与顺序。锤保留 3×3×3 同类方块范围挖掘与逐块耐久消耗。
- 补齐手持/普通模型、工具与材料标签、中英文名称及锤提示、创造栏条目，原样复制 11 张来源纹理。`runData`、`build` 与 31/31 GameTest 通过；晶芽粒/粉尘的自然获取、合金块与极锋剑、染梦套装增伤联动、客户端视觉和多人行为仍待后续批次或验证。

### 批次 014：染梦晶芽、母岩与染梦合金块（2026-10-30）

- 迁入染梦母岩、小/中/大三种染梦晶芽、染梦水晶块与染梦合金块共 6 个方块及方块物品，保留母岩随机刻生长与晶芽贴附、发光；按上游 PR #1252 让母岩仅精准采集掉落自身。
- 补齐方块状态/模型、cutout 晶芽模型、战利品、挖掘/信标/储存标签、创造栏、中英文名称和 4 条来源配方；原样复制 6 张来源纹理和 2 个来源方块类。
- `runData`、`build` 与 34/34 GameTest 通过；母岩无合成、真实世界生成及晶芽粒生存获取仍待世界生成接通，客户端视觉与多人行为待验证。详细来源与适配记录见迁移批次记录。

### 仓库维护：文档来源收敛（2026-10-30）

- 删除 ID 映射表、`mapping.json` 及映射生成/查询/结构转换工具约定；搬运时保留 Forge 来源已有 ID，新增内容直接按命名规范注册。
- 删除基于 MCR 原作的分析文档，包括原模组创造物品栏、源项目结构分析，以及风之旅途、暮影之笼、暗影地牢、亚伦柯斯之触/竞技场、无名实体等搬运分析与任务书。
- 统一来源口径：直接迁移来源为 Forge 重生版；“原模组”指 MCR 原作，`NOT_MODIFY/` 是其档案，不属于搬运对象，本仓库不创建、不查阅、不维护。同步清理失效引用与旧 ID 重命名指引。
- 删除 `document/design/新内容列表.md`，新内容信息以上游来源为准；策划文档的“重写大体流程”简化为当前进度说明。

### 批次 015：染梦合金护甲与套装增伤（2026-10-30）

- 迁入染梦头盔、胸甲、护腿、靴子与 `dyedream` 护甲材质，以及染梦守护、染梦工具增强两个效果；补齐 8 条护甲板工作台/锻造配方、模型、护甲与效果标签、中英文名称和提示、创造栏及 8 张原样来源纹理。
- 保留防御 3/8/6/3、耐久倍率 40、附魔值 22、韧性 3.0、击退抗性 0.1 与防火；整套刷新最大生命值 +4、每 30 秒吸收 I，并让染梦合金工具伤害 ×1.5。融梦水晶工具尚未迁入，其增伤分支已留 TODO。
- `runData`、`build` 与 39/39 GameTest 通过；染梦晶芽粒来源未接通，护甲生存获取链仍未闭合，客户端穿戴视觉与多人行为待验证。详细来源与适配记录见迁移批次记录。

### 批次 016：融梦水晶工具、极锋剑战技与融梦能量（2026-10-30）

- 迁入融梦水晶镐、斧、锹、锄及极锋染梦合金剑，保留等级数值、能量修补、凌空斩击、共享战技冷却与染梦守护增伤联动；补齐五条来源锻造配方、标签、模型、翻译和创造栏。
- 融梦能量转为 NeoForge 玩家 Attachment，接入持久化、死亡复制、仅本人同步、属性光环、调试命令及 HUD；复制来源工具、碎片、粒子、音效和 HUD 资源。
- `runData`、`build` 和 42/42 GameTest 通过。融梦碎片自然获取与放置实体、客户端实际视觉及多人/重登验证待后续批次；详见迁移批次记录。
- 补齐融梦能量 HUD 的 Alpha 混合渲染与 7 项客户端配置的来源注释；开发客户端已确认生成 TOML 配置。

### 批次 017：回响套装、回响之心与回响升级套件（2026-10-31）

- 迁入回响头盔、胸甲、护腿、靴子与 `sculk` 护甲材质，以及回响之心、回响升级套件和 `sculk_armor` 幽匿回响效果；保留防御 3/8/6/3、耐久倍率 42、附魔值 9、韧性 3.5、击退抗性 0.15、防火与回响之心修复。
- 补齐 1 条升级套件工作台配方和 4 条下界合金护甲锻造配方，保留来源 ID、模板/基底/追加物与组件保留行为；套装刷新最大生命值 +4、深层抗性、黑暗转换、失明免疫与监守者免伤/取消仇恨。
- 补齐监守者掉落回响之心的 `LivingDropsEvent`、两个 Mixin、模型、护甲/效果标签、中英文名称与 7 条提示、创造栏，并原样复制 9 张来源纹理。`runData`、`build` 与 48/48 GameTest 通过；客户端穿戴视觉、真实监守者交互与多人行为待验证。

### 批次 018：融梦水晶碎片完整功能与融梦星币（2026-10-31）

- 修正融梦水晶碎片为史诗品质、附魔光效并补回来源提示；补齐潜行放置水晶簇、交互/死亡掉落碎片、独立不可破坏掉落物实体与天赋/粒子/动画/音效表现。
- 移植融梦星币与融梦星币堆：10 枚星币右键合堆、星币堆右键拆分、丢弃与兑换音效、面额提示及来源中英文文案；补齐物品模型、纹理与创造栏条目。
- 新增本仓库首个 GeckoLib 实体链路（`ModEntities` → `ModEntityRenderer` → `ClientModEvents`），并注册 `melt_dream_crystal` 粒子 Provider。`runData`、`build` 与 48/48 GameTest 通过；客户端实体视觉、真实放置/拾取与多人行为待验证。

### 批次 019：狱炎工具、引潮剑、『北海若』引潮与海洋材料（2026-10-31）

- 迁入狱炎剑、狱炎镐、引潮剑、『北海若』引潮、远古守卫者鳞片、蔚蓝海洋之心与蔚蓝露滴，保留来源等级数值、点燃叠加、右键急迫、水下呼吸、北海潮涌、蓄力投掷、激流突进、忠诚/引雷与潮涌能量 II。
- 新增『北海若』引潮投掷物实体与剑模型渲染器，接入 1.21 `AbstractArrow` 拾取/保存同步；补入来源有序组件保留配方类，狱炎升级保留组件并重置损伤。
- 补齐 5 条来源配方、剑/镐/战技冷却/三叉戟附魔标签、模型、中英文名称与提示、创造栏，原样复制 7 张来源纹理；远古守卫者新增鳞片掉落，蔚蓝露滴可饮用获得水下呼吸。
- `runData`、`build` 通过且重复生成稳定；`runGameTestServer` 52/52 通过（新增 4 项）。客户端投掷物视觉、多人同步、真实守卫者掉落与深海秘宝获取仍待验证；蔚蓝露滴生存来源依赖未迁的深海秘宝战利品表。

### 批次 020：融骸狱炎剑、草薙系列、失落剑冢与剧毒（2026-10-31）

- 迁入『融骸』狱炎剑、草薙、『天丛云』草薙、失落之剑与失落剑冢方块，补入天丛云毒刺射弹实体/模型/渲染器、剧毒效果、粒子与音效。
- 新增狱炎剑锻造、草薙、丛林孢子与失落剑冢 4 条来源配方；丛林孢子作为食物迁入，新建食物创造栏并暂用蔚蓝露滴作图标。
- 失落剑冢结构与模板按来源放入静态数据目录；草薙击杀数改用 `CUSTOM_DATA`，剧毒保持不占用无敌帧的伤害结算。
- `compileJava`、`runData`、`build` 通过；`runGameTestServer` 54/54 通过（新增 2 项）。客户端视觉、结构自然生成、多人同步与丛林孢子首份生存来源待验证；详见迁移批次记录。

### 批次 021：朔漠剑、荒漠英雄之墓与断裂英雄剑（2026-10-31）

- 迁入 `desert_sword`、`chenjingmen_desert_sword`、`broken_hero_sword`、荒漠英雄之墓方块/方块物品、`rice_cake` 与灵魂粒子；保留来源 ID、耐久、攻击参数、品质、防火与提示。
- 荒漠英雄之墓按来源实现分玩家进度、延迟对话、清理掠夺者/尸壳、10 块年糕、附近马匹、村庄英雄和朔漠剑交换；GeckoLib 方块实体/物品渲染、动画、灵魂粒子和 custom 模型接线完成。
- 1.21 适配：`CUSTOM_DATA` 代替技能 NBT、组件复制代替整段标签复制、`Holder` 附魔查询、方块 `codec`/`useItemOn`/`useWithoutItem`、GeckoLib 4.9.2 包名及 `HolderLookup.Provider` 存取。
- 按用户范围仅注册年糕物品与提示，不迁其获取方式；断裂英雄剑的来源获取（融梦水晶箱战利品）仍依赖 T17，故本批不宣告生存获取闭环。
- `runData`、`build` 通过；`runGameTestServer` 56/56 通过（新增 2 项）。客户端 GeckoLib 视觉/动画、灵魂粒子、真实交互与多人同步待验证；上游开始 `c97b536`、结束 `1e10ec1`，开放 PR 查询因 403 未实时确认。

### 基础骨架已完成

- **环境与元数据**：依赖 jar 放入 `libs/`、`gradle.properties`、`neoforge.mods.toml` 依赖声明
- **项目文档**：README（中/英）、协议（LICENSE / PERMISSION_GRANTED / LICENSE_ASSETS / ASSETS_MANIFEST）、程序与架构规范文档、11 个搬运 skill 适配迁移
- **移植总纲**：API 差异清单、目录映射、骨架搭建阶段、风险清单
- **Phase 1 基础骨架**：
  - 主类 `PasterDreamMod` 重构（`MOD_ID`、构造注入 `IEventBus`/`ModContainer`、两个 Config）
  - `Config` / `config/PasterDreamClientConfig` 空壳
  - 客户端入口 `PasterDreamRebornClient`（配置界面）
  - `init/` 注册中枢：方块、物品、物品栏、方块实体、实体、流体、菜单、配方、音效、药水效果、药水、属性、粒子类型、Feature、树叶放置器、树木装饰器、DataComponents、AttachmentType
  - 空操作占位：GameRules、CriteriaTriggers、Commands、流体容器/属性关系、笔记/蓝图/作物/强化石关系、暗影地牢结构集
  - 网络 `ModNetwork`（`RegisterPayloadHandlersEvent` + `PayloadRegistrar`）
  - 客户端注册中枢：`client/ClientModEvents`、`ModScreens`、`ModParticles`、`ModKeyMappings`、`ModItemModels`
  - `./gradlew build` 通过（40 个类，产出 `pasterdream-1.0.0.jar`）

### 进行中

- 扩展已建立的数据层与基础内容，按系统继续迁移；以 `document/reference/迁移批次记录.md` 为当前状态入口。
