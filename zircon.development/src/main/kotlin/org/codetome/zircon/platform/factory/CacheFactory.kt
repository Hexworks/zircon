package org.codetome.zircon.platform.factory

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.api.util.DefaultCache

object CacheFactory {
    fun <R : Cacheable> create(): Cache<R> {
        return DefaultCache()
    }
}
