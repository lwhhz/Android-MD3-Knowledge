# Swipe to Dismiss / 滑动删除

- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15
- 状态：**Material 官网当前没有该组件的 Specs 页**（`components/swipe-to-dismiss/specs` 无内容），本页以 Compose API 与 M3 通则整理，尺寸/视觉参数标注为待核验

## 先说清楚一件事

`SwipeToDismissBox` 是 **Compose 组件**，但 Material 3 官网的组件目录里**没有对应的规范页**。所以：

- 它的**行为约定**（滑动方向、背景内容、撤销）来自 Compose 文档与 Material 通则；
- 它的**视觉参数**（背景色、圆角、图标位置）没有官方 dp 值可抄，需要按 M3 的角色系统自行映射。

这页因此重点写「怎么用才不出事故」，而不是「多少 dp」。

## 什么时候用

| 场景 | 是否适合 |
|---|---|
| 列表项删除（邮件、通知、待办） | ✅ 典型场景 |
| 归档 / 标记已读 | ✅ |
| 修改关键设置 | ❌ 太容易误触 |
| 支付、转账等不可逆操作 | ❌ 绝对不要 |
| 唯一入口的操作 | ❌ 必须另有按钮 |

**核心约束：滑动是"快捷方式"，不是"唯一入口"。**

## 三条硬规则

### 1. 必须可撤销

滑动删除后必须给撤销入口（通常是 Snackbar + 撤销按钮，约几秒窗口）。理由很直接：滑动是**无确认的高频手势**，误触率远高于点击。

```kotlin
scope.launch {
    val result = snackbarHostState.showSnackbar(
        message = "已删除 1 项",
        actionLabel = "撤销"
    )
    if (result == SnackbarResult.Dismissed) {
        permanentlyDelete(id)        // 只有用户没撤销才真删
    } else {
        restore(id)
    }
}
```

注意：撤销窗口内数据应**软删除**（标记 + 延迟真删），而不是删了再恢复——后者在进程被杀时无法挽回。

### 2. 方向要一致

整个应用里"向左滑"和"向右滑"必须含义固定。常见约定：向右滑 = 归档/完成，向左滑 = 删除。**不要同一个列表里两个方向做同一件事**，也不要让方向含义随行变化。

### 3. 背景内容要说明后果

滑动时露出的背景必须写明会发生什么：

- 背景色用语义角色：删除用 `errorContainer`，归档用 `secondaryContainer`。
- 背景里放**图标 + 文字**（如"删除"），不要只放一个图标。
- 达到触发阈值时给出视觉反馈（图标位置固定、背景铺满）。

## Compose 实现

```kotlin
val dismissState = rememberSwipeToDismissBoxState(
    confirmValueChange = { value ->
        when (value) {
            SwipeToDismissBoxValue.StartToEnd -> { onArchive(item); true }
            SwipeToDismissBoxValue.EndToStart -> { onDelete(item); true }
            else -> false
        }
    }
)

SwipeToDismissBox(
    state = dismissState,
    enableDismissFromStartToEnd = true,
    enableDismissFromEndToStart = true,
    backgroundContent = {
        val direction = dismissState.dismissDirection
        val color = when (direction) {
            SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
            SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.secondaryContainer
            else -> Color.Transparent
        }
        val alignment = when (direction) {
            SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
            else -> Alignment.CenterEnd
        }
        Box(
            Modifier.fillMaxSize().background(color).padding(horizontal = 20.dp),
            contentAlignment = alignment
        ) {
            Icon(Icons.Default.Delete, contentDescription = null)
        }
    }
) {
    ListItem(headlineContent = { Text(item.title) })
}
```

要点：
- `confirmValueChange` 里做业务判断并返回是否允许消失；返回 `false` 会回弹。
- **RTL 语言下方向会自动镜像**，不要按左右写死业务含义，要按 `StartToEnd` / `EndToStart` 判断。
- 列表项被移除后要给 `LazyColumn` 的 `key` 稳定值，否则动画错乱。

## 无障碍（这页最关键的部分）

**滑动操作对三类用户不可用**：读屏用户（TalkBack 会拦截滑动手势）、运动障碍用户、使用外接键盘/遥控的用户。

因此必须：

1. 每个可滑动项**同时提供非滑动的操作入口**（长按菜单、尾部溢出按钮，或详情页里的按钮）。
2. 滑动删除后**朗读**结果（Snackbar 会自动播报）。
3. 背景图标若承载语义要写 `contentDescription`；如果背景只是视觉提示，设 `null` 并在 `confirmValueChange` 里保证行为可撤销。
4. 不要用"三指滑动""双击滑动"之类自定义手势作为唯一入口。

## 常见错误

1. 没有撤销，删了就没了。
2. 撤销窗口内直接硬删除，进程一死无法恢复。
3. 背景只有颜色没有文字，用户不知道这一滑会发生什么。
4. 左右两个方向做同一件事，或含义在不同列表里不同。
5. 所有行都开启双向滑动，误触率翻倍。
6. 没有非滑动替代入口（无障碍硬伤）。
7. `LazyColumn` 没给稳定 `key`，删除后动画和状态错位。
8. 把 `SwipeToDismissBox` 用在表单、支付确认等不可逆场景。

## 待核验

- [ ] Material 官方是否会在后续版本补充该组件的 Specs 页
- [ ] 背景内容的推荐内边距与图标尺寸（当前按 M3 列表通则取 16-20dp）

## 来源

- Compose `SwipeToDismissBox` API（material3）
- `02-Components/snackbar.md`（撤销模式）
- `01-Foundations/color-system.md`（errorContainer / secondaryContainer）
- `01-Foundations/accessibility.md`
