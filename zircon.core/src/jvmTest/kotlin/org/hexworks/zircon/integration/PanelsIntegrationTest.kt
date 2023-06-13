package org.hexworks.zircon.integration

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class PanelsIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        screen.addComponent(
            Components.panel()
                .withDecorations(box())
                .withPreferredSize(18, 5)
                .withPosition(1, 1)
        )

        screen.addComponent(
            Components.panel()
                .withDecorations(shadow())
                .withPreferredSize(18, 5)
                .withPosition(1, 8)
        )

        screen.addComponent(
            Components.panel()
                .withDecorations(box(), shadow())
                .withPreferredSize(18, 5)
                .withPosition(1, 15)
        )

        screen.addComponent(
            Components.panel()
                .withDecorations(box(BoxType.DOUBLE))
                .withPreferredSize(18, 5)
                .withPosition(1, 22)
        )

        screen.addComponent(
            Components.panel()
                .withDecorations(box(BoxType.BASIC))
                .withPreferredSize(18, 5)
                .withPosition(21, 1)
        )

        screen.addComponent(
            Components.panel()
                .withDecorations(box(title = "Qux"))
                .withPreferredSize(18, 5)
                .withPosition(21, 8)
        )

        screen.addComponent(
            Components.panel()
                .withDecorations(shadow())
                .withPreferredSize(18, 5)
                .withPosition(21, 15)
        )

        screen.addComponent(
            Components.panel()
                .withPreferredSize(18, 5)
                .withDecorations(box(BoxType.TOP_BOTTOM_DOUBLE, "Wombat"))
                .withPosition(21, 22)
        )

        screen.addComponent(
            Components.panel()
                .withPreferredSize(18, 5)
                .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE))
                .withPosition(41, 1)
        )

        val panel = Components.panel()
            .withPreferredSize(18, 19)
            .withDecorations(box(title = "Parent"))
            .withPosition(41, 8)
            .build()

        screen.addComponent(panel)

        val nested0 = Components.panel()
            .withPreferredSize(14, 15)
            .withPosition(1, 1)
            .withDecorations(box(BoxType.DOUBLE, "Nested 0"))
            .build()

        val nested1 = Components.panel()
            .withPreferredSize(10, 11)
            .withPosition(1, 1)
            .withDecorations(box(BoxType.DOUBLE, "Nested 1"))
            .build()

        panel.addComponent(nested0)
        nested0.addComponent(nested1)
    }
}
