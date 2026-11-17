# Dialog 试点记录（阶段 2 · P0 第四批）

> 日期：2026-11-17　范围：Basic dialog / List dialog / Scrollable list dialog　结论：**Basic 5/5、List 3/4、Scrollable 3/4**；真机打开与关闭已验证。

## 一、实现

| 组件 | 入口 | 说明 |
|---|---|---|
| Basic dialog | M3BasicDialog | 28dp 圆角、280–560dp 宽、24dp 内边距；可选 icon、divider、content、两个操作 |
| List dialog | M3ListDialog | 标题 + 列表项，点击即选择，无底部按钮 |
| Scrollable list dialog | M3ScrollableListDialog | 内容区限高 320dp 内部滚动，标题固定 |

底层用 BasicAlertDialog + Surface 自绘容器，便于固定 28dp 圆角与 24dp 内边距。

## 二、官方尺寸（已锁进单测）

| 项 | 值 |
|---|---|
| 容器圆角 | 28dp |
| 容器宽度 | 280–560dp |
| 内边距 | 24dp |
| Icon | 24dp |
| 图标→标题 / 标题→正文 | 16dp / 16dp |
| 正文→操作区 | 24dp |
| 按钮间距 | 8dp |
| Divider | 1dp |
| Full-screen header / bottom bar | 56dp / 56dp |

常量在 [M3DialogTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3DialogTokens.kt>)，单测 [M3DialogMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3DialogMetricsTest.kt>)。

## 三、真机验证

![Basic dialog：icon 居中、标题/正文、divider、Cancel + Delete](<screenshots/dialog-basic.png>)

![Scrollable list dialog：标题固定，列表内部滚动](<screenshots/dialog-scrollable.png>)

| 项 | 结果 |
|---|---|
| Basic：icon 居中、标题居中、正文、divider、两个操作 | ✅ |
| Basic：Cancel 关闭 | ✅（点击后回到页面） |
| Scrollable：Tag 1–7 显示，内容限高 | ✅ |
| 遮罩 scrim | ✅ |
| 点击列表项关闭 | ✅（onItemClick 由 demo 关闭） |

## 四、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **13 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 五、参数落地

| Kit 组件 | 参数 | 落地 | 说明 |
|---|---|---|---|
| Basic dialog | Icon(2)/divider/content/actions | **5/5** | 有/无 icon、divider、content、两个按钮 |
| List dialog | Icon(2)/title/list | **3/4** | 无 supporting text 参数 |
| Scrollable list dialog | 同上 | **3/4** | 无 supporting text 参数 |
| XR/XR Dialog | 8 个 | 0/8 | P2，未做 |

## 六、已知限制

1. **Full-screen dialog 未实现**（P0 报告含，但 Figma 缺口表无此组件行）。
2. List / Scrollable 未提供 supporting text 参数。
3. Scrollable 内容区固定 320dp 上限，未按可用高度自适应。
4. 未做内容滚动时标题/按钮固定的完整验证（当前标题天然在滚动区外，无底部按钮）。
5. 未做焦点陷阱/Tab 循环的自动化验证；BasicAlertDialog 依赖官方行为。
6. 未做 RTL、密度、pixel diff。

## 七、下一步

按 P0 顺序：**Snackbar → Search**。