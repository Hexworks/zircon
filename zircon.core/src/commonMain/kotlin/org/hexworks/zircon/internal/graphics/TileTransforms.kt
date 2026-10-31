package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.color.extensions.darkenByPercent
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Makes the tileset a tile draws with explicit when it would otherwise be lost: a
 * [CharacterTile] without its own tileset that sits on a layer with [layerTileset] is
 * tagged with it, unless that is the [targetTileset] of the graphics it moves to. Other
 * tiles already carry their tileset or have none to carry.
 */
internal fun Tile.withTilesetUnless(layerTileset: TilesetResource, targetTileset: TilesetResource): Tile =
    if (this is CharacterTile && tileset.isUnknown && layerTileset != targetTileset) {
        withTileset(layerTileset)
    } else this

/**
 * Applies [transform] to this tile, or to every leaf of a [StackedTile], keeping the stack.
 */
internal fun Tile.mapLeaves(transform: (Tile) -> Tile): Tile = when (this) {
    is StackedTile -> StackedTile.create(
        baseTile = baseTile.mapLeaves(transform),
        rest = rest.map { it.mapLeaves(transform) }.toTypedArray()
    )
    else -> transform(this)
}

/**
 * Darkens the colours of this tile by [percentage] (`0` to `1`). Only [CharacterTile]s
 * have colours in core; graphical and image tiles come back unchanged.
 */
internal fun Tile.darkenByPercent(percentage: Double): Tile = mapLeaves { leaf ->
    if (leaf is CharacterTile) {
        leaf.withStyle(
            leaf.styleSet
                .withForegroundColor(leaf.foregroundColor.darkenByPercent(percentage))
                .withBackgroundColor(leaf.backgroundColor.darkenByPercent(percentage))
        )
    } else leaf
}
