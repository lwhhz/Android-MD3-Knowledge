package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3SearchMetricsTest {

    @Test
    fun officialSearchMeasurements() {
        assertEquals(360f, M3SearchMetrics.minWidth.value, 0.01f)
        assertEquals(720f, M3SearchMetrics.maxWidth.value, 0.01f)
        assertEquals(56f, M3SearchMetrics.barHeight.value, 0.01f)
        assertEquals(24f, M3SearchMetrics.unfocusedPadding.value, 0.01f)
        assertEquals(12f, M3SearchMetrics.focusedPadding.value, 0.01f)
        assertEquals(30f, M3SearchMetrics.avatarSize.value, 0.01f)
        assertEquals(4f, M3SearchMetrics.iconLabelGap.value, 0.01f)
        assertEquals(240f, M3SearchMetrics.dockedMinHeight.value, 0.01f)
    }
}
