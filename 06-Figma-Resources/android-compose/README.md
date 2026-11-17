# Figma → Android Jetpack Compose（Material 3 组件复刻）

本仓库把 Figma 官方的 **Material 3 Design Kit**（Figma Community，发布方 Material Design，CC BY 4.0）转写为 **Jetpack Compose + Material 3** 代码，并补上可构建的 Android 工程、官方数值常量与缺口清单。

> **当前状态（2026-11-17）**
> - **0.1 完成**：Gradle 工程可构建，`:app:assembleDebug` 通过，产出 `app/build/outputs/apk/debug/app-debug.apk`。
> - **0.2 完成**：新增 `ui/M3Metrics.kt`（官方状态层 8/10/10/16/38、按钮五档尺寸）。
> - **0.4 部分完成**：`appbar/` 已迁入 `components/`，App bar / Bottom app bar 接入 demo；修完 44 个 API 漂移错误。剩余“按域拆枚举”留到 **0.4b**。
> - **阶段 1 试点完成（Button 家族）**：Filled / Tonal / Elevated / Outlined / Text 五种样式全部实现，共 35 条规格参数落地（7/7 × 5）；单测 2/2；已在真机安装运行并截图，详见 [BUTTON_TRIAL.md](<BUTTON_TRIAL.md>)。
> - **阶段 1 试点 2（IconButton + FAB）**：8 个 IconButton 变体 + FAB / Extended FAB 已实现并真机截图；公共状态层与颜色表已抽出。详见 [ICONBUTTON_FAB_TRIAL.md](<ICONBUTTON_FAB_TRIAL.md>)。
> - **阶段 1 试点 3（ToggleButton）**：四个样式变体实现并真机截图，9 参数中 8 条落地。详见 [TOGGLE_TRIAL.md](<TOGGLE_TRIAL.md>)。
> - **缺口补齐**：ToggleButton `Icon selected`、IconButton 宽度档（narrow/default/wide）、FAB menu 已完成并截图。详见 [TOGGLE_TRIAL.md](<TOGGLE_TRIAL.md>)、[ICONBUTTON_FAB_TRIAL.md](<ICONBUTTON_FAB_TRIAL.md>)。
> - **阶段 1 试点 4（Text field）**：Filled / Outlined 两变体，10/10 参数落地，含聚焦/错误/禁用真机截图。详见 [TEXTFIELD_TRIAL.md](<TEXTFIELD_TRIAL.md>)。
> - **阶段 1 试点 5（选择控件）**：Checkbox / Radio / Switch 三件套实现，参数 3/3、3/3、4/4；真机截图通过。详见 [SELECTION_TRIAL.md](<SELECTION_TRIAL.md>)。
> - **阶段 1 试点 6（Slider）**：标准 / centered / range 三档自绘实现，可点击/拖动，value indicator 生效；垂直方向未做。详见 [SLIDER_TRIAL.md](<SLIDER_TRIAL.md>)。
> - **阶段 1 试点 7（Chip）**：Assist / Filter / Input / Suggestion / Chip group 五个变体实现，真机截图通过。详见 [CHIP_TRIAL.md](<CHIP_TRIAL.md>)。
> - **P0 试点 1（Tabs）**：Primary / Secondary / 固定 / 可滚动 / 三种布局，参数 4/4；并补 6dp 小圆点 Badge（文字 trailing / 图标右上）。详见 [TABS_TRIAL.md](<TABS_TRIAL.md>)。
> - **P2 补做（Badges）**：独立 Small / Large，支持 count（99+）与 label；参数 2/2。详见 [BADGES_TRIAL.md](<BADGES_TRIAL.md>)。
> - **P0 试点 2（Navigation）**：Bar（竖排/横排）、Rail（TOP/CENTER + header）、Modal Drawer；Bar 1/1+1/1、Rail 4/5，抽屉打开/返回键关闭均已真机验证。详见 [NAVIGATION_TRIAL.md](<NAVIGATION_TRIAL.md>)。
> - **P0 试点 3（Menu）**：Vertical Standard/Vibrant、菜单项、Baseline；Menu 7/7、item 6/7、baseline 2/2；滚动条与焦点环已补。详见 [MENU_TRIAL.md](<MENU_TRIAL.md>)。
> - **P0 试点 4（Dialog）**：Basic / List / Scrollable；Basic 5/5、List 3/4、Scrollable 3/4，真机打开/关闭验证。详见 [DIALOG_TRIAL.md](<DIALOG_TRIAL.md>)。
> - **P0 试点 5（Snackbar）**：单/双行、action、close；4/4；自动消失与 FAB 避让已验证。详见 [SNACKBAR_TRIAL.md](<SNACKBAR_TRIAL.md>)。
> - **P0 试点 6（Search，最后一项）**：SearchBar / Full-screen / Docked / Baseline；5/6、4/5、4/5、3/4、3/4，展开与建议列表验证。详见 [SEARCH_TRIAL.md](<SEARCH_TRIAL.md>)。
> - **P0 回归 + Search 全屏修复**：状态化 API、旧 query 入口迁移、六类 smoke、pixel diff 基线。详见 [P0_REGRESSION.md](<P0_REGRESSION.md>)、[PIXEL_DIFF.md](<PIXEL_DIFF.md>)。
> - **P1 试点 1（Progress / Loading）**：线性/环形 × determinate/indeterminate + Loading；真机截图，参数 3/3、2/3、3/3、3/4、2/2。详见 [PROGRESS_TRIAL.md](<PROGRESS_TRIAL.md>)。
> - **P1 试点 2（Toolbar）**：docked / floating、standard / vibrant、horizontal / vertical，并补 FAB 插槽、overflow 菜单、scrollBehavior 透传；参数 4/5，滚动隐藏未验证。详见 [TOOLBAR_TRIAL.md](<TOOLBAR_TRIAL.md>)。
> - **P1 试点 3（Bottom sheet）**：Modal / Standard，3/3；补 640dp 逻辑、50% 初始高度与 tall sheet 滚动验证。详见 [SHEET_TRIAL.md](<SHEET_TRIAL.md>)。
> - **P1 试点 4（Segmented button）**：2–5 段、单选/多选、Density 4 档；2/2，真机截图。详见 [SEGMENTED_BUTTON_TRIAL.md](<SEGMENTED_BUTTON_TRIAL.md>)。
> - **P1 试点 5（Connected button group）**：round/square、XS–XL、single/multi/required；5/5，真机截图。详见 [CONNECTED_GROUP_TRIAL.md](<CONNECTED_GROUP_TRIAL.md>)。
> - **P1 试点 6（Carousel）**：官方 carousel + 图片 + MULTI_ASPECT（LazyRow 降级）；Carousel 7/9、Full screen 2/2。详见 [CAROUSEL_TRIAL.md](<CAROUSEL_TRIAL.md>)。
> - **P1 试点 7（Date & Time pickers）**：官方 DatePicker/TimePicker/TimeInput；Dial 3/3、Keyboard 2/2，日期变体部分参数。详见 [DATETIME_TRIAL.md](<DATETIME_TRIAL.md>)。
> - **P1 收尾（2026-11-18）**：24 tests 全绿；P0 173/315、P1 147/180、P2 2/43；已知缺口见 [P1_REGRESSION.md](<P1_REGRESSION.md>)。
> - **P2 首批（2026-11-18）**：Plain Tooltip / Generic Avatar / Shape Set；Avatar 2/2、Shape 1/1、Tooltip 2/2，长按弹出已验证。详见 [P2_TRIAL.md](<P2_TRIAL.md>)。
> - **P2 工具组件（2026-11-18）**：Keyboard 2/2、Layout grid 2/2；29 tests。详见 [P2_UTILITIES_TRIAL.md](<P2_UTILITIES_TRIAL.md>)。
> - **P2 旧行核对（2026-11-18）**：Extended FAB 6/6（补 focus/state）、`.Shape` 1/1（9 变体）；30 tests。详见 [P2_RECONCILE.md](<P2_RECONCILE.md>)。
> - **P2 收尾（2026-11-18）**：30 tests 全绿；P2 18/43（XR 25 条未做），合计 338/538。详见 [P2_REGRESSION.md](<P2_REGRESSION.md>)。
> - **工程收尾（2026-11-18）**：P1/P2 pixel 基线自检 0%；RTL smoke 与 320/480dpi 通过；30 tests。详见 [ENGINEERING_WRAP.md](<ENGINEERING_WRAP.md>)。
> - **项目收口（2026-11-18）**：接受人工 Pixel 基线；XR **明确不推进**；最终 30 tests、338/538。总览见 [PROJECT_STATUS.md](<PROJECT_STATUS.md>)。
> - **App bar 迁移（2026-11-18）**：`appbar/` 迁入 `components/`，App bar / Bottom app bar 接入 demo；31 tests。详见 [APPBAR_TRIAL.md](<APPBAR_TRIAL.md>)。
> - **0.5 完成**：`M3SuggestionChipPixel` 状态层改为官方 **Pressed 10% / Dragged 16%**，说明文档同步。
> - 依赖口径 **material3 1.5.0-rc01**（预发布线），视觉口径**现行官方值**，依据见 [KB_COMPONENT_MAP.md](<KB_COMPONENT_MAP.md>)。

---

## 目录结构

```
android-compose/
├── README.md                     ← 本文件
├── LICENSE                       ← Apache-2.0（自有代码与文档）
├── NOTICE                        ← 来源、许可与修改声明（Apache-2.0 + CC BY 4.0）
├── COMPONENTS_SPEC.md            ← 28 页 / 171 个组件集 / 885 个参数的权威规格
├── IMPLEMENTATION_GAPS.md        ← 组件实现缺口清单（97 个正式组件）
├── KB_COMPONENT_MAP.md           ← 依赖/视觉决策、组件→知识库映射、冲突登记
├── SUGGESTION_CHIP_PIXEL.md      ← 单组件像素级还原说明（已按官方值修正）
├── evidence/                     ← 来源证据（Figma 社区页截图）
├── screenshots/                  ← 运行时截图（Button 家族）
├── app/                          ← 可构建的 Android 应用模块（0.1 起）
│   └── src/main/java/com/example/m3appbar/
│       ├── MainActivity.kt       ← 0.1/0.2 的可见验收面
│       └── ui/
│           ├── M3Metrics.kt      ← 官方数值常量（状态层 / 按钮五档）
│           └── theme/            ← Color / Shape / Type / Theme
├── components/                   ← 第三轮：全组件生成结果（已接入编译，0.4 起）
├── examples/                     ← 不参与编译的用法示例（原 appbar/AppBarUsageExample.kt）
├── gradle/                       ← 版本目录与 wrapper
└── 全figma链接.txt                ← Figma 节点深链
```

## 构建与验证

> 0.4 起 app 模块同时编译 `appbar/`、`components/`（见 `app/build.gradle.kts` 的 `sourceSets`）。Search 占位用 `TextField`，原因见下文。

环境要求：JDK 21、Android SDK（platform `android-37.2`、build-tools 37.0.0）、Gradle 9.8.1（wrapper 已生成）。

```bash
./gradlew :app:assembleDebug
# 产物：app/build/outputs/apk/debug/app-debug.apk
```

本次验证环境（2026-11-17）：

- 使用 **Microsoft OpenJDK 21**；Android Studio 自带 JBR 在本机沙箱内写工作区被拒（`canWrite=false`），换 JDK 后通过。普通桌面环境若遇到 `projectDirectory ... not writable`，可优先切换 `JAVA_HOME` 到其它 JDK 21。
- 无外网时用只读依赖缓存离线构建：
  ```bash
  GRADLE_USER_HOME=<工作区内目录> \
  GRADLE_RO_DEP_CACHE=<已有缓存>/caches \
  ./gradlew --offline :app:assembleDebug
  ```
- 调试签名移到工程内 `.debug/debug.keystore`（沙箱不允许写 `~/.android`），已在 `.gitignore` 排除。
- 离线缓存里 `navigationevent` 只有 1.1.2 的 android 变体，`app/build.gradle.kts` 用 `constraints` 上移补丁版本；联网环境可删除该约束。

## 依赖与版本（D1）

| 项 | 取值 | 依据 |
|---|---|---|
| Compose BOM | `androidx.compose:compose-bom-alpha:2026.11.00` | material3 1.5.0-rc01 / adaptive 1.4.0-alpha03 |
| AGP / Kotlin | 9.4.1 / 2.3.21 | 与知识库示例工程一致，且本机缓存可用 |
| compileSdk | 37 + minor 2 | Compose 1.13.0-rc01 要求 ≥ 37.1 |
| minSdk / targetSdk | 26 / 36 | 与示例工程一致 |
| 图标 | 显式 `material-icons-core`（扩展集才用 `-extended`） | KB 规则 V3 |

> ⚠️ 1.5.0 尚未 GA，属预发布线：锁死版本，BOM 升级单独提交（KB 规则 V4）。

## 数据来源与生成历史

1. **第一轮**：App bars 页精细手写（`appbar/`，含真实色值/尺寸/排版）。
2. **第二轮**：figmingo-mcp 读取全页生成骨架。
3. **第三轮**：解析 MCP 磁盘缓存中的完整 `/v1/files` 文档，从 `componentPropertyDefinitions` 提取 **171 个组件集 / 885 个参数**（含类型、默认值、全部选项），重新生成 `M3Enums.kt`（53 枚举）与每页 Composable。

> 参数类型分布：VARIANT 374 · BOOLEAN 236 · TEXT 126 · INSTANCE_SWAP 117 · SLOT 32。
>
> 2026-11-17 复核修正：实际为 29 个组件文件、约 135 处 `@Composable`；当时源码中 `TODO` 为 0（0.4 新增 1 处 Search 占位 TODO），占位骨架靠 `// 布局骨架：完整规格见 COMPONENTS_SPEC.md` 注释（31 处）标识；**从未真实编译过**（本轮已建立工程并编译，见上）。

## 生成代码的映射规则

| Figma 概念 | Compose 对应 |
|---|---|
| VARIANT 属性（如 `Type=Square, Size=Medium`） | `enum class`（`M3Enums.kt`）+ Composable 的 `vXxx` 枚举参数 |
| BOOLEAN 参数 | `checked: Boolean` / `enabled: Boolean` 等 |
| TEXT 参数 | `label: String` |
| INSTANCE_SWAP / icon slot | `Painter?` 或 slot lambda |
| CANVAS 页面 | 一个 `M3<Page>.kt` 文件 |
| COMPONENT_SET | 一个 `@Composable fun M3<Name>(...)` |

历史生成代码的函数体多数只委托到 M3 原生控件或直接返回占位；参数落地率见 [IMPLEMENTATION_GAPS.md](<IMPLEMENTATION_GAPS.md>) 的静态分析。

## 历史转写代码的现状与接入计划

- 97 个正式组件中，96 个有参数齐全的函数签名，但 **538 条规格参数只有 12 条（2.2%）真正影响渲染**；23 个完全空壳。
- 0.4 已完成：接入 `sourceSets` 编译、修完 API 漂移错误、删除 `components/M3Appbars.kt` 与 `appbar/` 重复的 `M3AppBar` / `M3BottomAppBar` 空壳。
- 0.4 剩余（0.4b）：按组件域拆分 `M3Enums.kt` 的 53 个混装枚举（需要按 `COMPONENTS_SPEC.md` 逐组件的选项集重新生成，属机械但量大的改动）。
- Search 系列暂时用 `TextField` 占位：1.5.0-rc01 里 `SearchBarState` 的公开构造不可用（internal），需先确认官方推荐入口，留到阶段 3。
- 逐组件补齐可参考 [KB_COMPONENT_MAP.md](<KB_COMPONENT_MAP.md>) 的映射表（88 个组件有知识库专篇）。

## 来源与许可

- 设计来源：**Material 3 Design Kit**，发布方 Material Design（Figma Partner），通过 Figma Community 分发，**CC BY 4.0**。
- 官方页面：[Figma Community](https://www.figma.com/community/file/1035203688168086460/material-3-design-kit) ｜ [Introducing the M3 design kit for Figma](https://m3.material.io/blog/material-3-figma-design-kit)
- 本地提取源：Figma 文件 key `42Mc2SlwS6zU3VXsHUSaYl`（社区 Kit 的同源副本，未编辑）。
- 本仓库是**非官方**的 Compose 转写：按现行官方规范修正了状态层等数值，修改说明见 [NOTICE](<NOTICE>)。不暗示 Google 背书。
- **自有代码与文档**：Apache-2.0，版权人 android-compose contributors；全文见 [LICENSE](<LICENSE>)。
- **Figma 设计素材例外**：Material 3 Design Kit 及其截图仍按 CC BY 4.0，署名与修改声明见 [NOTICE](<NOTICE>)。

## 文档索引

| 文件 | 用途 |
|---|---|
| [COMPONENTS_SPEC.md](<COMPONENTS_SPEC.md>) | Figma 参数权威规格（171 集 / 885 参数） |
| [IMPLEMENTATION_GAPS.md](<IMPLEMENTATION_GAPS.md>) | 97 个组件的实现缺口与补齐顺序 |
| [KB_COMPONENT_MAP.md](<KB_COMPONENT_MAP.md>) | 决策 D1–D4、依赖口径、组件→KB 映射、冲突 C1–C6 |
| [SUGGESTION_CHIP_PIXEL.md](<SUGGESTION_CHIP_PIXEL.md>) | 单组件像素级还原（状态层已按官方 10%/16% 修正） |
| [BUTTON_TRIAL.md](<BUTTON_TRIAL.md>) | Button 家族试点：5 样式 × 7/7、真机截图 |
| [ICONBUTTON_FAB_TRIAL.md](<ICONBUTTON_FAB_TRIAL.md>) | IconButton 八变体 + FAB 记录、公共状态层、限制 |
| [TOGGLE_TRIAL.md](<TOGGLE_TRIAL.md>) | ToggleButton 四变体记录与限制 |
| [TEXTFIELD_TRIAL.md](<TEXTFIELD_TRIAL.md>) | Text field 记录：10/10、错误/聚焦行为、限制 |
| [SELECTION_TRIAL.md](<SELECTION_TRIAL.md>) | Checkbox / Radio / Switch 记录与限制 |
| [SLIDER_TRIAL.md](<SLIDER_TRIAL.md>) | Slider 三档记录、自绘手势与限制 |
| [CHIP_TRIAL.md](<CHIP_TRIAL.md>) | Chip 五变体记录、尺寸与限制 |
| [P0_OSS_RESEARCH.md](<P0_OSS_RESEARCH.md>) | P0 六类组件的开源同类方案调研（仅报告，未改代码） |
| [TABS_TRIAL.md](<TABS_TRIAL.md>) | Tabs 试点：4/4、Primary/Secondary/滚动与限制 |
| [BADGES_TRIAL.md](<BADGES_TRIAL.md>) | Badges：Small / Large / count / label 与限制 |
| [NAVIGATION_TRIAL.md](<NAVIGATION_TRIAL.md>) | Navigation Bar / Rail / Drawer 记录与限制 |
| [MENU_TRIAL.md](<MENU_TRIAL.md>) | Menu / 菜单项 / Baseline 记录与限制 |
| [DIALOG_TRIAL.md](<DIALOG_TRIAL.md>) | Basic / List / Scrollable dialog 记录与限制 |
| [SNACKBAR_TRIAL.md](<SNACKBAR_TRIAL.md>) | Snackbar 记录与限制 |
| [SEARCH_TRIAL.md](<SEARCH_TRIAL.md>) | Search / 全屏 / Docked / Baseline 记录与限制 |
| [SEARCH_FULLSCREEN_RESEARCH.md](<SEARCH_FULLSCREEN_RESEARCH.md>) | Search 全屏修复方案调研（仅报告） |
| [P0_REGRESSION.md](<P0_REGRESSION.md>) | P0 六类回归 smoke 记录（2026-11-18） |
| [PIXEL_DIFF.md](<PIXEL_DIFF.md>) | P0 六类 pixel diff 基线、脚本与结果 |
| [PROGRESS_TRIAL.md](<PROGRESS_TRIAL.md>) | Progress / Loading 试点记录 |
| [TOOLBAR_TRIAL.md](<TOOLBAR_TRIAL.md>) | Toolbar docked / floating 记录 |
| [SHEET_TRIAL.md](<SHEET_TRIAL.md>) | Bottom sheet modal / standard 记录 |
| [SEGMENTED_BUTTON_TRIAL.md](<SEGMENTED_BUTTON_TRIAL.md>) | Segmented button 记录 |
| [CONNECTED_BUTTON_GROUP_EVAL.md](<CONNECTED_BUTTON_GROUP_EVAL.md>) | Connected button group 替代评估（仅报告） |
| [MATERIAL3_UPGRADE_EVAL.md](<MATERIAL3_UPGRADE_EVAL.md>) | material3 升级换取 keyline 的评估（仅报告） |
| [CONNECTED_GROUP_TRIAL.md](<CONNECTED_GROUP_TRIAL.md>) | Connected button group 实现记录 |
| [CAROUSEL_TRIAL.md](<CAROUSEL_TRIAL.md>) | Carousel 多布局 / full-screen 记录 |
| [DATETIME_TRIAL.md](<DATETIME_TRIAL.md>) | Date & time pickers 记录 |
| [P1_REGRESSION.md](<P1_REGRESSION.md>) | P1 回归记录与已知缺口 |
| [P2_TRIAL.md](<P2_TRIAL.md>) | P2 首批 Tooltip / Avatar / Shape |
| [P2_UTILITIES_TRIAL.md](<P2_UTILITIES_TRIAL.md>) | P2 Keyboard / Layout grid |
| [P2_RECONCILE.md](<P2_RECONCILE.md>) | P2 旧行核对：Extended FAB / .Shape |
| [P2_REGRESSION.md](<P2_REGRESSION.md>) | P2 回归记录（XR 除外） |
| [ENGINEERING_WRAP.md](<ENGINEERING_WRAP.md>) | Pixel 基线 / RTL / 密度收尾 |
| [PROJECT_STATUS.md](<PROJECT_STATUS.md>) | 项目最终状态与不做项 |
| [APPBAR_TRIAL.md](<APPBAR_TRIAL.md>) | App bar / Bottom app bar 迁移记录 |

## 关于 Token / MCP 安全

- Figma Token 曾以明文出现在对话与 `~/.workbuddy/mcp.json` 中，建议用完后在 Figma → Settings → Security 撤销并重新生成，再更新配置。
- MCP 服务以本地 HTTP 模式运行时监听 `127.0.0.1:3845`，仅供本机使用。
