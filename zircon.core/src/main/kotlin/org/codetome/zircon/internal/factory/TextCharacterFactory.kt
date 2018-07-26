package org.codetome.zircon.internal.factory

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.util.Cache
import org.codetome.zircon.internal.data.DefaultTile

internal object TextCharacterFactory {

    private val CACHE: Cache<Tile> = Cache.create()

    fun create(character: Char, styleSet: StyleSet, tags: Set<String>): Tile {
        return CACHE.retrieveIfPresent(Tile.generateCacheKey(
                character = character,
                styleSet = styleSet,
                tags = tags)).orElseGet {
            CACHE.store(DefaultTile(
                    character = character,
                    styleSet = styleSet,
                    tags = tags))
        }
    }
}
