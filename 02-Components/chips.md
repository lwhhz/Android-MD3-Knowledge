# Chips / 标签片

- 官方指南：https://m3.material.io/components/chips ｜ 规格页：https://m3.material.io/components/chips/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14 ｜ 核验方式：Chrome CDP 读官方 Specs 页
- 状态：**尺寸与颜色角色全部为官方原值**

## 四种 Chip，别混用

| Compose | 用途 | 能否选中 | 有头像 |
|---|---|---|---|
| `AssistChip` | 辅助完成一个动作（加日历、导航） | ❌ | ❌ |
| `FilterChip` | 从一组里筛选，**可多选** | ✅ | ❌ |
| `InputChip` | 表示一条用户输入（收件人、标签） | ✅（可选） | ✅ |
| `SuggestionChip` | 给出建议（动态生成、可点） | ❌ | ❌ |

**最容易搞错的**：FilterChip 是筛选器（可选中），AssistChip 是动作（不可选中）。把 AssistChip 当筛选器用，用户点了会执行动作而不是选中，行为完全对不上预期。

## 尺寸（官方 Measurements 原值）

### Assist / Filter / Suggestion

| 属性 | 值 |
|---|---|
| 高度 | **32dp** |
| 形状 | **8dp 圆角** |
| 图标尺寸 | **18dp** |
| 垂直文字对齐 | 居中 |
| 水平文字对齐 | 起始 |
| 左右内边距 | **16dp** |
| 带图标时左右内边距 | **8dp** |
| 元素间距 | **8dp** |

### Input Chip（多一个头像）

| 属性 | 值 |
|---|---|
| 容器高度 | 32dp |
| 容器形状 | 8dp 圆角 |
| 图标尺寸 | 18dp |
| 头像形状 | **12dp 圆角** |
| 头像尺寸 | **24dp** |
| 头像左内边距 | **4dp** |
| 头像右内边距 | **8dp** |
| 图标左右内边距 | 8dp |
| 元素间距 | 8dp |
| **关闭图标触控目标** | **最小 48dp** |

最后一条很容易漏：视觉上关闭图标是一小块，但触控目标必须是 48dp。

## 颜色角色（官方原文）

| 变体 | 颜色角色 |
|---|---|
| Assist | `surfaceContainerLow`（可选）、`onSurface`、`outline`、`primary` |
| Filter | `onSurfaceVariant`、`onSecondaryContainer`、`secondaryContainer`、`outlineVariant`、`surfaceContainerLow`（可选） |
| Suggestion | `outline`、`surfaceContainerLow`（可选）、`onSurfaceVariant` |

Filter 选中态用 `secondaryContainer` / `onSecondaryContainer`——这也是为什么它和「选中的 Tab」看起来是一族的。

## 状态

四种变体都有：Enabled / Disabled / Hovered / Focused / Pressed / **Dragged**。

`Dragged` 是 Chip 特有的：Input Chip 可以被拖拽（比如拖到另一个分组）。如果你的实现不支持拖拽，至少要保证视觉上不给出可拖的暗示。

## Compose 实现

```kotlin
FilterChip(
    selected = selected,
    onClick = { onToggle() },
    label = { Text("仅看未读") },
    leadingIcon = if (selected) {
        { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(FilterChipDefaults.IconSize)) }
    } else null
)
```

Input Chip 带删除：

```kotlin
InputChip(
    selected = false,
    onClick = { },
    label = { Text(name) },
    avatar = {
        AsyncImage(model = avatarUrl, contentDescription = null, modifier = Modifier.size(24.dp))
    },
    trailingIcon = {
        Icon(
            Icons.Default.Close,
            contentDescription = "移除 $name",
            modifier = Modifier
                .size(18.dp)
                .clickable { onRemove() }
        )
    }
)
```

> 注意 `size(18.dp)` 会缩小触控目标。正确做法是图标 18dp、外层留出 48dp 可点区域（用 `minimumInteractiveComponentSize()` 或尺寸外包一层 `IconButton`）。

## 无障碍

- Chip 的文案要能独立成立：「仅看未读」优于「未读」。
- FilterChip 的选中状态由组件自动提供 `selected` 语义，不要额外加"已选中"文字。
- **关闭图标必须写 `contentDescription`**，且明确对象：「移除 张三」而不是「删除」。
- 关闭图标的触控目标 ≥ 48dp（官方明确规定）。
- Chip 通常成组出现，组内焦点顺序按视觉从左到右。
- 不要用颜色单独表达选中——FilterChip 会加对勾或其它指示，别把它关掉。

## 常见错误

1. 用 AssistChip 做筛选（它不可选中）。
2. 用文字做「标签」时套 Chip（纯展示的标签不需要交互语义，用 Chip 会让人以为能点）。
3. 关闭图标触控目标不足 48dp。
4. Chip 里塞两行文字（高度固定 32dp，会被裁）。
5. 一行放 10 个 Chip 不换行也不横向滚动，挤成一团。
6. 图标用了 24dp（官方是 18dp），视觉变笨重。
7. Filter 选中后没有视觉指示，只靠颜色变化。

## 来源

- https://m3.material.io/components/chips/specs
- `01-Foundations/shape-system.md`（8dp = small 档）
- `02-Components/buttons.md`（触控目标与 contentDescription 通则）
