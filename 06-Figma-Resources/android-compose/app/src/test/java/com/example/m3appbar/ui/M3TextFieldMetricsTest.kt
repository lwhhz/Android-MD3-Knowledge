package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3TextFieldMetricsTest {

    @Test
    fun officialMeasurementsMatchKnowledgeBase() {
        assertEquals(56f, M3TextFieldMetrics.containerHeight.value, 0.01f)
        assertEquals(8f, M3TextFieldMetrics.verticalPadding.value, 0.01f)
        assertEquals(16f, M3TextFieldMetrics.horizontalPadding.value, 0.01f)
        assertEquals(12f, M3TextFieldMetrics.horizontalPaddingWithIcon.value, 0.01f)
        assertEquals(16f, M3TextFieldMetrics.iconTextGap.value, 0.01f)
        assertEquals(4f, M3TextFieldMetrics.supportingTextGap.value, 0.01f)
        assertEquals(24f, M3TextFieldMetrics.iconSize.value, 0.01f)
    }
}
