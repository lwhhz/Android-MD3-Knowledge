package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Menu 两套色系：Standard / Vibrant。 */
enum class M3MenuTheme { STANDARD, VIBRANT }

/** Baseline menu 的三档密度。 */
enum class M3MenuDensity { COMPACT, DEFAULT, COMFORTABLE }

/** 官方 Menu 测量值（KB menus.md，2026-11-16，baseline 数字）。 */
object M3MenuMetrics {
    val minWidth: Dp = 112.dp
    val maxWidth: Dp = 280.dp
    val baselineCorner: Dp = 4.dp
    val verticalCorner: Dp = 16.dp
    val itemHeight: Dp = 48.dp
    val horizontalPadding: Dp = 12.dp
    val elementGap: Dp = 12.dp
    val dividerHeight: Dp = 1.dp
    val dividerVerticalPadding: Dp = 8.dp
    val iconSize: Dp = 24.dp
    val elevation: Dp = 3.dp
}
