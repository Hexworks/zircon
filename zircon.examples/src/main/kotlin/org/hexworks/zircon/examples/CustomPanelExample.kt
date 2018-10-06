package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.impl.DefaultPanel
import org.hexworks.zircon.internal.component.renderer.DefaultPanelRenderer

object CustomPanelExample {

    class CustomPanel(title: String,
                      componentMetadata: ComponentMetadata) : DefaultPanel(
            componentMetadata = componentMetadata,
            title = title,
            renderingStrategy = DefaultComponentRenderingStrategy(
                    decorationRenderers = listOf(BoxDecorationRenderer(
                            title = Maybe.of(title))),
                    componentRenderer = DefaultPanelRenderer()))

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultSize(Sizes.create(60, 30))
                .enableBetaFeatures()
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(CustomPanel(
                title = "Whatever",
                componentMetadata = ComponentMetadata(
                        position = Positions.defaultPosition(),
                        size = Sizes.create(20, 10),
                        tileset = CP437TilesetResources.acorn8X16(),
                        componentStyleSet = ComponentStyleSet.defaultStyleSet())))

        screen.display()
        screen.applyColorTheme(ColorThemeResource.CYBERPUNK.getTheme())

    }

}
