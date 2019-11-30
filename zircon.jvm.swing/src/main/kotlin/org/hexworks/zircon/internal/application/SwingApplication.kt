package org.hexworks.zircon.internal.application

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.mvc.ViewContainer
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.impl.SwingFrame
import org.hexworks.zircon.internal.mvc.DefaultViewContainer

class SwingApplication(private val config: AppConfig,
                       override val tileGrid: InternalTileGrid = ThreadSafeTileGrid(
                               initialTileset = config.defaultTileset,
                               initialSize = config.size)) : BaseApplication(config), ViewContainer by DefaultViewContainer(tileGrid) {

    private val frame = SwingFrame(
            tileGrid = tileGrid,
            config = config)
    override val renderer = frame.renderer

    companion object {

        fun create(appConfig: AppConfig = AppConfig.defaultConfiguration()): Application {
            return SwingApplication(appConfig)
        }

    }
}
