# Toolbars / 工具栏

- 官方指南：https://m3.material.io/components/toolbars ｜ 规格页：https://m3.material.io/components/toolbars/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**M3 Expressive 新增组件**；顶部 app bar 见 `02-Components/app-bars.md`

## 先分清：toolbar 与 app bar 是两个组件

| 维度 | App bar（顶部） | Toolbar（底部 / 悬浮） |
|---|---|---|
| 位置 | 屏幕顶部 | 底部贴边（docked）或悬浮于内容上（floating） |
| 职责 | 页面标题 + 导航按钮 + 1–2 个核心动作 | 当前页的一组频繁操作 |
| 动作数量 | 1 个，最多 2 个 | 可容纳很多，放不下进溢出菜单 |
| 典型内容 | 返回 / 菜单、标题、副标题、头像、1–2 个图标按钮 | 图标按钮、按钮、文本框、选择控件 |
| 官方原文 | "App bars are placed at the top of the screen to help people navigate through a product." | "Toolbars display frequently used actions relevant to the current page" |

官方在 app bar 概览里直接写明了分工：**"Displays labels and page navigation controls at the top of the page. (Use a toolbar to display page actions)"**。所以「顶部标题栏」不要叫 toolbar，「底部操作条」也不要塞标题。

> ⚠️ 旧版本知识需要纠正：`Toolbar` 不是「M3 里不存在的东西」，官方 M3 Expressive 有独立的 toolbars 组件页（docked / floating）；但它指的是底部与悬浮工具栏，不是 `androidx.appcompat.widget.Toolbar` 那个 View 组件，也不是顶部的 `TopAppBar`。

## 变体与代际

| 变体 | M3 | M3 Expressive | 说明 |
|---|---|---|---|
| Docked toolbar | — | ✅ | 横向铺满窗口宽度，固定在底部 |
| Floating toolbar（horizontal） | — | ✅ | 悬浮于内容之上，默认横向 |
| Floating toolbar（vertical） | — | ✅ | 大断点可用，贴屏幕左右侧 |
| Bottom app bar（baseline） | ✅ | ❌ 不再推荐 | 用 docked toolbar 替代 |

官方原文：**"The bottom app bar is no longer recommended and should be replaced with the docked toolbar, which functions similarly, but is shorter and has more flexibility."**

M2 对比（官方列出）：M2 底栏有 8dp elevation、不含 FAB；M3 底栏换了颜色、容器更高、无阴影、FAB 收进容器内。

## 规格

### 通用尺寸

| 属性 | 值 | 依据 |
|---|---|---|
| 容器高度 | **64dp** | specs 原文：默认所有 toolbar 高 64dp |
| 垂直对齐 | 居中 | 同上 |
| 项目间距 | **8dp**（specs 图注）/ **32dp**（guidelines 称"只是默认值"） | 两处口径不一致，见下 |
| 外侧最小内边距 | **16dp**（leading / trailing 边） | specs 与 guidelines 一致 |
| 元素最小触控目标 | **48×48dp** | guidelines："All elements need a minimum 48x48dp target area to be accessible." |
| 圆角 | docked 默认直角；web / 大屏可圆角 | guidelines |

> "By default all toolbars are 64dp high, center-aligned, have equal padding between items, and have a minimum outside padding of 16dp."

> "As long as there's a minimum of 16dp padding on the leading and trailing edge, arrange controls inside however you see fit. The 32dp padding between items is just the default."

**⚠️ 官方两页对「项目间距」给出的数字不同（8dp / 32dp）**。可执行结论：外侧 16dp 是硬约束；内部间距按布局自定，等距即可，不要死记某一个值。

### Docked toolbar

| 属性 | 值 |
|---|---|
| 宽度 | 100% 屏幕宽度 |
| 位置 | 仅窗口底部 |
| 圆角 | 直角（web / 大屏例外，可圆角） |
| 布局选项 | 全部居中；或中间放主操作、两侧贴边 |

### Floating toolbar

| 属性 | 横向 | 纵向 |
|---|---|---|
| 距窗口边缘最小外边距 | **16dp** | **24dp** |
| 容器宽度 | 恰好包住内容为止（不额外留空） | 仅窄/默认图标按钮，别用 wide |
| 溢出 | 放不下时收进 trailing 溢出菜单 | 同上 |

官方原文：**"The container should only be as big as needed to hold the items inside before reaching the 16dp margin."**

> "Floating toolbars shouldn't exceed the edge of the window or pane"（Don't）

### 高度数字的边界

dump 中 toolbars specs 的浮层测量、baseline bottom app bar 测量均以**图片**呈现，文本只明示了 64dp 与 16dp。旧文档里「BottomAppBar 高度 80dp」**在本轮 dump 中找不到文本依据**，标注为 ⚠️ 推断，不要当作官方值引用。

## 颜色角色

| 配置 | 容器 | 内部按钮配色 |
|---|---|---|
| **Standard**（默认） | `surface container` | Filled button：`primary` / `on primary`；Toggle tonal：`secondary container` / `on secondary container`；Standard button：`primary` |
| **Vibrant** | `primary container` | Filled button：`primary` / `on primary`；Toggle tonal：`surface container` / `on surface`；Standard button：`on primary container` |

- Standard 是低强调，用于「把注意力留给正文」；Vibrant 是高强调，也可表示临时模式切换（如进入编辑态）。
- 状态 token 组：Enabled / Disabled / Hovered / Focused / Pressed（数值需查 token 面板，dump 文本未展开）。
- 可用其他颜色角色做更强/更弱的强调，但**一次只强调一个动作**。

> "Use the standard color scheme to draw focus to content outside the toolbar"
> "Use the vibrant color scheme to emphasize controls or actions"

## 使用规则（Guidelines → 可执行条款）

### 选型

1. 操作**跨多个页面都一样**（全局）→ docked toolbar。
2. 操作**只与当前页或当前选中内容有关**（上下文）→ floating toolbar。
3. 操作放不下 → 加溢出菜单，不要硬塞。
4. floating toolbar 可作为**相关子页面之间的局部 tab**，但要与全局导航保持区分，避免同一视图里出现两套互相打架的导航。

### 与导航栏互斥

- toolbar 与 navigation bar 都在窗口底部，**不要同时出现**。
- 主页面用 navigation bar；有操作的次级页面用 toolbar。
- ✅ 保持导航层级清晰，局部导航用 toolbar 表达。
- ❌ 同一屏同时出现「导航栏 + 带导航控件的 toolbar」。

> "The toolbar and navigation bar are both placed at the bottom of the window, so should not be shown at the same time. Show the navigation bar on primary pages, and toolbars on subsequent pages with actions."

### 容器形状

- docked toolbar **铺满宽度、直角**。官方理由是圆角会暗示容器可展开或随交互变化。
- ❌ 不要修改 docked 容器形状；❌ 不要给 docked toolbar 加圆角（web / 大屏除外，官方明确允许圆角）。
- floating toolbar 完全可见，不许超出窗口或分栏边界。

### 内容与强调

- 槽位可放按钮、图标按钮、图片、文本框、自定义组件。
- 图标按钮给出一致的层级感；混入**一个** filled 图标按钮可突出主操作。
- 强调单个动作的手段：换按钮色样式（filled / tonal / standard）、改单个动作的颜色角色、用 wide / narrow 图标按钮、配 FAB。
- ❌ 不要同时用多个加粗主色按钮；❌ 不要把按钮和 FAB 同时做成高强调。
- ❌ 不要混太多不同控件类型。
- ❌ **floating toolbar 里不要用方形 filled 图标按钮**（方角与全圆角容器冲突）；docked toolbar 可以用方形。

### 位置与自适应

| 场景 | 规则 |
|---|---|
| docked | 永远贴窗口底部；有 navigation bar 等底部元素时不要用 |
| floating 横向 | 距窗口边缘 ≥ 16dp |
| floating 纵向 | 距窗口边缘 ≥ 24dp；放置在与 navigation rail 相对的一侧 |
| 同时有 nav rail + 纵向 toolbar | nav rail 用居中配置 |
| compact | 元素等距；不要用纵向 toolbar；不要放多个 toolbar |
| medium 及以上 | 可全部居中，或主操作居中、其余贴边；可放大间距 |
| 大屏 / web | docked 可圆角、可放到页面不同位置，可用分割线组织大量项目 |
| 窗口变大 | 更多动作从溢出菜单回到可见区 |

⚠️ 纵向 toolbar 在 compact 窗口会遮挡重要内容（官方 Caution），仅在界面简单或控件很少时使用。

### 滚动行为

- docked：可常驻，也可随滚动移出屏幕。
- floating：可常驻、移出屏幕，或收缩成单个高强调动作（Compose 上可收缩为 FAB 或关键动作）。
- ❌ **不要同时「收缩」和「滚出屏幕」**——官方明确禁止两种动效叠加。

### RTL

- 镜像需要镜像的单项（图标、文字方向）；动作顺序重要时，整体翻转动作顺序，使 Next 类动作仍落在 trailing 边。

## 无障碍

- **容器本身没有默认交互**，所有交互来自里面的元素（官方原文）。
- 读屏用户应能：导航并激活 toolbar 内任意动作；从菜单选择目标；激活返回按钮；**内容滚动或折叠后仍能访问 toolbar 控件**。
- 初始焦点落在**第一个可交互元素**；Tab 在动作间移动；Space / Enter 激活。
- 键盘：`Tab` 或方向键在可交互元素间移动；`Space` / `Enter` 激活。
- 语义角色：**web 上容器应为 `toolbar` role；移动端可以是普通容器**（generic container）。内部各动作按各自组件规范标注。
- 触控目标：每个元素 ≥ 48×48dp（guidelines 原文），不要为塞更多图标而缩小。
- 对比度：官方未给 toolbar 专属阈值，按 Material 通用文本 / 图标对比度执行（⚠️ 推断，页面未明示）。

## Compose 对应 API

| 需求 | API | 状态 |
|---|---|---|
| Docked toolbar | `DockedToolbar` | Expressive，需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)` |
| 横向悬浮工具栏 | `HorizontalFloatingToolbar` | 同上；与 FAB 组合为官方支持的配置 |
| 纵向悬浮工具栏 | `VerticalFloatingToolbar` | 同上；大断点使用 |
| 旧底栏 | `BottomAppBar` | 稳定 API，但设计侧已不推荐 |

```kotlin
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
DockedToolbar {
    IconButton(onClick = onUndo) { Icon(Icons.AutoMirrored.Filled.Undo, "撤销") }
    IconButton(onClick = onRedo) { Icon(Icons.AutoMirrored.Filled.Redo, "重做") }
    // 可选：嵌入式主操作（替代 FAB）也放在这个容器内
}
```

接入前请核对三件事（API 名称与参数随版本演进）：

1. `DockedToolbar` / `HorizontalFloatingToolbar` / `VerticalFloatingToolbar` 在当前 material3 版本是否存在；
2. 64dp 高度、16dp 外边距、48dp 触控目标是否由组件默认值提供；
3. 滚动收缩（collapse to FAB）是否为独立参数，避免与滚出屏幕动效叠加。

## 常见错误

1. 把顶部标题栏写成 toolbar（顶部是 app bar）。
2. 底栏同时放 navigation bar 和 toolbar，两套控件抢同一块区域。
3. 给 docked toolbar 加圆角，暗示它可以展开。
4. floating toolbar 贴边不足 16dp（横向）或 24dp（纵向），或超出窗口。
5. 在 floating toolbar 里用方形 filled 图标按钮。
6. 一屏强调三个主色按钮，没有主次。
7. 把 6 个动作硬塞进贴边工具栏，不设溢出菜单。
8. 滚动时既收缩又移出屏幕。

## 来源

- https://m3.material.io/components/toolbars/overview
- https://m3.material.io/components/toolbars/specs
- https://m3.material.io/components/toolbars/guidelines
- https://m3.material.io/components/toolbars/accessibility
- 核验日期：2026-11-16
- 关联：`02-Components/app-bars.md`（顶部栏）、`02-Components/navigation.md`（导航栏与 rail）、`02-Components/fab.md`
