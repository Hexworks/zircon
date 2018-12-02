package org.hexworks.zircon.api.game.base

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.Cell3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameArea.Companion.fetchPositionsWithOffset
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.behavior.Scrollable3D
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable3D
import org.hexworks.zircon.internal.extensions.getIfPresent
import org.hexworks.zircon.internal.util.ThreadSafeMap
import org.hexworks.zircon.internal.util.ThreadSafeQueue
import org.hexworks.zircon.platform.factory.ThreadSafeMapFactory
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

abstract class BaseGameArea<T : Tile, B : Block<T>>(visibleSize: Size3D,
                                                    actualSize: Size3D)
    : GameArea<T, B>, Scrollable3D by DefaultScrollable3D(
        visibleSize = visibleSize,
        actualSize = actualSize) {

    private val overlays: ThreadSafeMap<Int, ThreadSafeQueue<Layer>> = ThreadSafeMapFactory.create()

    override fun fetchBlocks(fetchMode: GameArea.BlockFetchMode): Iterable<Cell3D<T, B>> {
        return if (fetchMode == GameArea.BlockFetchMode.IGNORE_EMPTY) {
            fetchBlocks()
        } else {
            actualSize().fetchPositions().map { createCell(it) }
        }
    }

    override fun fetchBlocksAt(offset: Position3D, size: Size3D): Iterable<Cell3D<T, B>> {
        return fetchPositionsWithOffset(offset, size)
                .asSequence()
                .filter { hasBlockAt(it) }
                .map { createCell(it) }
                .toList()
    }

    override fun fetchBlocksAt(offset: Position3D, size: Size3D, fetchMode: GameArea.BlockFetchMode): Iterable<Cell3D<T, B>> {
        return if (fetchMode == GameArea.BlockFetchMode.IGNORE_EMPTY) {
            fetchBlocksAt(offset, size)
        } else {
            fetchPositionsWithOffset(offset, size)
                    .map { createCell(it) }
        }
    }

    /**
     * Returns the [Block]s at the given `z` level.
     * Empty positions are **ignored**.
     */
    override fun fetchBlocksAtLevel(z: Int): Iterable<Cell3D<T, B>> {
        return fetchBlocks()
                .filter { it.position.z == z }
                .map { createCell(it.position) }
    }

    /**
     * Returns the [Block]s at the given `z` level.
     * Empty positions are either ignored, or a default filler value is returned.
     */
    override fun fetchBlocksAtLevel(z: Int, blockFetchMode: GameArea.BlockFetchMode): Iterable<Cell3D<T, B>> {
        return if (blockFetchMode == GameArea.BlockFetchMode.IGNORE_EMPTY) {
            fetchBlocksAtLevel(z)
        } else {
            GameArea.fetchPositionsWithOffset(
                    offset = Position3D.defaultPosition(),
                    size = Size3D.create(actualSize().xLength, actualSize().yLength, z))
                    .map { createCell(it) }
        }
    }

    /**
     * Returns the [Tile] at the given `position` and `layerIdx` (if any).
     */
    override fun fetchTileAt(position: Position3D, layerIdx: Int): Maybe<T> {
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
            (0 until layersPerBlock()).forEach { layerIdx ->
                val builder = TileGraphicsBuilder.newBuilder().withSize(size.to2DSize())
                window.forEach { pos ->
                    fetchTileAt(Position3D.from2DPosition(pos + offset2D, z), layerIdx).map { char ->
                        builder.withTile(pos, char)
                    }
                }
                images.add(builder.build())
            }
            // TODO: test overlay stuff
            getOverlaysAt(z).forEach { overlay ->
                val builder = TileGraphicsBuilder.newBuilder().withSize(size.to2DSize())
                window.forEach { pos ->
                    overlay.getTileAt(pos + offset2D).map { char ->
                        builder.withTile(pos, char)
                    }
                }
                images.add(builder.build())
            }
            images
        }
    }

    override fun getOverlaysAt(level: Int): Iterable<Layer> {
        return overlays[level]?.toList() ?: listOf()
    }

    override fun pushOverlayAt(layer: Layer, level: Int) {
        overlays.getOrPut(level) { ThreadSafeQueueFactory.create() }.offer(layer)
    }

    override fun popOverlayAt(level: Int): Maybe<Layer> {
        return overlays[level]?.poll() ?: Maybe.empty()
    }

    override fun removeOverlay(layer: Layer, level: Int) {
        overlays[level]?.remove(layer)
    }

}
