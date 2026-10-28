package org.hexworks.zircon.compose

import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceLoader
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.Resources
import org.hexworks.zircon.compose.resources.Res

/**
 * Reads [ResourceType.PROJECT] resources from this module's Compose resources (the bundled
 * tilesets and fonts) and [ResourceType.FILESYSTEM] resources through the platform.
 */
public object ComposeResourceLoader : ResourceLoader {

    override suspend fun loadBytes(resource: Resource): ByteArray = when (resource.resourceType) {
        ResourceType.PROJECT -> Res.readBytes("files/" + resource.path.trimStart('/'))
        ResourceType.FILESYSTEM -> readFileBytes(resource.path)
    }

    /**
     * Makes this loader the one core uses. [TileGridView] calls it, so an application
     * only needs it when it loads resources before composing anything.
     */
    public fun install() {
        Resources.loader = this
    }
}

/**
 * Reads a file from the platform's file system (or the network, in a browser).
 */
internal expect suspend fun readFileBytes(path: String): ByteArray
