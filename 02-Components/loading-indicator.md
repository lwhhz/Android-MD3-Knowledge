# Loading Indicator / 加载指示（M3 Expressive）

- 官方指南：https://m3.material.io/components/loading-indicator ｜ 规格页：https://m3.material.io/components/loading-indicator/specs
- 核验日期：2026-11-16 ｜ 核验方式：官方 Overview / Specs / Guidelines / Accessibility 页全文 dump
- 状态：**M3（基础版）无此组件，M3 Expressive 新增**；尺寸为官方原值

## 用途与代际

> The loading indicator is designed to show progress that loads in under five seconds. It should replace most uses of the indeterminate circular progress indicator.

它表示「**正在处理、进度不可知**」，是一组形状持续 morph 的动画；与 Progress indicator（有进度概念）不是同一组件。官方定位三条硬规则：

- **等待时间选控件**（与 Progress 共用同一张官方对照表）：

| 预期等待 | 官方推荐 |
|---|---|
| 即时（< 200ms） | 不显示任何指示器 |
| 短（200ms ~ 5s） | **Loading indicator** |
| 长（> 5s） | **Progress indicator** |

- **不得从 loading indicator 过渡成 determinate progress indicator**；若过程可能从"不可知进度"变"可知进度"，应在 progress indicator 内部从 indeterminate 切到 determinate。
- 用于 **pull-to-refresh**（Jetpack Compose 专属行为）；「Always reflect an ongoing process and are never simply decorative」——永远表示真实进行中的过程，不做纯装饰。

实现可用性：Figma ✅；Jetpack Compose（Expressive）✅；Android Views（MDC-Android）Expressive ✅；Web ❌。

## 配置

| 配置 | 说明 |
|---|---|
| **Default** | 独立成形，无容器 |
| **Contained** | 带可选容器（见下） |

单一 token set（不按形态分套）。

## 结构与规格

结构：**Active indicator**（必选）+ **Container**（可选）。

> To ensure sufficient margins, the size is **48dp** while the shape container is **38dp**.

| 属性 | 值 |
|---|---|
| 默认占位尺寸 | **48dp** |
| 形状容器（可见部分） | **38dp** |
| 尺寸范围 | **24dp ~ 240dp**（「Avoid exceeding the minimum and maximum sizes」） |

- Active indicator 是**由 7 个 Material 3 形状组成的循环 morph 序列**。
- 缩放时容器与 active indicator 的比例保持不变；默认 48dp 适合移动端，大窗口可放大（上限 240dp），超大号留给桌面等大窗口。
- 布局高度写 48dp 而不是 38dp——差值是官方预留的动画边距。

## 颜色角色（官方原值）

| 配置 | 角色 |
|---|---|
| Default | active indicator = `primary` |
| Contained | 容器 = `primary container`，active indicator = `on primary container` |

> When the container is visible, the active indicator should change color from primary to on-primary-container.

容器使用场景（官方原文）：loading indicator **叠在其他内容上**时显示容器以增强对比；直接放在 surface 上则不需要；**pull-to-refresh 必须带容器**。容器是圆形。

## 使用规则（Guidelines 翻译）

1. **整页/整容器加载时居中**；在已有内容页追加内容时，放在新内容将出现的空白处，**不要遮挡现有内容**。
2. **可嵌入按钮**等组件，表示动作进行中（如表单校验、检查更新）。
3. **Pull-to-refresh 阈值**：必须拖过阈值才触发刷新；过阈值后回退手势会取消刷新。指示器保持可见直到刷新完成且新内容可见（或用户离开），**不要让它被滚出屏幕**——否则用户会以为刷新只关联某个卡片而非整屏。
4. 等待很长时，考虑允许用户**离开页面**等后台完成。

## Compose 实现

```kotlin
// M3 Expressive API（androidx.compose.material3）
// ⚠️ @ExperimentalMaterial3ExpressiveApi，以当前版本 API reference 为准
LoadingIndicator(
    modifier = Modifier.size(48.dp)   // 官方默认占位尺寸
)
// 带容器版本使用 ContainedLoadingIndicator（同为实验性 API）
```

当前稳定 material3 没有"安全替身"——若项目尚未启用 Expressive，用 `CircularProgressIndicator()`（indeterminate）功能等价，但不符合 2025 Expressive 指南的推荐用法。

## 无障碍

- AT 用户应能：导航到 loading indicator、理解它在传达什么进度、**不依赖手势即可触发刷新**（pull-to-refresh 必须提供替代方式，如 app bar 中的刷新按钮）。
- Active indicator 对其所在背景/宿主组件至少 **3:1 对比度**；容器本身不做对比度要求（「the container does not」）。
- 使用 **progress bar 无障碍角色**，并写明加载对象：

> Write a label describing the purpose of the loading indicator, such as "loading news article" or "refreshing page."

## 常见错误

1. 用它表达有进度的过程（应改用 determinate progress indicator）。
2. 从 loading indicator 切到 determinate progress indicator（官方禁止；应在 progress 内部切换）。
3. 布局写 38dp 高，形状放大被裁。
4. Contained 场景仍用 `primary`（应切 `onPrimaryContainer`）。
5. pull-to-refresh 没有非手势替代入口。
6. 让指示器被滚出屏幕，或加载完成后不消失。
7. 在 M3 非 Expressive / Web 项目中找官方实现（不存在）。

## 来源

- https://m3.material.io/components/loading-indicator/overview
- https://m3.material.io/components/loading-indicator/specs
- https://m3.material.io/components/loading-indicator/guidelines
- https://m3.material.io/components/loading-indicator/accessibility
- 核验日期：2026-11-16
