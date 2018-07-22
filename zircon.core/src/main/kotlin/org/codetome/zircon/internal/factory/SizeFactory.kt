package org.codetome.zircon.internal.factory

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.internal.DefaultSize

internal object SizeFactory {

    private val cache = Cache.create<Size>()

    fun create(xLength: Int, yLength: Int): Size {
        return cache.retrieveIfPresent(Size.generateCacheKey(xLength, yLength)).orElseGet {
            cache.store(DefaultSize(xLength, yLength))
        }
    }
}
