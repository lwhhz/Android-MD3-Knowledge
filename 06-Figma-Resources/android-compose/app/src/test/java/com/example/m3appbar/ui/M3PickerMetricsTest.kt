package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3PickerMetricsTest {

    @Test
    fun pickerMeasurements() {
        assertEquals(360f, M3PickerMetrics.dockedWidth.value, 0.01f)
        assertEquals(460f, M3PickerMetrics.dockedHeight.value, 0.01f)
        assertEquals(524f, M3PickerMetrics.modalHeight.value, 0.01f)
        assertEquals(40f, M3PickerMetrics.dateCell.value, 0.01f)
        assertEquals(48f, M3PickerMetrics.touchTarget.value, 0.01f)
        assertEquals(96f, M3PickerMetrics.timeSelectorWidth.value, 0.01f)
        assertEquals(80f, M3PickerMetrics.timeSelectorHeight.value, 0.01f)
        assertEquals(256f, M3PickerMetrics.dialSize.value, 0.01f)
        assertEquals(48f, M3PickerMetrics.dialHandle.value, 0.01f)
        assertEquals(96f, M3PickerMetrics.inputFieldWidth.value, 0.01f)
        assertEquals(72f, M3PickerMetrics.inputFieldHeight.value, 0.01f)
        assertEquals(24f, M3PickerMetrics.containerPadding.value, 0.01f)
    }
}
