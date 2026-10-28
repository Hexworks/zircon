package org.hexworks.zircon.api.resource

/**
 * Reads the bytes behind a [Resource]. Core has no file system or classpath access of its
 * own; a renderer module (or the application) installs one through [Resources.loader].
 */
public fun interface ResourceLoader {
    public suspend fun loadBytes(resource: Resource): ByteArray
}

/**
 * Global resource loading configuration.
 */
public object Resources {

    /**
     * The loader used by [Resource.loadBytes]. Renderer modules install a platform
     * implementation; until then every load fails with a clear message.
     */
    public var loader: ResourceLoader = ResourceLoader { resource ->
        error(
            "No ResourceLoader installed, can't load ${resource.resourceType} resource '${resource.path}'. " +
                "Set Resources.loader before loading tilesets, REXPaint files or animations."
        )
    }
}

/**
 * Reads the bytes of this [Resource] through [Resources.loader].
 */
public suspend fun Resource.loadBytes(): ByteArray = Resources.loader.loadBytes(this)
