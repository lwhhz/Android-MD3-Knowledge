# Segmented Button / 分段按钮

- 官方指南：https://m3.material.io/components/segmented-buttons ｜ 规格页：https://m3.material.io/components/segmented-buttons/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**尺寸全部为官方原值**；已按官方标注补充「不再推荐」结论

> ⚠️ **官方四页均置顶声明：Segmented buttons 在 M3 Expressive 更新后不再推荐。已升级到 Expressive 的项目请改用 connected button group**（功能基本相同、视觉更新），见 `02-Components/button-group.md`。未升级或需维护存量 UI 时，本文规则仍然适用。

## 用途

Segmented button 把 2-5 个选项并排成一条：**选择选项、切换视图、排序元素**。点击即生效，无「确定」步骤。

与相邻控件的边界：

| 控件 | 语义 | 数量 |
|---|---|---|
| **Segmented button** | 选选项（单选或多选） | 2-5 |
| Tabs | 切换并列视图 | 多个（换内容区） |
| Filter Chip | 可叠加的多条件筛选 | 多个 |
| Radio / Checkbox | 表单内选择 | 少量 |

**超过 5 个选项或选择较复杂 → 官方建议改用 chips。**

## 尺寸（官方 Measurements 表原值）

| 属性 | 值 |
|---|---|
| 容器宽度 | 动态（按标签） |
| 单段宽度 | 容器宽 / 段数（例：1/3） |
| 高度 | **40dp** |
| 描边宽度 | **1dp** |
| 标签对齐 | 居中 |
| 左右内边距 | **最小 12dp** |
| 元素间距 | **8dp** |
| 触控目标 | **48dp** |

**密度（Density）**：官方支持为紧凑界面降密度，**只作用于高度，每降一档减 4dp**（即 36 / 32dp…）；宽度和内边距不变，触控目标需复测。

## 结构与变体

解剖：Segment + Container + Icon（未选中可选）+ Label text（可选）+ Selected icon（对勾）。

- **Single-select（单选）**：一组只选一个，等价 Radio 的横向表达；用于从 ≤5 个选项中选一（如杯型选择）。
- **Multi-select（多选）**：可选 0 到全部，等价 Checkbox；用于筛选/排序（如按价位筛餐厅）。
- 段数 **2-5**（Don't：超过 5 段；选项该收窄）。
- 选中视觉：**容器填充 + 对勾图标**；带文字的段选中时**图标位置被对勾替换**。**不要只靠颜色表达选中**。

## 颜色角色（官方 Specs 页）

Outlined 样式（segmented button 的默认形态）使用的角色：

| 用途 | 角色 |
|---|---|
| 描边 | `outline` |
| 未选中内容 | `onSurface` |
| 选中容器 | `secondaryContainer` |
| 选中内容 | `onSecondaryContainer` |

## 使用规则（Guidelines 页）

1. 容器默认**全圆角**（与普通按钮一致）。
2. 标签**短且长度一致**；段内放不下就改纯图标；**不允许换行**（Don't: wrap onto a new line）。
3. 图标可单独作标签或搭配文字；纯图标时**必须清晰表意**。
4. **标签类型要统一**：不要图标标签和文字标签混用（Don't）。
5. 与视口边缘留足边距；大屏设最大内边距，**不要整条铺满大屏**（段内留白过多降低可用性）。
6. 可放在 bottom sheet、全屏对话框等组件上。
7. M2→M3 差异：高度 40dp（M2 更矮）、全圆角、sentence case（M2 全大写）、新增动态色与选中对勾。

## 无障碍（Accessibility 页）

- 读屏应能：导航并激活各段；**理解每段选择的效果**。
- **对比度**：outline 描边与背景/表面 **≥ 3:1**（成组相似组件靠描边区分）。
- **选中双通道**：对勾图标 + 颜色变化——**颜色不能是唯一选中信号**（官方原文）。
- **初始焦点**：落在第一段（LTR 最左、RTL 最右），**无论当前选中状态**。
- **键盘**：Tab 进入按钮组；**方向键在段间移动**；Space / Enter 选中——单选为「选中/切换」，多选可逐段或全选/全不选。
- **角色语义**：单选行为像 radio（标签 Radiogroup）；多选行为像 checkbox（标签 Checkbox）。
- **标签**：与可见文字一致（Relevance、Distance）；纯图标段用动作描述（如单个货币符号 → "Inexpensive"）。

## Compose 实现

稳定 API（material3 1.3+）：

```kotlin
var selected by rememberSaveable { mutableIntStateOf(0) }
val options = listOf("日", "周", "月")

SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
    options.forEachIndexed { index, label ->
        SegmentedButton(
            selected = selected == index,
            onClick = { selected = index },
            shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size)
        ) { Text(label) }
    }
}
```

多选用 `MultiChoiceSegmentedButtonRow` + `SegmentedButton(checked = …, onCheckedChange = …)`。

- `shape` 必须用 `SegmentedButtonDefaults.itemShape(index, count)`——它负责首尾段的**外侧圆角**与中间段**直角拼接**；手写 `RoundedCornerShape` 会变成三个独立按钮。
- 新项目优先评估 connected button group（Expressive）；`ButtonGroup` / `ConnectedButtonGroup` 一族 API **需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`，签名随版本变动**。

## 常见错误

1. 分段超过 5 个，标签压成省略号（官方：改用 chips）。
2. 手写圆角，首尾与中间拼接不对。
3. 用 segmented button 做导航切换（Tabs 职责，会丢返回栈语义）。
4. 选中态只改文字颜色，没有填充或对勾（违反双通道要求）。
5. 图标标签与文字标签混用。
6. 多选场景用了单选组件（或反之，语义角色错误）。
7. 大屏整条铺满，段内留白过多。
8. 密度调了高度（每档 -4dp）却没复测 48dp 触控目标。

## 来源

- https://m3.material.io/components/segmented-buttons/overview（不推荐声明、M2 差异、40dp）
- https://m3.material.io/components/segmented-buttons/specs（测量表、颜色角色、密度 -4dp）
- https://m3.material.io/components/segmented-buttons/guidelines（段数、标签、位置）
- https://m3.material.io/components/segmented-buttons/accessibility（3:1、双通道、键盘、角色）
- 核验日期：2026-11-16
- 关联：`02-Components/button-group.md`（官方指定的替代品）
