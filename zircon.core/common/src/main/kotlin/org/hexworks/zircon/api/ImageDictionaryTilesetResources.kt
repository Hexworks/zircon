package org.hexworks.zircon.api

import org.hexworks.zircon.api.resource.ImageDictionaryTilesetResource
import org.hexworks.zircon.api.resource.TilesetResource
import kotlin.jvm.JvmStatic

object ImageDictionaryTilesetResources {

    /**
     * Loads the image dictionary from a folder.
     */
    @JvmStatic
    fun loadFromDirectory(path: String): TilesetResource {
        return ImageDictionaryTilesetResource(path)
    }
}
