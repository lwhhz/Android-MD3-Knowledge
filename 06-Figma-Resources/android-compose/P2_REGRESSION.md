# P2 回归记录（2026-11-18）

> 范围：P2 已实现组件 + 构建/单测 + 真机 smoke　结论：**P2 除 XR 外的可落地组件已完成；XR 仍为 0/25 参数**。

## 一、构建与单测

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **30 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 install（e91fb0f） | Success |
| 冷启动 | MainActivity 前台，首屏正常 |

## 二、P2 分区与证据

| 组件 | 真机状态 | 关键交互 | 证据 |
|---|---|---|---|
| Plain Tooltip | ✅ | 长按 Add 显示「Add item」，松手消失 | [P2_TRIAL.md](<P2_TRIAL.md>) / `screenshots/p2-tooltip.png` |
| Generic Avatar | ✅ | AVATAR / MONOGRAM / PORTRAIT 三态 | [P2_TRIAL.md](<P2_TRIAL.md>) / `screenshots/p2-primitives.png` |
| Shape Set | ✅ | 10 形状横向滚动 | 同上 |
| Keyboard | ✅ | QWERTY 竖排 + keypad floating | [P2_UTILITIES_TRIAL.md](<P2_UTILITIES_TRIAL.md>) / `screenshots/p2-keyboard-grid.png` |
| Layout grid | ✅ | expanded 12 列，列数/边距文案 | 同上 |
| Extended FAB | ✅ | 既有 FAB 分区；新增 focus 参数 | [P2_RECONCILE.md](<P2_RECONCILE.md>) |
| .Shape | ✅ | 9 变体函数 + 单测 | [P2_RECONCILE.md](<P2_RECONCILE.md>) |

smoke：PageDown 可达 Keyboard/Layout grid 分区，uiautomator 读到 `columns=12  margin=24dp  nav=0dp`；P2 首批分区此前已逐项截图。

## 三、Pixel Diff

- 本轮**未新增 P2 基线**，也未重新采集 P2 截图像素对比；
- P1 回归时只做基线自检，P0 六类仍无最新视觉对比；
- 结论：P2 视觉回归仍依赖人工截图，不是像素级自动化。

## 四、参数统计

| 范围 | 参数 | 已落地 | 未落地 | 落地率 |
|---|---:|---:|---:|---:|
| P2 | 43 | 18 | 25 | 41.9% |
| 合计 | 538 | 338 | 200 | 62.8% |

**P2 未落地的 25 条全部是 XR 家族**：App Bar 7、Dialog 8、Navigation Rail 3、Navigation bar 3、Toolbar 4。

## 五、已知限制

1. **XR 未实现**：依赖/设备/离线缓存未评估，0/25。
2. Tooltip 只验证按住显示，1.5 秒消失、hover/focus、多 tooltip 互斥未验证。
3. Keyboard 是视觉 mock，不提供输入。
4. Layout grid 用 411dp 窗口模拟 expanded，列宽非真实平板。
5. Extended FAB focus 边框未做 Tab 聚焦截图。
6. 无 P2 pixel 基线、RTL、密度矩阵、读屏走查。

## 六、结论

- P2 在**不引入 XR 依赖**的前提下已收尾：Avatar / Tooltip / Shape / Keyboard / Layout grid / Extended FAB / .Shape；
- 30 项单测全绿，最新 APK 已安装；
- XR 作为独立评估项保留，不计入“P2 完成”。

## 七、下一步

1. XR 可行性评估（依赖、离线、设备）；
2. 或工程收尾：P2 pixel 基线、RTL/密度、总 README。