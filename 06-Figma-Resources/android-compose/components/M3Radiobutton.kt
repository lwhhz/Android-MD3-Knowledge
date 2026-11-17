package com.example.m3appbar.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.m3appbar.ui.m3FocusRing

/**
 * RadioButton。
 *
 * 官方：图标 20dp（与 Checkbox 的 18dp 刻意不同）、状态层 40dp、触控目标 48dp。
 * 组语义需要调用方在外部用 `Modifier.selectableGroup()` 包一层。
 */
@Composable
fun M3RadioButtons(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    showFocusIndicator: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val scheme = MaterialTheme.colorScheme
    val colors = RadioButtonDefaults.colors(
        selectedColor = if (isError) scheme.error else scheme.primary,
        unselectedColor = if (isError) scheme.error else scheme.onSurfaceVariant,
    )

    RadioButton(
        selected = selected,
        onClick = onClick,
        modifier = modifier.m3FocusRing(focused = focused, show = showFocusIndicator),
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
    )
}
