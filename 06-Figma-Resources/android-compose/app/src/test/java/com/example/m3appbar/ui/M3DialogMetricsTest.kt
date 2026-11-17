package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3DialogMetricsTest {

    @Test
    fun officialDialogMeasurements() {
        assertEquals(28f, M3DialogMetrics.corner.value, 0.01f)
        assertEquals(280f, M3DialogMetrics.minWidth.value, 0.01f)
        assertEquals(560f, M3DialogMetrics.maxWidth.value, 0.01f)
        assertEquals(24f, M3DialogMetrics.padding.value, 0.01f)
        assertEquals(24f, M3DialogMetrics.iconSize.value, 0.01f)
        assertEquals(16f, M3DialogMetrics.iconTitleGap.value, 0.01f)
        assertEquals(16f, M3DialogMetrics.titleBodyGap.value, 0.01f)
        assertEquals(24f, M3DialogMetrics.bodyActionsGap.value, 0.01f)
        assertEquals(8f, M3DialogMetrics.buttonGap.value, 0.01f)
        assertEquals(1f, M3DialogMetrics.dividerHeight.value, 0.01f)
        assertEquals(56f, M3DialogMetrics.fullScreenHeaderHeight.value, 0.01f)
        assertEquals(320f, M3DialogMetrics.scrollMaxHeight.value, 0.01f)
    }
}
