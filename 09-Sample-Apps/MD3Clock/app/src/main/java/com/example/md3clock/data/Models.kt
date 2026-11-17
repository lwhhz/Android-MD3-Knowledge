package com.example.md3clock.data

import kotlinx.serialization.Serializable
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

/**
 * 闹钟模型。
 *
 * days 用 ISO 星期序号（周一 = 1 … 周日 = 7）：
 * - 空列表 = 一次性闹钟（下次触发后自动关闭）；
 * - 全部 1..7 = 每天；
 * - 1..5 = 工作日。
 *
 * 为什么存「时/分 + 星期集合」而不是时间戳：闹钟是规则，不是某一刻。
 * 存时间戳会在换时区、改夏令时后错一小时（date-time-picker.md 常见错误 8 的同类问题）。
 */
@Serializable
data class Alarm(
    val id: Long,
    val hour: Int,
    val minute: Int,
    val label: String,
    val days: List<Int> = emptyList(),
    val enabled: Boolean = true,
    /**
     * 一次性闹钟的目标日期（epochDay）。为空表示「按时间在下一次触发」。
     * 记录它才能避免「10 分钟后的提醒」在被错过一天后，于第二天同一时刻误响。
     */
    val oneShotEpochDay: Long? = null,
) {
    val timeText: String get() = "%02d:%02d".format(hour, minute)

    val repeatLabel: String
        get() = when {
            days.isEmpty() -> oneShotEpochDay?.let {
                LocalDate.ofEpochDay(it).format(DateTimeFormatter.ofPattern("M月d日", localeCn)) + " 仅一次"
            } ?: "仅一次"
            days.size == 7 -> "每天"
            days.sorted() == listOf(1, 2, 3, 4, 5) -> "工作日"
            days.sorted() == listOf(6, 7) -> "周末"
            else -> days.sorted().joinToString(" ") { "周${weekdayShort(it)}" }
        }

    fun ringsOn(date: LocalDate, time: LocalTime): Boolean {
        if (!enabled || time.hour != hour || time.minute != minute) return false
        return if (days.isEmpty()) {
            oneShotEpochDay == null || oneShotEpochDay == date.toEpochDay()
        } else {
            date.dayOfWeek.value in days
        }
    }

    /** 给一次性闹钟补上目标日期；循环闹钟原样返回。 */
    fun withOneShotDate(now: ZonedDateTime = ZonedDateTime.now()): Alarm {
        if (days.isNotEmpty()) return this
        // 已有未来日期就保留（用户可能在编辑标签）；日期已到/已过则按新时间重算
        val existing = oneShotEpochDay
        if (existing != null && existing > now.toLocalDate().toEpochDay()) return this
        var candidate = now.toLocalDate().atTime(hour, minute).atZone(now.zone)
        if (!candidate.isAfter(now)) candidate = candidate.plusDays(1)
        return copy(oneShotEpochDay = candidate.toLocalDate().toEpochDay())
    }
}

/** 日历日程：只存「哪一天 + 当天第几分钟」，避免时区换算把这个日程挪到前一天。 */
@Serializable
data class CalendarEvent(
    val id: Long,
    val epochDay: Long,
    val minuteOfDay: Int,
    val title: String,
) {
    val hour: Int get() = minuteOfDay / 60
    val minute: Int get() = minuteOfDay % 60
    val timeText: String get() = "%02d:%02d".format(hour, minute)
    val date: LocalDate get() = LocalDate.ofEpochDay(epochDay)
}

/** 世界时钟城市：zoneId 是唯一真相，显示名只是标签。 */
data class WorldCity(
    val zoneId: String,
    val name: String,
    val country: String,
) {
    val zone: ZoneId get() = ZoneId.of(zoneId)
}

val cityCatalog: List<WorldCity> = listOf(
    WorldCity("Asia/Shanghai", "北京", "中国"),
    WorldCity("Asia/Tokyo", "东京", "日本"),
    WorldCity("Asia/Seoul", "首尔", "韩国"),
    WorldCity("Asia/Singapore", "新加坡", "新加坡"),
    WorldCity("Asia/Dubai", "迪拜", "阿联酋"),
    WorldCity("Asia/Kolkata", "孟买", "印度"),
    WorldCity("Europe/London", "伦敦", "英国"),
    WorldCity("Europe/Paris", "巴黎", "法国"),
    WorldCity("Europe/Berlin", "柏林", "德国"),
    WorldCity("Europe/Moscow", "莫斯科", "俄罗斯"),
    WorldCity("America/New_York", "纽约", "美国"),
    WorldCity("America/Los_Angeles", "洛杉矶", "美国"),
    WorldCity("America/Sao_Paulo", "圣保罗", "巴西"),
    WorldCity("Australia/Sydney", "悉尼", "澳大利亚"),
    WorldCity("Pacific/Auckland", "奥克兰", "新西兰"),
)

private val localeCn = Locale.CHINA
private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", localeCn)
private val secondFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", localeCn)
private val dateFormatter = DateTimeFormatter.ofPattern("M月d日 EEEE", localeCn)
private val monthFormatter = DateTimeFormatter.ofPattern("yyyy年M月", localeCn)

fun ZonedDateTime.clockText(): String = format(timeFormatter)
fun ZonedDateTime.clockWithSeconds(): String = format(secondFormatter)
fun ZonedDateTime.dateText(): String = format(dateFormatter)
fun ZonedDateTime.monthText(): String = format(monthFormatter)

fun weekdayShort(isoDay: Int): String = when (isoDay) {
    1 -> "一"
    2 -> "二"
    3 -> "三"
    4 -> "四"
    5 -> "五"
    6 -> "六"
    else -> "日"
}

fun DayOfWeek.shortCn(): String = weekdayShort(value)

/**
 * 下一次触发时间。
 * 循环闹钟在「今天已过点」时顺延到下一个匹配的星期；
 * 一次性闹钟直接取下一次同刻。
 */
fun Alarm.nextTrigger(now: ZonedDateTime = ZonedDateTime.now()): ZonedDateTime? {
    if (!enabled) return null
    if (days.isEmpty()) {
        var candidate = now.toLocalDate().atTime(hour, minute).atZone(now.zone)
        if (!candidate.isAfter(now)) candidate = candidate.plusDays(1)
        return candidate
    }
    for (offset in 0..7) {
        val date = now.toLocalDate().plusDays(offset.toLong())
        if (date.dayOfWeek.value in days) {
            val candidate = date.atTime(hour, minute).atZone(now.zone)
            if (candidate.isAfter(now)) return candidate
        }
    }
    return null
}

/** "8 小时 12 分" / "12 分" / "不到 1 分钟" —— 给「距下次响铃」用。 */
fun humanizeDuration(millis: Long): String {
    val totalMinutes = (millis / 60_000L).coerceAtLeast(0)
    val days = totalMinutes / (24 * 60)
    val hours = (totalMinutes % (24 * 60)) / 60
    val minutes = totalMinutes % 60
    return buildString {
        if (days > 0) append("${days} 天 ")
        if (hours > 0) append("${hours} 小时 ")
        if (minutes > 0) append("${minutes} 分")
        if (days == 0L && hours == 0L && minutes == 0L) append("不到 1 分钟")
    }.trim()
}

/** 秒表格式：mm:ss.cc（超过一小时自动显示 h:mm:ss.cc）。 */
fun formatStopwatch(millis: Long): String {
    val total = millis.coerceAtLeast(0)
    val cs = (total % 1000) / 10
    val seconds = (total / 1000) % 60
    val minutes = (total / 60_000) % 60
    val hours = total / 3_600_000
    return if (hours > 0) "%d:%02d:%02d.%02d".format(hours, minutes, seconds, cs)
    else "%02d:%02d.%02d".format(minutes, seconds, cs)
}

/** 倒计时格式：HH:mm:ss（无小时时省略小时段）。 */
fun formatCountdown(millis: Long): String {
    val totalSeconds = (millis.coerceAtLeast(0) + 999) / 1000
    val seconds = totalSeconds % 60
    val minutes = (totalSeconds / 60) % 60
    val hours = totalSeconds / 3600
    return if (hours > 0) "%d:%02d:%02d".format(hours, minutes, seconds)
    else "%02d:%02d".format(minutes, seconds)
}

/** 世界时钟的 GMT 偏移文本，例如 GMT+8 / GMT-3:30。 */
fun ZoneId.offsetLabel(instant: Instant = Instant.now()): String {
    val totalSeconds = rules.getOffset(instant).totalSeconds
    val sign = if (totalSeconds >= 0) "+" else "-"
    val absolute = abs(totalSeconds)
    val hours = absolute / 3600
    val minutes = (absolute % 3600) / 60
    return if (minutes == 0) "GMT$sign$hours" else "GMT$sign$hours:%02d".format(minutes)
}

/** 该时区当前是否处在白天（6:00–18:00），用于日/夜标记。 */
fun ZoneId.isDaytime(instant: Instant = Instant.now()): Boolean {
    val hour = instant.atZone(this).hour
    return hour in 6..17
}

/** 相对本地日期差：+1 表示「比本地早一天」。 */
fun ZoneId.dayOffsetVsLocal(localZone: ZoneId = ZoneId.systemDefault(), instant: Instant = Instant.now()): Int {
    val remote = instant.atZone(this).toLocalDate()
    val local = instant.atZone(localZone).toLocalDate()
    return (remote.toEpochDay() - local.toEpochDay()).toInt()
}
