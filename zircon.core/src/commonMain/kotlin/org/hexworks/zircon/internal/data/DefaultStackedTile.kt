package org.hexworks.zircon.internal.data

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

class DefaultStackedTile(
        override val baseTile: Tile,
        private val rest: PersistentList<Tile> = persistentListOf()
) : StackedTile, Tile by baseTile {

    override val tiles: List<Tile> = persistentListOf(baseTile) + rest

    override fun withPushedTile(tile: Tile): StackedTile = DefaultStackedTile(
            baseTile = baseTile,
            rest = rest.add(tile)
    )

    override fun withBaseTile(tile: Tile): StackedTile = DefaultStackedTile(
            baseTile = tile,
            rest = rest.add(tile)
    )

    override fun withRemovedTile(tile: Tile): StackedTile = DefaultStackedTile(
            baseTile = tile,
            rest = rest.remove(tile)
    )

    override fun createCopy() = this

    override fun withForegroundColor(foregroundColor: TileColor) =
            withBaseTile(baseTile.withForegroundColor(foregroundColor))

    override fun withBackgroundColor(backgroundColor: TileColor) =
            withBaseTile(baseTile.withBackgroundColor(backgroundColor))

    override fun withModifiers(modifiers: Set<Modifier>) =
            withBaseTile(baseTile.withModifiers(modifiers))

    override fun withModifiers(vararg modifiers: Modifier) =
            withBaseTile(baseTile.withModifiers(modifiers.toSet()))

    override fun withAddedModifiers(modifiers: Set<Modifier>) =
            withBaseTile(baseTile.withAddedModifiers(modifiers))

    override fun withAddedModifiers(vararg modifiers: Modifier) =
            withBaseTile(baseTile.withAddedModifiers(modifiers.toSet()))

    override fun withRemovedModifiers(modifiers: Set<Modifier>) =
            withBaseTile(baseTile.withRemovedModifiers(modifiers))

    override fun withRemovedModifiers(vararg modifiers: Modifier) =
            withBaseTile(baseTile.withRemovedModifiers(modifiers.toSet()))

    override fun withNoModifiers() =
            withBaseTile(baseTile.withNoModifiers())

    override fun withStyle(style: StyleSet) =
            withBaseTile(baseTile.withStyle(style))

    override fun asCharacterTile(): Maybe<CharacterTile> = Maybe.empty()

    override fun asImageTile(): Maybe<ImageTile> = Maybe.empty()

    override fun asGraphicTile(): Maybe<GraphicalTile> = Maybe.empty()
}
