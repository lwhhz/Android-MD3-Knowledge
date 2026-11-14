# Lists / 列表

- 官方指南：https://m3.material.io/components/lists
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14 ｜ 状态：`ListItem` 槽位名为当前 API；高度为 M3 通行值

## 用途

列表是 Android 上**最常用的信息组织形式**。判断该不该用卡片：内容条目之间是同质、可比较、需要连续扫描的，就用列表；每一条都是独立主题、需要图文混排的，才用卡片。

## 结构

`ListItem` 有五个槽位：

| 槽位 | 内容 | 样式 |
|---|---|---|
| `overlineContent` | 分类、状态标签 | Label Small |
| `headlineContent` | 主标题（必需） | Body Large / Title Medium |
| `supportingContent` | 副标题、说明 | Body Medium, `onSurfaceVariant` |
| `leadingContent` | 头像、图标、复选框 | — |
| `trailingContent` | 数值、状态、箭头、开关 | — |

| 行数 | 高度 | 场景 |
|---|---|---|
| 单行 | 56dp | 设置项、简单条目 |
| 双行 | 72dp | 标题 + 说明（最常用） |
| 三行 | 88dp | 再多一行元信息 |

左内边距 16dp，文字与前置元素间距 16dp，右内边距 16dp（有尾部元素时相应调整）。

## 分组与分隔

- 同组条目之间**默认不加分隔线**——间距本身已经分隔了。
- 不同语义分组之间用 `HorizontalDivider`，或加一个分组标题（Label Large，`onSurfaceVariant`）。
- 分隔线用 `outlineVariant`，不要用 `outline`（太重）。

## 列表本身

| 需求 | 组件 |
|---|---|
| 可滚动的同质条目 | `LazyColumn` |
| 短列表（< 10 项，整屏可见） | `Column` 也可以 |
| 需要拖拽排序 | 额外库，官方无现成方案 |

**超过一屏必须用 Lazy 版本。**

## Compose 实现

```kotlin
LazyColumn(
    contentPadding = PaddingValues(vertical = 8.dp)
) {
    items(
        items = notes,
        key = { it.id },
        contentType = { "note" }
    ) { note ->
        ListItem(
            headlineContent = {
                Text(
                    text = note.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            },
            supportingContent = {
                Text(note.preview, maxLines = 1, overflow = TextOverflow.Ellipsis)
            },
            leadingContent = {
                Icon(Icons.Outlined.Description, contentDescription = null)
            },
            trailingContent = {
                Text(note.time, style = MaterialTheme.typography.labelMedium)
            },
            modifier = Modifier.clickable { open(note.id) }
        )
    }
}
```

带分组标题：

```kotlin
stickyHeader { 
    Text(
        "本周",
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}
```

## 滑动与拖拽

- 删除用 `SwipeToDismissBox`，且**必须配撤销**（Snackbar）。纯滑动删除不可恢复，风险太高。
- 只有确实需要频繁操作的条目才加滑动，否则容易误触。
- 滑动方向要一致，不要一条左滑一条右滑。

## 无障碍

- 可点击条目用 `Modifier.clickable`，它会带 `role = Button` 语义（或显式指定 `role = Role.Button`）。
- 条目内多个 `Text`（标题 + 副标题）应合并为一次朗读：`Modifier.semantics(mergeDescendants = true)`，否则读屏会停顿两次。
- 尾部只有箭头图标时 `contentDescription = null`（它不携带额外信息）。
- 尾部是开关时，开关自己带语义，不要合并进条目。
- 长按有额外操作时，要提供替代路径（读屏用户无法长按）。

## 常见错误

1. `items()` 不传 `key`，删除时动画错乱、状态错位。
2. 混排多种布局却不传 `contentType`，复用错乱导致滑动掉帧。
3. 用 `Column` + `forEach` 渲染长列表，内存直接爆。
4. 每行都加 `Card` 包一层，一条列表变成一堆卡片。
5. 标题不设 `maxLines`，超长标题把布局撑破。
6. 滑动删除没有撤销。
7. `supportingContent` 用了 `Body Small` 以下字号，实际不可读。

## 来源

- https://m3.material.io/components/lists
- `02-Components/cards.md`（什么时候用卡片而不用列表）
- `07-Development-Rules/compose-coding-rules.md`（列表性能约束）
