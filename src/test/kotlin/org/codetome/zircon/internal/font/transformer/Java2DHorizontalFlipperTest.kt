package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.Modifiers
import org.codetome.zircon.api.builder.TextCharacterBuilder
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
        target.transform(image, CHAR)

        // TODO: check horizontal flip?
    }

    companion object {
        val WIDTH = 10
        val HEIGHT = 10
        val CHAR = TextCharacterBuilder.newBuilder()
                .modifiers(Modifiers.HORIZONTAL_FLIP)
                .build()
    }
}