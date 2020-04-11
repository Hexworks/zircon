package org.hexworks.zircon.internal.impl

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.SwingCanvasRenderer
import java.awt.Canvas
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO
import javax.swing.JFrame

class SwingFrame(val tileGrid: InternalTileGrid,
                 config: AppConfig,
                 canvas: Canvas = Canvas(),
                 app: Application) : JFrame() {

    init {
        title = config.title
        if (config.icon != null) {
            ByteArrayInputStream(config.icon)
                .use { inputStream -> iconImage = ImageIO.read(inputStream) }
        }
        add(canvas)
    }

    val renderer: SwingCanvasRenderer = SwingCanvasRenderer(
            canvas = canvas,
            frame = this,
            tileGrid = tileGrid,
            config = config,
            app = app)
}
