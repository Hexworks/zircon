package org.codetome.zircon.api.behavior

/**
 * Represents an object which can be cached.
 */
interface Cacheable {

    /**
     * Generates a **unique** key for this object.
     */
    fun generateCacheKey(): String
}
