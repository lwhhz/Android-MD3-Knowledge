package com.example.m3appbar.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.border
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3ExtendedFabSize
import com.example.m3appbar.ui.M3FabColor
import com.example.m3appbar.ui.M3FabSize
import com.example.m3appbar.ui.M3InteractionState
import com.example.m3appbar.ui.m3FabColors
import com.example.m3appbar.ui.m3StateLayer
import com.example.m3appbar.ui.rememberM3InteractionState

/**
 * FAB：屏幕唯一首要动作。
 *
 * 官方规则：不要禁用（不可用时直接不显示），所以本 API 没有 enabled 参数。
 * 高度 40/56/80/96；颜色六套等价映射；状态层 hover 8% / focus 10% / press 10%；
 * hover 时 elevation 4（官方 States 表），静止 3 为社区通行值（KB 标注推断）。
 * 图标与圆角为 specs 图近似值，官方未给文字数值。
 */
@Composable
fun M3FAB(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: M3FabSize = M3FabSize.DEFAULT,
    color: M3FabColor = M3FabColor.PRIMARY_CONTAINER,
    showFocusIndicator: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val state = rememberM3InteractionState(enabled = true, interactionSource = interactionSource)
    val colors = m3FabColors(color)

    val elevation by animateDpAsState(
        targetValue = if (state == M3InteractionState.HOVERED) 4.dp else 3.dp,
        label = "M3FabElevation",
    )
    val shape = RoundedCornerShape(size.cornerRadius)

    Box(
        modifier = modifier
            .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick,
            )
            .focusable(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .shadow(elevation, shape)
                .size(size.height)
                .clip(shape)
                .background(colors.container)
                .then(
                    if (showFocusIndicator && state == M3InteractionState.FOCUSED) {
                        Modifier.border(2.dp, MaterialTheme.colorScheme.secondary, shape)
                    } else {
                        Modifier
                    },
                )
                .m3StateLayer(state, colors.content),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                modifier = Modifier.size(size.iconSize),
                tint = colors.content,
            )
        }
    }
}

/**
 * Extended FAB：带文字标签的 FAB。
 *
 * 高度 56/80/96；最小宽度 80dp；图标 24dp；内边距 16dp；
 * 字阶用 titleMedium（Expressive 由 labelLarge 提升，KB extended-fab.md）。
 */
@Composable
fun M3ExtendedFAB(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    contentDescription: String? = null,
    size: M3ExtendedFabSize = M3ExtendedFabSize.SMALL,
    color: M3FabColor = M3FabColor.PRIMARY_CONTAINER,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val state = rememberM3InteractionState(enabled = true, interactionSource = interactionSource)
    val colors = m3FabColors(color)
    val elevation by animateDpAsState(
        targetValue = if (state == M3InteractionState.HOVERED) 4.dp else 3.dp,
        label = "M3ExtendedFabElevation",
    )
    val shape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .shadow(elevation, shape)
                .defaultMinSize(minWidth = 80.dp)
                .height(size.height)
                .clip(shape)
                .background(colors.container)
                .m3StateLayer(state, colors.content)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(24.dp),
                    tint = colors.content,
                )
                Spacer(Modifier.width(12.dp))
            }
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = colors.content,
                maxLines = 1,
                softWrap = false,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun M3FabPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                M3FAB(Icons.Filled.Add, "添加", {}, size = M3FabSize.SMALL)
                M3FAB(Icons.Filled.Add, "添加", {}, size = M3FabSize.DEFAULT)
                M3FAB(Icons.Filled.Add, "添加", {}, size = M3FabSize.MEDIUM)
            }
            M3FAB(Icons.Filled.Add, "添加", {}, size = M3FabSize.LARGE)
            M3FAB(Icons.Filled.Edit, "创建", {}, color = M3FabColor.TERTIARY_CONTAINER)
            M3ExtendedFAB(label = "Compose", onClick = {}, imageVector = Icons.Filled.Edit, contentDescription = "写新消息")
            M3ExtendedFAB(label = "Checkout", onClick = {}, size = M3ExtendedFabSize.MEDIUM)
        }
    }
}
