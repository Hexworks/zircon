package org.codetome.zircon.poc.drawableupgrade.grid

import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.internal.behavior.impl.DefaultStyleable
import org.codetome.zircon.poc.drawableupgrade.drawables.DrawSurface
import org.codetome.zircon.poc.drawableupgrade.drawables.Layerable
import org.codetome.zircon.poc.drawableupgrade.drawables.TileImage
import org.codetome.zircon.poc.drawableupgrade.drawables.TilesetOverride
import org.codetome.zircon.poc.drawableupgrade.tileimage.DefaultLayerable
import org.codetome.zircon.poc.drawableupgrade.tileimage.ThreadedTileImage
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset


class RectangleTileGrid<T : Any, S : Any>(
        tileset: Tileset<T, S>,
        private val size: Size,
        private var backend: TileImage<T, S> = ThreadedTileImage(
                size = size,
                tileset = tileset,
                styleSet = StyleSet.defaultStyle()),
        styleable: Styleable = DefaultStyleable(StyleSet.defaultStyle()),
        private var layerable: Layerable = DefaultLayerable(size))
    : TileGrid<T, S>,
        DrawSurface<T> by backend,
        TilesetOverride<T, S> by backend,
        Styleable by styleable,
        Layerable by layerable {

    override fun close() {
        // TODO: listen to close on event bus
    }

    override fun clear() {
        layerable = DefaultLayerable(size)
        backend.clear()
    }

}
