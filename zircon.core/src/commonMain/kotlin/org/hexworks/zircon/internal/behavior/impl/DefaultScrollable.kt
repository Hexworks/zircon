package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.ZERO
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.toBoundable

class DefaultScrollable internal constructor(
    initialVisibleSize: Size,
    initialActualSize: Size
) : Scrollable {

    override val visibleSize = initialVisibleSize
    override val actualSizeProperty =
        initialActualSize.toProperty(
            optionalName = "Scrollable:actualSize",
            validator = { _, newSize ->
                newSize.toBoundable().containsBoundable(visibleSize.toBoundable())
            })
    override var actualSize: Size by actualSizeProperty.asDelegate()

    override val visibleOffsetProperty =
        ZERO.toProperty(
            optionalName = "Scrollable:visibleOffset",
            validator = { _, newPos ->
                newPos.x >= 0 && newPos.y >= 0 && actualSize.width >= newPos.x && actualSize.height >= newPos.y
            })

    override var visibleOffset: Position by visibleOffsetProperty.asDelegate()
}