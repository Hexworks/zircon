package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.border
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components.button
import org.hexworks.zircon.api.Components.hbox
import org.hexworks.zircon.api.Components.vbox
import org.hexworks.zircon.api.Functions.fromConsumer
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size.Companion.create
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.modifier.Border.Companion.newBuilder
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.examples.base.OneColumnComponentExample
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import java.util.*
import java.util.function.Consumer

object VBoxesExample : OneColumnComponentExample() {

    private var count = 0
    private val random = Random()

    @JvmStatic
    fun main(args: Array<String>) {
        VBoxesExample.show("VBoxes Example")
    }

    override fun build(box: VBox) {
        val addNew = button()
            .withText("Add New Button")
            .build()
        val container = hbox()
            .withPreferredSize(box.contentSize.minus(create(1, 2)))
            .withComponentRenderer(NoOpComponentRenderer())
            .withSpacing(1)
            .build()
        val defaultBox = vbox().withPreferredSize(container.contentSize.withWidth(14)).build()
        val boxedBox = vbox().withPreferredSize(container.contentSize.withWidth(14))
            .withDecorations(box(BoxType.SINGLE, "Boxed VBox")).build()
        val borderedBox =
            vbox().withPreferredSize(container.contentSize.withWidth(14)).withDecorations(border(newBuilder().build()))
                .build()
        val shadowedBox =
            vbox().withPreferredSize(container.contentSize.withWidth(14)).withDecorations(shadow()).build()
        val buttonContainers = Arrays.asList(defaultBox, borderedBox, boxedBox, shadowedBox)
        box.addComponent(addNew)
        container.addComponents(defaultBox, boxedBox, borderedBox, shadowedBox)
        box.addComponent(container)
        addNew.onActivated(fromConsumer(Consumer { componentEvent: ComponentEvent? ->
            addButton(
                buttonContainers[random.nextInt(4)]
            )
        }))
        buttonContainers.forEach(Consumer { box: VBox ->
            addButton(
                box
            )
        })
    }

    private fun addButton(box: VBox) {
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