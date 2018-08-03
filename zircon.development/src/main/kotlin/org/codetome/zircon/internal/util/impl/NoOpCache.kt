package org.codetome.zircon.internal.util.impl

import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.api.util.Maybe

/**
 * This is a no-op cache implementation.
 */
class NoOpCache<T> : Cache<T> {

    override fun retrieveIfPresent(key: String) = Maybe.empty<T>()

    override fun store(key: String, obj: T): T = obj

}
