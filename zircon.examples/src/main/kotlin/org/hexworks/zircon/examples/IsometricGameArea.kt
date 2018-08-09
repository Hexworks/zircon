package org.hexworks.zircon.examples

import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameModifiers
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.TileColors
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.resource.CP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.application.LibgdxApplication
import org.hexworks.zircon.internal.game.DefaultGameComponent
import org.hexworks.zircon.internal.game.InMemoryGameArea
import java.util.*

object IsometricGameArea {

    private val TILESET = CP437TilesetResource.WANDERLUST_16X16

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
            .modifiers(GameModifiers.BLOCK_FRONT)
            .character('-')
            .build()

    private val BACK_WALL = Tiles.newBuilder()
            .backgroundColor(FRONT_WALL_COLOR)
            .foregroundColor(FRONT_WALL_DECOR_COLOR)
            .modifiers(GameModifiers.BLOCK_FRONT)
            .character('-')
            .build()

    private val WALL = Tiles.newBuilder()
            .backgroundColor(WALL_COLOR)
            .foregroundColor(WALL_DECOR_COLOR)
            .modifiers(GameModifiers.BLOCK_TOP)
            .character('#')
            .build()

    private val ROOF = Tiles.newBuilder()
            .backgroundColor(ROOF_COLOR)
            .foregroundColor(ROOF_DECOR_COLOR)
            .modifiers(GameModifiers.BLOCK_TOP)
            .build()

    private val ANTENNA = Tiles.newBuilder()
            .backgroundColor(WALL_COLOR)
            .foregroundColor(WALL_DECOR_COLOR)
            .modifiers(GameModifiers.BLOCK_FRONT)
            .character('=')
            .build()

    private val INTERIOR = Tiles.newBuilder()
            .backgroundColor(INTERIOR_COLOR)
            .foregroundColor(INTERIOR_DECOR_COLOR)
            .character(Symbols.BLOCK_SPARSE)
            .modifiers(GameModifiers.BLOCK_BOTTOM)
            .build()

    private val GUY = Tiles.newBuilder()
            .backgroundColor(TileColor.transparent())
            .foregroundColor(ANSITileColor.RED)
            .character(Symbols.FACE_BLACK)
            .build()

    private val EXIT_CONDITIONS = listOf(InputType.Escape, InputType.EOF)

    private val random = Random(123423432)

    @JvmStatic
    fun main(args: Array<String>) {

        val config = AppConfigBuilder.newBuilder()
                .defaultTileset(TILESET)
                .defaultSize(Sizes.create(80, 50))
                .debugMode(true)
                .build()

        val app = LibgdxApplication.create(config)
        app.start()

        val screen = ScreenBuilder.createScreenFor(app.tileGrid)


        val gamePanel = Components.newPanelBuilder()
                .size(screen.size())
                .title("Game area")
                .wrapWithBox()
                .boxType(BoxType.TOP_BOTTOM_DOUBLE)
                .build()

        val visibleGameAreaSize = Size3D.from2DSize(gamePanel.size()
                .minus(Sizes.create(2, 2)), 8)

        val virtualSize = Size3D.create(200, 200, 30)

        val gameArea = InMemoryGameArea(
                virtualSize,
                1,
                Tile.empty())

        val gameComponent = Components.newGameComponentBuilder()
                .gameArea(gameArea)
                .projectionMode(ProjectionMode.TOP_DOWN_OBLIQUE)
                .visibleSize(visibleGameAreaSize)
                .build()

        screen.addComponent(gamePanel)
        gamePanel.addComponent(gameComponent)

        enableMovement(screen, gameComponent)

        val buildingCount = random.nextInt(5) + 3

        val pos = Position3D.create(15, random.nextInt(20) + 20, 0)

        (0 until buildingCount).forEach { idx ->

            val width = random.nextInt(5) + 5
            val depth = random.nextInt(5) + 5
            val height = random.nextInt(5) + 5

            generateBuilding(size = Size3D.create(width, depth, height),
                    offset = pos.withRelativeX(random.nextInt(5) + 10 * idx).withRelativeY(random.nextInt(10)),
                    gameArea = gameArea,
                    repeat = 2)
        }

        screen.applyColorTheme(ColorThemeResource.SOLARIZED_DARK_CYAN.getTheme())
        screen.display()
    }

    private tailrec fun generateBuilding(size: Size3D, offset: Position3D, gameArea: InMemoryGameArea, repeat: Int) {
        (0 until size.zLength).forEach { z ->
            (0 until size.yLength).forEach { y ->
                (0 until size.xLength).forEach { x ->
                    val pos = Position3D.from2DPosition(Position.create(x, y).plus(offset.to2DPosition()), z + offset.z)
                    val chars = if (y == size.yLength - 1) {
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
                                    .backgroundColor(TileColor.transparent())
                                    .character(Symbols.SINGLE_LINE_T_DOUBLE_DOWN)
                                    .build())
                        }
                        chars
                    }
                    if (z == size.zLength - 1) {
                        chars.remove(WALL)
                        if (repeat == 0 || x == 0 || x == size.xLength - 1 || y == 0 || y == size.yLength - 1) {
                            chars.add(ROOF)
                        }
                    }
                    gameArea.setBlockAt(pos, chars)
                }
            }
        }
        if (repeat > 0) {
            generateBuilding(
                    size = Size3D.create(size.xLength - 2, size.yLength - 2, size.zLength),
                    offset = offset.withRelativeZ(size.zLength).withRelativeX(1).withRelativeY(1),
                    gameArea = gameArea,
                    repeat = repeat - 1)
        }
    }

    private fun enableMovement(screen: Screen, gameComponent: DefaultGameComponent) {
        screen.onInput { input ->
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
                screen.pushLayer(LayerBuilder.newBuilder()
                        .tileGraphic(CharacterTileStringBuilder.newBuilder()
                                .backgroundColor(TileColor.transparent())
                                .foregroundColor(TileColors.fromString("#aaaadd"))
                                .text(String.format("Position: (x=%s, y=%s, z=%s)", x, y, z))
                                .build()
                                .toTileGraphic(TILESET))
                        .offset(Position.create(21, 1))
                        .build())
            }
        }
    }
}
