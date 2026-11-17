package com.example.m3appbar.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * 官方十档形状映射到 Compose 的五个槽位。
 *
 * 官方十档：0 / 4 / 8 / 12 / 16 / 20 / 28 / 32 / 48 / full
 * Compose Shapes 只有五槽：none / 20 / 32 / 48 / full 需就地写 RoundedCornerShape。
 * 来源：KB 05-Design-Tokens/shapes.json（2026-11-15 核验）。
 */
internal val M3KitShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

/** 十档里 Compose 五槽缺的档位，按需就地使用。 */
object M3CornerRadius {
    val none = 0.dp
    val largeIncreased = 20.dp
    val extraLargeIncreased = 32.dp
    val extraExtraLarge = 48.dp
}
