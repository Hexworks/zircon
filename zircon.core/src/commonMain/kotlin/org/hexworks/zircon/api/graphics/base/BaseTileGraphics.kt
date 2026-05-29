package org.hexworks.zircon.api.graphics.base

import org.hexworks.cobalt.databinding.api.extension.orElseGet
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.behavior.extensions.width
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.ZERO
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.extensions.asCharacterTileOrNull
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

    override fun createCopy(): TileGraphics {
        return tiles.toMutableMap().toTileGraphics(size, tileset)
    }
}
