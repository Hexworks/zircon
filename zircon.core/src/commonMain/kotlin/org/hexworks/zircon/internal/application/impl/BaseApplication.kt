package org.hexworks.zircon.internal.application.impl

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.hexworks.cobalt.databinding.api.binding.bindNot
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.RunTimeStats
import org.hexworks.zircon.internal.application.InternalApplication
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.platform.util.SystemUtils

abstract class BaseApplication<A : Application>(
    final override val config: AppConfig,
    final override val tileGrid: TileGrid,
    final override val eventBus: EventBus,
    final override val eventScope: ZirconScope = ZirconScope(),
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
) : InternalApplication {

    abstract val renderer: Renderer<A>

    private val beforeRenderData = RenderData(SystemUtils.getCurrentTimeMs()).toProperty()
    private val afterRenderData = RenderData(SystemUtils.getCurrentTimeMs()).toProperty()

    private val logger = LoggerFactory.getLogger(this::class)
    private val renderIntervalMs = 1000.div(config.fpsLimit)

    private var lastRender = 0L

    data class Command(val fn: suspend () -> Unit)

    private val channel = Channel<Command>()
    private var renderLoop: Job? = null

    final override val closed: Boolean
        get() = closedValue.value
    final override val closedValue: Property<Boolean> = false.toProperty()

    private val running = closedValue.bindNot()

    init {
        coroutineScope.launch {
            for (cmd in channel) {
                cmd.fn()
            }
        }
        executeCommand {
            renderer.create()
            startRenderLoop()
        }
    }

    private fun startRenderLoop() {
        renderLoop = executeCommand {
            while (running.value) {
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

    override fun close() {
        executeCommand {
            closedValue.value = true
            renderLoop?.cancel()
            renderer.close()
            tileGrid.close()
            coroutineScope.cancel()
            channel.close()
        }
    }

    override fun beforeRender(listener: (RenderData) -> Unit) = beforeRenderData.onChange {
        listener(it.newValue)
    }

    override fun afterRender(listener: (RenderData) -> Unit) = afterRenderData.onChange {
        listener(it.newValue)
    }

    override fun asInternal() = this

    private fun executeCommand(fn: suspend () -> Unit): Job = coroutineScope.launch {
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
