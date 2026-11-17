package com.example.m3appbar.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Toggle 按钮颜色（仅 Expressive，四种样式；官方不给 text 样式）。
 *
 * 来源：KB buttons.md 的「Toggle 按钮」表（m3.material.io/components/buttons/specs）。
 * 未选中与选中是两套容器/内容角色；禁用统一 onSurface 12% / 38%。
 */
@Composable
fun m3ToggleButtonColors(
    style: M3ButtonStyle,
    enabled: Boolean,
    selected: Boolean,
): M3ButtonColors {
    val scheme = MaterialTheme.colorScheme
    val disabledContainer = scheme.onSurface.copy(alpha = 0.12f)
    val disabledContent = scheme.onSurface.copy(alpha = M3StateLayer.DISABLED_ALPHA)

    if (!enabled) {
        return when (style) {
            M3ButtonStyle.OUTLINED -> M3ButtonColors(
                container = Color.Transparent,
                content = disabledContent,
                border = disabledContainer,
                elevation = 0.dp,
            )
            else -> M3ButtonColors(
                container = disabledContainer,
                content = disabledContent,
                border = null,
                elevation = 0.dp,
            )
        }
    }

    return if (selected) {
        when (style) {
            M3ButtonStyle.ELEVATED -> M3ButtonColors(scheme.primary, scheme.onPrimary, null, 1.dp)
            M3ButtonStyle.TONAL -> M3ButtonColors(scheme.secondary, scheme.onSecondary, null, 0.dp)
            M3ButtonStyle.OUTLINED -> M3ButtonColors(scheme.inverseSurface, scheme.inverseOnSurface, null, 0.dp)
            else -> M3ButtonColors(scheme.primary, scheme.onPrimary, null, 0.dp)
        }
    } else {
        when (style) {
            M3ButtonStyle.ELEVATED -> M3ButtonColors(scheme.surfaceContainerLow, scheme.primary, null, 1.dp)
            M3ButtonStyle.TONAL -> M3ButtonColors(scheme.secondaryContainer, scheme.onSecondaryContainer, null, 0.dp)
            M3ButtonStyle.OUTLINED -> M3ButtonColors(
                container = Color.Transparent,
                content = scheme.onSurfaceVariant,
                border = scheme.outlineVariant,
                elevation = 0.dp,
            )
            else -> M3ButtonColors(scheme.surfaceContainer, scheme.onSurfaceVariant, null, 0.dp)
        }
    }
}
