package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3NavigationMetricsTest {

    @Test
    fun officialNavigationMeasurements() {
        assertEquals(80f, M3NavigationMetrics.barHeight.value, 0.01f)
        assertEquals(64f, M3NavigationMetrics.barIndicatorWidth.value, 0.01f)
        assertEquals(32f, M3NavigationMetrics.barIndicatorHeight.value, 0.01f)
        assertEquals(24f, M3NavigationMetrics.barIconSize.value, 0.01f)
        assertEquals(12f, M3NavigationMetrics.barTopPadding.value, 0.01f)
        assertEquals(16f, M3NavigationMetrics.barBottomPadding.value, 0.01f)

        assertEquals(80f, M3NavigationMetrics.railWidth.value, 0.01f)
        assertEquals(56f, M3NavigationMetrics.railIndicatorWidth.value, 0.01f)
        assertEquals(32f, M3NavigationMetrics.railIndicatorHeight.value, 0.01f)
        assertEquals(12f, M3NavigationMetrics.railIndicatorMargin.value, 0.01f)
        assertEquals(56f, M3NavigationMetrics.railItemTargetHeight.value, 0.01f)

        assertEquals(360f, M3NavigationMetrics.drawerWidth.value, 0.01f)
        assertEquals(16f, M3NavigationMetrics.drawerCorner.value, 0.01f)
        assertEquals(56f, M3NavigationMetrics.drawerItemHeight.value, 0.01f)
        assertEquals(336f, M3NavigationMetrics.drawerIndicatorWidth.value, 0.01f)
        assertEquals(28f, M3NavigationMetrics.drawerIndicatorCorner.value, 0.01f)
        assertEquals(28f, M3NavigationMetrics.drawerHorizontalPadding.value, 0.01f)
    }
}
