package com.example.m3appbar.ui

import androidx.compose.material3.SnackbarDuration
import com.example.m3appbar.components.m3SnackbarDuration
import org.junit.Assert.assertEquals
import org.junit.Test

class M3SnackbarMetricsTest {

    @Test
    fun snackbarMeasurements() {
        assertEquals(48f, M3SnackbarMetrics.singleLineHeight.value, 0.01f)
        assertEquals(64f, M3SnackbarMetrics.doubleLineHeight.value, 0.01f)
        assertEquals(16f, M3SnackbarMetrics.horizontalPadding.value, 0.01f)
        assertEquals(600f, M3SnackbarMetrics.maxWidth.value, 0.01f)
        assertEquals(4f, M3SnackbarMetrics.cornerRadius.value, 0.01f)
        assertEquals(24f, M3SnackbarMetrics.iconSize.value, 0.01f)
    }

    @Test
    fun durationFollowsActionRule() {
        assertEquals(SnackbarDuration.Short, m3SnackbarDuration(null))
        assertEquals(SnackbarDuration.Indefinite, m3SnackbarDuration("Undo"))
    }
}
