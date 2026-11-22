# FAB / 悬浮操作按钮

- 官方指南：https://m3.material.io/components/floating-action-button ｜ 规格页：https://m3.material.io/components/floating-action-button/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**变体、颜色映射、状态、使用与无障碍规则已核对**；尺寸 dp 值在 specs 页图片中，未取到文字值

## 用途

> Use a FAB for the most important action on a screen; it appears in front of all other content.

FAB 代表屏幕**唯一的首要动作**，悬浮于所有内容之上，滚动时保持原位。

不适合 FAB 的（官方 Don't）：次要/溢出/不明确/破坏性动作——归档、删除、警报、剪贴文本这类小任务、以及更适合 toolbar 的控件（音量、字体颜色）。

## 三个变体（含代际差异）

| 变体 | M3 | M3 Expressive | 官方推荐度 |
|---|---|---|---|
| FAB（默认） | ✅ | ✅ | 紧凑窗口、屏上还有其它动作时 |
| **Medium FAB** | ❌ | ✅ | **官方标注 "most recommended"**，多数断点适用 |
| Large FAB | ✅ | ✅ | 需要醒目主动作、尤其大屏 |
| Small FAB | ✅ | ❌ **不再推荐**（"Use a larger size"） | 仍可用，但新设计别用 |

- **Medium 是 Expressive 新增**；M3 项目里没有。
- 变体按**尺寸**区分，不按颜色。
- 各档尺寸：specs 页 Measurements 区只有图片，**无文字值**。FAB menu 页写明「关闭按钮（即一个 FAB）should always be 56dp」；**2026-11-22 源码佐证**：material3 1.5.0 的 `FabBaselineTokens` 为 56×56dp，常规 FAB 56dp 可去掉推断标记。

## 颜色（Expressive 命名变更）

Expressive 更新（官方原文大意）：

- 原来的 primary/secondary/tertiary **set 色**实际用的是 container 角色，现更名为 **primary container / secondary container / tertiary container** 以对齐 token 名（**值未变**）。
- 新增真正使用 `primary`/`secondary`/`tertiary` 的 tone 色样式。

六套等价映射（官方：对比度与功能相同，选择纯凭风格）：

| 容器色 | 内容色 |
|---|---|
| `primaryContainer`（默认） | `onPrimaryContainer` |
| `secondaryContainer` | `onSecondaryContainer` |
| `tertiaryContainer` | `onTertiaryContainer` |
| `primary` | `onPrimary` |
| `secondary` | `onSecondary` |
| `tertiary` | `onTertiary` |

**Surface 色 FAB 仍可用但不再推荐。**

## 状态（官方 States 页）

| 状态 | 状态层 | elevation |
|---|---|---|
| Enabled | — | 官方此页未给数值 |
| Hovered | **8%** | **4** |
| Focused | 10% | 官方未单列 |
| Pressed | 10% | 官方未单列 |

（静止 elevation 3 为社区通行值，**⚠️ 推断，FAB specs 文本未明示**。）

> When using a non-default color mapping for FABs, make sure the state layer color is the same as the icon color.

即自定义颜色时状态层用**内容色**叠加，不要写半透明黑。

## 使用规则（Guidelines 页 Do / Don't）

1. **一屏最多一个 FAB**（Don't: 多个 FAB 竞争注意力）。
2. FAB 不是每屏必需——当图片等已表达主动作时不放。
3. 动作类型：**建设性**主动作（Create、Favorite、Share、Start a process）；**Don't**：次要、溢出、不明确、破坏性动作。
4. 图标用 **filled** 而非 outlined；选含义清晰的（add、message、edit），**Don't** 用开放歧义图标表达罕见动作。
5. 容器不被其它元素（如 badge）遮挡；与所在表面保持足够色彩对比。
6. **位置**：compact/medium 断点最佳为**右下角**（易触达、少遮挡）；expanded 断点考虑**左上角**（如 navigation rail 内），成为用户最先看到的交互元素之一。FAB 可左/中/右对齐，可位于导航栏之上或嵌入其中。
7. 卡片等**个体组件不应有自己的 FAB**。
8. Web 上 hover 时显示带文字标签的 tooltip。
9. 需要更多并列动作时：大屏转 extended FAB 或展开成 **FAB menu**（见 `02-Components/fab-menu.md`）。

### 行为

- **出现/消失**：从中心点向外展开；切页时 FAB 应消失并在新内容就位后（仅当仍相关时）在**原位置**重现；不要随正文内容一起动画。
- **扩展**：可用 container transform 变形为任意形状（含全屏表面）。
- **滚动**：FAB 原地不动；extended FAB 可在滚动时收起为 FAB、到底部再展开。

## 无障碍（Accessibility 页）

- **不要禁用 FAB**：动作不可用时不显示 FAB（官方原文："If the action represented in the FAB is unavailable, the FAB shouldn't appear"）。
- 图标与容器对比度 **≥ 3:1**。
- **焦点优先**：整体焦点顺序中优先安排 FAB——移动端顺序可以是 app bar → 导航栏 → 直接跳到 FAB。
- Web 上聚焦时可显示 tooltip 呈现标签。
- **位置**：大屏为读屏用户考虑把 FAB 放左上区域（需实测验证）；compact/medium 放右下。
- **Don't**：FAB 不得完全遮住可交互元素及其焦点指示器；部分遮挡允许，只要焦点指示器仍可见。
- **键盘**：Tab 聚焦；Space 或 Enter 执行默认动作。
- **读屏标签**：描述动作（pencil 图标 → "Compose a new message"）。

## Compose 实现

| 官方变体 | Compose |
|---|---|
| FAB | `FloatingActionButton` |
| Small FAB | `SmallFloatingActionButton`（官方已不推荐该尺寸） |
| Large FAB | `LargeFloatingActionButton` |
| Medium FAB | `MediumFloatingActionButton`（Expressive，需实验性注解核对） |

```kotlin
Scaffold(
    floatingActionButton = {
        FloatingActionButton(onClick = { compose() }) {
            Icon(Icons.Default.Edit, contentDescription = "写新笔记") // 描述动作
        }
    }
) { padding -> /* content */ }
```

自定义颜色时容器与内容成对改：

```kotlin
FloatingActionButton(
    onClick = {},
    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
) { Icon(Icons.Default.Add, contentDescription = "添加") }
```

- 需要文字标签 → `ExtendedFloatingActionButton`（见 `02-Components/extended-fab.md`）。
- FAB menu、形状 morph 等 Expressive API **需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`，签名随版本变动，接入前核对**。

## 常见错误

1. 一个屏幕两个 FAB（或 FAB + extended FAB 并存）。
2. 用 FAB 做删除 / 归档等破坏性或次要动作。
3. 仍用 Small FAB（官方已不推荐）。
4. 在 M3（非 Expressive）里找 Medium FAB。
5. 动作不可用时显示禁用态 FAB（官方：直接不显示）。
6. FAB 完全遮挡了某个可交互元素及其焦点指示器。
7. `contentDescription` 写图标名而不是动作。

## 来源

- https://m3.material.io/components/floating-action-button/overview（变体、颜色更名、M2 差异）
- https://m3.material.io/components/floating-action-button/specs（颜色映射、状态层、hover elevation 4）
- https://m3.material.io/components/floating-action-button/guidelines（Do / Don't、位置、行为）
- https://m3.material.io/components/floating-action-button/accessibility（禁用规则、3:1、焦点、键盘）
- 核验日期：2026-11-16
- 关联：`02-Components/extended-fab.md`、`02-Components/fab-menu.md`
