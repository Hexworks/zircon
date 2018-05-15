package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.behavior.impl.DefaultStyleable
import java.util.concurrent.atomic.AtomicReference

/**
 * Base class which you can use if you want to implement your own version
 * of [TextImage]. Comes with sensible defaults so you only have to implement
 * - [TextImage.resize]
 * - [TextImage.toSubImage]
 * - [TextImage.fetchCells]
 * - [TextImage.fetchCellsBy]
 * - [TextImage.transform]
 * - [TextImage.combineWith]
 * - [TextImage.setCharacterAt]
 * - [TextImage.getCharacterAt]
 * - [TextImage.drawOnto]
 *
 * If puzzled you can always take a look at [org.codetome.zircon.internal.graphics.DefaultTextImage]
 * for inspiration.
 */
abstract class TextImageBase private constructor(boundable: Boundable,
                                             styleable: Styleable)
    : TextImage, Boundable by boundable, Styleable by styleable {

    constructor(size: Size,
                styleSet: StyleSet) : this(
            boundable = DefaultBoundable(size = size),
            styleable = DefaultStyleable(AtomicReference(styleSet)))

    override fun draw(drawable: Drawable, offset: Position) {
        drawable.drawOnto(this, offset)
    }

    override fun putText(text: String, position: Position) {
        text.forEachIndexed { col, char ->
            setCharacterAt(position.withRelativeX(col), TextCharacterBuilder
                    .newBuilder()
                    .styleSet(toStyleSet())
                    .character(char)
                    .build())
        }
    }

    @Synchronized
    override fun applyStyle(styleSet: StyleSet, offset: Position, size: Size) {
        setStyleFrom(styleSet)
        size.fetchPositions().forEach { pos ->
            pos.plus(offset).let { fixedPos ->
                getCharacterAt(fixedPos).map { char: TextCharacter ->
                    setCharacterAt(fixedPos, char.withStyle(styleSet))
                }
            }
        }
    }

    override fun setCharacterAt(position: Position, character: Char) = setCharacterAt(position, TextCharacterBuilder.newBuilder()
            .character(character)
            .styleSet(toStyleSet())
            .build())

    override fun fetchCells(): Iterable<Cell> {
        return fetchCellsBy(Position.defaultPosition(), getBoundableSize())
    }
}
