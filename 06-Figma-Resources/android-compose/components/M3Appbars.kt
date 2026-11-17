package com.example.m3appbar.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.m3appbar.M3Typography

/** App bars 页面组件。参数来自 Figma componentPropertyDefinitions（真实默认值/选项）。 */
// 0.4 去重：M3AppBar / M3BottomAppBar 的生成空壳已删除。
// 对外实现以 appbar/M3AppBar.kt、appbar/M3BottomAppBar.kt 为准（包名不同）。
// 本文件只保留 App bars 页独有的 XR 变体。

@Composable
fun M3XRAppBar(
    modifier: Modifier = Modifier,
    vElevation: Elevation = Elevation.SURFACE_CONTAINER,
    vConfiguration: Configuration = Configuration.CENTER_ALIGNED,
    bShow1sttrailingicon: Boolean = true,
    bShow2ndtrailingicon: Boolean = true,
    bShow3rdtrailingicon: Boolean = true,
    bShowleadingicon: Boolean = true,
    tHeadline: String = "Label",
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}
