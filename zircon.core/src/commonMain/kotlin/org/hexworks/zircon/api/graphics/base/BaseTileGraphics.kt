package org.hexworks.zircon.api.graphics.base

import org.hexworks.cobalt.databinding.api.extension.orElseGet
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toTileGraphics
import org.hexworks.zircon.api.extensions.toTileImage
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.graphics.InternalTileGraphics

/**
 * This base class for [TileGraphics] can be re-used by complex image classes like layers,
 * boxes, components, and more.
 * All classes which are implementing the [TileGraphics] operations can
 * use this class as a base class.
 */
abstract class BaseTileGraphics(
    initialTileset: TilesetResource,
    initialSize: Size
) : InternalTileGraphics, TilesetOverride {

    final override val tilesetProperty = initialTileset.toProperty(validator = { oldValue, newValue ->
        oldValue isCompatibleWith newValue
    })

    override var tileset: TilesetResource by tilesetProperty.asDelegate()

    final override val size = initialSize

    override fun draw(tileComposite: TileComposite) = draw(
        tileComposite = tileComposite,
        drawPosition = Position.zero(),
        drawArea = tileComposite.size
    )

    override fun draw(
        tileComposite: TileComposite,
        drawPosition: Position
    ) = draw(
        tileComposite = tileComposite,
        drawPosition = drawPosition,
        drawArea = tileComposite.size
    )

    override fun draw(
        tileComposite: TileComposite,
        drawPosition: Position,
        drawArea: Size
    ) = draw(
        tileMap = tileComposite.tiles,
        drawPosition = drawPosition,
        drawArea = drawArea
    )

    override fun draw(tileMap: Map<Position, Tile>) = draw(
        tileMap = tileMap,
        drawPosition = Position.zero(),
        drawArea = this.size
    )

    override fun draw(
        tileMap: Map<Position, Tile>,
        drawPosition: Position
    ) = draw(
        tileMap = tileMap,
        drawPosition = drawPosition,
        drawArea = this.size
    )

    override fun toString(): String {
        val currTiles = tiles
        return (0 until height).joinToString("") { y ->
            (0 until width).joinToString("") { x ->
                (currTiles[Position.create(x, y)] ?: Tile.defaultTile())
                    .asCharacterTileOrNull()
                    .orElseGet { Tile.defaultTile() }
                    .character.toString()
            }.plus("\n")
        }.trim()
    }

    override fun toDrawWindow(
        rect: Rect
    ) = DrawWindow(
        rect = rect,
        backend = this
    )

    override fun toTileImage() = tiles.toTileImage(size, tileset)

    override fun toLayer(offset: Position) = if (this is Layer) this else layer {
        this.offset = offset
        tileGraphics = createCopy()
    }

    override fun toResized(newSize: Size): TileGraphics = toResized(newSize, Tile.empty())

    override fun toResized(newSize: Size, filler: Tile): TileGraphics {
        val newTiles = mutableMapOf<Position, Tile>()
        val positions = newSize.fetchPositions().toSet()
        tiles.forEach { (pos, tile) ->
            if (positions.contains(pos)) newTiles[pos] = tile
        }
        if (filler != Tile.empty()) {
            positions.subtract(size.fetchPositions().toSet()).forEach { pos ->
                newTiles[pos] = filler
            }
        }
        return newTiles.toTileGraphics(newSize, tileset)
    }

    override fun createCopy(): TileGraphics {
        return tiles.toMutableMap().toTileGraphics(size, tileset)
    }

    override fun applyStyle(styleSet: StyleSet) {
        transform { _, tile ->
            tile.withStyle(styleSet)
        }
    }
}
