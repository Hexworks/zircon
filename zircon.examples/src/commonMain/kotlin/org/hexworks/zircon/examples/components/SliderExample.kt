package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentAlignments.alignmentAround
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.horizontalNumberInput
import org.hexworks.zircon.api.builder.component.horizontalSlider
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.verticalSlider
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Horizontal and vertical sliders, one with a bound label, one driven by buttons and a number input. */
public val sliderExample: Example = Example("Sliders") { context ->
    val root = panel { preferredSize = context.contentSize }
    val panel = panel {
        decorations { +box(title = "Slider on panel"); +shadow() }
        preferredSize = Size(30, 28)
        alignment = positionalAlignment(29, 1)
    }
    root.addComponent(panel)
    val slider1 = horizontalSlider {
        minValue = 7
        maxValue = 100
        numberOfSteps = 10
        decorations { +box() }
        alignment = positionalAlignment(0, 5)
    }
    val label = label {
        preferredSize = Size(5, 1)
        +"30"
        alignment = alignmentAround(slider1, ComponentAlignment.RIGHT_CENTER)
    }
    panel.addComponent(slider1)
    panel.addComponent(label)
    label.textProperty.updateFrom(slider1.currentValueProperty) { it.toString() }
    slider1.currentValue = 35

    val slider2 = horizontalSlider {
        maxValue = 3
        numberOfSteps = 3
        alignment = positionalAlignment(0, 10)
        decorations { +box(BoxType.TOP_BOTTOM_DOUBLE) }
    }
    slider2.currentValue = 2
    panel.addComponent(slider2)

    val composite = hBox {
        preferredSize = Size(17, 1)
        spacing = 0
        alignment = positionalAlignment(0, 15)
    }
    val slider3 = horizontalSlider {
        maxValue = 255
        numberOfSteps = 10
    }
    val decrementButton = button {
        +"${Symbols.TRIANGLE_LEFT_POINTING_BLACK}"
        preferredSize = Size(1, 1)
        decorations { }
    }.apply {
        processMouseEvents(MouseEventType.MOUSE_PRESSED) { _, _ -> slider3.decrementCurrentValue() }
        onActivated { slider3.decrementCurrentValue() }
    }
    val incrementButton = button {
        +"${Symbols.TRIANGLE_RIGHT_POINTING_BLACK}"
        preferredSize = Size(1, 1)
        decorations { }
    }.apply { onActivated { slider3.incrementCurrentValue() } }
    val numberInput = horizontalNumberInput {
        preferredSize = Size(3, 1)
        initialValue = 0
        maxValue = 255
    }
    slider3.currentValueProperty.bind(numberInput.currentValueProperty)
    composite.addComponent(decrementButton)
    composite.addComponent(slider3)
    composite.addComponent(incrementButton)
    composite.addComponent(numberInput)
    panel.addComponent(composite)
    slider3.currentValue = 127

    val slider4 = horizontalSlider {
        maxValue = 100
        numberOfSteps = 10
        alignment = positionalAlignment(0, 20)
    }
    slider4.currentValue = 20
    panel.addComponent(slider4)

    val vertical = verticalSlider {
        maxValue = 100
        numberOfSteps = 10
        alignment = positionalAlignment(25, 3)
    }
    vertical.currentValue = 10
    panel.addComponent(vertical)
    ExampleView(root)
}
