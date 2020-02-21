package org.hexworks.zircon.examples.input

import org.hexworks.zircon.api.ComponentAlignments.alignmentWithin
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.examples.base.displayScreen

object MouseMovedExample {


    @JvmStatic
    fun main(args: Array<String>) {

        val screen = displayScreen()

        val panel = Components.panel()
                .withSize(Size.create(20, 5))
                .withAlignment(alignmentWithin(screen, CENTER))
                .withDecorations(box(title = "Hover me"))
                .build()

        screen.addComponent(panel)

        panel.processMouseEvents(MouseEventType.MOUSE_MOVED) { event, _ ->
            println(event)
        }

    }

}
