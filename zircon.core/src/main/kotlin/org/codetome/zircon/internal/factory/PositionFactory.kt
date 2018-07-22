package org.codetome.zircon.internal.factory

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.internal.DefaultPosition

internal object PositionFactory {

    private val cache = Cache.create<Position>()

    fun create(x: Int, y: Int): Position {
        return cache.retrieveIfPresent(Position.generateCacheKey(x, y)).orElseGet {
            cache.store(DefaultPosition(x, y))
        }
    }
}
