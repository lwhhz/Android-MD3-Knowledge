package com.example.m3appbar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3LayoutGridMetrics

/** Examples/Layout grid：按 Window size class 展示列数/边距；Layout regions 预留 navigation 区。 */
@Composable
fun M3ExamplesLayoutGrid(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass = WindowSizeClass.N1_COMPACT_0MINUS_599DP,
    layoutRegions: LayoutRegions = LayoutRegions.NONE,
) {
    val columns = M3LayoutGridMetrics.columnsFor(windowSizeClass)
    val margin = M3LayoutGridMetrics.marginFor(windowSizeClass)
    val navWidth = M3LayoutGridMetrics.navWidthFor(layoutRegions)
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "columns=" + columns + "  margin=" + margin.value.toInt() + "dp  nav=" + navWidth.value.toInt() + "dp",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(modifier = Modifier.fillMaxWidth().height(120.dp)) {
            if (navWidth > 0.dp) {
                Box(
                    modifier = Modifier.width(navWidth).fillMaxHeight()
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Nav", style = MaterialTheme.typography.labelMedium)
                }
            }
            Row(
                modifier = Modifier.weight(1f).fillMaxHeight().padding(horizontal = margin),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                repeat(columns) {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxHeight()
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)),
                    )
                }
            }
        }
    }
}
