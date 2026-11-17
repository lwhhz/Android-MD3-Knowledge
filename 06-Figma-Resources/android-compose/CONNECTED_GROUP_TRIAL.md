# Connected Button Group 试点记录（P1 · 第五批）

> 日期：2026-11-18　范围：round / square、XS–XL、single / multi / required　结论：**5/5 参数落地**；真机截图通过。

## 一、实现

| 项 | 说明 |
|---|---|
| 容器 | `Row` + `Arrangement.spacedBy(2.dp)`，组宽 `fillMaxWidth`、每段 `weight(1f)` |
| 形状 | `ButtonGroupDefaults.connectedLeading/Middle/TrailingButtonShapes()`；square 用 4dp 圆角覆盖 |
| 尺寸 | `ToggleButtonSize.ExtraSmall/Small/Medium/Large/ExtraLarge`（32/40/56/96/136dp） |
| 选择模式 | SINGLE / MULTI / REQUIRED（required 禁止取消最后一项） |
| 颜色 | 使用 ToggleButton 默认 toggle 色系，组本身无颜色属性 |

## 二、官方规格

| 项 | 值 |
|---|---|
| 组内间距 | 2dp |
| 最小宽度 | 48dp |
| 尺寸档 | XS–XL（跟随 ToggleButton 尺寸） |
| 圆角 | round：4/8/8/16/20dp 由 connectedShapes 提供；square：4dp |
| 触控目标 | ≥48dp |

常量在 [M3ConnectedButtonGroupTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3ConnectedButtonGroupTokens.kt>)，单测 [M3ConnectedButtonGroupMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3ConnectedButtonGroupMetricsTest.kt>)。

## 三、真机验证

![Connected group：单选 Day / 多选 Wi-Fi+BT / required Med / square B](<screenshots/connected-button-group.png>)

| 项 | 结果 |
|---|---|
| Single Day/Week/Month，选中变 pill | ✅ |
| Multi Wi-Fi/BT + NFC，多选保持 | ✅ |
| Required Low/Med/High，Med 选中 | ✅ |
| Square A/B/C，4dp 方角 | ✅ |
| 尺寸 S/M 档可见 | ✅ |

## 四、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **22 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 五、参数落地

| 参数 | 落地 | 说明 |
|---|---|---|
| Type（Round / Square） | ✅ | connected shapes；square 用 4dp 覆盖 |
| Size（XS–XL） | ✅ | ToggleButtonSize 五档 |
| 选择模式（额外） | ✅ | single / multi / required |

## 六、已知限制

1. **未使用官方 `ButtonGroup` 容器**：其 `toggleableItem` 不暴露 `buttonSize`，无法满足 XS–XL；这里用 Row + ToggleButton，因此**没有 overflow indicator、宽度动画、相邻挤压**。
2. **大尺寸在窄容器内会换行/挤压**：L/XL 的 ToggleButton 内边距大，标签在 1/3 宽度下可能换行；demo 用 S/M 规避。
3. Square 为 4dp 全角近似，官方 square 的内外圆角组合未逐项复刻。
4. 未做 RTL、键盘组内方向键、pixel diff；未接入 overflow。
5. Segmented 与 connected 并存：存量用 segmented，新设计用 connected。

## 七、下一步

P1 剩余建议：**Carousel** → Date pickers；也可回头补 Carousel 的 9 个参数。