package com.example.m3appbar.components

import androidx.compose.foundation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.example.m3appbar.ui.M3ButtonShape
import com.example.m3appbar.ui.M3ButtonStyle
import com.example.m3appbar.ui.m3ButtonColors
import com.example.m3appbar.ui.m3StateLayer
import com.example.m3appbar.ui.m3ToggleButtonColors
import com.example.m3appbar.ui.rememberM3InteractionState
import com.example.m3appbar.ui.M3ButtonSize
import com.example.m3appbar.ui.M3InteractionState
import com.example.m3appbar.ui.M3StateLayer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.m3appbar.M3Typography

/** Buttons 页面组件。参数来自 Figma componentPropertyDefinitions（真实默认值/选项）。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3SegmentedButton(
    modifier: Modifier = Modifier,
    vSegments: Segments = Segments.N2,
    vDensity: Density = Density.N0,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    Button(onClick = onClick, modifier = modifier) { Text(tLabel) }
}

@Composable
fun M3Button(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    style: M3ButtonStyle = M3ButtonStyle.FILLED,
    enabled: Boolean = true,
    showIcon: Boolean = false,
    icon: Painter? = null,
    showFocusIndicator: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val state = rememberM3InteractionState(enabled = enabled, interactionSource = interactionSource)

    // 触控目标至少 48dp；视觉尺寸由 size 决定（XS 32dp 时靠外层补足）。
    Box(
        modifier = modifier
            .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        M3ButtonVisual(
            label = label,
            state = state,
            size = size,
            shape = shape,
            style = style,
            showIcon = showIcon,
            icon = icon,
            showFocusIndicator = showFocusIndicator,
        )
    }
}

@Composable
fun M3ButtonVisual(
    label: String,
    state: M3InteractionState,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    style: M3ButtonStyle = M3ButtonStyle.FILLED,
    showIcon: Boolean = false,
    icon: Painter? = null,
    selectedIcon: Painter? = null,
    showFocusIndicator: Boolean = false,
    toggleable: Boolean = false,
    selected: Boolean = false,
) {
    val disabled = state == M3InteractionState.DISABLED
    val painted = if (toggleable) {
        m3ToggleButtonColors(style, enabled = !disabled, selected = selected)
    } else {
        m3ButtonColors(style, enabled = !disabled)
    }

    // Toggle 静止形状按官方语义变形：未选中圆 → 选中方；方形则相反。
    val restRadius = when {
        toggleable && selected -> if (shape == M3ButtonShape.ROUND) size.cornerRadius else size.height / 2f
        toggleable -> if (shape == M3ButtonShape.ROUND) size.height / 2f else size.cornerRadius
        shape == M3ButtonShape.ROUND -> size.height / 2f
        else -> size.cornerRadius
    }
    val pressed = state == M3InteractionState.PRESSED || state == M3InteractionState.DRAGGED
    val radius by animateDpAsState(
        targetValue = if (pressed) size.pressedCornerRadius else restRadius,
        label = "M3ButtonCornerRadius",
    )
    val buttonShape = RoundedCornerShape(radius)

    val ringModifier = if (state == M3InteractionState.FOCUSED && showFocusIndicator) {
        Modifier
            .border(3.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(radius + 2.dp))
            .padding(2.dp)
    } else {
        Modifier
    }

    Box(modifier = modifier.then(ringModifier)) {
        Box(
            modifier = Modifier
                .shadow(painted.elevation, buttonShape)
                .height(size.height)
                .clip(buttonShape)
                .background(painted.container)
                .m3StateLayer(state, painted.content)
                .then(
                    if (painted.border != null) {
                        Modifier.border(1.dp, painted.border, buttonShape)
                    } else {
                        Modifier
                    },
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = size.horizontalPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val effectiveIcon = if (toggleable && selected && selectedIcon != null) selectedIcon else icon
                if (showIcon && effectiveIcon != null) {
                    Image(
                        painter = effectiveIcon,
                        contentDescription = null,
                        modifier = Modifier.size(size.iconSize),
                        colorFilter = ColorFilter.tint(painted.content),
                    )
                    Spacer(Modifier.width(size.iconGap))
                }
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelLarge,
                    color = painted.content,
                    maxLines = 1,
                    softWrap = false,
                )
            }
        }
    }
}

@Composable
fun M3ButtonText(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    enabled: Boolean = true,
    showIcon: Boolean = false,
    icon: Painter? = null,
    showFocusIndicator: Boolean = false,
) = M3Button(
    label = label,
    onClick = onClick,
    modifier = modifier,
    size = size,
    shape = shape,
    style = M3ButtonStyle.TEXT,
    enabled = enabled,
    showIcon = showIcon,
    icon = icon,
    showFocusIndicator = showFocusIndicator,
)

@Composable
fun M3ButtonElevated(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    enabled: Boolean = true,
    showIcon: Boolean = false,
    icon: Painter? = null,
    showFocusIndicator: Boolean = false,
) = M3Button(
    label = label,
    onClick = onClick,
    modifier = modifier,
    size = size,
    shape = shape,
    style = M3ButtonStyle.ELEVATED,
    enabled = enabled,
    showIcon = showIcon,
    icon = icon,
    showFocusIndicator = showFocusIndicator,
)

@Composable
fun M3ButtonOutline(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    enabled: Boolean = true,
    showIcon: Boolean = false,
    icon: Painter? = null,
    showFocusIndicator: Boolean = false,
) = M3Button(
    label = label,
    onClick = onClick,
    modifier = modifier,
    size = size,
    shape = shape,
    style = M3ButtonStyle.OUTLINED,
    enabled = enabled,
    showIcon = showIcon,
    icon = icon,
    showFocusIndicator = showFocusIndicator,
)

@Composable
fun M3ButtonTonal(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonSize = M3ButtonSize.S,
    shape: M3ButtonShape = M3ButtonShape.ROUND,
    enabled: Boolean = true,
    showIcon: Boolean = false,
    icon: Painter? = null,
    showFocusIndicator: Boolean = false,
) = M3Button(
    label = label,
    onClick = onClick,
    modifier = modifier,
    size = size,
    shape = shape,
    style = M3ButtonStyle.TONAL,
    enabled = enabled,
    showIcon = showIcon,
    icon = icon,
    showFocusIndicator = showFocusIndicator,
)

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun M3ButtonStatesPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            M3InteractionState.entries.forEach { state ->
                M3ButtonVisual(
                    label = "State " + state.name.lowercase(),
                    state = state,
                    showFocusIndicator = true,
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun M3ButtonStylesPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            M3ButtonStyle.entries.forEach { style ->
                M3ButtonVisual(
                    label = style.name.lowercase().replaceFirstChar { it.uppercase() },
                    state = M3InteractionState.ENABLED,
                    style = style,
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun M3ButtonSizesPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            M3ButtonSize.entries.forEach { size ->
                M3ButtonVisual(label = "Size " + size.name, state = M3InteractionState.ENABLED, size = size)
            }
            M3ButtonVisual(label = "Square", state = M3InteractionState.ENABLED, shape = M3ButtonShape.SQUARE)
        }
    }
}
@Composable
fun M3ToggleButtonElevated(
    modifier: Modifier = Modifier,
    vType: Type = Type.ROUND,
    vSize: Size = Size.SMALL,
    vState: State = State.ENABLED,
    vSelected: Selected = Selected.FALSE,
    bShowicon: Boolean = true,
    bShowfocusindicator: Boolean = false,
    tLabeltext: String = "Label",
    iIcon: Painter? = null,
    iIconselected: Painter? = null,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    ElevatedButton(onClick = onClick, modifier = modifier) { Text(tLabel) }
}

@Composable
fun M3ToggleButtonOutline(
    modifier: Modifier = Modifier,
    vType: Type = Type.ROUND,
    vSize: Size = Size.SMALL,
    vState: State = State.ENABLED,
    vSelected: Selected = Selected.FALSE,
    bShowicon: Boolean = true,
    bShowfocusindicator: Boolean = false,
    tLabeltext: String = "Label",
    iIcon: Painter? = null,
    iIconselected: Painter? = null,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    OutlinedButton(onClick = onClick, modifier = modifier) { Text(tLabel) }
}

@Composable
fun M3ToggleButtonTonal(
    modifier: Modifier = Modifier,
    vType: Type = Type.ROUND,
    vSize: Size = Size.SMALL,
    vState: State = State.ENABLED,
    vSelected: Selected = Selected.FALSE,
    bShowicon: Boolean = true,
    bShowfocusindicator: Boolean = false,
    tLabeltext: String = "Label",
    iIcon: Painter? = null,
    iIconselected: Painter? = null,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    FilledTonalButton(onClick = onClick, modifier = modifier) { Text(tLabel) }
}

@Composable
fun M3SplitButton(
    modifier: Modifier = Modifier,
    vSize: Size = Size.SMALL,
    vColor: Color = Color.FILLED,
    vLeadingstate: LeadingState = LeadingState.ENABLED,
    vTrailingstate: TrailingState = TrailingState.ENABLED,
    bShowleadingicon: Boolean = true,
    bShowfocusindicator: Boolean = false,
    tLeadinglabeltext: String = "Label",
    iLeadingicon: Painter? = null,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    Button(onClick = onClick, modifier = modifier) { Text(tLabel) }
}

@Composable
fun M3ConnectedButtonGroup(
    modifier: Modifier = Modifier,
    vType: Type = Type.ROUND,
    vSize: Size = Size.XSMALL,
    bShow4thsegment: Boolean = false,
    bShow5thsegment: Boolean = false,
    bShow3rdsegment: Boolean = true,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    Button(onClick = onClick, modifier = modifier) { Text(tLabel) }
}

@Composable
fun M3StandardButtonGroup(
    modifier: Modifier = Modifier,
    vType: Type = Type.ROUND,
    vSize: Size = Size.XSMALL,
    vColor: Color = Color.FILLED,
    vButtontype: ButtonType = ButtonType.ICON,
    bShow5thbutton: Boolean = false,
    bShow6thbutton: Boolean = false,
    bShow7thbutton: Boolean = false,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    Button(onClick = onClick, modifier = modifier) { Text(tLabel) }
}
