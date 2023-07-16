package org.hexworks.zircon.api.builder.game

import kotlinx.collections.immutable.toPersistentMap
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameAreaTileFilter
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.game.impl.DefaultGameArea

@ZirconDsl
class GameAreaBuilder<T : Tile, B : Block<T>> : Builder<GameArea<T, B>> {

    var actualSize: Size3D = Size3D.one()
        set(value) {
            field = value
            blocks = blocks.filterKeys { actualSize.containsPosition(it) }
                .toMutableMap()
        }

    var visibleSize: Size3D = Size3D.one()
    var visibleOffset: Position3D = Position3D.defaultPosition()
    var blocks: Map<Position3D, B> = mapOf()
    var filters: List<GameAreaTileFilter> = listOf()


    override fun build(): GameArea<T, B> {
        return DefaultGameArea(
            initialVisibleSize = visibleSize,
            initialActualSize = actualSize,
            initialContents = blocks.toPersistentMap(),
            initialVisibleOffset = visibleOffset,
            initialFilters = filters.toList()
        )
    }
}

/**
 * Creates a new [GameAreaBuilder] using the builder DSL and returns it.
 */
fun <T : Tile, B : Block<T>> gameArea(init: GameAreaBuilder<T, B>.() -> Unit): GameArea<T, B> =
    GameAreaBuilder<T, B>().apply(init).build()
