package org.codetome.zircon.font.impl.cache

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.font.ImageCachingStrategy
import java.util.*

class CP437TilesetCachingStrategy : ImageCachingStrategy {

    override fun generateCacheKeyFor(textCharacter: TextCharacter) = Objects.hash(
            textCharacter.getCharacter(),
            textCharacter.getBackgroundColor(),
            textCharacter.getForegroundColor())
}