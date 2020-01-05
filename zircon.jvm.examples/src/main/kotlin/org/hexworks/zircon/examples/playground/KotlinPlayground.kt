@file:Suppress("UNUSED_VARIABLE", "MayBeConstant", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.extensions.side
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.screen.Screen


object KotlinPlayground {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    private val compStyleSet = ComponentStyleSetBuilder
            .newBuilder()
            .withDefaultStyle(StyleSet.create(ANSITileColor.CYAN, ANSITileColor.BLACK))
            .withActiveStyle(StyleSet.create(ANSITileColor.CYAN, ANSITileColor.BLACK))
            .withFocusedStyle(StyleSet.create(ANSITileColor.GREEN, ANSITileColor.BLACK))
            .build()

    private val contactSideStyleProperty = createPropertyFrom(compStyleSet)

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 40))
                .build())

        val screen = Screen.create(tileGrid)

        screen.theme = theme

        val panel = Components.panel()
                .withDecorations(box(title = "Buttons on panel"), shadow())
                .withSize(30, 32)
                .withPosition(29, 1)
                .build()

        panel.theme = theme

        screen.addComponent(panel)


        val simpleBtn = Components.button()
                .withText("Button")
                .withDecorations(side())
                //.withComponentStyleSet(compStyleSet)
                .withAlignment(positionalAlignment(1, 3))
                .build()
        panel.addComponent(simpleBtn)
        screen.display()
        simpleBtn.componentStyleSetProperty.value = contactSideStyleProperty.value

    }
}
