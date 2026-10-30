package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.extensions.addComponents
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import kotlin.random.Random

/** Vertical boxes that grow as buttons are added to a random one and shrink as buttons remove themselves. */
public val vBoxesExample: Example = Example("VBoxes") { context ->
    var count = 0
    val random = Random(3)
    val root = vBox { preferredSize = context.contentSize; spacing = 1; componentRenderer = NoOpComponentRenderer() }
    val addNew = button { +"Add New Button" }
    val container = hBox {
        preferredSize = Size(context.contentSize.width - 1, context.contentSize.height - 2)
        componentRenderer = NoOpComponentRenderer()
        spacing = 1
    }
    val columnSize = Size(14, container.contentSize.height)
    val defaultBox = vBox { preferredSize = columnSize }
    val boxedBox = vBox { preferredSize = columnSize; decorations { +box(BoxType.SINGLE, "Boxed VBox") } }
    val doubleBox = vBox { preferredSize = columnSize; decorations { +box(BoxType.DOUBLE) } }
    val shadowedBox = vBox { preferredSize = columnSize; decorations { +shadow() } }
    val buttonContainers = listOf(defaultBox, doubleBox, boxedBox, shadowedBox)
    root.addComponent(addNew)
    container.addComponents(defaultBox, boxedBox, doubleBox, shadowedBox)
    root.addComponent(container)

    fun addButton(box: VBox) {
        val attachment = box.addComponent(button { +"Remove: $count"; preferredSize = Size(12, 1) })
        attachment.onActivated { attachment.detach() }
        count++
    }
    addNew.onActivated { addButton(buttonContainers[random.nextInt(4)]) }
    buttonContainers.forEach { addButton(it) }
    ExampleView(root)
}
