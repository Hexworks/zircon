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

public val Boundable.x: Int
    get() = position.x

public val Boundable.y: Int
    get() = position.y

public val Boundable.width: Int
    get() = size.width

public val Boundable.height: Int
    get() = size.height

public val Boundable.topLeft: Position
    get() = position

public val Boundable.topCenter: Position
    get() = position.withRelativeX(width / 2)

public val Boundable.topRight: Position
    get() = position.withRelativeX(width)

public val Boundable.rightCenter: Position
    get() = position.withRelativeX(width).withRelativeY(height / 2)

public val Boundable.bottomRight: Position
    get() = position + size.toPosition()

public val Boundable.bottomCenter: Position
    get() = position.withRelativeY(height).withRelativeX(width / 2)

public val Boundable.bottomLeft: Position
    get() = position.withRelativeY(height)

public val Boundable.leftCenter: Position
    get() = position.withRelativeY(height / 2)

public val Boundable.center: Position
    get() = position.withRelativeX(width / 2).withRelativeY(height / 2)


public fun Boundable.splitHorizontal(splitAtX: Int): Pair<Boundable, Boundable> {
    val left = Boundable.create(Position(x, y), Size(splitAtX, height))
    val right = Boundable.create(Position(x + splitAtX, y), Size(width - splitAtX, height))
    return left to right
}

public fun Boundable.splitVertical(splitAtY: Int): Pair<Boundable, Boundable> {
    val left = Boundable.create(Position(x, y), Size(width, splitAtY))
    val right = Boundable.create(Position(x, y + splitAtY), Size(width, height - splitAtY))
    return left to right
}

public fun Boundable.fetchPositions(): Iterable<Position> {
    return size.fetchPositions()
        .map { it + position }
}

public fun Boundable.withX(x: Int): Boundable = Boundable.create(position.withX(x), size)

public fun Boundable.withRelativeX(delta: Int): Boundable = Boundable.create(position.withX(x + delta), size)

public fun Boundable.withY(y: Int): Boundable = Boundable.create(position.withY(y), size)

public fun Boundable.withRelativeY(delta: Int): Boundable = Boundable.create(position.withY(y + delta), size)

public fun Boundable.withWidth(width: Int): Boundable = Boundable.create(position, size.withWidth(width))

public fun Boundable.withRelativeWidth(delta: Int): Boundable = Boundable.create(position, size.withWidth(width + delta))

public fun Boundable.withHeight(height: Int): Boundable = Boundable.create(position, size.withHeight(height))

public fun Boundable.withRelativeHeight(delta: Int): Boundable = Boundable.create(position, size.withHeight(height + delta))

public fun Boundable.withPosition(position: Position): Boundable = Boundable.create(position, size)

public fun Boundable.withSize(size: Size): Boundable = Boundable.create(position, size)

public fun Boundable.withRelativePosition(position: Position): Boundable = Boundable.create(this.position + position, size)

public fun Boundable.withRelativeSize(size: Size): Boundable = Boundable.create(position, this.size + size)

