# 更新日志

## 未发布（NeoForge 1.21.1 移植）

> 本仓库为 [`PasterDream-Reborn-forge1.20.1`](https://github.com/PasterDreamReborn-Organization/PasterDream-Reborn-forge1.20.1) 的 NeoForge 移植版，从空 MDK 搭建骨架。
> 搬运总纲见 [`document/reference/移植总纲.md`](document/reference/移植总纲.md)。

### 已完成

- **环境与元数据**：依赖 jar 放入 `libs/`、`gradle.properties`、`neoforge.mods.toml` 依赖声明
- **项目文档**：README（中/英）、协议（LICENSE / PERMISSION_GRANTED / LICENSE_ASSETS / ASSETS_MANIFEST）、程序与架构规范文档、11 个搬运 skill 适配迁移
- **移植总纲**：API 差异清单、目录映射、骨架搭建阶段、风险清单

### 进行中

- 骨架搭建（Phase 1：主类重构 + `init/` 注册中枢）
