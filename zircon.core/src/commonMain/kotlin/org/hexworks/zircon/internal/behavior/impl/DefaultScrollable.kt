package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

class DefaultScrollable internal constructor(
    initialVisibleSize: Size,
    initialActualSize: Size
) : Scrollable {

    override val visibleSize = initialVisibleSize
    override val actualSizeProperty =
        initialActualSize.toProperty(name = "Scrollable:actualSize", validator = { _, newSize ->
            newSize.toRect().containsBoundable(visibleSize.toRect())
        })
    override var actualSize: Size by actualSizeProperty.asDelegate()

    override val visibleOffsetProperty = Position.zero().toProperty(name = "Scrollable:visibleOffset", validator = { _, newPos ->
        newPos.x >= 0 && newPos.y >= 0 && actualSize.width >= newPos.x && actualSize.height >= newPos.y
    })
    override var visibleOffset: Position by visibleOffsetProperty.asDelegate()


    override fun scrollOneRight(): Position {
        visibleOffset = visibleOffset.withRelativeX(1)
        return visibleOffset
    }

    override fun scrollOneDown(): Position {
        visibleOffset = visibleOffset.withRelativeY(1)
        return visibleOffset
    }

    override fun scrollOneLeft(): Position {
        visibleOffset = visibleOffset.withRelativeX(-1)
        return visibleOffset
    }

    override fun scrollOneUp(): Position {
        visibleOffset = visibleOffset.withRelativeY(-1)
        return visibleOffset
    }

    override fun scrollRightBy(columns: Int): Position {
        visibleOffset = visibleOffset.withRelativeX(columns)
        return visibleOffset
    }

    override fun scrollLeftBy(columns: Int): Position {
        visibleOffset = visibleOffset.withRelativeX(-columns)
        return visibleOffset
    }

    override fun scrollUpBy(rows: Int): Position {
        visibleOffset = visibleOffset.withRelativeY(-rows)
        return visibleOffset
    }

    override fun scrollDownBy(rows: Int): Position {
        visibleOffset = visibleOffset.withRelativeY(rows)
        return visibleOffset
    }

    override fun scrollTo(position: Position): Position {
        visibleOffset = position
        return visibleOffset
    }
}