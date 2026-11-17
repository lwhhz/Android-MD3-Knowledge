# KB 映射与决策登记

> 对象：本工程（Figma *Material 3 Design Kit (Community)* → Jetpack Compose 复刻）与 [Android-MD3-Knowledge](<D:/AI项目/Android-MD3-Knowledge/README.md>) 的对接。
> 生成日期：2026-11-17。知识库**只读**：本次未改动 KB 任何文件；本工程也未改代码。
> 结果：97 个正式组件中 **88 个有专篇（✅）**、**7 个部分覆盖（◐）**、**2 个无文档（✗）**。

## 一、本次决策

| # | 议题 | 决定 | 影响 |
|---|---|---|---|
| D1 | 依赖代际 | **material3 1.5.0-rc01**（`compose-bom-alpha:2026.11.00`） | 可用 Expressive 组件（Toggle / Split / FAB menu / Button group / FloatingToolbar）；但 1.5.0 尚未 GA，需锁版本 |
| D2 | 视觉口径 | **现行官方值**（以 KB 为准），不沿用社区 Kit 的旧数值 | 状态层 8/10/10/16/38；尺寸按官方测量；Kit 固化的 12% 需改 |
| D3 | 产出 | 本文件：组件→KB 映射 + 冲突登记 | 实施时按映射查文档，不再靠记忆 |
| D4 | 来源与许可 | **Material Design 官方发布**（Figma Community，Figma Partner），**CC BY 4.0** | 可在署名 + 注明修改 + 保留许可声明的前提下转写与登记；KB 登记可按「官方发布」处理 |

## 二、依赖与构建基线（按 D1）

| 项 | 取值 | 依据 |
|---|---|---|
| Compose BOM | `androidx.compose:compose-bom-alpha:2026.11.00` | KB `00-Metadata/version-tracking.md`（官方 Maven POM 实证） |
| material3 | **1.5.0-rc01** | 同上 |
| material3-adaptive | 1.4.0-alpha03 | 同上 |
| compileSdk | 37（minor 2） | 同上（本机实证，非官方规范） |
| AGP / Gradle / Kotlin / JDK | 9.4.1 / 9.8.1 / 2.3.21 / 21 | 同上 |
| 图标依赖 | 显式声明 `material-icons-core`（扩展集才用 `-extended`） | KB `07/compose-coding-rules.md` V3 |
| 测试 | 显式 `junit:junit:4.13.2` | AGP 9 不再隐式提供 |

**风险与纪律**

- 1.5.0 没有 GA 公告，`rc01` 是预发布线；生产使用属工程决策，需锁死版本并禁止自动升级。
- BOM 升级必须单独提交（KB 规则 V4），不与组件补齐混在一起。
- 工程当前**没有 Gradle 构建**，README 里写的 `compose-bom:2024.10.00` 与 D1 冲突 → 登记为待改项（本文件不直接改代码）。

## 三、视觉基线（按 D2）

### 3.1 状态层（官方原文数值）

| 状态 | 透明度 | 触发 | 附加视觉要求 |
|---|---|---|---|
| Enabled | — | 默认 | 组件默认样式 |
| Hover | **8%** | 光标悬停 | 低强调，淡入淡出 |
| Focused | **10%** | Tab / 键盘 / 语音 | 高强调 + 环状焦点指示 |
| Pressed | **10%** | 点击 / 回车 / 语音 | 高强调，可带高度/形状变化 |
| Dragged | **16%** | 按住并移动 | 低强调，可带高度变化 |
| Disabled | **38%** | 不可交互 | 不继承 state layer；不参与对比度要求 |

另两条硬规则：state layer **只有一层**（选中+hover 是状态叠加，不是两层透明度相加）；**40dp layer ≠ 48dp 热区**。

### 3.2 按钮五档（实施首用）

| 档 | 高度 | 纯文字左右内边距 | 图标尺寸 | 图标→文字 | 圆角（方角） | 按下圆角 |
|---|---:|---:|---:|---:|---:|---:|
| XS | 32 | 12 | 20 | 4 | 12 | 8 |
| S | 40 | 16 | 20 | 8 | 12 | 8 |
| M | 56 | 24 | 24 | 8 | 16 | 12 |
| L | 96 | 48 | 32 | 12 | 28 | 16 |
| XL | 136 | 64 | 40 | 16 | 28 | 16 |

### 3.3 颜色

- 颜色**不写死 hex**：走 `ColorScheme` 角色 + Material Theme Builder 导出（KB `05-Design-Tokens/README.md`）。
- 现有 `appbar/M3Tokens.kt` 的 baseline 色值来自社区 Kit，**不是官方值**；保留可用，但必须标注来源，且不得再作为「官方色」引用。
- Kit 里逐变体色值（如 Suggestion chip 48 变体）仍以 Kit 节点为准，但状态层透明度按 3.1 修正。

### 3.4 代际差异（D1 的直接后果）

| 组件 | Compose 入口（1.5.0-rc01） | 注解状态 | 备注 |
|---|---|---|---|
| Toggle button | `ToggleButton` | 无实验注解 | `content` 是 `RowScope` 槽位 |
| Split button | `SplitButtonLayout` / `SplitButtonDefaults` | ⚠️ KB 标注需 `@OptIn` | 官方另注：名称/签名随版本变动，接入前核对 |
| Button group | `ButtonGroup` | 无实验注解 | connected 形态用 `ButtonGroupDefaults.connected*ButtonShapes()` |
| Floating toolbar | `HorizontalFloatingToolbar` / `VerticalFloatingToolbar` | 无实验注解 | 签名含必填 `expanded: Boolean` |
| Loading indicator | `LoadingIndicator` / `ContainedLoadingIndicator` | **仍带** `@ExperimentalMaterial3ExpressiveApi` | 需 `@OptIn`，不外溢 |
| 波浪进度条 | `LinearWavyProgressIndicator` / `CircularWavyProgressIndicator` | 无实验注解 | |
| Shape morph | `MaterialShapes` / `Morph` | **仍带**实验注解 | Shape Set 若用 morph 需 `@OptIn` |

> 以上注解状态只对 1.5.0-rc01 有效；每次升级必须重查（KB `02-Components/expressive-components.md`）。

## 四、冲突登记

| ID | 冲突 | 现状 | 处理 | 状态 |
|---|---|---|---|---|
| C1 | 依赖代际：Kit 是 Expressive 时代，README 却钉 2024.10.00 | 2024.10 线没有 Toggle/Split/FAB menu/Button group/Loading 原生 API | 已切 1.5.0-rc01；README 依赖段已改 | ✅ 已执行（0.1 构建通过） |
| C2 | 状态层：Kit/像素样板用 Pressed·Dragged **12%**，官方是 **10%/16%** | 已改为 10% / 16%，`SUGGESTION_CHIP_PIXEL.md` 同步 | 按 D2 改官方值 | ✅ 已执行（0.5） |
| C3 | 社区 Kit 是否官方 | **已核实**：发布方 Material Design（Figma Partner）；页面明示 Google Terms of Service；官方博客两篇背书；CC BY 4.0。本地 `42Mc2SlwS6zU3VXsHUSaYl` 经缓存核对为**同源副本**：文件名 `Material 3 Design Kit (Community)`、`role: owner`、`lastModified 2026-11-17T09:34:48Z`、`version 2422765368874499395`，文件内含「Material Android is Compose-first」说明；**用户已确认副本仅查看、未编辑**（Figma 现在只能以副本形式使用） | 登记时写「Material Design 发布（Figma Community，CC BY 4.0）」并附证据；以社区页 V1.25 为版本基准，本地副本标注为提取源 | 已核实 |
| C4 | baseline vs flexible：Kit 是 baseline（nav 80dp、Segmented），官方已推 flexible / connected group | 两套数值并存 | 结构按 Kit、数值与 API 按官方；逐组件在映射表「注意」列标注 | 已决策，执行中 |
| C5 | 枚举混装 / 参数名机械音译（工程内） | `Style` 11 值混装、`Configuration` 30 值；已先把与 Compose 冲突的 `Alignment` 改名为 `M3Alignment` | 0.4b 按组件域拆枚举（依赖 COMPONENTS_SPEC 的逐组件选项集）；命名重排随后 | ◐ 部分（0.4b 待做） |
| C6 | KB 无 LICENSE，且自述「不是官方资料包」 | 内容可参考，不宜整段复制 | 只做对照与引用；登记只放元数据与链接 | 长期约束 |

## 五、组件 → KB 映射（97 个正式组件）

> 覆盖统计：✅ 专篇 88；◐ 部分 7；✗ 无 2。
> 「注意」列只标与实现直接相关的代际/口径差异，不重复各页细节；文档路径相对 KB 根目录，`—` 表示无对应文档。

| # | 组件 | 函数 | 文件 | KB 文档 | 覆盖 | 注意 |
|---:|---|---|---|---|:--:|---|
| 1 | App bar | `M3AppBar` | `components/M3AppBar.kt` | 02-Components/app-bars.md | ✅ | 已迁入 components，接入 demo |
| 2 | Bottom app bar | `M3BottomAppBar` | `components/M3BottomAppBar.kt` | 02-Components/app-bars.md | ✅ | 已迁入 components，接入 demo |
| 3 | XR/XR App Bar | `M3XRAppBar` | `M3Appbars.kt` | 01-Foundations/xr.md | ◐ 部分 | XR 只有方向性结论（KB 自评：不足以做完整体验）；结构参考 app-bars.md |
| 4 | Generic avatar | `M3GenericAvatar` | `M3Avatars.kt` | — | ✗ | KB 无 Avatar 文档 |
| 5 | Badges | `M3Badges` | `M3Badges.kt` | 02-Components/badge.md | ✅ | — |
| 6 | Split button | `M3SplitButton` | `M3Buttons.kt` | 02-Components/split-button.md | ✅ | ⚠️ KB 标注需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`，名称/签名随版本变动 |
| 7 | Button | `M3Button` | `M3Buttons.kt` | 02-Components/buttons.md | ✅ | — |
| 8 | Button - text | `M3ButtonText` | `M3Buttons.kt` | 02-Components/buttons.md | ✅ | — |
| 9 | Button - elevated | `M3ButtonElevated` | `M3Buttons.kt` | 02-Components/buttons.md | ✅ | — |
| 10 | Button - outline | `M3ButtonOutline` | `M3Buttons.kt` | 02-Components/buttons.md | ✅ | — |
| 11 | Button - tonal | `M3ButtonTonal` | `M3Buttons.kt` | 02-Components/buttons.md | ✅ | — |
| 12 | Toggle button | `M3ToggleButton` | `M3Buttons.kt` | 02-Components/buttons.md | ✅ | Toggle 仅 Expressive（1.5 线） |
| 13 | Toggle button - elevated | `M3ToggleButtonElevated` | `M3Buttons.kt` | 02-Components/buttons.md | ✅ | Toggle 仅 Expressive（1.5 线） |
| 14 | Toggle button - outline | `M3ToggleButtonOutline` | `M3Buttons.kt` | 02-Components/buttons.md | ✅ | Toggle 仅 Expressive（1.5 线） |
| 15 | Toggle button - tonal | `M3ToggleButtonTonal` | `M3Buttons.kt` | 02-Components/buttons.md | ✅ | Toggle 仅 Expressive（1.5 线） |
| 16 | Icon button togglable | `M3IconButtonTogglable` | `M3Buttons.kt` | 02-Components/icon-buttons.md | ✅ | — |
| 17 | Icon button togglable - tonal | `M3IconButtonTogglableTonal` | `M3Buttons.kt` | 02-Components/icon-buttons.md | ✅ | — |
| 18 | Icon button togglable - outline | `M3IconButtonTogglableOutline` | `M3Buttons.kt` | 02-Components/icon-buttons.md | ✅ | — |
| 19 | Icon button togglable - standard | `M3IconButtonTogglableStandard` | `M3Buttons.kt` | 02-Components/icon-buttons.md | ✅ | — |
| 20 | Standard button group | `M3StandardButtonGroup` | `M3Buttons.kt` | 02-Components/button-group.md | ✅ | — |
| 21 | Icon button | `M3IconButton` | `M3Buttons.kt` | 02-Components/icon-buttons.md | ✅ | — |
| 22 | Icon button - standard | `M3IconButtonStandard` | `M3Buttons.kt` | 02-Components/icon-buttons.md | ✅ | — |
| 23 | Icon button - outline | `M3IconButtonOutline` | `M3Buttons.kt` | 02-Components/icon-buttons.md | ✅ | — |
| 24 | Icon button - tonal | `M3IconButtonTonal` | `M3Buttons.kt` | 02-Components/icon-buttons.md | ✅ | — |
| 25 | FAB menu | `M3FABMenu` | `M3Buttons.kt` | 02-Components/fab-menu.md | ✅ | — |
| 26 | FAB | `M3FAB` | `M3Buttons.kt` | 02-Components/fab.md | ✅ | — |
| 27 | Connected button group | `M3ConnectedButtonGroup` | `M3Buttons.kt` | 02-Components/button-group.md | ✅ | — |
| 28 | Segmented button | `M3SegmentedButton` | `M3Buttons.kt` | 02-Components/segmented-button.md | ✅ | 官方标注不再推荐，替代为 connected button group |
| 29 | Extended FAB | `M3ExtendedFAB` | `M3Buttons.kt` | 02-Components/extended-fab.md | ✅ | — |
| 30 | Stacked card | `M3StackedCard` | `M3Cards.kt` | 02-Components/cards.md | ✅ | — |
| 31 | Horizontal card | `M3HorizontalCard` | `M3Cards.kt` | 02-Components/cards.md | ✅ | — |
| 32 | Carousel | `M3Carousel` | `M3Carousel.kt` | 02-Components/carousel.md | ✅ | — |
| 33 | Carousel - Full screen | `M3CarouselFullScreen` | `M3Carousel.kt` | 02-Components/carousel.md | ✅ | — |
| 34 | Checkboxes | `M3Checkboxes` | `M3Checkboxes.kt` | 02-Components/selection-controls.md | ✅ | — |
| 35 | Filter chip | `M3FilterChip` | `M3Chips.kt` | 02-Components/chips.md | ✅ | — |
| 36 | Assistive chip | `M3AssistiveChip` | `M3Chips.kt` | 02-Components/chips.md | ✅ | — |
| 37 | Input chip | `M3InputChip` | `M3Chips.kt` | 02-Components/chips.md | ✅ | — |
| 38 | Suggestion chip | `M3SuggestionChip` | `M3Chips.kt` | 02-Components/chips.md | ✅ | — |
| 39 | Chip groups | `M3ChipGroups` | `M3Chips.kt` | 02-Components/chips.md | ✅ | — |
| 40 | Modal date picker | `M3ModalDatePicker` | `M3Datetimepickers.kt` | 02-Components/date-time-picker.md | ✅ | — |
| 41 | Input date picker | `M3InputDatePicker` | `M3Datetimepickers.kt` | 02-Components/date-time-picker.md | ✅ | — |
| 42 | Dial picker | `M3DialPicker` | `M3Datetimepickers.kt` | 02-Components/date-time-picker.md | ✅ | — |
| 43 | Docked input date picker [desktop] | `M3DockedInputDatePickerDesktop` | `M3Datetimepickers.kt` | 02-Components/date-time-picker.md | ✅ | — |
| 44 | Keyboard picker | `M3KeyboardPicker` | `M3Datetimepickers.kt` | 02-Components/date-time-picker.md | ✅ | — |
| 45 | Basic dialog | `M3BasicDialog` | `M3Dialogs.kt` | 02-Components/dialogs.md | ✅ | — |
| 46 | List dialog | `M3ListDialog` | `M3Dialogs.kt` | 02-Components/dialogs.md | ✅ | — |
| 47 | Scrollable list dialog | `M3ScrollableListDialog` | `M3Dialogs.kt` | 02-Components/dialogs.md | ✅ | — |
| 48 | XR/XR Dialog | `M3XRDialog` | `M3Dialogs.kt` | 01-Foundations/xr.md | ◐ 部分 | XR 只有方向性结论（KB 自评：不足以做完整体验）；结构参考 dialogs.md |
| 49 | Examples/Layout grid | `M3ExamplesLayoutGrid` | `M3Examples.kt` | 01-Foundations/layout.md | ◐ 部分 | 只覆盖 Canonical Layout / 网格，不含该示例 |
| 50 | List item/List Item: -4 Density (baseline) | `M3ListItemListItem4DensityBaseline` | `M3Lists.kt` | 02-Components/lists.md | ✅ | — |
| 51 | List item/List Item: -2 Density (baseline) | `M3ListItemListItem2DensityBaseline` | `M3Lists.kt` | 02-Components/lists.md | ✅ | — |
| 52 | List item/List Item: 0 Density (baseline) | `M3ListItemListItem0DensityBaseline` | `M3Lists.kt` | 02-Components/lists.md | ✅ | — |
| 53 | List item | `M3ListItem` | `M3Lists.kt` | 02-Components/lists.md | ✅ | — |
| 54 | List | `M3List` | `M3Lists.kt` | 02-Components/lists.md | ✅ | — |
| 55 | List item - Accordion | `M3ListItemAccordion` | `M3Lists.kt` | 02-Components/lists.md | ✅ | — |
| 56 | List Item - Swipe | `M3ListItemSwipe` | `M3Lists.kt` | 02-Components/lists.md | ✅ | — |
| 57 | Circular-indeterminate progress indicator | `M3CircularIndeterminateProgressIndicator` | `M3Loadingprogress.kt` | 02-Components/progress-indicator.md | ✅ | — |
| 58 | Linear-determinate progress indicator | `M3LinearDeterminateProgressIndicator` | `M3Loadingprogress.kt` | 02-Components/progress-indicator.md | ✅ | — |
| 59 | Linear-indeterminate progress indicator | `M3LinearIndeterminateProgressIndicator` | `M3Loadingprogress.kt` | 02-Components/progress-indicator.md | ✅ | — |
| 60 | Circular-determinate progress indicator | `M3CircularDeterminateProgressIndicator` | `M3Loadingprogress.kt` | 02-Components/progress-indicator.md | ✅ | — |
| 61 | Loading indicator | `M3LoadingIndicator` | `M3Loadingprogress.kt` | 02-Components/loading-indicator.md | ✅ | LoadingIndicator 仍带实验注解 |
| 62 | Menu | `M3Menu` | `M3Menu.kt` | 02-Components/menus.md | ✅ | — |
| 63 | Menu item/Standard | `M3MenuItemStandard` | `M3Menu.kt` | 02-Components/menus.md | ✅ | — |
| 64 | Menu item/Vibrant | `M3MenuItemVibrant` | `M3Menu.kt` | 02-Components/menus.md | ✅ | — |
| 65 | Menu (baseline) | `M3MenuBaseline` | `M3Menu.kt` | 02-Components/menus.md | ✅ | — |
| 66 | Navigation Rail | `M3NavigationRail` | `M3Navigation.kt` | 02-Components/navigation-rail.md | ✅ | baseline 80dp 已被 flexible 取代 |
| 67 | Navigation Bar: Horizontal items | `M3NavigationBarHorizontalItems` | `M3Navigation.kt` | 02-Components/navigation-bar.md | ✅ | baseline 已被 flexible 取代 |
| 68 | Navigation Bar: Vertical items | `M3NavigationBarVerticalItems` | `M3Navigation.kt` | 02-Components/navigation-bar.md | ✅ | baseline 已被 flexible 取代 |
| 69 | Navigation Rail: Expanded | `M3NavigationRailExpanded` | `M3Navigation.kt` | 02-Components/navigation-rail.md | ✅ | baseline 80dp 已被 flexible 取代 |
| 70 | XR/XR Navigation Rail | `M3XRNavigationRail` | `M3Navigation.kt` | 01-Foundations/xr.md | ◐ 部分 | XR 只有方向性结论（KB 自评：不足以做完整体验）；结构参考 navigation-rail.md |
| 71 | XR/XR Navigation bar | `M3XRNavigationBar` | `M3Navigation.kt` | 01-Foundations/xr.md | ◐ 部分 | XR 只有方向性结论（KB 自评：不足以做完整体验）；结构参考 navigation-bar.md |
| 72 | Radio buttons | `M3RadioButtons` | `M3Radiobutton.kt` | 02-Components/selection-controls.md | ✅ | — |
| 73 | Search bar | `M3SearchBar` | `M3Search.kt` | 02-Components/search-bar.md | ✅ | — |
| 74 | Search full-screen layout | `M3SearchFullScreenLayout` | `M3Search.kt` | 02-Components/search-bar.md | ✅ | — |
| 75 | Search docked layout | `M3SearchDockedLayout` | `M3Search.kt` | 02-Components/search-bar.md | ✅ | — |
| 76 | Search full-screen layout (baseline) | `M3SearchFullScreenLayoutBaseline` | `M3Search.kt` | 02-Components/search-bar.md | ✅ | — |
| 77 | Search docked layout (baseline) | `M3SearchDockedLayoutBaseline` | `M3Search.kt` | 02-Components/search-bar.md | ✅ | — |
| 78 | Shape Set | `M3ShapeSet` | `M3Shape.kt` | 01-Foundations/shape-system.md | ✅ | 只讲十档圆角与 morph，不含 Shape Set 资源 |
| 79 | Side Sheet | `M3SideSheet` | `M3Sheets.kt` | 02-Components/side-sheet.md | ✅ | — |
| 80 | Bottom sheet | `M3BottomSheet` | `M3Sheets.kt` | 02-Components/bottom-sheet.md | ✅ | — |
| 81 | Standard slider | `M3StandardSlider` | `M3Sliders.kt` | 02-Components/slider.md | ✅ | 五档尺寸已核对 |
| 82 | Centered slider | `M3CenteredSlider` | `M3Sliders.kt` | 02-Components/slider.md | ✅ | 五档尺寸已核对 |
| 83 | Range slider | `M3RangeSlider` | `M3Sliders.kt` | 02-Components/slider.md | ✅ | 五档尺寸已核对 |
| 84 | Snackbar | `M3Snackbar` | `M3Snackbar.kt` | 02-Components/snackbar.md | ✅ | — |
| 85 | .Shape | —（无函数） | — | 01-Foundations/shape-system.md | ◐ 部分 | 构建块，KB 无对应资源文档 |
| 86 | Switch | `M3Switch` | `M3Switch.kt` | 02-Components/selection-controls.md | ✅ | — |
| 87 | Primary tabs/Icon and label | `M3PrimaryTabsIconAndLabel` | `M3Tabs.kt` | 02-Components/tabs.md | ✅ | — |
| 88 | Secondary tabs/Icon and label | `M3SecondaryTabsIconAndLabel` | `M3Tabs.kt` | 02-Components/tabs.md | ✅ | — |
| 89 | Primary tabs/Icon only | `M3PrimaryTabsIconOnly` | `M3Tabs.kt` | 02-Components/tabs.md | ✅ | — |
| 90 | Primary tabs/Label only | `M3PrimaryTabsLabelOnly` | `M3Tabs.kt` | 02-Components/tabs.md | ✅ | — |
| 91 | Secondary tabs/Label only | `M3SecondaryTabsLabelOnly` | `M3Tabs.kt` | 02-Components/tabs.md | ✅ | — |
| 92 | Tabs | `M3Tabs` | `M3Tabs.kt` | 02-Components/tabs.md | ✅ | — |
| 93 | Text field | `M3TextField` | `M3Textfields.kt` | 02-Components/text-fields.md | ✅ | — |
| 94 | Toolbar | `M3Toolbar` | `M3Toolbars.kt` | 02-Components/toolbars.md | ✅ | Compose 无 baseline toolbar；1.5 线才有 FloatingToolbar |
| 95 | XR/XR Toolbar | `M3XRToolbar` | `M3Toolbars.kt` | 01-Foundations/xr.md | ◐ 部分 | XR 只有方向性结论（KB 自评：不足以做完整体验）；结构参考 toolbars.md |
| 96 | Plain Tooltip | `M3PlainTooltip` | `M3Tooltips.kt` | 02-Components/tooltip.md | ✅ | — |
| 97 | Keyboard | `M3Keyboard` | `M3Utilities.kt` | — | ✗ | KB 无 Keyboard / Utilities 文档 |

## 六、页面级索引入口

| 页面 | KB 主文档 | 能查到的关键内容 | 代际/口径注意 |
|---|---|---|---|
| App bars | 02-Components/app-bars.md | 高度 64/112/120；目标 48；边距 4/8/16/24；search / small / medium flexible / large flexible | baseline 已更名为 flexible |
| Avatars | — | 无文档 | 需自建（`CircleShape` + 图片/文字） |
| Badges | 02-Components/badge.md | 规格、颜色角色、`Badge`/`BadgedBox` |  |
| Buttons | buttons / icon-buttons / fab / extended-fab / fab-menu / split-button / button-group / segmented-button | 五档 32/40/56/96/136；内边距 12/16/24/48/64；图标 20/20/24/32/40；圆角 12/12/16/28/28（按下 8/8/12/16/16）；状态层 | Toggle / Split / FAB menu / Button group 仅 Expressive |
| Cards | 02-Components/cards.md | 变体、布局间距 8/12（redline）、颜色角色 |  |
| Carousel | 02-Components/carousel.md | 六种布局、规格 |  |
| Checkboxes | 02-Components/selection-controls.md | 选择模型、状态、Compose API |  |
| Chips | 02-Components/chips.md | 官方测量值、状态、四种 Chip 行为 |  |
| Date & time pickers | 02-Components/date-time-picker.md | docked 360×460、格子 40、modal 360×524、目标 48 | 日期测量多为图 |
| Dialogs | 02-Components/dialogs.md | 规格、full-screen dialog、API |  |
| Examples | 01-Foundations/layout.md | canonical layout、网格、断点 | 不含该示例稿 |
| Lists | 02-Components/lists.md | 结构、规格、`ListItem`/`LazyColumn` |  |
| Loading & progress | progress-indicator.md / loading-indicator.md | 数值、厚度、wavy；加载指示器规格 | `LoadingIndicator` 仍带实验注解 |
| Menu | 02-Components/menus.md | vertical / baseline、规格、API |  |
| Navigation | navigation / navigation-bar / navigation-rail / navigation-drawer | nav bar 6/32/4/6/56/16；rail 80 或 220–360；drawer 规格 | baseline 已被 flexible 取代 |
| Radio button | 02-Components/selection-controls.md | 选择模型、状态、API |  |
| Search | 02-Components/search-bar.md | 两种 style、两种 layout、规格、API |  |
| Shape | 01-Foundations/shape-system.md | 十档 0/4/8/12/16/20/28/32/48/full；Compose 五槽 | 不含 Shape Set 资源 |
| Sheets | bottom-sheet.md / side-sheet.md | 规格、颜色角色、API |  |
| Sliders | 02-Components/slider.md | 五档尺寸逐格核对、状态、API |  |
| Snackbar | 02-Components/snackbar.md | 规格、`SnackbarHost`、API |  |
| Styles | — | 构建块，无文档 |  |
| Switch | 02-Components/selection-controls.md | 选择模型、状态、API |  |
| Tabs | 02-Components/tabs.md | 官方测量值、数量硬门槛 4、API |  |
| Text fields | 02-Components/text-fields.md | 官方测量值、结构、状态、API |  |
| Toolbars | 02-Components/toolbars.md | docked / floating、规格 | Compose 无 baseline toolbar |
| Tooltips | 02-Components/tooltip.md | 规格、plain/rich、API |  |
| Utilities | — | 无文档 | 需自建 |

## 七、覆盖缺口与替代路径

| 缺口 | 数量 | 替代路径 |
|---|---:|---|
| Avatars（Generic avatar） | 1 | KB 无文档；按 `CircleShape` + 文字/图片自建，尺寸用 40/32/24dp 档（Kit 节点为准） |
| Utilities（Keyboard） | 1 | KB 无文档；属工具类而非 M3 组件，可按 Kit 节点自绘或直接删去 |
| Styles（`.Shape`） | 1 | 构建块，无独立文档；Shape 体系见 `01-Foundations/shape-system.md` |
| Examples（Layout grid） | 1 | `01-Foundations/layout.md` 覆盖网格与 canonical layout，但不含该示例稿 |
| XR 变体（App Bar / Nav Rail / Nav Bar / Dialog / Toolbar） | 5 | `01-Foundations/xr.md` 只有方向性结论，KB 自评「不足以做完整体验」；实现时以 Kit 节点 + `01/xr.md` 的硬数字（面板 1024×720dp、目标 56dp 等）为准 |
| 74 个内部构建块 | 74 | KB 不覆盖；本就不建议单独排期，随主线组件覆盖 |

## 八、实施顺序（在缺口清单五阶段前加 0 阶段）

| 步骤 | 内容 | 依据 | 产出 |
|---|---|---|---|
| 0.1 | 建 Gradle 工程，按 D1 配置 BOM / compileSdk / AGP / 图标 / JUnit | 本文第二节 | 能 `assembleDebug` 的空工程 |
| 0.2 | 抽官方数值常量：状态层 5 档、40/48dp、按钮五档 | 本文 3.1/3.2 | `M3Metrics.kt`（状态层 + 尺寸） |
| 0.3 | 用 Theme Builder 导出 `ColorScheme`（浅/深 + dynamic fallback），Kit hex 降级为「Kit 取样」 | KB `05`、`03/color-scheme.md` | `ui/theme/Color.kt` |
| 0.4 | 拆 `M3Enums.kt` 的混装枚举、删 `components/M3Appbars.kt` 重复空壳 | 缺口清单 5.2/5.4 | 枚举按组件域拆分 |
| 0.5 | 修正 `M3SuggestionChipPixel` 的 12% → 10%/16%，更新说明文档 | 冲突 C2 | 像素样板与官方值一致 |
| 1–5 | 按缺口清单的阶段 1→5 补齐，逐组件查第五节映射 | 缺口清单 + KB | 组件实现 + `@Preview` |

**执行状态（2026-11-17）**：0.1 ✅；0.2 ✅；0.3 ◐ 部分（品牌种子色待 Theme Builder 导出）；0.4 ◐ 部分（接入编译 + 修 44 个 API 错误 + 删除重复 AppBar；按域拆枚举留 0.4b）；0.5 ✅（Pressed 10% / Dragged 16%）；阶段 1 试点 Button 家族 ✅（5 样式 × 7/7）；IconButton 八变体 + FAB/Extended FAB ✅；ToggleButton 四变体 ✅；三个已知缺口（Icon selected、IconButton 宽度、FAB menu）已补齐；Text field ✅（10/10）；Checkbox/Radio/Switch ✅；Slider 三档 ✅（垂直方向未做）；Chip 五变体 ✅；Tabs ✅；Badges ✅；Navigation Bar/Rail/Drawer ✅；Menu ✅；Dialog ✅；Snackbar ✅；Search ✅；Progress / Loading ✅；Toolbar ✅；Bottom sheet ✅；Segmented button ✅；Connected button group ✅；Carousel ✅；Date/time pickers ✅；Tooltip / Avatar / Shape ✅；Keyboard / Layout grid ✅（见相关 TRIAL 文档）。

**阶段门（Gate）**：每阶段结束必须同时满足 —— ① `assembleDebug` 通过；② 该批次规格参数 0 条未落地；③ 每档 `State` 有 Preview；④ 过一遍 KB `07/ui-review-checklist.md` 的阻断项。

## 九、未来登记到 `06-Figma-Resources/` 的准入清单（现在不执行）

**触发条件**（用户定义：项目「救活」后）：

1. 工程能构建（0.1 完成，`assembleDebug` 通过）；
2. 阶段 1、2 的 P0 组件按 Gate 验收通过（至少 Button / Text field / Chip / Card / List / Tab / App bar）；
3. C2 已修正（像素样板与官方值一致）。

**登记时需要准备**（对齐 KB `06-Figma-Resources/asset-index.md` 的「Figma Design Kit（待登记）」表）：

| 项 | 内容 | 状态 |
|---|---|---|
| 官方入口 URL | Figma 社区页 `https://www.figma.com/community/file/1035203688168086460/material-3-design-kit` | ✅ 已确认 |
| 发布方 | **Material Design**（带 Figma Partner 标识）；反馈邮箱 `m3-design-kit-feedback@google.com`；支持邮箱 `materialdesignteam@google.com` | ✅ 截图证据（2026-11-17） |
| 官方背书 | m3.material.io 博客《Introducing the M3 design kit for Figma》《What's new in the Material 3 design kit》；页面明示「By downloading this file, you agree to the Google Terms of Service」 | ✅ 已确认 |
| 许可 | **CC BY 4.0**（页面明示） | ✅ 已确认 |
| 版本 | Changelog 05.19.26 **V1.25**；Last updated 6 months ago（≈2026-05） | ✅ 已确认（截图） |
| 文件同一性 | 本地 key `42Mc2SlwS6zU3VXsHUSaYl` 是社区 Kit 的**同源副本**，用户确认仅查看未编辑；Figma 只能以副本使用，社区页原 key 不取 | ✅ 已确认（原 key 不取） |
| Kit 统计 | 页面 28、组件集 171、参数 885；本工程提取日期 2026-11-17 | 已有（本工程） |
| 派生工程 | 本仓库 Compose 转写 + 缺口清单 + 本文 | 部分完成 |

**登记原则**：KB 要求「不要把第三方社区文件标为官方」。本案证据已足够：发布方 Material Design、Google ToS、官方博客背书、CC BY 4.0，登记可写「Material Design 发布（Figma Community，CC BY 4.0）」；但**不镜像 Kit 资源**，只登记元数据、链接与我们的派生工程。

**登记风险**：本地副本 `lastModified` 为 2026-11-17、`version 2422765368874499395`；用户已确认它只被查看、未编辑。提取统计为 28 页 / 171 集 / 885 参数，与 `COMPONENTS_SPEC.md` 一致。登记时把这三项作为与社区页 V1.25 的对齐基准即可。

**CC BY 4.0 义务（转写与登记都要遵守）**：

1. **署名**：注明「Material 3 Design Kit © Google，CC BY 4.0，来源 Figma Community」；
2. **注明修改**：说明本仓库是 Figma → Jetpack Compose 的转写，并标注改动（如状态层按现行官方值修正）；
3. **保留许可声明**：派生工程附带 CC BY 4.0 链接；
4. **不暗示官方背书**：不得让第三方误以为本转写由 Google 背书；
5. ✅ **本工程补声明（已完成）**：README 增加「来源 + 许可 + 修改说明」，仓库根目录新增 `NOTICE`（含 CC BY 4.0、修改清单、非官方声明）。

### 9.1 证据链（2026-11-17）

| # | 证据 | 来源 | 存档 |
|---|---|---|---|
| E1 | 官方博客《Introducing the M3 design kit for Figma》 | `https://m3.material.io/blog/material-3-figma-design-kit` | URL |
| E2 | 官方博客《What's new in the Material 3 design kit》 | `https://m3.material.io/blog/whats-new-design-kit` | URL |
| E3 | 社区页：publisher **Material Design**、Figma Partner、Google ToS、**CC BY 4.0**、Changelog **05.19.26 V1.25**、57,512 赞 | `https://www.figma.com/community/file/1035203688168086460/material-3-design-kit` | [截图](<evidence/figma-community-publisher.png>) |
| E4 | 本地 Kit 元数据：key `42Mc2SlwS6zU3VXsHUSaYl`、28 页 / 171 组件集 / 885 参数 | 本工程 `COMPONENTS_SPEC.md` | 文件 |
| E5 | 本地副本元数据：name `Material 3 Design Kit (Community)`、role `owner`、lastModified `2026-11-17T09:34:48Z`、version `2422765368874499395` | MCP 缓存 `/v1/files` 响应 | 缓存文件 |
| E6 | 原社区页 key（与本地 key 的对应） | 未记录 | ⬜ 待补 |

## 十、验证记录与未执行事项

**已读 KB（只读）**：`README.md`、`AI-INSTRUCTIONS.md`、`00-Metadata/version-tracking.md`、`01-Foundations/interaction-states.md`、`02-Components/buttons.md`、`expressive-components.md`、`README.md`、`05-Design-Tokens/README.md`、`shapes.json`、`typography.json`、`colors.json`、`06-Figma-Resources/asset-index.md`、`07-Development-Rules/compose-coding-rules.md`、`03-Android-Compose/components-api.md`、`09-Sample-Apps/MD3Clock/app/build.gradle.kts` 等。

**外部查证（2026-11-17）**：

- `https://m3.material.io/blog/material-3-figma-design-kit` 标题《Introducing the M3 design kit for Figma》（正文需 JS 渲染，仅取到 meta 与标题）。
- `https://m3.material.io/blog/whats-new-design-kit` 标题《What's new in the Material 3 design kit》。
- `https://www.figma.com/community/file/1035203688168086460/material-3-design-kit` 存在，描述与 M3 Kit 一致，57,512 赞。
- 用户提供的 Figma 社区页截图（2026-11-17）：publisher **Material Design**、Made by a Figma Partner、**CC BY 4.0**、Changelog **05.19.26 V1.25**；存档为 [evidence/figma-community-publisher.png](<evidence/figma-community-publisher.png>)。
- 本地 MCP 缓存（`/v1/files` 响应）核对：name `Material 3 Design Kit (Community)`、role `owner`、lastModified `2026-11-17T09:34:48Z`、version `2422765368874499395`、28 个 canvas；用户确认副本仅查看未编辑。
- 0.1 构建验证（2026-11-17）：`gradlew --offline :app:assembleDebug` → **BUILD SUCCESSFUL**；产物 `app/build/outputs/apk/debug/app-debug.apk`（13.3 MB，含 appbar/ + components/）。环境：Microsoft OpenJDK 21、Gradle 9.8.1、AGP 9.4.1、compileSdk 37.2；JBR 在本沙箱内写工作区被拒，切 JDK 后通过；离线用 `GRADLE_RO_DEP_CACHE`，`navigationevent` 以 constraint 指向缓存中的 1.1.2。

**未执行**：未修改 KB；未登记 Kit 到 KB（触发条件见第九节）；0.3 品牌色、0.4 枚举拆分/去重、0.5 像素样板修正均未开始。**已执行**：0.1 工程与依赖、0.2 数值常量、README/NOTICE 许可声明。

## 附：参考链接

- KB 根目录：[Android-MD3-Knowledge](<D:/AI项目/Android-MD3-Knowledge/README.md>)
- 依赖/版本：[version-tracking.md](<D:/AI项目/Android-MD3-Knowledge/00-Metadata/version-tracking.md>)
- 状态层：[interaction-states.md](<D:/AI项目/Android-MD3-Knowledge/01-Foundations/interaction-states.md>)
- Expressive 状态：[expressive-components.md](<D:/AI项目/Android-MD3-Knowledge/02-Components/expressive-components.md>)
- API 对照：[components-api.md](<D:/AI项目/Android-MD3-Knowledge/03-Android-Compose/components-api.md>)
- 测量图：[asset-index.md](<D:/AI项目/Android-MD3-Knowledge/06-Figma-Resources/asset-index.md>)
- 评审清单：[ui-review-checklist.md](<D:/AI项目/Android-MD3-Knowledge/07-Development-Rules/ui-review-checklist.md>)