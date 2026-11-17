# Material 3 Components API

- 指南：https://developer.android.com/develop/ui/compose/designsystems/material3
- API Reference：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14

## 查一个组件时的固定顺序

1. **API Reference** 找类名与全部参数（`package-summary` 页可看完整清单）。
2. 看类或函数上是否有 `@ExperimentalMaterial3Api` / `@ExperimentalMaterial3ExpressiveApi`。
3. 看 `@Composable` 签名里的默认值——默认色角色、默认形状、默认尺寸都在这里。
4. 再看设计指南（m3.material.io）确认使用规则。**顺序不能反**：先看设计稿容易写出不存在的参数。

## 命名对照

设计稿上的名字和 Compose 类名并不总是一致：

| 设计稿 | Compose |
|---|---|
| Filled Button | `Button` |
| Tonal Button | `FilledTonalButton` |
| Outlined Button | `OutlinedButton` |
| Text Button | `TextButton` |
| FAB | `FloatingActionButton` |
| Extended FAB | `ExtendedFloatingActionButton` |
| Bottom Sheet | `ModalBottomSheet`（模态）/ `BottomSheetScaffold`（可停留） |
| Snackbar | `Snackbar` + `SnackbarHost` / `SnackbarHostState` |
| Navigation Bar | `NavigationBar` |
| Navigation Rail | `NavigationRail` |
| Top App Bar | `TopAppBar` / `CenterAlignedTopAppBar` / `MediumTopAppBar` / `LargeTopAppBar` |
| Text Field（filled） | `TextField` |
| Text Field（outlined） | `OutlinedTextField` |
| Chip | `AssistChip` / `FilterChip` / `InputChip` / `SuggestionChip` |
| Segmented Button | `SegmentedButton` + `SingleChoiceSegmentedButtonRow` |
| Divider | `HorizontalDivider` / `VerticalDivider`（`Divider` 已 deprecated） |
| Toolbar | **不存在**，见 `02-Components/toolbars.md` |

## 参数的通用约定

material3 组件基本遵循同一套参数模式，记住这套就能猜对八成：

```
onClick / onValueChange / onCheckedChange   行为回调
modifier                                    布局修饰符（第一个可选参数位）
enabled                                     是否可用
shape                                       形状，默认取 MaterialTheme.shapes
colors = XxxDefaults.xxxColors(...)          颜色，走 *Defaults 对象
elevation = XxxDefaults.xxxElevation(...)    层级，走 *Defaults 对象
content / label / title / text              内容槽位（@Composable lambda）
```

**永远走 `*Defaults`**，不要手写 `ButtonDefaults.buttonColors()` 里已有的默认值——官方调整默认值时你才能自动跟上。

## 槽位（Slot API）

M3 组件大量使用 slot：用 `@Composable` lambda 参数留出可替换的内容区。

```kotlin
TopAppBar(
    title = { Text("标题") },
    navigationIcon = { IconButton(onClick = {}) { /* ... */ } },
    actions = { /* RowScope 作用域 */ },
    scrollBehavior = scrollBehavior
)
```

槽位的 lambda 带 receiver scope（`RowScope` / `ColumnScope`），在作用域里可以用 `Modifier.weight()` 等只在对应布局下有效的修饰符。**在别处用 `weight()` 会编译不过**——这是作用域在起作用，不是 bug。

## 实验性 API

三种注解，含义不同：

| 注解 | 含义 | 处理 |
|---|---|---|
| `@ExperimentalMaterial3Api` | 组件在 material3 内为实验，签名可能变 | `@OptIn` + 注释原因 + 收敛在内部 |
| `@ExperimentalMaterial3ExpressiveApi` | Expressive 组件，转正进行中 | 同上，且不要写进核心流程 |
| `@ExperimentalFoundationApi` 等 | 底层 API 实验 | 同上 |

```kotlin
// 需要 ModalBottomSheet（material3 中为 experimental），版本核验于 2026-11-14
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheet(onDismiss: () -> Unit) { /* ... */ }
```

原则：**实验性注解不外溢**。组件内部用了 experimental，对外暴露的参数和类型要是稳定的。

## 状态与宿主组件

有些组件不自带状态，需要配套的 State 对象：

| 组件 | 配套状态 | 说明 |
|---|---|---|
| `Scaffold` | — | 消费 `paddingValues` |
| `Snackbar` | `SnackbarHostState` | `showSnackbar()` 是挂起函数 |
| `ModalBottomSheet` | `SheetState` | `rememberModalBottomSheetState()` |
| `NavigationDrawer` | `DrawerState` | `rememberDrawerState()` |
| `ExposedDropdownMenuBox` | `expanded` 布尔 | 需自行管理 |
| `SwipeToDismissBox` | `SwipeToDismissBoxState` | 需处理方向与撤销 |

State 对象基本都是 `remember` 出来的，**不要把它们放进 ViewModel**（它们绑定 composition 生命周期）。

## 常见错误

1. 从设计稿的旧版本文档里抄参数名，编译不过（如 `menuAnchor()` 的类型参数在 1.5.0-rc01 是 **`ExposedDropdownMenuAnchorType`**，不是旧文档写的 `MenuAnchorType`；2026-11-16 官方 sources jar + 编译实证，升级需重查）。
2. 手写颜色而不用 `*Defaults`，官方调色后不同步。
3. 在非 `RowScope` 里用 `Modifier.weight()`。
4. 把 `SheetState` 放进 ViewModel，配置变更后行为异常。
5. `Scaffold` 的 `paddingValues` 不消费，内容被系统栏盖住。
6. 用 `Divider`（已 deprecated），应为 `HorizontalDivider`。
