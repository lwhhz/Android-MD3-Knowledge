# Compose Animations 实现

- 官方指南：https://developer.android.com/develop/ui/compose/animation
- 核验日期：2026-11-14

## 按需求选 API

| 需求 | API | 复杂度 |
|---|---|---|
| 单个值随状态变化 | `animate*AsState`（`animateFloatAsState` / `animateDpAsState` / `animateColorAsState`） | 低 |
| 元素出现 / 消失 | `AnimatedVisibility` | 低 |
| 两个内容互斥切换 | `Crossfade` / `AnimatedContent` | 中 |
| 多个属性联动 | `updateTransition` + `animate*` | 中 |
| 手动控制播放 | `Animatable` / `AnimationState` | 高 |
| 跨页面共享元素 | `SharedTransitionLayout` + `Modifier.sharedElement()` | 高，实验性 |

**先用表里最上面那个能解决的。** 大量动画 bug 来自「用 `Animatable` 手搓了一个 `animateFloatAsState` 能做的事」。

## 基本用法

```kotlin
val expanded by animateFloatAsState(
    targetValue = if (isExpanded) 1f else 0f,
    animationSpec = tween(durationMillis = 200),
    label = "expand"
)
```

`label` 参数不是可选的装饰：它会出现在 Studio 的动画检查器里，不写就查不到是哪个动画在跑。

可见性：

```kotlin
AnimatedVisibility(
    visible = visible,
    enter = fadeIn() + expandVertically(),
    exit = fadeOut() + shrinkVertically()
) {
    Content()
}
```

内容切换（注意 `label` 与 `contentKey`）：

```kotlin
AnimatedContent(
    targetState = uiState,
    transitionSpec = {
        (fadeIn() togetherWith fadeOut())
            .using(SizeTransform(clip = false))
    },
    label = "state"
) { state ->
    when (state) {
        is Loading -> LoadingView()
        is Error -> ErrorView(state.message)
        is Success -> ContentView(state.data)
    }
}
```

## 多属性联动用 Transition

```kotlin
val transition = updateTransition(targetState = selected, label = "select")
val bg by transition.animateColor(label = "bg") { isSelected ->
    if (isSelected) MaterialTheme.colorScheme.secondaryContainer
    else MaterialTheme.colorScheme.surface
}
val scale by transition.animateFloat(label = "scale") { isSelected ->
    if (isSelected) 1f else 0.98f
}
```

**同一个视觉变化涉及的属性必须放进同一个 transition**，否则各属性时长不一致会出现撕裂感。

## 性能

1. **只动 `transform` 和 `alpha`。** 改 `width` / `height` / `padding` 会触发重新测量与布局，代价高。
2. 需要位移时优先 `Modifier.offset` + `graphicsLayer`（不参与测量），而不是改 padding。
3. 给动画加上 `Modifier.graphicsLayer { }` 可以让 Compose 走独立的渲染层。
4. **列表滚动时不要给每个 item 加进场动画**，快速滚动会掉帧。
5. 长列表里慎用 `animateContentSize()`。
6. 动画期间避免同时触发网络请求或大量状态变更。

## 共享元素（实验性）

```kotlin
SharedTransitionLayout {
    AnimatedContent(targetState = screen) { s ->
        when (s) {
            is List -> ListScreen(
                animatedVisibilityScope = this,
                sharedTransitionScope = this@SharedTransitionLayout
            )
            is Detail -> DetailScreen(...)
        }
    }
}
```

- 依赖 `ExperimentalSharedTransitionApi`，签名可能变。
- 两侧元素的尺寸、形状、内容要尽量一致，否则转场会跳变。
- 转场中两侧都存在同一份数据，注意不要触发两次加载。

## 减少动态效果

- 系统「移除动画」开启后，位移动画应降级为淡入淡出或直接切换。
- **不要用动画时长承担业务时序**（`delay(300)` 后跳转），动画被关掉逻辑就断。
- 提供无动画分支：

```kotlin
val durationScale = LocalMotionDurationScale.current.scaleFactor
val spec = if (durationScale == 0f) snap() else tween(200)
```

## 测试

- 不要用 `Thread.sleep` 等动画结束，用 idle 等待或提供的同步 API。
- 动画测试容易 flaky：优先断言最终状态，而不是中间帧。
- 涉及 `AnimatedVisibility` 的测试要先把 `visible` 设为 true 再断言内容存在。

## 常见错误

1. 每个属性单独写 `animate*AsState`，时长不一致导致错位。
2. 动画目标值来自 `remember { mutableStateOf }` 但从不更新，动画永远不触发。
3. 用 `AnimatedVisibility` 包裹整屏内容，首次进入就是一段慢动画。
4. 转场两侧 Composable 都触发数据加载，接口被调两次。
5. 忘记 `label`，性能分析时找不到热点动画。
6. 在 `LazyColumn` 的 item 内用 `animateContentSize()` + 图片，滚动时抖动。
