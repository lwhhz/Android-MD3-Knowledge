package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3SegmentedButtonMetricsTest {

    @Test
    fun segmentedButtonMeasurements() {
        assertEquals(40f, M3SegmentedButtonDensity.D0.height.value, 0.01f)
        assertEquals(36f, M3SegmentedButtonDensity.D1.height.value, 0.01f)
        assertEquals(32f, M3SegmentedButtonDensity.D2.height.value, 0.01f)
        assertEquals(28f, M3SegmentedButtonDensity.D3.height.value, 0.01f)
        assertEquals(1f, M3SegmentedButtonMetrics.borderWidth.value, 0.01f)
        assertEquals(12f, M3SegmentedButtonMetrics.horizontalPadding.value, 0.01f)
        assertEquals(8f, M3SegmentedButtonMetrics.elementGap.value, 0.01f)
        assertEquals(48f, M3SegmentedButtonMetrics.touchTarget.value, 0.01f)
        assertEquals(2, M3SegmentedButtonMetrics.minSegments)
        assertEquals(5, M3SegmentedButtonMetrics.maxSegments)
    }
}
