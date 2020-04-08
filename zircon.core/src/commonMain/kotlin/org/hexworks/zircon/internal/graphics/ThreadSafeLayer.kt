package org.hexworks.zircon.internal.graphics

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.core.platform.factory.UUIDFactory
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toTileGraphics
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable
import org.hexworks.zircon.internal.data.DefaultLayerState
import kotlin.jvm.Synchronized

open class ThreadSafeLayer(
        initialPosition: Position,
        initialContents: TileGraphics,
        private val movable: Movable = DefaultMovable(
                position = initialPosition,
                size = initialContents.size),
        private val backend: ThreadSafeTileGraphics = TileGraphicsBuilder.newBuilder()
                .withSize(initialContents.size)
                .withTiles(initialContents.tiles)
                .withTileset(initialContents.tileset)
                .build() as ThreadSafeTileGraphics
) : Clearable, TileGraphics by backend, InternalLayer, Boundable by movable {

    final override val id: UUID = UUIDFactory.randomUUID()

    final override val state: DefaultLayerState
        get() {
            val backendState = backend.state
            return DefaultLayerState(
                    tiles = backendState.tiles,
                    tileset = backendState.tileset,
                    size = backendState.size,
                    position = position,
                    isHidden = isHidden,
                    id = id)
        }

    final override val size: Size
        get() = backend.size
    final override val width: Int
        get() = backend.size.width
    final override val height: Int
        get() = backend.size.height
    final override val rect: Rect
        get() = movable.rect

    final override val hiddenProperty = createPropertyFrom(false)
    final override var isHidden: Boolean by hiddenProperty.asDelegate()

    override fun asInternal() = this

    override fun toString(): String {
        return DrawSurfaces.tileGraphicsBuilder()
                .withSize(size)
                .withTiles(tiles)
                .build()
                .toString()
    }

    final override fun getAbsoluteTileAt(position: Position): Maybe<Tile> {
        return backend.getTileAt(position - this.position)
    }

    @Synchronized
    final override fun drawAbsoluteTileAt(position: Position, tile: Tile) {
        backend.draw(tile, position - this.position)
    }

    final override fun createCopy(): Layer {
        val (currentTiles, currentTileset, currentSize, _, currentPosition, currentlyHidden) = state
        return ThreadSafeLayer(
                initialPosition = currentPosition,
                initialContents = currentTiles.toTileGraphics(currentSize, currentTileset)).apply {
            isHidden = currentlyHidden
        }
    }

    final override fun toTileImage() = backend.toTileImage()

    final override fun toLayer(offset: Position) = apply {
        moveTo(offset)
    }

    final override fun toResized(newSize: Size) = backend.toResized(newSize)

    final override fun toResized(newSize: Size, filler: Tile) = backend.toResized(newSize, filler)


    final override fun toSubTileGraphics(rect: Rect) = SubTileGraphics(rect, this)

    @Synchronized
    override fun moveTo(position: Position): Boolean {
        return movable.moveTo(position)
    }

    @Synchronized
    final override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        backend.draw(tileMap, drawPosition, drawArea)
    }


    @Synchronized
    final override fun draw(tile: Tile, drawPosition: Position) {
        backend.draw(tile, drawPosition)
    }

    @Synchronized
    final override fun draw(tileComposite: TileComposite) {
        backend.draw(tileComposite)
    }

    @Synchronized
    final override fun draw(tileComposite: TileComposite, drawPosition: Position) {
        backend.draw(tileComposite, drawPosition)
    }

    @Synchronized
    final override fun draw(tileComposite: TileComposite, drawPosition: Position, drawArea: Size) {
        backend.draw(tileComposite, drawPosition, drawArea)
    }

    @Synchronized
    final override fun draw(tileMap: Map<Position, Tile>) {
        backend.draw(tileMap)
    }

    @Synchronized
    final override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position) {
        backend.draw(tileMap, drawPosition)
    }

    @Synchronized
    final override fun fill(filler: Tile) {
        backend.fill(filler)
    }

    @Synchronized
    final override fun transform(transformer: (Position, Tile) -> Tile) {
        backend.transform(transformer)
    }

    final override fun applyStyle(styleSet: StyleSet) {
        backend.applyStyle(styleSet)
    }

    @Synchronized
    final override fun clear() {
        backend.clear()
    }
}
