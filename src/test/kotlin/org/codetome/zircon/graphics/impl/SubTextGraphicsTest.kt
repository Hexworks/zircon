package org.codetome.zircon.graphics.impl

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.builder.TextImageBuilder
import org.codetome.zircon.terminal.TerminalSize
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class SubTextGraphicsTest {

    internal lateinit var target: SubTextGraphics

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = SubTextGraphics(
                underlyingTextGraphics = TEXT_GRAPHICS,
                topLeft = TerminalPosition.OFFSET_1x1,
                size = SUB_SIZE)
    }

    @Test
    fun test() {

    }

    companion object {
        val SIZE = TerminalSize(10, 10)
        val SUB_SIZE = TerminalSize(8, 8)
        val TEXT_GRAPHICS = TextImageBuilder.newBuilder()
                .size(SIZE)
                .build().newTextGraphics()
    }

}