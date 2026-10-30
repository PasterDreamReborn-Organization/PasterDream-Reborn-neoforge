---
name: datagen
description: 数据生成器（runData）。覆盖所有 Provider（方块状态、战利品表、标签、物品模型、配方、语言、进度、世界生成）的职责与约束。当用户要跑数据生成、加 Provider、改标签/模型/配方生成逻辑时使用。
---

# 数据生成器（Datagen）

所有 Provider 位于 `datagen/`，入口 `datagen/ModDataGenerator.java`。数据输出到 `src/generated/resources/`（不纳入版本控制，**不可手动编辑**）。完整目录见 `document/rule/program/目录结构组织.md`。

---

## Provider 清单（目标职责；实际实现见迁移批次记录）

| Provider | 职责 |
|----------|------|
| `ModBlockStateProvider` | 方块状态与模型 |
| `ModBlockLootTablesProvider` | 方块战利品表 |
| `ModEntityLootTablesProvider` | 实体战利品表（`LootTableSubProvider`，上下文 `ENTITY`，**不用** `ApplyBonusCount`） |
| `ModBlockTagsProvider` | **必须**补全标签（挖掘工具 + 材质类型 + 功能标签 + `ANIMALS_SPAWNABLE_ON`） |
| `ModItemModelsProvider` | 物品模型（工具 `handheldItem()`、普通 `basicItem()`、刷怪蛋 `spawnEggItem()`） |
| `ModItemTagsProvider` | 物品标签（`copy()` 从方块标签同步，工具 `ItemTags.SWORDS` 等） |
| `ModEntityTypeTagsProvider` | 实体类型标签（走 datagen，**不手写 JSON**） |
| `ModBiomeTagsProvider` | 群系标签 |
| `ModFluidTagsProvider` | 流体标签 |
| `ModRecipesProvider` | 配方 |
| `ModAdvancementProvider` | 进度 |
| `ModWorldGenProvider` | 世界生成（`DatapackBuiltinEntriesProvider`） |
| `GenerateBucketItem` | 流体桶物品模型 |

语言文件：`datagen/lang/ModEnUsLangProvider.java` / `ModZhCnLangProvider.java`。

---

## 关键约束

- **入口事件**：NeoForge 用 `net.neoforged.neoforge.data.event.GatherDataEvent`（实现 `IModBusEvent`，用 `@EventBusSubscriber(modid = MOD_ID)` 注册，不写 `bus`）
- **1.21 目录名**多为单数：`advancement/`、`recipe/`、`loot_table/`、`structure_set/`、`template_pool/`；以 `runData` 实际输出为准
- 所有数据生成类以 `Provider` 结尾
- 标签常量定义在 `tag/`（`ModBlockTags`、`ModItemTags`、`ModEntityTypeTags`），Provider 引用常量
- 配方工具 `datagen/util/RecipeHelpers.java`（`plankFamilyRecipes` / `storageCompress` / `storageDecompress`）
- 战利品工具 `datagen/util/LootHelpers.java`
- 容器配方平衡见 `.claude/skills/recipe-container/SKILL.md`
- 粒子纹理、音效、饰品槽位标签、Curios 标签**不走 datagen**，手动维护
- 翻译值不用格式化代码控制颜色，用 `.rarity()`

---

## 运行

```
./gradlew runData
```

生成后检查 `src/generated/` 下的 JSON。

---

## 文件速查

| 用途 | 路径 |
|------|------|
| 入口 | `datagen/ModDataGenerator.java` |
| 各 Provider | `datagen/common/` |
| 语言 | `datagen/lang/` |
| 工具 | `datagen/util/` |
| 标签常量 | `tag/` |

---

## 引用文档

- `document/rule/program/目录结构组织.md` — 数据生成器输出目录、Provider 全列表
- `document/rule/program/容器配方平衡系统.md` — 配方容器平衡 datagen 写法

## 当前实现补充

- 批次进度见 `document/reference/迁移批次记录.md`。当前仅启用已迁入内容所需 Provider。
- 先建立 `ModWorldGenProvider`，其 `getRegistryProvider()` 传递给标签、配方与战利品 Provider。
- 语言只由 `LanguageProvider` 生成；不要在 main/resources 留下同路径语言文件造成重复资源。
- 运行目录为 `build/run-data`；生成资源仍写入 `src/generated/resources`。缓存不得入包。
- `runData` 后运行 `build` 与适用的 `runGameTestServer`；源码与 JSON 可编译不能替代实际数据包加载检查。
- 批次 004 的 `ModRecipesProvider.saveNbtPreserving` 使用 `RecipeOutput` 包装原版无序 Builder 的配方对象，替换为 `NbtPreservingShapelessRecipe` 并转发 advancement / conditions；不能沿用 1.20 `FinishedRecipe` 或后处理生成 JSON。
- 当前 `nbt_preserving_shapeless` 仅用于无容器钛金工具升级；新增容器配方前先迁移配平链路。有序保留配方尚未实现。
