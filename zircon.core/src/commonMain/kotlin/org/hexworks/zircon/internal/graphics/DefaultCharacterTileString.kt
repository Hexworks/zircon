package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.CharacterTileString
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler

data class DefaultCharacterTileString(override val characterTiles: List<CharacterTile>,
                                      override val size: Size,
                                      private val textWrap: TextWrap)
    : CharacterTileString,
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
            val wordCharacterIterator = CharacterTileIterator(charIter)

            if (cursorIsNotAtBottomRightCorner(cursorHandler) && wordCharacterIterator.hasNext()) {
                do {
                    val nextWord = wordCharacterIterator.next()
                    val wordSize = nextWord.size
                    var spaceRemaining = cols - cursorHandler.cursorPosition.x

                    //the word is bigger then 1 line when this happens we should character wrap
                    if (wordSize > cols) {
                        nextWord.forEach { tc ->
                            tiles[cursorHandler.cursorPosition] = tc
                            cursorHandler.moveCursorForward()
                        }
                    }

                    //this means we can plunk the word on our line
                    if (spaceRemaining >= wordSize) {
                        nextWord.forEach { tc ->
                            tiles[cursorHandler.cursorPosition] = tc
                            cursorHandler.moveCursorForward()
                        }
                    } else {
                        //this means we are at the last yLength and therefore we cannot wrap anymore. Therefore we should
                        //stop rendering
                        val row = cursorHandler.cursorPosition.y
                        if (row == rows - 1) {
                            return tiles.toMap()
                        }

                        //as our word couldn't fit on the last line lets move down a yLength
                        cursorHandler.cursorPosition = cursorHandler.cursorPosition.withRelativeY(1).withX(0)
                        //recalculate our space remaining
                        spaceRemaining = cols - cursorHandler.cursorPosition.x

                        if (spaceRemaining >= wordSize) {
                            //this means we can plunk the word on our line
                            nextWord.forEach { tc ->
                                tiles[cursorHandler.cursorPosition] = tc
                                cursorHandler.moveCursorForward()
                            }
                        }
                    }

                } while (cursorIsNotAtBottomRightCorner(cursorHandler) && wordCharacterIterator.hasNext())
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

    override fun plus(other: CharacterTileString) = DefaultCharacterTileString(
            characterTiles = characterTiles.plus(other.characterTiles),
            textWrap = textWrap,
            size = size + other.size)

    override fun withSize(size: Size): CharacterTileString {
        return copy(size = size)
    }

    private fun cursorIsNotAtBottomRightCorner(cursorHandler: DefaultCursorHandler) =
            (cursorHandler.isCursorAtTheLastRow && cursorHandler.isCursorAtTheEndOfTheLine).not()
}
