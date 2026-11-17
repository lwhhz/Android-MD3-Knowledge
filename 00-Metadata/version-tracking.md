# 版本追踪

每次核验记录：

| 检查日期 | 官方来源 | 组件/API | 版本 | 状态 | 变化 | 处理 |
|---|---|---|---|---|---|---|
| YYYY-MM-DD | URL | 名称 | 官方明确版本 | Stable/Beta/Alpha/Experimental/Deprecated | 摘要 | 已更新/待确认 |
| 2026-11-16 | `https://dl.google.com/dl/android/maven2/androidx/compose/compose-bom-alpha/2026.11.00/compose-bom-alpha-2026.11.00.pom`（下载 POM 读取，一手来源） | compose-bom-alpha → material3 / adaptive | 2026.11.00 → 1.5.0-rc01 / 1.4.0-alpha03 | RC / Alpha（artifact 存在，**非 GA**） | POM 明确列出两个依赖版本 | 已更新（见下方「2026-11-16 补充」） |
| 2026-11-16 | `https://dl.google.com/dl/android/maven2/androidx/compose/material3/material3/1.5.0-rc01/material3-1.5.0-rc01-sources.jar`（下载源码读取注解，一手来源） | Expressive 组件注解状态 | 1.5.0-rc01 | 见下方逐项表 | Wavy / FloatingToolbar / ToggleButton / ButtonGroup 无实验注解；LoadingIndicator / ContainedLoadingIndicator / MaterialShapes 仍标注 | 已更新 expressive-components.md |
| 2026-11-16 | Google Maven sources jar：navigation-common 2.10.2 / lifecycle-viewmodel-savedstate 2.11.0 / adaptive 1.4.0-alpha03（一手来源） | hasRoute / createSavedStateHandle / WindowWidthSizeClass | 对应版本 | 稳定/实验混合 | 位置与命名纠错（见 CHANGELOG v2.1） | 已更新对应页面 |

## 当前快照（2026-11-14）

**已在官方 release notes 页面渲染确认**（用 headless 浏览器打开 `https://developer.android.com/jetpack/androidx/releases/compose-material3`，读页面上的版本表）：

| 来源 | 对象 | 版本 | 状态 |
|---|---|---|---|
| Compose Material3 release notes 版本表（表内日期 2026-09-23） | `androidx.compose.material3` **稳定版** | **1.4.0** | Stable |
| 同上 | 该表「Alpha 版」列 | **1.5.0-alpha29** | Alpha |
| 同上 | 该表「RC 版」「Beta 版」列 | — | 空 |

**来自次级来源、尚未在官方页面确认**：

| 来源 | 对象 | 版本 | 说明 |
|---|---|---|---|
| jetc.dev 周报（2026-11-10） | Compose 主构件 | 1.13.0-rc01 | 未在 release notes 版本表确认 |
| jetc.dev 周报（2026-11-10） | `androidx.compose.material3` | 1.5.0-rc01 | **与版本表冲突**：版本表 RC 列为空、最新为 alpha29。`1.5.0-rc01` 仅出现在文档代码示例中（如 `material3-adaptive-navigation-suite:1.5.0-rc01`），未见到独立发布条目 |
| jetc.dev 周报（2026-08） | Compose BOM | 2026.08.00（stable）/ 2026.11.00（alpha、beta） | 稳定线指向 Compose 1.12.0 |
| Material Design Blog | Material Views（MDC-Android） | 1.14.0 | 最后一个 stable，进入维护模式；官方已转 Compose-first |
| Android Developers Blog | Compose 1.12 构建要求 | compileSdk 37 / AGP 9.2.0+ | 升级前置条件 |

> ⚠️ **关于 1.5.0-rc01 的冲突**：社区周报说已发 rc，但官方版本表仍是「1.4.0 stable + 1.5.0-alpha29」，RC 列为空。两种可能：版本表滞后，或 rc 仅用于部分 artifact。**以版本表为准写成 1.4.0 stable 更安全**，用之前请目视确认一次。

### 2026-11-16 补充（一手来源实证，冲突处理）

**新增一手证据**：从 Google 官方 Maven 下载 `compose-bom-alpha:2026.11.00` 的 POM（非博客、非周报），其中明确列出：
- `androidx.compose.material3:material3 → 1.5.0-rc01`
- `androidx.compose.material3.adaptive:adaptive → 1.4.0-alpha03`

同日下载 `material3-1.5.0-rc01-sources.jar` 读取源码注解，确认 Expressive 组件逐项状态（见 `02-Components/expressive-components.md`）。两轮真机（手机 Redmi 23013RK75C / 平板 OPPO OPD2409）编译并运行通过。

**冲突如何处理（遵循结论写法约定）**：
| 事实 | 来源层级 | 可写成的结论 |
|---|---|---|
| 官方 release notes 版本表仍是 1.4.0 stable / 1.5.0-alpha29，RC 列空（快照 2026-11-14） | 官方页面（一手） | 「版本表显示…」 |
| 官方 Maven 上存在 1.5.0-rc01 artifact，且 alpha BOM 映射指向它（2026-11-16） | 官方 Maven POM（一手） | 「官方 Maven 上存在 rc01 artifact，alpha BOM 映射到它」 |
| 1.5.0 是否 GA | 无官方 GA 公告 | ❌ **不写**「已 GA / 稳定」；仍列为待人工确认 |

即：**artifact 存在 ≠ GA**。生产项目是否采用预发布线属工程决策，本库不作「稳定」背书。

**本机编译环境实证**（非官方规范，注明仅作参考）：AGP 9.4.1（内建 Kotlin，不再应用 `org.jetbrains.kotlin.android`）、Gradle 9.8.1、Kotlin 2.3.21、compileSdk 37 + minor 2（Compose 1.13.0-rc01 要求 ≥37.1）、JDK 21、AGP 9 需显式声明 JUnit 4.13.2。

## 核验方法（实测结果，2026-11-16 更新）

| 方式 | m3.material.io | developer.android.com | 结论 |
|---|---|---|---|
| WebFetch 工具 | ❌ 只返回标题 | ❌ fetch failed | 不可用 |
| `curl` 取 HTML | ❌ 只有「This website requires JavaScript」 | — | 不可用 |
| **headless Chrome + CDP 批量抓取** | ✅ sitemap 375 页全部取到，219 万字符，0 失败 | ✅ | **当前首选**，见下方「全站抓取」 |
| headless Edge `--dump-dom` | ✅ 但重型页会 ERR_TIMED_OUT | ✅ 885KB，版本表可读 | 备用 |
| **Chrome CDP（web-access skill）** | ✅ innerText 更干净，超时页也能拿到 | ✅ | **可用，且能处理 Edge 超时的页** |
| **CDP + `textContent`** | ✅ 能拿到折叠面板里的隐藏值 | ✅ | **关键技巧**：innerText 只给可见文本，token 表要点开才渲染 |
| **CDP 视觉读取测量图** | ✅ specs 测量图（googleusercontent CDN）以 `=s0` 取原图 → 视觉读数 | — | **2026-11-16 新增**：shell `curl` 直连与代理都被墙/502，但 headless Chrome 能加载；用 `fetch(location.href)→blob→FileReader→dataURL` 取回字节（`Network.getResponseBody` 对 CDN 请求不稳定）。PNG 存档见 `06-Figma-Resources/assets/specs-redlines/` |
| **GitHub Contents API** | — | `androidx/androidx` 源码可读 | ✅ `api.github.com` + `Accept: application/vnd.github.raw` |
| `raw.githubusercontent.com` | — | — | ❌ SSL 连接失败（exit 35） |
| `android.googlesource.com` | — | — | ❌ 连接超时 |
| `dl.google.com` Maven 索引 | — | — | ❌ fetch failed |
| jetc.dev 周报 | ✅ | ✅ | 仅用于定位，不作结论 |

headless 命令模板（本机 Chrome 路径，2026-11-16 实测）：

```bash
"/c/Program Files/Google/Chrome/Application/chrome.exe" \
  --headless=new --disable-gpu --no-first-run \
  --remote-debugging-port=9222 \
  --user-data-dir="C:/Users/Administrator/AppData/Local/Temp/m3prof"
```

### 全站抓取流程（2026-11-16 实测，可复用）

1. 取站点地图：`curl https://m3.material.io/sitemap.xml` → 抽出 375 个 `<loc>`（`robots.txt` 里声明了该 sitemap）。
2. 启动 headless Chrome（上一条命令），只读 `/json/version` 的 `webSocketDebuggerUrl` 走原生 WebSocket，**不经过 cdp-proxy**。
3. 每页：`Target.createTarget` → `attachToTarget(flatten)` → `Page.navigate` → 轮询 `Runtime.evaluate('document.body.innerText')` 直到「readyState complete 且长度连续两次不变」→ 取文本 → `closeTarget`。
4. 并发 4 路，375 页约 10 分钟，成功率 100%。**不要**用固定 `sleep` 等页面：后台标签会被节流，固定等待会拿回空内容。

注意事项：
- 正文开头 20-30 行是导航噪音（`Skip to main content` / `On this page` …），写文档时要剥掉。
- sitemap 不是真相：375 条里有 6 条实际返回 404，见 `site-map.md` 末节。
- Edge 的 `--dump-dom` 在 `styles/color/roles` 等重型页会 ERR_TIMED_OUT，CDP 通道无此问题。

### Chrome CDP 通道的两个本机坑

1. **proxy 的兜底路径有 bug**：`cdp-proxy.mjs` 在「手动 `--remote-debugging-port` 启动」场景下返回 `wsPath: null`，拼出 `ws://127.0.0.1:9222/devtools/browser`（**少了 browser id**），Chrome 回 200 而非 101，报 `non-101 status code`。正常路径（`chrome://inspect#remote-debugging` 开关）不受影响。
2. **全局 `http_proxy` 会拦 localhost**：本机 `http_proxy=http://127.0.0.1:7706`，curl 访问 `127.0.0.1:3456` 会报 `upstream connect failed (10061)`。必须加 `--noproxy '*'`。

绕过方式（已验证）：直接用原生 WebSocket 连 `/json/version` 返回的 `webSocketDebuggerUrl`，走 `Target.createTarget` + `Target.attachToTarget` + `Runtime.evaluate('document.body.innerText')`，不经过 proxy。

另注：Chrome 136+ 起，用**默认 profile** 启动 `--remote-debugging-port` 不再生效，必须用 `--user-data-dir` 指定独立目录，或在 `chrome://inspect/#remote-debugging` 勾选授权（后者才能带上登录态）。

**结论写法约定**：版本表能读到 → 可直接写「官方版本表显示 X（日期）」；只有次级来源 → 写「据 X（日期），待人工确认」。

## 待核验清单

- [x] M3 是否新增了 emphasized 字阶 —— **已确认**：官网 typography 页写明 15 baseline + 15 emphasized，共 30 档；详见 `01-Foundations/typography.md`
- [x] 30 档字阶的**具体字号/行高数值**（2026-11-16 部分解决）：CDP 抓 `type-scale-tokens` 页正文，**确认官网正文无数值表**（只有单位换算说明，数值在图片/令牌模块）——原判断正确；同日从 `material3-1.5.0-rc01-sources.jar` 的 `TypeScaleTokens.kt` 取得 **15 baseline + 15 emphasized 的 Compose 实现值**（一手来源），写入 `typography.json` 的 `compose_values`。**官网设计规范页的数值仍未取到**，Compose 实现值 ≠ 官网规范值，两者分层记录、不互相覆盖。
- [ ] material3 1.5.0 是否 GA（**仍未勾**：官方无 GA 公告，版本表仅到 alpha29；Maven 存在 rc01 artifact 不等于 GA）
- [x] `1.5.0-rc01` 的真实发布状态（2026-11-16：官方 Maven POM 实证 artifact 存在且 alpha BOM 2026.11.00 映射到它；**非 GA**，与版本表冲突保留）
- [x] M3 Expressive 各组件从 experimental 转正的**逐项**清单（2026-11-16：1.5.0-rc01 sources jar 注解实证，见 `02-Components/expressive-components.md`；仅对该版本有效，升级需重查）
- [x] motion duration / easing 令牌的确切数值 —— **已获取官方令牌表原文**，见 `05-Design-Tokens/motion.json` 与 `01-Foundations/motion.md`
- [x] 动效范式是否已变更 —— **已确认**：M3 Expressive 引入弹簧物理系统，easing/duration 仅保留给平台转场且「no longer maintained」
- [x] Spring token 的 dampingRatio / stiffness 具体数值 —— **已从 AndroidX 源码取得 12 组**（`MotionScheme.kt` + `StandardMotionTokens.kt` + `ExpressiveMotionTokens.kt`）
- [x] 形状档位的 dp 值 —— **已确认是十档**（0/4/8/12/16/20/28/32/48 + full），见 `01-Foundations/shape-system.md`
- [x] 按钮各尺寸档（XS/M/L/XL）的容器高度文字值 —— **已完全确认**：2026-11-16 视觉读取 buttons measurements 图，32/40/56/96/136dp 与 icon-buttons 阶梯一致；内边距与图标尺寸也一并取得，见 `02-Components/buttons.md`
- [x] 语言高度（language height）在 Compose 中的 API 名称与生效方式 —— 已写入 `01-Foundations/typography.md`
- [ ] Compose 1.13 稳定后 Grid / Flexbox / 新 BasicTextField 的迁移方式
- [x] 官方 Specs 页中「只有标注图、没有文字数值」的测量值 —— **已大部分解决**（2026-11-16）：视觉读取 specs 测量图，覆盖 nav bar、nav rail、app bars、date pickers、cards、buttons，图片 PNG 已存档至 `06-Figma-Resources/assets/specs-redlines/`。剩余：**flexible nav bar 整条高度、app bar 标题字号、字形与圆角细节，仍需 Figma Design Kit 复核**——2026-11-16 CDP 重抓 `navigation-bar/specs` 正文确认「flexible 更矮、medium 横排项」为文字结论，但**整条高度无文字值**，维持待 Figma Kit，不臆造。
- [ ] XR / Watch 的组件级尺寸 —— 官网两套文档只有示意，缺可实现的 dp 值，需查 Jetpack XR SDK 与 Wear Compose
- [ ] `spatial dialog` 等 XR 能力在 Compose 中的落地时间（官方 Note 明说尚未提供）

### 2026-11-16 追加：Expressive 组件「跟进最新」而非「等 stable」

用户约定（2026-11-16）：**Expressive 组件不等待 stable 发布，直接跟进官方 Maven 上的最新版本**。执行口径：

1. **确定最新版本**：读官方 Maven 的 `maven-metadata.xml`（如 `https://dl.google.com/dl/android/maven2/androidx/compose/material3/material3/maven-metadata.xml` 的 `<version>` 列表 + `<lastUpdated>`），而不是仅看 release notes 版本表（后者可能滞后）。2026-11-16 实测：material3 最新为 **1.5.0-rc01**（`lastUpdated=20261104`），compose-bom-alpha 最新为 **2026.11.00**，无更高版本。
2. **逐项注解状态**：下载该版本的 `-sources.jar`，读类文件上的 `@ExperimentalMaterial3ExpressiveApi` 注解（一手来源），更新 `02-Components/expressive-components.md` 的逐项表；写明「仅对该版本有效」。
3. **写法分层**：组件在最新预发布版无实验注解 → 写「X 版本源码实证无注解」（可写结论）；**仍不能写「已 GA / 稳定」**——除非官方 release notes 版本表把稳定列更新。artifact 存在、注解移除、GA 是三件不同的事。
4. **升级节奏**：每次 BOM/artifact 升级都重跑第 1–2 步（规则 V4：BOM 升级是独立变更），并在 CHANGELOG 记录版本与注解变化。

## 更新规则

- Compose 依赖版本查 AndroidX Release Notes 与 BOM mapping。
- API 稳定性查 API Reference 和 release notes。
- 设计规则查 m3.material.io 对应章节。
- Android 平台行为查 Android Developers。
- **不要从博客日期或第三方文章推断 API 当前状态。**
- 每周检查 Compose Material3 release notes 与示例仓库；每月检查设计规范与 Figma Kit。
