package com.example.md3clock.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.md3clock.ui.theme.tabular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

/** 全应用共享的 Snackbar 宿主（在 ClockApp 的 Scaffold 里提供）。 */
val LocalSnackbarHostState = staticCompositionLocalOf<SnackbarHostState> {
    error("SnackbarHostState 未提供")
}

/** 带撤销的 Snackbar：删除类操作必须提供可恢复路径（snackbar.md / swipe-to-dismiss.md）。 */
fun CoroutineScope.showUndo(
    host: SnackbarHostState,
    message: String,
    onUndo: () -> Unit,
) {
    launch {
        val result = host.showSnackbar(
            message = message,
            actionLabel = "撤销",
            withDismissAction = true,
            duration = SnackbarDuration.Short,
        )
        if (result == SnackbarResult.ActionPerformed) onUndo()
    }
}

fun CoroutineScope.showMessage(host: SnackbarHostState, message: String) {
    launch { host.showSnackbar(message, withDismissAction = true, duration = SnackbarDuration.Short) }
}

/**
 * 时间数字。
 *
 * 两个约束来自官方：
 * 1) 值频繁变化的数字用 tabular figures（typography.md「数字」）；
 * 2) 用 emphasized 同档，而不是把字号调大（type-scale 的强调规则）。
 */
@Composable
fun ClockDigits(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Text(
        text = text,
        modifier = modifier,
        style = style.tabular(),
        color = color,
        maxLines = 1,
        softWrap = false,
        textAlign = TextAlign.Center,
    )
}

/** 页面标题：eyebrow（辅助行）+ 标题 + 可选 trailing 动作。 */
@Composable
fun PageHeader(
    eyebrow: String,
    title: String,
    modifier: Modifier = Modifier,
    trailing: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = eyebrow,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
        }
        trailing?.invoke()
    }
}

/** 分区标题：标题 + 计数/说明 + 可选动作。 */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    supporting: String? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            if (supporting != null) {
                Text(
                    text = supporting,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        trailing?.invoke()
    }
}

/** 小状态药丸：日/夜、时差、最快分段等。不用纯颜色传达状态，必须带文字。 */
@Composable
fun StatusPill(
    text: String,
    modifier: Modifier = Modifier,
    container: Color = MaterialTheme.colorScheme.secondaryContainer,
    content: Color = MaterialTheme.colorScheme.onSecondaryContainer,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = container,
        contentColor = content,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
        )
    }
}

/**
 * 模拟表盘。
 *
 * 用 Canvas 而不是图片：颜色跟语义角色走，深色主题自动适配；
 * 表盘只是装饰性可视化，读屏由外层语义一次播报（clearAndSetSemantics），
 * 不让读屏用户逐个听刻度。
 */
@Composable
fun AnalogDial(
    time: ZonedDateTime,
    modifier: Modifier = Modifier,
    showSecondHand: Boolean = true,
    dayProgress: Float? = null,
    handColor: Color = MaterialTheme.colorScheme.onSurface,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    /** 传 null 表示纯装饰（如城市列表里的小表盘），不进入读屏焦点。 */
    accessibilityLabel: String? = "表盘，${time.hour} 点 ${time.minute} 分",
) {
    val outline = MaterialTheme.colorScheme.outlineVariant
    val tickColor = MaterialTheme.colorScheme.onSurfaceVariant
    val hour = time.hour % 12
    val minute = time.minute
    val second = time.second

    Canvas(
        modifier = modifier.clearAndSetSemantics {
            if (accessibilityLabel != null) contentDescription = accessibilityLabel
        },
    ) {
        val stroke = 1.dp.toPx()
        val radius = size.minDimension / 2f - stroke
        val center = Offset(size.width / 2f, size.height / 2f)
        val dialSize = Size(radius * 2f, radius * 2f)
        val dialTopLeft = Offset(center.x - radius, center.y - radius)

        drawCircle(color = outline, radius = radius, center = center, style = Stroke(stroke))

        if (dayProgress != null) {
            drawArc(
                color = accentColor.copy(alpha = 0.28f),
                startAngle = -90f,
                sweepAngle = 360f * dayProgress.coerceIn(0f, 1f),
                useCenter = false,
                topLeft = dialTopLeft,
                size = dialSize,
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round),
            )
        }

        // 60 个刻度：整点更长、更深
        for (i in 0 until 60) {
            val major = i % 5 == 0
            val outer = radius - 3.dp.toPx()
            val inner = radius - (if (major) 8.dp.toPx() else 6.dp.toPx())
            rotate(degrees = i * 6f, pivot = center) {
                drawLine(
                    color = if (major) tickColor else outline,
                    start = Offset(center.x, center.y - outer),
                    end = Offset(center.x, center.y - inner),
                    strokeWidth = if (major) 1.6.dp.toPx() else 0.8.dp.toPx(),
                    cap = StrokeCap.Round,
                )
            }
        }

        fun hand(angle: Float, length: Float, width: Dp, color: Color) {
            rotate(degrees = angle, pivot = center) {
                drawLine(
                    color = color,
                    start = center,
                    end = Offset(center.x, center.y - radius * length),
                    strokeWidth = width.toPx(),
                    cap = StrokeCap.Round,
                )
            }
        }

        hand((hour + minute / 60f) * 30f, 0.48f, 3.dp, handColor)
        hand(minute * 6f, 0.70f, 2.dp, handColor)
        if (showSecondHand) {
            hand(second * 6f, 0.78f, 1.dp, accentColor)
        }
        drawCircle(color = accentColor, radius = 3.dp.toPx(), center = center)
    }
}

/** 用于「本地日进度 / 本月进度」的百分比文案，避免各处各自取整。 */
fun percentText(progress: Float): String = "${(progress.coerceIn(0f, 1f) * 100).toInt()}%"

/** 极简的空态：一句说明 + 一个下一步动作（ui-review-checklist 的四态要求）。 */
@Composable
fun EmptyHint(text: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().padding(vertical = 32.dp), contentAlignment = Alignment.Center) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}
