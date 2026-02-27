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
fun Movable.moveBy(position: Position) = moveTo(this.position + position)

fun Movable.moveRightBy(delta: Int) = moveTo(position.withRelativeX(delta))

fun Movable.moveLeftBy(delta: Int) = moveTo(position.withRelativeX(-delta))

fun Movable.moveUpBy(delta: Int) = moveTo(position.withRelativeY(-delta))

fun Movable.moveDownBy(delta: Int) = moveTo(position.withRelativeY(delta))
