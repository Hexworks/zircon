package org.hexworks.zircon.api

import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.ImageDictionaryTilesetResource
import org.hexworks.zircon.api.resource.ResourceType

/**
 * This object can be used to load either built-in Image Dictionary
 * [TilesetResource]s or external ones.
 */
object ImageDictionaryTilesetResources {

    fun load(
        path: String,
        resourceType: ResourceType
    ): TilesetResource {
        return ImageDictionaryTilesetResource(
            path = path,
            resourceType = resourceType
        )
    }

}
