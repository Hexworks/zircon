package org.hexworks.zircon.internal.application

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.application.filterByType
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.impl.SwingFrame
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.internal.renderer.SwingCanvasRenderer
import org.hexworks.zircon.internal.tileset.impl.DefaultTilesetLoader
import java.awt.Canvas
import java.awt.Graphics2D
import javax.swing.JFrame

internal class SwingApplication(
    config: AppConfig,
    eventBus: EventBus,
    tileGrid: InternalTileGrid,
    override val renderer: SwingCanvasRenderer
) : BaseApplication(config, tileGrid, eventBus) {

    init {
        System.setProperty("sun.java2d.opengl", "true")
        tileGrid.application = this
        renderer.onFrameClosed {
            stop()
        }
    }
}
