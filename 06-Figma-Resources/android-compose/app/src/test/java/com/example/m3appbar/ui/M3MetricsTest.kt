package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * 官方数值回归：这些常量一旦被改动，测试必须失败。
 * 来源：m3.material.io state-layers / buttons specs（KB 2026-11-16 核验）。
 */
class M3MetricsTest {

    @Test
    fun stateLayerAlphasMatchOfficialValues() {
        assertEquals(0.00f, M3StateLayer.alphaFor(M3InteractionState.ENABLED), 0.0001f)
        assertEquals(0.08f, M3StateLayer.alphaFor(M3InteractionState.HOVERED), 0.0001f)
        assertEquals(0.10f, M3StateLayer.alphaFor(M3InteractionState.FOCUSED), 0.0001f)
        assertEquals(0.10f, M3StateLayer.alphaFor(M3InteractionState.PRESSED), 0.0001f)
        assertEquals(0.16f, M3StateLayer.alphaFor(M3InteractionState.DRAGGED), 0.0001f)
        assertEquals(0.38f, M3StateLayer.alphaFor(M3InteractionState.DISABLED), 0.0001f)
        assertEquals(40f, M3StateLayer.layerSize.value, 0.01f)
        assertEquals(48f, M3StateLayer.targetSize.value, 0.01f)
    }

    @Test
    fun buttonSizesMatchOfficialTable() {
        val expected = mapOf(
            M3ButtonSize.XS to listOf(32f, 12f, 20f, 4f, 12f, 8f),
            M3ButtonSize.S to listOf(40f, 16f, 20f, 8f, 12f, 8f),
            M3ButtonSize.M to listOf(56f, 24f, 24f, 8f, 16f, 12f),
            M3ButtonSize.L to listOf(96f, 48f, 32f, 12f, 28f, 16f),
            M3ButtonSize.XL to listOf(136f, 64f, 40f, 16f, 28f, 16f),
        )
        for ((size, values) in expected) {
            assertEquals(size.name, values[0], size.height.value, 0.01f)
            assertEquals(size.name, values[1], size.horizontalPadding.value, 0.01f)
            assertEquals(size.name, values[2], size.iconSize.value, 0.01f)
            assertEquals(size.name, values[3], size.iconGap.value, 0.01f)
            assertEquals(size.name, values[4], size.cornerRadius.value, 0.01f)
            assertEquals(size.name, values[5], size.pressedCornerRadius.value, 0.01f)
        }
    }
}
