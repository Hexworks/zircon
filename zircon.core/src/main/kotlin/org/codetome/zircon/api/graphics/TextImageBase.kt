package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.data.Cell
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.behavior.impl.DefaultStyleable

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
 * If puzzled you can always take a look at [org.codetome.zircon.internal.graphics.InMemoryTextImage]
 * for inspiration.
 */
abstract class TextImageBase private constructor(boundable: Boundable,
                                             styleable: Styleable)
    : TextImage, Boundable by boundable, Styleable by styleable {

    constructor(size: Size,
                styleSet: StyleSet) : this(
            boundable = DefaultBoundable(size = size),
            styleable = DefaultStyleable(styleSet))

    override fun draw(drawable: Drawable, offset: Position) {
        drawable.drawOnto(this, offset)
    }

    override fun putText(text: String, position: Position) {
        text.forEachIndexed { col, char ->
            setCharacterAt(position.withRelativeX(col), TileBuilder
                    .newBuilder()
                    .styleSet(toStyleSet())
                    .character(char)
                    .build())
        }
    }

    override fun applyStyle(styleSet: StyleSet, offset: Position, size: Size) {
        setStyleFrom(styleSet)
        size.fetchPositions().forEach { pos ->
            pos.plus(offset).let { fixedPos ->
                getCharacterAt(fixedPos).map { char: Tile ->
                    setCharacterAt(fixedPos, char.withStyle(styleSet))
                }
            }
        }
    }

    override fun setCharacterAt(position: Position, character: Char) = setCharacterAt(position, TileBuilder.newBuilder()
            .character(character)
            .styleSet(toStyleSet())
            .build())

    override fun fetchCells(): Iterable<Cell> {
        return fetchCellsBy(Position.defaultPosition(), getBoundableSize())
    }
}
