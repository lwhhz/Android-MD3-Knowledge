package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** 进度指示类型：Flat / Wavy（Expressive）。 */
enum class M3ProgressType { FLAT, WAVY }

/** 厚度：固定 4dp 或加粗 8dp（官方默认 4dp，加粗仅示例）。 */
enum class M3ProgressThickness(val value: Dp) {
    FOUR_DP(4.dp),
    EIGHT_DP(8.dp),
}

/** 官方进度/加载测量值（KB 2026-11-16）。 */
object M3ProgressMetrics {
    val defaultThickness: Dp = 4.dp
    val stopIndicator: Dp = 4.dp
    val minLinearWidth: Dp = 40.dp
    val circularMin: Dp = 24.dp
    val circularMax: Dp = 240.dp
    val loadingPlaceholder: Dp = 48.dp
    val loadingContainer: Dp = 38.dp
    val loadingMin: Dp = 24.dp
    val loadingMax: Dp = 240.dp
}
