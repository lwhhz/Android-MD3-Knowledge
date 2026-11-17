package com.example.m3appbar.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3SheetMetrics

/**
 * Bottom sheet：Modal / Standard 两种形态。
 *
 * 官方：宽最大 640dp、顶部圆角 28dp、拖拽手柄上下 22dp、容器 surfaceContainerLow。
 * Modal 有 scrim，Standard 无 scrim 且与主内容共存。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3BottomSheet(
    show: Boolean = true,
    onDismissRequest: () -> Unit = {},
    modifier: Modifier = Modifier,
    modal: Boolean = true,
    showDragHandle: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (!show) return
    val config = LocalConfiguration.current
    val sideMargin = M3SheetMetrics.sideMarginForWindow(config.screenWidthDp.toFloat())
    val initialMaxHeight = M3SheetMetrics.initialModalHeight(config.screenHeightDp.toFloat())
    if (modal) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            modifier = modifier.padding(horizontal = sideMargin).widthIn(max = M3SheetMetrics.maxWidth),
            sheetState = rememberModalBottomSheetState(),
            dragHandle = if (showDragHandle) { { BottomSheetDefaults.DragHandle() } } else null,
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = M3SheetMetrics.maxWidth)
                    .heightIn(max = initialMaxHeight)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                content()
            }
        }
    } else {
        Surface(
            modifier = modifier.fillMaxWidth().widthIn(max = M3SheetMetrics.maxWidth),
            shape = RoundedCornerShape(topStart = M3SheetMetrics.corner, topEnd = M3SheetMetrics.corner),
            color = MaterialTheme.colorScheme.surfaceContainerLow,
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                if (showDragHandle) {
                    BottomSheetDefaults.DragHandle(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                }
                content()
            }
        }
    }
}
