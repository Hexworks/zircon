package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.tileset.lookup.CP437TileMetadataLoader

object CP437CharsExample {


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        val screen = Screens.createScreenFor(tileGrid)

        val cp437panel = Components.panel()
                .size(Sizes.create(19, 19))
                .position(Positions.create(2, 2))
                .wrapWithBox()
                .wrapWithShadow()
                .boxType(BoxType.SINGLE)
                .build()

        val loader = CP437TileMetadataLoader(16, 16)

        screen.addComponent(cp437panel)
        screen.applyColorTheme(ColorThemeResource.AMIGA_OS.getTheme())

        loader.fetchMetadata().forEach { char, meta ->
            cp437panel.draw(Tiles.defaultTile().withCharacter(char), Positions.create(meta.x, meta.y)
                    .plus(Positions.offset1x1()))
        }

        screen.display()
    }

}
