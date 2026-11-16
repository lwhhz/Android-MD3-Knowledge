# Tabs / 标签页

- 官方指南：https://m3.material.io/components/tabs ｜ 规格页：https://m3.material.io/components/tabs/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**全部尺寸为官方明示值**（含 Primary 3dp / Secondary 2dp 两套指示器）

## 用途

> "Tabs organize groups of related content that are at the same level of hierarchy."

同一层级的并列内容分组，属于**页面内**导航。关键区别是「相关」而非「顺序」：

> "Don't use tabs to move through sequential content that needs to be read in a particular order. Instead, create hierarchy within the content using techniques like typography style and open space."

| 用 Tabs | 用底部导航 | 用 Filter Chip |
|---|---|---|
| 同一页面内的并列内容组 | 应用级目的地 | 对同一列表做筛选 |
| 切换后仍在同一功能区内 | 每个目的地是独立页面 | 数据过滤，不换页面 |
| 文字标签短、可横向滚动 | 3–5 个顶层目的地 | 可叠加多条件 |

## 两个变体

| 变体 | 位置 | 用途 | Compose |
|---|---|---|---|
| Primary | 内容区顶部、App bar 之下 | 主内容目的地 | `PrimaryTabRow` |
| Secondary | 内容区**内部** | 在 Primary 之下再分层 | `SecondaryTabRow` |

> "Secondary tabs should always be placed below primary tabs."
> "Secondary tabs are necessary when a screen requires more than one level of tabs."

Secondary 的指示器更简单（2dp vs 3dp）、无图标槽位，但功能与 Primary 完全一致。⚠️ 推断，页面未明示：官方未规定最多几层，实际项目里两层以上应优先改信息架构。

## 结构

```
┌──────────────────────────────┐
│  [icon]  标签  [badge]        │  ← Container，均分/可滚动
│  ━━━━━━━                     │  ← Active indicator
└──────────────────────────────┘
──────────────────────────────   ← Divider（1dp，含在容器高度内）
```

元素：Container · Badge（可选）· Icon（可选，仅 Primary）· Label · Divider · Active indicator。

容器规则：

> "The container should always extend the full width of the window and be divided into equal sections, one for each tab."

分隔线在容器**底缘内**，"The divider is included in the height, placed inside the container."；内容可以从容器下方滚过。

## 尺寸（官方 Measurements 原值）

| 属性 | 值 |
|---|---|
| 容器高度（仅文字） | **48dp** |
| 容器高度（图标 + 文字） | **64dp** |
| 图标尺寸 | **24dp** |
| 分隔线高度 | **1dp** |
| Primary 指示器高度 | **3dp** |
| Secondary 指示器高度 | **2dp** |
| 指示器形状 | **3, 3, 0, 0**（上方两角全圆、下方两角直角） |
| 指示器最小长度 | **24dp** |
| 指示器左右内缩 | **各 2dp** |
| 图标与文字间距 | **8dp** |
| 文字与角标间距 | **4dp** |
| 角标叠在堆叠图标上的重叠量 | **6dp** |

原话：

> "Primary tab active indicators are inset 2dp on each side, have a fully rounded corner radius, and a minimum length of 24dp."

逐条核对结论：**3dp 只属于 Primary**，Secondary 是 2dp；「左右各缩进 2dp」「全圆角」「最短 24dp」三条对两者都成立（官方写在 Primary 段落，但形状 token `3,3,0,0` 是全族共用）。少了缩进或少了下缘直角，观感就退回 M2 的矩形下划线。

## 数量：4 是硬门槛

官方有两处口径，要一起读：

> guidelines："Avoid using more than four tabs at once. At five or more tabs, the container becomes cramped."
> overview："Tabs can horizontally scroll, so a UI can have as many tabs as needed"

合并后的可执行规则：

| 数量 | 做法 |
|---|---|
| 2–4 | 固定均分（`PrimaryTabRow`），可选图标 + 文字 |
| ≥ 5 | 改用**可滚动** tabs（`ScrollableTabRow`），标签长度决定每个 tab 宽度 |
| 超出可滚动仍难用 | 说明该用抽屉/下拉/分层结构，而不是继续加 tab |

旧版本把上限定成「7 个」没有官方依据；官方给的是 4 个的舒适上限 + 可滚动作为超限出口。

## 行为规则

- **固定 tabs**：点击切换，内容区可左右滑动切换。内容区里不要再放别的可横滑组件（地图、可滑列表）——官方 Don't 就是「手势方向要区分开」。
- **可滚动 tabs**：第一个可见 tab 距**前缘 52dp**，"so it's clear that more content is available"；每个 tab 宽度由标签长度决定；用箭头/Tab 键也能遍历。
- **不要无限循环**滚动 tab 集合：会让线性导航的读屏用户被困住。
- **滚动联动**：tabs 可以随内容滚出屏幕、向上滚回来；但不能滚到 App bar **后面**（"When tabs are attached to a component, they should appear and move as a single unit."）。
- **角标**：最多 4 个字符（含 `+`）；用户看过该 tab 内容后，角标数值要更新或消失。
- **标签**：单行，必要时可用第二行并截断；可滚动 tabs 才有更长空间。不要无故截断。

## 图标与标签的搭配

> "Icons alone aren't as effective as text labels at communicating complex content."
> "Don't use tabs with both icons and text labels on only some tabs, but not others."

- 允许纯图标 tab，但必须是**全球通用**图标，且无障碍标签要写清功能
- 一屏内要么全部「图标 + 文字」，要么全部「纯文字」；不能一部分带图标、另一部分不带
- 图标 + 文字时垂直居中（M3 与 M2 的差异点之一）

## 颜色角色

| 变体 | 容器 | 选中图标 | 选中文字 | 未选中图标/文字 | 分隔线 | 指示器 |
|---|---|---|---|---|---|---|
| Primary | `surface` | `primary` | `primary` | `onSurfaceVariant` | `outlineVariant` | `primary` |
| Secondary | `surface` | —（无图标） | `onSurface` | `onSurfaceVariant` | `outlineVariant` | `primary` |

状态集合：Enabled / Hover / Focused / Pressed × (active / inactive)。

⚠️ 推断，页面未明示：官方 token 面板按元素顺序列出颜色值（Primary 7 项、Secondary 5 项），未标注元素与值的绑定关系；上表按 anatomy 顺序对应，落地前在设计工具中二次确认。

## Compose 实现

```kotlin
PrimaryTabRow(selectedTabIndex = selectedIndex) {
    tabs.forEachIndexed { index, tab ->
        Tab(
            selected = selectedIndex == index,
            onClick = { selectedIndex = index },
            text = { Text(tab.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            icon = tab.icon?.let {
                { Icon(it, contentDescription = null, modifier = Modifier.size(24.dp)) }
            }
        )
    }
}
```

- Secondary 用 `SecondaryTabRow`，不要给它传 `icon`（官方结构里没有图标槽位）。
- 5 个以上改用 `ScrollableTabRow`，并用 `edgePadding` 实现官方要求的 52dp 首项偏移。
- 需要精确控制指示器时用 `TabRowDefaults.PrimaryIndicator(...)`（可传 `width` / `height` / `shape`），比自定义 `Modifier.tabIndicatorOffset` 更贴近 token。
- 配合 `HorizontalPager` 时 `selectedIndex` 与 `currentPage` 双向同步，避免滑动后高亮错位。

## 无障碍

| 要求 | 官方要点 |
|---|---|
| 焦点进入 | 焦点先到第一个可交互项，指示器随之出现 |
| 键位 | `Arrow` 下一个目的地；`Space/Enter` 激活 |
| 禁止用 Space/Enter 遍历 | "Space/Enter is only used for completing actions." 遍历只能用箭头/Tab |
| 选中反馈 | 触摸时 ripple 穿过指示器、指示器移位；键盘聚焦有 focus 指示器 |
| 标签可描述性 | 纯图标 tab 的无障碍标签要补充意图，例：摄像机图标 → "Video format media content" |
| 密度 | 默认不开密度，否则目标低于 48×48 |
| 循环滚动 | 不要做无限循环，读屏用户会被困 |

> "It's not recommended to loop a tab set where it scrolls infinitely. This can trap users who are navigating linearly with a screen reader."

## 常见错误

1. 5 个 tab 还强行固定均分，标签被压到不可读（官方明确 >4 就拥挤）。
2. 只改指示器颜色、不改高度/缩进/下缘直角，观感退回 M2。
3. 给 Secondary 也画 3dp 指示器，或给它塞图标。
4. 部分 tab 带图标、部分不带。
5. 可滚动 tabs 没有 52dp 首项偏移，用户看不出还能滑。
6. tabs 滚到 App bar 下面去，视觉上分成两块。
7. 内容区放了横向可滑组件（地图、轮播），与切页手势打架。
8. 用 tabs 承载有先后顺序的内容（应该用排版层级）。
9. 角标超过 4 个字符，或看完不消除。

## 来源

- https://m3.material.io/components/tabs/specs
- https://m3.material.io/components/tabs/guidelines
- https://m3.material.io/components/tabs/accessibility
- 关联：`02-Components/navigation.md`（tabs 与导航组件的边界）、`02-Components/chips.md`（filter chip）
