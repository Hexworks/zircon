package org.codetome.zircon.poc.drawableupgrade.grid

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.internal.behavior.impl.DefaultStyleable
import org.codetome.zircon.poc.drawableupgrade.drawables.Drawable
import org.codetome.zircon.poc.drawableupgrade.drawables.Layer
import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset
import java.util.*
import java.util.concurrent.LinkedBlockingQueue


class RectangleTileGrid<T : Any, S : Any>(
        private val size: Size,
        private val tileset: Tileset<T, S>,
        styleable: Styleable = DefaultStyleable(StyleSet.defaultStyle()))
    : TileGrid<T, S>,
        Styleable by styleable {

    private val layers = LinkedBlockingQueue<Layer<out Any, out Any>>()


    override fun getTileAt(position: Position): Optional<Tile<T>> {
        TODO("not implemented")
    }

    override fun setTileAt(position: Position, tile: Tile<T>) {
        TODO("not implemented")
    }

    override fun createSnapshot(): Map<Position, Tile<T>> {
        TODO("not implemented")
    }

    override fun draw(drawable: Drawable<T>, offset: Position) {
        TODO("not implemented")
    }

    override fun tileset(): Tileset<T, S> {
        TODO("not implemented")
    }

    override fun useTileset(tileset: Tileset<T, S>) {
        TODO("not implemented")
    }

    override fun pushLayer(layer: Layer<out Any, out Any>) {
        TODO("not implemented")
    }

    override fun popLayer(): Optional<Layer<out Any, out Any>> {
        TODO("not implemented")
    }

    override fun removeLayer(layer: Layer<out Any, out Any>) {
        TODO("not implemented")
    }

    override fun getLayers(): List<Layer<out Any, out Any>> {
        TODO("not implemented")
    }

    override fun getBoundableSize(): Size {
        TODO("not implemented")
    }

    override fun intersects(boundable: Boundable): Boolean {
        TODO("not implemented")
    }

    override fun containsPosition(position: Position): Boolean {
        TODO("not implemented")
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        TODO("not implemented")
    }


    override fun close() {
        // TODO: listen to close on event bus
    }

    override fun clear() {
    }

}
