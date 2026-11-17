# Progress / Loading 试点记录（P1 · 第一批）

> 日期：2026-11-18　范围：线性/环形 × determinate/indeterminate + Loading indicator　结论：**五个组件全部实现**；真机截图通过。

## 一、实现

| 组件 | 说明 |
|---|---|
| M3LinearDeterminateProgressIndicator | Flat（4/8dp 高度）或 Wavy；progress 0..1 |
| M3LinearIndeterminateProgressIndicator | Flat / Wavy |
| M3CircularDeterminateProgressIndicator | Flat / Wavy；24dp 起、可关 track；strokeWidth 4/8dp |
| M3CircularIndeterminateProgressIndicator | Flat / Wavy；可关 track |
| M3LoadingIndicator | Expressive LoadingIndicator / ContainedLoadingIndicator；默认动画；animated=false 时 step 0–6 取静态帧 |

## 二、官方尺寸

| 项 | 值 |
|---|---|
| 默认厚度 | 4dp（可配 8dp） |
| Stop indicator | 4dp 圆点 |
| Linear 最小宽度 | 40dp |
| Circular 尺寸范围 | 24–240dp |
| Loading 占位 / 容器 | 48dp / 38dp |
| Loading 尺寸范围 | 24–240dp |

常量在 [M3ProgressTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3ProgressTokens.kt>)，单测 [M3ProgressMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3ProgressMetricsTest.kt>)。

## 三、真机验证

![Progress / Loading：线性 determinate+indeterminate、环形 determinate+indeterminate、Loading 默认/容器](<screenshots/progress.png>)

| 项 | 结果 |
|---|---|
| 线性 determinate 60% + stop indicator | ✅ |
| 线性 indeterminate | ✅ |
| 环形 determinate 70% | ✅ |
| 环形 indeterminate | ✅ |
| Loading 默认（primary） | ✅ |
| Loading contained（primaryContainer / onPrimaryContainer） | ✅ |

## 四、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **17 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 五、参数落地

| 组件 | 参数 | 落地 | 说明 |
|---|---|---|---|
| Linear determinate | Type / Thickness / progress | **3/3** | Wavy 的 thickness 被 API 忽略 |
| Linear indeterminate | Type / Thickness / Step | **2/3** | Step 未用 |
| Circular determinate | Type / Thickness / progress | **3/3** | Wavy 的 thickness 被 API 忽略 |
| Circular indeterminate | Type / Thickness / Step / track | **3/4** | Step 未用 |
| Loading | Steps / container | **2/2** | step 取 7 个形状帧之一 |

## 六、已知限制

1. **Wavy 变体的 thickness 无法设置**：`LinearWavyProgressIndicator` / `CircularWavyProgressIndicator` 1.5.0-rc01 没有 strokeWidth 参数，传了会编译失败；Wavy 仅做形状开关。
2. **Step 参数只对 Loading 生效**：圆形 indeterminate 的 Step 轴未映射到动画帧，标记为缺口。
3. Loading 的静态 step 帧是一组形状，不是“进度百分比”；官方禁止 loading → determinate 过渡。
4. 未做 pull-to-refresh、按钮内嵌 loading（去 track、用内容色）。
5. 未做 RTL、密度、pixel diff。

## 七、下一步

P1 下一批建议：**Toolbar** → Bottom sheet → Segmented button → Carousel → Date pickers。

## 回归补充（2026-11-18）

- 修复：`M3LoadingIndicator()` 之前始终传 `progress`，导致默认渲染为静态实心圆；现默认调用无 `progress` 的动画重载，`animated=false` 才取静态帧。
- 真机截图：[screenshots/progress-loading-animated.png](<screenshots/progress-loading-animated.png>)。
