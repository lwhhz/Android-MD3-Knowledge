package com.example.m3appbar.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.SuggestionChip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3ChipGroupLayout
import com.example.m3appbar.ui.M3ChipStyle

private val ChipShape = RoundedCornerShape(8.dp)
private val ChipIconModifier: Modifier = Modifier.size(18.dp)

/** Assist chip：动作类，支持 outlined / elevated 两种样式。 */
@Composable
fun M3AssistiveChip(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: M3ChipStyle = M3ChipStyle.OUTLINED,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
) {
    val leading: (@Composable () -> Unit)? = if (leadingIcon != null) {
        { Icon(leadingIcon, contentDescription = null, modifier = ChipIconModifier) }
    } else {
        null
    }
    val trailing: (@Composable () -> Unit)? = if (trailingIcon != null) {
        { Icon(trailingIcon, contentDescription = null, modifier = ChipIconModifier) }
    } else {
        null
    }
    if (style == M3ChipStyle.ELEVATED) {
        ElevatedAssistChip(
            onClick = onClick,
            label = { androidx.compose.material3.Text(label) },
            modifier = modifier,
            enabled = enabled,
            leadingIcon = leading,
            trailingIcon = trailing,
            shape = ChipShape,
        )
    } else {
        AssistChip(
            onClick = onClick,
            label = { androidx.compose.material3.Text(label) },
            modifier = modifier,
            enabled = enabled,
            leadingIcon = leading,
            trailingIcon = trailing,
            shape = ChipShape,
        )
    }
}

/** Filter chip：可多选/单选，选中时在起始侧追加对勾。 */
@Composable
fun M3FilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showTrailingIcon: Boolean = false,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
) {
    val trailing: (@Composable () -> Unit)? = if (showTrailingIcon && trailingIcon != null) {
        { Icon(trailingIcon, contentDescription = null, modifier = ChipIconModifier) }
    } else {
        null
    }
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { androidx.compose.material3.Text(label) },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = if (selected) {
            { Icon(Icons.Filled.Check, contentDescription = null, modifier = ChipIconModifier) }
        } else {
            null
        },
        trailingIcon = trailing,
        shape = ChipShape,
    )
}

/** Input chip：用户输入的信息单元，支持头像与关闭动作。 */
@Composable
fun M3InputChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showClosingIcon: Boolean = true,
    onRemove: (() -> Unit)? = null,
    avatar: (@Composable () -> Unit)? = null,
) {
    val trailing: (@Composable () -> Unit)? = if (showClosingIcon) {
        {
            // 官方要求关闭动作 48dp 目标：chip 最小宽 88dp，右侧给 48dp 宽的点击区，
            // 视觉高度仍保持 32dp（外层 chip 自带 48dp 最小触控目标）。
            Box(
                modifier = Modifier
                    .widthIn(min = 48.dp)
                    .size(width = 48.dp, height = 32.dp)
                    .semantics(mergeDescendants = true) {
                        contentDescription = "移除 " + label
                    }
                    .clickable(enabled = enabled) { onRemove?.invoke() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    modifier = ChipIconModifier,
                )
            }
        }
    } else {
        null
    }
    InputChip(
        selected = selected,
        onClick = onClick,
        label = { androidx.compose.material3.Text(label) },
        modifier = modifier.widthIn(min = 88.dp),
        enabled = enabled,
        avatar = avatar,
        trailingIcon = trailing,
        shape = ChipShape,
    )
}

/** Suggestion chip：产品生成的建议，支持 outlined / elevated。 */
@Composable
fun M3SuggestionChip(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: M3ChipStyle = M3ChipStyle.OUTLINED,
    leadingIcon: ImageVector? = null,
) {
    val icon: (@Composable () -> Unit)? = if (leadingIcon != null) {
        { Icon(leadingIcon, contentDescription = null, modifier = ChipIconModifier) }
    } else {
        null
    }
    if (style == M3ChipStyle.ELEVATED) {
        ElevatedSuggestionChip(
            onClick = onClick,
            label = { androidx.compose.material3.Text(label) },
            modifier = modifier,
            enabled = enabled,
            icon = icon,
            shape = ChipShape,
        )
    } else {
        SuggestionChip(
            onClick = onClick,
            label = { androidx.compose.material3.Text(label) },
            modifier = modifier,
            enabled = enabled,
            icon = icon,
            shape = ChipShape,
        )
    }
}

/**
 * Chip 组：把多个 chip 按官方间距（≥8dp）排列。
 *
 * layout = SINGLE_ROW_SCROLLABLE 时横向滚动；WRAP 时换行。
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun M3ChipGroups(
    modifier: Modifier = Modifier,
    layout: M3ChipGroupLayout = M3ChipGroupLayout.SINGLE_ROW_SCROLLABLE,
    content: @Composable () -> Unit,
) {
    if (layout == M3ChipGroupLayout.WRAP) {
        FlowRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            content()
        }
    } else {
        Row(
            modifier = modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            content()
        }
    }
}
