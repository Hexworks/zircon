package org.hexworks.zircon.api

import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInGraphicalTilesetResource
import org.hexworks.zircon.internal.resource.GraphicalTilesetResource
import org.hexworks.zircon.api.resource.ResourceType

/**
 * This object can be used to load either built-in graphical
 * [TilesetResource]s or external ones.
 */
object GraphicalTilesetResources {

    fun nethack16x16(): TilesetResource = BuiltInGraphicalTilesetResource.NETHACK_16X16

    /**
     * Use this function if you want to load a [TilesetResource]
     * from the filesystem.
     */
    fun loadTilesetFromFilesystem(
        width: Int,
        height: Int,
        path: String
    ): TilesetResource {
        return GraphicalTilesetResource(
            width = width,
            height = height,
            path = path,
            resourceType = ResourceType.FILESYSTEM
        )
    }

    /**
     * Use this function if you want to load a [TilesetResource]
     * which is bundled into a jar file which you build from
     * your application.
     */
    fun loadTilesetFromJar(
        width: Int,
        height: Int,
        path: String
    ): TilesetResource {
        return GraphicalTilesetResource(
            width = width,
            height = height,
            path = path,
            resourceType = ResourceType.PROJECT
        )
    }
}
