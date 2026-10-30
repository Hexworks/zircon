package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.checkBox
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.horizontalSlider
import org.hexworks.zircon.api.builder.component.progressBar
import org.hexworks.zircon.api.builder.component.radioButton
import org.hexworks.zircon.api.builder.component.radioButtonGroup
import org.hexworks.zircon.api.builder.component.toggleButton
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** The same components with interactive and non-interactive decorations side by side. */
public val decorationsExample: Example = Example("Decorations") { context ->
    val root = context.oneColumn { box ->
        val columns = hBox { preferredSize = box.contentSize }
        box.addComponent(columns)
        val half = columns.contentSize.width / 2
        val interactive = vBox {
            preferredSize = Size(half, columns.height)
            spacing = 1
            decorations { +box(BoxType.SINGLE, "Interactive") }
        }
        val nonInteractive = vBox {
            preferredSize = Size(half, columns.height)
            spacing = 1
            decorations { +box(BoxType.SINGLE, "Non-Interactive") }
        }
        columns.addComponent(interactive)
        columns.addComponent(nonInteractive)
        addDecorated(interactive, RenderingMode.INTERACTIVE)
        addDecorated(nonInteractive, RenderingMode.NON_INTERACTIVE)
    }
    ExampleView(root)
}

private fun addDecorated(box: VBox, renderingMode: RenderingMode) {
    box.addComponent(button {
        +"Click Me!"
        decorations { +side(LEFT_HALF_BLOCK, RIGHT_HALF_BLOCK, renderingMode) }
    })
    box.addComponent(checkBox {
        +"Check Me!"
        decorations { +box(BoxType.SINGLE, "", renderingMode) }
    })
    box.addComponent(toggleButton {
        +"Toggle Me!"
        decorations { +box(BoxType.BASIC, "", renderingMode) }
    })
    val a = radioButton {
        +"Press A!"
        decorations { +box(BoxType.DOUBLE, "", renderingMode) }
        key = "a"
    }
    box.addComponent(a)
    val b = radioButton {
        +"Press B!"
        decorations { +box(BoxType.DOUBLE, "", renderingMode) }
        key = "b"
    }
    box.addComponent(b)
    radioButtonGroup { radioButtons = listOf(a, b) }
    box.addComponent(horizontalSlider {
        minValue = 1
        maxValue = 100
        numberOfSteps = box.contentSize.width - 5
        decorations { +box(BoxType.TOP_BOTTOM_DOUBLE, "Slide Me", renderingMode) }
    })
    val bar = progressBar {
        displayPercentValueOfProgress = true
        numberOfSteps = box.contentSize.width - 4
        range = 100
        decorations { +box(BoxType.TOP_BOTTOM_DOUBLE, "I'm progressing", renderingMode) }
    }
    bar.progress = 42.0
    box.addComponent(bar)
}
