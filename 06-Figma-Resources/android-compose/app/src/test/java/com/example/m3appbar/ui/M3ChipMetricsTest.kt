package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3ChipMetricsTest {

    @Test
    fun officialChipMeasurements() {
        assertEquals(32f, M3ChipMetrics.height.value, 0.01f)
        assertEquals(8f, M3ChipMetrics.cornerRadius.value, 0.01f)
        assertEquals(18f, M3ChipMetrics.iconSize.value, 0.01f)
        assertEquals(8f, M3ChipMetrics.paddingWithIcon.value, 0.01f)
        assertEquals(16f, M3ChipMetrics.paddingWithoutIcon.value, 0.01f)
        assertEquals(8f, M3ChipMetrics.elementGap.value, 0.01f)
        assertEquals(24f, M3ChipMetrics.avatarSize.value, 0.01f)
        assertEquals(12f, M3ChipMetrics.avatarCorner.value, 0.01f)
        assertEquals(48f, M3ChipMetrics.minTouchTarget.value, 0.01f)
        assertEquals(88f, M3ChipMetrics.minWidthWithAction.value, 0.01f)
    }
}
