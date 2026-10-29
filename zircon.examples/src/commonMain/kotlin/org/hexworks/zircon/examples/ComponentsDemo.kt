package org.hexworks.zircon.examples

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.checkBox
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.component.progressBar
import org.hexworks.zircon.api.builder.component.radioButton
import org.hexworks.zircon.api.builder.component.textArea
import org.hexworks.zircon.api.builder.component.toggleButton
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.Processed

/**
 * A tour of the built-in components: buttons, toggles, progress and text.
 */
public val componentsDemo: Demo = Demo("Components", CP437TilesetResources.wanderlust16x16()) { context ->
    val root = hBox {
        preferredSize = context.contentSize
        decorations { +box(title = "Zircon 2.0 components") }
    }
    val columnSize = Size((context.contentSize.width - 2) / 2, context.contentSize.height - 2)
    val left = vBox { preferredSize = columnSize }
    val right = vBox { preferredSize = columnSize }
    root.addComponent(left)
    root.addComponent(right)

    left.addComponent(header { +"Buttons" })
    val clicks = label { +"Clicked 0 times" }
    var count = 0
    left.addComponent(button { +"Click me" }.apply {
        handleComponentEvents(ComponentEventType.ACTIVATED) {
            count++
            clicks.text = "Clicked $count times"
            Processed
        }
    })
    left.addComponent(clicks)
    left.addComponent(toggleButton { +"Toggle" })
    left.addComponent(checkBox { +"Check box" })
    left.addComponent(radioButton { +"Radio"; key = "a" })
    left.addComponent(header { +"Progress" })
    left.addComponent(progressBar { preferredSize = Size(20, 1); range = 100; numberOfSteps = 20 }.apply { progress = 60.0 })

    right.addComponent(header { +"Text" })
    right.addComponent(paragraph {
        +"Paragraphs wrap their text to the width of the component, so long sentences look fine."
        preferredSize = Size(28, 4)
    })
    right.addComponent(textArea {
        text = "Editable text area"
        preferredSize = Size(28, 6)
        decorations { +box() }
    })

    DemoView(root)
}
