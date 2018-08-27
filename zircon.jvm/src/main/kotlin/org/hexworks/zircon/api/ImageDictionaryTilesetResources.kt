package org.hexworks.zircon.api

import org.hexworks.zircon.api.resource.ImageDictionaryTilesetResource
import org.hexworks.zircon.api.resource.TilesetResource

object ImageDictionaryTilesetResources {

    @JvmStatic
    fun loadFromDirectory(path: String): TilesetResource {
        return ImageDictionaryTilesetResource(path)
    }
}
