package org.hexworks.zircon.api.data.extensions

import kotlinx.collections.immutable.toPersistentList
import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.data.DefaultStackedTile

/**
 * Returns a new [StackedTile] that has its [baseTile] replaced by the given [tile].
 */
public fun StackedTile.withBaseTile(tile: Tile): StackedTile = DefaultStackedTile(
    baseTile = tile,
    rest = rest.toPersistentList()
)

/**
 * Returns a new [StackedTile] that's the copy of this one, with the given
 * [tile] pushed on top of the stack.
 */
public fun StackedTile.withPushedTile(tile: Tile): StackedTile = DefaultStackedTile(
    baseTile = baseTile,
    rest = rest.plus(tile).toPersistentList()
)


/**
 * Returns a new [StackedTile] that's the copy of this one, with the given
 * [tile] removed.
 */
public fun StackedTile.withRemovedTile(tile: Tile): StackedTile = DefaultStackedTile(
    baseTile = baseTile,
    rest = rest.minus(tile).toPersistentList()
)
