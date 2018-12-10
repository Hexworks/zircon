package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.HalfBlockDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType.*

object LabelsExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = LibgdxApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(Components.label()
                .withText("Foobar")
                .wrapWithShadow(true)
                .withPosition(Positions.create(2, 2))
                .build())

        screen.addComponent(Components.label()
                .withText("Barbaz wombat")
                .withSize(Sizes.create(5, 2))
                .withPosition(Positions.create(2, 6))
                .build())

        screen.addComponent(Components.label()
                .withText("Qux")
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .withPosition(Positions.create(2, 10))
                .build())

        screen.addComponent(Components.label()
                .withText("Qux")
                .wrapWithShadow(true)
                .withBoxType(DOUBLE)
                .wrapWithBox(true)
                .withPosition(Positions.create(15, 2))
                .build())

        screen.addComponent(Components.label()
                .withText("Wtf")
                .withDecorationRenderers(
                        ShadowDecorationRenderer(),
                        BoxDecorationRenderer(DOUBLE),
                        BoxDecorationRenderer(SINGLE))
                .withPosition(Positions.create(15, 7))
                .build())

        screen.addComponent(Components.label()
                .withText("Wtf")
                .withDecorationRenderers(
                        ShadowDecorationRenderer(),
                        HalfBlockDecorationRenderer())
                .withPosition(Positions.create(15, 14))
                .build())

        screen.display()
        screen.applyColorTheme(theme)
    }

}
