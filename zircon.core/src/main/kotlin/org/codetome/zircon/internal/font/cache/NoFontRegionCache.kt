package org.codetome.zircon.internal.font.cache

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.font.FontRegionCache
import java.util.*

/**
 * This is a no-op cache for fonts.
 */
class NoFontRegionCache<R> : FontRegionCache<R> {

    override fun retrieveIfPresent(textCharacter: TextCharacter) = Optional.empty<R>()

    override fun store(textCharacter: TextCharacter, region: R) {}
}
