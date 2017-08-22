package org.codetome.zircon.internal.font

import org.codetome.zircon.api.TextCharacter

/**
 * Transforms a font region. A font region is a part of a
 * font sprite sheet or other font source which represents a character.
 */
interface FontRegionTransformer<R> {

    /**
     * Transforms a font region and returns the transformed version.
     */
    fun transform(region: R, textCharacter: TextCharacter): R
}