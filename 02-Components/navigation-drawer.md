# Navigation Drawer / 导航抽屉

- 官方指南：https://m3.material.io/components/navigation-drawer ｜ 规格页：https://m3.material.io/components/navigation-drawer/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**360dp 宽、56dp 指示器高、28dp 指示器圆角为官方明示值**；2025-05 起整体不再推荐

## 先看这条：已被 expanded navigation rail 取代

> "The navigation drawer is no longer recommended in the Material 3 Expressive update. For those who have updated, use an expanded navigation rail, which has mostly the same functionality of the navigation drawer and adapts better across breakpoints."

判断标准：

| 情况 | 建议 |
|---|---|
| 新项目 / 已跟进 Expressive | 用 expanded navigation rail |
| 既有产品、Compose 依赖还没有 wide rail | 可继续 baseline drawer，但视为过渡 |
| 需要永久可见的多级导航（Web 桌面） | expanded rail（standard）优先 |

## 变体

| 变体 | 表现 | 断点 |
|---|---|---|
| Standard | 与内容并排；可常驻，也可用菜单图标开合 | Expanded / Large / XL |
| Modal | 覆盖在 scrim 之上，不影响布局栅格 | 任意断点，主要用于 compact / medium |

Standard 的两种可见性：

- **常驻（permanently visible）**：适合频繁在不相关目的地间跳转，用户不能关闭。
- **可关闭（dismissible）**：适合以内容为主（如相册）或很少切目的地的产品，必须提供可见的菜单图标开合。

Modal 的三个关闭方式：选中某一项、点击 scrim、朝锚定边缘滑动。

## 适用条件

官方明确推荐给：

- **5 个及以上**顶层目的地
- **2 层及以上**导航层级
- 不相关目的地之间的快速跳转
- 在大屏上替代 rail 或 bar

> "Use a navigation drawer for 5 or more primary destinations, or more than 1 level of navigation hierarchy"

硬约束：

> "Avoid using a navigation drawer with other primary navigation components, such as a navigation bar."

唯一例外：medium/expanded 下 rail + modal drawer，且抽屉要在层级上做出足够区分（可以重复 rail 里的目的地）。

## 尺寸（官方 Measurements 原值）

| 属性 | Standard | Modal |
|---|---|---|
| 容器高度 | **100%** | **100%** |
| 容器宽度 | **360dp** | **360dp** |
| 容器形状 | **0, 16, 16, 0dp**（仅靠内容一侧两角圆角） | —（未给） |
| 图标尺寸 | **24dp** | **24dp** |
| 指示器高度 | **56dp** | **56dp** |
| 指示器宽度 | **336dp** | **336dp** |
| 指示器形状（圆角） | **28dp**（= 高度一半，即全圆角药丸） | **28dp** |
| 文字水平对齐 | 起始 | 起始 |
| 左内边距 | **28dp** | **28dp** |
| 右内边距 | **28dp** | **28dp** |
| 指示器内边距 | **12dp** | **12dp** |
| 元素之间间距 | **0dp** | **0dp** |

数值之间可以互相对照：360 − 28 × 2 = 304 ≠ 336，说明 336dp 的指示器是**相对 sheet 内缘再内缩 12dp**（360 − 12 × 2 = 336），与"指示器内边距 12dp"一致。⚠️ 推断，页面未明示：上式是本库用来验证数值自洽的推导，不是官方说明。

## 颜色角色

官方列出 9 项（顺序即 token 面板顺序）：

| 元素 | 颜色角色 |
|---|---|
| Container（sheet） | `surfaceContainerLow` |
| Headline / 未选中文字 | `onSurfaceVariant` |
| 选中图标 | `onSecondaryContainer` |
| 选中文字 | `onSecondaryContainer` |
| 指示器 | `secondaryContainer` |
| 指示器内内容 | `onSecondaryContainer` |
| 未选中图标 | `onSurfaceVariant` |
| 未选中文字 | `onSurfaceVariant` |
| Scrim | `scrim` |

分隔线颜色见 `02-Components/divider.md`。⚠️ 推断，页面未明示：9 个值未逐元素标注，上表按 anatomy 顺序对应。

## 结构

Drawer 本质是「侧边 sheet 里的一个列表」，可以加分组副标题与分隔线：

元素：Sheet（容器）· Headline（可选）· Section label（可选）· Active indicator · Icon（可选）· Label · Badge label · Divider（可选）· Scrim（仅 modal）。

| 元素 | 规则 |
|---|---|
| Sheet | 所有元素的容器；**从屏幕起始边展开**（LTR 在左） |
| 指示器 | 背景形状，标出当前目的地 |
| 图标 | 可选；用了就**全部目的地都加**，不能只给一部分 |
| 文字 | 必需；简明，超出容器宽度时截断，**不换行、不缩字号** |
| Section label | 短副标题，用于给相关目的地分组 |
| 分隔线 | 只分隔**目的地分组**，不用于分隔单个目的地；用通栏宽度 |
| Scrim | 仅 modal，放在 sheet 正后方，可点击关闭 |

## 使用规则

✅ Standard 用于 expanded/large/XL，常驻或可关闭
✅ Modal 用于 compact/medium，必须由**抽屉外部**的动作打开（如 rail 或 app bar 里的菜单图标）
✅ 目的地按使用频率排序，最常用的放最上
✅ 层级切换组件时要有转场（竖屏 rail → 横屏 drawer）
✅ 抽屉内部可独立纵向滚动，正文保持不动

❌ 与 navigation bar 同时出现
❌ 用常驻 drawer 却允许关闭
❌ 文字换行或缩小字号来塞进一行
❌ 用分隔线分开每一个目的地

Web 补充规则：视口窄于 **320 CSS px** 时，把 drawer 换成 navigation bar 以保证可访问性。

## 行为

- **滚动**：目的地数量超过抽屉高度时，抽屉内部滚动，页面其余部分不动。
- **出现**：进入/退出用标准转场；modal 打开时 scrim 淡入。
- **选中**：点击项 → 以 top level 转场进入目的地；图标变实心、颜色加深（深色主题下变亮）。

## Compose 实现

```kotlin
val drawerState = rememberDrawerState(DrawerValue.Closed)
val scope = rememberCoroutineScope()

ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
        ModalDrawerSheet {          // 宽度即 360dp token
            NavigationDrawerItem(
                label = { Text("首页") },
                selected = current == "home",
                onClick = { navigate("home") },
                icon = { Icon(Icons.Default.Home, contentDescription = null) }
            )
        }
    }
) {
    MainContent(onOpenDrawer = { scope.launch { drawerState.open() } })
}
```

- 常驻形态用 `PermanentNavigationDrawer` + `PermanentDrawerSheet`；可关闭且不遮挡内容用 `DismissibleNavigationDrawer`。
- `NavigationDrawerItem` 的指示器、56dp 高度、28dp 圆角由 `NavigationDrawerTokens` 驱动，不要手写 `height(56.dp)` 与 `RoundedCornerShape(28.dp)`。
- 模态抽屉要处理 `Back`：`drawerState.isOpen` 时先关抽屉。`ModalNavigationDrawer` 不会自动接管系统返回，需自行拦截。
- 迁移到 expanded rail 时，`NavigationDrawerItem` 与 rail 的 item 语义不同（前者是列表项、后者是导航项），不要直接搬。

## 无障碍

| 要求 | 官方要点 |
|---|---|
| 触摸反馈 | 指示器出现 + ripple + 图标变实心 + 颜色加深 |
| 初始焦点 | 落在第一个导航项 |
| 关闭 | modal 可通过选中 scrim 关闭 |
| 键位 | `Tab` 进第一个目的地；`Space/Enter` 选中并**把焦点移入新打开的区块**；`Arrow` 在抽屉内移动 |
| 选中视觉 | 选中必须用实心图标，不能与未选中同一种图标样式 |
| 语义标签 | 通常等于目的地名；含糊时补充（"Recents" → "Recent images"） |
| Views 限制 | MDC-Android 下无法设置更详细的标签，角色也不播报 |

> "Icons are the primary focus of the navigation and thus give the dominant cue of its state."

## 常见错误

1. 新项目仍以 drawer 为默认方案（应评估 expanded rail）。
2. drawer 与 navigation bar 同时出现。
3. 只给部分目的地加图标。
4. 用分隔线分隔每一个列表项。
5. 标签换行或缩字号硬塞一行。
6. 常驻 drawer 还提供关闭按钮。
7. modal 抽屉打开后按返回键直接退出页面。
8. 抽屉打开时正文仍可滚动/可点击（缺 scrim 拦截）。
9. 自定义指示器高度与宽度，和 56 / 336dp 对不上。
10. Web 窄于 320 CSS px 仍用抽屉，触控目标不可用。

## 来源

- https://m3.material.io/components/navigation-drawer/specs
- https://m3.material.io/components/navigation-drawer/guidelines
- https://m3.material.io/components/navigation-drawer/accessibility
- 关联：`02-Components/navigation.md`（选型）、`02-Components/navigation-rail.md`（替代方案）、`02-Components/side-sheet.md`
