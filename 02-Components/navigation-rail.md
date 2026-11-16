# Navigation Rail / 侧边导航栏

- 官方指南：https://m3.material.io/components/navigation-rail ｜ 规格页：https://m3.material.io/components/navigation-rail/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**规格页 measurements 只有图片，无 dp 数值**；颜色角色与规则为官方明示

## 变体：两种宽度，各有替代对象

> "A collapsed and expanded navigation rail have been introduced to replace the baseline nav rail. The expanded nav rail is meant to replace the navigation drawer."

| 变体 | M3 | M3 Expressive | 替代谁 |
|---|---|---|---|
| Collapsed navigation rail | — | ✅ 推荐 | baseline navigation rail |
| Expanded navigation rail | — | ✅ 推荐 | navigation drawer |
| Navigation rail（baseline） | ✅ | ❌ 不再推荐 | — |

Expanded 又有两种模态：

| 配置 | 表现 | 适用 |
|---|---|---|
| Standard（默认） | 与正文并排，占布局空间 | 大窗口、横向空间充足 |
| Modal | 覆盖在正文之上（配 scrim） | 信息密集、目的地多的小窗口 |

另外两个可配置行为：

- **展开行为**：展→收（transition to collapsed）或收起时完全隐藏（hide when collapsed）。官方仅 Expressive 支持"hide when collapsed"。
- **收起后不可隐藏**："The collapsed navigation rail should not be hidden."——沉浸式场景只有 expanded 形态可以整体隐藏。

## 数量与断点

| 项 | 规则 |
|---|---|
| 目的地数 | **3–7** + 可选 FAB（"should contain 3–7 navigation items"） |
| Compact | ❌ 不要用标准 rail（空间不够），用 navigation bar |
| Medium | ✅ Rail；若目的地很少，也可用 bar |
| Expanded / Large / XL | ✅ Rail，不用 bar |

> "Compact: Don't use a standard navigation rail for compact layouts due to space constraints. Use a navigation bar instead."
> "Only use navigation rails for medium breakpoints and larger."

超过 5 个目的地且空间紧张时，官方建议改用**模态展开的 rail**。

## 尺寸

Specs 页 "Navigation rail padding and size measurements" 与 baseline 的两张测量图**均为图片**，文字层没有 dp 数字。本页可确证的只有布局约束：

| 项 | 规则 | 出处 |
|---|---|---|
| 容器宽度 | 未给数值；collapsed 为窄条，expanded 更宽 | — |
| 项的目标区域 | **始终横跨 rail 全宽**（即使项容器只包住内容） | "The navigation item's target area always spans the full width of the nav rail, even if the item container hugs its contents." |
| Expanded 的指示器 | **包裹标签文字**，不铺满容器 | "The active indicator hugs the label text in the expanded nav rail." |
| FAB 高度层级 | 嵌在 rail 内时 resting elevation = **level 0** | guidelines |
| Rail 与横滑内容的分离 | 可加分隔线，或抬到 **level 1** | guidelines |

⚠️ 推断，页面未明示：常见资料里的「rail 宽 80dp、指示器 32×56dp、图标 24dp」在本轮 dump 中无出处，本库不引用为官方值。要精确数值请查 Figma Design Kit。

## 颜色角色

官方列出 9 项（顺序即 token 面板顺序）：

| 位置 | 颜色角色 |
|---|---|
| Container（可选填充） | `surfaceContainer` |
| 选中图标 | `onSecondaryContainer` |
| 指示器 | `secondaryContainer` |
| 选中标签 | `secondary`（竖排）/ `onSecondaryContainer`（横排） |
| 未选中图标 | `onSurfaceVariant` |
| 未选中标签 | `onSurfaceVariant` |
| 徽标（3 项） | `error`、`onError`、`error` |

可确认的语义：Expressive 把竖排时选中标签从 `onSurfaceVariant` 改成了 `secondary`。⚠️ 推断，页面未明示：三个 `error` 系角色通常对应大徽标容器/文字与小徽标，逐项绑定需在设计工具核对。

容器填充可以关掉（rail 直接坐在页面上），此时：

> "When doing this, make sure all items have a minimum of 3:1 color contrast."

## 结构

元素：Container · Menu（可选）· FAB / Extended FAB（可选）· Icon · Active indicator · Label text · Large badge（可选）+ label · Small badge（可选）。

| 元素 | 规则 |
|---|---|
| 位置 | **前缘**（LTR 在左，RTL 在右），始终纵向；不能做成横向 |
| 对齐 | 整组项可顶部对齐或居中；平板建议居中以便触达 |
| Menu 图标 | 只在**顶部**；展开后图标要变成"可收起"的语义 |
| FAB | 只在**顶部**，位于所有目的地之上；展开时 FAB 可变为 extended FAB |
| Logo | 允许放顶部，但不要让它看起来像按钮；**不要用 logo 当展开按钮** |
| 标签 | 必需，**一个词**；需要断行时在词间断开或加连字符，不要缩小字号、不要省略号 |
| 徽标 | 收起时在图标右上角；展开时放在标签旁边 |
| 分隔线 | 可选，纵向，贴在**靠内容区那一侧** |

> "The collapsed nav rail runs along the leading edge of the window… It should not be hidden."
> "Don't use the navigation rail horizontally. Use a navigation bar instead."

## 使用规则

✅ 中等及以上窗口的**唯一**可见导航元素
✅ 自适应布局中放在**所有 pane 之外**，沿窗口前缘，不放进正文
✅ rail 可与 tabs 共存（tabs 提供第二层可见导航）
✅ 窗口由大变小可把 rail 转为 navigation bar；**转换要有转场**
✅ 展开/收起时页面内容要自动重排（不能只是 rail 变宽、内容被挤出去）

❌ 同时使用 rail 与 navigation bar（"Never use the navigation rail and navigation bar simultaneously."）
❌ 把 rail 放进正文内容里
❌ 用多个颜色或低对比色区分目的地

> "A navigation rail should be the only visible navigation element"

滚动行为：

- 纵向滚动时**目的地固定不动**；
- 横向滚动时 rail 可以滚走或固定；固定时用分隔线或 level 1 阴影把"内容在下面滚"表达出来。

返回：`predictive back` 只对**模态展开**的 rail 生效（"Predictive back only applies to the modal expanded navigation rail."），收起时 rail 从窗口边缘弹出。

## Compose 实现

```kotlin
NavigationRail(
    header = {
        FilledIconButton(onClick = { }) { Icon(Icons.Default.Edit, "新建") }
    }
) {
    destinations.forEach { dest ->
        NavigationRailItem(
            selected = current == dest.route,
            onClick = { navigateTo(dest.route) },
            icon = {
                Icon(
                    if (current == dest.route) dest.filledIcon else dest.outlinedIcon,
                    contentDescription = null
                )
            },
            label = { Text(dest.label) }
        )
    }
}
```

- `header` 槽位放菜单或 FAB；不要自己算顶部间距，槽位已按 token 处理。
- 需要 rail ↔ bar 自适应时用 `NavigationSuiteScaffold`（`material3-adaptive-navigation-suite`）。
- Expanded / modal wide 形态在 material3 1.4+ 线逐步提供，类名与参数仍在变动，用前查当前版本 API reference（⚠️ 推断，未在 dump 中核验）。

## 无障碍

| 要求 | 官方要点 |
|---|---|
| 触摸反馈 | 指示器出现 + ripple + 图标变实心 + 图标与文字变色 |
| 目标区域 | expanded rail 的目标区横跨容器全宽，即使指示器只包住内容 |
| 文字缩放 | 项纵向增高、标签可换行；**2× 以内必须完整可见** |
| 初始焦点 | 落在第一个可交互元素（可能是菜单或 FAB） |
| 键位 | `Tab` / `Arrow` 移动，`Space/Enter` 选中；从 FAB/菜单用 Tab 进入项列表 |
| 图标 | 选中实心、未选中描边；无实心版用 semibold 字重 |
| 语义标签 | 通常等于可见标签；含糊时补充（"Recent" → "Recent images"） |
| 对比度 | 选中与未选中图标都要与容器有足够对比；容器无填充时 ≥ 3:1 |

## 常见错误

1. 手机上用标准 rail（官方要求 compact 用 bar）。
2. rail 与 bar 同时出现。
3. rail 横过来当 tab 用。
4. 收起态做成可隐藏，用户找不到导航。
5. 展开后指示器铺满整行，看起来像 drawer 的列表项（除非有意覆盖成 drawer 观感）。
6. FAB 放在目的地下面。
7. 标签两个词以上还硬塞，缩小字号或显示省略号。
8. rail 放在 pane 里而不是窗口前缘，窗口一变宽就错位。
9. 展开/收起没有内容重排，正文被遮挡。
10. 引用「rail 宽 80dp」为官方值（本页查不到）。

## 来源

- https://m3.material.io/components/navigation-rail/specs
- https://m3.material.io/components/navigation-rail/guidelines
- https://m3.material.io/components/navigation-rail/accessibility
- 关联：`02-Components/navigation.md`（选型）、`02-Components/navigation-drawer.md`（被 expanded rail 取代）、`02-Components/fab.md`
