package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Chip 两种视觉样式（Figma Style = Elevated / Outlined）。 */
enum class M3ChipStyle { ELEVATED, OUTLINED }

/** Chip 组排布（单品横滚或换行）。 */
enum class M3ChipGroupLayout { SINGLE_ROW_SCROLLABLE, WRAP }

/** 官方 Chip 测量值（KB chips.md，2026-11-16）。 */
object M3ChipMetrics {
    val height: Dp = 32.dp
    val cornerRadius: Dp = 8.dp
    val iconSize: Dp = 18.dp
    val paddingWithIcon: Dp = 8.dp
    val paddingWithoutIcon: Dp = 16.dp
    val elementGap: Dp = 8.dp
    val avatarSize: Dp = 24.dp
    val avatarCorner: Dp = 12.dp
    val minTouchTarget: Dp = 48.dp
    val minWidthWithAction: Dp = 88.dp
}
