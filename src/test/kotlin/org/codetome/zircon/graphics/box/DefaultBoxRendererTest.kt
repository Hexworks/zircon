package org.codetome.zircon.graphics.box

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.builder.TextImageBuilder
import org.codetome.zircon.graphics.box.BoxType.*
import org.codetome.zircon.graphics.style.DefaultStyleSet
import org.codetome.zircon.terminal.TerminalSize
import org.junit.Before
import org.junit.Test

class DefaultBoxRendererTest {

    lateinit var target: DefaultBoxRenderer

    val textImage = TextImageBuilder.newBuilder()
            .size(TerminalSize(20, 20))
            .build()

    @Before
    fun setUp() {
        target = DefaultBoxRenderer()
    }

    @Test
    fun test() {
        target.drawBox(
                textGraphics = textImage.newTextGraphics(),
                topLeft = TerminalPosition(1, 2),
                size = TerminalSize(3, 3),
                boxType = DOUBLE,
                styleSet = DefaultStyleSet())

        target.drawBox(
                textGraphics = textImage.newTextGraphics(),
                topLeft = TerminalPosition(2, 3),
                size = TerminalSize(3, 3),
                boxType = DOUBLE,
                styleSet = DefaultStyleSet())
        target.drawBox(
                textGraphics = textImage.newTextGraphics(),
                topLeft = TerminalPosition(3, 1),
                size = TerminalSize(3, 3),
                boxType = DOUBLE,
                styleSet = DefaultStyleSet())
        target.drawBox(
                textGraphics = textImage.newTextGraphics(),
                topLeft = TerminalPosition(5, 2),
                size = TerminalSize(3, 3),
                boxType = DOUBLE,
                styleSet = DefaultStyleSet())

        target.drawBox(
                textGraphics = textImage.newTextGraphics(),
                topLeft = TerminalPosition(6, 1),
                size = TerminalSize(2, 2),
                boxType = DOUBLE,
                styleSet = DefaultStyleSet())
        println(textImage.toString())
    }
}