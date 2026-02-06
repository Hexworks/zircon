package org.hexworks.zircon.api

import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.CP437TilesetResource
import org.hexworks.zircon.internal.resource.GraphicalTilesetResource
import org.hexworks.zircon.internal.resource.ImageDictionaryTilesetResource
import org.hexworks.zircon.internal.resource.TrueTypeTilesetResource


fun loadCP437TilesetResource(
    width: Int,
    height: Int,
    path: String,
    resourceType: ResourceType
): TilesetResource {
    return CP437TilesetResource(
        width = width,
        height = height,
        path = path,
        resourceType = resourceType
    )
}

fun loadFontTilesetResource(
    width: Int,
    height: Int,
    path: String,
    resourceType: ResourceType
): TilesetResource {
    return TrueTypeTilesetResource(
        width = width,
        height = height,
        path = path,
        resourceType = resourceType
    )
}

fun loadImageDictionaryTilesetResource(
    path: String,
    resourceType: ResourceType
): TilesetResource {
    return ImageDictionaryTilesetResource(
        path = path,
        resourceType = resourceType
    )
}

fun loadGraphicalTilesetResource(
    width: Int,
    height: Int,
    path: String,
    resourceType: ResourceType
): TilesetResource {
    return GraphicalTilesetResource(
        width = width,
        height = height,
        path = path,
        resourceType = resourceType
    )
}
