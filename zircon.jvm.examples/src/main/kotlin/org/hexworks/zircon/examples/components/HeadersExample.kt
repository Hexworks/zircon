package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components.header
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.examples.base.TwoColumnComponentExample

object HeadersExample : TwoColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        HeadersExample.show("Headers Example")
    }

    override fun build(box: VBox) {
        box.addComponent(header().withText("Default"))
        box.addComponent(header().withText("Boxed").withDecorations(box()))
        box.addComponent(header().withText("Shadowed").withDecorations(shadow()))
        val disabled = header().withText("Disabled").build()
        box.addComponent(disabled)
        disabled.isDisabled = true
    }
}