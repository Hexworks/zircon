package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.resource.TilesetResource

/**
 * A higher-order [TilesetLoader] that will try to load a resource using [loaderA], and if it can't, tries [loaderB].
 * If neither can load the resource, an exception is thrown.
 *
 * Whether a [TilesetLoader] is able to load a [TilesetResource] is dependent on [TilesetLoader.canLoadResource].
 */
class ChainedTilesetLoader<T : Any>(
        private val loaderA: TilesetLoader<T>,
        private val loaderB: TilesetLoader<T>
) : TilesetLoader<T> {
    override fun loadTilesetFrom(resource: TilesetResource): Tileset<T> =
            when {
                loaderA.canLoadResource(resource) -> loaderA.loadTilesetFrom(resource)
                loaderB.canLoadResource(resource) -> loaderB.loadTilesetFrom(resource)
                else -> throw IllegalArgumentException("Unknown tile type '${resource.tileType}'.")
            }

    override fun canLoadResource(resource: TilesetResource): Boolean =
            loaderA.canLoadResource(resource) || loaderB.canLoadResource(resource)

    companion object {
        /**
         * Constructs a new [TilesetLoader] that will check each [TilesetLoader] given, in order, until it finds one
         * that can load the given resource.
         */
        fun <T : Any> inOrder(tilesetLoaders: List<TilesetLoader<T>>): TilesetLoader<T> {
            return when (tilesetLoaders.size) {
                0 -> throw IllegalArgumentException("tilesetLoaders cannot be empty")
                1 -> tilesetLoaders.first()
                else -> tilesetLoaders.reduceRight { right: TilesetLoader<T>, acc: TilesetLoader<T> -> ChainedTilesetLoader(right, acc) }
            }
        }
    }
}
