# PasterDream: Reborn — NeoForge 1.21.1

## 入口

- 当前迁移进度与上游同步记录：[迁移批次记录](document/reference/迁移批次记录.md)。
- 初始盘点：[迁移现状盘点](document/reference/迁移现状盘点.md)；技术路线：[移植总纲](document/reference/移植总纲.md)。
- 权威程序规范：`document/rule/program/`。架构约定与“已迁入内容”必须区分。
- 领域工作流：`.claude/skills/`，数据生成用 `datagen`，世界生成用 `worldgen`；API 以锁定的 NeoForge 21.1.249 源码与实际编译为准。

## 来源与持续同步

- 正式上游：`https://github.com/PasterDreamReborn-Organization/PasterDream-Reborn-forge1.20.1` 的 `master`，上游更新必须走 PR。
- 本地来源通常在 `../PasterDream-Reborn-forge1.20.1/`。读取前确认其提交与已合并上游的关系；不能默认个人分支 HEAD 就是正式基线。
- 本仓库不创建 `NOT_MODIFY/`。直接迁移来源为 Forge 重生版的代码、资源和设计；原作档案仅按需从外部来源仓库只读考证。
- 不改动来源仓库；构建、数据生成、运行和 CI 不得依赖来源仓库或本机绝对路径。
- 每批开始和结束检查上游 `master` 与开放 PR；长批次每个工作日至少检查一次。未合并 PR 作为影响预警，合并后登记同步任务。
- 分开记录“上游已审阅 SHA”和“模块已移植/验证状态”。暂缓项保留待办，不得仅推进 SHA 就宣布全部同步。

## 实现与验证

- 保留已确定的重生版注册 ID、配方 ID、数值和行为；不要在平台迁移中擅自重做平衡。新增/变更 ID 才更新 ID 映射，已登记 ID 不重复登记。
- `init/` 集中注册，datagen 入口为 `datagen/ModDataGenerator.java`。新增方块要设置地图颜色并核对挖掘等级、材质、功能标签。
- `src/generated/resources/` 只能通过 Provider + `runData` 更新；不要手改生成 JSON。缓存不入库、不入发布 Jar。
- Java 与手写文档使用 UTF-8 无 BOM、CRLF；生成文件遵循 `.gitattributes` 的 LF。
- 直接复制的纹理保留来源与授权记录，不作为新绘制资产登记。
- 最小检查：`./gradlew runData`、`./gradlew build`；涉及运行时行为的批次运行适用的 GameTest。客户端视觉、真实世界生成和多人行为分别记录验证状态，不能用编译通过代替。
- GameTest 位于 `src/gameTest/`，通过 `./gradlew runGameTestServer` 运行，不打入发布 Jar。测试/数据生成使用 `build/` 下独立运行目录，不触碰用户存档。

## 提交规则与收尾

- 用户已授权 NeoForge 移植期间直接提交、推送，无须 PR。提交前检查变更与验证结果，推送前核对远程及当前分支；不强推、不覆盖无关改动。
- 保留当前合适的开发分支；新建分支时默认使用 `codex/` 前缀。
- 提交信息遵循 `feat(scope): message` 等项目格式。来源 Forge 的 PR 规则不套用到本仓库。
- 每批更新迁移批次记录和 CHANGELOG；新增架构链路更新架构/目录规范，受影响 skill 保持索引准确。没有触发条件的文档不为更新而更新。
