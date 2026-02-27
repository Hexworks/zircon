package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.behavior.extensions.withPosition
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

class DefaultMovable(
    private val boundableValue: Property<Boundable>
) : Movable {

    private val boundable: Boundable by boundableValue.asDelegate()

    override val positionValue = boundableValue.value.position.toProperty().apply {
        updateFrom(boundableValue.bindTransform { it.position })
    }
    override val position: Position by positionValue.asDelegate()

    override val sizeValue = boundableValue.value.size.toProperty().apply {
        updateFrom(boundableValue.bindTransform { it.size })
    }
    override val size: Size by sizeValue.asDelegate()


    override fun moveTo(position: Position): Boolean {
        boundableValue.value = boundable.withPosition(position)
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
