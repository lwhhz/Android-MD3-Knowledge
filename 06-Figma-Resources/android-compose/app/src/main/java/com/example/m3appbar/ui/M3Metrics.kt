package com.example.m3appbar.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 官方 Material 3 数值常量（不来自社区 Kit）。
 *
 * 来源与核验：
 * - 状态层：m3.material.io/foundations/interaction/states/state-layers
 *   KB 01-Foundations/interaction-states.md（2026-11-16）
 * - 按钮五档：m3.material.io/components/buttons/specs
 *   KB 02-Components/buttons.md（2026-11-16）
 *
 * 口径：D2「视觉以现行官方值为准」。Kit 固化的旧值（如 Pressed 12%）不在此使用。
 */

/** M3 六种交互状态（Disabled 不继承状态层，单独处理）。 */
enum class M3InteractionState {
    ENABLED,
    HOVERED,
    FOCUSED,
    PRESSED,
    DRAGGED,
    DISABLED,
}

/**
 * 状态层：半透明覆盖层，颜色取内容色，同一时刻只有一层。
 *
 * 官方数值：hover 8% · focus 10% · press 10% · drag 16% · disabled 38%。
 * 另：状态层视觉 40dp，可交互目标 48dp，两者不要混用。
 */
object M3StateLayer {
    const val HOVER_ALPHA = 0.08f
    const val FOCUS_ALPHA = 0.10f
    const val PRESS_ALPHA = 0.10f
    const val DRAG_ALPHA = 0.16f
    const val DISABLED_ALPHA = 0.38f

    /** 视觉层尺寸。 */
    val layerSize: Dp = 40.dp

    /** 触控热区尺寸。 */
    val targetSize: Dp = 48.dp

    /** 状态层透明度；Enabled 为 0（不叠层）。 */
    fun alphaFor(state: M3InteractionState): Float = when (state) {
        M3InteractionState.ENABLED -> 0f
        M3InteractionState.HOVERED -> HOVER_ALPHA
        M3InteractionState.FOCUSED -> FOCUS_ALPHA
        M3InteractionState.PRESSED -> PRESS_ALPHA
        M3InteractionState.DRAGGED -> DRAG_ALPHA
        M3InteractionState.DISABLED -> DISABLED_ALPHA
    }

    /** 用内容色 + 状态得到状态层颜色（颜色只从内容取，不另起灰）。 */
    fun colorFor(contentColor: Color, state: M3InteractionState): Color =
        contentColor.copy(alpha = alphaFor(state))
}

/**
 * 按钮五档（官方测量值）。
 *
 * 高度 / 水平内边距 / 图标尺寸 / 图标与文字间距 / 方角圆角 / 按下态圆角。
 * XS/XL 档的触控目标需补足到 48dp（官方要求）。
 */
/** 按钮颜色样式（M3 五种：filled / tonal / elevated / outlined / text）。 */
enum class M3ButtonStyle { FILLED, TONAL, ELEVATED, OUTLINED, TEXT }

/** 按钮形状变体（Figma Type = Round / Square）。 */
enum class M3ButtonShape { ROUND, SQUARE }

enum class M3ButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val iconSize: Dp,
    val iconGap: Dp,
    val cornerRadius: Dp,
    val pressedCornerRadius: Dp,
) {
    XS(32.dp, 12.dp, 20.dp, 4.dp, 12.dp, 8.dp),
    S(40.dp, 16.dp, 20.dp, 8.dp, 12.dp, 8.dp),
    M(56.dp, 24.dp, 24.dp, 8.dp, 16.dp, 12.dp),
    L(96.dp, 48.dp, 32.dp, 12.dp, 28.dp, 16.dp),
    XL(136.dp, 64.dp, 40.dp, 16.dp, 28.dp, 16.dp),
}
