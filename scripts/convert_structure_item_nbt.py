"""Convert Forge 1.20.1 item stacks in structure NBT to 1.21 components.

Requires nbtlib: python -m pip install nbtlib
Only explicit files under src/main/resources/data/pasterdream/structure are writable.
"""

from __future__ import annotations

import argparse
from pathlib import Path

import nbtlib
from nbtlib import Compound, Int, List, String


ROOT = Path(__file__).resolve().parents[1] / "src/main/resources/data/pasterdream/structure"
NOTE_ID = "pasterdream:dream_notes_book"
NOTE_COMPONENT = "pasterdream:dream_note_content"
BLUEPRINT_ID = "pasterdream:blue_print"
BLUEPRINT_COMPONENT = "pasterdream:blueprint_content"
LEGACY_COMPONENTS = {
    "Damage": "minecraft:damage",
    "RepairCost": "minecraft:repair_cost",
}


def convert_stack(stack: Compound, where: str) -> tuple[bool, bool]:
    """Return (changed, is_note). Reject legacy tags without a known mapping."""
    if "id" not in stack or ("Count" not in stack and "count" not in stack):
        return False, False

    item_id = str(stack["id"])
    is_note = item_id == NOTE_ID
    if "Count" not in stack:
        if "tag" in stack:
            raise ValueError(f"{where}: 1.21 item still has legacy tag: {item_id}")
        return False, is_note
    if "count" in stack:
        raise ValueError(f"{where}: both Count and count exist: {item_id}")

    legacy_tag = stack.get("tag")
    if legacy_tag:
        components = stack.setdefault("components", Compound())
        for key, value in legacy_tag.items():
            if key == "content" and item_id == NOTE_ID:
                if NOTE_COMPONENT in components:
                    raise ValueError(f"{where}: duplicate note component")
                components[NOTE_COMPONENT] = String(str(value))
            elif key == "content" and item_id == BLUEPRINT_ID:
                if BLUEPRINT_COMPONENT in components:
                    raise ValueError(f"{where}: duplicate blueprint component")
                components[BLUEPRINT_COMPONENT] = String(str(value))
            elif key in LEGACY_COMPONENTS:
                if LEGACY_COMPONENTS[key] in components:
                    raise ValueError(f"{where}: duplicate {LEGACY_COMPONENTS[key]} component")
                components[LEGACY_COMPONENTS[key]] = value
            elif key == "display" and set(value) == {"Name"}:
                if "minecraft:custom_name" in components:
                    raise ValueError(f"{where}: duplicate custom name component")
                components["minecraft:custom_name"] = String(str(value["Name"]))
            else:
                raise ValueError(f"{where}: unmapped legacy item tag: {item_id}, {key}")

    stack["count"] = Int(int(stack.pop("Count")))
    stack.pop("tag", None)
    return True, is_note


def process(path: Path, check: bool) -> None:
    path = path.resolve()
    if not path.is_relative_to(ROOT.resolve()) or path.suffix != ".nbt":
        raise ValueError(f"only structure NBT under {ROOT} is accepted: {path}")

    data = nbtlib.load(path)
    converted = 0
    notes = []

    def walk(value: object, where: str) -> None:
        nonlocal converted
        if isinstance(value, Compound):
            if "id" in value and ("Count" in value or "count" in value):
                if check and ("Count" in value or "tag" in value):
                    raise ValueError(f"{where}: legacy item stack remains: {value['id']}")
                changed, is_note = (False, str(value["id"]) == NOTE_ID) if check else convert_stack(value, where)
                converted += changed
                if is_note:
                    content = value.get("components", {}).get(NOTE_COMPONENT)
                    if not isinstance(content, String) or not str(content):
                        raise ValueError(f"{where}: dream note content component is missing")
                    notes.append(str(content))
            for key, child in value.items():
                walk(child, f"{where}/{key}")
        elif isinstance(value, List):
            for index, child in enumerate(value):
                walk(child, f"{where}[{index}]")

    walk(data, path.name)
    if converted:
        data.save(path, gzipped=True)
    print(f"{path.name}: converted {converted} item stacks; checked {len(notes)} dream notes")


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("files", nargs="+", type=Path, help="explicit structure NBT paths")
    parser.add_argument("--check", action="store_true", help="validate without writing")
    args = parser.parse_args()
    for path in args.files:
        process(path, args.check)


if __name__ == "__main__":
    main()
