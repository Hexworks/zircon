package org.hexworks.zircon.api.game.base

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Scrollable3D
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea.Companion.fetchPositionsWithOffset
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable3D
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.game.GameAreaState
import org.hexworks.zircon.internal.game.InternalGameArea
import org.hexworks.zircon.internal.game.ProjectionStrategy

abstract class BaseGameArea<T : Tile, B : Block<T>>(
        initialVisibleSize: Size3D,
        initialActualSize: Size3D,
        initialTileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        initialVisibleOffset: Position3D = Position3D.defaultPosition(),
        initialContents: PersistentMap<Position3D, B> = persistentHashMapOf(),
        private val projectionStrategy: ProjectionStrategy = ProjectionMode.TOP_DOWN.projectionStrategy,
        private val scrollable3D: DefaultScrollable3D = DefaultScrollable3D(
                initialVisibleSize = initialVisibleSize,
                initialActualSize = initialActualSize
        )
) : InternalGameArea<T, B>, Scrollable3D by scrollable3D {

    final override val tilesetProperty = initialTileset.toProperty()
    final override var tileset: TilesetResource by tilesetProperty.asDelegate()

    final override val visibleOffsetValue: ObservableValue<Position3D>
        get() = scrollable3D.visibleOffsetValue

    final override var state = GameAreaState(
            blocks = initialContents,
            actualSize = initialActualSize,
            visibleSize = initialVisibleSize,
            visibleOffset = initialVisibleOffset,
            tileset = initialTileset)

    override val blocks: Map<Position3D, B>
        get() = state.blocks

    override val imageLayers: Sequence<TileImage>
        get() = projectionStrategy.projectGameArea(state)

    init {
        visibleOffsetValue.onChange { (_, newValue) ->
            state = state.copy(visibleOffset = newValue)
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

    override fun hasBlockAt(position: Position3D) = blocks.containsKey(position)

    override fun fetchBlockAt(position: Position3D) = Maybe.ofNullable(blocks[position])

    override fun setBlockAt(position: Position3D, block: B) {
        if (actualSize.containsPosition(position)) {
            state = state.copy(
                    blocks = state.blocks.put(position, block))
        }
    }

}
