package org.codetome.zircon.internal.factory

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.internal.graphics.DefaultStyleSet

object StyleSetFactory {

    private val cache = Cache.create<StyleSet>()

    fun create(foregroundColor: TextColor, backgroundColor: TextColor, modifiers: Set<Modifier>): StyleSet {
        return cache.retrieveIfPresent(StyleSet.generateCacheKey(
                foregroundColor = foregroundColor,
                backgroundColor = backgroundColor,
                modifiers = modifiers)).orElseGet {
            cache.store(DefaultStyleSet(
                    foregroundColor = foregroundColor,
                    backgroundColor = backgroundColor,
                    modifiers = modifiers))
        }
    }
}
