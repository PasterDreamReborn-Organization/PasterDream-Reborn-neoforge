---
name: curio
description: 新增/修改饰品（Curios）。覆盖 ICurioItem 实现、ModRarities 品质、被动处理器、插槽标签维护、效果实现方式选择。当用户要加饰品、改饰品效果、调整饰品槽位时使用。
---

# 饰品系统（Curios）

核心规范，详细设计见 `document/rule/program/架构规范.md`「Curios 集成规范」段。

---

## 关键约束

- 饰品类放 `world/item/curio/`，实现 `ICurioItem`
- 品质用 `ModRarities`（COMMON/EXCELLENT/SUPERIOR 等），**不用**原版 `Rarity`
- 已迁入内容的品质 tooltip 由批次 028 的统一处理器自动添加，饰品类不重复添加
- 增加幸运值用 MC 原版 `LUCK` 属性
- 插槽标签 JSON **手动维护** `data/curios/tags/item/<slot>.json`，不走 datagen，每个 slot 加 `"minecraft:air"` 允许空槽
- `charm` / `head` 等类型由 Curios 9.5.1 内置，但必须在 `data/pasterdream/curios/entities/player.json` 分配给玩家；只加物品标签不会显示属性 tooltip，也不能佩戴。来源戒指槽数量 2 由 `data/pasterdream/curios/slots/ring.json` 覆盖

---

## 效果实现方式选择

| 方式 | 适用场景 | 注意 |
|------|---------|------|
| `getAttributeModifiers()` | 静态属性加成 | 槽位已分配给玩家时自动生成 tooltip；用 `CuriosApi.getItemStackSlots` 和 `getAttributeModifiers` 验证，不重复手写描述 |
| `curioTick()` | 每 tick 持续效果 | 检查 `!level.isClientSide()` |
| `onEquip()` / `onUnequip()` | 装备/卸下一次属性变更 | `AttributeInstance.addPermanentModifier/removeModifier` |
| `makesPiglinsNeutral()` | 猪灵中立 | 仅覆写，无额外 tooltip |
| `canEquip()` | 禁止重复装备同类饰品 | `CuriosApi.getCuriosInventory().findFirstCurio()` |
| 事件驱动（外部 handler） | 依赖游戏事件（闪避/受击/攻击） | 在 `event/PlayerEvents.java` 或 `CurioPassiveHandler` 订阅 NeoForge 事件 |

---

## 被动效果

- 全局 tick 处理放 `world/item/curio/CurioPassiveHandler.java`（`@EventBusSubscriber` + `LivingTickEvent`）
- 事件驱动型在 `event/PlayerEvents.java` 注册 handler，用 `CuriosApi.getCuriosInventory()` 检测是否装备
- 需临时属性修改时，创建自定义 `MobEffect`（`world/effect/`），构造中 `addAttributeModifier()`，注册到 `init/ModEffects.java`

---

## 配方

- 统一写入 `curioRecipes()` 方法（独立于 `othersRecipes`）
- 胚胎原胚配方（钛金粒 + 灵魂精华）、升级配方（原胚 + 特定材料）

---

## 文件速查

| 用途 | 路径 |
|------|------|
| 饰品实现 | `world/item/curio/` |
| 被动处理器 | `world/item/curio/CurioPassiveHandler.java` |
| 品质系统 | `world/item/ModRarities.java` |
| 玩家槽位分配 | `src/main/resources/data/pasterdream/curios/entities/player.json`（手动维护） |
| 戒指槽数量 | `src/main/resources/data/pasterdream/curios/slots/ring.json`（手动维护） |
| 插槽标签 | `src/main/resources/data/curios/tags/item/`（手动维护） |
| 玩家事件 | `event/PlayerEvents.java` |
| 饰品网络包 | `network/curio/` |

槽位类型：`charm` / `necklace` / `ring` / `belt` / `head` / `back` / `body`

---

## 引用文档

- `document/rule/program/架构规范.md` — Curios 集成规范
- `.claude/skills/port-content/SKILL.md` — 搬运流程中的饰品章节
