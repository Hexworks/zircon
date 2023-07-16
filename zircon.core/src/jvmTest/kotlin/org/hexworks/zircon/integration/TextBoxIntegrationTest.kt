package org.hexworks.zircon.integration

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.screen.Screen

class TextBoxIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        val panel = Components.panel()
            .withDecorations(box())
            .withPreferredSize(28, 28)
            .withAlignment(positionalAlignment(30, 1))
            .build()
        screen.addComponent(panel)

        screen.addComponent(
            Components.textBox(26)
                .withAlignment(positionalAlignment(2, 2))
                .addHeader("Header!")
                .addParagraph("This is a plain text box.")
                .addNewLine()
                .addListItem("This is a list item")
                .addListItem("And another list item")
                .addNewLine()
                .addInlineText("Inline text ")
                .addInlineComponent(
                    Components.button()
                        .withText("Button")
                        .build()
                )
                .addInlineText(" text")
                .commitInlineElements()
                .addNewLine()
                .addParagraph("And a multi-line paragraph with typewriter effect...", withTypingEffectSpeedInMs = 200)
        )
        panel.addComponent(
            Components.textBox(26)
                .withAlignment(positionalAlignment(Position.zero()))
                .addHeader("Header!")
                .addParagraph("This is a plain text box.")
                .addNewLine()
                .addListItem("This is a list item")
                .addListItem("And another list item")
                .addNewLine()
                .addInlineText("Inline text ")
                .addInlineComponent(
                    Components.button()
                        .withText("Button")
                        .build()
                )
                .addInlineText(" text")
                .commitInlineElements()
                .addNewLine()
                .addParagraph("And a multi-line paragraph with typewriter effect...")
        )

        screen.addComponent(
            Components.textBox(22)
                .withAlignment(positionalAlignment(1, 18))
                .addHeader("Decorated!")
                .withDecorations(box(), shadow())
                .addParagraph("This is a paragraph which won't fit on one line.")
        )
        panel.addComponent(
            Components.textBox(22)
                .withAlignment(positionalAlignment(0, 16))
                .withDecorations(box(), shadow())
                .addHeader("Decorated!")
                .addParagraph("This is a paragraph which won't fit on one line.")
        )
    }

}
