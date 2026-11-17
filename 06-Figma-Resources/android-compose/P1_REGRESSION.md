# P1 回归记录（2026-11-18）

> 范围：P1 七个组件族 + 构建/单测 + 真机 smoke + P0 pixel 工具自检　结论：**可交付，但有明确已知缺口**。

## 一、构建与单测

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **24 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 install（e91fb0f） | Success |
| 冷启动 | MainActivity 前台，首屏正常 |

## 二、P1 分区与证据

| 组件族 | 真机状态 | 关键交互 | 证据 |
|---|---|---|---|
| Progress/Loading | ✅ 分区渲染 | 动画持续运行 | `screenshots/` / [PROGRESS_TRIAL.md](<PROGRESS_TRIAL.md>) |
| Toolbar | ✅ docked/floating/vibrant/vertical | MoreVert 打开 Delete/Archive；FAB 槽 | [TOOLBAR_TRIAL.md](<TOOLBAR_TRIAL.md>) |
| Bottom sheet | ✅ Modal + Standard | 打开 Modal、返回键关闭、Standard 共存 | [SHEET_TRIAL.md](<SHEET_TRIAL.md>) |
| Segmented button | ✅ 单选/多选/D2 | 点击切换选中 | [SEGMENTED_BUTTON_TRIAL.md](<SEGMENTED_BUTTON_TRIAL.md>) |
| Connected button group | ✅ single/multi/required/square | 点击切换、required 禁止全不选 | [CONNECTED_GROUP_TRIAL.md](<CONNECTED_GROUP_TRIAL.md>) |
| Carousel | ✅ multi-browse/hero/full-screen/MULTI_ASPECT | 官方组件滑动、图片显示、Show all | [CAROUSEL_TRIAL.md](<CAROUSEL_TRIAL.md>) |
| Date/time pickers | ✅ Modal date / dial / keyboard | Modal 日期打开、选中、Cancel/OK；Dial 12h；Keyboard 24h | [DATETIME_TRIAL.md](<DATETIME_TRIAL.md>) |

说明：本轮新增/调整 demo 后，冷启动首屏正常；快速 PageDown smoke 可到达 Date/Time pickers 分区。Docked input 因自动聚焦弹 IME 已从常驻 demo 移除，组件仍保留。

## 三、Pixel Diff

```text
PASS  dialog.png  diff=0/2451600  ratio=0.0000%
PASS  menu.png    diff=0/2451600  ratio=0.0000%
PASS  navigation.png  diff=0/2451600  ratio=0.0000%
PASS  search.png  diff=0/2451600  ratio=0.0000%
PASS  snackbar.png  diff=0/2451600  ratio=0.0000%
PASS  tabs.png   diff=0/2451600  ratio=0.0000%
threshold=1.00% tolerance=8/channel failed=0
```

⚠️ 本次为**基线→current 的自检**（同一批文件），只验证 PixelDiff 工具仍可运行，**不等于**对最新 APK 六类截图的重新视觉回归。P1 未新增 pixel 基线。

## 四、P1 参数覆盖（表内统计）

| 范围 | 参数 | 已落地 | 未落地 | 落地率 |
|---|---:|---:|---:|---:|
| P1 | 180 | 147 | 33 | 81.7% |
| P0 | 315 | 173 | 142 | 54.9% |
| P2 | 43 | 2 | 41 | 4.7% |
| 合计 | 538 | 322 | 216 | 59.9% |

## 五、已知缺口（P1 内）

1. **Date picker**：MONTH/YEAR、全屏 Range、清除按钮、错误态未做；Docked 仅 Input。
2. **Toolbar**：docked+vertical 回落 horizontal；滚动隐藏未验证；FAB 仅 floating。
3. **Bottom sheet**：640dp 逻辑完成但无平板视觉回归；50% 高度未扣系统栏。
4. **Carousel**：MULTI_ASPECT 因官方 keyline API internal 用 LazyRow 降级；全屏仍近似。
5. **Connected button group**：未用官方 ButtonGroup 容器，无 overflow/宽度动画。
6. 全项目缺：RTL、密度矩阵、Figma 像素还原、P1 pixel 基线、无障碍读屏走查。

## 六、结论

- P1 七个组件族均已接入主 demo 并可运行，24 项单测全绿；
- 明确接受上述缺口后，P1 可收尾；
- 不建议把“P1 完成”表述为“所有 Figma 参数 100% 还原”。

## 七、下一步建议

1. 若继续深度：补 Date picker MONTH/YEAR、Rail 垂直方向、Toolbar 滚动隐藏；
2. 若转 P2：先做 Tooltip/XR/Utilities 等剩余组件；
3. 工程收尾：补 P1 pixel 基线、RTL/密度 smoke、总 README 状态表。