package org.hexworks.zircon.api.behavior.extensions

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY

/**
 * Scrolls this [Scrollable] right by one tile.
 * Has no effect if the bounds are already reached.
 */
public fun Scrollable.scrollOneRight(): Position = scrollRightBy(1)

/**
 * Scrolls this [Scrollable] left by one tile.
 * Has no effect if the bounds are already reached.
 */
public fun Scrollable.scrollOneLeft(): Position = scrollLeftBy(1)

/**
 * Scrolls this [Scrollable] up by one tile.
 * Has no effect if the bounds are already reached.
 */
public fun Scrollable.scrollOneUp(): Position = scrollUpBy(1)

/**
 * Scrolls this [Scrollable] down by one tile.
 * Has no effect if the bounds are already reached.
 */
public fun Scrollable.scrollOneDown(): Position = scrollDownBy(1)

/**
 * Scrolls this [Scrollable] by [columns] width to the right.
 * Stops at the bounds of the actual size.
 */
public fun Scrollable.scrollRightBy(columns: Int): Position = scrollTo(visibleOffset.withRelativeX(columns))

/**
 * Scrolls this [Scrollable] by [columns] to the left.
 * Stops at the bounds of the actual size.
 */
public fun Scrollable.scrollLeftBy(columns: Int): Position = scrollTo(visibleOffset.withRelativeX(-columns))

/**
 * Scrolls this [Scrollable] by [rows] up.
 * Stops at the bounds of the actual size.
 */
public fun Scrollable.scrollUpBy(rows: Int): Position = scrollTo(visibleOffset.withRelativeY(-rows))

/**
 * Scrolls this [Scrollable] by [rows] down.
 * Stops at the bounds of the actual size.
 */
public fun Scrollable.scrollDownBy(rows: Int): Position = scrollTo(visibleOffset.withRelativeY(rows))

/**
 * Scrolls this [Scrollable] to the provided position, clamped so the visible area stays
 * inside the actual size.
 * @return the new visible offset
 */
public fun Scrollable.scrollTo(position: Position): Position {
    visibleOffset = Position(
        x = position.x.coerceIn(0, maxOf(0, actualSize.width - visibleSize.width)),
        y = position.y.coerceIn(0, maxOf(0, actualSize.height - visibleSize.height)),
    )
    return visibleOffset
}
