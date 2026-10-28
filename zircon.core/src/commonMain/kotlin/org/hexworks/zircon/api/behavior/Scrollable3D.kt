package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

/**
 * A [Scrollable3D] object has a visible 3D space which might be smaller than its real size.
 * A 3D scrollable maintains a visible "cube" of its content.
 */
public interface Scrollable3D {

    /**
     * The [Size3D] of the virtual space this [Scrollable3D] can scroll through.
     */
    public var actualSize: Size3D
    public val actualSizeProperty: Property<Size3D>

    /**
     * Returns the size of the visible part of this [Scrollable3D].
     */
    public val visibleSize: Size3D

    /**
     * The offset where the visible part of this [Scrollable3D] starts.
     */
    public var visibleOffset: Position3D
    public val visibleOffsetProperty: Property<Position3D>

    /**
     * The 2D scrollable of this [Scrollable3D].
     */
    public val scrollable2D: Scrollable

}
