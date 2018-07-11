package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.internal.util.Cache

expect object CacheFactory {

    fun <R : Cacheable> create(): Cache<R>
}
