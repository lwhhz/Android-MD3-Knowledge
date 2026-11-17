# Material 3 Expressive / 表现力组件

- 设计指南：https://m3.material.io/ ｜ 官方说明：https://m3.material.io/blog
- Compose 指南：https://developer.android.com/develop/ui/compose/designsystems/material3
- 核验日期：2026-11-14（原始）／2026-11-16（注解逐项实证）
- 状态：**部分组件已在 material3 1.5.0 线转正，逐项状态必须查 API reference 的注解，不要整体当成 stable**
- 2026-11-16 补充来源：**Google 官方 Maven 的 `material3-1.5.0-rc01-sources.jar`**（一手来源，读源码 `@ExperimentalMaterial3ExpressiveApi` 注解）；真机编译/运行通过属「已实证」，不冒充官方规范。仅对 1.5.0-rc01 有效，升级重查。

## 它是什么

M3 Expressive 是 M3 的表现力扩展，不是 M4。它改变的是：主题默认值、动效强度、形状变化幅度、字阶扩展，以及新增的一批组件。

官方定位是「expansion pack」——**可以选择性接入**，不接入的代码不受影响。

## 接入方式

主题层：

```kotlin
MaterialExpressiveTheme(
    colorScheme = expressiveLightColorScheme(),
    typography = AppTypography,
    shapes = AppShapes
) {
    AppContent()
}
```

- `MaterialExpressiveTheme` 与 `expressiveLightColorScheme()` / `expressiveDarkColorScheme()` 是 expressive 主题入口。
- **颜色槽位与 M3 完全一致**，所以迁移是机械的：换外层 + 换构造器。
- 变的是：默认形状（更圆）、组件内部动效规格、部分组件的默认样式。

风险：换成 expressive 主题会**同时改变所有组件的默认外观**，包括你没打算改的那些。上线前要全量回归一遍视觉。

## 组件清单与状态

**注解状态逐项实证（2026-11-16，material3 1.5.0-rc01 sources jar，一手来源）**：

| 组件 | Compose 入口 | 注解状态（1.5.0-rc01 源码实证） |
|---|---|---|
| 波浪进度条 | `LinearWavyProgressIndicator` / `CircularWavyProgressIndicator` | ✅ 无 `@ExperimentalMaterial3ExpressiveApi`（已转正） |
| 加载指示器 | `LoadingIndicator` / `ContainedLoadingIndicator` | ⚠️ **仍标注** `@ExperimentalMaterial3ExpressiveApi`（需 `@OptIn`） |
| 悬浮工具栏 | `HorizontalFloatingToolbar` / `VerticalFloatingToolbar` | ✅ 无实验注解；签名含必填 `expanded: Boolean` |
| 切换按钮 | `ToggleButton` | ✅ 无实验注解；`content` 是 `RowScope` 槽位（无 `checkedIcon` 参数） |
| 按钮组 | `ButtonGroup` | ✅ 无实验注解；connected 形态用 `ButtonGroupDefaults.connected*ButtonShapes()` |
| 主题入口 | `MaterialExpressiveTheme` | ✅ 无实验注解；`colorScheme` 等参数可空（null → 继承当前主题） |
| 形状 morph | `MaterialShapes` / `RoundedPolygon.toShape()` / `Morph.toPath` | ⚠️ **仍标注**实验注解 |
| 扩展字阶（emphasized） | `displayLargeEmphasized` … `labelSmallEmphasized`（15 个） | ✅ 1.5.0-rc01 源码实证：字号/行高与 baseline **完全相同**，只字重变化（400→500，500/700 档→700）；数值见 `05-Design-Tokens/typography.json` 的 `compose_values` |

> ⚠️ 上表**只对 1.5.0-rc01 有效**。`material3 1.5.0 是否 GA` 仍未确认（官方 release notes 版本表 RC 列空），本表不构成「可以整体当 stable 用」的背书。
>
> 每一行仍要在当前依赖版本里确认三件事：类是否存在、是否标注 `@ExperimentalMaterial3ExpressiveApi`、官方 API reference 是否已标稳定。升级版本后必须重查（规则 V2）。

## 使用判断

Expressive 的价值在于**表达状态的连续变化**（按下时形状收缩、展开时工具栏变形），而不是「让界面更花」。

三个判断：

1. **这个动效在解释什么？** 如果只是「好看」，不用。
2. **关掉动效后还成立吗？** 不成立说明信息被绑在动效上了。
3. **低端机上还流畅吗？** Expressive 动效幅度大、涉及形状插值，比普通过渡更吃性能。

## 什么时候明确不要用

- 表单、设置、金融操作等**以准确和稳定为第一优先**的界面。
- 已有成熟视觉规范、且与 Expressive 默认形状冲突的品牌。
- 需要与 Web / iOS 保持一致的跨端产品。
- 无障碍要求高、且团队没有精力做双重验证的场景。

## 渐进接入建议

1. 先只换主题，不改任何组件——观察全站视觉偏移量。
2. 挑一个低风险页面（如关于页、空态页）试用 expressive 组件。
3. 确认性能和回归成本后，再考虑进入主流程。
4. 任何 expressive 用法都要在代码里注释：**为什么用、依赖哪个版本、转正后要做什**。

## 与旧组件的兼容

- Expressive 组件与稳定组件可以共存，但同一屏内混用两种视觉规格（一个用 expressive 默认圆角、一个用 M3 默认）会显得不协调。
- 如果项目已经在 `MaterialTheme` 下定制了大量 shape，切到 expressive 主题会让这些定制失效或叠加出错——先在测试分支验证。

## 待核验清单

- [ ] material3 1.5.0 是否 GA（官方无 GA 公告；Maven 存在 rc01 artifact 不等于 GA）
- [x] 各 expressive 组件的 `@ExperimentalMaterial3ExpressiveApi` 注解是否已移除（2026-11-16：1.5.0-rc01 sources jar 逐项实证，见上表；仅对该版本有效）
- [x] 扩展字阶的确切样式名与数值（2026-11-16：`material3-1.5.0-rc01-sources.jar` 的 `TypeScaleTokens.kt` 实证 15 个 `*Emphasized` 样式；字号/行高与 baseline 相同，只改字重；写入 `typography.json`）
- [x] `HorizontalFloatingToolbar` 的完整参数（2026-11-16：签名 `HorizontalFloatingToolbar(expanded, floatingActionButton?, …, content: @Composable RowScope.() -> Unit)` 由 sources jar 实证）；锚点行为仍需 API reference 复核
- [x] Expressive motion 与 standard 的差异（2026-11-16：`ExpressiveMotionTokens.kt` vs `StandardMotionTokens.kt` 源码实证——**effects 完全相同**（damping 1.0，stiffness 1600/3800/800）；差异全在 **spatial**：damping 0.9→0.8/0.6，stiffness 700→380/800。无「推荐时长差异」，差异是弹簧参数）

记录到 `00-Metadata/version-tracking.md`。

## 来源

- https://m3.material.io/（Expressive 章节）
- https://m3.material.io/blog（官方转型与转正公告）
- `01-Foundations/motion.md`（动效判断规则）
- `00-Metadata/version-tracking.md`（版本快照）
