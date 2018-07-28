package org.codetome.zircon.poc.drawableupgrade

import org.codetome.zircon.poc.drawableupgrade.drawables.DrawSurface
import org.codetome.zircon.poc.drawableupgrade.drawables.Layerable
import org.codetome.zircon.poc.drawableupgrade.drawables.TileGrid
import org.codetome.zircon.poc.drawableupgrade.drawables.TileImage
import org.codetome.zircon.poc.drawableupgrade.tileimage.DefaultLayerable
import org.codetome.zircon.poc.drawableupgrade.tileimage.ThreadedTileImage
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset


class RectangleTileGrid<KEY : Any, SOURCE : Any>(
        private val width: Int,
        private val height: Int,
        private val tileset: Tileset<KEY, SOURCE>,
        private val backend: TileImage<KEY, SOURCE> = ThreadedTileImage(width, height, tileset),
        private val layerable: Layerable<SOURCE> = DefaultLayerable())
    : TileGrid<KEY, SOURCE>, DrawSurface<KEY> by backend, Layerable<SOURCE> by layerable {

    override fun tileset() = tileset

    override fun getColumnCount() = width

    override fun getRowCount() = height
}
