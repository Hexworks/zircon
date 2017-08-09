package org.codetome.zircon.screen

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.builder.TextCharacterBuilder
import org.codetome.zircon.builder.TextImageBuilder
import org.codetome.zircon.input.KeyStroke
import org.codetome.zircon.terminal.Size
import org.codetome.zircon.terminal.virtual.DefaultVirtualTerminal
import org.codetome.zircon.terminal.virtual.TextCharacterBuffer
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class TerminalScreenTest {

    lateinit var target: TerminalScreen
    val terminal = DefaultVirtualTerminal(SIZE)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = TerminalScreen(terminal)
    }

    @Test
    fun shouldDrainInputQueueWhenClosed() {
        target.addInput(KeyStroke(
                character = 'x'
        ))
        target.close()
        assertThat(target.pollInput().isPresent)
                .isFalse()
    }

    @Test
    fun shouldResizeOnResize() {
        val expectedSize = Size(5, 5)
        terminal.setSize(expectedSize)
        assertThat(terminal.getBoundableSize()).isEqualTo(expectedSize)
    }

    @Test
    fun shouldNotBeDrawnWhenCharacterSetButNotRefreshed() {
        val graphics = target.newTextGraphics()
        graphics.setCharacter(Position.OFFSET_1x1, CHAR)

        assertThat(target.getBackCharacter(Position.OFFSET_1x1))
                .isEqualTo(CHAR)
        assertThat(target.getFrontCharacter(Position.OFFSET_1x1))
                .isNotEqualTo(CHAR)

    }

    @Test
    fun shouldBeDrawnWhenCharacterSetAndRefreshed() {
        val graphics = target.newTextGraphics()
        graphics.setCharacter(Position.OFFSET_1x1, CHAR)

        target.refresh()

        assertThat(target.getBackCharacter(Position.OFFSET_1x1))
                .isEqualTo(CHAR)
        assertThat(target.getFrontCharacter(Position.OFFSET_1x1))
                .isEqualTo(CHAR)

    }

    @Test
    fun shouldBeAbleToDrawImageWhenDrawImageIsCalledOnNewGraphics() {
        val graphics = target.newTextGraphics()
        val image = TextImageBuilder.newBuilder()
                .filler(CHAR)
                .build()

        graphics.drawImage(Position.OFFSET_1x1, image)

        assertThat(target.getBackCharacter(Position.OFFSET_1x1))
                .isEqualTo(CHAR)
    }

    @Test
    fun shouldClearProperlyWhenClearIsCalled() {
        target.setCharacter(Position.OFFSET_1x1, CHAR)
        target.display()

        target.clear()

        assertThat(target.getBackCharacter(Position.OFFSET_1x1))
                .isNotEqualTo(CHAR)
    }


    companion object {
        val SIZE = Size(10, 10)
        val CHAR = TextCharacterBuilder.newBuilder()
                .character('x')
                .build()
    }
}