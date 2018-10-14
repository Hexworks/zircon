package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import org.junit.Before
import org.junit.Test
import java.awt.image.BufferedImage

class Java2DCrossedOutTransformerTest {

    lateinit var target: Java2DCrossedOutTransformer

    @Before
    fun setUp() {
        target = Java2DCrossedOutTransformer()
    }

    @Test
    fun shouldProperlyRun() {
        val image = BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB)
        target.transform(DefaultTileTexture(WIDTH, HEIGHT, image), CHAR)

        // TODO: check cross?
    }

    companion object {
        val WIDTH = 10
        val HEIGHT = 10
        val CHAR = Tiles.newBuilder()
                .withModifiers(Modifiers.crossedOut())
                .build()
    }
}
