package org.hexworks.zircon.internal.impl

import org.hexworks.zircon.internal.grid.InternalTileGrid
import java.awt.Canvas
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO
import javax.swing.JFrame

class SwingFrame(
    val tileGrid: InternalTileGrid,
    canvas: Canvas,
) : JFrame() {

    private val config = tileGrid.config

    init {
        title = config.title
        if (config.iconData != null) {
            ByteArrayInputStream(config.iconData).use { inputStream ->
                iconImage = ImageIO.read(inputStream)
            }
        } else if (config.iconPath != null) {
            ClassLoader.getSystemResourceAsStream(config.iconPath).use { inputStream ->
                iconImage = ImageIO.read(inputStream)
            }
        }
        add(canvas)
    }
}
