package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3CarouselMetricsTest {

    @Test
    fun carouselMeasurements() {
        assertEquals(28f, M3CarouselMetrics.corner.value, 0.01f)
        assertEquals(16f, M3CarouselMetrics.leadingPadding.value, 0.01f)
        assertEquals(16f, M3CarouselMetrics.trailingPadding.value, 0.01f)
        assertEquals(8f, M3CarouselMetrics.verticalPadding.value, 0.01f)
        assertEquals(8f, M3CarouselMetrics.itemGap.value, 0.01f)
        assertEquals(16f, M3CarouselMetrics.fullScreenGap.value, 0.01f)
        assertEquals(40f, M3CarouselMetrics.smallMin.value, 0.01f)
        assertEquals(56f, M3CarouselMetrics.smallMax.value, 0.01f)
        assertEquals(280f, M3CarouselMetrics.largeMobile.value, 0.01f)
    }
}
