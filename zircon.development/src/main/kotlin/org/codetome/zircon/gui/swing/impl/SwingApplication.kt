package org.codetome.zircon.gui.swing.impl

import org.codetome.zircon.RunTimeStats
import org.codetome.zircon.api.application.Application
import org.codetome.zircon.api.grid.ApplicationConfiguration
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.internal.grid.RectangleTileGrid
import java.util.concurrent.Executors
import java.util.concurrent.Future

class SwingApplication(private val appConfig: ApplicationConfiguration) : Application {

    val tileGrid: TileGrid = RectangleTileGrid(
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
                        if (appConfig.debugMode) {
                            RunTimeStats.addTimedStatFor("debug.render.time") {
                                renderer.render()
                            }
                        } else {
                            renderer.render()
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
        frame.renderer.dispose()
        tileGrid.close()
    }

    companion object {

        fun create(
                appConfig: ApplicationConfiguration = ApplicationConfiguration.defaultConfiguration()) =
                SwingApplication(appConfig)

    }
}
