package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3ConnectedButtonGroupMetricsTest {

    @Test
    fun connectedGroupMeasurements() {
        assertEquals(32f, M3ButtonGroupSize.XS.height.value, 0.01f)
        assertEquals(40f, M3ButtonGroupSize.S.height.value, 0.01f)
        assertEquals(56f, M3ButtonGroupSize.M.height.value, 0.01f)
        assertEquals(96f, M3ButtonGroupSize.L.height.value, 0.01f)
        assertEquals(136f, M3ButtonGroupSize.XL.height.value, 0.01f)
        assertEquals(2f, M3ConnectedButtonGroupMetrics.spaceBetween.value, 0.01f)
        assertEquals(48f, M3ConnectedButtonGroupMetrics.minWidth.value, 0.01f)
    }
}
