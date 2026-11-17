package com.example.md3clock.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

/**
 * 全应用唯一主题入口。
 *
 * Expressive 接入（02-Components/expressive-components.md）：
 * - 用 MaterialExpressiveTheme 而不是 MaterialTheme：颜色槽位不变，默认形状更圆、
 *   组件内部动效改用弹簧；
 * - motionScheme 显式传 MotionScheme.expressive()（spatial damping 0.8 / stiffness 380，
 *   effects damping 1.0，不越过冲，见 01-Foundations/motion.md）；
 * - 换 expressive 主题会改变所有组件默认外观，本项目所有页面都已按 expressive 回归。
 *
 * 颜色路径：动态取色（API 31+，可选）→ 品牌浅色/深色。
 * 默认关闭动态取色：时钟的「已过/剩余」需要稳定的冷暖对比，理由见 Color.kt。
 */
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme: ColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialExpressiveTheme(
            colorScheme = colorScheme,
            motionScheme = MotionScheme.expressive(),
            typography = AppTypography,
            shapes = AppShapes,
        ) {
            // MaterialLazyList 等组件仍依赖 Surface 提供背景；深色下避免白底闪一帧
            Surface(content = content)
        }
    }
}
