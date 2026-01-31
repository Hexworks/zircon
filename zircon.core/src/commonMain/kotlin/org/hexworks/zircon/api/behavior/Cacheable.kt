package org.hexworks.zircon.api.behavior

/**
 * Represents an object that can be cached.
 */
interface Cacheable {

    /**
     * A **unique** and immutable cache key for this object.
     */
    val cacheKey: String
}
