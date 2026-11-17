package com.example.m3appbar.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3TabConfiguration
import com.example.m3appbar.ui.M3TabLayout
import com.example.m3appbar.ui.M3TabMetrics
import com.example.m3appbar.ui.M3TabStyle

/** 单个 tab 的数据。 */
data class M3TabItem(
    val label: String,
    val icon: ImageVector? = null,
    val enabled: Boolean = true,
    val showBadge: Boolean = false,
)

/**
 * Tabs：Primary / Secondary × 固定 / 可滚动 × 仅文字 / 图标+文字 / 仅图标。
 *
 * 官方尺寸：
 * - 仅文字 48dp，图标+文字 64dp；
 * - 图标 24dp；分隔线 1dp；
 * - Primary 指示器 3dp、Secondary 2dp，左右各内缩 2dp，最短 24dp；
 * - 可滚动 tabs 前缘留 52dp。
 *
 * Badge：Kit 的 Tabs 只提供 Show badge 布尔值，这里实现为 6dp 小圆点：
 * - 有图标时叠在图标右上角；
 * - Label only 时放在文字 trailing，间距 4dp。
 *
 * Secondary 不支持图标（官方无图标槽位），传入 icon 会被忽略。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3Tabs(
    tabs: List<M3TabItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    style: M3TabStyle = M3TabStyle.PRIMARY,
    configuration: M3TabConfiguration = M3TabConfiguration.FIXED,
    layout: M3TabLayout = M3TabLayout.LABEL_ONLY,
) {
    val effectiveLayout = if (style == M3TabStyle.SECONDARY) M3TabLayout.LABEL_ONLY else layout
    val showIcon = effectiveLayout != M3TabLayout.LABEL_ONLY
    val showLabel = effectiveLayout != M3TabLayout.ICON_ONLY
    val tabHeight = if (effectiveLayout == M3TabLayout.ICON_AND_LABEL) M3TabMetrics.heightIconAndLabel else M3TabMetrics.heightLabelOnly

    val content: @Composable () -> Unit = {
        tabs.forEachIndexed { index, item ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onSelect(index) },
                modifier = Modifier.height(tabHeight),
                enabled = item.enabled,
                text = if (showLabel) {
                    {
                        if (item.showBadge && !showIcon) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = item.label,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Spacer(Modifier.width(M3TabMetrics.badgeTextGap))
                                M3SmallBadge()
                            }
                        } else {
                            Text(
                                text = item.label,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                } else {
                    null
                },
                icon = if (showIcon && item.icon != null) {
                    {
                        if (item.showBadge) {
                            BadgedBox(badge = { M3SmallBadge() }) {
                                Icon(item.icon, contentDescription = null, modifier = Modifier.size(M3TabMetrics.iconSize))
                            }
                        } else {
                            Icon(item.icon, contentDescription = null, modifier = Modifier.size(M3TabMetrics.iconSize))
                        }
                    }
                } else {
                    null
                },
            )
        }
    }

    when (style) {
        M3TabStyle.PRIMARY -> {
            if (configuration == M3TabConfiguration.SCROLLABLE) {
                PrimaryScrollableTabRow(
                    selectedTabIndex = selectedIndex,
                    modifier = modifier,
                    edgePadding = M3TabMetrics.scrollEdgePadding,
                ) { content() }
            } else {
                PrimaryTabRow(
                    selectedTabIndex = selectedIndex,
                    modifier = modifier,
                ) { content() }
            }
        }
        M3TabStyle.SECONDARY -> {
            if (configuration == M3TabConfiguration.SCROLLABLE) {
                SecondaryScrollableTabRow(
                    selectedTabIndex = selectedIndex,
                    modifier = modifier,
                    edgePadding = M3TabMetrics.scrollEdgePadding,
                ) { content() }
            } else {
                SecondaryTabRow(
                    selectedTabIndex = selectedIndex,
                    modifier = modifier,
                ) { content() }
            }
        }
    }
}

@Composable
private fun M3SmallBadge() {
    Badge(
        modifier = Modifier
            .size(M3TabMetrics.smallBadgeSize)
            .semantics { contentDescription = "新通知" },
    )
}
