package org.hexworks.zircon.examples.game

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.radioButton
import org.hexworks.zircon.api.builder.component.radioButtonGroup
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.data.block
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.game.gameArea
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.extensions.darkenByPercent
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.data.extensions.to2DPosition
import org.hexworks.zircon.api.data.extensions.toPosition3D
import org.hexworks.zircon.api.data.extensions.toSize3D
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY
import org.hexworks.zircon.api.behavior.extensions.scrollOneBackward
import org.hexworks.zircon.api.behavior.extensions.scrollOneDown
import org.hexworks.zircon.api.behavior.extensions.scrollOneForward
import org.hexworks.zircon.api.behavior.extensions.scrollOneLeft
import org.hexworks.zircon.api.behavior.extensions.scrollOneRight
import org.hexworks.zircon.api.behavior.extensions.scrollOneUp
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameAreaTileFilter
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.game.gameAreaComponentRenderer
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * Pyramids in a game area that scrolls with the arrow keys (`U`/`D` change the level),
 * with a depth filter darkening lower levels and a switch between the projection modes.
 */
public val scrollingExample: Example = Example("Scrolling") { context ->
    val projections = vBox {
        preferredSize = Size(24, 4)
        position = Position(1, 1)
        decorations { +box(BoxType.LEFT_RIGHT_DOUBLE, "Projection Mode") }
    }
    val topDown = radioButton {
        +"Top Down"
        key = ProjectionMode.TOP_DOWN.name
    }
    val topDownOblique = radioButton {
        +"Top Down Oblique"
        key = ProjectionMode.TOP_DOWN_OBLIQUE_FRONT.name
    }
    val projectionsGroup = radioButtonGroup { }.apply {
        addComponent(topDown)
        addComponent(topDownOblique)
    }
    projections.addComponent(topDown)
    projections.addComponent(topDownOblique)
    val projectionProperty = projectionsGroup.selectedButtonProperty.bindTransform { button ->
        ProjectionMode.entries.firstOrNull { it.name == button?.key } ?: ProjectionMode.TOP_DOWN
    }

    val contentSize = context.contentSize
    val gameArea = gameArea<Tile, Block<Tile>> {
        actualSize = Size3D(200, 200, LEVEL_COUNT)
        visibleSize = Size(contentSize.width - 2, contentSize.height - 2).toSize3D(5)
        filters = listOf(DepthFilter)
    }
    val gamePanel: Panel = panel {
        preferredSize = contentSize
        componentRenderer = gameAreaComponentRenderer<Panel, Tile, Block<Tile>>(gameArea, projectionProperty, FILLER)
        decorations { +box(BoxType.TOP_BOTTOM_DOUBLE, "Game Area with Scrolling: arrows, U, D") }
    }
    gamePanel.addComponent(projections)
    topDown.isSelected = true

    generatePyramid(3, Position3D(30, 5, 2), gameArea)
    generatePyramid(6, Position3D(40, 9, 5), gameArea)
    generatePyramid(5, Position3D(35, 21, 4), gameArea)

    var keys: Subscription? = null
    ExampleView(
        content = gamePanel,
        onShown = {
            keys = context.screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, _ ->
                when (event.code) {
                    KeyCode.UP -> gameArea.scrollOneBackward()
                    KeyCode.DOWN -> gameArea.scrollOneForward()
                    KeyCode.LEFT -> gameArea.scrollOneLeft()
                    KeyCode.RIGHT -> gameArea.scrollOneRight()
                    KeyCode.KEY_U -> gameArea.scrollOneUp()
                    KeyCode.KEY_D -> gameArea.scrollOneDown()
                    else -> Unit
                }
                Processed
            }
        },
        onHidden = { keys?.dispose() },
    )
}

/** Darkens tiles by how deep below the top visible level they are. */
private object DepthFilter : GameAreaTileFilter<Tile> {
    override fun transform(visibleSize: Size3D, offsetPosition: Position3D, blockTileType: BlockTileType, tile: Tile): Tile {
        if (tile !is CharacterTile) return tile
        val step = when (blockTileType) {
            BlockTileType.TOP -> 1 / (visibleSize.zLength.toDouble() * 5)
            BlockTileType.FRONT -> 1 / (visibleSize.zLength.toDouble() * 3)
            else -> 1.0
        }
        return tile.withForegroundColor(
            tile.foregroundColor.darkenByPercent(step * (visibleSize.zLength - offsetPosition.z).toDouble())
        )
    }
}

private fun generatePyramid(height: Int, startPos: Position3D, gameArea: GameArea<Tile, Block<Tile>>) {
    val wall = characterTile {
        character = Symbols.BLOCK_SOLID
        withStyleSet { foregroundColor = PYRAMID_COLOR }
    }
    var currLevel = startPos.z
    for (currSize in 0 until height) {
        val levelOffset = startPos.to2DPosition()
            .withRelativeX(-currSize)
            .withRelativeY(-currSize)
        val levelSize = Size(1 + currSize * 2, 1 + currSize * 2)
        levelSize.fetchPositions().forEach { position ->
            val pos = (position + levelOffset).toPosition3D(currLevel)
            gameArea.setBlockAt(pos, block {
                top = wall
                front = wall
                emptyTile = Tile.empty()
            })
        }
        currLevel--
    }
}

private const val LEVEL_COUNT = 10
private val FILLER: Tile = characterTile {
    character = 'x'
    withStyleSet { backgroundColor = Color.fromString("#e7b751") }
}
private val PYRAMID_COLOR = Color.fromString("#ecc987")
