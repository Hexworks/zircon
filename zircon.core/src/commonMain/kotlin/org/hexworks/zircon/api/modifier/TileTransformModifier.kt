package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.Tile

/**
 * A [TileTransformModifier] takes a [Tile] and transforms it into another
 * [Tile] for the [org.hexworks.zircon.internal.renderer.Renderer].
 * **Note that** this is an ephemeral change and won't modify any persistent state.
 */
interface TileTransformModifier<T : Tile> : Modifier {

    /**
     * Transforms the given [Tile] and returns the result.
     */
    fun transform(tile: T): T

    /**
     * Tells whether the given `tile` can be transformed by
     * this modifier or not.
     */
    fun canTransform(tile: Tile): Boolean = false
}
