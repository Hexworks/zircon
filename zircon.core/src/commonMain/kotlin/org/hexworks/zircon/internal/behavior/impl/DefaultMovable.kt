package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.behavior.extensions.withPosition
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

class DefaultMovable(
    private val boundableProperty: Property<Boundable>
) : Movable {

    private val boundable: Boundable by boundableProperty.asDelegate()

    override val positionProperty = boundableProperty.bindTransform(Boundable::position)
    override val position: Position by positionProperty.asDelegate()

    override val sizeProperty = boundableProperty.bindTransform(Boundable::size)
    override val size: Size by sizeProperty.asDelegate()

    override fun moveTo(position: Position): Boolean {
        boundableProperty.value = boundable.withPosition(position)
        return true
    }

    override fun intersects(other: Boundable): Boolean {
        return boundable.intersects(other)
    }

    override fun containsPosition(position: Position): Boolean {
        return boundable.containsPosition(position)
    }

    override fun containsBoundable(other: Boundable): Boolean {
        return boundable.containsBoundable(other)
    }

}
