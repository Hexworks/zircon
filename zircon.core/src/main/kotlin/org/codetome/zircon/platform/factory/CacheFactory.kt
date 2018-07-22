package org.codetome.zircon.platform.factory

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.api.util.Cache

expect object CacheFactory {

    fun <R : Cacheable> create(): Cache<R>
}
