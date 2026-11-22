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



## 四、组件级抽样（2026-11-22）

下表把已核对组件页数值与 `material3 1.5.0` 源码逐项对照（组件令牌 + 必要处的组件代码常量）：

| 组件 | 知识库设计值 | Compose 1.5.0 令牌/默认 | 结论 |
|---|---|---|---|
| Buttons（五档高度） | 32 / 40 / 56 / 96 / 136dp | ButtonXSmall…XLarge `ContainerHeight` 同值 | ✅ |
| Buttons（图标） | 20 / 20 / 24 / 32 / 40dp | `IconSize` 同值 | ✅ |
| Dialog（圆角/宽度） | 圆角 28 / 宽 280–560 | `CornerExtraLarge(28)`；`DialogMinWidth=280`、`DialogMaxWidth=560` | ✅ |
| Navigation bar | 高 80dp | `TallContainerHeight=80`（默认）；`ContainerHeight=64` 为 short 变体 | ✅ |
| Navigation rail | 80 / 96dp | `NarrowContainerWidth=80`、`ContainerWidth=96` | ✅ |
| FAB | 56dp（原推断） | `FabBaselineTokens 56×56` | ✅ 推断获源码支持 |
| FAB Expressive | 40 / 80 / 96dp | `FabSmall/Medium/Large` 同值 | ✅（设计页仅示意） |
| Extended FAB | 56 / 80 / 96dp | `ExtendedFabSmall/Medium/Large` | ✅ |
| Tabs | 48 / 64dp；指示器 3/2dp | 令牌 48/64/3；**`Tab` 渲染用 `LargeTabHeight=72dp`** | ⚠️ 64 vs 72（已写入 tabs.md） |
| Chips | 32 / 18 / 8dp | `ChipsTokens` 同值 | ✅ |
| Menus | 项高 48 / padding 8 / 宽 280 | `MenuListItemContainerHeight=48`、`VerticalPadding=8`、`MaxWidth=280` | ✅ |
| Badge | 6 / 16dp | `Size=6`、`LargeSize=16` | ✅ |
| Snackbar | 单行 48 / 双行 64dp | 48；**双行 68dp** | ⚠️ 64 vs 68（已写入 snackbar.md） |
| Search bar | 高 56dp | `ContainerHeight=56` | ✅ |
| Divider | 1dp | `Thickness=1` | ✅ |
| Icon button | 32/40/56/96/136dp | 五档 `ContainerHeight` 同值 | ✅ |
| Loading indicator | 48 / 38dp | `ContainerHeight/Width=48`、`ActiveSize=38` | ✅ |
| Progress indicator | 轨道 4dp；圆形范围 24–240 | 线性 4dp；圆形 `Size=40`、`TrackThickness=4`、`WaveSize=48` | ✅（40dp 为 Compose 默认） |
| Selection controls | Switch 32×52（handle 16/24）；Checkbox 18；Radio 20 | 同值；另有 pressed 28dp | ✅ |
| Tooltip (plain) | 高 24 / 内边距 8dp | 高 24；内边距垂直 4 / 水平 8 | ⚠️ 内边距差异（已写入 tooltip.md） |
| Bottom sheet | 圆角 28；手柄 32×4 | `CornerExtraLargeTop`、`DockedDragHandle 32×4` | ✅ |
| Toolbars | 64dp / 外距 16 | docked 64/16；floating 64、external 16 | ✅ |
| App bars | 64 / 112 / 120（flexible large） | `AppBarSmall=64`、`AppBarMedium=112`、`AppBarLarge=152`（baseline） | ✅ 64/112；152 为 Compose baseline 值 |
| Cards | 圆角 12dp | `CornerMedium(12)` | ✅ |
| Text fields | 56dp；图标 24；圆角 4（实现值） | 56 / 24 / 描边 1–2 | ✅（4dp 为 Compose 默认） |
| Slider | XS：轨道 16、handle 44×4 | `ActiveTrackHeight=16`、`HandleHeight=44`、`HandleWidth=4` | ✅ XS 一致 |
| Navigation drawer | 360；指示器 56×336 | 同值 | ✅ |
| Lists | 56 / 72 / 88dp | 同值 | ✅ |
| Bottom app bar | 知识库未单列 dp | `BottomAppBarTokens.ContainerHeight=80dp` | 源码值，补记 |
| FAB menu | 关闭按钮 56dp | `CloseButton 56×56`、item 56、icon 20 | ✅ 56 一致 |
| Split button | 32/40/56/96/136dp | 五档 `ContainerHeight` 同值 | ✅ |
| Segmented button | 高 40 / 描边 1 / 图标 18 | `OutlinedSegmentedButtonTokens` 同值 | ✅ |
| Date picker（modal） | 360 × 524dp | `DatePickerModalTokens` **360 × 568dp** | ⚠️ 高度 524 vs 568（已写入 date-time-picker.md） |
| Time picker | 表盘 256、手柄 48、中心 8、轨道 2、上下午 216×38 | `TimePickerTokens` 同值 | ✅ |

**边界**：共 34 组（含 Date picker 的高亮差异）；Side sheet 未找到令牌/常量，未核；其余组件未核；组件令牌的颜色部分未比对。

## 五、边界

1. 只核对了 **Compose 实现值**；官网设计数值表仍是图片/令牌模块，未逐像素读取（`typography.json` 的设计值沿用 2026-11-16 的通行值）。
2. 只核对了全局令牌文件；`tokens/` 下每个组件的令牌（AppBar、Slider、Button 等）未逐一比对。
3. Web/Flutter/iOS 的曲线、duration 未在此次范围内。
4. `ColorScheme` 共 48 个 Color 属性，比 26+17+1 多出的 4 个属非 26 标准角色（如 surfaceTint 等），本库不把它们计入「标准角色」。
