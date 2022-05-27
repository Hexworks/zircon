package org.hexworks.zircon.internal.application

import com.badlogic.gdx.utils.Disposable
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.renderer.LibgdxRenderer
import org.hexworks.zircon.platform.util.SystemUtils

class LibgdxApplication(
    override val config: AppConfig,
    override val eventBus: EventBus,
    override val tileGrid: ThreadSafeTileGrid = ThreadSafeTileGrid(config),
    override val eventScope: ZirconScope = ZirconScope()
) : Disposable, InternalApplication {

    init {
        tileGrid.application = this
    }

    private val logger = LoggerFactory.getLogger(this::class)

    private val renderer = LibgdxRenderer(tileGrid, config.debugMode)

    private val beforeRenderData = RenderData(SystemUtils.getCurrentTimeMs()).toProperty()
    private val afterRenderData = RenderData(SystemUtils.getCurrentTimeMs()).toProperty()

    private var started = false
    private var paused = false

    fun render() {
        if (paused.not() && started) {
            beforeRenderData.value = RenderData(SystemUtils.getCurrentTimeMs())
            renderer.render()
            afterRenderData.value = RenderData(SystemUtils.getCurrentTimeMs())
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

    override fun beforeRender(listener: (RenderData) -> Unit) = beforeRenderData.onChange {
        listener(it.newValue)
    }

    override fun afterRender(listener: (RenderData) -> Unit) = afterRenderData.onChange {
        listener(it.newValue)
    }

    override fun asInternal(): InternalApplication = this

    companion object {
        fun create(
            config: AppConfig = AppConfig.defaultConfiguration(),
            eventBus: EventBus = EventBus.create()
        ): Application {
            return LibgdxApplication(config, eventBus)
        }
    }
}
