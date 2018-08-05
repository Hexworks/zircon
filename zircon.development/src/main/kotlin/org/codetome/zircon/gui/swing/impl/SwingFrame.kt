package org.codetome.zircon.gui.swing.impl

import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.gui.swing.application.SwingCanvasRenderer
import java.awt.Canvas
import javax.swing.JFrame

class SwingFrame(val tileGrid: TileGrid,
                 canvas: Canvas = Canvas()) : JFrame() {

    val renderer: SwingCanvasRenderer

    init {
        add(canvas)
        renderer = SwingCanvasRenderer(canvas, this, tileGrid)
    }
}
