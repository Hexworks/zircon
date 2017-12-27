package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.graphics.TextCharacterString
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.graphics.TextWrap
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.behavior.impl.DefaultCursorHandler

data class DefaultTextCharacterString(private val textChars: List<TextCharacter>,
                                      private val textWrap: TextWrap,
                                      private val boundable: Boundable = DefaultBoundable(
                                              size = Size.of(textChars.size, 1)))
    : TextCharacterString, Boundable by boundable, Collection<TextCharacter> by textChars {

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        val (cols, rows) = surface.getBoundableSize()
        require(offset.column < cols) {
            "Can't draw string at offset column '${offset.column}' because the draw surface is smaller: $cols"
        }
        require(offset.row < rows) {
            "Can't draw string at offset row '${offset.row}' because the draw surface is smaller: $rows"
        }
        val charIter = textChars.iterator()
        val cursorHandler = DefaultCursorHandler(surface.getBoundableSize())
        cursorHandler.putCursorAt(offset)
        surface.setCharacterAt(cursorHandler.getCursorPosition(), charIter.next())

        if (textWrap == TextWrap.WORD_WRAP) {
            var wordCharacterIterator = WordCharacterIterator(charIter)
            var rowSize = getBoundableSize().rows;

            if (cursorIsNotAtBottomRightCorner(cursorHandler) && wordCharacterIterator.hasNext()) {
                do {

                    var nextWord = wordCharacterIterator.next();
                    var wordSize = nextWord.size;
                    var spaceRemaining = rowSize - cursorHandler.getCursorPosition().row

                    if (spaceRemaining >= wordSize) {
                        //this means we can plunk the word on our line
                        nextWord.forEach({ tc ->
                            cursorHandler.moveCursorForward()
                            surface.setCharacterAt(cursorHandler.getCursorPosition(), tc)
                        })
                    } else {
                        //this means we are at the last column and therefore we cannot wrap anymore. Therefore we should
                        //stop rendering
                        val column = cursorHandler.getCursorPosition().column
                        if(column == getBoundableSize().columns){
                            return
                        }

                        //as our word couldn't fit on the last line lets move down a column
                        cursorHandler.putCursorAt(Position(column + 1, 1))
                        //recalculate our space remaining
                        spaceRemaining = rowSize - cursorHandler.getCursorPosition().row

                        if (spaceRemaining >= wordSize) {
                            //this means we can plunk the word on our line
                            nextWord.forEach({ tc ->
                                cursorHandler.moveCursorForward()
                                surface.setCharacterAt(cursorHandler.getCursorPosition(), tc)
                            })
                        }else{
                            //this means our current word is too big for an entire line! therefore we should fall back
                            //to character wrap
                            nextWord.forEach({
                                tc ->
                                cursorHandler.moveCursorForward()
                                surface.setCharacterAt(cursorHandler.getCursorPosition(), tc)
                            })

                        }
                    }

                } while (cursorIsNotAtBottomRightCorner(cursorHandler) && wordCharacterIterator.hasNext())
            }

            return
        }

        if (cursorIsNotAtBottomRightCorner(cursorHandler) && charIter.hasNext()) {
            do {
                cursorHandler.moveCursorForward()
                surface.setCharacterAt(cursorHandler.getCursorPosition(), charIter.next())
            } while (cursorHandler.isCursorAtTheEndOfTheLine().not() && charIter.hasNext())

            if (textWrap == TextWrap.WRAP && charIter.hasNext() && cursorHandler.isCursorAtTheLastRow().not()) {
                do {
                    cursorHandler.moveCursorForward()
                    surface.setCharacterAt(cursorHandler.getCursorPosition(), charIter.next())
                } while (cursorIsNotAtBottomRightCorner(cursorHandler) && charIter.hasNext())
            }
        }
    }

    override fun toTextImage() = TextImageBuilder.newBuilder()
            .size(getBoundableSize())
            .build().apply {
        textChars.forEachIndexed { idx, tc ->
            setCharacterAt(Position.of(idx, 0), tc)
        }
    }

    override fun getTextCharacters() = textChars

    override fun plus(other: TextCharacterString) = DefaultTextCharacterString(
            textChars = textChars.plus(other.getTextCharacters()),
            textWrap = textWrap)

    private fun cursorIsNotAtBottomRightCorner(cursorHandler: DefaultCursorHandler) =
            (cursorHandler.isCursorAtTheLastRow() && cursorHandler.isCursorAtTheEndOfTheLine()).not()
}