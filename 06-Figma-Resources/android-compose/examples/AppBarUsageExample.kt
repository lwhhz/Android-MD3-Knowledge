package com.example.m3appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * 使用示例：展示 Figma 各参数组合如何映射到 M3AppBar。
 * 把 painterResource 中的 R.drawable.xxx 换成项目内实际图标资源即可。
 */
@Composable
fun AppBarShowcase() {
    Scaffold(
        topBar = {
            M3AppBar(
                headline = "Inbox",
                configuration = AppBarConfiguration.SMALL_CENTERED,
                elevation = AppBarElevation.ON_SCROLL,
                leadingIcon = painterResource(R.drawable.ic_menu),
                onLeadingClick = { /* 打开抽屉 */ },
                showTrailingAction1 = true,
                showTrailingAction2 = true,
                trailingActions = listOf(
                    TrailingAction(painterResource(R.drawable.ic_search), "搜索") {},
                    TrailingAction(painterResource(R.drawable.ic_more), "更多") {},
                ),
                avatar = painterResource(R.drawable.ic_avatar),
            )
        },
        bottomBar = {
            M3BottomAppBar(
                iconsCount = 2,
                showFab = true,
                actions = listOf(
                    TrailingAction(painterResource(R.drawable.ic_menu), "菜单") {},
                    TrailingAction(painterResource(R.drawable.ic_search), "搜索") {},
                ),
                fab = painterResource(R.drawable.ic_add),
                onFabClick = { /* 新建 */ },
            )
        },
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
            item { Text("Medium + Flat 配置：") }
            item {
                M3AppBar(
                    headline = "Title",
                    supportingText = "Supporting text",
                    configuration = AppBarConfiguration.MEDIUM,
                    elevation = AppBarElevation.FLAT,
                )
            }
            item { Text("Large + On-scroll 配置：") }
            item {
                M3AppBar(
                    headline = "Large title",
                    configuration = AppBarConfiguration.LARGE,
                    elevation = AppBarElevation.ON_SCROLL,
                )
            }
            item { Text("Search 配置：") }
            item {
                M3AppBar(
                    configuration = AppBarConfiguration.SEARCH,
                    onSearchClick = { /* 打开搜索 */ },
                )
            }
            item { Text("XR App Bar：") }
            item {
                M3XrAppBar(
                    headline = "XR Title",
                    elevation = XrElevation.SURFACE_CONTAINER_HIGH,
                    configuration = XrConfiguration.CENTER_ALIGNED,
                    leadingIcon = painterResource(R.drawable.ic_menu),
                )
            }
        }
    }
}
