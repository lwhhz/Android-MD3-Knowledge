# P2 旧行核对：Extended FAB / .Shape（2026-11-18）

> 范围：核对后补剩余参数　结论：**Extended FAB 6/6（补 focus/state）；.Shape 1/1（补 9 变体函数）**。

## 一、Extended FAB

**旧行**：`M3ExtendedFAB (M3Buttons.kt) 6/0/6`，看起来完全未实现。
**实际**：组件位于 [M3Fabs.kt](<components/M3Fabs.kt>)，早已在 FAB 批次实现，并在主 demo 使用。

| Spec 参数 | 状态 | 说明 |
|---|---|---|
| Label text | ✅ | `label` + titleMedium |
| Icon | ✅ | `imageVector` + contentDescription |
| Show focus indicator | ❌ | 未做 focus ring |
| Size（3） | ✅ | Small 56 / Medium 80 / Large 96dp |
| Color（6） | ✅ | `M3FabColor` 容器/内容映射 |
| State（4） | ⚠️ | 只做 hover elevation + pressed state layer；focused 未做 |

**修正为 4/6 后，本轮补到 6/6**：新增 `showFocusIndicator` 与 `focusable()` focused 状态；Show focus 用 2dp secondary 边框。

## 二、.Shape

- 官方 Key：`11a508c9e911f5512cb60c62692a37493c27e222`，节点 `55343:12390`，9 个变体；
- 它是 **内部构建块**（internal building block），不是独立组件；
- 当前 `components/` 无同名函数，统计里保留 **0/1**；
- 本轮新增 `M3Shape(variant: Int)` + `M3ShapeVariants`：1–9 映射 circle/square/pill/diamond/hexagon/pentagon/triangle/arch/gem；`.Shape` 由 0/1 修正为 **1/1**。

## 三、统计更新

| 范围 | 参数 | 已落地 | 未落地 | 落地率 |
|---|---:|---:|---:|---:|
| P2 | 43 | 18 | 25 | 41.9% |
| 合计 | 538 | 338 | 200 | 62.8% |

## 四、验证

- 本轮补 focus/state 与 9 变体函数；**30 tests / 0 failures**，最新 APK 已安装；
- Extended FAB 已在主 demo 渲染（见 FAB 分区截图）。

## 五、遗留

1. Extended FAB 的 focus 边框**未做真机 Tab 聚焦截图验证**；
2. `.Shape` 已补 9 个变体映射，但未做逐形状截图。