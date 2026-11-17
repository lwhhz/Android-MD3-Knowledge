package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Tabs 两块：Primary / Secondary。 */
enum class M3TabStyle { PRIMARY, SECONDARY }

/** 固定均分 / 可滚动。 */
enum class M3TabConfiguration { FIXED, SCROLLABLE }

/** 仅文字 / 图标+文字 / 仅图标（仅 Primary 合法）。 */
enum class M3TabLayout { LABEL_ONLY, ICON_AND_LABEL, ICON_ONLY }

/** 官方 Tabs 测量值（KB tabs.md，2026-11-16）。 */
object M3TabMetrics {
    val heightLabelOnly: Dp = 48.dp
    val heightIconAndLabel: Dp = 64.dp
    val iconSize: Dp = 24.dp
    val dividerHeight: Dp = 1.dp
    val primaryIndicatorHeight: Dp = 3.dp
    val secondaryIndicatorHeight: Dp = 2.dp
    val indicatorInset: Dp = 2.dp
    val indicatorMinLength: Dp = 24.dp
    val iconLabelGap: Dp = 8.dp
    val badgeGap: Dp = 4.dp
    val badgeOverlap: Dp = 6.dp
    val scrollEdgePadding: Dp = 52.dp
    val smallBadgeSize: Dp = 6.dp
    val badgeTextGap: Dp = 4.dp
    val smallBadgeOffset: Dp = 6.dp
}
