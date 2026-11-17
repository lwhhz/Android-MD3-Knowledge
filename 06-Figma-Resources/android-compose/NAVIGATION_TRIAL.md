# Navigation 试点记录（阶段 2 · P0 第二批）

> 日期：2026-11-17　范围：Navigation Bar（竖排/横排）、Navigation Rail（TOP/CENTER）、Modal Navigation Drawer　结论：**Bar 1/1 + 1/1、Rail 4/5 落地；Drawer 额外实现**，真机切换与抽屉打开已验证。

## 一、实现

| 组件 | 入口 | 说明 |
|---|---|---|
| Navigation Bar（竖排 item） | M3NavigationBar(layout = VERTICAL) | 图标在上、文字在下，高 80dp；支持 badge |
| Navigation Bar（横排 item） | M3NavigationBar(layout = HORIZONTAL) | 图标+文字同一行（近似 Kit 的 Horizontal items） |
| Navigation Rail | M3NavigationRail | 宽 80dp，alignment TOP/CENTER，header 槽可放菜单/FAB |
| Modal Navigation Drawer | M3NavigationDrawer | 360dp sheet、item 56dp、指示器 336×56dp / 28dp 圆角；状态由调用方持有 |

统一 M3NavItem(label, icon, selectedIcon, enabled, badge)，badge 用 Material Badge 显示。

## 二、官方尺寸（已锁进单测）

| 项 | 值 |
|---|---|
| Bar 高 | 80dp |
| Bar 指示器 | 64×32dp |
| Bar 图标 | 24dp |
| Bar 上下内边距 | 12 / 16dp |
| Rail 宽 | 80dp |
| Rail 指示器 | 56×32dp |
| Rail 指示器左右边距 | 12dp |
| Rail 项目标高 | 56dp |
| Drawer 宽 | 360dp |
| Drawer 内容侧圆角 | 16dp |
| Drawer item 高 | 56dp |
| Drawer 指示器 | 336×56dp，28dp 圆角 |

常量在 [M3NavigationTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3NavigationTokens.kt>)，单测 [M3NavigationMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3NavigationMetricsTest.kt>)。

## 三、真机验证

![Navigation：竖排 bar / 横排 bar / rail + drawer 打开按钮](<screenshots/navigation.png>)

![Modal Navigation Drawer 打开态](<screenshots/navigation-drawer.png>)

核对结果：

| 项 | 结果 |
|---|---|
| 竖排 bar：Home/Search/Settings/Saved + badge | ✅ |
| 点击 Settings 后指示器与选中态切换 | ✅（截图 nav6 验证） |
| 横排 bar：图标+文字一行 | ✅（近似实现） |
| Rail：80dp 宽、TOP header、CENTER 对齐、badge | ✅ |
| Drawer：点击按钮打开、scrim、Home 选中、Search 3 / Saved 99+ | ✅ |
| 指示器 / 56dp item / 360dp 宽 | ✅ |

## 四、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **11 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 五、参数落地

| Kit 组件 | 参数 | 落地 | 说明 |
|---|---|---|---|
| Navigation Bar: Vertical items | Nav items(3) | **1/1** | 竖排布局 + 3/4 项均可 |
| Navigation Bar: Horizontal items | Nav items(4) | **1/1** | 横排近似，指示器会包住整行 |
| Navigation Rail | Alignment(2) / Nav items(4) / 菜单 / FAB | **4/5** | FAB/menu 由 header 槽覆盖，无独立显隐参数 |
| Navigation Rail: Expanded | 6 个 | 0/6 | P1，本批未做 |

## 六、已知限制

1. **横排 bar 为近似实现**：官方横排 item 的固定宽度与两端留白未逐项对齐。
2. **Rail 的 FAB/menu 只是 header 槽**，没有“Show menu / Show FAB”独立开关；Expanded 变体未做。
3. **Drawer 不在 Figma 97 组件清单内**，是按官方文档额外实现的；Permanent / Dismissible 形态未做。
4. Drawer 返回键已拦截（2026-11-17 修复，见第七节）。
5. 未做 adaptive NavigationSuiteScaffold（方案 A 不引入依赖）。
6. 未做 RTL、密度、pixel diff；badge 与图标偏移依赖 Compose 默认。


## 七、Drawer 返回键修复（2026-11-17）

- 在 M3NavigationDrawer 内加 BackHandler(enabled = drawerState.isOpen)，触发 drawerState.close()。
- 验证：打开 Drawer 后 uiautomator 出现两个 Saved 节点（rail + drawer）；按返回键后 Drawer 关闭，应用仍停留在当前页，Open drawer 按钮可见，未退出 App。

![返回键关闭 Drawer 后：应用仍在当前页](<screenshots/navigation-drawer-after-back.png>)

## 八、下一步

按 P0 顺序：**Menu → Dialog → Snackbar → Search**。