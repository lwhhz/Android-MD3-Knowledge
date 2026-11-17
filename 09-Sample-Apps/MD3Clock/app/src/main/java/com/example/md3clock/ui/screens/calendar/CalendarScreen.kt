package com.example.md3clock.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import android.text.format.DateFormat
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.md3clock.data.CalendarEvent
import com.example.md3clock.ui.components.ClockDigits
import com.example.md3clock.ui.components.EmptyHint
import com.example.md3clock.ui.components.LocalSnackbarHostState
import com.example.md3clock.ui.components.PageHeader
import com.example.md3clock.ui.components.SectionHeader
import com.example.md3clock.ui.components.percentText
import com.example.md3clock.ui.components.showUndo
import com.example.md3clock.ui.theme.LocalSpacing
import com.example.md3clock.ui.theme.pageMargin
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

private val cnDateFormatter = DateTimeFormatter.ofPattern("M月d日 EEEE", Locale.CHINA)
private val cnMonthFormatter = DateTimeFormatter.ofPattern("yyyy年M月", Locale.CHINA)
private val weekdayHeaders = listOf("一", "二", "三", "四", "五", "六", "日")

/**
 * 日历。
 *
 * 组件映射：
 * - 月网格（40dp 格子、选中圆）按 date-picker 规格落地，今天用描边而不是填充；
 * - Date picker dialog：跳转远期日期，日历格子不是唯一入口（无障碍硬规则）；
 * - Linear wavy progress：本月已过比例，真实进度；
 * - Expanded 断点下变成两栏：左月历、右日程（list-detail 的单页变体）。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    now: java.time.ZonedDateTime,
    events: List<CalendarEvent>,
    isCompact: Boolean,
    onAddEvent: (epochDay: Long, minuteOfDay: Int, title: String) -> Unit,
    onDeleteEvent: (Long) -> Unit,
    onRestoreEvent: (CalendarEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val s = LocalSpacing.current
    val host = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()

    var monthText by rememberSaveable { mutableStateOf(YearMonth.now().toString()) }
    var selectedEpochDay by rememberSaveable { mutableLongStateOf(LocalDate.now().toEpochDay()) }
    var showAddSheet by rememberSaveable { mutableStateOf(false) }
    var showJumpPicker by rememberSaveable { mutableStateOf(false) }

    val month = remember(monthText) { YearMonth.parse(monthText) }
    val selectedDate = LocalDate.ofEpochDay(selectedEpochDay)
    val dayEvents = events.filter { it.epochDay == selectedEpochDay }.sortedBy { it.minuteOfDay }

    // 进度只描述「当前显示的月份」，翻到过去/未来月份时不能拿今天的日号硬算
    val thisMonth = YearMonth.from(now)
    val monthProgress = when {
        month == thisMonth -> now.dayOfMonth.toFloat() / month.lengthOfMonth()
        month.isBefore(thisMonth) -> 1f
        else -> 0f
    }

    fun select(date: LocalDate) {
        selectedEpochDay = date.toEpochDay()
        if (YearMonth.from(date) != month) monthText = YearMonth.from(date).toString()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = pageMargin(isCompact)),
        verticalArrangement = Arrangement.spacedBy(s.space200),
    ) {
        Spacer(Modifier.height(s.space100))
        PageHeader(
            eyebrow = "日程",
            title = "日历",
            trailing = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = { select(LocalDate.now()) }) { Text("今天") }
                    IconButton(onClick = { showJumpPicker = true }) {
                        Icon(Icons.Filled.DateRange, contentDescription = "跳转到日期")
                    }
                }
            },
        )

        if (isCompact) {
            MonthCard(
                month = month,
                selected = selectedDate,
                events = events,
                onPrev = { monthText = month.minusMonths(1).toString() },
                onNext = { monthText = month.plusMonths(1).toString() },
                onSelect = ::select,
            )
            MonthProgress(progress = monthProgress, month = month)
            EventsSection(
                date = selectedDate,
                events = dayEvents,
                onAdd = { showAddSheet = true },
                onDelete = { event ->
                    onDeleteEvent(event.id)
                    scope.showUndo(host, "已删除「${event.title}」") { onRestoreEvent(event) }
                },
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(s.space300),
            ) {
                Column(
                    modifier = Modifier.weight(1.1f),
                    verticalArrangement = Arrangement.spacedBy(s.space200),
                ) {
                    MonthCard(
                        month = month,
                        selected = selectedDate,
                        events = events,
                        onPrev = { monthText = month.minusMonths(1).toString() },
                        onNext = { monthText = month.plusMonths(1).toString() },
                        onSelect = ::select,
                    )
                    MonthProgress(progress = monthProgress, month = month)
                }
                Column(
                    modifier = Modifier.weight(0.9f),
                    verticalArrangement = Arrangement.spacedBy(s.space200),
                ) {
                    EventsSection(
                        date = selectedDate,
                        events = dayEvents,
                        onAdd = { showAddSheet = true },
                        onDelete = { event ->
                            onDeleteEvent(event.id)
                            scope.showUndo(host, "已删除「${event.title}」") { onRestoreEvent(event) }
                        },
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }

    if (showAddSheet) {
        AddEventSheet(
            date = selectedDate,
            onDismiss = { showAddSheet = false },
            onSave = { minuteOfDay, title ->
                onAddEvent(selectedEpochDay, minuteOfDay, title)
                showAddSheet = false
                scope.launch { host.showSnackbar("已添加到 ${selectedDate.format(cnDateFormatter)}") }
            },
        )
    }

    if (showJumpPicker) {
        val pickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(),
        )
        DatePickerDialog(
            onDismissRequest = { showJumpPicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        pickerState.selectedDateMillis?.let { millis ->
                            select(Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate())
                        }
                        showJumpPicker = false
                    },
                ) { Text("确定") }
            },
            dismissButton = {
                TextButton(onClick = { showJumpPicker = false }) { Text("取消") }
            },
        ) {
            DatePicker(state = pickerState)
        }
    }
}

@Composable
private fun MonthCard(
    month: YearMonth,
    selected: LocalDate,
    events: List<CalendarEvent>,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onSelect: (LocalDate) -> Unit,
) {
    val s = LocalSpacing.current
    val today = LocalDate.now()
    val firstDay = month.atDay(1)
    val leadingBlanks = firstDay.dayOfWeek.value - 1
    val totalCells = leadingBlanks + month.lengthOfMonth()
    val weeks = (totalCells + 6) / 7
    val eventDays = remember(events) { events.map { it.epochDay }.toSet() }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
    ) {
        Column(
            modifier = Modifier.padding(s.space200),
            verticalArrangement = Arrangement.spacedBy(s.space100),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(onClick = onPrev) {
                    Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "上个月")
                }
                Text(
                    text = month.format(cnMonthFormatter),
                    style = MaterialTheme.typography.titleLarge,
                )
                IconButton(onClick = onNext) {
                    Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "下个月")
                }
            }

            Row(Modifier.fillMaxWidth()) {
                weekdayHeaders.forEach { label ->
                    Text(
                        text = label,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            for (week in 0 until weeks) {
                Row(Modifier.fillMaxWidth()) {
                    for (column in 0 until 7) {
                        val cellIndex = week * 7 + column
                        val dayOfMonth = cellIndex - leadingBlanks + 1
                        if (dayOfMonth !in 1..month.lengthOfMonth()) {
                            Box(Modifier.weight(1f).aspectRatio(1f))
                        } else {
                            val date = month.atDay(dayOfMonth)
                            val isSelected = date == selected
                            val isToday = date == today
                            val hasEvent = date.toEpochDay() in eventDays
                            DayCell(
                                date = date,
                                isSelected = isSelected,
                                isToday = isToday,
                                hasEvent = hasEvent,
                                onClick = { onSelect(date) },
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(s.space150),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LegendDot(color = MaterialTheme.colorScheme.primary, label = "选中")
                StatusLegendDot(color = MaterialTheme.colorScheme.tertiary, label = "有日程")
            }
        }
    }
}

@Composable
private fun DayCell(
    date: LocalDate,
    isSelected: Boolean,
    isToday: Boolean,
    hasEvent: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick)
            .semantics {
                role = Role.Button
                contentDescription = buildString {
                    append(date.format(cnDateFormatter))
                    if (isToday) append("，今天")
                    if (hasEvent) append("，有日程")
                    if (isSelected) append("，已选中")
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .then(
                        when {
                            isSelected -> Modifier.background(MaterialTheme.colorScheme.primary, CircleShape)
                            isToday -> Modifier.border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            else -> Modifier
                        },
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = date.dayOfMonth.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isToday || isSelected) FontWeight.Medium else FontWeight.Normal,
                    color = when {
                        isSelected -> MaterialTheme.colorScheme.onPrimary
                        isToday -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                )
            }
            Box(
                modifier = Modifier
                    .padding(top = 3.dp)
                    .size(4.dp)
                    .background(
                        color = if (hasEvent) MaterialTheme.colorScheme.tertiary else androidx.compose.ui.graphics.Color.Transparent,
                        shape = CircleShape,
                    ),
            )
        }
    }
}

@Composable
private fun MonthProgress(progress: Float, month: YearMonth) {
    val s = LocalSpacing.current
    Column(verticalArrangement = Arrangement.spacedBy(s.space50)) {
        LinearWavyProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                "${month.monthValue} 月已过 ${percentText(progress)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                "共 ${month.lengthOfMonth()} 天",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun EventsSection(
    date: LocalDate,
    events: List<CalendarEvent>,
    onAdd: () -> Unit,
    onDelete: (CalendarEvent) -> Unit,
) {
    val s = LocalSpacing.current
    Column(verticalArrangement = Arrangement.spacedBy(s.space150)) {
        SectionHeader(
            title = date.format(cnDateFormatter),
            supporting = if (events.isEmpty()) "暂无日程" else "${events.size} 项日程",
            trailing = {
                FilledTonalButton(onClick = onAdd) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                    Spacer(Modifier.width(s.space100))
                    Text("添加")
                }
            },
        )

        if (events.isEmpty()) {
            EmptyHint("这一天还没有安排，添加一个日程吧。")
        } else {
            events.forEach { event ->
                key(event.id) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                ) {
                    Row(
                        modifier = Modifier.padding(start = s.space200, end = s.space50, top = s.space150, bottom = s.space150),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.width(64.dp)) {
                            ClockDigits(
                                text = event.timeText,
                                style = MaterialTheme.typography.titleMediumEmphasized,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Text(
                                "${event.hour.toString().padStart(2, '0')} 时",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Text(
                            text = event.title,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f),
                        )
                        IconButton(onClick = { onDelete(event) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "删除 ${event.title}")
                        }
                    }
                }
                }
            }
        }
    }
}

@Composable
private fun LegendDot(color: androidx.compose.ui.graphics.Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(8.dp).background(color, CircleShape))
        Spacer(Modifier.width(6.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun StatusLegendDot(color: androidx.compose.ui.graphics.Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(4.dp).background(color, CircleShape))
        Spacer(Modifier.width(6.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddEventSheet(
    date: LocalDate,
    onDismiss: () -> Unit,
    onSave: (minuteOfDay: Int, title: String) -> Unit,
) {
    val s = LocalSpacing.current
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    // 24 小时制由系统设置决定，不在组件里硬编码（date-time-picker.md 硬规则）
    val timeState = rememberTimePickerState(
        initialHour = 9,
        initialMinute = 0,
        is24Hour = DateFormat.is24HourFormat(context),
    )
    var title by rememberSaveable { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = s.space300)
                .padding(bottom = s.space400),
            verticalArrangement = Arrangement.spacedBy(s.space200),
        ) {
            Text("添加日程", style = MaterialTheme.typography.headlineSmall)
            Text(
                date.format(cnDateFormatter),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("日程标题") },
                singleLine = true,
            )
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                TimePicker(state = timeState)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(s.space100, Alignment.End),
            ) {
                TextButton(onClick = onDismiss) { Text("取消") }
                Button(
                    onClick = { onSave(timeState.hour * 60 + timeState.minute, title.ifBlank { "未命名日程" }) },
                ) {
                    Icon(Icons.Filled.Check, contentDescription = null)
                    Spacer(Modifier.width(s.space100))
                    Text("保存")
                }
            }
        }
    }
}
