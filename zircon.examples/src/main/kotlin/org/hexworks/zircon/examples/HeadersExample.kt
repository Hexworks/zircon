package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType

object HeadersExample {

    private val theme = ColorThemes.oliveLeafTea()
    private val tileset = TrueTypeFontResources.amstrad(20)

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .defaultSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .wrapWithBox(true)
                .size(Sizes.create(28, 28))
                .position(Positions.create(31, 1))
                .build()
        screen.addComponent(panel)

        val simpleHeader = Components.header()
                .text("Some header")
                .position(Positions.create(2, 2))

        screen.addComponent(simpleHeader)
        panel.addComponent(simpleHeader)

        val decoratedLabel = Components.label()
                .text("Some label")
                .boxType(BoxType.DOUBLE)
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .position(Positions.create(2, 4))

        screen.addComponent(decoratedLabel)
        panel.addComponent(decoratedLabel)

        val shadowedHeader = Components.header()
                .text("Some header")
                .wrapWithShadow(true)
                .position(Positions.create(2, 9))

        screen.addComponent(shadowedHeader)
        panel.addComponent(shadowedHeader)

        val tooLongHeader = Components.header()
                .text("Too long header")
                .size(Sizes.create(10, 1))
                .position(Positions.create(2, 13))

        screen.addComponent(tooLongHeader)
        panel.addComponent(tooLongHeader)

        val overTheTopHeader = Components.header()
                .text("WTF header")
                .decorationRenderers(
                        ShadowDecorationRenderer(),
                        BoxDecorationRenderer(BoxType.DOUBLE),
                        BoxDecorationRenderer(BoxType.SINGLE),
                        BoxDecorationRenderer(BoxType.LEFT_RIGHT_DOUBLE))
                .position(Positions.create(2, 16))

        screen.addComponent(overTheTopHeader)
        panel.addComponent(overTheTopHeader)


        screen.display()
        screen.applyColorTheme(theme)
    }

}
