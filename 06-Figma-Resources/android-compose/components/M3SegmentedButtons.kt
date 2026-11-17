package com.example.m3appbar.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3SegmentedButtonDensity

/** 单个 segment。label 与 icon 至少给一个。 */
data class M3Segment(
    val label: String,
    val icon: ImageVector? = null,
)

/**
 * Segmented button：2–5 段；单选 / 多选；Density 4 档（高度 40/36/32/28dp）。
 *
 * 官方已声明 Expressive 后不再推荐，替代品是 connected button group；
 * 本组件用于存量 UI 或未升级项目。shape 必须用 SegmentedButtonDefaults.itemShape。
 */
@Composable
fun M3SegmentedButton(
    segments: List<M3Segment>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    density: M3SegmentedButtonDensity = M3SegmentedButtonDensity.D0,
    multiSelect: Boolean = false,
    selectedIndices: Set<Int> = setOf(selectedIndex),
    onSelectionChange: (Set<Int>) -> Unit = {},
) {
    if (multiSelect) {
        MultiChoiceSegmentedButtonRow(modifier = modifier) {
            segments.forEachIndexed { index, segment ->
                SegmentedButton(
                    checked = selectedIndices.contains(index),
                    onCheckedChange = { checked ->
                        val next = selectedIndices.toMutableSet()
                        if (checked) next.add(index) else next.remove(index)
                        onSelectionChange(next)
                    },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = segments.size),
                    modifier = Modifier.height(density.height),
                    icon = if (segment.icon != null) {
                        { androidx.compose.material3.Icon(segment.icon, contentDescription = null) }
                    } else {
                        { SegmentedButtonDefaults.Icon(active = selectedIndices.contains(index)) }
                    },
                ) {
                    Text(segment.label)
                }
            }
        }
    } else {
        SingleChoiceSegmentedButtonRow(modifier = modifier) {
            segments.forEachIndexed { index, segment ->
                SegmentedButton(
                    selected = selectedIndex == index,
                    onClick = { onSelect(index) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = segments.size),
                    modifier = Modifier.height(density.height),
                    icon = if (segment.icon != null) {
                        { androidx.compose.material3.Icon(segment.icon, contentDescription = null) }
                    } else {
                        { SegmentedButtonDefaults.Icon(active = selectedIndex == index) }
                    },
                ) {
                    Text(segment.label)
                }
            }
        }
    }
}
