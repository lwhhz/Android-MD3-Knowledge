# 无障碍 / Accessibility

> Android：https://developer.android.com/guide/topics/ui/accessibility
> Compose：https://developer.android.com/develop/ui/compose/accessibility
> 核验日期：2026-11-14

## 四条硬指标

| 项 | 要求 |
|---|---|
| 触控目标 | 最小 48×48dp，相邻目标至少 8dp 间隔 |
| 文字对比度 | 4.5:1（正文）/ 3:1（≥18pt 或 ≥14pt bold） |
| 图形对比度 | 3:1（图标、边框、图形元素） |
| 文本缩放 | 系统字体 200% 下不截断、不重叠、可滚动 |

## 语义

屏幕阅读器读到的内容与视觉呈现是两套信息。常见缺口：只有图标没有描述、装饰性图片读出无意义文本、自定义绘制内容完全没有语义。

```kotlin
IconButton(onClick = onDelete) {
    Icon(Icons.Default.Delete, contentDescription = "删除这条笔记")
}

Image(painter = painter, contentDescription = null)

Row(modifier = Modifier.semantics(mergeDescendants = true) { }) { /* 标题 + 副标题 */ }

Switch(
    checked = checked,
    onCheckedChange = onToggle,
    modifier = Modifier.semantics {
        stateDescription = if (checked) "已开启" else "已关闭"
    }
)
```

规则：

- `contentDescription` 描述**结果**（「删除这条笔记」），不是控件名（「垃圾桶图标」）。
- 纯装饰元素设 `null`，不要用空字符串凑数。
- 同一条信息被拆成多个 `Text` 时用 `mergeDescendants` 合并，否则 TalkBack 断成碎片。
- 自定义 `Canvas` 绘制的内容必须补 `semantics`，否则完全不可访问。

## 焦点顺序

- 默认按组合顺序朗读。视觉顺序与代码顺序不一致时，**优先改代码顺序**，`traversalIndex` 只作兜底。
- 弹层（Dialog / BottomSheet）出现时焦点必须进入弹层，关闭后回到触发元素。
- 不要用 `focusable` 把不可交互的容器塞进焦点链。

## 颜色不是唯一载体

| 状态 | 除颜色外必须还有 |
|---|---|
| 错误 | 图标 + 文案 |
| 必填 | 文字标注（不要只加星号） |
| 选中 | 形状变化 / 勾选标记 |
| 图表数据系列 | 图案或直接标注 |

色盲用户看不到「红变绿」，但看得到图标和文字。

## 字体缩放

- 全部文字用 `sp`。
- 固定高度容器（`height(48.dp)`）是大字号截断的主因，改用 `heightIn(min = 48.dp)`。
- 按钮文字在 200% 时要允许换行或改用扩展宽度，不要用 `maxLines = 1` 硬截。
- 单个 `Icon` 不要跟着字号无限放大，图标尺寸应单独约束。

## 键盘与遥控

- 平板 / 折叠屏 / TV 场景下，所有可交互元素要能被 Tab / D-pad 到达。
- 焦点态必须有可见视觉指示，不能只依赖系统默认。
- 自定义 `clickable` 会丢失按钮语义，优先用 `Button` / `IconButton` 等语义完整的组件。

## 检查项

- [ ] 所有图标按钮有 `contentDescription`
- [ ] 装饰性图片 `contentDescription = null`
- [ ] 触控目标 ≥ 48dp，贴边目标留出边距
- [ ] 浅色 / 深色下对比度均达标
- [ ] 系统字体 200% 下无截断
- [ ] 弹层焦点能进能出
- [ ] 状态不用颜色单独表达
- [ ] 自定义绘制内容有语义描述
