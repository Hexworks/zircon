package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.internal.font.impl.Java2DFontTextureRegion
import org.junit.Before
import org.junit.Test
import java.awt.image.BufferedImage

class Java2DBorderTransformerTest {

    lateinit var target: Java2DBorderTransformer

    @Before
    fun setUp() {
        target = Java2DBorderTransformer()
    }

    @Test
    fun shouldProperlyRun() {
        val image = BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB)
        target.transform(Java2DFontTextureRegion(CHAR.generateCacheKey(), image), CHAR)

        // TODO: check border?
    }

    companion object {
        val WIDTH = 10
        val HEIGHT = 10
        val CHAR = TileBuilder.newBuilder()
                .modifiers(Modifiers.border())
                .build()
    }
}
