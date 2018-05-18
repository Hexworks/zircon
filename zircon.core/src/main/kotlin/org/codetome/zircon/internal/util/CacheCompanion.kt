package org.codetome.zircon.internal.util

import org.codetome.zircon.api.behavior.Cacheable

interface CacheCompanion {

    fun <R : Cacheable> create(): Cache<R> = CacheFactory.create()
}
