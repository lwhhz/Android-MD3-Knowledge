# Lists / 列表

- 官方指南：https://m3.material.io/components/lists ｜ 规格页：https://m3.material.io/components/lists/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**56 / 72 / 88dp 与内边距为官方明示值**；expressive 列表为 2025-12 新增

## 用途

列表是**连续的纵向索引**，用于让人快速找到某一项并对其操作。

> "Lists are continuous, vertical indexes of text and images"
> "Use lists for communicating or selecting discrete items, such as choosing from a set of colors."

| 场景 | 用列表 | 说明 |
|---|---|---|
| 同质条目、需要连续扫描 | ✅ | 设置项、消息、搜索结果 |
| 条目内需要多动作（主操作 + 次操作） | ✅ | 多动作列表 |
| 需要选择一项或多项 | ✅ | single-select / multi-select |
| 每项图文混排、独立主题 | ❌ | 用 cards |

## Expressive 与 baseline

| 变体 | M3 | M3 Expressive | 说明 |
|---|---|---|---|
| Lists（expressive） | — | ✅（2025-12） | 新设计推荐；分段式外观、圆角、选中高亮、可定制槽位 |
| Lists（baseline） | ✅ | ❌ 不再推荐 | 仍可用，但无新视觉、选中处理与槽位能力 |

官方原文：**"Lists have a new segmented visual style, improved selection treatment, and support for slots."**

expressive 列表的新能力：

- **Standard / Segmented** 两种视觉样式（纯视觉，不改变行为）；
- **高亮选中态**；
- **灵活槽位**（leading / content / trailing）。

## 结构

**容器与 label 文字是必需元素**，其余全部可选：

| 元素 | 必需 | 说明 |
|---|---|---|
| Container | ✅ | 承载全部条目；条目高度由最高元素决定 |
| Label text | ✅ | 主文字 |
| Supporting text | — | 副说明，最多 1–3 行 |
| Overline | — | 分类/状态小字（expressive 槽位） |
| Trailing text | — | 价格、数量、日期等元信息 |
| Trailing icon | — | 状态或动作提示 |
| Trailing selection control | — | checkbox / radio / switch |
| Leading avatar | — | 人/实体的圆形或 expressive 形状 |
| Leading icon | — | 与 label 相关的视觉提示 |
| Leading media | — | 图片或视频缩略图 |
| Divider | — | 分隔线（非首选） |

内容槽位规则：

1. **leading / trailing 槽位必须比 content 槽窄**；content 槽最大且居中。
2. leading 可放：视觉元素（头像、图标、图片、视频缩略图）、选择控件、badge 或更大的图。
3. content 可放：label、supporting text，以及 badge、图标、行内 label 等附加元素；❌ 避免过长行。
4. trailing 可放：图标、图标按钮、trailing text、选择控件。
5. 自定义槽位**默认可访问性不足**，必须遵守列表的规则、结构与交互，使用标准内边距，目标 ≥ 48×48dp，且不要塞入让读屏难以导航的元素。

> "Slots are not accessible by default."
> "Target size must be at least 48x48dp"

## 规格

### 高度与对齐

| 行数 | 高度 | 对齐 |
|---|---|---|
| 单行 | **56dp** | 元素垂直居中 |
| 双行 | **72dp** | 元素垂直居中 |
| 三行 / 高度 ≥ 88dp | **88dp** | 元素**顶部对齐** |

> "The tallest element within a list item determines the list item's height - either 56dp, 72dp, or 88dp"
> "If a list is 88dp or larger, or contains three or more lines of text, elements are top-aligned"

### 内边距与目标（baseline measurements 明示值）

| 属性 | 值 |
|---|---|
| Label 左内边距 | **16dp** |
| Leading 元素左内边距 | **16dp** |
| Leading 图标顶部内边距 | **8dp**（高度 ≥ 88dp 时 **12dp**） |
| Trailing 元素左内边距 | **16dp** |
| Trailing 元素右内边距 | **24dp** |
| 分割线上/下留白 | **0dp** |
| 触控目标 | **48dp** |
| 整宽分割线 | 100% |
| 内缩分割线左/右 | **16dp / 24dp** |

注意：trailing 右侧是 **24dp**，不是 16dp；旧文档写成「右内边距 16dp」需要纠正。

### 形状 morph（expressive 选中效果）

| 状态 | 圆角 |
|---|---|
| 未选中 | **内侧 4dp + 外侧 16dp**（分段式外观） |
| 选中 | **四周统一 16dp** |

> "Unselected corner radius: 4dp inner, 16dp outer"
> "Selected corner radius: 16dp"

### 图标按钮

图标按钮高度是**动态的**，自动填满列表项高度（specs 原文："The icon button height is dynamic, and automatically adjusts to fill the list item height."）。

## 颜色角色

| 部位 | 颜色角色 |
|---|---|
| 容器 | `surface` |
| Label | `on surface` |
| Supporting / trailing text / trailing icon | `on surface variant` |
| Overline | `on surface variant` |
| 分割线 | `outline variant` |
| 选中项容器 | `primary container` |
| 选中项内容 | `on primary container` |

状态：Enabled / Disabled / Hovered / Focused / Pressed / Dragged；选中项另有独立的五态 token 组。

## 使用规则

### 扫描与一致性

1. 每项中**支撑视觉与主文字的位置必须一致**；❌ 不要在不同条目里变换元素位置。
2. 用间距把注意力引向主操作区或关键内容；主操作区占更大空间。
3. Label 保持简短；supporting text **1–3 行**，按屏幕宽度截断。
4. 前置图标要与 label 语义相关；尾部图标用于表达状态或动作（如「展开更多」）。

### 媒体与头像

- 视觉元素**锚定在行首**，提高扫描效率；❌ 不要放在行中间。
- 人或实体用圆形 / expressive 形状头像；商品、视频等用方形或矩形图。
- 视频缩略图可打开播放器，也可在列表内播放。

### 选择模式（同时只能一种）

| 模式 | 行为 | 限制 |
|---|---|---|
| **Single-action** | 整项执行一个动作（如跳转） | 不能有次级嵌套动作；不能进入持久选中态 |
| **Multi-action** | 主操作 + 一个或多个次操作 | 主操作占 leading + content 大部分空间；次操作放 trailing |
| **Single-select** | 单项选择 | 不支持多动作；不能有次级动作；**不要用 checkbox** |
| **Multi-select** | 多项开关 | 配 checkbox / switch；不能有次级动作；**不要用 radio** |
| **Non-interactive** | 纯展示、不可选 | 仅用于信息组织 |

> "A list can have only one selection mode at a time."
> "For selection lists, use only one selection interaction per list item."

选中态作用于**整个列表项**：带 checkbox 的项被选中时，项与 checkbox 同时呈现选中态。

### 分组：间距优先于分割线

- **contained 列表用间距（gap）分组**，利用 expressive 形状与包覆感。
- 分割线只用于 **uncontained 或复杂列表**，且确实需要更强分隔时；✅ 用分段间距 + filled 列表项定义列表组。
- ⚠️ 过多分割线会让界面显得杂乱（官方 Caution）。

### 自适应

| 断点 | 规则 |
|---|---|
| Compact | 列表**边到边**；点击项进入全屏详情页 |
| Medium / Expanded | 列表与详情可同屏并排（list-detail） |
| 更大的窗口 | 列表可转为 carousel；条目可显示更多 supporting text |
| 任意 | 控制行宽：理想 40–60 字符，大屏最多 120 字符；接近 120 时增加行高 |

❌ 不要把组件整体放大却不调整文字长度与边距，导致行宽不可读。

### 展开与滑动

- 含子项的列表项可像文件夹一样展开/收起；展开用 **container transform**（父子过渡）。
- 滑动露出操作（**仅 Android Views 提供该交互**）：
  - 主操作必须是**最后一个尾对齐**选项；
  - 完整滑动触发主操作，并把该项与其他动作一起移出屏幕；
  - 必须提供替代入口（如 more 图标），因为滑动对辅助技术用户不可用。

## 无障碍

- 读屏用户应能：导航到列表项；选择列表项。
- **不要只靠颜色表达选中**：叠加 radio/checkbox、leading/trailing 图标、下划线等非颜色线索。
- 焦点：
  - single-action 列表：焦点落在**第一项**；若有选中项，则落在**选中项**；
  - 进入列表后用方向键在项间移动；
  - 多动作列表：`Tab` 聚焦第一个元素，方向键在全部可聚焦元素间移动，可跨项；
  - `Space` / `Enter` 激活。
- 键盘：`Tab` 到第一项/最后一项/移出列表；↓→ 下一项（末尾回到顶部）；↑← 上一项（首项回到底部）；`Space` / `Enter` 选择未选项。
- 标签：通常等于 **label text + supporting text**；纯展示列表读出文字但**不带 role**。
- 角色按平台不同：

| 模式 | Web | Android Views | Jetpack Compose |
|---|---|---|---|
| Single-select | 容器 listbox，项 option；状态 selected | 项 radio button；checked | 项 radio button；checked |
| Multi-select | 容器 listbox，项 option；状态 selected | 项 checkbox；checked | 项 checkbox；checked |

> "On Jetpack Compose, the role applies to the list item as a whole."

- 滑动项必须提供替代访问方式：单击、双击、长按或其他单点交互。

## Compose 对应 API

| 需求 | API | 状态 |
|---|---|---|
| 列表容器 | `LazyColumn` + `items(key, contentType)` | 稳定 |
| 列表项 | `ListItem(headlineContent, supportingContent, leadingContent, trailingContent, overlineContent)` | 稳定；`overlineContent` 需按版本核验 |
| expressive 分段样式 | `ListItemDefaults.segmentedColors()` 一类 | Expressive，需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`，名称需核验 |
| 滑动操作 | `SwipeToDismissBox` | 稳定（foundation/material3 视版本） |

```kotlin
LazyColumn {
    items(items = notes, key = { it.id }, contentType = { "note" }) { note ->
        ListItem(
            headlineContent = { Text(note.title, maxLines = 2) },
            supportingContent = { Text(note.preview, maxLines = 1) },
            leadingContent = { Icon(Icons.Outlined.Description, contentDescription = null) },
            trailingContent = { Text(note.time, style = MaterialTheme.typography.labelMedium) },
            modifier = Modifier.clickable { open(note.id) }
        )
    }
}
```

实现要点：

- 56 / 72 / 88dp 由 `ListItem` 依据内容自动取；不要手写 `height()` 覆盖。
- 需要分段外观时用 expressive 的 `ListItem` 颜色/形状 API；baseline 的 `ListItem` 仍是方形。
- 多选列表：把 `toggleable(value, role = Role.Checkbox)` 放在**整项**上，与官方「role 作用于整项」一致。
- `SwipeToDismissBox` 必须配撤销（Snackbar）与替代入口。

## 常见错误

1. trailing 右内边距写 16dp（官方为 24dp）。
2. 三行条目仍垂直居中，应为顶部对齐。
3. single-select 用 checkbox，multi-select 用 radio。
4. 一个条目里放两个选择控件。
5. 不同条目里元素位置不一致，列表无法扫描。
6. 用分割线切分每一行，而不是用间距分组。
7. 滑动删除没有撤销，也没有 more 菜单替代入口。
8. 大屏把列表拉成 160 字符宽的一行。
9. 槽位里塞自定义交互组件，读屏无法导航（官方：slots 默认可访问性不足）。

## 来源

- https://m3.material.io/components/lists/overview
- https://m3.material.io/components/lists/specs
- https://m3.material.io/components/lists/guidelines
- https://m3.material.io/components/lists/accessibility
- 核验日期：2026-11-16
- 关联：`02-Components/cards.md`（何时改用卡片）、`02-Components/divider.md`、`03-Android-Compose/components-api.md`
