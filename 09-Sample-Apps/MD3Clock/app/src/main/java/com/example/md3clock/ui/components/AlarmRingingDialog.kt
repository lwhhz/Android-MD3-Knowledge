package com.example.md3clock.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.md3clock.data.Alarm
import com.example.md3clock.data.clockText
import com.example.md3clock.ui.theme.LocalSpacing
import java.time.ZonedDateTime

/**
 * 到点提醒。
 *
 * 放在应用层而不是闹钟页：闹钟在后台页面也会响，弹窗不能被页面切换挡住。
 * 两条出路（关闭 / 贪睡）满足「对话框必须可退出」的要求。
 *
 * ⚠️ 范围：这是应用前台提醒，不注册系统闹钟；见 README 的能力边界。
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AlarmRingingDialog(
    alarm: Alarm,
    now: ZonedDateTime,
    onSnooze: () -> Unit,
    onDismiss: () -> Unit,
) {
    val s = LocalSpacing.current
    AlertDialog(
        onDismissRequest = { },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        title = {
            Text(
                "闹钟响了",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(s.space100),
                modifier = Modifier.fillMaxWidth(),
            ) {
                ClockDigits(
                    text = alarm.timeText,
                    style = MaterialTheme.typography.displayLargeEmphasized,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    alarm.label,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    text = "现在是 ${now.clockText()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("关闭") }
        },
        dismissButton = {
            FilledTonalButton(
                onClick = onSnooze,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                ),
            ) {
                Text("贪睡 10 分钟")
            }
        },
    )
}
