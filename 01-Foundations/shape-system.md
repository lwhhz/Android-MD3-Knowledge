# 形状系统 / Shape System

> 官方指南：https://m3.material.io/styles/shape/corner-radius-scale
> Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/MaterialShapes
> 核验日期：2026-11-15
> 核验方式：Chrome CDP 读官方正文（用 `textContent` 才能拿到折叠面板里的档位值）
> 状态：**十档 dp 值全部为官方原文**；morph 机制按 Shape morph / Overview & principles 页核对
> 核验日期：2026-11-16（初版 2026-11-15）

## 先纠正一个常见错误：不是七档，是十档

官网原文：**「The Material 3 shape system uses a size-based scale with ten styles.」**

而且它和 M2 的差异是本质性的：

| | 依据 | 档数 |
|---|---|---|
| M2 | 组件容器的**尺寸** | 3 |
| **M3** | 形状圆角的**圆润程度** | **10** |

所以 M3 里圆角不是「小/中/大容器」的附属属性，而是独立的、可跨组件复用的表达维度。

## 十档（官方原文）

| 档位 | 圆角 |
|---|---|
| None | **0dp** |
| Extra small | **4dp** |
| Small | **8dp** |
| Medium | **12dp** |
| Large | **16dp** |
| **Large increased** | **20dp** |
| Extra large | **28dp** |
| **Extra large increased** | **32dp** |
| **Extra extra large** | **48dp** |
| Full | 完全圆角（胶囊） |

我原先写的七档漏了 `Large increased`(20) / `Extra large increased`(32) / `Extra extra large`(48) 三档——这三个正是 Expressive 带来的更大圆角。

`Full` 不是固定数值，按容器短边算。

## 对称与非对称

官网：**「Both symmetric and asymmetric shapes use the same 10-step scale.」**

- **对称**：四角相同。
- **非对称**：只有部分角是圆的，用于贴边组件。官方命名的档位包括：
  `Extra small top rounding`、`Large top rounding`、`Extra large top rounding`、`Large start rounding`、`Large end rounding`
- 典型场景：Bottom sheet 顶部圆角、依附屏幕边缘的 FAB menu。

非对称**不是新档位**，只是同一套十档作用在部分角上。

## Compose 里只有五个槽位

`Shapes` 只有五个字段，缺 0 / 20 / 32 / 48 / full：

```kotlin
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)
```

超出五档的需求，只能就地写：

```kotlin
Card(shape = RoundedCornerShape(20.dp))            // large increased
FloatingActionButton(shape = RoundedCornerShape(32.dp))  // extra large increased
Box(Modifier.clip(RoundedCornerShape(48.dp)))      // extra extra large
```

另外，material3 提供了 **`MaterialShapes`**（可做 shape morph 的那套命名形状，即上文「35 个形状」的 Compose 实现），它和 `Shapes` 不是一回事：`Shapes` 是主题里的五档圆角，`MaterialShapes` 是用于 morph 的形状库。Web 端目前没有对应实现。

## 已验证的组件默认值

| 组件 | 形状 |
|---|---|
| Button（圆形样式） | Full（全档） |
| Button（方角样式，Expressive） | XS/S 12dp · M 16dp · L/XL 28dp |
| Button（**按下态**） | XS/S 8dp · M 12dp · L/XL 16dp |
| Chip | 8dp（small） |
| Card | medium（12dp，通行值） |
| Dialog | extraLarge（28dp） |
| Bottom sheet | 顶部圆角（非对称） |

注意按钮那两行：**按下时圆角变小（更方）**，这是 Expressive 的形状 morph，不是笔误。

## Shape Morph（形状变形机制）

Shape morph 页原文：

> **The Material shape library supports easy transitioning, or morphing, between shapes.** Shape morph is leveraged in the standard button group and loading indicator components.

### 哪些组件会 morph（官方点名，仅两处）

| 组件 | morph 用途（官方原话） |
|---|---|
| **Standard button group**（按钮组） | show interaction（展示交互状态） |
| **Loading indicator**（加载指示器） | show progress（展示进度） |

「The Material shape library contains many types of shapes that can all morph seamlessly into each other」——形状库里任意形状之间都可以无缝互变，但**官方只点名这两个组件**内置使用了 morph。其余出现 morph 的地方都是自定义实现（⚠️ 推断：组件默认表里按钮按下态的圆角变化属于组件自带的形状变化，官方页面未说明它是否走 MaterialShapes morph 通道）。

### 什么时机做 morph

Overview & principles 页给出了 morph 应传达的三类信息：

> Use shape morph to better communicate:
> **Interaction states**, like when a button is selected
> **Actions in progress**, like a friend typing, or a page loading
> **Changes in the environment**, like sound, temperature, or time of day

以及应响应的交互动作——官方原话：

> Think about how shapes could react to different interactions, such as **tapping, swiping, scrolling, releasing, and long pressing**.

核心原则：**「Shapes should morph to improve understanding and add moments of delight.」**——morph 的第一职责是「增进理解」，不是装饰。

### morph 的时长与曲线归属

Shape morph 页原文：

> **Shape morphing uses the expressive motion scheme by default. This can be switched to the standard motion scheme as needed.**

结论：

- morph 的动效归属 **motion physics（弹簧）系统**，不是 easing/duration legacy 系统。
- 默认用 **expressive** scheme（会过冲、有弹感），可切 **standard**（几乎不弹）。
- 官方页面**未给出 morph 专用的时长或曲线数值**（⚠️ 页面未明示；具体数值应取 `md.sys.motion.spring.*` 令牌——「形状变化属于 spatial 类」这一点也未在本页明示，但弹簧系统页把 rounded corners 列在 spatial 名下，可据此选 token）。

### morph 的取值范围

- 官方页面**未给出 morph 圆角变化的取值范围**（⚠️ 页面未明示）。
- 可参照的官方数据只有组件默认值表里的按钮按下态：按下时圆角往小走（如 XS/S 12dp→8dp、L/XL 28dp→16dp，见上表）。「交互态更方、静止态更圆」这一方向是从按钮案例归纳的（⚠️ 推断，页面未作为通则明示）。
- 走 `MaterialShapes` 通道时，morph 是**形状库内 35 个形状之间的互变**，不局限于圆角数值插值。

### API 与平台可用性

Shape morph 页原文（平台表）：

| 平台 | 状态 |
|---|---|
| Android | **Available**——「For Android, use the Shapes in Compose API」 |
| Web | **Not currently available** |

「Access to the Material shape library and the shape morph functionality are available through a **platform-specific API**.」

### 背景：Expressive 更新（2025 年 5 月）

Overview & principles 页 M3 Expressive update 条目原文：

> Added **35 new shapes and shape morphing** to Material Shape Library (Figma Design Kit) and Jetpack Compose.

同时新增了三个圆角令牌：Large increased (20dp) / Extra large increased (32dp) / Extra extra large (48dp)，并把 Full 从「组件尺寸的 50%」改为独立档位。也就是说：**shape morph 与 20/32/48dp 三档是同一批（M3 Expressive）引入的**。

## 同一屏的档位纪律

- 一屏内圆角档位不超过 3 种，否则视觉上会碎。
- 嵌套容器时内层圆角应小于外层（外 16 内 12），否则同心感会破。
- 圆角值只从十档里取，不要出现 10dp / 11dp / 13dp。

## 常见错误

- 以为只有七档，漏掉 20 / 32 / 48。
- 为了凑 full 把 `extraLarge` 改成一个大数值，连带改坏所有 Dialog 和 Sheet。
- 每个组件手写 `RoundedCornerShape(10.dp)`，主题 `Shapes` 形同虚设。
- 用 `clip()` 改形状却没同时改背景，露出方角。
- 大圆角配小内边距，内容贴边。
- 非对称形状用对称 API 硬拼，四角全圆了。
- 把 `MaterialShapes` 和 `Shapes` 搞混。
- 给纯装饰做 shape morph——官方要求 morph 响应**用户交互**并「improve understanding」。
- 在 Web 上规划 morph 交互——官方明确 Web 暂不可用。

## 检查项

- [ ] 所有圆角来自十档之一
- [ ] 主题 `Shapes` 只覆盖五档，其余就地写并注明档位名
- [ ] 一屏圆角档位 ≤ 3
- [ ] 嵌套结构内外圆角递减
- [ ] 非对称形状只作用于需要的角
- [ ] morph 只用于传达交互状态 / 进行中动作 / 环境变化
- [ ] 自定义 morph 动效走弹簧（expressive 或 standard scheme），不手写 easing

## 来源

- https://m3.material.io/styles/shape/corner-radius-scale（十档刻度、对称/非对称、光学圆角、形状库使用建议）
- https://m3.material.io/styles/shape/overview-principles（Expressive 更新、35 形状、morph 传达的三类信息）
- https://m3.material.io/styles/shape/shape-morph（morph 组件、动效归属、平台可用性）
- 核验日期：2026-11-16
