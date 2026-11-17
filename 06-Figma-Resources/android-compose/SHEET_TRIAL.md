# Bottom Sheet 试点记录（P1 · 第三批）

> 日期：2026-11-18　范围：Modal / Standard bottom sheet　结论：**3/3 参数落地**；modal 打开/关闭与 standard 共存已验证。

## 一、实现

| 形态 | 实现 |
|---|---|
| Modal | `ModalBottomSheet` + scrim；`sheetState = rememberModalBottomSheetState()`，dragHandle 可开关 |
| Standard | 自绘 Surface：顶部 28dp 圆角、surfaceContainerLow、无 scrim，与主内容共存 |
| 拖拽手柄 | Modal 用官方 `dragHandle` 槽；Standard 用 `BottomSheetDefaults.DragHandle()` |
| 内容宽度 | `widthIn(max = 640dp)` |

内容通过 `content: @Composable ColumnScope.() -> Unit` 传入。

## 二、官方尺寸（已锁进单测）

| 项 | 值 |
|---|---|
| 最大宽度 | 640dp |
| 大屏上边距 / 左右边距 | 56dp / 56dp |
| 默认上边距 | 72dp |
| 顶部圆角 | 28dp |
| 拖拽手柄上下内边距 | 22dp |
| 手柄触控热区 | 顶部 48dp |

常量在 [M3SheetTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3SheetTokens.kt>)，单测 [M3SheetMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3SheetMetricsTest.kt>)。

## 三、真机验证

![Standard bottom sheet：无 scrim，与主内容共存](<screenshots/bottom-sheet-standard.png>)

![Modal bottom sheet：scrim + 手柄 + 标题/正文/Cancel/Share](<screenshots/bottom-sheet.png>)

![Tall modal：初始高度约 50%，列表内部滚动](<screenshots/bottom-sheet-tall.png>)

| 项 | 结果 |
|---|---|
| Modal 打开显示 scrim、阻断背景 | ✅ |
| Handles：顶部拖拽手柄 | ✅ |
| Standard 无 scrim、inline 共存 | ✅ |
| 返回键关闭 Modal，App 保持前台 | ✅ |
| 高内容 Modal：初始高度约 50%，列表可滚动 | ✅ |
| 640dp 限制 + 宽屏 56dp 边距逻辑 | ✅（手机不可视验证，helper 有单测） |

## 四、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **19 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 五、参数落地

| 参数 | 落地 | 说明 |
|---|---|---|
| Modal（True / False） | ✅ | ModalBottomSheet / 自绘 Standard Surface |
| Show drag handle | ✅ | 可关，Modal 用官方槽、Standard 用 DragHandle |
| Content | ✅ | 任意 ColumnScope 内容 |

## 六、已知限制

1. **Modal 的 640dp 最大宽只在内容层生效**；ModalBottomSheet 自身在平板上仍按 Compose 默认宽度，未包一层 56dp 边距。
2. **初始高度 50% 上限未强制**：内容可超过半屏；未做拖拽预设高度循环。
3. Predictive back、焦点陷阱、关闭后焦点归还未验证。
4. 50% 高度按“屏幕高度一半”计算，未扣除状态栏/导航栏，实际可见比例可能略小于 50%。
4. Standard 为 inline 展示，不支持拖拽/高度切换。
5. Side sheet 是独立组件，本批未做；未做 XR、RTL、密度、pixel diff。

## 七、下一步

P1 下一批建议：**Segmented button** → Carousel → Date pickers；也可先补 sheet 的 640dp 响应式/高度预设。