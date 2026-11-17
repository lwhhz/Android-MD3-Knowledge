package com.example.md3clock.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 间距系统（01-Foundations/spacing.md）：
 * - 基线 8dp（space100），4dp 是半档；命名 token = space + (dp ÷ 8 × 100)；
 * - 页面级用 margin，组件内用 padding + gap；
 * - 200% 字号下间距不变（间距是结构，不是文字）；
 * - 用 leading/trailing，不用 left/right，RTL 自动换边。
 */
data class Spacing(
    val space0: Dp = 0.dp,
    val space25: Dp = 2.dp,
    val space50: Dp = 4.dp,
    val space75: Dp = 6.dp,
    val space100: Dp = 8.dp,
    val space125: Dp = 10.dp,
    val space150: Dp = 12.dp,
    val space200: Dp = 16.dp,
    val space250: Dp = 20.dp,
    val space300: Dp = 24.dp,
    val space400: Dp = 32.dp,
    val space500: Dp = 40.dp,
    val space600: Dp = 48.dp,
    val space700: Dp = 56.dp,
    val space800: Dp = 64.dp,
    val space900: Dp = 72.dp,
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }

/** 断点级页面边距：compact 16dp，其余 24dp（01-Foundations/layout.md）。 */
fun pageMargin(isCompact: Boolean): Dp = if (isCompact) 16.dp else 24.dp
