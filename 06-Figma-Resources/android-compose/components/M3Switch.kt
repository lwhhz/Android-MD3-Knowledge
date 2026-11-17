package com.example.m3appbar.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.m3FocusRing

/**
 * Switch（含 handle 图标）。
 *
 * 官方：轨道 52×32dp、handle 16（未选）/24（选中）/28（按下）、图标 16dp、状态层 40dp、热区 48dp。
 * handle 动画（16→24→28）由 Material 3 Switch 自带。
 * Icon 参数 = 是否在 handle 里显示 X / ✓；官方要求图标必须清楚表达开/关。
 */
@Composable
fun M3Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showThumbIcon: Boolean = true,
    isError: Boolean = false,
    showFocusIndicator: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val scheme = MaterialTheme.colorScheme
    val colors = SwitchDefaults.colors(
        checkedThumbColor = if (isError) scheme.onError else scheme.onPrimary,
        checkedTrackColor = if (isError) scheme.error else scheme.primary,
        uncheckedThumbColor = if (isError) scheme.error else scheme.outline,
        uncheckedTrackColor = if (isError) scheme.errorContainer else scheme.surfaceContainerHighest,
        uncheckedBorderColor = if (isError) scheme.error else scheme.outline,
    )

    val thumbContent: (@Composable () -> Unit)? = if (showThumbIcon) {
        {
            Icon(
                imageVector = if (checked) Icons.Filled.Check else Icons.Filled.Close,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
            )
        }
    } else {
        null
    }

    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.m3FocusRing(focused = focused, show = showFocusIndicator),
        thumbContent = thumbContent,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
    )
}
