# Scroll fields / 滚动字段

- 官方指南：[Overview](https://m3.material.io/components/scroll-fields/overview) ｜ [Specs](https://m3.material.io/components/scroll-fields/specs) ｜ [Guidelines](https://m3.material.io/components/scroll-fields/guidelines) ｜ [Accessibility](https://m3.material.io/components/scroll-fields/accessibility)
- Compose API：`ScrollField` / `ScrollFieldState` / `ScrollFieldColors` / `ScrollFieldDefaults`（`androidx.compose.material3`）
- 核验日期：2026-11-22 ｜ 核验方式：headless Chrome 渲染官方四页取正文；API 与版本来自 Compose Material3 1.5.0 release notes 与 API reference
- 状态：**M3 Expressive 新增组件**（官网原文：`The scroll field was introduced in the expressive update.`）；Compose 可用，Views / iOS / Wiz **不可用**

## 用途

滚动字段让「在离散数字列表中选值」变成一次滑动：**拨动即选、选中值居中放大**，再配一个切换按钮换成键盘输入。

官方口径：`Scroll fields provide a flexible, focused way for people to input or adjust specific measurements and numeric values.` 适合距离、重量、温度、水量、卡路里等**单位量值**；也用于**滚动式时间选择**。

**不该用它的场景**：

- 时间选择优先用 [时间选择器](https://m3.material.io/components/time-pickers)——滚动式 time picker 内部就是用 scroll field 拼的；
- 需要精确输入长数字时用 [Text fields](https://m3.material.io/components/text-fields)，不要用五位数以上的连续滚动。
- 它替代的是「数字滚轮」，不是普通下拉菜单或滑块。

## 尺寸与位数

官方给出三档，按**位数**选，不是按视觉大小随便挑：

| 尺寸 | 建议位数 | 最小宽度 |
|---|---|---|
| Small | 1–2 位 | **100dp** |
| Medium | 3 位 | **144dp** |
| Large | 4 位 | **186dp** |

- 官方原文：`Material recommends only using up to four digits in scroll fields.` 超过 4 位仍可加宽，但**不要**放「连续的五位以上数字」——滚动时间会失控；改为加间隔（如按 5 递增）或换文本输入。
- 默认**贴合内容**（hug contents），到每档的最小宽高为止；窗口更大时可以撑满容器或给固定尺寸。
- 高度没有在正文给统一值（在 token/测量图里）；**未取到文本数值的部分不臆造**。

## 结构

**容器** · **未选中值（inactive value，上下各一）** · **选中值（active value，居中）** · **选中态高亮容器** · **输入光标**（切到输入模式时）。

交互模型：上下相邻值始终露出一部分，暗示可滚动；中间值是当前值。

## 状态与颜色角色

官网 Specs 列出四态：Enabled / Hovered / Focused / Disabled，并区分 inactive / active。

官方列出的颜色角色（原文顺序）：`Surface container lowest` · `Outline` · `On surface` · `Primary`（页面重复列了三次 Primary）。

⚠️ 推断：页面未给「角色 → 部件」的一一映射；按 M3 通用语义，容器用 surface container lowest、描边用 outline、未选中值用 on surface、选中/高亮用 primary——写代码前以 Specs token 模块为准。

## 交互行为（Guidelines 原文）

1. **滚动**：在字段上滑动，或点上方/下方的未选中值跳转。
2. **吸附**：滚动项始终**垂直居中吸附**（`Scrolled items snap to always be vertically centered`）；选中值字号更大、颜色更深。
3. **输入模式**：切到文本输入后可用数字键盘或标准键盘；字符串输入应按首字符**自动补全**；错误提示显示在字段**下方**并高亮错误值。
4. 列表可以是**有限**的，也可以是**无限循环**的。
5. 多个字段可并排组合（如「时 + 分」「数值 + 单位」）；**追踪不同数值时必须给每个字段加标签**。
6. 可与 [Button groups](https://m3.material.io/components/button-groups/overview) 等控件一起用于更细的筛选。

## 无障碍（Accessibility 页原文）

- **必须同时提供滑动与文本输入两条路径**：字段旁放一个切换按钮（icon button / toggle button），或另配一个文本框。滚动式时间选择器内置了这个切换按钮。
- **触控目标**：未选中值的最小高度 **48dp**，默认会向容器外扩展。
- 键盘导航：

| 按键 | 行为 |
|---|---|
| Tab | 焦点落到滚动字段 |
| Space / Enter | 激活字段 |
| Up / Down | 选择列表中的上一个 / 下一个值 |

- 官方要求整组控件对屏幕阅读器可读、可用键盘完成完整选择（release notes 也写明 `full keyboard and screen reader accessibility`）。

## Compose 实现

依赖：Compose Material3 **1.5.0**（稳定版）起提供；走 Compose BOM 时用 **2026.11.01** 或更高。

```kotlin
// 结构示意：真实签名以 1.5.0 API reference 为准（本页未逐参数核对）
val state = remember { ScrollFieldState(initialValue = 0, valueRange = 0..59) }

ScrollField(
    state = state,
    // 官方要求：必须另有文本输入入口，不能只留滑动
)
```

- 时间场景优先用 **Scrollable TimePicker**（`VibrantTimePickerDialog` 支持 dial / uncontained scroll / input 三种模式）；1.5.0 release notes 明确 `Scrollable TimePicker variant and ScrollField` 属于同一批新组件。
- 与表单/校验结合时，把错误状态放在字段**下方**，与 Guidelines 一致。
- **未核验**：`ScrollField` 是否为 experimental、各参数默认值。1.5.0 release notes 把它列在「New Material Design 3 Components」而非「Remaining experimental APIs」，据此推断为稳定；用前请在 API reference 确认注解。

## 常见错误

1. **只做滑动，不给键盘输入**——官方无障碍红线。
2. 用 scroll field 代替时间选择器（应当用 time picker 的滚动变体）。
3. 连续五位以上数字硬塞进字段；应加间隔或换文本输入。
4. 多个字段并排却不加标签，用户无法判断哪个是「温度」哪个是「湿度」。
5. 把选中值做得和未选中值一样大——居中放大是它的核心反馈。
6. 在 Views / iOS / Wiz 上找实现——官方 Availability 表标为不可用，只有 Compose 有。

## 来源

- [Scroll fields · Overview](https://m3.material.io/components/scroll-fields/overview)（定义、三档尺寸、平台可用性）
- [Scroll fields · Guidelines](https://m3.material.io/components/scroll-fields/guidelines)（交互、尺寸、放置、输入行为）
- [Scroll fields · Accessibility](https://m3.material.io/components/scroll-fields/accessibility)（48dp 目标、键盘映射、输入切换）
- [Scroll fields · Specs](https://m3.material.io/components/scroll-fields/specs)（结构、颜色角色、状态）
- [Compose Material3 1.5.0 release notes](https://developer.android.com/jetpack/androidx/releases/compose-material3)（`ScrollField`、滚动时间选择器）
- [androidx.compose.material3 API reference](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary)（`ScrollFieldState` / `ScrollFieldColors` / `ScrollFieldDefaults`）
