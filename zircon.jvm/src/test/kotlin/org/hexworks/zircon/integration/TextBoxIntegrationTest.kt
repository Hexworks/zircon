package org.hexworks.zircon.integration

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.screen.Screen

class TextBoxIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        val panel = Components.panel()
                .wrapWithBox(true)
                .withSize(Sizes.create(28, 28))
                .withPosition(Positions.create(30, 1))
                .build()
        screen.addComponent(panel)

        screen.addComponent(Components.textBox()
                .withPosition(Positions.create(2, 2))
                .withContentWidth(26)
                .addHeader("Header!")
                .addParagraph("This is a plain text box.")
                .addNewLine()
                .addListItem("This is a list item")
                .addListItem("And another list item")
                .addNewLine()
                .addInlineText("Inline text ")
                .addInlineComponent(Components.button()
                        .wrapSides(false)
                        .withText("Button")
                        .build())
                .addInlineText(" text")
                .commitInlineElements()
                .addNewLine()
                .addParagraph("And a multi-line paragraph with typewriter effect...", withTypingEffectSpeedInMs = 200))
        panel.addComponent(Components.textBox()
                .withPosition(Positions.zero())
                .withContentWidth(26)
                .addHeader("Header!")
                .addParagraph("This is a plain text box.")
                .addNewLine()
                .addListItem("This is a list item")
                .addListItem("And another list item")
                .addNewLine()
                .addInlineText("Inline text ")
                .addInlineComponent(Components.button()
                        .wrapSides(false)
                        .withText("Button")
                        .build())
                .addInlineText(" text")
                .commitInlineElements()
                .addNewLine()
                .addParagraph("And a multi-line paragraph with typewriter effect..."))

        screen.addComponent(Components.textBox()
                .withPosition(Positions.create(1, 17))
                .withContentWidth(22)
                .addHeader("Decorated!")
                .wrapWithBox(true)
                .wrapWithShadow(true)
                .addParagraph("This is a paragraph which won't fit on one line."))
        panel.addComponent(Components.textBox()
                .withPosition(Positions.create(0, 15))
                .withContentWidth(22)
                .wrapWithBox(true)
                .wrapWithShadow(true)
                .addHeader("Decorated!")
                .addParagraph("This is a paragraph which won't fit on one line."))
    }

}
