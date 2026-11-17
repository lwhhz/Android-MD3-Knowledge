package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3TabMetricsTest {

    @Test
    fun officialTabMeasurements() {
        assertEquals(48f, M3TabMetrics.heightLabelOnly.value, 0.01f)
        assertEquals(64f, M3TabMetrics.heightIconAndLabel.value, 0.01f)
        assertEquals(24f, M3TabMetrics.iconSize.value, 0.01f)
        assertEquals(1f, M3TabMetrics.dividerHeight.value, 0.01f)
        assertEquals(3f, M3TabMetrics.primaryIndicatorHeight.value, 0.01f)
        assertEquals(2f, M3TabMetrics.secondaryIndicatorHeight.value, 0.01f)
        assertEquals(2f, M3TabMetrics.indicatorInset.value, 0.01f)
        assertEquals(24f, M3TabMetrics.indicatorMinLength.value, 0.01f)
        assertEquals(8f, M3TabMetrics.iconLabelGap.value, 0.01f)
        assertEquals(52f, M3TabMetrics.scrollEdgePadding.value, 0.01f)
        assertEquals(6f, M3TabMetrics.smallBadgeSize.value, 0.01f)
        assertEquals(4f, M3TabMetrics.badgeTextGap.value, 0.01f)
        assertEquals(6f, M3TabMetrics.smallBadgeOffset.value, 0.01f)
    }
}
