package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.HalfBlockDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType.*

object LabelsExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.rogueYun16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(Components.label()
                .text("Foobar")
                .wrapWithShadow(true)
                .position(Positions.create(2, 2))
                .build())

        screen.addComponent(Components.label()
                .text("Barbaz wombat")
                .size(Sizes.create(5, 2))
                .position(Positions.create(2, 6))
                .build())

        screen.addComponent(Components.label()
                .text("Qux")
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .position(Positions.create(2, 10))
                .build())

        screen.addComponent(Components.label()
                .text("Qux")
                .wrapWithShadow(true)
                .boxType(DOUBLE)
                .wrapWithBox(true)
                .position(Positions.create(15, 2))
                .build())

        screen.addComponent(Components.label()
                .text("Wtf")
                .decorationRenderers(
                        ShadowDecorationRenderer(),
                        BoxDecorationRenderer(DOUBLE),
                        BoxDecorationRenderer(SINGLE))
                .position(Positions.create(15, 7))
                .build())

        screen.addComponent(Components.label()
                .text("Wtf")
                .decorationRenderers(
                        ShadowDecorationRenderer(),
                        HalfBlockDecorationRenderer())
                .position(Positions.create(15, 14))
                .build())

        screen.display()
        screen.applyColorTheme(theme)
    }

}
