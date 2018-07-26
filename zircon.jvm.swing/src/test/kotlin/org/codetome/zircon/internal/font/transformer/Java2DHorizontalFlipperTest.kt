package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.api.interop.Tiles
import org.codetome.zircon.internal.font.impl.Java2DFontTextureRegion
import org.junit.Before
import org.junit.Test
import java.awt.image.BufferedImage

class Java2DHorizontalFlipperTest {

    lateinit var target: Java2DHorizontalFlipper

    @Before
    fun setUp() {
        target = Java2DHorizontalFlipper()
    }

    @Test
    fun shouldProperlyRun() {
        val image = BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB)
        target.transform(Java2DFontTextureRegion(CHAR.generateCacheKey(), image), CHAR)

        // TODO: check horizontal flip?
    }

    companion object {
        val WIDTH = 10
        val HEIGHT = 10
        val CHAR = Tiles.newBuilder()
                .modifiers(Modifiers.horizontalFlip())
                .build()
    }
}
