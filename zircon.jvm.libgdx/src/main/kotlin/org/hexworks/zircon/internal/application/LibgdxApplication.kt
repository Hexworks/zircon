package org.hexworks.zircon.internal.application

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.RectangleTileGrid
import org.hexworks.zircon.internal.renderer.LibgdxRenderer

class LibgdxApplication(appConfig: AppConfig):  Disposable, Application {

    override val tileGrid: InternalTileGrid = RectangleTileGrid(
            tileset = appConfig.defaultTileset,
            size = appConfig.size)

    private val renderer = LibgdxRenderer(tileGrid, appConfig.debugMode)

    private var started = false
    private var paused = false

    private fun create() {
        Gdx.app.log("Initialization", "Starting LibgdxRenderer")
        renderer.create()
    }

    fun render() {
        if (paused.not() && started) {
            renderer.render()
        }
    }

    fun resize() {

    }

    override fun dispose() {
        stop()
    }

    // zircon overrides

    override fun start() {
        if (started.not()) {
            Gdx.app.log("Initialization", "Starting Zircon Application")
            started = true
            create()
        } else {
            Gdx.app.error("Warning", "Zircon Application already started")
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
        fun create(appConfig: AppConfig = AppConfig.defaultConfiguration()): Application {
            return LibgdxApplication(appConfig)
        }
    }
}
