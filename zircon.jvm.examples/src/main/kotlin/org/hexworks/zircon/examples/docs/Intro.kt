package org.hexworks.zircon.examples.docs


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.animation.AnimationResource
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.examples.animations.HexworksSkullExampleJava

object Intro {

    val tileset = CP437TilesetResources.wanderlust16x16()
    private val screenSize = Size.create(65, 33)

    @JvmStatic
    fun main(args: Array<String>) {
        val grid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                .withSize(screenSize)
                .enableBetaFeatures()
                .withTitle("Zircon")
                .build())

        val introScreen = Screen.create(grid)

        val animBuilder = AnimationResource.loadAnimationFromStream(
                HexworksSkullExampleJava::class.java.getResourceAsStream("/animations/skull.zap"),
                tileset).withLoopCount(0)
        for (i in 0 until animBuilder.totalFrameCount) {
            animBuilder.addPosition(Position.create(2, 5))
        }
        val zirconSplash = animBuilder.build()

        val splashPanel = Components.panel()
                .withSize(screenSize.withWidth(17))
                .withDecorations(box(boxType = BoxType.SINGLE))
                .build()
        val introPanel = Components.panel()
                .withSize(screenSize.withRelativeWidth(-17))
                .withAlignment(positionalAlignment(17, 0))
                .withDecorations(box(boxType = BoxType.SINGLE, title = "Zircon: fiendishly simple text GUI"))
                .build()

        introPanel.addComponent(Components.header()
                .withAlignment(positionalAlignment(Position.offset1x1()))
                .withText("Do you plan to make a roguelike?")
                .build())

        val introBox = Components.textBox(45)
                .withAlignment(positionalAlignment(1, 3))
                .addParagraph("Look no further. Zircon is the right tool for the job.")
                .addParagraph("Zircon is a Text GUI library and a Tile Engine which is designed for simplicity and ease of use.")
                .addParagraph("It is usable out of the box for all JVM languages including Java, Kotlin, Clojure and Scala.")
                .addParagraph("Things Zircon knows:")
                .addListItem("Animations")
                .addListItem("A Component System with built-in components for games")
                .addListItem("Layering")
                .addListItem("Mouse and keyboard support")
                .addListItem("Shape and Box drawing")
                .addListItem("Tilesets, and Graphical tiles")
                .addListItem("REXPaint file loading")
                .addListItem("Color Themes and more!")
                .addNewLine()
                .addParagraph("Interested in more details? Read on...")
                .build()

        val nextButton = Components.button()
                .withAlignment(positionalAlignment(Position.bottomLeftOf(introBox)))
                .withText("Next")
                .build()

        introPanel.addComponent(introBox)
        introPanel.addComponent(nextButton)

        introScreen.addComponent(splashPanel)
        introScreen.addComponent(introPanel)

        introPanel.theme = ColorThemes.tron()

        introScreen.start(zirconSplash)

        introScreen.display()
    }
}
