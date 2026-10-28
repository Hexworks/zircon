package org.hexworks.zircon.api

import io.kotest.matchers.ints.shouldBeGreaterThan
import kotlin.test.Test

class REXPaintResourcesTest {

    @Test
    fun loadsTheBundledGzippedSample() {
        val bytes = checkNotNull(javaClass.getResourceAsStream("/test/xptest.xp")) { "sample missing" }
            .use { it.readBytes() }

        val layers = loadREXFile(bytes).toLayerList(CP437TilesetResources.aduDhabi16x16())

        layers.size shouldBeGreaterThan 0
        layers.forEach { layer ->
            layer.size.width shouldBeGreaterThan 0
            layer.size.height shouldBeGreaterThan 0
        }
    }
}
