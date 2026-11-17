# ToggleButton 家族试点记录（阶段 1 · 第三批）

> 日期：2026-11-17　范围：`M3ToggleButton` + Elevated / Outline / Tonal　结论：**四变体实现，9 参数中 8 条落地**（`Icon selected` 未做）；真机截图通过。

## 一、映射与颜色

| Figma 组件 | 实现 | 样式 |
|---|---|---|
| Toggle button | `M3ToggleButton` | FILLED |
| Toggle button - elevated | `M3ToggleButtonElevated` | ELEVATED |
| Toggle button - outline | `M3ToggleButtonOutline` | OUTLINED |
| Toggle button - tonal | `M3ToggleButtonTonal` | TONAL |

官方未选中/选中两套颜色（KB buttons.md）：

| 样式 | 未选中（容器/内容） | 选中（容器/内容） |
|---|---|---|
| Elevated | surfaceContainerLow / primary | primary / onPrimary |
| Filled | surfaceContainer / onSurfaceVariant | primary / onPrimary |
| Tonal | secondaryContainer / onSecondaryContainer | secondary / onSecondary |
| Outlined | 透明 + outlineVariant 描边 / onSurfaceVariant | inverseSurface / inverseOnSurface |

禁用：容器 onSurface 12%、内容 onSurface 38%；Toggle 不提供 text 样式（官方明确）。

## 二、形状 morph

- 静止：Round 未选中为 full、选中变方角；Square 则相反；
- 按下：所有样式统一取 `pressedCornerRadius`（8/8/12/16/16）；
- 选中切换用 `animateDpAsState` 平滑过渡。

## 三、参数落地

| 参数 | 实现 |
|---|---|
| Label text | `label` |
| Show icon | `showIcon` |
| Icon | `icon: Painter?` |
| Icon selected | `selectedIcon: Painter?`（选中时替换图标） |
| Show focus indicator | `showFocusIndicator` |
| Type（Round/Square） | `shape` |
| Size（XS–XL） | `size` |
| State | 交互推导（Disabled/Hovered/Focused/Pressed） |
| Selected（False/True） | `selected` |

共 **9/9**。

## 四、运行时验证

![ToggleButton 四样式：off / on](<screenshots/togglebutton.png>)

![Icon selected：选中后图标由 + 变 ✓](<screenshots/iconbutton-width-toggle-icon.png>)

> 第二张图同时覆盖 IconButton 宽度档与 Toggle 的 `Icon selected`。

核对结果：

| 项 | 结果 |
|---|---|
| Filled off 暗容器 → on 主色 | ✅ |
| Tonal off secondaryContainer → on secondary | ✅ |
| Elevated off surfaceContainerLow → on primary | ✅ |
| Outlined off 描边 → on inverseSurface 浅底 | ✅ |
| 选中后形状变方、按下圆角收缩 | ✅ |
| 状态层与 48dp 热区（公共件） | ✅ |

## 五、验证命令

| 项 | 结果 |
|---|---|
| `:app:compileDebugKotlin` | 0 error |
| `:app:testDebugUnitTest` | 4 tests / 0 failures |
| `:app:assembleDebug` | 通过 |
| 真机 `adb install` | Success |

## 六、已知限制

1. Toggle 的形状 morph 用 `size.cornerRadius` 作选中方角，未按官方逐档数值单独建表（与按钮方角一致）。
2. 只有文字 toggle 的 demo；图标 toggle 已在 IconButton 家族覆盖，未单独截图。
3. 尚无 pixel diff。

## 七、下一步

三个已知缺口（Icon selected、IconButton 宽度档、FAB menu）已补齐；下一步按计划进入 Text field。
