# 像素级还原示例：Suggestion chip

这是一个从 Figma 设计稿**逐属性还原**的 Jetpack Compose 组件示例，用来展示如何从 MCP 拿到的完整参数/节点数据生成贴合设计稿的实现，而不是 Material 3 组件的近似映射。

文件：`components/M3SuggestionChipPixel.kt`　入口 Composable：`M3SuggestionChipPixel(...)`

## 数据来源

Figma 组件 **Suggestion chip**（节点 `53923:28679`），从 figmingo-mcp 缓存的完整文件文档中提取：

- **48 个变体** = Style(2) × State(6) × Selected(2) × Show icon(2)
- `componentPropertyDefinitions`：4 个参数（Style / State / Selected / Show icon 均为 VARIANT）+ 3 个实例参数（Leading icon=INSTANCE_SWAP、Label text=TEXT、Show focus indicator=BOOLEAN）
- 每个变体节点的真实填充色、描边、圆角、阴影、文字样式与状态层颜色

## 设计规格（逐项对照）

| 项 | Figma 值 | 代码实现 |
|---|---|---|
| 尺寸 | 85×32dp（带图标）/ 67×32dp（无图标） | `.height(32.dp)` + 内容自适应宽度 |
| 圆角 | 8dp | `RoundedCornerShape(8.dp)` |
| 图标槽 | 距左 8dp，18×18dp，图形 14×14dp | `padding(start=8).size(18)` + `Image(size=14)` |
| 文本 | 距左 34dp（图标后 8dp），Roboto 14sp / w500 / lh20 / ls0.1 | `fontSize=14.sp, FontWeight.W500, lineHeight=20.sp, letterSpacing=0.1.sp, padding(start=8)` |
| 聚焦指示器 | 外扩 2dp → 89×36dp，圆角 10dp，3dp 描边 #625B71 | Focused 时外扩 Box + `border(3.dp, #625B71, r10)` |
| Elevated 阴影 | black15% blur3 offY1 + black30% blur2 offY1 | `shadow(1.dp, shape)`（对应 M3 elevation level1） |
| 状态层 | Hover 8% / Focus 10% / Pressed 10% / Dragged 16%（2026-11-17 改为现行官方值） | `chipColors()` 按 state 返回对应透明度叠层 |

## 颜色矩阵（代码内 `chipColors()` 查表）

| Style | State | Selected | 容器 | 描边 | 文字 | 图标 |
|---|---|---|---|---|---|---|
| Outlined | Enabled | false | 透明 | #CAC4D0 | #49454F | #6750A4 |
| Outlined | Enabled | true | #E8DEF8 | — | #4A4459 | #6750A4 |
| Elevated | Enabled | false | #F7F2FA | — | #49454F | #6750A4 |
| Elevated | Enabled | true | #E8DEF8 | — | #4A4459 | #6750A4 |
| 任意 | Hovered | false | +on-surface 8% 层 | 同上 | 同上 | 同上 |
| 任意 | Focused | false | +on-surface 10% 层 + 聚焦框 | 同上 | 同上 | 同上 |
| 任意 | Pressed | false | +on-surface 10% 层 | 同上 | 同上 | 同上 |
| 任意 | Dragged | false | +on-surface 16% 层 | 同上 | 同上 | 同上 |
| 任意 | Disabled | 任意 | on-surface 10%（Elevated）/ 描边 10%（Outlined） | — | #1D1B20@38% | #1D1B20@38% |

## 与自动生成的 `M3SuggestionChip` 区别

| | `M3SuggestionChip`（自动生成） | `M3SuggestionChipPixel`（本示例） |
|---|---|---|
| 参数 | 完整（含全部 Figma 参数签名） | 完整，且语义化 |
| 视觉 | 直接委托 Material3 `SuggestionChip` | 逐像素自绘：真实颜色/圆角/状态层/聚焦框/排版 |
| 状态 | 由 Material3 管理 | 按 Figma 6 态显式建模，可用 `state` 参数强制预览任一状态 |
| 用途 | 快速集成、跟随系统主题 | 需要与设计稿 1:1 对齐的场景 |

## 使用

```kotlin
M3SuggestionChipPixel(
    label = "Suggestion",
    style = ChipStyleElevated.OUTLINED,
    selected = false,
    showIcon = true,
    icon = painterResource(R.drawable.ic_check), // 14dp 图形
    onClick = { /* ... */ },
)

// 预览任意状态（设计走查用）
M3SuggestionChipPixel(label = "Pressed", state = ChipVisualState.PRESSED)
```

文件末尾的 `@Preview` 展示了 Outlined / Elevated / Selected / Disabled 四种组合。

## 校验状态

括号配平通过、枚举/颜色常量无未使用项、Scope 相关调用（matchParentSize）已修正。本机无 kotlinc，未做真实编译——请在 Android Studio 中预览 `SuggestionChipPreview` 确认效果，有偏差告诉我具体哪项，我按上表微调。
