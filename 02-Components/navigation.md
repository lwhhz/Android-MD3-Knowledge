# Navigation / 导航

- 官方指南：https://m3.material.io/components/navigation-bar
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14 ｜ 状态：组件类名为当前 API；高度等尺寸请复核官网对应页面

## 先决定结构，再选组件

导航组件不是「选一个好看的」，而是由**目的地数量**和**窗口宽度**共同决定：

| 目的地数 | 紧凑窗口（< 600dp） | 中等（600-839dp） | 展开（≥ 840dp） |
|---|---|---|---|
| 2-5 | `NavigationBar`（底部） | `NavigationRail` | `NavigationRail` 或常驻抽屉 |
| 6-7 | `NavigationBar` 已达上限，考虑分层 | `NavigationRail` | `PermanentNavigationDrawer` |
| 更多 | 底部导航不适用 | 抽屉 | `PermanentNavigationDrawer` |

**硬上限：底部导航 5 个、Rail 7 个。** 超了就说明信息架构需要分层，不是换个组件能解决的。

## 三种主组件

### `NavigationBar`（底部）

```
┌────┬────┬────┬────┐
│ ●  │    │    │    │
│首页│搜索│收藏│我的│   3-5 项，图标 + 文字常显
└────┴────┴────┴────┘
```

- 高度 80dp（含手势区，需配合 navigation bar inset）。
- 每项：图标 + 文字标签，**标签不能只在选中时显示**（读屏与可发现性都依赖它）。
- 选中项：图标变实心 + `secondaryContainer` 药丸底 + 文字变 `onSurface`。

### `NavigationRail`（侧边）

- 用于中等及以上窗口。可折叠为只显示图标，但**默认应带标签**。
- 顶部常放 FAB 或头像。
- 选中指示器同样是药丸形。

### `NavigationDrawer`

| 变体 | Compose | 场景 |
|---|---|---|
| 模态 | `ModalNavigationDrawer` | 紧凑窗口，需点击图标唤出 |
| 常驻 | `PermanentNavigationDrawer` | 展开窗口，始终可见 |
| 可关闭 | `DismissibleNavigationDrawer` | 少见，需明确理由 |

## 顶部栏

| Compose | 高度 | 用途 |
|---|---|---|
| `TopAppBar`（small） | 64dp | 默认，单行标题 + 操作 |
| `CenterAlignedTopAppBar` | 64dp | 标题居中，返回箭头在左 |
| `MediumTopAppBar` | 112dp | 需要更大标题，滚动时收起 |
| `LargeTopAppBar` | 152dp | 首页级大标题 |

**规则**：滚动时标题收起用 `Medium` / `Large` 的 `scrollBehavior`；不要自己写折叠逻辑。

## 导航实现要点

**保持各 Tab 的独立返回栈**：

```kotlin
navController.navigate(route) {
    popUpTo(navController.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}
```

少了这三行，切 Tab 会重建页面、丢失滚动位置和输入内容——这是最常见的导航 Bug。

**Tab 切换不叠栈**：`launchSingleTop = true` 防止重复点击同一 Tab 时压入多个相同目的地。

## 无障碍

- 每一项都要有文字标签。只有图标的底部导航对读屏和认知障碍用户都不可用。
- 选中态用 `selected` 语义，不要只靠颜色——`NavigationBarItem` 已自动处理。
- TalkBack 焦点顺序按视觉顺序，从左到右。
- 导航栏与内容之间要有语义分隔，避免读屏一路滑进内容区才发现换了区域。

## 常见错误

1. 底部导航放 6 个以上目的地。
2. 底部导航里再嵌底部导航（二级页面保留底部栏但语义混乱）。
3. 切 Tab 丢状态（漏了 `saveState` / `restoreState`）。
4. 用 `NavigationBar` 做「操作按钮」（中间放一个大 + 号发布按钮）——那是 FAB 的职责，混进去会破坏选中语义。
5. 大屏仍用底部导航，浪费横向空间且不符合平台习惯。
6. 顶部栏标题写应用名而不是当前页面名。
7. 自己实现 Tab 切换动画，与系统转场打架。

## 与 Navigation 3 的关系

Material Adaptive 库已与 Navigation 3 集成，用 scene 概念处理列表-详情（双栏）场景，开发者给返回栈条目打 `ListPane` / `DetailPane` 标记即可。这套能力在演进中，新项目评估前先确认当前稳定版本是否包含。

## 来源

- https://m3.material.io/components/navigation-bar
- https://m3.material.io/components/navigation-rail
- https://m3.material.io/components/navigation-drawer
- `03-Android-Compose/adaptive-layout.md`（窗口尺寸类）
