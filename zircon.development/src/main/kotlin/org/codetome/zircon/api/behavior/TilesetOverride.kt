package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.tileset.Tileset

/**
 * Interface which adds functionality for overriding [Tileset]s used
 * in its implementors (components, layers, etc).
 */
interface TilesetOverride<T: Any, S: Any> {

    /**
     * Returns the currently used [Tileset].
     */
    fun tileset(): Tileset<T, S>

    /**
     * Sets the [Tileset] to use.
     */
    fun useTileset(tileset: Tileset<T, S>)
}
