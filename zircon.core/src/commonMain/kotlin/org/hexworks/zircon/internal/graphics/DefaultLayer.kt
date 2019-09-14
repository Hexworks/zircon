package org.hexworks.zircon.internal.graphics

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Snapshot
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.util.TileTransformer
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable

class DefaultLayer(position: Position,
                   val backend: TileGraphics,
                   private val movable: DefaultMovable = DefaultMovable(
                           position = position,
                           size = backend.size))
    : Layer,
        DrawSurface by backend,
        Drawable by backend,
        Movable by movable {

    override val size: Size
        get() = movable.size

    override val width: Int
        get() = size.width

    override val height: Int
        get() = size.height

    override val hiddenProperty = createPropertyFrom(false)

    override var isHidden: Boolean by hiddenProperty.asDelegate()


    override fun createSnapshot(): Snapshot {
        return if (isHidden) {
            Snapshot.create(listOf(), currentTileset())
        } else {
            backend.createSnapshot().let { snapshot ->
                Snapshot.create(
                        cells = snapshot.cells.map { it.withPosition(it.position + position) },
                        tileset = snapshot.tileset)
            }
        }
    }

    override fun transform(transformer: TileTransformer) {
        backend.transform(transformer)
    }

    override fun toTileImage(): TileImage {
        return backend.toTileImage()
    }

    override fun toTileGraphics(): TileGraphics {
        return backend.createCopy()
    }

    override fun getAbsoluteTileAt(position: Position): Maybe<Tile> {
        return backend.getTileAt(position - this.position)
    }

    override fun setAbsoluteTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position - this.position, tile)
    }

    override fun createCopy() = DefaultLayer(
            position = position,
            backend = backend.createCopy())

    override fun fill(filler: Tile): Layer {
        backend.fill(filler)
        return this
    }

    override fun clear() {
        backend.clear()
    }

}
