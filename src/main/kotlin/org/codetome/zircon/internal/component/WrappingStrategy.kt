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

    /**
     * Applies the [WrappingStrategy] to a [TextImage] at the given offset (position)
     * and in a given [Size] using the given style. The offset/size is necessary because
     * if you apply border and shadow as well you need to know where the border should
     * end and the shadow should start.
     */
    fun apply(textImage: TextImage, size: Size, offset: Position, style: StyleSet)

    /**
     * Themes don't apply to this wrapper.
     */
    fun isThemeNeutral(): Boolean
}