# Segmented Button / 分段按钮

- 官方指南：https://m3.material.io/components/segmented-buttons ｜ 规格页：https://m3.material.io/components/segmented-buttons/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Measurements 表
- 状态：**尺寸全部为官方原值**

## 用途

Segmented button 把 2-5 个**互斥或可多选**的选项并排成一条，点击即选中，不需要额外的"确定"。

它和相邻控件的边界很容易混：

| 控件 | 语义 | 数量 | 是否换页 |
|---|---|---|---|
| **Segmented button** | 选选项（单选或多选） | 2-5 | 否 |
| Tabs | 切换并列视图 | 2-7 | 否，但换内容区 |
| Filter Chip | 多条件筛选，可叠加 | 多个 | 否 |
| Radio | 互斥单选 | 少量 | 否 |

**判断标准**：选项本身就是操作结果（如"日/周/月"视图切换、对齐方式、字号档位），用 Segmented button；如果是"筛选数据"，用 Filter Chip。

## 尺寸（官方 Measurements 原值）

| 属性 | 值 |
|---|---|
| 容器宽度 | 动态（按标签） |
| 单个分段宽度 | 容器宽 / 分段数（例：1/3） |
| 高度 | **40dp** |
| 描边宽度 | **1dp** |
| 标签对齐 | 居中 |
| 左右内边距 | **最小 12dp** |
| 元素间距 | **8dp** |
| 触控目标 | **48dp** |

**密度（Density）**：官方支持为紧凑界面降低密度，**只作用于高度，每一档减 4dp**。也就是说密集列表里可以做到 36 / 32dp，但宽度和内边距不变。

## 变体与配置

- **单选（single-select）**：一组里只能选一个，等价于 Radio 的横向表达。
- **多选（multi-select）**：可同时选多个，等价于 Checkbox。
- 分段数：官方建议 2-5 个，超过 5 个标签会被压到不可读。

选中态的视觉：容器填充 + 对勾图标。**不要只改文字颜色**。

## Compose 实现

单选：

```kotlin
var selected by rememberSaveable { mutableIntStateOf(0) }
val options = listOf("日", "周", "月")

SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
    options.forEachIndexed { index, label ->
        SegmentedButton(
            selected = selected == index,
            onClick = { selected = index },
            shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size)
        ) {
            Text(label)
        }
    }
}
```

多选：

```kotlin
MultiChoiceSegmentedButtonRow {
    options.forEachIndexed { index, label ->
        SegmentedButton(
            checked = checked[index],
            onCheckedChange = { checked = checked.toMutableList().also { it[index] = !it[index] } },
            shape = SegmentedButtonDefaults.itemShape(index, options.size)
        ) { Text(label) }
    }
}
```

`shape` 必须用 `itemShape(index, count)`：它负责把首尾分段的**外侧圆角**和中间分段的**直角拼接**算对。手写 `RoundedCornerShape` 会让整条看起来像三个独立按钮。

## 无障碍

- 一组分段按钮相当于一个复合控件，读屏应报出「N 个中的第 M 个」——单选行用 `selectableGroup()` 语义。
- 选中态由组件提供，不要额外拼「已选中」文字。
- **只有图标的分段按钮不可用**，必须有文字（或至少 `contentDescription`）。
- 触控目标 48dp 由组件补齐，但密度调整后要重测。
- 键盘 / 遥控要能用方向键在分段间移动。

## 常见错误

1. 分段数超过 5 个，标签被压成省略号。
2. 手写圆角，首尾与中间的拼接不对。
3. 用 Segmented button 做导航切换（那是 Tabs 的职责，会丢返回栈语义）。
4. 选中态只改文字颜色，没有填充或对勾。
5. 只有图标没有文字。
6. 多选场景用了单选组件。
7. 在窄屏上放 4 个中文长标签（如"最近七天"），直接溢出。
8. 密度调了高度却没调触控目标，实际可点区域不足 48dp。

## 来源

- https://m3.material.io/components/segmented-buttons/specs
- `02-Components/tabs.md`（Tabs 与分段按钮的边界）
- `02-Components/chips.md`（Filter Chip 的适用场景）
