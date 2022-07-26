package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.border
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components.button
import org.hexworks.zircon.api.Components.hbox
import org.hexworks.zircon.api.Functions.fromConsumer
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.modifier.Border.Companion.newBuilder
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.examples.base.OneColumnComponentExample
import java.util.*
import java.util.function.Consumer

object HBoxesExample : OneColumnComponentExample() {
    private var count = 0
    private val random = Random()

    @JvmStatic
    fun main(args: Array<String>) {
        HBoxesExample.show("HBoxes Example")
    }

    override fun build(box: VBox) {
        val addNew = button()
            .withText("Add New Button")
            .build()
        val defaultBox = hbox().withPreferredSize(box.size.width, 3).build()
        val boxedBox = hbox().withPreferredSize(box.width, 5).withDecorations(box(BoxType.SINGLE, "Boxed HBox")).build()
        val borderedBox = hbox().withPreferredSize(box.width, 5).withDecorations(border(newBuilder().build())).build()
        val shadowedBox = hbox().withPreferredSize(box.width, 5).withDecorations(shadow()).build()
        val buttonContainers = Arrays.asList(defaultBox, borderedBox, boxedBox, shadowedBox)
        box.addComponents(addNew, defaultBox, boxedBox, borderedBox, shadowedBox)
        addNew.onActivated(fromConsumer(Consumer { componentEvent: ComponentEvent? ->
            addButton(
                buttonContainers[random.nextInt(4)]
            )
        }))
        buttonContainers.forEach(Consumer { box: HBox ->
            addButton(
                box
            )
        })
    }

    private fun addButton(box: HBox) {
        val attachment = box.addComponent(
            button()
                .withText(String.format("Remove: %d", count))
                .withPreferredSize(12, 1)
                .build()
        )
        attachment.onActivated(fromConsumer(Consumer { componentEvent: ComponentEvent? -> attachment.detach() }))
        count++
    }
}