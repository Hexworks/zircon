package org.hexworks.zircon.api.behavior

/**
 * Represents an object that can be cached.
 */
public interface Cacheable {

    /**
     * A **unique** and immutable cache key for this object.
     */
    public val cacheKey: String
}
