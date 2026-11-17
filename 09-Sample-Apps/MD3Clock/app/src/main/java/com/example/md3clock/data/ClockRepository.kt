package com.example.md3clock.data

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate

/**
 * 本地持久化：SharedPreferences + kotlinx.serialization。
 *
 * 只存稳定事实（闹钟规则、城市时区、日程日期），不存「现在几点」。
 * 数据量在 KB 级，不需要数据库；换成 DataStore 也只是换壳。
 *
 * ⚠️ 范围说明：本项目是「时间界面」示例，闹钟只在应用前台到点时提醒，
 * 不注册系统 AlarmManager，因此不清求精确闹钟权限、也不做开机重启恢复。
 */
class ClockRepository(context: Context) {

    private val prefs = context.getSharedPreferences("md3clock", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }

    fun loadAlarms(): List<Alarm> {
        val raw = prefs.getString(KEY_ALARMS, null) ?: return defaultAlarms()
        return runCatching { json.decodeFromString<List<Alarm>>(raw) }.getOrNull()
            ?: defaultAlarms()
    }

    fun saveAlarms(alarms: List<Alarm>) {
        prefs.edit().putString(KEY_ALARMS, json.encodeToString(alarms)).apply()
    }

    fun loadCities(): List<String> {
        val raw = prefs.getString(KEY_CITIES, null) ?: return defaultCities
        return runCatching { json.decodeFromString<List<String>>(raw) }.getOrNull()
            ?.filter { runCatching { java.time.ZoneId.of(it) }.isSuccess }
            ?: defaultCities
    }

    fun saveCities(zoneIds: List<String>) {
        prefs.edit().putString(KEY_CITIES, json.encodeToString(zoneIds)).apply()
    }

    fun loadEvents(): List<CalendarEvent> {
        val raw = prefs.getString(KEY_EVENTS, null) ?: return defaultEvents()
        return runCatching { json.decodeFromString<List<CalendarEvent>>(raw) }.getOrNull()
            ?: defaultEvents()
    }

    fun saveEvents(events: List<CalendarEvent>) {
        prefs.edit().putString(KEY_EVENTS, json.encodeToString(events)).apply()
    }

    private fun defaultAlarms(): List<Alarm> = listOf(
        Alarm(id = 1, hour = 7, minute = 30, label = "晨间唤醒", days = listOf(1, 2, 3, 4, 5), enabled = true),
        Alarm(id = 2, hour = 13, minute = 30, label = "午休结束", days = listOf(1, 2, 3, 4, 5), enabled = true),
        Alarm(id = 3, hour = 22, minute = 30, label = "睡前阅读", days = listOf(1, 2, 3, 4, 5, 6, 7), enabled = false),
    )

    private val defaultCities: List<String> = listOf(
        "Asia/Tokyo",
        "Europe/London",
        "America/New_York",
        "Australia/Sydney",
    )

    private fun defaultEvents(): List<CalendarEvent> {
        val today = LocalDate.now()
        return listOf(
            CalendarEvent(id = 1, epochDay = today.toEpochDay(), minuteOfDay = 9 * 60 + 30, title = "设计评审 · 时序 v1"),
            CalendarEvent(id = 2, epochDay = today.toEpochDay(), minuteOfDay = 14 * 60, title = "与客户通话"),
            CalendarEvent(id = 3, epochDay = today.toEpochDay(), minuteOfDay = 18 * 60 + 30, title = "健身"),
            CalendarEvent(id = 4, epochDay = today.plusDays(1).toEpochDay(), minuteOfDay = 10 * 60, title = "周会"),
            CalendarEvent(id = 5, epochDay = today.plusDays(3).toEpochDay(), minuteOfDay = 19 * 60, title = "电影《沙丘 3》"),
        )
    }

    private companion object {
        const val KEY_ALARMS = "alarms"
        const val KEY_CITIES = "cities"
        const val KEY_EVENTS = "events"
    }
}
