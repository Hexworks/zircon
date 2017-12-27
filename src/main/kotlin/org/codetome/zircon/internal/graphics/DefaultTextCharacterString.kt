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

        if (textWrap == TextWrap.WORD_WRAP) {
            var wordCharacterIterator = WordCharacterIterator(charIter)

            if (cursorIsNotAtBottomRightCorner(cursorHandler) && wordCharacterIterator.hasNext()) {
                do {
                    var nextWord = wordCharacterIterator.next();

                    var wordSize = nextWord.size;
                    var spaceRemaining = cols - cursorHandler.getCursorPosition().column

                    //the word is bigger then 1 line when this happens we should character wrap
                    if(wordSize > cols){
                        nextWord.forEach({
                            tc ->
                            surface.setCharacterAt(cursorHandler.getCursorPosition(), tc)
                            cursorHandler.moveCursorForward()
                        })
                    }

                    //this means we can plunk the word on our line
                    if (spaceRemaining >= wordSize) {
                        nextWord.forEach({ tc ->
                            surface.setCharacterAt(cursorHandler.getCursorPosition(), tc)
                            cursorHandler.moveCursorForward()
                        })
                    } else {
                        //this means we are at the last row and therefore we cannot wrap anymore. Therefore we should
                        //stop rendering
                        val row = cursorHandler.getCursorPosition().row
                        if(row == rows - 1){
                            return
                        }

                        //as our word couldn't fit on the last line lets move down a row
                        cursorHandler.putCursorAt(cursorHandler.getCursorPosition().withRelativeRow(1).withColumn(0))
                        //recalculate our space remaining
                        spaceRemaining = cols - cursorHandler.getCursorPosition().column

                        if (spaceRemaining >= wordSize) {
                            //this means we can plunk the word on our line
                            nextWord.forEach({ tc ->
                                surface.setCharacterAt(cursorHandler.getCursorPosition(), tc)
                                cursorHandler.moveCursorForward()
                            })
                        }
                    }

                } while (cursorIsNotAtBottomRightCorner(cursorHandler) && wordCharacterIterator.hasNext())
            }

            return
        }

        surface.setCharacterAt(cursorHandler.getCursorPosition(), charIter.next())
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