# Compose 1.13 与 Material3 1.5.0 迁移

- 官方来源：[Compose 11 月发布博客](https://developer.android.com/blog/posts/jetpack-compose-november-2026-release) ｜ [compose-ui release notes](https://developer.android.com/jetpack/androidx/releases/compose-ui) ｜ [compose-material3 release notes](https://developer.android.com/jetpack/androidx/releases/compose-material3) ｜ [文本字段迁移指南](https://developer.android.com/develop/ui/compose/text/migrate-state-based)
- 核验日期：2026-11-22
- 核验方式：官方 release notes 版本表与正文条目、官方博客全文、BOM POM 解析
- 适用范围：从 **Compose 1.12.x / Material3 1.4.x** 升到 **Compose 1.13.0 + Material3 1.5.0**

## 版本事实（三方一致）

| 对象 | 版本 | 状态 | 依据 |
|---|---|---|---|
| Compose 核心（ui / foundation / runtime / animation） | **1.13.0** | Stable（2026-11-18） | compose-ui 版本表 |
| `androidx.compose.material3` | **1.5.0** | Stable（2026-11-18） | material3 版本表 + 正文条目 |
| `compose-bom` | **2026.11.01** | Stable 线 | BOM POM |
| 下一轮预发布 | Compose 1.14.0-alpha01 / material3 1.6.0-alpha01 | Alpha | 同上 |

**升级前提**：Kotlin **2.2+**、compileSdk **37.1**（官方博客原文）。BOM 一行升级：

```kotlin
implementation(platform("androidx.compose:compose-bom:2026.11.01"))
```

## 一、文本字段：value-based → state-based（最大的迁移面）

官方迁移指南开篇即声明：

> Starting in Compose 1.13, value-based `BasicTextField`, Material 2 `TextField`, and `VisualTransformation` are deprecated in favor of `TextFieldState`-based text fields with `InputTransformation` and `OutputTransformation`.

- **Material 3 的 value-based `TextField`/`OutlinedTextField` 在 1.5.0 里尚未弃用**，但官方建议一并迁移；
- material3 **1.6.0-alpha01 已弃用** `TextField`/`OutlinedTextField` 的 value/onValueChange 重载与 `TextFieldDefaults.DecorationBox`；
- `VisualTransformation` 弃用，改用 `OutputTransformation`；输入过滤改为 `InputTransformation`。

### 最小迁移

```kotlin
// before
var text by rememberSaveable { mutableStateOf("") }
TextField(value = text, onValueChange = { text = it }, singleLine = true)

// after
TextField(
    state = rememberTextFieldState(),
    lineLimits = TextFieldLineLimits.SingleLine,
)
```

### 过滤（onValueChange 逻辑 → InputTransformation）

```kotlin
// 去掉前导 0
TextField(
    state = rememberTextFieldState(),
    inputTransformation = InputTransformation {
        while (length > 0 && charAt(0) == '0') delete(0, 1)
    },
)
```

### 格式化（VisualTransformation → OutputTransformation）

```kotlin
val state = rememberTextFieldState()
TextField(
    state = state,
    inputTransformation = InputTransformation.maxLength(16),
    outputTransformation = OutputTransformation {
        if (length > 4) insert(4, "-")
        if (length > 9) insert(9, "-")
        if (length > 14) insert(14, "-")
    },
)
```

官方强调：`OutputTransformation` 自动处理偏移映射，不需要再手写 `OffsetMapping`。

## 二、布局：FlexBox / Grid 稳定，Flow 与 ConstraintLayout 退场

| 变化 | 结论 | 官方口径 |
|---|---|---|
| FlexBox / Grid | **stable**（1.11 引入，1.13 转正） | 「officially graduating from experimental to stable」 |
| FlexBox vs FlowRow/FlowColumn | **新代码用 FlexBox** | 「FlexBox supersedes the Flow layouts (FlowRow and FlowColumn)」 |
| FlexBox 新增 | `maxItemsInEachLine` | 换行时限制每行数量 |
| ConstraintLayout / MotionLayout（Compose） | **官方弃用** | 「officially deprecating ConstraintLayout and MotionLayout in Compose」 |
| LazyLayoutCacheWindow | **stable** | 窗口化预取取代按 item 预取；配置从 `rememberLazyListState` 移到 `LazyColumn` / `LazyVerticalGrid` 等布局参数 |

迁移建议：Flow 布局换 FlexBox；复杂约束布局改为 canonical layouts（feed / list-detail / supporting pane）+ Grid；动画从 MotionLayout 迁到新的 Shared Element / 动画 API。

官方指南：[FlexBox get started](https://developer.android.com/develop/adaptive-apps/guides/flexbox/get-started) ｜ [Grid get started](https://developer.android.com/develop/adaptive-apps/guides/grid/get-started)。

## 三、图形：渐进模糊与多边形形状

- **渐进模糊**：`Modifier.blur { blurRadiusSpec = BlurRadiusSpec... }`，支持线性和径向渐变、甚至 AGSL RuntimeShader；`BlurredEdgeTreatment.Unbounded` 可把图片边缘融进背景。
- **`DialogProperties` / `PopupProperties` 新增**：`blurBehindRadius`、`backgroundBlurRadius`、`scrimAlpha`、`windowShape`；`WindowInfo.isCrossWindowBlurEnabled` 查询系统跨窗口模糊。
- **`PolygonShape` / `MorphPolygonShape`**（`androidx.compose.foundation`）：普通 `Shape`，可直接用于 `Modifier.clip` 或组件 `shape` 参数；`MorphPolygonShape` 在 draw 阶段读 progress 做形变，**不触发重组**。有了它不必再直接依赖 `graphics-shapes`。
- `Modifier.border` 重写：更快，支持 RuntimeShader 画刷，统一按 outline 尺寸绘制。

## 四、文本排版

- `LineHeightStyle.Mode.PerLine`：多语言/混排时**逐行**计算行高，避免缅甸文、高棉文、泰文等 tall script 与相邻行碰撞。
- `FontVariation.Settings` 新增 `merge` 重载：基础可变字体配置 + 单轴覆盖（weight / slant / width），不用重建整个 list。

## 五、动画与状态

- **`CapturedAnimatedVisibility`**：退出时不再要求调用方保留状态——它捕获最后一帧做动画，状态可以立即清空，避免详情面板关闭时的闪烁 / 崩溃。
- `DeferredTransition` 稳定。
- **`computedStateOf`**：轻量替代 `derivedStateOf`，不缓存结果、开销更低；官方建议默认用它，只有重计算/必须缓存时才用 `derivedStateOf`。
- 重组合追踪（Perfetto）：记录 state 读写与堆栈，定位触发的具体代码行；有开销，排障时再开。

## 六、Material3 1.5.0 的行为变更（升级必读）

| 组件/API | 变化 |
|---|---|
| `Slider` / `RangeSlider` | 迁到 **`SliderState` / `RangeSliderState`**；`onValueChange` 变成必填且位置变化；`activeRangeStart/End` 改名 `startValue/endValue`；无状态重载弃用 |
| `SecureTextField` / `OutlinedSecureTextField` | 默认混淆模式改为 `TextObfuscationMode.System`（原 RevealLastTyped） |
| `ModalBottomSheet` / `BottomSheetScaffold` | 不再按高度启发式自动移除 PartiallyExpanded 锚点；由调用方显式控制；sheet 遵循 `MaterialTheme.motionScheme` |
| `NavigationItem`（水平/WideNavigationRail） | 标签颜色改走 `selectedTextColorStartIconPosition`，高对比主题下与图标一致 |
| `Checkbox` | 尺寸与勾选标记按规范重校（可用 `ComposeMaterial3Flags.isCheckboxStylingFixEnabled` 控制） |
| `TopAppBarDefaults` | `pinnedScrollBehavior` / `enterAlwaysScrollBehavior` 改收 `ScrollableState`；`isAtTop` 改名 `isAtStart` |
| 主题 | `MaterialTheme` 收敛为单一 CompositionLocal；`LocalMotionScheme` 弃用，改用 `MaterialTheme.motionScheme` |
| 新工件 | **`material3-ripple`**：独立 ripple 库 + **inset focus rings**（焦点态用内嵌焦点环而非透明度层） |

**新增/转正组件**：ToggleButton 家族、SplitButton、ButtonGroup、expressive TextField（`roundedShape` / `tonalColors()` / label 位置）、expressive Chips、Selectable/Checkable DropdownMenuItem、VibrantTimePickerDialog、滚动 TimePicker + ScrollField、WavyProgressIndicator、FloatingToolbar、expressive ListItem、AppBarWithSearch（实验）等。

**仍在 experimental**：LoadingIndicator / ContainedLoadingIndicator（及其默认值）、MaterialShapes、独立 Sheet/Scrim、部分 Search 变体——用到时保留 `@OptIn`。

## 七、生态级变化

- **Compose Material 2（`androidx.compose.material`）已弃用且不再维护**；每个核心 M2 组件都已有稳定的 M3 等价物。
- **Material Views（MDC-Android）处于维护模式**，官方建议迁到 Compose。
- M2 → M3 迁移指南已由官方提供（见 [M3 博客](https://m3.material.io/blog/compose-material-3-is-stable) 的 Resources 表）。

## 升级检查清单

1. Kotlin ≥ 2.2、compileSdk ≥ 37.1；AGP 按项目现状（库内实测 9.4.1 可用）。
2. BOM 升 2026.11.01，确认 material3=1.5.0、Compose=1.13.0。
3. 全仓搜索 `BasicTextField(`、`TextField(value`、`VisualTransformation`、`FlowRow`/`FlowColumn`、`constraintlayout-compose`、`Slider(value`。
4. 主题里 `LocalMotionScheme` 改 `MaterialTheme.motionScheme`；检查 `MaterialTheme` 自定义用法是否依赖旧的多 CompositionLocal。
5. 底部面板锚点逻辑（`PartiallyExpanded`）显式化。
6. 升级后跑启动/滚动基准，对照官方 Hero benchmarks（Compose 1.13 与 1.12.1 持平，冷启动 TTID 147.65ms vs Views 152.87ms）。

## 常见错误

1. 只升 BOM 不改 Slider 参数——`onValueChange` 位置变化 + 必填会造成编译错误。
2. 把 `VisualTransformation` 直接删掉却没补 `OutputTransformation`，格式丢失。
3. 继续给新代码用 `FlowRow`——官方已明确由 FlexBox 取代。
4. 以为 value-based M3 `TextField` 在 1.5.0 已弃用——届时只是「建议迁移」，1.6.0-alpha01 才弃用。
5. 以为 1.5.0 全量转正——LoadingIndicator / MaterialShapes 仍是 experimental，`@OptIn` 不能删。

## 来源

- [What's new in the Jetpack Compose November '26 release](https://developer.android.com/blog/posts/jetpack-compose-november-2026-release)（2026-11-18）
- [Compose UI release notes](https://developer.android.com/jetpack/androidx/releases/compose-ui) ｜ [Compose Material3 release notes](https://developer.android.com/jetpack/androidx/releases/compose-material3)
- [Migrate to state-based text fields](https://developer.android.com/develop/ui/compose/text/migrate-state-based)
- [FlexBox](https://developer.android.com/develop/adaptive-apps/guides/flexbox/get-started) ｜ [Grid](https://developer.android.com/develop/adaptive-apps/guides/grid/get-started)
- [Compose Material 3 1.5.0 is stable（Material 博客）](https://m3.material.io/blog/compose-material-3-is-stable)
