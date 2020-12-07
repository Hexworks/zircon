package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.resource.TilesetResource

/**
 * A [TilesetLoader] can be used to load the actual [Tileset] textures
 * into memory, from a given [TilesetResource].
 *
 * @param T the type of the draw surface (eg.: Graphics2D)
 */
interface TilesetLoader<T : Any> {

    /**
     * Loads a [Tileset] using the given `resource`.
     */
    fun loadTilesetFrom(resource: TilesetResource): Tileset<T>

}
