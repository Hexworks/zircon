package org.hexworks.zircon.internal.application

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.mvc.ViewContainer
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.RectangleTileGrid
import org.hexworks.zircon.internal.mvc.DefaultViewContainer
import org.hexworks.zircon.internal.renderer.VirtualRenderer

class VirtualApplication(private val appConfig: AppConfig,
                         override val tileGrid: InternalTileGrid = RectangleTileGrid(
                                 tileset = appConfig.defaultTileset,
                                 size = appConfig.size)) : BaseApplication(appConfig), ViewContainer by DefaultViewContainer(tileGrid) {

    override val renderer = VirtualRenderer(tileGrid)

    companion object {

        fun create(appConfig: AppConfig = AppConfig.defaultConfiguration()): Application {
            return VirtualApplication(appConfig)
        }

    }
}
