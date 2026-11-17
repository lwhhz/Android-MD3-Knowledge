# Search 试点记录（阶段 2 · P0 第六批 / 最后一批）

> 日期：2026-11-17　范围：M3SearchBar、Full-screen / Docked layout、Baseline 两变体　结论：**五个入口全部实现**；真机展开、建议列表与键盘已验证。

## 一、实现

| 组件 | 说明 |
|---|---|
| M3SearchBar（状态化） | `state: SearchBarState` + `textFieldState: TextFieldState`；与 ExpandedFullScreenSearchBar 共享 state；建议列表、leading/trailing、avatar |
| M3SearchBar（旧 query 入口） | 保留签名，内部已委托状态化实现（String ↔ TextFieldState、expanded ↔ SearchBarState 双向同步） |
| M3SearchFullScreenLayout | 强制展开的全屏搜索（compact） |
| M3SearchDockedLayout | DockedSearchBar，建议在下方（medium/expanded） |
| Baseline × 2 | 4dp 方角 + surfaceContainer 容器 |

## 二、官方尺寸（已锁进单测）

| 项 | 值 |
|---|---|
| 宽 | 360–720dp |
| 高 | 56dp |
| 未聚焦 / 聚焦边距 | 24dp / 12dp |
| Avatar | 30dp |
| 图标与标签间距 | 4dp |
| Docked 最小高 | 240dp |

常量在 [M3SearchTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3SearchTokens.kt>)，单测 [M3SearchMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3SearchMetricsTest.kt>)。

## 三、真机验证

![Search 折叠态：contained + docked 两个入口](<screenshots/search-collapsed.png>)

![Search 状态化展开：真正的全屏（覆盖整个窗口）](<screenshots/search-fullscreen.png>)

| 项 | 结果 |
|---|---|
| contained 搜索栏（min 360dp、56dp 高） | ✅ |
| 状态化入口：点击展开为**真正全屏**（非 700dp） | ✅ |
| 建议列表 Compose/Material 3/Kotlin/Figma | ✅ |
| 点击 Kotlin 建议：回填输入 + 收起 | ✅ |
| 返回键收起全屏搜索，App 保持前台 | ✅ |
| 键盘弹出、App 保持前台 | ✅ |
| docked 入口存在 | ✅ |
| baseline 两变体可编译（未单独截图） | ✅ |

## 四、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **16 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 五、参数落地

| Kit 组件 | 参数 | 落地 | 说明 |
|---|---|---|---|
| Search bar | State(3)/Show avatar(2)/icons/placeholder | **5/6** | State 只做 enabled/disabled 近似 |
| Search full-screen | Configuration(2)/suggestions/placeholder | **4/5** | 无独立 input-text 参数 |
| Search docked | 同上 | **4/5** | 同上 |
| Baseline × 2 | Configuration(2)/baseline 视觉 | **3/4** | 未做 baseline 的完整输入态 |

## 六、已知限制（重要）

1. 状态化入口用 `ExpandedFullScreenSearchBar` 实现全屏；旧的 query 重载仍保留，但**不要在无界滚动容器中直接展开**（会复现约束异常）。
2. 状态化入口在 Demo 中无额外高度容器，已验证全屏；旧入口仍属兼容路径。
3. State 轴只做 enabled/disabled 近似，没有逐状态视觉。
4. Baseline 两个变体只换方角与容器色，未单独截图/逐项验证。
5. 建议列表为简单文本行，未做历史/分组/高亮。
6. 未做 RTL、密度、pixel diff。

## 七、下一步

P0 六类（Tabs / Navigation / Menu / Dialog / Snackbar / Search）已全部走完；建议进入 **P1**（Carousel / Date picker / Sheets / Toolbar / Loading），或先做 P0 的回归与 pixel diff。