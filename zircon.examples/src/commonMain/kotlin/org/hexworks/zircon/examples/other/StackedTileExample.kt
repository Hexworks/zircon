package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.graphicalTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * Stacked tiles: tiles drawn on top of each other in one cell. Each column shows the parts
 * (`+`) and the stacked result (`=`): a character stack, a stack extended with another tile,
 * a stack with a swapped base, a stack of sprites, and a stack of stacks.
 */
public val stackedTileExample: Example = Example("Stacked tiles", textOnly = false) { context ->
    val canvas = layer {
        size = context.contentSize
        offset = Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP)
    }
    val start = Position(4, 3)
    simpleStack(canvas, start)
    createVsPush(canvas, start.withRelativeX(2))
    withBaseTile(canvas, start.withRelativeX(4))
    graphicalStack(canvas, start.withRelativeX(6))
    stackOfStacks(canvas, start.withRelativeX(8))
    ExampleView(context.emptyContent(), layers = listOf(canvas))
}

private fun simpleStack(canvas: Layer, position: Position) {
    canvas.draw(stackXAndPlus(), position)
}

private fun createVsPush(canvas: Layer, position: Position) {
    val circumflex = charTile(Symbols.CIRCUMFLEX, ansi(ANSIColor.RED), Color.TRANSPARENT)
    val stack = stackXAndPlus()
    // The same stack extended two ways: a new stack with the old one as its base, and the
    // parts of the old one plus the new tile.
    val created = StackedTile.create(stack, circumflex)
    val pushed = StackedTile.create(stack.baseTile, *(stack.rest + circumflex).toTypedArray())
    canvas.draw(created, position)
    canvas.draw(pushed, position.withRelativeY(1))
}

private fun withBaseTile(canvas: Layer, position: Position) {
    val stack = stackXAndPlus()
    val switchedBase = StackedTile.create(
        charTile('O', ansi(ANSIColor.BRIGHT_WHITE), ansi(ANSIColor.GREEN)),
        *stack.rest.toTypedArray()
    )
    canvas.draw(switchedBase, position)
}

private fun graphicalStack(canvas: Layer, position: Position) {
    val werewolf = sprite("Werewolf")
    val fireAnt = sprite("Fire ant")
    val stack = StackedTile.create(werewolf, fireAnt)
    drawAdditionAt(position, canvas, stack, werewolf, fireAnt)
}

private fun stackOfStacks(canvas: Layer, position: Position) {
    val graphical = StackedTile.create(sprite("Gremlin"), sprite("Fire ant"))
    val character = stackXAndPlus()
    val marker = charTile(Symbols.BULLET, ansi(ANSIColor.CYAN), Color.TRANSPARENT)
    val full = StackedTile.create(character, graphical, marker)
    drawAdditionAt(position, canvas, full, graphical, character, marker)
}

private fun drawAdditionAt(position: Position, canvas: Layer, result: Tile, vararg tiles: Tile) {
    var y = 0
    for (tile in tiles) {
        if (y > 0) {
            canvas.draw(charTile('+', ansi(ANSIColor.WHITE), ansi(ANSIColor.BLACK)), position.withRelativeY(y - 1))
        }
        canvas.draw(tile, position.withRelativeY(y))
        y += 2
    }
    canvas.draw(charTile('=', ansi(ANSIColor.WHITE), ansi(ANSIColor.BLACK)), position.withRelativeY(y - 1))
    canvas.draw(result, position.withRelativeY(y))
}

private fun sprite(tileName: String): GraphicalTile = graphicalTile {
    name = tileName
    tileset = GraphicalTilesetResources.nethack16x16()
}

private fun stackXAndPlus(): StackedTile = StackedTile.create(
    charTile('x', ansi(ANSIColor.BRIGHT_YELLOW), ansi(ANSIColor.BLUE)),
    charTile('+', ansi(ANSIColor.BRIGHT_MAGENTA), Color.TRANSPARENT)
)

private fun ansi(color: ANSIColor): Color = DefaultAnsiPalette[color]

private fun charTile(char: Char, foreground: Color, background: Color): CharacterTile = characterTile {
    character = char
    withStyleSet {
        foregroundColor = foreground
        backgroundColor = background
    }
}
