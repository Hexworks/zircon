package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.BlockSide.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameModifiers.*
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.listener.InputListener
import org.hexworks.zircon.api.resource.BuiltInTrueTypeFontResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.game.DefaultGameComponent
import org.hexworks.zircon.internal.game.InMemoryGameArea
import java.util.*

object IsometricGameArea {

    private val TILESET = BuiltInTrueTypeFontResource.IBM_BIOS.toTilesetResource(20)

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
            .withBackgroundColor(FRONT_WALL_COLOR)
            .withForegroundColor(FRONT_WALL_DECOR_COLOR)
            .withModifiers(BLOCK_FRONT)
            .withCharacter('-')
            .build()

    private val BACK_WALL = Tiles.newBuilder()
            .withBackgroundColor(FRONT_WALL_COLOR)
            .withForegroundColor(FRONT_WALL_DECOR_COLOR)
            .withModifiers(BLOCK_FRONT)
            .withCharacter('-')
            .build()

    private val WALL = Tiles.newBuilder()
            .withBackgroundColor(WALL_COLOR)
            .withForegroundColor(WALL_DECOR_COLOR)
            .withModifiers(BLOCK_TOP)
            .withCharacter('#')
            .build()

    private val ROOF = Tiles.newBuilder()
            .withBackgroundColor(ROOF_COLOR)
            .withForegroundColor(ROOF_DECOR_COLOR)
            .withModifiers(BLOCK_TOP)
            .build()

    private val ANTENNA = Tiles.newBuilder()
            .withBackgroundColor(WALL_COLOR)
            .withForegroundColor(WALL_DECOR_COLOR)
            .withModifiers(BLOCK_FRONT)
            .withCharacter('=')
            .build()

    private val INTERIOR = Tiles.newBuilder()
            .withBackgroundColor(INTERIOR_COLOR)
            .withForegroundColor(INTERIOR_DECOR_COLOR)
            .withCharacter(Symbols.BLOCK_SPARSE)
            .withModifiers(BLOCK_BOTTOM)
            .build()

    private val GUY = Tiles.newBuilder()
            .withBackgroundColor(TileColors.transparent())
            .withForegroundColor(ANSITileColor.RED)
            .withCharacter(Symbols.FACE_BLACK)
            .build()

    private val EXIT_CONDITIONS = listOf(InputType.Escape, InputType.EOF)

    private val random = Random(123423432)

    @JvmStatic
    fun main(args: Array<String>) {

        val config = AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(Sizes.create(80, 50))
                .withDebugMode(true)
                .enableBetaFeatures()
                .build()

        val app = SwingApplications.startApplication(config)

        val screen = Screens.createScreenFor(app.tileGrid)


        addGamePanel(screen, Positions.zero(), screen.size, Positions.create(15, 20))

        screen.display()
    }

    fun addGamePanel(screen: Screen, position: Position, size: Size, offset: Position) {
        val gamePanel = Components.panel()
                .withSize(size)
                .withPosition(position)
                .withTitle("Game area")
                .wrapWithBox(true)
                .withBoxType(BoxType.TOP_BOTTOM_DOUBLE)
                .build()

        val visibleGameAreaSize = Sizes.from2DTo3D(gamePanel.size
                .minus(Sizes.create(2, 2)), 8)

        val virtualSize = Sizes.create3DSize(200, 200, 30)

        val gameArea = InMemoryGameArea(
                virtualSize,
                1,
                Tiles.empty())

        val gameComponent = Components.gameComponent()
                .withGameArea(gameArea)
                .withProjectionMode(ProjectionMode.TOP_DOWN_OBLIQUE)
                .withVisibleSize(visibleGameAreaSize)
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
                                    .withForegroundColor(TileColors.fromString("#333333"))
                                    .withBackgroundColor(TileColors.transparent())
                                    .withCharacter(Symbols.SINGLE_LINE_T_DOUBLE_DOWN)
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
                        if (tile.modifiers.isNotEmpty()) {
                            bb.withSide(MODIFIER_LOOKUP[tile.modifiers.first()]!!, tile)
                        } else {
                            bb.addLayer(tile)
                        }
                    }
                    gameArea.setBlockAt(pos, bb.withPosition(pos).build())
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
        screen.onInput(object : InputListener {
            override fun inputEmitted(input: Input) {
                if (EXIT_CONDITIONS.contains(input.inputType())) {
                    System.exit(0)
                } else {
                    if (InputType.ArrowUp === input.inputType()) {
                        gameComponent.scrollOneBackward()
                    }
                    if (InputType.ArrowDown === input.inputType()) {
                        gameComponent.scrollOneForward()
                    }
                    if (InputType.ArrowLeft === input.inputType()) {
                        gameComponent.scrollOneLeft()
                    }
                    if (InputType.ArrowRight === input.inputType()) {
                        gameComponent.scrollOneRight()
                    }
                    if (InputType.PageUp === input.inputType()) {
                        gameComponent.scrollOneUp()
                    }
                    if (InputType.PageDown === input.inputType()) {
                        gameComponent.scrollOneDown()
                    }
                    screen.layers.forEach {
                        screen.removeLayer(it)
                    }
                    val (x, y, z) = gameComponent.visibleOffset()
                    screen.pushLayer(Layers.newBuilder()
                            .withTileGraphics(CharacterTileStringBuilder.newBuilder()
                                    .withBackgroundColor(TileColors.transparent())
                                    .withForegroundColor(TileColors.fromString("#aaaadd"))
                                    .withText(String.format("Position: (x=%s, y=%s, z=%s)", x, y, z))
                                    .build()
                                    .toTileGraphic(TILESET))
                            .withOffset(Position.create(21, 1))
                            .build())
                }
            }
        })
    }
}
