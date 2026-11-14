# ColorScheme 实现

- 官方指南：https://developer.android.com/develop/ui/compose/designsystems/material3
- 核验日期：2026-11-14

## 不要手写色值，用工具生成

角色的取色不是「挑几个好看的颜色」。M3 的角色来自**从种子色生成的色调板（tonal palette）**，每个角色对应特定色调值。手调会破坏对比度关系。

推荐流程：

1. 用 [Material Theme Builder](https://m3.material.io/theme-builder) 输入品牌种子色；
2. 导出 Compose `ColorScheme`（含浅色、深色）；
3. 粘贴到 `ui/theme/Color.kt`，保留导出日期注释；
4. 在 `05-Design-Tokens/colors.json` 登记来源（不要在这里存值）。

## 结构

```kotlin
// ui/theme/Color.kt
// 生成方式：Material Theme Builder，种子色 #6750A4，导出日期 2026-11-14

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6750A4),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),
    // secondary / tertiary / error 四件套同理
    surface = Color(0xFFFEF7FF),
    onSurface = Color(0xFF1D1B20),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0)
)
```

深色 scheme 是**另一套色调选择**，不是把浅色值反相。浅色主题的 `primary` 通常取亮度较低的色调，深色主题取较高的色调，以维持对比度。

## Dynamic color 与 fallback

```kotlin
val colorScheme = if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    val context = LocalContext.current
    if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
} else {
    if (darkTheme) DarkColorScheme else LightColorScheme
}
```

三条必须同时成立：

1. **开关打开且系统支持** → 用 dynamic。
2. **系统支持但用户/应用关闭** → 用自定义 scheme。
3. **系统不支持（API 31 以下）** → 用自定义 scheme。

**自定义 scheme 不能是「占位色」**。它必须是完整、经过对比度验证的一套配色，因为它在旧设备上就是唯一配色。

## 品牌扩展色

M3 的角色集不覆盖所有语义（比如「成功」「警告」「信息」）。扩展方式：

```kotlin
@Immutable
data class ExtendedColors(
    val success: Color,
    val onSuccess: Color,
    val warning: Color,
    val onWarning: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        success = Color(0xFF2E7D32),
        onSuccess = Color(0xFFFFFFFF),
        warning = Color(0xFFED6C02),
        onWarning = Color(0xFFFFFFFF)
    )
}
```

原则：
- 扩展色**不要**复用 primary/secondary 的槽位，也不要把它们塞进 `ColorScheme` 的现有字段。
- 每个扩展色同样要有 `on*` 配对，且必须验过对比度。
- 深色主题下扩展色要单独给一套，不能沿用浅色值。
- 在 `05-Design-Tokens/` 里标注为「本项目品牌，非 MD3 官方」。

## 过渡与状态层

- 状态层（按下 / 悬停的叠加色）由组件内部用 `contentColor` 计算，**不要手动加 `background`**。
- 需要自定义状态反馈时，用 `interactionSource` 收集 `PressInteraction` 自行绘制，而不是叠一层 `Color(0x14000000)`。
- 跨主题切换（浅↔深）默认是瞬变。需要平滑过渡时把颜色包进 `animateColorAsState`，但注意这会增加绘制成本，长列表慎用。

## 校验

| 检查 | 方法 |
|---|---|
| 正文对比度 ≥ 4.5:1 | 取 `surface` 与 `onSurface` 计算，或截屏用取色工具 |
| 大号文字与图形 ≥ 3:1 | 同上 |
| `primaryContainer` 上的文字可读 | 实际渲染看一眼，不要只看色值 |
| 深色主题不是反相 | 对比浅色 scheme，角色色调关系应保持 |
| 关闭 dynamic color 后不塌 | 手动关掉系统主题色再跑一遍 |
| API 31 以下可用 | 模拟器 API 30 跑一遍 |

## 常见错误

1. 手写一堆色值，角色关系全靠猜。
2. 深色 scheme 直接反相浅色。
3. `onPrimary` 固定白色，遇到浅色 primary 时不可读。
4. 只测了 dynamic color 开启的情况，关掉后发现自定义配色缺字段。
5. 在组件里写 `MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)` 当背景——叠在别的表面上会算错色，应该用对应的 container 角色。
