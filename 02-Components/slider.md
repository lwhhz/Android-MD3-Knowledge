# Slider / 滑块

- 官方指南：https://m3.material.io/components/sliders ｜ 规格页：https://m3.material.io/components/sliders/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Measurements 表
- 状态：**五个尺寸档的数值全部为官方原值**

## 用途

Slider 在一个连续或离散区间里取值。它让用户在**看到范围**的前提下调节。

| 场景 | 控件 |
|---|---|
| 在区间内取值（音量、亮度、价格区间） | **Slider** |
| 精确输入一个数字 | TextField（数字键盘） |
| 二选一开关 | Switch |
| 少量互斥选项 | Radio / Segmented Button |

**判断标准**：如果用户需要知道"我在整个范围里的哪个位置"，用 Slider；只需要一个精确数字，用输入框。

## 结构

```
        ┌─ 数值气泡（拖动时出现）
────────●━━━━━━━━━━━━━━━━━  ← 轨道 + 已选段 + Handle
```

- **Track**：分隔已选/未选
- **Active track**：已选段
- **Handle**：拖动点（Expressive 里是**窄竖条**，不是圆点）
- **Value label**：拖动时才显示，松手后消失
- **Inset icon**：轨道两端的图标（如音量小/大）

## 尺寸（官方 Measurements 原值）

Expressive 给了五个尺寸档：

| 属性 | XS | S | M | L | XL |
|---|---|---|---|---|---|
| 轨道高度 | **16dp** | **24dp** | **40dp** | **56dp** | **96dp** |
| Handle 高度 | **44dp** | **44dp** | **52dp** | **68dp** | **108dp** |
| Handle 宽度 | **4dp** | 4dp | 4dp | 4dp | 4dp |
| 轨道形状（圆角） | 8dp | 8dp | 12dp | 16dp | 28dp |
| 两端图标尺寸 | — | — | 24dp | 24dp | 32dp |

另外两个固定值：
- **数值标签容器高度 44dp**
- **数值标签容器宽度 48dp**

两个值得注意的点：

1. **Handle 宽度只有 4dp** —— 它是细竖条，不是圆点。这是 Expressive 滑块的识别特征，M3 老版本的圆点不是这个规格。
2. **Handle 高度（44-108dp）远大于轨道高度（16-96dp）** —— handle 是"竖着跨越轨道"的，视觉上有明显出挑。

## 状态

Enabled · Disabled · Hovered · Focused · **Pressed** · 拖动中（Thumb 带数值气泡）

拖动时**必须显示当前值**，否则用户不知道精确停在哪——这是滑块最常见的可用性缺陷。

## Compose 实现

```kotlin
var value by remember { mutableFloatStateOf(50f) }

Slider(
    value = value,
    onValueChange = { value = it },
    valueRange = 0f..100f,
    steps = 9,                          // 0..100 每 10 一个刻度（不含两端）
    onValueChangeFinished = { commit(value) }
)
```

关键参数：

- **`onValueChange` 高频触发**，只更新 UI 状态；真正落库/请求放 `onValueChangeFinished`，否则每拖一像素打一次接口。
- `steps` = 中间刻度数（`0..100 steps=9` 表示 11 个停靠点）。
- 区间选择用 `RangeSlider`。
- 需要自定义 handle 外观时用 `thumb` / `track` 槽位，不要自己拼一个 `Box` + `draggable`。

带图标的两端：

```kotlin
Row(verticalAlignment = Alignment.CenterVertically) {
    Icon(Icons.Default.VolumeMute, contentDescription = null)
    Slider(value = volume, onValueChange = onVolume, modifier = Modifier.weight(1f))
    Icon(Icons.Default.VolumeUp, contentDescription = null)
}
```

## 无障碍

- Slider 自带 `progressBarRangeInfo` 语义，TalkBack 会朗读百分比，但**必须给可理解的标签**（用 `Modifier.semantics { contentDescription = "音量" }` 或在旁边放可见文字）。
- 两端图标若是纯装饰，`contentDescription = null`；如果是可点的快捷按钮，要单独给语义。
- 触控目标要覆盖 Handle 高度（44dp 起），不要只让 4dp 的竖条可拖。
- 键盘 / 遥控器方向键要能调节，步长与 `steps` 一致。
- 拖动时的数值气泡要能被读屏读到，不能只做视觉。

## 常见错误

1. `onValueChange` 里直接调接口，拖动时打几十次请求。
2. 拖动时不显示当前值。
3. 用 Slider 让用户选精确数字（如金额），应该用输入框。
4. 没有端点标签，用户不知道区间含义（0-100 是什么单位？）。
5. Handle 只有 4dp 宽却只有它可拖，触控体验极差。
6. 离散取值却没设 `steps`，用户能停在任意小数上。
7. 用 M3 老版本的圆点 handle 样式去对标 Expressive 规格（两者不同）。
8. 深浅色下 active track 与 inactive track 对比度不足。

## 来源

- https://m3.material.io/components/sliders/specs
- `01-Foundations/color-system.md`（primary / surfaceContainerHighest 的配对）
- `03-Android-Compose/components-api.md`（槽位 API）
