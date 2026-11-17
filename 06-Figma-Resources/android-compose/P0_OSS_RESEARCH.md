# P0 剩余组件 · 开源同类方案调研报告

> 日期：2026-11-17　范围：Tabs / Navigation / Menu / Dialog / Snackbar / Search（P0）　状态：**仅调研，未改任何代码**
>
> 方法：GitHub REST API 读取 license / archived / pushed_at / stars；读取各仓库最新 Release；官方文档与组件目录核对。
> 限制：GitHub 代码搜索在调研末尾返回 403（限流），第三方库检索可能不完整；所有“维护活跃度”以 2026-11-17 的 API 数据为准；未做性能/包体实测。

## 一、结论先行

1. **P0 六类组件在官方 Compose Material3 中全部存在**，且项目已经依赖 `material3 1.5.0-rc01`，**无需引入任何第三方组件库**。
2. **Flutter 的 Material 实现覆盖全部六类**（BSD-3-Clause，仓库活跃），是行为/交互最好的参考实现。
3. **Material Web 只覆盖 Tabs / Menu / Dialog**，没有 Navigation / Snackbar / Search 组件，只能做局部参考。
4. **MDC-Android 覆盖全部六类，但官方标注 [MAINTENANCE MODE]**（最后 Release 1.14.0 / 2026-05-13），适合查行为，不适合作为新依赖。
5. **MDC-Web 已归档**（最后 Release v14.0.0 / 2022-04-28，archived=true），直接排除。
6. 第三方 Compose 库中没有发现“生产级、许可证清晰、覆盖 P0”的替代品：命中的多为样式化底栏、示例工程或缺少许可证。

## 二、候选项目总表

| # | 项目 | 链接 | 许可证 | 最近活动 | 类型 | P0 匹配度 | 适配成本 | 风险 |
|---|---|---|---|---|---|---|---|---|
| 1 | AndroidX Compose Material3 | https://github.com/androidx/androidx | Apache-2.0 | 2026-11-17 有提交；稳定 1.4.0 / RC 1.5.0-rc01（2026-11-04） | 官方 Compose 组件库 | **6/6** | 低（已依赖） | RC 版本 API 漂移；SearchBar 实验性 |
| 2 | material3-adaptive-navigation-suite | https://mvnrepository.com/artifact/androidx.compose.material3/material3-adaptive-navigation-suite/1.3.0 | Apache-2.0 | 1.3.0（2024-09） | 官方自适应导航套件 | Navigation 1/1 | 低（加一个依赖） | 版本需与 material3 对齐 |
| 3 | Flutter Material | https://github.com/flutter/flutter | BSD-3-Clause | 2026-11-17 有提交；181k stars | 跨平台框架内置组件 | **6/6** | 仅参考（不可直接用于 Compose） | 行为参考≠像素一致 |
| 4 | Material Web | https://github.com/material-components/material-web | Apache-2.0 | v2.5.0（2026-07-15）；2026-11-17 有提交 | Web Components | **3/6**（Tabs/Menu/Dialog） | 仅参考 | 缺 Navigation/Snackbar/Search |
| 5 | MDC-Android | https://github.com/material-components/material-components-android | Apache-2.0 | 1.14.0（2026-05-13）；[MAINTENANCE MODE] | Android Views | **6/6** | 高（Views 互操作） | 维护模式；与 Compose 双栈成本 |
| 6 | MDC-Web | https://github.com/material-components/material-components-web | MIT | v14.0.0（2022-04-28）；**archived** | Web（旧） | 3/6 左右 | 不建议 | 已归档，明确淘汰 |
| 7 | Now in Android | https://github.com/android/nowinandroid | Apache-2.0 | 2026-11-17 有提交；22k stars | 官方示例 App | 架构参考 | 仅参考 | 非组件库 |
| 8 | Compose Samples | https://github.com/android/compose-samples | Apache-2.0 | 2026-11-16 有提交；23.5k stars | 官方示例 | 用法参考 | 仅参考 | 非组件库 |
| 9 | AnimatedBottomBar | https://github.com/MehdiSekoba/AnimatedBottomBar | Apache-2.0 | 2026-11-10；18 stars | 第三方 Compose 底栏 | 仅 Navigation 外观 | 中 | 非 M3 规范，动画定制为主 |
| 10 | Composive | https://github.com/Gursimarsingh12/Composive | **未检测到许可证** | 2025-07-01；50 stars | KMP 响应式布局库 | 低 | — | 无许可证，默认不可用 |
| 11 | adaptive-navigation-bar | https://github.com/narendraanjana09/adaptive-navigation-bar | **未检测到许可证** | 2026-09-14；46 stars | 第三方自适应底栏 | 仅 Navigation 外观 | — | 无许可证；视觉定制 |
| 12 | SearchBarJetpackCompose | https://github.com/netolobo/SearchBarJetpackCompose | **未检测到许可证** | 2023-09-24；4 stars | 示例工程 | 仅 Search 示例 | — | 无许可证；两年未更新 |

说明：许可证以 GitHub API 的 license 字段为准；“未检测到许可证”不等于公有领域，按默认版权保留处理，**不得复制/引入**。

## 三、分组件对照

### 1. Tabs

| 实现 | 组件 | 结论 |
|---|---|---|
| Compose 官方 | `TabRow` / `PrimaryTabRow` / `SecondaryTabRow` / `ScrollableTabRow` | ✅ 直接使用 |
| Flutter | `TabBar` + `TabBarView` | 行为参考（指示器、滚动、键盘） |
| Material Web | `md-tabs`（docs/components/tabs.md） | 可参考 Web 交互 |
| MDC-Android | `TabLayout` | 维护模式，仅参考 |

**建议**：A（官方直接依赖），不需要第三方。

### 2. Navigation（bar / rail / drawer）

| 实现 | 组件 | 结论 |
|---|---|---|
| Compose 官方 | `NavigationBar`, `NavigationRail`, `ModalNavigationDrawer`, `PermanentNavigationDrawer`, `ModalDrawerSheet` | ✅ 直接使用 |
| 官方 adaptive | `NavigationSuiteScaffold`（material3-adaptive-navigation-suite） | 可选：按窗口宽度自动切换 bar/rail |
| Flutter | `NavigationBar`, `NavigationRail`, `NavigationDrawer` | 行为参考 |
| Material Web | **无** | 不可参考 |
| MDC-Android | `NavigationBarView`, `NavigationRailView`, `NavigationView` | 维护模式，仅参考 |

**建议**：A + 可选 B；若需要“手机 bar / 平板 rail”自动切换，再加 adaptive 依赖（Apache-2.0，成本低）。
**风险**：adaptive 1.3.0 与 material3 1.5.0-rc01 的二进制兼容需实测；不引入也能用官方三个组件手写断点。

### 3. Menu

| 实现 | 组件 | 结论 |
|---|---|---|
| Compose 官方 | `DropdownMenu`, `DropdownMenuItem`, `ExposedDropdownMenuBox` | ✅ 直接使用 |
| Flutter | `MenuAnchor`, `DropdownMenu` | 行为参考（锚点、键盘） |
| Material Web | `md-menu` | 可参考 |
| MDC-Android | `menu` 包 | 仅参考 |

**建议**：A。M3 的 menu 视觉在 Compose 里通常需要少量 color/shape 覆盖，不需要第三方。

### 4. Dialog

| 实现 | 组件 | 结论 |
|---|---|---|
| Compose 官方 | `AlertDialog`, `BasicAlertDialog` | ✅ 直接使用 |
| Flutter | `AlertDialog`, `Dialog`, `showDialog` | 行为参考（barrier、返回键） |
| Material Web | `md-dialog` | 可参考 |
| MDC-Android | `dialog` 包 | 仅参考 |

**建议**：A。

### 5. Snackbar

| 实现 | 组件 | 结论 |
|---|---|---|
| Compose 官方 | `SnackbarHost`, `SnackbarHostState`, `Snackbar` | ✅ 直接使用 |
| Flutter | `SnackBar`, `ScaffoldMessenger` | 行为参考（队列、action） |
| Material Web | **无** | 不可参考 |
| MDC-Android | `snackbar` 包 | 仅参考 |

**建议**：A。

### 6. Search

| 实现 | 组件 | 结论 |
|---|---|---|
| Compose 官方 | `SearchBar`, `DockedSearchBar`, `SearchBarDefaults`（**ExperimentalMaterial3Api**） | ⚠️ 可用但实验性 |
| Flutter | `SearchAnchor`, `SearchBar` | 行为参考（展开/建议列表） |
| Material Web | **无** | 不可参考 |
| MDC-Android | `search` 包（SearchView） | 仅参考 |

**建议**：A（先用官方实验 API），加项目内封装隔离；若后续 API 变动频繁，再评估自研 SearchBar（成本中）。
**风险**：实验 API 可能在 1.6 变更；需要 `@OptIn` 和回归截图。

## 四、方案选项与影响

| 方案 | 内容 | 成本 | 收益 | 风险 | 建议 |
|---|---|---|---|---|---|
| **A** | 直接使用官方 Compose Material3；Flutter / Web 只做行为参考 | 低 | 6/6 覆盖、无新依赖、许可证干净 | RC/实验 API 漂移 | **推荐** |
| **B** | A + material3-adaptive-navigation-suite | 低+ | Navigation 自动适配 bar/rail | 多一个依赖、版本对齐 | 可选，按是否要自适应 |
| **C** | MDC-Android Views 互操作 | 高 | 官方 Views 行为成熟 | 维护模式、双栈、主题桥接 | 不推荐 |
| **D** | 第三方 Compose 库 | 中 | 样式/动画现成 | 无生产级；许可证缺失；非 M3 规范 | 不推荐 |
| **E** | 全部自研 | 很高 | 完全可控 | 重复造轮子、无障碍/交互成本 | 不推荐（Search 封装除外） |

## 五、许可证与合规

- Apache-2.0（AndroidX、Material Web、MDC-Android、NIA、Compose Samples）：允许商用/修改/再分发，需保留版权与 NOTICE（如分发二进制/源码）。
- BSD-3-Clause（Flutter）：允许商用/修改，需保留版权声明与免责声明，不得用作者名义背书。
- MIT（MDC-Web）：宽松，但项目已归档，不采用。
- **许可证缺失的第三方库（Composive、adaptive-navigation-bar、SearchBarJetpackCompose）默认不可复制/引入**。
- 本报告只做行为参考，**未复制任何第三方代码**；后续若复制示例代码，需要单独记录来源与许可证。
- **项目自身已补 LICENSE（2026-11-17，Apache-2.0）**：本报告写作时尚未声明，现已在 LICENSE / NOTICE 中补齐。

## 六、风险清单

1. material3 1.5.0-rc01 是 RC；SearchBar 等实验 API 存在漂移风险。
2. MDC-Android 处于维护模式，不能作为长期“参考实现”的唯一来源。
3. Material Web 缺 Navigation / Snackbar / Search，不能作为全量参考。
4. 第三方 Compose 组件生态里，P0 级别没有“许可证清晰 + 生产级 + 全覆盖”的库；不要为了省事引入。
5. GitHub 代码搜索在本次调研末尾被限流，可能漏掉小众库；结论基于已核实项目。
6. adaptive-navigation-suite 1.3.0 较旧，需实测与 material3 1.5.0-rc01 的兼容性。

## 七、建议的下一步决策

请选择：

1. **方案 A**：六类全部用官方 Compose，Flutter/Web 只做参考（推荐）；
2. **方案 A + B**：Navigation 额外引入 adaptive-navigation-suite；
3. 其他组合（请说明哪几类需要特殊处理）。

另需确认：

- Search 是否接受 **ExperimentalMaterial3Api**，还是先做项目内封装/自研？
- 是否要先补项目 LICENSE，再开始下一批？

## 八、来源

- AndroidX Compose Material3：https://github.com/androidx/androidx ｜ https://developer.android.com/jetpack/androidx/releases/compose-material3
- Compose Material3 API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- material3-adaptive-navigation-suite：https://mvnrepository.com/artifact/androidx.compose.material3/material3-adaptive-navigation-suite/1.3.0
- Flutter：https://github.com/flutter/flutter ｜ https://api.flutter.dev/flutter/material/NavigationBar-class.html ｜ https://api.flutter.dev/flutter/material/SearchAnchor-class.html ｜ https://api.flutter.dev/flutter/material/MenuAnchor-class.html ｜ https://api.flutter.dev/flutter/material/SnackBar-class.html
- Material Web：https://github.com/material-components/material-web ｜ https://github.com/material-components/material-web/tree/main/docs/components
- MDC-Android：https://github.com/material-components/material-components-android
- MDC-Web：https://github.com/material-components/material-components-web
- Now in Android：https://github.com/android/nowinandroid
- Compose Samples：https://github.com/android/compose-samples
- 第三方样本：https://github.com/MehdiSekoba/AnimatedBottomBar ｜ https://github.com/Gursimarsingh12/Composive ｜ https://github.com/narendraanjana09/adaptive-navigation-bar ｜ https://github.com/netolobo/SearchBarJetpackCompose
