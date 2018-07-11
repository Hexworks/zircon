package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.internal.util.Cache
import org.codetome.zircon.internal.util.DefaultCache

actual object CacheFactory {
    actual fun <R : Cacheable> create(): Cache<R> {
        return DefaultCache()
    }
}
