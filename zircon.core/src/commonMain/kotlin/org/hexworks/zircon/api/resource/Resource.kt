package org.hexworks.zircon.api.resource

import org.hexworks.zircon.internal.resource.DefaultResource

/**
 * Represents a resource (file or directory) that can be loaded into
 * Zircon e.g.: tileset files, animation files, etc.
 */
interface Resource {
    val resourceType: ResourceType
    val path: String

    companion object {
        fun create(
            path: String,
            resourceType: ResourceType = ResourceType.FILESYSTEM,
        ) = DefaultResource(resourceType, path)
    }
}

