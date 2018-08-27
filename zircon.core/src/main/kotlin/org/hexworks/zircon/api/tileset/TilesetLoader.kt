package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.resource.TilesetResource

/**
 * A [TilesetLoader] can be used to load the actual [Tileset] textures
 * into memory, from a given [TilesetResource].
 *
 * @param S the type of the texture (eg.: BufferedImage)
 */
interface TilesetLoader<S : Any, T: Any> {

    /**
     * Loads a [Tileset] using the given `resource`.
     */
    fun loadTilesetFrom(resource: TilesetResource): Tileset<S, T>

}
