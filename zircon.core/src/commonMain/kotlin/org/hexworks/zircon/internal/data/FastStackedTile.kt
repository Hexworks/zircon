package org.hexworks.zircon.internal.data

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.resource.TileType

/**
 * This **fast** [StackedTile] implementation foregoes validation and it allows mutation of its state.
 * Only use it if you need the speed and you can be really cautious.
 */
@Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")
@Beta
class FastStackedTile(
        initialTiles: List<Tile> = listOf(),
        initialCapacity: Int = initialTiles.size
) : StackedTile {

    private val actualTiles = ArrayDeque<Tile>(initialCapacity).apply {
        addAll(initialTiles)
    }

    override val tiles: List<Tile> = actualTiles

    override val baseTile: Tile
        get() = actualTiles.first()

    override val top: Tile
        get() = tiles.last()

    fun addFirst(tile: Tile) {
        actualTiles.addFirst(tile)
    }

    fun addLast(tile: Tile) {
        actualTiles.addLast(tile)
    }

    // delegation
    override val tileType: TileType
        get() = baseTile.tileType
    override val styleSet: StyleSet
        get() = baseTile.styleSet
    override val isOpaque: Boolean
        get() = baseTile.isOpaque
    override val isUnderlined: Boolean
        get() = baseTile.isUnderlined
    override val isCrossedOut: Boolean
        get() = baseTile.isCrossedOut
    override val isBlinking: Boolean
        get() = baseTile.isBlinking
    override val isVerticalFlipped: Boolean
        get() = baseTile.isVerticalFlipped
    override val isHorizontalFlipped: Boolean
        get() = baseTile.isHorizontalFlipped
    override val hasBorder: Boolean
        get() = baseTile.hasBorder
    override val isEmpty: Boolean
        get() = baseTile.isEmpty
    override val isNotEmpty: Boolean
        get() = baseTile.isNotEmpty
    override val cacheKey: String
        get() = baseTile.cacheKey
    override val foregroundColor: TileColor
        get() = baseTile.foregroundColor
    override val backgroundColor: TileColor
        get() = baseTile.backgroundColor
    override val modifiers: Set<Modifier>
        get() = baseTile.modifiers

    override fun withPushedTile(tile: Tile): StackedTile = FastStackedTile(
            initialTiles = tiles.plus(tile)
    )

    override fun withBaseTile(tile: Tile): StackedTile = FastStackedTile(
            initialTiles = tiles.toMutableList().apply {
                set(0, tile)
            }
    )

    override fun withRemovedTile(tile: Tile): StackedTile = FastStackedTile(
            initialTiles = tiles.minus(tile)
    )

    override fun fetchBorderData() = baseTile.fetchBorderData()

    override fun createCopy() = FastStackedTile(
            initialTiles = tiles.toList()
    )

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

    override fun asCharacterTile(): Maybe<CharacterTile> = top.asCharacterTile()

    override fun asImageTile(): Maybe<ImageTile> = top.asImageTile()

    override fun asGraphicTile(): Maybe<GraphicalTile> = top.asGraphicTile()

    override fun toBuilder(): TileBuilder {
        throw UnsupportedOperationException("This operation is not implemented yet")
    }
}
