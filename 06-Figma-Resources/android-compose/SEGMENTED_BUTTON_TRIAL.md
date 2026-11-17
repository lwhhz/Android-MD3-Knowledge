# Segmented Button 试点记录（P1 · 第四批）

> 日期：2026-11-18　范围：2–5 段、单选/多选、Density 4 档　结论：**2/2 参数落地**；真机单选/多选/密度验证。

## 一、实现

| 项 | 说明 |
|---|---|
| 单选 | `SingleChoiceSegmentedButtonRow` + `SegmentedButton(selected, onClick)` |
| 多选 | `MultiChoiceSegmentedButtonRow` + `SegmentedButton(checked, onCheckedChange)` |
| 形状 | `SegmentedButtonDefaults.itemShape(index, count)`，保证首尾圆角/中间拼接 |
| 选中态 | 填充 + 对勾（`SegmentedButtonDefaults.Icon(active)`），双通道 |
| Density | D0 40 / D1 36 / D2 32 / D3 28dp，通过 `Modifier.height` |

## 二、官方尺寸（已锁进单测）

| 项 | 值 |
|---|---|
| 默认高度 | 40dp |
| Density 每档 | -4dp（36/32/28） |
| 描边 | 1dp |
| 左右内边距 | 最小 12dp |
| 元素间距 | 8dp |
| 触控目标 | 48dp |
| 段数 | 2–5 |

常量在 [M3SegmentedButtonTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3SegmentedButtonTokens.kt>)，单测 [M3SegmentedButtonMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3SegmentedButtonMetricsTest.kt>)。

## 三、真机验证

![Segmented button：单选 3 段 / D2 密度 / 多选 3 段](<screenshots/segmented-button.png>)

| 项 | 结果 |
|---|---|
| 单选 Day/Week/Month，Day 选中带对勾 | ✅ |
| D2 密度（Small/Medium/Large） | ✅（视觉高度略降） |
| 多选 Wi-Fi/Bluetooth/NFC，Wi-Fi 选中 | ✅ |
| 首尾圆角、中间直角拼接 | ✅ |

## 四、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **21 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 五、参数落地

| 参数 | 落地 | 说明 |
|---|---|---|
| Segments（2–5） | ✅ | 4 档数量；未强制校验 |
| Density（4 档） | ✅ | 高度 40/36/32/28dp |
| 单选 / 多选 | ✅ | 两个 Row 与选择语义 |

## 六、已知限制

1. **官方已声明 Segmented button 在 Expressive 后不再推荐**，替代品是 connected button group；本组件保留给存量 UI。
2. **Density 高度可能被 48dp 最小触控目标抵消**：视觉高度能降，但实际可点区域仍应 ≥48dp；未逐档复测。
3. 未强制校验段数 2–5；超过 5 仍会渲染。
4. 允许图标+文字任意混用，未做“标签类型统一”的约束。
5. 未做 RTL、键盘方向键、pixel diff。

## 七、下一步

P1 下一批建议：**Carousel** → Date pickers；注意 Carousel 参数最多（9 个）。