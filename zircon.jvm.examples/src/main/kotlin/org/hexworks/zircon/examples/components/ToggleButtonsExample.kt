package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.halfBlock
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.Components.toggleButton
import org.hexworks.zircon.api.Functions.fromConsumer
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.examples.base.TwoColumnComponentExample
import java.util.function.Consumer



object ToggleButtonsExample : TwoColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        ToggleButtonsExample.show("Toggle Buttons Example")
    }

    override fun build(box: VBox) {
        val invisible = toggleButton()
            .withText("Click Me")
            .withDecorations(side())
            .build()
        invisible.processComponentEvents(
            ComponentEventType.ACTIVATED, fromConsumer(
                Consumer { event: ComponentEvent? ->
                    invisible.isHidden = true
                })
        )
        val disabled = toggleButton()
            .withText("Disabled")
            .build()
        box.addComponents(
            toggleButton()
                .withText("Default")
                .build(),
            toggleButton()
                .withText("Boxed")
                .withDecorations(box())
                .build(),
            toggleButton()
                .withText("Too long name for button")
                .withDecorations(box(), shadow())
                .withPreferredSize(10, 4)
                .build(),
            toggleButton()
                .withText("Half Block")
                .withDecorations(halfBlock(), shadow())
                .build(),
            invisible, disabled
        )
        disabled.isDisabled = true
    }
}