package org.codetome.zircon.internal.component

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TileImage

interface WrappingStrategy<T: Any, S: Any> {

    /**
     * Returns the size the wrapping occupies.
     * For example a border will occupy 2x2 size.
     */
    fun getOccupiedSize(): Size

    fun getOffset(): Position

    /**
     * Applies the [WrappingStrategy] to a [TileImage] at the given offset (position)
     * and in a given [Size] using the given style. The offset/size is necessary because
     * if you apply border and shadow as well you need to know where the border should
     * end and the shadow should start.
     */
    fun apply(tileImage: TileImage<T, S>, size: Size, offset: Position, style: StyleSet)

    /**
     * Removes the last change made by this [WrappingStrategy].
     */
    fun remove(tileImage: TileImage<T, S>, size: Size, offset: Position, style: StyleSet): Unit {

    }

    /**
     * Themes don't apply to this wrapper.
     */
    fun isThemeNeutral(): Boolean
}
