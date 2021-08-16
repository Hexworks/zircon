package org.hexworks.zircon.internal.application

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.impl.SwingFrame

class SwingApplication(
    private val config: AppConfig,
    override val tileGrid: InternalTileGrid = ThreadSafeTileGrid(config)
) : BaseApplication(config, tileGrid) {

    init {
        System.setProperty("sun.java2d.opengl", "true")
    }

    override val renderer = SwingFrame(
        tileGrid = tileGrid,
        config = config,
        app = this
    ).renderer

    companion object {

        fun create(appConfig: AppConfig = AppConfig.defaultConfiguration()): Application {
            return SwingApplication(appConfig)
        }

    }
}
