package com.example.m3appbar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Suggestion chip —— 按 Figma 设计稿像素级还原的完整实现。
 *
 * 数据来源：Figma 组件 "Suggestion chip"（节点 53923:28679）的 48 个变体，
 * 颜色/尺寸/排版/状态层全部来自 componentPropertyDefinitions 与各变体节点的真实属性值。
 *
 * 设计规格（基准 85×32dp，无图标 67×32dp）：
 *  - 外形：圆角 8dp；高度 32dp；水平内边距 8dp
 *  - 图标：18×18dp 槽位，内部图形 14×14dp，距左 8dp，垂直居中
 *  - 文本：距左 34dp（即图标后 8dp），Roboto 14sp / w500 / 行高 20sp / 字距 0.1sp
 *  - 聚焦指示器：外扩 2dp（89×36dp），圆角 10dp，3dp 描边 #625B71
 *  - 状态层：pressed/hovered/focused/dragged 叠加半透明层（见颜色表）
 *  - Elevated 样式：容器填充 + elevation level1 阴影（black15% blur3 offsetY1 + black30% blur2 offsetY1）
 *  - Outlined 样式：透明容器 + 1dp 描边 #CAC4D0
 */

/** 芯片样式：Elevated（填充+阴影）/ Outlined（描边） */
enum class ChipStyleElevated { ELEVATED, OUTLINED }

/** 交互状态（Enabled 为默认；其余由组件根据 interactionSource 自动推断，也可手动指定） */
enum class ChipVisualState { ENABLED, HOVERED, FOCUSED, PRESSED, DRAGGED, DISABLED }

/** 单个状态下的颜色规格 —— 直接对应 Figma 变体的属性值 */
private data class ChipColors(
    val container: Color?,      // 容器填充；null = 透明
    val outline: Color?,        // 描边；null = 无
    val stateLayer: Color?,     // 状态层叠加色；null = 无
    val label: Color,
    val icon: Color,
    val elevation: Dp = 0.dp,
)

private val Unselected = Color(0xFF49454F)  // on-surface-variant
private val SelectedText = Color(0xFF4A4459) // on-secondary-container
private val Primary = Color(0xFF6750A4)
private val OnSurface = Color(0xFF1D1B20)
private val Outline = Color(0xFFCAC4D0)
private val SurfaceContainerLow = Color(0xFFF7F2FA)
private val SurfaceContainerLowest = Color(0xFFFEF7FF)
private val SecondaryContainer = Color(0xFFE8DEF8)

/**
 * 颜色查表 —— 严格对应 Figma 48 变体的颜色矩阵。
 * 选中态容器固定 #E8DEF8；未选中态 Elevated 容器 #F7F2FA、Outlined 透明+描边。
 */
private fun chipColors(
    style: ChipStyleElevated,
    state: ChipVisualState,
    selected: Boolean,
): ChipColors {
    if (state == ChipVisualState.DISABLED) {
        // 禁用态：Elevated 容器 = on-surface 10% 状态层；Outlined 描边 = on-surface 10%
        // 文字/图标 = #1D1B20（Figma 禁用态在此 kit 中保留了实色，透明度由容器层体现）
        return ChipColors(
            container = if (style == ChipStyleElevated.ELEVATED) OnSurface.copy(alpha = 0.10f) else null,
            outline = if (style == ChipStyleElevated.OUTLINED) OnSurface.copy(alpha = 0.10f) else null,
            stateLayer = null,
            label = OnSurface.copy(alpha = 0.38f),
            icon = OnSurface.copy(alpha = 0.38f),
        )
    }
    val baseContainer = when {
        selected -> SecondaryContainer
        style == ChipStyleElevated.ELEVATED -> SurfaceContainerLow
        else -> null // Outlined 未选中：透明
    }
    val outline = if (style == ChipStyleElevated.OUTLINED && !selected) Outline else null
    val layer = when (state) {
        ChipVisualState.HOVERED -> if (selected) SelectedText.copy(alpha = 0.08f) else Unselected.copy(alpha = 0.08f)
        ChipVisualState.FOCUSED -> if (selected) SelectedText.copy(alpha = 0.10f) else Unselected.copy(alpha = 0.10f)
        ChipVisualState.PRESSED ->
            if (selected) SelectedText.copy(alpha = 0.10f) else Unselected.copy(alpha = 0.10f)
        ChipVisualState.DRAGGED ->
            if (selected) SelectedText.copy(alpha = 0.16f) else Unselected.copy(alpha = 0.16f)
        else -> null
    }
    val label = if (selected) SelectedText else Unselected
    val icon = Primary
    val elev = if (style == ChipStyleElevated.ELEVATED) 1.dp else 0.dp
    // Outlined 未选中 + 非禁用：enabled 时容器透明，hover/pressed 时填 #FEF7FF
    val container = if (baseContainer == null && state != ChipVisualState.ENABLED) SurfaceContainerLowest else baseContainer
    return ChipColors(container, outline, layer, label, icon, elev)
}

/**
 * 像素级还原的 Suggestion chip。
 *
 * @param label 文本（Figma TEXT 参数 "Label text"，默认 "Label"）
 * @param style Elevated / Outlined（Figma VARIANT "Style"）
 * @param selected 是否选中（Figma VARIANT "Selected"）
 * @param showIcon 是否显示 leading icon（Figma VARIANT "Show icon"）
 * @param icon leading 图标（Figma INSTANCE_SWAP "Leading icon"，14×14 图形）
 * @param enabled 是否可用（对应 State=Disabled）
 * @param state 手动覆盖视觉状态；null 时根据交互自动推断
 * @param onClick 点击回调
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun M3SuggestionChipPixel(
    label: String = "Label",
    modifier: Modifier = Modifier,
    style: ChipStyleElevated = ChipStyleElevated.OUTLINED,
    selected: Boolean = false,
    showIcon: Boolean = true,
    icon: Painter? = null,
    enabled: Boolean = true,
    state: ChipVisualState? = null,
    onClick: () -> Unit = {},
) {
    val interaction = remember { MutableInteractionSource() }
    val hovered by interaction.collectIsHoveredAsState()
    val focused by interaction.collectIsFocusedAsState()
    val pressed by interaction.collectIsPressedAsState()
    val autoState = when {
        !enabled -> ChipVisualState.DISABLED
        pressed -> ChipVisualState.PRESSED
        focused -> ChipVisualState.FOCUSED
        hovered -> ChipVisualState.HOVERED
        else -> ChipVisualState.ENABLED
    }
    val s = state ?: autoState
    val c = chipColors(style, s, selected)

    val shape = RoundedCornerShape(8.dp)

    // Focus indicator：外扩 2dp 的 3dp 描边（仅 Focused 状态）。
    // 用 drawBehind 绘制扩展圆角矩形，避免负 padding（Compose 不允许）。
    val focusModifier = if (s == ChipVisualState.FOCUSED) {
        Modifier.drawBehind {
            val expand = 2.dp.toPx()
            drawRoundRect(
                color = Color(0xFF625B71),
                topLeft = Offset(-expand, -expand),
                size = Size(size.width + expand * 2, size.height + expand * 2),
                cornerRadius = CornerRadius(10.dp.toPx()),
                style = Stroke(width = 3.dp.toPx()),
            )
        }
    } else Modifier

    Box(modifier = modifier.then(focusModifier)) {
        Row(
            modifier = Modifier
                .height(32.dp)
                .clip(shape)
                .then(if (c.container != null) Modifier.background(c.container!!) else Modifier)
                .then(if (c.stateLayer != null) Modifier.background(c.stateLayer!!) else Modifier)
                .then(if (c.outline != null) Modifier.border(1.dp, c.outline!!, shape) else Modifier)
                .then(if (c.elevation > 0.dp) Modifier.shadow(c.elevation, shape) else Modifier)
                .clickable(
                    interactionSource = interaction,
                    indication = null, // 状态层由我们自己绘制，避免叠加 Material ripple
                    enabled = enabled,
                ) { onClick() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showIcon && icon != null) {
                Box(
                    Modifier
                        .padding(start = 8.dp)
                        .size(18.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(painter = icon, contentDescription = null, modifier = Modifier.size(14.dp))
                }
            }
            Text(
                text = label,
                color = c.label,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp, end = 16.dp),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SuggestionChipPreview() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 这里 icon 传 null 仅作预览；实际使用时传入 14dp 图形 Painter
        M3SuggestionChipPixel(label = "Outlined", style = ChipStyleElevated.OUTLINED, showIcon = false)
        M3SuggestionChipPixel(label = "Elevated", style = ChipStyleElevated.ELEVATED, showIcon = false)
        M3SuggestionChipPixel(label = "Selected", selected = true, showIcon = false)
        M3SuggestionChipPixel(label = "Disabled", enabled = false, showIcon = false)
    }
}
