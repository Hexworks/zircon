package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.behavior.Scrollable3D
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.extensions.to2DSize

public class DefaultScrollable3D(
    initialVisibleSize: Size3D,
    initialActualSize: Size3D
) : Scrollable3D {

    override val scrollable2D: Scrollable = Scrollable.create(
        visibleSize = initialVisibleSize.to2DSize(),
        actualSize = initialActualSize.to2DSize()
    )

    override val visibleSize: Size3D = initialVisibleSize
    override val actualSizeProperty: Property<Size3D> = initialActualSize.toProperty()
    override var actualSize: Size3D by actualSizeProperty.asDelegate()

    init {
        checkSizes(initialActualSize)
    }

    override val visibleOffsetProperty: Property<Position3D> = Position3D.from2DPosition(
        position = Position.ZERO
    ).toProperty()
    override var visibleOffset: Position3D by visibleOffsetProperty.asDelegate()

    private fun checkSizes(newSize: Size3D) {
        require(newSize.xLength >= visibleSize.xLength) {
            "Can't have a virtual space (${newSize.xLength}, ${newSize.zLength})" +
                    " with less xLength than the visible space (${visibleSize.xLength}, ${visibleSize.zLength})!"
        }
        require(newSize.zLength >= visibleSize.zLength) {
            "Can't have a virtual space (${newSize.xLength}, ${newSize.zLength})" +
                    " with less yLength than the visible space (${visibleSize.xLength}, ${visibleSize.zLength})!"
        }
    }
}
