package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType

object CheckBoxesExample {

    private val theme = ColorThemes.gamebookers()
    private val tileset = CP437TilesetResources.taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .wrapWithBox(true)
                .withSize(Sizes.create(28, 28))
                .withPosition(Positions.create(31, 1))
                .build()
        screen.addComponent(panel)

        val simpleCheckBox = Components.checkBox()
                .text("Check me")
                .withPosition(Positions.create(2, 2))

        screen.addComponent(simpleCheckBox)
        panel.addComponent(simpleCheckBox)

        val decoratedCheckBox = Components.checkBox()
                .text("Check me")
                .withBoxType(BoxType.DOUBLE)
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .withPosition(Positions.create(2, 4))

        screen.addComponent(decoratedCheckBox)
        panel.addComponent(decoratedCheckBox)

        val shadowedCheckBox = Components.checkBox()
                .text("Check me")
                .wrapWithShadow(true)
                .withPosition(Positions.create(2, 9))

        screen.addComponent(shadowedCheckBox)
        panel.addComponent(shadowedCheckBox)

        val tooLongCheckBox = Components.checkBox()
                .text("Too long text")
                .width(12)
                .withPosition(Positions.create(2, 13))

        screen.addComponent(tooLongCheckBox)
        panel.addComponent(tooLongCheckBox)

        val overTheTopCheckBox = Components.checkBox()
                .text("Over the top")
                .withDecorationRenderers(
                        ShadowDecorationRenderer(),
                        BoxDecorationRenderer(BoxType.DOUBLE),
                        BoxDecorationRenderer(BoxType.SINGLE),
                        BoxDecorationRenderer(BoxType.LEFT_RIGHT_DOUBLE))
                .withPosition(Positions.create(2, 16))

        screen.addComponent(overTheTopCheckBox)
        panel.addComponent(overTheTopCheckBox)


        screen.display()
        screen.applyColorTheme(theme)
    }

}
