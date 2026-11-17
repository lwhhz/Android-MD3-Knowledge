package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3SelectionMetricsTest {

    @Test
    fun officialSelectionControlSizes() {
        assertEquals(18f, M3SelectionMetrics.checkboxSize.value, 0.01f)
        assertEquals(2f, M3SelectionMetrics.checkboxCorner.value, 0.01f)
        assertEquals(20f, M3SelectionMetrics.radioSize.value, 0.01f)
        assertEquals(52f, M3SelectionMetrics.switchTrackWidth.value, 0.01f)
        assertEquals(32f, M3SelectionMetrics.switchTrackHeight.value, 0.01f)
        assertEquals(2f, M3SelectionMetrics.switchTrackBorder.value, 0.01f)
        assertEquals(16f, M3SelectionMetrics.switchHandleUnchecked.value, 0.01f)
        assertEquals(24f, M3SelectionMetrics.switchHandleChecked.value, 0.01f)
        assertEquals(28f, M3SelectionMetrics.switchHandlePressed.value, 0.01f)
        assertEquals(16f, M3SelectionMetrics.switchThumbIcon.value, 0.01f)
        assertEquals(40f, M3SelectionMetrics.stateLayerSize.value, 0.01f)
        assertEquals(48f, M3SelectionMetrics.targetSize.value, 0.01f)
    }
}
