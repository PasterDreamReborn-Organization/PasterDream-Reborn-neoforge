# 更新日志

## 未发布（NeoForge 1.21.1 移植）

> 本仓库为 [`PasterDream-Reborn-forge1.20.1`](https://github.com/PasterDreamReborn-Organization/PasterDream-Reborn-forge1.20.1) 的 NeoForge 移植版，从空 MDK 搭建骨架。
> 搬运总纲见 [`document/reference/移植总纲.md`](document/reference/移植总纲.md)。

### 已完成

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

- Phase 2 数据层骨架（`datagen/ModDataGenerator` + Provider + `tag/` 常量 + `worldgen/` bootstrap）
