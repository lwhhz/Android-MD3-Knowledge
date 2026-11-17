package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3MenuMetricsTest {

    @Test
    fun officialMenuMeasurements() {
        assertEquals(112f, M3MenuMetrics.minWidth.value, 0.01f)
        assertEquals(280f, M3MenuMetrics.maxWidth.value, 0.01f)
        assertEquals(4f, M3MenuMetrics.baselineCorner.value, 0.01f)
        assertEquals(16f, M3MenuMetrics.verticalCorner.value, 0.01f)
        assertEquals(48f, M3MenuMetrics.itemHeight.value, 0.01f)
        assertEquals(12f, M3MenuMetrics.horizontalPadding.value, 0.01f)
        assertEquals(12f, M3MenuMetrics.elementGap.value, 0.01f)
        assertEquals(1f, M3MenuMetrics.dividerHeight.value, 0.01f)
        assertEquals(8f, M3MenuMetrics.dividerVerticalPadding.value, 0.01f)
        assertEquals(24f, M3MenuMetrics.iconSize.value, 0.01f)
    }
}
