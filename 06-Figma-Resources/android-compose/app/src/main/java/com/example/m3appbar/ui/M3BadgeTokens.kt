package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Badge 两种尺寸：Small（圆点）/ Large（计数或文本）。 */
enum class M3BadgeSize { SMALL, LARGE }

/** 官方 Badge 测量值（KB badge.md，2026-11-16）。 */
object M3BadgeMetrics {
    val smallSize: Dp = 6.dp
    val smallCorner: Dp = 3.dp
    val largeHeight: Dp = 16.dp
    val largeMaxWidth: Dp = 34.dp
    val largeCorner: Dp = 8.dp
    val textPadding: Dp = 4.dp
    val maxCharacters: Int = 4
    val smallOffset: Dp = 6.dp
    val largeOffsetX: Dp = 14.dp
    val largeOffsetY: Dp = 12.dp
}
