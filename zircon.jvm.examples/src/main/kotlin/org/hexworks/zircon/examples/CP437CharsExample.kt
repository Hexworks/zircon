package org.hexworks.zircon.examples

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.LibgdxApplications
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.tileset.impl.CP437TileMetadataLoader

object CP437CharsExample {

    private val theme = ColorThemes.solarizedLightBlue()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = LibgdxApplications.startTileGrid(AppConfigs.newConfig()
                .withSize(Sizes.create(21, 21))
                .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val loader = CP437TileMetadataLoader(16, 16)
        val cp437panel = Components.panel()
                .withSize(Sizes.create(19, 19))
                .withPosition(Positions.create(1, 1))
                .withDecorations(box(BoxType.SINGLE), shadow())
                .withRendererFunction { tileGraphics, _ ->
                    loader.fetchMetadata().forEach { (char, meta) ->
                        tileGraphics.draw(
                                tile = Tile.defaultTile()
                                        .withCharacter(char)
                                        .withBackgroundColor(theme.primaryBackgroundColor)
                                        .withForegroundColor(theme.primaryForegroundColor),
                                drawPosition = Positions.create(meta.x, meta.y)
                                        .plus(Positions.offset1x1()))
                    }
                }
                .build()

        screen.addComponent(cp437panel)
        screen.applyColorTheme(theme)

        screen.display()
    }

}
