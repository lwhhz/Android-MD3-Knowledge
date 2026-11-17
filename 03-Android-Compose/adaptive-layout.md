# Adaptive Layout 实现

- 官方指南：https://developer.android.com/develop/ui/compose/layouts/adaptive
- 核验日期：2026-11-14 ｜ 版本相关：窗口尺寸类与 `ListDetailPaneScaffold` 来自 material3-adaptive 系列依赖，API 在演进中
- **2026-11-16 更新提示（优先读）**：本页「三个断点」与「导航形态切换」代码段是 2026-11-14 的 baseline 记录，**设计规范已更新**——断点为**五档**（600/840/1200/1600，左闭右开），baseline 导航三件套（bar/rail/drawer）已不再推荐，改用 flexible nav bar + collapsed/expanded rail。权威来源见 `01-Foundations/layout.md` 与 `02-Components/{navigation-bar,navigation-rail}.md`（v2.0，2026-11-16 全站抓取实证）。下方的 API 名称纠错仍有效。
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

## 三个断点

| 类别 | 宽度 | 典型形态 | 布局策略 |
|---|---|---|---|
| Compact | < 600dp | 竖屏手机 | 单栏、底部导航 |
| Medium | 600-839dp | 折叠屏展开、小平板 | 双栏起步、NavigationRail |
| Expanded | ≥ 840dp | 平板横屏、桌面 | 多栏、常驻抽屉、限制内容宽度 |

获取方式（依赖版本不同，入口可能不同）：

```kotlin
// material3-adaptive 1.4.0-alpha03（2026-11-16 实证）
// import androidx.window.core.layout.WindowWidthSizeClass  ← 注意包路径
val adaptiveInfo = currentWindowAdaptiveInfoV2()   // V1 已 deprecated
val widthClass = adaptiveInfo.windowSizeClass.windowWidthSizeClass  // COMPACT/MEDIUM/...（大写）

// 旧路径：material3-window-size-class
val windowSizeClass = calculateWindowSizeClass(this)
```

模块差异较大，接入前先确认项目用的哪个 artifact。

## 导航形态切换

```kotlin
@Composable
fun AppNavScaffold(windowWidthSizeClass: WindowWidthSizeClass) {
    when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Scaffold(bottomBar = { AppNavigationBar() }) { AppNavHost() }
        }
        WindowWidthSizeClass.Medium -> {
            Row {
                AppNavigationRail()
                AppNavHost()
            }
        }
        else -> {
            Row {
                AppPermanentDrawer()
                AppNavHost()
            }
        }
    }
}
```

要点：**导航形态变了，返回栈不能重置**。`NavHost` 必须同一实例，通过参数切换外层容器，而不是在三个分支里各建一个 `NavHost`。

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
- [ ] 三种尺寸类都实机 / 模拟器验证过
- [ ] 双栏场景无重复请求
