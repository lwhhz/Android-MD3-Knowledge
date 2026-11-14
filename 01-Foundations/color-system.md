# 色彩系统 / Color System

> 官方指南：https://m3.material.io/styles/color/roles
> Compose：https://developer.android.com/develop/ui/compose/designsystems/material3
> 核验日期：2026-11-14
> 核验方式：浏览器渲染官网正文后提取（headless Edge 该页超时，改用 Chrome CDP 成功）
> 状态：**角色数量与分组已按官网原文核对**；具体色值不在本库固化

## 一句话结论

官网原文：**「There are 26 standard color roles organized into six groups: primary, secondary, tertiary, error, surface, and outline.」**

26 个标准角色 + 一组**可选的 add-on 角色**。add-on 不是「高级用法」，是「大多数产品不需要」——官网明确说：不确定要不要用，就是不需要。

## 先分清四个词

| 词 | 含义 | 常见误用 |
|---|---|---|
| Surface | 背景、大面积低强调区域 | 当成卡片专用色 |
| Container | **前景元素的填充色**（按钮底、Chip 底） | **拿它当文字色**——官方明确禁止 |
| On | 画在配对颜色之上的文字 / 图标色 | 自己拿白或黑硬顶 |
| Variant | 同名角色的低强调版本 | 用 `outlineVariant` 定义可点击边界 |

## 26 个标准角色

### 强调三组（各 4 个，共 12）

| 角色 | 用途（官网表述） |
|---|---|
| `primary` | 最重要的组件：FAB、高强调按钮、激活态 |
| `onPrimary` | primary 上的文字与图标 |
| `primaryContainer` | 相对 surface 的醒目填充，用于关键组件如 FAB |
| `onPrimaryContainer` | primaryContainer 上的文字与图标 |
| `secondary` | 不那么突出的组件，如 Filter Chip |
| `onSecondary` | secondary 上的文字与图标 |
| `secondaryContainer` | 相对 surface 的低强调填充，用于 Tonal 按钮这类退后的组件 |
| `onSecondaryContainer` | secondaryContainer 上的文字与图标 |
| `tertiary` | 与 primary/secondary 形成对比的强调色，用于输入框等需特别关注的小元素 |
| `onTertiary` | tertiary 上的文字与图标 |
| `tertiaryContainer` | 相对 surface 的互补容器色，如输入框 |
| `onTertiaryContainer` | tertiaryContainer 上的文字与图标 |

### Error（4 个）

`error` / `onError` / `errorContainer` / `onErrorContainer`

**关键特性**：error 是**静态色**——在任意 dynamic color scheme 中都默认保持不变（但仍随浅色/深色主题适配）。所以「错误必须显眼」不依赖品牌色。

### Surface（3 + 5 个容器 = 8）

| 角色 | 用途 |
|---|---|
| `surface` | 背景默认色 |
| `onSurface` | 任意 surface / surfaceContainer 上的文字与图标 |
| `onSurfaceVariant` | 同上，低强调 |
| `surfaceContainerLowest` | 最低强调容器 |
| `surfaceContainerLow` | 低强调容器 |
| `surfaceContainer` | **默认**容器色 |
| `surfaceContainerHigh` | 高强调容器 |
| `surfaceContainerHighest` | 最高强调容器 |

五个容器色用于**在展开尺寸的屏幕上建立层级与嵌套容器**。

**跨断点映射不能变**：正文区用 `surface`、导航区用 `surfaceContainer`，手机与平板一致。窗口变大可以增加容器色层次，但这两个映射要守住。

### Outline（2 个）

`outline` / `outlineVariant`

## Outline 的四条禁令（官网 Don't 原文）

1. **不要用 `outline` 做分割线** → 用 `outlineVariant`（两者对比度要求不同）。
2. **不要用 `outline` 做卡片这类包含多个元素的组件的边框** → 用 `outlineVariant`。
3. **不要用 `outlineVariant` 建立视觉层级或定义可点击目标的边界** → 用 `outline` 或其它与 surface 达 3:1 的颜色。
4. **例外**：Chip、Button 的边框可用 `outlineVariant`，前提是内部图标与文字本身已达 4.5:1。

## Add-on 角色（默认不用）

### Fixed 强调色（12 个）

`primaryFixed` / `primaryFixedDim` / `onPrimaryFixed` / `onPrimaryFixedVariant`，secondary、tertiary 同构。

- Fixed：浅色与深色主题下**保持同一色调**，不像 container 那样翻转。
- FixedDim：比 Fixed 更深、更强调。
- OnFixed / OnFixedVariant：画在 Fixed 之上的文字图标，后者为低强调。

**风险**：Fixed 不随主题变，**容易造成对比度问题**。需要对比度时仍用 primary/secondary/tertiary。

### 表面 add-on（2 个）

`surfaceDim` / `surfaceBright`

与默认 surface 的反转方式不同：**保持相对明度**。默认 surface 在浅色主题最亮、深色主题最暗；surfaceBright 在两种主题下都最亮。

典型用法：导航栏用 surfaceDim 作背景，聊天窗口用 surfaceBright 作背景。

### Inverse（3 个）

`inverseSurface` / `inverseOnSurface` / `inversePrimary`

用于需要与周围界面**反色对比**的组件。典型：Snackbar 的背景、文字、文字按钮分别用这三个。

## 两个功能色（不属于角色分组）

`scrim`（模态遮罩）、`shadow`（阴影色）——Compose 中有，但不在 26 个角色与 add-on 清单里。

## 三条硬规则

1. **Container 不能当文字色**。官网明确：container 是前景元素的填充色，不应用于文字或图标。
2. **只在官方给定的配对与层级里用色**。乱配会在用户调整对比度档位时直接不可读；系统对配对本身保证至少 3:1。
3. **颜色不能是唯一信息载体**。错误态必须同时有图标或文案。

## Dynamic color

```kotlin
val colorScheme = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (dynamicColor) {
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        } else {
            if (darkTheme) DarkColors else LightColors
        }
    }
    else -> if (darkTheme) DarkColors else LightColors
}
```

- 仅 Android 12（API 31）及以上。
- **必须有 fallback**：关掉主题色、旧设备上界面要完整。
- 开启动态取色后**用户可调对比度档位**，必须在该状态下重新验证可读性。
- 品牌色只应落在 primary / tertiary 一族，不要污染 surface 系列。

## 对比度

| 内容 | 最低 |
|---|---|
| 角色配对（系统保证） | 3:1 |
| 正文文字 | 4.5:1 |
| 大号文字、图标、控件边界 | 3:1 |

注意区分：3:1 是**角色配对**的底线，不等于正文文字可以只做到 3:1。

## 常见错误

- 直接写 `Color(0xFF6750A4)` 当主色，绕过角色系统。
- 用 `primaryContainer` 当文字色（官方禁止：container 是填充色）。
- 用 `outline` 画分割线或卡片边框（应用 `outlineVariant`）。
- 深色主题只是把浅色主题反相。
- 用 `background` / `onBackground`——新版 material3 已 deprecated，改用 `surface` 系列。
- 用 primary 铺大面积背景（primary 是强调色，大面积会疲劳）。
- 一屏出现两个以上 primary 填充按钮，主次消失。
- 用 Fixed 色做需要对比度的地方，深色主题下不可读。
- 把 scrim / shadow 当成普通角色参与配对。

## 检查项

- [ ] 组件里没有裸 `Color(0x...)`
- [ ] 每个填充色都配了对应的 `on*` 内容色
- [ ] 没有用 container 当文字色
- [ ] 分割线用 `outlineVariant`，可点击边界用 `outline`
- [ ] Dynamic color 有 fallback，各对比度档位都验过
- [ ] 浅色 / 深色两套都验过
- [ ] 跨断点 surface 映射一致（正文 surface / 导航 surfaceContainer）
