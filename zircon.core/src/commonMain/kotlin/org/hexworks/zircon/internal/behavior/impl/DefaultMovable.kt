package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size

class DefaultMovable(
        size: Size,
        position: Position = Position.defaultPosition()
) : Boundable, Movable {

    // note that we could delegate `Boundable` to rect but delegation of
    // mutable vars is broken in Kotlin:
    // http://the-cogitator.com/2018/09/29/by-the-way-exploring-delegation-in-kotlin.html#the-pitfall-of-interface-delegation

    override val position: Position
        get() = rect.position

    override val size: Size
        get() = rect.size

    override val rectValue = Rect.create(position, size).toProperty()
    override var rect: Rect by rectValue.asDelegate()

    override val x: Int
        get() = position.x

    override val y: Int
        get() = position.y

    override val width: Int
        get() = size.width

    override val height: Int
        get() = size.height

    override fun moveTo(position: Position): Boolean {
        rect = rect.withPosition(position)
        return true
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
