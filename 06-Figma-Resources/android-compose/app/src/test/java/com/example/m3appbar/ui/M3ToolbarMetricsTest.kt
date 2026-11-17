package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3ToolbarMetricsTest {

    @Test
    fun toolbarMeasurements() {
        assertEquals(64f, M3ToolbarMetrics.height.value, 0.01f)
        assertEquals(16f, M3ToolbarMetrics.outerPadding.value, 0.01f)
        assertEquals(8f, M3ToolbarMetrics.itemGap.value, 0.01f)
        assertEquals(16f, M3ToolbarMetrics.floatingMarginHorizontal.value, 0.01f)
        assertEquals(24f, M3ToolbarMetrics.floatingMarginVertical.value, 0.01f)
        assertEquals(48f, M3ToolbarMetrics.minTouchTarget.value, 0.01f)
    }
}
