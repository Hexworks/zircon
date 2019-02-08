package org.hexworks.zircon.internal.impl

import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.SwingCanvasRenderer
import java.awt.Canvas
import javax.swing.JFrame

class SwingFrame(val tileGrid: TileGrid,
                 canvas: Canvas = Canvas()) : JFrame() {

    val renderer: SwingCanvasRenderer

    init {
        title = RuntimeConfig.config.title
        add(canvas)
        require(tileGrid is InternalTileGrid) {
            "The supplied TileGrid is not an instance of InternalTileGrid, can't use it."
        }
        renderer = SwingCanvasRenderer(canvas, this, tileGrid)
    }
}
