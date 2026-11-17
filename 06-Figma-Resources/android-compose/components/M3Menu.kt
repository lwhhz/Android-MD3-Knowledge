package com.example.m3appbar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3MenuDensity
import com.example.m3appbar.ui.M3MenuMetrics
import com.example.m3appbar.ui.M3MenuTheme

/** 菜单项数据。 */
data class M3MenuItemData(
    val label: String,
    val supportingText: String? = null,
    val leadingIcon: ImageVector? = null,
    val trailingText: String? = null,
    val enabled: Boolean = true,
    val selected: Boolean = false,
)

private data class MenuColors(
    val container: androidx.compose.ui.graphics.Color,
    val content: androidx.compose.ui.graphics.Color,
    val supporting: androidx.compose.ui.graphics.Color,
    val selectedContainer: androidx.compose.ui.graphics.Color,
    val selectedContent: androidx.compose.ui.graphics.Color,
)

@Composable
private fun menuColors(theme: M3MenuTheme): MenuColors {
    val s = MaterialTheme.colorScheme
    return if (theme == M3MenuTheme.VIBRANT) {
        MenuColors(
            container = s.tertiaryContainer,
            content = s.onTertiaryContainer,
            supporting = s.onTertiaryContainer,
            selectedContainer = s.tertiary,
            selectedContent = s.onTertiary,
        )
    } else {
        MenuColors(
            container = s.surfaceContainerLow,
            content = s.onSurface,
            supporting = s.onSurfaceVariant,
            selectedContainer = s.tertiaryContainer,
            selectedContent = s.onTertiaryContainer,
        )
    }
}

/**
 * Vertical Menu（Standard / Vibrant）。
 *
 * 官方尺寸：宽 112–280dp、item 高 48dp、左右 12dp、图标 24dp；
 * baseline 圆角 4dp；vertical 圆角来自 Expressive，取 16dp。
 * showScrollbar 当前未绘制（仅滚动容器），见 MENU_TRIAL 限制。
 */
@Composable
fun M3Menu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<M3MenuItemData>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    theme: M3MenuTheme = M3MenuTheme.STANDARD,
    groups: Int = 1,
    showScrollbar: Boolean = false,
    showSectionLabel: Boolean = false,
    sectionLabel: String = "Section",
    showFocusIndicator: Boolean = true,
) {
    val innerScroll = rememberScrollState()
    var viewportHeight by remember { mutableIntStateOf(0) }
    var contentHeight by remember { mutableIntStateOf(0) }
    val colors = menuColors(theme)
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier.widthIn(min = M3MenuMetrics.minWidth, max = M3MenuMetrics.maxWidth),
        shape = RoundedCornerShape(M3MenuMetrics.verticalCorner),
        containerColor = colors.container,
        tonalElevation = M3MenuMetrics.elevation,
    ) {
        Box(
            modifier = Modifier
                .heightIn(max = 320.dp)
                .onSizeChanged { viewportHeight = it.height },
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(innerScroll)
                    .onSizeChanged { contentHeight = it.height },
            ) {
                if (showSectionLabel) {
                    Text(
                        text = sectionLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.supporting,
                        modifier = Modifier.padding(horizontal = M3MenuMetrics.horizontalPadding, vertical = 8.dp),
                    )
                }
                val groupSize = if (groups > 1) (items.size + groups - 1) / groups else items.size
                items.forEachIndexed { index, item ->
                    M3MenuRow(
                        item = item,
                        colors = colors,
                        showFocusIndicator = showFocusIndicator,
                        onClick = { onItemClick(index) },
                    )
                    if (groups > 1 && index < items.lastIndex && (index + 1) % groupSize == 0) {
                        Divider(
                            modifier = Modifier.padding(vertical = M3MenuMetrics.dividerVerticalPadding),
                            thickness = M3MenuMetrics.dividerHeight,
                            color = MaterialTheme.colorScheme.outlineVariant,
                        )
                    }
                }
            }
            val maxScroll = innerScroll.maxValue
            if (showScrollbar && maxScroll > 0 && viewportHeight > 0 && contentHeight > viewportHeight) {
                val density = LocalDensity.current
                val thumbPx = (viewportHeight.toFloat() * viewportHeight / contentHeight)
                    .coerceAtLeast(with(density) { 24.dp.toPx() })
                val trackPx = (viewportHeight - thumbPx).coerceAtLeast(0f)
                val offsetPx = (innerScroll.value.toFloat() / maxScroll * trackPx).coerceIn(0f, trackPx)
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .offset { IntOffset(0, offsetPx.roundToInt()) }
                        .width(4.dp)
                        .height(with(density) { thumbPx.toDp() })
                        .background(MaterialTheme.colorScheme.outline, RoundedCornerShape(2.dp)),
                )
            }
        }
    }
}

/** Standard 菜单项（可单独使用）。 */
@Composable
fun M3MenuItemStandard(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingText: String? = null,
    showLeadingElement: Boolean = true,
    showTrailingElement: Boolean = true,
    showDivider: Boolean = false,
    showFocusIndicator: Boolean = true,
) {
    Column(modifier.fillMaxWidth()) {
        M3MenuRow(
            item = M3MenuItemData(
                label = label,
                leadingIcon = if (showLeadingElement) leadingIcon else null,
                trailingText = if (showTrailingElement) trailingText else null,
                enabled = enabled,
                selected = selected,
            ),
            colors = menuColors(M3MenuTheme.STANDARD),
            showFocusIndicator = showFocusIndicator,
            onClick = onClick,
        )
        if (showDivider) {
            Divider(
                modifier = Modifier.padding(vertical = M3MenuMetrics.dividerVerticalPadding),
                thickness = M3MenuMetrics.dividerHeight,
                color = MaterialTheme.colorScheme.outlineVariant,
            )
        }
    }
}

/** Vibrant 菜单项（可单独使用）。 */
@Composable
fun M3MenuItemVibrant(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingText: String? = null,
    showLeadingElement: Boolean = true,
    showTrailingElement: Boolean = true,
    showDivider: Boolean = false,
    showFocusIndicator: Boolean = true,
) {
    Column(modifier.fillMaxWidth()) {
        M3MenuRow(
            item = M3MenuItemData(
                label = label,
                leadingIcon = if (showLeadingElement) leadingIcon else null,
                trailingText = if (showTrailingElement) trailingText else null,
                enabled = enabled,
                selected = selected,
            ),
            colors = menuColors(M3MenuTheme.VIBRANT),
            showFocusIndicator = showFocusIndicator,
            onClick = onClick,
        )
        if (showDivider) {
            Divider(
                modifier = Modifier.padding(vertical = M3MenuMetrics.dividerVerticalPadding),
                thickness = M3MenuMetrics.dividerHeight,
                color = MaterialTheme.colorScheme.outlineVariant,
            )
        }
    }
}

/** Baseline menu：方角 4dp，用 DropdownMenuItem 原生样式。 */
@Composable
fun M3MenuBaseline(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<String>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    density: M3MenuDensity = M3MenuDensity.DEFAULT,
) {
    val verticalPadding = when (density) {
        M3MenuDensity.COMPACT -> 4.dp
        M3MenuDensity.DEFAULT -> 8.dp
        M3MenuDensity.COMFORTABLE -> 12.dp
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier.widthIn(min = M3MenuMetrics.minWidth, max = M3MenuMetrics.maxWidth),
        shape = RoundedCornerShape(M3MenuMetrics.baselineCorner),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = M3MenuMetrics.elevation,
    ) {
        items.forEachIndexed { index, label ->
            DropdownMenuItem(
                text = { Text(label) },
                onClick = { onItemClick(index) },
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    horizontal = M3MenuMetrics.horizontalPadding,
                    vertical = verticalPadding,
                ),
            )
        }
    }
}

@Composable
private fun M3MenuRow(
    item: M3MenuItemData,
    colors: MenuColors,
    showFocusIndicator: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val focusModifier = if (focused && showFocusIndicator) {
        Modifier.border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(4.dp))
    } else {
        Modifier
    }
    val background = if (item.selected) colors.selectedContainer else androidx.compose.ui.graphics.Color.Transparent
    val contentColor = when {
        !item.enabled -> colors.content.copy(alpha = 0.38f)
        item.selected -> colors.selectedContent
        else -> colors.content
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(M3MenuMetrics.itemHeight)
            .background(background)
            .then(focusModifier)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = item.enabled,
                onClick = onClick,
            )
            .padding(horizontal = M3MenuMetrics.horizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (item.leadingIcon != null) {
            Icon(
                imageVector = item.leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(M3MenuMetrics.iconSize),
                tint = contentColor,
            )
            Spacer(Modifier.width(M3MenuMetrics.elementGap))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.label,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (item.supportingText != null) {
                Text(
                    text = item.supportingText,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.supporting,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        if (item.selected) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier.size(M3MenuMetrics.iconSize),
                tint = contentColor,
            )
        } else if (item.trailingText != null) {
            Text(
                text = item.trailingText,
                style = MaterialTheme.typography.labelMedium,
                color = colors.supporting,
            )
        }
    }
}
