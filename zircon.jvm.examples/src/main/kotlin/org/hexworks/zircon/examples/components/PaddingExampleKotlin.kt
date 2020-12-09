package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.border
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.padding
import org.hexworks.zircon.api.Components.header
import org.hexworks.zircon.api.Components.panel
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.examples.base.TwoColumnComponentExampleKotlin
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

class PaddingExampleKotlin : TwoColumnComponentExampleKotlin() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            PaddingExampleKotlin().show("Padding Example")
        }
    }

    override fun build(box: VBox) {
        box.addComponent(header().withText("Panels with padding"))

        val panelWidth = 26
        val panelHeight = 8

        val p0 = panel().withSize(panelWidth, panelHeight)
            .withDecorations(border())
            .withComponentRenderer(NoOpComponentRenderer())
            .build()

        box.addComponent(p0)
        p0.addComponent(
            panel()
                .withSize(panelWidth, panelHeight)
                .withDecorations(box(title = "Padding 1"), padding(1))
        )

        val p1 = panel().withSize(panelWidth, panelHeight)
            .withDecorations(border())
            .withComponentRenderer(NoOpComponentRenderer())
            .build()

        box.addComponent(p1)
        p1.addComponent(
            panel()
                .withSize(panelWidth, panelHeight)
                .withDecorations(box(title = "Padding 1, 2"), padding(1, 2))
        )

        val p2 = panel().withSize(panelWidth, panelHeight)
            .withDecorations(border())
            .withComponentRenderer(NoOpComponentRenderer())
            .build()

        box.addComponent(p2)
        p2.addComponent(
            panel()
                .withSize(panelWidth, panelHeight)
                .withDecorations(box(title = "Padding 0, 1, 2, 3"), padding(0, 1, 2, 3))
        )
    }
}
