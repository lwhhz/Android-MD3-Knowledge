package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Density：每降一档高度 -4dp（D0 40 / D1 36 / D2 32 / D3 28）。 */
enum class M3SegmentedButtonDensity(val height: Dp) {
    D0(40.dp),
    D1(36.dp),
    D2(32.dp),
    D3(28.dp),
}

object M3SegmentedButtonMetrics {
    val defaultHeight: Dp = 40.dp
    val borderWidth: Dp = 1.dp
    val horizontalPadding: Dp = 12.dp
    val elementGap: Dp = 8.dp
    val touchTarget: Dp = 48.dp
    const val minSegments: Int = 2
    const val maxSegments: Int = 5
}
