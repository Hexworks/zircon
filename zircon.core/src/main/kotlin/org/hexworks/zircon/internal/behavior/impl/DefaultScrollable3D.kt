package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.util.Math
import org.hexworks.zircon.internal.behavior.Scrollable3D

class DefaultScrollable3D(private var visibleSize: Size3D,
                          private var actualSize: Size3D)
    : Scrollable3D {

    private var offset = Position3D.from2DPosition(Position.defaultPosition())

    private var scrollable2D = DefaultScrollable(
            visibleSize = visibleSize.to2DSize(),
            initialActualSize = actualSize.to2DSize())

    init {
        checkSizes()
    }

    override fun visibleSize() = visibleSize

    override fun actualSize() = actualSize

    override fun setActualSize(size: Size3D) {
        checkSizes()
        this.actualSize = size
    }

    override fun visibleOffset() = offset

    override fun scrollOneRight() = Position3D.from2DPosition(
            position = scrollable2D.scrollOneRight(),
            z = offset.z).apply {
        offset = this
    }

    override fun scrollOneLeft() = Position3D.from2DPosition(
            position = scrollable2D.scrollOneLeft(),
            z = offset.z).apply {
        offset = this
    }

    override fun scrollOneForward() = Position3D.from2DPosition(
            position = scrollable2D.scrollOneDown(),
            z = offset.z).apply {
        offset = this
    }

    override fun scrollOneBackward() = Position3D.from2DPosition(
            position = scrollable2D.scrollOneUp(),
            z = offset.z).apply {
        offset = this
    }

    override fun scrollOneUp(): Position3D {
        if (visibleSize.zLength + offset.z < actualSize.zLength) {
            this.offset = offset.withRelativeZ(1)
        }
        return offset
    }

    override fun scrollOneDown(): Position3D {
        if (offset.z > 0) {
            offset = offset.withRelativeZ(-1)
        }
        return offset
    }

    override fun scrollRightBy(x: Int) = Position3D.from2DPosition(
            position = scrollable2D.scrollRightBy(x),
            z = offset.z).apply {
        offset = this
    }

    override fun scrollLeftBy(x: Int) = Position3D.from2DPosition(
            position = scrollable2D.scrollLeftBy(x),
            z = offset.z).apply {
        offset = this
    }

    override fun scrollForwardBy(y: Int) = Position3D.from2DPosition(
            position = scrollable2D.scrollDownBy(y),
            z = offset.z).apply {
        offset = this
    }

    override fun scrollBackwardBy(y: Int) = Position3D.from2DPosition(
            position = scrollable2D.scrollUpBy(y),
            z = offset.z).apply {
        offset = this
    }

    override fun scrollUpBy(z: Int): Position3D {
        require(z >= 0) {
            "You can only scroll up by a positive amount!"
        }
        val levelToScrollTo = offset.z + z
        val lastScrollableLevel = actualSize.zLength - visibleSize.zLength
        offset = offset.copy(z = Math.min(levelToScrollTo, lastScrollableLevel))
        return offset
    }

    override fun scrollDownBy(z: Int): Position3D {
        require(z >= 0) {
            "You can only scroll down by a positive amount!"
        }
        val levelToScrollTo = offset.z - z
        offset = offset.copy(z = Math.max(0, levelToScrollTo))
        return offset
    }

    private fun checkSizes() {
        require(actualSize.xLength >= visibleSize.xLength) {
            "Can't have a virtual space (${actualSize.xLength}, ${actualSize.zLength})" +
                    " with less xLength than the visible space (${visibleSize.xLength}, ${visibleSize.zLength})!"
        }
        require(actualSize.zLength >= visibleSize.zLength) {
            "Can't have a virtual space (${actualSize.xLength}, ${actualSize.zLength})" +
                    " with less yLength than the visible space (${visibleSize.xLength}, ${visibleSize.zLength})!"
        }
    }
}
