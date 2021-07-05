package org.hexworks.zircon.integration

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class TextAreaIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        val panel = Components.panel()
                .withDecorations(box())
                .withPreferredSize(28, 28)
                .withPosition(31, 1)
                .build()
        screen.addComponent(panel)

        screen.addComponent(Components.textArea()
                .withText("Some text")
                .withPreferredSize(13, 5)
                .withPosition(2, 2))
        panel.addComponent(Components.textArea()
                .withText("Some text")
                .withPreferredSize(13, 5)
                .withPosition(2, 2))

        screen.addComponent(Components.textArea()
                .withText("Some other text")
                .withDecorations(box(BoxType.DOUBLE), shadow())
                .withPreferredSize(13, 7)
                .withPosition(2, 8))
        panel.addComponent(Components.textArea()
                .withText("Some other text")
                .withDecorations(box(BoxType.DOUBLE), shadow())
                .withPreferredSize(13, 7)
                .withPosition(2, 8))
    }

}
