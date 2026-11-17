package com.example.m3appbar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.m3appbar.ui.M3ToolbarColor
import com.example.m3appbar.ui.M3ToolbarConfiguration

/** 隔离的 App bar 滚动验收：列表与 app bar 在 Dialog 内，不受主看板 verticalScroll 干扰。 */
@Composable
fun M3AppBarScrollDemo(onDismissRequest: () -> Unit) {
    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Dialog(onDismissRequest = onDismissRequest, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(modifier = Modifier.fillMaxWidth().height(560.dp)) {
            Column {
                M3AppBar(
                    headline = "Flexible scroll",
                    supportingText = "Scroll to collapse",
                    configuration = AppBarConfiguration.MEDIUM_FLEXIBLE,
                    elevation = AppBarElevation.ON_SCROLL,
                    scrollBehavior = behavior,
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().height(448.dp).nestedScroll(behavior.nestedScrollConnection),
                ) {
                    items((1..30).toList()) { index ->
                        Text(
                            "App bar scroll item " + index,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        )
                    }
                }
            }
        }
    }
}

/** 隔离的 Toolbar 滚动验收。 */
@Composable
fun M3ToolbarScrollDemo(onDismissRequest: () -> Unit) {
    val behavior = androidx.compose.material3.FloatingToolbarDefaults.exitAlwaysScrollBehavior(
        exitDirection = androidx.compose.material3.FloatingToolbarExitDirection.Bottom,
    )
    Dialog(onDismissRequest = onDismissRequest, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(modifier = Modifier.fillMaxWidth().height(560.dp)) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().nestedScroll(behavior),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 96.dp),
                ) {
                    items((1..30).toList()) { index ->
                        Text(
                            "Toolbar scroll item " + index,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        )
                    }
                }
                M3Toolbar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    configuration = M3ToolbarConfiguration.FLOATING,
                    color = M3ToolbarColor.STANDARD,
                    scrollBehavior = behavior,
                ) {
                    IconButton(onClick = {}) { Icon(Icons.Filled.Add, contentDescription = "Add") }
                }
            }
        }
    }
}
