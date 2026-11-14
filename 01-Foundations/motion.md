# 动效 / Motion

> 官方指南：https://m3.material.io/styles/motion ｜ 令牌：https://m3.material.io/styles/motion/easing-and-duration/tokens-specs
> 核验日期：2026-11-14
> 核验方式：Chrome CDP 渲染官网正文提取（含令牌表）
> 状态：**弹簧令牌结构 + easing/duration 数值均已核对**

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

## 动效必须回答一个问题

「这个动画让用户多知道了什么？」合格答案只有四类：

1. **来自哪里**：新元素从触发点展开（FAB → 全屏页）。
2. **去了哪里**：被删除或收起的元素回到归宿。
3. **状态变了**：按下、选中、展开的即时反馈。
4. **注意力转移**：把视线引到刚变化的内容上。

答不上来就是装饰，删掉。

## 三条经验规则

- **退出快于进入**（约为进入的 60%-70%），否则界面显黏。
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

系统开启「移除动画」后：
- 位移动画降级为淡入淡出或直接切换。
- 不依赖动画完成业务逻辑（别 `delay()` 后跳转）。
- 弹簧动效应降级为 `snap()`。

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
