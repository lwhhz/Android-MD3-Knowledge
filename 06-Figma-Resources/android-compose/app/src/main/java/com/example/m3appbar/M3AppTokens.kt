package com.example.m3appbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Material 3 色彩 Token。
 * 颜色值直接提取自 Figma "Material 3 Design Kit (Community)" 的 App bars 页面，
 * 对应 M3 baseline light 色板，可直接用于 Compose 或映射到 XML colors.xml。
 */
object M3Colors {
    val Primary = Color(0xFF6750A4)
    val OnPrimary = Color(0xFFFFFFFF)
    val PrimaryContainer = Color(0xFFEADDFF)
    val OnPrimaryContainer = Color(0xFF21005D)
    val SecondaryContainer = Color(0xFFE8DEF8)
    val OnSecondaryContainer = Color(0xFF4A4459)

    // 表面容器层级（Figma 中出现频率最高的中性色）
    val SurfaceContainerLowest = Color(0xFFFFFFFF)
    val SurfaceContainer = Color(0xFFF3EDF7)
    val SurfaceContainerHigh = Color(0xFFECE6F0)
    val SurfaceContainerHighest = Color(0xFFE6E0E9)

    val OnSurface = Color(0xFF1D1B20)
    val OnSurfaceVariant = Color(0xFF49454F)
    val OutlineVariant = Color(0xFFE6E0E9)

    val Shadow = Color(0xFF000000)
}

/**
 * 排版 Token。数值来自 Figma 节点 style 字段：
 * fontSize / fontWeight / lineHeightPx / letterSpacing。
 */
object M3Typography {
    val headlineMedium = TextStyle(
        fontFamily = FontFamily.Default, fontWeight = FontWeight.W400,
        fontSize = 36.sp, lineHeight = 44.sp,
    )
    val headlineSmall = TextStyle(
        fontFamily = FontFamily.Default, fontWeight = FontWeight.W400,
        fontSize = 28.sp, lineHeight = 36.sp,
    )
    val titleLarge = TextStyle(
        fontFamily = FontFamily.Default, fontWeight = FontWeight.W400,
        fontSize = 22.sp, lineHeight = 28.sp,
    )
    val titleMedium = TextStyle(
        fontFamily = FontFamily.Default, fontWeight = FontWeight.W500,
        fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.15.sp,
    )
    val bodyLarge = TextStyle(
        fontFamily = FontFamily.Default, fontWeight = FontWeight.W400,
        fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp,
    )
    val labelLarge = TextStyle(
        fontFamily = FontFamily.Default, fontWeight = FontWeight.W500,
        fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp,
    )
    val labelMedium = TextStyle(
        fontFamily = FontFamily.Default, fontWeight = FontWeight.W500,
        fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp,
    )
}
