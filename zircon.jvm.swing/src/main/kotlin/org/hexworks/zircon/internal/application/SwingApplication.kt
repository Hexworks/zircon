package org.hexworks.zircon.internal.application

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.mvc.ViewContainer
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.impl.SwingFrame
import org.hexworks.zircon.internal.mvc.DefaultViewContainer

class SwingApplication(private val appConfig: AppConfig,
                       override val tileGrid: InternalTileGrid = ThreadSafeTileGrid(
                               initialTileset = appConfig.defaultTileset,
                               initialSize = appConfig.size)) : BaseApplication(appConfig), ViewContainer by DefaultViewContainer(tileGrid) {

    private val frame = SwingFrame(tileGrid)
    override val renderer = frame.renderer

    companion object {

        fun create(
                appConfig: AppConfig = AppConfig.defaultConfiguration()): Application {
            return SwingApplication(appConfig)
        }

    }
}
