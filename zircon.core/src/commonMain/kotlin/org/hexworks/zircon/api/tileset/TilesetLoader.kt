package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.resource.TilesetResource

/**
 * A [TilesetLoader] can be used to load the actual [Tileset] textures into memory, from a given [TilesetResource].
 *
 * @param T the type of the draw surface (Swing, for example will use `Graphics2D`)
 */
interface TilesetLoader<T : Any> {

    /**
     * Loads a [Tileset] for the given [resource].
     */
    fun loadTilesetFrom(resource: TilesetResource): Tileset<T>

    /**
     * Returns true if calling [loadTilesetFrom] for this particular [resource] will likely succeed.
     */
    fun canLoadResource(resource: TilesetResource): Boolean
}
