#!/usr/bin/env python3
"""同步 android/compose-samples 的索引元数据（不拉源码）。

只更新两处：
  04-Official-Samples/compose-samples.manifest.json
  04-Official-Samples/README.md 中 <!-- BEGIN GENERATED --> 与 <!-- END GENERATED --> 之间的内容

设计约束：
  - 只用标准库，无第三方依赖。
  - 任何一步失败就非零退出，避免把空数据写进仓库。
  - focus 字段是人工维护的，脚本不覆盖。
"""

import json
import os
import re
import sys
import urllib.request
from collections import defaultdict

UPSTREAM = os.environ.get("UPSTREAM", "android/compose-samples")
API = "https://api.github.com"
ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", ".."))
OUT_DIR = os.path.join(ROOT, "04-Official-Samples")
MANIFEST = os.path.join(OUT_DIR, "compose-samples.manifest.json")
README = os.path.join(OUT_DIR, "README.md")

BEGIN = "<!-- BEGIN GENERATED -->"
END = "<!-- END GENERATED -->"

# 这些目录不是示例应用
NON_SAMPLE = {".github", "readme", "scripts"}


def get_json(url):
    req = urllib.request.Request(
        url,
        headers={
            "Accept": "application/vnd.github+json",
            "User-Agent": "md3-kb-index-sync",
        },
    )
    with urllib.request.urlopen(req, timeout=30) as resp:
        return json.loads(resp.read().decode("utf-8"))


def main():
    print(f"fetching latest commit of {UPSTREAM}")
    commits = get_json(f"{API}/repos/{UPSTREAM}/commits?per_page=1")
    if not commits:
        sys.exit("no commits returned")
    head = commits[0]
    sha = head["sha"]
    date = head["commit"]["committer"]["date"]
    message = head["commit"]["message"].splitlines()[0]
    print(f"head = {sha[:7]} ({date})")

    tree = get_json(f"{API}/repos/{UPSTREAM}/git/trees/{sha}?recursive=1")
    blobs = [t for t in tree["tree"] if t["type"] == "blob"]
    if not blobs:
        sys.exit("empty tree")

    per_sample = defaultdict(lambda: {"files": 0, "kotlin_files": 0, "bytes": 0, "themes": []})
    for b in blobs:
        parts = b["path"].split("/")
        if len(parts) < 2:
            continue  # 根目录的 LICENSE / CODEOWNERS 等文件，不是示例
        top = parts[0]
        if top in NON_SAMPLE:
            continue
        s = per_sample[top]
        s["files"] += 1
        s["bytes"] += b.get("size", 0)
        if b["path"].endswith(".kt"):
            s["kotlin_files"] += 1
        if len(parts) > 2 and parts[-2] == "theme":
            s["themes"].append("/".join(parts[:-1]) + "/")

    # 读旧 manifest，保留人工维护的 focus
    old = {}
    if os.path.exists(MANIFEST):
        with open(MANIFEST, encoding="utf-8") as f:
            old = json.load(f)
    old_focus = {s["name"]: s.get("focus", "") for s in old.get("samples", [])}

    samples = []
    for name in sorted(per_sample, key=lambda n: -per_sample[n]["files"]):
        s = per_sample[name]
        candidates = sorted(set(s["themes"]))
        # 优先级：独立的 designsystem 模块 > 非 tv/wear 的 ui/theme > 其它
        ds = [p for p in candidates if "designsystem" in p]
        no_device = [p for p in candidates if not re.search(r"/(wear|tv)/", p)] or candidates
        ui = [p for p in no_device if "ui/theme" in p]
        pool = ds or ui or no_device
        theme_path = sorted(pool, key=len)[0] if pool else ""
        samples.append(
            {
                "name": name,
                "files": s["files"],
                "kotlin_files": s["kotlin_files"],
                "approx_kb": round(s["bytes"] / 1024),
                "focus": old_focus.get(name, ""),
                "theme_path": theme_path,
            }
        )

    total_kb = round(sum(b.get("size", 0) for b in blobs) / 1024)
    manifest = {
        "_note": "官方示例仓库索引，不镜像源码。由 .github/workflows/sync-samples.yml 每周更新 commit 与统计。",
        "repository": UPSTREAM,
        "url": f"https://github.com/{UPSTREAM}",
        "checked_on": date[:10],
        "commit": {"sha": sha, "date": date, "message": message},
        "totals": {
            "files": len(blobs),
            "kotlin_files": sum(1 for b in blobs if b["path"].endswith(".kt")),
            "approx_bytes": sum(b.get("size", 0) for b in blobs),
        },
        "samples": samples,
        "other_references": old.get("other_references", []),
    }
    with open(MANIFEST, "w", encoding="utf-8") as f:
        json.dump(manifest, f, ensure_ascii=False, indent=2)
        f.write("\n")
    print(f"manifest written: {len(samples)} samples, {len(blobs)} files, {total_kb}KB")

    size_mb = total_kb / 1024
    block = [
        "| 项 | 值 |",
        "|---|---|",
        f"| 仓库 | `{UPSTREAM}` |",
        f"| Commit | `{sha}` |",
        f"| 提交日期 | {date[:10]} |",
        f"| 提交说明 | {message} |",
        f"| 文件总数 | {len(blobs)}（Kotlin 文件 {manifest['totals']['kotlin_files']}） |",
        f"| 体积 | 约 {size_mb:.0f}MB（以图片资源为主） |",
        "",
        "机器可读版本见 `compose-samples.manifest.json`。",
        "",
        "## 示例应用索引",
        "",
        "| 示例 | 文件数 | Kotlin | 体积 | 主题 / 配色入口 |",
        "|---|---|---|---|---|",
    ]
    for s in samples:
        block.append(
            f"| **{s['name']}** | {s['files']} | {s['kotlin_files']} | "
            f"{s['approx_kb'] / 1024:.1f}MB | `{s['theme_path']}` |"
        )

    with open(README, encoding="utf-8") as f:
        readme = f.read()
    if BEGIN not in readme or END not in readme:
        sys.exit("README 缺少 GENERATED 区块标记")
    head_part, rest = readme.split(BEGIN, 1)
    _, tail_part = rest.split(END, 1)
    new_readme = head_part + BEGIN + "\n" + "\n".join(block) + "\n" + END + tail_part
    with open(README, "w", encoding="utf-8") as f:
        f.write(new_readme)
    print("README generated block updated")


if __name__ == "__main__":
    main()
