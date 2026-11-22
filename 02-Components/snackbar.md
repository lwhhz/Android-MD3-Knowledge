# Snackbar / 轻提示

- 官方指南：https://m3.material.io/components/snackbar ｜ [Specs](https://m3.material.io/components/snackbar/specs) ｜ [Guidelines](https://m3.material.io/components/snackbar/guidelines) ｜ [Accessibility](https://m3.material.io/components/snackbar/accessibility)
- Compose API：`SnackbarHost`、`SnackbarHostState`、`Snackbar`、`SnackbarDuration`（androidx.compose.material3）
- 核验日期：2026-11-16 ｜ 依据：官网四页全文
- 状态：颜色角色为 Specs 原值；**Specs 页的 Measurements 只有图、没有数字表**，下文 dp 值均来自 Guidelines 正文

## 用途与边界

> "Snackbars inform users of a process that an app has performed or will perform. They appear temporarily, towards the bottom of the screen."

> "They shouldn't interrupt the user experience. People can browse the page content without being required to interact with the snackbar."

Snackbar 只报告进程状态，不能让用户做决策。它和对话框的分工是官方的"消息策略"：

| 组件 | 优先级 | 是否需要用户动作 |
|---|---|---|
| **Snackbar** | 低 | 可选：可以没有按钮，会自动消失 |
| **Dialog** | 高 | 必须：阻塞应用直到用户处理或退出 |

> "Snackbars communicate messages that are minimally interruptive and don't require user action."

**判断标准**：用户不看这条消息也不会做错事，用 snackbar；要用户先做选择才能继续，用 dialog。

## 结构

```
┌──────────────────────────────────────┐
│  文案                [动作]     [✕]  │
└──────────────────────────────────────┘
   ①Supporting text   ②Action  ③Close
   Container 承载全部
```

| 元素 | 必需 | 规则 |
|---|---|---|
| Container | ✅ | 矩形、完全 opaque 的灰色底，靠阴影与内容区分 |
| Supporting text | ✅ | 简短描述已发生/将发生的进程 |
| Action | 可选 | **最多一个** text button |
| Close button | 可选 | close affordance，不是"取消"动作 |

## 规格

### Specs 页只有图

> "Snackbar padding and size measurements"

Specs 页这一节只放了一张测量图，**没有任何数字**。能确定的只有配置形态：单行、单行带动作、双行、双行带动作、双行带较长动作。

因此以下数值**全部来自 Guidelines**，不是 Specs 表：

| 属性 | 值 | 出处 |
|---|---|---|
| Compact 单行高度 | **48dp** | Guidelines：从 48dp 展开到 64dp |
| Compact 双行高度 | **64dp** | 同上 |
| 与屏幕 leading / trailing / bottom 的距离 | 固定 | 原文只说 "maintaining a fixed distance"，未给具体值 |
| 圆角 | 未给 | Specs 与 Guidelines 均无数字（⚠️ 不要照抄其他组件的 4dp/28dp） |
| 左右内边距 | 未给 | 只能看图或 Design Kit |
| 自动消失时长 | **4–10 秒** | "can auto-dismiss after 4–10 seconds, depending on platform" |
| 中等/大屏文本行长 | 约 **40–60 字符** | "the ideal line length for text is typically between 40-60 characters" |
| 最大宽度 | 未给 | 只要求"宽布局下横向扩展"，无数值 |

> **Compose 实现差异（2026-11-22 源码核对）**：`SnackbarTokens.SingleLineContainerHeight = 48dp`（与设计一致）；但 `TwoLinesContainerHeight = **68dp**`，而官方 Guidelines 写的是 48 → **64dp**。设计值仍按 64dp 记录；截图/像素回归时按 Compose 实际 68dp 预期。

> "In compact breakpoints, snackbars should expand vertically from 48dp to 64dp to accommodate one or two lines of text, while maintaining a fixed distance from the leading, trailing, and bottom edges of the screen."

## 颜色角色

Specs 页给出 4 个角色，与 Anatomy 顺序一致，可直接采用：

| 元素 | 颜色角色 |
|---|---|
| Container | **Inverse surface** |
| Supporting text | **Inverse on surface** |
| Action（text button） | **Inverse primary** |
| Close button | **Inverse on surface** |

> "Snackbars use a color intended to stand out against UI elements. Use the default color mapping to avoid color conflict issues."

- 整套是 **inverse** 系列，所以浅色主题下是一个深色浮条，深色主题下反过来。
- 文案与动作按钮**不能同色**：

> "The text label shouldn't share the same color as the text button"

- 不要自定义成普通 surface；这组反色就是为了在不加 elevation 的前提下与内容区分。
- 官方 elevation 分级表里没有 snackbar——它靠反色对比而不是阴影高度。

## 使用规则

### 频率与堆叠

> "Only one snackbar may be displayed at a time."
> "Don't stack snackbars on top of one another"

- 同一时刻只能有一条，不叠放、不并排。
- 例外：更新的信息可以**立即替换**旧 snackbar（"a snackbar with updated information can immediately replace an outdated snackbar"）。
- 出现/消失时不要带着其他组件一起动（"Don't animate other components along with snackbar animations, such as the floating action button"）。

### 文案

| 规则 | 说明 |
|---|---|
| 一条一行优先 | "Keep the snackbar text label to one line long when possible" |
| 手机最多两行 | "On mobile, the text label can be up to two lines long" |
| 不加图标 | "Avoid adding icons to snackbars. If your message needs an icon, consider using a different component such as a dialog." |
| 不用富文本/内联链接 | "Avoid using stylized text or inline links in snackbars"；需要链接就加按钮，或换组件 |
| 不承载核心用例 | "Snackbars shouldn't be the only way to access a core use case" |

### 动作

- 只能有**一个** text button；文案要能独立听懂：「撤销」「重试」「查看」，不要「确定」「好的」。
- 长动作可以放到第三行（"If an action is long, it can be displayed on a third line"）；允许用户修正选择时用 **Undo**。
- 关闭动作是多余的（"A dismiss action is unnecessary, as snackbar disappears on their own by default"）。
- 不要用 filled / elevated button（"it draws too much attention"）。

### 出现与消失

> "Snackbars without actions can auto-dismiss after 4–10 seconds, depending on platform."

> "Snackbars with actions should remain on the screen until the user takes an action on the snackbar, or dismisses it."

- **无动作**：4–10 秒自动消失。
- **有动作**：不自动消失，直到用户操作或关闭。这条是无障碍硬要求，见下节。
- Web 上自动消失的 snackbar 对低视力用户不友好，必须同时提供内联反馈，或改成可操作 snackbar：

> "On web, auto-dismissing snackbars are inaccessible for people with low vision or who require additional time to perceive information."

### 位置

> "Snackbars should be placed at the bottom of a UI, in front of the main content."

| 规则 | 说明 |
|---|---|
| 可以上移避让 | 底部有 FAB 或 docked toolbar 时可整体上移 |
| 不挡导航/触控目标 | "Avoid placing snackbars in front of navigation components" |
| 不遮住焦点元素 | Web 键盘用户必须能看见当前焦点 |
| 在 FAB 之上 | "Snackbars should appear above FABs."，且不能放到 FAB 后面 |
| 全宽有条件 | 只有界面没有常驻导航（app bar / navigation bar）时才可整行通栏 |
| 不贴边 | "Don't place snackbars flush to one edge of the layout" |

### 响应式

- Compact：竖向从 48dp 长到 64dp。
- Medium / Expanded：横向扩展以容纳更长文案，尽量保持单行 + 可选按钮；可左对齐或居中，但**同一产品要固定一种**（"if they are consistently placed on the same spot at the bottom of the screen"）。

## 无障碍

这一节是 snackbar 与对话框最不一样的地方：**它不抢焦点**。

### 焦点

> "When a snackbar appears, announce the message but don't move focus."
> "Don't automatically move focus."
> "Don't trap focus in the snackbar. Users should be able to freely navigate in and out."

| 场景 | 规则 |
|---|---|
| 出现 | 只播报，不移动焦点 |
| 停留期间 | 焦点可自由进出，不困在条内 |
| 消失后 | 回到之前的焦点元素；若该元素已不在页面，则去下一个最合理的元素 |
| 键盘 | Tab 在可交互元素间移动；**Esc 在条内聚焦时关闭** |

> "Ideally, focus should either return to the element that triggered the snackbar, or go to the next most logical element on the page."

> "On Android Compose, focus may move to the nearest visible element, or to the first actionable item on the page."

- 所以"snackbar 打开后焦点进入 snackbar"是**错的**；那是 dialog 的规则。
- Compose 的 `SnackbarHost` 默认不会抢焦点，但动作按钮会进入 Tab 序，需要确认读屏顺序自然。

### 播报

> "On Android and web, use a live region with a polite (queued) announcement instead of an assertive announcement."

- 用 **polite**，不要 assertive：snackbar 不该打断读屏正在读的内容。
- 启动时就出现的 snackbar，应在页面标题之后播报，且不获得焦点。
- 内容只播报一次；不要把同一句话再塞进 `contentDescription`。
- 动作按钮的标签要自包含；关闭图标写「关闭」。

### 超时与暂停

- 有动作 → **不自动消失**，用户可按自己的节奏读和操作：

> "Snackbars with actions shouldn't auto-dismiss. This way, users can read and interact with it at their own pace."

- 无动作 → 4–10 秒，且必须留足阅读时间：

> "Auto-dismissing snackbars should remain on screen long enough to read the information"

- 无障碍没有规定"鼠标悬停暂停计时"；官方给的另一条出路是**提供等价的持久信息**（内联反馈或可操作 snackbar）。不要把"悬停暂停"当成合规替代（⚠️ 推断：页面未提及悬停暂停）。

## Compose 对应 API

```kotlin
val snackbarHostState = remember { SnackbarHostState() }

Scaffold(
    snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.navigationBarsPadding()
        )
    }
) { padding -> /* content */ }
```

带撤销的提示：

```kotlin
scope.launch {
    val result = snackbarHostState.showSnackbar(
        message = "已删除 1 条笔记",
        actionLabel = "撤销",
        withDismissAction = true,
        duration = SnackbarDuration.Indefinite   // 有动作 → 不自动消失
    )
    if (result == SnackbarResult.ActionPerformed) restoreNote()
}
```

| 参数 / API | 说明 |
|---|---|
| `showSnackbar()` | 挂起函数，返回 `ActionPerformed` / `Dismissed` |
| `duration = Short / Long` | 分别约 4s / 10s，对应官方 4–10 秒区间 |
| `duration = Indefinite` | 有动作时用它，才符合"有动作不自动消失" |
| `withDismissAction = true` | 对应 Anatomy 里的 Close button |
| `SnackbarHostState.currentSnackbarData` | 可在新消息到来时 `dismiss()` 旧条，实现"新替换旧"而非排队 |

- **常见坑**：`actionLabel` + `duration = Short` 会在几秒后自动消失，直接违反官网无障碍要求。有动作就用 `Indefinite`，并确保用户能通过动作或关闭退出。
- `SnackbarHost` 自身不处理 `navigationBarsPadding`；放在 `Scaffold` 里时不要再叠一层，否则出现双倍间距。
- 队列行为：连续调用 `showSnackbar` 默认是排队而不是替换；需要"最新覆盖"时手动 `dismiss()` 当前条。

## 常见错误

1. 用 snackbar 承载需要用户决策的操作（删除确认、权限选择）——那是 dialog 的职责。
2. 放两个动作按钮（撤销 + 取消），语义冗余且违反"单个动作"；有动作却设 `Short`。
3. 连续触发多条排队，或多条 snackbar 叠放、并排。
4. 放图标、内联链接或富文本，或用 filled / elevated button 做动作。
5. 挡住底部导航、FAB 或当前焦点元素。
6. 大屏上拉满整屏宽，或贴着屏幕某一边。
7. 认为 snackbar 出现时焦点会进入它——实际上绝不能抢焦点。
8. 用 assertive 播报打断读屏。
9. 把"操作成功"做成 snackbar + 「好的」按钮，多此一举。

## 来源

- https://m3.material.io/components/snackbar/overview
- https://m3.material.io/components/snackbar/specs
- https://m3.material.io/components/snackbar/guidelines
- https://m3.material.io/components/snackbar/accessibility
- 以上四页均于 **2026-11-16** 核验
- 关联：`02-Components/dialogs.md`（高优先级消息）、`01-Foundations/color-system.md`（inverse 角色）
