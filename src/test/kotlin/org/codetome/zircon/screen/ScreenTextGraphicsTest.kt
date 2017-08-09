package org.codetome.zircon.screen

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.builder.TextCharacterBuilder
import org.codetome.zircon.builder.TextImageBuilder
import org.codetome.zircon.terminal.Size
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
        // these are delegated, no need to shouldProperlySetValues, but needs coverage
        target.fill('x')
        target.drawLine(Position.DEFAULT_POSITION,
                Position.OFFSET_1x1,
                'x')
        target.drawLine(Position.DEFAULT_POSITION,
                Position.OFFSET_1x1,
                SET_CHAR)
        target.drawRectangle(Position.DEFAULT_POSITION,
                Size.ONE,
                'x')
        target.drawRectangle(Position.DEFAULT_POSITION,
                Size.ONE,
                SET_CHAR)
        target.drawTriangle(Position.DEFAULT_POSITION,
                Position.OFFSET_1x1,
                Position(2, 2),
                'x')
        target.drawTriangle(Position.DEFAULT_POSITION,
                Position.OFFSET_1x1,
                Position(2, 2),
                SET_CHAR)

        target.fillRectangle(Position.DEFAULT_POSITION,
                Size.ONE,
                'x')
        target.fillRectangle(Position.DEFAULT_POSITION,
                Size.ONE,
                SET_CHAR)
        target.fillTriangle(Position.DEFAULT_POSITION,
                Position.OFFSET_1x1,
                Position(2, 2),
                'x')
        target.fillTriangle(Position.DEFAULT_POSITION,
                Position.OFFSET_1x1,
                Position(2, 2),
                SET_CHAR)
    }

    @Test
    fun shouldProperlyDrawImage() {
        target.drawImage(
                Position.OFFSET_1x1,
                TextImageBuilder.newBuilder()
                        .filler(SET_CHAR)
                        .size(Size(2, 2))
                        .build())

        assertThat(target.getCharacter(Position.DEFAULT_POSITION).get())
                .isEqualTo(EMPTY_CHAR)
        assertThat(target.getCharacter(Position.OFFSET_1x1).get())
                .isEqualTo(SET_CHAR)
        assertThat(target.getCharacter(Position(2, 2)).get())
                .isEqualTo(SET_CHAR)
        assertThat(target.getCharacter(Position(3, 3)).get())
                .isEqualTo(EMPTY_CHAR)
    }

    companion object {
        val SIZE = Size(10, 10)
        val SET_POS = Position(2, 2)
        val SET_CHAR = TextCharacterBuilder.newBuilder()
                .character('x')
                .build()
        val EMPTY_CHAR = TextCharacterBuilder.newBuilder().build()
    }
}