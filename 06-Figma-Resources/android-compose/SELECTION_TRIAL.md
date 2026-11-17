# 选择控件三件套试点记录（阶段 1 · 第五批）

> 日期：2026-11-17　范围：`M3Checkboxes` / `M3RadioButtons` / `M3Switch`　结论：**三组件参数全部落地**（3/3、3/3、4/4）；真机截图通过。

## 一、规格 → 实现

| 组件 | Figma 参数 | 实现 |
|---|---|---|
| Checkboxes（3） | Show focus indicator / Type(6) / State(5) | `checked` + `indeterminate` + `isError` 组合出 6 档 Type；`enabled` + 状态层覆盖 5 档 State；`showFocusIndicator` |
| Radio buttons（3） | Show focus indicator / Selected(2) / State(5) | `selected` / `enabled` / `isError` / `showFocusIndicator` |
| Switch（4） | Show focus indicator / Selected(2) / State(5) / Icon(2) | `checked` / `showThumbIcon` / `enabled` / `isError` / `showFocusIndicator` |

底层直接用 Material 3 `Checkbox` / `TriStateCheckbox` / `RadioButton` / `Switch`，获得官方状态层、handle 动画（16→24→28）与 48dp 触控目标。

## 二、官方尺寸（已锁进单测）

| 项 | 值 |
|---|---|
| Checkbox 容器 / 圆角 | 18dp / 2dp |
| Radio 图标 | 20dp（与 Checkbox 刻意不同） |
| Switch 轨道 | 52 × 32dp，描边 2dp |
| Switch handle（未选/选中/按下） | 16 / 24 / 28dp |
| handle 图标 | 16dp |
| 状态层 / 触控目标 | 40dp / 48dp |

尺寸常量在 [M3SelectionMetrics.kt](<app/src/main/java/com/example/m3appbar/ui/M3SelectionMetrics.kt>)，单测 [M3SelectionMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3SelectionMetricsTest.kt>)。

## 三、颜色

- Checkbox：选中 `primary/onPrimary`，未选 `onSurfaceVariant`；error 用 `error/onError`。
- Radio：选中 `primary`，未选 `onSurfaceVariant`；error 用 `error`。
- Switch：选中 `primary` 轨道 + `onPrimary` handle；未选 `surfaceContainerHighest` + `outline`；handle 图标：选中 ✓ / 未选 ✕（官方要求明确语义）。
- **error 态的 Switch 颜色官方未给映射**，这里用 `error / onError / errorContainer` 近似（已在限制中注明）。

## 四、焦点与无障碍

- 焦点环抽成公共 `Modifier.m3FocusRing(focused, show)`（3dp，半径 20dp 近似），三个控件共用。
- 触控目标由 Material 3 组件自带 48dp；18/20dp 框体不被当作热区。
- Checkbox 的 indeterminate 使用 `TriStateCheckbox`，读屏语义正确。
- Radio 组语义需要调用方用 `Modifier.selectableGroup()` 包裹（文档已注明，demo 未做组容器）。

## 五、真机验证

![Checkbox / Radio / Switch：正常、选中、indeterminate、error、disabled](<screenshots/selection-controls.png>)

核对结果：

| 项 | 结果 |
|---|---|
| Checkbox 选中 / indeterminate / error 边框 / disabled | ✅ |
| Radio 选中 / 未选 / error / disabled | ✅ |
| Switch off（✕）/ on（✓）/ error / disabled，handle 位置与图标 | ✅ |
| 18 vs 20dp 尺寸差异 | ✅ 肉眼可辨 |

## 六、验证命令

| 项 | 结果 |
|---|---|
| `:app:compileDebugKotlin` | 0 error |
| `:app:testDebugUnitTest` | **6 tests / 0 failures**（新增选择控件尺寸回归） |
| `:app:assembleDebug` | 通过 |
| 真机 `adb install` | Success |

## 七、已知限制

1. **Switch error 颜色为近似**（官方未给文字映射）；
2. 焦点环尺寸 20dp 为近似（官方只给「Show focus indicator」开关）；
3. 未提供「父 Checkbox + 子项」联动组件，只有单控件；
4. Radio 的 `selectableGroup` 组容器与预选逻辑需调用方负责；
5. 未做密度模式、RTL、pixel diff。

## 八、下一步

按计划：**Slider**（三档：standard / centered / range），之后是 **Chip**（5 个变体）。
