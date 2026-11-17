package com.example.m3appbar.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.m3appbar.ui.M3TextFieldMetrics
import com.example.m3appbar.ui.M3TextFieldStyle

/**
 * 文本输入框（Filled / Outlined）。
 *
 * 尺寸/内边距沿用官方 56dp 容器与 Compose 默认内容内边距（8/16/12dp），
 * 颜色与状态（hover/focus/disabled/error）走 Material 3 的 TextField 默认 token，
 * 不自写 height —— KB 明确指出硬写高度会与内边距冲突。
 *
 * 参数覆盖 Figma Text field 页面 10 条规格：
 * Style、State、Text configurations（Input/Label/Placeholder）、Leading/Trailing icon、
 * Show supporting text、Label/Input/Placeholder/Supporting text。
 */
@Composable
fun M3TextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    supportingText: String? = null,
    showSupportingText: Boolean = true,
    errorText: String? = null,
    style: M3TextFieldStyle = M3TextFieldStyle.FILLED,
    enabled: Boolean = true,
    isError: Boolean = false,
    leadingIcon: ImageVector? = null,
    leadingIconDescription: String? = null,
    trailingIcon: ImageVector? = null,
    trailingIconDescription: String? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
) {
    // 官方要求：错误文本替换辅助文本，而不是追加（避免布局跳动）。
    val supportingContent: (@Composable () -> Unit)? = if (showSupportingText) {
        val text = if (isError && errorText != null) errorText else supportingText
        if (text != null) {
            { Text(text) }
        } else {
            null
        }
    } else {
        null
    }

    val leadingContent: (@Composable () -> Unit)? = leadingIcon?.let { icon ->
        {
            Icon(
                imageVector = icon,
                contentDescription = leadingIconDescription,
                modifier = Modifier.size(M3TextFieldMetrics.iconSize),
            )
        }
    }

    // 官方强烈建议错误态显示错误图标；未显式提供 trailing 时自动补 Warning。
    val effectiveTrailing = if (isError && trailingIcon == null) Icons.Filled.Warning else trailingIcon
    val trailingContent: (@Composable () -> Unit)? = effectiveTrailing?.let { icon ->
        {
            val description = if (isError && trailingIconDescription == null) "Error" else trailingIconDescription
            if (onTrailingIconClick != null) {
                IconButton(onClick = onTrailingIconClick) {
                    Icon(
                        imageVector = icon,
                        contentDescription = description,
                        modifier = Modifier.size(M3TextFieldMetrics.iconSize),
                    )
                }
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = description,
                    modifier = Modifier.size(M3TextFieldMetrics.iconSize),
                )
            }
        }
    }

    if (style == M3TextFieldStyle.OUTLINED) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = modifier,
            placeholder = placeholder?.let { { Text(it) } },
            leadingIcon = leadingContent,
            trailingIcon = trailingContent,
            supportingText = supportingContent,
            isError = isError,
            enabled = enabled,
            singleLine = singleLine,
            readOnly = readOnly,
        )
    } else {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = modifier,
            placeholder = placeholder?.let { { Text(it) } },
            leadingIcon = leadingContent,
            trailingIcon = trailingContent,
            supportingText = supportingContent,
            isError = isError,
            enabled = enabled,
            singleLine = singleLine,
            readOnly = readOnly,
        )
    }
}
