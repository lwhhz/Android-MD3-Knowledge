package com.example.m3appbar.ui

import com.example.m3appbar.components.AppBarConfiguration
import com.example.m3appbar.components.AppBarElevation
import org.junit.Assert.assertEquals
import org.junit.Test

class M3AppBarTokensTest {

    @Test
    fun variantParsing() {
        assertEquals(AppBarConfiguration.SMALL_CENTERED, AppBarConfiguration.from("Small-centered"))
        assertEquals(AppBarConfiguration.SEARCH, AppBarConfiguration.from("Search"))
        assertEquals(AppBarConfiguration.LARGE_FLEXIBLE, AppBarConfiguration.from("Large flexible"))
        assertEquals(AppBarElevation.ON_SCROLL, AppBarElevation.from("On-scroll"))
        assertEquals(AppBarElevation.FLAT, AppBarElevation.from("Flat"))
    }
}
