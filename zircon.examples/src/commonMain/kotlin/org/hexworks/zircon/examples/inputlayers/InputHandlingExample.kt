package org.hexworks.zircon.examples.inputlayers

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.logArea
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * Listens to key presses and mouse presses on the whole screen and logs them. Key events
 * are passed on (`Pass`) so the rest of the UI still gets them; mouse presses are consumed.
 */
public val inputHandlingExample: Example = Example("Input") { context ->
    val root = vBox {
        preferredSize = context.contentSize
        decorations { +box(title = "Press keys, click anywhere") }
    }
    val log = logArea {
        preferredSize = Size(root.contentSize.width, root.contentSize.height)
        logRowHistorySize = 20
    }
    root.addComponent(log)
    val subscriptions = mutableListOf<Subscription>()
    ExampleView(
        content = root,
        onShown = {
            subscriptions += context.screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, _ ->
                log.addRow(label { +"Keyboard event is: $event" })
                Pass
            }
            subscriptions += context.screen.handleMouseEvents(MouseEventType.MOUSE_PRESSED) { event, _ ->
                log.addRow(label { +"$event" })
                Processed
            }
        },
        onHidden = {
            subscriptions.forEach { it.dispose() }
            subscriptions.clear()
        },
    )
}
