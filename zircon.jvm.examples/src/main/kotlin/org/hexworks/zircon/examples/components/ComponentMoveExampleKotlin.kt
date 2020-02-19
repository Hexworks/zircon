package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components.button
import org.hexworks.zircon.api.Components.panel
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Position.Companion.create
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin
import kotlin.concurrent.thread

class ComponentMoveExampleKotlin : OneColumnComponentExampleKotlin() {

    override fun build(box: VBox) {

        val panel = panel()
                .withSize(20, 10)
                .withDecorations(box())
                .build()
        val innerPanel = panel()
                .withSize(10, 5)
                .withDecorations(box())
                .build()
        innerPanel.addComponent(button()
                .withText("Foo")
                .withPosition(1, 1)
                .build())

        panel.addComponent(innerPanel)
        box.addComponent(panel)

        thread {
            Thread.sleep(2000)
            panel.moveBy(create(5, 5))
            Thread.sleep(2000)
            innerPanel.moveBy(create(2, 2))
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            ComponentMoveExampleKotlin().show("Moving a Component")
        }
    }
}