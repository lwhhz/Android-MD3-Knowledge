# Navigation / 导航选择指南

- 官方指南：https://m3.material.io/components/navigation-bar ｜ https://m3.material.io/components/navigation-rail ｜ https://m3.material.io/components/navigation-drawer
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 状态：本篇是**选型指南**；三个组件的尺寸、颜色、规则、无障碍分别见
  `navigation-bar.md` / `navigation-rail.md` / `navigation-drawer.md`

## 2025-05 的路线变化（先读这段）

M3 Expressive 更新把三个导航组件的推荐关系整体改写了：

| 旧（baseline） | 现状 | 官方措辞 |
|---|---|---|
| Navigation bar | **不再推荐**，改用 flexible navigation bar | "The baseline nav bar is no longer recommended, and should be replaced by the flexible nav bar, which is shorter and supports horizontal navigation items in medium windows." |
| Navigation rail | **不再推荐**，改用 collapsed / expanded navigation rail | "The baseline navigation rail is no longer recommended, and should be replaced by the collapsed navigation rail." |
| Navigation drawer | **不再推荐**，改用 expanded navigation rail | "The navigation drawer is no longer recommended… use an expanded navigation rail, which has mostly the same functionality of the navigation drawer and adapts better across breakpoints." |

含义：**结构上只保留「底部条 / 侧边 rail（可展开）/ 抽屉」三类需求，但三类的推荐形态都换了一代。** Compose 侧的新形态仍在补齐，落地时先确认当前依赖是否已提供 flexible 形态；未提供时按 baseline 实现，但要清楚这是过渡方案。

## 选型：窗口宽度 × 目的地数量

| 目的地数 | Compact | Medium | Expanded 及以上 |
|---|---|---|---|
| 2 | ❌ 用 tabs | ❌ 用 tabs | ❌ 用 tabs |
| 3–5 | ✅ Navigation bar | ✅ Bar（横向项）或 Rail | ✅ Collapsed rail |
| 6–7 | ❌ 不要用 bar；考虑 tabs 分组或模态 expanded rail | ✅ Rail | ✅ Rail / Expanded rail |
| ≥ 8 或多级层级 | ❌ 模态 expanded rail | ✅ Expanded rail（modal） | ✅ Expanded rail / （旧方案）drawer |

窗口尺寸类的 dp 阈值（compact < 600、medium 600–839、expanded ≥ 840）来自窗口尺寸类定义，不在本篇三个 dump 内，见 `03-Android-Compose/adaptive-layout.md`。

官方对数量和断点的原话：

> "Navigation bars provide access to three to five destinations."
> "Don't use a navigation bar for fewer than three destinations. Instead, use tabs."
> "For products with more than five navigation items, don't use a navigation bar; the elements may collide and there likely won't be enough space for translated text."
> "Only use navigation bars for compact and medium breakpoints."
> "The collapsed nav rail… should contain 3–7 navigation items."

## 三个组件的职责

| 组件 | 位置 | 目的地数 | 适用断点 | 能否收纳二级 |
|---|---|---|---|---|
| Navigation bar | 底部，通栏 | 3–5 | compact / medium | ❌ |
| Navigation rail | 前缘（LTR 左侧） | 3–7 + 可选 FAB | medium 及以上（compact 禁用标准 rail） | 仅 expanded 形态 |
| Navigation drawer | 前缘抽屉 + scrim | 5+ | 任意；standard 用于 expanded+ | ✅（可加分组与子层级） |

## 决策顺序（工程版）

⚠️ 推断，页面未明示：官方没有给决策树，下面四问是按三篇 guidelines 的约束条件整理的判据。

1. **目的地是否 ≥ 3 且都是顶层？** 否 → 用 tabs（同页并列内容）。
2. **窗口是否 compact？** 是 → navigation bar（目的地 >5 时改用模态 expanded rail）。
3. **是否需要两层以上导航或 ≥ 8 个目的地？** 是 → expanded rail（modal 或 standard）。
4. **其余 medium/expanded 场景** → 按「纵向空间是否更紧张」在 bar 与 rail 间取舍：

> "Medium: Use a navigation bar or navigation rail. Decide based on whether horizontal or vertical space is more important."

## 三条跨组件硬规则

1. **一屏只用一个主导航组件**

> "Avoid using two navigation components on the same screen."

例外只有一种：medium/expanded 下 rail + modal drawer 组合，且抽屉要在层级上做出足够区分。

2. **位置固定，不随屏变化**

> "Navigation bar destinations have fixed positions. Don't scroll them or modify their positions."
> "Always put the rail in the same place, even on different screens of an app."

3. **标签不可省**

> "Don't remove the labels from navigation items."
> "All navigation items require a label text. It should be 1-2 words."（bar）

## 状态保持与返回栈

导航栏/rail 的切换属于**顶层目的地切换**：

> "When selecting a navigation bar item not currently selected, the product navigates to that destination's screen using a top level transition pattern. It can either remember where you left off, or reset to the default view."

两种策略都要显式选择：保留状态（回到原滚动位置、原 tab、原搜索词）或重置状态。频繁来回切换的产品应保留。

官方还规定：**再次点击当前已选目的地 = 回到顶部**（"Re-selecting the currently active destination should reset the scroll position to the top of the page."）。

Compose 里保留各目的地状态的标准写法：

```kotlin
navController.navigate(route) {
    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
    launchSingleTop = true
    restoreState = true
}
```

⚠️ 推断，页面未明示：官方只说「可以记住或重置」，`popUpTo + saveState/restoreState` 是 Compose 侧实现该语义的既有做法。

## 顶部栏

主 App bar 的四个变体（small / center-aligned / medium / large）与高度见 `02-Components/toolbars.md` 与 `02-Components/app-bars.md`，本篇不重复核验，避免两处数值不一致。

导航相关的一条硬规则在 tabs 里：**tabs 不能在滚动时跑到 App bar 后面**——两者附着时要作为一个整体移动。

## 无障碍通则（三组件共有）

| 要求 | 说明 |
|---|---|
| 选中态必须有多重指示 | 图标实心 + 颜色加深 + 指示器形状，三者至少两重；不要只靠颜色 |
| 实心图标缺失时 | 用更粗字重（semibold）的图标代替 |
| 文字缩放 | 1.5×、2× 时容器纵向增高、标签可换行；2× 以内必须完整可见 |
| 焦点顺序 | 初始焦点落在第一个导航项（rail 可能是菜单或 FAB） |
| 键位 | `Tab` / `Arrow` 移动，`Space/Enter` 选中 |
| 标签歧义 | 可见文字含糊时（如 "Library"），无障碍标签要补成 "Music library" |
| 对比度 | 图标与容器 ≥ 3:1；rail 容器填充关闭时同样成立 |

> "Note: On Android Views (MDC-Android), a more descriptive accessibility label is not available and the role is not announced." —— 用 Views 时这部分能力缺失，是选 Compose 的实际理由之一。

## 常见错误

1. 2 个目的地还用底部导航（官方下限 3）。
2. 底部导航塞 6 个以上，译文一长就撞车。
3. 同一屏同时出现 bar 与 rail。
4. 大屏仍用底部导航，横向空间全浪费。
5. 只有图标的导航项，或图标含义不自明。
6. 切换目的地丢失滚动位置与输入内容。
7. 用 NavigationBar 中间塞一个「+」发布按钮，破坏选中语义（那是 FAB 的职责）。
8. 把 drawer 当成 2025 之后的首选方案，忽略 expanded rail。
9. 模态抽屉的 scrim 与手势方向不匹配，滑不回去。

## 来源

- https://m3.material.io/components/navigation-bar/guidelines
- https://m3.material.io/components/navigation-rail/guidelines
- https://m3.material.io/components/navigation-drawer/overview
- 关联：`02-Components/navigation-bar.md`、`02-Components/navigation-rail.md`、`02-Components/navigation-drawer.md`、`03-Android-Compose/adaptive-layout.md`
