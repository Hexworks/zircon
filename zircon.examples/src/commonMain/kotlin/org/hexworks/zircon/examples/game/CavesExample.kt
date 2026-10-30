package org.hexworks.zircon.examples.game

import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.data.block
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.game.gameArea
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.data.extensions.to2DSize
import org.hexworks.zircon.api.data.extensions.toPosition3D
import org.hexworks.zircon.api.data.extensions.toSize3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.game.gameAreaComponentRenderer
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import kotlin.random.Random

/**
 * A game area filled by a cellular-automaton cave generator, rendered top-down through
 * a plain label. (The old example subclassed `BaseGameArea`; in 2.0 that base stays
 * internal, so the area comes from the `gameArea {}` builder.)
 */
public val cavesExample: Example = Example("Caves") { context ->
    val visibleSize = context.contentSize.toSize3D(10)
    val area = gameArea<Tile, Block<Tile>> {
        this.visibleSize = visibleSize
        // Bigger than any screen the app runs on: the visible size may not exceed it.
        actualSize = Size3D(300, 200, 100)
    }
    makeCaves(area)
    val view = label {
        preferredSize = context.contentSize
        componentRenderer = gameAreaComponentRenderer(area, ProjectionMode.TOP_DOWN, Tile.empty())
    }
    ExampleView(view)
}

private fun makeCaves(gameArea: GameArea<Tile, Block<Tile>>, smoothTimes: Int = 8) {
    val random = Random(1337)
    val width = gameArea.actualSize.xLength
    val height = gameArea.actualSize.yLength
    var tiles: MutableMap<Position, Tile> = mutableMapOf()
    gameArea.actualSize.to2DSize().fetchPositions().forEach { pos ->
        tiles[pos] = if (random.nextDouble() < 0.5) FLOOR else WALL
    }
    repeat(smoothTimes) {
        val newTiles: MutableMap<Position, Tile> = mutableMapOf()
        for (x in 0 until width) {
            for (y in 0 until height) {
                var floors = 0
                var rocks = 0
                for (ox in -1..1) {
                    for (oy in -1..1) {
                        if (x + ox < 0 || x + ox >= width || y + oy < 0 || y + oy >= height) continue
                        if (tiles[Position(x + ox, y + oy)] === FLOOR) floors++ else rocks++
                    }
                }
                newTiles[Position(x, y)] = if (floors >= rocks) FLOOR else WALL
            }
        }
        tiles = newTiles
    }
    tiles.forEach { (pos, tile) ->
        gameArea.setBlockAt(pos.toPosition3D(0), block {
            content = tile
            emptyTile = Tile.empty()
        })
    }
}

private val FLOOR: Tile = characterTile {
    character = Symbols.INTERPUNCT
    withStyleSet { foregroundColor = DefaultAnsiPalette[ANSIColor.YELLOW] }
}

private val WALL: Tile = characterTile {
    character = '#'
    withStyleSet { foregroundColor = Color.fromString("#999999") }
}
