package org.hexworks.zircon.examples.game

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.zircon.api.CP437TilesetResources.rexPaint20x20
import org.hexworks.zircon.api.ColorThemes.ammo
import org.hexworks.zircon.api.ColorThemes.stormyGreen
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components.button
import org.hexworks.zircon.api.Components.panel
import org.hexworks.zircon.api.Components.radioButton
import org.hexworks.zircon.api.Components.radioButtonGroup
import org.hexworks.zircon.api.Components.vbox
import org.hexworks.zircon.api.DrawSurfaces.tileGraphicsBuilder
import org.hexworks.zircon.api.GameComponents.newGameAreaBuilder
import org.hexworks.zircon.api.GameComponents.newGameAreaComponentRenderer
import org.hexworks.zircon.api.Shapes
import org.hexworks.zircon.api.SwingApplications.startTileGrid
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.TileColor.Companion.fromString
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.data.Position3D.Companion.create
import org.hexworks.zircon.api.data.Size.Companion.create
import org.hexworks.zircon.api.data.Tile.Companion.empty
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.screen.Screen.Companion.create
import org.hexworks.zircon.api.shape.Shape
import org.hexworks.zircon.api.shape.ShapeFactory
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse.Companion.processed
import java.awt.Toolkit
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.exitProcess

@Suppress("DuplicatedCode")
class ObliqueCityWithScrollingKotlin {
    companion object {
        private val THEME = ammo()
        private val TILESET = rexPaint20x20()
        private val DIMENSIONS = Toolkit.getDefaultToolkit().screenSize
        private val GRID_WIDTH = DIMENSIONS.width / TILESET.width - 1
        private val GRID_HEIGHT = DIMENSIONS.height / TILESET.height - 1
        private val GRID_SIZE = create(GRID_WIDTH, GRID_HEIGHT)

        private val GRASS_TILE = Tile.newBuilder()
            .withBackgroundColor(fromString("#243000"))
            .withForegroundColor(fromString("#394c00"))
            .buildCharacterTile()
        private val GRASS_TILES = listOf(
            GRASS_TILE,
            GRASS_TILE,
            GRASS_TILE,
            GRASS_TILE.withCharacter('%'),
            GRASS_TILE.withCharacter('\''),
            GRASS_TILE.withCharacter(','),
            GRASS_TILE.withCharacter('.'),
            GRASS_TILE.withCharacter(';')
        )

        private val PAVEMENT_TILE = Tile.newBuilder()
            .withBackgroundColor(fromString("#1f292e"))
            .withForegroundColor(fromString("#2a373e"))
            .buildCharacterTile()

        private val GAME_AREA_SIZE = Size3D.create(200, 200, 10)
        private val FILLER = GRASS_TILE
        private val RANDOM = Random(3248351)

        @JvmStatic
        fun main(args: Array<String>) {
            val screen = create(
                startTileGrid(
                    AppConfig.newBuilder()
                        .withDefaultTileset(TILESET)
                        .withSize(GRID_SIZE)
                        .withDebugMode(true)
                        .build()
                )
            )
            val actions = panel()
                .withPreferredSize(24, 5)
                .withPosition(1, 1)
                .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE, "Actions"))
                .build()
            val quit = button()
                .withText("Quit")
                .build()
            quit.onActivated { exitProcess(0) }
            val projections = vbox()
                .withPreferredSize(24, 4)
                .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE, "Projection Mode"))
                .withPosition(Position.create(0, 2).relativeToBottomOf(actions))
                .build()
            val topDown = radioButton()
                .withText("Top Down")
                .withKey(ProjectionMode.TOP_DOWN.name)
                .build()
            val topDownOblique = radioButton()
                .withText("Top Down Oblique")
                .withKey(ProjectionMode.TOP_DOWN_OBLIQUE_FRONT.name)
                .build()
            val projectionsGroup = radioButtonGroup()
                .build().apply {
                    addComponent(topDown)
                    addComponent(topDownOblique)
                }
            projections.addComponents(topDown, topDownOblique)
            val projectionProperty = projectionsGroup.selectedButtonProperty.bindTransform {
                it.map { radioButton ->
                    ProjectionMode.valueOf(radioButton.key)
                }.orElse(ProjectionMode.TOP_DOWN)
            }
            actions.addComponents(quit)
            val visibleGameAreaSize = GRID_SIZE.minus(create(2, 2)).toSize3D(10)
            val gameArea: GameArea<Tile, Block<Tile>> = newGameAreaBuilder<Tile, Block<Tile>>()
                .withActualSize(GAME_AREA_SIZE)
                .withVisibleSize(visibleGameAreaSize)
                .build()
            screen.onShutdown {
                gameArea.dispose()
            }
            val gamePanel = panel()
                .withPreferredSize(screen.size)
                .withComponentRenderer(
                    newGameAreaComponentRenderer(
                        gameArea = gameArea,
                        projectionMode = projectionProperty,
                        fillerTile = FILLER
                    )
                )
                .withDecorations(box(BoxType.TOP_BOTTOM_DOUBLE, "Game Area with Scrolling"))
                .build()
            gamePanel.addComponents(actions, projections)
            screen.addComponent(gamePanel)
            enableMovement(screen, gameArea)

            generateGrass(gameArea)
            generateBuilding(
                gameArea, create(
                    x = RANDOM.nextInt(10) + 20,
                    y = RANDOM.nextInt(10) + 20,
                    z = RANDOM.nextInt(10) + 20
                )
            )

            screen.theme = THEME
            screen.display()
        }

        private fun generateGrass(gameArea: GameArea<Tile, Block<Tile>>) {
            gameArea.actualSize.to2DSize().fetchPositions().forEach { pos ->
                gameArea.setBlockAt(
                    position = pos.toPosition3D(0),
                    block = Block.newBuilder<Tile>()
                        .withEmptyTile(empty())
                        .withBottom(GRASS_TILES.fetchRandomElement())
                        .build()
                )
            }
        }

        private fun generateBuilding(
            gameArea: GameArea<Tile, Block<Tile>>,
            position: Position3D
        ) {
            val pos = position.to2DPosition()
            val buildingPos = pos + Position.offset1x1()
            val baseSize = Size.create(5, 5)
            val buildingSize = Size.create(3, 3)
            val rect = Shapes.buildRectangle(pos, baseSize)
            rect.positions.forEach {
                gameArea.setBlockAt(
                    position = it.toPosition3D(0),
                    block = newBlock().withBottom(PAVEMENT_TILE).build()
                )
            }
            gameArea.setBlockAt(
                position = (baseSize.fetchTopLeftPosition() + pos).toPosition3D(0),
                block = newBlock().withBottom(PAVEMENT_TILE.withCharacter(Symbols.SINGLE_LINE_TOP_LEFT_CORNER)).build()
            )
            gameArea.setBlockAt(
                position = (baseSize.fetchTopRightPosition() + pos).toPosition3D(0),
                block = newBlock().withBottom(PAVEMENT_TILE.withCharacter(Symbols.SINGLE_LINE_TOP_RIGHT_CORNER)).build()
            )
            gameArea.setBlockAt(
                position = (baseSize.fetchBottomLeftPosition() + pos).toPosition3D(0),
                block = newBlock().withBottom(PAVEMENT_TILE.withCharacter(Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER))
                    .build()
            )
            gameArea.setBlockAt(
                position = (baseSize.fetchBottomRightPosition() + pos).toPosition3D(0),
                block = newBlock().withBottom(PAVEMENT_TILE.withCharacter(Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER))
                    .build()
            )
        }

        private fun enableMovement(screen: Screen, gameArea: GameArea<Tile, Block<Tile>>) {
            screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { (_, _, code), _: UIEventPhase? ->
                if (code == KeyCode.ESCAPE) {
                    exitProcess(0)
                } else {
                    if (code == KeyCode.UP) {
                        gameArea.scrollOneBackward()
                    }
                    if (code == KeyCode.DOWN) {
                        gameArea.scrollOneForward()
                    }
                    if (code == KeyCode.LEFT) {
                        gameArea.scrollOneLeft()
                    }
                    if (code == KeyCode.RIGHT) {
                        gameArea.scrollOneRight()
                    }
                    if (code == KeyCode.KEY_U) {
                        gameArea.scrollOneUp()
                    }
                    if (code == KeyCode.KEY_D) {
                        gameArea.scrollOneDown()
                    }
                }
                processed()
            }
        }

        private fun newBlock() = Block.newBuilder<Tile>().withEmptyTile(empty())

        private fun <T : Any> List<T>.fetchRandomElement(): T {
            return get(RANDOM.nextInt(this.size))
        }

        private fun GameArea<Tile, Block<Tile>>.replaceBlockAt(
            position3D: Position3D,
            fn: (Block<Tile>) -> Block<Tile>
        ) {
            fetchBlockAtOrNull(position3D)?.let { block ->
                setBlockAt(position3D, fn(block))
            }
        }
    }
}
