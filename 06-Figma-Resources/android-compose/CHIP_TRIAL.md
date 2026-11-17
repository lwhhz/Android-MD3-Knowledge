# Chip 五变体试点记录（阶段 1 · 第七批）

> 日期：2026-11-17　范围：Assist / Filter / Input / Suggestion / Chip group　结论：**五变体全部实现**；参数 7/8、8/9、8/8、6/7、2/2。

## 一、实现

| 变体 | 入口 | 说明 |
|---|---|---|
| Assist | `M3AssistiveChip` | outlined / elevated 两样式，leading/trailing 图标 |
| Filter | `M3FilterChip` | selected，选中时起始侧自动加对勾；可选 trailing 图标 |
| Input | `M3InputChip` | 头像槽、可关闭（`onRemove`）、selected |
| Suggestion | `M3SuggestionChip` | outlined / elevated，可选 leading 图标 |
| Chip group | `M3ChipGroups` | `SINGLE_ROW_SCROLLABLE` 横滚 / `WRAP` 换行，间距 8dp |

底层用 Material 3 `AssistChip` / `ElevatedAssistChip` / `FilterChip` / `InputChip` / `SuggestionChip` / `ElevatedSuggestionChip`，形状统一 8dp、图标 18dp。

## 二、官方尺寸

| 项 | 值 |
|---|---|
| 容器高度 / 圆角 | 32dp / 8dp |
| 图标 | 18dp |
| 内边距（有/无图标） | 8dp / 16dp |
| 元素间距 / chip 间距 | 8dp |
| Input 头像 | 24dp / 12dp 圆角 |
| 触控目标 | 48dp（由 Material3 chip 提供） |
| 带次要操作最小宽 | 88dp（文档约束，未自动强制） |

常量在 [M3ChipTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3ChipTokens.kt>) 并加单测。

## 三、真机验证

![Chip：assist / filter / input / suggestion，WRAP 组](<screenshots/chips.png>)

核对结果：

| 项 | 结果 |
|---|---|
| Assist outlined（Add + 图标）、elevated（Saved） | ✅ |
| Filter 选中带对勾 + trailing 关闭；未选中无对勾 | ✅ |
| Input chip 带关闭图标（可点） | ✅ |
| Suggestion outlined / elevated + 图标 | ✅ |
| WRAP 组：两行、8dp 间距 | ✅ |
| 32dp 高度 / 8dp 圆角 | ✅ |

## 四、验证命令

| 项 | 结果 |
|---|---|
| `:app:compileDebugKotlin` | 0 error |
| `:app:testDebugUnitTest` | **8 tests / 0 failures** |
| `:app:assembleDebug` | 通过 |
| 真机 `adb install` | Success |

## 五、参数落地

| 组件 | 参数 | 落地 | 缺口 |
|---|---|---|---|
| Assistive（8） | Style / Configuration / State | 7/8 | **Configuration 的 4 档未作为显式枚举**，用图标组合表达 |
| Filter（9） | Style / Configuration / State / Selected / Show trailing | 8/9 | **Style 的 Elevated 未做**（Compose 无 elevated filter chip） |
| Input（8） | Configuration / State / Selected / Show closing | 8/8 | — |
| Suggestion（7） | Style / State / Show icon / Selected | 6/7 | **Selected 语义不存在**（suggestion 不是可选态） |
| Chip groups（2） | Type / Layout | 2/2 | Type 由内容决定 |

## 六、Input chip 热区修复（2026-11-17）

- 方案：关闭动作外包 48dp 宽点击区，chip 加 `widthIn(min = 88dp)`；视觉高度仍 32dp。
- 语义：`Box` 上 `semantics(mergeDescendants = true)` 合并为单个可点节点，读屏描述“移除 Alice”。
- uiautomator：`content-desc="移除 Alice"`，`clickable="true"`，bounds `[203,786][329,912]`（126×126px = **48×48dp**）。
- 行为：点击后 Alice 从状态列表移除，Bob 保留；截图见下。

| 修复前 | 修复后 |
|---|---|
| 18dp 图标可点，节点 clickable=false | 48×48dp，clickable=true |

![修复前：两个 input chip](<screenshots/chips.png>)

![点击 Alice 的关闭后，仅剩 Bob](<screenshots/chip-remove-after.png>)

## 七、已知限制

1. ~~Input chip 关闭图标触控目标只有 18dp~~ **已修复（2026-11-17）**：右侧 48dp 宽点击区 + `widthIn(min = 88dp)`，uiautomator 实测节点 126×126px = **48×48dp**，`clickable=true`；见第六节。
2. **Filter 的 Style 未实现**（没有 elevated filter chip），只有 outlined。
3. Assist / Suggestion 的 Configuration 用图标组合表达，没有专门的枚举参数。
4. Chip 组横向滚动的边缘渐隐 / 滚动指示未做。
5. 没有 chip 进入编辑态（Input chip → 文本）的行为。
6. 尚无 pixel diff。

## 八、下一步

按计划进入 P1 组件：**Carousel**、**Date & time pickers**、**Sheets**、**Toolbar**、**Loading & progress**，或先把 P0 的 **Tabs / Navigation / Menu / Dialog / Snackbar / Search** 补齐。
