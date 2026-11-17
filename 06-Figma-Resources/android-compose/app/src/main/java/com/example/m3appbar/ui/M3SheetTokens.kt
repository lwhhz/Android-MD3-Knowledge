package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object M3SheetMetrics {
    val maxWidth: Dp = 640.dp
    val topMargin: Dp = 72.dp
    val sideMarginWide: Dp = 56.dp
    val corner: Dp = 28.dp
    val handlePadding: Dp = 22.dp
    val handleWidth: Dp = 32.dp
    val handleHeight: Dp = 4.dp
    val touchTop: Dp = 48.dp

    fun sideMarginForWindow(widthDp: Float): Dp = if (widthDp > 640f) sideMarginWide else 0.dp

    fun initialModalHeight(screenHeightDp: Float): Dp = (screenHeightDp * 0.5f).dp
}
