package com.example.m3appbar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import android.text.format.DateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3PickerMetrics

/**
 * Modal date picker：官方 DatePickerDialog + DatePicker。
 * Type.DAY 单选；Type.RANGE / FULL_SCREEN_RANGE 使用 DateRangePicker。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3ModalDatePicker(
    show: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: (Long?) -> Unit = {},
    modifier: Modifier = Modifier,
    type: Type = Type.DAY,
) {
    if (!show) return
    if (type == Type.MONTH || type == Type.YEAR) {
        M3MonthYearDialog(
            type = type,
            onDismissRequest = onDismissRequest,
            onConfirm = onConfirm,
        )
        return
    }
    if (type == Type.FULL_SCREEN_RANGE) {
        val rangeState = rememberDateRangePickerState()
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        TextButton(onClick = onDismissRequest) { Text("Close") }
                        TextButton(onClick = {
                            onConfirm(rangeState.selectedStartDateMillis)
                            onDismissRequest()
                        }) { Text("Save") }
                    }
                    DateRangePicker(state = rangeState, modifier = Modifier.fillMaxSize())
                }
            }
        }
        return
    }
    if (type == Type.RANGE) {
        val rangeState = rememberDateRangePickerState()
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            modifier = modifier,
            confirmButton = {
                TextButton(onClick = {
                    onConfirm(rangeState.selectedStartDateMillis)
                    onDismissRequest()
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = onDismissRequest) { Text("Cancel") } },
        ) {
            DateRangePicker(state = rangeState, modifier = Modifier.height(500.dp))
        }
    } else {
        val state = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            modifier = modifier,
            confirmButton = {
                TextButton(onClick = {
                    onConfirm(state.selectedDateMillis)
                    onDismissRequest()
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = onDismissRequest) { Text("Cancel") } },
        ) {
            DatePicker(state = state)
        }
    }
}

/** Input date picker：官方 DatePickerDialog + DisplayMode.Input（纯输入，无日历）。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3InputDatePicker(
    show: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: (Long?) -> Unit = {},
    modifier: Modifier = Modifier,
    type: Type = Type.SINGLE_INPUT,
) {
    if (!show) return
    val state = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(state.selectedDateMillis)
                onDismissRequest()
            }) { Text("OK") }
        },
        dismissButton = { TextButton(onClick = onDismissRequest) { Text("Cancel") } },
    ) {
        DatePicker(state = state)
    }
}

/** Dial picker：官方 TimePicker；format 决定 12/24，orientation 决定表盘排布。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3DialPicker(
    modifier: Modifier = Modifier,
    format: Format = Format.N12_HOUR,
    orientation: Orientation = Orientation.VERTICAL,
) {
    val context = LocalContext.current
    val state = rememberTimePickerState(
        initialHour = 9,
        initialMinute = 30,
        is24Hour = DateFormat.is24HourFormat(context),
    )
    val layout = if (orientation == Orientation.HORIZONTAL) {
        TimePickerLayoutType.Horizontal
    } else {
        TimePickerLayoutType.Vertical
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Select time", style = MaterialTheme.typography.labelMedium)
            TimePicker(state = state, layoutType = layout)
        }
    }
}

/** Keyboard picker：官方 TimeInput；format 决定 12/24。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3KeyboardPicker(
    modifier: Modifier = Modifier,
    format: Format = Format.N12_HOUR,
) {
    val context = LocalContext.current
    val state = rememberTimePickerState(
        initialHour = 9,
        initialMinute = 30,
        is24Hour = DateFormat.is24HourFormat(context),
    )
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Enter time", style = MaterialTheme.typography.labelMedium)
            TimeInput(state = state)
        }
    }
}

/** Docked input date picker（desktop）：360dp 宽卡内直接显示 Input 日历。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3DockedInputDatePickerDesktop(
    modifier: Modifier = Modifier,
    type: Type = Type.DAY,
) {
    val state = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val keyboard = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) { keyboard?.hide() }
    Surface(
        modifier = modifier.width(M3PickerMetrics.dockedWidth),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        DatePicker(state = state)
    }
}
