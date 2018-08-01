package org.codetome.zircon.swing.impl

import org.codetome.zircon.api.application.Application
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.internal.grid.RectangleTileGrid
import java.awt.image.BufferedImage

class SwingApplication<T : Any>(size: Size,
                                tileset: Tileset<T, BufferedImage>) : Application {

    val tileGrid: TileGrid<T, BufferedImage> = RectangleTileGrid(
            tileset = tileset,
            size = size)

    private val frame = SwingFrame(tileGrid)

    override fun create() {
        frame.isVisible = true
        frame.renderer.create()
    }

    override fun render() {
        frame.renderer.render()
    }

    override fun dispose() {
        frame.renderer.dispose()
        tileGrid.close()
    }

    companion object {

        fun <T : Any> create(size: Size,
                             tileset: Tileset<T, BufferedImage>) =
                SwingApplication(size, tileset)

        fun <T : Any> createLooped(size: Size,
                             tileset: Tileset<T, BufferedImage>) : SwingApplication<T> {
            val result = SwingApplication(size, tileset)
            result.create()
            Thread {
                try {

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
            return result
        }

    }
}
