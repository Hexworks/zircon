package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.internal.font.impl.Java2DFontTextureRegion
import org.junit.Before
import org.junit.Test
import java.awt.image.BufferedImage

class NoOpTransformerTest {

    lateinit var target: NoOpTransformer

    @Before
    fun setUp() {
        target = NoOpTransformer()
    }

    @Test
    fun shouldProperlyRun() {
        val image = BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB)
        target.transform(Java2DFontTextureRegion(image), CHAR)

        // TODO: check same?
    }

    companion object {
        val WIDTH = 10
        val HEIGHT = 10
        val CHAR = TextCharacterBuilder.newBuilder()
                .build()
    }
}