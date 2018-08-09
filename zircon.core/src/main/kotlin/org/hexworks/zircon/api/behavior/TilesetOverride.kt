package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Tile
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
    fun tileset(): TilesetResource

    /**
     * Sets the [Tileset] to use.
     */
    fun useTileset(tileset: TilesetResource)
}
