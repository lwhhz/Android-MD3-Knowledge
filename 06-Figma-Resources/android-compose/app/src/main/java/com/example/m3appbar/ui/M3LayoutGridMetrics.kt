package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.m3appbar.components.LayoutRegions
import com.example.m3appbar.components.WindowSizeClass

object M3LayoutGridMetrics {
    fun columnsFor(size: WindowSizeClass): Int = when (size) {
        WindowSizeClass.N1_COMPACT_0MINUS_599DP -> 4
        WindowSizeClass.N2_MEDIUM_600MINUS_839DP -> 8
        else -> 12
    }

    fun marginFor(size: WindowSizeClass): Dp =
        if (size == WindowSizeClass.N1_COMPACT_0MINUS_599DP) 16.dp else 24.dp

    fun navWidthFor(regions: LayoutRegions): Dp = when (regions) {
        LayoutRegions.NAVIGATION_EXPANDED -> 240.dp
        LayoutRegions.NAVIGATION_REGION -> 80.dp
        LayoutRegions.NONE -> 0.dp
    }
}
