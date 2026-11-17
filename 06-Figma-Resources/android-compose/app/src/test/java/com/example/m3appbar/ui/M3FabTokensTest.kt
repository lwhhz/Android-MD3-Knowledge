package com.example.m3appbar.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class M3FabTokensTest {

    @Test
    fun fabHeightsMatchOfficialLadder() {
        assertEquals(40f, M3FabSize.SMALL.height.value, 0.01f)
        assertEquals(56f, M3FabSize.DEFAULT.height.value, 0.01f)
        assertEquals(80f, M3FabSize.MEDIUM.height.value, 0.01f)
        assertEquals(96f, M3FabSize.LARGE.height.value, 0.01f)
    }

    @Test
    fun extendedFabHeightsAlignWithFab() {
        assertEquals(56f, M3ExtendedFabSize.SMALL.height.value, 0.01f)
        assertEquals(80f, M3ExtendedFabSize.MEDIUM.height.value, 0.01f)
        assertEquals(96f, M3ExtendedFabSize.LARGE.height.value, 0.01f)
    }
}
