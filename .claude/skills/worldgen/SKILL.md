---
name: worldgen
description: 新增/修改世界生成（群系、维度类型、噪声设置、LevelStem、地物 ConfiguredFeature/PlacedFeature、结构、自定义 Feature）。覆盖 bootstrap 数据生成模式。当用户要加群系/维度/结构/地物或改世界生成时使用。
---

# 世界生成（Worldgen）

配置地物、放置地物和维度等动态注册表通过 `RegistrySetBuilder` + `DatapackBuiltinEntriesProvider` 产出 JSON。自定义 Feature 类型由 `init/ModFeatures.java` 使用 `DeferredRegister` 注册。核心规范见 `document/rule/program/架构规范.md`「世界生成」段。

---

## 注册体系

| 对象 | 类 | Registry |
|------|-----|----------|
| 配置的地物 | `worldgen/ModConfiguredFeatures.java` | `CONFIGURED_FEATURE` |
| 放置的地物 | `worldgen/ModPlacedFeatures.java` | `PLACED_FEATURE` |
| 群系 | `worldgen/biome/ModBiomes.java` | `BIOME` |
| 群系修改器 | `worldgen/biome/ModBiomeModifiers.java` | `BIOME_MODIFIERS` |
| 维度类型 | `worldgen/dimension/ModDimensionTypes.java` | `DIMENSION_TYPE` |
| 噪声设置 | `worldgen/dimension/ModNoiseSettings.java` | `NOISE_SETTINGS` |
| 维度 | `worldgen/dimension/ModLevelStems.java` | `LEVEL_STEM` |

全部由 `datagen/common/ModWorldGenProvider.java`（`DatapackBuiltinEntriesProvider`）串联，`RegistrySetBuilder` 按依赖顺序注册（BIOME / DIMENSION_TYPE / NOISE_SETTINGS 在 LEVEL_STEM 之前）。

---

## 关键约束

- 自定义 `Feature<?>` 实现放 `worldgen/feature/`，与注册入口类分离
- **1.21 数据驱动注册表**：`Enchantment`（附魔）与 `PaintingVariant`（画）不再是普通注册表，加入本包（如 `worldgen/enchantment/`、`worldgen/painting/`）的 `ResourceKey` + bootstrap，由 `ModWorldGenProvider` 的 `RegistrySetBuilder` 一并产出
- 结构数据生成 Provider 放 `worldgen/structures/`（`ModStructureProvider` / `ModStructureSetProvider` / `ModTemplatePoolProvider`）
- 自定义 `DimensionSpecialEffects` 用客户端 Mixin（`FogRendererMixin`、`LevelRendererMixin`）注入
- 运行时通过 `ResourceKey` 引用（如 `ModTreeGrowers` → `ModConfiguredFeatures.DYEDREAM_TREE`）

---

## 维度/群系搬运流程

1. 维度类型 → `ModDimensionTypes.java`（`DimensionType`，含 MonsterSettings）
2. 噪声设置 → `ModNoiseSettings.java`（`NoiseGeneratorSettings`，含 NoiseRouter + SurfaceRules）
3. 维度注册 → `ModLevelStems.java`（`LevelStem`，关联 BiomeSource + ChunkGenerator）
4. 群系注册 → `ModBiomes.java`（`Biome.BiomeBuilder`，含特效、生物生成、雕刻器、特征列表）
5. 自定义 DimensionSpecialEffects → 客户端 Mixin

---

## 结构 NBT 中的物品栈转换（Forge 1.20.1 → NeoForge 1.21.1）

结构模板放在 `src/main/resources/data/pasterdream/structure/`（1.21 使用单数 `structure`）。复制来源 `.nbt` 后、运行 GameTest 前，先转换模板内所有方块实体的物品栈；物品可能藏在 `Inventory.Items` 等嵌套容器里，不能只检查顶层 `Items`。

```powershell
python -m pip install nbtlib
python scripts/convert_structure_item_nbt.py src/main/resources/data/pasterdream/structure/dream_train.nbt
python scripts/convert_structure_item_nbt.py --check src/main/resources/data/pasterdream/structure/dream_train.nbt
```

- 脚本只写入显式指定的目标仓库 `structure/*.nbt`，不会修改 Forge 来源。将旧物品栈 `Count` 转为 `count`（Int），保留 `id`、`Slot` 和其他方块实体字段。
- 寻梦者笔记 `pasterdream:dream_notes_book` 的旧 `tag.content` 必须转为 `components."pasterdream:dream_note_content"`；`minecraft:custom_data` 不能代替该专用组件。转换后核对内容 key 已在 `ModDreamNotesBookContentRelation` 注册，笔记可正常显示。
- 已映射的通用旧字段：`Damage`→`minecraft:damage`、`RepairCost`→`minecraft:repair_cost`、`display.Name`→`minecraft:custom_name`。遇到其他非空旧 `tag` 时脚本报错；先逐项确认 1.21 对应组件并扩展映射，再重跑。不要丢弃旧 NBT，也不要把未知字段一概塞进自定义数据。
- 提交前用 `--check` 验证每份新模板没有旧 `Count`/`tag` 物品栈；再运行 `runData`、`build`、`runGameTestServer`，并在游戏内检查容器中的笔记内容。模板“能加载”不等于其中物品已正确解码。

---

## 文件速查

| 用途 | 路径 |
|------|------|
| 地物 bootstrap | `worldgen/ModConfiguredFeatures.java` / `ModPlacedFeatures.java` |
| 自定义 Feature | `init/ModFeatures.java` + `worldgen/feature/` |
| 群系 | `worldgen/biome/ModBiomes.java` |
| 群系修改器 | `worldgen/biome/ModBiomeModifiers.java` + `ModBiomeModifierProvider.java` |
| 维度 | `worldgen/dimension/` |
| 睡眠跳时与跨维度天气 | `mixin/ServerLevelSleepTimeMixin.java`、`mixin/ServerLevelWeatherMixin.java` |
| 结构 Provider | `worldgen/structures/` |
| 世界生成 datagen | `datagen/common/ModWorldGenProvider.java` |
| 结构生成辅助 | `helper/structuregenerate/` |
| 结构生成事件 | `event/ModWorldGenEvents.java` |
| 结构物品 NBT 转换 | `scripts/convert_structure_item_nbt.py` |

---

## 引用文档

- `document/rule/program/架构规范.md` — 世界生成数据流、子包划分
- `document/rule/program/程序规范文档.md`「易犯错误记录」— 骨粉催生与世界生成共用 Feature 时避免重复放置装饰方块

## 当前已实现

`ModConfiguredFeatures` / `ModPlacedFeatures` / `biome/ModBiomes` / `dimension/ModDimensionTypes` / `dimension/ModNoiseSettings` / `dimension/ModLevelStems` / `biome/ModBiomeModifiers` 由 `ModWorldGenProvider` 统一 bootstrap。群系修改器通过 NeoForge registry codec 输出到 `data/pasterdream/neoforge/biome_modifier/`，不复制来源 `forge/` 目录。普通钛矿定义已生成；批次 069 已迁入染梦维度类型、噪声设置、LevelStem 与 16 个群系骨架；批次 070 已补全染梦维度树木/矿脉/植被/洞穴/海洋等 configured 与 placed 地物、25 个 `dyedream_world_*` 群系标签及逐条 `neoforge:add_features` 注入，并迁入 8 个自定义 Feature 与方解石尖锥结构模板。萤火虫巢因子系统未迁暂缓，结构、模组生物生成仍待后续任务。批次 099 已迁入灯影之下维度类型、噪声设置、LevelStem、4 个群系、植被/地物与自然刷怪（生物生成通过 `add_spawns` biome modifier），阴影地牢等结构生成与客户端维度特效留待后续。完整状态见 `document/reference/archive/迁移批次记录.md`（迁移期归档）与 Provider 源码。

批次 135 接通 `WindJourneyWaterPoolFeature` 与 `SlumberPalmTreeFeature`。风泊群岛沿用 `SURFACE_STRUCTURES` 阶段的 `wind_journey_water_pool` 放置项。水池复用 `DyedreamWaterPoolFeature`，岸边扫描与树木放置按 1.21.1 地物阶段的可用区块限制访问。`slumber_palm_tree` configured feature 同时供树苗生长使用。

批次 031 增加灵魂沙峡谷的 soul_ore_patch：仅替换灵魂土，矿脉大小 16、每区块 8 次、Y=0～128，群系标签由 ModBiomeTagsProvider 生成，ModBiomeModifiers 在 vegetal_decoration 阶段挂载。GameTest 覆盖目标群系和放置规则；真实自然分布待游戏内验证。

批次 003 增加下界 `molten_gold_ore_patch`：保留来源下界岩替换、矿脉大小 16、每区块 4 次、Y=0～64 均匀分布及 `vegetal_decoration` 阶段。GameTest 覆盖群系挂载和放置规则，不能替代真实世界自然分布与客户端视觉验收。

批次 082 补全主世界内容：12 种植物/小石子 configured/placed 地物与群系标签注入；橡木/云杉渔夫小屋、露营地、沙漠小屋、沙漠要塞的 structure/structure_set/template_pool/NBT 与 4 条箱子战利品表。批次 096 迁入 `ModStructureConfig` 与 `ModStructureProvider` / `ModStructureSetProvider` / `ModTemplatePoolProvider`，主世界与染梦世界可生成结构改由 `runData` 输出到 `src/generated/resources/data/pasterdream/worldgen/`；仅固定区块世界树、沙漠要塞、大气泡及多部件手工结构保留 `src/main/resources/data/pasterdream/worldgen/` 静态 JSON。NBT 放在 `data/pasterdream/structure/`。批次 091 的逐梦列车模板已按上面的脚本转换 3 个旧物品栈，其中 2 本寻梦者笔记使用专用内容组件。
