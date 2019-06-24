package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow

object TextBoxesExample {

    private val theme = ColorThemes.oliveLeafTea()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withDecorations(box())
                .withSize(Sizes.create(28, 28))
                .withAlignment(positionalAlignment(Positions.create(30, 1)))
                .build()
        screen.addComponent(panel)

        screen.addComponent(Components.textBox(26)
                .withAlignment(positionalAlignment(Positions.create(2, 2)))
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
                .withAlignment(positionalAlignment(Positions.zero()))
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

        screen.addComponent(Components.textBox(22)
                .withAlignment(positionalAlignment(Positions.create(1, 17)))
                .addHeader("Decorated!")
                .withDecorations(box(), shadow())
                .addParagraph("This is a paragraph which won't fit on one line."))
        panel.addComponent(Components.textBox(22)
                .withAlignment(positionalAlignment(Positions.create(0, 15)))
                .withDecorations(box(), shadow())
                .addHeader("Decorated!")
                .addParagraph("This is a paragraph which won't fit on one line."))

        screen.display()
        screen.applyColorTheme(theme)
    }

}
