package org.codetome.zircon.internal.font.cache

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.font.ImageCachingStrategy
import java.util.*

class PhysicalFontCachingStrategy : ImageCachingStrategy {

    override fun generateCacheKeyFor(textCharacter: TextCharacter) = Objects.hash(
            textCharacter.getCharacter(),
            textCharacter.getBackgroundColor(),
            textCharacter.getForegroundColor())
}