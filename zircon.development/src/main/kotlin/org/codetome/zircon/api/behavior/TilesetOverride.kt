package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.tileset.Tileset

/**
 * Interface which adds functionality for overriding [Tileset]s used
 * in its implementors (components, layers, etc).
 */
interface TilesetOverride {

    /**
     * Returns the currently used [Tileset].
     */
    fun tileset(): TilesetResource<out Tile>

    /**
     * Sets the [Tileset] to use.
     */
    fun useTileset(tileset: TilesetResource<out Tile>)
}
