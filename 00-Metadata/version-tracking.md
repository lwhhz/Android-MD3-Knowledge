# 版本追踪

每次核验记录：

| 检查日期 | 官方来源 | 组件/API | 版本 | 状态 | 变化 | 处理 |
|---|---|---|---|---|---|---|
| YYYY-MM-DD | URL | 名称 | 官方明确版本 | Stable/Beta/Alpha/Experimental/Deprecated | 摘要 | 已更新/待确认 |

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

## 核验方法（实测结果，2026-11-14）

| 方式 | m3.material.io | developer.android.com | 结论 |
|---|---|---|---|
| WebFetch 工具 | ❌ 只返回标题 | ❌ fetch failed | 不可用 |
| `curl` 取 HTML | ❌ 只有「This website requires JavaScript」 | — | 不可用 |
| **headless Edge `--dump-dom`** | ✅ 42MB DOM，正文完整 | ✅ 885KB，版本表可读 | **可用** |
| **Chrome CDP（web-access skill）** | ✅ innerText 更干净，超时页也能拿到 | ✅ | **可用，且能处理 Edge 超时的页** |
| **CDP + `textContent`** | ✅ 能拿到折叠面板里的隐藏值 | ✅ | **关键技巧**：innerText 只给可见文本，token 表要点开才渲染 |
| **GitHub Contents API** | — | `androidx/androidx` 源码可读 | ✅ `api.github.com` + `Accept: application/vnd.github.raw` |
| `raw.githubusercontent.com` | — | — | ❌ SSL 连接失败（exit 35） |
| `android.googlesource.com` | — | — | ❌ 连接超时 |
| `dl.google.com` Maven 索引 | — | — | ❌ fetch failed |
| jetc.dev 周报 | ✅ | ✅ | 仅用于定位，不作结论 |

headless 命令模板（本机 Edge 路径）：

```bash
"/c/Program Files (x86)/Microsoft/Edge/Application/msedge.exe" \
  --headless=new --disable-gpu --no-first-run \
  --user-data-dir=/tmp/edgeprof --virtual-time-budget=12000 \
  --dump-dom "URL" > out.html
```

注意事项：
- `--virtual-time-budget` 给 10000-15000ms；m3 的 typography 页需要 10s 才渲染完。
- 部分页面会 `ERR_TIMED_OUT`（实测 `styles/color/roles`、`designsystems/material3` 失败）；`color/roles` 改用 Chrome CDP 可正常拿到。
- 输出可能极大（typography 页 42MB），先 `grep` 命中再定点提取，别整文件读。

### Chrome CDP 通道的两个本机坑

1. **proxy 的兜底路径有 bug**：`cdp-proxy.mjs` 在「手动 `--remote-debugging-port` 启动」场景下返回 `wsPath: null`，拼出 `ws://127.0.0.1:9222/devtools/browser`（**少了 browser id**），Chrome 回 200 而非 101，报 `non-101 status code`。正常路径（`chrome://inspect#remote-debugging` 开关）不受影响。
2. **全局 `http_proxy` 会拦 localhost**：本机 `http_proxy=http://127.0.0.1:7706`，curl 访问 `127.0.0.1:3456` 会报 `upstream connect failed (10061)`。必须加 `--noproxy '*'`。

绕过方式（已验证）：直接用原生 WebSocket 连 `/json/version` 返回的 `webSocketDebuggerUrl`，走 `Target.createTarget` + `Target.attachToTarget` + `Runtime.evaluate('document.body.innerText')`，不经过 proxy。

另注：Chrome 136+ 起，用**默认 profile** 启动 `--remote-debugging-port` 不再生效，必须用 `--user-data-dir` 指定独立目录，或在 `chrome://inspect/#remote-debugging` 勾选授权（后者才能带上登录态）。

**结论写法约定**：版本表能读到 → 可直接写「官方版本表显示 X（日期）」；只有次级来源 → 写「据 X（日期），待人工确认」。

## 待核验清单

- [x] M3 是否新增了 emphasized 字阶 —— **已确认**：官网 typography 页写明 15 baseline + 15 emphasized，共 30 档；详见 `01-Foundations/typography.md`
- [ ] 30 档字阶的**具体字号/行高数值** —— 官网以图片与令牌模块呈现，DOM 中无数值表，仍未取到
- [ ] material3 1.5.0 是否 GA（当前版本表仅到 1.5.0-alpha29）
- [ ] `1.5.0-rc01` 的真实发布状态（与版本表冲突）
- [ ] M3 Expressive 各组件从 experimental 转正的**逐项**清单
- [x] motion duration / easing 令牌的确切数值 —— **已获取官方令牌表原文**，见 `05-Design-Tokens/motion.json` 与 `01-Foundations/motion.md`
- [x] 动效范式是否已变更 —— **已确认**：M3 Expressive 引入弹簧物理系统，easing/duration 仅保留给平台转场且「no longer maintained」
- [x] Spring token 的 dampingRatio / stiffness 具体数值 —— **已从 AndroidX 源码取得 12 组**（`MotionScheme.kt` + `StandardMotionTokens.kt` + `ExpressiveMotionTokens.kt`）
- [x] 形状档位的 dp 值 —— **已确认是十档**（0/4/8/12/16/20/28/32/48 + full），见 `01-Foundations/shape-system.md`
- [ ] 按钮各尺寸档（XS/M/L/XL）的容器高度文字值（官方以图片呈现）
- [ ] 语言高度（language height）在 Compose 中的 API 名称与生效方式
- [ ] Compose 1.13 稳定后 Grid / Flexbox / 新 BasicTextField 的迁移方式

## 更新规则

- Compose 依赖版本查 AndroidX Release Notes 与 BOM mapping。
- API 稳定性查 API Reference 和 release notes。
- 设计规则查 m3.material.io 对应章节。
- Android 平台行为查 Android Developers。
- **不要从博客日期或第三方文章推断 API 当前状态。**
- 每周检查 Compose Material3 release notes 与示例仓库；每月检查设计规范与 Figma Kit。
