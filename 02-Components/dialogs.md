# Dialogs / 对话框

- 官方指南：https://m3.material.io/components/dialogs ｜ [Specs](https://m3.material.io/components/dialogs/specs) ｜ [Guidelines](https://m3.material.io/components/dialogs/guidelines) ｜ [Accessibility](https://m3.material.io/components/dialogs/accessibility)
- Compose API：`AlertDialog`、`BasicAlertDialog`、`Dialog`（androidx.compose.material3 / androidx.compose.ui.window）
- 核验日期：2026-11-16 ｜ 依据：官网四页全文（Overview / Specs / Guidelines / Accessibility）
- 状态：尺寸与颜色角色均为 Specs 页原值；凡"颜色角色 → 元素"的对应关系，官网只按图示顺序平铺，推断处已标 ⚠️

## 用途与边界

> "A dialog is a modal window that appears in front of app content to provide critical information or ask for a decision. Dialogs disable all app functionality when they appear, and remain on screen until confirmed, dismissed, or a required action has been taken."

两个变体：**Basic dialog** 与 **Full-screen dialog**。对话框只做一件事，可以顺带展示与该任务相关的信息，最常见用途是确认高风险操作（如删除进度）。

| 组件 | 重要性 | 是否需要用户动作 |
|---|---|---|
| Snackbar | 低 | 可选：可以没有按钮，会自动消失 |
| Dialog | 高 | 必须：阻塞主内容直到确认 |

> "Don't use dialogs for low- or medium-priority information. Instead use a snackbar, which can be dismissed or disappear automatically."

**可执行规则**

- 操作成功提示、可撤销的删除、网络错误提示，都不要用对话框：分别用 Snackbar、Snackbar + 撤销、内联错误态。
- 需要用户读一段话再决定（条款、不可逆删除、权限解释）才用对话框。
- 对话框打开即阻断全部应用功能，所以"顺手提示一下"不是理由。

## 规格（Specs 原值）

### Basic dialog

| 属性 | 值 |
|---|---|
| 容器圆角 | **28dp** |
| 容器高度 | 动态 |
| 容器宽度 | **最小 280dp；最大 560dp** |
| Divider 高度 | **1dp** |
| Icon 尺寸 | **24dp** |
| 有图标时对齐 | 居中（Center-aligned） |
| 无图标时对齐 | 起始（Start-aligned） |
| 上 / 左 / 右 / 下内边距 | **24dp** |
| 按钮之间的间距 | **8dp** |
| 标题与正文间距 | **16dp** |
| 图标与标题间距 | **16dp** |
| 正文与操作区间距 | **24dp** |

最小 280dp 与最大 560dp 同时是**硬约束**，不是"建议值"。

### Full-screen dialog

| 属性 | 值 |
|---|---|
| 容器圆角 | **0dp** |
| 容器高度 | 动态 |
| 容器宽度 | 容器宽度；**最大 560dp** |
| Header 高度 | **56dp** |
| Header 宽度 | 容器宽度 |
| 标题对齐 | 起始 |
| Divider 高度 | **1dp** |
| 关闭图标尺寸 | **24dp** |
| 底部操作栏高度 | **56dp** |
| 底部操作栏宽度 | 容器宽度 |
| 上 / 左 / 右内边距 | **24dp** |
| 元素之间的间距 | **8dp** |

> "Full-screen dialogs are for compact breakpoints only, like mobile devices. For medium and expanded breakpoints, use a basic dialog."

## 颜色角色

Specs 页只给角色清单，不逐项标注到元素。下表按 Anatomy 顺序对齐，映射关系为 ⚠️ 推断（Divider 未出现在清单中）。

### Basic dialog

| 元素 | 颜色角色 |
|---|---|
| Container | **Surface container high** |
| Icon（可选） | **Secondary** |
| Headline（可选） | **On surface** |
| Supporting text | **On surface variant** |
| Divider（可选） | Specs 未给出 |
| Button label text | **Primary** |
| Scrim | **Scrim** |

### Full-screen dialog

官网共列 5 个角色：`Surface container high`、`On surface`、`On surface`、`Primary`、`On surface variant`，对应容器、Header、关闭图标、标题、文字按钮、Divider 六类元素，但未逐项绑定。

- 容器确定为 **Surface container high**，文字按钮确定为 **Primary**。
- 其余 `On surface` / `On surface variant` 用于关闭图标、标题与分隔线（⚠️ 推断，按顺序无法唯一确定哪一项对应哪一个）。Full-screen dialog 没有 scrim。

> "To focus attention on the dialog, surfaces behind the container are scrimmed with a temporary overlay to make them less prominent."

## 使用规则

### 标题

> "Headlines should: Contain a brief, clear statement or question; Avoid apologies ("Sorry for the interruption"), alarm ("Warning!"), or ambiguity ("Are you sure?")."

- 标题写具体问题或结论：「删除这条笔记？」而不是「提示」「确认」「警告！」；可以折到第二行，超出则截断。
- Full-screen dialog 的长标题不要塞进 app bar，放进内容区：

> "Avoid placing long headlines in a full-screen dialog's app bar (1), as the truncated text may lead to misunderstanding."

### 按钮

> "Dialog actions are most often represented as buttons and allow users to confirm, dismiss, or acknowledge something. Buttons are aligned to the trailing edge of the dialog for easier interaction. The confirmation button is always closest to the edge."

| 规则 | 官方原文 |
|---|---|
| 最多两个操作 | "Dialogs should contain a maximum of two actions." |
| 只有一个操作时 | "If a single action is provided, it must be an acknowledgement action" |
| 两个操作时 | "one must be a confirming action, and the other a dismissing action" |
| 确认键可禁用 | "Disable confirming actions (1) until a choice is made. Dismissive actions are never disabled." |
| 否定操作在左 | "Don't place dismissive actions (1) to the right of confirming actions." |
| 竖排时确认在上 | "Stacked buttons accommodate longer button text, but take up more room. Confirming actions appear above dismissive actions." |
| 不要第三个操作 | "Providing a third action, such as Learn more, is not recommended as it navigates the user away from the dialog, leaving the dialog task unfinished." |

需要更多信息时用**内联展开**（inline expansion），或在进入对话框之前就给足信息，不要加「了解更多」把用户带走。

### Basic dialog 的行为

- 必须有人做出动作才会关闭；常用于 alert、快速选择、确认，也可承载列表、日期/时间选择器。
- 内容尽量不滚动；必须滚动时，**标题固定在顶部、按钮固定在底部**，只有中间内容滚动：

> "Most dialog content should avoid scrolling. Even when scrolling is required, the dialog title is pinned at the top, with buttons pinned at the bottom."

- 对话框不与背景一起滚动（"Dialogs don't scroll with elements outside of the dialog, such as the background."）。

### Full-screen dialog 的行为

| 场景 | 规则 |
|---|---|
| 何时用 | 需要键盘输入（表单）、改动不会即时保存、内部组件会再开对话框 |
| 层级 | 全屏是唯一可以被其他对话框叠加的对话框："the only dialogs over which other dialogs can appear" |
| 保存 | 用 Save；关闭图标 / Cancel / Back 只负责关闭，不负责保存 |
| 确认文案 | 用 Send / Create 这类明确动词，避免 Done、OK、Close |
| 确认键 | 不要禁用（与 basic dialog"未选择前禁用确认"的规则相反，按变体执行）；失败时才额外弹一个 basic dialog |
| 放弃改动 | 关闭未保存时必须用 basic dialog 二次确认 |
| 导航 | app bar 里只能有关闭「X」这一个导航项 |
| 过渡 | 可用 container transform 把 FAB 变成 full-screen dialog |

> "When someone dismisses a full-screen dialog, a basic dialog should appear to confirm that they want to discard the unsaved changes."

字段级错误**内联**显示在字段旁；只有与字段无关的失败（如网络导致保存失败）才在 basic dialog 中呈现，且发生在确认动作失败之后。错误文案要说清原因与修复方式，并一次显示全部错误。

### 自适应与定位

- 默认居中；中等及以上断点可以自定义位置以改善可达性。
- 大屏自定义定位要留 **56dp 边距**，避免贴边碰撞：

> "Basic dialogs can be custom-positioned anywhere on larger screens, respecting margins to prevent edge collision."

- 断点变化时可以换变体：小屏 full-screen，大屏 basic。

## 无障碍

### 焦点

> "When a dialog appears, focus should automatically land on the first interactive element within the dialog."

| 按键 | 行为 |
|---|---|
| Tab | 下一个可交互元素；在最后一个时回到第一个 |
| Shift + Tab | 上一个可交互元素；在第一个时跳到最后一个 |
| Space / Enter | 触发或提交当前聚焦元素 |
| Escape | 关闭对话框 |

- 焦点循环局限在对话框内，不能跑到背景内容；关闭后回到触发元素。Compose 的 `Dialog` / `AlertDialog` 会建立独立窗口与焦点域，但**关闭后归还焦点需要自己用 `FocusRequester` 处理**。

### 读屏语义

> "The accessibility label for a dialog is typically the same as the dialog's title or headline."

> "On web, basic dialogs should have the alert dialog role."

- 标题必须放在 `title` 槽位，读屏才会当成标题；正文别手写 `Text` 冒充标题。
- 对话框内的按钮、输入框按各自组件规范标注。
- 图标只是装饰时 `contentDescription = null`，避免重复朗读。
- 对话框本身就是"打断"，读屏用户成本更高，因此更不该塞非关键信息。

### 200% 字号

> "On Android, headlines should be kept concise enough to fit within four lines after the text size is increased to 200%. If a headline exceeds this limit and gets truncated, provide an alternative way to access the full content in a single tap."

- 200% 字号下标题不超过四行；超了要给"单击查看全文"的替代路径。正文不要用 `bodySmall` 以下字号。

## Compose 对应 API

### Basic dialog

```kotlin
if (showDialog) {
    AlertDialog(
        onDismissRequest = { showDialog = false },
        icon = { Icon(Icons.Outlined.Warning, contentDescription = null) },
        title = { Text("删除这条笔记？") },
        text = { Text("删除后无法恢复。") },
        confirmButton = {
            TextButton(onClick = { onDelete(); showDialog = false }) { Text("删除") }
        },
        dismissButton = {
            TextButton(onClick = { showDialog = false }) { Text("保留") }
        }
    )
}
```

- `AlertDialog` 的宽度约束（280–560dp）与官网一致，无需自己套 `widthIn`。
- 破坏性操作的确认键官方没有要求用 `error` 色；M3 对话框按钮就是 `primary` 文本按钮。想强调风险，靠**文案**（「永久删除」）而不是改颜色（⚠️ 推断：Specs 未提供 error 角色）。
- `BasicAlertDialog` 可完全自定义内容，当前仍标注 `@ExperimentalMaterial3Api`，且需要自行处理内边距、标题语义与焦点。

### Full-screen dialog

material3 没有 `FullScreenDialog` 组件，用 `Dialog` + `usePlatformDefaultWidth = false` 自建：

```kotlin
Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
) {
    Surface(Modifier.widthIn(max = 560.dp).fillMaxWidth().fillMaxHeight()) { // 圆角 0dp
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.height(56.dp).padding(horizontal = 24.dp)) { /* 标题 + 关闭 */ }
            HorizontalDivider(Modifier.height(1.dp))
            Box(Modifier.weight(1f)) { /* 内容 */ }
            Row(Modifier.height(56.dp).padding(horizontal = 24.dp)) { /* 操作 */ }
        }
    }
}
```

- 顶部 header 56dp、底部操作栏 56dp、左右 24dp、圆角 0dp 都来自 Specs 表；关闭未保存内容时再叠一个 `AlertDialog` 做放弃确认，这是官网明确允许的嵌套场景。

## 常见错误

1. 用对话框做"操作成功"提示，用户每步都要点一下关闭。
2. 标题写「提示」「确认」「你确定吗？」，读完不知道在确认什么；按钮写「是 / 否」，脱离上下文看不懂。
3. 放三个操作（确认 / 取消 / 了解更多），"了解更多"把用户带离任务。
4. 把否定操作放在确认操作右边（LTR 下），不符合"确认最靠边缘"的规则。
5. 正文很长仍硬塞进 basic dialog；应改 full-screen dialog 或独立页面。点击遮罩就关闭且内容有未保存输入，误触即丢。
6. 自定义 `Dialog` 忘了 `usePlatformDefaultWidth = false`，在平板上被压成窄条或拉成通栏；full-screen dialog 的 app bar 里放长标题也会被截断丢语义。

## 来源

- https://m3.material.io/components/dialogs/overview
- https://m3.material.io/components/dialogs/specs
- https://m3.material.io/components/dialogs/guidelines
- https://m3.material.io/components/dialogs/accessibility
- 以上四页均于 **2026-11-16** 核验
- 关联：`02-Components/snackbar.md`（低优先级消息）、`01-Foundations/elevation.md`（模态 Dialog 为 Level 3）
