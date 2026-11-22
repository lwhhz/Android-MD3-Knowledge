# Adaptive Layout 实现

- 官方指南：https://developer.android.com/develop/ui/compose/layouts/adaptive
- 核验日期：2026-11-14 ｜ 版本相关：窗口尺寸类与 `ListDetailPaneScaffold` 来自 material3-adaptive 系列依赖，API 在演进中
- **2026-11-22 更新（优先读）**：本页断点与导航示例已按 **五档断点**（600/840/1200/1600，左闭右开）重写，并新增 **FlexBox** 示例。五档口径权威来源见 `01-Foundations/layout.md`；FlexBox/Grid 自 Compose **1.13** 起 stable，官方建议用 FlexBox 取代 `FlowRow`/`FlowColumn`。
- **API 位置实证（2026-11-16，material3-adaptive 1.4.0-alpha03 sources jar，一手来源）**：`WindowWidthSizeClass` 来自 **`androidx.window.core.layout`**（不是 `androidx.compose.material3.adaptive`），枚举值为大写 **`COMPACT / MEDIUM / EXPANDED`**（不是 `Compact / Medium`）；`currentWindowAdaptiveInfo()` 已 deprecated，用 `currentWindowAdaptiveInfoV2()` 支持 L/XL 宽度类。

## 判断依据是窗口宽度，不是设备型号

```kotlin
// 错误
if (isTablet) { /* 双栏 */ }

// 正确
val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
val useTwoPane = windowSizeClass.isWidthAtLeastBreakpoint(
    WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
)
```

理由：手机可以分屏、折叠屏会展开、平板能开小窗。按设备型号判断，在这三种情况下全错。

## 五个断点（2026 规范）

| 断点 | 宽度 | 典型形态 | 布局策略 |
|---|---|---|---|
| Compact | < 600dp | 竖屏手机 | 单栏 + bottom navigation bar |
| Medium | 600–839dp | 折叠屏展开、小平板 | 双栏起步 + collapsed navigation rail |
| Expanded | 840–1199dp | 平板横屏、手机横屏 | 多栏 + expanded navigation rail |
| Large | 1200–1599dp | 桌面 / laptop | 多栏、常驻 rail、正文限宽 |
| Extra-large | ≥ 1600dp | 超宽屏 / 外接显示器 | 1–3 窗格、rail 默认展开 |

区间**左闭右开**：600dp 属 medium，840dp 属 expanded，1200dp 属 large，1600dp 属 extra-large（详见 `01-Foundations/layout.md`）。

获取方式（材料自适应库不同版本能力不同）：

```kotlin
// 官方入口：支持 large / xlarge 宽度类（material3-adaptive）
val adaptiveInfo = currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true)
val windowSizeClass = adaptiveInfo.windowSizeClass
// 注意：旧入口 currentWindowAdaptiveInfoV2() 与 WindowWidthSizeClass 的枚举档位
// 在不同 artifact 版本里可能是 3 档（COMPACT/MEDIUM/EXPANDED），不一定有五档。
// 需要严格五档时，用下面的 dp 阈值函数，保证与设计规范一一对应。

enum class WidthClass { Compact, Medium, Expanded, Large, ExtraLarge }

@Composable
fun rememberWidthClass(): WidthClass {
    val widthDp = LocalConfiguration.current.screenWidthDp
    return when {
        widthDp < 600 -> WidthClass.Compact
        widthDp < 840 -> WidthClass.Medium
        widthDp < 1200 -> WidthClass.Expanded
        widthDp < 1600 -> WidthClass.Large
        else -> WidthClass.ExtraLarge
    }
}
```

> `screenWidthDp` 反映当前窗口而非整机屏幕，分屏 / 多窗口下会变化；若项目已用窗口尺寸类做整体决策，可两者并存：外层用 `WindowSizeClass`，五档细分用本函数。

## 导航形态切换

```kotlin
@Composable
fun AppNavScaffold(widthClass: WidthClass = rememberWidthClass()) {
    // NavHost 必须在 when 之外创建：切换断点只换外壳，不重建返回栈
    val navController = rememberNavController()

    when (widthClass) {
        WidthClass.Compact -> Scaffold(
            bottomBar = { AppNavigationBar() },
        ) { AppNavHost(navController) }

        WidthClass.Medium -> Row {
            AppNavigationRail(collapsed = true)
            AppNavHost(navController, Modifier.weight(1f))
        }

        WidthClass.Expanded -> Row {
            AppNavigationRail(collapsed = false)
            AppNavHost(navController, Modifier.weight(1f))
        }

        WidthClass.Large -> Row {
            AppNavigationRail(collapsed = false)
            AppNavHost(navController, Modifier.weight(1f))
        }

        WidthClass.ExtraLarge -> Row {
            AppPermanentDrawer()
            AppNavHost(navController, Modifier.weight(1f))
        }
    }
}
```

> 官方文档已把 baseline 三件套（bar / rail / drawer）标为不再推荐，新项目优先 **flexible navigation bar + collapsed/expanded navigation rail**（见 `02-Components/navigation-bar.md`、`navigation-rail.md`）。

要点：**导航形态变了，返回栈不能重置**。`NavHost` 必须同一实例，通过参数切换外层容器，而不是在五个分支里各建一个 `NavHost`；`navController` 也只创建一次。

## 列表-详情

`ListDetailPaneScaffold` 按可用宽度自动决定单栏还是双栏：

```kotlin
val navigator = rememberListDetailPaneScaffoldNavigator<Note>()

ListDetailPaneScaffold(
    navigator = navigator,
    listPane = {
        AnimatedPane { NoteList(onSelect = { navigator.navigateToDetail(it) }) }
    },
    detailPane = {
        AnimatedPane {
            val note = navigator.currentDestination?.contentKey
            if (note != null) NoteDetail(note) else EmptyDetail()
        }
    }
)
```

- 紧凑宽度下自动变成「列表 → 详情」的压栈导航。
- 展开宽度下并排显示。
- 返回手势在双栏下会先关闭详情栏，而不是退出页面。

**状态提醒**：该 API 在有多个版本迭代（参数与 `ThreePaneScaffold` 系列有变动），接入前核对当前依赖的签名。

## 内容最大宽度

大屏上把正文拉满 1200dp 是不可读的——一行文字超过约 70 个字符，眼睛无法回扫。

```kotlin
Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    Column(
        modifier = Modifier
            .widthIn(max = 640.dp)
            .fillMaxWidth()
    ) { /* 内容 */ }
}
```

原则：**可读文本区限制宽度，容器和背景可以铺满。**
图片、地图、视频这类内容不受此限。

## 换行布局：FlexBox（取代 FlowRow / FlowColumn）

FlexBox 自 **Compose 1.13** 起 stable（`androidx.compose.foundation:foundation-layout:1.13.0`；用 BOM **2026.11.01+** 时不必写版本），官方明确用它取代 `FlowRow` / `FlowColumn`。

```kotlin
// 卡片按行换行：每行 8dp 间距，卡片按剩余空间伸展
FlexBox(
    modifier = Modifier.fillMaxWidth(),
    config = {
        wrap(FlexWrap.Wrap)      // 允许换行
        gap(8.dp)                // 行/列间距
        // maxItemsInEachLine(3) // 需要固定每行数量时再用
    },
) {
    cards.forEach { card ->
        NoteCard(
            card = card,
            modifier = Modifier.flex { grow(1f) },
        )
    }
}
```

- 单轴排列用 `FlexBox` 的 `direction(FlexDirection.Row / Column)`，对齐用 `alignItems(FlexAlignItems.Center)` 等。
- 二维网格（需要行列同时控制）用 `Grid`；两者都在 1.13 转正。
- 迁移：`FlowRow { }` → `FlexBox(config = { wrap(FlexWrap.Wrap) }) { }`，`FlowColumn` → `direction(FlexDirection.Column)`。

## 折叠屏

```kotlin
val layoutInfo = WindowLayoutInfoProvider.current  // 或自行监听 WindowLayoutInfo
if (foldingFeature?.orientation == FoldingFeature.Orientation.VERTICAL &&
    foldingFeature.state == FoldingFeature.State.HALF_OPENED
) {
    // 双栏贴着折痕摆放
}
```

要点：
- 内容避开折痕（`WindowInsets.displayCutout` 或 folding feature bounds）。
- 半折叠（桌面模式）时，把内容放到上半部分，控件放到下半部分。
- 不要假设折痕一定在中间。

## 可调整窗口

桌面 / 多窗口场景下窗口尺寸会连续变化：

- 布局切换要有阈值滞回，否则在 599/600dp 反复拖动会疯狂重建。
- 避免在 `onConfigurationChanged` 里做重活。
- 用 `rememberSaveable` 保住输入内容。

## 常见错误

1. 用 `isTablet` / 屏幕尺寸判断布局。
2. 尺寸类变化时重建 `NavHost`，返回栈丢失。
3. 大屏正文铺满全宽。
4. 双栏模式下两栏各自请求数据，接口被调两次。
5. 折叠屏用固定百分比分栏，折痕正好压在内容上。
6. 只测了竖屏手机，横屏下 NavigationRail + 内容高度溢出。

## 检查项

- [ ] 布局判断基于窗口宽度类
- [ ] 导航形态切换时返回栈与页面状态保留
- [ ] 大屏正文有最大宽度
- [ ] 折叠屏折痕未压内容
- [ ] 五档断点（600/840/1200/1600）都实机 / 模拟器验证过
- [ ] 双栏场景无重复请求
