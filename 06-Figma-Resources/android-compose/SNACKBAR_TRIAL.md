# Snackbar 试点记录（阶段 2 · P0 第五批）

> 日期：2026-11-17　范围：Snackbar（action / close / 单双行）　结论：**4/4 参数落地**；真机显示、Undo 动作与关闭已验证。

## 一、实现

| 入口 | 说明 |
|---|---|
| M3Snackbar | 视觉组件：inverseSurface 容器、inverseOnSurface 文本、inversePrimary 动作；单行 48dp / 双行 64dp；最多一个 action；close 可选 |
| M3SnackbarHost | 接到 SnackbarHostState，action 调用 performAction，close 调用 dismiss |

## 二、官方规格 / 近似值

| 项 | 值 | 来源 |
|---|---|---|
| 单行 / 双行高度 | 48dp / 64dp | Guidelines |
| 左右内边距 | 16dp | 近似（Specs 无数值） |
| 圆角 | 4dp | 近似（官方未给） |
| 最大宽度 | 600dp | 近似（官方未给） |
| 自动消失 | 无 action 4–10 秒；有 action 不自动消失 | Guidelines |
| 颜色 | inverseSurface / inverseOnSurface / inversePrimary | Specs |

常量在 [M3SnackbarTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3SnackbarTokens.kt>)，单测 [M3SnackbarMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3SnackbarMetricsTest.kt>)。

## 三、真机验证

![Snackbar：两行文案 + Undo + 关闭](<screenshots/snackbar.png>)

| 项 | 结果 |
|---|---|
| 两行文案（64dp） | ✅ |
| Undo 动作（inversePrimary） | ✅ |
| 关闭图标（inverseOnSurface） | ✅ |
| 点击 Undo 后 Snackbar 关闭 | ✅（uiautomator 中文案消失） |
| 反色容器（深色主题下为浅色浮条） | ✅ |

## 四、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **14 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 五、参数落地

| Kit 组件 | 参数 | 落地 | 说明 |
|---|---|---|---|
| Snackbar | Configuration(3)/#lines(2)/close affordance/label | **4/4** | 单/双行、action、close 均可组合 |

## 六、已知限制

1. 圆角 4dp、内边距 16dp、最大宽 600dp 均为近似；官方 Specs 只给测量图，未给数字。
2. 自动消失已实现：无 action → Short（约 4 秒）；有 action → Indefinite。
3. close affordance 官方认为“非必需”，组件按 Kit 的 Show close 轴保留。
4. FAB 避让用 Scaffold 演示；导航栏避让与“无导航才全宽”的条件判断仍未做。
5. 未做 RTL、密度、pixel diff。

## 七、自动消失 / FAB 避让（2026-11-17）

- 新增 `showM3Snackbar` 扩展：无 action → `SnackbarDuration.Short`（约 4 秒）；有 action → `SnackbarDuration.Indefinite`。
- 纯映射函数 `m3SnackbarDuration(actionLabel)` 已加单测。
- Demo 改为 `Scaffold(snackbarHost + floatingActionButton)`，Scaffold 自动把 Snackbar 放到 FAB 上方。

| 项 | 结果 |
|---|---|
| 无 action：显示后 5 秒，uiautomator 中文案消失 | ✅ |
| 有 action：5 秒后仍在（Indefinite） | ✅ |
| Snackbar 位于 FAB 上方 | ✅ |

![Auto-dismiss：Scaffold 中位于 FAB 上方，4 秒后消失](<screenshots/snackbar-auto-dismiss.png>)

![With action：5 秒后仍显示，等待用户操作](<screenshots/snackbar-with-action.png>)

## 八、下一步

按 P0 顺序：**Search**（最后一项）。