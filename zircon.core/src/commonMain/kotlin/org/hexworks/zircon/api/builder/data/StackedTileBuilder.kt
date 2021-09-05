package org.hexworks.zircon.api.builder.data

import kotlinx.collections.immutable.toPersistentList
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.data.DefaultStackedTile

/**
 * Builds [StackedTile]s
 * Defaults:
 * - BaseTile is the default Tile
 * - TileStack is an empty Stack
 *
 */
public class StackedTileBuilder private constructor(
    private var baseTile: Tile,
    private val tileStack: MutableList<Tile>
): Builder<StackedTile> {

    // The first element of the list is the top of the stack!

    val top: Tile = tileStack.firstOrNull() ?: baseTile

    /**
     * Sets the BaseTile to the desired [tile]
     */
    public fun withBaseTile(tile: Tile): StackedTileBuilder = also {
        baseTile = tile
    }

    /**
     * Pushes the [tile] to the stack
     */
    public fun withPushedTile(tile: Tile): StackedTileBuilder = also {
        tileStack.add(0, tile)
    }

    /**
     * Pushes the [tiles] to the stack in reverse order.
     * The first Tile in the list will be on top of the stack
     */
    public fun withPushedTiles(vararg tiles: Tile): StackedTileBuilder = also {
        tiles.reversed().forEach { tile ->
            withPushedTile(tile)
        }
    }

    /**
     * Builds the StackedTile
     */
    override fun build(): StackedTile {
        return DefaultStackedTile(
            baseTile = baseTile,
            rest = tileStack.reversed().toPersistentList()
            )
    }

    override fun createCopy(): Builder<StackedTile> = StackedTileBuilder(baseTile, tileStack)

    companion object {
        /**
         * Returns a builder with the default configuration
         */
        public fun newBuilder(): StackedTileBuilder{
            return StackedTileBuilder(
                baseTile = Tile.defaultTile(),
                tileStack = mutableListOf()
                )
        }
    }

}