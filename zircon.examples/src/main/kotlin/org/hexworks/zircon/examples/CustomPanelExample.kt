package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.game.BaseGameArea
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.impl.DefaultPanel
import org.hexworks.zircon.internal.component.renderer.DefaultPanelRenderer
import java.util.*

object CustomPanelExample {

    class CustomPanel(title: String,
                      position: Position,
                      size: Size,
                      tileset: TilesetResource) : DefaultPanel(
            title = title,
            renderingStrategy = DefaultComponentRenderingStrategy(
                    decorationRenderers = listOf(BoxDecorationRenderer(
                            title = Maybe.of(title))),
                    componentRenderer = DefaultPanelRenderer()),
            position = position,
            size = size,
            tileset = tileset,
            componentStyleSet = ComponentStyleSet.defaultStyleSet())

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultSize(Sizes.create(60, 30))
                .enableBetaFeatures()
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(CustomPanel(
                title = "Whatever",
                position = Positions.defaultPosition(),
                size = Sizes.create(20, 10),
                tileset = CP437TilesetResources.acorn8X16()))

        screen.display()
        screen.applyColorTheme(ColorThemeResource.CYBERPUNK.getTheme())

    }

}
