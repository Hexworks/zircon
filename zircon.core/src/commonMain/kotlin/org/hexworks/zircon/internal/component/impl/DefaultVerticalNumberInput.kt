package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import kotlin.math.min

class DefaultVerticalNumberInput(
        initialValue: Int,
        minValue: Int,
        maxValue: Int,
        componentMetadata: ComponentMetadata,
        renderingStrategy: ComponentRenderingStrategy<NumberInput>)
    : BaseNumberInput(
        initialValue, minValue, maxValue, componentMetadata, renderingStrategy) {

    override var maxNumberLength = min(Int.MAX_VALUE.toString().length, size.height)

    override fun refreshCursor() {
        var pos = textBuffer.cursor.position
        pos = pos.withX(min(pos.x, contentSize.height))
        pos = pos.withY(0)
        val invertedPosition = Position.create(pos.y, pos.x)
        Zircon.eventBus.publish(
                event = ZirconEvent.RequestCursorAt(invertedPosition
                        .withRelative(relativePosition + contentOffset)),
                eventScope = ZirconScope)
    }
}
