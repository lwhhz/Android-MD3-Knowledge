# Button Group / 按钮组（Expressive）

- 官方指南：https://m3.material.io/components/button-groups ｜ 规格页：https://m3.material.io/components/button-groups/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**两个变体、五档间距数值、圆角数值、无障碍要求均已按官方文本核对**（本次 dump 已取得全部关键测量文字值）

## 两个变体，来路不同

| 变体 | M3 | M3 Expressive |
|---|---|---|
| **Standard button group** | ❌ | ✅ 全新组件 |
| **Connected button group** | ✅（以 segmented button 形式存在） | ✅（**官方定位：取代 segmented button**） |

配置（两变体通用）：

| 维度 | 取值 |
|---|---|
| 尺寸 | XS / S / M / L / XL |
| 形状 | round / square |
| 选择模式 | single-select / multi-select / **selection-required**（单选必须保持一项选中） |

## Standard vs Connected 怎么选

| | Standard | Connected |
|---|---|---|
| 视觉 | 按钮之间有间距，各自独立成形 | 分段连成一条，撑满所在容器宽度 |
| 按下/选中 | **改变自身与相邻按钮的宽度和形状**（相邻按钮被挤压让位） | 只改变**自己**的形状，不影响相邻 |
| 适合 | 一组相关但独立的**动作**（按钮 + 图标按钮混排） | 一组**互斥/可多选的选项**（切视图、排序、筛选） |

一句话：**Connected 是「选项」，Standard 是「动作」**。官方明确：connected group 应**取代 segmented button**（后者已不推荐，见 `02-Components/segmented-button.md`）；**没有可切换（toggle）按钮时不要用 connected group**。

## 规格

### Standard button group（官方 Measurements 文字值）

组内间距（inner padding）——随尺寸档变化以保证 **48dp 最小触控目标**：

| | XS | S | M | L | XL |
|---|---|---|---|---|---|
| 按钮间距 | **18dp** | **12dp** | **8dp** | **8dp** | **8dp** |

（token 面板同证：`Button group xsmall container height 32dp`、`between space 18dp`。）

- 组容器是**不可见的**，只加内边距、改按钮形状，本身不包含按钮外观。
- 组宽**贴合内部按钮**（hugs the width），动画时不动周围布局。

### Connected button group（官方 Measurements 文字值）

| 属性 | 值 |
|---|---|
| 组内间距（所有尺寸） | **2dp** |
| 最小宽度（XS / S） | **48dp**（触控目标同为 48dp） |

圆角（round 与 square 两版数值相同）：

| | XS | S | M | L | XL |
|---|---|---|---|---|---|
| 内圆角 / 外圆角 | **4dp** | **8dp** | **8dp** | **16dp** | **20dp** |

- Round 版：外形状全圆（fully round），内部保持上述方角。
- 组应**撑满页面/表面宽度**（各按钮随之加宽）；大窗口可设最大宽度防过宽。

### 密度

按钮组**跟随内部按钮的密度**自适应高度（官方原文 "Button groups adapt to density of the buttons inside"）。按钮组本身没有独立的密度步进值。

## 颜色

> Button groups have no color properties.

- 组本身无颜色属性；用内部按钮的 default / toggle 颜色样式（filled、tonal、outlined、elevated）。
- **避免 standard 图标按钮和 text 按钮**——无容器处理，视觉不成组。
- **不要在 connected group 里混用颜色样式**（选择与强调会变得不清晰，官方 Don't）。

## 使用规则（Guidelines 页）

1. 组内按钮默认**同尺寸、同形状**；仅 hero 时刻才用多尺寸，且不要频繁混。
2. 形状差异**保留给关键交互**（选中变化），不要拿来装饰。
3. **不换行**：组保持单行；多组可上下堆叠，但**组与组之间没有纵向交互**。
4. 自适应：固定模式（手动定宽度/尺寸/内边距）或弹性模式（按钮与组自动增减宽，直到所有弹性按钮到最大宽度）；手动调宽时**图标按钮不要拉超过 wide 档**。
5. 紧凑窗口用更小更窄的按钮保证全组放得下；大窗口用更大更宽的按钮填空间。
6. 跨断点**保持视觉层级**：主动作在任何断点都是最大/最宽/最显眼的。
7. trailing 端的按钮可在小断点**收进 overflow 菜单**、大断点恢复显示；overflow 菜单放组的 trailing 末端。
8. 组外的按钮不受组行为影响。

## 无障碍（Accessibility 页）

- 读屏应能：**逐个导航并操作组内按钮**；**识别选中状态**。
- **触控目标**：每个按钮 ≥ 48×48dp；**XS/S 档靠更大的组内间距保证**——**不要压缩这两档的间距**（官方原文）。
- **容器不可聚焦、无需标签**；初始焦点落在**第一个按钮**，然后逐个移动。
- **键盘**：Tab 进入/在按钮间移动；**方向键在组内导航**；Space 或 Enter 选中聚焦项。
- 每个按钮按 buttons / icon buttons 的无障碍指南单独打标签。

## Compose 实现

Expressive 新增组件（官方确认 Jetpack Compose: Expressive 与 MDC-Android: Expressive 可用）。**2026-11-16 修正**：此前写「`ButtonGroup` 需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`」不准确——据官方 `material3-1.5.0-rc01-sources.jar` 一手实证，`ButtonGroup` 类**无**该实验注解（已转正）；connected 形态的组外观通过 `ButtonGroupDefaults.connectedLeadingButtonShapes()` / `connectedMiddleButtonShapes()` / `connectedTrailingButtonShapes()` 设置，组本身无颜色属性。名称与签名仍随版本变动，接入前核对：

1. 组件类名与 slot 结构（单行布局约束）；
2. 选择模式（single/multi/selection-required）由参数还是容器控制；
3. 间距（18/12/8/8/8 与 2dp）与最小宽度 48dp 是否为组件默认值；
4. toggle 语义（selected 状态）是否由组件播报。

> ⚠️ 注解状态仅对 1.5.0-rc01 有效，升级按 V2 重查。设计规范数值（间距/圆角）为官方原值，未因本次实证改动。

## 常见错误

1. 把一组**动作**做成 connected 样式，用户以为只能选一个。
2. 在 M3（非 Expressive）里找 standard button group。
3. 压缩 XS/S 档的组内间距（官方为保 48dp 目标特意加大）。
4. connected group 里混用颜色样式。
5. 组内混尺寸档且不是 hero 时刻。
6. 按钮组换行（官方：单行，多组堆叠）。
7. 给组容器加聚焦或标签（容器不可聚焦、无需标签）。
8. 用按钮组替代底部导航做页面切换。

## 来源

- https://m3.material.io/components/button-groups/overview（变体、配置、平台可用性）
- https://m3.material.io/components/button-groups/specs（间距 18/12/8/8/8 与 2dp、圆角 4/8/8/16/20、最小宽 48dp）
- https://m3.material.io/components/button-groups/guidelines（Do / Don't、自适应、overflow）
- https://m3.material.io/components/button-groups/accessibility（48dp、焦点、方向键）
- 核验日期：2026-11-16
- 关联：`02-Components/segmented-button.md`（connected 的前身）、`02-Components/buttons.md`、`02-Components/icon-buttons.md`
