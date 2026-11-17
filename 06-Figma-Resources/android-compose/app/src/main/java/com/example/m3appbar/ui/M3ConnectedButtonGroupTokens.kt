package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class M3ButtonGroupSize(val height: Dp) {
    XS(32.dp),
    S(40.dp),
    M(56.dp),
    L(96.dp),
    XL(136.dp),
}

enum class M3ButtonGroupSelection { SINGLE, MULTI, REQUIRED }

enum class M3ButtonGroupShape { ROUND, SQUARE }

object M3ConnectedButtonGroupMetrics {
    val spaceBetween: Dp = 2.dp
    val minWidth: Dp = 48.dp
    val sizes: List<M3ButtonGroupSize> = M3ButtonGroupSize.entries
}
