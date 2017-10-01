package org.codetome.zircon.internal.terminal.swing

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.terminal.config.CursorStyle
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.terminal.virtual.VirtualTerminal
import org.junit.Before
import org.junit.Test
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.util.concurrent.atomic.AtomicBoolean


class Java2DTerminalImplementationTest {

    lateinit var target: Java2DTerminalImplementation

    val drawn = AtomicBoolean(false)

    @Before
    fun setUp() {
        target = object : Java2DTerminalImplementation(
                deviceConfiguration = CONFIG,
                font = FONT,
                terminal = VirtualTerminal(
                        initialSize = SIZE)) {
            override fun getFontHeight() = FONT.getHeight()

            override fun getFontWidth() = FONT.getWidth()

            override fun getHeight() = SIZE.rows * FONT.getHeight()

            override fun getWidth() = SIZE.columns * FONT.getWidth()

            override fun draw() {
                drawn.set(true)
            }
        }
    }

    @Test
    fun shouldSubscribeToDrawWhenCreated() {
        val drawTriggered = AtomicBoolean(false)
        EventBus.subscribe(EventType.Draw, {
            drawTriggered.set(true)
        })

        target.onCreated()

        EventBus.emit(EventType.Draw)

        assertThat(drawTriggered.get()).isTrue()
    }

    @Test
    fun shouldProperlyCalculatePreferredSize() {
        assertThat(target.getPreferredSize()).isEqualTo(Dimension(target.getWidth(), target.getHeight()))
    }

    @Test
    fun flushShouldTriggerDraw() {
        target.flush()

        assertThat(drawn.get()).isTrue()
    }

    @Test
    fun shouldProperlyDraw() {
        val image = BufferedImage(target.getWidth(), target.getHeight(), BufferedImage.TYPE_INT_RGB)

        target.setCursorVisibility(true)

        target.draw(image.createGraphics())

        // TODO: asssert
    }

    companion object {
        val SIZE = Size.of(10, 20)
        val CONFIG = DeviceConfigurationBuilder.newBuilder()
                .cursorBlinking(true)
                .cursorStyle(CursorStyle.USE_CHARACTER_FOREGROUND)
                .build()
        val FONT = CP437TilesetResource.WANDERLUST_16X16.toFont()
    }
}