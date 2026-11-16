# Icon Buttons / 图标按钮

- 官方指南：https://m3.material.io/components/icon-buttons ｜ 规格页：https://m3.material.io/components/icon-buttons/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**变体、五档尺寸（含 dp 值）、圆角、颜色角色、状态层百分比均已按官方文本核对**；icon 尺寸 / 三种宽度的 dp 值在图片中

## 用途

> Icon buttons must use a system icon with a clear meaning.

图标按钮承载**常见动作**。两个变体：

- **Default**：打开其它元素（菜单、搜索）或执行动作。
- **Toggle**：可开/关的二元动作（favorite、bookmark）。

M2 时代这类组件叫 "toggle buttons"，M3 起统一为 icon buttons 的两个变体。

## 两代能力

| 配置 | M3 | M3 Expressive |
|---|---|---|
| Default / Toggle 两变体 | ✅ | ✅ |
| 尺寸：Small（默认） | ✅ | ✅ |
| 尺寸：**XS / M / L / XL** | ❌ | ✅ |
| 形状：Round（默认） | ✅ | ✅ |
| 形状：**Square** | ❌ | ✅ |
| 颜色：filled（默认）/ tonal / outlined / standard | ✅ | ✅ |
| 宽度：Default | ✅ | ✅ |
| 宽度：**Narrow / Wide** | ❌ | ✅ |

注意：M3 里 icon buttons 的 **filled 是默认**颜色样式（label buttons 默认是 filled）。

## 规格

### 尺寸（官方 Guidelines 页文字值）

| 尺寸 | 值 |
|---|---|
| Extra small | **32dp** |
| Small（默认） | **40dp** |
| Medium | **56dp** |
| Large | **96dp** |
| Extra large | **136dp** |

宽度三档（narrow / default / wide）与 icon 尺寸的 dp 值在 specs 页图片中，未给文字值。

### 圆角（官方 Measurements 表）

| | XS | S | M | L | XL |
|---|---|---|---|---|---|
| A. 圆形按钮 | Full | Full | Full | Full | Full |
| B. 方角按钮 | 12dp | 12dp | 16dp | 28dp | 28dp |
| C. 按下态 | 8dp | 8dp | 12dp | 16dp | 16dp |

- 按下时变方；round 与 square 的**按下圆角必须一致**（取 C 行）。
- Toggle 静止态变形：默认未选中圆 → 选中方；静止为方角则选中变圆。

### 触控目标

> Extra small and small icon buttons must have a target size of 48x48dp or larger to be accessible.

XS（32dp）和 S（40dp）本体不足 48dp，**必须用外围目标区补足**。

## 颜色角色

四种颜色样式，按强调递减：**Filled > Tonal > Outlined > Standard**。Default 与 Toggle 用不同角色：

| 样式 | Default（容器/图标） | Toggle 未选中 | Toggle 选中 |
|---|---|---|---|
| Filled | `primary` / `onPrimary` | `surfaceContainer` / `onSurfaceVariant` | `primary` / `onPrimary` |
| Tonal | `secondaryContainer` / `onSecondaryContainer` | `secondaryContainer` / `onSecondaryContainer` | `secondary` / `onSecondary` |
| Outlined | `outlineVariant` 描边 / `onSurfaceVariant` | `outlineVariant` 描边 / `onSurfaceVariant` | `inverseSurface` / `inverseOnSurface` |
| Standard | 无容器 / `onSurfaceVariant` | 无容器 / `onSurfaceVariant` | 无容器 / `primary` |

> Other color roles can be used as long as the container and text have a 3:1 contrast ratio.

Standard 的容器静止时不可见，施加状态层后才可见。

### 状态层（官方 States 页文字值）

所有样式统一：**Disabled 10%、Hovered 8%、Focused 10%、Pressed 10%**（state layer 不透明度）。

## 使用规则（Guidelines 页）

1. 图标含义必须**清晰无歧义**；default 按钮用 filled 图标。
2. **Toggle 图标规则**：未选中用 outlined 版本，选中用 filled 版本；没有 filled 版本就提高到 **semibold** 字重，仍不够用 **bold**——保证选中至少通过两个属性传达（不只靠颜色）。
3. 强调选型：filled 用于高强调关键动作（下载、删除），**一屏克制使用**；tonal 是 filled 与 outlined 的中间档，适合搭配高强调动作的次动作（如视频会议的 "Raise hand"）；outlined 用于中强调（翻卡片组）；standard 用于低强调或彩色表面上。
4. 需要与背景分离时用 filled/tonal/outlined（有容器），不要把 standard 放在花背景上。
5. **尺寸与宽度用于层级**：主动作最大最显眼（计时开始/暂停、播放/暂停）；同等重要的按钮**用同一尺寸**。
6. 一次只用少量图标按钮；密集布局中把常用动作放进 toolbar 或 button group（相邻图标按钮按压时互相响应）。
7. **Don't**：没有选中状态的动作用 toggle（如 overflow 菜单按钮）。
8. **Don't**：混排变体时不靠颜色区分主次。

## 无障碍（Accessibility 页）

- **读屏应能**：理解图标含义；导航并激活；（适用时）获得 tooltip。
- **对比度**：图标与所在表面/背景 **≥ 3:1**。
- **触控目标**：每个图标按钮 ≥ 48dp，**嵌套在其它组件（如 app bar）内时同样适用**。
- **密度**：**不要默认压缩密度**——会把目标压到 48×48 CSS px 以下；密度调整必须由用户主动选择，且控件保持 ≥ 48×48 CSS px。
- **键盘**：Tab 聚焦（跳过禁用项）；Space 或 Enter 激活。
- **读屏标签**：描述**动作**而非图标名——"Add to favorites"（心形图标）、"Bookmark"、"Send message"。
- **Hover（Web）**：悬停时显示带无障碍标签的 tooltip，文案清晰简短（描述动作，不是图标名）。

## Compose 实现

稳定 API（material3）：

| 官方样式 | Compose |
|---|---|
| Standard | `IconButton` / `IconToggleButton` |
| Filled | `FilledIconButton` / `FilledIconToggleButton` |
| Tonal | `FilledTonalIconButton` / `FilledTonalIconToggleButton` |
| Outlined | `OutlinedIconButton` / `OutlinedIconToggleButton` |

```kotlin
// 必须给 contentDescription，描述动作
IconButton(onClick = { toggleFavorite() }) {
    if (isFavorite) {
        Icon(Icons.Filled.Favorite, contentDescription = "收藏")
    } else {
        Icon(Icons.Outlined.FavoriteBorder, contentDescription = "收藏")
    }
}
```

- `IconButton` 默认 48×48dp 触控目标，**不要用 `Modifier.size(24.dp)` 覆写整体尺寸**（改用图标自身 `Modifier.size()`）。
- 选中态图标切换遵循官方 outlined→filled 规则；Compose 没有内置 semibold 加粗，需自备双套图标资源。
- Expressive 能力（square 形状、XS/M/L/XL、narrow/wide 宽度、形状 morph）在 `androidx.compose.material3` 的 expressive API 线（如 `IconButton` 的 shapes 参数、`WobblyIconButton` 等），**需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`，名称与签名随版本变动，接入前核对**。

## 常见错误

1. 图标含义模糊还坚持纯图标（官方：system icon with a clear meaning）。
2. XS/S 档没补足 48dp 触控目标。
3. `contentDescription` 写图标名（"心形"）而不是动作（"收藏"）。
4. 没有选中状态的动作用了 `IconToggleButton`。
5. 选中态只换颜色、图标始终同一副 outline（违反「双属性传达」要求）。
6. 默认开启密度压缩，触控目标跌破 48dp。
7. 一排全是 filled 图标按钮，无层级。

## 来源

- https://m3.material.io/components/icon-buttons/overview（变体、Expressive 更新、M2 差异）
- https://m3.material.io/components/icon-buttons/specs（配置表、颜色角色、圆角、状态层百分比、48dp 目标）
- https://m3.material.io/components/icon-buttons/guidelines（尺寸 dp 值、强调选型、图标规则）
- https://m3.material.io/components/icon-buttons/accessibility（3:1、密度、键盘、标签、tooltip）
- 核验日期：2026-11-16
- 关联：`02-Components/buttons.md`（同族尺寸阶梯与形状 morph）
