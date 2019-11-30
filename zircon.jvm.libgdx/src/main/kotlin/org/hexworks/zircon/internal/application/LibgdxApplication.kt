package org.hexworks.zircon.internal.application

import com.badlogic.gdx.utils.Disposable
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.renderer.LibgdxRenderer

class LibgdxApplication(appConfig: AppConfig,
                        override val tileGrid: InternalTileGrid = ThreadSafeTileGrid(
                                initialTileset = appConfig.defaultTileset,
                                initialSize = appConfig.size))
    : Disposable, Application {

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
            renderer.create()
            started = true
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
