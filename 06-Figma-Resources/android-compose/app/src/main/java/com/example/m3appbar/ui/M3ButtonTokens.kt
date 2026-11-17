package com.example.m3appbar.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** 一种按钮样式解析出的颜色、描边与高度。 */
data class M3ButtonColors(
    val container: Color,
    val content: Color,
    val border: Color?,
    val elevation: Dp,
)

/**
 * 五种按钮样式的官方颜色角色。
 *
 * 来源：m3.material.io/components/buttons/specs（KB buttons.md 2026-11-16 核验）。
 * 禁用：容器 onSurface 12%、内容 onSurface 38%、描边同 12%。
 */
@Composable
fun m3ButtonColors(style: M3ButtonStyle, enabled: Boolean): M3ButtonColors {
    val scheme = MaterialTheme.colorScheme
    val disabledContainer = scheme.onSurface.copy(alpha = 0.12f)
    val disabledContent = scheme.onSurface.copy(alpha = M3StateLayer.DISABLED_ALPHA)
    return when (style) {
        M3ButtonStyle.FILLED -> M3ButtonColors(
            container = if (enabled) scheme.primary else disabledContainer,
            content = if (enabled) scheme.onPrimary else disabledContent,
            border = null,
            elevation = 0.dp,
        )
        M3ButtonStyle.TONAL -> M3ButtonColors(
            container = if (enabled) scheme.secondaryContainer else disabledContainer,
            content = if (enabled) scheme.onSecondaryContainer else disabledContent,
            border = null,
            elevation = 0.dp,
        )
        M3ButtonStyle.ELEVATED -> M3ButtonColors(
            container = if (enabled) scheme.surfaceContainerLow else disabledContainer,
            content = if (enabled) scheme.primary else disabledContent,
            border = null,
            elevation = if (enabled) 1.dp else 0.dp,
        )
        M3ButtonStyle.OUTLINED -> M3ButtonColors(
            container = Color.Transparent,
            content = if (enabled) scheme.onSurfaceVariant else disabledContent,
            border = if (enabled) scheme.outlineVariant else disabledContainer,
            elevation = 0.dp,
        )
        M3ButtonStyle.TEXT -> M3ButtonColors(
            container = Color.Transparent,
            content = if (enabled) scheme.primary else disabledContent,
            border = null,
            elevation = 0.dp,
        )
    }
}
