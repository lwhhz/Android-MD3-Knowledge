# P2 记录：Keyboard / Layout Grid

> 日期：2026-11-18　范围：M3Keyboard、M3ExamplesLayoutGrid　结论：**2/2 + 2/2，均为视觉/示例组件，不是输入或生产布局容器**。

## 一、实现

| 组件 | 实现 |
|---|---|
| Keyboard | 4 Configuration：Base QWERTY / Alphanumeric / Keypad / Numeric only；3 Style：Portrait / Landscape / Floating（宽 300dp、圆角 28dp） |
| Layout grid | Window size class 5 档 → 4/8/12 列；margin 16/24dp；Layout regions 预留 0/80/240dp navigation 区 |

## 二、真机验证

![Keyboard：QWERTY + floating keypad；Layout grid：12 列示例](<screenshots/p2-keyboard-grid.png>)

| 项 | 结果 |
|---|---|
| Base keyboard 竖排 | ✅ |
| Keypad floating（300dp） | ✅ |
| Expanded + None：12 列 | ✅ |
| 标题与统计文案 | ✅ |

## 三、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **29 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 四、参数落地

| 组件 | 参数 | 落地 | 说明 |
|---|---|---|---|
| Keyboard | 2 | **2/2** | Configuration 4 + Style 3 |
| Layout grid | 2 | **2/2** | Window class 5 + Layout regions 3 |

## 五、已知限制

1. **Keyboard 是视觉 mock**：不连接系统 IME、不产生输入、不包含 space/delete/enter 等功能键。
2. Style 只改变高度/宽度/圆角，不是完整横竖屏重排。
3. **Layout grid 是示例**：demo 用 411dp 窗口展示 expanded 12 列；没有真实 navigation 组件；导航区宽度 240dp 时剩余列会被压窄。
4. 未做断点切换动画、列间距 token 化、RTL、pixel diff。

## 六、下一步

P2 剩余：XR 家族（先评估）、Extended FAB 旧行核对、.Shape 缺失行。