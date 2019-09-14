package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.CharacterTileString
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler

data class DefaultCharacterTileString(private val textChars: List<CharacterTile>,
                                      private val size: Size = Size.create(textChars.size, 1),
                                      private val textWrap: TextWrap)
    : CharacterTileString,
        Iterable<CharacterTile> by textChars {

    override fun drawOnto(surface: DrawSurface, position: Position) {
        val (cols, rows) = surface.size
        require(position.x < cols) {
            "Can't draw string at position xLength '${position.x}' because the draw surface is smaller: $cols"
        }
        require(position.y < rows) {
            "Can't draw string at position yLength '${position.y}' because the draw surface is smaller: $rows"
        }
        val charIter = textChars.iterator()
        val cursorHandler = DefaultCursorHandler(surface.size)
        cursorHandler.putCursorAt(position)

        if (textWrap == TextWrap.WORD_WRAP) {
            val wordCharacterIterator = CharacterTileIterator(charIter)

            if (cursorIsNotAtBottomRightCorner(cursorHandler) && wordCharacterIterator.hasNext()) {
                do {
                    val nextWord = wordCharacterIterator.next()
                    val wordSize = nextWord.size
                    var spaceRemaining = cols - cursorHandler.cursorPosition().x

                    //the word is bigger then 1 line when this happens we should character wrap
                    if (wordSize > cols) {
                        nextWord.forEach { tc ->
                            surface.setTileAt(cursorHandler.cursorPosition(), tc)
                            cursorHandler.moveCursorForward()
                        }
                    }

                    //this means we can plunk the word on our line
                    if (spaceRemaining >= wordSize) {
                        nextWord.forEach { tc ->
                            surface.setTileAt(cursorHandler.cursorPosition(), tc)
                            cursorHandler.moveCursorForward()
                        }
                    } else {
                        //this means we are at the last yLength and therefore we cannot wrap anymore. Therefore we should
                        //stop rendering
                        val row = cursorHandler.cursorPosition().y
                        if (row == rows - 1) {
                            return
                        }

                        //as our word couldn't fit on the last line lets move down a yLength
                        cursorHandler.putCursorAt(cursorHandler.cursorPosition().withRelativeY(1).withX(0))
                        //recalculate our space remaining
                        spaceRemaining = cols - cursorHandler.cursorPosition().x

                        if (spaceRemaining >= wordSize) {
                            //this means we can plunk the word on our line
                            nextWord.forEach { tc ->
                                surface.setTileAt(cursorHandler.cursorPosition(), tc)
                                cursorHandler.moveCursorForward()
                            }
                        }
                    }

                } while (cursorIsNotAtBottomRightCorner(cursorHandler) && wordCharacterIterator.hasNext())
            }

            return
        }

        surface.setTileAt(cursorHandler.cursorPosition(), charIter.next())
        if (cursorIsNotAtBottomRightCorner(cursorHandler) && charIter.hasNext()) {
            do {
                cursorHandler.moveCursorForward()
                surface.setTileAt(cursorHandler.cursorPosition(), charIter.next())
            } while (cursorHandler.isCursorAtTheEndOfTheLine().not() && charIter.hasNext())

            if (textWrap == TextWrap.WRAP && charIter.hasNext() && cursorHandler.isCursorAtTheLastRow().not()) {
                do {
                    cursorHandler.moveCursorForward()
                    surface.setTileAt(cursorHandler.cursorPosition(), charIter.next())
                } while (cursorIsNotAtBottomRightCorner(cursorHandler) && charIter.hasNext())
            }
        }
    }

    override fun toTileGraphic(tileset: TilesetResource) =
            TileGraphicsBuilder.newBuilder()
                    .withTileset(tileset)
                    .withSize(size)
                    .build().apply {
                        textChars.forEachIndexed { idx, tc ->
                            setTileAt(Position.create(idx, 0), tc)
                        }
                    }

    override fun textCharacters() = textChars

    override fun plus(other: CharacterTileString) = DefaultCharacterTileString(
            textChars = textChars.plus(other.textCharacters()),
            textWrap = textWrap)

    private fun cursorIsNotAtBottomRightCorner(cursorHandler: DefaultCursorHandler) =
            (cursorHandler.isCursorAtTheLastRow() && cursorHandler.isCursorAtTheEndOfTheLine()).not()
}
