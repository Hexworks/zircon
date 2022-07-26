package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components.label
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.examples.base.TwoColumnComponentExample

object LabelsExample : TwoColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        LabelsExample.show("Labels Example")
    }

    override fun build(box: VBox) {
        box.addComponent(label().withText("Default"))
        box.addComponent(label().withText("Boxed").withDecorations(box()))
        box.addComponent(label().withText("Shadowed").withDecorations(shadow()))
        val disabled = label().withText("Disabled").build()
        box.addComponent(disabled)
        disabled.isDisabled = true
    }
}