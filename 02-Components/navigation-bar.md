# Navigation Bar / 底部导航栏

- 官方指南：https://m3.material.io/components/navigation-bar ｜ 规格页：https://m3.material.io/components/navigation-bar/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**规格页 measurements 只有图片，无 dp 数值**；颜色角色与规则为官方明示

## 变体：baseline 已被 flexible 取代

> "The baseline nav bar is no longer recommended, and should be replaced by the flexible nav bar, which is shorter and supports horizontal navigation items in medium windows."

| 变体 | M3 | M3 Expressive | 说明 |
|---|---|---|---|
| Flexible navigation bar | — | ✅ 推荐 | 更矮；medium 窗口支持横向项 |
| Navigation bar（baseline） | ✅ | ❌ 不再推荐 | 仍可用，属过渡方案 |

Expressive 更新（2025-05）带来的具体差异：

- **高度变矮**（"Shorter height"）——官方**未给出具体 dp**；
- **选中标签颜色** 从 `onSurfaceVariant` 改为 `secondary`；
- medium 窗口可使用**横向导航项**（baseline 仅竖排）。

## 两种项布局

| 布局 | 结构 | 适用 |
|---|---|---|
| Vertical（默认） | 图标在上、标签在下，指示器包住两者 | Compact 窗口（手机） |
| Horizontal | 图标与文字并排，同在指示器内 | Medium 窗口（平板） |

> "Vertical navigation items work best in compact windows. Horizontal items work best in medium windows."
> "Horizontal items are centered in the nav bar with outer margins."

竖排项**动态等分**容器宽度；横排项**固定宽度**，多余空间加在两端：

> "Vertical navigation items dynamically change width to equally fit the container. Horizontal navigation items have a fixed width, so extra space is added to the ends of the navigation bar instead."

## 尺寸

Specs 页的 "Navigation bar padding and size measurements"、"width and margins"、"target size and margins"、"baseline measurements" **全部是图片**，文字层没有任何 dp 数字。因此本页能写死的尺寸只有：

| 项 | 值 | 出处 |
|---|---|---|
| 容器宽度 | 窗口宽度的 **100%** | "The navigation bar container spans 100% of the window width." |
| 容器位置 | 窗口底部 | "The container should always be placed at the bottom of the product and span the full length of the window." |
| 状态层 | Hover **8%**、Focus **10%**、Press **10%** | specs 页 States 段 |

⚠️ 推断，页面未明示：常见资料里的「80dp 高、指示器 32×64dp、图标 24dp」在本轮 dump 中**找不到出处**，本库不再作为官方值引用。需要精确值时以 Figma Design Kit 的组件测量为准。

## 颜色角色

**新（flexible / 当前）**，官方列出 6 项：

| 元素 | 颜色角色 |
|---|---|
| Container | `surfaceContainer` |
| 选中图标 | `onSecondaryContainer` |
| 选中标签 | `secondary` |
| 指示器 | `secondaryContainer` |
| 未选中图标 | `onSurfaceVariant` |
| 未选中标签 | `onSurfaceVariant` |

**Baseline**，官方列出 6 项：

| 元素 | 颜色角色 |
|---|---|
| Container | `surface` |
| 选中图标 | `onSecondaryContainer` |
| 选中标签 | `onSurface` |
| 指示器 | `secondaryContainer` |
| 未选中图标 | `onSurfaceVariant` |
| 未选中标签 | `onSurfaceVariant` |

两代差在**容器**（surfaceContainer vs surface）与**选中标签**（secondary vs onSurface）。徽标颜色见 `02-Components/badge.md`。

## 结构

元素：Container · Icon · Label text · Active indicator · Small badge（可选）· Large badge（可选）· Large badge label。

| 元素 | 规则 |
|---|---|
| Container | 通栏、底部、有填充色以与内容分离；导航项在容器内居中 |
| Icon | 必需。选中用实心（filled），未选中用描边（outlined） |
| Label | 必需，**1–2 个词** |
| Active indicator | 只用于当前目的地 |
| Badge | 叠在图标右上角，可显示数量 |

图标缺实心版本时：

> "If an icon doesn't have a filled version, apply semibold weight to the icon instead."

对比度：选中与未选中图标对容器 **≥ 3:1**。

## 使用规则

✅ 3–5 个**同等重要**的顶层页面；目的地跨屏保持一致
✅ 只用于 compact / medium 窗口
✅ FAB 右对齐放在导航栏**上方**
✅ 滚动时导航栏可隐藏/显示；但读屏开启时不要隐藏

> "Don't hide the navigation bar on scroll when a screen reader is active."

❌ 少于 3 个目的地（改用 tabs）
❌ 超过 5 个（元素会挤在一起，译文没空间）
❌ 去掉标签
❌ 目的地可滚动或改变顺序
❌ 用于桌面布局（"Instead, use a navigation rail or tabs."）
❌ 用 FAB 盖住导航栏
❌ 用多种颜色或低对比色区分目的地

导航栏可以被对话框、bottom sheet、drawer、软键盘**临时**覆盖，但不应被永久遮挡。

## 行为

- **切换**：点击未选中项 → 以 top level 转场进入该目的地，图标变实心，指示器从图标中心展开。
- **状态**：可选择保留（滚动位置/tab/搜索）或重置。
- **重复点击当前项** → 滚回顶部。
- **不支持横滑切换目的地**：

> "Swiping across the screen does not navigate between destinations, and is not supported by the navigation bar."

- **指示器动画只在一个轴上**（"should only apply on one axis to better represent a flat, shared plane"）。

## Compose 实现

```kotlin
NavigationBar {
    destinations.forEach { dest ->
        NavigationBarItem(
            selected = current == dest.route,
            onClick = { navigateTo(dest.route) },
            icon = {
                Icon(
                    imageVector = if (current == dest.route) dest.filledIcon else dest.outlinedIcon,
                    contentDescription = null   // 标签已提供语义
                )
            },
            label = { Text(dest.label, maxLines = 1) }
        )
    }
}
```

- 自适应切换（手机 bar / 平板 rail）可用 `NavigationSuiteScaffold`（`material3-adaptive-navigation-suite` artifact），它会按窗口尺寸类自动换组件；该 API 曾为 experimental，用前查注解。
- Expressive 的 flexible/short 形态在较新 material3 版本中有对应 composable，但命名与包路径在 1.4–1.5 线仍在变动，**不要照抄旧教程里的类名**，以当前版本 API reference 为准（⚠️ 推断，未在本次 dump 中核验）。
- 不要自己写 `height(80.dp)` 之类的固定高度：官方未给高度，组件按内容与 inset 自适应；系统手势区由 Scaffold 的 inset 处理。

## 无障碍

| 要求 | 官方要点 |
|---|---|
| 触摸反馈 | 指示器就地出现 + ripple 穿过指示器 + 图标变实心并变色 |
| 文字缩放 | 容器纵向增高以容纳更大标签（保留默认内边距）；标签可换行；**2× 以内必须完整可见**，超出可截断 |
| 初始焦点 | 落在第一个导航项 |
| 键位 | `Tab` 在项间移动，`Space/Enter` 选中 |
| 语义标签 | 通常等于目的地名；文字含糊时补充意图（"Library" → "Music library"） |
| 角色播报 | Android Views 下无法设置更详细的标签，且不播报角色 |

> "To remain accessible, ensure the full label is always visible on-screen at up to 2x text sizing."

## 常见错误

1. 2 个或 6 个以上目的地。
2. 去掉标签只留图标。
3. 选中项仍用描边图标。
4. 指示器同时出现在多个项上。
5. 大屏（desktop/expanded）继续用底部导航。
6. FAB 压在导航栏上。
7. 目的地顺序随页面变化，或做成可滚动。
8. 用横滑手势切目的地。
9. 读屏开启时仍在滚动中隐藏导航栏。
10. 直接把 80dp 当作官方高度写进设计规范（本页查不到该数值）。

## 来源

- https://m3.material.io/components/navigation-bar/specs
- https://m3.material.io/components/navigation-bar/guidelines
- https://m3.material.io/components/navigation-bar/accessibility
- 关联：`02-Components/navigation.md`（选型）、`02-Components/navigation-rail.md`、`02-Components/fab.md`
