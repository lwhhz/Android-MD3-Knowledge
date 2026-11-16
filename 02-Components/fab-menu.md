# FAB Menu / 悬浮菜单（Expressive）

- 官方指南：https://m3.material.io/components/fab-menu ｜ 规格页：https://m3.material.io/components/fab-menu/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**M3 无此组件，Expressive 新增**；关闭按钮 56dp、边距 16/24dp、2-6 项、无障碍标签规范均为官方文字值

## 代际归属与用途

| 变体 | M3 | M3 Expressive |
|---|---|---|
| FAB menu | ❌ | ✅（仅 Jetpack Compose: Expressive 已实现；MDC-Android 与 Web 的 Expressive 均 Unavailable） |

官方原文：FAB menu 用来**取代 speed dial 和堆叠小 FAB** 的用法。它从 FAB 展开，显示 **2-6 个相关动作**，动作必须同属一类（如 Share 下的各分享渠道）；**不要把不相关动作塞进同一个 FAB menu**。

> Don't open a FAB menu from an extended FAB or any other component.

只能从**普通 FAB** 展开，不能从 extended FAB 或其它组件打开。

## 结构与规格

解剖：**Close button**（关闭按钮）+ **Menu item**（列表项）。

| 属性 | 值 | 说明 |
|---|---|---|
| 关闭按钮尺寸 | **始终 56dp** | 官方强调 "should always be 56dp"，与常规 FAB 一致 |
| 菜单项尺寸 | 与 **medium button specs 相同** | 官方原文 "FAB menu items share the same measurements as the medium button specs" |
| 项目数 | **2-6 个** | 少于 2 或多于 6 都不行（Don't: 只有一项的 FAB menu） |
| FAB 边距 | **16dp** | "The FAB should always have 16dp margins" |
| 大屏边距 | **16dp → 24dp** | large/extra large 窗口增为 24dp |
| Web 上 FAB 与菜单间距 | **推荐 4dp** | 可变，但 4dp 为官方推荐值 |

排布与动画：

- 菜单从 FAB 的 **trailing 角**（优先顶部对齐角）展开；关闭按钮与 FAB **共享同一个 top trailing 角锚点**、出现在同一位置。
- 更大的 FAB（medium/large）：关闭按钮位置更高，与 FAB **顶部对齐**，下方留更大边距。
- 菜单整体沿窗口 **trailing 边对齐**；RTL 语言中靠左对齐并整体镜像。
- 高度受限（如手机横屏）时，**菜单项可以在关闭按钮后方滚动**——关闭按钮必须始终易达、不被遮挡（不是收起菜单）。

## 颜色

三套颜色集：**Primary / Secondary / Tertiary**。每套含 container 与 tone 两档角色（`primaryContainer`/`onPrimaryContainer` 与 `primary`/`onPrimary` 等）。支持动态色，兼容任意 FAB 颜色样式。

**配对规则（官方）**：FAB menu 颜色集要**匹配 FAB 的颜色样式**——primary/primaryContainer 的 FAB 配 primary set，secondary 配 secondary set，tertiary 配 tertiary set。

关闭按钮与菜单项使用**对比色**以聚焦注意力。颜色组只决定视觉强调，不承载语义。

## 使用规则（Guidelines 页）

1. ✅ 菜单出现的位置必须与打开它的 FAB **完全相同**（"It should always appear in the same place as the FAB that opened it"）。
2. 菜单项**必须有文字标签**；图标**不要删**（图标用于区分各项），仅在必要时才去掉图标。
3. 列表项宽度**贴合内容**，容器、图标、文字的内边距保持一致；**不截断文字、不设固定宽度**；所有元素保持圆角。
4. ❌ 不要改 FAB menu 的形状（Don't: "Don't change FAB menu shapes"）；❌ 不要撑大容器。
5. **FAB 与 floating toolbar、navigation rail 等组件同屏时不要用 FAB menu**（避免认知过载）。
6. 任何尺寸的 FAB 都能打开 FAB menu；窗口尺寸对应 FAB 尺寸（大窗口用大 FAB）。
7. 菜单锚定同一角/边，不随窗口尺寸漂移。
8. 菜单项可用 container transform 变形为任意形状（含全屏表面）。
9. Web 上 FAB menu 使用 menu 组件实现，与其它桌面应用体验一致。

## 无障碍（Accessibility 页）

- **触控目标**：所有元素 ≥ 48dp；默认就有 48×48dp 最小宽度与足够间距。
- **初始焦点**：FAB 被选中 → 菜单展开 → **焦点留在关闭按钮上**（它取代了原 FAB），然后从顶部菜单项向下移动。
- **键盘**：Tab 移到下一个交互元素；Space 或 Enter 激活聚焦的按钮/菜单项。
- **不要完全遮挡**可交互元素及其焦点指示器（部分遮挡可以，焦点指示器须可见）。
- **标签规范（Android）**：
  - 关闭按钮：Label = **"Toggle menu"**，Role = **Button**，State = **expanded / collapsed**（读屏需播报展开状态）。
  - 菜单项：Label = **与 UI 文字一致**（如 "Reply all"），Role = **Button**。
- **Web**：FAB 的无障碍标签应描述它将打开的**菜单**；其余遵循 FAB 与 menu 的无障碍指南。

## Compose 实现

Expressive 新增组件，官方确认 **Jetpack Compose: Expressive 已实现**。对应 `androidx.compose.material3` 中的 `FloatingActionButtonMenu` / `ToggleFloatingActionButton` / `FloatingActionButtonMenuItem` 一族 API，**均需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`，名称与签名随版本变动**，接入前核对：

1. 组件类是否存在（上述命名以当前版本 API reference 为准）；
2. 关闭按钮与触发 FAB 的锚点/位置参数；
3. 三套颜色集的 Defaults 入口；
4. 展开状态的语义（toggle 状态）是否由组件自带。

## 常见错误

1. 从 extended FAB 打开 FAB menu（官方禁止）。
2. 关闭态 FAB 缩小成 40dp（官方是始终 56dp）。
3. 展开项只有图标没有文字，或删掉了图标。
4. 菜单项少于 2 个或多于 6 个，或混入不相关动作。
5. 横屏短屏时关闭按钮被菜单项挡住。
6. 关闭按钮读屏没有 expanded/collapsed 状态播报。
7. 与 floating toolbar / navigation rail 同屏使用。
8. 大屏仍用 16dp 边距（应为 24dp）。

## 来源

- https://m3.material.io/components/fab-menu/overview（2-6 项、颜色集、平台可用性）
- https://m3.material.io/components/fab-menu/specs（56dp 关闭按钮、16/24dp 边距、4dp 间距、medium button 同尺寸）
- https://m3.material.io/components/fab-menu/guidelines（Do / Don't、排布、滚动）
- https://m3.material.io/components/fab-menu/accessibility（48dp、初始焦点、标签规范）
- 核验日期：2026-11-16
- 关联：`02-Components/fab.md`（触发器规则）、`02-Components/menus.md`（普通菜单）
