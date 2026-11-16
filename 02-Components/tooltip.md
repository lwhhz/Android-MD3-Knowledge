# Tooltip / 提示气泡

- 官方指南：https://m3.material.io/components/tooltips ｜ [Specs](https://m3.material.io/components/tooltips/specs) ｜ [Guidelines](https://m3.material.io/components/tooltips/guidelines) ｜ [Accessibility](https://m3.material.io/components/tooltips/accessibility)
- Compose API：`TooltipBox`、`PlainTooltip`、`RichTooltip`、`rememberTooltipState`（androidx.compose.material3）
- 核验日期：2026-11-16 ｜ 依据：官网四页全文
- 状态：尺寸与颜色为 Specs 原值；圆角官网未给数字

## 用途与边界

> "A tooltip provides additional context for a UI element."

| 类型 | 内容 | 典型用途 |
|---|---|---|
| **Plain** | 一行短文字 | 给**无文字**的图标按钮/字段补标签 |
| **Rich** | 副标题 + 正文 + 最多两个按钮 + 可选链接 | 解释定义、说明新功能、提供附加动作 |

> "Plain tooltips briefly describe a UI element. They're best used for labelling UI elements with no text, like icon-only buttons and fields."
> "Rich tooltips are best used for longer text like definitions or explanations."

**关键区别**：plain 回答"这是什么"；rich 回答"为什么 + 你能做什么"。

### Do / Don't

| 官方 Do | 官方 Don't |
|---|---|
| 用 plain tooltip 给纯图标按钮做标签 | 元素本身已有文字标签时还加 plain tooltip |
| 用 rich tooltip 补充信息与动作、介绍新功能 | 把关键信息藏在 tooltip 里 |

> "Plain tooltips aren't needed when the UI element already has label text"
> "Don't hide critical information within tooltips as it's easy to miss. Use an interruptive dialog instead."

**可执行规则**：如果某条信息用户不知道就会做错事，它不能只出现在 tooltip 里——触摸端要长按才出现，读屏用户通常只拿到 `contentDescription`。

## 规格（Specs 原值）

### Plain tooltip

| 属性 | 值 |
|---|---|
| 容器高度 | **24dp** |
| 内边距 | **8dp** |

24dp 是一个非常矮的单行气泡。它没有给第二行留任何空间——**plain tooltip 不该换行**。

### Rich tooltip

| 属性 | 值 |
|---|---|
| 上内边距 | **12dp** |
| 下内边距 | **8dp** |
| 左右内边距 | **16dp** |
| 按钮数量上限 | **2 个** |

> "Rich tooltips can have a headline, body, and up to two buttons. The headline and number of buttons can be configured."

配置组合（Specs 页列出五种）：副标题 + 正文 + 两个按钮、副标题 + 正文 + 一个按钮、副标题 + 正文、正文 + 一个按钮、正文 + 两个按钮。

### 官网缺数字的部分

- **圆角**：四页都没有给数值。Overview 只说 "Rich tooltips have more rounded corners"（相对 M2），没有 dp。
- 最大/最小宽度、正文行数上限：均未给。
- Compose 侧的内边距与圆角由 `PlainTooltip` / `RichTooltip` 默认值实现，以当前版本源码为准。

## 颜色角色

### Plain tooltip

| 元素 | 颜色角色 |
|---|---|
| Container | **Inverse surface** |
| Supporting text | **Inverse on surface** |

与 snackbar 同一套反色逻辑：浅色主题下是深色气泡。

### Rich tooltip

| 元素 | 颜色角色 |
|---|---|
| Container | **Surface container** |
| Subhead | **On surface variant** |
| Supporting text | **On surface variant** |
| Text button | **Primary** |

注意两者不同：plain 用 `inverseSurface`，rich 用 `surfaceContainer`。rich 是"卡片式"补充信息，不需要反色抢眼；plain 是瞬时标签，必须从任何背景上跳出来。

> "Don't use a persistent rich tooltip on icon buttons"（样式与行为都不同，见下节）

## 使用规则

### 触发

> "To show a tooltip, hover on the parent element on desktop, or tap and hold the element on mobile. Persistent rich tooltips only appear when clicked or tapped."

| 类型 | 桌面 | 移动 | 备注 |
|---|---|---|---|
| Plain | 悬停 | 长按 | 键盘聚焦也可触发 |
| Rich（普通） | 悬停 / 聚焦 | 长按 | 选中元素也可触发 |
| Rich（persistent） | 点击 | 点击 / 页面加载 | 悬停**不**触发 |

> "Tooltips can appear when an actionable element, like a button or navigation rail, is hovered or focused."
> "Rich tooltips can also appear by selecting an element instead of hovering or focusing on it."

### 消失与互斥

> "Both plain and rich tooltips disappear 1.5 seconds after navigating away from the target region."
> "Triggering a new tooltip immediately closes any other open tooltip."

- 离开目标区域后 **1.5 秒**消失（不是立刻），给用户回看的时间。
- 同一时刻只显示一个 tooltip；新的立刻关掉旧的。
- Persistent rich tooltip 是例外：离开目标区不消失，直到用户操作其他 UI 元素才关。

> "Persistent rich tooltips remain active even when leaving the target region. They only disappear once a person interacts with another UI element. Hovering doesn't trigger the tooltip."

- 不要在图标按钮上用 persistent rich tooltip（会长期遮挡界面）。
- Persistent rich 常用于"页面加载时介绍新功能"，此时**应包含副标题**：

> "Subheads are important to include when the rich tooltip appears automatically, like when the page loads."

### 位置

| 类型 | 默认位置 | 间距 |
|---|---|---|
| Plain | 正上方 | 有视觉边界（按钮）**4dp**；无边界（文字基线）**8dp** |
| Plain（在 app bar 内） | 元素下方 | 同样 4dp / 8dp |
| Rich | 元素右下方 | 以 **8dp** 为步进避让屏幕边缘 |
| Rich（桌面） | 可居中显示在元素下方 | 在目标区域内移动时保持可见 |

> "By default, plain tooltips are positioned directly above the parent element."
> "The position of the tooltip adjusts in increments of 8dp to avoid going off-screen."
> "Tooltips shouldn't cover the parent element."

### 文案

| 规则 | 说明 |
|---|---|
| Plain 只写一句 | 不换行、不堆信息 |
| Rich 副标题一行 | "Keep subheads brief, ideally to one line." |
| 按钮简短并排 | "Keep buttons short so they can be side by side. Avoid stacking them when possible." |
| 按钮最多两个 | 超过两个说明该用 dialog / bottom sheet |

## 无障碍

### 焦点

> "Tooltip containers should not block important information or prevent a person from completing an action."
> "Avoid trapping screen reader and keyboard focus on rich tooltips."
> "People should be able to move linearly through the rest of the page."

- Rich tooltip 内部按**从上到下**的顺序在可交互元素间移动。
- 不困住焦点：用户必须能继续线性浏览页面其余部分。
- Plain tooltip 不可交互，不应进入 Tab 序。

| 按键 | 行为 |
|---|---|
| Tab | 焦点落在按钮（如果有） |
| Space / Enter | 激活当前聚焦元素 |

### 读屏语义

> "Tooltips should have the Tooltip role, or similar."
> "Label all elements in the tooltip according to their own accessibility guidance."

- 容器角色是 **Tooltip**。
- 内部按钮、链接按各自规范标注。
- Plain tooltip 的文字如果是图标按钮的唯一说明，**图标按钮的 `contentDescription` 必须自包含**，不能依赖气泡内容；读屏用户不一定能触发气泡（⚠️ 推断：官方未直接这么写，但 "receive a tooltip message / activate with keyboard" 是 use case，不能假设气泡必被朗读）。

### 时长

> "Plain and rich tooltips without required actions should remain on screen long enough for people to receive the information without disrupting their existing flow or task."

> "Plain tooltips should remain on the screen temporarily after the cursor moves away"

- 1.5 秒的延迟消失就是给读屏/低视力用户留的缓冲，不要改成 0。
- 无必需动作的 tooltip 不能挡住用户完成当前任务。
- 有必需动作时不要用 tooltip 承载，改用 rich tooltip 的按钮之外的明确入口，或 dialog。

## Compose 对应 API

### Plain

```kotlin
TooltipBox(
    positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
    tooltip = { PlainTooltip { Text("添加到收藏") } },
    state = rememberTooltipState()
) {
    IconButton(onClick = onFav) {
        Icon(Icons.Outlined.BookmarkBorder, contentDescription = "收藏")
    }
}
```

### Rich

```kotlin
TooltipBox(
    positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
    tooltip = {
        RichTooltip(
            title = { Text("同步设置") },
            action = { TextButton(onClick = onOpen) { Text("查看详情") } }
        ) {
            Text("关闭后仅在 Wi-Fi 下同步数据。")
        }
    },
    state = rememberTooltipState()
) {
    IconButton(onClick = {}) {
        Icon(Icons.Outlined.Info, contentDescription = "同步说明")
    }
}
```

| API | 说明 |
|---|---|
| `TooltipBox` | 触发区 + 气泡的组合容器，多个 material3 版本中为 `@ExperimentalMaterial3Api` |
| `rememberTooltipState(isPersistent = true)` | 对应 persistent rich tooltip；`isPersistent` 存在时点击触发、离开不消失 |
| `TooltipDefaults.rememberPlainTooltipPositionProvider()` | 默认上方，间距按官方 4dp/8dp 逻辑 |
| `TooltipDefaults.rememberRichTooltipPositionProvider()` | 默认右下方，按 8dp 步进避让 |
| `PlainTooltip` / `RichTooltip` | 已内置 8dp / 12-8-16dp 内边距与配色 |

- `TooltipBox` 生成的语义节点带 tooltip 角色；不要在外层再套一个可点击语义，否则读屏会重复。
- 键盘聚焦触发需要触发元素本身可聚焦（`IconButton` 默认可以）。
- 若同一屏有多个 tooltip，`TooltipBox` 各自独立，**不会自动互斥**（⚠️ 推断：官方要求一次只显示一个，Compose 需自行保证）。

## 常见错误

1. 把关键信息只放在 tooltip 里（触摸端等于没写，读屏多半拿不到）。
2. Plain tooltip 写成一整段话——它只有 24dp 高、单行。
3. Rich tooltip 放三个按钮（上限两个）。
4. Rich tooltip 的按钮竖排堆叠（应尽量并排）。
5. 用 tooltip 做错误提示（应该用字段下方的 supporting text）。
6. 在纯图标按钮上用 persistent rich tooltip，长期遮挡界面。
7. 气泡挡住被说明的控件本身。
8. 深色主题下自定义容器色，破坏反色约定。
9. 把 1.5 秒延迟改成 0，读屏用户来不及读。
10. 同屏多个 tooltip 同时打开。
11. 页面加载就自动弹 rich tooltip 却没写副标题，用户不知道在说什么。
12. 认为 rich tooltip 的触发只有点击（悬停/聚焦/选中都可以）。

## 来源

- https://m3.material.io/components/tooltips/overview
- https://m3.material.io/components/tooltips/specs
- https://m3.material.io/components/tooltips/guidelines
- https://m3.material.io/components/tooltips/accessibility
- 以上四页均于 **2026-11-16** 核验
- 关联：`01-Foundations/color-system.md`（inverse 角色）、`01-Foundations/accessibility.md`（contentDescription 自包含）
