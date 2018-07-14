package org.codetome.zircon.internal.font.cache

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.api.util.Maybe

/**
 * This is a no-op cache implementation.
 */
class NoOpCache<R : Cacheable> : Cache<R> {

    override fun retrieveIfPresent(key: String) = Maybe.empty<R>()

    override fun store(cacheable: R): R = cacheable

}
