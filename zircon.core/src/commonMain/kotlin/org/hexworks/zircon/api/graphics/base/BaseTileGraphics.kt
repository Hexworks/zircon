package org.hexworks.zircon.api.graphics.base

import org.hexworks.cobalt.databinding.api.toProperty
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toTileGraphics
import org.hexworks.zircon.api.extensions.toTileImage
import org.hexworks.zircon.api.graphics.*
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * This base class for [TileGraphics] can be re-used by complex image classes like layers,
 * boxes, components, and more.
 * All classes which are implementing the [TileGraphics] operations can
 * use this class as a base class.
 */
abstract class BaseTileGraphics(
        initialTileset: TilesetResource,
        initialSize: Size)
    : TileGraphics, TilesetOverride {

    final override val tilesetProperty = initialTileset.toProperty { newValue ->
        tileset.isCompatibleWith(newValue)
    }

    override var tileset: TilesetResource by tilesetProperty.asDelegate()

    final override val size = initialSize

    final override fun draw(tileComposite: TileComposite) = draw(
            tileComposite = tileComposite,
            drawPosition = Position.zero(),
            drawArea = tileComposite.size)

    final override fun draw(tileComposite: TileComposite,
                            drawPosition: Position) = draw(
            tileComposite = tileComposite,
            drawPosition = drawPosition,
            drawArea = tileComposite.size)

    final override fun draw(tileComposite: TileComposite,
                            drawPosition: Position,
                            drawArea: Size) = draw(
            tileMap = tileComposite.tiles,
            drawPosition = drawPosition,
            drawArea = drawArea)

    final override fun draw(tileMap: Map<Position, Tile>) = draw(
            tileMap = tileMap,
            drawPosition = Position.zero(),
            drawArea = this.size)

    final override fun draw(tileMap: Map<Position, Tile>,
                            drawPosition: Position) = draw(
            tileMap = tileMap,
            drawPosition = drawPosition,
            drawArea = this.size)

    override fun toString(): String {
        val currTiles = tiles
        return (0 until height).joinToString("") { y ->
            (0 until width).joinToString("") { x ->
                (currTiles[Position.create(x, y)] ?: Tile.defaultTile())
                        .asCharacterTile()
                        .orElse(Tile.defaultTile())
                        .character.toString()
            }.plus("\n")
        }.trim()
    }

    override fun toSubTileGraphics(
            rect: Rect): SubTileGraphics {
        return SubTileGraphics(
                rect = rect,
                backend = this)
    }

    override fun toTileImage(): TileImage {
        val (tiles, tileset, size) = state
        return tiles.toTileImage(size, tileset)
    }

    override fun toLayer(offset: Position): Layer {
        return if (this is Layer) {
            this.apply {
                moveTo(offset)
            }
        } else {
            LayerBuilder.newBuilder()
                    .withOffset(offset)
                    .withTileGraphics(createCopy())
                    .build()
        }
    }

    override fun toResized(newSize: Size): TileGraphics = toResized(newSize, Tile.empty())

    override fun toResized(newSize: Size, filler: Tile): TileGraphics {
        val (tiles, tileset) = state
        val newTiles = mutableMapOf<Position, Tile>()
        tiles.forEach { (pos, tile) ->
            if (newSize.containsPosition(pos)) newTiles[pos] = tile
        }
        if (filler != Tile.empty()) {
            newSize.fetchPositions().subtract(size.fetchPositions()).forEach { pos ->
                newTiles[pos] = filler
            }
        }
        return newTiles.toTileGraphics(newSize, tileset)
    }

    override fun createCopy(): TileGraphics {
        val (tiles, tileset, size) = state
        return tiles.toTileGraphics(size, tileset)
    }

    override fun transform(transformer: (Position, Tile) -> Tile) {
        val (tiles, _, size) = state
        val newTiles = mutableMapOf<Position, Tile>()
        size.fetchPositions().forEach { pos ->
            newTiles[pos] = transformer(pos, tiles.getOrElse(pos) { Tile.defaultTile() })
        }
        draw(newTiles)
    }

    override fun applyStyle(styleSet: StyleSet) {
        transform { _, tile ->
            tile.withStyle(styleSet)
        }
    }
}
