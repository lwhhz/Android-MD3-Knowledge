package com.example.m3appbar.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3ButtonShape
import com.example.m3appbar.ui.M3ButtonSize
import com.example.m3appbar.ui.M3ButtonStyle
import com.example.m3appbar.ui.M3InteractionState
import com.example.m3appbar.ui.rememberM3InteractionState

/**
 * Toggle 按钮（仅 Expressive）。
 *
 * 四种样式，无 text 样式；未选中/选中两套颜色，选中后形状变形（圆→方 / 方→圆）。
 * 无文字的纯图标切换请用 M3IconButtonTogglable。
 */
@Composable
fun M3ToggleButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    style: M3ButtonStyle = M3ButtonStyle.FILLED,
    enabled: Boolean = true,
    showIcon: Boolean = false,
    icon: Painter? = null,
    selectedIcon: Painter? = null,
    showFocusIndicator: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val state = rememberM3InteractionState(enabled = enabled, interactionSource = interactionSource)

    Box(
        modifier = modifier
            .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
            .toggleable(
                value = selected,
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onValueChange = { onClick() },
            ),
        contentAlignment = Alignment.Center,
    ) {
        M3ButtonVisual(
            label = label,
            state = state,
            size = size,
            shape = shape,
            style = style,
            showIcon = showIcon,
            icon = icon,
            selectedIcon = selectedIcon,
            showFocusIndicator = showFocusIndicator,
            toggleable = true,
            selected = selected,
        )
    }
}

@Composable
fun M3ToggleButtonElevated(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    enabled: Boolean = true,
    showIcon: Boolean = false,
    icon: Painter? = null,
    selectedIcon: Painter? = null,
    showFocusIndicator: Boolean = false,
) = M3ToggleButton(
    label = label,
    selected = selected,
    onClick = onClick,
    modifier = modifier,
    size = size,
    shape = shape,
    style = M3ButtonStyle.ELEVATED,
    enabled = enabled,
    showIcon = showIcon,
    icon = icon,
    selectedIcon = selectedIcon,
    showFocusIndicator = showFocusIndicator,
)

@Composable
fun M3ToggleButtonOutline(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    enabled: Boolean = true,
    showIcon: Boolean = false,
    icon: Painter? = null,
    selectedIcon: Painter? = null,
    showFocusIndicator: Boolean = false,
) = M3ToggleButton(
    label = label,
    selected = selected,
    onClick = onClick,
    modifier = modifier,
    size = size,
    shape = shape,
    style = M3ButtonStyle.OUTLINED,
    enabled = enabled,
    showIcon = showIcon,
    icon = icon,
    selectedIcon = selectedIcon,
    showFocusIndicator = showFocusIndicator,
)

@Composable
fun M3ToggleButtonTonal(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    enabled: Boolean = true,
    showIcon: Boolean = false,
    icon: Painter? = null,
    selectedIcon: Painter? = null,
    showFocusIndicator: Boolean = false,
) = M3ToggleButton(
    label = label,
    selected = selected,
    onClick = onClick,
    modifier = modifier,
    size = size,
    shape = shape,
    style = M3ButtonStyle.TONAL,
    enabled = enabled,
    showIcon = showIcon,
    icon = icon,
    selectedIcon = selectedIcon,
    showFocusIndicator = showFocusIndicator,
)

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun M3ToggleButtonPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            M3ButtonStyle.entries.filter { it != M3ButtonStyle.TEXT }.forEach { style ->
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    M3ButtonVisual(
                        label = style.name.lowercase() + " off",
                        state = M3InteractionState.ENABLED,
                        style = style,
                        toggleable = true,
                        selected = false,
                    )
                    M3ButtonVisual(
                        label = style.name.lowercase() + " on",
                        state = M3InteractionState.ENABLED,
                        style = style,
                        toggleable = true,
                        selected = true,
                    )
                }
            }
        }
    }
}
