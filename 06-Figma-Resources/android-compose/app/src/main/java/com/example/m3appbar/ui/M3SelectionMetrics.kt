package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 选择控件官方尺寸（KB selection-controls.md，2026-11-16）。
 * Checkbox 18dp 与 Radio 20dp 的不对称是官方设定，不要统一。
 */
object M3SelectionMetrics {
    val checkboxSize: Dp = 18.dp
    val checkboxCorner: Dp = 2.dp
    val radioSize: Dp = 20.dp

    val switchTrackWidth: Dp = 52.dp
    val switchTrackHeight: Dp = 32.dp
    val switchTrackBorder: Dp = 2.dp
    val switchHandleUnchecked: Dp = 16.dp
    val switchHandleChecked: Dp = 24.dp
    val switchHandlePressed: Dp = 28.dp
    val switchThumbIcon: Dp = 16.dp

    val stateLayerSize: Dp = 40.dp
    val targetSize: Dp = 48.dp
}
