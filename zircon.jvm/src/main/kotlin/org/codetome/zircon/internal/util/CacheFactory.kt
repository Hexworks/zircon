package org.codetome.zircon.internal.util

import org.codetome.zircon.api.behavior.Cacheable

actual object CacheFactory {
    actual fun <R : Cacheable> create(): Cache<R> {
        return DefaultCache()
    }
}
