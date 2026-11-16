# Progress Indicator / 进度指示

- 官方指南：https://m3.material.io/components/progress-indicators ｜ 规格页：https://m3.material.io/components/progress-indicators/specs
- Compose API：`LinearProgressIndicator` / `CircularProgressIndicator`（稳定）；`LinearWavyProgressIndicator` / `CircularWavyProgressIndicator`（Expressive 实验性）
- 核验日期：2026-11-16 ｜ 核验方式：官方 Overview / Specs / Guidelines / Accessibility 页全文 dump
- 状态：**配置、颜色与固定数值已核对**；变体厚度等仅图片示例，无官方数字（见文末说明）

## 行为、形状、厚度三选一

| 维度 | 取值 | M3 | M3 Expressive |
|---|---|---|---|
| **行为** | Determinate（默认）/ Indeterminate | ✅ | ✅ |
| **厚度** | Fixed（**4dp**） | ✅ | ✅ |
| **厚度** | Configurable（可变） | ❌ | ✅ |
| **形状** | Flat（默认）/ **Wavy** | ✅ / ❌ | ✅ / ✅ |

变体：**Linear**（线性）与 **Circular**（环形），两代均有。

**能拿到总量就用 determinate**——官方要求 determinate 必须准确反映真实进度；仅当进度与等待时长都未知时用 indeterminate。过程信息增多时，**应从 indeterminate 切到 determinate**（可以切换，这与 loading indicator 不同）。

官方代际提示：

> The circular and linear progress indicator had separate token sets. These are no longer recommended.

（环形/线性旧的两套 token 已废弃，统一用 common token set。）

## 结构

**Active indicator**（已完成的进度）+ **Track**（轨道）+ **Stop indicator**（终点标记，仅线性 determinate）。

- Linear 从 leading edge 向 trailing edge 填充；Circular 默认从顶部顺时针。
- Indeterminate 时 active indicator 在固定轨道上反复伸缩移动。
- 低百分比空间受限时，active indicator 显示为一个**圆点**，让人知道进度已开始。
- **Wavy 形状**会让组件整体高度变大；很小尺寸下波浪不明显——小按钮里用 flat。

## 数值（官方原值）

| 属性 | 值 |
|---|---|
| 默认轨道厚度 | **4dp** |
| 线性指示器与屏幕边缘内缩 | **4dp** |
| Stop indicator | **4dp 圆点** |
| Circular 尺寸范围 | **24dp ~ 240dp**（不要超出） |
| Linear 放置的最小元素宽度 | **40dp** |
| Linear 两端 padding | **4dp 起**（可调整） |

> The stop indicator is a 4dp circle that marks the end of a linear determinate progress indicator to meet Material's accessibility standards.

Stop indicator **只用于线性 determinate**；track 与容器/背景对比度 ≥ 3:1 时才可省略。Wavy 的 amplitude/wavelength 官方只给了定义（振幅从中线到波峰中线，波长为相邻波峰间距），具体数值仅图片示例。

## 颜色角色（官方原值）

| 部位 | 角色 |
|---|---|
| Active indicator | `primary` |
| Track | `secondary container` |

按钮内使用时（官方原文）：active indicator 改用**按钮图标/文字同色**并**去掉 track**，以保证 ≥ 3:1 对比度。

## 使用规则（Guidelines 翻译）

1. **多个条目一起加载时，只用一个指示器表达整组进度**；「Don't add progress indicators to every activity」。
2. **同一过程在全产品中用同一变体**：刷新在 A 处用 circular，在 B 处也要用 circular。
3. **Linear 贴容器边缘放**（容器变形时放在会动画的那条边）；页面顶部一条指示整页加载，附着卡片表示只加载卡片。
4. **Circular 居中**于所加载的容器/页面；追加内容时放新内容出现的空白处。加载不长也可换 loading indicator。
5. 按钮内：用 circular 表示动作进行中（< 5s 的 indeterminate 场景）；**不要给列表里每个按钮都挂进度指示**。
6. **RTL**：linear 要水平镜像，circular 不需要镜像。
7. 大屏：超大号（上限 240dp）留给桌面等大窗口；波形要随尺寸等比缩放。
8. 等待很长时考虑允许用户离开页面。

（等待时间对照表见 `02-Components/loading-indicator.md`：<200ms 不显示 / 200ms–5s loading indicator / >5s progress indicator。）

## Compose 实现

```kotlin
// Determinate（progress 为 lambda，旧版直接传 Float）
LinearProgressIndicator(progress = { downloaded / total })

// Indeterminate
CircularProgressIndicator()

// Expressive 波浪形 ⚠️ @ExperimentalMaterial3ExpressiveApi
LinearWavyProgressIndicator(progress = { downloaded / total })
CircularWavyProgressIndicator()
```

按钮内加载态：按官方要求 active indicator 与按钮内容同色、去掉 track——Compose 里用 `trackColor = Color.Transparent`、`color = LocalContentColor.current`。⚠️ 具体尺寸 dump 未给数字（旧文档的 16dp 为推断，页面未明示）。

## 无障碍

- AT 用户应能：导航到指示器、理解它传达的进度。
- Active indicator 与 stop indicator 对背景 ≥ **3:1** 对比度；嵌入其他组件时对宿主组件 ≥ 3:1。
- Track 对比度 < 3:1 时**必须保留 stop indicator**；「the end of the track must be easy to identify」。
- 使用 **progress bar 无障碍角色**，标签写明过程与对象（如 "Loading news article"、"Refreshing page"）。
- 状态不只靠颜色：进度本身是形状变化，但成功/失败等结果要另加图标或文案。

## 常见错误

1. 能算进度却用 indeterminate。
2. 每个活动各挂一个指示器（应一个代表整组）。
3. 同一过程在不同页面混用 linear/circular。
4. 去掉 stop indicator 但 track 对比度不足 3:1。
5. 按钮内沿用默认 `primary` 色 + 默认 track，对比度不足。
6. RTL 下 linear 未镜像。
7. 在 < 40dp 宽的元素里塞 linear。
8. 仍按环形/线性两套 token 旧文档配置（已废弃统一）。

## 仅图片无数字的项

Specs 页以下内容**只有图例，无文字数值**，无法引用 dp 值：linear/circular 各变体（加厚款）的示例尺寸、wavy 的 amplitude/wavelength 具体取值。旧文档如引用了这些数值即属推断。

## 来源

- https://m3.material.io/components/progress-indicators/overview
- https://m3.material.io/components/progress-indicators/specs
- https://m3.material.io/components/progress-indicators/guidelines
- https://m3.material.io/components/progress-indicators/accessibility
- 核验日期：2026-11-16
