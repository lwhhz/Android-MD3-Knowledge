# Navigation Bar / 底部导航栏

- 官方指南：https://m3.material.io/components/navigation-bar ｜ 规格页：https://m3.material.io/components/navigation-bar/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**规格页 measurements 为图片；2026-11-16 已对测量图做视觉读取，baseline 数值完整、flexible 部分数值待 Figma Kit 复核**；颜色角色与规则为官方明示

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

Specs 页的 measurements **全部是图片**。以下数值为 2026-11-16 从官方测量图逐个视觉读取（原始 PNG 存档见 `06-Figma-Resources/asset-index.md`）：

### Baseline navigation bar（经典形态）

| 项 | 值 | 图示依据 |
|---|---|---|
| 高度 | **80dp**（12 + 32 + 4 + 标签区 + 16 组成；图未直接印总数，但组成值如下） | padding/size 测量图 |
| 上内边距 | **12dp** | 测量图顶部标注 |
| 下内边距 | **16dp** | 测量图底部标注 |
| 图标行与标签间距 | **4dp** | 测量图中部标注 |
| 选中指示器 | **64 × 32dp**（宽 × 高） | 测量图横向/纵向标注 |
| 图标 | **24dp**（未选中项图标容器） | 测量图标注 |
| 徽标 | **16dp**；小圆点徽标 **6dp** | 测量图标注 |
| 项内边距（水平） | 竖排项动态等分宽度（见上文引用） | — |

### Flexible navigation bar（Expressive，推荐）

图中有两种项布局的测量（数值锚点按原图描述，绑定关系为视觉判读）：

**图标在上、标签在下（竖排项）：**

| 值 | 图示所指 |
|---|---|
| 6 / 6 | 项区域上、下内边距 |
| 32 | 图标区高度 |
| 4 | 图标区与标签间距 |
| 56 | 选中项指示器（虚线框） |
| 16 | 顶部到图标的偏移（视觉判读 ⚠️） |

**图标与标签并排（横排项，medium 窗口）：**

| 值 | 图示所指 |
|---|---|
| 12 / 12 | 项区域上、下内边距 |
| 40 | 选中项指示器高度（虚线框） |
| 4 | 图标与标签间距 |
| 20 | 指示器内左侧留白（视觉判读 ⚠️） |

⚠️ flexible 形态的**整条高度**未在图中直接标注；"Navigation bar and item widths"、"target size and margins" 等图与「3 种配置」图无印刷数值（仅编号）。确切高度请以 Figma Design Kit 或 Compose 默认值为准。

其余不依赖测量图的官方明示值：

| 项 | 值 | 出处 |
|---|---|---|
| 容器宽度 | 窗口宽度的 **100%** | "The navigation bar container spans 100% of the window width." |
| 容器位置 | 窗口底部 | "The container should always be placed at the bottom of the product and span the full length of the window." |
| 状态层 | Hover **8%**、Focus **10%**、Press **10%** | specs 页 States 段 |

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
- 不要自己写 `height(80.dp)` 之类的固定高度：baseline 的 80dp 是测量图值，flexible 高度官方未标注，组件按内容与 inset 自适应；系统手势区由 Scaffold 的 inset 处理。

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
10. 给 baseline 写错数值（正确值：高 80dp、指示器 64×32dp、图标 24dp；flexible 整条高度官方未标注）。

## 来源

- https://m3.material.io/components/navigation-bar/specs
- https://m3.material.io/components/navigation-bar/guidelines
- https://m3.material.io/components/navigation-bar/accessibility
- 关联：`02-Components/navigation.md`（选型）、`02-Components/navigation-rail.md`、`02-Components/fab.md`
