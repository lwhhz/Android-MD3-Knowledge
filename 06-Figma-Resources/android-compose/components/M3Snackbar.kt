package com.example.m3appbar.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3SnackbarMetrics

/**
 * Snackbar 视觉：inverseSurface 容器、inverseOnSurface 文本、inversePrimary 动作。
 *
 * 单行 48dp、双行 64dp；最多一个 action；close affordance 可选。
 * 官方未给圆角/内边距数字，4dp 圆角与 16dp 内边距为近似值。
 */
@Composable
fun M3Snackbar(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    showCloseAffordance: Boolean = false,
    onDismiss: (() -> Unit)? = null,
    maxLines: Int = 1,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = M3SnackbarMetrics.maxWidth)
            .heightIn(
                min = if (maxLines > 1) M3SnackbarMetrics.doubleLineHeight else M3SnackbarMetrics.singleLineHeight,
            ),
        shape = RoundedCornerShape(M3SnackbarMetrics.cornerRadius),
        color = MaterialTheme.colorScheme.inverseSurface,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = M3SnackbarMetrics.horizontalPadding, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = message,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
            )
            if (actionLabel != null) {
                Spacer(Modifier.width(8.dp))
                TextButton(
                    onClick = { onAction?.invoke() },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.inversePrimary),
                ) {
                    Text(actionLabel)
                }
            }
            if (showCloseAffordance) {
                Spacer(Modifier.width(4.dp))
                IconButton(onClick = { onDismiss?.invoke() }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "关闭提示",
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.size(M3SnackbarMetrics.iconSize),
                    )
                }
            }
        }
    }
}

/** SnackbarHost：把 M3Snackbar 接到 SnackbarHostState。 */
@Composable
fun M3SnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    showCloseAffordance: Boolean = false,
) {
    SnackbarHost(hostState = hostState, modifier = modifier) { data: SnackbarData ->
        M3Snackbar(
            message = data.visuals.message,
            actionLabel = data.visuals.actionLabel,
            onAction = { data.performAction() },
            showCloseAffordance = showCloseAffordance || data.visuals.withDismissAction,
            onDismiss = { data.dismiss() },
            maxLines = maxLines,
        )
    }
}


/**
 * 根据是否有 action 决定持续时间：
 * - 无 action → SnackbarDuration.Short（约 4 秒，官方 4–10 秒区间下限）
 * - 有 action → SnackbarDuration.Indefinite（不自动消失，等待用户操作）
 */
fun m3SnackbarDuration(actionLabel: String?): SnackbarDuration =
    if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite

/** 按官方规则显示 Snackbar（自动选择持续时间）。 */
suspend fun SnackbarHostState.showM3Snackbar(
    message: String,
    actionLabel: String? = null,
    withDismissAction: Boolean = false,
): SnackbarResult = showSnackbar(
    message = message,
    actionLabel = actionLabel,
    withDismissAction = withDismissAction,
    duration = m3SnackbarDuration(actionLabel),
)
