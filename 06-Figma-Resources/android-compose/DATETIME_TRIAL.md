# Date & Time Pickers 试点记录（P1 · 第七批）

> 日期：2026-11-18　范围：5 个 Date/Time picker 变体　结论：**Dial 3/3、Keyboard 2/2 完成；Date 变体为官方组件的部分参数落地**。

## 一、实现

| 变体 | 实现 |
|---|---|
| Modal date | `DatePickerDialog` + `DatePicker`；RANGE 用 `DateRangePicker` |
| Input date | `DatePickerDialog` + state `DisplayMode.Input` |
| Dial | `TimePicker` + `rememberTimePickerState`；12/24、Vertical/Horizontal |
| Keyboard | `TimeInput` + 同一 state；12/24 |
| Docked input | 360dp 卡内 `DatePicker`（DisplayMode.Input） |

## 二、官方尺寸（已锁进单测）

| 项 | 值 |
|---|---|
| Docked 宽 / 高 | 360 / 460dp |
| Modal 高 | 524dp |
| 日期格 | 40dp（目标 48dp） |
| 时间选择区 | 96 × 80dp |
| 表盘 | 256dp，手柄 48dp |
| 输入框 | 96 × 72dp |
| 容器内边距 | 24dp |

常量在 [M3PickerTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3PickerTokens.kt>)，单测 [M3PickerMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3PickerMetricsTest.kt>)。

## 三、真机验证

![Modal date picker：日历、选中日期、Cancel/OK](<screenshots/date-picker-modal.png>)

![Time pickers：标题 / 触发按钮 / 表盘选择区 / 键盘输入](<screenshots/datetime-pickers.png>)

| 项 | 结果 |
|---|---|
| Modal 日期对话框打开、选中 18、Cancel/OK | ✅ |
| Dial 12h（06:29 + 上午/下午） | ✅ |
| Keyboard 24h（09:30） | ✅ |
| Docked input 日期卡 | ✅（自动聚焦会弹 IME；已从常驻 demo 移除，组件保留） |

## 四、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **24 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 五、参数落地

| 组件 | 参数 | 落地 | 说明 |
|---|---|---|---|
| Modal date | 6 | **4/6** | Type DAY/RANGE 可用；clear button、支持/标题文案未暴露 |
| Input date | 5 | **3/5** | 单一输入；类型/错误态未做 |
| Dial | 3 | **3/3** | Format 2 + Orientation 2 |
| Docked input | 2 | **1/2** | 仅 Input 形态；Type 3 未全做 |
| Keyboard | 2 | **2/2** | Format 2 |

## 六、已知限制

1. **Modal 的月份/年份选择视图未单独实现**：官方 `DatePicker` 只有日/输入模式；Type 的 MONTH/YEAR 未映射。
2. **DateRangePicker 用 500dp 固定高度**，全屏范围选择未做；未验证范围起止高亮。
3. **Docked input 自动聚焦会弹 IME**，演示时会遮挡；未做键盘控制。
4. 日期选择器使用系统 locale（当前中文），未做多语言/12h 日期格式测试。
5. 未做手动输入格式校验、错误态、清除按钮、键盘快捷键、pixel diff。

## 七、下一步

P1 组件族已基本覆盖；剩余：Date picker 的 MONTH/YEAR、范围全屏、时间选择器横排截图；或转入 P2/收尾文档。