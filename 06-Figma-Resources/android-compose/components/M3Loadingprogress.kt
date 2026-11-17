package com.example.m3appbar.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3ProgressMetrics
import com.example.m3appbar.ui.M3ProgressThickness
import com.example.m3appbar.ui.M3ProgressType

/** 线性 determinate。Type = Flat/Wavy；Thickness 4/8dp。 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun M3LinearDeterminateProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    type: M3ProgressType = M3ProgressType.FLAT,
    thickness: M3ProgressThickness = M3ProgressThickness.FOUR_DP,
) {
    val track = MaterialTheme.colorScheme.secondaryContainer
    if (type == M3ProgressType.WAVY) {
        LinearWavyProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = track,
        )
    } else {
        LinearProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = modifier.fillMaxWidth().height(thickness.value),
            color = MaterialTheme.colorScheme.primary,
            trackColor = track,
        )
    }
}

/** 线性 indeterminate。 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun M3LinearIndeterminateProgressIndicator(
    modifier: Modifier = Modifier,
    type: M3ProgressType = M3ProgressType.FLAT,
    thickness: M3ProgressThickness = M3ProgressThickness.FOUR_DP,
    step: Int = 0,
) {
    val track = MaterialTheme.colorScheme.secondaryContainer
    if (type == M3ProgressType.WAVY) {
        LinearWavyProgressIndicator(
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = track,
        )
    } else {
        LinearProgressIndicator(
            modifier = modifier.fillMaxWidth().height(thickness.value),
            color = MaterialTheme.colorScheme.primary,
            trackColor = track,
        )
    }
}

/** 环形 determinate。 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun M3CircularDeterminateProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    type: M3ProgressType = M3ProgressType.FLAT,
    thickness: M3ProgressThickness = M3ProgressThickness.FOUR_DP,
    showTrack: Boolean = true,
) {
    val track = if (showTrack) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
    if (type == M3ProgressType.WAVY) {
        CircularWavyProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = modifier.size(M3ProgressMetrics.circularMin * 2),
            color = MaterialTheme.colorScheme.primary,
            trackColor = track,
        )
    } else {
        CircularProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = modifier.size(M3ProgressMetrics.circularMin * 2),
            color = MaterialTheme.colorScheme.primary,
            trackColor = track,
            strokeWidth = thickness.value,
        )
    }
}

/** 环形 indeterminate。 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun M3CircularIndeterminateProgressIndicator(
    modifier: Modifier = Modifier,
    type: M3ProgressType = M3ProgressType.FLAT,
    thickness: M3ProgressThickness = M3ProgressThickness.FOUR_DP,
    step: Int = 0,
    showTrack: Boolean = true,
) {
    val track = if (showTrack) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
    if (type == M3ProgressType.WAVY) {
        CircularWavyProgressIndicator(
            modifier = modifier.size(M3ProgressMetrics.circularMin * 2),
            color = MaterialTheme.colorScheme.primary,
            trackColor = track,
        )
    } else {
        CircularProgressIndicator(
            modifier = modifier.size(M3ProgressMetrics.circularMin * 2),
            color = MaterialTheme.colorScheme.primary,
            trackColor = track,
            strokeWidth = thickness.value,
        )
    }
}

/**
 * Loading indicator（Expressive）：7 个形状的循环 morph。
 *
 * step 0..6 对应 Figma 的静态帧；容器版用 primaryContainer / onPrimaryContainer。
 * 默认占位 48dp，可见容器 38dp。
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun M3LoadingIndicator(
    modifier: Modifier = Modifier,
    step: Int = 0,
    showContainer: Boolean = false,
) {
    val frame = ((step % 7) / 6f)
    if (showContainer) {
        ContainedLoadingIndicator(
            modifier = modifier.size(M3ProgressMetrics.loadingPlaceholder),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            indicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerShape = CircleShape,
            progress = { frame },
        )
    } else {
        LoadingIndicator(
            modifier = modifier.size(M3ProgressMetrics.loadingPlaceholder),
            color = MaterialTheme.colorScheme.primary,
            progress = { frame },
        )
    }
}
