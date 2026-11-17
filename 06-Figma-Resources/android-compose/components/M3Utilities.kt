package com.example.m3appbar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3KeyboardMetrics

/**
 * Keyboard 视觉 mock（非系统 IME）：4 种 Configuration × 3 种 Style。
 * 仅用于设计系统展示；不负责输入、不连接 InputConnection。
 */
@Composable
fun M3Keyboard(
    modifier: Modifier = Modifier,
    configuration: Configuration = Configuration.BASE_KEYBOARD,
    style: Style = Style.PORTRAIT,
) {
    val rows = when (configuration) {
        Configuration.ALPHANUMERIC -> listOf("1234567890", "QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM")
        Configuration.KEYPAD -> listOf("123", "456", "789", "*0#")
        Configuration.NUMERIC_ONLY -> listOf("123", "456", "789", "0")
        else -> listOf("QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM")
    }
    val height = if (style == Style.LANDSCAPE) M3KeyboardMetrics.landscapeHeight else M3KeyboardMetrics.portraitHeight
    val widthModifier = if (style == Style.FLOATING) Modifier.width(M3KeyboardMetrics.floatingWidth) else Modifier.fillMaxWidth()
    Surface(
        modifier = modifier.then(widthModifier),
        shape = RoundedCornerShape(if (style == Style.FLOATING) 28.dp else 12.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            rows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth().height(height / rows.size),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    row.forEach { key ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(
                                    MaterialTheme.colorScheme.surfaceContainerHighest,
                                    RoundedCornerShape(6.dp),
                                )
                                .clickable { },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(key.toString(), style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}
