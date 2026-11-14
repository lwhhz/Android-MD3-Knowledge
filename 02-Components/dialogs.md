# Dialogs / 对话框

- 官方指南：https://m3.material.io/components/dialogs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14 ｜ 状态：`AlertDialog` / `BasicAlertDialog` 为当前 API；具体宽度上限等尺寸请复核官网

## 用途

对话框**打断用户**，所以只在两个条件下用：

1. 必须现在决定，跳过会导致不可逆后果；
2. 信息本身需要用户完整阅读后才能继续。

不满足这两条，用 Snackbar、内联提示或新页面。**「顺便告知一下」不是对话框的用途。**

## 什么时候不该用

| 场景 | 更好的做法 |
|---|---|
| 操作成功提示 | Snackbar |
| 可撤销的删除 | Snackbar + 撤销 |
| 表单输入 | 新页面 / Bottom Sheet |
| 一次性说明、引导 | 内联卡片或首次进入的空态提示 |
| 网络错误 | 内联错误态 + 重试按钮 |

## 变体

| Compose | 说明 |
|---|---|
| `AlertDialog` | 标准三段式：标题 + 正文 + 按钮，覆盖绝大多数场景 |
| `BasicAlertDialog` | 完全自定义内容，需自己处理布局与语义，慎用 |

`AlertDialog` 参数：`onDismissRequest`、`confirmButton`、`dismissButton`、`title`、`text`、`icon`。

## 结构

```
┌──────────────────────────┐
│  [icon]  标题             │  Headline Small
│  正文说明，一到两句         │  Body Medium, onSurfaceVariant
│                          │
│           [取消] [确认]    │  右对齐，TextButton
└──────────────────────────┘
```

- 宽度：最小 280dp，上限建议 560dp（官网取值需复核）。
- 形状：`extraLarge`（28dp）。
- 按钮右对齐，**主操作在最右**。
- 遮罩：`scrim`，点击遮罩是否关闭由你决定——有未保存内容时不要让点遮罩关闭。

## 文案规则

- **标题是问句或短陈述**：「删除这条笔记？」而不是「提示」。
- **正文说明后果**：「删除后无法恢复。」——一句话。不要在这里解释产品逻辑。
- **按钮用具体动词**：「删除」「保留」，不要「确定」「取消」。用户扫一眼按钮就知道结果。
- 破坏性操作的确认按钮用 `error` 色。

## Compose 实现

```kotlin
if (showDialog) {
    AlertDialog(
        onDismissRequest = { showDialog = false },
        icon = { Icon(Icons.Outlined.Warning, contentDescription = null) },
        title = { Text("删除这条笔记？") },
        text = { Text("删除后无法恢复。") },
        confirmButton = {
            TextButton(
                onClick = {
                    onDelete()
                    showDialog = false
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) { Text("删除") }
        },
        dismissButton = {
            TextButton(onClick = { showDialog = false }) { Text("保留") }
        }
    )
}
```

带校验的自定义内容用 `BasicAlertDialog`，但要自己负责：焦点进入、语义分组、尺寸约束。

## 无障碍

- 对话框出现时**焦点必须进入对话框**，关闭后回到触发元素。`AlertDialog` 默认处理，自定义的 `Dialog` 需要自己确认。
- 按返回键应等同于「取消 / 关闭」，不能直接退出页面。
- 标题用 `title` 槽位而不是手写 `Text`，否则读屏不会读成标题。
- 正文不要用 `Body Small` 以下字号——对话框是必须读的内容。
- 图标在标题旁时 `contentDescription = null`，避免重复朗读。

## 常见错误

1. 用对话框做「操作成功」提示，用户每步都要点一下关闭。
2. 标题写「提示」「确认」，用户读完不知道在确认什么。
3. 按钮写「是 / 否」，脱离上下文就看不懂。
4. 对话框里再弹对话框（嵌套确认），用户会直接退出应用。
5. 内容很长（超过 3 行）仍用对话框，应该改成独立页面。
6. 点击遮罩就关闭，用户误触后输入内容全丢。
7. 自定义 `Dialog` 忘了 `usePlatformDefaultWidth = false` 或宽度约束，在平板上拉成一条横贯全屏的白条。

## 来源

- https://m3.material.io/components/dialogs
- `02-Components/buttons.md`（按钮层级：对话框内用 `TextButton`）
- `01-Foundations/elevation.md`（模态 Dialog 为 Level 3 / 6dp，**不是** Level 5）
