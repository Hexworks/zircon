package org.hexworks.zircon.internal.data

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Modifier

data class DefaultStackedTile(
    override val baseTile: Tile,
    private val rest: PersistentList<Tile> = persistentListOf()
) : StackedTile, Tile by baseTile {

    override val tiles: List<Tile> = persistentListOf(baseTile) + rest

    override val top: Tile = rest.lastOrNull() ?: baseTile


    override val modifiers: Set<Modifier>
        get() = top.modifiers

    override fun withModifiers(modifiers: Set<Modifier>): StackedTile = DefaultStackedTile(
        baseTile = if(rest.isEmpty()) baseTile.withModifiers(modifiers) else baseTile,
        rest = if(rest.isEmpty()) rest else rest.add(baseTile.withModifiers(modifiers))
    )

    override fun withPushedTile(tile: Tile): StackedTile = DefaultStackedTile(
        baseTile = baseTile,
        rest = rest.add(tile)
    )

    override fun withBaseTile(tile: Tile): StackedTile = DefaultStackedTile(
        baseTile = tile,
        rest = rest
    )

    override fun withRemovedTile(tile: Tile): StackedTile = DefaultStackedTile(
        baseTile = baseTile,
        rest = rest.remove(tile)
    )
}
