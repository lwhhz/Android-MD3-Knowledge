# Date & Time Pickers / 日期与时间选择器

- 官方指南：https://m3.material.io/components/date-pickers ｜ https://m3.material.io/components/time-pickers
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页 ×2）
- 状态：**时间选择器全部尺寸为官方明示值；日期选择器 measurements 页只有图片、无数字**（官方原文仅给出 48dp 触控目标与"不随断点缩放"两条硬约束）

本篇把日期与时间合并，因为二者的选型逻辑、无障碍要求高度重合；两者的尺寸可得性差别很大，见下文标注。

## 一句话原则

**选择器是辅助入口，不是唯一入口。** 日期与时间都必须支持手动输入——这是官方无障碍章节的第一条要求，不是可选项。

## 选型：日期

| 变体 | 形态 | 官方定位 |
|---|---|---|
| Docked | **默认显示输入框**，点击输入框后日历下拉在下方展开 | 中等/展开断点；需要近期与远期都能跳 |
| Modal | 对话框内日历 | 紧凑断点最常用；推荐全屏 |
| Modal input | 对话框内纯数字输入 | 不需要日历视图的日期；远期日期 |

> "Don't use a modal date picker to prompt for dates in the distant past or future, such as a date of birth. In these cases, use a modal input picker or a docked date picker instead."

范围选择（Date range）是同族能力，起止日期用颜色连成一段，中间日期用弱高亮连接；常见于订机票、订酒店。

紧凑断点规则：

> "On compact breakpoints, such as mobile, a full-screen modal date picker is recommended to increase readability and touch target size. It can cover the entire screen."

全屏形态额外带 `×` 关闭按钮和 Save 确认。

## 选型：时间

| 变体 | 形态 | 官方定位 |
|---|---|---|
| Dial（表盘） | 圆形表盘，可点数字或拖动轨道 | 触摸设备快速选择 |
| Input（输入） | 时/分独立输入框 | 精确输入、键盘与读屏用户 |

两条硬规则：

- 输入形态必须能从任何时间选择界面进入（点键盘图标）。
- 24 小时制**由系统设置决定**，不在组件里切换；24 小时制下不显示 AM/PM。

时限提醒：时间选择器不适合毫秒级等细粒度选择（官方原文举例秒表应用）。

## 时间选择器尺寸（官方 Measurements 原值）

| 元素 | 属性 | 值 |
|---|---|---|
| 容器 | 上下 / 左右内边距 | **24dp / 24dp** |
| 容器 | 宽 / 高 | 动态 |
| 标题（headline）对齐 | 左对齐 | — |
| 时间选择区（Time selector container） | 宽 | **96dp**；24 小时竖排 **114dp** |
| 时间选择区 | 高 | **80dp** |
| 上下午区（竖排） | 宽 × 高 | **52 × 80dp** |
| 上下午区（横排） | 宽 × 高 | **216 × 38dp** |
| 表盘容器 | 尺寸 | **256dp** |
| 表盘选择手柄 | 尺寸 | **48dp** |
| 表盘中心点 | 尺寸 | **8dp** |
| 表盘轨道 | 线宽 | **2dp** |
| 时间输入框（input 形态） | 宽 × 高 | **96 × 72dp** |
| 上下午区（input 形态） | 宽 × 高 | **52 × 72dp** |

竖排与横排两套 measurements 的数值完全相同（官方把同一张表复制到两个标签页），差别只在布局方向。注意 input 形态的输入框比 dial 形态的时间选择区**矮 8dp**（72 vs 80），上下午区同理。

## 日期选择器尺寸

Specs 页的 docked / modal / modal input 三组 measurements **均为图片**，文字层只有标题（"Modal date picker padding and size measurements"），没有任何 dp 数值。可写入文档的官方明示值只有：

| 项 | 值 | 出处 |
|---|---|---|
| 所有可交互元素的触控目标 | **48×48dp** | accessibility 页 |
| 尺寸是否随断点缩放 | **否** | guidelines 页 |

> "The sizing of the docked and modal date picker components don't scale responsively to different breakpoints."
> "Interactive targets for all elements meet Material's 48x48dp minimum touch target requirement. Increasing density would negatively impact accessibility by limiting tappable/clickable targets."

⚠️ 推断，页面未明示：日历格子直径、对话框宽度/圆角等常见数值（如 40dp 选中圆、328dp 对话框宽）在本轮 dump 中查不到，本库不做估算补写；需要时以设计工具中的 Figma Design Kit 为准。

## 颜色角色

**日期选择器**（官方按元素顺序列出，元素与值的绑定关系未标注）：

| 变体 | 颜色角色（官方顺序） |
|---|---|
| Docked（日历） | `primary`、`onSurfaceVariant`、`onSurfaceVariant`、`onSurface`、`onSurface`、`primary`、`onSurfaceVariant`、`primary`、`surfaceContainerHigh`、`primary`、`onPrimary` |
| Docked（月份菜单） | `primary`、`onSurfaceVariant`、`onSurface`、`outlineVariant`、`surfaceContainerHigh`、`surfaceVariant`、`onSurface` |
| Modal（日选择） | `onSurface`、`onSurfaceVariant`、`surfaceContainerHigh`、`onSurfaceVariant`、`onSurfaceVariant`、`onSurface`、`primary`、`onSurface`、`primary`、`primary`、`onSurfaceVariant`、`outlineVariant` |
| Modal（年选择） | `onSurface`、`onSurfaceVariant`、`surfaceContainerHigh`、`onSurfaceVariant`、`onSurfaceVariant`、`primary`、`primary`、`outlineVariant`、`onSurfaceVariant` |
| Modal（范围选择） | 含 `secondaryContainer` / `onSecondaryContainer`（区间内日期）、`primary`（起止点）、`outlineVariant`（分隔线） |
| Modal input | `onSurface`、`onSurfaceVariant`、`surfaceContainerHigh`、`onSurfaceVariant`、`primary`、`primary`、`outlineVariant` |

**时间选择器**：

| 变体 | 颜色角色（官方顺序） |
|---|---|
| Dial | `onSurfaceVariant`、`onSurface`、`surfaceContainerHighest`、`onSurface`、`tertiaryContainer`、`onTertiaryContainer`、`surfaceContainerHigh`、`outline`、`onSurface`、`primary`、`onPrimary`、`primary`、`onSurfaceVariant`、`onSurface`、`surfaceContainerHighest`、`onPrimaryContainer`、`primaryContainer` |
| Input | `onSurfaceVariant`、`onSurface`、`surfaceContainerHighest`、`onSurface`、`tertiaryContainer`、`onTertiaryContainer`、`surfaceContainerHigh`、`outline`、`onSurface`、`primary`、`onSurfaceVariant`、`onPrimaryContainer`、`primaryContainer` |

可确认的语义（guidelines 原文支撑）：选中项用 `primary` / `onPrimary`，AM/PM 选中容器用 `tertiaryContainer` / `onTertiaryContainer`，未选中文本 `onSurface`，容器 `surfaceContainerHighest`。⚠️ 推断，页面未明示：其余角色的逐元素对应关系需在设计工具中核对。

## 使用规则

日期：

- ✅ Docked 适合中等及以上断点，输入框与日历**两种入口同时可用**
- ✅ Modal 横向滑动换月、纵向滚轮换年、点年份进年选择
- ✅ 范围选择的起止点与中间段必须视觉可分
- ✅ 对话框打开有进入/退出动画；`OK` 确认、`Cancel` 或点外部关闭
- ❌ 用 Modal 选生日等远期日期
- ❌ 把日期选择器按断点放大（官方 Don't）

时间：

- ✅ 表盘与输入两种形态可互相切换（键盘图标 ↔ 时钟图标）
- ✅ 12 小时制所有数字在外圈；24 小时制偶数在内圈、奇数在外圈
- ✅ 竖排/横排随设备方向切换；高度受限时退化到横排或 input
- ❌ 让选择器被屏幕边缘裁切（要换方向或换变体）
- ❌ 让弹层内部滚动（应换布局，而不是滚动）
- ❌ 视口受限时给表盘加密度（应改用 input）

## Compose 实现

日期（对话框 + 全屏/输入模式）：

```kotlin
val state = rememberDatePickerState(
    initialSelectedDateMillis = todayMillis,
    initialDisplayMode = DisplayMode.Picker,   // 可切 DisplayMode.Input
    selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long) = utcTimeMillis >= todayMillis
    }
)
DatePickerDialog(
    onDismissRequest = { show = false },
    confirmButton = { TextButton(onClick = { onPick(state.selectedDateMillis); show = false }) { Text("确定") } },
    dismissButton = { TextButton(onClick = { show = false }) { Text("取消") } }
) { DatePicker(state = state) }
```

范围选择用 `DateRangePicker` + `rememberDateRangePickerState()`。`DatePicker` / `DateRangePicker` / `DatePickerDialog` / `DisplayMode` 长期带 `@ExperimentalMaterial3Api`，升级依赖时先确认注解是否已摘除。

时间：

```kotlin
val state = rememberTimePickerState(
    initialHour = 9,
    initialMinute = 30,
    is24Hour = DateFormat.is24HourFormat(context)
)
TimePicker(state = state)   // 表盘
TimeInput(state = state)    // 输入
```

`TimePicker` / `TimeInput` 同样曾长期为 experimental；`TimePickerDialog` 是较新版本才提供的封装，旧版本需自己用 `BasicAlertDialog` 包一层——用前查当前依赖。

## 无障碍

日期：

| 要求 | 官方原文要点 |
|---|---|
| 手动输入 | 必须能纯文本输入日期，不经过日历 |
| 日历图标是唯一触发点 | 让选择器成为可选路径，减少读屏与键盘操作 |
| 禁止输入掩码 | 不要在输入过程中自动加斜杠；**在回车或失焦后**再格式化 |
| 接受多种格式 | 破折号、空格、斜杠、点号，以及单位数月/日前补 `0` |
| 组件标签 | 无障碍标签说明用途（如"活动日期"），空态与 placeholder 一致 |
| 辅助文本 | 说明格式（默认 "MM/DD/YYYY"），作为字段描述 |
| 颜色对比 | 日期文字与背景 ≥ **4.5:1** |
| 完整播报 | 标签枚举完整日期，读「Monday, August 17」而非「17」 |
| 星期表头 | 角色 column header；月份网格角色 grid |
| 清除按钮 | 非必需时移除，减少 tab 停留点 |

键盘：`Enter` 关闭并保存；`PageUp/Down` 上/下月同日；`Home/End` 当月首日；`Shift+PageUp/Down` 上/下年；`Shift+M` 跳月下拉；`Shift+Y` 跳年下拉。快捷键要写进 tooltip 与提示描述。

时间：

| 元素 | 无障碍标签 | Compose 角色 |
|---|---|---|
| 小时输入 | Hour | Text input |
| 分钟输入 | Minute | Text input |
| AM/PM | AM / PM | Radio button（列表内） |
| 键盘/时钟切换按钮 | Toggle input picker / Toggle dial picker | Button |
| Cancel / OK | Cancel / OK | Button |
| 表盘选择项 | `{Value} Hours or minutes of {Total}`，如 "Hour 7 of 12" | Button |

表盘选择手柄目标 **48×48dp**；`Tab` 落在时间槽、`Space/Enter` 激活。

## 常见错误

1. 只有表盘/日历，没有手动输入替代。
2. 日历格子只读数字，读屏听不出月份年份。
3. 输入过程中自动插斜杠（输入掩码），读屏播报与用户输入不一致。
4. 12/24 小时制写死，不跟随系统设置。
5. 用 Modal 让用户选生日，翻几十年。
6. 时间弹层在小屏被裁切，却选择内部滚动。
7. 12 小时制下仍显示 AM/PM 之外的 0–23 数字，或 24 小时制仍显示 AM/PM。
8. 存格式化字符串而非时间戳，跨时区错一天。
9. 假设日期选择器会随断点放大，大屏上仍按手机尺寸摆放。

## 来源

- https://m3.material.io/components/date-pickers/specs
- https://m3.material.io/components/date-pickers/guidelines
- https://m3.material.io/components/date-pickers/accessibility
- https://m3.material.io/components/time-pickers/specs
- https://m3.material.io/components/time-pickers/guidelines
- https://m3.material.io/components/time-pickers/accessibility
