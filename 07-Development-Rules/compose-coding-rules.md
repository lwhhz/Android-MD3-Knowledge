# Compose Coding Rules

> 定位：代码层的硬约束。与 `android-ui-rules.md`（设计决策）互补。
> 核验日期：2026-11-14

## 依赖与版本

**V1. 用 Compose BOM 统一版本，不要在单个 artifact 上写版本号。**

```kotlin
val composeBom = platform("androidx.compose:compose-bom:2026.08.00")
implementation(composeBom)
androidTestImplementation(composeBom)

implementation("androidx.compose.material3:material3")
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.ui:ui-tooling-preview")
debugImplementation("androidx.compose.ui:ui-tooling")
```

**2026-11-16 实证补充（编译环境，非设计规范；来源：本机 Gradle 构建 + Google 官方 Maven）**：

| 项 | 实证结果 | 说明 |
|---|---|---|
| AGP 9.0+ | **内建 Kotlin 支持**，不再应用 `org.jetbrains.kotlin.android` 插件（否则编译报错，官方提示见 kotl.in/gradle/agp-built-in-kotlin） | 仍应用 `org.jetbrains.kotlin.plugin.compose` / `.serialization`（与 Kotlin 同版本） |
| Compose 1.13.0-rc01 | compileSdk **≥ 37.1**（实测 37.2；AGP DSL 为 `compileSdk = 37` + `compileSdkMinor = 2`） | 官方 Maven 的 compose-bom-alpha 2026.11.00 映射 |
| AGP 9 | JUnit 不再隐式提供，需显式 `testImplementation("junit:junit:4.13.2")` | 编译实证 |
| Expressive 预发布线 | 需 `compose-bom-alpha:2026.11.00`（→ material3 1.5.0-rc01，官方 Maven POM 实证） | **仅作预发布选项注明，不替代稳定线 BOM**；是否采用属工程决策 |

> ⚠️ 上表是构建环境实证，遵循 V2「版本号只从官方来源取」：AGP/Gradle/compileSdk 要求以官方 release notes 为准，本机版本仅作参考；BOM 升级仍是独立变更（V4）。

**V2. 版本号只从官方来源取，禁止猜。**
查询入口：`00-Metadata/sources.yaml`。当前快照记录在 `00-Metadata/version-tracking.md`。

**V3. 图标依赖要显式声明。**
较新的 BOM 起不再隐式提供图标；用到 `Icons.Default.*` 时显式加 `material-icons-core`，用到 `Icons.Outlined.*` 等扩展集时加 `material-icons-extended`（体积大，优先只引 core）。

**V4. BOM 升级是独立变更。**
BOM 升级必须单独提一次提交，附回归范围（Popup 定位、文本选择、导航返回、动画测试）。不要和其他功能改动混在一起。

## 结构与分层

**S1. 一个屏幕三层，不要混。**

```
ui/theme/        主题、颜色、字阶、形状
ui/components/   无状态可复用组件（只接收参数，不持有状态）
ui/screen/XxxScreen.kt   屏幕级 Composable（接收 state + 事件 lambda）
XxxViewModel.kt  状态与业务逻辑
```

**S2. Composable 不在内部持有业务状态。**
可复用组件对外暴露 `value` + `onValueChange`；屏幕级 Composable 接收 `UiState` + `onEvent`。

**S3. `UiState` 用不可变类型。**
`data class` + `copy()`，或 `sealed interface` 表达互斥状态。不用多个平行 `Boolean`（`isLoading` + `isError` + `isEmpty` 会出现非法组合）。

**S4. 单向数据流。**
事件向上（lambda），状态向下（参数）。不要在子组件里直接改父组件的 ViewModel。

## 状态

**T1. 状态归属判断。**

| 状态 | 放哪 |
|---|---|
| 纯 UI、需跨配置变更保留 | `rememberSaveable` |
| 纯 UI、不需保留（如滚动手势中间量） | `remember` |
| 业务数据、跨页面、需恢复 | `ViewModel` + `SavedStateHandle` |

**T2. 收集 ViewModel 状态用生命周期感知 API。**
`collectAsStateWithLifecycle()`，不要裸 `collectAsState()`。

**T3. 派生状态用 `derivedStateOf`，不要在 composition 里重算。**
尤其是依赖滚动位置的计算。

**T4. 列表动画的 key 必须稳定。**
`items(list, key = { it.id })`，用 index 做 key 会导致删除时动画错乱。

## 性能

**P1. composition 阶段不做重活。**
不在 Composable 函数体里做 IO、排序大列表、创建 `SimpleDateFormat`。这些放 `remember` 或 ViewModel。

**P2. `LazyColumn` 提供 `key` 和 `contentType`。**
混合内容不提供 `contentType` 会导致复用错乱、滑动掉帧。

**P3. lambda 参数保持稳定。**
把回调用 `remember` / 方法引用固定，避免每次重组产生新 lambda 触发下游重组。

**P4. Modifier 顺序有意义。**

```kotlin
Modifier
    .fillMaxWidth()      // 尺寸先定
    .padding(16.dp)      // 内边距
    .background(...)     // 背景在 padding 内
    .clickable { }       // 点击区域包含 padding
```

顺序错了会出现「点击区域比视觉区域大」或「背景被 padding 裁掉」。

**P5. 不要用 `Modifier.offset` 做布局。**
`offset` 不参与测量，会导致重叠和裁剪问题；布局位移用 `padding` / `Arrangement` / `Spacer`。

**P6. 列表内避免 `animateContentSize()`。**
会引发布局抖动，尤其在快速滚动时。

## API 稳定性

**R1. `@OptIn` 只能用于已确认的实验性 API，并在旁边写清原因。**

```kotlin
// 需要 FlexibleBottomAppBar 的 docked FAB 能力，material3 1.5.0 起为 experimental
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
```

**R2. 用 deprecated API 必须写替代方案和迁移计划。**
存在官方替代品时直接用替代品，不要「先跑起来再说」。

**R3. 不把 experimental 组件当作稳定能力写进可复用组件。**
如果必须用，在组件内部收敛，对外接口不暴露实验性类型。

**R4. Material3 与 M3 Expressive 的边界要写清楚。**
Expressive 组件在 1.5.0 线逐步转正，同一份代码里混用稳定 / 实验 API 时，注释标明各自状态。

## 主题

**C1. 单一主题入口。**
`MaterialTheme`（或 expressive 主题）只在一个地方配置，`AppTheme` 包裹整个应用。

**C2. 组件里不硬编码颜色、字号、形状。**
全部走 `MaterialTheme.colorScheme / typography / shapes`。

**C3. 主题必须提供浅色、深色、dynamic color 三条路径。**

## 测试

**Q1. 关键屏幕必须有 `@Preview`。**
至少覆盖：浅色、深色、大字号、空态。

**Q2. 关键交互有 UI 测试，用语义选择器。**

```kotlin
composeTestRule.onNodeWithContentDescription("删除这条笔记").performClick()
```

不要用 `onNodeWithText` 断言纯装饰文本，也不要用坐标点击。

**Q3. 涉及动画的测试避免依赖固定延时。**
用 idle 等待或新提供的同步 API，不要 `Thread.sleep`。

## 提交前自检

- [ ] 没有裸色值 / 裸字号 / 裸圆角
- [ ] 没有猜的版本号
- [ ] `@OptIn` 都有原因注释
- [ ] 列表有 `key` 和 `contentType`
- [ ] 状态收集用生命周期感知版本
- [ ] Preview 覆盖深浅色
- [ ] 无 `Thread.sleep` 式等待
