package com.example.m3appbar.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.carousel.CarouselItemScope
import androidx.compose.material3.carousel.HorizontalCenteredHeroCarousel
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3CarouselContext
import com.example.m3appbar.ui.M3CarouselLayout
import com.example.m3appbar.ui.M3CarouselMetrics

data class M3CarouselItem(
    val label: String,
    val color: Color = Color.Unspecified,
    val image: Painter? = null,
    val contentDescription: String? = null,
)

/**
 * Carousel（官方 material3.carousel 组件）。
 *
 * MULTI_BROWSE → HorizontalMultiBrowseCarousel；
 * HERO 复用 MultiBrowse；CENTER_ALIGNED_HERO → HorizontalCenteredHeroCarousel；
 * UNCONTAINED / MULTI_ASPECT → HorizontalUncontainedCarousel；
 * FULL_SCREEN 用 Uncontained + 全宽 item。
 *
 * 官方组件自带 snap、parallax、动态宽度与 maskClip；28dp 圆角通过 maskClip 应用。
 */
@Composable
fun M3Carousel(
    items: List<M3CarouselItem>,
    modifier: Modifier = Modifier,
    layout: M3CarouselLayout = M3CarouselLayout.MULTI_BROWSE,
    context: M3CarouselContext = M3CarouselContext.MOBILE,
    onItemClick: (Int) -> Unit = {},
    onShowAll: (() -> Unit)? = null,
) {
    val state = rememberCarouselState { items.size }
    val largeWidth = if (context == M3CarouselContext.DESKTOP) {
        M3CarouselMetrics.largeDesktop
    } else {
        M3CarouselMetrics.largeMobile
    }
    Column(modifier = modifier.fillMaxWidth()) {
        when (layout) {
            M3CarouselLayout.MULTI_BROWSE, M3CarouselLayout.HERO -> {
                HorizontalMultiBrowseCarousel(
                    state = state,
                    preferredItemWidth = largeWidth,
                    modifier = Modifier.fillMaxWidth(),
                    itemSpacing = M3CarouselMetrics.itemGap,
                    minSmallItemWidth = M3CarouselMetrics.smallMin,
                    maxSmallItemWidth = M3CarouselMetrics.smallMax,
                    contentPadding = PaddingValues(horizontal = M3CarouselMetrics.leadingPadding, vertical = M3CarouselMetrics.verticalPadding),
                ) { index ->
                    CarouselSurface(
                        item = items[index],
                        index = index,
                        onClick = { onItemClick(index) },
                    )
                }
            }
            M3CarouselLayout.CENTER_ALIGNED_HERO -> {
                HorizontalCenteredHeroCarousel(
                    state = state,
                    preferredItemWidth = largeWidth,
                    modifier = Modifier.fillMaxWidth(),
                    itemSpacing = M3CarouselMetrics.itemGap,
                    contentPadding = PaddingValues(horizontal = M3CarouselMetrics.leadingPadding, vertical = M3CarouselMetrics.verticalPadding),
                ) { index ->
                    CarouselSurface(
                        item = items[index],
                        index = index,
                        onClick = { onItemClick(index) },
                    )
                }
            }
            M3CarouselLayout.UNCONTAINED -> {
                HorizontalUncontainedCarousel(
                    state = state,
                    itemWidth = M3CarouselMetrics.uncontainedWidth,
                    modifier = Modifier.fillMaxWidth(),
                    itemSpacing = M3CarouselMetrics.itemGap,
                    contentPadding = PaddingValues(start = M3CarouselMetrics.leadingPadding, top = M3CarouselMetrics.verticalPadding, bottom = M3CarouselMetrics.verticalPadding),
                ) { index ->
                    CarouselSurface(
                        item = items[index],
                        index = index,
                        onClick = { onItemClick(index) },
                    )
                }
            }
            M3CarouselLayout.MULTI_ASPECT -> {
                val listState = rememberLazyListState()
                LazyRow(
                    state = listState,
                    modifier = Modifier.fillMaxWidth(),
                    flingBehavior = rememberSnapFlingBehavior(listState),
                    contentPadding = PaddingValues(start = M3CarouselMetrics.leadingPadding, top = M3CarouselMetrics.verticalPadding, bottom = M3CarouselMetrics.verticalPadding),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(M3CarouselMetrics.itemGap),
                ) {
                    itemsIndexed(items) { index, item ->
                        val width = M3CarouselMetrics.aspectWidths[index % M3CarouselMetrics.aspectWidths.size]
                        CarouselPlainItem(
                            item = item,
                            modifier = Modifier.width(width),
                            onClick = { onItemClick(index) },
                        )
                    }
                }
            }
        }
        if (onShowAll != null) {
            TextButton(onClick = { onShowAll() }, modifier = Modifier.padding(horizontal = 4.dp)) {
                Text("Show all")
            }
        }
    }
}

@Composable
private fun CarouselPlainItem(
    item: M3CarouselItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(M3CarouselMetrics.corner)
    if (item.image != null) {
        Box(modifier = modifier.height(180.dp).clip(shape).clickable { onClick() }) {
            Image(
                painter = item.image,
                contentDescription = item.contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            Text(
                text = item.label,
                modifier = Modifier.align(Alignment.BottomStart).padding(16.dp),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )
        }
    } else {
        Surface(
            modifier = modifier.height(180.dp).clickable { onClick() },
            shape = shape,
            color = if (item.color == Color.Unspecified) MaterialTheme.colorScheme.secondaryContainer else item.color,
        ) {
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.BottomStart) {
                Text(item.label, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
private fun CarouselItemScope.CarouselSurface(
    item: M3CarouselItem,
    index: Int,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(M3CarouselMetrics.corner),
) {
    val masked = Modifier.maskClip(shape)
    val showLabel = carouselItemDrawInfo.size > with(androidx.compose.ui.platform.LocalDensity.current) { 56.dp.toPx() }
    if (item.image != null) {
        Box(
            modifier = masked
                .height(180.dp)
                .fillMaxWidth()
                .clickable { onClick() },
        ) {
            Image(
                painter = item.image,
                contentDescription = item.contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            if (showLabel) {
                Text(
                    text = item.label,
                    modifier = Modifier.align(Alignment.BottomStart).padding(16.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                )
            }
        }
    } else {
        Surface(
            modifier = masked
                .height(180.dp)
                .fillMaxWidth()
                .clickable { onClick() },
            shape = shape,
            color = if (item.color == Color.Unspecified) MaterialTheme.colorScheme.secondaryContainer else item.color,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                contentAlignment = Alignment.BottomStart,
            ) {
                if (showLabel) {
                    Text(item.label, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

/** Full-screen：官方 Uncontained，item 宽 = 容器宽、页间距 16dp、无 contentPadding。 */
@Composable
fun M3CarouselFullScreen(
    items: List<M3CarouselItem>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit = {},
) {
    val state = rememberCarouselState { items.size }
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        HorizontalUncontainedCarousel(
            state = state,
            itemWidth = maxWidth,
            modifier = Modifier.fillMaxWidth().height(280.dp),
            itemSpacing = M3CarouselMetrics.fullScreenGap,
            contentPadding = PaddingValues(0.dp),
        ) { index ->
            CarouselSurface(
                item = items[index],
                index = index,
                onClick = { onItemClick(index) },
                shape = RoundedCornerShape(0.dp),
            )
        }
    }
}
