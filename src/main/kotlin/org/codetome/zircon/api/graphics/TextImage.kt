package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.graphics.StyleSet

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

    /**
     * Writes the given `text` at the given `position`.
     */
    fun putText(text: String, position: Position)

    /**
     * Sets the [StyleSet] of this [TextImage]
     * and also applies it to all currently present
     * [TextCharacter]s.
     */
    fun applyStyle(styleSet: StyleSet)

}
