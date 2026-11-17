package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Nav bar 的 item 排布：竖排（图标在上）或横排（图标+文字一行）。 */
enum class M3NavBarItemLayout { VERTICAL, HORIZONTAL }

/** Rail 内容的对齐方式。 */
enum class M3NavRailAlignment { TOP, CENTER }

/** 官方 Navigation Bar / Rail / Drawer 测量值（KB 2026-11-16）。 */
object M3NavigationMetrics {
    // Bar
    val barHeight: Dp = 80.dp
    val barIndicatorWidth: Dp = 64.dp
    val barIndicatorHeight: Dp = 32.dp
    val barIconSize: Dp = 24.dp
    val barTopPadding: Dp = 12.dp
    val barBottomPadding: Dp = 16.dp
    val barIconLabelGap: Dp = 4.dp

    // Rail
    val railWidth: Dp = 80.dp
    val railIndicatorWidth: Dp = 56.dp
    val railIndicatorHeight: Dp = 32.dp
    val railIndicatorMargin: Dp = 12.dp
    val railItemSpacing: Dp = 12.dp
    val railItemTargetHeight: Dp = 56.dp
    val railHeaderGap: Dp = 56.dp

    // Drawer
    val drawerWidth: Dp = 360.dp
    val drawerCorner: Dp = 16.dp
    val drawerItemHeight: Dp = 56.dp
    val drawerIndicatorWidth: Dp = 336.dp
    val drawerIndicatorCorner: Dp = 28.dp
    val drawerHorizontalPadding: Dp = 28.dp
    val drawerIndicatorPadding: Dp = 12.dp
}
