package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.tileset.impl.CP437TileMetadataLoader

object CP437CharsExample {

    private val theme = ColorThemes.solarizedLightBlue()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withSize(Sizes.create(21, 21))
                .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val cp437panel = Components.panel()
                .withSize(Sizes.create(19, 19))
                .withPosition(Positions.create(1, 1))
                .wrapWithBox(true)
                .wrapWithShadow(true)
                .withBoxType(BoxType.SINGLE)
                .build()

        val loader = CP437TileMetadataLoader(16, 16)

        screen.addComponent(cp437panel)
        screen.applyColorTheme(theme)

        loader.fetchMetadata().forEach { char, meta ->
            cp437panel.draw(drawable = Tiles.defaultTile()
                    .withCharacter(char)
                    .withBackgroundColor(theme.primaryBackgroundColor)
                    .withForegroundColor(theme.primaryForegroundColor),
                    position = Positions.create(meta.x, meta.y)
                            .plus(Positions.offset1x1()))
        }

        screen.display()
    }

}
