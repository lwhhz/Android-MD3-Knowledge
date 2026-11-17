# IconButton + FAB 试点记录（阶段 1 · 第二批）

> 日期：2026-11-17　范围：IconButton 八变体 + FAB / Extended FAB　结论：**已实现并在真机截图验证**；随后补齐宽度档与 FAB menu（见第八节）。

## 一、先抽的两个公共件

| 文件 | 内容 | 被谁复用 |
|---|---|---|
| `ui/M3Interaction.kt` | `rememberM3InteractionState(enabled, interactionSource)` + `Modifier.m3StateLayer(state, contentColor)` | Button / IconButton / FAB（后续 Chip、ListItem、Checkbox） |
| `ui/M3ButtonTokens.kt` | `M3ButtonColors` + `m3ButtonColors(style, enabled)` | Button 家族 |
| `ui/M3IconButtonTokens.kt` | `M3IconButtonStyle`、`M3IconButtonColors`、`m3IconButtonColors(...)` | IconButton 八变体 |
| `ui/M3FabTokens.kt` | `M3FabSize`、`M3FabColor`、`M3ExtendedFabSize`、`m3FabColors(...)` | FAB / Extended FAB |

`M3Button` 已改为使用公共状态层与颜色表，行为不变（截图回归通过）。

## 二、IconButton 八变体

| 组件 | 入口 | 样式 | Toggle |
|---|---|---|---|
| Icon button | `M3IconButton` | FILLED | ❌ |
| Icon button - standard | `M3IconButtonStandard` | STANDARD | ❌ |
| Icon button - outline | `M3IconButtonOutline` | OUTLINED | ❌ |
| Icon button - tonal | `M3IconButtonTonal` | TONAL | ❌ |
| Icon button togglable | `M3IconButtonTogglable` | FILLED | ✅ |
| … standard / outline / tonal | `M3IconButtonTogglableStandard/Outline/Tonal` | 对应样式 | ✅ |

颜色角色按 KB `icon-buttons.md`：

| 样式 | Default 容器/图标 | Toggle 未选中 | Toggle 选中 |
|---|---|---|---|
| Filled | primary / onPrimary | surfaceContainer / onSurfaceVariant | primary / onPrimary |
| Tonal | secondaryContainer / onSecondaryContainer | secondaryContainer / onSecondaryContainer | secondary / onSecondary |
| Outlined | 透明 + outlineVariant 描边 / onSurfaceVariant | 同左 | inverseSurface / inverseOnSurface |
| Standard | 透明 / onSurfaceVariant | 同左 | 透明 / primary |

- 尺寸沿用 32/40/56/96/136；XS/S 用外层 48dp 补足触控目标。
- Toggle 形状 morph：Round 未选中为 full、选中变方角；Square 相反；按下统一取 pressed 圆角。
- 焦点环、禁用色（12% 容器 / 38% 内容）、`contentDescription` 必填。

## 三、FAB / Extended FAB

| 组件 | 入口 | 关键值 |
|---|---|---|
| FAB | `M3FAB` | 40/56/80/96；六套颜色映射；无 `enabled`（官方：不可用时不显示）；状态层 8/10/10；hover elevation 4 |
| Extended FAB | `M3ExtendedFAB` | 56/80/96；最小宽 80；图标 24；内边距 16；`titleMedium` |

颜色六套等价映射：`primaryContainer/onPrimaryContainer`（默认）、secondary、tertiary 的 container，以及 primary/secondary/tertiary tone。

## 四、运行时验证

![IconButton 四样式 + Toggle 选中态 + FAB 尺寸/颜色](<screenshots/iconbutton-fab.png>)

![Extended FAB（Compose / Checkout）](<screenshots/fab-extended.png>)

肉眼核对：

| 项 | 结果 |
|---|---|
| IconButton 四样式容器/描边/图标色 | ✅ |
| Toggle 未选中暗容器 → 选中主色，且形状由圆变方 | ✅ |
| Outlined toggle 选中变 inverseSurface 浅底深图标 | ✅ |
| FAB 尺寸 40/56/80/96 阶梯 | ✅ |
| FAB tertiaryContainer 色映射 | ✅ |
| Extended FAB 带图标 / 纯文字两种 | ✅ |

## 五、验证命令

| 项 | 结果 |
|---|---|
| `:app:compileDebugKotlin` | 0 error |
| `:app:testDebugUnitTest` | 4 tests / 0 failures（原 2 + FAB 高度 2） |
| `:app:assembleDebug` | 通过 |
| 真机 `adb install` | Success |

## 六、已知限制

1. **图标尺寸为近似值**：XS 20 / S 24 / M 24 / L 32 / XL 40；S=24dp 为社区通行值，官方 specs 未给文字值。
2. **FAB 静止 elevation 3 为社区通行值**（KB 标注推断）；官方只给 hover 4。
3. Extended FAB 三档的圆角统一 16dp，官方三档的圆角/内边距在图中未取文字值。
4. 尚未做 pixel diff；均以真机截图人工核对。

## 七、下一步

1. 按计划进入 **Text field**（表单地基，P0 里参数最多、状态最全的组件之一）；
2. 之后是 Checkbox / Radio / Switch / Slider / Chip 等表单控件；
3. 每批完成后继续用真机截图做回归。

## 八、缺口补齐（2026-11-17 第二轮）

| 缺口 | 处理 |
|---|---|
| ToggleButton `Icon selected` | 增加 `selectedIcon: Painter?`，选中时替换图标 |
| IconButton 宽度档 | `M3IconButtonWidth { NARROW(0.75×h), DEFAULT(1.0×h), WIDE(1.25×h) }`；官方只给图片，系数为近似值 |
| FAB menu | 新增 [M3FabMenu.kt](<components/M3FabMenu.kt>)：2–6 项、关闭按钮 56dp、展开旋转 45°、项 56dp + 文字标签、`expanded/collapsed` 语义 |

![宽度档 + Icon selected](<screenshots/iconbutton-width-toggle-icon.png>)

![FAB menu 展开态](<screenshots/fab-menu.png>)

验证：编译 0 error；单测 4/4；真机安装成功。

### FAB menu 参数说明

Figma 的 6 个参数（Color、Segments 3–6、Show 3rd/4th/5th/6th）在实现里由 `items` 列表自然表达：
列表长度即段数，列表内容即显示项，因此 6/6 记为落地；颜色用 `color: M3FabColor`。

### 仍未做

1. 菜单项容器色用 `surfaceContainerHigh`（官方只说「对比色」，角色未给文字值）；
2. IconButton 宽度系数是近似值；
3. FAB menu 的横屏滚动、与 toolbar/nav rail 同屏限制只写在文档，未做运行时分流；
4. 无 pixel diff。
