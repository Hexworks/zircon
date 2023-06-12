package org.hexworks.zircon.api.game.base

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


abstract class BaseGameArea<T : Tile, B : Block<T>>(
    initialVisibleSize: Size3D,
    initialActualSize: Size3D,
    initialVisibleOffset: Position3D = Position3D.defaultPosition(),
    initialContents: Map<Position3D, B> = mapOf(),
    initialFilters: Iterable<GameAreaTileFilter>,
    private val scrollable3D: DefaultScrollable3D = DefaultScrollable3D(
        initialVisibleSize = initialVisibleSize,
        initialActualSize = initialActualSize
    )
) : InternalGameArea<T, B>, Scrollable3D by scrollable3D {

    final override val visibleOffsetValue: ObservableValue<Position3D>
        get() = scrollable3D.visibleOffsetValue

    final override val filter = initialFilters.fold(GameAreaTileFilter.identity, GameAreaTileFilter::plus)

    // 📙 Note that this was necessary back in the day when Zircon supported Java and we needed
    // to have consistent snapshots for thread safety. Now we don't need that anymore as with coroutines
    // we don't have this problem anymore, but we left this as-is.
    override var state = GameAreaState(
        blocks = initialContents.toMutableMap(),
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
            state.blocks[position] = block
        }
    }

    override fun asInternalGameArea() = this

}
