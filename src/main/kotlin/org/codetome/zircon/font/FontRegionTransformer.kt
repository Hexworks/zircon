package org.codetome.zircon.font

import org.codetome.zircon.TextCharacter

/**
 * Transforms a font region.
 */
interface FontRegionTransformer<R> {

    /**
     * Transforms a font region and returns the transformed version.
     */
    fun transform(region: R, textCharacter: TextCharacter): R
}