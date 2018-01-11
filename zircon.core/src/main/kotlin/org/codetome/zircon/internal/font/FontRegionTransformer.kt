package org.codetome.zircon.internal.font

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.FontTextureRegion

/**
 * Transforms a font region. A font region is a part of a
 * font sprite sheet or other font source which represents a character.
 */
interface FontRegionTransformer<T> {

    /**
     * Transforms a font region and returns the transformed version.
     */
    fun transform(region: FontTextureRegion<T>, textCharacter: TextCharacter): FontTextureRegion<T>
}