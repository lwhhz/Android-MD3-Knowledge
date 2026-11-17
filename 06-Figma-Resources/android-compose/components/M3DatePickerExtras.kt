package com.example.m3appbar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.ZoneOffset

/** 自定义 MONTH/YEAR 选择（官方 DatePicker 无公开月/年视图）。 */
@Composable
internal fun M3MonthYearDialog(
    type: Type,
    onDismissRequest: () -> Unit,
    onConfirm: (Long?) -> Unit,
) {
    val today = LocalDate.now()
    var year by remember { mutableIntStateOf(today.year) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                val millis = LocalDate.of(year, 1, 1)
                    .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                onConfirm(millis)
                onDismissRequest()
            }) { Text("OK") }
        },
        dismissButton = { TextButton(onClick = onDismissRequest) { Text("Cancel") } },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(onClick = { year -= 1 }) { Text("<") }
                Text(year.toString())
                TextButton(onClick = { year += 1 }) { Text(">") }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (type == Type.MONTH) {
                    for (row in 0 until 4) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            for (col in 0 until 3) {
                                val month = row * 3 + col + 1
                                Button(onClick = {
                                    val millis = LocalDate.of(year, month, 1)
                                        .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                                    onConfirm(millis)
                                    onDismissRequest()
                                }) { Text(month.toString()) }
                            }
                        }
                    }
                } else {
                    for (row in 0 until 3) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            for (col in 0 until 4) {
                                val y = year - 5 + row * 4 + col
                                Button(onClick = {
                                    onConfirm(
                                        LocalDate.of(y, 1, 1)
                                            .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                                    )
                                    onDismissRequest()
                                }) { Text(y.toString()) }
                            }
                        }
                    }
                }
            }
        },
    )
}
