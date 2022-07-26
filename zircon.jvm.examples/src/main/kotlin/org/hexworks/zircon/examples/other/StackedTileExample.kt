package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CP437TilesetResources.bisasam16x16
import org.hexworks.zircon.api.GraphicalTilesetResources.nethack16x16
import org.hexworks.zircon.api.Modifiers.border
import org.hexworks.zircon.api.SwingApplications.startApplication
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.data.StackedTile.Companion.create
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.grid.TileGrid

object StackedTileExample {

    private const val GRID_WIDTH = 60
    private const val GRID_HEIGHT = 30
    private val SIZE = Size.create(GRID_WIDTH, GRID_HEIGHT)

    @JvmStatic
    fun main(args: Array<String>) {
        val app = startApplication(
            AppConfig.newBuilder()
                .withDefaultTileset(bisasam16x16())
                .withSize(SIZE)
                .build()
        )
            .tileGrid
        val p = Position.create(4, 5)
        simpleStack(app, p)
        createVsPush(app, movePos(p, 2))
        withBaseTile(app, movePos(p, 4))
        graphicalStack(app, movePos(p, 6))
        letsGoApeshit(app, movePos(p, 8))
    }

    private fun simpleStack(app: TileGrid, gridPosition: Position) {
        app.draw(stackXAndPlus(), gridPosition)
    }

    private fun createVsPush(app: TileGrid, gridPosition: Position) {
        val circumflex = charTile(Symbols.CIRCUMFLEX, ANSITileColor.RED, transparent())
        val stackedTile1 = stackXAndPlus()
        val stackedTile2Created = create(
            stackedTile1,
            circumflex
        )
        val stackedTile2Pushed = stackedTile1
            .withPushedTile(circumflex)
        app.draw(stackedTile2Created, gridPosition)
        app.draw(stackedTile2Pushed, gridPosition.withRelativeY(1))
    }

    private fun withBaseTile(app: TileGrid, gridPosition: Position) {
        val switchedBaseTile = stackXAndPlus().withBaseTile(
            charTile('O', ANSITileColor.BRIGHT_WHITE, ANSITileColor.GREEN)
        )
        app.draw(switchedBaseTile, gridPosition)
    }

    private fun graphicalStack(app: TileGrid, gridPosition: Position) {
        val werewolf = graphicalTile("Werewolf")
        val giantAnt = graphicalTile("Fire ant")
        val graphicalStack = create(
            werewolf,
            giantAnt
        )
        drawAdditionAt(gridPosition, app, graphicalStack, werewolf, giantAnt)
    }

    private fun letsGoApeshit(app: TileGrid, gridPosition: Position) {
        val stackGraphical = create(
            graphicalTile("Gremlin"),
            graphicalTile("Fire ant")
        )
        val stackCharacter = stackXAndPlus()
        val border: Tile = charTile(' ', ANSITileColor.CYAN, transparent())
            .withAddedModifiers(border())
        val fullStack = create(
            stackCharacter,
            stackGraphical,
            border
        )
        drawAdditionAt(gridPosition, app, fullStack, stackGraphical, stackCharacter, border)
    }

    private fun drawAdditionAt(gridPosition: Position, app: TileGrid, result: Tile, vararg tiles: Tile) {
        var y = 0
        for (tile in tiles) {
            if (y > 0) {
                app.draw(charTile('+', ANSITileColor.WHITE, ANSITileColor.BLACK), gridPosition.withRelativeY(y - 1))
            }
            app.draw(tile, gridPosition.withRelativeY(y))
            y += 2
        }
        app.draw(charTile('=', ANSITileColor.WHITE, ANSITileColor.BLACK), gridPosition.withRelativeY(y - 1))
        app.draw(result, gridPosition.withRelativeY(y))
    }

    private fun graphicalTile(tileName: String): GraphicalTile {
        val tileset = nethack16x16()
        return Tile.newBuilder()
            .withName(tileName)
            .withTileset(tileset)
            .buildGraphicalTile()
    }

    private fun stackXAndPlus(): StackedTile {
        return create(
            charTile('x', ANSITileColor.BRIGHT_YELLOW, ANSITileColor.BLUE),
            charTile('+', ANSITileColor.BRIGHT_MAGENTA, transparent())
        )
    }

    private fun movePos(position: Position, offset: Int): Position {
        return position.withRelativeX(offset)
    }

    private fun charTile(character: Char, foreground: TileColor, background: TileColor): CharacterTile {
        return Tile.newBuilder()
            .withCharacter(character)
            .withForegroundColor(foreground)
            .withBackgroundColor(background)
            .buildCharacterTile()
    }
}