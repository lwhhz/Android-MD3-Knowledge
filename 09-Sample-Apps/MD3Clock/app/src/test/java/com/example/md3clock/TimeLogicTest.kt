package com.example.md3clock

import com.example.md3clock.data.Alarm
import com.example.md3clock.data.formatCountdown
import com.example.md3clock.data.formatStopwatch
import com.example.md3clock.data.nextTrigger
import com.example.md3clock.data.offsetLabel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * 时钟核心逻辑的边界测试。
 * 这些规则错了，界面再漂亮也没用：闹钟差一天、倒计时显示 -1 秒都是硬 bug。
 */
class TimeLogicTest {

    private val zone = ZoneId.of("Asia/Shanghai")

    @Test
    fun `repeat alarm rolls to next matching weekday`() {
        // 2026-11-17 是周二
        val now = ZonedDateTime.of(2026, 11, 17, 8, 0, 0, 0, zone)
        val alarm = Alarm(id = 1, hour = 7, minute = 30, label = "晨间", days = listOf(1, 2, 3, 4, 5))
        val next = alarm.nextTrigger(now)
        assertNotNull(next)
        // 今天 07:30 已过，顺延到周三
        assertEquals(LocalDate.of(2026, 11, 18), next!!.toLocalDate())
        assertEquals(LocalTime.of(7, 30), next.toLocalTime())
    }

    @Test
    fun `repeat alarm stays today when time is ahead`() {
        val now = ZonedDateTime.of(2026, 11, 17, 6, 0, 0, 0, zone)
        val alarm = Alarm(id = 1, hour = 7, minute = 30, label = "晨间", days = listOf(2))
        val next = alarm.nextTrigger(now)
        assertEquals(LocalDate.of(2026, 11, 17), next!!.toLocalDate())
    }

    @Test
    fun `weekend alarm skips to saturday`() {
        val now = ZonedDateTime.of(2026, 11, 17, 9, 0, 0, 0, zone) // 周二
        val alarm = Alarm(id = 2, hour = 9, minute = 0, label = "周末", days = listOf(6, 7))
        val next = alarm.nextTrigger(now)
        assertEquals(LocalDate.of(2026, 11, 21), next!!.toLocalDate())
    }

    @Test
    fun `one shot alarm schedules next occurrence and disables logic is external`() {
        val now = ZonedDateTime.of(2026, 11, 17, 23, 0, 0, 0, zone)
        val alarm = Alarm(id = 3, hour = 7, minute = 0, label = "一次", days = emptyList())
        val next = alarm.nextTrigger(now)
        assertEquals(LocalDate.of(2026, 11, 18), next!!.toLocalDate())
        assertFalse(alarm.enabled.not())
    }

    @Test
    fun `disabled alarm has no next trigger`() {
        val now = ZonedDateTime.of(2026, 11, 17, 6, 0, 0, 0, zone)
        val alarm = Alarm(id = 4, hour = 7, minute = 0, label = "停用", days = listOf(1), enabled = false)
        assertNull(alarm.nextTrigger(now))
    }

    @Test
    fun `one shot alarm only rings on its target date`() {
        val alarm = Alarm(
            id = 9,
            hour = 7,
            minute = 0,
            label = "一次",
            days = emptyList(),
            oneShotEpochDay = LocalDate.of(2026, 11, 18).toEpochDay(),
        )
        assertFalse(alarm.ringsOn(LocalDate.of(2026, 11, 17), LocalTime.of(7, 0)))
        assertTrue(alarm.ringsOn(LocalDate.of(2026, 11, 18), LocalTime.of(7, 0)))
    }

    @Test
    fun `withOneShotDate rolls past time to tomorrow`() {
        val alarm = Alarm(id = 10, hour = 7, minute = 0, label = "一次", days = emptyList())
        val now = ZonedDateTime.of(2026, 11, 17, 8, 0, 0, 0, zone)
        val dated = alarm.withOneShotDate(now)
        assertEquals(LocalDate.of(2026, 11, 18).toEpochDay(), dated.oneShotEpochDay)
    }

    @Test
    fun `ringsOn respects weekday set and time`() {
        val alarm = Alarm(id = 5, hour = 7, minute = 30, label = "工作日", days = listOf(1, 2, 3, 4, 5))
        assertTrue(alarm.ringsOn(LocalDate.of(2026, 11, 17), LocalTime.of(7, 30)))   // 周二
        assertFalse(alarm.ringsOn(LocalDate.of(2026, 11, 21), LocalTime.of(7, 30)))  // 周六
        assertFalse(alarm.ringsOn(LocalDate.of(2026, 11, 17), LocalTime.of(7, 31)))
    }

    @Test
    fun `stopwatch formatting keeps centiseconds and hours`() {
        assertEquals("00:00.00", formatStopwatch(0))
        assertEquals("00:01.23", formatStopwatch(1_234))
        assertEquals("01:00.00", formatStopwatch(60_000))
        assertEquals("1:00:00.00", formatStopwatch(3_600_000))
        assertEquals("00:00.00", formatStopwatch(-5))
    }

    @Test
    fun `countdown rounds up so it never shows zero before finishing`() {
        assertEquals("00:01", formatCountdown(1))
        assertEquals("00:01", formatCountdown(999))
        assertEquals("00:00", formatCountdown(0))
        assertEquals("1:01:01", formatCountdown(3_661_000))
    }

    @Test
    fun `time zone offsets handle half hour zones`() {
        assertEquals("GMT+9", ZoneId.of("Asia/Tokyo").offsetLabel())
        assertEquals("GMT+5:30", ZoneId.of("Asia/Kolkata").offsetLabel())
        assertEquals("GMT-5", ZoneId.of("America/New_York").offsetLabel(java.time.Instant.parse("2026-11-17T00:00:00Z")))
    }
}
