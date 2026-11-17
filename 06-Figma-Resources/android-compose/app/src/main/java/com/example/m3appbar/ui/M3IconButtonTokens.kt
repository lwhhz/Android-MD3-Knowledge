package com.example.m3appbar.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * 图标按钮宽度三档（Expressive）。
 *
 * 官方 specs 只给图片无 dp 文字值；这里用相对高度的近似系数，
 * 0.75 / 1.0 / 1.25 × height，接入前可用 Figma 节点复核。
 */
enum class M3IconButtonWidth(val widthFactor: Float) {
    NARROW(0.75f),
    DEFAULT(1.0f),
    WIDE(1.25f),
}

/** 图标按钮四种颜色样式（按强调递减：filled > tonal > outlined > standard）。 */
enum class M3IconButtonStyle { FILLED, TONAL, OUTLINED, STANDARD }

/** 图标按钮解析出的容器色、内容色与描边。 */
data class M3IconButtonColors(
    val container: Color,
    val content: Color,
    val border: Color?,
)

/**
 * 图标按钮颜色角色。
 *
 * 来源：m3.material.io/components/icon-buttons/specs（KB icon-buttons.md 2026-11-16）。
 * Default 与 Toggle 用不同角色；Toggle 选中后 filled/tonal/outlined 都有独立映射。
 */
@Composable
fun m3IconButtonColors(
    style: M3IconButtonStyle,
    enabled: Boolean,
    toggleable: Boolean,
    selected: Boolean,
): M3IconButtonColors {
    val scheme = MaterialTheme.colorScheme
    val disabledContainer = scheme.onSurface.copy(alpha = 0.12f)
    val disabledContent = scheme.onSurface.copy(alpha = M3StateLayer.DISABLED_ALPHA)
    val transparent = Color.Transparent

    if (!enabled) {
        return when (style) {
            M3IconButtonStyle.OUTLINED -> M3IconButtonColors(transparent, disabledContent, disabledContainer)
            M3IconButtonStyle.STANDARD -> M3IconButtonColors(transparent, disabledContent, null)
            else -> M3IconButtonColors(disabledContainer, disabledContent, null)
        }
    }

    if (toggleable) {
        return if (selected) when (style) {
            M3IconButtonStyle.FILLED -> M3IconButtonColors(scheme.primary, scheme.onPrimary, null)
            M3IconButtonStyle.TONAL -> M3IconButtonColors(scheme.secondary, scheme.onSecondary, null)
            M3IconButtonStyle.OUTLINED -> M3IconButtonColors(scheme.inverseSurface, scheme.inverseOnSurface, null)
            M3IconButtonStyle.STANDARD -> M3IconButtonColors(transparent, scheme.primary, null)
        } else when (style) {
            M3IconButtonStyle.FILLED -> M3IconButtonColors(scheme.surfaceContainer, scheme.onSurfaceVariant, null)
            M3IconButtonStyle.TONAL -> M3IconButtonColors(scheme.secondaryContainer, scheme.onSecondaryContainer, null)
            M3IconButtonStyle.OUTLINED -> M3IconButtonColors(transparent, scheme.onSurfaceVariant, scheme.outlineVariant)
            M3IconButtonStyle.STANDARD -> M3IconButtonColors(transparent, scheme.onSurfaceVariant, null)
        }
    }

    return when (style) {
        M3IconButtonStyle.FILLED -> M3IconButtonColors(scheme.primary, scheme.onPrimary, null)
        M3IconButtonStyle.TONAL -> M3IconButtonColors(scheme.secondaryContainer, scheme.onSecondaryContainer, null)
        M3IconButtonStyle.OUTLINED -> M3IconButtonColors(transparent, scheme.onSurfaceVariant, scheme.outlineVariant)
        M3IconButtonStyle.STANDARD -> M3IconButtonColors(transparent, scheme.onSurfaceVariant, null)
    }
}
