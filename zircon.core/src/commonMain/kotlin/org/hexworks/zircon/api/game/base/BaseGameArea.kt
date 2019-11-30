package org.hexworks.zircon.api.game.base

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toPersistentMap
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Scrollable3D
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.extensions.toTileImage
import org.hexworks.zircon.api.game.GameArea.Companion.fetchPositionsWithOffset
import org.hexworks.zircon.internal.game.GameAreaState
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable3D
import org.hexworks.zircon.internal.game.InternalGameArea
import org.hexworks.zircon.internal.game.ProjectionStrategy

abstract class BaseGameArea<T : Tile, B : Block<T>>(
        initialVisibleSize: Size3D,
        initialActualSize: Size3D,
        initialContents: Map<Position3D, B> = mapOf(),
        private val projectionStrategy: ProjectionStrategy = ProjectionMode.TOP_DOWN.projectionStrategy)
    : InternalGameArea<T, B>, Scrollable3D by DefaultScrollable3D(
        initialVisibleSize = initialVisibleSize,
        initialActualSize = initialActualSize) {

    private var persistentBlocks: PersistentMap<Position3D, B> = initialContents.toPersistentMap()

    override val blocks: Map<Position3D, B>
        get() = persistentBlocks

    override val state: GameAreaState<T, B>
        get() = GameAreaState(
                blocks = blocks,
                actualSize = actualSize,
                visibleSize = visibleSize,
                visibleOffset = visibleOffset)

    override fun fetchImageLayers(tileset: TilesetResource): Sequence<TileImage> {
        return projectionStrategy.projectGameArea(state).map {
            it.tiles.toTileImage(visibleSize.to2DSize(), tileset)
        }
    }

    override fun fetchBlocksAt(offset: Position3D, size: Size3D): Sequence<Pair<Position3D, B>> {
        val currentBlocks = blocks
        return sequence {
            fetchPositionsWithOffset(offset, size).forEach { pos ->
                currentBlocks[pos]?.let { block ->
                    yield(pos to block)
                }
            }
        }
    }

    override fun fetchBlocksAtLevel(z: Int): Sequence<Pair<Position3D, B>> {
        return fetchBlocksAt(
                offset = Position3D.create(0, 0, z),
                size = actualSize)
    }

    override fun hasBlockAt(position: Position3D) = persistentBlocks.containsKey(position)

    override fun fetchBlockAt(position: Position3D) = Maybe.ofNullable(blocks[position])

    override fun setBlockAt(position: Position3D, block: B) {
        if (actualSize.containsPosition(position)) {
            persistentBlocks = persistentBlocks.put(position, block)
        }
    }

}
