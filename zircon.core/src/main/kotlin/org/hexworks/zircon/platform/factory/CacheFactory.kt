package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.util.Cache

expect object CacheFactory {

    fun <R : Cacheable> create(): Cache<R>
}
