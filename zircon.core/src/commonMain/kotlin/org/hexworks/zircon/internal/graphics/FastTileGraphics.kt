package org.hexworks.zircon.internal.graphics

import kotlinx.collections.immutable.toPersistentMap
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.base.BaseTileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.DefaultTileGraphicsState
import org.hexworks.zircon.internal.data.TileGraphicsState
import kotlin.jvm.Synchronized

/**
 * This is a fast implementation of [TileGraphics] that sacrifices memory footprint
 * and consistent snapshots for speed and uses an array behind the scenes.
 */
class FastTileGraphics(
        initialSize: Size,
        initialTileset: TilesetResource,
        initialTiles: Map<Position, Tile> = mapOf()
) : BaseTileGraphics(
        initialSize = initialSize,
        initialTileset = initialTileset
) {

    private var arr = arrayOfNulls<Tile>(initialSize.width * initialSize.height)

    init {
        for (e in initialTiles.entries) {
            arr[e.key.index] = e.value
        }
    }

    override val tiles = ArrayBackedTileMap(initialSize, arr)
    override val state: TileGraphicsState
        get() = DefaultTileGraphicsState(
                size = size,
                tileset = tileset,
                tiles = tiles.toPersistentMap())

    @Synchronized
    override fun draw(tile: Tile, drawPosition: Position) {
        if (size.containsPosition(drawPosition)) {
            arr[drawPosition.index] = tile
        }
    }

    @Synchronized
    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        tileMap.asSequence()
                .filter { drawArea.containsPosition(it.key) && size.containsPosition(it.key + drawPosition) }
                .map { (key, value) -> key + drawPosition to value }
                .forEach { (pos, tile) ->
                    if (tile.isEmpty) {
                        arr[pos.index] = null
                    } else {
                        arr[pos.index] = tile
                    }
                }
    }

    override fun draw(tileComposite: TileComposite) {
        if(tileComposite is FastTileGraphics) {
            tileComposite.arr.copyInto(arr)
        } else super.draw(tileComposite)
    }

    @Synchronized
    override fun clear() {
        arr = arrayOfNulls(size.width * size.height)
    }

    @Synchronized
    override fun fill(filler: Tile) {
        if (filler.isNotEmpty) {
            for (i in arr.indices) {
                if (arr[i] == null) {
                    arr[i] = filler
                }
            }
        }
    }

    @Synchronized
    override fun transform(transformer: (Position, Tile) -> Tile) {
        size.fetchPositions().forEach { pos ->
            val tile = transformer(pos, arr[pos.index] ?: Tile.empty())
            if (tile.isEmpty) {
                arr[pos.index] = null
            } else {
                arr[pos.index] = tile
            }
        }
    }

    private val Position.index: Int
        get() = size.width * y + x


}
