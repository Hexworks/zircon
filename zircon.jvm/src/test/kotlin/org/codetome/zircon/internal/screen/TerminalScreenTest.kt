package org.codetome.zircon.internal.screen

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.animation.DefaultAnimationHandler
import org.codetome.zircon.api.animation.AnimationResource
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.internal.component.impl.DefaultLabelTest
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.font.impl.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.TestFontLoader
import org.codetome.zircon.internal.terminal.virtual.VirtualTerminal
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.util.concurrent.atomic.AtomicBoolean

class TerminalScreenTest {

    lateinit var target: TerminalScreen
    lateinit var font: Font
    lateinit var terminal: VirtualTerminal

    @Before
    fun setUp() {
        FontLoaderRegistry.setFontLoader(TestFontLoader())
        font = DefaultLabelTest.FONT.toFont()
        terminal = VirtualTerminal(
                initialSize = SIZE,
                initialFont = font)
        MockitoAnnotations.initMocks(this)
        target = TerminalScreen(terminal)
    }

    @Test
    fun givenScreenWithAnimationWhenGivenInputThenFireOnInput() {
        val animation = AnimationResource.loadAnimationFromStream(this.javaClass.getResourceAsStream("/animations/skull.zap"))
                .setPositionForAll(Position.create(0, 0))
                .loopCount(0)
                .build()

        val inputFired = AtomicBoolean(false)
        target.onInput { inputFired.set(true) }

        //first of all lets make sure the default behaviour works. if a key is pressed I should get an input fired
        EventBus.broadcast(Event.Input(KeyStroke('a')))
        assertThat(inputFired.get()).isTrue()

        //now lets add the animation and make sure we can still get input
        var animationHandler = DefaultAnimationHandler(target)
        animationHandler.addAnimation(animation)

        inputFired.set(false)
        EventBus.broadcast(Event.Input(KeyStroke('a')))
        assertThat(inputFired.get()).isTrue()

    }

    @Test
    fun shouldBeAbleToPutCharacterWhenPutCharacterIsCalled() {
        val char = 'x'
        val expected = TextCharacterBuilder.newBuilder()
                .styleSet(target.toStyleSet())
                .character(char)
                .build()
        val currCursorPos = target.getCursorPosition()

        target.putCharacter(char)

        assertThat(target.getCharacterAt(currCursorPos).get()).isEqualTo(expected)
        assertThat(target.getCursorPosition()).isEqualTo(currCursorPos.withRelativeX(1))

    }

    @Test
    fun shouldUseTerminalsFontWhenCreating() {
        assertThat(target.getCurrentFont().getId())
                .isEqualTo(terminal.getCurrentFont().getId())
    }

    @Test
    fun shouldProperlyOverrideTerminalFontWhenHasOverrideFontAndDisplayIsCalled() {
        val expectedFont = CP437TilesetResource.AESOMATICA_16X16.toFont()
        target.useFont(expectedFont)
        target.display()
        assertThat(target.getCurrentFont().getId()).isEqualTo(expectedFont.getId())
        assertThat(terminal.getCurrentFont().getId()).isEqualTo(expectedFont.getId())
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldProperlyThrowExceptionWhenTyringToSetNonCompatibleFont() {
        target.useFont(CP437TilesetResource.BISASAM_20X20.toFont())
    }

    @Test
    fun shouldResizeOnResize() {
        val expectedSize = Size.create(5, 5)
        terminal.setSize(expectedSize)
        assertThat(terminal.getBoundableSize()).isEqualTo(expectedSize)
    }


    @Test
    fun shouldBeDrawnWhenCharacterSet() {
        target.setCharacterAt(Position.offset1x1(), CHAR)
        assertThat(target.getCharacterAt(Position.offset1x1()).get())
                .isEqualTo(CHAR)

    }

    @Test
    fun shouldClearProperlyWhenClearIsCalled() {
        target.setCharacterAt(Position.offset1x1(), CHAR)
        target.display()

        target.clear()

        assertThat(target.getCharacterAt(Position.offset1x1()))
                .isNotEqualTo(CHAR)
    }


    companion object {
        val SIZE = Size.create(10, 10)
        val FONT = CP437TilesetResource.ROGUE_YUN_16X16
        val CHAR = TextCharacterBuilder.newBuilder()
                .character('x')
                .build()
    }
}
