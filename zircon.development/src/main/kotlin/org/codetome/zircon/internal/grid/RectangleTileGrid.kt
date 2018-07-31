package org.codetome.zircon.internal.grid

import org.codetome.zircon.api.behavior.*
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.modifier.Modifier
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.internal.behavior.impl.DefaultLayerable
import org.codetome.zircon.internal.graphics.ConcurrentTileImage
import org.codetome.zircon.internal.graphics.CtrieTileImage
import org.codetome.zircon.internal.graphics.MapTileImage
import java.util.*


class RectangleTileGrid<T : Any, S : Any>(
        tileset: Tileset<T, S>,
        size: Size,
        override var backend: TileImage<T, S> = ConcurrentTileImage(
                size = size,
                tileset = tileset,
                styleSet = StyleSet.defaultStyle()),
        override var layerable: Layerable = DefaultLayerable(size))
    : InternalTileGrid<T, S> {

    override fun getTileAt(position: Position): Optional<Tile<T>> {
        return backend.getTileAt(position)
    }

    override fun setTileAt(position: Position, tile: Tile<T>) {
        backend.setTileAt(position, tile)
    }

    override fun createSnapshot(): Map<Position, Tile<T>> {
        return backend.createSnapshot()
    }

    override fun draw(drawable: Drawable<T>, offset: Position) {
       backend.draw(drawable, offset)
    }

    override fun pushLayer(layer: Layer<out Any, out Any>) {
        layerable.pushLayer(layer)
    }

    override fun popLayer(): Optional<Layer<out Any, out Any>> {
        return layerable.popLayer()
    }

    override fun removeLayer(layer: Layer<out Any, out Any>) {
        layerable.removeLayer(layer)
    }

    override fun getLayers(): List<Layer<out Any, out Any>> {
        return layerable.getLayers()
    }

    override fun getBoundableSize(): Size {
        return backend.getBoundableSize()
    }

    override fun intersects(boundable: Boundable): Boolean {
        return backend.intersects(boundable)
    }

    override fun containsPosition(position: Position): Boolean {
        return backend.containsPosition(position)
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        return backend.containsBoundable(boundable)
    }

    override fun tileset(): Tileset<T, S> {
        return backend.tileset()
    }

    override fun useTileset(tileset: Tileset<T, S>) {
        backend.useTileset(tileset)
    }

    override fun toStyleSet(): StyleSet {
        return backend.toStyleSet()
    }

    override fun getBackgroundColor(): TextColor {
        return backend.getBackgroundColor()
    }

    override fun setBackgroundColor(backgroundColor: TextColor) {
        backend.setBackgroundColor(backgroundColor)
    }

    override fun getForegroundColor(): TextColor {
        return backend.getForegroundColor()
    }

    override fun setForegroundColor(foregroundColor: TextColor) {
        backend.setForegroundColor(foregroundColor)
    }

    override fun enableModifiers(modifiers: Set<Modifier>) {
        backend.enableModifiers(modifiers)
    }

    override fun enableModifiers(vararg modifiers: Modifier) {
        enableModifiers(modifiers.toSet())
    }

    override fun disableModifiers(modifiers: Set<Modifier>) {
        backend.disableModifiers(modifiers)
    }

    override fun disableModifiers(vararg modifiers: Modifier) {
        disableModifiers(modifiers.toSet())
    }

    override fun setModifiers(modifiers: Set<Modifier>) {
        backend.setModifiers(modifiers)
    }

    override fun clearModifiers() {
        backend.clearModifiers()
    }

    override fun resetColorsAndModifiers() {
        backend.resetColorsAndModifiers()
    }

    override fun getActiveModifiers(): Set<Modifier> {
        return backend.getActiveModifiers()
    }

    override fun setStyleFrom(source: StyleSet) {
        backend.setStyleFrom(source)
    }

    private var originalBackend = backend
    private var originalLayerable = layerable

    override fun useContentsOf(tileGrid: InternalTileGrid<T, S>) {
        backend = tileGrid.backend
        layerable = tileGrid.layerable
    }

    override fun reset() {
        backend = originalBackend
        layerable = originalLayerable
    }

    override fun close() {
        // TODO: listen to close on event bus ?
    }

    override fun clear() {
        backend.clear()
        layerable = DefaultLayerable(backend.getBoundableSize())
    }


}
