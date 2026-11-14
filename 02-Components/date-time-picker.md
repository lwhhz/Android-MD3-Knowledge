# Date & Time Pickers / 日期与时间选择器

- 官方指南：https://m3.material.io/components/date-pickers ｜ https://m3.material.io/components/time-pickers
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Specs 页
- 状态：时间选择器尺寸为官方原值；日期选择器测量以图片呈现，未取到文字值

## 一句话原则

**选择器是辅助，不是唯一入口。** 任何日期/时间都必须同时支持手动输入——否则批量录入、跨年选择、读屏用户都会被卡死。

## 日期选择器：三个变体

| 变体 | 形态 | 适用 |
|---|---|---|
| **Docked** | 贴着输入框展开的浮层 | 桌面 / 大屏，有足够横向空间 |
| **Modal** | 居中对话框 + 日历 | 手机默认 |
| **Modal input** | 对话框 + 纯文本输入 | 需要输入远期日期、键盘用户 |

另有 **Date range（范围选择）**：选起止两个日期。

选型：手机用 Modal；需要键盘输入时提供 Modal input；大屏可用 Docked。

## 时间选择器：两个变体

| 变体 | 形态 | 适用 |
|---|---|---|
| **Dial** | 表盘拖动 | 触摸设备，快速粗选 |
| **Input** | 数字输入框 | 精确输入、键盘、读屏 |

支持 **24 小时制**（跟随系统设置，不要自己写死 12 小时制）。

### 尺寸（官方 Measurements 原值）

| 元素 | 属性 | 值 |
|---|---|---|
| 容器 | 上下内边距 | **24dp** |
| 容器 | 左右内边距 | **24dp** |
| 容器 | 标题对齐 | 左对齐 |
| 时间选择选区（time selector） | 宽度 | **96dp**（24 小时竖排为 **114dp**） |
| 时间选择选区 | 高度 | **80dp** |
| 上下午选区（period selector，竖排） | 宽 × 高 | **52 × 80dp** |
| 上下午选区（横排） | 宽 × 高 | **216 × 38dp** |
| 容器整体 | 宽 / 高 | 动态 |

注意 24 小时制会让时间选区**变宽**（96 → 114dp），因为要去掉上下午列、把小时位撑开。

## 颜色与结构

时间选择器（Dial）的元素：Clock dial selector center / track / container / label text、Time selector container / label text、Period selector。

日期选择器：标题区、星期表头、日期网格、选中态圆点、今日描边、操作按钮。

**选中态与"今天"必须是两种视觉**——只用一种颜色区分，用户分不清"我选的"和"今天的"。

## Compose 实现

日期（对话框形态）：

```kotlin
val state = rememberDatePickerState(
    initialSelectedDateMillis = todayMillis,
    selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean =
            utcTimeMillis >= todayMillis
    }
)

DatePickerDialog(
    onDismissRequest = { show = false },
    confirmButton = {
        TextButton(onClick = { onPick(state.selectedDateMillis); show = false }) {
            Text("确定")
        }
    },
    dismissButton = { TextButton(onClick = { show = false }) { Text("取消") } }
) {
    DatePicker(state = state)
}
```

范围选择用 `DateRangePicker` + `rememberDateRangePickerState()`。

时间：

```kotlin
val timeState = rememberTimePickerState(
    initialHour = 9,
    initialMinute = 30,
    is24Hour = isSystem24Hour()
)
TimePicker(state = timeState)     // Dial
TimeInput(state = timeState)      // Input
```

`TimePickerDialog` 在较新版本才提供，旧版本需用 `AlertDialog` / `BasicAlertDialog` 自己包一层——用之前确认版本。

## 行为规则

- **默认值**：新建用"今天 / 当前时间"；编辑用原值；不要默认 1970。
- **可选范围**要显式限制（如不能选过去日期），并把不可选日期置灰而不是隐藏。
- 选完立即关闭还是需要"确定"？一致性优先：同一产品里统一。
- 时区：存 `Instant` / UTC 毫秒，展示时转本地时区。**不要在 UI 层存格式化字符串。**
- 12/24 小时制跟随 `DateFormat.is24HourFormat(context)`，不要写死。

## 无障碍

- **必须提供文本输入替代**（Dial 对读屏和运动障碍用户不可用）。
- 每个日期格子的 `contentDescription` 要完整（"2026 年 11 月 15 日，星期六"），不能只读数字。
- 已选 / 今天 / 不可选三种状态都要能通过语义区分，不能只靠颜色。
- 日历网格支持方向键导航（TV、键盘场景）。
- 对话框打开时焦点进入，关闭后回到触发控件。

## 常见错误

1. 只有表盘没有输入替代。
2. 日期格子只读数字，读屏听不出是几月。
3. 12/24 小时制写死。
4. 存格式化字符串而不是时间戳，跨时区直接错一天。
5. 不可选日期用隐藏而非置灰，用户以为日历坏了。
6. 选中态和"今天"用同一种颜色。
7. 默认值给 1970-01-01，用户每次都要从头翻。
8. 选完没有"确定"却又不关闭，用户不知道生效没有。

## 待核验

- [ ] 日期选择器的容器尺寸、日历格子尺寸（官方以图片呈现）

## 来源

- https://m3.material.io/components/date-pickers/specs
- https://m3.material.io/components/time-pickers/specs
- `02-Components/text-fields.md`（输入替代形态）
- `01-Foundations/accessibility.md`
