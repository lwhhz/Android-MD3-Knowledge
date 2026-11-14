# MaterialTheme 主题

- 官方指南：https://developer.android.com/develop/ui/compose/designsystems/material3
- 核验日期：2026-11-14 ｜ 版本相关：material3 1.5.0 线新增 expressive 主题入口，见文末

## 单一入口原则

主题只在一个地方配置，包裹整个应用。**任何屏幕内部都不应该有第二个 `MaterialTheme`。** 需要局部改色时用 `Surface(color = ...)` 或组件的 `colors` 参数，不要再套一层主题。

```kotlin
// ui/theme/Theme.kt
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
```

应用入口：

```kotlin
setContent {
    AppTheme {
        AppNavHost()
    }
}
```

## 三个系统各自的入口

| 系统 | 主题属性 | 自定义方式 |
|---|---|---|
| 颜色 | `MaterialTheme.colorScheme` | `lightColorScheme(...)` / `darkColorScheme(...)` 或 Theme Builder 导出 |
| 字体 | `MaterialTheme.typography` | `Typography(...)` 逐档覆盖 |
| 形状 | `MaterialTheme.shapes` | `Shapes(...)`，只有五档 |

组件读取时一律走 `MaterialTheme.xxx`，不写死值。

## 自定义令牌用 CompositionLocal

M3 没有覆盖到的品牌级令牌（间距、自定义语义色），用 `CompositionLocal` 提供，不要做成全局单例：

```kotlin
data class Spacing(
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }
```

在 `AppTheme` 里 `CompositionLocalProvider(LocalSpacing provides Spacing()) { content() }`。

用 `staticCompositionLocalOf` 是因为间距不会中途变化——一旦变化会触发整棵子树重组，所以只在真的需要动态变化时才改用 `compositionLocalOf`。

## Preview 也要包主题

```kotlin
@Preview(name = "浅色", showBackground = true)
@Preview(name = "深色", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ButtonPreview() {
    AppTheme {
        Surface { /* 组件 */ }
    }
}
```

Preview 里**不要**开 dynamic color（Studio 预览环境的系统色不可控），固定用 `LightColorScheme` / `DarkColorScheme`。

## 常见错误

1. 在屏幕级 Composable 里再包一层 `MaterialTheme`，导致局部颜色与全局不一致。
2. 只在 `MainActivity` 里 `setContent` 时算一次主题颜色，配置变更后不更新——用 `isSystemInDarkTheme()` 而不是读一次系统状态。
3. 主题参数写成全局 `var`，Compose 观察不到变化。
4. `MaterialTheme` 外面没有 `Surface`，深色主题下背景是白的（Compose 不会自动铺背景）。
5. Preview 不包主题，渲染出来的颜色和真机不一致。

## Expressive 主题（1.5.0 线）

```kotlin
MaterialExpressiveTheme(
    colorScheme = expressiveLightColorScheme(),
    typography = AppTypography,
    shapes = AppShapes
) { AppContent() }
```

颜色槽位与 M3 完全一致，切换是机械的。但它会改掉**所有组件的默认形状与内部动效**，上线前需全量视觉回归。状态与风险见 `02-Components/expressive-components.md`。

## 检查项

- [ ] 全项目只有一处 `MaterialTheme`
- [ ] `MaterialTheme` 外层有 `Surface` 或显式背景
- [ ] 浅色 / 深色 / dynamic color 三条路径都通
- [ ] Preview 包了主题，且覆盖深浅色
- [ ] 组件内无裸色值、裸字号、裸圆角
