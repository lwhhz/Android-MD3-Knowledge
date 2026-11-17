# Compose 组件实现缺口清单

> 对象：`android-compose/` 目录（Figma *Material 3 Design Kit (Community)* 转写的 Jetpack Compose 代码）。
> 生成方式：解析 `COMPONENTS_SPEC.md` 的组件参数 + 静态分析 32 个 Kotlin 文件的函数签名与函数体。判定基于源码文本；**0.4 已把源码接入编译并修完 44 个 API 错误**，但本清单的“落地率”未重新统计。
> 生成时间：2026-11-17。
> **阶段 1 试点（2026-11-17）**：Button 家族 5 个组件全部实现（35 条参数落地）；IconButton 八变体（含宽度档）、FAB/Extended FAB、FAB menu、ToggleButton 四变体（含 Icon selected）、Text field（10/10）、Checkbox/Radio/Switch（3/3、3/3、4/4）、Slider 三档（7/8、5/6、5/6，垂直方向未做）、Chip 五变体（7/8、8/9、8/8、6/7、2/2）、Tabs（4/4）、Navigation（Bar 1/1+1/1、Rail 4/5、Modal Drawer 额外实现）、Menu（7/7、6/7、6/7、2/2）、Dialog（5/5、3/4、3/4）、Snackbar（4/4）、Search（5/6、4/5、4/5、3/4、3/4）、Progress/Loading（3/3、2/3、3/3、3/4、2/2）、Toolbar（4/5）、Bottom sheet（3/3）、Segmented button（2/2）、Connected button group（5/5）、Carousel（7/9、Full screen 2/2）、Date/time pickers（4/6、3/5、3/3、1/2、2/2）均已实现。详见 [BUTTON_TRIAL.md](<BUTTON_TRIAL.md>)、[ICONBUTTON_FAB_TRIAL.md](<ICONBUTTON_FAB_TRIAL.md>)。下表其余行仍是旧统计。
> **0.4 更新**：`App bar` / `Bottom app bar` 已迁入 `components/` 并接入 demo；Search 系列当前用 `TextField` 占位（`SearchBarState` 构造为 internal）。下表保留原始统计口径。
> 依赖与视觉口径决策、组件→KB 映射见 [KB_COMPONENT_MAP.md](<KB_COMPONENT_MAP.md>)。


## P0 / P1 实施进度（2026-11-18 收尾）

| 优先级 | 组件行 | 规格参数 | 已落地 | 未落地 | 落地率 |
|---|---:|---:|---:|---:|---:|
| P0 | 51 | 315 | 181 | 134 | 57.5% |
| P1 | 33 | 180 | 147 | 33 | 81.7% |
| P2 | 13 | 43 | 18 | 25 | 41.9% |
| **合计** | **97** | **538** | **346** | **192** | **64.3%** |

- 单测：**24 tests / 0 failures**；最新 APK 已真机安装。
- P1 七个组件族：Progress/Loading、Toolbar、Bottom sheet、Segmented button、Connected button group、Carousel、Date/time pickers。
- P2 首批：Plain Tooltip / Generic avatar / Shape Set（+5 条参数）；随后 Keyboard / Layout grid（+4 条）；核对 Extended FAB 旧行（+4 条，实际 4/6）。
- P1 剩余 33 条参数集中在：Date picker 的 MONTH/YEAR/范围、Toolbar 的 docked 垂直与 FAB/scroll、Slider 垂直方向、Sheet 平板响应式等。
- P2 已收尾；**XR 家族明确不推进**，其 25 条参数不计入后续计划（统计仍保留）。
- 本统计按表内行求和（含变体行），不是去重后的设计 token 数。

## 一、判定口径

| 概念 | 定义 |
|---|---|
| 规格参数 | `componentPropertyDefinitions` 转写：97 个正式组件共 538 条（另有 74 个内部构建块 347 条） |
| 已落地 | 该规格参数在函数体中被引用（允许**改名/改类型**，如 `Selected` → `bSelected: Boolean`、`Progress` → `progress: Float`） |
| 未落地 | 函数体从不引用该参数 → 调用方传值不产生任何效果 |
| 缺失的状态轴 | 未落地变体参数携带的选项；例如 `State` 未落地 = Enabled/Hovered/Focused/Pressed/Disabled 五档视觉全缺 |
| 完全空壳 | 函数体没有引用**任何一个**非 `modifier` 参数 |
| 体量 S/M/L | 按未落地参数数与状态档数粗估：S ≤3 项且档≤3；M ≤8 项或档≤12；L 更多。**不是工时承诺** |

## 二、总览

| 指标 | 数值 |
|---|---:|
| 正式组件集（不含 74 个内部构建块） | 97 |
| 有同名 Kotlin 函数 | 96 |
| 无函数 | 1（`.Shape`） |
| 规格参数总数 | 538 |
| **真正影响渲染的规格参数** | **12 条（2.2%）** |
| 签名在、函数体未引用 | 521 条 |
| 签名缺失 | 5 条（Menu 3 个插槽、Toolbar 1 个插槽、`.Shape` 1 个） |
| 完全空壳组件（一个参数都不用） | 23 |
| 仅用通用参数（`tLabel`/`onClick`），无规格参数生效 | 54 |
| 仅自建参数生效（不在规格内） | 8：Checkboxes（`bChecked`）、3 个「indeterminate / loading」指示器（`progress`）、4 个导航项（`bSelected`） |
| 参数真正生效（≥1 条规格参数落地） | 12：Badges、2 个进度指示器、Radio buttons、3 个 Slider、5 个 Tabs 变体 |
| 上四项合计 | 23 + 8 + 54 + 12 = 97 |
| 源码中显式“布局骨架”注释 | 31 处 |
| 已实现样板 | 3 个：`appbar/M3AppBar`、`appbar/M3BottomAppBar`、`components/M3SuggestionChipPixel` |

**一句话结论**：97 个正式组件里 96 个有参数齐全的函数签名，但 538 条规格参数中只有 **12 条（2.2%）** 以改名/改类型的方式勉强影响渲染；其余 **521 条收了不用、5 条连签名都没有**。尺寸、风格、状态、图标四个维度基本都是空的：函数体要么是 `Button { Text(tLabel) }` 这类空壳，要么只用一个自建参数（`sliderValue`/`bSelected`/`progress`）驱动一个维度。

------

## 三、按页面清单

> **参数** = 规格参数数；**已落地/未落地** 见第一节口径；**缺失状态轴** 括号内为档数，完整选项见第四节；**现用参数** 为函数体真正引用的参数（`onClick` 省略）。

### App bars

正式组件 3 个 · 规格参数 15 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| App bar | `M3AppBar` (components/M3AppBar.kt) | 6 | 6 | 0 | — | config/elevation/actions/avatar/search | P0 | S |
| Bottom app bar | `M3BottomAppBar` (components/M3BottomAppBar.kt) | 2 | 2 | 0 | — | icons/fab | P0 | S |
| XR/XR App Bar | `M3XRAppBar` ⚠ (M3Appbars.kt) | 7 | 0 | 7 | Elevation(3)·Configuration(2) | tLabel | P2 | M |

### Avatars

正式组件 1 个 · 规格参数 2 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Generic avatar | `M3GenericAvatar` (M3Avatars.kt) | 2 | 2 | 0 | — | style/letter/image | P2 | S |

### Badges

正式组件 1 个 · 规格参数 2 条 · 已落地 1 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Badges | `M3Badges` (M3Badges.kt) | 2 | 2 | 0 | — | size/small/large/count/label | P2 | S |

### Buttons

正式组件 24 个 · 规格参数 166 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Split button | `M3SplitButton` (M3Buttons.kt) | 8 | 0 | 8 | Size(5)·Color(4)·Leading state(5)·Trailing state(6) | tLabel | P0 | L |
| Button | `M3Button` (M3Buttons.kt) | 7 | 7 | 0 | — | size/shape/state/label/icon | P0 | S |
| Button - text | `M3ButtonText` (M3Buttons.kt) | 7 | 7 | 0 | — | style/size/shape/label/icon | P0 | S |
| Button - elevated | `M3ButtonElevated` (M3Buttons.kt) | 7 | 7 | 0 | — | style/size/shape/label/icon | P0 | S |
| Button - outline | `M3ButtonOutline` (M3Buttons.kt) | 7 | 7 | 0 | — | style/size/shape/label/icon | P0 | S |
| Button - tonal | `M3ButtonTonal` (M3Buttons.kt) | 7 | 7 | 0 | — | style/size/shape/label/icon | P0 | S |
| Toggle button | `M3ToggleButton` (M3ToggleButtons.kt) | 9 | 9 | 0 | — | label/selected/icon/selectedIcon/size/shape/state | P1 | S |
| Toggle button - elevated | `M3ToggleButtonElevated` (M3ToggleButtons.kt) | 9 | 9 | 0 | — | label/selected/icon/selectedIcon/size/shape/state | P1 | S |
| Toggle button - outline | `M3ToggleButtonOutline` (M3ToggleButtons.kt) | 9 | 9 | 0 | — | label/selected/icon/selectedIcon/size/shape/state | P1 | S |
| Toggle button - tonal | `M3ToggleButtonTonal` (M3ToggleButtons.kt) | 9 | 9 | 0 | — | label/selected/icon/selectedIcon/size/shape/state | P1 | S |
| Icon button togglable | `M3IconButtonTogglable` (M3IconButtons.kt) | 8 | 8 | 0 | — | icon/style/size/width/selected/state | P1 | S |
| Icon button togglable - tonal | `M3IconButtonTogglableTonal` (M3IconButtons.kt) | 8 | 8 | 0 | — | icon/style/size/width/selected/state | P1 | S |
| Icon button togglable - outline | `M3IconButtonTogglableOutline` (M3IconButtons.kt) | 8 | 8 | 0 | — | icon/style/size/width/selected/state | P1 | S |
| Icon button togglable - standard | `M3IconButtonTogglableStandard` (M3IconButtons.kt) | 8 | 8 | 0 | — | icon/style/size/width/selected/state | P1 | S |
| Standard button group | `M3StandardButtonGroup` (M3Buttons.kt) | 7 | 0 | 7 | Type(2)·Size(5)·Color(3)·Button type(2) | tLabel | P1 | M |
| Icon button | `M3IconButton` (M3IconButtons.kt) | 6 | 6 | 0 | — | icon/style/size/width/state | P1 | S |
| Icon button - standard | `M3IconButtonStandard` (M3IconButtons.kt) | 6 | 6 | 0 | — | icon/style/size/width/state | P1 | S |
| Icon button - outline | `M3IconButtonOutline` (M3IconButtons.kt) | 6 | 6 | 0 | — | icon/style/size/width/state | P1 | S |
| Icon button - tonal | `M3IconButtonTonal` (M3IconButtons.kt) | 6 | 6 | 0 | — | icon/style/size/width/state | P1 | S |
| FAB menu | `M3FABMenu` (M3FabMenu.kt) | 6 | 6 | 0 | — | items/color/expanded | P1 | S |
| FAB | `M3FAB` (M3Buttons.kt) | 5 | 0 | 5 | Size(3)·Color(6)·State(4) | 仅 onClick | P1 | M |
| Connected button group | `M3ConnectedButtonGroup` (M3ConnectedButtonGroups.kt) | 5 | 5 | 0 | — | type/size/selection | P1 | S |
| Segmented button | `M3SegmentedButton` (M3SegmentedButtons.kt) | 2 | 2 | 0 | — | segments/density/single-multi | P1 | S |
| Extended FAB | `M3ExtendedFAB` (M3Fabs.kt) | 6 | 6 | 0 | — | label/icon/focus/size/color/state | P2 | S |

### Cards

正式组件 2 个 · 规格参数 16 条 · 已落地 0 条 · 完全空壳 2 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Stacked card | `M3StackedCard` (M3Cards.kt) | 10 | 0 | 10 | Style(3)·Layout(2) | 无 | P0 | L |
| Horizontal card | `M3HorizontalCard` (M3Cards.kt) | 6 | 0 | 6 | Style(3)·Layout(2) | 无 | P0 | M |

### Carousel

正式组件 2 个 · 规格参数 11 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Carousel | `M3Carousel` (M3Carousel.kt) | 9 | 7 | 2 | Context(2)·Layout(5) | items/layout/context | P1 | M |
| Carousel - Full screen | `M3CarouselFullScreen` (M3Carousel.kt) | 2 | 2 | 0 | — | items/content | P1 | S |

### Checkboxes

正式组件 1 个 · 规格参数 3 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Checkboxes | `M3Checkboxes` (M3Checkboxes.kt) | 3 | 3 | 0 | — | checked/indeterminate/error/enabled | P0 | S |

### Chips

正式组件 5 个 · 规格参数 34 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Filter chip | `M3FilterChip` (M3Chips.kt) | 9 | 8 | 1 | Style(2) | selected/trailing/state/label | P0 | S |
| Assistive chip | `M3AssistiveChip` (M3Chips.kt) | 8 | 7 | 1 | Configuration(4) | style/leading/trailing/state | P0 | S |
| Input chip | `M3InputChip` (M3Chips.kt) | 8 | 8 | 0 | — | label/avatar/close/selected/state | P0 | S |
| Suggestion chip | `M3SuggestionChip` (M3Chips.kt) | 7 | 6 | 1 | Selected(2) | style/icon/state | P0 | S |
| Chip groups | `M3ChipGroups` (M3Chips.kt) | 2 | 2 | 0 | — | layout/content | P0 | S |

### Date & time pickers

正式组件 5 个 · 规格参数 18 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Modal date picker | `M3ModalDatePicker` (M3Datetimepickers.kt) | 6 | 4 | 2 | Type(3) | DAY/RANGE | P1 | M |
| Input date picker | `M3InputDatePicker` (M3Datetimepickers.kt) | 5 | 3 | 2 | Type(2) | single input | P1 | M |
| Dial picker | `M3DialPicker` (M3Datetimepickers.kt) | 3 | 3 | 0 | — | format/orientation | P1 | S |
| Docked input date picker [desktop] | `M3DockedInputDatePickerDesktop` (M3Datetimepickers.kt) | 2 | 1 | 1 | Type(3) | input only | P1 | S |
| Keyboard picker | `M3KeyboardPicker` (M3Datetimepickers.kt) | 2 | 2 | 0 | — | format | P1 | S |

### Dialogs

正式组件 4 个 · 规格参数 21 条 · 已落地 0 条 · 完全空壳 4 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Basic dialog | `M3BasicDialog` (M3Dialogs.kt) | 5 | 5 | 0 | — | icon/divider/content/actions | P0 | S |
| List dialog | `M3ListDialog` (M3Dialogs.kt) | 4 | 3 | 1 | Icon(2) | title/items/icon | P0 | S |
| Scrollable list dialog | `M3ScrollableListDialog` (M3Dialogs.kt) | 4 | 3 | 1 | Icon(2) | title/items/icon | P0 | S |
| XR/XR Dialog | `M3XRDialog` (M3Dialogs.kt) | 8 | 0 | 8 | Elevation(2)·Show icon(2) | 无 | P2 | M |

### Examples

正式组件 1 个 · 规格参数 2 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Examples/Layout grid | `M3ExamplesLayoutGrid` (M3Examples.kt) | 2 | 2 | 0 | — | window class/regions | P2 | S |

### Lists

正式组件 7 个 · 规格参数 72 条 · 已落地 0 条 · 完全空壳 1 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| List item/List Item: -4 Density (baseline) | `M3ListItemListItem4DensityBaseline` (M3Lists.kt) | 20 | 0 | 20 | Condition(3)·Leading(8)·Trailing(5)·Show overline(2)·Show supporting text(2) | tLabel | P0 | L |
| List item/List Item: -2 Density (baseline) | `M3ListItemListItem2DensityBaseline` (M3Lists.kt) | 20 | 0 | 20 | Condition(3)·Leading(8)·Trailing(5)·Show overline(2)·Show supporting text(2) | tLabel | P0 | L |
| List item/List Item: 0 Density (baseline) | `M3ListItemListItem0DensityBaseline` (M3Lists.kt) | 20 | 0 | 20 | Condition(3)·Leading(8)·Trailing(5)·Show overline(2)·Show supporting text(2) | tLabel | P0 | L |
| List item | `M3ListItem` (M3Lists.kt) | 7 | 0 | 7 | Alignment(2)·State(6)·Selected(2) | 无 | P0 | M |
| List | `M3List` (M3Lists.kt) | 3 | 0 | 3 | Type(6)·Multi-line(2) | tLabel | P0 | M |
| List item - Accordion | `M3ListItemAccordion` (M3Lists.kt) | 1 | 0 | 1 | Accordion(2) | tLabel | P0 | S |
| List Item - Swipe | `M3ListItemSwipe` (M3Lists.kt) | 1 | 0 | 1 | Swipe state(4) | tLabel | P0 | S |

### Loading & progress

正式组件 5 个 · 规格参数 15 条 · 已落地 2 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Circular-indeterminate progress indicator | `M3CircularIndeterminateProgressIndicator` (M3Loadingprogress.kt) | 4 | 3 | 1 | Type(2)·Thickness(2)·Step(2) | type/thickness/track | P1 | S |
| Linear-determinate progress indicator | `M3LinearDeterminateProgressIndicator` (M3Loadingprogress.kt) | 3 | 3 | 0 | — | type/thickness/progress | P1 | S |
| Linear-indeterminate progress indicator | `M3LinearIndeterminateProgressIndicator` (M3Loadingprogress.kt) | 3 | 2 | 1 | Type(2)·Thickness(2)·Step(3) | type/thickness | P1 | S |
| Circular-determinate progress indicator | `M3CircularDeterminateProgressIndicator` (M3Loadingprogress.kt) | 3 | 3 | 0 | — | type/thickness/progress | P1 | S |
| Loading indicator | `M3LoadingIndicator` (M3Loadingprogress.kt) | 2 | 2 | 0 | — | step/container | P1 | S |

### Menu

正式组件 4 个 · 规格参数 23 条 · 已落地 0 条 · 完全空壳 3 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Menu | `M3Menu` (M3Menu.kt) | 7 | 7 | 0 | — | theme/groups/scrollbar/section label | P0 | S |
| Menu item/Standard | `M3MenuItemStandard` (M3Menu.kt) | 7 | 6 | 1 | State(6) | selected/leading/trailing/divider/focus | P0 | S |
| Menu item/Vibrant | `M3MenuItemVibrant` (M3Menu.kt) | 7 | 6 | 1 | State(6) | selected/leading/trailing/divider/focus | P0 | S |
| Menu (baseline) | `M3MenuBaseline` (M3Menu.kt) | 2 | 2 | 0 | — | density/items | P0 | S |

### Navigation

正式组件 6 个 · 规格参数 19 条 · 已落地 0 条 · 完全空壳 2 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Navigation Rail | `M3NavigationRail` (M3Navigation.kt) | 5 | 4 | 1 | Alignment(2)·Nav items(4) | alignment/header/items | P0 | S |
| Navigation Bar: Horizontal items | `M3NavigationBarHorizontalItems` (M3Navigation.kt) | 1 | 1 | 0 | — | layout=HORIZONTAL | P0 | S |
| Navigation Bar: Vertical items | `M3NavigationBarVerticalItems` (M3Navigation.kt) | 1 | 1 | 0 | — | layout=VERTICAL | P0 | S |
| Navigation Rail: Expanded | `M3NavigationRailExpanded` (M3Navigation.kt) | 6 | 0 | 6 | Type(2)·Alignment(2)·Nav items(4) | 无 | P1 | M |
| XR/XR Navigation Rail | `M3XRNavigationRail` (M3Navigation.kt) | 3 | 0 | 3 | Configuration(2) | tLabel,bSelected | P2 | S |
| XR/XR Navigation bar | `M3XRNavigationBar` (M3Navigation.kt) | 3 | 0 | 3 | Segments(3) | tLabel,bSelected | P2 | S |

### Radio button

正式组件 1 个 · 规格参数 3 条 · 已落地 1 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Radio buttons | `M3RadioButtons` (M3Radiobutton.kt) | 3 | 3 | 0 | — | selected/enabled/error | P0 | S |

### Search

正式组件 5 个 · 规格参数 24 条 · 已落地 0 条 · 完全空壳 5 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Search bar | `M3SearchBar` (M3Search.kt) | 6 | 5 | 1 | State(3) | query/suggestions/avatar/icons | P0 | M |
| Search full-screen layout | `M3SearchFullScreenLayout` (M3Search.kt) | 5 | 4 | 1 | Configuration(2) | suggestions/placeholder/dismiss | P0 | M |
| Search docked layout | `M3SearchDockedLayout` (M3Search.kt) | 5 | 4 | 1 | Configuration(2) | suggestions/placeholder/expand | P0 | M |
| Search full-screen layout (baseline) | `M3SearchFullScreenLayoutBaseline` (M3Search.kt) | 4 | 3 | 1 | Configuration(2) | baseline shape/colors | P0 | S |
| Search docked layout (baseline) | `M3SearchDockedLayoutBaseline` (M3Search.kt) | 4 | 3 | 1 | Configuration(2) | baseline shape/colors | P0 | S |

### Shape

正式组件 1 个 · 规格参数 1 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Shape Set | `M3ShapeSet` (M3Shape.kt) | 1 | 1 | 0 | — | 10 shapes | P2 | S |

### Sheets

正式组件 2 个 · 规格参数 8 条 · 已落地 0 条 · 完全空壳 2 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Side Sheet | `M3SideSheet` (M3Sheets.kt) | 5 | 0 | 5 | Type(2)·Show back(2) | 无 | P1 | M |
| Bottom sheet | `M3BottomSheet` (M3Sheets.kt) | 3 | 3 | 0 | — | modal/drag handle/content | P1 | S |

### Sliders

正式组件 3 个 · 规格参数 20 条 · 已落地 3 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Standard slider | `M3StandardSlider` (M3Sliders.kt) | 8 | 7 | 1 | Orientation(2) | value/size/state/stops/indicator/icon | P0 | S |
| Centered slider | `M3CenteredSlider` (M3Sliders.kt) | 6 | 5 | 1 | Orientation(2) | value/size/state/stops/indicator | P0 | S |
| Range slider | `M3RangeSlider` (M3Sliders.kt) | 6 | 5 | 1 | Orientation(2) | values/size/state/stops/indicator | P0 | S |

### Snackbar

正式组件 1 个 · 规格参数 4 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Snackbar | `M3Snackbar` (M3Snackbar.kt) | 4 | 4 | 0 | — | action/lines/close/label | P0 | S |

### Styles

正式组件 1 个 · 规格参数 1 条 · 已落地 0 条 · 无函数 1 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| .Shape | `M3Shape` (M3Shape.kt) | 1 | 1 | 0 | — | 9 variants | P2 | S |

### Switch

正式组件 1 个 · 规格参数 4 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Switch | `M3Switch` (M3Switch.kt) | 4 | 4 | 0 | — | checked/icon/enabled/error | P0 | S |

### Tabs

正式组件 6 个 · 规格参数 31 条 · 已落地 5 条 · 完全空壳 1 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Primary tabs/Icon and label | `M3PrimaryTabsIconAndLabel` (M3Tabs.kt) | 6 | 1 | 5 | State(4) | tLabel,bSelected | P0 | M |
| Secondary tabs/Icon and label | `M3SecondaryTabsIconAndLabel` (M3Tabs.kt) | 6 | 1 | 5 | State(4) | tLabel,bSelected | P0 | M |
| Primary tabs/Icon only | `M3PrimaryTabsIconOnly` (M3Tabs.kt) | 5 | 1 | 4 | State(4) | tLabel,bSelected | P0 | S |
| Primary tabs/Label only | `M3PrimaryTabsLabelOnly` (M3Tabs.kt) | 5 | 1 | 4 | State(4) | tLabel,bSelected | P0 | S |
| Secondary tabs/Label only | `M3SecondaryTabsLabelOnly` (M3Tabs.kt) | 5 | 1 | 4 | State(4) | tLabel,bSelected | P0 | S |
| Tabs | `M3Tabs` (M3Tabs.kt) | 4 | 4 | 0 | — | tabs/index/style/config/layout + badge | P0 | S |

### Text fields

正式组件 1 个 · 规格参数 10 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Text field | `M3TextField` (M3Textfields.kt) | 10 | 10 | 0 | — | value/label/placeholder/supporting/style/state/icons | P0 | S |

### Toolbars

正式组件 2 个 · 规格参数 9 条 · 已落地 0 条 · 完全空壳 2 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Toolbar | `M3Toolbar` (M3Toolbars.kt) | 5 | 4 | 1 | Orientation(2) | configuration/color/content | P1 | S |
| XR/XR Toolbar | `M3XRToolbar` (M3Toolbars.kt) | 4 | 0 | 4 | Elevation(4)·Orientation(2) | 无 | P2 | M |

### Tooltips

正式组件 1 个 · 规格参数 2 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Plain Tooltip | `M3PlainTooltip` (M3Tooltips.kt) | 2 | 2 | 0 | — | type/text | P2 | S |

### Utilities

正式组件 1 个 · 规格参数 2 条 · 已落地 0 条 · 完全空壳 0 个

| 组件 | 函数（文件） | 参数 | 已落地 | 未落地 | 缺失状态轴 | 现用参数 | 级别 | 体量 |
|---|---|---:|---:|---:|---|---|:--:|:--:|
| Keyboard | `M3Keyboard` (M3Utilities.kt) | 2 | 2 | 0 | — | configuration/style | P2 | S |

## 四、重点组件逐项缺口（P0 代表组件）

> 每个组件列出规格参数、已落地/未落地、缺失的状态轴（含全部选项）、当前函数体。其余组件形态相同，见第三节表格。

### App bar · `M3AppBar`（⚠ 与 `appbar/` 同名函数重复）（`components/M3Appbars.kt`）

- 规格参数 **6** 个，已落地 **0** 个；函数体：`// 布局骨架：完整规格见 COMPONENTS_SPEC.md Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }`
- 缺失的变体轴：
  - `Configuration`（6 档）：`Small-centered` / `Small-image` / `Search` / `Small` / `Medium` / `Large`
  - `Elevation`（2 档）：`Flat` / `On-scroll`
- 缺失的非变体参数：`Image`（实例替换）、`Show 1st trailing action`（布尔）、`Show 2nd trailing action`（布尔）、`Show 3rd trailing action`（布尔）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；布尔参数作为显示开关接入布局；实例替换参数接到图标/图片槽（`Painter`），并补 Figma 默认图标。

### Bottom app bar · `M3BottomAppBar`（⚠ 与 `appbar/` 同名函数重复）（`components/M3Appbars.kt`）

- 规格参数 **2** 个，已落地 **0** 个；函数体：`// 布局骨架：完整规格见 COMPONENTS_SPEC.md Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }`
- 缺失的变体轴：
  - `Icons`（4 档）：`1` / `2` / `3` / `4`
- 缺失的非变体参数：`Show FAB`（布尔）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；布尔参数作为显示开关接入布局。

### Button · `M3Button`（`components/M3Buttons.kt`）

- 规格参数 **7** 个，已落地 **0** 个；函数体：`Button(onClick = onClick, modifier = modifier) { Text(tLabel) }`
- 缺失的变体轴：
  - `Type`（2 档）：`Round` / `Square`
  - `Size`（5 档）：`XSmall` / `Small` / `Medium` / `Large` / `XLarge`
  - `State`（5 档）：`Enabled` / `Hovered` / `Focused` / `Pressed` / `Disabled`
- 缺失的非变体参数：`Label text`（文本）、`Show icon`（布尔）、`Icon`（实例替换）、`Show focus indicator`（布尔）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；文本参数接到实际 `Text`（替换硬编码 `tLabel`）；布尔参数作为显示开关接入布局；实例替换参数接到图标/图片槽（`Painter`），并补 Figma 默认图标。

### Text field · `M3TextField`（`components/M3Textfields.kt`）

- 规格参数 **10** 个，已落地 **0** 个；函数体：`var value by remember { mutableStateOf("") } TextField(value = value, onValueChange = { value = it }, label = { Text(tLabel) }, modifier = modifier)`
- 缺失的变体轴：
  - `Style`（2 档）：`Filled` / `Outlined`
  - `State`（5 档）：`Enabled` / `Hovered` / `Focused` / `Error` / `Disabled`
  - `Text configurations`（3 档）：`Input text` / `Label text` / `Placeholder text`
  - `Leading icon`（2 档）：`False` / `True`
  - `Trailing icon`（2 档）：`True` / `False`
- 缺失的非变体参数：`Show supporting text`（布尔）、`Label text`（文本）、`Placeholder text`（文本）、`Input text`（文本）、`Supporting text`（文本）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；文本参数接到实际 `Text`（替换硬编码 `tLabel`）；布尔参数作为显示开关接入布局。

### Stacked card · `M3StackedCard`（`components/M3Cards.kt`）

- 规格参数 **10** 个，已落地 **0** 个；函数体：`Card(onClick = onClick, modifier = modifier) { Text(tLabel, style = M3Typography.titleMedium, modifier = Modifier.padding(16.dp)) }`
- 缺失的变体轴：
  - `Style`（3 档）：`Outlined` / `Elevated` / `Filled`
  - `Layout`（2 档）：`Slot` / `Media & text`
- 缺失的非变体参数：`Slot swap`（实例替换）、`Show secondary action`（布尔）、`Header text`（文本）、`Subhead text`（文本）、`Title text`（文本）、`Subtitle text`（文本）、`Supporting text`（文本）、`Content`（插槽）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；文本参数接到实际 `Text`（替换硬编码 `tLabel`）；布尔参数作为显示开关接入布局；实例替换参数接到图标/图片槽（`Painter`），并补 Figma 默认图标；插槽参数接到 `content: @Composable` 槽。

### Suggestion chip · `M3SuggestionChip`（`components/M3Chips.kt`）

- 规格参数 **7** 个，已落地 **0** 个；函数体：`SuggestionChip(onClick = onClick, label = { Text(tLabel) }, modifier = modifier)`
- 缺失的变体轴：
  - `Style`（2 档）：`Outlined` / `Elevated`
  - `State`（6 档）：`Enabled` / `Hovered` / `Focused` / `Pressed` / `Dragged` / `Disabled`
  - `Selected`（2 档）：`False` / `True`
  - `Show icon`（2 档）：`False` / `True`
- 缺失的非变体参数：`Leading icon`（实例替换）、`Label text`（文本）、`Show focus indicator`（布尔）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；文本参数接到实际 `Text`（替换硬编码 `tLabel`）；布尔参数作为显示开关接入布局；实例替换参数接到图标/图片槽（`Painter`），并补 Figma 默认图标。

### Checkboxes · `M3Checkboxes`（`components/M3Checkboxes.kt`）

- 规格参数 **3** 个，已落地 **0** 个；函数体：`Checkbox(checked = bChecked, onCheckedChange = { onClick() }, modifier = modifier)`
- 缺失的变体轴：
  - `Type`（6 档）：`Selected` / `Unselected` / `Indeterminate` / `Error unselected` / `Error indeterminate` / `Error selected`
  - `State`（5 档）：`Enabled` / `Hovered` / `Focused` / `Pressed` / `Disabled`
- 缺失的非变体参数：`Show focus indicator`（布尔）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；布尔参数作为显示开关接入布局。

### Radio buttons · `M3RadioButtons`（`components/M3Radiobutton.kt`）

- 规格参数 **3** 个，已落地 **1** 个（`Selected`→`bSelected`）；函数体：`RadioButton(selected = bSelected, onClick = onClick, modifier = modifier)`
- 缺失的变体轴：
  - `State`（5 档）：`Enabled` / `Hovered` / `Focused` / `Pressed` / `Disabled`
- 缺失的非变体参数：`Show focus indicator`（布尔）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；布尔参数作为显示开关接入布局。

### Switch · `M3Switch`（`components/M3Switch.kt`）

- 规格参数 **4** 个，已落地 **0** 个；函数体：`Switch(checked = bSelected, onCheckedChange = { onClick() }, modifier = modifier)`
- 缺失的变体轴：
  - `Selected`（2 档）：`False` / `True`
  - `State`（5 档）：`Enabled` / `Hovered` / `Focused` / `Pressed` / `Disabled`
  - `Icon`（2 档）：`False` / `True`
- 缺失的非变体参数：`Show focus indicator`（布尔）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；布尔参数作为显示开关接入布局。

### Standard slider · `M3StandardSlider`（`components/M3Sliders.kt`）

- 规格参数 **8** 个，已落地 **1** 个（`Value`→`sliderValue`）；函数体：`Slider(value = sliderValue, onValueChange = {}, modifier = modifier)`
- 缺失的变体轴：
  - `Orientation`（2 档）：`Horizontal` / `Vertical`
  - `Size`（5 档）：`Large` / `XLarge` / `Medium` / `Small` / `XSmall`
  - `State`（4 档）：`Enabled` / `Hovered` / `Pressed` / `Disabled`
- 缺失的非变体参数：`Show value indicator`（布尔）、`Show stops`（布尔）、`Icon`（实例替换）、`Show icon`（布尔）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；布尔参数作为显示开关接入布局；实例替换参数接到图标/图片槽（`Painter`），并补 Figma 默认图标。

### List item · `M3ListItem`（`components/M3Lists.kt`）

- 规格参数 **7** 个，已落地 **0** 个；函数体：`// 布局骨架：完整规格见 COMPONENTS_SPEC.md Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }`
- 缺失的变体轴：
  - `Alignment`（2 档）：`Middle-aligned` / `Top-aligned`
  - `State`（6 档）：`Enabled` / `Hovered` / `Focused` / `Pressed` / `Dragged` / `Disabled`
  - `Selected`（2 档）：`False` / `True`
- 缺失的非变体参数：`Show trailing element`（布尔）、`Show leading element`（布尔）、`Show focus indicator`（布尔）、`Content`（插槽）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；布尔参数作为显示开关接入布局；插槽参数接到 `content: @Composable` 槽。

### Menu · `M3Menu`（`components/M3Menu.kt`）

- 规格参数 **7** 个，已落地 **0** 个；函数体：`// 布局骨架：完整规格见 COMPONENTS_SPEC.md Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }`
- 缺失的变体轴：
  - `Theme`（2 档）：`Standard` / `Vibrant`
  - `Groups`（3 档）：`1` / `2` / `3`
- 缺失的非变体参数：`Show scrollbar`（布尔）、`Show section label`（布尔）、`List 1 content`（插槽）、`List 2 content`（插槽）、`List 3 content`（插槽）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；布尔参数作为显示开关接入布局；插槽参数接到 `content: @Composable` 槽。

### Menu item/Standard · `M3MenuItemStandard`（`components/M3Menu.kt`）

- 规格参数 **7** 个，已落地 **0** 个；函数体：`// 布局骨架：完整规格见 COMPONENTS_SPEC.md Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }`
- 缺失的变体轴：
  - `State`（6 档）：`Enabled` / `Hovered` / `Focused` / `Pressed` / `Disabled` / `Active`
  - `Selected`（2 档）：`False` / `True`
- 缺失的非变体参数：`Show leading element`（布尔）、`Show trailing element`（布尔）、`Show divider`（布尔）、`Show focus indicator`（布尔）、`Content`（插槽）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；布尔参数作为显示开关接入布局；插槽参数接到 `content: @Composable` 槽。

### Primary tabs/Icon and label · `M3PrimaryTabsIconAndLabel`（`components/M3Tabs.kt`）

- 规格参数 **6** 个，已落地 **1** 个（`Selected`→`bSelected`）；函数体：`Tab(selected = bSelected, onClick = onClick, text = { Text(tLabel) }, modifier = modifier)`
- 缺失的变体轴：
  - `State`（4 档）：`Enabled` / `Hovered` / `Focused` / `Pressed`
- 缺失的非变体参数：`Icon`（实例替换）、`Label text`（文本）、`Show focus indicator`（布尔）；`Show badge` 已在 2026-11-17 补为 6dp 小圆点
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；文本参数接到实际 `Text`（替换硬编码 `tLabel`）；布尔参数作为显示开关接入布局；实例替换参数接到图标/图片槽（`Painter`），并补 Figma 默认图标。

### Navigation Bar: Horizontal items · `M3NavigationBarHorizontalItems`（`components/M3Navigation.kt`）

- 规格参数 **1** 个，已落地 **0** 个；函数体：`NavigationBarItem(selected = bSelected, onClick = onClick, icon = {}, label = { Text(tLabel) }, modifier = modifier)`
- 缺失的变体轴：
  - `Nav items`（4 档）：`3` / `4` / `5` / `6`
- 缺失的非变体参数：无
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）。

### Navigation Rail · `M3NavigationRail`（`components/M3Navigation.kt`）

- 规格参数 **5** 个，已落地 **0** 个；函数体：`NavigationBarItem(selected = bSelected, onClick = onClick, icon = {}, label = { Text(tLabel) }, modifier = modifier)`
- 缺失的变体轴：
  - `Alignment`（2 档）：`Top` / `Middle`
  - `Nav items`（4 档）：`3` / `4` / `5` / `6`
- 缺失的非变体参数：`Show FAB`（布尔）、`Show Menu`（布尔）、`Segments`（插槽）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；布尔参数作为显示开关接入布局；插槽参数接到 `content: @Composable` 槽。

### Basic dialog · `M3BasicDialog`（`components/M3Dialogs.kt`）

- 规格参数 **5** 个，已落地 **0** 个；函数体：`AlertDialog(onDismissRequest = {}, confirmButton = { TextButton(onClick = onClick) { Text("OK") } }, title = { Text(tLabel) }, text = {}, modifier = modifier)`
- 缺失的变体轴：
  - `Icon`（2 档）：`True` / `False`
- 缺失的非变体参数：`Show divider`（布尔）、`Title`（文本）、`Supporting text`（文本）、`Content`（插槽）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；文本参数接到实际 `Text`（替换硬编码 `tLabel`）；布尔参数作为显示开关接入布局；插槽参数接到 `content: @Composable` 槽。

### Snackbar · `M3Snackbar`（`components/M3Snackbar.kt`）

- 规格参数 **4** 个，已落地 **0** 个；函数体：`Snackbar(action = { TextButton(onClick = onClick) { Text(tLabel) } }, modifier = modifier)`
- 缺失的变体轴：
  - `Configuration`（3 档）：`Text only` / `Text & action` / `Text & longer action`
  - `# of lines`（2 档）：`One line` / `Two lines`
  - `Show close affordance`（2 档）：`False` / `True`
- 缺失的非变体参数：`Supporting text`（文本）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；文本参数接到实际 `Text`（替换硬编码 `tLabel`）。

### Search bar · `M3SearchBar`（`components/M3Search.kt`）

- 规格参数 **6** 个，已落地 **0** 个；函数体：`SearchBar(inputField = {}, leadingIcon = {}, modifier = modifier)`
- 缺失的变体轴：
  - `State`（3 档）：`Enabled` / `Hovered` / `Pressed`
  - `Show avatar`（2 档）：`False` / `True`
- 缺失的非变体参数：`Show 2nd trailing icon`（布尔）、`Show 1st trailing icon`（布尔）、`Placeholder text`（文本）、`Show leading icon`（布尔）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；文本参数接到实际 `Text`（替换硬编码 `tLabel`）；布尔参数作为显示开关接入布局。

### FAB · `M3FAB`（`components/M3Buttons.kt`）

- 规格参数 **5** 个，已落地 **0** 个；函数体：`FloatingActionButton(onClick = onClick, modifier = modifier) { /* icon */ }`
- 缺失的变体轴：
  - `Size`（3 档）：`Default` / `Medium` / `Large`
  - `Color`（6 档）：`Primary container` / `Secondary container` / `Tertiary container` / `Primary` / `Secondary` / `Tertiary`
  - `State`（4 档）：`Enabled` / `Hovered` / `Focused` / `Pressed`
- 缺失的非变体参数：`Show focus indicator`（布尔）、`Icon`（实例替换）
- 补齐动作：把每个未落地的轴按档位拆成独立视觉分支（尺寸档改高度/内边距，状态档叠 M3 状态层与禁用色）；布尔参数作为显示开关接入布局；实例替换参数接到图标/图片槽（`Painter`），并补 Figma 默认图标。

## 五、结构性缺口（不属于“实现为空”，但会挡住补齐）

### 5.1 签名层面缺失的 5 条（分布 3 个组件）

| 组件 | 规格 | 现状 | 影响 |
|---|---|---|---|
| `Menu` | 7 个：2 布尔 + 3 个具名插槽（`List 1/2/3 content`）+ 2 变体 | 5 个：2 布尔 + 2 变体 + 1 个泛化 `content` | 三组菜单内容无法分别挂载 |
| `Toolbar` | 5 个：2 个插槽（`Content (standard)` / `Content (vibrant)`）+ 3 变体 | 4 个：1 个插槽 + 3 变体 | standard / vibrant 两套内容槽被合并 |
| `.Shape`（Shape 页） | 1 个参数 | 无函数 | Shape 组件集完全没生成 |

### 5.2 重复定义：改错文件的风险

`M3AppBar`、`M3BottomAppBar` 在 `appbar/`（手写、真实现）和 `components/`（生成、空壳）各有一份。包名不同所以能编过，但同名同签名，`import` 到哪份取决于路径。**补齐时以 `appbar/` 版为准**；`components/M3Appbars.kt` 里的两份空壳建议删除或改名。

### 5.3 实例替换参数：默认实例全丢

正式组件有 55 个 `实例替换` 参数（`Icon`、`Image`、`Trailing element` 等），Figma 每条都记录了默认实例节点 id（例如 Button 的 `Icon` 默认 `54616:25409`）。代码里一律退化为 `Painter? = null`：图标槽在，但默认图标、尺寸、着色规则都没有。

### 5.4 枚举按名合并，语义已混装

`M3Enums.kt` 的 53 个枚举是按 Figma 属性名全局去重合并的，同一枚举承载了多个页面语义：

| 枚举 | 合并来源 | 后果 |
|---|---|---|
| `Style`（11 值） | Avatar（`CHECK/MONOGRAM/PORTRAIT`）、Card/Chip（`OUTLINED/ELEVATED/FILLED`）、Tabs（`PRIMARY/SECONDARY`）、Text field（`FILLED`） | `M3StackedCard(vStyle = Style.CHECK)` 类型合法、语义荒谬 |
| `Type` | 按钮（`ROUND`）、日期选择器（`DAY/SINGLE_INPUT`）、列表（`STANDARD`）、进度条（`FLAT`）、导航（`DOCKED`）、复选框（`SELECTED`） | 默认值容易错位，可传非法值 |
| `Configuration`（30 值） | App bar、FAB、Snackbar、Search、Sheet、Button group | 参数 30 个选项，调用方无法判断哪些合法 |
| `State`（5 值） | 全部交互组件 | 合并方向正确，但各组件支持档位不同（Tabs 无 `Disabled`） |

建议：**先拆枚举再补实现**，否则每补一个组件都要在 30 值枚举里挑，越晚拆成本越高。

### 5.5 命名是机械音译，且有重复参数

生成参数名直接来自 Figma 属性名：`vType`、`vSize`、`bShowicon`、`tLabeltext`、`iSlotswap`、`sContent`。其中 19 个函数同时存在 `tLabeltext`（规格）与 `tLabel`（硬编码兜底）两个文案参数，调用方极易传错。建议统一为 Compose 惯例：`type/size/state/style/showIcon/icon/label/content`。

### 5.6 真正生效的参数只有 12 条，且都是单维度

按“允许改名/改类型”的宽口径统计，只有这些参数影响了渲染：

- Badges：`Badge label`→`tLabel`
- Linear-determinate progress indicator：`Progress`→`progress`
- Circular-determinate progress indicator：`Progress`→`progress`
- Radio buttons：`Selected`→`bSelected`
- Standard slider：`Value`→`sliderValue`
- Centered slider：`Value`→`sliderValue`
- Range slider：`Value`→`sliderValue`
- Primary tabs/Icon and label：`Selected`→`bSelected`
- Primary tabs/Icon only：`Selected`→`bSelected`
- Primary tabs/Label only：`Selected`→`bSelected`
- Secondary tabs/Label only：`Selected`→`bSelected`
- Secondary tabs/Icon and label：`Selected`→`bSelected`

它们的特点：全部是**单维度替换**（选中与否、进度值、滑杆值、角标文案），没有覆盖任何尺寸/风格/状态/图标组合。例如 Checkboxes 的 `Type` 有 6 档（含 `Indeterminate`、`Error selected`），代码只做了 `bChecked` 二态；5 个 Tabs 变体只做了选中态，`Icon`、`Show badge`、`Show focus indicator` 全缺。

另有一批组件用了**规格之外的**自建参数：Checkboxes（bChecked）；Linear-indeterminate progress indicator（progress）；Circular-indeterminate progress indicator（progress）；Loading indicator（progress）；XR/XR Navigation Rail（bSelected）；XR/XR Navigation bar（bSelected）；Navigation Bar: Horizontal items（bSelected）；Navigation Bar: Vertical items（bSelected）。这些参数能驱动 Material 3 原生控件，但不在 Figma 规格里，补齐时应删除或并入规格参数。

## 六、建议补齐顺序

排序依据：**用得多 × 参数少 × 改动面小**。先做“每个界面都会出现、状态轴不多”的组件，再做组合型组件，最后做 XR / 内部构建块这类边缘页。

| 阶段 | 范围 | 组件数 | 规格参数 | 当前已落地 | L/M/S | 说明 |
|---|---|---:|---:|---:|---|---|
| 1 | 基础输入控件：Button 全系、Text field、Checkbox/Radio/Switch、Slider、Chip | 18 | 117 | 4 | 2/15/1 | 表单页地基；State 五档统一，最适合先抽状态层工具函数 |
| 2 | 容器与内容：Card、List / List item、Tabs、App bar / Bottom app bar | 17 | 127 | 5 | 4/7/6 | 依赖阶段 1 的按钮/排版；App bar 需先删掉 components 里的重复空壳 |
| 3 | 导航与浮层：Navigation Bar/Rail、Menu / Menu item、Dialog、Snackbar、Search | 16 | 71 | 0 | 0/9/7 | 涉及弹层与焦点管理；Search 还依赖 Text field |
| 4 | 复杂交互：Date & time pickers、Sheets、Carousel、Toolbar、进度指示器、FAB/Icon button/Segmented/Toggle | 33 | 180 | 2 | 8/18/7 | 参数最多、状态最多；日期选择器要单独写日历网格 |
| 5 | 边缘页面：XR 变体、74 个内部构建块、Avatars/Badges/Tooltips/Utilities/Shape/Examples | 13 | 43 | 1 | 0/8/5 | 不影响主线 UI，可等主线稳定后再评估 |

合计 97 个正式组件、538 条规格参数（另有 74 个内部构建块、347 条）。

### 第一批建议动手（阶段 1 中参数最多的 12 个）

| 组件 | 函数 | 参数 | 已落地 | 缺失状态轴 | 体量 | 文件 |
|---|---|---:|---:|---|:--:|---|
| Text field | `M3TextField` | 10 | 10 | — | S | `components/M3Textfields.kt` |
| Filter chip | `M3FilterChip` | 9 | 0 | Style(2)·Configuration(2)·State(6)·Selected(2)·Show trailing icon(2) | M | `components/M3Chips.kt` |
| Split button | `M3SplitButton` | 8 | 0 | Size(5)·Color(4)·Leading state(5)·Trailing state(6) | L | `components/M3Buttons.kt` |
| Assistive chip | `M3AssistiveChip` | 8 | 0 | Style(2)·Configuration(4)·State(6) | M | `components/M3Chips.kt` |
| Input chip | `M3InputChip` | 8 | 0 | Configuration(3)·State(4)·Selected(2)·Show closing icon(2) | M | `components/M3Chips.kt` |
| Standard slider | `M3StandardSlider` | 8 | 1 | Orientation(2)·Size(5)·State(4) | M | `components/M3Sliders.kt` |
| Button | `M3Button` | 7 | 7 | — | S | `components/M3Buttons.kt` |
| Button - text | `M3ButtonText` | 7 | 7 | — | S | `components/M3Buttons.kt` |
| Button - elevated | `M3ButtonElevated` | 7 | 7 | — | S | `components/M3Buttons.kt` |
| Button - outline | `M3ButtonOutline` | 7 | 7 | — | S | `components/M3Buttons.kt` |
| Button - tonal | `M3ButtonTonal` | 7 | 7 | — | S | `components/M3Buttons.kt` |
| Suggestion chip | `M3SuggestionChip` | 7 | 0 | Style(2)·State(6)·Selected(2)·Show icon(2) | M | `components/M3Chips.kt` |

### 每个组件的验收标准（建议写进 PR 模板）

1. 该组件的规格参数 **0 条未落地**（按同一口径复算）。
2. 每个 `State` 档位有独立 `@Preview`；`Size`/`Style` 各档可切换。
3. 抽 3 个状态与 Figma 节点截图比对尺寸/圆角/颜色/字号，差异记录在 PR。
4. 文件里不再有“布局骨架”注释与硬编码 `tLabel`。

## 七、附录

### A. 74 个内部构建块

设计稿里的拼装件（Buttons/Lists/Navigation 等页面内部用），不是对外 API。目前只有 5 个有对应函数（`M3BuildingBlocks*`，在 `components/M3Lists.kt`），其余 69 个没有生成。补齐主线组件时会自然覆盖，**不建议单独排期**。

<details><summary>展开 74 个名称</summary>

· .Building Blocks/App bar/Content/Avatar　|　.Building Blocks/App bar/Content/Text Large　|　.Building Blocks/App bar/Content/Text Medium
· .Building Blocks/App bar/Content/Text Small　|　.Building Blocks/Card states/Elevated　|　.Building Blocks/Card states/Filled
· .Building Blocks/Card states/Outlined　|　.Building Blocks/Content　|　.Building Blocks/Direct Input (keyboard) input
· .Building Blocks/FAB Menu/Primary/FAB　|　.Building Blocks/FAB Menu/Primary/Segment　|　.Building Blocks/FAB Menu/Secondary/FAB
· .Building Blocks/FAB Menu/Secondary/Segment　|　.Building Blocks/FAB Menu/Tertiary/FAB　|　.Building Blocks/FAB Menu/Tertiary/Segment
· .Building Blocks/Flat/Search bar - Modified　|　.Building Blocks/Hour　|　.Building Blocks/Input
· .Building Blocks/Leading element　|　.Building Blocks/Local M3 calendar cell　|　.Building Blocks/Menu button
· .Building Blocks/On-scroll/Search bar - Modified　|　.Building Blocks/Period Selector　|　.Building Blocks/Period Selector - Horizontal
· .Building Blocks/Progress indicator/Width 4/Segment - wave　|　.Building Blocks/Progress indicator/Width 8/Segment - wave　|　.Building Blocks/Snackbar-action
· .Building Blocks/Snackbar-close-affordance　|　.Building Blocks/Trailing element　|　.Building Blocks/Year
· .Building Blocks/hour-line　|　Building Blocks / Nav item　|　Building Blocks/Bottom sheets/Content
· Building Blocks/Button group/Connected segments/Large　|　Building Blocks/Button group/Connected segments/Medium　|　Building Blocks/Button group/Connected segments/Small
· Building Blocks/Button group/Connected segments/XSmall　|　Building Blocks/Button group/Connected segments/Xlarge　|　Building Blocks/Leading element
· Building Blocks/Menu list item　|　Building Blocks/Menu list item: -2 density　|　Building Blocks/Menu list item: -4 density
· Building Blocks/Navigation bars/Horizontal nav item　|　Building Blocks/Navigation bars/Vertical nav item　|　Building Blocks/Navigation rail/Horizontal nav item
· Building Blocks/Navigation rail/Vertical nav item　|　Building Blocks/Segmented button/Button segment (end)　|　Building Blocks/Segmented button/Button segment (middle)
· Building Blocks/Segmented button/Button segment (start)　|　Building Blocks/Side sheets/Content　|　Building Blocks/Standard/Button toggleable
· Building Blocks/Standard/Icon button　|　Building Blocks/Standard/Icon button toggleable　|　Building Blocks/Trailing element
· Building Blocks/Trailing element-selected　|　Building Blocks/Vibrant/Button toggleable　|　Building Blocks/Vibrant/Icon button
· Building Blocks/Vibrant/Icon button toggleable　|　Building Blocks/XR/Navigation bar/Nav item　|　Building Blocks/XR/Navigation rail/Nav item
· Building blocks/Content　|　Building blocks/Leading element　|　Building blocks/List item/Accordion buttton
· Building blocks/Reveal element　|　Building blocks/Trailing element　|　XR/Building Blocks/Surface high/Button toggleable
· XR/Building Blocks/Surface high/Icon button　|　XR/Building Blocks/Surface high/Icon button toggleable　|　XR/Building Blocks/Surface/Button toggleable
· XR/Building Blocks/Surface/Icon button　|　XR/Building Blocks/Surface/Icon button toggleable　|　XR/Building Blocks/Tertiary container/Button toggleable
· XR/Building Blocks/Tertiary container/Icon button　|　XR/Building Blocks/Tertiary container/Icon button toggleable

</details>

### B. 与 README 的说法差异

| README 说法 | 实际 |
|---|---|
| 28 个组件文件、133 个 Composable | 29 个组件文件、约 135 处 `@Composable` |
| 复杂组件“给出可编译占位骨架，并标注 TODO” | 全目录 `TODO` 数量为 **0**；骨架靠 `// 布局骨架：完整规格见 COMPONENTS_SPEC.md` 注释（31 处）标识 |
| “所有 Kotlin 文件括号配平通过” | 本轮未复验；且括号配平 ≠ 可编译（从未真实编译） |

### C. 方法与局限

- 规格参数来自 `COMPONENTS_SPEC.md`（`componentPropertyDefinitions` 转写），885 条 = 正式组件 538 + 内部构建块 347。
- “落地”按**参数名启发式**判断：去掉 `#node-id` 与大小写/下划线，允许 `t/b/v/i/s` 前缀与改类型（如 `Selected` 变体 → `bSelected: Boolean`）。若只认生成命名（`vType`/`tLabeltext` 这类），落地数是 **0**。
- 未编译、未跑 UI，结论仅限源码文本层面。
- 本轮只产出清单，**未修改任何代码**。

### D. 下一步（需你确认）

1. 是否按阶段 1 → 5 推进，还是先挑 3–5 个组件试点？
2. 是否先做“拆枚举 + 删重复 AppBar”这两件基础设施？
3. 是否需要我补一个可编译的 Gradle 工程（否则每改一处都无法确认能否编译）？
4. 验收是否采用 Figma 截图比对（需要 `figmingo` MCP / 截图导出）？
