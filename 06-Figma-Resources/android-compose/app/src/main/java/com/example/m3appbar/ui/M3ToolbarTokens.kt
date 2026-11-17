package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class M3ToolbarConfiguration { DOCKED, FLOATING }
enum class M3ToolbarOrientation { HORIZONTAL, VERTICAL }
enum class M3ToolbarColor { STANDARD, VIBRANT }

object M3ToolbarMetrics {
    val height: Dp = 64.dp
    val outerPadding: Dp = 16.dp
    val itemGap: Dp = 8.dp
    val floatingMarginHorizontal: Dp = 16.dp
    val floatingMarginVertical: Dp = 24.dp
    val minTouchTarget: Dp = 48.dp
}
