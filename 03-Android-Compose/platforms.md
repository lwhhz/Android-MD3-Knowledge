# M3 官方实现平台对照（Jetpack Compose / Android Views / Flutter / Web）

> 事实来源：m3.material.io 官方站点全站文本抓取（抓取时间 2026-11-16）。
> 本文件只陈述官方 dump 中明确出现的内容；由本库推断或补充的内容以 ⚠️ 标注。

## 1. 总览：四大平台的维护状态

| 平台 | 官方实现 | 维护状态 | M3 Expressive | 官方定位 |
|---|---|---|---|---|
| Android – Jetpack Compose | Material Compose（Compose Material 3） | **积极开发，Material 自家团队在内部构建**，第一时间获得最新 Material 更新 | ✅ 可用（2025-05-13 起公告） | Android 上的推荐工具包，Material Android 已「Compose-first」 |
| Android – Views（MDC-Android） | Material Components for Android | **维护模式**：仅接收关键修复，不再有功能更新 | ✅ 可用（2025-05-13 起公告） | 遗留平台，官方建议迁移到 Jetpack Compose |
| Flutter | Flutter 框架内置 Material 组件（由 Flutter 团队维护） | **积极维护**，周期性获得 Material 更新 | ❌ 不可用（官方明确「M3 Expressive is not available on Flutter」） | 跨平台应用开发的最佳 Material 支持 |
| Web | Material Web Components（MWC，Material Web 2.0） | **维护模式**：「No updates are currently being made to this library」 | ❌ 未实现（官方明确「M3 Expressive is not implemented on Web」） | 可在任何 Web 框架中使用；另有 Angular Material 文档 |

关键公告（2026-05-19，develop 页 Announcements）：**Material Android is Compose-first** —— MDC-Android 进入维护模式、只收关键修复；Material Android 的后续投入全部转向 Compose。

## 2. Jetpack Compose（推荐平台）

- 定位：Android 官方现代原生 UI 工具包，构建 Material 3 界面。
- 官方库：Material Compose 「built in-house」（Material 团队内部构建），因此**最早获得 Material 新特性**。
- 公告时间线（develop/android/jetpack-compose 页 Announcements）：
  - 2025-05-13：M3 Expressive 可用于 Jetpack Compose。
  - 2025-05-20：官方博客「Adding Motion Physics with Jetpack Compose」——M3 Expressive 的 motion theming（动效物理）体系。
  - 2026-05-19：Compose-first 公告，并预告即将到来的 Material Compose 能力：
    1. **stable Expressive components**（稳定的 Expressive 组件）；
    2. **Styles API**（更简便的自定义样式 API）；
    3. **Navigation3 集成进 adaptive（自适应）库**。
- 支持的扩展形态：官方列出 **WearOS** 与 **Android XR** 的开发入口（design for watches / designing for XR）。
- 官方资源清单（dump 中列名）：
  - Compose Material 3 组件文档（「all Material 3 Components available for Jetpack Compose」）
  - M2 → M3 迁移指南（Jetpack Compose）
  - Jetpack Compose Tutorial / Pathway、Theming in Compose
  - Material Components and layouts（以 composable 函数提供）
  - **Reply 示例应用**（Best of Material 3 sample）
  - Adaptive apps：nav rail、drawer、tabs 等导航组件在 Compose 中的自适应用法

### 2.1 库坐标

Dump（blog_material-3-compose-stable.txt，即 Compose M3 稳定版发布公告）中出现的 Gradle 坐标：

```
implementation "androidx.compose.material3:material3:$material3_version"
implementation "androidx.compose.material3:material3-window-size-class:$material3_version"
```

来源：<https://m3.material.io/blog/material-3-compose-stable>

## 3. Android Views（MDC-Android）

- 官方库名：Material Components for Android（MDC-Android），GitHub 仓库文档在官方 Resources 中列出。
- **Material Views 1.14 是 Views 库的最后一个稳定版本**（「final stable release」，2026-05-19 公告）。
- 维护模式说明（官方横幅原文）：虽会接收关键修复，但 Material Android 已 Compose-first，请迁移到 Jetpack Compose 以使用最新特性。
- 历史版本公告（仍可作为版本事实引用）：
  - 2025-05-13：M3 Expressive 也可用于 Android Views（MDC-Android）。
  - 2024-05-08：MDC-Android 1.11.0 与 1.12.0 发布——新颜色选项与 Carousel 细化。
- dump 中出现的 MDC 类坐标（com.google.android.material 包，可作为包名事实引用）：
  - `com.google.android.material.carousel.MaskableFrameLayout`
  - `com.google.android.material.imageview.ShapeableImageView`
  - `com.google.android.material.slider.Slider` / `RangeSlider`
  - Material 过渡类基于 AndroidX Transition（`com.google.android.material.transition` 包）与 Framework Transition（`com.google.android.material.transition.platform` 包）
- 官方资源：Material Motion codelab（过渡模式）、动态颜色 codelab、MAD Skills 视频系列。

## 4. Flutter

- 定位：单一代码库构建多平台原生编译应用；**官方称 Flutter 提供跨平台开发的最佳 Material 支持**。
- 维护方：**Flutter 团队**（非 Material 自家团队），因此 Material 更新为「periodic（周期性）」而非最先。
- 组件覆盖：支持**原版 M3 组件**（Material 3 Widget catalog）；Flutter 团队仍在持续做 M3 支持。
- M3 Expressive：**不可用**。
- 开启方式（官方 dump 原文）：设置 `ThemeData.useMaterial3 = true` 以启用更新后的组件并应用新 typography、color、elevation 等。
- 官方资源：MDC-101 codelab（应用 M3）、5 Steps 大屏适配、Basil 材料研究实现（多设备主题技术）、**canonical layouts 用 `adaptive_scaffold` 包实现**、示例应用 **Wonderous**。
- M3 支持的跟踪方式：GitHub umbrella issue（「Material 3 GitHub issue」，汇总所有过去与将来的 M3 工程工作）。

## 5. Web

- 官方库：Material Web Components（Material Web 2.0，「lightweight and accessible」组件，可用于任何 Web 框架）。
- 维护模式原文：「Material Web Components are currently in maintenance mode. No updates are currently being made to this library.」
- M3 Expressive：**未实现**。
- 路线图：官方 Web roadmap 页「lists what used to be planned and all past releases」（只列过去的计划与历史版本）。
- 另有 **Angular Material** 开发文档入口，官方注明其「实现可能与 Material Web Components 及本规范指南不同」。

## 6. 能力差异速查

| 能力 / 特性 | Compose | MDC-Android | Flutter | Web (MWC) |
|---|---|---|---|---|
| M3 基础组件 | ✅ | ✅ | ✅（原版 M3 组件） | ✅（维护中存量） |
| M3 Expressive 组件 | ✅（2025-05 起，且将持续演进） | ✅（2025-05 起，随 1.x 存量，无后续） | ❌ | ❌ |
| M3 Expressive motion theming（动效物理） | ✅（官方博客专门介绍） | （dump 未明确 ⚠️） | ❌ | ❌ |
| 动态颜色（dynamic color） | ✅（官方 codelab/资料多次提及） | ✅（官方 codelab 专门章节） | （dump 未在平台页提及 ⚠️） | （dump 未提及 ⚠️） |
| 自适应布局 / canonical layouts | ✅（adaptive 库 + nav rail/drawer/tabs 指南） | （dump 未专门列出 ⚠️） | ✅（adaptive_scaffold 包 + canonical layouts 指南） | （dump 未提及 ⚠️） |
| WearOS / Android XR 官方入口 | ✅ | ❌（页面未列出） | ❌（页面未列出） | — |
| 获得更新的优先级 | 第 1（in-house） | 维护模式 | 第 2（periodic） | 维护模式 |
| 官方 M2→M3 迁移指南 | ✅ 专门列出 | ✅（migrate 类博客存在） | （useMaterial3 flag 即开关） | — |

## 7. AI 检索要点

1. **回答 Android 实现问题时应默认走 Jetpack Compose**；MDC-Android 仅在用户明确使用 Views/XML 时引用，且要注明其 1.14 为最终版、仅剩关键修复。
2. **Flutter 与 Web 均无 M3 Expressive**：涉及 Expressive（新组件、新动效体系、Styles API 等）的实现问题时，目前唯一官方实现平台是 Android（Compose；MDC-Android 存量可用）。
3. **版本事实锚点**：Compose M3 稳定版发布公告给出坐标 `androidx.compose.material3:material3`；MDC-Android 终版为 1.14.0（⚠️ dump 原文写「Material Views 1.14」，小版本号未给出）。
4. Flutter 的 `useMaterial3` flag 是启用 M3 的官方开关。

## 来源

| 页面 | URL | 核验日期 |
|---|---|---|
| Develop 总览 | <https://m3.material.io/develop> | 2026-11-16 |
| Jetpack Compose | <https://m3.material.io/develop/android/jetpack-compose> | 2026-11-16 |
| Android Views (MDC-Android) | <https://m3.material.io/develop/android/mdc-android> | 2026-11-16 |
| Flutter | <https://m3.material.io/develop/flutter> | 2026-11-16 |
| Web | <https://m3.material.io/develop/web> | 2026-11-16 |
| Compose M3 稳定版公告（坐标出处） | <https://m3.material.io/blog/material-3-compose-stable> | 2026-11-16 |
