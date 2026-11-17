package com.example.m3appbar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.m3appbar.ui.M3ButtonGroupSelection
import com.example.m3appbar.ui.M3ButtonGroupShape
import com.example.m3appbar.ui.M3ButtonGroupSize
import com.example.m3appbar.ui.M3ConnectedButtonGroupMetrics

/**
 * Connected button group（Expressive）。
 *
 * 组内间距 2dp、最小宽 48dp、单行；选择模式 single / multi / required。
 * 尺寸 XS–XL 通过 ToggleButtonSize 控制。
 *
 * 说明：官方 ButtonGroup 容器的 toggleableItem 不暴露 buttonSize，
 * 这里用 Row + ToggleButton 实现连接形状与尺寸；overflow/宽度动画未做。
 */
@Composable
fun M3ConnectedButtonGroup(
    labels: List<String>,
    selected: Set<Int>,
    onSelectionChange: (Set<Int>) -> Unit,
    modifier: Modifier = Modifier,
    size: M3ButtonGroupSize = M3ButtonGroupSize.S,
    selection: M3ButtonGroupSelection = M3ButtonGroupSelection.SINGLE,
    shape: M3ButtonGroupShape = M3ButtonGroupShape.ROUND,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(M3ConnectedButtonGroupMetrics.spaceBetween),
    ) {
        labels.forEachIndexed { index, label ->
            val checked = selected.contains(index)
            ToggleButton(
                checked = checked,
                onCheckedChange = { next ->
                    val updated = when (selection) {
                        M3ButtonGroupSelection.SINGLE, M3ButtonGroupSelection.REQUIRED -> {
                            if (next) setOf(index) else if (selection == M3ButtonGroupSelection.REQUIRED) selected else emptySet()
                        }
                        M3ButtonGroupSelection.MULTI -> {
                            if (next) selected + index else selected - index
                        }
                    }
                    onSelectionChange(updated)
                },
                modifier = Modifier.weight(1f),
                buttonSize = when (size) {
                    M3ButtonGroupSize.XS -> ToggleButtonSize.ExtraSmall
                    M3ButtonGroupSize.S -> ToggleButtonSize.Small
                    M3ButtonGroupSize.M -> ToggleButtonSize.Medium
                    M3ButtonGroupSize.L -> ToggleButtonSize.Large
                    M3ButtonGroupSize.XL -> ToggleButtonSize.ExtraLarge
                },
                shapes = when {
                    shape == M3ButtonGroupShape.SQUARE -> {
                        val square = RoundedCornerShape(4.dp)
                        when {
                            index == 0 -> ButtonGroupDefaults.connectedLeadingButtonShapes(shape = square, pressedShape = square, checkedShape = square)
                            index == labels.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes(shape = square, pressedShape = square, checkedShape = square)
                            else -> ButtonGroupDefaults.connectedMiddleButtonShapes(shape = square, pressedShape = square, checkedShape = square)
                        }
                    }
                    index == 0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                    index == labels.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                    else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                },
            ) {
                Text(label)
            }
        }
    }
}
