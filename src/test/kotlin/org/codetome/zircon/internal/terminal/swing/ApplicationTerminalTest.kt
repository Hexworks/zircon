package org.codetome.zircon.internal.terminal.swing

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder
import org.codetome.zircon.api.font.FontTextureRegion
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


class ApplicationTerminalTest {

    lateinit var target: ApplicationTerminal

    val drawn = AtomicBoolean(false)

    @Before
    fun setUp() {
        target = object : ApplicationTerminal(
                deviceConfiguration = CONFIG,
                terminal = VirtualTerminal(
                        initialSize = SIZE,
                        initialFont = FONT)) {
            override fun drawFontTextureRegion(fontTextureRegion: FontTextureRegion, x: Int, y: Int) {
                TODO("not implemented")
            }

            override fun drawCursor(character: TextCharacter, x: Int, y: Int) {
                TODO("not implemented")
            }

            override fun getHeight() = SIZE.rows * FONT.getHeight()

            override fun getWidth() = SIZE.columns * FONT.getWidth()

            override fun doRender() {
                drawn.set(true)
            }
        }
    }


    @Test
    fun shouldProperlyDraw() {
        val image = BufferedImage(target.getWidth(), target.getHeight(), BufferedImage.TYPE_INT_RGB)

        target.setCursorVisibility(true)

        target.doRender()

        // TODO: asssert ?
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