package org.hexworks.zircon.internal.application

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.RectangleTileGrid
import org.hexworks.zircon.internal.renderer.LibgdxRenderer

class LibgdxApplication(appConfig: AppConfig) : ApplicationAdapter(), Application {

    override val tileGrid: InternalTileGrid = RectangleTileGrid(
            tileset = appConfig.defaultTileset,
            size = appConfig.size)

    private val renderer = LibgdxRenderer(tileGrid, appConfig.debugMode)

    private var started = false
    private var paused = false

    // libgdx overrides
    override fun create() {
        start()
    }

    override fun render() {
        if (paused.not() && started) {
            renderer.render()
        }
    }

    override fun dispose() {
        stop()
    }

    // zircon overrides

    override fun start() {
        if (started.not()) {
            started = true
            val cfg = LwjglApplicationConfiguration()
            cfg.title = "LibGDX Test"
            cfg.height = tileGrid.heightInPixels()
            cfg.width = tileGrid.widthInPixels()
            LwjglApplication(this, cfg)
            renderer.create()
        }
    }

    override fun pause() {
        this.paused = true
    }

    override fun resume() {
        this.paused = false
    }

    override fun stop() {
        tileGrid.close()
        renderer.close()
    }

    companion object {

        fun create(
                appConfig: AppConfig = AppConfig.defaultConfiguration()): Application {
            return LibgdxApplication(appConfig)
        }
    }
}
