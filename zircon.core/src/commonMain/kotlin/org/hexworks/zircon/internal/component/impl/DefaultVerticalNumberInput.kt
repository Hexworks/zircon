package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.event.ZirconEvent
import kotlin.math.min

class DefaultVerticalNumberInput internal constructor(
    initialValue: Int,
    minValue: Int,
    maxValue: Int,
    componentMetadata: ComponentMetadata,
    renderingStrategy: ComponentRenderingStrategy<NumberInput>
) : BaseNumberInput(
    initialValue, minValue, maxValue, componentMetadata, renderingStrategy
) {

    override var maxNumberLength = min(Int.MAX_VALUE.toString().length, size.height)

    override fun refreshCursor() {
        var pos = _textBuffer.cursor.position
        pos = pos.withX(min(pos.x, contentSize.height))
        pos = pos.withY(0)
        val invertedPosition = Position.create(pos.y, pos.x)
        whenConnectedToRoot { root ->
            root.eventBus.publish(
                event = ZirconEvent.RequestCursorAt(
                    position = invertedPosition.withRelative(relativePosition + contentOffset),
                    emitter = this
                ),
                eventScope = root.eventScope
            )
        }
    }
}
