package org.hexworks.zircon.internal.application

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.internal.application.impl.BaseApplication
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.SwingRenderer

class SwingApplication(
    config: AppConfig,
    eventBus: EventBus,
    tileGrid: InternalTileGrid,
    override val renderer: SwingRenderer
) : BaseApplication(config, tileGrid, eventBus) {

    init {
        System.setProperty("sun.java2d.opengl", "true")
        tileGrid.application = this
        renderer.onFrameClosed {
            stop()
        }
    }
}
