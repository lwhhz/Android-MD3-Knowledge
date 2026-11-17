package com.example.m3appbar.ui

import com.example.m3appbar.components.Shape as M3ShapeEnum
import com.example.m3appbar.components.M3ShapeVariants
import org.junit.Assert.assertEquals
import org.junit.Test

class M3ShapeVariantsTest {

    @Test
    fun nineShapeVariants() {
        assertEquals(9, M3ShapeVariants.variants.size)
        assertEquals(M3ShapeEnum.CIRCLE, M3ShapeVariants.shapeFor(1))
        assertEquals(M3ShapeEnum.SQUARE, M3ShapeVariants.shapeFor(2))
        assertEquals(M3ShapeEnum.GEM, M3ShapeVariants.shapeFor(9))
        assertEquals(M3ShapeEnum.CIRCLE, M3ShapeVariants.shapeFor(0))
    }
}
