package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.internal.multiplatform.api.Cache
import org.codetome.zircon.internal.multiplatform.impl.JvmCache

actual object CacheFactory {
    actual fun <R : Cacheable> create(): Cache<R> {
        return JvmCache()
    }
}
