# Slider / 滑块

- 官方指南：https://m3.material.io/components/sliders ｜ 规格页：https://m3.material.io/components/sliders/specs
- Compose API：`Slider` / `RangeSlider`（稳定）；Expressive 配置见文末
- 核验日期：2026-11-16 ｜ 核验方式：官方 Overview / Specs / Guidelines / Accessibility 页全文 dump
- 状态：**五档尺寸表已与官方 specs 逐格核对一致**（2026-11-16 dump）

## 用途

> Changes made with sliders must take effect immediately, so people can understand the effects of their selection as they're moving the slider.

在连续或离散区间取值（音量、亮度、滤镜强度），**调节立即生效**。需要精确数字用输入框；二选一用 Switch。

## 变体与配置（M3 vs Expressive）

| 项 | M3 | M3 Expressive |
|---|---|---|
| Standard（旧称 continuous slider） | ✅ | ✅ |
| Centered（从中间起，正负区间） | ✅ 仅 Web | ✅ |
| Range（双柄区间） | ✅ | ✅ |
| Discrete / Stops 刻度 | Discrete slider | ✅ 改名「stops 配置」 |
| Orientation：水平（默认）/ 垂直 | 仅水平 | ✅ 垂直 |
| 尺寸 XS（默认）/ S/M/L/XL | 仅 XS | ✅（Android Views 可用预设，其余平台用 token） |
| Inset icon / Value indicator | ✅ | ✅ |

- **Centered**：0 或默认值在区间中点时用（如正负平衡）。
- **Range**：定义最小/最大值；**避免竖向 range slider**（认知负担大）。
- 尺寸 token 互换：把 `md.comp.slider.xsmall.[...]` 换成目标尺寸档。

## 结构

**Track**（active 段 + inactive 段）· **Handle** · **Value indicator**（可选）· **Stop indicators**（可选）· **Inset icon**（可选）。LTR 从左到右递增，RTL 反向。Handle 按下时改变形状（宽度收窄）给出反馈。

## 尺寸（官方 Measurements 原值，五档逐格核对）

| 属性 | XS | S | M | L | XL |
|---|---|---|---|---|---|
| 轨道高度 | **16dp** | **24dp** | **40dp** | **56dp** | **96dp** |
| Handle 高度 | **44dp** | **44dp** | **52dp** | **68dp** | **108dp** |
| Handle 宽度 | **4dp**（全档一致） |||||
| 轨道形状（圆角） | 8dp | 8dp | 12dp | 16dp | 28dp |
| 两端 Inset icon 尺寸 | — | — | 24dp | 24dp | 32dp |

固定值：**数值标签容器高 44dp、宽 48dp**。

- Handle 宽 **4dp** 是官方值——细竖条是 Expressive 滑块的识别特征（M2 是圆柄）。
- 按下时 handle 宽度**收窄**（无官方数值），同时弹出数值标签。
- **active 与 inactive 轨道必须同宽**；尺寸越大触控目标与视觉权重越大。

## 颜色角色（官方列表）

官方 Specs 列出的角色（顺序对应 value indicator 容器/文字、active track、handle、inactive track、inset icon 等）：

`inverse surface` · `inverse on surface` · `primary` · `on primary` · `secondary container` · `on secondary container`

⚠️ 推断，页面未明示各角色到部件的一一映射；**页面未出现 `surfaceContainerHighest`**——旧文档"primary / surfaceContainerHighest 配对"与 dump 冲突，不要再用。

## 状态

Enabled · Disabled · Hovered · Focused · **Pressed**（token 模块分五态给出）。

## 使用规则（Guidelines 翻译）

1. **数值标签**：拖动/按压对应 handle 时显示；range slider **一次只显示一个 handle 的值**。值若已在别处显示（如输入框），内置标签可省。
2. 可用**外部输入框替代内置标签**，但两者必须自动同步，且**Tab 顺序紧接滑块**。
3. **Stop indicators**：handle 吸附最近停靠点；**不要放太多**（视觉拥挤、难调值）。所有滑块默认在 inactive track 末端有停靠点以保证 ≥3:1 对比；对比达标才能去掉。
4. 两端图标/文字可替代 stop indicator 标示区间。
5. **Inset icon**：只用于 **M/L/XL**（轨道 ≥ 40dp）的 standard slider——**禁用于 XS/S、centered、range**；active track 空间不足时图标移到 inactive track；零值处可换图标（如音量变静音）。
6. **XL 只用于 hero 时刻**——滑块即页面主角时。
7. 交互行为：拖动平滑移动；点轨道（select jump）跳到点击处或最近停靠点；键盘见下。

## Compose 实现

```kotlin
var value by remember { mutableFloatStateOf(50f) }
Slider(
    value = value,
    onValueChange = { value = it },                 // 高频回调，只更新 UI 状态
    onValueChangeFinished = { commit(value) },     // 提交动作放这里
    valueRange = 0f..100f,
    steps = 9                                       // 0..100 每 10 一档（不含两端）
)
```

- 区间选择用 `RangeSlider`；`thumb` / `track` 槽位可自定义外观，不要自己拼 `Box + draggable`。
- ⚠️ Compose `Slider` 为水平方向；垂直方向、centered、inset icon 属 Expressive 配置，API 未在 dump 中给出，用前查当前版本 reference。
- 官方建议的外部输入框方案：滑块旁放 `TextField`，`Tab` 从滑块直接到输入框。

## 无障碍

- AT 用户应能：导航到滑块、沿轨道控制 handle、按输入类型获得反馈。
- **初始焦点直接落在 handle 上**（滑块的主交互元素）。
- **键盘操作**（官方表）：

| 键 | 行为 |
|---|---|
| Tab | 焦点到 handle |
| 方向键 | 增/减一个值或一个停靠点 |
| Space + 方向键 | 增/减一个更大间隔 |
| Home / End | 跳到第一个/最后一个值 |

- **对比度**：inactive track 末端用视觉锚点（stop indicator 或图标）保证 ≥ **3:1**。
- **标签**：无障碍标签与相邻 UI 文字一致，组件应为 **slider 角色**；读屏读到「文字 → 角色」。滑块外的图标按钮应是 button 角色。
- 拖动时的数值气泡不能只做视觉——通过语义让读屏感知当前值。

## 常见错误

1. `onValueChange` 里直接调接口（高频触发）。
2. 拖动时不显示当前值。
3. Inset icon 加到 XS/S 或 centered/range 滑块上。
4. 竖向 range slider（认知负担）。
5. 没有端点标示（无停靠点/图标/文字），低对比背景下找不到轨道末端。
6. 离散取值却没设停靠（用户停在任意小数上）。
7. 引用旧文「surfaceContainerHighest 作未选轨道色」——官方角色表无此角色（见颜色一节）。
8. 用 M2 圆点 handle 视觉对标 Expressive 规格（官方明示差异）。

## 来源

- https://m3.material.io/components/sliders/overview
- https://m3.material.io/components/sliders/specs
- https://m3.material.io/components/sliders/guidelines
- https://m3.material.io/components/sliders/accessibility
- 核验日期：2026-11-16
