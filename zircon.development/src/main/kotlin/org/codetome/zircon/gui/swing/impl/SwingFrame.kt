package org.codetome.zircon.gui.swing.impl

import org.codetome.zircon.api.grid.TileGrid
import java.awt.Canvas
import java.awt.image.BufferedImage
import javax.swing.JFrame

class SwingFrame(val grid: TileGrid<out Any, BufferedImage>,
                 canvas: Canvas = Canvas()) : JFrame() {

    val renderer: SwingCanvasRenderer

    init {
        add(canvas)
        renderer = SwingCanvasRenderer(canvas, this, grid)
    }
}
