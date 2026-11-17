# P0 回归记录（2026-11-18）

> 范围：Tabs / Navigation / Menu / Dialog / Snackbar / Search 六类 smoke　方式：构建 + 单测 + 真机逐段 presence + 关键交互；**不含 pixel diff / RTL / 密度**。

## 一、构建与单测

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **16 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 install | Success（设备 e91fb0f） |

## 二、六类 smoke 结果

| 组件 | 分区渲染（本次真机） | 关键交互 | 证据 |
|---|---|---|---|
| Tabs | ✅ `text="M3Tabs · primary / secondary / scrollable"` | 点击 Specs 切换指示器（前批验证，代码未改） | [TABS_TRIAL.md](<TABS_TRIAL.md>) / `screenshots/tabs.png` |
| Navigation | ✅ `text="M3Navigation · bar / rail / drawer"` | Settings 切换、Drawer 打开与返回键关闭（前批验证） | [NAVIGATION_TRIAL.md](<NAVIGATION_TRIAL.md>) / `screenshots/navigation*.png` |
| Menu | ✅ `text="M3Menu · standard / vibrant / baseline"` | 长菜单滚动条、TAB 焦点环（前批验证） | [MENU_TRIAL.md](<MENU_TRIAL.md>) / `screenshots/menu*.png` |
| Dialog | ✅ `text="M3Dialog · basic / list / scrollable"` | Basic 打开/Cancel 关闭、Scrollable 列表（前批验证） | [DIALOG_TRIAL.md](<DIALOG_TRIAL.md>) / `screenshots/dialog-*.png` |
| Snackbar | ✅ `text="M3Snackbar · auto-dismiss / action / FAB avoidance"` | 4 秒自动消失、有 action 保持、FAB 上方避让（前批验证） | [SNACKBAR_TRIAL.md](<SNACKBAR_TRIAL.md>) / `screenshots/snackbar*.png` |
| Search | ✅ `text="M3Search · bar / docked / full-screen"` | **状态化全屏 + 返回键收起 + 建议回填；旧 query 入口也已迁移并验证全屏（本次重新验证）** | [SEARCH_TRIAL.md](<SEARCH_TRIAL.md>) / `screenshots/search-fullscreen.png` |

## 三、Search 全屏修复回归

- 新入口：`rememberM3SearchBarState()` + 状态化 `M3SearchBar(state, textFieldState)`；展开由 `ExpandedFullScreenSearchBar` 负责。
- 真机验证：点击搜索栏 → 覆盖整个窗口的全屏建议列表；返回键 → 收起并回到页面，App 未退出；点击 Kotlin → 输入回填并收起。
- 旧 `query/onQueryChange` 入口仍保留兼容，但**不要在无界滚动容器中直接展开**（1.5.0-rc01 会复现约束异常）。

## 四、未覆盖 / 风险

1. 未做 pixel diff、RTL、密度检查。
2. 非 Search 组件的“关键交互”沿用前批验证结果；本次代码只改了 Search 相关文件与文案，未触碰这些组件。
3. 六类分区渲染用 uiautomator 文本 presence 验证，不是视觉像素级对比。
4. Search 的 Baseline 变体仍只编译、未真机截图。
5. 旧 SearchBar query 入口已改为委托状态化实现，并在 demo 中验证全屏；IME 打开时第一次返回先关输入法。

## 五、Pixel Diff

已建立 P0 六类基线并跑通 0% 自比较；负向测试（折叠态替换全屏）得到 88.77% FAIL。详见 [PIXEL_DIFF.md](<PIXEL_DIFF.md>)。

## 六、结论

P0 六类在最新构建上均可渲染，单测全绿；Search 真全屏、返回键与建议点击通过。P0 smoke 通过（视觉像素级回归除外）。