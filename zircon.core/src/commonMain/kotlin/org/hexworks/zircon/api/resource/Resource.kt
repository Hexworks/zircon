package org.hexworks.zircon.api.resource

import org.hexworks.zircon.internal.resource.DefaultResource

/**
 * Represents a resource (file or directory) that can be loaded into
 * Zircon e.g.: tileset files, animation files, etc.
 */
public interface Resource {
    public val resourceType: ResourceType
    public val path: String

    public companion object {
        public fun create(
            path: String,
            resourceType: ResourceType,
        ): Resource = DefaultResource(resourceType, path)
    }
}



