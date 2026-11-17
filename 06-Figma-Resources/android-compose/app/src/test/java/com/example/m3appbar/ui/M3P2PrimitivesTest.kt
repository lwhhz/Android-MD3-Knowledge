package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3P2PrimitivesTest {

    @Test
    fun plainTooltipMeasurements() {
        assertEquals(24, M3P2Primitives.tooltipMinHeightDp)
        assertEquals(8, M3P2Primitives.tooltipPaddingDp)
    }

    @Test
    fun avatarSize() {
        assertEquals(40, M3P2Primitives.avatarSizeDp)
    }

    @Test
    fun shapeSetCount() {
        assertEquals(10, M3P2Primitives.shapeCount)
    }
}
