package org.hexworks.zircon.internal.data

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.builder.data.StackedTileBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

@Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")
@Beta
data class DefaultStackedTile(
    override val baseTile: Tile,
    private val rest: PersistentList<Tile> = persistentListOf()
) : StackedTile, Tile by baseTile {

    override val tiles: List<Tile> = persistentListOf(baseTile) + rest

    override val top: Tile = rest.lastOrNull() ?: baseTile

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

    override fun asCharacterTileOrNull() = top.asCharacterTileOrNull()

    override fun asImageTileOrNull() = top.asImageTileOrNull()

    override fun asGraphicalTileOrNull() = top.asGraphicalTileOrNull()

    override fun toBuilder(): StackedTileBuilder {
        return StackedTileBuilder.newBuilder()
            .apply {
                this.withBaseTile(baseTile)
                rest.forEach { tile -> // The List "rest" is a stack, where the last element of the list is on top of the stack
                    this.withPushedTile(tile)
                }
            }
    }
}
