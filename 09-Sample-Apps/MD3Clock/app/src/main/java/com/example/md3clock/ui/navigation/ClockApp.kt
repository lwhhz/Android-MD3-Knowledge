package com.example.md3clock.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.WideNavigationRail
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.md3clock.ClockViewModel
import com.example.md3clock.ui.components.AlarmRingingDialog
import com.example.md3clock.ui.components.LocalSnackbarHostState
import com.example.md3clock.ui.screens.alarm.AlarmScreen
import com.example.md3clock.ui.screens.calendar.CalendarScreen
import com.example.md3clock.ui.screens.countdown.CountdownScreen
import com.example.md3clock.ui.screens.stopwatch.StopwatchScreen
import com.example.md3clock.ui.screens.world.WorldClockScreen
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable object AlarmRoute
@Serializable object WorldRoute
@Serializable object StopwatchRoute
@Serializable object CountdownRoute
@Serializable object CalendarRoute

private data class Dest(val route: Any, val icon: ImageVector, val label: String)

/**
 * 五个顶层目的地，正好是 M3 nav bar 的 3–5 上限。
 * compact 用 ShortNavigationBar，medium 及以上用 WideNavigationRail。
 * 顶层目的地之间用 top-level 转场：快速淡出 + 淡入（motion.md 转场模式 4），
 * 不用 lateral 滑动——那会暗示可以左右滑。
 */
private val destinations = listOf(
    Dest(AlarmRoute, Icons.Filled.Notifications, "闹钟"),
    Dest(WorldRoute, Icons.Filled.Place, "世界时钟"),
    Dest(StopwatchRoute, Icons.Filled.PlayArrow, "计时器"),
    Dest(CountdownRoute, Icons.Filled.Refresh, "倒计时"),
    Dest(CalendarRoute, Icons.Filled.DateRange, "日历"),
)

@Composable
fun ClockApp(
    vm: ClockViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val widthDp = with(LocalDensity.current) { LocalWindowInfo.current.containerSize.width.toDp() }
    val isCompact = widthDp < 600.dp

    val now by vm.now.collectAsStateWithLifecycle()
    val alarms by vm.alarms.collectAsStateWithLifecycle()
    val cities by vm.cities.collectAsStateWithLifecycle()
    val events by vm.events.collectAsStateWithLifecycle()
    val stopwatch by vm.stopwatch.collectAsStateWithLifecycle()
    val countdown by vm.countdown.collectAsStateWithLifecycle()
    val ringing by vm.ringing.collectAsStateWithLifecycle()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val current = backStackEntry?.destination

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun navigate(route: Any) {
        // 与 MD3Showcase 相同的规避：回起始目的地时禁用状态保存/恢复，
        // 否则 alpha 版 navigation 会把其他页面的 saveState 错误恢复过来。
        val toStart = route::class == AlarmRoute::class
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = !toStart }
            launchSingleTop = true
            restoreState = !toStart
        }
    }

    val navContent: @Composable (Modifier) -> Unit = { mod ->
        NavHost(
            navController = navController,
            startDestination = AlarmRoute,
            modifier = mod,
            enterTransition = { fadeIn(tween(180)) },
            exitTransition = { fadeOut(tween(120)) },
            popEnterTransition = { fadeIn(tween(180)) },
            popExitTransition = { fadeOut(tween(120)) },
        ) {
            composable<AlarmRoute> {
                AlarmScreen(
                    now = now,
                    alarms = alarms,
                    isCompact = isCompact,
                    newId = vm::newAlarmId,
                    onSave = vm::saveAlarm,
                    onToggle = vm::setAlarmEnabled,
                    onDelete = vm::deleteAlarm,
                    onRestore = vm::restoreAlarm,
                )
            }
            composable<WorldRoute> {
                WorldClockScreen(
                    now = now,
                    cities = cities,
                    isCompact = isCompact,
                    onAddCity = vm::addCity,
                    onRemoveCity = vm::removeCity,
                    onRestoreCity = vm::restoreCity,
                )
            }
            composable<StopwatchRoute> {
                StopwatchScreen(
                    now = now,
                    stopwatch = stopwatch,
                    isCompact = isCompact,
                    onToggle = vm::toggleStopwatch,
                    onLap = vm::lap,
                    onReset = vm::resetStopwatch,
                )
            }
            composable<CountdownRoute> {
                CountdownScreen(
                    now = now,
                    countdown = countdown,
                    isCompact = isCompact,
                    onSelectMinutes = vm::setCountdownMinutes,
                    onToggle = vm::toggleCountdown,
                    onReset = vm::resetCountdown,
                    onConsumeFinished = vm::consumeCountdownFinished,
                )
            }
            composable<CalendarRoute> {
                CalendarScreen(
                    now = now,
                    events = events,
                    isCompact = isCompact,
                    onAddEvent = vm::addEvent,
                    onDeleteEvent = vm::deleteEvent,
                    onRestoreEvent = vm::restoreEvent,
                )
            }
        }
    }

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        Box(Modifier.fillMaxSize()) {
            if (isCompact) {
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    bottomBar = {
                        ShortNavigationBar {
                            destinations.forEach { d ->
                                ShortNavigationBarItem(
                                    selected = current?.hasRoute(d.route::class) == true,
                                    onClick = { navigate(d.route) },
                                    icon = { Icon(d.icon, contentDescription = null) },
                                    label = { Text(d.label, maxLines = 1) },
                                )
                            }
                        }
                    },
                ) { pad ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(pad)
                            .consumeWindowInsets(pad),
                    ) {
                        navContent(Modifier.fillMaxSize())
                    }
                }
            } else {
                Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { pad ->
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(pad)
                            .consumeWindowInsets(pad),
                    ) {
                        val railState = rememberWideNavigationRailState()
                        val expanded = railState.currentValue == WideNavigationRailValue.Expanded
                        WideNavigationRail(
                            state = railState,
                            header = {
                                IconButton(onClick = { scope.launch { railState.toggle() } }) {
                                    Icon(Icons.Filled.Menu, contentDescription = "展开或收起导航")
                                }
                            },
                        ) {
                            destinations.forEach { d ->
                                WideNavigationRailItem(
                                    selected = current?.hasRoute(d.route::class) == true,
                                    onClick = { navigate(d.route) },
                                    icon = { Icon(d.icon, contentDescription = null) },
                                    label = { Text(d.label) },
                                    railExpanded = expanded,
                                )
                            }
                        }
                        navContent(Modifier.weight(1f).fillMaxSize())
                    }
                }
            }

            // 闹钟弹窗放在导航之上，任何页面都会看到
            ringing?.let { alarm ->
                AlarmRingingDialog(
                    alarm = alarm,
                    now = now,
                    onSnooze = vm::snoozeRinging,
                    onDismiss = vm::dismissRinging,
                )
            }
        }
    }
}
