package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.extensions.toScreen

object RadioButtonGroupsExample {

    private val THEME = ColorThemes.ghostOfAChance()
    private val TILESET = CP437TilesetResources.cla18x18()

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(Size.create(60, 30))
                .build()
        ).toScreen().apply {
            theme = THEME
            display()
        }

        val leftBox = Components.vbox()
            .withPosition(5, 5)
            .withPreferredSize(20, 15)
            .withDecorations(box())
            .build()

        val rightBox = Components.vbox()
            .withPosition(35, 5)
            .withPreferredSize(20, 15)
            .withDecorations(box())
            .build()

        val group0 = Components.radioButtonGroup().build()
        val group1 = Components.radioButtonGroup().build()

        val btn0 = Components.radioButton()
            .withKey("0")
            .withText("Button 0")
            .build()

        val btn1 = Components.radioButton()
            .withKey("1")
            .withText("Button 1")
            .build()

        val btn2 = Components.radioButton()
            .withKey("2")
            .withText("Button 2")
            .build()

        val btnA = Components.radioButton()
            .withKey("A")
            .withText("Button A")
            .build()

        val btnB = Components.radioButton()
            .withKey("B")
            .withText("Button B")
            .build()

        val btnC = Components.radioButton()
            .withKey("C")
            .withText("Button C")
            .build()

        leftBox.addComponents(btn0, btnA, btn1)
        rightBox.addComponents(btn2, btnB, btnC)

        group0.addComponents(btn0, btn1, btn2)
        group1.addComponents(btnA, btnB, btnC)

        screen.addComponents(leftBox, rightBox)

    }

}
