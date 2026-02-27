package org.hexworks.zircon.api.behavior.extensions

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY

/**
 * Scrolls this [Scrollable] right by one tile.
 * Has no effect if the bounds are already reached.
 */
fun Scrollable.scrollOneRight(): Position {
    visibleOffset = visibleOffset.withRelativeX(1)
    return visibleOffset
}

/**
 * Scrolls this [Scrollable] left by one tile.
 * Has no effect if the bounds are already reached.
 */
fun Scrollable.scrollOneLeft(): Position {
    visibleOffset = visibleOffset.withRelativeX(-1)
    return visibleOffset
}

/**
 * Scrolls this [Scrollable] up by one tile.
 * Has no effect if the bounds are already reached.
 */
fun Scrollable.scrollOneUp(): Position {
    visibleOffset = visibleOffset.withRelativeY(-1)
    return visibleOffset
}

/**
 * Scrolls this [Scrollable] down by one tile.
 * Has no effect if the bounds are already reached.
 */
fun Scrollable.scrollOneDown(): Position {
    visibleOffset = visibleOffset.withRelativeY(1)
    return visibleOffset
}

/**
 * Scrolls this [Scrollable] by [columns] width to the right.
 * Has no effect if the bounds are already reached.
 */
fun Scrollable.scrollRightBy(columns: Int): Position {
    visibleOffset = visibleOffset.withRelativeX(columns)
    return visibleOffset
}

/**
 * Scrolls this [Scrollable] by [columns] to the left.
 * Has no effect if the bounds are already reached.
 */
fun Scrollable.scrollLeftBy(columns: Int): Position {
    visibleOffset = visibleOffset.withRelativeX(-columns)
    return visibleOffset
}

/**
 * Scrolls this [Scrollable] by [rows] up.
 * Has no effect if the bounds are already reached.
 */
fun Scrollable.scrollUpBy(rows: Int): Position {
    visibleOffset = visibleOffset.withRelativeY(-rows)
    return visibleOffset
}

/**
 * Scrolls this [Scrollable] by [rows] down.
 * Has no effect if the bounds are already reached.
 */
fun Scrollable.scrollDownBy(rows: Int): Position {
    visibleOffset = visibleOffset.withRelativeY(rows)
    return visibleOffset
}

/**
 * Scrolls this [Scrollable] to the provided position.
 * Has no effect if the bounds are already reached.
 */
fun Scrollable.scrollTo(position: Position): Position {
    visibleOffset = position
    return visibleOffset
}
