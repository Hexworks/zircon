package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.halfBlock
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.Components.checkBox
import org.hexworks.zircon.api.Functions.fromConsumer
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.examples.base.TwoColumnComponentExample
import java.util.function.Consumer

object CheckBoxesExample : TwoColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        CheckBoxesExample.show("Check Boxes Example")
    }

    override fun build(box: VBox) {
        val invisible = checkBox()
            .withText("Make me invisible")
            .withDecorations(side())
            .build()
        invisible.processComponentEvents(
            ComponentEventType.ACTIVATED, fromConsumer(
                Consumer { event: ComponentEvent? ->
                    invisible.isHidden = true
                })
        )
        val disabled = checkBox()
            .withText("Disabled Button")
            .build()
        box.addComponents(
            checkBox()
                .withText("Default")
                .build(),
            checkBox()
                .withText("Boxed")
                .withDecorations(box())
                .build(),
            checkBox()
                .withText("Too long name")
                .withDecorations(box(), shadow())
                .withPreferredSize(16, 4)
                .build(),
            checkBox()
                .withText("Half block")
                .withDecorations(halfBlock(), shadow())
                .build(),
            invisible, disabled
        )
        disabled.isDisabled = true
    }
}