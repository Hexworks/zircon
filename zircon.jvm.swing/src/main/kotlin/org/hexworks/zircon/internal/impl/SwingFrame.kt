package org.hexworks.zircon.internal.impl

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.SwingCanvasRenderer
import java.awt.Canvas
import javax.swing.JFrame

class SwingFrame(val tileGrid: InternalTileGrid,
                 config: AppConfig,
                 canvas: Canvas = Canvas()) : JFrame() {

    init {
        title = config.title
        add(canvas)
    }

    val renderer: SwingCanvasRenderer = SwingCanvasRenderer(
            canvas = canvas,
            frame = this,
            tileGrid = tileGrid,
            config = config)
}
