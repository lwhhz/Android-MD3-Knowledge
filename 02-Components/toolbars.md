# Toolbars / 工具栏

- 官方指南：https://m3.material.io/components/top-app-bar ｜ https://m3.material.io/components/bottom-app-bar
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14

## 先纠正一个常见误解

**Material 3 里没有叫 "Toolbar" 的组件。**

`Toolbar` 是旧的 AndroidX/View 体系组件（`androidx.appcompat.widget.Toolbar`），已不属于 Material 3 规范。在 Compose + M3 中，对应的东西是：

| 需求 | 组件 |
|---|---|
| 顶部标题栏 + 操作 | `TopAppBar`（四个变体） |
| 底部操作栏 | `BottomAppBar` |
| 悬浮操作工具栏（Expressive） | `FloatingToolbar` 系列，仍属 expressive API |

如果你的设计稿写「Toolbar」，先翻译成上面三者之一，再看规范。

## TopAppBar 四个变体

| Compose | 高度 | 标题样式 | 用途 |
|---|---|---|---|
| `TopAppBar`（small） | 64dp | Title Large | 默认，绝大多数页面 |
| `CenterAlignedTopAppBar` | 64dp | Title Large，居中 | iOS 风格 / 强调品牌 |
| `MediumTopAppBar` | 112dp | Headline Small | 滚动时收起为 small |
| `LargeTopAppBar` | 152dp | Headline Medium | 内容型首页 |

结构：`navigationIcon`（左）+ `title` + `actions`（右）+ 可选 `bottom`（Tab 或搜索栏）。

## 使用规则

**标题**
- 写**当前页面名**，不是应用名。用户已经在应用里了。
- 首页可以不写标题，改用品牌标识或大标题。
- 标题超过一行会截断，长标题请用 `Medium` / `Large` 允许换行。

**返回箭头**
- 只在**有上一级**时出现。顶级页面不要放返回箭头。
- 系统返回键与箭头行为必须一致。
- `contentDescription` 由 `TopAppBar` 处理，但图标要传对（`Icons.AutoMirrored.Filled.ArrowBack`，不要用未镜像的版本，RTL 语言会指反）。

**操作项**
- 最多 3 个直接可见，其余收进溢出菜单（三点）。
- 破坏性操作不要放顶部栏。
- 操作项用 `IconButton`，每个都要 `contentDescription`。

**滚动行为**

```kotlin
val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
        MediumTopAppBar(
            title = { Text("订单详情") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                }
            },
            actions = {
                IconButton(onClick = onShare) {
                    Icon(Icons.Default.Share, contentDescription = "分享")
                }
            },
            scrollBehavior = scrollBehavior
        )
    }
) { padding ->
    LazyColumn(contentPadding = padding) { /* ... */ }
}
```

注意 `Scaffold` 的 `padding` 必须消费掉，否则内容会被顶栏压住。

## BottomAppBar

- 用于底部放 2-5 个平级操作，可与 FAB 组合（FAB 嵌入底栏缺口）。
- **不要用它替代底部导航**——导航是切换目的地，底栏是执行操作。两者同时存在在 Material 里是可以的，但语义不要混。
- 高度 80dp。

## FloatingToolbar（Expressive）

Expressive 引入的悬浮工具栏，用于「选中内容后浮出的一组操作」或「需要随手可及的多操作」。特点：

- 可横向展开更多操作，不用弹出菜单。
- 形状与位置随交互变化。

**状态提醒**：这属于 expressive API，是否已从 `@ExperimentalMaterial3ExpressiveApi` 转正需按当前版本核验。在转正前，只建议在实验性功能里试用，不要写进核心流程。

## 无障碍

- 顶部栏是页面的第一个语义区域，读屏应从标题开始。
- 返回箭头语义是「返回」，不是「箭头」。
- 溢出菜单按钮语义是「更多操作」。
- 大标题变体（Medium/Large）滚动收起时，标题不能从语义树里消失——收起后仍是当前页面标题。
- 顶栏图标按钮触控目标 ≥ 48dp，不要为了塞下更多操作缩小图标。

## 常见错误

1. 在 Compose 项目里去找 `Toolbar` 组件。
2. 顶级页面放返回箭头，点了直接退出应用。
3. 标题写应用名，用户不知道自己在哪一页。
4. 顶部放了 6 个图标按钮，挤成一排看不清。
5. 用 `Medium` / `Large` 但没配 `scrollBehavior`，标题占掉三分之一屏幕还收不起来。
6. 用了 `Scaffold` 却忽略 `paddingValues`，内容被顶栏盖住。
7. RTL 语言下返回箭头指向错误（用了非 AutoMirrored 图标）。

## 来源

- https://m3.material.io/components/top-app-bar
- https://m3.material.io/components/bottom-app-bar
- `02-Components/navigation.md`（顶栏与导航结构的关系）
