package org.codetome.zircon.internal.terminal

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.terminal.DeviceConfiguration
import org.codetome.zircon.internal.terminal.application.ApplicationTerminal
import org.codetome.zircon.internal.terminal.virtual.VirtualTerminal

/**
 * Concrete implementation of [ApplicationTerminal] that adapts it to libGDX.
 */
class LibgdxTerminal(
        initialTileset: Tileset,
        initialSize: Size,
        private val deviceConfiguration: DeviceConfiguration)

    : ApplicationTerminal(
        deviceConfiguration = deviceConfiguration,
        terminal = VirtualTerminal(
                initialSize = initialSize,
                initialTileset = initialTileset),
        checkDirty = false) {

    lateinit var batch: SpriteBatch

    init {
        // TODO Setup key/mouse listeners here"
        // TODO Setup doCreate/doDispose listeners here
    }

    override fun getHeight() = getSupportedFontSize().yLength * getBoundableSize().yLength

    override fun getWidth() = getSupportedFontSize().xLength * getBoundableSize().xLength

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

    override fun drawFontTextureRegion(tileTexture: TileTexture<*>, x: Int, y: Int) {
        val (_, rows) = getBoundableSize()
        val font = getCurrentFont()
        val (_, height) = font.getSize()
        val fixedY = (height * rows) - y
        TextureRegionDrawable(tileTexture.getBackend() as TextureRegion).draw(batch,
                x.toFloat(),
                fixedY.toFloat(),
                font.getWidth().toFloat(),
                font.getHeight().toFloat())
    }

    override fun drawCursor(character: Tile, x: Int, y: Int) {
        // TODO implement this
    }
}
