# PasterDream: Reborn — NeoForge 1.21.1

## 入口

- 上游同步进度（唯一进度记录）：[上游同步记录](document/reference/上游同步记录.md)。只登记最新已同步的 PR 编号、合并提交与验证结论；同步内容以上游 PR 为准。
- 技术路线与平台差异：[移植总纲](document/reference/移植总纲.md)。
- 迁移期文档（2026-11-24 归档，仅作历史查证，不再按批次更新）：[迁移批次记录](document/reference/archive/迁移批次记录.md)、[迁移任务线](document/reference/迁移任务线.md)、[迁移现状盘点](document/reference/迁移现状盘点.md)。
- 玩家更新日志：[CHANGELOG.md](CHANGELOG.md)，只保留书写规则与未发布段；已归档版本与迁移期日志在 `changelog/`，归档件只作历史查证。
- 权威程序规范：`document/rule/program/`。架构约定与“已迁入内容”必须区分。
- 领域工作流：`.claude/skills/`，数据生成用 `datagen`，世界生成用 `worldgen`；API 以锁定的 NeoForge 21.1.249 源码与实际编译为准。

## 来源与持续同步

- 正式上游：`https://github.com/PasterDreamReborn-Organization/PasterDream-Reborn-forge1.20.1` 的 `master`，上游更新必须走 PR。
- 本地来源通常在 `../PasterDream-Reborn-forge1.20.1/`。读取前确认其提交与已合并上游的关系；不能默认个人分支 HEAD 就是正式基线。
- 直接迁移来源为 Forge 重生版的代码、资源和设计。`NOT_MODIFY/` 是 MCR 原作档案，不属于搬运对象，本仓库不创建、不查阅、不维护。
- 不改动来源仓库；构建、数据生成、运行和 CI 不得依赖来源仓库或本机绝对路径。
- 每批开始和结束检查上游 `master` 与开放 PR；长批次每个工作日至少检查一次。未合并 PR 只作影响预警，合并后才进入同步。
- 同步只记游标：一个已合并 PR（或同窗口一组 PR）完成适配并通过 `runData`、`build`（按需 `runGameTestServer`）后，在 [上游同步记录](document/reference/上游同步记录.md) 追加一行并推进游标，只写 PR 编号、合并提交、日期和验证结论，不写同步内容、来源清单或逐文件对照。
- 游标表示“已同步到哪个 PR”，不代表全部历史内容都已移植；客户端画面、真实世界生成与多人行为由用户验收，未验收项标「待用户验收」。

## 实现与验证

- 低 API 差异模块先直接复制已合并 Forge 原类，保持字段、方法和方法内每行代码的顺序、注释及排版；仅改必要的平台 API，不顺手重构、重命名或统一格式。高差异边界逐块登记适配理由，未受影响代码仍保留原样；详见任务线第 2 节与程序规范。
- 保留已确定的重生版注册 ID、配方 ID、数值和行为；不要在平台迁移中擅自重做平衡。本仓库不维护 ID 映射表，新增内容直接按现有命名规范注册。
- `init/` 集中注册，datagen 入口为 `datagen/ModDataGenerator.java`。新增方块要设置地图颜色并核对挖掘等级、材质、功能标签。
- `src/generated/resources/` 只能通过 Provider + `runData` 更新；不要手改生成 JSON。缓存不入库、不入发布 Jar。
- Java 与手写文档使用 UTF-8 无 BOM、CRLF；生成文件遵循 `.gitattributes` 的 LF。
- 直接复制的纹理保留来源与授权记录，不作为新绘制资产登记；新增或重绘纹理时同步 `src/main/resources/ASSETS_MANIFEST.md`。
- 最小检查：`./gradlew runData`、`./gradlew build`；语言文件改动另跑 `python tools/check_lang.py`（`./gradlew check` 已包含 `checkLang`）；涉及运行时行为的批次运行适用的 GameTest。客户端实机验证（`./gradlew runClient` 的画面、交互、配方与音效）由用户执行，代理不启动客户端、服务端，也不用 computer-use/桌面自动化代测，只交付待验收项、复现步骤与自动检查结果；真实世界生成和多人行为同样分别记录验证状态，不能用编译通过代替。
- GameTest 位于 `src/gameTest/`，通过 `./gradlew runGameTestServer` 运行，不打入发布 Jar。测试/数据生成使用 `build/` 下独立运行目录，不触碰用户存档。

## 提交规则与收尾

- NeoForge 移植的提交、推送由用户亲自执行。代理完成实现、验证和文档更新后，检查变更并交付改动摘要、验证结果与建议提交信息，保留未提交的工作树供用户检查；不自行提交或推送。用户提交前核对变更与验证结果，推送前核对远程及当前分支；不强推、不覆盖无关改动。
- 保留当前合适的开发分支；新建分支时默认使用 `codex/` 前缀。
- 提交信息遵循 `feat(scope): message` 等项目格式；标题中的 message 与正文优先使用中文，与 Forge 1.20.1 仓库历史风格一致，type/scope 保留惯用英文。来源 Forge 的 PR 规则不套用到本仓库。
- 同步批次只推进上游同步记录与游标，不再写迁移批次记录；CHANGELOG 只保留书写规则与未发布段，发布时整理玩家可见改动，已发布与迁移期日志移入 `changelog/`。新增架构链路更新架构/目录规范，料理（cook）溢出机制变更更新 `document/rule/program/料理状态效果.md`，受影响 skill 保持索引准确。没有触发条件的文档不为更新而更新。
