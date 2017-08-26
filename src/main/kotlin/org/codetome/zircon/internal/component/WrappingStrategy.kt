package org.codetome.zircon.internal.component

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TextImage

interface WrappingStrategy {

    /**
     * Returns the size the wrapping occupies.
     * For example a border will occupy 2x2 size.
     */
    fun getOccupiedSize(): Size

    fun getOffset(): Position

    fun apply(textImage: TextImage, size: Size, style: StyleSet)

    /**
     * Themes don't apply to this wrapper.
     */
    fun isThemeNeutral(): Boolean
}