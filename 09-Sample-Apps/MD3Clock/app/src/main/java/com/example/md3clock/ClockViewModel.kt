package com.example.md3clock

import android.app.Application
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.md3clock.data.Alarm
import com.example.md3clock.data.CalendarEvent
import com.example.md3clock.data.ClockRepository
import com.example.md3clock.data.WorldCity
import com.example.md3clock.data.cityCatalog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

data class StopwatchState(
    val running: Boolean = false,
    val elapsedMillis: Long = 0L,
    /** 由新到旧的分段用时。 */
    val laps: List<Long> = emptyList(),
)

data class CountdownState(
    val totalMillis: Long = 5 * 60_000L,
    val remainingMillis: Long = 5 * 60_000L,
    val running: Boolean = false,
    val finished: Boolean = false,
)

/**
 * 时钟应用唯一状态源。
 *
 * 时间基准全部取 SystemClock.elapsedRealtime()，而不是 System.currentTimeMillis()：
 * 后者会被系统校时/改时区改动，秒表和倒计时会瞬间跳变；
 * 显示用的「现在」才用挂钟时间（ZonedDateTime.now()）。
 */
class ClockViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = ClockRepository(application)

    private val _now = MutableStateFlow(ZonedDateTime.now())
    val now: StateFlow<ZonedDateTime> = _now.asStateFlow()

    private val _alarms = MutableStateFlow(repo.loadAlarms().sortedBy { it.hour * 60 + it.minute })
    val alarms: StateFlow<List<Alarm>> = _alarms.asStateFlow()

    private val _cities = MutableStateFlow(repo.loadCities().map(::cityOf))
    val cities: StateFlow<List<WorldCity>> = _cities.asStateFlow()

    private val _events = MutableStateFlow(repo.loadEvents().sortedBy { it.minuteOfDay })
    val events: StateFlow<List<CalendarEvent>> = _events.asStateFlow()

    private val _stopwatch = MutableStateFlow(StopwatchState())
    val stopwatch: StateFlow<StopwatchState> = _stopwatch.asStateFlow()

    private val _countdown = MutableStateFlow(CountdownState())
    val countdown: StateFlow<CountdownState> = _countdown.asStateFlow()

    /** 正在响铃的闹钟；null 表示没有。 */
    private val _ringing = MutableStateFlow<Alarm?>(null)
    val ringing: StateFlow<Alarm?> = _ringing.asStateFlow()

    private var stopwatchBase = 0L
    private var stopwatchStartedAt = 0L

    private var countdownEndAt = 0L

    private val firedKeys = mutableSetOf<String>()
    private val snoozes = mutableMapOf<Long, Long>()

    init {
        viewModelScope.launch {
            while (isActive) {
                tick()
                val interval = when {
                    _stopwatch.value.running -> 16L
                    _countdown.value.running -> 50L
                    else -> 200L
                }
                delay(interval)
            }
        }
    }

    private fun tick() {
        val wallNow = ZonedDateTime.now()
        if (wallNow.second != _now.value.second) _now.value = wallNow

        if (_stopwatch.value.running) {
            val elapsed = stopwatchBase + (SystemClock.elapsedRealtime() - stopwatchStartedAt)
            _stopwatch.update { it.copy(elapsedMillis = elapsed) }
        }

        if (_countdown.value.running) {
            val remaining = (countdownEndAt - SystemClock.elapsedRealtime()).coerceAtLeast(0L)
            if (remaining == 0L) {
                _countdown.update { it.copy(running = false, remainingMillis = 0L, finished = true) }
            } else {
                _countdown.update { it.copy(remainingMillis = remaining) }
            }
        }

        checkAlarms(wallNow)
    }

    private fun checkAlarms(wallNow: ZonedDateTime) {
        if (_ringing.value != null) return

        val nowElapsed = SystemClock.elapsedRealtime()
        val snoozeDue = snoozes.entries.firstOrNull { nowElapsed >= it.value }
        if (snoozeDue != null) {
            snoozes.remove(snoozeDue.key)
            _ringing.value = _alarms.value.firstOrNull { it.id == snoozeDue.key }
            return
        }

        // 只在整分后的前两秒判定，避免同一分钟内重复触发
        if (wallNow.second > 1) return
        val due = _alarms.value.firstOrNull { alarm ->
            alarm.ringsOn(wallNow.toLocalDate(), wallNow.toLocalTime())
        } ?: return
        val key = "${due.id}-${wallNow.toLocalDate()}-${due.hour}-${due.minute}"
        if (!firedKeys.add(key)) return
        _ringing.value = due
        if (due.days.isEmpty()) setAlarmEnabled(due.id, false)
    }

    // ---------------- 闹钟 ----------------

    fun saveAlarm(alarm: Alarm) {
        val existing = _alarms.value.any { it.id == alarm.id }
        val next = if (existing) {
            _alarms.value.map { if (it.id == alarm.id) alarm else it }
        } else {
            _alarms.value + alarm
        }.sortedBy { it.hour * 60 + it.minute }
        _alarms.value = next
        repo.saveAlarms(next)
    }

    fun setAlarmEnabled(id: Long, enabled: Boolean) {
        val next = _alarms.value.map { if (it.id == id) it.copy(enabled = enabled) else it }
        _alarms.value = next
        repo.saveAlarms(next)
    }

    fun deleteAlarm(id: Long) {
        val next = _alarms.value.filterNot { it.id == id }
        _alarms.value = next
        repo.saveAlarms(next)
    }

    fun restoreAlarm(alarm: Alarm) {
        if (_alarms.value.any { it.id == alarm.id }) return
        val next = (_alarms.value + alarm).sortedBy { it.hour * 60 + it.minute }
        _alarms.value = next
        repo.saveAlarms(next)
    }

    fun newAlarmId(): Long = (System.currentTimeMillis() % 1_000_000L)

    fun snoozeRinging() {
        val current = _ringing.value ?: return
        snoozes[current.id] = SystemClock.elapsedRealtime() + SNOOZE_MILLIS
        _ringing.value = null
    }

    fun dismissRinging() {
        _ringing.value = null
    }

    // ---------------- 世界时钟 ----------------

    fun addCity(city: WorldCity) {
        if (_cities.value.any { it.zoneId == city.zoneId }) return
        val next = _cities.value + city
        _cities.value = next
        repo.saveCities(next.map { it.zoneId })
    }

    fun removeCity(zoneId: String) {
        val next = _cities.value.filterNot { it.zoneId == zoneId }
        _cities.value = next
        repo.saveCities(next.map { it.zoneId })
    }

    fun restoreCity(city: WorldCity, index: Int) {
        if (_cities.value.any { it.zoneId == city.zoneId }) return
        val mutable = _cities.value.toMutableList()
        mutable.add(index.coerceIn(0, mutable.size), city)
        _cities.value = mutable
        repo.saveCities(mutable.map { it.zoneId })
    }

    // ---------------- 秒表 ----------------

    fun toggleStopwatch() {
        val state = _stopwatch.value
        _stopwatch.value = if (state.running) {
            val elapsed = stopwatchBase + (SystemClock.elapsedRealtime() - stopwatchStartedAt)
            state.copy(running = false, elapsedMillis = elapsed)
        } else {
            stopwatchBase = state.elapsedMillis
            stopwatchStartedAt = SystemClock.elapsedRealtime()
            state.copy(running = true)
        }
    }

    fun lap() {
        val state = _stopwatch.value
        if (!state.running) return
        val current = stopwatchBase + (SystemClock.elapsedRealtime() - stopwatchStartedAt)
        if (current <= 0) return
        _stopwatch.value = state.copy(laps = listOf(current) + state.laps)
    }

    fun resetStopwatch() {
        stopwatchBase = 0L
        _stopwatch.value = StopwatchState()
    }

    // ---------------- 倒计时 ----------------

    fun setCountdownMinutes(minutes: Int) {
        val state = _countdown.value
        if (state.running) return
        val total = minutes.coerceIn(1, 60) * 60_000L
        _countdown.value = state.copy(totalMillis = total, remainingMillis = total, finished = false)
    }

    fun toggleCountdown() {
        val state = _countdown.value
        _countdown.value = if (state.running) {
            val remaining = (countdownEndAt - SystemClock.elapsedRealtime()).coerceAtLeast(0L)
            state.copy(running = false, remainingMillis = remaining)
        } else {
            val remaining = if (state.remainingMillis <= 0L) state.totalMillis else state.remainingMillis
            countdownEndAt = SystemClock.elapsedRealtime() + remaining
            state.copy(running = true, remainingMillis = remaining, finished = false)
        }
    }

    fun resetCountdown() {
        _countdown.update { it.copy(running = false, remainingMillis = it.totalMillis, finished = false) }
    }

    fun consumeCountdownFinished() {
        _countdown.update { it.copy(finished = false) }
    }

    // ---------------- 日历 ----------------

    fun addEvent(epochDay: Long, minuteOfDay: Int, title: String) {
        val event = CalendarEvent(
            id = System.currentTimeMillis(),
            epochDay = epochDay,
            minuteOfDay = minuteOfDay,
            title = title,
        )
        val next = (_events.value + event).sortedWith(compareBy({ it.epochDay }, { it.minuteOfDay }))
        _events.value = next
        repo.saveEvents(next)
    }

    fun deleteEvent(id: Long) {
        val next = _events.value.filterNot { it.id == id }
        _events.value = next
        repo.saveEvents(next)
    }

    fun restoreEvent(event: CalendarEvent) {
        if (_events.value.any { it.id == event.id }) return
        val next = (_events.value + event).sortedWith(compareBy({ it.epochDay }, { it.minuteOfDay }))
        _events.value = next
        repo.saveEvents(next)
    }

    fun eventsOn(epochDay: Long): List<CalendarEvent> =
        _events.value.filter { it.epochDay == epochDay }.sortedBy { it.minuteOfDay }

    private fun cityOf(zoneId: String): WorldCity =
        cityCatalog.firstOrNull { it.zoneId == zoneId }
            ?: WorldCity(zoneId, zoneId.substringAfterLast('/'), zoneId.substringBefore('/'))

    private companion object {
        const val SNOOZE_MILLIS = 10 * 60_000L
    }
}
