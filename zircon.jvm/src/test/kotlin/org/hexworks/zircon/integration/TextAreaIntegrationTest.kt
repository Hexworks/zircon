package org.hexworks.zircon.integration

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class TextAreaIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        val panel = Components.panel()
                .wrapWithBox(true)
                .withSize(Sizes.create(28, 28))
                .withPosition(Positions.create(31, 1))
                .build()
        screen.addComponent(panel)

        screen.addComponent(Components.textArea()
                .withText("Some text")
                .withSize(Sizes.create(13, 5))
                .withPosition(Positions.create(2, 2)))
        panel.addComponent(Components.textArea()
                .withText("Some text")
                .withSize(Sizes.create(13, 5))
                .withPosition(Positions.create(2, 2)))

        screen.addComponent(Components.textArea()
                .withText("Some other text")
                .withBoxType(BoxType.DOUBLE)
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .withSize(Sizes.create(13, 7))
                .withPosition(Positions.create(2, 8)))
        panel.addComponent(Components.textArea()
                .withText("Some other text")
                .withBoxType(BoxType.DOUBLE)
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .withSize(Sizes.create(13, 7))
                .withPosition(Positions.create(2, 8)))
    }

}
