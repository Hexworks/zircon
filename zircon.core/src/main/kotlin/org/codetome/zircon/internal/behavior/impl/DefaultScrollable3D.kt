package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.game.Position3D
import org.codetome.zircon.api.game.Size3D
import org.codetome.zircon.internal.behavior.Scrollable3D
import org.codetome.zircon.api.util.Math

class DefaultScrollable3D(private var visibleSpaceSize: Size3D,
                          private var virtualSpaceSize: Size3D)
    : Scrollable3D {

    private var offset = Position3D.from2DPosition(Position.defaultPosition())

    private var scrollable2D = DefaultScrollable(
            visibleSpaceSize = visibleSpaceSize.to2DSize(),
            virtualSpaceSize = virtualSpaceSize.to2DSize())

    init {
        checkSizes()
    }

    override fun getVisibleSpaceSize() = visibleSpaceSize

    override fun getVirtualSpaceSize() = virtualSpaceSize

    override fun setVirtualSpaceSize(size: Size3D) {
        checkSizes()
        this.virtualSpaceSize = size
    }

    override fun getVisibleOffset() = offset

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
        if (visibleSpaceSize.zLength + offset.z < virtualSpaceSize.zLength) {
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
        val lastScrollableLevel = virtualSpaceSize.zLength - visibleSpaceSize.zLength
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
        require(virtualSpaceSize.xLength >= visibleSpaceSize.xLength) {
            "Can't have a virtual space (${virtualSpaceSize.xLength}, ${virtualSpaceSize.zLength})" +
                    " with less xLength than the visible space (${visibleSpaceSize.xLength}, ${visibleSpaceSize.zLength})!"
        }
        require(virtualSpaceSize.zLength >= visibleSpaceSize.zLength) {
            "Can't have a virtual space (${virtualSpaceSize.xLength}, ${virtualSpaceSize.zLength})" +
                    " with less yLength than the visible space (${visibleSpaceSize.xLength}, ${visibleSpaceSize.zLength})!"
        }
    }
}
