package org.codetome.zircon.internal.tileset.transformer

import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.api.interop.Tiles
import org.codetome.zircon.internal.tileset.impl.Java2DTileTexture
import org.junit.Before
import org.junit.Test
import java.awt.image.BufferedImage

class Java2DUnderlineTransformerTest {

    lateinit var target: Java2DUnderlineTransformer

    @Before
    fun setUp() {
        target = Java2DUnderlineTransformer()
    }

    @Test
    fun shouldProperlyRun() {
        val image = BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB)
        target.transform(Java2DTileTexture(CHAR.generateCacheKey(), image), CHAR)

        // TODO: check underline?
    }

    companion object {
        val WIDTH = 10
        val HEIGHT = 10
        val CHAR = Tiles.newBuilder()
                .modifiers(Modifiers.underline())
                .build()
    }
}
