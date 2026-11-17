package com.example.m3appbar.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3ButtonShape
import com.example.m3appbar.ui.M3ButtonSize
import com.example.m3appbar.ui.M3IconButtonStyle
import com.example.m3appbar.ui.M3IconButtonWidth
import com.example.m3appbar.ui.m3IconButtonColors
import com.example.m3appbar.ui.m3StateLayer
import com.example.m3appbar.ui.rememberM3InteractionState

/**
 * 图标按钮核心：8 个对外变体共用。
 *
 * 尺寸沿用按钮五档（32/40/56/96/136）；图标尺寸为官方图值近似
 * （XS 20 / S 24 / M 24 / L 32 / XL 40，S=24dp 为社区通行值，官方 specs 未给文字值）。
 * Toggle 形状 morph：Round 未选中为 full、选中变方角；Square 则相反（官方语义）。
 */
@Composable
private fun M3IconButtonCore(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: M3IconButtonStyle,
    size: M3ButtonSize,
    shape: M3ButtonShape,
    width: M3IconButtonWidth = M3IconButtonWidth.DEFAULT,
    enabled: Boolean,
    toggleable: Boolean,
    selected: Boolean,
    showFocusIndicator: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val state = rememberM3InteractionState(enabled = enabled, interactionSource = interactionSource)
    val colors = m3IconButtonColors(style, enabled, toggleable, selected)

    val baseRadius = if (shape == M3ButtonShape.ROUND) size.height / 2f else size.cornerRadius
    val toggledRadius = if (shape == M3ButtonShape.ROUND) size.cornerRadius else size.height / 2f
    val restRadius = if (toggleable && selected) toggledRadius else baseRadius
    val pressed = state == com.example.m3appbar.ui.M3InteractionState.PRESSED ||
        state == com.example.m3appbar.ui.M3InteractionState.DRAGGED
    val radius by animateDpAsState(
        targetValue = if (pressed) size.pressedCornerRadius else restRadius,
        label = "M3IconButtonRadius",
    )
    val shapeR = RoundedCornerShape(radius)

    val ringModifier = if (state == com.example.m3appbar.ui.M3InteractionState.FOCUSED && showFocusIndicator) {
        Modifier
            .border(3.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(radius + 2.dp))
            .padding(2.dp)
    } else {
        Modifier
    }

    val actionModifier = if (toggleable) {
        Modifier.toggleable(
            value = selected,
            interactionSource = interactionSource,
            indication = null,
            enabled = enabled,
            onValueChange = { onClick() },
        )
    } else {
        Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = enabled,
            role = Role.Button,
            onClick = onClick,
        )
    }

    Box(
        modifier = modifier
            .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
            .then(actionModifier),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .then(ringModifier)
                .width(size.height * width.widthFactor)
                .height(size.height)
                .clip(shapeR)
                .background(colors.container)
                .m3StateLayer(state, colors.content)
                .then(
                    if (colors.border != null) {
                        Modifier.border(1.dp, colors.border, shapeR)
                    } else {
                        Modifier
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                modifier = Modifier.size(size.iconButtonIconSize),
                tint = colors.content,
            )
        }
    }
}

private val M3ButtonSize.iconButtonIconSize: Dp
    get() = when (this) {
        M3ButtonSize.XS -> 20.dp
        M3ButtonSize.S -> 24.dp
        M3ButtonSize.M -> 24.dp
        M3ButtonSize.L -> 32.dp
        M3ButtonSize.XL -> 40.dp
    }

@Composable
fun M3IconButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    width: M3IconButtonWidth = M3IconButtonWidth.DEFAULT,
    enabled: Boolean = true,
    showFocusIndicator: Boolean = true,
) = M3IconButtonCore(
    imageVector = imageVector,
    contentDescription = contentDescription,
    onClick = onClick,
    modifier = modifier,
    style = M3IconButtonStyle.FILLED,
    size = size,
    shape = shape,
    width = width,
    enabled = enabled,
    toggleable = false,
    selected = false,
    showFocusIndicator = showFocusIndicator,
)

@Composable
fun M3IconButtonStandard(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    width: M3IconButtonWidth = M3IconButtonWidth.DEFAULT,
    enabled: Boolean = true,
    showFocusIndicator: Boolean = true,
) = M3IconButtonCore(
    imageVector = imageVector,
    contentDescription = contentDescription,
    onClick = onClick,
    modifier = modifier,
    style = M3IconButtonStyle.STANDARD,
    size = size,
    shape = shape,
    width = width,
    enabled = enabled,
    toggleable = false,
    selected = false,
    showFocusIndicator = showFocusIndicator,
)

@Composable
fun M3IconButtonOutline(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    width: M3IconButtonWidth = M3IconButtonWidth.DEFAULT,
    enabled: Boolean = true,
    showFocusIndicator: Boolean = true,
) = M3IconButtonCore(
    imageVector = imageVector,
    contentDescription = contentDescription,
    onClick = onClick,
    modifier = modifier,
    style = M3IconButtonStyle.OUTLINED,
    size = size,
    shape = shape,
    width = width,
    enabled = enabled,
    toggleable = false,
    selected = false,
    showFocusIndicator = showFocusIndicator,
)

@Composable
fun M3IconButtonTonal(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    width: M3IconButtonWidth = M3IconButtonWidth.DEFAULT,
    enabled: Boolean = true,
    showFocusIndicator: Boolean = true,
) = M3IconButtonCore(
    imageVector = imageVector,
    contentDescription = contentDescription,
    onClick = onClick,
    modifier = modifier,
    style = M3IconButtonStyle.TONAL,
    size = size,
    shape = shape,
    width = width,
    enabled = enabled,
    toggleable = false,
    selected = false,
    showFocusIndicator = showFocusIndicator,
)

@Composable
fun M3IconButtonTogglable(
    imageVector: ImageVector,
    contentDescription: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    width: M3IconButtonWidth = M3IconButtonWidth.DEFAULT,
    enabled: Boolean = true,
    showFocusIndicator: Boolean = true,
) = M3IconButtonCore(
    imageVector = imageVector,
    contentDescription = contentDescription,
    onClick = onClick,
    modifier = modifier,
    style = M3IconButtonStyle.FILLED,
    size = size,
    shape = shape,
    width = width,
    enabled = enabled,
    toggleable = true,
    selected = selected,
    showFocusIndicator = showFocusIndicator,
)

@Composable
fun M3IconButtonTogglableStandard(
    imageVector: ImageVector,
    contentDescription: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    width: M3IconButtonWidth = M3IconButtonWidth.DEFAULT,
    enabled: Boolean = true,
    showFocusIndicator: Boolean = true,
) = M3IconButtonCore(
    imageVector = imageVector,
    contentDescription = contentDescription,
    onClick = onClick,
    modifier = modifier,
    style = M3IconButtonStyle.STANDARD,
    size = size,
    shape = shape,
    width = width,
    enabled = enabled,
    toggleable = true,
    selected = selected,
    showFocusIndicator = showFocusIndicator,
)

@Composable
fun M3IconButtonTogglableOutline(
    imageVector: ImageVector,
    contentDescription: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    width: M3IconButtonWidth = M3IconButtonWidth.DEFAULT,
    enabled: Boolean = true,
    showFocusIndicator: Boolean = true,
) = M3IconButtonCore(
    imageVector = imageVector,
    contentDescription = contentDescription,
    onClick = onClick,
    modifier = modifier,
    style = M3IconButtonStyle.OUTLINED,
    size = size,
    shape = shape,
    width = width,
    enabled = enabled,
    toggleable = true,
    selected = selected,
    showFocusIndicator = showFocusIndicator,
)

@Composable
fun M3IconButtonTogglableTonal(
    imageVector: ImageVector,
    contentDescription: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    width: M3IconButtonWidth = M3IconButtonWidth.DEFAULT,
    enabled: Boolean = true,
    showFocusIndicator: Boolean = true,
) = M3IconButtonCore(
    imageVector = imageVector,
    contentDescription = contentDescription,
    onClick = onClick,
    modifier = modifier,
    style = M3IconButtonStyle.TONAL,
    size = size,
    shape = shape,
    width = width,
    enabled = enabled,
    toggleable = true,
    selected = selected,
    showFocusIndicator = showFocusIndicator,
)

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun M3IconButtonStylesPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            M3IconButton(imageVector = Icons.Filled.Add, contentDescription = "添加", onClick = {})
            M3IconButtonTonal(imageVector = Icons.Filled.Add, contentDescription = "添加", onClick = {})
            M3IconButtonOutline(imageVector = Icons.Filled.Add, contentDescription = "添加", onClick = {})
            M3IconButtonStandard(imageVector = Icons.Filled.Add, contentDescription = "添加", onClick = {})
        }
    }
}

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun M3IconButtonTogglePreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                M3IconButtonTogglable(imageVector = Icons.Filled.Check, contentDescription = "收藏", selected = false, onClick = {})
                M3IconButtonTogglable(imageVector = Icons.Filled.Check, contentDescription = "收藏", selected = true, onClick = {})
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                M3IconButtonTogglableTonal(imageVector = Icons.Filled.Check, contentDescription = "收藏", selected = false, onClick = {})
                M3IconButtonTogglableTonal(imageVector = Icons.Filled.Check, contentDescription = "收藏", selected = true, onClick = {})
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                M3IconButtonTogglableOutline(imageVector = Icons.Filled.Check, contentDescription = "收藏", selected = false, onClick = {})
                M3IconButtonTogglableOutline(imageVector = Icons.Filled.Check, contentDescription = "收藏", selected = true, onClick = {})
            }
        }
    }
}
