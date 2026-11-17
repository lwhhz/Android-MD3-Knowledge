package com.example.m3appbar.ui

import com.example.m3appbar.components.LayoutRegions
import com.example.m3appbar.components.WindowSizeClass
import org.junit.Assert.assertEquals
import org.junit.Test

class M3KeyboardLayoutTest {

    @Test
    fun keyboardMetrics() {
        assertEquals(220f, M3KeyboardMetrics.portraitHeight.value, 0.01f)
        assertEquals(140f, M3KeyboardMetrics.landscapeHeight.value, 0.01f)
        assertEquals(300f, M3KeyboardMetrics.floatingWidth.value, 0.01f)
        assertEquals(4, M3KeyboardMetrics.columns)
    }

    @Test
    fun layoutGridColumns() {
        assertEquals(4, M3LayoutGridMetrics.columnsFor(WindowSizeClass.N1_COMPACT_0MINUS_599DP))
        assertEquals(8, M3LayoutGridMetrics.columnsFor(WindowSizeClass.N2_MEDIUM_600MINUS_839DP))
        assertEquals(12, M3LayoutGridMetrics.columnsFor(WindowSizeClass.N3_EXPANDED_840MINUS_1199P))
        assertEquals(12, M3LayoutGridMetrics.columnsFor(WindowSizeClass.N5_EXTRA_LARGE_1600PLUS_DP))
        assertEquals(16f, M3LayoutGridMetrics.marginFor(WindowSizeClass.N1_COMPACT_0MINUS_599DP).value, 0.01f)
        assertEquals(24f, M3LayoutGridMetrics.marginFor(WindowSizeClass.N2_MEDIUM_600MINUS_839DP).value, 0.01f)
        assertEquals(80f, M3LayoutGridMetrics.navWidthFor(LayoutRegions.NAVIGATION_REGION).value, 0.01f)
        assertEquals(240f, M3LayoutGridMetrics.navWidthFor(LayoutRegions.NAVIGATION_EXPANDED).value, 0.01f)
    }
}
