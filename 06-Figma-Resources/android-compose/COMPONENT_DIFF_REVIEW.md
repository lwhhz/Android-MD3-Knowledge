# 组件代码与知识库差异报告（高风险 8 个）

> 基准：`06-Figma-Resources/android-compose/components/` vs `02-Components/*.md`（规格 + Compose 代码片段）
> 范围：Toolbar、Bottom sheet、Carousel、Date/Time、Connected button group、Search、App bar、Loading
> 状态：**只出报告，未改代码**；最新构建 31 tests / 0 failures。

## 一、结论摘要

| 组件 | 对齐度 | 主要差异 | 建议 |
|---|---|---|---|
| Toolbar | 部分 | 64/16/8/48 对齐；floating 16/24 边距未应用；docked 垂直回落；无滚动隐藏 | 补边距/滚动或文档降级 |
| Bottom sheet | 部分 | 640/56/28/22/48 逻辑对齐；平板未实测；50% 高度未扣系统栏 | 平板回归后再定 |
| Carousel | 部分/冲突 | 5 布局 + FullScreen 用官方；MULTI_ASPECT 为 LazyRow 固定宽度，官方 keyline internal | 保留降级并在 KB 记录限制 |
| Date/Time | 部分 | 5 变体接入；缺 MONTH/YEAR、全屏 Range；24h 由参数而非系统决定 | 补月份/年份与系统 24h |
| Connected group | 部分/冲突 | 2dp/48/尺寸对齐；用 Row+ToggleButton，未用官方 ButtonGroup，无 overflow/宽度动画 | 二选一：补官方容器或文档标注折衷 |
| Search | 部分 | 360–720/30dp 对齐；**56dp 高与 24/12 边距的 metrics 已定义但未使用** | 直接修代码，高优先级 |
| App bar | 部分 | 64/112/120 对齐；命名仍是 Medium/Large，非 flexible；Bottom app bar 已被 KB 标为不推荐 | 补 flexible 或更新 KB 代际说明 |
| Loading | 基本对齐 | 48dp/动画已修；`animated=false` 静态帧未逐帧验证 | 保持，补静态帧截图 |

## 二、逐项证据

### 1. Toolbar

- KB：高 64dp、外侧 16dp、间距 8dp（guidelines 另说 32dp 默认）、触控 48dp；docked 直角；floating 水平 16/垂直 24；vertical 仅大断点。
- 代码：`M3ToolbarMetrics` 64/16/8/48 已定义；`floatingMarginHorizontal/Vertical` **未被组件使用**；docked + vertical 在代码中强制回落 horizontal；overflow/FAB 已做，滚动隐藏未验证。
- 建议：要么把 floating 边距接入（由调用方或组件），要么在 KB/README 标为“布局由调用方负责”。

### 2. Bottom sheet

- KB：最大宽 640、>640dp 左右 56、上边距 72、顶部圆角 28、手柄 22、触控 48。
- 代码：`M3SheetMetrics` 与 `sideMarginForWindow`/`initialModalHeight` 已实现；内容 `heightIn(50%)` + scroll；Standard 自绘。
- 风险：平板未视觉回归；50% 以整屏算，未扣状态栏/导航栏；Modal 容器宽度仍受系统布局影响。

### 3. Carousel

- KB：六种布局（含 uncontained multi-aspect）；28dp 圆角、16/8 边距、8dp 间距、Full 16dp。
- 代码：MultiBrowse/Hero/CenteredHero/Uncontained/FullScreen 用官方组件；`MULTI_ASPECT` 用 LazyRow + snap + 固定 160/120/200/140dp。
- 根因：1.5.0-rc01 的 `Carousel`/`keylineListOf` 为 internal，无法做官方逐项 keyline。
- 建议：保留降级，但在 KB `carousel.md` 增加“实现限制”注记，避免把 6 布局全当作已官方实现。

### 4. Date / Time pickers

- KB：Docked / Modal / Modal input / Dial / Input；日期范围全屏、MONTH/YEAR；24h 由系统设置决定。
- 代码：Modal/Input/DatePickerDialog、Dial、TimeInput、Docked Input 已接；`M3ModalDatePicker` 只做 DAY/RANGE，无 MONTH/YEAR；无全屏 Range；`is24Hour` 由 `format` 参数显式决定。
- 建议：补 MONTH/YEAR 与全屏 Range；24h 改读系统 `DateFormat.is24HourFormat(context)`（或提供默认参数）。

### 5. Connected button group

- KB：组内 2dp、XS/S 最小宽 48、圆角 4/8/8/16/20、round/square、official ButtonGroup 语义、overflow。
- 代码：`M3ConnectedButtonGroupMetrics` 2dp/48；`M3ButtonGroupSize` 32/40/56/96/136；Row + `ToggleButton` + `connected*ButtonShapes`；square 用 4dp 全角；无 overflow/宽度动画。
- 根因：官方 `ButtonGroup.toggleableItem` 不暴露 `buttonSize`。
- 建议：二选一——A 接受当前实现并写清限制；B 用 official ButtonGroup + 自定义 customItem 重新评估尺寸。

### 6. Search

- KB：宽 360–720、高 56、未聚焦边距 24、聚焦 12、avatar 30、间距 4。
- 代码：`M3SearchMetrics` 定义 360/720/56/24/12/30；实际只引用 `minWidth/maxWidth/avatarSize/baselineCorner`；`barHeight/unfocusedPadding/focusedPadding` **未使用**。
- 结论：Search 的官方尺寸未真正落地，属于**高优先级代码修复**；其余状态化全屏/legacy 入口逻辑正常。

### 7. App bar / Bottom app bar

- KB：Small/Search 64、Medium flexible 112、Large flexible 120；Bottom app bar 已不推荐，应由 docked toolbar 替代。
- 代码：`AppBarConfiguration` 6 变体，高度 64/112/120 与 KB 对齐；命名/结构仍是旧 Medium/Large，无 flexible 命名与滚动收缩；`M3BottomAppBar` 仍实现。
- 建议：要么补 flexible 命名/行为，要么在 KB `app-bars.md`/`toolbars.md` 明确“该示例保留 baseline 变体用于对照”。

### 8. Loading indicator

- KB：48dp 占位、38dp 可见容器、24–240 范围、7 形状 morph；不得 loading→determinate。
- 代码：`M3ProgressMetrics` 48/38/24/240；默认已改动画；`animated=false` 才取静态 step 帧。
- 剩余：静态 7 帧未逐帧截图；容器版可见尺寸由官方组件决定，未单独测量。

## 三、建议处理顺序

| 优先级 | 条目 | 类型 |
|---|---|---|
| P0 | Search 的 56dp 高与 24/12 边距真正接入 | 代码修复 |
| P0 | Date picker 的 24h 系统设置 + MONTH/YEAR | 代码补齐 |
| P1 | Carousel MULTI_ASPECT / Connected 的 KB 限制注记 | 文档 |
| P1 | Toolbar floating 边距/滚动隐藏 | 代码或文档取舍 |
| P2 | App bar flexible 命名/行为；Bottom app bar 代际说明 | 代码/文档 |
| P2 | Sheet 平板回归、Loading 静态帧截图 | 验证 |

## 四、需要你确认的取舍

1. Search 尺寸修复是否直接改 archive 代码？
2. Date picker 是否补 MONTH/YEAR + 全屏 Range（工作量较大）？
3. Carousel/Connected 选择“接受折衷并写 KB 限制”，还是继续攻官方 API？
4. App bar 是否补 flexible 命名/滚动，还是保留 baseline 对照？

## 五、说明

- KB `02-Components` 是规格文档 + 代码片段，不是可复用组件库；本报告比较的是“文档声称的行为/数值”与 archive 实现。
- 本轮未修改任何代码或 KB 文档；确认后再按条目执行。