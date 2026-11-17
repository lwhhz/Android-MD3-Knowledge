package com.example.m3appbar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalFloatingToolbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3ToolbarColor
import com.example.m3appbar.ui.M3ToolbarConfiguration
import com.example.m3appbar.ui.M3ToolbarMetrics
import com.example.m3appbar.ui.M3ToolbarOrientation

data class M3ToolbarOverflowItem(val label: String, val onClick: () -> Unit = {})

/**
 * Toolbar（Expressive）。
 *
 * 官方：高 64dp、外侧最小 16dp、元素 48dp 触控目标；
 * docked 铺满宽度直角，floating 水平距边 16dp / 垂直距边 24dp。
 *
 * 1.5.0-rc01 只有 FloatingToolbar API，没有 DockedToolbar composable；
 * docked 由 Surface + Row 按规格自绘。
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun M3Toolbar(
    modifier: Modifier = Modifier,
    configuration: M3ToolbarConfiguration = M3ToolbarConfiguration.DOCKED,
    orientation: M3ToolbarOrientation = M3ToolbarOrientation.HORIZONTAL,
    color: M3ToolbarColor = M3ToolbarColor.STANDARD,
    overflowItems: List<M3ToolbarOverflowItem> = emptyList(),
    floatingActionButton: (@Composable () -> Unit)? = null,
    scrollBehavior: FloatingToolbarScrollBehavior? = null,
    content: @Composable () -> Unit,
) {
    val effectiveOrientation = if (configuration == M3ToolbarConfiguration.DOCKED) {
        M3ToolbarOrientation.HORIZONTAL
    } else {
        orientation
    }

    if (configuration == M3ToolbarConfiguration.DOCKED) {
        Surface(
            modifier = modifier.fillMaxWidth().height(M3ToolbarMetrics.height),
            color = if (color == M3ToolbarColor.VIBRANT) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceContainer
            },
            shape = RoundedCornerShape(0.dp),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = M3ToolbarMetrics.outerPadding),
                horizontalArrangement = Arrangement.spacedBy(M3ToolbarMetrics.itemGap),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            ) {
                content()
                if (overflowItems.isNotEmpty()) {
                    ToolbarOverflowMenu(overflowItems)
                }
            }
        }
        return
    }

    val colors = if (color == M3ToolbarColor.VIBRANT) {
        FloatingToolbarDefaults.vibrantFloatingToolbarColors()
    } else {
        FloatingToolbarDefaults.standardFloatingToolbarColors()
    }

    if (effectiveOrientation == M3ToolbarOrientation.VERTICAL) {
        VerticalFloatingToolbar(
            expanded = true,
            modifier = modifier,
            colors = colors,
            scrollBehavior = scrollBehavior,
            floatingActionButton = floatingActionButton ?: {},
        ) {
            Column(
                modifier = Modifier.padding(horizontal = M3ToolbarMetrics.outerPadding),
                verticalArrangement = Arrangement.spacedBy(M3ToolbarMetrics.itemGap),
            ) {
                content()
                if (overflowItems.isNotEmpty()) {
                    ToolbarOverflowMenu(overflowItems)
                }
            }
        }
    } else {
        HorizontalFloatingToolbar(
            expanded = true,
            modifier = modifier,
            colors = colors,
            scrollBehavior = scrollBehavior,
            floatingActionButton = floatingActionButton ?: {},
        ) {
            Row(
                modifier = Modifier.padding(horizontal = M3ToolbarMetrics.outerPadding),
                horizontalArrangement = Arrangement.spacedBy(M3ToolbarMetrics.itemGap),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            ) {
                content()
                if (overflowItems.isNotEmpty()) {
                    ToolbarOverflowMenu(overflowItems)
                }
            }
        }
    }
}

@Composable
private fun ToolbarOverflowMenu(items: List<M3ToolbarOverflowItem>) {
    var open by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { open = true }) {
            Icon(Icons.Filled.MoreVert, contentDescription = "更多操作")
        }
        DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.label) },
                    onClick = {
                        item.onClick()
                        open = false
                    },
                )
            }
        }
    }
}
