package org.codetome.zircon.internal.util

import org.codetome.zircon.api.behavior.Cacheable

expect object CacheFactory {

    fun <R : Cacheable> create(): Cache<R>
}
