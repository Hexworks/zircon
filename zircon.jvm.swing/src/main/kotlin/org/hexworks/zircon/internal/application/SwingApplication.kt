package org.hexworks.zircon.internal.application

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.internal.RunTimeStats
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.RectangleTileGrid
import org.hexworks.zircon.internal.impl.SwingFrame
import java.util.concurrent.Executors
import java.util.concurrent.Future

class SwingApplication(private val appConfig: AppConfig) : Application {

    override val tileGrid: InternalTileGrid = RectangleTileGrid(
            tileset = appConfig.defaultTileset,
            size = appConfig.size)

    private var running = false
    private var paused = false

    private val frame = SwingFrame(tileGrid)
    private val renderer = frame.renderer
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var renderJob: Future<*>

    override fun start() {
        if (running.not()) {
            frame.isVisible = true
            renderer.create()
            running = true
            renderJob = executor.submit {
                while (running) {
                    if (paused.not()) {
                        try {
                            if (appConfig.debugMode) {
                                RunTimeStats.addTimedStatFor("debug.render.time") {
                                    renderer.render()
                                }
                            } else {
                                renderer.render()
                            }
                        } catch (e: Exception) {
                            System.err.println("Rendering failed:")
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    override fun pause() {
        paused = true
    }

    override fun resume() {
        paused = false
    }

    override fun stop() {
        running = false
        renderJob.cancel(false)
        executor.shutdownNow()
        frame.renderer.close()
        tileGrid.close()
    }

    companion object {

        fun create(
                appConfig: AppConfig = AppConfig.defaultConfiguration()): Application {
            return SwingApplication(appConfig)
        }


    }
}
