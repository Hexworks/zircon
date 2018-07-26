package org.codetome.zircon.internal.factory

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.internal.DefaultTextCharacter

internal object TextCharacterFactory {

    private val cache: Cache<TextCharacter> = Cache.create()

    fun create(character: Char, styleSet: StyleSet, tags: Set<String>): TextCharacter {
        return cache.retrieveIfPresent(TextCharacter.generateCacheKey(
                character = character,
                styleSet = styleSet,
                tags = tags)).orElseGet {
            cache.store(DefaultTextCharacter(
                    character = character,
                    styleSet = styleSet,
                    tags = tags))
        }
    }
}
