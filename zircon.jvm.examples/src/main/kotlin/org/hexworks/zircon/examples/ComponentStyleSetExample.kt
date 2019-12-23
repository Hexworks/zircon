package org.hexworks.zircon.examples

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor.BLACK
import org.hexworks.zircon.api.color.ANSITileColor.CYAN
import org.hexworks.zircon.api.color.ANSITileColor.RED
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.extensions.side
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.graphics.StyleSet

object ComponentStyleSetExample {

    private val THEME = ColorThemes.gamebookers()

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = SwingApplications.startTileGrid().toScreen().apply {
            theme = THEME
            display()
        }

        val panel = Components.panel()
                .withDecorations(box(title = "Buttons on panel"), shadow())
                .withSize(30, 20)
                .withPosition(5, 5)
                .build()

        screen.addComponent(panel)

        val compStyleSet = ComponentStyleSetBuilder
                .newBuilder()
                .withDefaultStyle(StyleSet.create(CYAN, BLACK))
                .withActiveStyle(StyleSet.create(CYAN, BLACK))
                .withFocusedStyle(StyleSet.create(RED, BLACK))
                .build()


        val simpleBtn = Components.button()
                .withText("Button")
                .withDecorations(side())
                .withComponentStyleSet(compStyleSet)
                .withPosition(1, 3)


        panel.addComponent(simpleBtn)

    }

}
