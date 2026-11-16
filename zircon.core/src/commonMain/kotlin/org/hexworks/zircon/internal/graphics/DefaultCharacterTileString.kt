package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.extensions.isCursorAtTheEndOfTheLine
import org.hexworks.zircon.api.behavior.extensions.isCursorAtTheLastRow
import org.hexworks.zircon.api.behavior.extensions.moveCursorForward
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.CharacterTileString
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler

public data class DefaultCharacterTileString (
    override val characterTiles: List<CharacterTile>,
    override val size: Size,
    private val textWrap: TextWrap
) : CharacterTileString,
    Iterable<CharacterTile> by characterTiles {

    override val tiles: Map<Position, Tile>
        get() = buildTiles()


    private fun buildTiles(): Map<Position, Tile> {
        val tiles = mutableMapOf<Position, Tile>()
        val (cols, rows) = size
        val charIter = characterTiles.iterator()
        val cursorHandler = DefaultCursorHandler(size)
        if (characterTiles.isEmpty()) return tiles.toMap()

        if (textWrap == TextWrap.WORD_WRAP) {
            wrapWords(characterTiles, cols) { it.character }.take(rows).forEachIndexed { y, line ->
                line.forEachIndexed { x, tile -> tiles[Position(x, y)] = tile }
            }
            return tiles.toMap()
        }

        tiles[cursorHandler.cursorPosition] = charIter.next()

        if (cursorIsNotAtBottomRightCorner(cursorHandler) && charIter.hasNext()) {
            do {
                cursorHandler.moveCursorForward()
                tiles[cursorHandler.cursorPosition] = charIter.next()
            } while (cursorHandler.isCursorAtTheEndOfTheLine.not() && charIter.hasNext())

            if (textWrap == TextWrap.WRAP && charIter.hasNext() && cursorHandler.isCursorAtTheLastRow.not()) {
                do {
                    cursorHandler.moveCursorForward()
                    tiles[cursorHandler.cursorPosition] = charIter.next()
                } while (cursorIsNotAtBottomRightCorner(cursorHandler) && charIter.hasNext())
            }
        }
        return tiles.toMap()
    }

    override fun plus(other: CharacterTileString): CharacterTileString = DefaultCharacterTileString(
        characterTiles = characterTiles.plus(other.characterTiles),
        textWrap = textWrap,
        size = size + other.size
    )

    override fun withSize(size: Size): CharacterTileString {
        return copy(size = size)
    }

    private fun cursorIsNotAtBottomRightCorner(cursorHandler: DefaultCursorHandler) =
        (cursorHandler.isCursorAtTheLastRow && cursorHandler.isCursorAtTheEndOfTheLine).not()
}
