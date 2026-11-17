package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** 文本输入框两个容器变体（功能相同，仅视觉不同）。 */
enum class M3TextFieldStyle { FILLED, OUTLINED }

/**
 * 官方测量值（KB text-fields.md，2026-11-16 核验）。
 *
 * 官方只给这些文字值；圆角半径未给 dp，沿用 Compose 默认实现值。
 */
object M3TextFieldMetrics {
    val containerHeight: Dp = 56.dp
    val verticalPadding: Dp = 8.dp
    val horizontalPadding: Dp = 16.dp
    val horizontalPaddingWithIcon: Dp = 12.dp
    val iconTextGap: Dp = 16.dp
    val supportingTextGap: Dp = 4.dp
    val iconSize: Dp = 24.dp
}
