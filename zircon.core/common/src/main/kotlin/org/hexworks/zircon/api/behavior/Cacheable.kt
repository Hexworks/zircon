package org.hexworks.zircon.api.behavior

/**
 * Represents an object which can be cached.
 */
interface Cacheable {

    /**
     * Generates a **unique** key for this object.
     */
    // TODO: use val instead
    fun generateCacheKey(): String
}
