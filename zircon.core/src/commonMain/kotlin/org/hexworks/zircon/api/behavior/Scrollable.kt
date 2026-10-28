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
public interface Scrollable {

    /**
     * The [Size] of the virtual space this [Scrollable] can scroll through.
     */
    public var actualSize: Size
    public val actualSizeProperty: Property<Size>

    /**
     * The size of the visible part of this [Scrollable3D].
     */
    public val visibleSize: Size

    /**
     * The offset where the visible part of this [Scrollable] starts.
     */
    public var visibleOffset: Position
    public val visibleOffsetProperty: Property<Position>

    public companion object {

        public fun create(
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
