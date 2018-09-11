package org.hexworks.zircon.api.game

import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea.Companion.fetchPositionsWithOffset
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.extensions.getIfPresent

abstract class BaseGameArea : GameArea {

    override fun fetchBlocks(fetchMode: GameArea.BlockFetchMode): Iterable<Block> {
        return if (fetchMode == GameArea.BlockFetchMode.IGNORE_EMPTY) {
            fetchBlocks()
        } else {
            size().fetchPositions().map {
                fetchBlockOrDefault(it)
            }
        }
    }

    override fun fetchBlocksAt(offset: Position3D, size: Size3D): Iterable<Block> {
        return fetchPositionsWithOffset(offset, size)
                .filter { hasBlockAt(it) }
                .map { fetchBlockOrDefault(it) }
    }

    override fun fetchBlocksAt(offset: Position3D, size: Size3D, fetchMode: GameArea.BlockFetchMode): Iterable<Block> {
        return if (fetchMode == GameArea.BlockFetchMode.IGNORE_EMPTY) {
            fetchBlocksAt(offset, size)
        } else {
            fetchPositionsWithOffset(offset, size)
                    .map { fetchBlockOrDefault(it) }
        }
    }

    /**
     * Returns the [Block]s at the given `z` level.
     * Empty positions are **ignored**.
     */
    override fun fetchBlocksAtLevel(z: Int): Iterable<Block> {
        return fetchBlocks()
                .filter { it.position.z == z }
                .map { fetchBlockOrDefault(it.position) }
    }

    /**
     * Returns the [Block]s at the given `z` level.
     * Empty positions are either ignored, or a default filler value is returned.
     */
    override fun fetchBlocksAtLevel(z: Int, blockFetchMode: GameArea.BlockFetchMode): Iterable<Block> {
        return if (blockFetchMode == GameArea.BlockFetchMode.IGNORE_EMPTY) {
            fetchBlocksAtLevel(z)
        } else {
            GameArea.fetchPositionsWithOffset(
                    offset = Position3D.defaultPosition(),
                    size = Size3D.create(size().xLength, size().yLength, z))
                    .map { fetchBlockOrDefault(it) }
        }
    }

    /**
     * Returns the [Tile] at the given `position` and `layerIdx` (if any).
     */
    override fun fetchTileAt(position: Position3D, layerIdx: Int): Maybe<Tile> {
        return fetchBlockOrDefault(position).layers.getIfPresent(layerIdx)
    }

    /**
     * Returns all the layers from bottom to top as a collection of [org.hexworks.zircon.api.graphics.TileGraphics]s.
     * A layer is a collection of [Tile]s at a given `z` level and `layerIndex`.
     */
    override fun fetchLayersAt(offset: Position3D, size: Size3D): Iterable<TileGraphics> {
        val offset2D = offset.to2DPosition()
        val window = size.to2DSize().fetchPositions()
        return (offset.z until size.zLength + offset.z).flatMap { z ->
            val images = mutableListOf<TileGraphics>()
            (0 until getLayersPerBlock()).forEach { layerIdx ->
                val builder = TileGraphicsBuilder.newBuilder().size(size.to2DSize())
                window.forEach { pos ->
                    fetchTileAt(Position3D.from2DPosition(pos + offset2D, z), layerIdx).map { char ->
                        builder.tile(pos, char)
                    }
                }
                images.add(builder.build())
            }
            images
        }
    }

}
