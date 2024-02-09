package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable

/**
 * A [Scrollable] object maintains a scrollable "window" of `visibleSize` over
 * an area of (possibly) larger (`actualSize`) size. `visibleOffset` can be modified
 * to move the window around.
 */
interface Scrollable {

    /**
     * the [Size] of the virtual space this [Scrollable] can scroll through
     */
    var actualSize: Size
    val actualSizeProperty: Property<Size>

    /**
     * the size of the visible part of this [Scrollable3D].
     */
    val visibleSize: Size

    /**
     * The offset where the visible part of this [Scrollable] starts.
     */
    var visibleOffset: Position
    val visibleOffsetProperty: Property<Position>

    /**
     * Scrolls this [Scrollable] right.
     * Has no effect if the bounds are already reached.
     * @return the current `visibleOffset`
     */
    fun scrollOneRight(): Position

    /**
     * Scrolls this [Scrollable] left.
     * Has no effect if the bounds are already reached.
     * @return the current `visibleOffset`
     */
    fun scrollOneLeft(): Position

    /**
     * Scrolls this [Scrollable] up.
     * Has no effect if the bounds are already reached.
     * @return the current `visibleOffset`
     */
    fun scrollOneUp(): Position

    /**
     * Scrolls this [Scrollable] down.
     * Has no effect if the bounds are already reached.
     * @return the current `visibleOffset`
     */
    fun scrollOneDown(): Position

    /**
     * Scrolls this [Scrollable] by `width` width to the right.
     * Has no effect if the bounds are already reached.
     * @return the current `visibleOffset`
     */
    fun scrollRightBy(columns: Int): Position

    /**
     * Scrolls this [Scrollable] with `width` width to the left.
     * Has no effect if the bounds are already reached.
     * @return the current `visibleOffset`
     */
    fun scrollLeftBy(columns: Int): Position

    /**
     * Scrolls this [Scrollable] by `depth` depth up.
     * Has no effect if the bounds are already reached.
     * @return the current `visibleOffset`
     */
    fun scrollUpBy(rows: Int): Position

    /**
     * Scrolls this [Scrollable] with `rows` rows down.
     * Has no effect if the bounds are already reached.
     * @return the current `visibleOffset`
     */
    fun scrollDownBy(rows: Int): Position

    /**
     * Scrolls this [Scrollable] to the provided position.
     * Has no effect if the bounds are already reached.
     * @return the current `visibleOffset`
     */
    fun scrollTo(position: Position): Position

    companion object {

        fun create(
            visibleSize: Size,
            actualSize: Size
        ): Scrollable {
            require(actualSize.toRect().containsBoundable(visibleSize.toRect())) {
                "Visible size cannot be bigger than actual size"
            }
            return DefaultScrollable(visibleSize, actualSize)
        }
    }

}
