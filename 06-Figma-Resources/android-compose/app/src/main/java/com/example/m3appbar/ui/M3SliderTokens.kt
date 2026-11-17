package com.example.m3appbar.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Slider 五档尺寸（KB slider.md，官方 Measurements 逐格核对 2026-11-16）。
 *
 * 轨道高 16/24/40/56/96；handle 高 44/44/52/68/108，宽固定 4dp；
 * 轨道圆角 8/8/12/16/28；两端 inset icon 仅 M/L/XL（24/24/32）。
 */
enum class M3SliderSize(
    val trackHeight: Dp,
    val handleHeight: Dp,
    val trackCorner: Dp,
    val insetIconSize: Dp?,
) {
    XS(16.dp, 44.dp, 8.dp, null),
    S(24.dp, 44.dp, 8.dp, null),
    M(40.dp, 52.dp, 12.dp, 24.dp),
    L(56.dp, 68.dp, 16.dp, 24.dp),
    XL(96.dp, 108.dp, 28.dp, 32.dp),
}

/** 官方固定值：handle 宽 4dp、数值标签 44×48dp。 */
object M3SliderMetrics {
    val handleWidth: Dp = 4.dp
    val valueIndicatorHeight: Dp = 44.dp
    val valueIndicatorWidth: Dp = 48.dp
    val stateLayerSize: Dp = 40.dp
    val targetSize: Dp = 48.dp
}

/** 方向。当前实现仅水平（垂直属 Expressive 配置）。 */
enum class M3SliderOrientation { HORIZONTAL, VERTICAL }
