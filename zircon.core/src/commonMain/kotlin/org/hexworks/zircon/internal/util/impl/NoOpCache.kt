package org.hexworks.zircon.internal.util.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.util.Cache

/**
 * This is a no-op cache implementation.
 */
class NoOpCache<T> : Cache<T> {

    override fun retrieveIfPresent(key: String) = Maybe.empty<T>()

    override fun retrieveIfPresentOrNull(key: String): T? = null

    override fun store(key: String, obj: T): T = obj

    override fun store(obj: T): T = obj

}
