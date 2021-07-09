package org.hexworks.zircon.examples.game


import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

object GameComponentExample {

    private val theme = ColorThemes.cyberpunk()
    private val tileset = CP437TilesetResources.hack64x64()
    private val width = 24
    private val height = 12

    private val greenA = Tile.newBuilder()
        .withCharacter('a')
        .withBackgroundColor(TileColor.fromString("#bdd5ac"))
        .withForegroundColor(TileColor.fromString("#314c1c"))
        .withModifiers(
            BorderBuilder.newBuilder()
                .withBorderColor(TileColor.fromString("#78a55a"))
                .build()
        )
        .buildCharacterTile()

    private val blockA = block(
        side = greenA,
        top = greenA.withBackgroundColor(TileColor.fromString("#dce9d5"))
    )

    private val greenB = greenA.withCharacter('b')

    private val blockB = block(
        side = greenB,
        top = greenB.withBackgroundColor(TileColor.fromString("#dce9d5"))
    )

    private val tealC = Tile.newBuilder()
        .withCharacter('c')
        .withBackgroundColor(TileColor.fromString("#a8c3c8"))
        .withForegroundColor(TileColor.fromString("#19353e"))
        .withModifiers(
            BorderBuilder.newBuilder()
                .withBorderColor(TileColor.fromString("#53808c"))
                .build()
        )
        .buildCharacterTile()

    private val blockC = block(
        side = tealC,
        top = tealC.withBackgroundColor(TileColor.fromString("#d3dfe2"))
    )

    private val redD = Tile.newBuilder()
        .withCharacter('d')
        .withBackgroundColor(TileColor.fromString("#df9d9b"))
        .withForegroundColor(TileColor.fromString("#5d0e07"))
        .withModifiers(
            BorderBuilder.newBuilder()
                .withBorderColor(TileColor.fromString("#bc261a"))
                .build()
        )
        .buildCharacterTile()

    private val blockD = block(
        side = redD,
        top = redD.withBackgroundColor(TileColor.fromString("#eecdcd"))
    )

    private val blueE = Tile.newBuilder()
        .withCharacter('e')
        .withBackgroundColor(TileColor.fromString("#a9c2f0"))
        .withForegroundColor(TileColor.fromString("#2d4e89"))
        .withModifiers(
            BorderBuilder.newBuilder()
                .withBorderColor(TileColor.fromString("#99afd6"))
                .build()
        )
        .buildCharacterTile()

    private val blockE = block(
        side = blueE,
        top = blueE.withBackgroundColor(TileColor.fromString("#ccdaf5"))
    )

    private val purpleF = Tile.newBuilder()
        .withCharacter('f')
        .withBackgroundColor(TileColor.fromString("#a9a0c8"))
        .withForegroundColor(TileColor.fromString("#1f184c"))
        .withModifiers(
            BorderBuilder.newBuilder()
                .withBorderColor(TileColor.fromString("#a099bd"))
                .build()
        )
        .buildCharacterTile()

    private val blockF = block(
        side = purpleF,
        top = purpleF.withBackgroundColor(TileColor.fromString("#d8d3e7"))
    )

    private val mallowG = Tile.newBuilder()
        .withCharacter('g')
        .withBackgroundColor(TileColor.fromString("#cea8bc"))
        .withForegroundColor(TileColor.fromString("#50213a"))
        .withModifiers(
            BorderBuilder.newBuilder()
                .withBorderColor(TileColor.fromString("#b899aa"))
                .build()
        )
        .buildCharacterTile()

    private val blockG = block(
        side = mallowG,
        top = mallowG.withBackgroundColor(TileColor.fromString("#e6d2db"))
    )


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(width, height))
                .build()
        )

        val screen = Screen.create(tileGrid)

        val gameArea = GameComponents.newGameAreaBuilder<Tile, Block<Tile>>()
            .withActualSize(Size3D.create(3, 6, 3))
            .withProjectionMode(ProjectionMode.TOP_DOWN_OBLIQUE_FRONT)
            .withVisibleSize(Size3D.create(3, 6, 3))
            .build()
        screen.onShutdown {
            gameArea.dispose()
        }

        val panel = Components.panel()
            .withPreferredSize(width, height)
            .withDecorations(box(title = "Game Component"))
            .build()

        val gameComponent = Components.label()
            .withComponentRenderer(GameComponents.newGameAreaComponentRenderer(gameArea))
            .withSize(panel.contentSize)
            .build()

        panel.addComponent(gameComponent)
        screen.addComponent(panel)

        gameArea.setBlockAt(Position3D.create(0, 2, 1), blockA)
        gameArea.setBlockAt(Position3D.create(0, 2, 0), blockB)
        gameArea.setBlockAt(Position3D.create(1, 2, 0), blockC)
        gameArea.setBlockAt(Position3D.create(1, 1, 0), blockD)
        gameArea.setBlockAt(Position3D.create(0, 1, 0), blockE)
        gameArea.setBlockAt(Position3D.create(2, 0, 0), blockF)
        gameArea.setBlockAt(Position3D.create(0, 0, 1), blockG)

        screen.display()
        screen.theme = theme
    }

    private fun block(side: Tile, top: Tile) = Block.newBuilder<Tile>()
        .withLeft(side)
        .withRight(side)
        .withFront(side)
        .withBack(side)
        .withTop(top)
        .withEmptyTile(Tile.empty())
        .build()

}
