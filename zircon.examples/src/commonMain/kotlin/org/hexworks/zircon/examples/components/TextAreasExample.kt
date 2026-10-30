package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.horizontalNumberInput
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.textArea
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Text areas plain and decorated, on the screen and on a panel, plus a number input with buttons. */
public val textAreasExample: Example = Example("Text areas") { context ->
    val root = panel { preferredSize = context.contentSize }
    val panel = panel {
        decorations { +box() }
        preferredSize = Size(28, 28)
        position = Position(31, 1)
    }
    root.addComponent(panel)
    root.addComponent(textArea { text = "Some text"; preferredSize = Size(13, 5); position = Position(2, 2) })
    panel.addComponent(textArea { text = "Some text"; preferredSize = Size(13, 5); position = Position(2, 2) })
    root.addComponent(textArea {
        text = "Some other text"
        decorations { +box(boxType = BoxType.DOUBLE); +shadow() }
        preferredSize = Size(13, 7)
        position = Position(2, 8)
    })
    panel.addComponent(textArea {
        text = "Some other text"
        decorations { +box(boxType = BoxType.DOUBLE); +shadow() }
        preferredSize = Size(13, 7)
        position = Position(2, 8)
    })
    root.addComponent(label {
        +"Numbers only!"
        decorations { +box() }
        alignment = positionalAlignment(Position(2, 17))
    })
    val boundLabel = label {
        +""
        preferredSize = Size(13, 3)
        decorations { +box() }
        position = Position(2, 27)
    }
    val numLabel = label { +"Also 256 Max!"; position = Position(2, 25) }
    val hbox = hBox {
        preferredSize = Size(18, 4)
        spacing = 0
        position = Position(2, 20)
        decorations { +box(); +shadow() }
    }
    val numberInput = horizontalNumberInput {
        preferredSize = Size(13, 1)
        initialValue = 0
        minValue = 14
        maxValue = 256
    }
    boundLabel.textProperty.updateFrom(numberInput.currentValueProperty) { it.toString() }
    val decrementButton = button {
        +"${Symbols.TRIANGLE_DOWN_POINTING_BLACK}"
        preferredSize = Size(1, 1)
        decorations { }
    }.apply { onActivated { numberInput.decrementCurrentValue() } }
    val incrementButton = button {
        +"${Symbols.TRIANGLE_UP_POINTING_BLACK}"
        preferredSize = Size(1, 1)
        decorations { }
    }.apply { onActivated { numberInput.incrementCurrentValue() } }
    hbox.addComponent(decrementButton)
    hbox.addComponent(numberInput)
    hbox.addComponent(incrementButton)
    root.addComponent(hbox)
    root.addComponent(numLabel)
    root.addComponent(boundLabel)
    ExampleView(root)
}
