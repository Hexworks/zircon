package org.hexworks.zircon.examples.docs

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.animation.AnimationResource
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.examples.AnimationExample

object Intro {

    val tileset = CP437TilesetResources.wanderlust16x16()
    val screenSize = Sizes.create(65, 33)
    val sep = System.lineSeparator()

    @JvmStatic
    fun main(args: Array<String>) {
        val grid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(CP437TilesetResources.wanderlust16x16())
                .defaultSize(screenSize)
                .enableBetaFeatures()
                .title("Zircon")
                .build())

        val introScreen = Screens.createScreenFor(grid)

        val animBuilder = AnimationResource.loadAnimationFromStream(
                AnimationExample::class.java.getResourceAsStream("/animations/skull.zap"),
                tileset).loopCount(0)
        for (i in 0 until animBuilder.getTotalFrameCount()) {
            animBuilder.addPosition(Positions.create(2, 5))
        }
        val zirconSplash = animBuilder.build()

        val splashPanel = Components.panel()
                .withSize(screenSize.withWidth(17))
                .withBoxType(BoxType.SINGLE)
                .wrapWithBox(true)
                .build()
        val introPanel = Components.panel()
                .withSize(screenSize.withRelativeWidth(-17))
                .withPosition(Positions.create(17, 0))
                .withTitle("Zircon: fiendishly simple text GUI")
                .withBoxType(BoxType.SINGLE)
                .wrapWithBox(true)
                .build()

        introPanel.addComponent(Components.header()
                .withPosition(Positions.offset1x1())
                .text("Do you plan to make a roguelike?")
                .build())

        val introBox = Components.textBox()
                .withPosition(Positions.create(1, 3))
                .contentWidth(45)
                .paragraph("Look no further. Zircon is the right tool for the job.")
                .paragraph("Zircon is a Text GUI library and a Tile Engine which is designed for simplicity and ease of use.")
                .paragraph("It is usable out of the box for all JVM languages including Java, Kotlin, Clojure and Scala.")
                .paragraph("Things Zircon knows:")
                .listItem("Animations")
                .listItem("A Component System with built-in components for games")
                .listItem("Layering")
                .listItem("Mouse and keyboard support")
                .listItem("Shape and Box drawing")
                .listItem("Tilesets, and Graphical tiles")
                .listItem("REXPaint file loading")
                .listItem("Color Themes and more!")
                .newLine()
                .paragraph("Interested in more details? Read on...")
                .build()

        val nextButton = Components.button()
                .withPosition(Positions.bottomLeftOf(introBox))
                .text("Next")
                .build()

        introPanel.addComponent(introBox)
        introPanel.addComponent(nextButton)

        introScreen.addComponent(splashPanel)
        introScreen.addComponent(introPanel)

        introPanel.applyColorTheme(ColorThemeResource.TRON.getTheme())

        introScreen.startAnimation(zirconSplash)

        introScreen.display()
    }
}
