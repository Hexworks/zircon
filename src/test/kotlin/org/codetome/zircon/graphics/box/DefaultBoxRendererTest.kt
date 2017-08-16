package org.codetome.zircon.graphics.box

import org.codetome.zircon.Position
import org.codetome.zircon.api.TextImageBuilder
import org.codetome.zircon.graphics.box.BoxType.*
import org.codetome.zircon.graphics.style.DefaultStyleSet
import org.codetome.zircon.Size
import org.junit.Before
import org.junit.Test

class DefaultBoxRendererTest {

    lateinit var target: DefaultBoxRenderer

    val textImage = TextImageBuilder.newBuilder()
            .size(Size(20, 20))
            .build()

    @Before
    fun setUp() {
        target = DefaultBoxRenderer()
    }

    @Test
    fun test() {
        target.drawBox(
                textGraphics = textImage.newTextGraphics(),
                topLeft = Position(1, 2),
                size = Size(3, 3),
                boxType = DOUBLE,
                styleToUse = DefaultStyleSet())

        target.drawBox(
                textGraphics = textImage.newTextGraphics(),
                topLeft = Position(2, 3),
                size = Size(3, 3),
                boxType = DOUBLE,
                styleToUse = DefaultStyleSet())
        target.drawBox(
                textGraphics = textImage.newTextGraphics(),
                topLeft = Position(3, 1),
                size = Size(3, 3),
                boxType = DOUBLE,
                styleToUse = DefaultStyleSet())
        target.drawBox(
                textGraphics = textImage.newTextGraphics(),
                topLeft = Position(5, 2),
                size = Size(3, 3),
                boxType = DOUBLE,
                styleToUse = DefaultStyleSet())

        target.drawBox(
                textGraphics = textImage.newTextGraphics(),
                topLeft = Position(6, 1),
                size = Size(2, 2),
                boxType = DOUBLE,
                styleToUse = DefaultStyleSet())
        println(textImage.toString())
    }
}