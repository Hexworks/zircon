package org.codetome.zircon.internal.screen

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.Tileset
import java.util.*

class TileGridScreen<T: Any, S: Any> : Screen<T, S> {
    override fun display() {
        TODO("not implemented")
    }

    override fun close() {
        TODO("not implemented")
    }

    override fun clear() {
        TODO("not implemented")
    }

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

    override fun tileset(): Tileset<T, S> {
        TODO("not implemented")
    }

    override fun useTileset(tileset: Tileset<T, S>) {
        TODO("not implemented")
    }
}
