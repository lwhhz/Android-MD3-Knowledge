# Divider / 分割线

- 官方指南：https://m3.material.io/components/divider ｜ 规格页：https://m3.material.io/components/divider/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**数值全部来自官方 measurements 表**；线宽为 Compose 默认值，已标注

## 用途

分割线是**分组工具**，不是装饰线。

> "Dividers are thin lines that group content in lists or other containers"

官方三条原则：

1. **可见但不粗重**（"Make dividers visible but not bold"）。
2. **只有当内容无法用留白分组时才用**。
3. **用来分组，不是用来分隔每一个条目**。

> "Only use dividers if items can't be grouped with open space"
> "Use dividers to group things, not separate individual items"

因此最常见的正确答案是**不加线**：把间距调好、标题层级做清楚，往往比加一排线更干净。

## 形态

| 形态 | 几何 | 用途 |
|---|---|---|
| **Full-width** | 100% 容器宽度 | 分隔不相关的大区块；分隔可交互区与不可交互区 |
| **Inset** | 左侧内缩，右侧贴边 | 分隔同一区块内的相关内容，与 leading 图标/头像对齐 |
| **Middle-inset** | 左右各内缩 16dp | 页面中部使用，如正文与选择 chip 之间 |
| **Vertical** | 竖线 | 大屏上分隔并排区域，如正文与视频/图片 |

官方原文：

> "Use full-width dividers to separate larger sections of unrelated content. They can be used directly on a surface or inside other components like cards or lists."
> "Use inset dividers to separate related content within a section."
> "A vertical divider can be used to arrange content on a larger screen, such as separating paragraph text from video or imagery."

## 规格

### 尺寸（specs 明示值）

| 属性 | 值 |
|---|---|
| 整宽分割线宽度 | **100%** |
| Inset 左缩进 | **16dp** |
| Inset 右缩进 | **0dp** |
| Middle-inset 左缩进 | **16dp** |
| Middle-inset 右缩进 | **16dp** |
| 与 supporting text 的间距 | **4dp** |
| 右侧外边距 | **8dp** |
| 底部外边距 | **8dp** |

### 线宽与颜色

| 属性 | 值 | 来源 |
|---|---|---|
| 颜色角色 | **`outline variant`** | specs 明示 |
| 线宽 | **1dp** | ⚠️ Compose `DividerDefaults.Thickness` 默认值；dump 文本未给 dp |

**⚠️ 官方两页对 inset 的口径不一致**：specs 表给的是「左 16dp / 右 0dp」，guidelines 则写 "Inset dividers are equally indented from both sides of the screen by default." 可执行结论：以规格表为准（左 16 / 右 0），并与 leading 图标或头像对齐；若用于页面中部（middle-inset），左右各 16dp。

官方把 divider 描述为 "a simple line"，没有在 specs 文本中给出粗细数值；实现上用 1dp，不要再加粗——「visible but not bold」是明确要求。

## 颜色角色

| 部位 | 颜色角色 |
|---|---|
| 线条 | `outline variant` |

- 明暗主题共用同一角色映射。
- 不要用 `outline` 替代 `outlineVariant`：前者对比度更高，会把线画「重」，与「not bold」冲突。
- 分割线是**装饰元素，没有对比度下限**（见无障碍）。

## 使用规则

### Full-width

1. 用于**不相关的大区块**之间。
2. 可直接放在 surface 上，也可放在 card、list 内部。
3. 可以分隔**可交互区与不可交互区**（例如卡片里的正文区与按钮区）。
4. ✅ 用它表达「这些元素在交互上不是一组」。
5. ⚠️ 节制使用；线太多会让界面显得杂乱。

### Inset

1. 用于**同一区块内的相关内容**（如邮件列表中的各封邮件）。
2. 规格表的 inset 为左 16dp、右 0dp；配合 leading 图标/头像时，线与它们对齐并贴齐屏幕起始边。
3. 可放在页面中部，分隔正文与 chip 等元素。
4. 有重复格式的列表项**可能完全不需要 inset 线**，仅靠条目间距即可。

### 两种混用

若同一屏同时使用整宽与内缩线，必须让它们**反映信息层级**：

| 要表达的关系 | 用哪种 |
|---|---|
| 不同种类的内容分区 | Full-width |
| 嵌套的同类子项 | Inset |

> "Use a combination of inset and full-width dividers to reflect the hierarchy of information"（Do）

### 什么时候不用

- 条目本身有稳定的间距与对齐，线只是噪音。
- 卡片之间已有 8dp 间距与不同容器色。
- 想用线「补足」本来就弱的层级——应先改排版，而不是加线。

## 无障碍

- 分割线是**纯装饰元素，没有任何对比度下限**。

> "Dividers are decorative elements, which have no contrast minimums."

- 它**不承载语义**，不应进入焦点顺序，也不应被读屏单独朗读。
- Compose 的 `HorizontalDivider` / `VerticalDivider` 默认无点击与语义，保持默认即可；不要给它加 `contentDescription` 或 `clickable`。
- 若用线表达分组，必须另有文字标题或间距等**可被读屏理解**的结构；线本身不能是唯一的分组线索。

## Compose 对应 API

| 需求 | API |
|---|---|
| 水平分割线 | `HorizontalDivider(modifier, thickness, color)` |
| 垂直分割线 | `VerticalDivider(modifier, thickness, color)` |
| 默认值 | `DividerDefaults.Thickness`（1dp）、`DividerDefaults.color`（`outlineVariant`） |

```kotlin
// 整宽
HorizontalDivider()

// inset：左侧 16dp，右侧 0dp
HorizontalDivider(modifier = Modifier.padding(start = 16.dp))

// middle-inset：左右各 16dp
HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

// 垂直分隔（大屏并排区域）
VerticalDivider(modifier = Modifier.height(48.dp))
```

实现要点：

- 默认颜色已是 `outlineVariant`，不要手动改成 `outline`。
- inset 用 `padding(start = 16.dp)` 实现，不要用 `width(0.9f)` 之类比例值。
- 垂直分割线需要显式给高度，否则会撑满父容器。
- 列表内分割线优先考虑「不加线 + 间距分组」；确需线时参考 lists 的内缩规则（左 16dp / 右 24dp）。

## 常见错误

1. 每个列表项之间都画线，界面变成表格。
2. 用 `outline`（更重）而不是 `outlineVariant`。
3. 加粗到 2dp 以上，违反「visible but not bold」。
4. 整宽线与内缩线混用却没有层级含义。
5. 给分割线加点击或语义，读屏多出一个无意义焦点。
6. 用分割线代替标题/间距来表达分组，读屏用户拿不到结构。
7. 卡片之间已有 8dp 间距，还额外画线。

## 来源

- https://m3.material.io/components/divider/overview
- https://m3.material.io/components/divider/specs
- https://m3.material.io/components/divider/guidelines
- https://m3.material.io/components/divider/accessibility
- 核验日期：2026-11-16
- 关联：`02-Components/lists.md`（列表内缩分割线）、`02-Components/cards.md`（卡片内分割线）
