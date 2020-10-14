package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.halfBlock
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.Components.button
import org.hexworks.zircon.api.Functions.fromConsumer
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.examples.base.TwoColumnComponentExampleKotlin
import java.util.function.Consumer

class ButtonsExampleKotlin : TwoColumnComponentExampleKotlin() {
    override fun build(box: VBox) {
        val invisible = button()
                .withText("Click Me")
                .withDecorations(side())
                .build()
        invisible.processComponentEvents(ComponentEventType.ACTIVATED, fromConsumer(Consumer { invisible.isHidden = true }))
        val disabled = button()
                .withText("Disabled")
                .build()
        box.addComponents(
                button()
                        .withText("Default")
                        .build(),
                button()
                        .withText("Boxed")
                        .withDecorations(box())
                        .build(),
                button()
                        .withText("Too long name for button")
                        .withDecorations(box(), shadow())
                        .withSize(10, 4)
                        .build(),
                button()
                        .withText("Half Block")
                        .withDecorations(halfBlock(), shadow())
                        .build(),
                invisible, disabled)
        disabled.isDisabled = true
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ButtonsExampleKotlin().show("Buttons Example")
        }
    }
}
