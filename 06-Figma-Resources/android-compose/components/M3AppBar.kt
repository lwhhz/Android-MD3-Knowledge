package com.example.m3appbar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/** VARIANT: Configuration。Expressive 的 medium/large 为 flexible。 */
enum class AppBarConfiguration {
    SMALL_CENTERED,
    SMALL_IMAGE,
    SEARCH,
    SMALL,
    MEDIUM_FLEXIBLE,
    LARGE_FLEXIBLE;

    companion object {
        fun from(value: String): AppBarConfiguration = when (value) {
            "Small-centered" -> SMALL_CENTERED
            "Small-image" -> SMALL_IMAGE
            "Search" -> SEARCH
            "Small" -> SMALL
            "Medium", "Medium flexible" -> MEDIUM_FLEXIBLE
            "Large", "Large flexible" -> LARGE_FLEXIBLE
            else -> SMALL_CENTERED
        }
    }
}

/** VARIANT: Elevation。 */
enum class AppBarElevation {
    FLAT,
    ON_SCROLL;

    companion object {
        fun from(value: String): AppBarElevation =
            if (value == "On-scroll") ON_SCROLL else FLAT
    }
}

data class TrailingAction(
    val icon: Painter,
    val description: String,
    val onClick: () -> Unit,
)

/**
 * App bar：Small / Small-centered / Search / Small-image / Medium flexible / Large flexible。
 *
 * 使用 Material3 TopAppBar / CenterAlignedTopAppBar / MediumFlexibleTopAppBar /
 * LargeFlexibleTopAppBar，滚动收缩完全交给 TopAppBarScrollBehavior。
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun M3AppBar(
    modifier: Modifier = Modifier,
    headline: String = "Label",
    supportingText: String? = null,
    configuration: AppBarConfiguration = AppBarConfiguration.SMALL_CENTERED,
    elevation: AppBarElevation = AppBarElevation.FLAT,
    leadingIcon: Painter? = null,
    onLeadingClick: (() -> Unit)? = null,
    leadingImage: Painter? = null,
    showTrailingAction1: Boolean = true,
    showTrailingAction2: Boolean = false,
    showTrailingAction3: Boolean = false,
    trailingActions: List<TrailingAction> = emptyList(),
    avatar: Painter? = null,
    onAvatarClick: (() -> Unit)? = null,
    onSearchClick: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val colors = TopAppBarDefaults.topAppBarColors(
        containerColor = if (elevation == AppBarElevation.ON_SCROLL) {
            MaterialTheme.colorScheme.surfaceContainer
        } else {
            Color.Transparent
        },
    )
    val navigationIcon: @Composable () -> Unit = {
        if (leadingIcon != null) {
            IconButton(onClick = { onLeadingClick?.invoke() }) {
                Icon(leadingIcon, contentDescription = null)
            }
        }
    }
    val actions: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit = {
        val visible = trailingActions.take(
            when {
                showTrailingAction3 -> 3
                showTrailingAction2 -> 2
                showTrailingAction1 -> 1
                else -> 0
            },
        )
        visible.forEach { action ->
            IconButton(onClick = action.onClick) {
                Icon(action.icon, contentDescription = action.description)
            }
        }
        if (avatar != null) {
            IconButton(onClick = { onAvatarClick?.invoke() }) {
                Image(
                    painter = avatar,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp).clip(CircleShape),
                )
            }
        }
    }
    val title: @Composable () -> Unit = {
        when (configuration) {
            AppBarConfiguration.SEARCH -> SearchEntry(onSearchClick)
            AppBarConfiguration.SMALL_IMAGE -> Row(verticalAlignment = Alignment.CenterVertically) {
                if (leadingImage != null) {
                    Image(
                        painter = leadingImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(40.dp).clip(CircleShape),
                    )
                    Spacer(Modifier.width(12.dp))
                }
                Text(headline, style = MaterialTheme.typography.titleLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            else -> Text(
                text = headline,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
    val subtitle: (@Composable () -> Unit)? = supportingText?.let { text ->
        {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
            )
        }
    }
    val scrolled = if (elevation == AppBarElevation.ON_SCROLL) Modifier.shadow(3.dp) else Modifier

    when (configuration) {
        AppBarConfiguration.MEDIUM_FLEXIBLE -> MediumFlexibleTopAppBar(
            title = title,
            modifier = modifier.then(scrolled),
            subtitle = subtitle,
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors,
            scrollBehavior = scrollBehavior,
        )
        AppBarConfiguration.LARGE_FLEXIBLE -> LargeFlexibleTopAppBar(
            title = title,
            modifier = modifier.then(scrolled),
            subtitle = subtitle,
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors,
            scrollBehavior = scrollBehavior,
        )
        AppBarConfiguration.SMALL_CENTERED -> CenterAlignedTopAppBar(
            title = title,
            modifier = modifier.then(scrolled),
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors,
            scrollBehavior = scrollBehavior,
        )
        else -> TopAppBar(
            title = title,
            modifier = modifier.then(scrolled),
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors,
            scrollBehavior = scrollBehavior,
        )
    }
}

@Composable
private fun SearchEntry(onSearchClick: (() -> Unit)?) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(48.dp).clickable { onSearchClick?.invoke() },
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                painter = androidx.compose.ui.res.painterResource(android.R.drawable.ic_menu_search),
                contentDescription = null,
            )
            Text("Search", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
