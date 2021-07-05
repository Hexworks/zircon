package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentAlignments
import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.DebugConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildPanel
import org.hexworks.zircon.api.dsl.component.panel
import org.hexworks.zircon.api.extensions.toScreen

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {
        SwingApplications.startTileGrid().toScreen().apply {

            val screen = this

            val panel = Components.panel()
                    .withDecorations(box())
                    .withPreferredSize(28, 28)
                    .withAlignment(ComponentAlignments.positionalAlignment(30, 1))
                    .build()
            screen.addComponent(panel)

            screen.addComponent(Components.textBox(26)
                    .withAlignment(ComponentAlignments.positionalAlignment(2, 2))
                    .addHeader("Header!")
                    .addParagraph("This is a plain text box.")
                    .addNewLine()
                    .addListItem("This is a list item")
                    .addListItem("And another list item")
                    .addNewLine()
                    .addInlineText("Inline text ")
                    .addInlineComponent(Components.button()
                            .withText("Button")
                            .build())
                    .addInlineText(" text")
                    .commitInlineElements()
                    .addNewLine()
                    .addParagraph("And a multi-line paragraph with typewriter effect...", withTypingEffectSpeedInMs = 200))

            panel.addComponent(Components.textBox(26)
                    .withAlignment(ComponentAlignments.positionalAlignment(Position.zero()))
                    .addHeader("Header!")
                    .addParagraph("This is a plain text box.")
                    .addNewLine()
                    .addListItem("This is a list item")
                    .addListItem("And another list item")
                    .addNewLine()
                    .addInlineText("Inline text ")
                    .addInlineComponent(Components.button()
                            .withText("Button")
                            .build())
                    .addInlineText(" text")
                    .commitInlineElements()
                    .addNewLine()
                    .addParagraph("And a multi-line paragraph with typewriter effect..."))

            screen.addComponent(Components.textBox(22)
                    .withAlignment(ComponentAlignments.positionalAlignment(1, 18))
                    .addHeader("Decorated!")
                    .withDecorations(box(), ComponentDecorations.shadow())
                    .addParagraph("This is a paragraph which won't fit on one line."))

            panel.addComponent(Components.textBox(22)
                    .withAlignment(ComponentAlignments.positionalAlignment(0, 16))
                    .withDecorations(box(), ComponentDecorations.shadow())
                    .addHeader("Decorated!")
                    .addParagraph("This is a paragraph which won't fit on one line."))

            theme = ColorThemes.ammo()
            display()
        }
    }

}

