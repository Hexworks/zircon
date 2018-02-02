package org.codetome.zircon.internal.terminal

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
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
                initialFont = initialFont),
        checkDirty = false) {

    lateinit var batch: SpriteBatch

    init {
        // TODO Setup key/mouse listeners here"
        // TODO Setup doCreate/doDispose listeners here
    }

    override fun getHeight() = getSupportedFontSize().rows * getBoundableSize().rows

    override fun getWidth() = getSupportedFontSize().columns * getBoundableSize().columns

    @Synchronized
    override fun flush() {
        Gdx.graphics.requestRendering()
    }

    override fun doCreate() {
        super.doCreate()
        batch = SpriteBatch()
        Gdx.graphics.isContinuousRendering = false
    }

    override fun doRender() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        super.doRender()
        batch.end()
    }

    override fun doDispose() {
        super.doDispose()
        batch.dispose()
    }

    override fun doResize(width: Int, height: Int) {
        TODO()
    }

    override fun drawFontTextureRegion(fontTextureRegion: FontTextureRegion<*>, x: Int, y: Int) {
        val (_, rows) = getBoundableSize()
        val font = getCurrentFont()
        val (_, height) = font.getSize()
        val fixedY = (height * rows) - y
        TextureRegionDrawable(fontTextureRegion.getBackend() as TextureRegion).draw(batch,
                x.toFloat(),
                fixedY.toFloat(),
                font.getWidth().toFloat(),
                font.getHeight().toFloat())
    }

    override fun drawCursor(character: TextCharacter, x: Int, y: Int) {
        // TODO implement this
    }
}
