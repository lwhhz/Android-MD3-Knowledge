package com.example.m3appbar.components

import com.example.m3appbar.M3Typography

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

/**
 * Bottom app bar —— 对应 Figma 组件 "Bottom app bar"
 * (key 0c59024002ce52a6c764dfd56ff8a9a0763be99b, 高度 80dp)
 *
 * @param iconsCount     [VARIANT: Icons] 显示图标数量 1~4，默认 1
 * @param showFab        [BOOLEAN: Show FAB] 是否显示悬浮按钮，默认 true
 * @param actions        图标槽位（前 iconsCount 个生效）
 * @param fab            FAB 图标
 * @param onFabClick     FAB 点击
 */
@Composable
fun M3BottomAppBar(
    modifier: Modifier = Modifier,
    iconsCount: Int = 1,
    showFab: Boolean = true,
    containerColor: Color? = null,
    actions: List<TrailingAction> = emptyList(),
    fab: Painter? = null,
    onFabClick: (() -> Unit)? = null,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        color = containerColor ?: MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                actions.take(iconsCount.coerceIn(0, 4)).forEach { action ->
                    IconButton(onClick = action.onClick, modifier = Modifier.size(48.dp)) {
                        Icon(action.icon, contentDescription = action.description, tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
            if (showFab && fab != null) {
                androidx.compose.material3.FloatingActionButton(
                    onClick = { onFabClick?.invoke() },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ) {
                    Icon(fab, contentDescription = "FAB")
                }
            }
        }
    }
}

/** VARIANT: Elevation — XR App Bar 的容器层级 */
enum class XrElevation {
    SURFACE_CONTAINER,
    SURFACE_CONTAINER_HIGH,
    SURFACE_CONTAINER_HIGHEST;

    companion object {
        fun from(value: String): XrElevation = when (value) {
            "Surface container high" -> SURFACE_CONTAINER_HIGH
            "Surface container highest" -> SURFACE_CONTAINER_HIGHEST
            else -> SURFACE_CONTAINER
        }
    }
}

/** VARIANT: Configuration — XR App Bar 对齐方式 */
enum class XrConfiguration {
    CENTER_ALIGNED, SMALL;

    companion object {
        fun from(value: String): XrConfiguration =
            if (value == "Small") SMALL else CENTER_ALIGNED
    }
}

/**
 * XR App Bar —— 对应 Figma 组件 "XR/XR App Bar"
 * (key 1a042467d46532a0891224b53caaf65c0afab2c3, 高度 72dp)
 */
@Composable
fun M3XrAppBar(
    modifier: Modifier = Modifier,
    headline: String = "Label",
    elevation: XrElevation = XrElevation.SURFACE_CONTAINER,
    configuration: XrConfiguration = XrConfiguration.CENTER_ALIGNED,
    showLeadingIcon: Boolean = true,
    showTrailingIcon1: Boolean = true,
    showTrailingIcon2: Boolean = true,
    showTrailingIcon3: Boolean = true,
    leadingIcon: Painter? = null,
    trailingActions: List<TrailingAction> = emptyList(),
) {
    val containerColor = when (elevation) {
        XrElevation.SURFACE_CONTAINER -> MaterialTheme.colorScheme.surfaceContainer
        XrElevation.SURFACE_CONTAINER_HIGH -> MaterialTheme.colorScheme.surfaceContainerHigh
        XrElevation.SURFACE_CONTAINER_HIGHEST -> MaterialTheme.colorScheme.surfaceContainerHighest
    }
    Surface(
        modifier = modifier.fillMaxWidth().height(72.dp),
        color = containerColor,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showLeadingIcon && leadingIcon != null) {
                    IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
                        Icon(leadingIcon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
                androidx.compose.material3.Text(
                    text = headline,
                    style = M3Typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                val flags = listOf(showTrailingIcon1, showTrailingIcon2, showTrailingIcon3)
                trailingActions.take(3).forEachIndexed { index, action ->
                    if (flags.getOrNull(index) == true) {
                        IconButton(onClick = action.onClick, modifier = Modifier.size(48.dp)) {
                            Icon(action.icon, contentDescription = action.description, tint = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
    }
}
