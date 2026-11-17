package com.example.m3appbar.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 由 interaction source 推导 M3 交互状态。
 *
 * 优先级：Disabled > Pressed > Focused > Hovered > Enabled。
 * 供 Button / IconButton / FAB / Chip / ListItem 等复用。
 */
@Composable
fun rememberM3InteractionState(
    enabled: Boolean,
    interactionSource: MutableInteractionSource,
): M3InteractionState {
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    return when {
        !enabled -> M3InteractionState.DISABLED
        pressed -> M3InteractionState.PRESSED
        focused -> M3InteractionState.FOCUSED
        hovered -> M3InteractionState.HOVERED
        else -> M3InteractionState.ENABLED
    }
}

/**
 * 官方状态层：半透明覆盖，颜色取内容色（on 色），不透明度见 M3StateLayer。
 *
 * 用法：`.background(container).m3StateLayer(state, contentColor)`，需在 clip 之后。
 * Disabled 不叠层（内容自身 38% 已表达禁用）。
 */
fun Modifier.m3StateLayer(
    state: M3InteractionState,
    contentColor: Color,
): Modifier = if (state == M3InteractionState.ENABLED || state == M3InteractionState.DISABLED) {
    this
} else {
    drawBehind { drawRect(color = M3StateLayer.colorFor(contentColor, state)) }
}

/**
 * 选择控件的焦点环：3dp，画在控件 48dp 热区中心（半径默认 20dp）。
 * 官方只给「Show focus indicator」开关，未给选择控件的环尺寸，20dp 为近似。
 */
@Composable
fun Modifier.m3FocusRing(
    focused: Boolean,
    show: Boolean = true,
    ringRadius: Dp = 20.dp,
): Modifier {
    if (!focused || !show) return this
    val color = MaterialTheme.colorScheme.secondary
    return drawBehind {
        drawCircle(
            color = color,
            radius = ringRadius.toPx(),
            style = Stroke(width = 3.dp.toPx()),
        )
    }
}
