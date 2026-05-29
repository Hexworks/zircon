package org.hexworks.zircon.internal.graphics

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toTileGraphics
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.extensions.draw
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable
import org.hexworks.zircon.api.behavior.Boundable.Companion.create as boundable

open class DefaultLayer internal constructor(
    initialPosition: Position,
    initialContents: TileGraphics,
    private val movable: Movable = DefaultMovable(
        boundable(initialPosition, initialContents.size).toProperty()
    ),
    private val backend: InternalTileGraphics = FastTileGraphics(
        initialSize = initialContents.size,
        initialTileset = initialContents.tileset,
        initialTiles = initialContents.tiles
    )
) : Clearable, InternalLayer, Movable by movable, TileGraphics by backend {

    final override val id: UUID = UUID.randomUUID()

    final override val size: Size
        get() = backend.size

    final override val hiddenProperty = false.toProperty()
    final override var isHidden: Boolean by hiddenProperty.asDelegate()

    override fun asInternal() = this

    override fun getAbsoluteTileAtOrNull(position: Position): Tile? {
        return backend.getTileAtOrNull(position)
    }

    override fun getAbsoluteTileAtOrElse(position: Position, orElse: (position: Position) -> Tile): Tile {
        return getAbsoluteTileAtOrNull(position) ?: orElse(position)
    }

    final override fun drawAbsoluteTileAt(position: Position, tile: Tile) {
        backend.draw(tile, position - this.position)
    }

    override fun moveTo(position: Position): Boolean {
        return movable.moveTo(position)
    }

    final override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        backend.draw(tileMap, drawPosition, drawArea)
    }


    final override fun draw(tile: Tile, drawPosition: Position) {
        backend.draw(tile, drawPosition)
    }

    final override fun transform(transformer: (Position, Tile) -> Tile) {
        backend.transform(transformer)
    }

    final override fun clear() {
        backend.clear()
    }

    final override fun createCopy(): Layer {
        return DefaultLayer(
            initialPosition = position, initialContents = tiles.toTileGraphics(size, tileset)
        ).apply {
            isHidden = isHidden
        }
    }

    override fun render(): TileGraphics {
        return backend
    }

    override fun render(graphics: TileGraphics) {
        graphics.draw(backend)
    }

    override fun toString(): String {
        return backend.toString()
    }

}
