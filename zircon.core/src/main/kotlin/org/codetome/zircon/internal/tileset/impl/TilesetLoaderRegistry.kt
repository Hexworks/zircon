package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.tileset.TilesetLoader

object TilesetLoaderRegistry {

    private var currentTilesetLoader: TilesetLoader = VirtualTilesetLoader()

    fun getCurrentFontLoader() = currentTilesetLoader

    fun setFontLoader(tilesetLoader: TilesetLoader) {
        currentTilesetLoader = tilesetLoader
    }
}
