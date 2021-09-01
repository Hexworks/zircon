package org.hexworks.zircon.internal.util.impl


import org.hexworks.zircon.api.util.Cache

/**
 * This is a no-op cache implementation.
 */
class NoOpCache<T> : Cache<T> {

    override fun retrieveIfPresentOrNull(key: String): T? = null

    override fun store(obj: T): T = obj

}
