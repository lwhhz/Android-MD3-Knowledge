# App bars / 顶部应用栏

- 官方指南：https://m3.material.io/components/app-bars ｜ 规格页：https://m3.material.io/components/app-bars/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**Expressive 已改名为 app bar**；测量图为图，文本数字有限，见「规格」小节

## 它是什么

官方定义：**"App bars are placed at the top of the screen to help people navigate through a product."**

它负责「我在哪一页 + 怎么离开这一页」，动作数量被严格限制：

> "Focus on describing the current page and provide 1–2 essential actions"
> "Displays labels and page navigation controls at the top of the page. (Use a toolbar to display page actions)"

**与 toolbar 的分界**：app bar 在顶部，给页面标题、导航按钮和 1–2 个核心动作；toolbar 在底部或悬浮，承载大量页面操作。动作多就放 toolbar，不要往 app bar 里塞溢出菜单。详见 `02-Components/toolbars.md`。

## 变体与代际

| 变体 | M3 | M3 Expressive | 用途（官方原文转述） |
|---|---|---|---|
| Search app bar | — | ✅ | 搜索是产品核心的首页；点击后打开 search view |
| Small | ✅ | ✅ | 密集布局，或页面已滚动时 |
| Medium flexible | — | ✅ | 需要更大标题；滚动时可收缩为 small |
| Large flexible | — | ✅ | 强调页面标题 |
| Medium（baseline） | ✅ | ❌ 不再推荐 | 用 medium flexible 替代 |
| Large（baseline） | ✅ | ❌ 不再推荐 | 用 large flexible 替代 |
| Center-aligned（旧） | ✅ | 并入 small | 改用 small 的居中文字配置 |

官方 Expressive 变更（2025 年 5 月）：

- 组件由 **top app bar 更名为 app bar**。
- 新增 search app bar。
- medium / large **不再推荐**，由 medium flexible / large flexible 替代。
- flexible 变体带来：整体高度降低、标题字号更大、支持副标题、支持左对齐或居中、支持文字换行、可放图片与 filled 按钮。
- small 也获得副标题、居中文字、更灵活的元素槽位。

## 规格

### 文字明示值（specs 页）

| 属性 | 值 | 适用 |
|---|---|---|
| 容器高度 | **112dp** | 仅 baseline **medium** app bar |
| 图标按钮尺寸 | **24dp** | baseline medium |
| 标题字体 | 以 `Aa` 占位，未给数值 | baseline medium |
| 副标题字体 | 以 `Aa` 占位，未给数值 | baseline medium |

### 排版样式（guidelines 明示）

| 变体 | 标题（headline） | 副标题（subtitle） |
|---|---|---|
| Search | Body large | —（用搜索提示文字） |
| Small | Title large | Label medium |
| Medium flexible | Headline medium | Label large |
| Large flexible | Display small | Title medium |

> "Headline typography style for each app bar — Search: Body large / Small: Title large / Medium flexible: Headline medium / Large flexible: Display small"

### 测量页的数字缺口

app bars 的测量章节（search / small / medium flexible / large flexible / baseline medium / baseline large）**全部是带标注的示意图**，正文只有图注 "…padding and size measurements"，没有可复制的数字表。

- 因此 **search / small / medium flexible / large flexible 的内边距、高度、图标尺寸在本轮 dump 中无法取得数值**。
- 旧文档里的「small 64dp / medium 112dp / large 152dp」中，**只有 112dp 能在 dump 文本里找到**（且属于已不推荐的 baseline medium）；64dp 与 152dp 属 ⚠️ 推断，页面未明示，不要作为官方值传播。
- 唯一可执行的高度规则来自 guidelines：**用默认高度，不要压矮**。

> "Always use the default height of the app bar, and make it span the full width of the window."
> "Don't make an app bar shorter than its default height"（Don't）

### 宽度与自适应

| 规则 | 值 |
|---|---|
| 容器宽度 | 始终 100% 窗口宽度 |
| 搜索容器宽度 | 先填满 leading / trailing 之间的全部空间，**上限 312dp**；之后只再增长到该空间的 **50%** |
| 大屏 trailing 图标 | search app bar 最多 **4 个** |
| 手机 trailing 图标 | 最多 **2 个** + 1 个头像 |
| 标题换行 | flexible 最多 2 行；small 不换行 |

> "The search container of the search app bar should fill 100% of the space between leading and trailing app bar elements until it reaches 312dp. Then, it should only grow further to fill 50% of that space."

## 颜色角色

| 部位 | 颜色角色 |
|---|---|
| app bar 容器（flat） | `surface`（与页面背景同色） |
| 标题 | `on surface` |
| 副标题 / leading / trailing 图标 | `on surface variant` |
| 滚动后容器 | `surface container` |
| search app bar 的搜索容器 | `surface container` |
| 搜索提示文字 | `on surface variant` |
| 深色背景上的搜索容器替代 | `surface bright` |

- 所有 app bar 共享同一套颜色角色；**滚动时容器变为 `surface container`**。
- search app bar 的搜索栏也会随滚动变色；官方 token 组中另有 `surface container highest` 用于更高层级（具体绑定以 token 面板为准）。
- 自定义颜色时，**搜索文字与容器对比度至少 3:1**。

> "All app bars share the same color roles. On scroll, the container changes color to surface container."

## 使用规则

### 动作

1. app bar **只放 1 个动作，必要时 2 个**。
2. 主操作应作用于整页或离开整页，例如发送、保存、编辑。
3. ❌ 尽量不在 app bar 里放溢出菜单；动作多请改用 toolbar。
4. 需要突出主操作时，把图标按钮改为 **filled 或 tonal**，可再用 wide 图标按钮。
5. ❌ 不要同时放多个 filled / tonal 按钮。
6. trailing 图标按钮**最多 2 个**，最常用的靠近 leading 边；优先用 filled 图标，outlined 仅用于未选中的 toggle。

> "App bars should only have one action, two if necessary."
> "If the product has many actions, place those in a toolbar. Avoid placing an overflow menu in the app bar when possible."

### 容器

- ✅ 直角；❌ 圆角（圆角会暗示容器可展开）。
- ❌ 不要改容器位置或形状。
- 始终铺满窗口宽度，使用默认高度。

### Leading 按钮

只用于产品内导航，通常是两种之一：

- **菜单图标** → 打开 modal expanded navigation rail；
- **返回箭头** → 回到上一屏。

### 标题与副标题

- 标题可描述：当前页面、当前分区、产品或品牌。
- 标题要短到能放进 app bar；flexible 变体可换行到第二行，**最多 2 行**。
- ❌ 不要截断标题；❌ 不要在 small app bar 里换行。
- 副标题补充上下文，可与标题同左对齐或同居中。

### 图片与 logo

- 可放高质量、相关的图片或 logo，不得干扰功能。
- small app bar 中图片**可以完全替代文字**；其他变体中图片位于文字上方。
- search app bar 的 leading 位可放产品 logo，可纯装饰，也可触发回首页/刷新。
- ❌ 不要用 logo 去打开 expanded navigation rail。

### Search app bar 专则

- 必须包含 "Search" 一词；大小写随产品风格（`Search` / `Search inbox` / `Search Photos`）。
- 点击搜索栏必须打开 **search view** 组件，而不是就地展开输入。
- 可有一个头像 + 最多两个 trailing 图标，图标可放在搜索栏内或栏外；最常用的靠左。
- ❌ 手机端不要「3 个图标 + 头像」；更多动作请移到 toolbar。

### 滚动行为

- 初始与背景同色；滚动后填充对比色，与正文分离。
- app bar 可以常驻，也可以随滚动隐藏/重现。
- 想更突出正文时，可让容器在滚动后保持透明，但**内部图标按钮必须有容器填充**，并建议改用窄图标按钮。
- medium flexible / large flexible 滚动时可**压缩为 small app bar**，并保持 small 直到滚回顶部。
- ❌ 不要把 app bar 变成 search app bar。
- 窗口变窄时 trailing 动作可收进溢出菜单，变宽后再出现。

### RTL

布局按 leading / trailing 自动镜像，无需为 RTL 单独改结构。

## 无障碍

- 读屏用户应能：知道当前在哪一页；执行动作或导航到新目的地；**内容滚动后仍能访问 app bar 动作**。
- **初始焦点落在 leading 按钮**（app bar 的第一个可交互元素）；`Tab` 在交互项间移动；`Space` / `Enter` 激活。
- 每个可交互元素都要有焦点指示（focus ring）。
- 标题的无障碍标签应与可见标题文本一致；必要时补充上下文，帮助用户确认页面。
- 读屏会先读 UI 文本，再读组件角色。
- 图标按钮按其自身规范标注动作（例："在地图中查看"），不要读成图标名。
- 搜索栏与其提示文字的对比度 **≥ 3:1**；尽量使用默认颜色角色。

> "Focus should initially land on the leading button, since it's the first interactive element of the app bar."

## Compose 对应 API

| 需求 | API | 状态 |
|---|---|---|
| small app bar | `TopAppBar` | 稳定 |
| 居中标题 | `CenterAlignedTopAppBar` | 稳定 |
| medium / large（baseline） | `MediumTopAppBar` / `LargeTopAppBar` | 稳定，但设计侧已不推荐 |
| medium / large flexible | `MediumFlexibleTopAppBar` / `LargeFlexibleTopAppBar` | Expressive，需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`，名称需按版本核验 |
| search app bar | `SearchBar`（配合 `TopAppBar` 布局） | `@ExperimentalMaterial3Api` |

```kotlin
val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
        TopAppBar(
            title = { Text("订单详情") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                }
            },
            actions = {
                IconButton(onClick = onSave) {
                    Icon(Icons.Default.Save, contentDescription = "保存")
                }
            },
            scrollBehavior = scrollBehavior
        )
    }
) { padding ->
    LazyColumn(contentPadding = padding) { /* ... */ }
}
```

注意：

- 标题 / 副标题的 `subtitle` 参数仅 flexible 变体提供（⚠️ 需按当前版本核验签名）。
- `Scaffold` 的 `paddingValues` 必须消费，否则内容被顶栏压住。
- 返回图标用 `Icons.AutoMirrored.Filled.ArrowBack`，RTL 下自动指向正确。

## 常见错误

1. 把页面动作全塞进 app bar，再挂一个溢出菜单（应改用 toolbar）。
2. 标题写应用名而不是当前页面名。
3. 顶级页面放返回箭头，点了直接退出应用。
4. small app bar 里让标题换行或截断。
5. 同时放两个 filled 按钮，主次不分。
6. 给容器加圆角，暗示可展开。
7. 把 search app bar 做成「就地展开输入框」，而不是打开 search view。
8. 滚动后 app bar 保持透明却没有给图标按钮容器填充，图标糊在图片上。
9. 把 medium / large baseline 当成推荐方案（Expressive 已不推荐）。

## 来源

- https://m3.material.io/components/app-bars/overview
- https://m3.material.io/components/app-bars/specs
- https://m3.material.io/components/app-bars/guidelines
- https://m3.material.io/components/app-bars/accessibility
- 核验日期：2026-11-16
- 关联：`02-Components/toolbars.md`（底部/悬浮工具栏）、`02-Components/search-bar.md`、`02-Components/navigation.md`
