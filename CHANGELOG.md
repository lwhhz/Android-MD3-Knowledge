# Changelog

## 2026-11-22 — v2.4 版本口径更新（1.5.0 GA）+ 全站复核 + 新组件/桌面/A2UI

**版本口径（一手来源：release notes 版本表 + 正文条目 + Google Maven）**

- Compose Material3 **1.5.0 已 GA**（2026-11-18；1.6.0-alpha01 为下一轮 alpha），Compose 核心 **1.13.0 stable**（1.14.0-alpha01），`compose-bom:2026.11.01` 为稳定线。此前「RC artifact 存在 ≠ GA」「版本表滞后」的悬案由版本表 + 正文 + Maven 三方闭合。
- 升级前提（官方博客）：**Kotlin 2.2+、compileSdk 37.1**。
- 周边：Wear Compose M3 1.7.2、Jetpack XR Compose 1.0.0-rc01、`androidx.xr.compose.material3` 1.0.0-alpha18、`material3-a2ui` 1.0.0-alpha02。
- 回写：`00-Metadata/version-tracking.md`（新快照 + 待核验清单 3 项勾选）、`02-Components/expressive-components.md`（GA 复核）、`07-Development-Rules/compose-coding-rules.md`（稳定线/预发布线分开）。

**全站复核（375 → 385 页）**

- 2026-11-22 重爬全部 385 页并与 11-16 抓取逐页比对：**356 页无实质变化、19 页有差异、10 页新增、0 页消失**；8 页为 404/空壳。
- 高置信变化：Time pickers 四页新增 **Scroll time picker** 与 Expressive variants；`blog` / `develop` 新增 11-18 的 M3 1.5.0 stable 条目。
- 低置信变化（token 表折叠导致，未改数值）：shape corner-radius / spacing tokens / motion specs / color baseline 等 11 页，列为「待目视复核」。
- 新文件：`00-Metadata/site-changes-2026-11-22.md`；`site-map.md` 更新为 385 页、376 落点、8 空壳，并补 desktop / scroll-fields 行。

**新增内容页**

- `02-Components/scroll-fields.md`：官方四页核对（100/144/186dp 三档、48dp 目标、键盘映射、Compose `ScrollField`）。
- `01-Foundations/desktop.md`：桌面形态四页（多窗格/限宽、rail、side sheet、右键菜单、拖放/缩放、指针与键盘）。
- `03-Android-Compose/compose-1-13.md`：BOM 升级、state-based 文本字段迁移（含 before/after）、FlexBox/Grid 稳定与 Flow 退场、ConstraintLayout/MotionLayout 弃用、渐进模糊/多边形、M3 1.5.0 行为变更表。
- `03-Android-Compose/a2ui.md`：A2UI 协议 + 渲染器、库拆解、surface/catalog 结构、接入骨架、风险（Alpha）。

**修正与同步**

- `02-Components/date-time-picker.md`：补 Scroll time picker 变体与官方选型口径。
- `01-Foundations/xr.md`：删除「Compose 尚不支持 spatial dialog」的旧结论；补 XR Compose 1.0.0-rc01、XR 专用 M3 库、Glimmer、可点目标 48/56dp 口径。
- 索引与计数：README（385 页、16/35/11 篇、37 组件）、`01-Foundations/README`、`02-Components/README`、`03-Android-Compose/README`、`00-Metadata/sources.yaml`。

**示例与归档（最终状态）**

- `09-Sample-Apps/` 由用户清空，根 README 的示例行已删除。
- `06-Figma-Resources/android-compose/` 于 2026-11-22 移出仓库（240 个文件、27MB），归档位置与引用口径见 `06-Figma-Resources/README.md`。

**2026-11-22 决策追加**

- 删除根 README 的「09-Sample-Apps」表格行（示例工程已被用户清空）。
- `03-Android-Compose/adaptive-layout.md` 示例改为**五档断点**（600/840/1200/1600，左闭右开）+ **FlexBox**（含 `FlowRow/FlowColumn` 迁移写法）；导航示例改为五分支、单一 `NavHost`。
- 对 12 个「文本变短」页面做展开复核（点击 `expand_all` + 滚动 + innerText/textContent 双取样）：缺失行全部恢复，仅剩 `expand_all` / `Default, Light` 等控件标签。结论：**折叠渲染导致，未发现官方删除条目**；明细见 `00-Metadata/site-changes-2026-11-22.md` 第三节。

**仍待核验**

- XR / Watch 组件级 dp 规格表（XR 仅可点目标 48/56dp，Watch 仍无 dp 表）。
- 1.6.0-alpha01 的逐项实验注解（升级时按规则 V4 重跑）。
- token 表页面已复核（2026-11-22 展开取样，未发现官方删除；见 site-changes 第三节）。
- 设计令牌逐格对照完成（`05-Design-Tokens/crosscheck-2026-11-22.md`）：以 material3 **1.5.0** sources jar 为基准，字阶 30/30、形状 10/10、动效 12/12 全一致；修正 `colors.json` 中不存在于 ColorScheme 的 `shadow` 角色；三个 tracking 设计值/实现值差异保留双记录。
- 组件级抽样扩到 10 组：新增 Extended FAB（56/80/96）、Tabs、Chips、Menus；发现 **Tabs 设计 64dp vs Compose 渲染 72dp** 的实现差异，已写入 `02-Components/tabs.md`。
- 组件级核对继续扩到 18 组：新增 Divider（1dp）、Icon button（32/40/56/96/136）、Loading（48/38）、Progress（轨道 4dp、圆形默认 40）、Switch/Checkbox/Radio（32×52、18、20）；全部与 1.5.0 令牌一致；Snackbar 双行 64 vs 68 的实现差异已记录。
- 组件级核对接续扩到 23 组：新增 Tooltip（高 24 一致，内边距设计 8 vs 实现垂直 4/水平 8）、Bottom sheet（圆角 28、手柄 32×4）、Toolbar（64/16）、App bar（64/112；152 为 Compose baseline 值）、Card（12dp）；实现差异已写入对应组件页。
- 组件级核对扩到 28 组：新增 Text fields（56、图标 24 获源码证实）、Slider（XS 16/44×4）、Navigation drawer（360/56×336）、Lists（56/72/88）、Dialog（280/560）；Side sheet 未找到令牌，未核。
- 组件级核对扩到 34 组：新增 Split button（32/40/56/96/136）、Segmented（40/1/18）、FAB menu（关闭 56）、Bottom app bar（80，Compose 值）、Time picker（256/48/8/2/216×38）；发现 **Date picker modal 设计 524 vs Compose 568** 的差异，已写入 `date-time-picker.md`。
- 新增 `03-Android-Compose/implementation-deltas.md`：汇总已知设计值 vs Compose 实现差异（Tabs 64/72、Snackbar 64/68、Tooltip 内边距、Date picker 524/568）+ 变体/默认值差异 + 升级重查方法。
- 补「能力/口径缺口」：Side sheet 官方标 Compose Unavailable（无组件可核）、Carousel 用 keyline 比例无 dp 令牌、Docked date picker 无独立尺寸令牌；crosscheck 边界同步更新。
- 补最后三项佐证：Bottom app bar（Compose 80dp，注明仅实现值）、FAB menu（Close 56、item 56、间距 4/8、icon 20/24）、Search bar（56dp；docked 尺寸为设计值、无 Compose 常量）。
- 删除根目录误生成的 `CON`（内容是 git 凭据提示，非项目文件）。
- **归档移出仓库**：`06-Figma-Resources/android-compose/`（240 个文件、27MB，含 13MB APK 与试点/回归文档）移至 `D:\AI项目\安卓软件项目\android-compose-archive`；同步更新 README、`06-Figma-Resources/README.md`、`AI-INSTRUCTIONS.md` 与两处组件页引用。
- **删除本地记忆目录**：`.workbuddy/`（4 个 memory 文件，gitignored）整体删除。


## 2026-11-17 — v2.3 新增示例应用「时序」MD3Clock

**新增 `09-Sample-Apps/`**：知识库第一次带可运行代码。首个示例 `MD3Clock/` 是 5 页时间应用（闹钟 / 世界时钟 / 秒表 / 倒计时 / 日历），自包含 Gradle wrapper 与版本目录，可单独复制出去构建。

**为什么放这个示例**：文档只能说明「规范怎么说」，无法证明「按规范写出来能跑」。示例把 v2.1–v2.2 实证过的 API（`MaterialExpressiveTheme`、`MotionScheme.expressive()`、wavy progress、SplitButton、FAB menu、connected ButtonGroup、`ContainedLoadingIndicator`）全部落到真实界面，并在 README 里做「组件 → 知识库章节」映射，可反向核对。

**版本与来源**：material3 **1.5.0-rc01**（compose-bom-alpha 2026.11.00）、AGP 9.4.1（内建 Kotlin，不应用 `org.jetbrains.kotlin.android`）、Kotlin 2.3.21、compileSdk **37.2**、minSdk 26、JDK 17。版本目录 `gradle/libs.versions.toml` 每条都标注官方来源与查证日期，未凭空填版本。

**验证范围（不夸大）**：`assembleDebug` 通过、`TimeLogicTest` **11/11** 通过（下次触发、星期匹配、一次性闹钟日期、停用、格式化边界、半小时时区偏移）。**真机未测**：构建会话 `adb devices` 为空，滚动 / 断点 / 200% 字号均未在设备上验证，README 已如实标注 ⏳。

**发布**：Release `sample-md3clock-v1.0.0` 附 debug 签名 APK 与源码 zip。debug 签名仅供试装，不用于分发。

**边界说明（写进 README，避免误解）**：闹钟只是应用前台提醒，未注册系统 `AlarmManager`、无精确闹钟权限、无开机恢复；「校准」是 1.2s 交互演示，未连网络时间源；持久化只覆盖闹钟/城市/日程。

## 2026-11-16 — v2.2 字阶/动效实证 + 跟进最新约定

**用户约定**：Expressive 组件**不等 stable，直接跟进官方 Maven 最新版本**（写入 `00-Metadata/version-tracking.md`）。执行口径：读 `maven-metadata.xml` 定最新版本 → 下 `-sources.jar` 读注解 → 分层写结论（源码实证 ≠ GA）→ 每次升级重跑。2026-11-16 实测最新：material3 **1.5.0-rc01**（lastUpdated 20261104）、compose-bom-alpha **2026.11.00**，无更高版本。

**#9 扩展字阶（已勾选）**：从 `material3-1.5.0-rc01-sources.jar` 的 `TypeScaleTokens.kt` 实证 15 个 `*Emphasized` 样式名与数值。核心结论：**emphasized 与 baseline 的字号/行高完全相同，只改字重**（Regular 400→500，Medium/Bold 档→700），与官网「不要为强调改字号」一致。写入 `05-Design-Tokens/typography.json` 新增 `compose_values` 字段；与通行值在 letterSpacing 上的细微差异（-0.2 vs -0.25、0.2 vs 0.25）两者并存、不互相覆盖。

**#10 Expressive motion 差异（已勾选）**：`ExpressiveMotionTokens.kt` vs `StandardMotionTokens.kt` 源码实证——effects 完全相同（damping 1.0，stiffness 1600/3800/800）；差异全在 spatial（damping 0.9→0.8/0.6，stiffness 700→380/800）。无「推荐时长差异」，差异是弹簧参数。

**#8 21 个 physics 组件明细（保持待核验）**：源码只有弹簧令牌定义，无「默认走 physics 的 21 个组件」清单；官网 How it works 页只给数量。属官方未列出，不臆造，保持待核验。

**内部矛盾修复（同 v2.1 续）**：typography.md 语言高度 API 表述与勾选对齐；05-Design-Tokens/README.md 的「形状七档」改十档、「动效待核验」改为 spring 数值已获取；README「内容状态」的 `06-Figma-Resources/` 由「🚧 骨架 / 不含资源文件」改为「✅ 部分可用 / 19 张测量图已存档」，与 `asset-index.md` 对齐；删掉与 1/3 字节完全相同的 `date-pickers-2/4.png`（索引原本就注明「未存档重复文件」，文件残留属不一致）。

## 2026-11-16 — v2.1 依赖实证回写（来源分层，不改设计规范数值）

**来源分层**（遵循 `AI-INSTRUCTIONS.md` 与 `00-Metadata/version-tracking.md` 的「结论写法约定」）：
- 一手来源（可直接写结论，注明版本与日期）：Google 官方 Maven（`dl.google.com`）下载的 **BOM POM** 与 **material3 sources jar**；
- 实证来源（写「已实证」，不冒充官方规范）：本机 Gradle 编译、两轮真机联调（手机 Redmi / 平板 OPPO）；
- 次级来源（周报/社区）仍只用于定位，不 upgrade 为结论。

**版本追踪（`00-Metadata/version-tracking.md`）**
- 新增 2026-11-16 核验行：官方 Maven 上 `compose-bom-alpha:2026.11.00` 的 POM 明确列出 `androidx.compose.material3:material3 = 1.5.0-rc01` 与 `...adaptive:adaptive = 1.4.0-alpha03`（一手来源，下载 POM 读取）。
- **冲突保留**：官方 release notes 版本表快照（2026-11-14）仍是「1.4.0 stable / 1.5.0-alpha29、RC 列空」。本次只写「Maven 上存在 rc01 artifact」，**不把 rc01 写成 GA 或 stable**，GA 状态仍待人工确认。
- 待核验清单勾掉 3 项：`1.5.0-rc01` 的真实发布状态（确认 artifact 存在，非 GA）、Expressive 组件 experimental 注解逐项清单、AGP 9 内建 Kotlin 与 compileSdk 要求。

**Expressive 组件（`02-Components/expressive-components.md`）**
- 按 1.5.0-rc01 sources jar 的注解实证逐项标注：Wave 进度 / FloatingToolbar / ToggleButton / ButtonGroup 等类**无** `@ExperimentalMaterial3ExpressiveApi`（已转正）；`LoadingIndicator` / `ContainedLoadingIndicator` 与 `MaterialShapes` 家族**仍标注**该注解。写清「以 1.5.0-rc01 sources 为准，升级需重查」。
- 明确区分：注解状态来自源码（可写结论），真机编译通过只是「已实证」。

**API 名称纠错（编译实证，来源：官方 sources jar + 两轮真机编译）**
- `02-Components/menus.md`、`03-Android-Compose/components-api.md`：`menuAnchor()` 的参数类型在 1.5.0-rc01 是 **`ExposedDropdownMenuAnchorType.PrimaryNotEditable`**，不是 `MenuAnchorType`；原表「1.3 后需要 MenuAnchorType」更新为「1.5.0 起为 ExposedDropdownMenuAnchorType」。
- `03-Android-Compose/adaptive-layout.md`：`WindowWidthSizeClass` 自 material3-adaptive 1.4.0-alpha03 起来自 **`androidx.window.core.layout`**（不再是 `androidx.compose.material3.adaptive`），枚举值为大写 `COMPACT / MEDIUM`；原代码段修正并注明「建议改用五档断点，见 `01-Foundations/layout.md`」。
- `02-Components/button-group.md`、`02-Components/segmented-button.md`：源码中组形态用 `ButtonGroupDefaults.connectedLeading/Middle/TrailingButtonShapes()`；纠正文案中「ButtonGroup 需 @OptIn」为「按 1.5.0-rc01 sources 该类无实验注解」。

**编码规则（`07-Development-Rules/compose-coding-rules.md`）**
- V1 示例补 2026-11-16 实证：AGP 9.0 起内建 Kotlin，**不再应用 `org.jetbrains.kotlin.android` 插件**（编译实证 + kotl.in 官方说明）；Compose 1.13.0-rc01 要求 compileSdk ≥ 37.1（实测 37.2），需显式声明 JUnit 依赖（AGP 9 不再隐式提供）。
- 不修改 V2 的「版本号只从官方来源取」原则；新增的 alpha BOM 只作为「Expressive 预发布线选项」注明，不作为稳定线推荐。

**内部矛盾修复（2026-11-16）**
- `01-Foundations/typography.md:137` 原写「Compose 语言高度 API 尚未确认」，与 `version-tracking.md` 已勾项冲突 → 改为「生效走 `lineHeight` 随配置适配，非独立 Local* API；30 档数值仍待核验」，两处一致。
- `05-Design-Tokens/README.md` 原写「形状七档」「动效待核验」→ 按已核实数据改为「形状十档」「动效 spring 数值已获取（motion.json verified: true）」。属文档同步，未引入新结论。

**设计规范数值不动**：`01-Foundations/layout.md` / `spacing.md` 等页面的 412/360dp、16/24dp、8dp 基线等为官方原值，本轮真机仅验证，未改动。

## 2026-11-16 — v2.0 全站抓取（375 页）后全面重构

**抓取**
- 用 headless Chrome + CDP 渲染 sitemap 全部 **375 个 URL**，取 `innerText` 后落盘（219 万字符，0 失败）。6 个页面实际 404（`whats-new-at-io26`、`material-is-compose-first`、`building-with-m3-expressive`、`m3-expressive-motion-theming`、`blog/get-started`、`blog/search.html`），已单列。
- 生成 `00-Metadata/site-map.md`：375 个页面逐条映射到知识库文件，含每页正文字符数与失效标记。

**01-Foundations 由 7 篇扩到 15 篇**（新增 8 篇）
- `spacing.md`：8dp 基线、18 个 system token 全表（`space0`→`space900`）、组件属性命名从 `-space` 迁到 `padding/margin/gap`、RTL 与 200% 字号不缩间距。
- `icons.md`：四轴（weight / fill / grade / optical size）、四档尺寸、24dp 网格里 20dp 活区 + 2dp padding、keyline 四形、48dp 触控目标。
- `layout.md`：**五档断点 600 / 840 / 1200 / 1600dp**（左闭右开）、compact 边距 16dp 其余 24dp、固定窗格 360dp（expanded）与 412dp（large/xl）、三种 canonical layout、RTL 镜像规则。
- `interaction-states.md`：state layer **hover 8% / focus 10% / press 10% / drag 16%**，图层视觉 40dp 而热区 48dp，每屏最多一个 hover/focus/pressed。
- `content-design.md`：UX 写作、通知文案长度上限、alt text、译文膨胀 1.5 倍、文本缩放下限 200%。
- `design-tokens.md`：`md.ref.*` → `md.sys.*` → `md.comp.*` 三层与使用边界。
- `watches.md` / `xr.md`：Wear（屏宽 192dp 起、纯黑背景、Tiles 不滚动）与 XR（面板 1024×720dp、深度 0.75–5m、目标 56dp）。

**既有页面修正（按官网原文）**
- `color-system.md`：补 HCT 机制、三档对比度、动态取色两源、advanced 三动作。**推翻「配对色保证 3:1」**——只有 `on*` 与父色有保证。
- `typography.md`：补字体家族与 editorial treatments。
- `shape-system.md`：补 shape morph——内置会 morph 的只有 standard button group 与 loading indicator，归 expressive motion，Web 暂不可用。
- `elevation.md`：补「surface container 色阶与 elevation **不绑定**，但重叠容器必须用不同 color role」、scrim 固定 32%。
- `motion.md`：补 how-it-works、**六种转场模式**（container transform / forward-backward / lateral / top level / enter-exit / skeleton）、reduced motion 时连 shape morph 都要禁用。
- `design-principles.md`：删除无法溯源的「三原则 + 优先级」表述，改用可用性五维度与定制化边界。
- `accessibility.md`：补辅助技术与包容性设计；对比度 tone 差 40 保 3:1、差 50 保 4.5:1。

**02-Components 由 29 篇扩到 34 篇**（补 `app-bars` / `icon-buttons` / `extended-fab` / `divider` / `navigation-bar` / `navigation-rail` / `navigation-drawer`，官方 36 组件全覆盖）。这一轮最重要的几处纠错：
- **Navigation 三件套已不再推荐**：baseline nav bar / nav rail / navigation drawer 被 flexible nav bar、collapsed/expanded rail 取代；目的地下限是 **3**（2 个必须用 tabs），80dp 高度等旧数字无官方出处。
- **Segmented button 官方已标不再推荐**，改用 connected button group；button group 组间距 XS 18 / S 12 / M-L-XL 8dp，connected 全档 2dp。
- **Tabs**：indicators 3dp 只属 Primary，**Secondary 是 2dp**；「>4 即拥挤」而非 5–7。
- **Toolbars 不是 TopAppBar**：官方有独立的 docked/floating toolbar，TopAppBar 四变体已迁到 `app-bars.md`。
- **Carousel 现为六种布局**（新增 uncontained multi-aspect ratio），不是两种。
- **Menus 左右内边距 12dp**；菜单项**可以**有 supporting text。
- **Text fields 容器高 56dp**（target size），「4dp 圆角」并无官方出处。
- **Snackbar 有动作时不能自动消失**（`SnackbarDuration.Indefinite`），不是「停留更久」。
- **Slider 颜色角色**里没有 `surfaceContainerHighest`，官方是 `inverseSurface / inverseOnSurface / primary / onPrimary / secondaryContainer / onSecondaryContainer`。
- **Dialog 宽度 280–560dp**；full-screen dialog 是唯一允许叠加的对话框。

**其它**
- `03-Android-Compose/platforms.md`：四平台维护状态（Compose 积极开发 / MDC-Android 维护模式且 1.14 为终版 / Flutter 无 Expressive / Web 维护模式）+ 能力差异表。
- `00-Metadata/blog-index.md`：110 篇博客全量索引 + 20 条「仍然有效」结论 + 明确的过期清单（M2 时代教程禁止引用）。

## 2026-11-15 — v1.11 补完剩余 5 个组件，组件层收口

**新增 5 篇（官方原值）**
- `carousel.md`：左右 16dp / 上下 8dp / 元素间 8dp / **项圆角 28dp**（extraLarge）/ Small 项宽 40-56dp；Contained 与 Uncontained 两种布局。
- `side-sheet.md`：**最大宽度 400dp**、左右内边距 24dp、顶部元素间距 12dp、**底部操作区 72dp（上 16 / 下 24dp 给手势条留位）**；Modal 与 Docked 两形态。
- `split-button.md`：确认 **M3 无此组件、Expressive 新增**；官方 Anatomy 硬规则「后段必须是菜单图标」；**五档尺寸全部取得**（XS/S/M/L/XL 容器高度 32/40/56/96/136dp，含各段内外边距、图标尺寸与三种状态下的内圆角）。修正了初版基于扁平化文本的错位推断，并写明正确读法（按 `token-value-swatch` 节点取值，不要整页复制文本）。
- `fab-menu.md`：**只有一个变体**、三套颜色组（Primary/Secondary/Tertiary）、**关闭按钮始终 56dp**。
- `loading-indicator.md`：与 Progress indicator 的区别（无进度概念）、Default / Contained 两配置、**形状容器 38dp / 占位 48dp**（差值就是必要边距）。

**一个组件消失了**
- **Banner 已不在当前 M3 组件目录**：`/components/banners` 与 `/components/banner` 均 404，组件索引页也无此项。已从「尚未覆盖」清单移除并注明不要再引用历史文档里的 banner。

**收口**
- `02-Components/` 现为 29 篇内容页，M3 主要组件 + Expressive 三个新增组件均有独立页面。
- 索引页把「尚未覆盖」改为说明性小节。

## 2026-11-15 — v1.10 补 5 篇组件 + 官方示例索引 + workflow 改造

**新增 5 篇**
- `tooltip.md`：Plain（24dp 高 / 8dp 内边距，`inverseSurface` 系）与 Rich（上 12 / 下 8 / 左右 16dp，最多 2 个按钮）；重点写了「tooltip 里的信息不能是必需的」这条无障碍硬约束。
- `date-time-picker.md`：日期三变体（Docked / Modal / Modal input）+ 范围；时间两变体（Dial / Input）。时间选择器尺寸为官方原值：容器内边距 24dp、时间选区 96×80dp（24 小时制 114×80dp）、上下午选区竖排 52×80 / 横排 216×38dp。
- `segmented-button.md`：40dp 高 / 1dp 描边 / 最小 12dp 内边距 / 8dp 间距 / 48dp 触控目标；**密度每档减 4dp 且只作用于高度**。
- `button-group.md`：两个变体来路不同 —— **Standard 是 Expressive 全新组件，Connected 就是 segmented button 的进化版**；说明 Standard 用 shape morph 表达交互。
- `swipe-to-dismiss.md`：**官方组件目录里没有该组件的 Specs 页**（实测请求无内容），因此以 Compose API + M3 通则整理，重点写「滑动是快捷方式而非唯一入口」「必须可撤销且撤销窗口内软删除」。

**04-Official-Samples 改为索引而非镜像**
- 实测上游 `android/compose-samples` 当前为 **1017 文件 / 约 106MB**（Jetsnack 32MB、Jetchat 23MB、Reply 21.5MB 均为图片资源），整体镜像会让知识库仓库膨胀且必然过期。
- 改为记录 pinned commit（`25631e8`）+ 六个示例的文件数 / Kotlin 数 / 体积 / 主题入口路径，附「怎么按路径取单个文件」的命令。
- 新增 `compose-samples.manifest.json`（机器可读）与 `.github/scripts/sync_samples.py`。

**workflow 改造**
- `sync-samples.yml` 从「clone 整个仓库再提交」改为「调 API 只更新元数据」，两条 `git` 之外的破坏性风险（大体积提交、浅克隆残留）一并消除。
- README 的两张表用 `<!-- BEGIN GENERATED -->` 标记，由脚本自动重写；`focus` 字段人工维护不被覆盖。
- 脚本本地验证通过：6 个示例、1017 文件、主题路径优选规则（designsystem > 非 tv/wear 的 ui/theme > 其它）。

## 2026-11-15 — v1.9 补 Slider / Progress / Badge，并首次提交 Git

**新增三页（数值均为官方 Measurements 原值）**
- `slider.md`：五档尺寸全部记录 —— 轨道高 16/24/40/56/96dp、handle 高 44/44/52/68/108dp、**handle 宽仅 4dp**（Expressive 是细竖条而非圆点）、轨道圆角 8/8/12/16/28dp、数值标签容器 44×48dp。
- `progress-indicator.md`：四个维度分开（行为 / 形状 / 厚度 / 样式）、代际差异（**可调厚度与 wavy 仅 Expressive**）、默认 4dp 厚度且**距屏幕边缘内缩 4dp**、环形与线性的 token 已统一（旧的分套配置不再推荐）。
- `badge.md`：small 6dp/3dp 圆角、large 单位数字 16dp、最大 16×34dp、偏移 small 6×6 / large 14×12、与文字容器间距 4dp；颜色为 `error`/`onError`（静态色，不随品牌色变化）。

**仓库**
- 完成 `git init` 与首次提交，纳入版本管理。

## 2026-11-15 — v1.8 攻下两个老问题：十档形状 + 弹簧原始值

**方法上的两个突破**
1. **`innerText` → `textContent`**：官网把档位值放在折叠面板里，`innerText` 只返回可见文本，`textContent` 连隐藏节点一起返回。换成 textContent 后形状十档当场就出来了。
2. **GitHub Contents API 取源码**：`raw.githubusercontent.com` 与 `android.googlesource.com` 在本机都不通，但 `api.github.com` 可达，用 `Accept: application/vnd.github.raw` 可直接拿到 `androidx/androidx` 的原始文件。

**形状：七档 → 十档（重大修正）**
- 官网原文「ten styles」，M2 是 3 档（按容器尺寸），M3 是 10 档（按圆角圆润程度）。
- 完整档位：None 0 · XS 4 · S 8 · M 12 · L 16 · **Large increased 20** · XL 28 · **Extra large increased 32** · **Extra extra large 48** · Full。
- 新增非对称说明：对称与非对称共用同一套十档，官方命名有 `extraSmallTop` / `largeTop` / `extraLargeTop` / `largeStart` / `largeEnd`。
- 记录 `Shapes`（五槽位）与 `MaterialShapes`（morph 形状库）不是一回事。
- `shapes.json` 重写为十档 + 非对称 + 已验证的组件默认值（按钮方角 12/16/28、按下 8/12/16、Chip 8、Dialog 28）。

**弹簧：12 组 damping / stiffness 全部到位**
- Standard：spatial 0.9 / (1400 · 700 · 300)，effects 1.0 / (3800 · 1600 · 800)
- Expressive：spatial 0.6·0.8·0.8 / (800 · 380 · 200)，effects 同 Standard
- 三条从数值直接读出的结论：effects 阻尼恒为 1.0（数学上不可能过冲）；spatial 欠阻尼 0.6-0.9；两个 scheme 的 effects 完全相同，Expressive 的「活力」全部来自 spatial。
- `motion.md` 与 `motion.json` 同步，`version-tracking.md` 勾掉两项待核验。

## 2026-11-15 — v1.7 再补 3 篇：Bottom Sheet / Search Bar / Selection Controls

**`bottom-sheet.md`**
- 官方测量：宽度撑满但**最大 640dp**；窗口 > 640dp 时改为上、左右各 56dp 边距（手机上贴边、平板上变悬浮面板）；默认上边距 72dp；拖拽手柄居中、上下内边距 22dp；高度可变。
- 区分 Modal / Standard 两种形态，`sheetState.hide()` 再改状态的关闭写法，`skipPartiallyExpanded` 的取舍。

**`search-bar.md`**
- 官方已定论：Expressive 下 **divided 样式「Not recommended. Use contained.」**，新项目只做 contained。
- 官方测量：容器 56dp 高、**最小 360dp / 最大 720dp**；左右内边距**未聚焦 24dp → 聚焦 12dp**（Expressive 聚焦展开）；图标与文字间距 4dp；Avatar 30dp。
- 标出 Compose 侧 search API 近期有拆分（SearchBar / DockedSearchBar / Expanded*），用前需确认版本。

**`selection-controls.md`（Switch + Checkbox + Radio 合一）**
- 三者的选择判断：Switch 立即生效、Checkbox 先勾后提交、Radio 少量互斥。
- 官方测量：Switch 52×32dp、描边 2dp、handle **未选中 16 → 选中 24 → 按下 28dp**、状态层 40dp；Checkbox 容器 18dp + 2dp 圆角 + 三态；Radio 图标 **20dp**（与 Checkbox 的 18dp **不对称是官方的**）；三者触控目标均 48dp。
- 记录命名变更：官方称 **Handle**，旧文档的 thumb 是同一个东西。
- 给出 `toggleable(role=...)` / `selectableGroup()` 的正确写法，避免重复触发与读屏读不出组关系。

## 2026-11-15 — v1.6 补 4 个组件页 + 弹簧曲线补齐

**新增组件页**
- `snackbar.md`：结构与颜色角色（`inverseSurface` / `inverseOnSurface` / `inversePrimary`）、五种配置形态、`showSnackbar` 挂起语义与 `SnackbarResult` 撤销模式、不参与 elevation 分级的说明。
- `fab.md`：三变体与代际差异（**Medium 仅 Expressive**；**Small 官方标注不再推荐**）、Expressive 的颜色命名变更（原 primary 实为 `primaryContainer`）、状态层 8%/10% 与 hover 时 elevation 3→4、状态层颜色须与图标色一致。
- `chips.md`：四种 Chip 的分工与**尺寸全为官方原值**（32dp 高 / 8dp 圆角 / 18dp 图标 / 16dp 或 8dp 内边距 / 元素间 8dp；Input Chip 头像 24dp、12dp 圆角、关闭图标目标 ≥48dp）、各变体颜色角色、`Dragged` 状态。
- `tabs.md`：Primary/Secondary 两变体、**指示器四条参数**（3dp 高 / 左右各缩进 2dp / 全圆角 / 最短 24dp）、容器 48dp（纯文字）与 64dp（图标+文字）、固定 vs 可滚动阈值、与底部导航和 Filter Chip 的边界。

**动效补齐**
- `motion.md` 增加官方 Web 转换表：12 条弹簧的等效 cubic-bezier 与时长（Expressive fast/default/slow spatial = 350/500/650ms 等），并点出「spatial 曲线 y 值 >1 即过冲」「Standard 三档曲线相同只改时长」。
- 补平台支持差异：Compose 可用、MDC-Android **未进组件**、Web 用曲线模拟。
- 说明复合令牌 = damping + stiffness 两个原始令牌组合；原始数值未在 DOM 中，仍列待核验。
- `05-Design-Tokens/motion.json` 增加 `web_curves` 与 `platform_support`。

## 2026-11-14 — v1.5 层级分级按官方 Tokens 页修正

**修正 `01-Foundations/elevation.md`**
- 补上官方原文前提：**「Surface tint color is deprecated. Use elevation level tokens (0–5) instead.」** 以及 **「Elevation has no shadow or value of its own by default.」**
- 重新界定职责：elevation 只管 **z 轴堆叠关系**，视觉层级靠 surface container 色阶，两者不要混用。
- 按官方组件分级表重写 Level 0-3 的组件清单，并标注 **Level 4 / 5「not assigned as resting level」**，只在 hover / focus 时临时抬升。
- 纠正此前错误：**模态 Dialog 是 Level 3（6dp），不是 5**；**Menu 是 Level 2（3dp），不是 3**；Navigation drawer（模态）是 Level 1；Snackbar 不在分级表里（用 inverse surface）。
- 同步修正 `02-Components/dialogs.md` 与 `menus.md` 中的层级引用。

**形状**
- 核对 `styles/shape/shape-scale-tokens` 当前指向的是 Shape morph 页，**七个档位的 dp 值仍未在 DOM 中以文字呈现**，`shape-system.md` 的 4/8/12/16/28 继续标为通行值。

## 2026-11-14 — v1.4 动效范式与按钮规格按 Specs 页核验

**核验方式**：Chrome 157 headless + CDP，直接读 `Runtime.evaluate('document.body.innerText')`，比 42MB 原始 DOM 干净得多。

**动效：范式已变更（这是本轮最重要的发现）**
- 官网原文：M3 Expressive 引入 **motion physics（弹簧）系统，正在取代 easing + duration**；旧的 easing/duration **「is no longer maintained」**，仅保留给平台转场。
- `01-Foundations/motion.md` 重写：以弹簧为主，讲清两条轴（spatial 会过冲 / effects 不过冲）× 三档速度（fast/default/slow）、令牌命名 `md.sys.motion.spring.{speed}.{type}`、scheme 在产品层指定、跨设备数值不同但相对关系不变、与 Compose springs 兼容。
- 补齐 easing 六个令牌的 Android/CSS 原文值（含 emphasized 在 CSS 上无对应、需用 standard 兜底的坑）。
- 补齐 16 个 duration 令牌（short1-4 / medium1-4 / long1-4 / extra-long1-4 = 50→1000ms）及官方示例配对。
- `05-Design-Tokens/motion.json` 由空壳变为完整令牌表，并标注 `spring: current` / `easing_duration: legacy`。

**按钮：规格页给出的信息比之前多得多**
- 新增「两代能力」对照：**Toggle 按钮、XS/M/L/XL 尺寸、Square 方角形状都只在 M3 Expressive 存在**，M3 只有 default + Small + Round。
- 新增圆角表：圆角按钮全档 Full；方角按钮 12/12/16/28/28dp；**按下态 8/8/12/16/16dp**（形状 morph），并写明圆形与方角按钮的按下形状必须一致、Toggle 静止态在圆/方之间切换。
- 新增五种样式的容器色/内容色对照表（此前只有模糊描述），以及 Toggle 未选中/选中两套颜色。
- 补充官方规则：容器与文字 ≥ 3:1 时允许换别的颜色角色；Elevated 默认 elevation 1、禁用 0；XS/S 图标按钮触控目标必须 ≥ 48dp。
- 标注：Small 的 40dp 高度仍属通行值（官方以图片呈现，未取到文字）。

## 2026-11-14 — v1.3 色彩角色清单按官网原文修正

**核验方式**：web-access skill 的 CDP 通道（Chrome 157）。该页 headless Edge 会 `ERR_TIMED_OUT`，用 Chrome CDP 正常。

**修正**
- `01-Foundations/color-system.md` 重写角色清单，按官网原文：**26 个标准角色分六组**（primary 4 / secondary 4 / tertiary 4 / error 4 / surface 8 / outline 2）。
- 把原先混在「角色」里的一堆项拆出去，归为 **add-on 角色**（Fixed 12 个 + surfaceDim/Bright 2 个 + inverse 3 个），并写上官网态度：大多数产品不需要，不确定就是不需要。
- 新增官网明确、此前遗漏的规则：
  - **Container 是填充色，不能用于文字或图标**。
  - **error 是静态色**，不随 dynamic color 变化，但仍随浅色/深色适配。
  - **Outline 四条禁令**：分隔线用 `outlineVariant`；卡片等多元素组件边框用 `outlineVariant`；可点击边界必须用 `outline`（3:1）；Chip/Button 边框用 `outlineVariant` 的前提是内部内容已达 4.5:1。
  - **跨断点 surface 映射不变**：正文 `surface`、导航 `surfaceContainer`。
  - `surfaceDim` / `surfaceBright` **保持相对明度**，反转方式与 surface 不同。
- `05-Design-Tokens/colors.json` 结构重做：`groups`（26 个）+ `add_on_roles` + `functional_colors`（scrim/shadow）+ `rules`，`verified` 标为 `taxonomy_only`。

**工具侧结论**
- web-access skill 的 CDP 通道可用，但其 `cdp-proxy` 在「手动调试端口」兜底模式下有缺陷（拼出的 WS URL 缺 browser id），且本机全局 `http_proxy` 会拦截 localhost 请求。绕过方式见 `00-Metadata/version-tracking.md`。

## 2026-11-14 — v1.2 浏览器核验修正

**核验方法突破**
- 确认 `m3.material.io` 与 `developer.android.com` 可以用 **headless 浏览器 `--dump-dom`** 渲染出正文（WebFetch 与 curl 都只拿到标题 / 报 fetch failed）。
- 方法与实测结果写入 `00-Metadata/version-tracking.md`，含命令模板、超时页面的处理、以及哪些源仍然不可用。

**据此修正的内容**
- `01-Foundations/typography.md` 重写：
  - 纠正「15 档字阶」为 **30 档 = 15 baseline + 15 emphasized**（官网已确认）。
  - 新增 Emphasized 使用规则：用于已有字重的文字；可与 weight 同用；baseline 与 emphasized 可用不同字体。
  - 新增**语言高度（Language height）**一节：中文属 Medium 类，行高约 +7%；固定高度组件不会自动适配，会出现文字重叠。
  - 补官网明确警告：不要为强调改字号，会影响组件渲染与重排。
- `05-Design-Tokens/typography.json`：新增 `scale_structure` 与 `language_height`，`verified` 标记为 `structure_only`（结构已核，数值未取到）。
- `00-Metadata/version-tracking.md` 版本快照改为以官方版本表为准：**material3 稳定版 1.4.0，最新 alpha 1.5.0-alpha29（表内日期 2026-09-23），RC 列为空**。
- 记录一处冲突：社区周报称 1.5.0-rc01 已发布，但官方版本表 RC 列为空，`1.5.0-rc01` 仅出现在文档代码示例中——标注为待确认，不写成结论。
- 待核验清单勾掉一项、新增两项（30 档数值、语言行高的 Compose API）。

## 2026-11-14 — v1.1 内容填充

**基础层 `01-Foundations/`（7 篇全部完成）**
- 设计原则：M3 三条原则、从任务到组件的推导顺序、一致性边界、Expressive 三问。
- 色彩系统：完整角色表（含 Fixed 组）、配对硬规则、dynamic color 与 fallback、对比度阈值。
- 字体系统：15 档字阶表、按用途选样式、文本缩放约束。
- 形状系统：七档形状、Compose `Shapes` 只有五档这一限制、Expressive 形状变化。
- 层级：Level 0-5、M3「用色表达层级」与表面色阶映射、深色主题限制。
- 动效：四类目的判断、时长区间、缓动选择、Transition 用法、减少动态效果。
- 无障碍：四条硬指标、语义写法、焦点顺序、颜色非唯一载体。

**规则层 `07-Development-Rules/`（3 篇）**
- `android-ui-rules.md`：A-G 七组决策规则，末附冲突优先级（可用性 > 无障碍 > 一致性 > 表现力）。
- `compose-coding-rules.md`：依赖版本、分层结构、状态归属、性能、API 稳定性、测试六类约束。
- `ui-review-checklist.md`：区分为阻断项 / 应修项 / 建议项，每项附验证方法。

**组件层 `02-Components/`（9 篇）**
- 完成 buttons、cards、dialogs、text-fields、navigation、lists、menus、toolbars、expressive-components。
- `buttons.md` 定为完整体例。
- `toolbars.md` 纠正一个常见误解：M3 没有 "Toolbar" 组件。

**实现层 `03-Android-Compose/`（8 篇）**
- material-theme、color-scheme、typography、components-api、navigation、animations、window-insets、adaptive-layout。
- `components-api.md` 提供设计稿名称 → Compose 类名对照表与参数通用约定。

**元数据与令牌**
- `sources.yaml`：新增官方博客、API reference、BOM mapping；新增 `secondary_sources` 分区并明确「只用于定位，不作结论」。
- `version-tracking.md`：记录 2026-11-14 版本快照、核验方法局限（官网正文抓不到）、待核验清单。
- `glossary.md`：扩充为设计系统 / Android 平台 / Compose 编码 / 状态四类。
- `05-Design-Tokens/`：确立「颜色不存值，结构存值」；字阶与形状填值；动效只登记令牌族。
- `README.md`：新增内容状态表与阅读路径。

**记录的重要事实变更**
- Material Android 已转为 **Compose-first**，Material Views（MDC-Android）1.14.0 为最后一个稳定版本。
- material3 已到 1.5.0-rc01，M3 Expressive API 在 1.5.0 线逐步转正；稳定线 BOM 仍为 2026.08.00。
- Compose 1.12 要求 compileSdk 37 / AGP 9.2.0+。

## 2026-10-30 — v1.0 initial structure
- 建立官方来源索引、AI 指令、基础规范和组件主题模板。
- 加入 Compose 示例同步工作流。
- 所有主题细节均需按官方来源持续核验；模板不是 Google 官方原文。
