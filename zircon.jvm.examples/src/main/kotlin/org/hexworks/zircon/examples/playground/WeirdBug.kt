package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.color.ANSITileColor.BLACK
import org.hexworks.zircon.api.color.ANSITileColor.GRAY
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.StyleSet


object WeirdBug {

    private val tileset = CP437TilesetResources.rexPaint20x20()
    private val theme = ColorThemes.solarizedLightBlue()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(60, 30)
                .build())

        val screen = tileGrid.toScreen()
        screen.display()
        screen.theme = theme


        val gameArea = GameAreaBuilder<Tile, Block<Tile>>()
                .withActualSize(Size3D.create(10, 10, 1))
                .withVisibleSize(Size3D.create(10, 10, 1))
                .withProjectionMode(ProjectionMode.TOP_DOWN)
                .build()

        val gameComponent = Components.gameComponent<Tile, Block<Tile>>()
                .withGameArea(gameArea)
                .withSize(10, 10)
                .build()

        screen.addComponents(gameComponent)

        val block: Block<Tile> = BlockBuilder<Tile>()
                .withEmptyTile(Tile.empty())
                .build()

        block.bottom = Tile.createCharacterTile('_', StyleSet.create(
                foregroundColor = TileColor.create(0, 0, 0, 255),
                backgroundColor = TileColor.create(178, 140, 0, 255)))
        block.top = Tile.createCharacterTile('r', StyleSet.create(
                foregroundColor = TileColor.create(128, 128, 128, 255),
                backgroundColor = TileColor.create(0, 0, 0, 0)))

        gameArea.setBlockAt(Position3D.create(5, 5, 0), block)

    }
}
