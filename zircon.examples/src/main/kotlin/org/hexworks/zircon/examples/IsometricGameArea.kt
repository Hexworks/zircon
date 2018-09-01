package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.data.BlockSide.*
import org.hexworks.zircon.api.game.GameModifiers.*
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.BuiltInMonospaceFontResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.internal.game.DefaultGameComponent
import org.hexworks.zircon.internal.game.InMemoryGameArea
import java.util.*

object IsometricGameArea {

    private val TILESET = BuiltInMonospaceFontResource.IBM_BIOS.toTilesetResource(20)

    private val MODIFIER_LOOKUP = mapOf(
            BLOCK_BACK to BACK,
            BLOCK_FRONT to FRONT,
            BLOCK_TOP to TOP,
            BLOCK_BOTTOM to BOTTOM)

    private val WALL_COLOR = TileColors.fromString("#333333")
    private val WALL_DECOR_COLOR = TileColors.fromString("#444444")


    private val FRONT_WALL_COLOR = TileColors.fromString("#555555")
    private val FRONT_WALL_DECOR_COLOR = TileColors.fromString("#666666")

    private val ROOF_COLOR = TileColors.fromString("#666666")
    private val ROOF_DECOR_COLOR = TileColors.fromString("#4e4e4e")

    private val INTERIOR_COLOR = TileColors.fromString("#999999")
    private val INTERIOR_DECOR_COLOR = TileColors.fromString("#aaaaaa")

    private val FRONT_WALL = Tiles.newBuilder()
            .backgroundColor(FRONT_WALL_COLOR)
            .foregroundColor(FRONT_WALL_DECOR_COLOR)
            .modifiers(BLOCK_FRONT)
            .character('-')
            .build()

    private val BACK_WALL = Tiles.newBuilder()
            .backgroundColor(FRONT_WALL_COLOR)
            .foregroundColor(FRONT_WALL_DECOR_COLOR)
            .modifiers(BLOCK_FRONT)
            .character('-')
            .build()

    private val WALL = Tiles.newBuilder()
            .backgroundColor(WALL_COLOR)
            .foregroundColor(WALL_DECOR_COLOR)
            .modifiers(BLOCK_TOP)
            .character('#')
            .build()

    private val ROOF = Tiles.newBuilder()
            .backgroundColor(ROOF_COLOR)
            .foregroundColor(ROOF_DECOR_COLOR)
            .modifiers(BLOCK_TOP)
            .build()

    private val ANTENNA = Tiles.newBuilder()
            .backgroundColor(WALL_COLOR)
            .foregroundColor(WALL_DECOR_COLOR)
            .modifiers(BLOCK_FRONT)
            .character('=')
            .build()

    private val INTERIOR = Tiles.newBuilder()
            .backgroundColor(INTERIOR_COLOR)
            .foregroundColor(INTERIOR_DECOR_COLOR)
            .character(Symbols.BLOCK_SPARSE)
            .modifiers(BLOCK_BOTTOM)
            .build()

    private val GUY = Tiles.newBuilder()
            .backgroundColor(TileColors.transparent())
            .foregroundColor(ANSITileColor.RED)
            .character(Symbols.FACE_BLACK)
            .build()

    private val EXIT_CONDITIONS = listOf(InputType.Escape, InputType.EOF)

    private val random = Random(123423432)

    @JvmStatic
    fun main(args: Array<String>) {

        val config = AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(Sizes.create(80, 50))
                .debugMode(true)
                .build()

        val app = SwingApplications.startApplication(config)

        val screen = Screens.createScreenFor(app.tileGrid)


        addGamePanel(screen, Positions.defaultPosition(), screen.size(), Positions.create(15, 20))

        screen.display()
    }

    fun addGamePanel(screen: Screen, position: Position, size: Size, offset: Position) {
        val gamePanel = Components.panel()
                .size(size)
                .position(position)
                .title("Game area")
                .wrapWithBox()
                .boxType(BoxType.TOP_BOTTOM_DOUBLE)
                .build()

        val visibleGameAreaSize = Sizes.from2DTo3D(gamePanel.size()
                .minus(Sizes.create(2, 2)), 8)

        val virtualSize = Sizes.create3DSize(200, 200, 30)

        val gameArea = InMemoryGameArea(
                virtualSize,
                1,
                Tiles.empty())

        val gameComponent = Components.gameComponent()
                .gameArea(gameArea)
                .projectionMode(ProjectionMode.TOP_DOWN_OBLIQUE)
                .visibleSize(visibleGameAreaSize)
                .build()

        screen.addComponent(gamePanel)
        gamePanel.addComponent(gameComponent)

        enableMovement(screen, gameComponent)

        val buildingCount = random.nextInt(5) + 3

        val pos = Positions.create3DPosition(offset.x, random.nextInt(20) + offset.y, 0)

        (0 until buildingCount).forEach { idx ->

            val width = random.nextInt(5) + 5
            val depth = random.nextInt(5) + 5
            val height = random.nextInt(5) + 5

            generateBuilding(size = Sizes.create3DSize(width, depth, height),
                    offset = pos.withRelativeX(random.nextInt(5) + 10 * idx).withRelativeY(random.nextInt(10)),
                    gameArea = gameArea,
                    repeat = 2)
        }
        gamePanel.applyColorTheme(ColorThemes.gamebookers())
    }

    private tailrec fun generateBuilding(size: Size3D, offset: Position3D, gameArea: InMemoryGameArea, repeat: Int) {
        (0 until size.zLength).forEach { z ->
            (0 until size.yLength).forEach { y ->
                (0 until size.xLength).forEach { x ->
                    val pos = Positions.from2DTo3D(Positions.create(x, y).plus(offset.to2DPosition()), z + offset.z)
                    val blockTiles = if (y == size.yLength - 1) {
                        mutableListOf(if (size.xLength < 3 || size.yLength < 3) {
                            ANTENNA
                        } else if (random.nextInt(5) < 1) {
                            FRONT_WALL.withForegroundColor(TileColors.fromString("#ffff00"))
                        } else {
                            FRONT_WALL
                        }, WALL)
                    } else if (x == 0 || x == size.xLength - 1) {
                        mutableListOf(WALL)
                    } else if (y == 0) {
                        mutableListOf(BACK_WALL, WALL)
                    } else {
                        val chars = mutableListOf(INTERIOR)
                        val extra = random.nextInt(10)
                        if (extra == 0 || extra == 1) {
                            chars.add(GUY.withForegroundColor(ANSITileColor.values()[random.nextInt(ANSITileColor.values().size)]))
                        }
                        if (extra == 2) {
                            chars.add(Tiles.newBuilder()
                                    .foregroundColor(TileColors.fromString("#333333"))
                                    .backgroundColor(TileColors.transparent())
                                    .character(Symbols.SINGLE_LINE_T_DOUBLE_DOWN)
                                    .build())
                        }
                        chars
                    }
                    if (z == size.zLength - 1) {
                        blockTiles.remove(WALL)
                        if (repeat == 0 || x == 0 || x == size.xLength - 1 || y == 0 || y == size.yLength - 1) {
                            blockTiles.add(ROOF)
                        }
                    }
                    val bb = Blocks.newBuilder()
                    blockTiles.forEach { tile ->
                        if (tile.getModifiers().isNotEmpty()) {
                            bb.side(MODIFIER_LOOKUP[tile.getModifiers().first()]!!, tile)
                        } else {
                            bb.layer(tile)
                        }
                    }
                    gameArea.setBlockAt(pos, bb.position(pos).build())
                }
            }
        }
        if (repeat > 0) {
            generateBuilding(
                    size = Sizes.create3DSize(size.xLength - 2, size.yLength - 2, size.zLength),
                    offset = offset.withRelativeZ(size.zLength).withRelativeX(1).withRelativeY(1),
                    gameArea = gameArea,
                    repeat = repeat - 1)
        }
    }

    private fun enableMovement(screen: Screen, gameComponent: DefaultGameComponent) {
        screen.onInput(object : Consumer<Input> {
            override fun accept(input: Input) {
                if (EXIT_CONDITIONS.contains(input.getInputType())) {
                    System.exit(0)
                } else {
                    if (InputType.ArrowUp === input.getInputType()) {
                        gameComponent.scrollOneBackward()
                    }
                    if (InputType.ArrowDown === input.getInputType()) {
                        gameComponent.scrollOneForward()
                    }
                    if (InputType.ArrowLeft === input.getInputType()) {
                        gameComponent.scrollOneLeft()
                    }
                    if (InputType.ArrowRight === input.getInputType()) {
                        gameComponent.scrollOneRight()
                    }
                    if (InputType.PageUp === input.getInputType()) {
                        gameComponent.scrollOneUp()
                    }
                    if (InputType.PageDown === input.getInputType()) {
                        gameComponent.scrollOneDown()
                    }
                    screen.getLayers().forEach {
                        screen.removeLayer(it)
                    }
                    val (x, y, z) = gameComponent.visibleOffset()
                    screen.pushLayer(Layers.newBuilder()
                            .tileGraphic(CharacterTileStringBuilder.newBuilder()
                                    .backgroundColor(TileColors.transparent())
                                    .foregroundColor(TileColors.fromString("#aaaadd"))
                                    .text(String.format("Position: (x=%s, y=%s, z=%s)", x, y, z))
                                    .build()
                                    .toTileGraphic(TILESET))
                            .offset(Position.create(21, 1))
                            .build())
                }
            }
        })
    }
}
