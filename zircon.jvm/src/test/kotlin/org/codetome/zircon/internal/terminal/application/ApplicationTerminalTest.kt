package org.codetome.zircon.internal.terminal.application

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.terminal.config.CursorStyle
import org.codetome.zircon.internal.component.impl.DefaultLabelTest
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.font.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.TestFontLoader
import org.codetome.zircon.internal.font.impl.VirtualFontLoader
import org.codetome.zircon.internal.terminal.virtual.VirtualTerminal
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean


class ApplicationTerminalTest {

    lateinit var target: ApplicationTerminal
    lateinit var font: Font

    val fontTextureDraws = mutableListOf<Triple<FontTextureRegion<*>, Int, Int>>()
    val cursorDraws = mutableListOf<Triple<TextCharacter, Int, Int>>()
    var rendered = AtomicBoolean(false)

    @Before
    fun setUp() {
        FontLoaderRegistry.setFontLoader(TestFontLoader())
        font = DefaultLabelTest.FONT.toFont()
        target = object : ApplicationTerminal(
                deviceConfiguration = CONFIG,
                terminal = VirtualTerminal(
                        initialSize = SIZE,
                        initialFont = font)) {
            override fun drawFontTextureRegion(fontTextureRegion: FontTextureRegion<*>, x: Int, y: Int) {
                fontTextureDraws.add(Triple(fontTextureRegion, x, y))
            }

            override fun drawCursor(character: TextCharacter, x: Int, y: Int) {
                cursorDraws.add(Triple(character, x, y))
            }

            override fun getHeight() = SIZE.yLength * font.getHeight()

            override fun getWidth() = SIZE.xLength * font.getWidth()

            override fun doRender() {
                super.doRender()
                rendered.set(true)
            }
        }
    }


    @Test
    fun shouldRenderAfterCreateIfCursorBlinksAndEnoughTimePassed() {
        target.doCreate()
        Thread.sleep(100)

        assertThat(rendered.get()).isTrue()
    }

    @Test
    fun shouldSendEofOnDispose() {
        val eofReceived = AtomicBoolean(false)
        EventBus.subscribe<Input>(EventType.Input, {
            if (it.data == KeyStroke.EOF_STROKE) {
                eofReceived.set(true)
            }
        })

        target.doDispose()

        assertThat(eofReceived.get()).isTrue()
    }


    companion object {
        val SIZE = Size.create(10, 20)
        val BLINK_LEN_MS = 2L
        val CONFIG = DeviceConfigurationBuilder.newBuilder()
                .cursorBlinking(true)
                .blinkLengthInMilliSeconds(BLINK_LEN_MS)
                .cursorStyle(CursorStyle.USE_CHARACTER_FOREGROUND)
                .build()
        val FONT = CP437TilesetResource.WANDERLUST_16X16
    }
}
