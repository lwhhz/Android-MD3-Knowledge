# App Bar / Bottom App Bar 迁移记录（2026-11-18）

> 决策：App bar 继续做；从 `appbar/` 迁入 `components/` 并接入主 demo。

## 一、迁移

| 原位置 | 新位置 |
|---|---|
| `appbar/M3AppBar.kt` | `components/M3AppBar.kt`（package components） |
| `appbar/M3BottomAppBar.kt` | `components/M3BottomAppBar.kt` |
| `appbar/M3Tokens.kt` | `app/src/main/java/com/example/m3appbar/M3AppTokens.kt` |

- `appbar/` 目录已删除，`sourceSets` 中的 `appbar` 路径已移除；
- 颜色从硬编码 baseline `M3Colors` 改为 `MaterialTheme.colorScheme`，深色主题可读；
- `M3BottomAppBar.containerColor` 改为可空，默认跟随主题；
- 新增单测 `M3AppBarTokensTest`（variant 解析）。

## 二、Demo

新增分区 `M3AppBar / M3BottomAppBar · configurations`：

- Small-centered + Flat（透明容器）；
- Medium + On-scroll（surfaceContainer + 阴影 + supporting text）；
- Bottom app bar：2 图标 + FAB。

![App bar / Bottom app bar 真机](<screenshots/app-bar.png>)

## 三、验证

| 项 | 结果 |
|---|---|
| :app:testDebugUnitTest | **31 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 | 已安装并截图 |

## 四、参数

| 组件 | 参数 | 落地 |
|---|---|---|
| App bar | 6 | **6/6**：Configuration、Elevation、headline/supporting、leading、trailing、avatar/search |
| Bottom app bar | 2 | **2/2**：Icons(4)、Show FAB |

P0 统计相应从 173/315 更新到 **181/315**。

## 五、限制

1. App bar 已改用官方 `TopAppBar` / flexible 变体；Search 配置的搜索框仍是自绘入口。
2. 头像/leading image 仍是 `Painter` 槽，未做图片加载；
3. 未做 RTL、pixel 基线、宽度断点测试；
4. `components/M3Appbars.kt` 及其 `M3XRAppBar` 空壳已删除（XR 不推进）。