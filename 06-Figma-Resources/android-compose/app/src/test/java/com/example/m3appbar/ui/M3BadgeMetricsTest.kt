package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3BadgeMetricsTest {

    @Test
    fun officialBadgeMeasurements() {
        assertEquals(6f, M3BadgeMetrics.smallSize.value, 0.01f)
        assertEquals(3f, M3BadgeMetrics.smallCorner.value, 0.01f)
        assertEquals(16f, M3BadgeMetrics.largeHeight.value, 0.01f)
        assertEquals(34f, M3BadgeMetrics.largeMaxWidth.value, 0.01f)
        assertEquals(8f, M3BadgeMetrics.largeCorner.value, 0.01f)
        assertEquals(4f, M3BadgeMetrics.textPadding.value, 0.01f)
        assertEquals(4, M3BadgeMetrics.maxCharacters)
        assertEquals(6f, M3BadgeMetrics.smallOffset.value, 0.01f)
        assertEquals(14f, M3BadgeMetrics.largeOffsetX.value, 0.01f)
        assertEquals(12f, M3BadgeMetrics.largeOffsetY.value, 0.01f)
    }
}
