package org.codetome.zircon.internal.tileset.cache

import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.api.util.Maybe

/**
 * This is a no-op cache implementation.
 */
class NoOpCache<R> : Cache<R> {

    override fun retrieveIfPresent(key: String) = Maybe.empty<R>()

    override fun store(key: String, obj: R): R = obj

}
