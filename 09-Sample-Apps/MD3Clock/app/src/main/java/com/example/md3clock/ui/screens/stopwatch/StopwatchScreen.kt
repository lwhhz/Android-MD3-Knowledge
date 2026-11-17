package com.example.md3clock.ui.screens.stopwatch

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.md3clock.StopwatchState
import com.example.md3clock.data.formatStopwatch
import com.example.md3clock.ui.components.ClockDigits
import com.example.md3clock.ui.components.EmptyHint
import com.example.md3clock.ui.components.PageHeader
import com.example.md3clock.ui.components.SectionHeader
import com.example.md3clock.ui.components.StatusPill
import com.example.md3clock.ui.theme.LocalSpacing
import com.example.md3clock.ui.theme.pageMargin
import java.time.ZonedDateTime

/**
 * 计时器（秒表）。
 *
 * 组件映射：
 * - Circular wavy progress（Expressive，已转正）：把「当前这一分钟」变成可读的环形进度；
 * - Emphasized + tabular 数字：百分秒连续变化时数字不跳位；
 * - Filled / tonal / outlined 三档按钮：开始-暂停是主操作，计次是次操作，复位是低强调。
 */
@Composable
fun StopwatchScreen(
    now: ZonedDateTime,
    stopwatch: StopwatchState,
    isCompact: Boolean,
    onToggle: () -> Unit,
    onLap: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val s = LocalSpacing.current
    val elapsed = stopwatch.elapsedMillis
    val main = formatStopwatch(elapsed).substringBefore('.')
    val centis = formatStopwatch(elapsed).substringAfter('.')
    val secondProgress = (elapsed % 60_000L) / 60_000f

    val laps = stopwatch.laps
    val splits = laps.mapIndexed { index, total ->
        total - (laps.getOrNull(index + 1) ?: 0L)
    }
    val fastest = splits.minOrNull()
    val slowest = if (splits.size >= 2) splits.maxOrNull() else null

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = pageMargin(isCompact)),
        verticalArrangement = Arrangement.spacedBy(s.space200),
    ) {
        Spacer(Modifier.height(s.space100))
        PageHeader(
            eyebrow = "计时器",
            title = "秒表",
            trailing = { StatusPill(if (stopwatch.running) "计时中" else "已暂停") },
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surfaceContainerLow,
        ) {
            Column(
                modifier = Modifier.padding(vertical = s.space300),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(s.space200),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CircularWavyProgressIndicator(
                        progress = { secondProgress },
                        modifier = Modifier.size(if (isCompact) 248.dp else 280.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            ClockDigits(
                                text = main,
                                style = MaterialTheme.typography.displayMediumEmphasized,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Text(
                                text = ".$centis",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                        Text(
                            text = "分 : 秒 . 百分秒",
                            style = MaterialTheme.typography.labelSmall,
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
                                stopwatch.running -> "暂停"
                                elapsed > 0 -> "继续"
                                else -> "开始"
                            },
                        )
                    }
                    FilledTonalButton(
                        onClick = onLap,
                        enabled = stopwatch.running && elapsed > 0,
                        modifier = Modifier.weight(1f).heightIn(min = 56.dp),
                    ) { Text("计次") }
                    OutlinedButton(
                        onClick = onReset,
                        enabled = elapsed > 0 || laps.isNotEmpty(),
                        modifier = Modifier.heightIn(min = 56.dp),
                    ) { Text("复位") }
                }
            }
        }

        SectionHeader(
            title = "分段",
            supporting = when {
                laps.isEmpty() -> "开始计时后可计次"
                else -> "共 ${laps.size} 段 · 由新到旧"
            },
        )

        if (laps.isEmpty()) {
            EmptyHint("计次会记录每一段用时，并标出最快与最慢。")
        } else {
            if (splits.size >= 2) {
                Row(horizontalArrangement = Arrangement.spacedBy(s.space100)) {
                    StatusPill(
                        text = "最快 ${formatStopwatch(fastest ?: 0)}",
                        container = MaterialTheme.colorScheme.tertiaryContainer,
                        content = MaterialTheme.colorScheme.onTertiaryContainer,
                    )
                    StatusPill(
                        text = "最慢 ${formatStopwatch(slowest ?: 0)}",
                        container = MaterialTheme.colorScheme.secondaryContainer,
                        content = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                }
            }
            laps.forEachIndexed { index, total ->
                val split = splits[index]
                val marker = when (split) {
                    fastest -> "最快"
                    slowest -> "最慢"
                    else -> null
                }
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = s.space200, vertical = s.space150),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "第 ${laps.size - index} 次",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.width(64.dp),
                        )
                        ClockDigits(
                            text = "+${formatStopwatch(split)}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f),
                        )
                        if (marker != null) {
                            StatusPill(
                                text = marker,
                                container = if (marker == "最快") {
                                    MaterialTheme.colorScheme.tertiaryContainer
                                } else {
                                    MaterialTheme.colorScheme.secondaryContainer
                                },
                                content = if (marker == "最快") {
                                    MaterialTheme.colorScheme.onTertiaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                },
                            )
                            Spacer(Modifier.width(s.space100))
                        }
                        ClockDigits(
                            text = formatStopwatch(total),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}
