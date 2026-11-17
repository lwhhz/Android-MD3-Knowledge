package com.example.m3appbar.components

import com.example.m3appbar.components.Shape as M3Shape

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.unit.dp

/**
 * Shape Set：10 个常用形状（M3 Expressive shape 子集）。
 * 仅做视觉 token 展示；CIRCLE/SQUARE/PILL 用内置 shape，其余用 GenericShape 路径。
 */
@Composable
fun M3ShapeSet(
    modifier: Modifier = Modifier,
    shape: M3Shape = M3Shape.CIRCLE,
    label: String? = null,
) {
    val clipShape = shape.toComposeShape()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(clipShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
        )
        Text(
            text = label ?: shape.name.lowercase().replace('_', ' '),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

private fun M3Shape.toComposeShape(): androidx.compose.ui.graphics.Shape = when (this) {
    M3Shape.CIRCLE -> CircleShape
    M3Shape.SQUARE -> RoundedCornerShape(0.dp)
    M3Shape.PILL -> RoundedCornerShape(50)
    M3Shape.DIAMOND -> polygon(4)
    M3Shape.HEXAGON -> polygon(6)
    M3Shape.PENTAGON -> polygon(5)
    M3Shape.TRIANGLE -> polygon(3)
    M3Shape.ARCH -> RoundedCornerShape(topStartPercent = 50, topEndPercent = 50)
    M3Shape.GEM -> CutCornerShape(12.dp)
    M3Shape.SLANTED -> GenericShape { size, _ ->
        val w = size.width
        val h = size.height
        moveTo(w * 0.2f, 0f)
        lineTo(w, 0f)
        lineTo(w * 0.8f, h)
        lineTo(0f, h)
        close()
    }
    else -> RoundedCornerShape(12.dp)
}

private fun polygon(sides: Int): androidx.compose.ui.graphics.Shape = GenericShape { size, _ ->
    val radius = minOf(size.width, size.height) / 2f
    val center = Offset(size.width / 2f, size.height / 2f)
    val start = if (sides % 2 == 0) -90f else -90f
    for (i in 0 until sides) {
        val angle = Math.toRadians((start + 360f / sides * i).toDouble())
        val point = Offset(
            center.x + radius * kotlin.math.cos(angle).toFloat(),
            center.y + radius * kotlin.math.sin(angle).toFloat(),
        )
        if (i == 0) moveTo(point.x, point.y) else lineTo(point.x, point.y)
    }
    close()
}

/** .Shape 内部块的 9 个变体映射（1–9），供 Shape token 展示。 */
object M3ShapeVariants {
    val variants: List<M3Shape> = listOf(
        M3Shape.CIRCLE,
        M3Shape.SQUARE,
        M3Shape.PILL,
        M3Shape.DIAMOND,
        M3Shape.HEXAGON,
        M3Shape.PENTAGON,
        M3Shape.TRIANGLE,
        M3Shape.ARCH,
        M3Shape.GEM,
    )

    fun shapeFor(index: Int): M3Shape = variants[(index - 1).coerceIn(0, variants.lastIndex)]
}

@Composable
fun M3Shape(
    variant: Int = 1,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier = modifier
            .clip(M3ShapeVariants.shapeFor(variant).toComposeShape())
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}
