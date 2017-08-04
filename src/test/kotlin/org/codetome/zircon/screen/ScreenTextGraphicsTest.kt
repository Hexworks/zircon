package org.codetome.zircon.screen

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.builder.TextCharacterBuilder
import org.codetome.zircon.builder.TextImageBuilder
import org.codetome.zircon.terminal.TerminalSize
import org.codetome.zircon.terminal.virtual.DefaultVirtualTerminal
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class ScreenTextGraphicsTest {

    val terminal = DefaultVirtualTerminal(SIZE)
    val screen = TerminalScreen(terminal)
    internal lateinit var target: ScreenTextGraphics

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = ScreenTextGraphics(screen)
    }

    @Test
    fun shouldProperlySetCharacter() {
        target.setCharacter(SET_POS, SET_CHAR)

        assertThat(screen.getBackCharacter(SET_POS))
                .isEqualTo(SET_CHAR)
    }

    @Test
    fun testDelegates() {
        // these are delegated, no need to test, but needs coverage
        target.fill('x')
        target.drawLine(TerminalPosition.DEFAULT_POSITION,
                TerminalPosition.OFFSET_1x1,
                'x')
        target.drawLine(TerminalPosition.DEFAULT_POSITION,
                TerminalPosition.OFFSET_1x1,
                SET_CHAR)
        target.drawRectangle(TerminalPosition.DEFAULT_POSITION,
                TerminalSize.ONE,
                'x')
        target.drawRectangle(TerminalPosition.DEFAULT_POSITION,
                TerminalSize.ONE,
                SET_CHAR)
        target.drawTriangle(TerminalPosition.DEFAULT_POSITION,
                TerminalPosition.OFFSET_1x1,
                TerminalPosition(2, 2),
                'x')
        target.drawTriangle(TerminalPosition.DEFAULT_POSITION,
                TerminalPosition.OFFSET_1x1,
                TerminalPosition(2, 2),
                SET_CHAR)

        target.fillRectangle(TerminalPosition.DEFAULT_POSITION,
                TerminalSize.ONE,
                'x')
        target.fillRectangle(TerminalPosition.DEFAULT_POSITION,
                TerminalSize.ONE,
                SET_CHAR)
        target.fillTriangle(TerminalPosition.DEFAULT_POSITION,
                TerminalPosition.OFFSET_1x1,
                TerminalPosition(2, 2),
                'x')
        target.fillTriangle(TerminalPosition.DEFAULT_POSITION,
                TerminalPosition.OFFSET_1x1,
                TerminalPosition(2, 2),
                SET_CHAR)
    }

    @Test
    fun shouldProperlyDrawImage() {
        target.drawImage(
                TerminalPosition.OFFSET_1x1,
                TextImageBuilder.newBuilder()
                        .filler(SET_CHAR)
                        .size(TerminalSize(2, 2))
                        .build())

        assertThat(target.getCharacter(TerminalPosition.DEFAULT_POSITION).get())
                .isEqualTo(EMPTY_CHAR)
        assertThat(target.getCharacter(TerminalPosition.OFFSET_1x1).get())
                .isEqualTo(SET_CHAR)
        assertThat(target.getCharacter(TerminalPosition(2, 2)).get())
                .isEqualTo(SET_CHAR)
        assertThat(target.getCharacter(TerminalPosition(3, 3)).get())
                .isEqualTo(EMPTY_CHAR)
    }

    companion object {
        val SIZE = TerminalSize(10, 10)
        val SET_POS = TerminalPosition(2, 2)
        val SET_CHAR = TextCharacterBuilder.newBuilder()
                .character('x')
                .build()
        val EMPTY_CHAR = TextCharacterBuilder.newBuilder().build()
    }
}