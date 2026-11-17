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

/** Cards 页面组件。参数来自 Figma componentPropertyDefinitions（真实默认值/选项）。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3StackedCard(
    modifier: Modifier = Modifier,
    vStyle: Style = Style.OUTLINED,
    vLayout: Layout = Layout.MEDIA_TEXT,
    bShowsecondaryaction: Boolean = true,
    tHeadertext: String = "Header",
    tSubheadtext: String = "Subhead",
    tTitletext: String = "Title",
    tSubtitletext: String = "Subtitle",
    tSupportingtext: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
    iSlotswap: Painter? = null,
    sContent: @Composable () -> Unit = {},
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    Card(onClick = onClick, modifier = modifier) { Text(tLabel, style = M3Typography.titleMedium, modifier = Modifier.padding(16.dp)) }
}

@Composable
fun M3HorizontalCard(
    modifier: Modifier = Modifier,
    vStyle: Style = Style.OUTLINED,
    vLayout: Layout = Layout.MEDIA_TEXT,
    tHeadertext: String = "Header",
    tSubheadtext: String = "Subhead",
    iSlotswap: Painter? = null,
    sContent: @Composable () -> Unit = {},
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    Card(onClick = onClick, modifier = modifier) { Text(tLabel, style = M3Typography.titleMedium, modifier = Modifier.padding(16.dp)) }
}
