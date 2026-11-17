package com.example.md3clock.ui.screens.world

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.md3clock.data.WorldCity
import com.example.md3clock.data.cityCatalog
import com.example.md3clock.data.clockText
import com.example.md3clock.data.clockWithSeconds
import com.example.md3clock.data.dateText
import com.example.md3clock.data.dayOffsetVsLocal
import com.example.md3clock.data.isDaytime
import com.example.md3clock.data.offsetLabel
import com.example.md3clock.ui.components.AnalogDial
import com.example.md3clock.ui.components.ClockDigits
import com.example.md3clock.ui.components.EmptyHint
import com.example.md3clock.ui.components.LocalSnackbarHostState
import com.example.md3clock.ui.components.PageHeader
import com.example.md3clock.ui.components.SectionHeader
import com.example.md3clock.ui.components.StatusPill
import com.example.md3clock.ui.components.percentText
import com.example.md3clock.ui.components.showMessage
import com.example.md3clock.ui.components.showUndo
import com.example.md3clock.ui.theme.LocalSpacing
import com.example.md3clock.ui.theme.pageMargin
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * 世界时钟。
 *
 * 组件映射：
 * - Linear wavy progress（Expressive，已转正）：本地一天的时间进度，是真实进度而非装饰；
 * - Loading indicator（contained，仍实验）：校准动作的 200ms–5s 等待，符合官方「短等待」区间；
 * - FAB menu（Expressive）：添加城市 / 排序切换，收起时是普通 FAB；
 * - Analog dial：每城的昼夜与时刻一眼可读，颜色随主题角色变化。
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WorldClockScreen(
    now: ZonedDateTime,
    cities: List<WorldCity>,
    isCompact: Boolean,
    onAddCity: (WorldCity) -> Unit,
    onRemoveCity: (String) -> Unit,
    onRestoreCity: (WorldCity, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val s = LocalSpacing.current
    val host = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()

    var showAddSheet by rememberSaveable { mutableStateOf(false) }
    var sortByTime by rememberSaveable { mutableStateOf(false) }
    var fabExpanded by rememberSaveable { mutableStateOf(false) }
    var syncing by rememberSaveable { mutableStateOf(false) }

    val localZone = ZoneId.systemDefault()
    val dayProgress = (now.hour * 3600 + now.minute * 60 + now.second) / 86_400f
    val minutesToMidnight = 24 * 60 - (now.hour * 60 + now.minute)

    val sortedCities = remember(cities, sortByTime) {
        if (sortByTime) {
            cities.sortedBy { it.zone.rules.getOffset(Instant.now()).totalSeconds }
        } else {
            cities
        }
    }

    LaunchedEffect(syncing) {
        if (syncing) {
            delay(1200)
            syncing = false
            scope.showMessage(host, "时间已校准（演示：未连接网络时间源）")
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = pageMargin(isCompact)),
            verticalArrangement = Arrangement.spacedBy(s.space200),
        ) {
            Spacer(Modifier.height(s.space100))
            PageHeader(
                eyebrow = now.dateText(),
                title = "世界时钟",
                trailing = {
                    if (syncing) {
                        ContainedLoadingIndicator(
                            modifier = Modifier
                                .size(48.dp)
                                .semantics { contentDescription = "正在校准时间" },
                        )
                    } else {
                        IconButton(onClick = { syncing = true }) {
                            Icon(Icons.Filled.Refresh, contentDescription = "校准时间")
                        }
                    }
                },
            )

            // 本地时间英雄区
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.surfaceContainerLow,
            ) {
                Column(
                    modifier = Modifier.padding(s.space250),
                    verticalArrangement = Arrangement.spacedBy(s.space150),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                "本地时间 · ${localZone.offsetLabel()}",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            ClockDigits(
                                text = now.clockText(),
                                style = MaterialTheme.typography.displayLargeEmphasized,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Text(
                                text = "秒 ${"%02d".format(now.second)} · ${localZone.id.substringAfterLast('/')}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        AnalogDial(
                            time = now,
                            modifier = Modifier.size(if (isCompact) 84.dp else 104.dp),
                            dayProgress = dayProgress,
                        )
                    }

                    LinearWavyProgressIndicator(
                        progress = { dayProgress },
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            "今天已过 ${percentText(dayProgress)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            "距明天还有 ${minutesToMidnight / 60} 小时 ${minutesToMidnight % 60} 分",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            SectionHeader(
                title = "世界时钟",
                supporting = if (cities.isEmpty()) "还没有添加城市" else "${cities.size} 个城市 · 实时更新",
                trailing = {
                    TextButton(onClick = { sortByTime = !sortByTime }) {
                        Text(if (sortByTime) "按添加顺序" else "按时间排序")
                    }
                },
            )

            if (cities.isEmpty()) {
                EmptyHint("点击右下角 + 添加第一个城市。")
            } else {
                sortedCities.forEach { city ->
                    key(city.zoneId) {
                        CityRow(
                            city = city,
                            now = now,
                            isLocal = city.zoneId == localZone.id,
                            onRemove = {
                                val index = cities.indexOf(city)
                                onRemoveCity(city.zoneId)
                                scope.showUndo(host, "已移除 ${city.name}") { onRestoreCity(city, index) }
                            },
                        )
                    }
                }
            }

            Spacer(Modifier.height(96.dp))
        }

        // FAB menu（Expressive）：收起态是 FAB，展开是动作菜单
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = s.space200, bottom = s.space200),
        ) {
            FloatingActionButtonMenu(
                expanded = fabExpanded,
                button = {
                    ToggleFloatingActionButton(
                        checked = fabExpanded,
                        onCheckedChange = { fabExpanded = it },
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "添加城市或排序")
                    }
                },
            ) {
                FloatingActionButtonMenuItem(
                    onClick = {
                        fabExpanded = false
                        showAddSheet = true
                    },
                    text = { Text("添加城市") },
                    icon = { Icon(Icons.Filled.Search, contentDescription = null) },
                )
                FloatingActionButtonMenuItem(
                    onClick = {
                        fabExpanded = false
                        sortByTime = !sortByTime
                        scope.showMessage(host, if (sortByTime) "已按当地时间排序" else "已恢复添加顺序")
                    },
                    text = { Text(if (sortByTime) "按添加顺序" else "按时间排序") },
                    icon = { Icon(Icons.Filled.Star, contentDescription = null) },
                )
            }
        }
    }

    if (showAddSheet) {
        AddCitySheet(
            existing = cities.map { it.zoneId }.toSet(),
            now = now,
            onDismiss = { showAddSheet = false },
            onAdd = { city ->
                onAddCity(city)
                showAddSheet = false
                scope.showMessage(host, "已添加 ${city.name}")
            },
        )
    }
}

@Composable
private fun CityRow(
    city: WorldCity,
    now: ZonedDateTime,
    isLocal: Boolean,
    onRemove: () -> Unit,
) {
    val s = LocalSpacing.current
    val zoneTime = now.withZoneSameInstant(city.zone)
    val offsetDays = city.zone.dayOffsetVsLocal()

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
    ) {
        Row(
            modifier = Modifier.padding(start = s.space200, end = s.space100, top = s.space150, bottom = s.space150),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnalogDial(
                time = zoneTime,
                modifier = Modifier.size(56.dp),
                showSecondHand = false,
                accessibilityLabel = null,
                handColor = MaterialTheme.colorScheme.onSurfaceVariant,
                accentColor = if (city.zone.isDaytime()) {
                    MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.primary
                },
            )
            Spacer(Modifier.width(s.space150))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = city.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (isLocal) {
                        Spacer(Modifier.width(s.space100))
                        StatusPill("本地")
                    }
                }
                Text(
                    text = "${city.country} · ${city.zone.offsetLabel()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                ClockDigits(
                    text = zoneTime.clockText(),
                    style = MaterialTheme.typography.titleLargeEmphasized,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = when {
                        offsetDays > 0 -> "比本地早 $offsetDays 天"
                        offsetDays < 0 -> "比本地晚 ${-offsetDays} 天"
                        else -> if (city.zone.isDaytime()) "白天" else "夜间"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Filled.Close, contentDescription = "移除 ${city.name}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddCitySheet(
    existing: Set<String>,
    now: ZonedDateTime,
    onDismiss: () -> Unit,
    onAdd: (WorldCity) -> Unit,
) {
    val s = LocalSpacing.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var query by rememberSaveable { mutableStateOf("") }
    val candidates = cityCatalog
        .filterNot { it.zoneId in existing }
        .filter { query.isBlank() || it.name.contains(query, ignoreCase = true) || it.country.contains(query, ignoreCase = true) || it.zoneId.contains(query, ignoreCase = true) }

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = s.space300)
                .padding(bottom = s.space400),
            verticalArrangement = Arrangement.spacedBy(s.space150),
        ) {
            Text("添加城市", style = MaterialTheme.typography.headlineSmall)
            Text(
                "共 ${cityCatalog.size} 个城市可选，时间按 IANA 时区实时换算。",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                label = { Text("搜索城市或国家") },
                singleLine = true,
            )
            if (candidates.isEmpty()) {
                EmptyHint("没有匹配的城市，换个关键词试试。")
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(s.space100)) {
                    candidates.forEach { city ->
                        val time = now.withZoneSameInstant(city.zone)
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.small,
                            color = MaterialTheme.colorScheme.surfaceContainerLow,
                            onClick = { onAdd(city) },
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = s.space200, vertical = s.space150),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(city.name, style = MaterialTheme.typography.titleSmall)
                                    Text(
                                        "${city.country} · ${city.zone.offsetLabel()}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                                ClockDigits(
                                    text = time.clockWithSeconds(),
                                    style = MaterialTheme.typography.titleMediumEmphasized,
                                )
                                Spacer(Modifier.width(s.space100))
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
