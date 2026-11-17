package com.example.m3appbar

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ---------------------------------------------------------------------------
// Figma 参数 -> Kotlin 类型映射
// 来源：组件 "App bar" (key 722fe239697fed85fc9a79a407fff6495ea93820)
// ---------------------------------------------------------------------------

/** VARIANT: Configuration — 决定标题排布与高度 */
enum class AppBarConfiguration {
    SMALL_CENTERED,   // Configuration=Small-centered
    SMALL_IMAGE,      // Configuration=Small-image
    SEARCH,           // Configuration=Search
    SMALL,            // Configuration=Small
    MEDIUM,           // Configuration=Medium
    LARGE;            // Configuration=Large

    /** 从 Figma variant 选项字符串解析 */
    companion object {
        fun from(value: String): AppBarConfiguration = when (value) {
            "Small-centered" -> SMALL_CENTERED
            "Small-image" -> SMALL_IMAGE
            "Search" -> SEARCH
            "Small" -> SMALL
            "Medium" -> MEDIUM
            "Large" -> LARGE
            else -> SMALL_CENTERED
        }
    }
}

/** VARIANT: Elevation — Flat 透明无阴影；On-scroll 容器色 + 滚动阴影 */
enum class AppBarElevation {
    FLAT,       // Elevation=Flat
    ON_SCROLL;  // Elevation=On-scroll

    companion object {
        fun from(value: String): AppBarElevation =
            if (value == "On-scroll") ON_SCROLL else FLAT
    }
}

/** 各 Configuration 对应的组件高度（dp），来自 Figma absoluteBoundingBox.height */
private fun AppBarConfiguration.height(): Dp = when (this) {
    AppBarConfiguration.SMALL_CENTERED,
    AppBarConfiguration.SMALL_IMAGE,
    AppBarConfiguration.SEARCH,
    AppBarConfiguration.SMALL -> 64.dp
    AppBarConfiguration.MEDIUM -> 112.dp
    AppBarConfiguration.LARGE -> 120.dp
}

/**
 * 主组件 —— 与 Figma "App bar" 组件参数一一对应。
 *
 * @param headline           [TEXT 参数] 标题文本，Figma 默认 "Label"
 * @param supportingText     辅助文本（中等/大高度下显示）
 * @param configuration      [VARIANT 参数] 标题排布方式
 * @param elevation          [VARIANT 参数] 高度/阴影层级
 * @param leadingIcon        [INSTANCE_SWAP 参数] leading 图标（默认菜单图标由调用方提供）
 * @param leadingImage       [INSTANCE_SWAP 参数] Image，仅 SMALL_IMAGE 配置使用
 * @param showTrailingAction1 [BOOLEAN 参数] 显示第 1 个尾部动作，默认 true
 * @param showTrailingAction2 [BOOLEAN 参数] 显示第 2 个尾部动作，默认 false
 * @param showTrailingAction3 [BOOLEAN 参数] 显示第 3 个尾部动作，默认 false
 * @param trailingActions    三个尾部动作的图标与点击事件（与上面 boolean 一一对应）
 * @param avatar             [INSTANCE_SWAP 参数] 尾部头像，可选
 * @param onSearchClick      SEARCH 配置下搜索框点击
 */
@OptIn(ExperimentalMaterial3Api::class)
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
) {
    val h = configuration.height()
    val containerColor = when (elevation) {
        AppBarElevation.FLAT -> Color.Transparent
        AppBarElevation.ON_SCROLL -> M3Colors.SurfaceContainer
    }
    val shadowElevation = when (elevation) {
        AppBarElevation.FLAT -> 0.dp
        AppBarElevation.ON_SCROLL -> 3.dp
    }
    val headlineStyle = when (configuration) {
        AppBarConfiguration.MEDIUM -> M3Typography.headlineSmall
        AppBarConfiguration.LARGE -> M3Typography.headlineMedium
        else -> M3Typography.titleLarge
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(h)
            .shadow(shadowElevation),
        color = containerColor,
    ) {
        when (configuration) {
            AppBarConfiguration.SEARCH -> SearchConfiguration(
                onSearchClick = onSearchClick,
                leadingIcon = leadingIcon,
                onLeadingClick = onLeadingClick,
                trailing = trailingActions,
                show1 = showTrailingAction1,
                show2 = showTrailingAction2,
                show3 = showTrailingAction3,
                avatar = avatar,
                onAvatarClick = onAvatarClick,
            )

            AppBarConfiguration.SMALL_IMAGE -> ImageConfiguration(
                image = leadingImage,
                headline = headline,
                headlineStyle = headlineStyle,
                trailing = trailingActions,
                show1 = showTrailingAction1,
                show2 = showTrailingAction2,
                show3 = showTrailingAction3,
                avatar = avatar,
                onAvatarClick = onAvatarClick,
            )

            else -> TextConfiguration(
                centered = configuration == AppBarConfiguration.SMALL_CENTERED,
                headline = headline,
                supportingText = supportingText,
                headlineStyle = headlineStyle,
                leadingIcon = leadingIcon,
                onLeadingClick = onLeadingClick,
                trailing = trailingActions,
                show1 = showTrailingAction1,
                show2 = showTrailingAction2,
                show3 = showTrailingAction3,
                avatar = avatar,
                onAvatarClick = onAvatarClick,
            )
        }
    }
}

/** 尾部动作：Figma 中 "Show Nth trailing action" 控制的图标槽位 */
data class TrailingAction(
    val icon: Painter,
    val description: String,
    val onClick: () -> Unit,
)

@Composable
private fun TextConfiguration(
    centered: Boolean,
    headline: String,
    supportingText: String?,
    headlineStyle: androidx.compose.ui.text.TextStyle,
    leadingIcon: Painter?,
    onLeadingClick: (() -> Unit)?,
    trailing: List<TrailingAction>,
    show1: Boolean, show2: Boolean, show3: Boolean,
    avatar: Painter?,
    onAvatarClick: (() -> Unit)?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (centered) Arrangement.Start else Arrangement.SpaceBetween,
    ) {
        LeadingSlot(leadingIcon, onLeadingClick)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            horizontalAlignment = if (centered) Alignment.CenterHorizontally else Alignment.Start,
        ) {
            Text(
                text = headline,
                style = headlineStyle,
                color = M3Colors.OnSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            if (supportingText != null) {
                Text(
                    text = supportingText,
                    style = M3Typography.labelMedium,
                    color = M3Colors.OnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        TrailingSlot(trailing, show1, show2, show3, avatar, onAvatarClick)
    }
}

@Composable
private fun ImageConfiguration(
    image: Painter?,
    headline: String,
    headlineStyle: androidx.compose.ui.text.TextStyle,
    trailing: List<TrailingAction>,
    show1: Boolean, show2: Boolean, show3: Boolean,
    avatar: Painter?,
    onAvatarClick: (() -> Unit)?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (image != null) {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(M3Colors.SurfaceContainerHigh),
                contentAlignment = Alignment.Center,
            ) {
                Icon(image, contentDescription = null, modifier = Modifier.size(32.dp))
            }
        }
        Text(
            text = headline,
            style = headlineStyle,
            color = M3Colors.OnSurface,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        TrailingSlot(trailing, show1, show2, show3, avatar, onAvatarClick)
    }
}

@Composable
private fun SearchConfiguration(
    onSearchClick: (() -> Unit)?,
    leadingIcon: Painter?,
    onLeadingClick: (() -> Unit)?,
    trailing: List<TrailingAction>,
    show1: Boolean, show2: Boolean, show3: Boolean,
    avatar: Painter?,
    onAvatarClick: (() -> Unit)?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LeadingSlot(leadingIcon, onLeadingClick)
        Surface(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .padding(horizontal = 4.dp)
                .clip(CircleShape)
                .clickable { onSearchClick?.invoke() },
            shape = CircleShape,
            color = M3Colors.SurfaceContainerHigh,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (leadingIcon != null) {
                    Icon(leadingIcon, contentDescription = null, Modifier.padding(start = 16.dp))
                }
                Text(
                    text = "Search",
                    style = M3Typography.bodyLarge,
                    color = M3Colors.OnSurfaceVariant,
                    modifier = Modifier.padding(start = 12.dp),
                )
            }
        }
        TrailingSlot(trailing, show1, show2, show3, avatar, onAvatarClick)
    }
}

@Composable
private fun LeadingSlot(icon: Painter?, onClick: (() -> Unit)?) {
    if (icon != null) {
        IconButton(onClick = { onClick?.invoke() }, modifier = Modifier.size(48.dp)) {
            Icon(icon, contentDescription = null, tint = M3Colors.OnSurface)
        }
    }
}

@Composable
private fun TrailingSlot(
    trailing: List<TrailingAction>,
    show1: Boolean, show2: Boolean, show3: Boolean,
    avatar: Painter?,
    onAvatarClick: (() -> Unit)?,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val flags = listOf(show1, show2, show3)
        trailing.take(3).forEachIndexed { index, action ->
            if (flags.getOrNull(index) == true) {
                IconButton(onClick = action.onClick, modifier = Modifier.size(48.dp)) {
                    Icon(action.icon, contentDescription = action.description, tint = M3Colors.OnSurface)
                }
            }
        }
        if (avatar != null) {
            Spacer(Modifier.width(4.dp))
            IconButton(onClick = { onAvatarClick?.invoke() }, modifier = Modifier.size(48.dp)) {
                Icon(avatar, contentDescription = "Avatar")
            }
        }
    }
}
