@file:Suppress("UNUSED_VARIABLE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.onKeyboardEvent
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Processed

object KotlinPlayground {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.rogueYun16x16()
    val viewPanel = Components.panel()
            .wrapWithBox(true)
            .withSize(24, 20)
            .withPosition(0, 6)
            .build()

    private val UnitXPos: Int = 15
    val ValueTabXPos = 10

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(60, 30)
                .build())

        val screen = Screens.createScreenFor(tileGrid)
        screen.addComponent(viewPanel)

        val unitHeadingTextArea = Components.textArea()
                .withText("012")
                .withPosition(Position.create(ValueTabXPos, 0))
                .withSize(3, 1)
                .build()

        unitHeadingTextArea.onKeyboardEvent(KeyboardEventType.KEY_PRESSED) { event, phase ->
            //press Enter
            if (event.code == KeyCode.ENTER) {
                unitHeadingTextArea.isDisabled = true
                unitHeadingTextArea.clearFocus()
            }
            Processed
        }
        //unitHeadingTextArea.onInput(F10InputRemoverInputListener(viewContext, this, unitHeadingTextArea) { ownSubComponent.currentHeading.displayName() })
        viewPanel.addComponent(unitHeadingTextArea)


        screen.display()
        unitHeadingTextArea.isEnabled = true
        unitHeadingTextArea.requestFocus()
    }
}
