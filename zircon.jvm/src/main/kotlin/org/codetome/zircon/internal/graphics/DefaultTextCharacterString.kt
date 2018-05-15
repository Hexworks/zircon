package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.graphics.TextCharacterString
import org.codetome.zircon.api.graphics.TextWrap
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.behavior.impl.DefaultCursorHandler

data class DefaultTextCharacterString(private val textChars: List<TextCharacter>,
                                      private val textWrap: TextWrap,
                                      private val boundable: Boundable = DefaultBoundable(
                                              size = Size.create(textChars.size, 1)))
    : TextCharacterString, Boundable by boundable, Collection<TextCharacter> by textChars {

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        val (cols, rows) = surface.getBoundableSize()
        require(offset.x < cols) {
            "Can't draw string at offset xLength '${offset.x}' because the draw surface is smaller: $cols"
        }
        require(offset.y < rows) {
            "Can't draw string at offset yLength '${offset.y}' because the draw surface is smaller: $rows"
        }
        val charIter = textChars.iterator()
        val cursorHandler = DefaultCursorHandler(surface.getBoundableSize())
        cursorHandler.putCursorAt(offset)

        if (textWrap == TextWrap.WORD_WRAP) {
            val wordCharacterIterator = WordCharacterIterator(charIter)

            if (cursorIsNotAtBottomRightCorner(cursorHandler) && wordCharacterIterator.hasNext()) {
                do {
                    val nextWord = wordCharacterIterator.next()
                    val wordSize = nextWord.size
                    var spaceRemaining = cols - cursorHandler.getCursorPosition().x

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
                        //this means we are at the last yLength and therefore we cannot wrap anymore. Therefore we should
                        //stop rendering
                        val row = cursorHandler.getCursorPosition().y
                        if(row == rows - 1){
                            return
                        }

                        //as our word couldn't fit on the last line lets move down a yLength
                        cursorHandler.putCursorAt(cursorHandler.getCursorPosition().withRelativeY(1).withX(0))
                        //recalculate our space remaining
                        spaceRemaining = cols - cursorHandler.getCursorPosition().x

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
            setCharacterAt(Position.create(idx, 0), tc)
        }
    }

    override fun getTextCharacters() = textChars

    override fun plus(other: TextCharacterString) = DefaultTextCharacterString(
            textChars = textChars.plus(other.getTextCharacters()),
            textWrap = textWrap)

    private fun cursorIsNotAtBottomRightCorner(cursorHandler: DefaultCursorHandler) =
            (cursorHandler.isCursorAtTheLastRow() && cursorHandler.isCursorAtTheEndOfTheLine()).not()
}
