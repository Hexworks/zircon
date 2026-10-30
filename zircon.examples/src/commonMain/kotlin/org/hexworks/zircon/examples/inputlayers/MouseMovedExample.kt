package org.hexworks.zircon.examples.inputlayers

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.ComponentAlignments.alignmentWithin
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** A panel that reports the mouse moving over it. */
public val mouseMovedExample: Example = Example("Mouse moved") { context ->
    val root = panel { preferredSize = context.contentSize }
    val hover = panel {
        preferredSize = Size(30, 5)
        alignment = alignmentWithin(root, ComponentAlignment.CENTER)
        decorations { +box(title = "Hover me") }
    }
    val last = label { preferredSize = Size(28, 1) }
    hover.addComponent(last)
    root.addComponent(hover)
    var subscription: Subscription? = null
    ExampleView(
        content = root,
        onShown = {
            subscription = hover.processMouseEvents(MouseEventType.MOUSE_MOVED) { event, _ ->
                last.text = "Moved to ${event.position}"
            }
        },
        onHidden = {
            subscription?.dispose()
            subscription = null
        },
    )
}
