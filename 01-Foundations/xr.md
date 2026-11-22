# XR 空间界面 / Design for Immersive XR

> 官方指南：https://m3.material.io/foundations/xr/design/overview
> 核验日期：2026-11-16
> 核验方式：浏览器渲染 m3.material.io 后提取纯文本，逐页核对 10 个页面（design 4 页 + components 6 页）
> 适用范围：Android XR（`Full space` / `Home space`）。所有数值（dp、米、角度）均为原文；推断处标「⚠️ 推断，页面未明示」。
> 重要前提（原文）：`XR guidelines are primarily intended for designers. Find what's implemented in code in the design kit.`

## 一句话结论

XR 里 **屏幕边界消失，改成「面板 + 深度 + 视野」**：默认面板 **1024×720dp**、距人 **1.75 米**、圆角 **32dp**，活动范围 **0.75–5 米**；主导航控件脱离面板，变成浮在面板旁的 **orbiter（轨道器）**。所有「空间化」（面板、orbiter、空间高度）**只在 full space 可用**，home space 必须退回 2D 平面布局。

## 两种空间：full space vs home space（最重要的分界线）

| | Full space | Home space |
|---|---|---|
| 英文定义（原文） | `Android XR's immersive mode that supports spatial components` | `Compatible with mobile and large screen apps, but doesn't support spatial components` |
| 能否用 spatial panel | 是 | **否** |
| 能否用 orbiter | 是 | **否** |
| 能否用空间高度（dialog 上浮等） | 是 | **否** |
| 组件怎么放 | 放进 orbiter 浮在面板外 | 与正文内容**同一平面**，模拟 2D |
| 现实世界 | 可完全遮挡（headset 全遮挡实现完全沉浸） | 真实世界作为背景，可与其他应用并排 |

> "In home space, an XR app can run side by side with other apps, with the real world in the background. In full space, the XR app takes center stage with immersive, spatial capabilities."

各组件的官方写法高度统一：`In home space, use a regular [组件] on the same plane as the body content to mimic a 2D experience.` —— 这句话在 app bar / nav bar / nav rail / toolbar 四页重复出现。

**开发含义**：能力检测不是可选项。同一套 UI 要在两种空间下走两条渲染路径。

## 术语表（官方 XR terms，逐条原文）

| 术语 | 官方定义 |
|---|---|
| 3D models | `Digital objects rendered with depth and volume` |
| Field of view（视野） | `The area a person can see without turning their head` |
| Full space | `Android XR's immersive mode that supports spatial components` |
| Home space | `Compatible with mobile and large screen apps, but doesn't support spatial components` |
| **Orbiters** | `Floating elements that control the content within spatial panels, full space only` |
| Passthrough | `A blended reality where an XR device displays multiple large apps and the user's physical environment` |
| Spatial elevation | `Displays a component above an app on the Z-axis` |
| Spatial environments | `The 360° 3D virtual worlds people see in an immersive app` |
| **Spatial panels** | `A container for UI elements, interactive components, and immersive content, full space only` |

## 五条设计原则

> "Use depth and expanded space to create believable environments"
> "Map interactions, like gaze and gestures, to real-world expectations"
> "Group UI elements on floating spatial panels"
> "Design for comfort to minimize motion sickness and physical strain"
> "Provide feedback through spatial audio, haptics, and visual cues"

展开后另有四条（Overview 页 Principles 节）：

| 原则 | 关键约束 |
|---|---|
| 用熟悉的模式 | `Material components like buttons and menus help people navigate spatial apps with confidence.` 例：菜单用 elevation 在 3D 中出现 |
| 舒适优先 | `Place content in the center of a person's field of view, and design for different body positions, such as seated, standing, and reclined.` |
| 拥抱深度 | 用 elevation 和 3D 模型制造体积感与空间理解 |
| 无障碍 | `Provide large target sizes, support multimodal inputs, and ensure text is legible against any background.` 硬数字：`In XR, icon buttons should have a 56dp target size and 4dp offset` |

## 空间布局

### 基本单位：spatial panel

> "The fundamental building block of an Android XR app is the spatial panel."

- 面板是「灵活画布」：可装 UI 元素、媒体、spatial video。
- 面板常作为 3D 模型与 orbiter 的**锚点**。
- ⚠️ 注意（官方 Note）：`Spatial panels are available in full space only. They aren't currently available in home space.`

### 尺寸与位置（默认值，可直接写进代码）

| 属性 | 默认值 | 上限 / 范围 |
|---|---|---|
| 启动尺寸 | **1024×720dp** | 最大 **2560×1800dp**；**无最小尺寸** |
| 启动距离 | **1.75 米** | 深度范围 **0.75 m – 5 m** |
| 圆角 | **32dp** | — |
| 垂直中心 | 视线下方 **5°** | — |
| 主内容范围 | 视野中心 **41°** 内 | — |

> "Place the panel's vertical center **5° below eye level** to maximize comfort"
> "Place primary content in the center **41°** of a person's field of view"

深度限制的目的写得很直白：`To avoid system UI conflicts, stay within default movement limits`（最小 0.75 米，最大 5 米）。

### 自适应缩放

> "Spatial panels dynamically scale based on their distance from a person."

- 人可手动放大/缩小面板以看清内容；移动面板时 Android XR **自动**调整尺寸。
- M3 组件用自适应设计保证 `content automatically scales and reflows to remain legible and comfortable at any distance or angle`。
- 从 full space 切到 home space 时：`spatial panels usually stay in the same predictable position`。

### 多面板组合（三种布局）

| 布局 | 定义（原文） | 适用 |
|---|---|---|
| Flat | `Panels are arranged in a straight line.` | 并排比较信息 |
| Curved | `Panels curve around the person.` | 沉浸式媒体、宽幅仪表盘 |
| Arbitrary | `Panels are placed freely in space.` | 多任务 |

### 深度与空间高度

> "Use the Z-axis to create volumetric UI. Unlike 2D elevation which uses shadows, volumetric UI uses actual depth."

这是 XR 与手机在视觉语法上的根本差异：**手机用阴影伪造高度，XR 用真实 Z 轴位移**。

- 空间高度可建立层级：把活跃元素前移、背景元素后推。
- 分层可以把 UI 层在物理上分开：`a scrim can float several centimeters behind a dialog box`（遮罩浮在对话框后方几厘米处）。

### 锚定行为

| 模式 | 做法 |
|---|---|
| World-locked（世界锁定） | 面板固定在房间中的具体位置，如锚在桌上的音乐播放器 |
| Head-locked（头部锁定） | **避免**：`Avoid locking UI directly to the person's head view, as it can feel jarring.` 改用 **lazy follow**：UI 缓缓漂移追上人的移动 |

### 颜色对比与调暗

使用标准 surface token 时，面板自动处理对比度：

- **Passthrough**：房间亮时，系统自动调暗背景，保证 UI 可读。
- **虚拟环境**：面板适配虚拟世界的光照。

## 交互模型

### 总纲

> "XR interactions should be flexible, comfortable, and intuitive. People expect to interact with digital objects just like they do with physical ones."

`A person should be able to switch between hands, eyes, voice, or controllers depending on their posture and preference.` —— **多模态不是加分项，是默认要求**。

### 手势的四条要求

1. **Easy to learn**
2. **Follow familiar patterns**：`such as press, pinch, and swipe on mobile`
3. **Comfortable to use repeatedly**
4. `Support one-handed interactions for essential actions. Don't require large, repetitive arm movements, as they can cause fatigue.`

选择（select）的默认手势是**食指与拇指捏合（pinch）**。

### 手部追踪：两种模式

| 模式 | 机制 | 适用距离 |
|---|---|---|
| Direct（直接） | 触摸、抓取、推挤虚拟物体 | 手臂可及范围（`arm's reach`） |
| Ray-based（射线） | 从手发出类似激光笔的射线，指向远处目标后捏合选中 | 远处元素 |

### 眼动追踪

- 支持 gaze（凝视）与 dwell（驻留）交互。
- `Looking at an object triggers a hover state.` —— 视线落在物体上即触发悬停态。

这是 XR 特有的状态机：**hover 由视线驱动，不由指针驱动**。

### 其他输入

| 输入 | 用途（原文） |
|---|---|
| Voice | 虚拟键盘上的免手文字输入；常用动作命令（`Open Settings`、`Go back`） |
| Mouse & keyboard | 精确操作、文字密集型工作流 |
| 6DoF controllers | 游戏、复杂 3D 操作 |

### 系统导航（必须可用）

> "People should be able to open a navigation menu anywhere, anytime."

系统级导航菜单固定三项：

| 项 | 行为 |
|---|---|
| Go back | `Operates the same as the back gesture on Android phones` |
| Launcher | 回到主屏 |
| Recents | 打开、关闭、切换应用 |

`A navigation menu should always be available using a simple gesture, so people don't get lost.`

### 动效：防晕动症

> "In XR, motion sickness can happen when visual cues disconnect from the inner ear's sense of balance."

四条缓解手段：

| 手段 | 原文 |
|---|---|
| 缓动曲线 | `Use standard easing and long duration motion tokens` |
| 地平线 | `Maintain a stable horizon line` |
| 限制连续位移 | `Limit continuous motion. To switch locations, use teleportation or instant jump instead.` |
| 收窄视野 | `Use tunnel vision or vignetting to reduce the field of view while in motion` |

> **Caution**："Limit use of continuous motion. If required, keep the horizon line stable."

### 反馈（三种通道）

虚拟物体没有物理阻力，因此**必须**用视觉、听觉、触觉补偿：

| 通道 | 做法 |
|---|---|
| 视觉 | hover 图标、焦点指示、涟漪、文字标签、elevation 变化；`To indicate an item is targeted, use spatial elevation or a highlight state.` |
| 空间音频 | 声源定位在 3D 空间；如按钮按下时加 click 声；可提示「该看哪里」或「动作已发生」 |
| 触觉 | 手柄振动可开关，模拟触摸/抓取的手感 |

## 组件在空间中的适配

### 只这五个组件被 XR 适配

> "The following Material components are adapted for XR: App bars / Dialogs / Navigation bar / Navigation rail / Toolbars"

注意这是**封闭列表**：M3 的其他组件（card、chip、FAB 独立使用等）在官网 XR 章节没有对应规范。唯一例外是 nav rail 里的 FAB 变体。

### 位置规则总表（四个 orbiter 对照）

| 组件 | 默认位置 | 允许的 context | 与面板间距 | 越界红线 |
|---|---|---|---|---|
| **App bar** | 顶部居中 | Global（推荐）/ Local | **offset 20dp** 或 **inset 12dp** | 不得压面板超过 **12dp**；宽度不得超出相邻面板 |
| **Nav bar** | 底部居中 | Global（推荐）/ Local | 相邻留 **20dp** 或 **inset 12dp** | 重叠不超过面板高度的**一半**；不得超宽；**禁止放顶部** |
| **Nav rail** | 左/右边缘居中 | **仅 Global** | 相邻留 **20dp** 或 **inset 12dp** | 重叠不超过面板宽度的**一半**；不得超高；**禁止放在面板之间** |
| **Toolbar** | 底部居中 | Local（推荐）/ Global | **offset 20dp** 或 **inset 12dp** | 不得压面板超过 **12dp**；横竖均不得越界；**竖向 toolbar 禁止放在面板之间** |

三条通用红线：**offset 20dp / inset 12dp / 不越出相邻面板的宽或高**。

### App bar

- 变体只有**一个** orbiter，`closely aligns with the small app bar`，可配置**居中或左对齐**。
- 解剖：Container / Headline / Trailing icons / Leading icon。
- 颜色（三选一）：`surface container` / `surface container high` / `surface container highest`。
- **Global context**：居中于所控应用顶部，布局或内容变化时**保持锚定**。
- **Local context**：居中于所控面板顶部，随变化**重新定位**；官方 Caution：较少见，必须只放「只影响该面板」的动作。
- `In most cases, apps should only have one app bar orbiter, placed in global context.`
- 宽度：默认居中于面板，可按需改为左/右对齐（人体工学、RTL）；宽度应调整到留在视野内，**不建议**超出自然视野。

### Nav bar

- 解剖：Container / Icon / Active indicator / Small badge（可选）/ Large badge（可选）/ Large badge label（可选）/ Label text。
- 颜色（四选一）：`surface container` / `surface container high` / `surface container highest` / `tertiary container`。
- Global：居中于应用底部并锚定；Local：居中于面板底部并随内容重排 —— 但官方 Caution：若含影响整体应用的导航项，**必须**放 global。
- 位置语义：**offset = 全局动作；inset = 面板局部动作**。
- inset 时：`should overlap spatial panels by 12dp and no more than half their height`。
- **禁止放面板顶部**：`this area is typically reserved for app bar orbiters or other critical UI elements`。

### Nav rail

两个变体：

| 变体 | 说明 |
|---|---|
| **Contained FAB rail** | FAB 嵌在 rail 内部，紧凑、熟悉 |
| **Spatialized FAB rail** | FAB 自己变成一个 orbiter，放在 rail 外部，强调度更高 |

- 解剖：Container / Active indicator / Large badge / Badge / Large badge label / Label text / Icon / Embedded or spatialized FAB（可选）/ Menu icon（可选）。
- 颜色组合（四组绑定 FAB 配色）：`surface container` + tertiary FAB｜`surface container high` + tertiary fixed dim FAB｜`surface container highest` + tertiary fixed dim FAB｜`tertiary container` + primary FAB。
- **只允许 global context**：`Don't place a navigation rail orbiter in local context or between spatial panels.`
- 垂直对齐可选 top / middle / center。
- 跨度超过两个面板时：`consider using a navigation bar orbiter`（改用底部导航栏 orbiter）。
- Spatialized FAB 与 rail 的间距推荐 **20dp**，可放在 rail 上方或下方，且不得超出相邻面板高度。

> ⚠️ 官网自身存在一处不一致，照实记录：Behavior 节明确写「nav rail **不要**用 local context」，但 Placement 节的 navigation context 列表仍写「inset positioning for local actions that are specific to a spatial panel」。落地时以 Behavior 节的禁令为准（更具体、更强），inset 12dp 只视为几何规则。

### Dialog

- 只有 full space 有空间化 dialog；home space 走通用 dialog 规范。
- 颜色只有两档：`surface container high` 或 `surface container highest`。
- **必须是全产品最高层级**：`if the dialog is surface container high, don't use surface container highest for any other elements`。
- 必须加 scrim：阻止其他内容被选中，直到 dialog 动作完成。
- **只用 basic dialog**：`Avoid using full-screen dialogs in XR. Required actions could appear beyond a person's field of view.`
- 动效：等比缩放 + 淡入淡出；scrim 只淡入淡出；激活时**从应用升至 Z 轴最高静止层**，完成后回到常规静止层；scrim 始终停在应用内容层；`use standard easing and long duration motion tokens`。
- 位置：置于**最高静止层**，且距离要舒适；居中于视野；若不能追踪头部，则居中于应用内容；若能追踪，配置 **lazy follow** 直到用户执行动作。

> **实现警告（原文 Note）**：`Color and elevation for spatial dialogs aren't available in Jetpack Compose yet. These need to be customized manually.`

这是全篇唯一一条明确的「代码尚不支持」信息，优先级最高。

### Toolbar

- 只有一个 orbiter，`closely aligns with the floating toolbar`，可**水平或垂直**。
- 解剖：Container / Placed components。
- 颜色：`surface container` / `surface container high` / `surface container highest` / `tertiary container`。
- **Local context 是推荐用法**（与 app bar 相反）：居中于所控面板底部。
- Global context 也可用但较少见，因为 `toolbars usually contain actions that control a specific panel`。
- 超过 **5 个** item 时可展开/收起，展开时须留在相邻面板边界内；更复杂的可拆成多个 toolbar。
- 默认居中于面板，按横/竖配置可对齐 center / left / right / top / bottom。

> **Caution**：`Limit the use of multiple toolbars to rare cases when additional spatialization improves usability.`（app bar 有同款 Caution）

## 无障碍要求

### 三类使用场景（原文）

1. `Navigate and interact with spatial content using their preferred input method`
2. `Understand the spatial environment and select interactive elements`
3. `Customize the experience to fit their physical posture and comfort`

### 系统级能力（必须兼容）

`Google's TalkBack screen reader, voice to text, live captions, dwell control, magnification, and color inversion and correction`

### 文字与对比度

| 要求 | 值 / 做法 |
|---|---|
| 正文字号 | **最小 14dp** |
| 对比度 | 文字与背景高对比 |
| 背景色 | 用标准色角色（如 `surface container`）而非自定义色，**以便系统自动调暗** |
| 主题 | 同时支持亮色与暗色 |
| 场景 | passthrough 下背景可能是「明亮窗户」或「暗房间」 |

### 身体可达性

- 动作应能用**单手、语音或眼控**完成。
- **禁止双手手势**：`Don't require two-handed gestures.`
- 适配坐姿、站姿、半躺姿。
- 允许重新居中（recenter）和把 UI 拉近。

### 目标尺寸（硬数字）

| 项 | 值 |
|---|---|
| 交互目标 | **≥ 56×56dp** |
| 可视范围（visual affordance） | **≥ 48×48dp** |
| 目标偏移 | **4dp** |
| 重叠 | `Don't overlap targets of different elements.` |
| 缩放 | 目标与图标随父容器或标签文字缩放 |

Overview 页对 icon button 的说法一致：`icon buttons should have a 56dp target size and 4dp offset`。

### 各组件页的无障碍结论（高度一致）

四页都只写同一句话的变体：`XR accessibility guidelines are still evolving. XR [组件] should follow applicable Material [组件] accessibility standards.` —— **没有 XR 专属的组件级无障碍规格**，退回手机端 M3 的对应规范。

## 开发可用性评估（诚实结论）

| 能直接落地 | 只能当方向 |
|---|---|
| 面板默认 1024×720dp / 1.75 m / 32dp 圆角 / 0.75–5 m | 「舒适」「不晕」等定性判断 |
| 视野中心 41°、视线下 5° | 组件内部尺寸（各页只有「见 Measurements 图」而无数字） |
| offset 20dp / inset 12dp / 不越过 12dp | 颜色以外的 token 值 |
| 目标 56dp、可视 48dp、offset 4dp、正文 14dp | 多面板的间距与相对角度 |
| full space / home space 双路径 | 3D 模型与环境的规格 |
| dialog 最高层 + scrim + 只做 basic dialog | 手势的参数（速度、阈值、dwell 时长） |

**判断**：XR 章节比手表章节**硬数字更多**（面板尺寸、距离、角度、目标尺寸、间距），足以支撑「做一个符合规范的 XR 容器骨架 + 五个组件的摆放」，但**不足以做完整体验**——动效 token 数值、手势阈值、面板内部栅格全都没有，需要转 Jetpack XR SDK / Android XR 设计指南。且官方自己声明 `primarily intended for designers`，并以 design kit 为实现真相来源。

## 开发核对清单

- [ ] 检测当前空间：full space 才启用 panel / orbiter / spatial elevation
- [ ] home space 回退为与正文同平面的常规组件
- [ ] 面板默认 1024×720dp、1.75 m、32dp 圆角；限制在 0.75–5 m
- [ ] 面板垂直中心放在视线下 5°，主内容落在中心 41° 视野
- [ ] 避免 head-locked，改用 lazy follow
- [ ] 单面板单 app bar orbiter（global context）；单 toolbar orbiter（local context）
- [ ] nav rail 只放 global，且不置于两面板之间
- [ ] nav bar 只放底部，绝不置顶
- [ ] orbiter 与面板：offset 20dp 或 inset 12dp，重叠不超 12dp，不越出面板宽高
- [x] dialog：basic 形态、最高静止层、scrim；**2026-11-22 更正**：Compose 已提供 `SpatialDialog`（XR Compose 1.0.0-rc01），不再是「尚不支持」
- [ ] 手势：捏合选中、射线选远处、支持单手指令集、不要求大幅挥臂
- [ ] 视线悬停触发 hover 态
- [ ] 动效：standard easing + long duration，稳定地平线，位移用瞬移/传送，位移中加 vignetting
- [ ] 反馈三通道齐全：视觉态 + 空间音频 + 手柄触觉（可关）
- [ ] 目标 ≥ 56dp、可视 ≥ 48dp、间距 4dp、不重叠
- [ ] 正文 ≥ 14dp，背景用标准 surface 角色以便自动调暗
- [ ] 兼容 TalkBack / 语音转文字 / 实时字幕 / dwell / 放大 / 反色
- [ ] 复用手机端 M3 的组件无障碍规范

## 来源

- 设计总览（术语 / 原则 / 组件清单）：https://m3.material.io/foundations/xr/design/overview
- 交互（手势 / 多模态 / 系统导航 / 动效 / 反馈）：https://m3.material.io/foundations/xr/design/interaction
- 布局（面板尺寸 / 深度 / 组合 / orbiter / 锚定）：https://m3.material.io/foundations/xr/design/layout
- 无障碍（字号 / 目标尺寸 / 身体可达性）：https://m3.material.io/foundations/xr/design/accessibility
- 组件总览：https://m3.material.io/foundations/xr/components/overview
- App bars：https://m3.material.io/foundations/xr/components/app-bars
- Dialogs：https://m3.material.io/foundations/xr/components/dialogs
- Nav bar：https://m3.material.io/foundations/xr/components/nav-bar
- Nav rail：https://m3.material.io/foundations/xr/components/nav-rail
- Toolbars：https://m3.material.io/foundations/xr/components/toolbars

核验日期：2026-11-16

## 2026-11-22 更新：Compose 侧落地状态

> 本节把「设计规范」与「Compose 实现」分层记录。设计层数值仍以 m3.material.io 为准；实现层以 Android Developers / Google Maven 为准。

### 1. spatial dialog 已落地（推翻旧结论）

旧版本本页写「dialog …（Compose 尚不支持）」。**2026-11-22 核验：已支持。**

- `androidx.xr.compose.spatial.SpatialDialog` 在 **Jetpack XR Compose 1.0.0-rc01** 中存在（Maven lastUpdated 2026-11-18），官方有 [Spatial dialogs 指南](https://developer.android.com/develop/xr/jetpack-xr-sdk/compose/spatial-dialog) 与 API reference。
- 同一批空间组件还有 `SpatialPanel`（`SubspaceModifier` 控制尺寸/位置）、`Orbiter`、`SpatialPopup`。
- 写法：`SpatialDialog(onDismissRequest, properties, content)`；`SpatialPopup` / `SpatialDialog` 在非空间环境会退化成普通 2D 组件，便于同一套代码两处跑。

### 2. XR 专用 Material3 库

- 工件：`androidx.xr.compose.material3`，版本 **1.0.0-alpha18**（2026-11-18，仍 Alpha）。
- 提供：`SpatialNavigationRail` / `SpatialWideNavigationRail`、`SpatialNavigationBar` / `SpatialShortNavigationBar`、`SpatialBasicAlertDialog`、floating toolbar、list-detail 布局、Home/Full space 切换按钮。
- 全部 **experimental**，需 `@OptIn(ExperimentalMaterial3XrApi::class)`。
- 分支写法：`if (LocalSpatialCapabilities.current.isSpatialUiEnabled) 用 Spatial* else 用标准 M3`。

### 3. 尺寸口径（部分解决）

- XR 质量指南给出可点目标：**最小 48×48dp，推荐 56×56dp 或更大**；本页原有「目标 ≥56dp、可视 ≥48dp」与之一致。
- 设计侧另有 [Scale, sizes, and visual design](https://developer.android.com/design/ui/xr/guides/visual-design)。
- **组件级完整 dp 规格表仍未公开**（面板 1024×720dp、1.75m、圆角 32dp 是本页已有的设计层数值）；该项维持「待核验」，不要用示意图像素反推。

### 4. Jetpack Compose Glimmer（眼镜形态）

- `androidx.xr.glimmer`：面向 **optical see-through（OST）显示**（有线 XR 眼镜 / Display Glasses）的设计系统与 UI 工具包，**不属于 M3 规范**。
- 官方：[Build glanceable UI with Jetpack Compose Glimmer](https://developer.android.com/develop/xr/jetpack-xr-sdk/glimmer) ｜ [设计指南](https://developer.android.com/design/ui/xr/guides/glimmer) ｜ [release notes](https://developer.android.com/jetpack/androidx/releases/xr-glimmer)。
- 与头显 XR 的区别：眼镜是**注视 + 轻交互**、信息卡片刻度优先，不要套用头显的空间面板布局。

### 5. 结论写法（更新）

- 设计规则：「官方 m3 页面显示 …」（截至 2026-11-22）。
- 实现状态：「XR Compose 1.0.0-rc01 中已提供 SpatialDialog …」——**RC 不等于 GA**，生产接入需自行评估。
- 跨版本依赖：`androidx.xr.compose.material3` 仍是 alpha18，升级需重查注解。
