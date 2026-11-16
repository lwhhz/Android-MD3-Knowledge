# 手表端 Material 3 / Design for Watches (Wear OS)

> 官方指南：https://m3.material.io/foundations/watches/overview
> 核验日期：2026-11-16
> 核验方式：浏览器渲染 m3.material.io 后提取纯文本，逐页核对 4 个页面（overview / foundations / styles / layout）
> 内容密度提示：手表章节在官网只有 4 页、每页正文约 2–8 段（CHARS 2730–4596，含导航噪音）。本文所有数字均来自原文；原文没给的（安全区 inset、字号、组件尺寸、组件黑白名单）**一律不补**，只在「dump 未覆盖」一节列出缺口。

## 一句话结论

> "Watches have special design considerations and interaction patterns."

手表不是手机的缩小版：**黑底省电、圆屏贴边、一次只做一两件事、抬手即读**。官方在本章节只给出方向性规则和少量硬数字（起步 **192dp**、大于 **225dp** 才加内容），没有给完整的尺寸规范。

## 四个页面的分工

| 页面 | URL 片段 | 讲什么 | 正文体量 |
|---|---|---|---|
| Overview | `/foundations/watches/overview` | GM3 Expressive 在手表上的四类表达：形状、动效、颜色、字体 | 约 6 段 |
| Foundations | `/foundations/watches/foundations` | 解剖（表盘/按键）、设计原则、交互模式 | 约 8 段 |
| Styles | `/foundations/watches/styles` | 字体、颜色、动效、触感 | 约 8 段 |
| Layout | `/foundations/watches/layout` | 布局原则、标准布局、Tiles、通知、自适应 | 约 9 段 |

## 屏幕与形态

### 圆屏是默认前提

> "A new shape system with edge-hugging containers and buttons creates variety and visual balance on round screens."

- 官方称新形状系统提供 **edge-hugging（贴边）** 容器与按钮，用于在圆屏上取得视觉平衡。
- 布局页把「贴边按钮」写进标准布局的公共规则：`Edge-hugging buttons are used for round screens`。
- 类型基线同样是按圆屏优化的：`The baseline type scale is optimized for round screens to keep text legible in a compact space`。

> "Controls for smartwatches can adapt to the form factor."

即控件应当随形态（圆屏、屏幕大小）自适应，而不是把手机控件硬搬。

### 屏幕尺寸：192dp 起步，225dp 以上加内容

| 规则 | 官方原文 |
|---|---|
| 最小设计基准 | "Design for small screens first, starting with a **192dp** size watch" |
| 大屏增值 | "Show more content on devices that are larger than **225dp**" |
| 尺寸定义方式 | "Define margins and padding using **percentages** rather than fixed pixel values" |

原文说明用百分比的原因：`This prevents clipping and ensures content remains centered and proportional as the screen size increases.`

### 自适应布局的四条做法（原文列表）

1. **Design for small screens first**：从最小常见尺寸开始设计（192dp）。
2. **Use percentages**：边距和内边距用百分比，不用固定 px。
3. **Add value on larger screens**：大屏多出来的空间用于追加按钮、文本行、数据可视化。
4. **Test all font sizes**：`Font scaling and accessibility settings such as bold text may cause changes in the size of UI elements`。

官方还说明实现层已内建：`The Material 3 Compose component library has built-in adaptive behavior.`（指 Material 3 Compose for Wear OS）

### 常亮显示（AOD）

> "Watches can have always on displays, which allow ambient content to be shown when the watch isn't in use."

- 适用于「进行中」的体验：计时器、锻炼，需要持续可见。
- 硬约束：`Because they remain on the screen for long time periods, consider limiting the number of pixels that are illuminated.` —— 控制点亮像素数量（原文是 "consider limiting"，属建议强度，不是绝对值）。

⚠️ 推断，页面未明示：这条与「黑底省电」是同一条设计逻辑的两面（黑像素在 OLED 上不发光），但官网没有把两者明确关联起来。

## 表盘与系统入口（Anatomy）

### 表盘的三个信息层

| 层 | 说明（原文要点） |
|---|---|
| 表盘本体 | 显示时间，也显示其他信息，并通过 apps 和 tiles 提供功能入口 |
| Complication（复杂功能） | `self-contained details that can show contextual info like heart rate or progress`，可按样式或功能定制 |
| Ongoing activities | 表盘上显示进行中的动作，如秒表倒计时、锻炼计时 |

> "Watch faces display the time, as well as other information and can provide access to other functions through apps and tiles."

### 物理按键（三类，职责互斥）

| 类型 | 用途 | 可否被应用自定义 |
|---|---|---|
| Rotating side buttons（可旋转侧键） | 音量控制，或在选项/列表中滚动 | — |
| System buttons（系统键） | 开关机等 OS 功能 | **不可以**：`cannot be customized` |
| Multifunction buttons（多功能键） | 应用自定义动作，如启停秒表 | 可以 |

这条对开发的直接含义：**旋转输入应当被当作滚动/音量输入处理**，不要指望拦截系统键。

## 设计原则（原文四条）

> "Tailor layouts for different screen sizes with adaptive design"
> "Design for short interactions to conserve battery"
> "Focus on one or two tasks at a time rather than a full app experience"
> "Test designs in situations that involve movement to make sure the design is usable at a glance"

配套的 Do / Don't 只有一对：

| | 原文 |
|---|---|
| Do | `At-a-glance views allow people to quickly see calendar events` |
| Don't | `Don't create complex and detailed apps such as a calendar grid` |

### 两条交互前提

**始终相关（Always relevant）**：`Consider how to update app content based on context, such as time, place, and activity.`，并且 `Navigation on a watch complements the experience on a phone`。

**离线可用（Works offline）**：`Design for slow connections and offline use, such as exercising and commuting.` 网络状态通过两种方式传达：**offline icon** 或 **dialog**。

### 跨设备

> "Watches are often dependent on connected phones for functionality or complex interactions. In some cases, a watch and a phone are used together to accomplish different parts of the same task."

原则是分工而非复制：`Consider which actions are appropriate for each device`。

## 字体

手表类型系统只有两个新增角色，且都属 GM3 Expressive：

> "GM3 Expressive adds two type styles specially designed for watches."

| 角色 | 用途 | 原文要点 |
|---|---|---|
| **Numeral Large** | 纯数字显示，通常一次只有几位 | `This text can take on more expressive properties at larger display sizes without the accommodations usually required by text that must be localized.`（不受本地化排版约束，因此在展示级尺寸上可以更夸张） |
| **Arc Large** | 沿圆屏曲线排布的文字 | 用于 page titles、confirmation overlays、call to action；`optimizes character spacing for text displayed along a curve at the top or bottom of a round screen` |

结论性差异：**手表有「弧形文字」这一手机端不存在的排版角色**，标题沿圆屏上下缘弯曲时要用它，而不是靠手动旋转普通文本。

⚠️ 推断，页面未明示：dump 中没有给出这两个角色的具体字号/行高数值，只有 "Large" 这一级命名。落地时需查 Compose for Wear OS 的 type scale 资源，不能从本节推导。

## 颜色

### 黑底是硬规则

> "Watches are designed with a black background, instead of the tinted background that phones use."

原因一句说完：`Watches use a black background to conserve battery.` —— 这是手表与手机在视觉上最根本的一条差异。

### 颜色角色按光照分两套

`color tokens for Wear OS are specifically tailored for dark themes in low-light environments and light themes for daylight environments.` 即暗色主题面向弱光、亮色主题面向日光，不是单纯跟随系统外观。

### 对比度：Do / Don't（这条对写代码最有用）

| | 原文 |
|---|---|
| Do | `Buttons with on primary on primary and on primary container on primary container stay legible as the contrast level changes` |
| Don't | `Buttons with primary dim on primary or primary dim on primary container become illegible as contrast levels shift` |

即：**按钮文字用 `on-*` 角色，不要用 `*-dim` 角色**。`primary dim` 随对比度档位变化会失效。

### 推荐配色组合（原文三组语义）

| 场景 | 用法 |
|---|---|
| 强调重要元素 / 点击反馈 | `primary dim` 突出重要元素，`tertiary` 做突出反馈（如点击响应） |
| 主操作不明确 | `tertiary` + `primary` 作为主操作，`secondary container` 作为辅助操作 |
| 两个同等重要的选项/容器 | `secondary` + `primary container`，同时保持对比 |

## 动效与触感

### 动效

> "A new physics-based system makes interactions and transitions feel more alive, fluid, and natural."

形状变形被用作交互反馈：`With shape morphing, controls respond to show interaction. Containers change corner radius.`（选中的按钮改变形状）。转场则用于表达状态或层级的变化。

### 触感（Haptics）

> "Haptics are tactile effects used to grab a person's attention for something important, or add emphasis to an interaction on the screen."

使用场景（原文）：列表滚动、列表项选择、音量控制。

三条规则：

1. 优先用系统定义的模式与 token：`Use system-defined patterns and tokens (when available) to reinforce interaction expectations.`
2. 与 UI 动效、声音同步。
3. 分级：关键交互（如支付确认）用**更强**的触感；精细交互（如列表滚动）用**更弱**的触感。

## 布局

### 布局原则（三条）

| 原则 | 原文 |
|---|---|
| 内容优先 | `Place the most important information at the top of the screen.` |
| 限制选择 | `Reduce the number of actions to prevent decision fatigue. Focus on critical tasks to help people get things done within seconds.` |
| 简化导航 | `Use a clear, shallow hierarchy so people don't get lost in complex menus. Aim to display content and navigation inline.` |

### 标准布局的四个公共元素

滚动与非滚动布局共用：

> "The time is shown on most app screens" / "Edge-hugging buttons are used for round screens" / "Show an indicator if more content is available on a scrolling apps"

四个组成部分：**Time text / Page title / Scroll indicator / Action button**。

注意「时间要在多数应用页面上显示」——⚠️ 推断，页面未明示：这句意味着时间由页面自己绘制，而非依赖系统状态栏（官网未说明状态栏的有无）。

### 非滚动布局

适用于内容完全放得下的一次性任务：媒体播放器、选择器与切换器、健身记录页、确认对话框。

> "Non-scrolling layouts are for focused tasks or single-screen interactions where all content fits within the display."

### 滚动布局

内容超出屏高时使用：消息会话、联系人列表、菜单选项。

> "Scrolling layouts can show content that exceeds the screen height, such as lists or dialogs."

### Tiles（磁贴）

> "Tiles (or widgets) are designed for glanceability."

- 用途：时效性更新、高频轻任务（看目标进度、看天气）。
- 入口与形态是硬约束：`Tiles are accessible with a swipe from the watch face. They have a fixed screen height and don't scroll.`

| 属性 | 值 |
|---|---|
| 入口 | 从表盘滑动进入 |
| 高度 | 固定屏高 |
| 滚动 | **不支持** |

### 通知

- 可展开以提供更多交互：回复消息、在地图上打开位置、播放歌曲。
- Wear OS 提供**即时消息**与**日历事件**两类通知模板。
- 原则：`Notifications should offer easy access to more interactions.`

### 布局类型选择表

| 内容特征 | 用哪种 |
|---|---|
| 全部内容一屏放得下、单一任务 | 非滚动布局 |
| 列表 / 会话 / 菜单，超出屏高 | 滚动布局 |
| 需要从表盘一划即达、固定高度 | Tile |
| 事件提醒、需要展开交互 | 通知 |

## 导航模式

dump 里关于导航只有三条事实，没有 nav bar / nav rail / drawer 之类的组件级规范：

1. `Navigation on a watch complements the experience on a phone` —— 手表导航是手机体验的补充，不是替代。
2. `Simplify navigation`：清晰、浅层级的结构，`Aim to display content and navigation inline`（内容与导航内联展示，避免多层菜单）。
3. 跨设备分工：`Consider which actions are appropriate for each device`。

⚠️ 推断，页面未明示：官网手表章节没有独立「导航」页，导航被拆散在 foundations / layout 两页的段落里。因此「手表用什么导航组件」在 m3.material.io 上并无规范答案，需转到 Android Developers 的 Wear OS 文档（dump 里只给了链接，没有内容）。

## 与手机端的差异

| 维度 | 手机（M3） | 手表（Wear OS） | 依据 |
|---|---|---|---|
| 背景 | tinted background（带色调的表面色） | **纯黑** | `instead of the tinted background that phones use` |
| 屏幕 | 矩形，断点 600/840/1200/1600dp | 圆屏，基准 **192dp**，> **225dp** 加内容 | layout 页 |
| 一次任务数 | 完整应用流程 | `one or two tasks at a time` | foundations 页 |
| 排版角色 | display/headline/title/body/label | 额外增加 **Arc**（弧形）与 **Numeral**（数字） | styles 页 |
| 交互时长 | 可持续操作 | `short interactions to conserve battery` | foundations 页 |
| 输入 | 触摸为主 | 触摸 + **旋转侧键** + 多功能键 + 触感 | foundations 页 |
| 屏幕常亮 | 无此概念 | AOD，需限制点亮像素 | foundations 页 |
| 网络 | 默认在线 | 必须设计离线态（offline icon / dialog） | foundations 页 |
| 尺寸定义 | 固定 dp 边距 | **百分比**边距 | layout 页 |

### 哪些组件「不能用」

**dump 里没有组件黑白名单。** 四个页面从未列出「手表不支持的 M3 组件」，也没有出现 card / chip / FAB / bottom sheet 等组件名。唯一接近禁令的是一条应用级 Do/Don't：

> "Don't create complex and detailed apps such as a calendar grid"

⚠️ 推断，页面未明示：从「一次只做一两件事」「显示与导航内联」「浅层级」可以推出**深层级容器（多级抽屉、嵌套 tab）不适用**，但这是我的推论，官网未成文。若需要组件级可用性清单，只能查 Compose for Wear OS 的 API 范围，不能引用本文。

## dump 未覆盖的内容（重要缺口）

以下都是实际开发必需、但 m3.material.io 手表章节**没有给**的：

| 缺口 | 状态 |
|---|---|
| 安全区 / chin（圆屏底部黑边）inset 数值 | 未出现「safe area」「inset」相关数值 |
| 字体字号 / 行高 / 字重表 | 只有 Numeral Large、Arc Large 两个名字 |
| 组件尺寸（按钮直径、列表行高、图标尺寸） | 只有 192dp / 225dp 两个屏幕级数字 |
| 组件可用性清单 | 无 |
| 导航组件的具体形态 | 无（仅"内容与导航内联"一句） |
| Tile 与通知的模板规格 | 只说明"有模板"，未给尺寸 |

这一点必须写进仓库：**手表章节是方向性指南，不是可据以像素级实现的规格文档。**

## 开发核对清单

- [ ] 背景用纯黑，不用手机那套带色调 surface
- [ ] 最迟在 192dp 宽的屏上验证不裁切；边距/内边距用百分比
- [ ] 在 > 225dp 的设备上追加内容，而不是等比放大
- [ ] 圆屏：容器与按钮贴边处理，标题用 Arc 文字，纯数字用 Numeral
- [ ] 按钮文字用 `on-*` 角色，禁止 `*-dim` on `*` 的组合
- [ ] 多数页面自己绘制时间文本
- [ ] 滚动页显示 scroll indicator
- [ ] 一次只保留一个主任务，层级 ≤ 2 层，导航内联
- [ ] 离线：offline icon 或 dialog 至少实现一种
- [ ] AOD：限制点亮像素，仅保留进行中体验的必要信息
- [ ] 触感：关键交互强、精细交互弱，优先系统 token，与动效/声音同步
- [ ] 旋转侧键映射为滚动或音量，不拦截系统键
- [ ] 字号放大与粗体开关下不破版

## 来源

- 总览：https://m3.material.io/foundations/watches/overview
- 基础（解剖 / 原则 / 交互模式）：https://m3.material.io/foundations/watches/foundations
- 样式（字体 / 颜色 / 动效 / 触感）：https://m3.material.io/foundations/watches/styles
- 布局（原则 / 标准布局 / Tiles / 通知 / 自适应）：https://m3.material.io/foundations/watches/layout

核验日期：2026-11-16
