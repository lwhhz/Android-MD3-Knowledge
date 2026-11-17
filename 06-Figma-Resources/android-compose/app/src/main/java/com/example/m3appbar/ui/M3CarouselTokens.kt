package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class M3CarouselContext { MOBILE, DESKTOP }

enum class M3CarouselLayout {
    MULTI_BROWSE,
    UNCONTAINED,
    HERO,
    CENTER_ALIGNED_HERO,
    MULTI_ASPECT,
}

object M3CarouselMetrics {
    val corner: Dp = 28.dp
    val leadingPadding: Dp = 16.dp
    val trailingPadding: Dp = 16.dp
    val verticalPadding: Dp = 8.dp
    val itemGap: Dp = 8.dp
    val fullScreenGap: Dp = 16.dp
    val smallMin: Dp = 40.dp
    val smallMax: Dp = 56.dp
    val largeMobile: Dp = 280.dp
    val largeDesktop: Dp = 360.dp
    val uncontainedWidth: Dp = 240.dp
    val aspectWidths: List<Dp> = listOf(160.dp, 120.dp, 200.dp, 140.dp)
}
