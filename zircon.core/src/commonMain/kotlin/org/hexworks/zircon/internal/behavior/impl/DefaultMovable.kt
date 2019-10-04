package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size

class DefaultMovable(size: Size,
                     position: Position = Position.defaultPosition())
    : Boundable, Movable {

    // note that we could delegate `Boundable` to rect but delegation of
    // mutable vars is broken in Kotlin:
    // http://the-cogitator.com/2018/09/29/by-the-way-exploring-delegation-in-kotlin.html#the-pitfall-of-interface-delegation

    override val position: Position
        get() = rect.position

    override val size: Size
        get() = rect.size

    override val rect: Rect
        get() = currentRect

    override val x: Int
        get() = position.x

    override val y: Int
        get() = position.y

    override val width: Int
        get() = size.width

    override val height: Int
        get() = size.height

    private var currentRect: Rect = Rect.create(position, size)

    override fun moveTo(position: Position) {
        currentRect = currentRect.withPosition(position)
    }

    override fun intersects(boundable: Boundable): Boolean {
        return rect.intersects(boundable.rect)
    }

    override fun containsPosition(position: Position): Boolean {
        return rect.containsPosition(position)
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        return rect.containsBoundable(boundable)
    }

}
