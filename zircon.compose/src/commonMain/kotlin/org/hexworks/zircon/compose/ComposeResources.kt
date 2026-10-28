package org.hexworks.zircon.compose

import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceLoader
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.Resources
import org.hexworks.zircon.compose.resources.Res

/**
 * Reads [ResourceType.PROJECT] resources from Compose resources and
 * [ResourceType.FILESYSTEM] resources through the platform.
 *
 * Compose resources are per module: this loader knows Zircon's own bundled tilesets and
 * fonts, and an application registers its module's resources with [addProjectResources],
 * typically `ComposeResourceLoader.addProjectResources { Res.readBytes(it) }`.
 */
public object ComposeResourceLoader : ResourceLoader {

    private val projectReaders = mutableListOf<suspend (String) -> ByteArray?>()

    override suspend fun loadBytes(resource: Resource): ByteArray = when (resource.resourceType) {
        ResourceType.PROJECT -> readProjectResource(resource.path)
        ResourceType.FILESYSTEM -> readFileBytes(resource.path)
    }

    /**
     * Registers an application's Compose resources. [reader] receives a resource path such
     * as `files/rex_files/map.xp` and returns its bytes, or `null` if it has no such file.
     */
    public fun addProjectResources(reader: suspend (String) -> ByteArray?) {
        projectReaders += reader
    }

    /**
     * Makes this loader the one core uses. [TileGridView] calls it, so an application
     * only needs it when it loads resources before composing anything.
     */
    public fun install() {
        Resources.loader = this
    }

    private suspend fun readProjectResource(path: String): ByteArray {
        val resourcePath = "files/" + path.trimStart('/')
        for (reader in projectReaders) {
            runCatching { reader(resourcePath) }.getOrNull()?.let { return it }
        }
        return runCatching { Res.readBytes(resourcePath) }.getOrElse {
            error("No Compose resource at '$resourcePath'. Register your module's resources with ComposeResourceLoader.addProjectResources.")
        }
    }
}

/**
 * Reads a file from the platform's file system (or the network, in a browser).
 */
internal expect suspend fun readFileBytes(path: String): ByteArray
