package org.codetome.zircon.internal.terminal

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import org.codetome.zircon.internal.terminal.application.ApplicationTerminal
import org.codetome.zircon.internal.terminal.virtual.VirtualTerminal
import java.awt.Canvas

/**
 * Concrete implementation of [ApplicationTerminal] that adapts it to libGDX.
 */
class LibgdxTerminal(
        initialFont: Font,
        initialSize: Size,
        private val deviceConfiguration: DeviceConfiguration)

    : ApplicationTerminal(
        deviceConfiguration = deviceConfiguration,
        terminal = VirtualTerminal(
                initialSize = initialSize,
                initialFont = initialFont)) {

    lateinit var batch: SpriteBatch

    init {
        batch = SpriteBatch()
        TODO("Setup libgdx graphics here")
        TODO("Setup key/mouse listeners here")
        TODO("Setup doCreate/doDispose listeners here")
    }

    override fun getHeight() = TODO()

    override fun getWidth() = TODO()

    @Synchronized
    override fun flush() {
        TODO("draw and dispose?")
    }

    override fun doResize(width: Int, height: Int) {
        TODO()
    }

    override fun drawFontTextureRegion(fontTextureRegion: FontTextureRegion<*>, x: Int, y: Int) {
        TODO("draw")
    }

    override fun drawCursor(character: TextCharacter, x: Int, y: Int) {
        TODO()
    }
}
