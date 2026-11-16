# 布局与自适应 / Layout & Adaptive Design

> 官方指南：https://m3.material.io/foundations/layout/layout-overview/overview
> 核验日期：2026-11-16
> 核验方式：浏览器渲染 m3.material.io 后提取纯文本（全站 375 页 dump），逐页核对
> 状态：断点区间、margin、spacer、固定窗格宽度、窗格数为**官方原文**；网格列数官方 dump 只有定性描述，**未给出的数字一律不编**
> 命名更新：官网原文「**Window size classes renamed to breakpoints**」「**Responsive layout renamed to adaptive design**」——旧文档里的 window size class 就是今天的 breakpoint

## 一句话结论

> 「**A breakpoint (previously window size class) is the window size at which a layout needs to change to match available space, device conventions, and ergonomics. These apply to Android and web.**」

五档宽度断点（<600 / 600–839 / 840–1199 / 1200–1599 / 1600+ dp），页面边距只有两个值：**compact 16dp，其余四档 24dp**；窗格之间的 spacer（俗称 gutter）**≥ medium 一律 24dp**。

## 断点系统

### 宽度断点（官方全表）

| 断点 | 宽度 | 常见设备（原文） | 推荐窗格 | 其他窗格 |
|---|---|---|---|---|
| Compact | **under 600dp** | Phone in portrait | 1 | — |
| Medium | **600–839dp** | Tablet in portrait；Foldable in portrait (unfolded) | 1 | 2 |
| Expanded | **840–1199dp** | Phone in landscape；Tablet in landscape；Foldable in landscape (unfolded)；Desktop | 2 | 1 |
| Large | **1200–1599dp** | Desktop | 2 | 1 |
| Extra-large | **1600dp+** | Desktop；Ultra-wide monitors | 2 | 1, 3 |

窗格数一列来自 scaffold/panes 页的官方表；断点概览页给的顺序略有不同（large 写「1 or 2 (recommended)」、extra-large 写「1 to 3 (recommended)」），两页都把 **expanded 起的两窗格视为常态**。

注意区间是**左闭右开**：600dp 归 medium，840dp 归 expanded，1200dp 归 large，1600dp 归 extra-large。写条件分支时用 `< 600.dp`、`in 600.dp..839.dp` 这类写法，不要用 `<= 600`——600dp 本身不属于 compact。

> 「**Rather than designing for an ever-increasing number of display states, focusing on breakpoints ensures layouts work across a wide range of devices.**」

### 每档的导航 / 沟通 / 操作形态

| 断点 | 导航 | 沟通（对话框） | 操作（浮层） |
|---|---|---|---|
| Compact | Navigation bar，或 modal expanded navigation rail | Simple dialog / Full-screen dialog | Bottom sheet |
| Medium | Navigation bar，或 modal expanded navigation rail | Simple dialog | Menu |
| Expanded | Modal 或 standard expanded navigation rail | Simple dialog | Menu |
| Large | Modal 或 standard expanded navigation rail | Simple dialog | Menu |
| Extra-large | Modal 或 standard expanded navigation rail | Simple dialog | Menu |

各断点页的原文差异：compact「**Use a navigation bar or modal expanded navigation rail.**」并「**Place navigation elements near the bottom of a compact window**」；medium「Single-pane layouts: Navigation rail；Two-pane layouts: Navigation bar」；expanded 起「**Use a navigation rail, either collapsed or expanded.**」，排序/筛选/次级导航改为「use tabs or other components directly in the pane」；「**An expanded navigation rail is best suited for extra-large windows, where there's still plenty of room for content.**」

⚠️ 推断，页面未明示：compact 导航条与 medium 双窗格导航条是一回事（都在底部），但官方没有把「导航条只到 medium」写成硬规则，只是两处推荐都停在 medium。

### 高度断点

> 「**On Android, compact, medium, and expanded breakpoints are also available for height. These can be used to adjust the layout when available vertical space is unusually small or large. However, since most layouts contain vertically scrolling content, it's rare that layouts need to adjust to available height.**」

**dump 未给出高度断点的具体 dp 值**，只说明存在 compact / medium / expanded 三档且用于高度。不要凭印象写 480dp / 900dp 之类的数字。

### 为什么要按断点而不是按设备

> 「**Design for breakpoints instead of specific devices because: The amount of available window space is dynamic and changes based on user behavior, such as multi-window modes or unfolding a foldable device. Devices fall into different breakpoints based on orientation.**」

各断点页都列了「必须动态切换」的触发条件，归纳起来是五类事件：折叠/展开（foldable）、旋转、进/出分屏、进入多窗口、自由窗口缩放。

## 边距与栏间距（margin / spacer）

这张表是全文最该记住的部分，也是 AI 最容易编的地方：**官方只给 16dp 和 24dp 两个值**，不存在 8dp / 32dp / 48dp 的断点级边距。

| 断点 | 左右 margin（leading + trailing） | 窗格间 spacer | 原文 |
|---|---|---|---|
| Compact | **16dp** | —（单窗格） | 「**Margins are 16dp from the leading and trailing edge of the window.**」 |
| Medium | **24dp** | **24dp** | 「**Medium layouts have margins of 24dp. The spacer between panes is also 24dp.**」 |
| Expanded | **24dp** | **24dp** | 「**Expanded layouts have a leading and trailing margin of 24dp. The spacer between panes is 24dp.**」 |
| Large | **24dp** | **24dp** | 「**Large and extra-large layouts have a leading and trailing margin of 24dp.**」 |
| Extra-large | **24dp** | **24dp** | 同上 |

### 关于「gutter」这个词

**这 21 个布局 dump 里没有出现 gutter**，官方用 **spacer**（「**Spacer: The space between two panes on a foldable device**」）。所以写「spacer between panes = 24dp」，不要写「gutter 24dp」；compact 是单窗格，官方没给栏间距，因为它不存在。「gutter」只在 `blog_material-density-web` 出现过一次，讲的是 Web 密度，不属于 M3 布局规范。

⚠️ 推断，页面未明示：网格内的**列间距**（column gap）在 grids 页只说「column count, width, and spacing change」，没给数值。等同于 24dp 的 pane spacer 是合理工程近似，但官方没这样写。

### 固定窗格宽度（不是边距，但常被混为一谈）

| 断点 | fixed pane 默认宽 | 原文 |
|---|---|---|
| Expanded | **360dp** | 「**When using a fixed-and-flexible layout, the fixed pane should have a width of 360dp by default.**」 |
| Large / Extra-large | **412dp** | 「**Fixed panes should be 412dp in large and extra-large layouts**」 |
| Expanded（拖拽吸附值） | 360dp 或 412dp | 「The recommended custom widths are: 360dp / 412dp」 |
| Extra-large 第三窗格（side sheet） | 默认最大 **400dp** | 「**side sheets have a default maximum width of 400dp**」 |

注意 400dp 与 412dp 的差别是官方自己点明的：固定窗格推荐 412dp，但 side sheet 的默认最大宽是 400dp，两者不是同一个东西。

## 网格

> 「**Layouts in Material are based on a grid that adapts across all breakpoints.**」
> 「**Parts of the layout scaffold like rails and panes are positioned on this grid to create consistent adaptive layouts**」

### 列数规则（官方只有定性表述）

> 「**Grids adapt across breakpoints. As the size increases, column count, width, and spacing change as well.**」
> 「**When moving between sizes, column count may increase to show more content or controls.**」

可以确认的三条：

1. **compact 列数最少**：「**On compact screens, fewer columns are used to create a focused layout**」；
2. **尺寸变大时列数增加**：「**As screen size increases, for example when a foldable screen is unfolded, additional columns allow for a richer layout**」；
3. **列数不是无限增**：feed 页说列宽本身也会随断点变宽（「**Column width can increase at larger breakpoints**」），所以 expanded 之后更多是「加宽」而不是「一直加列」。

**dump 中没有出现「compact 4 列 / medium 8 列 / expanded 12 列」这类具体数字。** 网上流传的 4/8/12 属于旧版 responsive grid 规范，本次抓取的 M3 页面不再给数字。

⚠️ 推断，页面未明示：唯一能反推的列数来自 feed 布局——compact 下「**A feed layout should stack vertically, like a list of cards with individual items filling the width of the pane**」，即 compact = 1 列；medium「can support components with different widths and be split across multiple columns」；expanded 起「The number of columns should usually increase」。若实现中需要 4/8/12，应视为团队约定而非 M3 规范。

### 网格的放置顺序

官方给的是三步：**先放列**（Start with placing grid columns）→ **再填 bar 与 rail**（「**Populate regions of the layout scaffold that are closest to the edges of the screen's usable space first.**」，含 navigation bar/rail、toolbar、app bar）→ **最后放窗格**（「Next, populate the main region of the screen with panes with content and components」）。

### Rulers（全局对齐线）

> 「**Rulers are a set of recommended global alignment lines that help create consistent focal points in a product.**」

四类 ruler：**Bar & safety rulers**「reserve space for system UI elements like the status bar and gesture navigation」，保证 app bar 不被系统 UI 盖住；**Title rulers**「creates consistency for the screen's title, aligning the text, icons, and other components in an app bar」；**Content rulers** 第一内容线对齐 hero image / headline，次级 ruler 决定补充文字与操作的起点；**Margin rulers**「come with some wiggle room」，可左右调整松紧。

> 「**Rulers allow components and media to use different margin widths**」——这就是「图片通栏、搜索框留边」这类效果的做法：不是破坏网格，而是在同一套 ruler 上给不同元素分配不同的 margin ruler。

## 密度（density）

先分清**两个不同的密度**，这是最常被合并的错误：

| 概念 | 定义 | 手段 |
|---|---|---|
| Information density（信息密度） | 「the amount of content (such as text, images, or videos) in a given space」 | 改 margin / spacer / padding、改布局结构 |
| Component scaling（组件缩放 / component density） | 「controls the internal spacing of individual components」 | 按 **0 / -1 / -2 / -3** 档缩组件内部 |

> 「**The density scale is numbered, starting at 0 for a component's default density. The scale moves to negative numbers (-1, -2, -3) as space decreases, creating higher density.**」
> 「**Higher density is typically applied by decreasing the top and bottom padding or overall height by 4dp.**」

即：**每一档 = 上下 padding 或整体高度减 4dp**，0 是默认，往负数走越密。dump 里没有 -4 以下的档位，也没有把档位对应到具体设备（手机/平板/桌面）——**「density 档位 ↔ 设备」的对照表官方未提供**。

硬约束（每条都是官方原文）：

- 最小目标：「**The default target size should be at least 48x48 CSS pixels**」
- 默认不得变密：「**People should be able to opt in to dense layouts and components.**」
- 不得跨断点自动变密：「**Density shouldn't automatically change across breakpoints or orientation unless a person changes it.**」
- 设置入口本身要用默认尺寸（48×48），保证密度可恢复。
- 聚焦类 UI 不要加密：「**Don't increase density in UIs that involve focused tasks, such as selecting from a menu.**」
- 提示类组件不要加密：snackbar、dialog 不得提高密度。
- 文字不随容器缩：「**Text size shouldn't change as the container size scales.**」

高密度适用：news、financial portals、dashboards、数据表、长表单；低密度适用：重审美、单一信息、更少内容、更易导航的场景。

## 布局骨架：Scaffold

> 「**Scaffold: A fundamental UI design structure that provides a standard platform for assembling key screen components**」

骨架由 **bar / rail / pane** 三类区域 + **safety region** 组成。术语表原文把 pane 定义得很死：「**A layout container that houses other components and elements within a single app. A pane can be fixed, flexible, floating, or semi-permanent.**」，并且「**All content must be in a pane.**」

### Bars

> 「**Bars frame the screen to help people navigate through a product. They typically contain an app bar or bottom navigation bar.**」

| 类型 | 位置 | 要点 |
|---|---|---|
| App bar | 顶部，**safety region 之外** | 「providing a title and 1–2 essential actions like search or back」 |
| Navigation bar | 底部，**safety region 之上** | 「let people switch between **3–5 primary UI views** at compact or medium breakpoints」 |

> 「**Bars can span a single pane or across the full width of a window.**」

Safety region 是系统 UI 的地盘：「**The safety region shouldn't contain primary content.**」——顶部和底部边缘在 compact 设备上都算。

### Rails

> 「**Rails are the next level in layout after bars, filling the perimeter space surrounding panes or floating above them.**」

Compact 的 top / bottom rail 放 toolbars、chat inputs、FABs 与单屏主控件；大屏 **leading** rail 放 navigation rail（可为 expanded），**trailing** rail 放支持性控件、垂直 toolbar 或与窗格内容相关的操作；XR 里 rail 可变成 **orbiter**，浮在可视内容区之外。

### Panes

> 「**All layouts are made up of 1–3 visible panes.**」

窗格可按宽度分 **Fixed / Flexible**（「**All layouts need at least one flexible pane.**」），按生命周期分 **Permanent / Temporary**（临时窗格的出现与消失会影响其他窗格尺寸），按呈现关系分三种：

| 呈现方式 | 定义 | 典型用法 |
|---|---|---|
| Co-planar | 两块并排 | 「persistent utilities like tool panels should be co-planar with primary content」 |
| Floating | 小窗格浮在大窗格之上 | 「Temporary tasks should remain floating regardless of breakpoint, such as a dialog」；大屏上浮动是默认，scrim 可选 |
| Docked | 贴边钉住，一边超出屏幕 | 如 bottom sheet；大屏上「**consider changing docked panes into co-planar panes**」 |

### 窗格自适应三策略

> 「**Pane layouts can adapt using three strategies: show and hide, levitate, or reflow.**」

| 策略 | 做什么 | 例子 |
|---|---|---|
| Show and hide | 窗格进出屏幕或改为并排 | 折叠屏展开后 detail 窗格出现 |
| Levitate | 窗格升为 floating / docked | co-planar 窗格在切断点时浮起；medium/expanded 的 docked 可转 floating 或 co-planar |
| Reflow | 重排位置与方向 | 竖屏时 supporting pane 移到 primary pane 下方；横向空间不足时改为纵向堆叠 |

### 拖拽与吸附

- Split-pane：两窗格默认各 **50%**，「**Avoid setting custom widths.**」；有导航 rail/drawer 时「it only reduces the size of one pane」，以便 spacer 视觉居中。
- 吸附宽度：**360dp / 412dp**（expanded、large、extra-large 可用）。
- 拖拽手柄除拖拽外还应可**点击/双击/长按**切换布局尺寸（无障碍要求）。
- **Persistent resizing**：记住用户宽度偏好，且**跨断点保持**——「if a two-pane layout is collapsed to one pane at any size, it'll remain collapsed even when changing breakpoints」。用于多数可缩放布局。
- **Temporary resizing**：不记忆，重开后回默认；主要用于 supporting pane（缩放不常见）。

## 三种 Canonical Layout

> 「**There are three canonical layout examples: feed, list-detail, and supporting pane. Each example has configurations for compact, medium, and expanded breakpoints.**」

### Feed

定位：「**A feed layout uses a grid composition to enable quick content browsing and discovery. Key use cases include news, photos, and social media.**」

| 断点 | 结构 |
|---|---|
| Compact | 「**should stack vertically, like a list of cards with individual items filling the width of the pane**」（单列） |
| Medium | 「can support components with different widths and be split across multiple columns」（可加列） |
| Expanded / Large / Extra-large | 多列，且「**The number of columns should usually increase at expanded breakpoints.**」；列宽也会增加 |

结构要点：卡片尺寸可以不同（「Feeds can organize content of different sizes, like using small and large cards」），**顺序由位置决定**（「**The order of items is determined by their position.**」），所以重排时不要靠 DOM/数据顺序补，要靠位置排版。窗口变化时「**Feed items should reflow when the amount of available space changes**」。

### List-detail

定位：「**Use the list-detail layout for quickly accessing details of an item from a long list of content.**」典型成对关系：短信+会话、文件浏览器+文件夹、歌手+专辑、设置+分类详情、收件箱+邮件。

| 断点 | 可见窗格 |
|---|---|
| Compact (0–599) | **1 pane**（list 或 detail 二者其一） |
| Medium (600–839) | **1（推荐）或 2** |
| Expanded (840+) | **2 panes** |
| Large (1200–1599) | **2 panes** |
| Extra-large (1600+) | **2 panes** |

行为规则（这几条最常被写错）：

| 状态 | 单窗格 | 双窗格 |
|---|---|---|
| 返回按钮 | 只在 detail 视图出现 | 不出现 |
| 选中态 | 列表项不显示选中 | 只在 list 视图显示 |
| 未选中任何项 | 显示 list | detail 显示占位/空状态 |
| 有选中项 | 通常显示 detail + app bar | detail 显示选中项内容 |

> 「**Consistency is key: If a layout showed the list view previously, it should return to that view when returning to a single pane**」

例外：多选等「不需要深层导航的选择」场景，单窗格可以停在带选中态的 list；「the most recently used pane should stay visible when switching to single-pane layout」。

状态保持：「In most cases, a state should be saved when navigating between detail views」，包括已读/未读与滚动位置。

### Supporting pane

定位：**主内容 + 佐证内容**，主区占大部分（canonical 概览页写「typically about two-thirds」）。适用 productivity、文档编辑与批注、内容与媒体浏览。

> 「**Use the supporting pane layout when the secondary content is only meaningful in relation to the primary content.**」
> 「**For content with a parent-child relationship, use a list-detail layout instead.**」

| 放置 | 窗格宽度 | 断点 |
|---|---|---|
| Below（下方） | Flexible | Compact 或 Medium |
| Leading 或 trailing（侧边） | **Fixed 360dp** | Expanded |

compact 的加分项：「**A bottom sheet can be useful for keeping focus on the primary pane while providing access to supporting information.**」

### 三者怎么选

| 判断 | 选 |
|---|---|
| 大量同类内容，重在浏览与发现 | Feed |
| 父子/主从关系，从长列表进详情 | List-detail |
| 次级内容只对主内容有意义（批注、上下文） | Supporting pane |
| 需要聚焦单一任务、临时遮挡 | 不是 canonical，用 levitate 做**分层（layering）**：购物车确认、回复评论、新建日程 |

> 「**To create a custom layout, build on top of canonical layouts or layer scaffold elements.**」

## 自适应设计：什么时候改布局，什么时候只拉伸

### responsive vs adaptive 的分界

> 「**While responsive design scales a single layout to fit any screen, adaptive design customizes a product to optimize the experience on each device.**」

这句话就是判断标准：**只等比放大 = responsive（拉伸）；按设备/窗口重排结构 = adaptive（改布局）**。M3 要的是后者。

自适应的三个输入维度：「People / Devices / Usage」——个人偏好、设备类型（watch / phone / foldable / tablet / desktop / XR）、使用状态（缩放窗口、转向、换设备）。

### 跨断点的五个问题

官方给的是决策清单，不是数值表：

| # | 问题 | 判据（原文要点） |
|---|---|---|
| 1 | **What should be revealed?** | 小屏隐藏的部分在大屏露出：默认收起的 navigation rail、第二个 pane。「**Additional space doesn't just mean making the same thing bigger.**」 |
| 2 | **How should a screen be divided?** | compact/medium 单窗格最佳；expanded/large 推荐两窗格；extra-large 可考虑三窗格 |
| 3 | **What should be resized?** | cards / feeds / lists / panes；同时「**adjust margins and type styles to keep text between 40–60 characters per line**」 |
| 4 | **What should be repositioned?** | 卡片重排、加第二列、更复杂图片布局、加负空间；「shifting actions from the bottom of a compact window to the leading edge of medium and expanded windows」 |
| 5 | **What should be swapped?** | 功能等价组件互换，如 navigation bar → navigation rail、collapsed rail → expanded rail |

第 2 问的定量说明：medium 双窗格只在**低信息密度**时用——

> 「**At medium breakpoints, two panes are useful when they contain low-density content with clear actions. Don't use two panes in medium layouts with high information density, as it can reduce usability.**」

第 5 问的刹车：「Use caution when swapping components... **The interchangeable components are functionally equivalent**；**The component swap serves a functional and ergonomic purpose**」；Don't「**swap components that aren't functionally equivalent, such as swapping a button with a menu**」，也不要随意在 list item 与 card 之间换。

### 组件级的三条自适应策略

> 「**Most Material components respond using three main strategies: resizing, showing and hiding, and presentation changes.**」

| 策略 | 含义 | 例子 |
|---|---|---|
| Resizing | 跟随容器与内容改尺寸 | 按钮 hug content 或撑满容器 |
| Showing & hiding | 选择性露出信息 | 平板上的 list item 多显示一行描述 |
| Presentation changes | 换配置/方向/颜色/字体/形状 | FAB ↔ extended FAB；navigation rail 自动展开 |

### 触达与动态切换

medium 页给的唯一定量几何：「**the top 25% of the screen is likely out of reach**」，因此「**limit the amount of interactions that are placed in the upper 25% of the screen**」；也避免把关键交互贴到最底部（大手用户难触达）。分区结论：上部 = Inconvenient，中部 = Comfortable，最下缘 = Challenging。

必须动态切换的五类事件（各断点页反复出现）：折叠/展开、旋转、（平板）进出分屏、进入多窗口、自由窗口缩放。这是**运行时**要求，不是设计稿的静态状态。

## RTL：什么镜像，什么不镜像

> 「**Over 2 billion people read and write in RTL languages like Arabic, Hebrew, Farsi, and Urdu.**」

基础机制：Material 组件命名用 **leading / trailing** 而非 left / right，「**However, extra configuration may be needed to achieve specific RTL situations.**」

> 「**When a layout is changed from LTR to RTL (or vice-versa), or flipped horizontally, it's often called mirroring. UI elements and text that typically appear on the left in LTR aligns to the right. Reading flow starts from the top right corner, instead of the top left.**」

### 镜像

| 对象 | 规则 |
|---|---|
| 布局整体 | 三个 canonical（list-detail / feed / supporting-pane）**全部镜像**；supporting pane 这时出现在主内容左侧 |
| 导航 rail / expanded rail | 放在 **leading edge**：LTR 左、RTL 右 |
| App bar | 「**Mirror an app bar's layout in RTL**」，翻转箭头类图标 |
| Toolbar | 「mirror the order of the tools」 |
| 方向性图标 | back / forward 镜像；send 镜像；help 图标在 **Urdu 与 Persian** 中镜像 |
| Badge | 位置与对齐翻转（小/大 badge 移到图标左上） |
| Text field 前后图标 | leading / trailing 图标随方向换边 |
| Chips | trailing 图标「always aligned to the end side」：LTR 右、RTL 左 |
| 手势 | 「**RTL swiping and gestures should mirror their counterparts in LTR**」；Android predictive back 同样镜像 |
| 文本对齐与流向 | RTL 文本右对齐、从右往左排；**对齐与方向必须同时为 RTL** |
| 线性进度 | 多数 RTL 语言从右往左推进（**Hebrew 例外，保持 LTR**） |

### 不镜像

| 对象 | 规则 |
|---|---|
| 图表 / 图形 | 「**graphs and charts maintain a LTR directionality for Persian and Urdu.**」 |
| 时间线、媒体控件（Hebrew） | 「**in Hebrew, timelines and media controls on a page should retain left-to-right directionality.**」 |
| 媒体播放器控件 | 「**Media controls for video or audio players are always LTR.**」 |
| 时钟方向 | 「**the directionality of time remains LTR, and clocks still turn clockwise**」；**12 小时制的 AM/PM 放到左侧** |
| 圆形进度 / 顺时针图标 | 「**Circular representations of time remain the same.**」；「Clock icons, circular refresh icons, and progress indicators with arrows pointing clockwise shouldn't be mirrored.」 |
| 邮箱地址 | 「**Don't reverse the order of the email username and domain (@google.com).**」，域名永远在用户名右侧 |
| 文本方向设置 | Don't：「**Don't apply LTR directionality to RTL content, because it may scramble word order.**」 |

常见 RTL 渲染坑（官方点名）：文本输入、光标位置、标点、电话号码、URL。

## designing 四篇里与布局相关的规则

这四篇的主题是**无障碍**，不是排版；能落到 Android 布局上的只有四条。

**Structure（层级与目标尺寸）**：相关项相邻，重要动作放屏幕顶部或底部；触控目标「consider making touch targets at least **48 x 48dp**」（约 9mm，推荐 7–10mm），指针目标「**minimums 44 x 44dp**」，目标间距「**targets separated by 8dp of space or more**」。图标 24dp、Star 40dp，但目标都要 48dp。阅读顺序由结构决定——「**screen readers rely on the top-down structure of HTML on any platform (mobile or web)**」，所以视觉顺序必须与结构顺序一致。该页 landmarks / H1–H6 章节明确标注「**For web only**」，Android 不要照搬 ARIA 角色表。

**Flow（焦点顺序）**：默认顺序「**follows the DOM ... and generally flows from left to right; top to bottom**」，并「**Use the defaults**」。进屏要定初始焦点，多交互组件（复杂卡片、dialog）也要定组件内焦点；dialog 关闭后「**focus returns to the interactive element that initiated the action**」。Tab / Shift+Tab 在元素间移动，方向键在组件内移动，Enter 激活；特殊布局可「**group a collection of interactive elements as one tab stop**」再用方向键遍历子元素。快捷键「**should use a combination of two or more keys by default**」。

**Elements（标签与角色）**：无可见文字的图标按钮、可交互图片、进度/错误提示、有意义的插图需要标签；通用文案（"Learn more"、页面上多个 "Save"）补上下文；纯展示文本与文案已足够的按钮不需要。**标签里不要写控件类型**——「**Don't include the control type in the label**」，否则读成「Got it button button」。装饰性图像标为 decorative / hide。Web 用 ARIA role，非 Web「assign roles based on your design system components」。

**Overview（三阶段）**：「**The three stages described in these tabs help translate a visual UI into a text-based, linear user experience that maps to code.**」即 Structure → Flow → Elements，把视觉界面转成线性体验；用平台原生控件，Don't 用「non-standard platform dialog」做标准对话框任务。

## 常见错误

- 断点写成 `<= 600`（600dp 属 medium）；给 compact 写 24dp 边距，或给 large 写 32dp——官方只有 16 / 24 两档。
- 把 pane spacer 叫 gutter，或给 compact 编栏间距；把 4/8/12 列说成 M3 规定。
- expanded 的固定窗格写 412dp（应为 360dp）；把 side sheet 的 400dp 上限当成 412dp。
- medium 双窗格塞高密度内容；双窗格自定义非 50% 宽度，破坏 spacer 居中。
- 单窗格显示选中态，或双窗格显示 back 按钮。
- 默认开高密度，或在 snackbar / dialog / 菜单里加密度；密度一改连文字一起缩。
- 只做 RTL 右对齐、忘了 directionality；把图表、媒体控件、时钟镜像过去。
- 折叠/多窗口下只做等比缩放，不重建布局。

## 检查项

- [ ] 断点判断用左闭右开区间（600 / 840 / 1200 / 1600 归上一档）
- [ ] compact 边距 16dp，其余 24dp
- [ ] 双窗格 spacer = 24dp
- [ ] 固定窗格：expanded 360dp，large/extra-large 412dp
- [ ] 窗格数 ≤ 3，且**至少一个 flexible pane**
- [ ] medium 双窗格仅用于低密度内容
- [ ] 联表关系用 list-detail，上下文补充用 supporting pane，浏览用 feed
- [ ] 单窗格才显示 back，双窗格才显示选中态
- [ ] split-pane 默认各 50%，spacer 视觉居中
- [ ] 触摸目标 ≥ 48×48dp，指针 ≥ 44×44dp，目标间距 ≥ 8dp
- [ ] 密度默认关闭，且不随断点/方向自动变化
- [ ] 每行文字 40–60 字符（靠 margin 与字级调，不靠改断点）
- [ ] RTL：rail 在 leading，back/send 图标镜像，图表与媒体控件不镜像
- [ ] 阅读顺序（焦点顺序）与视觉顺序一致
- [ ] 折叠 / 旋转 / 分屏 / 多窗口 / 自由缩放五类事件都能动态切换布局

## 来源

- 布局：总览 https://m3.material.io/foundations/layout/layout-overview/overview ｜ 组成 https://m3.material.io/foundations/layout/layout-overview/parts-of-layout ｜ 自适应 https://m3.material.io/foundations/layout/layout-overview/adaptive-design
- 断点：总览 https://m3.material.io/foundations/layout/breakpoints/overview ｜ compact https://m3.material.io/foundations/layout/breakpoints/compact ｜ medium https://m3.material.io/foundations/layout/breakpoints/medium ｜ expanded https://m3.material.io/foundations/layout/breakpoints/expanded ｜ large-extra-large https://m3.material.io/foundations/layout/breakpoints/large-extra-large
- 网格与间距：总览 https://m3.material.io/foundations/layout/grids-spacing/overview ｜ 网格 https://m3.material.io/foundations/layout/grids-spacing/grids ｜ 间距 https://m3.material.io/foundations/layout/grids-spacing/spacing ｜ 密度 https://m3.material.io/foundations/layout/grids-spacing/density
- Scaffold：总览 https://m3.material.io/foundations/layout/scaffold/overview ｜ bars https://m3.material.io/foundations/layout/scaffold/bars ｜ panes https://m3.material.io/foundations/layout/scaffold/panes ｜ rails https://m3.material.io/foundations/layout/scaffold/rails
- Canonical 布局：总览 https://m3.material.io/foundations/layout/canonical-examples/overview ｜ feed https://m3.material.io/foundations/layout/canonical-examples/feed ｜ list-detail https://m3.material.io/foundations/layout/canonical-examples/list-detail ｜ supporting-pane https://m3.material.io/foundations/layout/canonical-examples/supporting-pane
- 双向性 / RTL：https://m3.material.io/foundations/layout/bidirectionality-rtl
- Designing：总览 https://m3.material.io/foundations/designing/overview ｜ structure https://m3.material.io/foundations/designing/structure ｜ flow https://m3.material.io/foundations/designing/flow ｜ elements https://m3.material.io/foundations/designing/elements

核验日期：2026-11-16
