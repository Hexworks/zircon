package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** A panel moves after two seconds, then the panel inside it moves too. */
public val moveExample: Example = Example("Move") { context ->
    lateinit var inner: Panel
    val outer = panel {
        preferredSize = Size(20, 10)
        decorations { +box() }
        inner = panel {
            preferredSize = Size(10, 5)
            decorations { +box() }
            button {
                +"Foo"
                position = Position(1, 1)
            }
        }
    }
    val root = context.oneColumn { it.addComponent(outer) }
    var frames = 0
    ExampleView(root, onFrame = {
        // The old example slept on a thread; here the moves happen on frame counts.
        frames++
        if (frames == 120) outer.moveTo(outer.position + Position(5, 5))
        if (frames == 240) inner.moveTo(inner.position + Position(2, 2))
    })
}
