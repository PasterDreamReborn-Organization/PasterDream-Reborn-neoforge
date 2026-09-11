---
name: capability
description: 新增/修改能力系统（融梦能量 MeltDreamEnergy、SAN 值）。覆盖 Capability 接口+实现+Provider 模式、网络同步、HUD 渲染、命令。当用户要加玩家数值能力、改 SAN/能量逻辑、做同步或 HUD 时使用。
---

# 能力系统（Capability）

覆盖融梦能量（`capability/meltdreamenergy/`）与 SAN 值（`capability/san/`）两套能力。NeoForge 1.21.1 已无 Forge Capability，改用 `AttachmentType`（数据附加）+ Helper 封装，序列化/同步由 attachment 配置承担。

---

## 关键约束

- Attachment 类型声明在 `capability/ModAttachments.java`，用 `DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MOD_ID)` 注册，attach 到 Mod 总线
- 每个能力子包含：数据类（含 `Codec`/`StreamCodec`）、Helper（如 `SanHelper`）
- 网络同步优先用 `AttachmentType.Builder.sync(...)`；若不满足再走 `network/meltdreamenergy/` 与 `network/san/`（新 payload 框架，每包一功能）
- HUD 渲染在 `client/`（`MeltDreamEnergyTank.java`、`SanTank.java`、`LoseMind.java`）
- 命令在 `command/`（`MeltDreamEnergy`、`San` 子命令）
- 仅一个 int 且无需同步/生命周期的状态，优先用 `Entity.getPersistentData()`（NeoForge 仍提供）而非 Attachment（见暗影难度系统的选择）

---

## 实现步骤

1. 定义数据类 + `AttachmentType`（`builder(...).serialize(...).sync(...).copyOnDeath()`）
2. 在 `ModAttachments.java` 注册 `DeferredRegister<AttachmentType<?>>`，attach 到 Mod 总线
3. Helper 封装读写（服务端 `getData`/`setData`，客户端用同步值）
4. 需要自定义协议时用 payload 网络包（注册在 `init/ModNetwork.java` 的 `RegisterPayloadHandlersEvent`）
5. HUD 在 `client/` 渲染，必要时加配置开关
6. 命令暴露调试入口

---

## 文件速查

| 用途 | 路径 |
|------|------|
| Attachment 类型声明 | `capability/ModAttachments.java` |
| 融梦能量 | `capability/meltdreamenergy/` |
| SAN 值 | `capability/san/` |
| 融梦能量网络 | `network/meltdreamenergy/` |
| SAN 网络 | `network/san/` |
| 融梦能量 HUD | `client/MeltDreamEnergyTank.java` |
| SAN HUD | `client/SanTank.java` |
| 低 SAN 失智视觉 | `client/LoseMind.java` |
| 命令 | `command/meltdreamenergy/MeltDreamEnergy.java`、`command/san/San.java` |
| SAN 群系倍率 | `helper/sanbiomeratemanager/` |

> 若沿用旧目录 `capability/ModCapabilities.java` / `EventRegister.java`，其内容应替换为 `AttachmentType` 声明与注册，不再是 Forge `CapabilityManager`。

---

## 引用文档

- `document/rule/program/架构规范.md` — capability 模块职责、客户端分离
- `.claude/skills/shadow-difficulty/SKILL.md` — 依赖 SAN 的暗影难度系统
