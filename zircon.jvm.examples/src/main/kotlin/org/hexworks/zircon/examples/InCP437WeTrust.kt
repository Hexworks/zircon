package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.tileset.impl.CP437TileMetadataLoader
import java.util.*

object InCP437WeTrust {

    private val theme = ColorThemes.solarizedLightCyan()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withSize(Sizes.create(23, 24))
                .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val cp437panel = Components.panel()
                .withSize(Sizes.create(19, 19))
                .withPosition(Positions.create(2, 2))
                .wrapWithBox(true)
                .wrapWithShadow(true)
                .withBoxType(BoxType.SINGLE)
                .build()

        val loader = CP437TileMetadataLoader(16, 16)

        screen.addComponent(cp437panel)

        val btn = Components.checkBox()
                .withText("In CP437 we trust!")
                .withPosition(Positions.create(1, 22))

        screen.addComponent(btn.build())

        screen.applyColorTheme(theme)

        loader.fetchMetadata().forEach { char, meta ->
            cp437panel.draw(drawable = Tiles.defaultTile()
                    .withCharacter(char)
                    .withBackgroundColor(theme.primaryBackgroundColor)
                    .withForegroundColor(ANSITileColor.values()[Random().nextInt(ANSITileColor.values().size)]),
                    position = Positions.create(meta.x, meta.y)
                            .plus(Positions.offset1x1()))
        }

        screen.display()
    }

}
