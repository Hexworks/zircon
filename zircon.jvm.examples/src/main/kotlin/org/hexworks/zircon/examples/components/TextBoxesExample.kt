package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*

object TextBoxesExample {

    private val theme = ColorThemes.oliveLeafTea()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = LibgdxApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

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
                .addParagraph("And a multi-line paragraph with typewriter effect...", withTypingEffectSpeedInMs = 200))

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

        screen.display()
        screen.applyColorTheme(theme)
    }

}
