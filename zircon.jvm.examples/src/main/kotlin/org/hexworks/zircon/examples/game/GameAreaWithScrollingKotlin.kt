package org.hexworks.zircon.examples.game

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.CP437TilesetResources.rexPaint20x20
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
import org.hexworks.zircon.api.SwingApplications.startTileGrid
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.TileColor.Companion.fromString
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.data.Position3D.Companion.create
import org.hexworks.zircon.api.data.Size.Companion.create
import org.hexworks.zircon.api.data.Tile.Companion.empty
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameAreaTileFilter
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.screen.Screen.Companion.create
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse.Companion.processed
import java.awt.Toolkit
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.exitProcess

@Suppress("DuplicatedCode")
class GameAreaWithScrollingKotlin {

    companion object {

        private val THEME = stormyGreen()
        private val TILESET = rexPaint20x20()
        private val DIMENSIONS = Toolkit.getDefaultToolkit().screenSize
        private val GRID_WIDTH = DIMENSIONS.width / TILESET.width - 1
        private val GRID_HEIGHT = DIMENSIONS.height / TILESET.height - 1
        private val GRID_SIZE = create(GRID_WIDTH, GRID_HEIGHT)

        private val LEVEL_COUNT = 10
        private val FILLER: Tile = empty()
            .withCharacter('x')
            .withBackgroundColor(fromString("#e7b751"))
        private val PYRAMID_COLOR = fromString("#ecc987")

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
            val visibleGameAreaSize = GRID_SIZE.minus(create(2, 2)).toSize3D(5)
            val actualGameAreaSize = create(Int.MAX_VALUE, Int.MAX_VALUE)
            val gameArea: GameArea<Tile, Block<Tile>> = newGameAreaBuilder<Tile, Block<Tile>>()
                .withActualSize(actualGameAreaSize.toSize3D(LEVEL_COUNT))
                .withVisibleSize(visibleGameAreaSize)
                .withFilter(object : GameAreaTileFilter {
                    override fun transform(
                        visibleSize: Size3D,
                        offsetPosition: Position3D,
                        blockTileType: BlockTileType,
                        tileBuilder: TileBuilder
                    ): TileBuilder {
                        val step = when (blockTileType) {
                            BlockTileType.TOP -> 1 / (visibleSize.zLength.toDouble() * 5)
                            BlockTileType.FRONT -> 1 / (visibleSize.zLength.toDouble() * 3)
                            else -> 1.0
                        }
                        val style = tileBuilder.styleSet()
                        return tileBuilder.withStyleSet(
                            style.withForegroundColor(
                                style.foregroundColor
                                    .darkenByPercent(step * (visibleSize.zLength - offsetPosition.z).toDouble())
                            )
                        )
                    }
                })
                .build()
            screen.onShutdown {
                gameArea.dispose()
            }
            val gamePanel = panel()
                .withSize(screen.size)
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
            val levels: MutableMap<Int, List<TileGraphics>> = HashMap()
            for (i in 0 until LEVEL_COUNT) {
                levels[i] = listOf(
                    tileGraphicsBuilder()
                        .withSize(actualGameAreaSize)
                        .build()
                )
            }
            screen.addComponent(gamePanel)
            enableMovement(screen, gameArea)
            generatePyramid(3, create(30, 5, 2), gameArea)
            generatePyramid(6, create(40, 9, 5), gameArea)
            generatePyramid(5, create(35, 21, 4), gameArea)
            screen.theme = THEME
            screen.display()
        }

        private fun generatePyramid(height: Int, startPos: Position3D, gameArea: GameArea<Tile, Block<Tile>>) {
            val wall = Tile.newBuilder()
                .withCharacter(Symbols.BLOCK_SOLID)
                .withForegroundColor(PYRAMID_COLOR)
                .build()
            val currLevel = AtomicInteger(startPos.z)
            for (currSize in 0 until height) {
                val levelOffset = startPos.to2DPosition()
                    .withRelativeX(-currSize)
                    .withRelativeY(-currSize)
                val levelSize = create(1 + currSize * 2, 1 + currSize * 2)
                levelSize.fetchPositions().forEach { position ->
                    val pos = position.plus(levelOffset).toPosition3D(currLevel.get())
                    val top = wall.withForegroundColor(PYRAMID_COLOR)
                    gameArea.setBlockAt(
                        position = pos,
                        block = Block.newBuilder<Tile>()
                            .withTop(top)
                            .withFront(wall)
                            .withEmptyTile(empty())
                            .build()
                    )

                }
                currLevel.decrementAndGet()
            }
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
    }
}
