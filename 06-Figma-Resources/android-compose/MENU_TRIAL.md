# Menu 试点记录（阶段 2 · P0 第三批）

> 日期：2026-11-17　范围：Vertical Menu（Standard/Vibrant）、Standard/Vibrant 菜单项、Baseline Menu　结论：**Menu 6/7、菜单项 5/7、Baseline 2/2**；真机弹出与选中态已验证。

## 一、实现

| 组件 | 入口 | 说明 |
|---|---|---|
| Vertical Menu | M3Menu | Standard / Vibrant；支持分组、section label、菜单项列表 |
| Standard 菜单项 | M3MenuItemStandard | 48dp 高、leading/trailing、selected 对勾、disabled、divider |
| Vibrant 菜单项 | M3MenuItemVibrant | 同上，tertiary 色系 |
| Baseline Menu | M3MenuBaseline | 4dp 方角、surfaceContainer，Density 三档 |

数据模型 M3MenuItemData(label, supportingText, leadingIcon, trailingText, enabled, selected)。

## 二、官方尺寸（已锁进单测）

| 项 | 值 |
|---|---|
| 容器宽 | 112–280dp |
| Baseline 圆角 | 4dp |
| Vertical 圆角 | 16dp（Expressive，推断） |
| item 高 | 48dp |
| 左右内边距 | 12dp |
| 元素间距 | 12dp |
| 图标 | 24dp |
| Divider | 1dp，上下 8dp |
| Elevation | 3dp |

常量在 [M3MenuTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3MenuTokens.kt>)，单测 [M3MenuMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3MenuMetricsTest.kt>)。

## 三、真机验证

![Standard menu：Edit 分区、Cut/Copy/Paste、选中对勾、支持文本](<screenshots/menu.png>)

![Vibrant menu：tertiary container 与 selected tertiary](<screenshots/menu-vibrant.png>)

![Baseline menu：4dp 方角、三档 Density 入口](<screenshots/menu-baseline.png>)

| 项 | 结果 |
|---|---|
| Standard：section label、Group divider、Copy 选中、Paste 禁用 | ✅ |
| Vibrant：tertiary 容器、选中项浅色反白 | ✅ |
| Baseline：4dp 方角、item 48dp | ✅ |
| 点击 item 关闭菜单 | ✅（onItemClick 由 demo 关闭） |

## 四、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **12 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 五、参数落地

| Kit 组件 | 参数 | 落地 | 说明 |
|---|---|---|---|
| Menu | Theme(2)/Groups(3)/scrollbar/section label | **7/7** | 滚动条已补 |
| Menu item/Standard | State/Selected/leading/trailing/divider/focus | **6/7** | focus ring 已补；hover/pressed 未单独绘制 |
| Menu item/Vibrant | 同上 | **6/7** | 同上 |
| Menu (baseline) | Density(3)/label | **2/2** | 三档密度影响 item 垂直 padding |

## 六、已知限制

1. 菜单项只有 enabled/selected；**hover / pressed 未分别绘制**。
2. 菜单项 `supportingText` 是额外扩展，baseline 不支持；vertical 的圆角 16dp 为推断值。
3. 子菜单（submenu）未做；Compose 官方也未提供。
4. 窄屏自适应（菜单改 bottom sheet）未做。
5. 未做键盘完整导航（字母跳转、Escape 由 DropdownMenu 提供）、RTL、pixel diff。
6. 滚动条与焦点环只覆盖自定义 M3Menu / 菜单项；Baseline 菜单仍用官方 DropdownMenuItem，不含这两项。

## 七、滚动条与焦点修复（2026-11-17）

- 滚动条：M3Menu 内容改为 Box(heightIn max 320dp) + 内层 verticalScroll，右侧 4dp 圆角 thumb 按 scrollState 比例计算；滚动时 thumb 跟随。
- 焦点环：菜单项用 MutableInteractionSource + collectIsFocusedAsState，聚焦时绘制 2dp secondary 圆角边框；Demo 中用 adb keyevent TAB 触发。

| 项 | 结果 |
|---|---|
| 长菜单（20 项）出现常驻滚动条 | ✅ |
| 滑动菜单后 thumb 下移 | ✅（见第二张图） |
| TAB 聚焦 Action 1 出现 2dp focus ring | ✅ |

![长菜单：滚动条 + TAB 焦点环](<screenshots/menu-scrollbar-focus.png>)

![滑动内容后：thumb 跟随移动](<screenshots/menu-scrolled.png>)

## 八、下一步

按 P0 顺序：**Dialog → Snackbar → Search**。