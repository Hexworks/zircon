package org.hexworks.zircon.examples.modifiers

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** A paragraph with the typing effect: the text shows up character by character. */
public val delayedTextExample: Example = Example("Delayed text") { context ->
    val root = panel {
        preferredSize = context.contentSize
        decorations { +box() }
    }
    root.addComponent(paragraph {
        +"This is a very long string I'd like to add with a typewriter effect"
        preferredSize = Size(root.contentSize.width, 3)
        typingEffectSpeedInMs = 200
    })
    ExampleView(root)
}
