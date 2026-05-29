package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.toBoundable
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable

/**
 * A [Scrollable] object maintains a scrollable "window" of `visibleSize` over
 * an area of (possibly) larger (`actualSize`) size. `visibleOffset` can be modified
 * to move the window around.
 */
interface Scrollable {

    /**
     * The [Size] of the virtual space this [Scrollable] can scroll through.
     */
    var actualSize: Size
    val actualSizeProperty: Property<Size>

    /**
     * The size of the visible part of this [Scrollable3D].
     */
    val visibleSize: Size

    /**
     * The offset where the visible part of this [Scrollable] starts.
     */
    var visibleOffset: Position
    val visibleOffsetProperty: Property<Position>

    companion object {

        fun create(
            visibleSize: Size,
            actualSize: Size
        ): Scrollable {
            require(visibleSize.width <= actualSize.width && visibleSize.height <= actualSize.height){
                "Visible size cannot be bigger than actual size"
            }
            return DefaultScrollable(visibleSize, actualSize)
        }
    }

}
