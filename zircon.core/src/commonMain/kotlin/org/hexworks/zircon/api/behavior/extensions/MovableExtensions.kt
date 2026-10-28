package org.hexworks.zircon.api.behavior.extensions

import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY

/**
 * Moves this [Movable] relative to its current position by the given
 * [position]. E.g.: if its current position is (3, 2) and it is moved by
 * (-1, 2), its new position will be (2, 4).
 */
public fun Movable.moveBy(position: Position): Boolean = moveTo(this.position + position)

public fun Movable.moveRightBy(delta: Int): Boolean = moveTo(position.withRelativeX(delta))

public fun Movable.moveLeftBy(delta: Int): Boolean = moveTo(position.withRelativeX(-delta))

public fun Movable.moveUpBy(delta: Int): Boolean = moveTo(position.withRelativeY(-delta))

public fun Movable.moveDownBy(delta: Int): Boolean = moveTo(position.withRelativeY(delta))
