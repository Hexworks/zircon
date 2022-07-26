package org.hexworks.zircon.api.game.base

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf
import org.hexworks.cobalt.core.api.behavior.DisposeState
import org.hexworks.cobalt.core.api.behavior.NotDisposed
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.behavior.Scrollable3D
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameAreaTileFilter
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable3D
import org.hexworks.zircon.internal.game.GameAreaState
import org.hexworks.zircon.internal.game.InternalGameArea


@Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")
abstract class BaseGameArea<T : Tile, B : Block<T>>(
    initialVisibleSize: Size3D,
    initialActualSize: Size3D,
    initialVisibleOffset: Position3D = Position3D.defaultPosition(),
    initialContents: PersistentMap<Position3D, B> = persistentHashMapOf(),
    initialFilters: Iterable<GameAreaTileFilter>,
    private val scrollable3D: DefaultScrollable3D = DefaultScrollable3D(
        initialVisibleSize = initialVisibleSize,
        initialActualSize = initialActualSize
    )
) : InternalGameArea<T, B>, Scrollable3D by scrollable3D {

    final override val visibleOffsetValue: ObservableValue<Position3D>
        get() = scrollable3D.visibleOffsetValue

    final override val filter = initialFilters.fold(GameAreaTileFilter.identity, GameAreaTileFilter::plus)

    override var state = GameAreaState(
        blocks = initialContents,
        actualSize = initialActualSize,
        visibleSize = initialVisibleSize,
        visibleOffset = initialVisibleOffset,
        filter = filter
    )

    override val blocks: Map<Position3D, B>
        get() = state.blocks

    override var disposeState: DisposeState = NotDisposed
        internal set

    private val offsetChangedSubscription = visibleOffsetValue.onChange { (_, newValue) ->
        state = state.copy(visibleOffset = newValue)
    }

    override fun dispose(disposeState: DisposeState) {
        offsetChangedSubscription.dispose()
        this.disposeState = disposeState
    }

    override fun hasBlockAt(position: Position3D) = blocks.containsKey(position)

    override fun fetchBlockAtOrNull(position: Position3D) = blocks[position]

    override fun setBlockAt(position: Position3D, block: B) {
        if (actualSize.containsPosition(position)) {
            state = state.copy(
                blocks = state.blocks.put(position, block)
            )
        }
    }

    override fun asInternalGameArea() = this

}
