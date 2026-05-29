package org.hexworks.zircon.api.behavior.extensions

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.data.extensions.toPosition
import org.hexworks.zircon.api.data.extensions.withHeight
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY
import org.hexworks.zircon.api.data.extensions.withWidth
import org.hexworks.zircon.api.data.extensions.withX
import org.hexworks.zircon.api.data.extensions.withY

val Boundable.x
    get() = position.x

val Boundable.y
    get() = position.y

val Boundable.width
    get() = size.width

val Boundable.height
    get() = size.height

val Boundable.topLeft: Position
    get() = position

val Boundable.topCenter: Position
    get() = position.withRelativeX(width / 2)

val Boundable.topRight: Position
    get() = position.withRelativeX(width)

val Boundable.rightCenter: Position
    get() = position.withRelativeX(width).withRelativeY(height / 2)

val Boundable.bottomRight: Position
    get() = position + size.toPosition()

val Boundable.bottomCenter: Position
    get() = position.withRelativeY(height).withRelativeX(width / 2)

val Boundable.bottomLeft: Position
    get() = position.withRelativeY(height)

val Boundable.leftCenter: Position
    get() = position.withRelativeY(height / 2)

val Boundable.center: Position
    get() = position.withRelativeX(width / 2).withRelativeY(height / 2)


fun Boundable.splitHorizontal(splitAtX: Int): Pair<Boundable, Boundable> {
    val left = Boundable.create(Position.create(x, y), Size.create(splitAtX, height))
    val right = Boundable.create(Position.create(x + splitAtX, y), Size.create(width - splitAtX, height))
    return left to right
}

fun Boundable.splitVertical(splitAtY: Int): Pair<Boundable, Boundable> {
    val left = Boundable.create(Position.create(x, y), Size.create(width, splitAtY))
    val right = Boundable.create(Position.create(x, y + splitAtY), Size.create(width, height - splitAtY))
    return left to right
}

fun Boundable.fetchPositions(): Iterable<Position> {
    return size.fetchPositions()
        .map { it + position }
}

fun Boundable.withX(x: Int) = Boundable.create(position.withX(x), size)

fun Boundable.withRelativeX(delta: Int) = Boundable.create(position.withX(x + delta), size)

fun Boundable.withY(y: Int) = Boundable.create(position.withY(y), size)

fun Boundable.withRelativeY(delta: Int) = Boundable.create(position.withY(y + delta), size)

fun Boundable.withWidth(width: Int) = Boundable.create(position, size.withWidth(width))

fun Boundable.withRelativeWidth(delta: Int) = Boundable.create(position, size.withWidth(width + delta))

fun Boundable.withHeight(height: Int) = Boundable.create(position, size.withHeight(height))

fun Boundable.withRelativeHeight(delta: Int) = Boundable.create(position, size.withHeight(height + delta))

fun Boundable.withPosition(position: Position) = Boundable.create(position, size)

fun Boundable.withSize(size: Size) = Boundable.create(position, size)

fun Boundable.withRelativePosition(position: Position) = Boundable.create(this.position + position, size)

fun Boundable.withRelativeSize(size: Size) = Boundable.create(position, this.size + size)

