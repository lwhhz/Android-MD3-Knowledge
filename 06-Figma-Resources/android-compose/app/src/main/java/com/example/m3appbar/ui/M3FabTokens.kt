package com.example.m3appbar.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * FAB 尺寸阶梯。
 *
 * 高度来自官方：regular 56 / small 40 / large 96（KB fab.md 未给全部文字值，56 有 fab-menu 佐证）；
 * medium 80 为 Expressive 档（与 extended FAB 对齐）。图标与圆角为规格图近似值，官方未给文字值。
 */
enum class M3FabSize(
    val height: Dp,
    val iconSize: Dp,
    val cornerRadius: Dp,
) {
    SMALL(40.dp, 24.dp, 12.dp),
    DEFAULT(56.dp, 24.dp, 16.dp),
    MEDIUM(80.dp, 28.dp, 20.dp),
    LARGE(96.dp, 36.dp, 28.dp),
}

/** FAB 六套等价颜色映射（container / content）。 */
enum class M3FabColor { PRIMARY_CONTAINER, SECONDARY_CONTAINER, TERTIARY_CONTAINER, PRIMARY, SECONDARY, TERTIARY }

/** Extended FAB 三档高度（Expressive 与 FAB 对齐：56 / 80 / 96）。 */
enum class M3ExtendedFabSize(val height: Dp) {
    SMALL(56.dp),
    MEDIUM(80.dp),
    LARGE(96.dp),
}

data class M3FabColors(val container: Color, val content: Color)

@Composable
fun m3FabColors(color: M3FabColor): M3FabColors {
    val scheme = MaterialTheme.colorScheme
    return when (color) {
        M3FabColor.PRIMARY_CONTAINER -> M3FabColors(scheme.primaryContainer, scheme.onPrimaryContainer)
        M3FabColor.SECONDARY_CONTAINER -> M3FabColors(scheme.secondaryContainer, scheme.onSecondaryContainer)
        M3FabColor.TERTIARY_CONTAINER -> M3FabColors(scheme.tertiaryContainer, scheme.onTertiaryContainer)
        M3FabColor.PRIMARY -> M3FabColors(scheme.primary, scheme.onPrimary)
        M3FabColor.SECONDARY -> M3FabColors(scheme.secondary, scheme.onSecondary)
        M3FabColor.TERTIARY -> M3FabColors(scheme.tertiary, scheme.onTertiary)
    }
}
