#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""静态配方物品 ID 校验（合并门禁）。

检查 src/main/resources/data/pasterdream/recipe 下所有配方 JSON 的物品字段
（`"item"` 与 1.21 的 `"id"`），只校验 minecraft 与 pasterdream 两个命名空间：

  E01 文件不是合法 UTF-8 / JSON 解析失败 / 带 BOM
  E02 物品 ID 缺少命名空间（形如 "stick"）
  E10 minecraft 命名空间的物品 ID 不存在
  E11 pasterdream 命名空间的物品 ID 没在物品注册里注册（流体字段的 `"id"` 除外）
  E12 compat 配方缺少顶层 neoforge:conditions（未装对应模组时会报解析错误）
  E13 compat 配方的 neoforge:mod_loaded modid 与配方 type 的命名空间不一致
  E14 配方缺少 type，或 type 格式异常

  W01 原版 ID 清单的版本与 gradle.properties 的 minecraft_version 不一致
  W02 某处物品注册的名字不是字符串字面量（脚本识别不到，可能误报 E11）

判定依据：
  * pasterdream: 的物品 ID 取自 src/main/java 下的注册字面量：
      `ITEMS.register("...")`、`ITEMS.registerSimpleItem("...")`，
      以及 `ITEMS.registerSimpleBlockItem(ModBlocks.X)` 对应的 `BLOCKS.register("...")`。
    物品注册不都在 ModItems.java 里（world/item/PotionBottleRegistry.java 等也注册），
    所以扫整个源码目录。
  * minecraft: 的物品 ID 取自 tools/data/vanilla_1_21_1_ids.json（由 tools/gen_vanilla_ids.py
    从客户端 jar 生成，CI 上没有 Minecraft jar，所以清单必须随仓库提交）。
  * 其它命名空间（create、immersiveengineering 等）本地没有对应 jar，只统计不校验。

退出码：0 通过；1 有错误。CI（.github/workflows/item-id-check.yml）与 `gradlew check` 据此拦截合并。

用法：
    python tools/check_item_ids.py
    python tools/check_item_ids.py --recipes-dir <dir>   # 指定配方目录（自测用）
"""

from __future__ import annotations

import argparse
import json
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DEFAULT_RECIPES_DIR = ROOT / "src" / "main" / "resources" / "data" / "pasterdream" / "recipe"
JAVA_SRC_DIR = ROOT / "src" / "main" / "java"
VANILLA_IDS_JSON = ROOT / "tools" / "data" / "vanilla_1_21_1_ids.json"
GRADLE_PROPERTIES = ROOT / "gradle.properties"

CHECKED_NAMESPACES = ("minecraft", "pasterdream")
MOD_NAMESPACE = "pasterdream"
CONDITION_KEY = "neoforge:conditions"
MOD_LOADED = "neoforge:mod_loaded"
BOM = b"\xef\xbb\xbf"

# 1.21 配方里原料用 "item"，产物用 "id"，两边都要查
ITEM_FIELD_RE = re.compile(r'"(?:item|id)"\s*:\s*"([^"\\]+)"')
MOD_ITEM_RE = re.compile(r'ITEMS\s*\.\s*register\s*\(\s*"([A-Za-z0-9_.\-/]+)"')
SIMPLE_ITEM_RE = re.compile(r'ITEMS\s*\.\s*registerSimpleItem\s*\(\s*"([A-Za-z0-9_.\-/]+)"')
SIMPLE_BLOCK_ITEM_RE = re.compile(r"ITEMS\s*\.\s*registerSimpleBlockItem\s*\(\s*ModBlocks\s*\.\s*(\w+)")
MOD_FLUID_RE = re.compile(r'FLUIDS\s*\.\s*register\s*\(\s*"([A-Za-z0-9_.\-/]+)"')
BLOCK_REGISTER_RE = re.compile(
    r"(\w+)\s*=\s*BLOCKS\s*\.\s*register\s*\(\s*\"([A-Za-z0-9_.\-/]+)\""
)
ANY_ITEM_REGISTER_RE = re.compile(r"ITEMS\s*\.\s*(?:register|registerSimpleItem)\s*\(")
BUS_REGISTER_RE = re.compile(r"ITEMS\s*\.\s*register\s*\(\s*eventBus\s*\)")

issues: list[tuple[str, str, str, int, str]] = []  # (severity, rule, file, line, message)


def add_issue(severity: str, rule: str, file: str, line: int, message: str) -> None:
    issues.append((severity, rule, file, line, message))


def load_vanilla_items() -> set[str]:
    data = json.loads(VANILLA_IDS_JSON.read_text(encoding="utf-8"))
    return set(data["item_ids"])


def strip_comments(raw: str) -> str:
    """去掉整行注释，避免被注释掉的注册模板被当成真实注册。"""
    return "\n".join(
        line for line in raw.splitlines() if not line.lstrip().startswith(("//", "/*", "*"))
    )


def load_mod_items() -> set[str]:
    """扫描整个 Java 源码目录，收集 pasterdream 命名空间下的物品注册名。"""
    names: set[str] = set()
    block_ids: dict[str, str] = {}
    pending_block_items: list[tuple[str, str]] = []
    sources: list[tuple[Path, str]] = []

    for path in sorted(JAVA_SRC_DIR.rglob("*.java")):
        raw = path.read_text(encoding="utf-8", errors="replace")
        if "ITEMS" not in raw and "BLOCKS" not in raw:
            continue
        text = strip_comments(raw)
        sources.append((path, text))
        block_ids.update(BLOCK_REGISTER_RE.findall(text))

    for path, text in sources:
        if "ITEMS" not in text:
            continue
        names.update(MOD_ITEM_RE.findall(text))
        names.update(SIMPLE_ITEM_RE.findall(text))
        for field in SIMPLE_BLOCK_ITEM_RE.findall(text):
            pending_block_items.append((path.relative_to(ROOT).as_posix(), field))
        total = len(ANY_ITEM_REGISTER_RE.findall(text))
        literal = len(MOD_ITEM_RE.findall(text)) + len(SIMPLE_ITEM_RE.findall(text))
        bus = len(BUS_REGISTER_RE.findall(text))
        if total != literal + bus:
            add_issue(
                "warning",
                "W02",
                path.relative_to(ROOT).as_posix(),
                0,
                f"有 {total - literal - bus} 处 ITEMS.register 的注册名不是字符串字面量，"
                "这些物品无法被本脚本识别（配方里出现会误报 E11）",
            )

    for file, field in pending_block_items:
        block_id = block_ids.get(field)
        if block_id is None:
            add_issue(
                "warning",
                "W02",
                file,
                0,
                f"registerSimpleBlockItem(ModBlocks.{field}) 找不到对应的 BLOCKS.register 字面量",
            )
            continue
        names.add(block_id)
    return names


def load_mod_fluids() -> set[str]:
    """收集 pasterdream 命名空间的流体 ID。

    Create 等模组的流体产物同样用 `"id"` 字段（如 mixing 的 results），
    这些值不是物品，不能按物品表报错。
    """
    names: set[str] = set()
    for path in sorted(JAVA_SRC_DIR.rglob("*.java")):
        raw = path.read_text(encoding="utf-8", errors="replace")
        if "FLUIDS" not in raw:
            continue
        names.update(MOD_FLUID_RE.findall(strip_comments(raw)))
    return names


def check_vanilla_version() -> None:
    if not GRADLE_PROPERTIES.is_file():
        return
    props = GRADLE_PROPERTIES.read_text(encoding="utf-8", errors="replace")
    m = re.search(r"^minecraft_version\s*=\s*(\S+)", props, re.MULTILINE)
    if not m:
        return
    data_version = json.loads(VANILLA_IDS_JSON.read_text(encoding="utf-8")).get("minecraft_version")
    if m.group(1) != data_version:
        add_issue(
            "warning",
            "W01",
            VANILLA_IDS_JSON.relative_to(ROOT).as_posix(),
            0,
            f"清单版本 {data_version} 与 gradle.properties 的 minecraft_version {m.group(1)} 不一致，"
            "请重跑 tools/gen_vanilla_ids.py",
        )


def is_compat(display: str) -> bool:
    return "/compat/" in f"/{display}"


def check_file(
    path: Path,
    display: str,
    vanilla_items: set[str],
    mod_items: set[str],
    mod_fluids: set[str],
    stats: dict[str, int],
) -> None:
    raw = path.read_bytes()
    if raw.startswith(BOM):
        add_issue("error", "E01", display, 0, "文件带 UTF-8 BOM，项目要求无 BOM")
        raw = raw[len(BOM) :]
    try:
        text = raw.decode("utf-8")
    except UnicodeDecodeError as exc:
        add_issue("error", "E01", display, 0, f"不是合法 UTF-8：{exc}")
        return
    try:
        recipe = json.loads(text)
    except json.JSONDecodeError as exc:
        add_issue("error", "E01", display, exc.lineno, f"JSON 解析失败：{exc.msg}")
        return
    if not isinstance(recipe, dict):
        add_issue("error", "E14", display, 0, "配方顶层不是 JSON 对象")
        return

    recipe_type = recipe.get("type")
    type_namespace = None
    if not isinstance(recipe_type, str) or ":" not in recipe_type:
        add_issue("error", "E14", display, 0, f"type 缺失或不是 ns:path 形式：{recipe_type!r}")
    else:
        type_namespace = recipe_type.split(":", 1)[0]

    # compat 配方：未安装对应模组时必须能静默跳过
    if type_namespace is not None and is_compat(display):
        conditions = recipe.get(CONDITION_KEY)
        wanted = type_namespace
        if not isinstance(conditions, list):
            add_issue(
                "error",
                "E12",
                display,
                0,
                f'缺少顶层 {CONDITION_KEY}，应含 [{{"type":"{MOD_LOADED}","modid":"{wanted}"}}]',
            )
        elif not any(
            isinstance(c, dict) and c.get("type") == MOD_LOADED and c.get("modid") == wanted
            for c in conditions
        ):
            add_issue(
                "error",
                "E13",
                display,
                0,
                f'{CONDITION_KEY} 里没有 modid 为 "{wanted}" 的 {MOD_LOADED}'
                "（配方 type 的命名空间）",
            )

    for line_no, line in enumerate(text.split("\n"), 1):
        for match in ITEM_FIELD_RE.finditer(line):
            item_id = match.group(1)
            if ":" not in item_id:
                add_issue("error", "E02", display, line_no, f'物品 ID 缺少命名空间："{item_id}"')
                continue
            namespace, name = item_id.split(":", 1)
            stats[f"refs:{namespace}"] = stats.get(f"refs:{namespace}", 0) + 1
            if namespace not in CHECKED_NAMESPACES:
                continue
            stats[f"checked:{namespace}"] = stats.get(f"checked:{namespace}", 0) + 1
            if namespace == "minecraft":
                if name not in vanilla_items:
                    add_issue("error", "E10", display, line_no, f"原版没有这个物品：{item_id}")
            elif name in mod_items:
                continue
            elif name in mod_fluids:
                stats["fluid_refs"] = stats.get("fluid_refs", 0) + 1
            else:
                add_issue("error", "E11", display, line_no, f"物品注册里没有这个物品：{item_id}")


def main(argv: list[str]) -> int:
    for stream in (sys.stdout, sys.stderr):
        try:
            stream.reconfigure(encoding="utf-8", errors="replace")
        except (AttributeError, ValueError):
            pass

    parser = argparse.ArgumentParser(description="校验静态配方里的物品 ID")
    parser.add_argument("--recipes-dir", default=str(DEFAULT_RECIPES_DIR), help="配方根目录（自测用）")
    args = parser.parse_args(argv)
    recipes_dir = Path(args.recipes_dir).resolve()

    if not recipes_dir.is_dir():
        print(f"配方目录不存在：{recipes_dir}", file=sys.stderr)
        return 1
    if not JAVA_SRC_DIR.is_dir():
        print(f"找不到 Java 源码目录：{JAVA_SRC_DIR}", file=sys.stderr)
        return 1
    if not VANILLA_IDS_JSON.is_file():
        print(
            f"找不到原版 ID 清单：{VANILLA_IDS_JSON}\n请先运行 tools/gen_vanilla_ids.py 生成并提交。",
            file=sys.stderr,
        )
        return 1

    vanilla_items = load_vanilla_items()
    mod_items = load_mod_items()
    mod_fluids = load_mod_fluids()
    check_vanilla_version()

    files = sorted(recipes_dir.rglob("*.json"))
    stats: dict[str, int] = {}
    for path in files:
        display = path.relative_to(recipes_dir).as_posix()
        check_file(path, display, vanilla_items, mod_items, mod_fluids, stats)

    issues.sort(key=lambda i: (i[2], i[3], i[1]))

    for severity, rule, file, line, message in issues:
        tag = {"error": "错误", "warning": "警告", "info": "信息"}[severity]
        where = f"{file}:{line}" if line else file
        print(f"[{tag}] {rule} {where} {message}")

    errors = sum(1 for i in issues if i[0] == "error")
    warnings = sum(1 for i in issues if i[0] == "warning")
    print()
    print(f"检查配方 {len(files)} 个，模组物品 {len(mod_items)} 个，原版物品 {len(vanilla_items)} 个")
    checked = sum(v for k, v in stats.items() if k.startswith("checked:"))
    skipped = sum(v for k, v in stats.items() if k.startswith("refs:")) - checked
    print(f"物品引用：校验 {checked} 条，跳过其它命名空间 {skipped} 条")
    fluid_refs = stats.get("fluid_refs", 0)
    if fluid_refs:
        print(f"流体引用：{fluid_refs} 条（按流体表放行）")
    print(f"结果：错误 {errors} 个，警告 {warnings} 个")
    return 1 if errors else 0


if __name__ == "__main__":
    sys.exit(main(sys.argv[1:]))
