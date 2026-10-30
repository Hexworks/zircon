package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.behavior.extensions.width
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import kotlin.random.Random

/** HBoxes with different decorations; buttons are added to a random one and remove themselves. */
public val hBoxesExample: Example = Example("HBoxes") { context ->
    var count = 0
    val random = Random(1)
    fun addButton(box: HBox) {
        val attachment = box.addComponent(button {
            +"Remove: $count"
            preferredSize = Size(12, 1)
        })
        attachment.onActivated { attachment.detach() }
        count++
    }
    val root = context.oneColumn { box ->
        val addNew = button { +"Add New Button" }
        val defaultBox = hBox { preferredSize = Size(box.size.width, 3) }
        val boxedBox = hBox {
            preferredSize = Size(box.width, 5)
            decorations { +box(BoxType.SINGLE, "Boxed HBox") }
        }
        // 2.0 has no border decoration; a margin stands in for it.
        val marginBox = hBox {
            preferredSize = Size(box.width, 5)
            decorations { +margin(1) }
        }
        val shadowedBox = hBox {
            preferredSize = Size(box.width, 5)
            decorations { +shadow() }
        }
        val buttonContainers = listOf(defaultBox, marginBox, boxedBox, shadowedBox)
        listOf(addNew, defaultBox, boxedBox, marginBox, shadowedBox).forEach { box.addComponent(it) }
        addNew.onActivated { addButton(buttonContainers[random.nextInt(4)]) }
        buttonContainers.forEach { addButton(it) }
    }
    ExampleView(root)
}
