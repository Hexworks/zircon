package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.internal.event.ZirconEvent
import kotlin.math.min

class DefaultHorizontalNumberInput internal constructor(
    initialValue: Int,
    minValue: Int,
    maxValue: Int,
    componentMetadata: ComponentMetadata,
    renderingStrategy: ComponentRenderingStrategy<NumberInput>
) : BaseNumberInput(
    initialValue, minValue, maxValue, componentMetadata, renderingStrategy
) {

    override var maxNumberLength = min(Int.MAX_VALUE.toString().length, size.width)

    override fun refreshCursor() {
//        var pos = textBuffer.cursor.position
//        pos = pos.withX(min(pos.x, contentSize.width))
//            .withY(0)
//        whenConnectedToRoot { root ->
//            root.eventBus.publish(
//                event = ZirconEvent.RequestCursorAt(
//                    position = pos.withRelative(absolutePosition + contentOffset),
//                    emitter = this
//                ),
//                eventScope = root.eventScope
//            )
//        }
    }
}
