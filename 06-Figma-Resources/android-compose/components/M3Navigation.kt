package com.example.m3appbar.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3NavBarItemLayout
import kotlinx.coroutines.launch
import com.example.m3appbar.ui.M3NavRailAlignment
import com.example.m3appbar.ui.M3NavigationMetrics

/** 导航目的地。 */
data class M3NavItem(
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector? = null,
    val enabled: Boolean = true,
    val badge: String? = null,
)

private fun Modifier.navBadge(item: M3NavItem): Modifier = this

@Composable
private fun NavBadgedIcon(item: M3NavItem, selected: Boolean) {
    val icon = if (selected) item.selectedIcon ?: item.icon else item.icon
    if (item.badge != null) {
        BadgedBox(badge = {
            Badge {
                Text(item.badge.take(4))
            }
        }) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(M3NavigationMetrics.barIconSize))
        }
    } else {
        Icon(icon, contentDescription = null, modifier = Modifier.size(M3NavigationMetrics.barIconSize))
    }
}

/**
 * Navigation Bar（底部导航）。
 *
 * 官方：高 80dp、指示器 64×32dp、图标 24dp、图标与标签 4dp。
 * layout = HORIZONTAL 时把图标与文字放进同一行（近似 Kit 的 Horizontal items）。
 */
@Composable
fun M3NavigationBar(
    items: List<M3NavItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    layout: M3NavBarItemLayout = M3NavBarItemLayout.VERTICAL,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBar(
        modifier = modifier.height(M3NavigationMetrics.barHeight),
        containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceContainer,
    ) {
        items.forEachIndexed { index, item ->
            val selected = selectedIndex == index
            if (layout == M3NavBarItemLayout.HORIZONTAL) {
                NavigationBarItem(
                    selected = selected,
                    onClick = { onSelect(index) },
                    enabled = item.enabled,
                    icon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            NavBadgedIcon(item, selected)
                            Spacer(Modifier.width(M3NavigationMetrics.barIconLabelGap))
                            Text(item.label)
                        }
                    },
                    label = null,
                )
            } else {
                NavigationBarItem(
                    selected = selected,
                    onClick = { onSelect(index) },
                    enabled = item.enabled,
                    icon = { NavBadgedIcon(item, selected) },
                    label = { Text(item.label) },
                    alwaysShowLabel = alwaysShowLabel,
                )
            }
        }
    }
}

/**
 * Navigation Rail（侧边导航）。
 *
 * 官方：宽 80dp、指示器 56×32dp、左右边距 12dp、项间距 12dp、项目标高 56dp。
 * alignment = CENTER 时在项前后加权重占位。
 */
@Composable
fun M3NavigationRail(
    items: List<M3NavItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    alignment: M3NavRailAlignment = M3NavRailAlignment.TOP,
    header: (@Composable ColumnScope.() -> Unit)? = null,
) {
    NavigationRail(
        modifier = modifier.width(M3NavigationMetrics.railWidth),
        containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceContainer,
        header = header,
    ) {
        if (alignment == M3NavRailAlignment.CENTER) {
            Spacer(Modifier.weight(1f))
        }
        items.forEachIndexed { index, item ->
            NavigationRailItem(
                selected = selectedIndex == index,
                onClick = { onSelect(index) },
                enabled = item.enabled,
                icon = { NavBadgedIcon(item, selectedIndex == index) },
                label = { Text(item.label) },
            )
        }
        if (alignment == M3NavRailAlignment.CENTER) {
            Spacer(Modifier.weight(1f))
        }
    }
}

/**
 * Modal Navigation Drawer。
 *
 * 官方：宽 360dp、sheet 内容侧圆角 16dp、item 高 56dp、指示器 336×56dp / 圆角 28dp。
 * 打开/关闭由调用方持有 DrawerState；系统返回需调用方拦截。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3NavigationDrawer(
    items: List<M3NavItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    header: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = modifier,
        drawerContent = {
            ModalDrawerSheet {
                header?.invoke()
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(item.label) },
                        selected = selectedIndex == index,
                        onClick = { onSelect(index) },
                        icon = { Icon(item.icon, contentDescription = null, modifier = Modifier.size(24.dp)) },
                        badge = item.badge?.let {
                            {
                                Badge {
                                    Text(it.take(4))
                                }
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                }
            }
        },
        content = content,
    )
}
