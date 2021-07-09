package org.hexworks.zircon.integration

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.halfBlock
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.graphics.BoxType.SINGLE
import org.hexworks.zircon.api.screen.Screen

class LabelsIntegrationTest : ComponentIntegrationTestBase() {


    override fun buildScreenContent(screen: Screen) {
        screen.addComponent(
            Components.label()
                .withText("Foobar")
                .withDecorations(shadow())
                .withPosition(2, 2)
                .build()
        )

        screen.addComponent(
            Components.label()
                .withText("Barbaz wombat")
                .withPreferredSize(5, 2)
                .withPosition(2, 6)
                .build()
        )

        screen.addComponent(
            Components.label()
                .withText("Qux")
                .withDecorations(box(), shadow())
                .withPosition(2, 10)
                .build()
        )

        screen.addComponent(
            Components.label()
                .withText("Qux")
                .withDecorations(box(DOUBLE), shadow())
                .withPosition(15, 2)
                .build()
        )

        screen.addComponent(
            Components.label()
                .withText("Wtf")
                .withDecorations(box(SINGLE), box(DOUBLE), shadow())
                .withPosition(15, 7)
                .build()
        )

        screen.addComponent(
            Components.label()
                .withText("Wtf")
                .withDecorations(halfBlock(), shadow())
                .withPosition(15, 14)
                .build()
        )
    }
}
