package org.codetome.zircon.graphics.image

import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.behavior.DrawSurface
import org.codetome.zircon.behavior.Drawable
import org.codetome.zircon.graphics.style.StyleSet

/**
 * An image built from [TextCharacter]s with color and style information.
 * These are completely in memory and not visible,
 * but can be used when drawing on other [DrawSurface]s.
 */
interface TextImage : DrawSurface, StyleSet, Drawable {

    /**
     * Returns a copy of this image resized to a new size and using a specified filler character
     * if the new size is larger than the old and we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun resize(newSize: Size, filler: TextCharacter): TextImage

}
