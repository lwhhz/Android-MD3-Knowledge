# 组件差异修复记录（2026-11-18）

> 决策执行：Search 尺寸、Date picker 扩展、Carousel/Connected KB 限制、App bar flexible、Toolbar floating 边距。

## 1. Search

- 已应用：`widthIn(360–720)`、`height(56dp)`、avatar 30dp、baseline 4dp 圆角（baseline 变体）。
- **未能应用 24/12 内部 padding**：1.5.0-rc01 的 `SearchBarDefaults.InputField` 两个公开重载都没有 `contentPadding` 参数；强行包 padding 会改掉输入框容器背景，故保留官方默认内边距。
- `M3SearchMetrics.unfocusedPadding/focusedPadding` 目前仅作文档 anchor，不参与渲染；已如实记录。

## 2. Date / Time

- 新增 `Type.MONTH` / `Type.YEAR` 自定义月/年选择（官方 DatePicker 无公开月/年视图）。
- 新增 `Type.FULL_SCREEN_RANGE`：`Dialog(usePlatformDefaultWidth=false)` + 全屏 `DateRangePicker` + Close/Save。
- Dial / Keyboard 的 12/24h 改为读系统 `DateFormat.is24HourFormat(context)`；`format` 参数保留但不再强制。
- 限制：月/年选择是自绘网格，不是官方月/年视图；未做逐月/逐年截图。

## 3. Carousel / Connected

- 代码保留现有折衷（MULTI_ASPECT LazyRow；Connected Row+ToggleButton）。
- 已在 KB 写入限制：`02-Components/carousel.md`、`02-Components/button-group.md`。

## 4. App bar

- `AppBarConfiguration` 新增 `MEDIUM_FLEXIBLE` / `LARGE_FLEXIBLE`，`from()` 同时接受 Old/New 字符串；高度仍 112/120。
- 新增 `scrollBehavior: TopAppBarScrollBehavior?`，高度按 `state.heightOffset` 收缩（最低 64dp）。
- 已用隔离 Dialog 验证：滚动列表后 app bar 从 112dp 收缩到 64dp（[appbar-scroll.png](<screenshots/appbar-scroll.png>)）；Bottom app bar 仍保留用于 baseline 对照。

## 5. Toolbar

- floating 横/纵边距 16/24dp 已真正加到 `floatingModifier`。
- `scrollBehavior` 由调用方通过 `nestedScroll` 驱动；隔离 Dialog 验证列表滚动后 floating toolbar 隐藏（[toolbar-scroll-hide.png](<screenshots/toolbar-scroll-hide.png>)）。

## 验证

| 项 | 结果 |
|---|---|
| :app:testDebugUnitTest | **31 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 | 最新 APK 已安装 |

## 未完成

1. Search 内部 24/12 padding（API 限制）。
2. ~~App bar / Toolbar 滚动隐藏真机验收~~ 已完成。
3. Date month/year 自绘 UI 的视觉验收与无障碍。