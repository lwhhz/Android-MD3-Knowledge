package com.example.m3appbar.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import androidx.compose.ui.unit.dp

/**
 * Plain tooltip：容器高 24dp、内边距 8dp、inverseSurface 反色。
 * Type.SINGLE_LINE 单行；Type.MULTI_LINE 最多两行（超过两行应改用 rich tooltip）。
 * 移动端长按触发；桌面悬停/聚焦由 TooltipBox 处理。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3PlainTooltip(
    text: String,
    modifier: Modifier = Modifier,
    type: Type = Type.SINGLE_LINE,
    content: @Composable () -> Unit,
) {
    val state = rememberTooltipState()
    val scope = rememberCoroutineScope()
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip(modifier = Modifier.heightIn(min = 24.dp)) {
                Text(
                    text = text,
                    maxLines = if (type == Type.MULTI_LINE) 2 else 1,
                    modifier = Modifier.padding(8.dp),
                )
            }
        },
        state = state,
        modifier = modifier.pointerInput(Unit) {
            awaitEachGesture {
                awaitFirstDown(requireUnconsumed = false, pass = PointerEventPass.Initial)
                val up = withTimeoutOrNull(600) {
                    waitForUpOrCancellation(pass = PointerEventPass.Initial)
                }
                if (up == null) {
                    scope.launch { state.show() }
                    waitForUpOrCancellation(pass = PointerEventPass.Initial)
                }
            }
        },
    ) {
        content()
    }
}
