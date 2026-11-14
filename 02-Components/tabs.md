# Tabs / 标签页

- 官方指南：https://m3.material.io/components/tabs ｜ 规格页：https://m3.material.io/components/tabs/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14 ｜ 核验方式：Chrome CDP 读官方 Specs 页
- 状态：**尺寸与指示器规格全部为官方原值**

## 用途

Tabs 在**同一层级**的内容之间切换，属于页面内的导航，不是应用级导航。

边界要分清：

| 用 Tabs | 用底部导航 | 用 Filter Chip |
|---|---|---|
| 同一页面的并列视图 | 应用级目的地切换 | 对同一列表做筛选 |
| 2-7 个，平级 | 3-5 个，顶层 | 多个可叠加条件 |
| 切换不改变返回栈 | 每个目的地有独立返回栈 | 数据过滤，不换页面 |

**Tabs 不产生新的返回栈。** 按返回键应该退出页面，而不是回到上一个 Tab。

## 两个变体

| 变体 | 用途 | Compose |
|---|---|---|
| Primary tabs | 主层级内容切换（默认） | `PrimaryTabRow` |
| Secondary tabs | 嵌套在 Primary 内部，做次一层切换 | `SecondaryTabRow` |

一屏最多两层：Primary 里面再套 Secondary。三层就该重新设计信息架构。

## 结构（官网 Anatomy）

```
┌──────────────────────────────┐
│  [icon]  标签  [badge]        │
│  ━━━━━━━                     │  ← Active indicator
└──────────────────────────────┘
──────────────────────────────   ← Divider
```

元素：Container · Badge（可选）· Icon（可选）· Label · Divider · Active indicator

## 尺寸（官方 Measurements 原值）

| 属性 | 值 |
|---|---|
| 容器高度（仅文字） | **48dp** |
| 容器高度（图标 + 文字） | **64dp** |
| 图标尺寸 | **24dp** |
| 分隔线高度 | **1dp** |
| Primary 指示器高度 | **3dp** |
| Secondary 指示器高度 | **2dp** |
| 指示器最小长度 | **24dp** |
| 指示器左右内缩 | **各 2dp** |
| 指示器圆角 | 全圆角 |
| 图标与文字间距 | **8dp** |
| 文字与角标间距 | **4dp** |
| 角标叠在图标上的重叠量 | **6dp** |

指示器这几条组合起来才有 M3 的手感：**3dp 高、左右各缩进 2dp、两端全圆、最短 24dp**。少了任一条（尤其是"缩进"），看起来就像 M2。

## 固定 vs 可滚动

| 数量 | 做法 |
|---|---|
| 2-4 个 | 固定宽度均分（`TabRow` / `PrimaryTabRow`） |
| 5-7 个 | 可横向滚动（`ScrollableTabRow`） |
| 超过 7 个 | 不该用 Tabs，改用抽屉或下拉 |

固定布局超过 4 个，标签文字会被压到不可读——这是最常见的 Tab 翻车点。

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

配合 `HorizontalPager` 做手势切换时，`selectedIndex` 与 pager 的 `currentPage` 要双向同步，且用 `LaunchedEffect` 避免回弹抖动。

## 无障碍

- 每个 Tab 必须有文字标签。**只有图标的 Tab 不可用**（读屏无法区分）。
- 选中态由 `Tab` 自动提供，不要自己加"选中"文字。
- Tab 与内容区要有语义关联：内容区应让读屏知道"当前在哪个 Tab 下"。
- 标签文字在 200% 字号下会换行，`maxLines = 1` + 省略号会丢信息——关键标签允许两行或改用可滚动布局。
- 角标（Badge）里的数字要让读屏能读到，不要只做视觉。

## 常见错误

1. 用 Tabs 做应用级导航（应该用底部导航）。
2. 5 个以上 Tab 还强行固定均分，文字被截。
3. 给了 Tab 独立返回栈，返回键行为混乱。
4. 指示器没有缩进 / 不是全圆角，观感退回 M2。
5. Tab 内容各自懒加载但每次切换都重新请求，没有缓存。
6. 用颜色单独表示选中（指示器缺失时尤其糟）。
7. 只有图标的 Tab。
8. `HorizontalPager` 与 Tab 状态不同步，滑动后高亮错位。

## 来源

- https://m3.material.io/components/tabs/specs
- `02-Components/navigation.md`（Tabs 与底部导航的边界）
- `02-Components/chips.md`（Filter Chip 与 Tabs 的区别）
