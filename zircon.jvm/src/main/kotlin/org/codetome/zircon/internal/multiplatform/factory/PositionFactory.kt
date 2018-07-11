package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.api.Position
import org.codetome.zircon.internal.DefaultPosition
import org.codetome.zircon.internal.util.Cache

actual object PositionFactory {

    private val cache = Cache.create<Position>()

    actual fun create(x: Int, y: Int): Position {
        return cache.retrieveIfPresent(Position.generateCacheKey(x, y)).orElseGet {
            cache.store(DefaultPosition(x, y))
        }
    }
}
