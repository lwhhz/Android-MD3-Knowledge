# 交互与状态 / Interaction & States

> 官方指南（手势）：https://m3.material.io/foundations/interaction/gestures
> 官方指南（输入）：https://m3.material.io/foundations/interaction/inputs
> 官方指南（选择）：https://m3.material.io/foundations/interaction/selection
> 官方指南（状态）：https://m3.material.io/foundations/interaction/states/overview ｜ /state-layers ｜ /applying-states
> 核验日期：2026-11-16
> 核验方式：m3.material.io 全站 dump（浏览器渲染正文后提取）逐页核对
> 状态：**六种状态的触发方式、state layer 五个透明度值、40dp/48dp 尺寸关系均为官网原文数值**

## 一句话结论

官网原文：**「A state layer is a semi-transparent covering on an element that indicates its state.」**

状态不是「加个灰色」，而是**一层固定透明度的覆盖层**：颜色取自内容色（on 色），每个状态一个固定百分比，尺寸 40dp，而可点区域是 48dp。

## 一、手势

### 官方手势清单

官网对「手势」的定义：**「Gestures are all the ways people interact with UI elements using touch.」** 并强调 **「UI elements should respond to gestures in real time.」**

| 手势 | 官网定义 | 官网示例 |
|---|---|---|
| Tap / 单击 | 通过触摸跳转或与元素交互 | 点卡片打开文章 |
| Double tap / 双击 | 两次快速点击，用于放大或缩小内容 | 双击照片全屏 |
| Long press / 长按 | 按住一段时间以访问附加功能 | 长按列表项进入选择 |
| Scroll and pan / 滚动与平移 | 沿垂直、水平或任意方向滑动表面 | 垂直滚动露出更多内容 |
| Swipe / 滑动 | 水平导航：在同级视图（如 Tab）间切换，或完成某个动作 | 滑动列表项露出操作 |
| Predictive back / 预测式返回 | **仅 Android**：在特定组件上左右滑动返回上一级，滑动过程中可选择继续或留在当前页 | 在 bottom sheet 上返回上一屏 |
| Drag / 拖拽 | 移动元素、把表面滑入或滑出视野 | 拖动列表项重排 |
| Pick up and move / 拿起并移动 | 长按 + 拖拽，用于重排内容 | 把日历事件拖到新时间 |
| Pinch / 捏合 | 缩放表面，用于在屏幕间导航 | 双指外扩把照片放大到全屏 |
| Compound gestures / 复合手势 | 在多种手势间流畅切换 | 地图里同时平移与缩放 |

### 哪些手势是平台级、哪些归组件

| 类型 | 手势 | 依据 |
|---|---|---|
| 平台（Android 系统约定） | Predictive back | 原文限定 **「On Android」**，且只列出兼容组件：bottom sheet、navigation bar、navigation rail、search bar、side sheet |
| 内容/组件级 | Tap、Double tap、Long press、Swipe、Drag、Pick up and move、Pinch | 官网按组件行为描述，未声明为系统保留手势 |
| 选择专用 | 长按触摸、双指触摸 | 见「三、选择模型」 |
| 输入设备手势 | 滚轮滚动、触控板双指拖动、触控板捏合 | 见「二、输入方式」 |

⚠️ 推断，页面未明示：手势页面没有给出「系统级 vs 组件级」的分类表，上表是按原文限定语（「On Android」「Compatible components are」）与本库对平台行为的常识整理。落地时若与 Android 平台返回手势、系统栏手势冲突，以平台为准。

### 手势冲突：官方唯一一条硬规则

> Don't use this gesture combination if it is already in use to pick up and move items, like cards.

即：**长按 + 拖拽不能同时承担「批量选择」与「拿起并移动」两种语义**。这是选择页在「Larger selections」里明确写出的禁止项。

## 二、输入方式

官网把输入定义为：**「Inputs are devices that provide interactive control of an app. Common inputs include a mouse, keyboard, or touchpad.」** 原则是 **「Embrace multiple input methods and gestures within your app.」**

### 五种输入通道与官方要求

| 输入 | 官方要求 | 关键细节 |
|---|---|---|
| 触摸 | 始终显示触摸控件 | **「When interacting using touch, always show touch controls, even if other inputs are connected.」** |
| 鼠标 / 触控板 | 必须显示光标，即使设备本身不识别鼠标 | 设备不识别鼠标/手写笔时，**鼠标按触摸事件处理** |
| 物理键盘 | 虚拟键盘随物理键盘的接入/移除自动隐藏/显示 | 物理键盘应能完成虚拟键盘的全部动作「and more」 |
| 手写笔 | 通常不需要光标，除非光标表达工具属性（笔刷大小、形状） | 笔尖点击应与触摸反馈一致（如 pressed 的 ripple） |
| 语音 | 作为焦点、按下、选择的输入方式之一 | 原文枚举：focused「using a keyboard or voice」；pressed「keyboard or voice input」；selection「via tap, cursor, keyboard, or voice」。dump 无独立语音章节 ⚠️ |

### 鼠标行为对照表

| 设备动作 | 预期行为 |
|---|---|
| 鼠标 / 触控板移动 | 屏幕上显示光标 |
| 主键点击（primary click） | **与触摸事件区别处理**，但反馈要一致（例如显示 pressed 的 ripple） |
| 次键点击（secondary click） | 唤出上下文菜单（单键或触控板双指均可） |
| Hover | 改变组件状态；适用时同时触发 tooltip |
| 高亮（highlight） | 允许用光标选择文本 |
| 滚轮 / 触控板双指拖动 | 垂直与水平滚动列表 |
| 触控板捏合 | 缩放元素或页面 |
| 物理键盘 | 隐藏 / 显示屏幕键盘 |

### 光标形态

| 光标 | 使用场景 |
|---|---|
| Pointer（箭头） | 默认形态 |
| Hand（手型） | 链接、可点击图片 |
| Resize arrows | 可调整大小元素的边界 |
| I-beam | 文本上方；单击定位光标、双击选词、三击选段 |

### 文本选择的两套控件（互斥，按输入源决定）

- 触摸选择：显示触摸控件（选择手柄），**不要**在触摸场景显示右键菜单。
- 鼠标 / 触控板 / 手写笔选择：显示 I-beam 与右键上下文菜单，**不要**在高亮区旁显示触摸控件。
- 触摸拖动文本区 = 滚动；鼠标拖动文本区 = 选中文本与图片。

### 物理键盘约定

| 按键 | 官方预期 |
|---|---|
| Enter | 触发常见动作，如发送消息 |
| Spacebar / 媒体键 | 播放与暂停音视频 |
| Tab | 焦点按逻辑顺序移动（多数页面：从左到右、从上到下），且**必须带可见的环状焦点指示** |
| Escape | 关闭 modal（菜单、对话框、bottom sheet）；移除可见焦点指示并把焦点顺序置 0；结束文本输入但不删除已输入内容 |

## 三、选择模型

官网定义：**「Selection is how people interact with UI elements or choose which items to act on.」** 显示方式为**勾选图标、checkbox 组件、表面颜色变化，或三者组合**。

### 三种选择模型

| 模型 | 承载组件 | 原文依据 |
|---|---|---|
| 单选 | Radio button、Segmented button、Tabs、Navigation bar / drawer / rail | radio button：**「select one option from a set」**；带 active indicator 的导航类组件**同一时刻只能选中一项** |
| 多选 | Checkbox、Chip、List item、Card、Icon button、Menu item、Switch | checkbox：**「select one or more items from a set」**；开关是单项的 on/off |
| 范围 / 批量 | 长按 + 拖拽跨项选择；Date picker 选日期区间；Slider 取值范围内的值 | 批量选择原文：**「To select multiple items simultaneously, long press and drag across items.」** |

### 触摸端

- 进入选择模式：长按条目，或使用快捷方式（如点条目上的头像）。
- 追加选择：逐项点击。
- 退出：逐项点回未选中，或点击工具栏上的动作。
- 批量选择：长按 + 拖拽横扫；**若该手势已被「拿起并移动」占用，则不可复用**。

### 桌面端（指针）

选择是主任务时，checkbox 常显；选择是次任务时，checkbox 仅在两种情况下出现：

1. hover 到某一行时显示该行的单个 checkbox；
2. 一旦有一项被选中，则所有行都显示 checkbox。

### active indicator（导航类专用）

Navigation bar、Navigation drawer、Navigation rail、Tabs 用 **active indicator** 表示当前项，其颜色与形状因组件而异，且**每组件只允许一项被选中**。

## 四、状态系统

官网列出六种状态，并给出两条总则：

> States have two visual indicators to ensure accessibility.

> States can be combined, such as selection and hover.

即：**状态不能只靠一层透明度表达**，必须有两个视觉指示（例如 state layer + 形状/描边/勾选），且状态可以叠加（选中 + hover、聚焦 + 按下）。

| # | 状态 | 官网定义 | 触发方式 | 强调级别 |
|---|---|---|---|---|
| 1 | Enabled | 「communicates an interactive component or element」，使用该组件的默认样式 | 默认 | 基线 |
| 2 | Disabled | 「communicates an inoperable component or element」，通过颜色变化**与降低高度（reduced elevation）**表达 | 不可交互 | 无对比度义务 |
| 3 | Hover | 「when a user has placed a cursor above an interactive element」 | 光标悬停 | 低强调，淡入淡出 |
| 4 | Focused | 「when a user has highlighted an element, using an input method such as a keyboard or voice」 | Tab / 键盘 / 语音 | 高强调 + 环状焦点指示 |
| 5 | Pressed | 「communicates a user tap」，由光标、键盘或语音触发；**触发 composition 变化** | 点击 / 回车 / 语音 | 高强调（ripple） |
| 6 | Dragged | 「when a user presses and moves an element」 | 按住并移动 | 低强调（避免分散注意力） |

唯一性约束（原文）：

| 状态 | 一屏内数量 |
|---|---|
| Hover | 「There can only be one hover state at a time in a layout.」 |
| Focused | 「There can only be one focus state at a time in a layout.」 |
| Pressed | 「There may only be a single pressed state at a time in a layout.」 |
| Dragged | 「There may only be a single dragged state at a time within a layout.」 |
| Disabled | 「There can be any number of disabled states in a layout.」 |

### Disabled 的行为与豁免

- 不能聚焦、不能拖拽、不能按下；点击与 hover 都不改变状态。
- **不继承 state layer**（包括 hover）。
- **「Disabled states don't need to meet Material's contrast requirements.」**
- 动作不可用时：**FAB 应直接不出现**，而不是显示为 disabled。

## 五、State layer

### 构成与层级

| 属性 | 规则 |
|---|---|
| 形态 | 半透明覆盖层，可覆盖整个元素，也可做成圆形覆盖元素局部 |
| 数量 | **同一时刻只能有一层**（「only one state layer can be applied at a given time」） |
| 颜色 | 与内容同色：**「uses the same color as the content」**，默认取图标色或标签文字色（通常是 on 色） |
| 透明度 | 每个状态一个**固定百分比** |
| 尺寸 | **state layer 40dp，可交互目标 48dp** |
| 堆叠顺序 | Container（1）→ State layer（2）→ Content（3） |

颜色推导的两个官方示例：

- 容器用 `secondary container`、内容用 `on secondary container` → state layer 用 `on secondary container`。
- 容器用 `surface`、内容用 `primary` → state layer 用 `primary`。

### 透明度数值（官网原文数值）

| 状态 | 透明度 | 原文 |
|---|---|---|
| Hover | **8%** | 「Hover +8% opacity」 |
| Focus | **10%** | 「Focus +10% opacity」 |
| Press | **10%** | 「Press +10% opacity」 |
| Drag | **16%** | 「Drag +16% opacity」 |
| Disabled | **38%** | 同页数值表「Disabled state layer opacity 0.38」 |

> The state layer uses a fixed percentage for the opacity for each state.

注意两处原文的张力：applying-states 页明确「disabled 不继承 hover 或其他 state layer」，而 state-layers 页数值表仍列出 disabled 0.38。⚠️ 推断，页面未明示：0.38 在实现上对应 disabled 内容/容器的整体降透明，而不是在 disabled 元素上再叠一层。

### State layer 的实现规则

1. **从 enabled 到有状态样式，必须新增一层 state layer**：「To transition from an enabled style to a stateful style requires the addition of a state layer.」
2. 颜色只从内容取，不要另起一套灰；容器与内容互换时，layer 颜色随之改变。
3. 只叠一层。选中 + hover 是两个**状态**叠加，但视觉上仍应按组件规范合成，而不是叠两层各自 8%。
4. Hover 用**低强调**淡入淡出；Focused 用**高强调**；Pressed **高强调**且可带高度变化（按钮、卡片）或 ripple；Dragged **低强调**且可带高度变化（列表项、chip、卡片）。
5. 40dp 的 layer 不等于 48dp 的点击区：视觉小、热区大，两者不要混为一谈。

### 例外：哪些组件不继承状态

| 状态 | 会继承的组件（原文列举） | 不继承的组件（原文列举） |
|---|---|---|
| Disabled | Buttons、Cards、Checkboxes、Chips、List items、Radio buttons、Switches、Text fields | App bars、Badges、Dialogs、FABs、Menus、Navigation bar/drawer/rail、Sheets、Tabs、Tooltips |
| Hover | Buttons、Cards、Checkbox、Chips、Date and time pickers、List items、Slider、Switch、Text fields | App bars、Badges、Dialogs、Menus、Navigation bar/drawer/rail、Sheets、Tabs |
| Focused | Buttons、Cards、Checkbox、Chips、Date and time pickers、List items、Selection controls、Text fields | App bars、Badges、Banner、Card、Dialogs、Navigation bar/drawer/rail、Sheets |
| Pressed | Buttons、Cards、Checkbox、Chips、List items、Text fields | App bars、Badges、Bottom navigation、Dialogs、Menus、Sheets、Tabs |
| Dragged | Cards、Chips、List items、Sliders | App bars、Badges、Buttons、Dialogs、Menus、Navigation bar/drawer/rail |

通用解释只有一句：**容器类组件（app bar、dialog、sheet、nav、tab）不整体继承状态，只让其中真正可点的子元素继承。**

> The individual components that are actionable within the app bar inherit hover states, not the whole app bar.

⚠️ 数据本身的矛盾：focused 一栏同时把「Cards」列进继承、把「Card」列进不继承。官网原文如此，实现时以具体组件页的 specs 为准。

## 落地检查项

- [ ] 每个可交互元素都有 enabled / disabled 两态，且 disabled 不显示 hover 反馈
- [ ] state layer 用的是内容色（on 色），不是自造灰色
- [ ] hover 8%、focus 10%、press 10%、drag 16%，没有随手改成 12%/20%
- [ ] state layer 视觉 40dp，热区 48dp
- [ ] 同一屏最多一个 hover、一个 focus、一个 pressed、一个 dragged
- [ ] 状态至少有两个视觉指示（不靠单层透明度传达）
- [ ] 长按 + 拖拽没有同时用于「批量选择」和「拖动重排」
- [ ] 鼠标接入时显示光标，主键点击有与触摸一致的 ripple
- [ ] Tab 焦点有可见环状指示，Escape 能关闭 modal 并清焦点

## 来源

- https://m3.material.io/foundations/interaction/gestures （核验 2026-11-16）
- https://m3.material.io/foundations/interaction/inputs （核验 2026-11-16）
- https://m3.material.io/foundations/interaction/selection （核验 2026-11-16）
- https://m3.material.io/foundations/interaction/states/overview （核验 2026-11-16）
- https://m3.material.io/foundations/interaction/states/state-layers （核验 2026-11-16）
- https://m3.material.io/foundations/interaction/states/applying-states （核验 2026-11-16）
