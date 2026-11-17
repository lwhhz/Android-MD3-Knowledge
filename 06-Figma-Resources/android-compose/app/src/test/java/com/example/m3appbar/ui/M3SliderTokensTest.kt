package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3SliderTokensTest {

    @Test
    fun officialSliderSizes() {
        assertEquals(16f, M3SliderSize.XS.trackHeight.value, 0.01f)
        assertEquals(24f, M3SliderSize.S.trackHeight.value, 0.01f)
        assertEquals(40f, M3SliderSize.M.trackHeight.value, 0.01f)
        assertEquals(56f, M3SliderSize.L.trackHeight.value, 0.01f)
        assertEquals(96f, M3SliderSize.XL.trackHeight.value, 0.01f)

        assertEquals(44f, M3SliderSize.XS.handleHeight.value, 0.01f)
        assertEquals(52f, M3SliderSize.M.handleHeight.value, 0.01f)
        assertEquals(68f, M3SliderSize.L.handleHeight.value, 0.01f)
        assertEquals(108f, M3SliderSize.XL.handleHeight.value, 0.01f)

        assertEquals(8f, M3SliderSize.XS.trackCorner.value, 0.01f)
        assertEquals(28f, M3SliderSize.XL.trackCorner.value, 0.01f)
        assertEquals(4f, M3SliderMetrics.handleWidth.value, 0.01f)
        assertEquals(44f, M3SliderMetrics.valueIndicatorHeight.value, 0.01f)
        assertEquals(48f, M3SliderMetrics.valueIndicatorWidth.value, 0.01f)
    }
}
