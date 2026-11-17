package com.example.m3appbar.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m3appbar.ui.M3SliderMetrics
import com.example.m3appbar.ui.M3SliderOrientation
import com.example.m3appbar.ui.M3SliderSize
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Slider 核心：标准 / centered / range 共用，自定义绘制以对齐 Expressive 尺寸。
 *
 * 尺寸：轨道 16/24/40/56/96；handle 高 44/44/52/68/108、宽 4dp；按下收窄到 3dp。
 * 颜色：active primary、inactive secondaryContainer、value indicator inverseSurface。
 * 未实现：垂直方向、inset icon 的零值换图标。
 */
@Composable
private fun M3SliderCore(
    values: List<Float>,
    valueRange: ClosedFloatingPointRange<Float>,
    onValuesChange: (List<Float>) -> Unit,
    centered: Boolean,
    size: M3SliderSize,
    enabled: Boolean,
    showValueIndicator: Boolean,
    showStops: Boolean,
    steps: Int,
    showIcon: Boolean,
    icon: ImageVector?,
    modifier: Modifier,
    onValueChangeFinished: () -> Unit,
) {
    val indicatorSpace = if (showValueIndicator) 52.dp else 0.dp
    val density = LocalDensity.current
    var interacting by remember { mutableStateOf(false) }
    var activeHandle by remember { mutableIntStateOf(0) }
    val latestValues by rememberUpdatedState(values)
    val latestRange by rememberUpdatedState(valueRange)
    val latestOnChange by rememberUpdatedState(onValuesChange)
    val latestFinished by rememberUpdatedState(onValueChangeFinished)

    fun snap(raw: Float): Float {
        val span = latestRange.endInclusive - latestRange.start
        if (steps <= 0 || span <= 0f) return raw.coerceIn(latestRange.start, latestRange.endInclusive)
        val step = span / (steps + 1)
        val idx = ((raw - latestRange.start) / step).roundToInt()
        return (latestRange.start + idx * step).coerceIn(latestRange.start, latestRange.endInclusive)
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(indicatorSpace + size.handleHeight),
    ) {
        val trackWidth = with(density) { maxWidth.toPx() }
        val gestureModifier = Modifier
            .fillMaxSize()
            .semantics {
                if (latestValues.size == 2) {
                    stateDescription = formatValue(latestValues[0]) + " - " + formatValue(latestValues[1])
                } else {
                    progressBarRangeInfo = ProgressBarRangeInfo(latestValues.first(), latestRange)
                }
            }
            .pointerInput(enabled, trackWidth, size, steps) {
                if (!enabled || trackWidth <= 0f) return@pointerInput
                fun valueAt(x: Float): Float {
                    val fraction = (x / trackWidth).coerceIn(0f, 1f)
                    val span = latestRange.endInclusive - latestRange.start
                    return snap(latestRange.start + fraction * span)
                }
                fun xAt(v: Float): Float {
                    val span = latestRange.endInclusive - latestRange.start
                    val fraction = if (span <= 0f) 0f else ((v - latestRange.start) / span).coerceIn(0f, 1f)
                    return fraction * trackWidth
                }
                fun nearest(x: Float): Int {
                    if (latestValues.size < 2) return 0
                    return if (abs(x - xAt(latestValues[0])) <= abs(x - xAt(latestValues[1]))) 0 else 1
                }
                fun apply(handle: Int, x: Float) {
                    val updated = latestValues.toMutableList()
                    updated[handle] = valueAt(x)
                    if (updated.size > 1) updated.sort()
                    latestOnChange(updated)
                }
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    activeHandle = nearest(down.position.x)
                    interacting = true
                    apply(activeHandle, down.position.x)
                    down.consume()
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull { it.id == down.id } ?: break
                        if (!change.pressed) {
                            change.consume()
                            break
                        }
                        apply(activeHandle, change.position.x)
                        change.consume()
                    }
                    interacting = false
                    latestFinished()
                }
            }

        val measurer = rememberTextMeasurer()
        val scheme = MaterialTheme.colorScheme
        val trackColor = if (enabled) scheme.secondaryContainer else scheme.onSurface.copy(alpha = 0.12f)
        val activeColor = if (enabled) scheme.primary else scheme.onSurface.copy(alpha = 0.38f)
        val handleColor = if (enabled) scheme.primary else scheme.onSurface.copy(alpha = 0.38f)
        val stopColor = if (enabled) scheme.onSecondaryContainer else scheme.onSurface.copy(alpha = 0.38f)

        Canvas(modifier = gestureModifier) {
            drawSlider(
                values = latestValues,
                valueRange = latestRange,
                centered = centered,
                steps = steps,
                showStops = showStops,
                showValueIndicator = showValueIndicator,
                interacting = interacting,
                activeHandle = activeHandle,
                trackColor = trackColor,
                activeColor = activeColor,
                handleColor = handleColor,
                stopColor = stopColor,
                indicatorContainer = scheme.inverseSurface,
                indicatorContent = scheme.inverseOnSurface,
                measurer = measurer,
                size = size,
                indicatorSpacePx = indicatorSpace.toPx(),
            )
        }

        if (showIcon && icon != null && size.insetIconSize != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp, top = indicatorSpace)
                    .size(size.insetIconSize),
                tint = if (enabled) scheme.onSurfaceVariant else scheme.onSurface.copy(alpha = 0.38f),
            )
        }
    }
}

private fun DrawScope.drawSlider(
    values: List<Float>,
    valueRange: ClosedFloatingPointRange<Float>,
    centered: Boolean,
    steps: Int,
    showStops: Boolean,
    showValueIndicator: Boolean,
    interacting: Boolean,
    activeHandle: Int,
    trackColor: Color,
    activeColor: Color,
    handleColor: Color,
    stopColor: Color,
    indicatorContainer: Color,
    indicatorContent: Color,
    measurer: TextMeasurer,
    size: M3SliderSize,
    indicatorSpacePx: Float,
) {
    val span = valueRange.endInclusive - valueRange.start
    fun xFor(v: Float): Float = if (span <= 0f) 0f else ((v - valueRange.start) / span).coerceIn(0f, 1f) * this.size.width

    val trackHeight = size.trackHeight.toPx()
    val handleHeight = size.handleHeight.toPx()
    val corner = size.trackCorner.toPx()
    val centerY = indicatorSpacePx + handleHeight / 2f
    val currentHandleWidth = (if (interacting) 3.dp else M3SliderMetrics.handleWidth).toPx()

    drawRoundRect(
        color = trackColor,
        topLeft = Offset(0f, centerY - trackHeight / 2f),
        size = Size(this.size.width, trackHeight),
        cornerRadius = CornerRadius(corner),
    )

    val positions = values.map { xFor(it) }
    if (values.size == 2) {
        drawRoundRect(
            color = activeColor,
            topLeft = Offset(positions[0], centerY - trackHeight / 2f),
            size = Size((positions[1] - positions[0]).coerceAtLeast(0f), trackHeight),
            cornerRadius = CornerRadius(corner),
        )
    } else {
        val start = if (centered) this.size.width / 2f else 0f
        drawRoundRect(
            color = activeColor,
            topLeft = Offset(minOf(start, positions[0]), centerY - trackHeight / 2f),
            size = Size(abs(positions[0] - start), trackHeight),
            cornerRadius = CornerRadius(corner),
        )
    }

    if (showStops && steps > 0) {
        val stopInset = 4.dp.toPx()
        for (i in 0..(steps + 1)) {
            val v = valueRange.start + (span * i / (steps + 1))
            val x = xFor(v).coerceIn(stopInset, this.size.width - stopInset)
            drawCircle(color = stopColor, radius = 2.dp.toPx(), center = Offset(x, centerY))
        }
    }

    positions.forEachIndexed { index, x ->
        val width = if (index == activeHandle && interacting) currentHandleWidth else M3SliderMetrics.handleWidth.toPx()
        drawRoundRect(
            color = handleColor,
            topLeft = Offset(x - width / 2f, centerY - handleHeight / 2f),
            size = Size(width, handleHeight),
            cornerRadius = CornerRadius(width / 2f),
        )
    }

    if (interacting && showValueIndicator) {
        val text = formatValue(values[activeHandle])
        val bubbleWidth = M3SliderMetrics.valueIndicatorWidth.toPx()
        val bubbleHeight = M3SliderMetrics.valueIndicatorHeight.toPx()
        val left = (positions[activeHandle] - bubbleWidth / 2f).coerceIn(0f, this.size.width - bubbleWidth)
        val layout = measurer.measure(
            text = text,
            style = TextStyle(color = indicatorContent, fontSize = 14.sp, fontWeight = FontWeight.Medium),
        )
        drawRoundRect(
            color = indicatorContainer,
            topLeft = Offset(left, 0f),
            size = Size(bubbleWidth, bubbleHeight),
            cornerRadius = CornerRadius(8.dp.toPx()),
        )
        drawText(
            textLayoutResult = layout,
            topLeft = Offset(
                left + (bubbleWidth - layout.size.width) / 2f,
                (bubbleHeight - layout.size.height) / 2f,
            ),
        )
    }
}

private fun formatValue(v: Float): String = v.roundToInt().toString()

@Composable
fun M3StandardSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..100f,
    steps: Int = 0,
    enabled: Boolean = true,
    size: M3SliderSize = M3SliderSize.XS,
    showValueIndicator: Boolean = true,
    showStops: Boolean = false,
    showIcon: Boolean = false,
    icon: ImageVector? = null,
    orientation: M3SliderOrientation = M3SliderOrientation.HORIZONTAL,
    onValueChangeFinished: () -> Unit = {},
) {
    M3SliderCore(
        values = listOf(value),
        valueRange = valueRange,
        onValuesChange = { onValueChange(it[0]) },
        centered = false,
        size = size,
        enabled = enabled,
        showValueIndicator = showValueIndicator,
        showStops = showStops,
        steps = steps,
        showIcon = showIcon,
        icon = icon,
        modifier = modifier,
        onValueChangeFinished = onValueChangeFinished,
    )
}

@Composable
fun M3CenteredSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = -50f..50f,
    steps: Int = 0,
    enabled: Boolean = true,
    size: M3SliderSize = M3SliderSize.XS,
    showValueIndicator: Boolean = true,
    showStops: Boolean = false,
    orientation: M3SliderOrientation = M3SliderOrientation.HORIZONTAL,
    onValueChangeFinished: () -> Unit = {},
) {
    M3SliderCore(
        values = listOf(value),
        valueRange = valueRange,
        onValuesChange = { onValueChange(it[0]) },
        centered = true,
        size = size,
        enabled = enabled,
        showValueIndicator = showValueIndicator,
        showStops = showStops,
        steps = steps,
        showIcon = false,
        icon = null,
        modifier = modifier,
        onValueChangeFinished = onValueChangeFinished,
    )
}

@Composable
fun M3RangeSlider(
    values: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..100f,
    steps: Int = 0,
    enabled: Boolean = true,
    size: M3SliderSize = M3SliderSize.XS,
    showValueIndicator: Boolean = true,
    showStops: Boolean = false,
    orientation: M3SliderOrientation = M3SliderOrientation.HORIZONTAL,
    onValueChangeFinished: () -> Unit = {},
) {
    M3SliderCore(
        values = listOf(values.start, values.endInclusive),
        valueRange = valueRange,
        onValuesChange = { onValueChange(it[0]..it[1]) },
        centered = false,
        size = size,
        enabled = enabled,
        showValueIndicator = showValueIndicator,
        showStops = showStops,
        steps = steps,
        showIcon = false,
        icon = null,
        modifier = modifier,
        onValueChangeFinished = onValueChangeFinished,
    )
}
