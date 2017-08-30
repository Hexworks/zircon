package org.codetome.zircon.internal.font

import org.codetome.zircon.api.TextCharacter

/**
 * An [ImageCachingStrategy] is responsible for generating cache keys for [TextCharacter]s.
 * For a [org.codetome.zircon.api.font.Font] which is a graphical tileset for example and uses tags to differentiate between
 * textures a caching strategy will generate hashes based on tags, while for a cp437 font
 * only the character and the colors might be used.
 */
interface ImageCachingStrategy {

    /**
     * Generates a cache key for a given [TextCharacter].
     */
    fun generateCacheKeyFor(textCharacter: TextCharacter): Int
}