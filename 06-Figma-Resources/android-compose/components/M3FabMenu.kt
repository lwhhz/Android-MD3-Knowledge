package com.example.m3appbar.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3FabColor
import com.example.m3appbar.ui.m3FabColors
import com.example.m3appbar.ui.m3StateLayer
import com.example.m3appbar.ui.rememberM3InteractionState

/** FAB menu 的一项：图标 + 文字标签（官方要求两者都有）。 */
data class M3FabMenuItem(
    val label: String,
    val imageVector: ImageVector,
    val onClick: () -> Unit,
)

/**
 * FAB menu（仅 Expressive）。
 *
 * 官方硬规则：2–6 项、关闭按钮始终 56dp、从 FAB 同一位置展开、项必须有文字标签、
 * 关闭按钮语义为 "Toggle menu" 且播报 expanded/collapsed。
 *
 * 颜色：关闭按钮用与触发 FAB 相同的颜色集；菜单项用 surfaceContainerHigh 作对比容器
 * （官方只说“对比色”，具体角色未给文字值）。
 */
@Composable
fun M3FABMenu(
    imageVector: ImageVector,
    items: List<M3FabMenuItem>,
    modifier: Modifier = Modifier,
    color: M3FabColor = M3FabColor.PRIMARY_CONTAINER,
    initiallyExpanded: Boolean = false,
    contentDescription: String = "Toggle menu",
) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }
    val interactionSource = remember { MutableInteractionSource() }
    val state = rememberM3InteractionState(enabled = true, interactionSource = interactionSource)
    val colors = m3FabColors(color)
    val itemContainer = MaterialTheme.colorScheme.surfaceContainerHigh
    val itemContent = MaterialTheme.colorScheme.onSurface
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 45f else 0f,
        label = "M3FabMenuRotation",
    )
    val shape = RoundedCornerShape(16.dp)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .height(56.dp)
                            .clip(shape)
                            .background(itemContainer)
                            .clickable(role = Role.Button) {
                                item.onClick()
                                expanded = false
                            }
                            .padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = item.imageVector,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = itemContent,
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelLarge,
                            color = itemContent,
                            maxLines = 1,
                            softWrap = false,
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                .semantics {
                    stateDescription = if (expanded) "expanded" else "collapsed"
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button,
                ) { expanded = !expanded },
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .shadow(3.dp, shape)
                    .size(56.dp)
                    .clip(shape)
                    .background(colors.container)
                    .m3StateLayer(state, colors.content),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotation),
                    tint = colors.content,
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun M3FabMenuPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            M3FABMenu(
                imageVector = Icons.Filled.Add,
                items = listOf(
                    M3FabMenuItem("Search", Icons.Filled.Search, {}),
                    M3FabMenuItem("Add", Icons.Filled.Add, {}),
                    M3FabMenuItem("Done", Icons.Filled.Check, {}),
                ),
                initiallyExpanded = true,
            )
        }
    }
}
