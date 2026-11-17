package com.example.md3clock.ui.screens.alarm

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import com.example.md3clock.data.Alarm
import com.example.md3clock.data.clockText
import com.example.md3clock.data.dateText
import com.example.md3clock.data.humanizeDuration
import com.example.md3clock.data.nextTrigger
import com.example.md3clock.data.weekdayShort
import com.example.md3clock.ui.components.AnalogDial
import com.example.md3clock.ui.components.ClockDigits
import com.example.md3clock.ui.components.LocalSnackbarHostState
import com.example.md3clock.ui.components.PageHeader
import com.example.md3clock.ui.components.SectionHeader
import com.example.md3clock.ui.components.StatusPill
import com.example.md3clock.ui.components.showMessage
import com.example.md3clock.ui.components.showUndo
import com.example.md3clock.ui.theme.LocalSpacing
import com.example.md3clock.ui.theme.pageMargin
import java.time.ZonedDateTime

/**
 * 首页：闹钟。
 *
 * 组件映射：
 * - Split button（Expressive）：主操作「新建闹钟」+ 尾部快捷选项菜单；
 * - Switch：启用/停用；FilterChip：重复星期；
 * - Swipe to dismiss + Snackbar 撤销：删除的可恢复路径；
 * - Time picker（dial）：时间选择，24 小时制跟随系统；
 * - Emphasized 字阶 + tabular 数字：时间读数。
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AlarmScreen(
    now: ZonedDateTime,
    alarms: List<Alarm>,
    isCompact: Boolean,
    newId: () -> Long,
    onSave: (Alarm) -> Unit,
    onToggle: (Long, Boolean) -> Unit,
    onDelete: (Long) -> Unit,
    onRestore: (Alarm) -> Unit,
    modifier: Modifier = Modifier,
) {
    val s = LocalSpacing.current
    val scope = rememberCoroutineScope()
    val host = LocalSnackbarHostState.current

    var editorAlarm by remember { mutableStateOf<Alarm?>(null) }
    var showEditor by rememberSaveable { mutableStateOf(false) }
    var quickMenu by rememberSaveable { mutableStateOf(false) }

    val enabledAlarms = alarms.filter { it.enabled }
    val next = enabledAlarms
        .mapNotNull { alarm -> alarm.nextTrigger(now)?.let { alarm to it } }
        .minByOrNull { it.second }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = pageMargin(isCompact)),
        verticalArrangement = Arrangement.spacedBy(s.space200),
    ) {
        Spacer(Modifier.height(s.space100))
        PageHeader(
            eyebrow = now.dateText(),
            title = "闹钟",
        )

        // Hero：大号当前时间 + 小表盘，构成首页的时间锚点
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                ClockDigits(
                    text = now.clockText(),
                    style = MaterialTheme.typography.displayLargeEmphasized,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "%02d".format(now.second),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 4.dp, bottom = 10.dp),
                )
            }
            AnalogDial(
                time = now,
                modifier = Modifier.size(if (isCompact) 76.dp else 92.dp),
                dayProgress = (now.hour * 3600 + now.minute * 60 + now.second) / 86_400f,
            )
        }

        // 下一个闹钟：主色容器，信息层级高于列表
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ) {
            Column(
                modifier = Modifier.padding(s.space250),
                verticalArrangement = Arrangement.spacedBy(s.space75),
            ) {
                Text("下一个闹钟", style = MaterialTheme.typography.labelLarge)
                if (next == null) {
                    Text("暂无启用的闹钟", style = MaterialTheme.typography.titleLarge)
                    Text(
                        "新建或启用一个闹钟后，这里会显示倒计时。",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                } else {
                    val (alarm, trigger) = next
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ClockDigits(
                            text = alarm.timeText,
                            style = MaterialTheme.typography.displayMediumEmphasized,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Spacer(Modifier.width(s.space150))
                        StatusPill(
                            text = alarm.repeatLabel,
                            container = MaterialTheme.colorScheme.tertiaryContainer,
                            content = MaterialTheme.colorScheme.onTertiaryContainer,
                        )
                    }
                    Text(
                        text = "还有 ${humanizeDuration(trigger.toInstant().toEpochMilli() - now.toInstant().toEpochMilli())} · ${alarm.label}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

        // Split button：主操作 + 快捷选项，Expressive 新增组件
        Box {
            SplitButtonLayout(
                leadingButton = {
                    SplitButtonDefaults.LeadingButton(
                        onClick = {
                            editorAlarm = null
                            showEditor = true
                        },
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                        Text("新建闹钟")
                    }
                },
                trailingButton = {
                    SplitButtonDefaults.TrailingButton(onClick = { quickMenu = true }) {
                        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "更多新建选项")
                    }
                },
            )
            DropdownMenu(expanded = quickMenu, onDismissRequest = { quickMenu = false }) {
                listOf(10, 30, 60).forEach { minutes ->
                    val target = now.plusMinutes(minutes.toLong())
                    DropdownMenuItem(
                        text = { Text("$minutes 分钟后提醒（${"%02d:%02d".format(target.hour, target.minute)}）") },
                        onClick = {
                            quickMenu = false
                            onSave(
                                Alarm(
                                    id = newId(),
                                    hour = target.hour,
                                    minute = target.minute,
                                    label = "$minutes 分钟快速提醒",
                                    days = emptyList(),
                                    enabled = true,
                                    oneShotEpochDay = target.toLocalDate().toEpochDay(),
                                ),
                            )
                            scope.showUndo(host, "已创建 $minutes 分钟后的提醒") {}
                        },
                    )
                }
            }
        }

        SectionHeader(
            title = "全部闹钟",
            supporting = if (alarms.isEmpty()) "还没有闹钟" else "${alarms.size} 个 · ${alarms.count { it.enabled }} 个已启用",
        )

        if (alarms.isEmpty()) {
            Text(
                "点击上方「新建闹钟」创建第一个闹钟。",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            alarms.forEach { alarm ->
                // key 必须绑定闹钟 id：否则删除后按索引复用 SwipeToDismissBox 状态，
                // 下一行会继承上一行的 dismiss 进度（真实踩坑点）
                key(alarm.id) {
                    AlarmRow(
                        alarm = alarm,
                        onToggle = { onToggle(alarm.id, it) },
                        onEdit = {
                            editorAlarm = alarm
                            showEditor = true
                        },
                        onDelete = {
                            onDelete(alarm.id)
                            scope.showUndo(host, "已删除「${alarm.label}」") { onRestore(alarm) }
                        },
                    )
                }
            }
        }

        Spacer(Modifier.height(88.dp))
    }

    if (showEditor) {
        AlarmEditorSheet(
            initial = editorAlarm,
            newId = newId,
            onDismiss = { showEditor = false },
            onSave = { alarm ->
                onSave(alarm.withOneShotDate(now))
                showEditor = false
                scope.showMessage(host, "已保存 ${alarm.timeText} 的闹钟")
            },
        )
    }
}

@Composable
private fun AlarmRow(
    alarm: Alarm,
    onToggle: (Boolean) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    val s = LocalSpacing.current
    var menu by rememberSaveable { mutableStateOf(false) }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else {
                false
            }
        },
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = MaterialTheme.shapes.medium,
                    )
                    .padding(horizontal = s.space250, vertical = s.space250),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onErrorContainer,
                )
            }
        },
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            color = if (alarm.enabled) {
                MaterialTheme.colorScheme.surfaceContainerLow
            } else {
                MaterialTheme.colorScheme.surfaceContainer
            },
        ) {
            Row(
                modifier = Modifier.padding(start = s.space250, end = s.space100, top = s.space150, bottom = s.space150),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    ClockDigits(
                        text = alarm.timeText,
                        style = MaterialTheme.typography.displaySmallEmphasized,
                        color = if (alarm.enabled) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        modifier = Modifier.alpha(if (alarm.enabled) 1f else 0.6f),
                    )
                    Text(
                        text = "${alarm.label} · ${alarm.repeatLabel}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Switch(
                    checked = alarm.enabled,
                    onCheckedChange = onToggle,
                    modifier = Modifier.semantics {
                        contentDescription = "${alarm.timeText} ${alarm.label}，${if (alarm.enabled) "已启用" else "已停用"}"
                    },
                )
                Box {
                    IconButton(onClick = { menu = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "更多操作")
                    }
                    DropdownMenu(expanded = menu, onDismissRequest = { menu = false }) {
                        DropdownMenuItem(
                            text = { Text("编辑") },
                            onClick = {
                                menu = false
                                onEdit()
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("删除") },
                            onClick = {
                                menu = false
                                onDelete()
                            },
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun AlarmEditorSheet(
    initial: Alarm?,
    newId: () -> Long,
    onDismiss: () -> Unit,
    onSave: (Alarm) -> Unit,
) {
    val s = LocalSpacing.current
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val timeState = rememberTimePickerState(
        initialHour = initial?.hour ?: 7,
        initialMinute = initial?.minute ?: 30,
        is24Hour = DateFormat.is24HourFormat(context),
    )
    var label by rememberSaveable { mutableStateOf(initial?.label ?: "闹钟") }
    var days by remember { mutableStateOf(initial?.days?.toSet() ?: emptySet()) }
    var enabled by rememberSaveable { mutableStateOf(initial?.enabled ?: true) }

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = s.space300)
                .padding(bottom = s.space400),
            verticalArrangement = Arrangement.spacedBy(s.space200),
        ) {
            Text(
                text = if (initial == null) "新建闹钟" else "编辑闹钟",
                style = MaterialTheme.typography.headlineSmall,
            )

            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                TimePicker(state = timeState)
            }

            OutlinedTextField(
                value = label,
                onValueChange = { label = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("标签") },
                singleLine = true,
            )

            Text("重复", style = MaterialTheme.typography.labelLarge)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(s.space100),
                verticalArrangement = Arrangement.spacedBy(s.space100),
            ) {
                FilterChip(
                    selected = days.isEmpty(),
                    onClick = { days = emptySet() },
                    label = { Text("仅一次") },
                )
                FilterChip(
                    selected = days == setOf(1, 2, 3, 4, 5),
                    onClick = { days = setOf(1, 2, 3, 4, 5) },
                    label = { Text("工作日") },
                )
                FilterChip(
                    selected = days == (1..7).toSet(),
                    onClick = { days = (1..7).toSet() },
                    label = { Text("每天") },
                )
                FilterChip(
                    selected = days == setOf(6, 7),
                    onClick = { days = setOf(6, 7) },
                    label = { Text("周末") },
                )
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(s.space100),
                verticalArrangement = Arrangement.spacedBy(s.space100),
            ) {
                (1..7).forEach { day ->
                    FilterChip(
                        selected = day in days,
                        onClick = {
                            days = if (day in days) days - day else days + day
                        },
                        label = { Text("周${weekdayShort(day)}") },
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = enabled, onCheckedChange = { enabled = it })
                Spacer(Modifier.width(s.space150))
                Text(
                    if (enabled) "保存后立即启用" else "保存为停用状态",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(s.space100, Alignment.End),
            ) {
                TextButton(onClick = onDismiss) { Text("取消") }
                Button(
                    onClick = {
                        onSave(
                            Alarm(
                                id = initial?.id ?: newId(),
                                hour = timeState.hour,
                                minute = timeState.minute,
                                label = label.ifBlank { "闹钟" },
                                days = days.sorted(),
                                enabled = enabled,
                            ),
                        )
                    },
                ) {
                    Icon(Icons.Filled.Check, contentDescription = null)
                    Spacer(Modifier.width(s.space100))
                    Text("保存")
                }
            }
        }
    }
}
