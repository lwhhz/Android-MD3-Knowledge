package com.example.m3appbar.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import com.example.m3appbar.ui.m3FocusRing

/**
 * Checkbox（含 indeterminate 与 error 变体）。
 *
 * 官方：容器 18dp、圆角 2dp、状态层 40dp、触控目标 48dp（由 Checkbox 自带）。
 * Type 的 6 档 = checked × indeterminate × isError 的组合；State 交给 Material 3 默认状态层。
 */
@Composable
fun M3Checkboxes(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    indeterminate: Boolean = false,
    isError: Boolean = false,
    showFocusIndicator: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val scheme = MaterialTheme.colorScheme
    val colors = CheckboxDefaults.colors(
        checkedColor = if (isError) scheme.error else scheme.primary,
        checkmarkColor = if (isError) scheme.onError else scheme.onPrimary,
        uncheckedColor = if (isError) scheme.error else scheme.onSurfaceVariant,
    )
    val ringModifier = modifier.m3FocusRing(focused = focused, show = showFocusIndicator)

    if (indeterminate) {
        TriStateCheckbox(
            state = ToggleableState.Indeterminate,
            onClick = { onCheckedChange(!checked) },
            modifier = ringModifier,
            enabled = enabled,
            colors = colors,
            interactionSource = interactionSource,
        )
    } else {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = ringModifier,
            enabled = enabled,
            colors = colors,
            interactionSource = interactionSource,
        )
    }
}
