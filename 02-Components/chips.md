# Chips / 标签片

- 官方指南：https://m3.material.io/components/chips ｜ 规格页：https://m3.material.io/components/chips/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**32dp 高度、8dp 圆角、18dp 图标、8/16dp 内边距为官方明示值**

## 用途

> "Chips help people enter information, make selections, filter content, or trigger actions."

四个变体按两个问题选（官方原文给出的判断路径）：

1. 它表示**动作**（assist）还是**筛选结果**（filter）？
2. 内容是**产品生成**（suggestion）还是**用户输入**（input）？

| 变体 | 目的 | 官方定义 | Compose |
|---|---|---|---|
| Assist | 动作 | "smart or automated actions that can span multiple apps" | `AssistChip` |
| Filter | 筛选 | "filters for a collection" | `FilterChip`（可选中） |
| Input | 用户输入的信息 | "discrete pieces of information entered by a person" | `InputChip` |
| Suggestion | 产品生成的信息 | "help narrow a person's intent by presenting dynamically-generated suggestions" | `SuggestionChip` |

## Chip 不是按钮

> "Use chips to enhance a person's current journey and encourage action. Use buttons to progress them through the product and for significant actions."

| 维度 | Chip | Button |
|---|---|---|
| 出现方式 | 动态、随情境变化 | 常驻固定 |
| 语义 | 当前任务的岔路 | 线性步骤 |
| 数量 | 成组出现（横向可滚） | 一次不超过 3 个 |
| 职责 | 补充选项 | 推进/完成流程 |

两条硬 Don't：不要用 chip 替代主要动作；不要用 chip 完成或推进任务。

> "Don't display a single chip by itself. Chips should appear in a set."

## 尺寸（官方 Measurements 原值）

| 属性 | Assist | Filter | Suggestion | Input |
|---|---|---|---|---|
| 容器高度 | **32dp** | **32dp** | **32dp** | **32dp** |
| 容器形状 | **8dp 圆角** | **8dp 圆角** | **8dp 圆角** | **8dp 圆角** |
| 图标尺寸 | **18dp** | **18dp** | **18dp** | **18dp** |
| 文字垂直对齐 | 居中 | 居中 | 居中 | 居中 |
| 文字水平对齐 | 起始 | 起始 | 起始 | 起始 |
| 左右内边距（无图标） | **16dp** | **16dp** | **16dp** | — |
| 左右内边距（有图标） | **8dp** | **8dp** | **8dp** | **8dp** |
| 元素间距 | **8dp** | **8dp** | **8dp** | **8dp** |
| 头像形状 / 尺寸 | — | — | — | **12dp 圆角 / 24dp** |
| 头像左 / 右内边距 | — | — | — | **4dp / 8dp** |
| 关闭图标触控目标 | — | — | — | **最小 48dp** |

规格页之外的官方数值（都出自 guidelines，同样可追溯）：

| 项 | 值 | 出处原文 |
|---|---|---|
| 标签长度 | **≤ 20 字符** | "Chip label text should be 20 characters or fewer" |
| chip 之间最小间距 | **8dp** | "Keep an 8dp minimum space between chips" |
| chip 最小触控目标 | **48dp** | "Chips must also have a minimum 48dp target size, regardless of placement or density" |
| 带次要操作时最小容器宽 | **88dp**（或标签文字 42dp） | "apply a minimum width of 88dp to the chip, or 42dp to the label text" |

88dp 这条最容易被漏：尾部关闭/菜单按钮要拿到 48×48 的目标，32dp 高的 chip 只能靠**横向加宽**腾出来。

## 颜色角色

| 变体 | 颜色角色（官方顺序与项数） |
|---|---|
| Assist | `surfaceContainerLow`（可选）、`onSurface`、`outline`、`primary` |
| Filter | `onSurfaceVariant`（未选中标签）、`onSecondaryContainer`（选中标签）、`secondaryContainer`（选中容器）、`outlineVariant`、`surfaceContainerLow`（可选） |
| Input | `onSurfaceVariant`、`surfaceContainerLow`（可选）、`onSurfaceVariant`、`onSurfaceVariant`、`outlineVariant`、`primary`、`secondaryContainer`、`onSecondaryContainer`、`onSecondaryContainer` |
| Suggestion | `outline`、`surfaceContainerLow`（可选）、`onSurfaceVariant` |

可确认的语义：Filter 选中态 = `secondaryContainer` / `onSecondaryContainer`；Input 选中态同为 `secondaryContainer` 系。⚠️ 推断，页面未明示：Input 的 9 个值未逐元素标注，`primary` 一项通常对应前置图标。

引导图标颜色可定制：

> "While the default color role is primary, the on surface variant color role is a good alternative when the icon style requires less emphasis."

## 外观与状态

- 四种变体状态一致：Enabled / Disabled / Hovered / Focused / Pressed / **Dragged**。
- 阴影：只有在**图片或动态背景**上才可加高度；直接放在页面上时用描边定义边界。
- 不要用高度变化表达按下态，用 ripple。

## 各变体的行为规则

Assist：

- 文案以动词开头（Get / Add），状态变了要跟着改（Save → Saved）
- 放在主要内容之后（卡片下方、屏幕底部常驻）
- 交互中可以展开为弹窗、全屏内容或就地显示更多结果

Filter：

- 点选后**在标签起始侧追加对勾图标**（不要只换颜色）
- 文案用名词描述"要包含的分类"，避免否定式（Don't 例：Exclude images）
- 可多选，也可单选；**同一页面所有 chip 集合必须统一单选或多选**
- 可换成菜单触发（尾部图标），此时整块 chip 都应能打开菜单——紧凑窗口里小图标目标不够大
- 超过两行建议改为横向滚动
- 可以放在搜索框下方、侧边 sheet 里组织大量筛选项

Input：

- 代表用户输入的一个信息单元（收件人、搜索条件），文本转 chip
- 可编辑：进入编辑态退回文本；退格键在光标位于 chip 前时**先整块选中，再按一次才删除**
- 同一字段可含多个 chip，可排序、可跨字段移动
- 尾部删除图标**必需**；展开可显示更多信息（容器变换转场）
- 溢出时行为同普通文本：未聚焦显示开头，聚焦跳到末尾并唤起键盘

Suggestion：

- 文案为名词或短语，尽量 ≤ 20 字符
- 多个 chip 用**一行排列**，不要竖排；溢出换行；单行高度时改横向滚动

## 排布

- chip 水平排列并可在集合内横向滚动
- chip 之间 ≥ 8dp；换行时行间也留间距
- 标签在换行布局与超窗宽时会截断，所以文案必须短

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

`FilterChipDefaults.IconSize` 即 18dp，别手写 24dp。

Input chip 带删除（注意触控目标与宽度）：

```kotlin
InputChip(
    selected = false,
    onClick = { },
    label = { Text(name) },
    avatar = { Avatar(name) },          // 24dp
    trailingIcon = {
        Icon(Icons.Default.Close, contentDescription = "移除 $name")
    },
    modifier = Modifier.widthIn(min = 88.dp)   // 给 48dp 删除目标腾空间
)
```

`InputChip` 的 `trailingIcon` 点击区域默认落在 18dp 图标上，务必用 `Modifier.minimumInteractiveComponentSize()` 或外包可点区域把目标撑到 48dp。

## 无障碍

| 元素 | 无障碍标签 | Compose 角色 |
|---|---|---|
| chip 内图标/图片 | 隐藏（decorative） | — |
| 单动作 chip | `{chip content}` | button |
| 可选 chip | `{chip content}` | checkbox |
| 纯删除图标 | `Remove {chip content}` | — |
| 双动作（选中 + 删除） | 先 `{chip content}`，再 `Remove {chip content}` | button 或 checkbox |

规则：

- chip 标签与背景对比 ≥ **3:1**；成组 chip 尤其需要高对比来区分彼此
- **必须提供"可交互"的第二重指示**（只靠外观不够，低视力/认知障碍用户看不出能点）。四种官方做法任选其一：组前加引导标签（"Select type"）、给页面上下文（"Filter results"）、用 `outline` 而非 `outlineVariant` 保证 3:1、加引导图标或动作性文案
- 纯删除 icon 时，整块 chip 与图标是**一个**焦点元素；有第二个动作时拆成**两个**焦点元素
- 移动端若删除是唯一动作，可以不显示删除图标，改用选中 + Delete 键
- 横向溢出时提供"全部显示"：用 filter chip 作为引导项让列表回流，或用引导按钮弹出菜单（后者不要用在带第二动作的 chip 上）
- 键位：`Tab` 进入 chip/chip 组，`Space/Enter` 选中/取消，`Backspace/Delete` 删除当前输入 chip，`Arrow` 组内移动

## 常见错误

1. 用 AssistChip 做筛选（它不可选中）。
2. 用 chip 代替「提交/下一步」这类主操作。
3. 只放一个 chip（官方要求成组）。
4. 关闭图标触控目标不足 48dp，容器宽度没到 88dp。
5. 标签超过 20 字符，换行后被截断。
6. 图标用 24dp（官方 18dp），chip 显得笨重。
7. Filter 选中只换底色、不加对勾。
8. 同页混用单选与多选 chip 集合。
9. chip 间距小于 8dp，点错相邻项。
10. 在纯色页面上给 chip 加阴影，或反过来用亮度变化表示按下。

## 来源

- https://m3.material.io/components/chips/specs
- https://m3.material.io/components/chips/guidelines
- https://m3.material.io/components/chips/accessibility
- 关联：`01-Foundations/shape-system.md`（8dp = small 档）、`02-Components/buttons.md`（chip vs button）
