package org.codetome.zircon.api

import org.codetome.zircon.internal.util.Cache

actual object PositionFactory {

    private val cache = Cache.create<Position>()

    actual fun create(x: Int, y: Int): Position {
        return cache.retrieveIfPresent(Position.generateCacheKey(x, y)).orElseGet {
            cache.store(JvmPosition(x, y))
        }
    }
}
