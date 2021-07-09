package org.hexworks.zircon.internal.application

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.RunTimeStats
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.platform.util.SystemUtils

abstract class BaseApplication(
    private val config: AppConfig,
    override val tileGrid: TileGrid,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
) : Application {

    abstract val renderer: Renderer

    private val beforeRenderData = RenderData(SystemUtils.getCurrentTimeMs()).toProperty()
    private val afterRenderData = RenderData(SystemUtils.getCurrentTimeMs()).toProperty()

    private val logger = LoggerFactory.getLogger(this::class)
    private val renderIntervalMs = 1000.div(config.fpsLimit)

    private var stopped = false
    private var running = false

    private var lastRender = 0L

    data class Command(val fn: suspend () -> Unit)

    private val channel = Channel<Command>()
    private lateinit var renderLoop: Job

    init {
        scope.launch {
            for (cmd in channel) {
                cmd.fn()
            }
        }
    }

    override fun start() {
        executeCommand {
            if (stopped.not() && running.not()) {
                renderer.create()
                running = true
                startRenderLoop()
            }
        }
    }

    private fun startRenderLoop() {
        renderLoop = executeCommand {
            while (!stopped && running) {
                try {
                    val now = SystemUtils.getCurrentTimeMs()
                    val elapsedTimeMs = now - lastRender
                    if (elapsedTimeMs > renderIntervalMs) {
                        logger.debug {
                            "Rendering because now: $now - last render: $lastRender > render interval: $renderIntervalMs."
                        }
                        doRender()
                        lastRender = now
                    } else {
                        delay(renderIntervalMs - elapsedTimeMs)
                    }
                } catch (e: Exception) {
                    logger.error("Render failed", e)
                }
            }
        }
    }

    override fun pause() {
        renderLoop.cancel()
    }

    override fun resume() {
        startRenderLoop()
    }

    override fun stop() {
        executeCommand {
            stopped = true
            running = false
            renderLoop.cancel()
            renderer.close()
            tileGrid.close()
            scope.cancel()
            channel.close()
        }
    }

    override fun beforeRender(listener: (RenderData) -> Unit) = beforeRenderData.onChange {
        listener(it.newValue)
    }

    override fun afterRender(listener: (RenderData) -> Unit) = afterRenderData.onChange {
        listener(it.newValue)
    }

    private fun executeCommand(fn: suspend () -> Unit): Job = scope.launch {
        channel.send(Command(fn))
    }

    private fun doRender() {
        beforeRenderData.value = RenderData(SystemUtils.getCurrentTimeMs())
        if (config.debugMode) {
            RunTimeStats.addTimedStatFor("Rendering time") {
                renderer.render()
            }
        } else {
            renderer.render()
        }
        afterRenderData.value = RenderData(SystemUtils.getCurrentTimeMs())
    }
}
