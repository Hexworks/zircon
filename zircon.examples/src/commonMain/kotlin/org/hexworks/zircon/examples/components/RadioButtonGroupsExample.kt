package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.radioButton
import org.hexworks.zircon.api.builder.component.radioButtonGroup
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.extensions.addComponents
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Two radio button groups whose members are spread over two boxes: a group is not a container. */
public val radioButtonGroupsExample: Example = Example("Radio groups") { context ->
    val root = panel { preferredSize = context.contentSize }
    val leftBox = vBox {
        position = Position(5, 5)
        preferredSize = Size(20, 15)
        decorations { +box() }
    }
    val rightBox = vBox {
        position = Position(35, 5)
        preferredSize = Size(20, 15)
        decorations { +box() }
    }
    val group0 = radioButtonGroup { }
    val group1 = radioButtonGroup { }
    val btn0 = radioButton { key = "0"; +"Button 0" }
    val btn1 = radioButton { key = "1"; +"Button 1" }
    val btn2 = radioButton { key = "2"; +"Button 2" }
    val btnA = radioButton { key = "A"; +"Button A" }
    val btnB = radioButton { key = "B"; +"Button B" }
    val btnC = radioButton { key = "C"; +"Button C" }
    leftBox.addComponents(btn0, btnA, btn1)
    rightBox.addComponents(btn2, btnB, btnC)
    group0.addComponents(btn0, btn1, btn2)
    group1.addComponents(btnA, btnB, btnC)
    root.addComponents(leftBox, rightBox)
    ExampleView(root)
}
