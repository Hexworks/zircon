package org.hexworks.zircon.integration

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.screen.Screen

class LogAreaIntegrationTest : ComponentIntegrationTestBase(size = Size.create(70, 30)) {

    override fun buildScreenContent(screen: Screen) {
        val panel = Components.panel()
            .withDecorations(box(title = "Log"))
            .withPreferredSize(60, 25)
            .build()

        screen.addComponent(panel)
        val logArea = Components.logArea()
            .withSize(Size.create(58, 23))
            .build()

        logArea.addParagraph("This is a simple log row")
        logArea.addParagraph("This is a further log row with a modifier")
        logArea.addNewRows(2)

        logArea.addInlineText("This is a log row with a ")
        val btn = Components.button()
            .withDecorations()
            .withText("Button")
            .build()
        logArea.addInlineComponent(btn)
        logArea.commitInlineElements()

        logArea.addNewRows(2)
        logArea.addParagraph("This is a long log row, which gets wrapped, since it is long")

        panel.addComponent(logArea)
    }
}
