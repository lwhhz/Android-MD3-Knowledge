# 动效 / Motion

> 官方指南：https://m3.material.io/styles/motion ｜ 令牌：https://m3.material.io/styles/motion/easing-and-duration/tokens-specs ｜ 转场：https://m3.material.io/styles/motion/transitions/transition-patterns
> 核验日期：2026-11-14（How it works / 转场模式 / 落地规则增补于 2026-11-16）
> 核验方式：Chrome CDP 渲染官网正文提取（含令牌表）
> 状态：**弹簧令牌结构 + easing/duration 数值 + 六种转场模式均已核对**

## 最重要的一条：动效范式已经换了

官网原文：

> Material introduced the **motion physics system** with M3 Expressive. ... **The physics system is replacing the previous system based on easing and duration.**

以及更狠的一句：

> The easing and duration system is still used for transitions and **can be used by teams that haven't yet updated to GM3 Expressive, but is no longer maintained.**

所以：

| 你在做什么 | 该用什么 |
|---|---|
| 组件内动效（按下、展开、状态切换、形状变化） | **弹簧（spring）** |
| 平台级转场（页面进出、共享元素） | 目前仍是 easing + duration（官方说将来也会换） |
| 老项目还没上 Expressive | 可以继续用 easing + duration，但不会有新维护 |

新项目直接按弹簧写，不要从 easing 表抄数值——那是给存量代码用的。

## 弹簧系统怎么用

### 两个轴

| 轴 | 取值 | 含义 |
|---|---|---|
| 运动类型 | `spatial` | 屏幕上移动的东西：位置、旋转、尺寸、圆角。**会过冲并弹到位** |
| | `effects` | 颜色、透明度。**不允许过冲** |
| 速度 | `fast` / `default` / `slow` | 小元素用 fast，大范围用 slow，大多数用 default |

### 令牌命名

```
md.sys.motion.spring.{speed}.{type}
例：md.sys.motion.spring.fast.spatial
```

**scheme（expressive / standard）不在令牌名里**——它在产品层统一指定，作用于所有令牌。这样换 scheme 不用改代码里的令牌引用。

### 速度怎么选

| 速度 | spatial 例子 | effects 例子 |
|---|---|---|
| default | 部分覆盖屏幕：底部弹层、展开的导航栏 | 导航栏内内容的透明度 |
| fast | 小组件：开关、按钮 | 开关滑杆的颜色变化 |
| slow | 全屏动画 | 全屏内容刷新 |

### 跨设备

同一个令牌在手表、手机、平板上**实际数值不同**，但相对关系不变（fast 永远比 slow 快）。目的是让动感在各类设备上都「合适」。

### 弹簧的等效曲线与时长（官方 Web 转换表）

各平台支持情况（官网 Specs 页原文）：

| 平台 | 支持 | 说明 |
|---|---|---|
| Jetpack Compose | ✅ Available | 用内置组件 + spring tokens |
| Android Views（MDC-Android） | ✅ Available | **未加入组件内部**，需自己用 spring token |
| Web | ⚠️ Compatible | 有中断或无手势的动画用曲线模拟弹簧 |

Web 端官方给的等效值（cubic-bezier + 时长）：

| 弹簧 | 曲线 | 时长 |
|---|---|---|
| Expressive fast spatial | `0.42, 1.67, 0.21, 0.90` | 350ms |
| Expressive default spatial | `0.38, 1.21, 0.22, 1.00` | 500ms |
| Expressive slow spatial | `0.39, 1.29, 0.35, 0.98` | 650ms |
| Expressive fast effects | `0.31, 0.94, 0.34, 1.00` | 150ms |
| Expressive default effects | `0.34, 0.80, 0.34, 1.00` | 200ms |
| Expressive slow effects | `0.34, 0.88, 0.34, 1.00` | 300ms |
| Standard fast spatial | `0.27, 1.06, 0.18, 1.00` | 350ms |
| Standard default spatial | `0.27, 1.06, 0.18, 1.00` | 500ms |
| Standard slow spatial | `0.27, 1.06, 0.18, 1.00` | 750ms |
| Standard fast effects | `0.31, 0.94, 0.34, 1.00` | 150ms |
| Standard default effects | `0.34, 0.80, 0.34, 1.00` | 200ms |
| Standard slow effects | `0.34, 0.88, 0.34, 1.00` | 300ms |

看这张表能读出两件事：

1. **spatial 的曲线 y 值大于 1**（如 1.67、1.29）——这就是「过冲」。effects 全部 ≤1.00，从曲线上保证了不过冲。
2. **Standard 的三档 spatial 曲线完全相同**，只靠时长区分快慢；**Expressive 的三档连曲线都不同**，所以 Expressive 更有「弹性个性」。

### 具体数值（来自 AndroidX 源码）

官网 Specs 说复合令牌由 damping + stiffness 组合，但数值不在页面文本里。这些值在 AndroidX 源码中可以直接读到（`MotionScheme.kt` 引用 `StandardMotionTokens` / `ExpressiveMotionTokens`）：

| Scheme | 类型 | 速度 | dampingRatio | stiffness |
|---|---|---|---|---|
| Standard | spatial | fast | 0.9 | 1400 |
| Standard | spatial | default | 0.9 | 700 |
| Standard | spatial | slow | 0.9 | 300 |
| Standard | effects | fast | 1.0 | 3800 |
| Standard | effects | default | 1.0 | 1600 |
| Standard | effects | slow | 1.0 | 800 |
| Expressive | spatial | fast | **0.6** | **800** |
| Expressive | spatial | default | **0.8** | **380** |
| Expressive | spatial | slow | **0.8** | **200** |
| Expressive | effects | fast | 1.0 | 3800 |
| Expressive | effects | default | 1.0 | 1600 |
| Expressive | effects | slow | 1.0 | 800 |

从数值能直接读出三个事实：

1. **effects 的 dampingRatio 恒为 1.0** —— 临界阻尼，数学上不可能过冲。这就是「颜色和透明度不允许 overshoot」的实现方式。
2. **spatial 的 dampingRatio 在 0.6-0.9** —— 欠阻尼，会回弹。Expressive fast spatial 的 0.6 最弹，Standard 三档统一 0.9（几乎不弹，只是轻微过冲）。
3. **两个 scheme 的 effects 数值完全相同**，差异只在 spatial。所以「Expressive 更活泼」这个感受，全部来自位移动画，而非颜色淡入。

Compose 里直接用：

```kotlin
// 等价于 Expressive default spatial
spring(dampingRatio = 0.8f, stiffness = 380f)
```

内置组件在 `MaterialExpressiveTheme` / `MaterialTheme` 下已经自动应用对应 scheme，不需要手写这些数字；只有自定义动画时才需要。

另外：**easing / duration / path 令牌属于 legacy 系统**，官网原话「so can be ignored」。

### 系统构成与自定义层级（How it works）

**弹簧的三要素**（How it works 页原文）：

> A spring is a combination of three attributes which control all motion behavior: **stiffness, damping, and initial velocity**.

（前两个对应官方复合令牌的 damping + stiffness；**initial velocity 由手势自然给出**——这正是弹簧「handle gestures, interruptions, and retargeting animations seamlessly」的原因。）

**两个 motion scheme**：expressive（官方立场是「should be used for most situations」，过冲、有弹感）与 standard（「feels more functional with minimal bounce」，用于实用型产品）。也可自建 custom scheme。

**组件侧现状**（How it works 页原文）：

> On Jetpack Compose, **21 Material components** use the motion physics system by default. Android Views (MDC-Android) support is coming soon.

> All component motion is driven by **two tokens: expressive fast spatial and expressive fast effects**.

平台支持（How it works 页，含一个与 Specs 页不同的新增事实——**Flutter 目前不可用**）：

| 平台 | 状态 | 说明 |
|---|---|---|
| Jetpack Compose | Available | 21 个内置组件默认使用 |
| Android Views（MDC-Android） | Available（未进组件， coming soon） | 自己用 spring token |
| Web | Compatible | 兼容 Compose 弹簧，见转换表 |
| **Flutter** | **Unavailable** | ⚠️ Specs 页未列 Flutter，How it works 页明确标注 Unavailable |

**官方给的三档自定义层级**：

| 层级 | 做法 |
|---|---|
| Level 1 | 直接用默认 scheme（expressive / standard）——「should be sufficient for all motion needs」 |
| Level 2 | 自建 `MotionScheme` 对象，为 scheme 的每个属性返回不同 `AnimationSpec`，全局替换 |
| Level 3 | 按 element 换 scheme：大部分用 expressive、个别元素 override `CompositionLocal` 换成 standard（如 FAB menu 用特硬 / 特软 scheme） |

### 与 Compose 的关系

Compose 用内置组件即自动走弹簧；自定义动画时用 `spring(dampingRatio, stiffness)`，不要用 `tween()` 模拟弹簧。Web 端才需要用上面的曲线表。

## 旧系统：easing + duration（已核验数值）

以下为官网令牌表原文，供存量项目与平台转场使用。

### Easing

| 令牌 | Android | CSS |
|---|---|---|
| `emphasized` | `pathInterpolator(M 0,0 C 0.05,0, 0.133333,0.06, 0.166666,0.4 C 0.208333,0.82, 0.25,1, 1,1)` | N/A（用 Standard 兜底） |
| `emphasized.decelerate` | `PathInterpolator(0.05f, 0.7f, 0.1f, 1f)` | `cubic-bezier(0.05, 0.7, 0.1, 1.0)` |
| `emphasized.accelerate` | `PathInterpolator(0.3f, 0f, 0.8f, 0.15f)` | `cubic-bezier(0.3, 0.0, 0.8, 0.15)` |
| `standard` | `PathInterpolator(0.2f, 0f, 0f, 1f)` | `cubic-bezier(0.2, 0.0, 0, 1.0)` |
| `standard.decelerate` | `PathInterpolator(0f, 0f, 0f, 1f)` | `cubic-bezier(0, 0, 0, 1)` |
| `standard.accelerate` | `PathInterpolator(0.3f, 0f, 1f, 1f)` | `cubic-bezier(0.3, 0, 1, 1)` |

注意 **emphasized 在 CSS 上没有对应值**，Web 端要用 standard 兜底。Android 上它是一种特殊的多段曲线，不是简单的 cubic-bezier。

### Duration

| 令牌 | 值 | 用途 |
|---|---|---|
| `short1` | 50ms | |
| `short2` | 100ms | |
| `short3` | 150ms | |
| `short4` | 200ms | 选择类控件（如开关）配 Standard easing |
| `medium1` | 250ms | |
| `medium2` | 300ms | |
| `medium3` | 350ms | |
| `medium4` | 400ms | FAB 展开为 Sheet，配 Emphasized easing |
| `long1` | 450ms | |
| `long2` | 500ms | Card 展开为全屏，配 Emphasized easing |
| `long3` | 550ms | |
| `long4` | 600ms | |
| `extra-long1` | 700ms | |
| `extra-long2` | 800ms | |
| `extra-long3` | 900ms | |
| `extra-long4` | 1000ms | 环境型转场（无用户输入），如轮播自动切换 |

四档短 → 四档中 → 四档长 → 四档超长，共 16 个。

### 官方建议配对（Applying easing and duration 页原文）

「As a simple starting point, these are sensible defaults that will work for most transitions.」

| Easing | Duration | 转场类型 |
|---|---|---|
| Emphasized | 500ms | 屏上起止（Begin and end on screen） |
| Emphasized decelerate | 400ms | 进入屏幕 |
| Emphasized accelerate | 200ms | 退出屏幕 |
| Standard | 300ms | 屏上起止 |
| Standard decelerate | 250ms | 进入屏幕 |
| Standard accelerate | 200ms | 退出屏幕 |

配套的选型规则（同页）：

- **按运动与屏幕的关系选 easing**：屏上起止用 Emphasized（快速起步 + 缓缓收尾）；进入用 decelerate（峰值速度起步、缓停）；**永久退出**用 accelerate（静止起步、峰值速度结束——「gives the impression the exiting component cannot be retrieved」）；**临时退出**用 Emphasized（停在屏外——暗示还能找回）。
- **按覆盖面积选 duration**：小区域短、大区域长（官方示例：小区域 200ms、大区域 500ms）。
- **进入长于退出**：进入/常驻用更长时间帮用户注意新内容；退出更快（官方示例：进入 500ms、退出 200ms）。
- Emphasized 是大多数转场的推荐集；Standard 用于需要快的实用型小转场，也是 iOS / Web 不支持 Emphasized 时的兜底。

## 转场（Transitions）：六种模式

先钉一个前提（Transition patterns / Applying transitions 两页都挂了同一条注）：

> **Note: M3 transitions use the legacy easing and duration system. They'll eventually be updated to use the motion physics system.**

即：**转场目前是 easing/duration 的最后一块阵地**，官方明确「eventually」会迁到弹簧。存量转场代码可以不动，新转场也暂时没有弹簧方案可选。

官方对转场的定位（原文）：

> Transitions are short animations that connect individual elements or full-screen views of an app. ... **They should be the top priority for a strong motion implementation.**

### 六种模式总览

| # | 模式 | 用途 | 常用组件（官方清单） |
|---|---|---|---|
| 1 | **Container transform** | 元素无缝变形以展示更多细节（Card → 详情页） | Cards、lists、image galleries、search boxes、sheets、FABs、chips |
| 2 | **Forward and backward** | 相邻层级屏幕间导航（收件箱 → 邮件会话） | Lists、cards、buttons、links |
| 3 | **Lateral** | 同层级同级内容间浏览（内容库 tab 间滑动） | Tabs、carousels、image galleries |
| 4 | **Top level** | 顶级目的地间切换 | Navigation bar、navigation rail、navigation drawer |
| 5 | **Enter and exit** | 屏幕上引入 / 移除组件 | 屏内：FABs、dialogs、menus、snackbars、time pickers、tooltips；跨屏边界：app bars、banners、navigation bar/rail/drawer、sheets |
| 6 | **Skeleton loaders** | 临时加载态 → 加载完成的过渡 | —— |

### 1. Container transform

用**持久元素（persistent element）**连接起止状态——最常见的持久元素是 container（表示封闭区域的形状），也可以是重要元素如 hero image。官方评价：

> Of all transition patterns, **this one creates the strongest relationship between elements. It's also perceived to be the most expressive**.

两个使用范围：**整屏之间**（图片 / 列表项 / Card / Search box 展开为全屏）与**屏幕之内**（搜索框展开、FAB 转场、Sheet 展开）。

适用判据（Applying transitions 页）：hero 时刻、**浅层级**应用（展开看详情再收起）、要建立无缝连接时。反例：**深层级应用不要用**（motion becomes excessive）；高频使用会觉得过度——且它**需要自定义实现**（平台不给默认）。

### 2. Forward and backward

水平滑动表示层级的前进 / 后退。**平台默认差异**（官方原文）：

1. **Android**：滑动 + 淡入淡出（fade as screens slide）——「reduces the amount of motion, since the screens don't have to slide the full width of the device」。
2. **iOS**：视差（背景比前景滑得慢）——同样为了减少运动量。

官方建议：**直接用平台默认**——「It's easy to implement and stays current as platforms update. They have a simple motion style suitable for such a common transition.」

### 3. Lateral

与 forward/backward 类似的滑动，但**不用 fade 或 parallax**——元素成组、整体滑动（slide in unison），建立强同级关系，并暗示**可以手势滑动导航**。

官方警告：滑动时加 fade 会削弱同级关系与滑动手势的暗示，还容易被误认成 forward/backward。**不要用于层级间导航**——全屏宽滑动对高频转场太重，且暗示了不存在的平级关系。

### 4. Top level

**退出屏快速淡出、进入屏淡入**。因为顶级目的地内容不一定相关，**刻意不用分组或持久元素**去建立屏幕间关系。

官方警告：顶级导航**不要用 lateral**——它暗示可以在顶级目的地间滑动，与 carousel、可滑动列表项等组件的手势冲突。

### 5. Enter and exit

两个子类（Android 行为为官方原文，iOS 对照也列出）：

**屏内（within screen bounds）**：

> Android components **expand and collapse along the x or y axis** as they enter and exit. **Scale and z-axis motion is avoided** since they imply elevation change, which doesn't match M3's reduced elevation model.

（注意：这条把转场和层级系统挂钩了——Android 刻意避开缩放 / z 轴动作。iOS 则统一缩放进场、淡出退场。）

进场方向由屏幕位置决定：**从最近的设备边缘向外展开**——顶部菜单向下展开、底部 snackbar 向上展开。方向共同建立 app 的空间模型（官方举例）：通知从顶部进（提示通知抽屉也能从顶部拉下）、nav drawer 从左侧进、bottom sheet 和键盘从底部进（底部最易触及）。

**跨屏边界（beyond screen bounds）**：Android 组件滑入滑出的同时沿 x/y 轴 expand/collapse（强调形状）；iOS 滑动不变形。可随滚动手势进出（app bar / navigation bar 滚出滚入）。**共面（coplanar）sheet** 可与主内容同层级进出，压缩内容可用区域。

反例：**不要用 enter/exit 做层级导航**——全屏高滑动过重，且屏幕关系不明。

### 6. Skeleton loaders

UI 抽象，提示内容加载后将出现在哪。与其他转场**组合使用**，降低感知延迟、稳定布局。两个官方细节：

- 有**微弱的脉动动画**表示不确定进度，从屏幕**左上向右下**推进。
- 内容加载完成后**快速淡入并覆盖在 skeleton 之上**。

### 转场质量八条（Applying transitions 页）

好转场的官方特征清单：

1. **遵循无障碍设置**：开启 reduced motion 时——「Use subtle fades instead of intense sliding or scaling animations. Disable decorative effects like parallax or **shape morphing**.」（连 shape morph 也要关。）
2. **一致性**：同类转场全 app 一致， predictable。
3. **稳定布局**：用 skeleton loader，避免内容加载时位移或突现（pop in）。
4. **不要 jump cut 作为默认**：瞬间切换不提供任何定向线索；仅当纯效率优先（如效率类 app 开菜单）才可选。
5. **连贯空间模型**：转场帮用户理解 app 的物理布局（反例：carousel 横竖布局间切换）。
6. **统一方向**：元素成组、沿一根主轴运动，不要各自乱动；只有 hero 之类的重要元素保持 persistent。
7. **干净的淡入淡出**：**先完全淡出旧内容再淡入新内容**，避免半透明叠加的脏帧；必须 cross fade 时要快、藏在转场最快段；Dialog 类中屏进场需要 fade 时用短时长；**不要给 bottom sheet 加淡入淡出**。
8. **简单风格**：转场高频、占屏大、以完成任务为主——「Common transitions should not use overt style effects like **bouncy springs**.」（注意：这条针对转场；组件内的弹簧动效不受此限。）



## 动效必须回答一个问题

「这个动画让用户多知道了什么？」合格答案只有四类：

1. **来自哪里**：新元素从触发点展开（FAB → 全屏页）。
2. **去了哪里**：被删除或收起的元素回到归宿。
3. **状态变了**：按下、选中、展开的即时反馈。
4. **注意力转移**：把视线引到刚变化的内容上。

答不上来就是装饰，删掉。

## 三条经验规则

- **退出快于进入**（约为进入的 60%-70%），否则界面显黏。官方示例更极端：进入 500ms / 退出 200ms（40%），仅规则上「退出更快」有官方背书，具体比例是本库经验值。
- **同一屏内并列动画时长差不超过 3 倍**。
- **超过 400ms 的等待必须有中间状态**。

## Compose 实现

弹簧（推荐，新代码用这个）：

```kotlin
val scale by animateFloatAsState(
    targetValue = if (pressed) 0.96f else 1f,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,  // effects 类不允许过冲
        stiffness = Spring.StiffnessMedium
    ),
    label = "scale"
)
```

位移类允许过冲时用 `Spring.DampingRatioMediumBouncy` 一类；颜色/透明度必须用 no-bouncy。

过渡（平台转场，仍用 easing）：

```kotlin
val transition = updateTransition(targetState = expanded, label = "expand")
val height by transition.animateDp(
    transitionSpec = { tween(durationMillis = 300, easing = FastOutSlowInEasing) },
    label = "height"
) { if (it) 200.dp else 56.dp }
```

原则：
- 用一个 `Transition` 驱动相关属性，避免各自为政导致错位。
- 动画 `targetValue` 必须来自状态，不要在 `LaunchedEffect` 里手动 `snapTo` 序列。
- 长列表慎用 `animateContentSize()`，会引发布局抖动。

## 减少动态效果

系统开启「移除动画」后（官方 Applying transitions 页原文）：

- 「Use **subtle fades** instead of intense sliding or scaling animations」——位移动画降级为细微淡入淡出。
- 「**Disable decorative effects** like parallax or shape morphing」——视差与形状变形这类装饰效果直接关闭。
- 不依赖动画完成业务逻辑（别 `delay()` 后跳转）。
- 弹簧动效应降级为 `snap()`（⚠️ 推断：snap 降级是工程实践，Applying transitions 页只讲了 fades 与禁用装饰效果）。

## 常见错误

- 新代码还在抄 easing 数值——那套已不再维护。
- 用带弹性的 spring 做透明度/颜色动画（应该用 effects 类，不允许过冲）。
- 每个属性单独写 `animate*AsState`，时长不一致导致错位。
- 列表滚动时给每个 item 加进场动画。
- 只在高端机调时长，低端机掉帧。
- 用动画掩盖加载慢。

## 待核验

- [x] 弹簧的 damping / stiffness 数值 —— **已从 AndroidX 源码取得全部 12 组**
- [ ] 平台转场迁移到 physics 的时间表（官网仅说「eventually」）
- [ ] 官方 21 个默认走 physics 的组件明细清单（How it works 页只给了数量）

## 来源

- https://m3.material.io/styles/motion/overview/how-it-works（motion schemes、弹簧三要素、21 组件、三级自定义、平台支持）
- https://m3.material.io/styles/motion/overview/specs（Web 曲线转换表、easing/duration legacy 定位）
- https://m3.material.io/styles/motion/transitions/transition-patterns（六种转场模式及各模式组件清单与平台行为）
- https://m3.material.io/styles/motion/transitions/applying-transitions（好转场八条、各模式适用判据）
- https://m3.material.io/styles/motion/easing-and-duration/tokens-specs（easing / duration 令牌表）
- https://m3.material.io/styles/motion/easing-and-duration/applying-easing-and-duration（建议配对、easing 与 duration 选型规则）
- 核验日期：2026-11-16
