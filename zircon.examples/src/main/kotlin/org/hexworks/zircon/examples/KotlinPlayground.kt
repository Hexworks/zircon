package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.resource.BuiltInCP437Tileset
import org.hexworks.zircon.api.util.Consumer

object KotlinPlayground {

    private val SIZE = Sizes.create(50, 30)
    private val TILESET = BuiltInCP437Tileset.TAFFER_20X20

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(SIZE)
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val c = Components.panel()
                .size(Sizes.create(5, 5))
                .wrapWithBox()
                .position(Positions.create(1, 1))
                .build()

        c.setRelativeTileAt(Positions.create(1, 1), Tiles.defaultTile().withCharacter('x'))
        c.setRelativeTileAt(Positions.create(2, 2), Tiles.defaultTile().withCharacter('y'))
        c.setRelativeTileAt(Positions.create(3, 3), Tiles.defaultTile().withCharacter('z'))

        screen.addComponent(c)

        c.onMouseReleased(object : Consumer<MouseAction> {
            override fun accept(t: MouseAction) {
                println("Pos: ${c.position()}, EPos: ${c.getEffectivePosition()}")
                println("Char: ${c.getTileAt(t.position).get().asCharacterTile().get().character}")
            }
        })

        screen.display()
        screen.applyColorTheme(ColorThemes.adriftInDreams())

    }

}
