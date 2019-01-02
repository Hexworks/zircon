package org.hexworks.zircon.internal.application

import com.badlogic.gdx.utils.Disposable
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.mvc.ViewContainer
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.RectangleTileGrid
import org.hexworks.zircon.internal.mvc.DefaultViewContainer
import org.hexworks.zircon.internal.renderer.LibgdxRenderer

class LibgdxApplication(appConfig: AppConfig,
                        override val tileGrid: InternalTileGrid = RectangleTileGrid(
                                tileset = appConfig.defaultTileset,
                                size = appConfig.size))
    : Disposable, Application, ViewContainer by DefaultViewContainer(tileGrid) {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val renderer = LibgdxRenderer(tileGrid, appConfig.debugMode)

    private var started = false
    private var paused = false

    fun render() {
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
            logger.info("Starting LibgdxApplication")
            started = true
            renderer.create()
        } else {
            logger.error("LibgdxApplication already started")
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
