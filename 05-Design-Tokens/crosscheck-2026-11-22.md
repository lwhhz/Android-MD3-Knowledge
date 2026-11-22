# 设计令牌逐格对照（2026-11-22）

> 目的：把 `05-Design-Tokens/` 的数值与 **Compose 实现（一手来源）**逐项比对，确认库内数值不是抄来的相传值。
> 对照日期：2026-11-22 ｜ 对照对象：`androidx.compose.material3:material3:1.5.0`
> 源码包：`https://dl.google.com/dl/android/maven2/androidx/compose/material3/material3/1.5.0/material3-1.5.0-sources.jar`（849,385 B，2026-11-22 下载）

## 一、方法

1. 从官方 Maven 下载 `1.5.0-sources.jar`，解压以下文件：
   - `tokens/TypeScaleTokens.kt`（v0_103）
   - `tokens/TypefaceTokens.kt`
   - `tokens/ShapeTokens.kt`（VERSION 14_1_0）
   - `tokens/StandardMotionTokens.kt` / `tokens/ExpressiveMotionTokens.kt`（v0_14_0）
   - `ColorScheme.kt`
2. 用脚本解析 Kotlin 常量（字号/行高/字距/字重、圆角 dp、spring damping/stiffness、ColorScheme 属性名）。
3. 与 `typography.json` / `shapes.json` / `motion.json` / `colors.json` 逐字段比对，容差 0.001。
4. 复现：脚本 `$TEMP/tokens-compare3.mjs`（本次运行结果：styles 30、typo 0、shape 0、motion 0、colors 1）。

## 二、结果

| 令牌类 | 核对范围 | 结果 |
|---|---|---|
| 字阶 | 15 baseline + 15 emphasized 的 size / lineHeight / weight；另比对 `styles` 的 15 档设计值 | **30/30 一致**；设计值三字段全部一致 |
| 字距（tracking） | 设计值 vs Compose 值 | 3 处差异，**两者并存、不覆盖**（见下） |
| 形状 | 十档 dp（0/4/8/12/16/20/28/32/48/full） | **10/10 一致**；`CornerFull = CircleShape` 与 JSON「短边/2」语义等价 |
| 动效 | standard / expressive × 6 = 12 组 dampingRatio / stiffness | **12/12 一致** |
| 颜色 | 26 个标准角色 + 17 个 add-on + functional | 标准 26 全在 `ColorScheme`；add-on 17 全在；functional 中 **`scrim` 在、`shadow` 不在** |

### 字距差异（已知，保留双值）

| 样式 | 设计值 | Compose 1.5.0 |
|---|---:|---:|
| displayLarge | -0.25 | -0.2 |
| titleMedium | 0.15 | 0.2 |
| bodyMedium | 0.25 | 0.2 |

说明：设计规范页给的是设计值，Compose 用的是实现值；两端各自维护，不互相覆盖（与 `typography.json` 的分层写法一致）。

### 颜色：`shadow` 的修正

- 官方角色页 2026-11-22 正文 **没有出现 `shadow`**（`grep -c shadow` = 0）。
- `androidx.compose.material3 1.5.0` 的 `ColorScheme` 只有 `scrim`，**没有 `shadow` 属性**。
- 处理：`colors.json` 的 `functional_colors.items` 由 `["scrim", "shadow"]` 改为 `["scrim"]`，并写明不要把 shadow 当颜色角色。

## 三、本次回写

| 文件 | 改动 |
|---|---|
| `colors.json` | 删掉 `shadow`；补核对说明 |
| `typography.json` | `compose_source` 指向 **1.5.0**（原 rc01）；`updated_on` 2026-11-22；补 `compose_verified_on`（30 档一致） |
| `shapes.json` | 补 `compose_verified_on`（十档一致） |
| `motion.json` | 补 `compose_verified_on`（12 组一致） |
| `README.md` | 字阶从「结构已存、数值待补」改为「结构 + 数值（Compose 实现值 30/30）」 |



## 五、组件级抽样（2026-11-22）

对 6 组组件页数值与 `material3 1.5.0` 组件令牌做抽样核对（非全量）：

| 组件 | 知识库设计值 | Compose 1.5.0 令牌/默认 | 结论 |
|---|---|---|---|
| 按钮五档高度 | 32 / 40 / 56 / 96 / 136dp | ButtonXSmall/Small/Medium/Large/XLarge `ContainerHeight` 同值 | ✅ 完全一致 |
| 按钮图标尺寸 | 20 / 20 / 24 / 32 / 40dp（按档） | `IconSize` 同值 | ✅ 一致 |
| 对话框圆角 | 28dp | `DialogTokens.ContainerShape = CornerExtraLarge`（28dp） | ✅ 一致 |
| Navigation bar 高度 | 80dp（baseline，测量图） | `TallContainerHeight = 80dp`（Compose 默认）；`ContainerHeight = 64dp`（short 变体） | ✅ 80 一致；64dp 属 short/flexible 变体源码值，非设计页标注 |
| Navigation rail 宽度 | 80dp baseline / 96dp flexible | `NarrowContainerWidth = 80dp`（默认）；`CollapsedTokens.ContainerWidth = 96dp` | ✅ 两个数值各有对应变体 |
| FAB 常规尺寸 | 56dp（原标 ⚠️ 推断） | `FabBaselineTokens 56×56` | ✅ 推断获得源码支持 |
| FAB expressive 档 | 设计页仅示意图 | small 40 / medium 80 / large 96 | 仅源码值，不作设计规范值 |

| Extended FAB | 56 / 80 / 96dp | `ExtendedFabSmall/Medium/LargeTokens.ContainerHeight` = 56 / 80 / 96dp；baseline Extended FAB 56dp | ✅ 一致 |
| Tabs | 48（纯文字）/ 64（图标+文字）；指示器 Primary 3dp / Secondary 2dp | `ContainerHeight=48dp`、`IconAndLabelTextContainerHeight=64dp`、`ActiveIndicatorHeight=3dp`；**但 `Tab` 组件用硬编码 `LargeTabHeight=72dp`** | ⚠️ 设计 64 vs Compose 渲染 72，已在 `02-Components/tabs.md` 注明 |
| Chips | 高 32 / 图标 18 / 圆角 8dp | `ChipsTokens`：32dp / 18dp / CornerSmall(8dp) | ✅ 一致 |
| Menus | 项高 48 / 分组纵向 padding 8 / 最大宽 280 | `MenuListItemContainerHeight=48dp`、`DropdownMenuVerticalPadding=8dp`、`DropdownMenuItemDefaultMaxWidth=280dp`、组间距 2dp | ✅ 一致 |

| Badge | Small 6dp / Large 16dp | `BadgeTokens.Size=6dp`、`LargeSize=16dp` | ✅ 一致 |
| Snackbar | 单行 48dp / 双行 64dp | `SingleLineContainerHeight=48dp` ✅；`TwoLinesContainerHeight=68dp` ⚠️ 设计 64 vs 实现 68 | 已在 `02-Components/snackbar.md` 注明 |
| Search bar | 容器高 56dp | `SearchBarTokens.ContainerHeight=56dp`；Avatar 30dp（KB 未列） | ✅ 一致 |
| Progress indicator | 轨道高度（KB 4dp 等） | 令牌文件只有形状（CornerFull），尺寸在组件代码常量中，本次未解析 | 未核 |

| Divider | 1dp | `DividerTokens.Thickness = 1dp` | ✅ 一致 |
| Icon button | 32 / 40 / 56 / 96 / 136dp | `XSmall…XLargeIconButtonTokens.ContainerHeight` = 32/40/56/96/136 | ✅ 一致 |
| Loading indicator | 占位 48dp / 形状容器 38dp | `LoadingIndicatorTokens.ContainerHeight/Width = 48dp`、`ActiveSize = 38dp` | ✅ 一致 |
| Progress indicator | 轨道 4dp；圆形范围 24–240dp | 线性 `Height = ActiveThickness = StopSize = 4dp`；圆形 `Size = 40dp`、`TrackThickness = 4dp`、`WaveSize = 48dp` | ✅ 设计值一致；40dp 为 Compose 默认 |
| Selection controls | Switch 32×52、handle 16/24；Checkbox 18；Radio 20 | `SwitchTokens.TrackHeight/Width=32/52`、handle 16/24、pressed 28、icon 16；`CheckboxTokens.ContainerSize=18`；`RadioButtonTokens.IconSize=20` | ✅ 一致（Compose 另有 pressed 28dp） |

| Tooltip (plain) | 高 24dp；内边距 8dp | `TooltipMinHeight = 24dp`；`PlainTooltip` 垂直 4dp / 水平 8dp，间距 4dp；Rich 水平 16dp、按钮最小高 36dp | ⚠️ 高度一致；**内边距设计 8 vs 实现 4/8**，已写入 `tooltip.md` |
| Bottom sheet | 顶部圆角 28dp；拖拽手柄 32×4dp | `SheetBottomTokens.CornerExtraLargeTop`（顶部 28dp）、`DockedDragHandle 32×4dp` | ✅ 一致 |
| Toolbars | 64dp；外部最小边距 16dp | `DockedToolbarTokens`：高 64、leading/trailing 16；`FloatingToolbarTokens`：高 64、external 16、leading/trailing 8、间距 4 | ✅ 一致 |
| App bars | small 64 / medium 112 / large flexible 120dp | `AppBarSmall=64`、`AppBarMedium=112`、`AppBarLarge=152`（baseline） | ✅ 64/112；**152 为 Compose baseline 值**，KB 原「152 无出处」已补来源 |
| Cards | 圆角 12dp | `CardTokens.ContainerShape = CornerMedium`（12dp） | ✅ 一致 |

**边界**：共核 23 组（按钮、对话框、导航栏/栏、FAB、Extended FAB、Tabs、Chips、Menus、Badge、Snackbar、Search bar、Divider、Icon button、Loading、Progress、Switch、Checkbox、Radio、Tooltip、Bottom sheet、Toolbar、App bar、Card）；其余组件未核；组件令牌的颜色部分未比对。

## 四、边界

1. 只核对了 **Compose 实现值**；官网设计数值表仍是图片/令牌模块，未逐像素读取（`typography.json` 的设计值沿用 2026-11-16 的通行值）。
2. 只核对了全局令牌文件；`tokens/` 下每个组件的令牌（AppBar、Slider、Button 等）未逐一比对。
3. Web/Flutter/iOS 的曲线、duration 未在此次范围内。
4. `ColorScheme` 共 48 个 Color 属性，比 26+17+1 多出的 4 个属非 26 标准角色（如 surfaceTint 等），本库不把它们计入「标准角色」。
