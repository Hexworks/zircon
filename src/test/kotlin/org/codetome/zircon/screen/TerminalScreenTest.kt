package org.codetome.zircon.screen

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.input.KeyStroke
import org.codetome.zircon.screen.impl.TerminalScreen
import org.codetome.zircon.terminal.virtual.VirtualTerminal
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class TerminalScreenTest {

    lateinit var target: TerminalScreen
    val terminal = VirtualTerminal(SIZE)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = TerminalScreen(terminal)
    }

    @Test
    fun shouldResizeOnResize() {
        val expectedSize = Size(5, 5)
        terminal.setSize(expectedSize)
        assertThat(terminal.getBoundableSize()).isEqualTo(expectedSize)
    }


    @Test
    fun shouldBeDrawnWhenCharacterSet() {
        target.setCharacterAt(Position.OFFSET_1x1, CHAR)
        assertThat(target.getCharacterAt(Position.OFFSET_1x1).get())
                .isEqualTo(CHAR)

    }

    @Test
    fun shouldClearProperlyWhenClearIsCalled() {
        target.setCharacterAt(Position.OFFSET_1x1, CHAR)
        target.display()

        target.clear()

        assertThat(target.getCharacterAt(Position.OFFSET_1x1))
                .isNotEqualTo(CHAR)
    }


    companion object {
        val SIZE = Size(10, 10)
        val CHAR = TextCharacterBuilder.newBuilder()
                .character('x')
                .build()
    }
}