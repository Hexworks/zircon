package org.hexworks.zircon.internal.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.RunTimeStats
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.platform.util.SystemUtils
import java.util.concurrent.Executors

abstract class BaseApplication(
        private val config: AppConfig,
        override val tileGrid: TileGrid) : Application, CoroutineScope {

    abstract val renderer: Renderer

    override val coroutineContext = Executors.newSingleThreadExecutor().asCoroutineDispatcher() + SupervisorJob()

    private val logger = LoggerFactory.getLogger(this::class)
    private val renderInterval = 1000.div(config.fpsLimit)

    private var stopped = false
    private var running = false

    private var paused = false
    private var lastRender = 0L

    @Synchronized
    override fun start() {
        if (stopped.not() && running.not()) {
            renderer.create()
            running = true
            launch {
                while (!stopped && running) {
                    if (paused.not()) {
                        try {
                            val now = SystemUtils.getCurrentTimeMs()
                            if (now - lastRender > renderInterval) {
                                logger.debug(
                                        "Rendering because now: $now - last render: $lastRender > render interval: $renderInterval.")
                                doRender()
                                lastRender = now
                            }
                        } catch (e: Exception) {
                            logger.error("Render failed", e)
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

    @Synchronized
    override fun stop() {
        stopped = true
        running = false
        renderer.close()
        tileGrid.close()
        coroutineContext.cancel()
    }

    private fun doRender() {
        if (config.debugMode) {
            RunTimeStats.addTimedStatFor("debug.render.time") {
                renderer.render()
            }
        } else {
            renderer.render()
        }
    }
}
