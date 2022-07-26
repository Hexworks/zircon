package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components.button
import org.hexworks.zircon.api.Components.textBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.examples.base.TwoColumnComponentExample

object TextBoxesExample : TwoColumnComponentExample() {

    private val count = 0

    @JvmStatic
    fun main(args: Array<String>) {
        TextBoxesExample.show("Text Boxes Example")
    }

    override fun build(box: VBox) {
        box.addComponent(
            textBox(26)
                .addHeader("Header!")
                .addParagraph("This is a plain text box.")
                .addNewLine()
                .addListItem("This is a list item")
                .addListItem("And another list item")
                .addNewLine()
                .addInlineText("Inline text ")
                .addInlineComponent(
                    button()
                        .withText("Button")
                        .build()
                )
                .addInlineText(" text")
                .commitInlineElements()
                .addNewLine()
                .addParagraph("And a multi-line paragraph with typewriter effect...", false, 200)
        )
        box.addComponent(
            textBox(22)
                .addHeader("Decorated!")
                .withDecorations(box(), shadow())
                .addParagraph("This is a paragraph which won't fit on one line.")
        )
    }
}