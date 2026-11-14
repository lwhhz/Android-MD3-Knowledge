# Menus / 菜单

- 官方指南：https://m3.material.io/components/menus
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14 ｜ 状态：`DropdownMenu` / `ExposedDropdownMenuBox` 为当前 API；Expressive 新增的子菜单与分组能力状态需逐项核验

## 用途

菜单是从一个触发点**展开一组动作**的浮层。它解决的问题是：一个位置有多个平级操作，但全铺出来太占地方。

不适合菜单的：
- 主操作（应该直接是个按钮）
- 需要解释的操作（菜单项只有一行字，解释不了）
- 需要多步输入的操作（用 Bottom Sheet 或页面）

## 变体

| Compose | 说明 |
|---|---|
| `DropdownMenu` + `DropdownMenuItem` | 标准下拉菜单，挂在某个锚点旁 |
| `ExposedDropdownMenuBox` | 选择式下拉（看起来像输入框），用于「从列表里选一个」 |
| 子菜单 / 分组 / 勾选项 | 较新能力，见下 |

## 结构与尺寸

```
┌─────────────────────┐
│  [✓] 选项一          │  48dp 高
│      选项二          │
│ ─────────────────── │  分组分隔
│      删除            │  error 色
└─────────────────────┘
```

- 单项高度 48dp，水平内边距 12-16dp。
- 宽度 112dp - 280dp（按内容，不超过上限）。
- 形状 `extraSmall`（4dp）。
- 层级 **Level 2（3dp）**（官方组件分级表），容器用 `surfaceContainer`。

## 内容规则

- **项数控制在 5-7 项以内**。再多就该考虑 Bottom Sheet 或独立页面。
- 文案用动词短语（「重命名」「导出为 PDF」），不要用名词。
- 破坏性操作（删除）放**最后一项**，用 `error` 色，并且**不与常用操作相邻**。
- 勾选型菜单项（显示当前是否启用）用尾部对勾，不要用开关。
- 菜单项不支持副标题。需要解释时用 Bottom Sheet。

## Compose 实现

```kotlin
Box {
    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "更多操作")
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("重命名") },
            onClick = {
                expanded = false
                onRename()
            }
        )
        DropdownMenuItem(
            text = { Text("导出") },
            onClick = {
                expanded = false
                onExport()
            }
        )
        HorizontalDivider()
        DropdownMenuItem(
            text = {
                Text("删除", color = MaterialTheme.colorScheme.error)
            },
            onClick = {
                expanded = false
                onDelete()
            }
        )
    }
}
```

选择式下拉：

```kotlin
ExposedDropdownMenuBox(
    expanded = expanded,
    onExpandedChange = { expanded = it }
) {
    OutlinedTextField(
        value = selected,
        onValueChange = {},
        readOnly = true,
        label = { Text("排序方式") },
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
    )
    ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                text = { Text(option.label) },
                onClick = {
                    onSelect(option)
                    expanded = false
                }
            )
        }
    }
}
```

> `menuAnchor` 的签名在 material3 1.3 之后带上了 `MenuAnchorType` 参数。旧写法 `menuAnchor()` 已 deprecated，迁移时补上类型参数。

## 无障碍

- 触发按钮必须有语义：「更多操作」，不要只放一个三点图标而不写 `contentDescription`。
- 菜单展开时读屏焦点应进入第一项，关闭后回到触发按钮。
- 菜单项的文字就是朗读内容，不要再额外加 `contentDescription`。
- 勾选态要用 `DropdownMenuItem` 的选中/勾选语义，而不是在文字后面拼一个「✓」。

## 常见错误

1. 把「设置」「关于」这类导航入口塞进菜单——那是抽屉或页面的职责。
2. 菜单项太多（10 项以上），用户要点开才能知道有没有自己要的。
3. 展开菜单不关闭：`onClick` 里忘了 `expanded = false`。
4. 破坏性项放在第一项，手指一滑就误删。
5. 用 `DropdownMenu` 做长列表选择器（比如选国家），应该用搜索或独立页面。
6. `ExposedDropdownMenuBox` 里输入框没设 `readOnly`，用户能输入却选不到匹配项。

## 来源

- https://m3.material.io/components/menus
- `02-Components/lists.md`（列表与菜单的边界）
- `02-Components/expressive-components.md`（子菜单与分组能力）
