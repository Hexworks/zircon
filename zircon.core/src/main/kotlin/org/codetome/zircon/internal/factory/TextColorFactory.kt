package org.codetome.zircon.internal.factory

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.internal.color.DefaultTextColor

internal object TextColorFactory {

    private val cache = Cache.create<TextColor>()

    fun create(red: Int, green: Int, blue: Int, alpha: Int): TextColor {
        return cache.retrieveIfPresent(TextColor.generateCacheKey(red, green, blue, alpha)).orElseGet {
            cache.store(DefaultTextColor(red, green, blue, alpha))
        }
    }
}
