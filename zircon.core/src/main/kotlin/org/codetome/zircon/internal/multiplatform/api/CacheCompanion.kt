package org.codetome.zircon.internal.multiplatform.api

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.internal.multiplatform.factory.CacheFactory

interface CacheCompanion {

    fun <R : Cacheable> create(): Cache<R> = CacheFactory.create()
}
