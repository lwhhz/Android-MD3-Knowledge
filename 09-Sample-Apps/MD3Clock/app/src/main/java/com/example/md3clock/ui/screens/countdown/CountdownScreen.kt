package com.example.md3clock.ui.screens.countdown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.md3clock.CountdownState
import com.example.md3clock.data.formatCountdown
import com.example.md3clock.ui.components.ClockDigits
import com.example.md3clock.ui.components.PageHeader
import com.example.md3clock.ui.components.SectionHeader
import com.example.md3clock.ui.components.StatusPill
import com.example.md3clock.ui.theme.LocalSpacing
import com.example.md3clock.ui.theme.pageMargin
import java.time.ZonedDateTime
import kotlin.math.roundToInt

/**
 * 倒计时。
 *
 * 组件映射：
 * - Connected button group（Expressive，取代 segmented button）：时长预设是互斥选项，
 *   组内间距 2dp、选中只改自身形状，符合 button-group.md 的「选项用 connected」；
 * - Circular wavy progress：剩余比例是真实 determinate 进度；
 * - Slider：自定义时长，1–60 分钟；
 * - Alert dialog：到时提醒，必须有关闭 + 再来一次两条出路。
 */
@Composable
fun CountdownScreen(
    now: ZonedDateTime,
    countdown: CountdownState,
    isCompact: Boolean,
    onSelectMinutes: (Int) -> Unit,
    onToggle: () -> Unit,
    onReset: () -> Unit,
    onConsumeFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val s = LocalSpacing.current
    val totalMinutes = (countdown.totalMillis / 60_000L).toInt()
    val progress = if (countdown.totalMillis <= 0) 0f else {
        (countdown.remainingMillis / countdown.totalMillis.toFloat()).coerceIn(0f, 1f)
    }
    val idle = !countdown.running && countdown.remainingMillis == countdown.totalMillis
    val endTime = now.plusSeconds((countdown.remainingMillis / 1000).coerceAtLeast(0))

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = pageMargin(isCompact)),
        verticalArrangement = Arrangement.spacedBy(s.space200),
    ) {
        Spacer(Modifier.height(s.space100))
        PageHeader(
            eyebrow = "专注",
            title = "倒计时",
            trailing = {
                StatusPill(
                    text = when {
                        countdown.running -> "运行中"
                        countdown.remainingMillis < countdown.totalMillis -> "已暂停"
                        else -> "待开始"
                    },
                )
            },
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surfaceContainerLow,
        ) {
            Column(
                modifier = Modifier.padding(vertical = s.space300),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(s.space150),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CircularWavyProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.size(if (isCompact) 248.dp else 280.dp),
                        color = if (countdown.remainingMillis == 0L) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "剩余",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        ClockDigits(
                            text = formatCountdown(countdown.remainingMillis),
                            style = MaterialTheme.typography.displayMediumEmphasized,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = if (countdown.running) "预计 ${"%02d:%02d".format(endTime.hour, endTime.minute)} 结束" else "共 $totalMinutes 分钟",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = s.space200),
                    horizontalArrangement = Arrangement.spacedBy(s.space100),
                ) {
                    Button(
                        onClick = onToggle,
                        modifier = Modifier.weight(1.4f).heightIn(min = 56.dp),
                    ) {
                        Text(
                            when {
                                countdown.running -> "暂停"
                                countdown.remainingMillis < countdown.totalMillis -> "继续"
                                else -> "开始"
                            },
                        )
                    }
                    OutlinedButton(
                        onClick = onReset,
                        enabled = countdown.remainingMillis != countdown.totalMillis || countdown.running,
                        modifier = Modifier.weight(1f).heightIn(min = 56.dp),
                    ) { Text("重置") }
                }
            }
        }

        SectionHeader(title = "时长", supporting = "预设与自定义二选一")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (idle) 1f else 0.5f),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            val presets = listOf(1, 5, 10, 25)
            presets.forEachIndexed { index, minutes ->
                ToggleButton(
                    checked = totalMinutes == minutes,
                    onCheckedChange = { if (idle) onSelectMinutes(minutes) },
                    enabled = idle,
                    modifier = Modifier.weight(1f),
                    shapes = when (index) {
                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                        presets.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                    },
                ) { Text("$minutes 分") }
            }
        }

        Column(
            modifier = Modifier.alpha(if (idle) 1f else 0.5f),
            verticalArrangement = Arrangement.spacedBy(s.space50),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    "自定义时长",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    "$totalMinutes 分钟",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Slider(
                value = totalMinutes.toFloat(),
                onValueChange = { if (idle) onSelectMinutes(it.roundToInt()) },
                valueRange = 1f..60f,
                steps = 58,
                enabled = idle,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("1 分", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("60 分", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(Modifier.height(24.dp))
    }

    if (countdown.finished) {
        AlertDialog(
            onDismissRequest = onConsumeFinished,
            title = { Text("时间到") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(s.space100)) {
                    ClockDigits(
                        text = formatCountdown(countdown.totalMillis),
                        style = MaterialTheme.typography.displaySmallEmphasized,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        "$totalMinutes 分钟的倒计时已结束。",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            },
            confirmButton = {
                Button(onClick = onConsumeFinished) { Text("完成") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onReset()
                        onToggle()
                        onConsumeFinished()
                    },
                ) { Text("再来一次") }
            },
        )
    }
}
