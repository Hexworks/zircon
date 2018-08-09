package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.util.Cache
import org.hexworks.zircon.api.util.DefaultCache

actual object CacheFactory {

    actual fun <R : Cacheable> create(): Cache<R> {
        return DefaultCache()
    }
}
