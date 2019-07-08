package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset

/**
 * Interface which adds functionality for overriding [Tileset]s used
 * in its implementors (components, layers, etc).
 */
interface TilesetOverride {

    /**
     * Returns the currently used [Tileset].
     */
    // TODO: use a property instead
    fun currentTileset(): TilesetResource

    /**
     * Sets the [Tileset] to use.
     */
    fun useTileset(tileset: TilesetResource)
}
