package com.example.m3appbar.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3BadgeMetrics
import com.example.m3appbar.ui.M3BadgeSize

/**
 * 独立 Badge：Small 圆点 / Large 计数或短文本。
 *
 * 官方：
 * - Small 6dp，圆角 3dp；
 * - Large 高 16dp、最大宽 34dp、圆角 8dp，文字最多 4 字符（含 +）；
 * - 容器 error / 内容 onError。
 *
 * Large 文本优先取 label，其次 count；count 超过 maxCount 时显示 "maxCount+"。
 */
@Composable
fun M3Badges(
    modifier: Modifier = Modifier,
    size: M3BadgeSize = M3BadgeSize.LARGE,
    count: Int? = null,
    maxCount: Int = 99,
    label: String? = null,
) {
    if (size == M3BadgeSize.SMALL) {
        Badge(
            modifier = modifier
                .size(M3BadgeMetrics.smallSize)
                .semantics { contentDescription = "新通知" },
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError,
        )
        return
    }

    val text = (label ?: count?.let { if (it > maxCount) "$maxCount+" else "$it" } ?: "3")
        .take(M3BadgeMetrics.maxCharacters)
    // count 走官方 34dp 上限；任意 label 允许略宽，避免字母被裁切。
    val widthModifier = if (label != null) {
        Modifier.widthIn(min = M3BadgeMetrics.largeHeight)
    } else {
        Modifier.widthIn(min = M3BadgeMetrics.largeHeight, max = M3BadgeMetrics.largeMaxWidth)
    }
    Badge(
        modifier = modifier
            .height(M3BadgeMetrics.largeHeight)
            .then(widthModifier)
            .semantics { contentDescription = text },
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = MaterialTheme.colorScheme.onError,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Clip,
        )
    }
}
