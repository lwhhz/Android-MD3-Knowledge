package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3ProgressMetricsTest {

    @Test
    fun progressMeasurements() {
        assertEquals(4f, M3ProgressMetrics.defaultThickness.value, 0.01f)
        assertEquals(4f, M3ProgressMetrics.stopIndicator.value, 0.01f)
        assertEquals(40f, M3ProgressMetrics.minLinearWidth.value, 0.01f)
        assertEquals(24f, M3ProgressMetrics.circularMin.value, 0.01f)
        assertEquals(240f, M3ProgressMetrics.circularMax.value, 0.01f)
        assertEquals(48f, M3ProgressMetrics.loadingPlaceholder.value, 0.01f)
        assertEquals(38f, M3ProgressMetrics.loadingContainer.value, 0.01f)
        assertEquals(8f, M3ProgressThickness.EIGHT_DP.value.value, 0.01f)
    }
}
