package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Scrollable3D
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import kotlin.math.max
import kotlin.math.min

class DefaultScrollable3D(initialVisibleSize: Size3D,
                          initialActualSize: Size3D)
    : Scrollable3D {

    private var scrollable2D = DefaultScrollable(
            visibleSize = initialVisibleSize.to2DSize(),
            initialActualSize = initialActualSize.to2DSize())

    override val visibleSize = initialVisibleSize

    override val actualSize = initialActualSize

    init {
        checkSizes(initialActualSize)
    }

    override var visibleOffset = Position3D.from2DPosition(Position.defaultPosition())
        private set

    override fun scrollOneRight() = Position3D.from2DPosition(
            position = scrollable2D.scrollOneRight(),
            z = visibleOffset.z).apply {
        visibleOffset = this
    }

    override fun scrollOneLeft() = Position3D.from2DPosition(
            position = scrollable2D.scrollOneLeft(),
            z = visibleOffset.z).apply {
        visibleOffset = this
    }

    override fun scrollOneForward() = Position3D.from2DPosition(
            position = scrollable2D.scrollOneDown(),
            z = visibleOffset.z).apply {
        visibleOffset = this
    }

    override fun scrollOneBackward() = Position3D.from2DPosition(
            position = scrollable2D.scrollOneUp(),
            z = visibleOffset.z).apply {
        visibleOffset = this
    }

    override fun scrollOneUp(): Position3D {
        if (visibleSize.zLength + visibleOffset.z < actualSize.zLength) {
            this.visibleOffset = visibleOffset.withRelativeZ(1)
        }
        return visibleOffset
    }

    override fun scrollOneDown(): Position3D {
        if (visibleOffset.z > 0) {
            visibleOffset = visibleOffset.withRelativeZ(-1)
        }
        return visibleOffset
    }

    override fun scrollRightBy(x: Int) = Position3D.from2DPosition(
            position = scrollable2D.scrollRightBy(x),
            z = visibleOffset.z).apply {
        visibleOffset = this
    }

    override fun scrollLeftBy(x: Int) = Position3D.from2DPosition(
            position = scrollable2D.scrollLeftBy(x),
            z = visibleOffset.z).apply {
        visibleOffset = this
    }

    override fun scrollForwardBy(y: Int) = Position3D.from2DPosition(
            position = scrollable2D.scrollDownBy(y),
            z = visibleOffset.z).apply {
        visibleOffset = this
    }

    override fun scrollBackwardBy(y: Int) = Position3D.from2DPosition(
            position = scrollable2D.scrollUpBy(y),
            z = visibleOffset.z).apply {
        visibleOffset = this
    }

    override fun scrollUpBy(z: Int): Position3D {
        require(z >= 0) {
            "You can only scroll up by a positive amount!"
        }
        val levelToScrollTo = visibleOffset.z + z
        val lastScrollableLevel = actualSize.zLength - visibleSize.zLength
        visibleOffset = visibleOffset.copy(z = min(levelToScrollTo, lastScrollableLevel))
        return visibleOffset
    }

    override fun scrollDownBy(z: Int): Position3D {
        require(z >= 0) {
            "You can only scroll down by a positive amount!"
        }
        val levelToScrollTo = visibleOffset.z - z
        visibleOffset = visibleOffset.copy(z = max(0, levelToScrollTo))
        return visibleOffset
    }

    override fun scrollTo3DPosition(position3D: Position3D) {
        require(actualSize.containsPosition(position3D))
        {
            "new position $position3D has to be within the actual size $actualSize"
        }
        visibleOffset = position3D
    }

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
