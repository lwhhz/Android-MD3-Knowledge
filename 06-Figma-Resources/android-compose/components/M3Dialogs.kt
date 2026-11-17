package com.example.m3appbar.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3DialogMetrics

/**
 * Basic dialog：28dp 圆角、宽 280–560dp、内边距 24dp。
 *
 * 图标可选（居中）；无图标时标题起始对齐；最多两个操作，确认键在最右侧。
 * 滚动内容/固定标题与按钮的规则见 M3ScrollableListDialog 与限制说明。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3BasicDialog(
    title: String,
    supportingText: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    confirmLabel: String = "OK",
    onConfirm: () -> Unit = onDismissRequest,
    dismissLabel: String? = null,
    onDismiss: (() -> Unit)? = null,
    icon: ImageVector? = null,
    showDivider: Boolean = false,
    content: (@Composable () -> Unit)? = null,
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest, modifier = modifier) {
        Surface(
            shape = RoundedCornerShape(M3DialogMetrics.corner),
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            tonalElevation = 6.dp,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(min = M3DialogMetrics.minWidth, max = M3DialogMetrics.maxWidth)
                    .padding(M3DialogMetrics.padding),
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(M3DialogMetrics.iconSize),
                    )
                    Spacer(Modifier.height(M3DialogMetrics.iconTitleGap))
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = if (icon != null) TextAlign.Center else TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(M3DialogMetrics.titleBodyGap))
                Text(
                    text = supportingText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                if (content != null) {
                    Spacer(Modifier.height(M3DialogMetrics.titleBodyGap))
                    content()
                }
                if (showDivider) {
                    Spacer(Modifier.height(M3DialogMetrics.bodyActionsGap))
                    Divider(thickness = M3DialogMetrics.dividerHeight)
                    Spacer(Modifier.height(8.dp))
                } else {
                    Spacer(Modifier.height(M3DialogMetrics.bodyActionsGap))
                }
                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(M3DialogMetrics.buttonGap),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (dismissLabel != null) {
                        TextButton(onClick = onDismiss ?: onDismissRequest) { Text(dismissLabel) }
                    }
                    TextButton(onClick = onConfirm) { Text(confirmLabel) }
                }
            }
        }
    }
}

/** List dialog：无操作按钮，点击列表项即选择。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3ListDialog(
    title: String,
    items: List<String>,
    onItemClick: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    M3DialogContainer(
        title = title,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        icon = icon,
    ) {
        items.forEachIndexed { index, label ->
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable { onItemClick(index) }
                    .padding(vertical = 12.dp),
            )
        }
    }
}

/** Scrollable list dialog：内容区限高 320dp，标题与底部固定。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3ScrollableListDialog(
    title: String,
    items: List<String>,
    onItemClick: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    M3DialogContainer(
        title = title,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        icon = icon,
    ) {
        Column(
            modifier = Modifier
                .heightIn(max = M3DialogMetrics.scrollMaxHeight)
                .verticalScroll(rememberScrollState()),
        ) {
            items.forEachIndexed { index, label ->
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable { onItemClick(index) }
                        .padding(vertical = 12.dp),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun M3DialogContainer(
    title: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    icon: ImageVector?,
    content: @Composable () -> Unit,
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest, modifier = modifier) {
        Surface(
            shape = RoundedCornerShape(M3DialogMetrics.corner),
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            tonalElevation = 6.dp,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(min = M3DialogMetrics.minWidth, max = M3DialogMetrics.maxWidth)
                    .padding(M3DialogMetrics.padding),
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(M3DialogMetrics.iconSize),
                    )
                    Spacer(Modifier.height(M3DialogMetrics.iconTitleGap))
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = if (icon != null) TextAlign.Center else TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(M3DialogMetrics.titleBodyGap))
                content()
            }
        }
    }
}
