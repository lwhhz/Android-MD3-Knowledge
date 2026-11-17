package com.example.m3appbar.ui

import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Test

class M3SheetMetricsTest {

    @Test
    fun sheetMeasurements() {
        assertEquals(640f, M3SheetMetrics.maxWidth.value, 0.01f)
        assertEquals(72f, M3SheetMetrics.topMargin.value, 0.01f)
        assertEquals(56f, M3SheetMetrics.sideMarginWide.value, 0.01f)
        assertEquals(28f, M3SheetMetrics.corner.value, 0.01f)
        assertEquals(22f, M3SheetMetrics.handlePadding.value, 0.01f)
        assertEquals(32f, M3SheetMetrics.handleWidth.value, 0.01f)
        assertEquals(4f, M3SheetMetrics.handleHeight.value, 0.01f)
        assertEquals(48f, M3SheetMetrics.touchTop.value, 0.01f)
    }

    @Test
    fun responsiveMarginsAndHeight() {
        assertEquals(0f, M3SheetMetrics.sideMarginForWindow(411f).value, 0.01f)
        assertEquals(56f, M3SheetMetrics.sideMarginForWindow(800f).value, 0.01f)
        assertEquals(400f, M3SheetMetrics.initialModalHeight(800f).value, 0.01f)
    }
}
